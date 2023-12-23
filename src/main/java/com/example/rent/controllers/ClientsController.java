package com.example.rent.controllers;

import com.example.rent.DTOs.ApartmentPreferenceDTO;
import com.example.rent.DTOs.SearchDTO;
import com.example.rent.models.*;
import com.example.rent.repositories.ApartmentRepository;
import com.example.rent.repositories.ClientRepository;
import com.example.rent.repositories.UserRepository;
import com.example.rent.services.ClientService;
import com.example.rent.specifications.ClientSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
public class ClientsController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private ClientService clientService;


    @GetMapping("/clients")
    public String getClients(@ModelAttribute SearchDTO search, Model model){
        Specification<Client> spec = Specification.where(null);
        if(!search.equals(null)) {
            if (!search.getSearchAddress().isEmpty())
                spec = spec.and(ClientSpecification.hasAddress(search.getSearchAddress()));
            if (!search.getSearchRooms().isEmpty())
                spec = spec.and(ClientSpecification.hasNumRooms(Integer.parseInt(search.getSearchRooms())));
            if (!search.getSearchTelephone().isEmpty())
                spec = spec.and(ClientSpecification.hasUserTelephoneNumber(search.getSearchTelephone()));
            if (!search.getSearchName().isEmpty())
                spec = spec.and(ClientSpecification.hasFullName(search.getSearchName()));
        }
        model.addAttribute("clients", clientRepository.findAll(spec));
        return "showClients";
    }

    @PostMapping("/delete-client")
    public String deleteClient(@RequestParam long id, Model model) {
        clientService.deleteClient(id);
        return "redirect:/clients";
    }

    @PostMapping("/add-client")
    public String postAddClient(@ModelAttribute User user, @ModelAttribute ApartmentPreferenceDTO dto, Model model){
        if(user == null)
            return "addUser";

        var apartment = new Apartment();

        apartment.setAddress(dto.getAddress());
        apartment.setArea(dto.getArea());
        apartment.setNumRooms(dto.getNumRooms());
        apartment.setRentCost(dto.getRentCost());
        apartment.setPreference(true);

        userRepository.save(user);

        apartment = apartmentRepository.save(apartment);
        clientRepository.save(new Client(0, user, apartment));

        return "redirect:/clients";
    }

    @PostMapping("/edit-client")
    public String postEditClient(@RequestParam long clientId,@ModelAttribute User user, @ModelAttribute ApartmentPreferenceDTO dto, Model model){
        if(user == null)
            return "addUser";

        userRepository.save(user);

        Apartment preferenceApartment = new Apartment();


        preferenceApartment.setId(dto.getApartmentPreferenceId());
        preferenceApartment.setNumRooms(dto.getNumRooms());
        preferenceApartment.setArea(dto.getArea());
        preferenceApartment.setRentCost(dto.getRentCost());
        preferenceApartment.setAddress(dto.getAddress());
        preferenceApartment.setPreference(true);

        apartmentRepository.save(preferenceApartment);

        clientRepository.save(new Client(clientId, user, preferenceApartment));

        return "redirect:/clients";
    }
}
