package com.example.pinterest.repository;

import com.example.pinterest.model.ImageMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageMetaRepository extends JpaRepository<ImageMeta, Long> {
}
