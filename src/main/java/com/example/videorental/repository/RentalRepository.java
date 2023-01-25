package com.example.videorental.repository;

import com.example.videorental.models.RentalModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RentalRepository extends JpaRepository<RentalModel, UUID> {
    Optional<RentalModel> findRentalByMediaId(UUID mediaId);
}
