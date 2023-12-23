package com.example.rent.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "apartments_rentals")
public class ApartmentsRentals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name="client_id", nullable=false)
    @OneToOne
    private Client client;

    private double rentCost;

    @JoinColumn(name="apartment_id", nullable=false)
    @OneToOne
    private Apartment apartment;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
}
