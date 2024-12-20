package com.william.bootleg_bereal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String username;
    @JsonIgnore
    private String password;

    private String name;
    private int age;
    private String birthday;
    private List<String> friendList;
    private List<String> friendRequestList;
    private List<String> photoIds;
}
