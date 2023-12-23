package com.example.rent.services;

import com.example.rent.repositories.LandlordRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LandlordService {

    @Autowired
    private LandlordRepository landlordRepository;

    @Transactional
    public void deleteLandlord(Long landlordId){
        var landlord = landlordRepository.findById(landlordId).orElse(null);
        if(landlord != null){
            landlord.getApartments().forEach(apartment -> {
                apartment.setLandlord(null);
            });
            landlordRepository.delete(landlord);
        }
    }
}
