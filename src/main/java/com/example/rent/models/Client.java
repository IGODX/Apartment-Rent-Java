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
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name="user_id", nullable=false)
    @OneToOne(cascade = CascadeType.REMOVE)
    private User user;

    @JoinColumn(name="apartment_id", nullable=true)
    @OneToOne
    private Apartment apartment;

    @JoinColumn(name="apartment_preference_id", nullable=true)
    @OneToOne
    private Apartment apartmentPreference;


    public Client(long id, User user){
        this.id = id;
        this.user = user;
    }

    public Client(long id, User user, Apartment apartmentPreference){
        this.id = id;
        this.user = user;
        this.apartmentPreference = apartmentPreference;
    }

}
