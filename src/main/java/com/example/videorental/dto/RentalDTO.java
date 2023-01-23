package com.example.videorental.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class RentalDTO {

    private UUID idMedia;

    public UUID getIdMedia() {
        return idMedia;
    }

    public void setIdMedia(UUID idMedia) {
        this.idMedia = idMedia;
    }
}
