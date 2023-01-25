package com.example.videorental.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MediaDTO {

    @NotBlank
    @Size(max = 200)
    private String name;
    @NotNull
    private int releasedIn;
    @NotNull
    private int ageRating;
    @NotNull
    private int movieLength;
    @NotBlank
    @Size(max = 200)
    private String director;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleasedIn() {
        return releasedIn;
    }

    public void setReleasedIn(int releasedIn) {
        this.releasedIn = releasedIn;
    }

    public int getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(int ageRating) {
        this.ageRating = ageRating;
    }

    public int getMovieLength() {
        return movieLength;
    }

    public void setMovieLength(int movieLength) {
        this.movieLength = movieLength;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
