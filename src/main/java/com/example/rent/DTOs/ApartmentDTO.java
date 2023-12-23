package com.example.rent.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ApartmentDTO {
    private long id;

    private String address;

    private int numRooms;

    private double area;

    private Long landlordId;


    private boolean isPreference = false;
}
