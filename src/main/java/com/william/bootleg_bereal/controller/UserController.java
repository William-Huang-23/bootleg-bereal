package com.william.bootleg_bereal.controller;

import com.william.bootleg_bereal.model.User;
import com.william.bootleg_bereal.service.UserService;
import com.william.bootleg_bereal.utilities.ErrorUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user-control")
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> userList;

        try {
            userList = userService.getAllUser();
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

        Map<String, Object> response = ErrorUtils.success();
        response.put("data", userList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-user")
    public ResponseEntity<?> getUser(@RequestParam String username) {
//        checks if input parameters are valid
        if (ErrorUtils.stringIsEmpty(username)) {
            return ErrorUtils.errorFormat(3);
        }

        User user;

        try {
            user = userService.getUser(username).orElse(null);
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

        if (user != null) {
            Map<String, Object> response = ErrorUtils.success();
            response.put("data", user);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return ErrorUtils.errorFormat(6);
        }
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody Map<String, Object> input) {
//        checks if input parameters are valid
        if (ErrorUtils.stringIsEmpty(input.get("username"))) {
            return ErrorUtils.errorFormat(3);
        }

        if (ErrorUtils.stringIsEmpty(input.get("password"))) {
            return ErrorUtils.errorFormat(14);
        }

        if (ErrorUtils.stringIsEmpty(input.get("name"))) {
            return ErrorUtils.errorFormat(15);
        }

        if (ErrorUtils.stringIsEmpty(input.get("age"))) {
            return ErrorUtils.errorFormat(16);
        }

        if (ErrorUtils.stringIsEmpty(input.get("birthday"))) {
            return ErrorUtils.errorFormat(17);
        }

//        checks if user already exists
        try {
            if (userService.getUser(input.get("username").toString()).isPresent()) {
                return ErrorUtils.errorFormat(7);
            }
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

        User user;

        try {
            user = userService.createUser(
                    input.get("username").toString(),               //username
                    passwordEncoder.encode(input.get("password").toString()),                                 //password (encoded)
                    input.get("name").toString(),                   //name
                    Integer.parseInt(input.get("age").toString()),  //age (int)
                    input.get("birthday").toString(),               //DDMMYYYY
                    new ArrayList<>(),                              //initialize friend list
                    new ArrayList<>());                             //initialize photo id list
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

        Map<String, Object> response = ErrorUtils.success();
        response.put("data", user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam String username) {
//        checks if input parameters are valid
        if (ErrorUtils.stringIsEmpty(username)) {
            return ErrorUtils.errorFormat(3);
        }

        User user;

        try {
            user = userService.getUser(username).orElse(null);
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

        if (user != null) {
            try {
                userService.deleteUser(username);
            } catch (Exception e) {
                return ErrorUtils.errorFormat(99);
            }

            Map<String, Object> response = ErrorUtils.success();
            response.put("data", user);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return ErrorUtils.errorFormat(6);
        }
    }

    @PutMapping("/user-login")
    public ResponseEntity<?> userLogin(@RequestBody Map<String, Object> input) {
        User user = userService.getUser(input.get("username").toString()).orElse(null);

        if (user != null) {
            if (passwordEncoder.matches(input.get("password").toString(), user.getPassword())) {
                return new ResponseEntity<>(ErrorUtils.success(), HttpStatus.OK);
            } else {
                return ErrorUtils.errorFormat(24);
            }
        } else {
            return ErrorUtils.errorFormat(24);
        }
    }

    @PutMapping("/update-user-profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody Map<String, Object> input) {
        if (ErrorUtils.stringIsEmpty(input.get("username"))) {
            return ErrorUtils.errorFormat(3);
        }

        if (ErrorUtils.stringIsEmpty(input.get("name"))) {
            return ErrorUtils.errorFormat(15);
        }

        if (ErrorUtils.stringIsEmpty(input.get("age"))) {
            return ErrorUtils.errorFormat(16);
        }

        if (ErrorUtils.stringIsEmpty(input.get("birthday"))) {
            return ErrorUtils.errorFormat(17);
        }

        User user;

        try {
            user = userService.getUser(input.get("username").toString()).orElse(null);
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

        if (user != null) {
            user.setName(input.get("name").toString());
            user.setAge(Integer.parseInt(input.get("age").toString()));
            user.setBirthday(input.get("birthday").toString());

            try {
                user = userService.updateUserProfile(user);
            } catch (Exception e) {
                return ErrorUtils.errorFormat(99);
            }

            Map<String, Object> response = ErrorUtils.success();
            response.put("data", user);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return ErrorUtils.errorFormat(6);
        }
    }

//    @PutMapping("/updatefriendlist")
//    public ResponseEntity<?> updateFriendList(@RequestBody Map<String, Object> input) {
////        checks if input parameters are valid
//        if (ErrorUtils.stringIsEmpty(input.get("requestId"))) {
//            return ErrorUtils.errorFormat(2);
//        }
//
//        if (ErrorUtils.stringIsEmpty(input.get("username"))) {
//            return ErrorUtils.errorFormat(3);
//        }
//
//        if (ErrorUtils.stringIsEmpty(input.get("userToBeAdded"))) {
//            return ErrorUtils.errorFormat(3);
//        }
//
////        checks if username and user to be added is the same
//        if (Objects.equals(input.get("username").toString(), input.get("userToBeAdded").toString())) {
//            return ErrorUtils.errorFormat(10);
//        }
//
//        User user;
//        User userToBeAdded;
//
//        try {
//            user = userService.getUser(input.get("username").toString()).orElse(null);
//            userToBeAdded = userService.getUser(input.get("userToBeAdded").toString()).orElse(null);
//        } catch (Exception e) {
//            return ErrorUtils.errorFormat(99);
//        }
//
//        if (user != null && userToBeAdded != null) {
//            switch ((Integer) input.get("requestId")) {
//                case 1: {
//                    if (user.getFriendList().contains(input.get("userToBeAdded").toString())) {
//                        return ErrorUtils.errorFormat(11);
//                    }
//
//                    List<User> userList;
//
//                    try {
//                        userList = userService.addFriend(user, userToBeAdded);
//                    } catch (Exception e) {
//                        return ErrorUtils.errorFormat(99);
//                    }
//
//                    Map<String, Object> response = ErrorUtils.success();
//                    response.put("data", userList);
//
//                    return new ResponseEntity<>(response, HttpStatus.OK);
//                }
//                case 2: {
//                    if (!user.getFriendList().contains(input.get("userToBeAdded").toString())) {
//                        return ErrorUtils.errorFormat(12);
//                    }
//
//                    List<User> userList;
//
//                    try {
//                        userList = userService.removeFriend(user, userToBeAdded);
//                    } catch (Exception e) {
//                        return ErrorUtils.errorFormat(99);
//                    }
//
//                    Map<String, Object> response = ErrorUtils.success();
//                    response.put("data", userList);
//
//                    return new ResponseEntity<>(response, HttpStatus.OK);
//                }
//                default: {
//                    return ErrorUtils.errorFormat(2);
//                }
//            }
//        } else {
//            return ErrorUtils.errorFormat(6);
//        }
//    }

    @PutMapping("/add-friend")
    public ResponseEntity<?> addFriend(@RequestBody Map<String, Object> input) {
//        checks if input parameters are valid
        if (ErrorUtils.stringIsEmpty(input.get("username"))) {
            return ErrorUtils.errorFormat(3);
        }

        if (ErrorUtils.stringIsEmpty(input.get("targetUsername"))) {
            return ErrorUtils.errorFormat(18);
        }

//        checks if username and user to be added is the same
        if (Objects.equals(input.get("username").toString(), input.get("targetUsername").toString())) {
            return ErrorUtils.errorFormat(10);
        }

        User user;
        User targetUser;

        try {
            user = userService.getUser(input.get("username").toString()).orElse(null);
            targetUser = userService.getUser(input.get("targetUsername").toString()).orElse(null);
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

        if (user != null && targetUser != null) {
            if (user.getFriendList().contains(input.get("targetUsername").toString())) {
                return ErrorUtils.errorFormat(11);
            }

            List<User> userList;

            try {
                userList = userService.addFriend(user, targetUser);
            } catch (Exception e) {
                return ErrorUtils.errorFormat(99);
            }

            Map<String, Object> response = ErrorUtils.success();
            response.put("data", userList);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            if (user == null) {
                return ErrorUtils.errorFormat(6);
            } else {
                return ErrorUtils.errorFormat(19);
            }
        }
    }

    @PutMapping("/remove-friend")
    public ResponseEntity<?> removeFriend(@RequestBody Map<String, Object> input) {
//        checks if input parameters are valid
        if (ErrorUtils.stringIsEmpty(input.get("username"))) {
            return ErrorUtils.errorFormat(3);
        }

        if (ErrorUtils.stringIsEmpty(input.get("targetUsername"))) {
            return ErrorUtils.errorFormat(18);
        }

//        checks if username and user to be added is the same
        if (Objects.equals(input.get("username").toString(), input.get("targetUsername").toString())) {
            return ErrorUtils.errorFormat(10);
        }

        User user;
        User targetUser;

        try {
            user = userService.getUser(input.get("username").toString()).orElse(null);
            targetUser = userService.getUser(input.get("targetUsername").toString()).orElse(null);
        } catch (Exception e) {
            return ErrorUtils.errorFormat(99);
        }

        if (user != null && targetUser != null) {
            if (!user.getFriendList().contains(input.get("targetUsername").toString())) {
                return ErrorUtils.errorFormat(12);
            }

            List<User> userList;

            try {
                userList = userService.removeFriend(user, targetUser);
            } catch (Exception e) {
                return ErrorUtils.errorFormat(99);
            }

            Map<String, Object> response = ErrorUtils.success();
            response.put("data", userList);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            if (user == null) {
                return ErrorUtils.errorFormat(6);
            } else {
                return ErrorUtils.errorFormat(19);
            }
        }
    }
}
