package com.example.rent.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String firstname;
    private String surname;
    private String patronymic;

    @Transient
    private String fullName;

    public String getFullName() {
        return firstname + " " + surname + " " + patronymic;
    }

    @Column(unique = true)
    private String telephoneNumber;

    @Override
    public String toString(){
        return firstname + " " + surname + " " + patronymic;
    }

}
