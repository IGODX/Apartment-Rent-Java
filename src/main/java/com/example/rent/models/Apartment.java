package com.example.rent.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String address;

    private int numRooms;

    private double area;

    private double rentCost;

    @JoinColumn(name="landlord_id", nullable=true)
    @ManyToOne
    private Landlord landlord;

    @JoinColumn(name="client_id", nullable=true)
    @OneToOne
    private Client client;



    private boolean isPreference;

}
