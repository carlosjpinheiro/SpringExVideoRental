package com.example.videorental.services;

import com.example.videorental.models.MediaModel;
import com.example.videorental.repository.MediaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MediaServices {

    final MediaRepository mediaRepository;

    public MediaServices(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    @Transactional
    public MediaModel saveMedia(MediaModel mediaModel) {
        return mediaRepository.save(mediaModel);
    }

    public List<MediaModel> findAllMedias() {
        return mediaRepository.findAll();
    }

    public Optional<MediaModel> findMediaById(UUID id) {
        return mediaRepository.findById(id);
    }

    public List<MediaModel> findMediasByCustomerId(UUID id){
        return mediaRepository.findMediasByCustomerId(id);
    }

    @Transactional
    public void deleteMedia(MediaModel mediaModel) {
        mediaRepository.delete(mediaModel);
    }
}
