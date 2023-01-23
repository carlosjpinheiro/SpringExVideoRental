package com.example.videorental.repository;

import com.example.videorental.models.VideoMediaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VideoMediaRepository extends JpaRepository<VideoMediaModel, UUID> {

    List<VideoMediaModel> findMediasByCustomerId(UUID id);

}
