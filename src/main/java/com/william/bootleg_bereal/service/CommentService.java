package com.william.bootleg_bereal.service;

import com.william.bootleg_bereal.model.Comment;
import com.william.bootleg_bereal.model.Photo;
import com.william.bootleg_bereal.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Optional<Comment> getComment(String commentId) {
        return commentRepository.findById(commentId);
    }

    public Comment postComment(String commentId, String username, String date, String time, String commentBody, String photoUsername, String photoDate) {
        Comment comment = commentRepository.insert(new Comment(commentId, username, date, time, commentBody));

        mongoTemplate.update(Photo.class)
                .matching(Criteria.where("photoId").is(photoUsername + photoDate))
                .apply(new Update().push("commentIds").value(comment))
                .first();

        return comment;
    }

    public void deleteComment(String commentId, String photoId) {
        mongoTemplate.update(Photo.class)
                .matching(Criteria.where("photoId").is(photoId))
                        .apply(new Update().pull("commentIds", commentId))
                        .first();

        commentRepository.deleteById(commentId);
    }
}
