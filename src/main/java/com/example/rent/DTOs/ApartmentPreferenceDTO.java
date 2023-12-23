package com.example.rent.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentPreferenceDTO {
    private long apartmentPreferenceId;

    private String address;

    private int numRooms;

    private double area;

    private double rentCost;

    private final boolean isPreference = true;
}
