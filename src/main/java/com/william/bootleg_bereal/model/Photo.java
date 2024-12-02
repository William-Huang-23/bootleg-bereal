package com.william.bootleg_bereal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "photos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Photo {
    @Id
    private String photoId;

    private String username;
    private String date;
    private String time;
    private String caption;
    @Lob
    @Column(name = "imagedata")
    private byte[] imageData;
    @DocumentReference
    private List<Comment> commentIds;
}
