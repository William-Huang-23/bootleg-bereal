package com.william.bootleg_bereal.repository;

import com.william.bootleg_bereal.model.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends MongoRepository<Photo, String> {
    Optional<List<Photo>> findPhotoByUsername (String username);
    Optional<List<Photo>> findPhotoByDate (String date);
}
