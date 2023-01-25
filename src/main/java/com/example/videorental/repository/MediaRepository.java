package com.example.videorental.repository;

import com.example.videorental.models.MediaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MediaRepository extends JpaRepository<MediaModel, UUID> {

    List<MediaModel> findMediasByCustomerId(UUID id);

}
