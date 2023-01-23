package com.example.videorental.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "TB_CUSTOMER")
public class CustomerModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String name;
    @Column(nullable = false, unique = true, length = 15)
    private String cpf;
    @Column(nullable = false, length = 12)
    private String birthDate;
    @Column(nullable = false, unique = true, length = 120)
    private String email;
    @Column(nullable = false, unique = true, length = 20)
    private String phone;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @JsonIgnore
    @Column
    @OneToMany(mappedBy = "customer")
    private List<VideoMediaModel> medias = new ArrayList<>();

    public CustomerModel() {}

    public CustomerModel(UUID id, String name, String cpf, String birthDate, String email, String phone) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<VideoMediaModel> getMedias() {
        return medias;
    }

    public void setMedias(VideoMediaModel media) {
        medias.add(media);
    }

    public void deleteMedia(VideoMediaModel media){
        medias.remove(media);
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
        CustomerModel customer = (CustomerModel) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(cpf, customer.cpf) && Objects.equals(birthDate, customer.birthDate) && Objects.equals(email, customer.email) && Objects.equals(phone, customer.phone) && Objects.equals(medias, customer.medias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cpf, birthDate, email, phone, medias);
    }
}
