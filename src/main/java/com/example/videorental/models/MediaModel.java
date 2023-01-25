package com.example.videorental.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "TB_VIDEO_MEDIA")
public class MediaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String name;
    @Column(nullable = false, length = 20)
    private int releasedIn;
    @Column(nullable = false, length = 5)
    private int ageRating;
    @Column(nullable = false, length = 5)
    private int movieLength;
    @Column(nullable = false, length = 200)
    private String director;
    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerModel customer;

    public MediaModel() {}

    public MediaModel(UUID id, String name, int releasedIn, int ageRating, int movieLength, String director, CustomerModel customer) {
        this.id = id;
        this.name = name;
        this.releasedIn = releasedIn;
        this.ageRating = ageRating;
        this.movieLength = movieLength;
        this.director = director;
        this.customer = customer;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaModel that = (MediaModel) o;
        return releasedIn == that.releasedIn && ageRating == that.ageRating && movieLength == that.movieLength && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(director, that.director) && Objects.equals(customer, that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, releasedIn, ageRating, movieLength, director, customer);
    }
}
