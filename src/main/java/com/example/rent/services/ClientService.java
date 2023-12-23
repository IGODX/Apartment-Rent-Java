package com.example.rent.services;

import com.example.rent.models.Client;
import com.example.rent.repositories.ApartmentRentalsRepository;
import com.example.rent.repositories.ApartmentRepository;
import com.example.rent.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private ApartmentRentalsRepository apartmentRentalsRepository;

    @Transactional
    public void deleteClient(Long clientId) {
        Client client = clientRepository.findById(clientId).orElse(null);

        if (client != null) {

            var apartment = client.getApartment();
            if(apartment != null) {
                apartment.setClient(null);
               apartmentRepository.save(apartment);
            }

            var apartmentPreference = client.getApartmentPreference();
            if(apartmentPreference != null)
                apartmentRepository.delete(apartmentPreference);

            var apartmentRentals = apartmentRentalsRepository.findByClient_Id(clientId);
            if(apartmentRentals != null) {
                apartmentRentals.setClient(null);
                apartmentRentals.setApartment(null);
                apartmentRentalsRepository.delete(apartmentRentals);
            }

            clientRepository.delete(client);
        }
    }
}
