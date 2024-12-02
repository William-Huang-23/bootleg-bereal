package com.william.bootleg_bereal.service;

import com.william.bootleg_bereal.model.Comment;
import com.william.bootleg_bereal.model.Photo;
import com.william.bootleg_bereal.model.User;
import com.william.bootleg_bereal.repository.PhotoRepository;
import com.william.bootleg_bereal.repository.UserRepository;
import com.william.bootleg_bereal.utilities.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PhotoService photoService;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User createUser(String username, String password, String name,  int age, String birthday, List<String> friendList, List<String> photoIds) {
        return userRepository.insert(new User(username, password, name, age, birthday, friendList, photoIds));
    }

    public void deleteUser(String username) {
        User user = userRepository.findById(username).orElse(null);

        for (String photoId : user.getPhotoIds()) {
            System.out.println(user.getPhotoIds());
            photoService.deletePhoto(photoId, username);
        }

        for (String friendUsername : user.getFriendList()) {
            removeFriend(user, getUser(friendUsername).orElse(null));
        }

        userRepository.deleteById(username);
    }

    public User updateUserProfile(User user) {
        return userRepository.save(user);
    }

    public List<User> addFriend(User user1, User user2) {
        List<String> list1 = new ArrayList<>(user1.getFriendList());
        list1.add(user2.getUsername());
        user1.setFriendList(list1);

        List<String> list2 = new ArrayList<>(user2.getFriendList());
        list2.add(user1.getUsername());
        user2.setFriendList(list2);

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        return userList;
    }

    public List<User> removeFriend(User user1, User user2) {
        List<String> list1 = new ArrayList<>(user1.getFriendList());
        list1.remove(user2.getUsername());
        user1.setFriendList(list1);

        List<String> list2 = new ArrayList<>(user2.getFriendList());
        list2.remove(user1.getUsername());
        user2.setFriendList(list2);

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        return userList;
    }
}
