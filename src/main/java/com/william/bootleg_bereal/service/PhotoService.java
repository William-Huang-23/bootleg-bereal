package com.william.bootleg_bereal.service;

import com.william.bootleg_bereal.model.Comment;
import com.william.bootleg_bereal.model.User;
import com.william.bootleg_bereal.utilities.ImageUtils;
import com.william.bootleg_bereal.repository.PhotoRepository;
import com.william.bootleg_bereal.model.Photo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
public class PhotoService {
    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommentService commentService;

    public List<Photo> downloadAllPhoto() throws DataFormatException, IOException {
        List<Photo> photoList = photoRepository.findAll();

        for (Photo photo : photoList) {
            photo.setImageData(ImageUtils.decompressImage(photo.getImageData()));
        }

        return photoList;
    }

    public Optional<Photo> downloadPhotoByPhotoId(String photoId) {
        return photoRepository.findById(photoId)
                .map(photo -> {
                    try {
                        photo.setImageData(ImageUtils.decompressImage(photo.getImageData()));
                    } catch (DataFormatException | IOException e) {
                        throw new RuntimeException(e);
                    }

                    return photo;
                });
    }

    public Optional<List<Photo>> downloadPhotoByUsername(String username) {
        return photoRepository.findPhotoByUsername(username)
                .map(photoList -> {
                    for (Photo photo : photoList) {
                        try {
                            photo.setImageData(ImageUtils.decompressImage(photo.getImageData()));
                        } catch (DataFormatException | IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    return photoList;
                });
    }

    public Optional<List<Photo>> downloadPhotoByDate(String date) {
        return photoRepository.findPhotoByDate(date)
                .map(photoList -> {
                    for (Photo photo : photoList) {
                        try {
                            photo.setImageData(ImageUtils.decompressImage(photo.getImageData()));
                        } catch (DataFormatException | IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    return photoList;
                });
    }

    public Photo uploadPhoto(String photoId, String username, String date, String time, String caption, MultipartFile photoFile, List<Comment> commentIds) throws IOException, DataFormatException {
        Photo photo = photoRepository.insert(new Photo(photoId,username, date, time, caption, ImageUtils.compressImage(photoFile.getBytes()), commentIds));

        mongoTemplate.update(User.class)
                .matching(Criteria.where("username").is(username))
                .apply(new Update().push("photoIds").value(photoId))
                .first();

        photo.setImageData(ImageUtils.decompressImage(photo.getImageData()));

        return photo;
    }

    public void deletePhoto(String photoId, String username) {
        mongoTemplate.update(User.class)
                .matching(Criteria.where("username").is(username))
                .apply(new Update().pull("photoIds", photoId))
                .first();

        Photo photo = photoRepository.findById(photoId).orElse(null);

        for (Comment comment : photo.getCommentIds()) {
            commentService.deleteComment(comment.getCommentId(), photoId);
        }

        photoRepository.deleteById(photoId);
    }

    public void updatePhoto(Photo photo) throws IOException {
        photo.setImageData(ImageUtils.compressImage(photo.getImageData()));

        photoRepository.save(photo);
    }
}
