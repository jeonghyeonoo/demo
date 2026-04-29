package com.memoire.album.repository;

import com.memoire.album.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, String> {
    List<Photo> findAllByOrderByDateDesc();
}