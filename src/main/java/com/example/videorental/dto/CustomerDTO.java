package com.example.videorental.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public class CustomerDTO {

    @NotBlank
    @Size(max = 200)
    private String name;
    @NotBlank
    @CPF
    @Size(max = 15)
    private String cpf;
    @NotBlank
    @Size(max = 12)
    private String birthDate;
    @NotBlank
    @Email
    @Size(max = 120)
    private String email;
    @NotBlank
    @Size(max = 20)
    private String phone;

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
}
