package com.example.videorental.repository;

import com.example.videorental.models.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerModel, UUID> {

    public boolean existsByCpf(String cpf);
    public boolean existsByEmail(String email);
    public boolean existsByPhone(String phone);
    Optional<CustomerModel> findByCpf(String cpf);
}
