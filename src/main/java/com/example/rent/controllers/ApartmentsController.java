package com.example.rent.controllers;

import com.example.rent.DTOs.ApartmentDTO;
import com.example.rent.models.Apartment;
import com.example.rent.models.Landlord;
import com.example.rent.repositories.ApartmentRepository;
import com.example.rent.repositories.LandlordRepository;
import com.example.rent.services.ApartmentsRentalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ApartmentsController {
    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private LandlordRepository landlordRepository;

    @Autowired
    private ApartmentsRentalsService apartmentsRentalsService;

    @GetMapping("/apartments")
    public String getApartments(Model model){
        Iterable<Apartment> apartments = apartmentRepository.findByIsPreferenceFalse();
        model.addAttribute("apartments", apartments);
        return "showApartments";
    }

    @GetMapping("/add-apartment")
    public String getAddApartment(Model model){
        Iterable<Landlord> landlords = landlordRepository.findAll();
        model.addAttribute("landlords", landlords);
        return "addApartment";
    }

    @PostMapping("/add-apartment")
    public String postAddApartment(@ModelAttribute ApartmentDTO dto, Model model){
        var landlord = landlordRepository.findById(dto.getLandlordId()).orElse(null);
        Apartment apartment = new Apartment();
        if(landlord != null)
            apartment.setLandlord(landlord);

        apartment.setAddress(dto.getAddress());

        apartment.setNumRooms(dto.getNumRooms());

        apartment.setArea(dto.getArea());

        apartment.setPreference(dto.isPreference());

        apartmentRepository.save(apartment);
        return "redirect:/apartments";
    }
    @PostMapping("/delete-apartment")
    public String deleteApartment(@RequestParam long id, Model model){
        apartmentsRentalsService.deleteApartmentRentals(id);
        return "redirect:/apartments";
    }

    @GetMapping("/edit-apartment")
    public String getEditApartment(@RequestParam long id, Model model){
        Apartment apartment = apartmentRepository.findById(id).orElse(null);
        if(apartment == null)
            return "redirect:/apartments";
        Iterable<Landlord> landlords = landlordRepository.findAll();
        model.addAttribute("landlords", landlords);
        model.addAttribute("apartment", apartment);
        return "editApartment";
    }

    @PostMapping("/edit-apartment")
    public String postEditApartment(@ModelAttribute ApartmentDTO dto, Model model){
        if(dto == null || dto.getId() == 0)
            return "redirect:/apartments";

        var landlord = landlordRepository.findById(dto.getLandlordId()).orElse(null);
        Apartment apartment = new Apartment();

        apartment.setLandlord(landlord);


        apartment.setId(dto.getId());


        apartment.setAddress(dto.getAddress());

        apartment.setNumRooms(dto.getNumRooms());

        apartment.setArea(dto.getArea());

        apartment.setPreference(dto.isPreference());


        apartmentRepository.save(apartment);
        return "redirect:/apartments";
    }
}
