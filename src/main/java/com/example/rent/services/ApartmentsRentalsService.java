package com.example.rent.services;

import com.example.rent.repositories.ApartmentRentalsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApartmentsRentalsService {

    @Autowired
    private ApartmentRentalsRepository apartmentRentalsRepository;

    @Transactional
    public void deleteApartmentRentals(Long id){
        var apartmentRentals = apartmentRentalsRepository.findById(id).orElse(null);
        if(apartmentRentals != null){
            apartmentRentals.setClient(null);
            apartmentRentals.setApartment(null);
            apartmentRentalsRepository.delete(apartmentRentals);
        }
    }

}
