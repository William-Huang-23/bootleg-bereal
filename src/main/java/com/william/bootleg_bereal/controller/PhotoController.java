package com.william.bootleg_bereal.controller;

import com.william.bootleg_bereal.model.User;
import com.william.bootleg_bereal.service.PhotoService;
import com.william.bootleg_bereal.model.Photo;
import com.william.bootleg_bereal.service.UserService;
import com.william.bootleg_bereal.utilities.ErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/photo-control")
//@CrossOrigin(origins = "*")
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    @Autowired
    private UserService userService;

//    @GetMapping("/downloadphoto")
//    public ResponseEntity<?> downloadPhoto(@RequestParam int requestId, @RequestParam String username, @RequestParam String date) {
//        switch (requestId) {
//            case 1: {
////                checks if input parameters are valid
//                if (ErrorUtils.stringIsEmpty(username)) {
//                    return ErrorUtils.errorFormat(3);
//                }
//
//                if (ErrorUtils.stringIsEmpty(date)) {
//                    return ErrorUtils.errorFormat(4);
//                }
//
////                inquires MongoDB using native query {username and date}
//                List<Photo> photoList = new ArrayList<>();
//
//                try {
//                    photoList.add(photoService.downloadPhotoByPhotoId(username + date).orElse(null));
//                } catch (Exception e) {
//                    return ErrorUtils.errorFormat(99);
//                }
//
////                checks if photo is successfully inquired
//                if (!photoList.isEmpty() && !photoList.contains(null)) {
//                    Map<String, Object> response = ErrorUtils.success();
//                    response.put("data", photoList);
//
//                    return new ResponseEntity<>(response, HttpStatus.OK);
//                } else {
//                    return ErrorUtils.errorFormat(1);
//                }
//            }
//            case 2: {
////                checks if input parameters are valid
//                if (ErrorUtils.stringIsEmpty(username)) {
//                    return ErrorUtils.errorFormat(3);
//                }
//
////                inquires MongoDB using username field
//                List<Photo> photoList;
//
//                try {
//                    photoList = photoService.downloadPhotoByUsername(username).orElse(null);
//                } catch (Exception e) {
//                    return ErrorUtils.errorFormat(99);
//                }
//
////                checks if photo is successfully inquired
//                if (!photoList.isEmpty()) {
//                    Map<String, Object> response = ErrorUtils.success();
//                    response.put("data", photoList);
//
//                    return new ResponseEntity<>(response, HttpStatus.OK);
//                } else {
//                    return ErrorUtils.errorFormat(1);
//                }
//            }
//            case 3: {
////                checks if input parameters are valid
//                if (ErrorUtils.stringIsEmpty(date)) {
//                    return ErrorUtils.errorFormat(4);
//                }
//
////                inquires MongoDB using date field
//                List<Photo> photoList;
//
//                try {
//                    photoList = photoService.downloadPhotoByDate(date).orElse(null);
//                } catch (Exception e) {
//                    return ErrorUtils.errorFormat(99);
//                }
//
////                checks if photo is successfully inquired
//                if (photoList.isEmpty()) {
//                    Map<String, Object> response = ErrorUtils.success();
//                    response.put("data", photoList);
//
//                    return new ResponseEntity<>(response, HttpStatus.OK);
//                } else {
//                    return ErrorUtils.errorFormat(1);
//                }
//            }
//            default: {
////                if requestId isn't supported yet
//                return ErrorUtils.errorFormat(2);
//            }
//        }
//    }

    @GetMapping("/download-all-photos")
    public ResponseEntity<?> downloadAllPhotos() {
        List<Photo> photoList;

        try {
            photoList = photoService.downloadAllPhoto();
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

        Map<String, Object> response = ErrorUtils.success();
        response.put("data", photoList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/download-photo-by-photo-id")
    public ResponseEntity<?> downloadPhotoByPhotoId(@RequestParam String username, @RequestParam String date) {
//        checks if input parameters are valid
        if (ErrorUtils.stringIsEmpty(username)) {
            return ErrorUtils.errorFormat(3);
        }

        if (ErrorUtils.stringIsEmpty(date)) {
            return ErrorUtils.errorFormat(4);
        }

//        inquires MongoDB using native query {username and date}
        Photo photo;

        try {
            photo = photoService.downloadPhotoByPhotoId(username + date).orElse(null);
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

//        checks if photo is successfully inquired
        if (photo != null) {
            Map<String, Object> response = ErrorUtils.success();
            response.put("data", photo);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return ErrorUtils.errorFormat(1);
        }
    }

    @GetMapping("/download-photo-by-username")
    public ResponseEntity<?> downloadPhotoByUsername(@RequestParam String username) {
//        checks if input parameters are valid
        if (ErrorUtils.stringIsEmpty(username)) {
            return ErrorUtils.errorFormat(3);
        }

//        inquires MongoDB using username field
        List<Photo> photoList;

        try {
            photoList = photoService.downloadPhotoByUsername(username).orElse(null);
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

//        checks if photo is successfully inquired
        if (!photoList.isEmpty()) {
            Map<String, Object> response = ErrorUtils.success();
            response.put("data", photoList);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return ErrorUtils.errorFormat(1);
        }
    }

    @GetMapping("/download-photo-by-date")
    public ResponseEntity<?> downloadPhotoByDate(@RequestParam String date) {
//        checks if input parameters are valid
        if (ErrorUtils.stringIsEmpty(date)) {
            return ErrorUtils.errorFormat(4);
        }

//        inquires MongoDB using username field
        List<Photo> photoList;

        try {
            photoList = photoService.downloadPhotoByDate(date).orElse(null);
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

//        checks if photo is successfully inquired
        if (!photoList.isEmpty()) {
            Map<String, Object> response = ErrorUtils.success();
            response.put("data", photoList);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return ErrorUtils.errorFormat(1);
        }
    }

    @PostMapping("/upload-photo")
    public ResponseEntity<?> uploadPhoto(@RequestPart("info") Map<String, Object> input, @RequestPart("image") MultipartFile photoFile) throws IOException {
//        checks if input parameters are valid
        if (ErrorUtils.stringIsEmpty(input.get("username"))) {
            return ErrorUtils.errorFormat(3);
        }

        if (ErrorUtils.stringIsEmpty(input.get("caption"))) {
            return ErrorUtils.errorFormat(20);
        }

//        checks if user exists
        try {
            if (userService.getUser(input.get("username").toString()).isEmpty()) {
                return ErrorUtils.errorFormat(6);
            }
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

//        get current date
        String date = new SimpleDateFormat("ddMMyyyy").format(new Date());

//        checks if today's photo has been uploaded yet
        try {
            if (photoService.downloadPhotoByPhotoId(input.get("username").toString() + date).isPresent()) {
                return ErrorUtils.errorFormat(5);
            }
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

//        get current time
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmssms"));

        Photo photo;

        try {
            photo = photoService.uploadPhoto(
                    input.get("username").toString() + date,    //photoId
                    input.get("username").toString(),   //username
                    date,                               //DDMMYYYY
                    time,                               //HHMMSSMS
                    input.get("caption").toString(),    //caption
                    photoFile,                          //MultipartFile but stored in bytes
                    new ArrayList<>());                 //initialize comment id list
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

        Map<String, Object> response = ErrorUtils.success();
        response.put("data", photo);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete-photo")
    public ResponseEntity<?> deletePhoto(@RequestParam String username, @RequestParam String date) {
//        checks if input parameters are valid
        if (ErrorUtils.stringIsEmpty(username)) {
            return ErrorUtils.errorFormat(3);
        }

        if (ErrorUtils.stringIsEmpty(date)) {
            return ErrorUtils.errorFormat(4);
        }

//        checks if user exists
        try {
            if (userService.getUser(username).isEmpty()) {
                return ErrorUtils.errorFormat(6);
            }
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

//        inquires MongoDB using native query {username and date}
        Photo photo;

        try {
            photo = photoService.downloadPhotoByPhotoId(username + date).orElse(null);
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

//        checks if photo is successfully inquired
        if (photo != null) {
            try {
                photoService.deletePhoto(username + date, username);
            } catch (Exception e) {
                return ErrorUtils.errorFormat(99);
            }

            Map<String, Object> response = ErrorUtils.success();
            response.put("data", photo);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return ErrorUtils.errorFormat(1);
        }
    }

    @PutMapping("/update-photo")
    public ResponseEntity<?> updatePhoto(@RequestBody Map<String, Object> input) {
//        checks if input parameters are valid
        if (ErrorUtils.stringIsEmpty(input.get("username"))) {
            return ErrorUtils.errorFormat(3);
        }

        if (ErrorUtils.stringIsEmpty(input.get("date"))) {
            return ErrorUtils.errorFormat(4);
        }

        if (ErrorUtils.stringIsEmpty(input.get("caption"))) {
            return ErrorUtils.errorFormat(20);
        }

//        checks if user exists
        try {
            if (userService.getUser(input.get("username").toString()).isEmpty()) {
                return ErrorUtils.errorFormat(6);
            }
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

        Photo photo;

        try {
            photo = photoService.downloadPhotoByPhotoId(input.get("username").toString() + input.get("date").toString()).orElse(null);
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

//        checks if photo inquired is present
        if (photo != null) {
//            fields to be updated
            photo.setCaption(input.get("caption").toString());

            try {
                photoService.updatePhoto(photo);
            } catch (Exception e) {
                return ErrorUtils.errorFormat(99);
            }

            Map<String, Object> response = ErrorUtils.success();
            response.put("data", photo);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return ErrorUtils.errorFormat(1);
        }
    }
}
