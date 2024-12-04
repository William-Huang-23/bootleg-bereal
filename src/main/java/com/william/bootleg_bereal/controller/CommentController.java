package com.william.bootleg_bereal.controller;

import com.william.bootleg_bereal.model.Comment;
import com.william.bootleg_bereal.model.Photo;
import com.william.bootleg_bereal.service.CommentService;
import com.william.bootleg_bereal.service.PhotoService;
import com.william.bootleg_bereal.service.UserService;
import com.william.bootleg_bereal.utilities.ErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/commentcontrol")
//@CrossOrigin(origins = "*")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private UserService userService;

    @PostMapping("/postcomment")
    public ResponseEntity<?> postComment(@RequestBody Map<String, Object> input) {
//        checks if input parameters are valid
        if (ErrorUtils.stringIsEmpty(input.get("photoUsername"))) {
            return ErrorUtils.errorFormat(21);
        }

        if (ErrorUtils.stringIsEmpty(input.get("photoDate"))) {
            return ErrorUtils.errorFormat(4);
        }

        if (ErrorUtils.stringIsEmpty(input.get("commentUsername"))) {
            return ErrorUtils.errorFormat(22);
        }

        if (ErrorUtils.stringIsEmpty(input.get("commentBody"))) {
            return ErrorUtils.errorFormat(23);
        }

//        checks if commenter exists
        try {
            if (userService.getUser(input.get("commentUsername").toString()).isEmpty()) {
                return ErrorUtils.errorFormat(6);
            }
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

//        checks if photo exists
        try {
            if (photoService.downloadPhotoByPhotoId(input.get("photoUsername").toString() + input.get("photoDate").toString()).isEmpty()) {
                return ErrorUtils.errorFormat(1);
            }
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

//        get current date and time
        String date = new SimpleDateFormat("ddMMyyyy").format(new Date());
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmssms"));

        Comment comment;

        try {
            comment = commentService.postComment(
                    input.get("photoUsername").toString() + input.get("photoDate").toString() + input.get("commentUsername").toString() + date + time,
                    input.get("photoUsername").toString() + input.get("photoDate").toString(),
                    input.get("commentUsername").toString(),    //commentor's username
                    date,                                       //DDMMYYYY
                    time,                                       //HHMMSSMS
                    input.get("commentBody").toString(),        //commentBody
                    input.get("photoUsername").toString(),      //username of the photo's owner
                    input.get("photoDate").toString());         //date of the photo itself DDMMYYYY
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

        Map<String, Object> response = ErrorUtils.success();
        response.put("data", comment);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deletecomment")
    public ResponseEntity<?> deleteComment(@RequestParam String commentId) {
//        checks if input parameters are valid
        if (ErrorUtils.stringIsEmpty(commentId)) {
            return ErrorUtils.errorFormat(9);
        }

        Comment comment;

        try {
            comment = commentService.getComment(commentId).orElse(null);
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

        Photo photo;

//        check if comment exists
        if (comment != null) {
            try {
                photo = photoService.downloadPhotoByPhotoId(comment.getPhotoId()).orElse(null);
            } catch (Exception e) {
                return ErrorUtils.errorFormat(99);
            }
        } else {
            return ErrorUtils.errorFormat(8);
        }

//        checks if the photo exists
        if (photo != null) {
            if (!photo.getCommentIds().contains(comment)) {
                return ErrorUtils.errorFormat(13);
            }
        } else {
            return ErrorUtils.errorFormat(1);
        }

        try {
            commentService.deleteComment(commentId, comment.getPhotoId());
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

        Map<String, Object> response = ErrorUtils.success();
        response.put("data", comment);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
