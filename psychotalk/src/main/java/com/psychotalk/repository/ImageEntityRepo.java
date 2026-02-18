package com.psychotalk.repository;

import com.psychotalk.model.mediaModels.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageEntityRepo extends JpaRepository<ImageEntity , Long> {
}
