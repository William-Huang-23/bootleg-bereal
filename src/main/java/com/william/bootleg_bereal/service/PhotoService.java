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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {
    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommentService commentService;

    public List<Photo> downloadAllPhoto() {
        return photoRepository.findAll();
    }

    public Optional<Photo> downloadPhotoByPhotoId(String photoId) {
        return photoRepository.findById(photoId);
    }

    public Optional<List<Photo>> downloadPhotoByUsername(String username) {
        return photoRepository.findPhotoByUsername(username);
    }

    public Optional<List<Photo>> downloadPhotoByDate(String date) {
        return photoRepository.findPhotoByDate(date);
    }

    public Photo uploadPhoto(String photoId, String username, String date, String time, String caption, MultipartFile photoFile, List<Comment> commentIds) throws IOException {
        Photo photo = photoRepository.insert(new Photo(photoId,username, date, time, caption, ImageUtils.compressImage(photoFile.getBytes()), commentIds));

        mongoTemplate.update(User.class)
                .matching(Criteria.where("username").is(username))
                .apply(new Update().push("photoIds").value(photoId))
                .first();

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

    public void updatePhoto(Photo photo) {
        photoRepository.save(photo);
    }
}
