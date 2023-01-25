package com.example.videorental.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_RENTALS")
public class RentalModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerModel customer;

    @ManyToOne
    @JoinColumn(name = "media_id")
    private MediaModel media;
    @Column
    private LocalDateTime rentalDate;
    @Column
    private LocalDateTime returnDate;

}
