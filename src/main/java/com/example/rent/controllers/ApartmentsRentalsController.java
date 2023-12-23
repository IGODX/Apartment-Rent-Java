package com.example.rent.controllers;

import com.example.rent.DTOs.ApartmentsRentalsDTO;
import com.example.rent.DTOs.RentalSearchDTO;
import com.example.rent.models.Apartment;
import com.example.rent.models.ApartmentsRentals;
import com.example.rent.models.Client;
import com.example.rent.repositories.ApartmentRentalsRepository;
import com.example.rent.repositories.ApartmentRepository;
import com.example.rent.repositories.ClientRepository;
import com.example.rent.specifications.ApartmentsRentalsSpecification;
import com.example.rent.specifications.ClientSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ApartmentsRentalsController {
    @Autowired
    private ApartmentRentalsRepository apartmentRentalsRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/apartments-rentals")
    public String getApartmentsRentals(@ModelAttribute RentalSearchDTO search, Model model){
        Specification<ApartmentsRentals> spec = Specification.where(null);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

        if(search.getRentStartMonth() != null) {
            String formattedDate = dateFormat.format(search.getRentStartMonth());
            spec = spec.and(ApartmentsRentalsSpecification.hasFormattedRentStartMonth(formattedDate));
        }

        if(search.getRentEndMonth() != null) {
            String formattedDate = dateFormat.format(search.getRentEndMonth());
            spec = spec.and(ApartmentsRentalsSpecification.hasFormattedEndDate(formattedDate));
        }

        if(search.isAvgRentTermLessMonth())
        spec = spec.and(ApartmentsRentalsSpecification.hasShortAverageRentTerm());

        if(search.isAvgRentTermGreaterYear())
            spec = spec.and(ApartmentsRentalsSpecification.hasLongAverageRentTerm());

        model.addAttribute("averageRentCost", apartmentRentalsRepository.findAverageRentCost());
        model.addAttribute("maxRentCost", apartmentRentalsRepository.findMaxRentCost());
        model.addAttribute("minRentCost", apartmentRentalsRepository.findMinRentCost());
        model.addAttribute("apartmentRentals", apartmentRentalsRepository.findAll(spec));
        return "showApartmentsRentals";
    }

    @GetMapping("/add-apartments-rentals")
        public String getAddApartmentsRentals(Model model){
        model.addAttribute("apartmentsRentals", new ApartmentsRentals());
        model.addAttribute("apartments", getApartments());
        model.addAttribute("clients", getClients());
        return "addApartmentsRentals";
    }
    @GetMapping("/edit-apartments-rentals")
    public String getEditApartmentsRentals(@RequestParam long id, Model model){
        var apartmentsRentals = apartmentRentalsRepository.findById(id).orElse(null);
        if(apartmentsRentals == null)
            return "redirect:/apartments-rentals";
        model.addAttribute("apartments", getApartments());
        model.addAttribute("apartmentsRentals", apartmentsRentals);
        model.addAttribute("clients", getClients());
        return "editApartmentsRentals";
    }
    @PostMapping("/add-apartments-rentals")
    public String postAddApartmentsRentals(@ModelAttribute ApartmentsRentalsDTO dto, BindingResult bindingResult, Model model) {
        boolean hasError = false;
        if(dto.getStartDate() == null && dto.getEndDate() == null){
            bindingResult.rejectValue("startDate", "error.dataError", "The date is not set!");
            hasError = true;
        }
        else if (dto.getStartDate().after(dto.getEndDate())) {
            bindingResult.rejectValue("startDate", "error.startDate", "The start date must be less than the end date");
            hasError = true;
        }
        if(hasError){
            model.addAttribute("apartments", getApartments());
            model.addAttribute("clients", getClients());
            return "addApartmentsRentals";
        }
        var client = clientRepository.findById(dto.getClientId()).orElse(null);
        var apartment = apartmentRepository.findById(dto.getApartmentId()).orElse(null);

        if(client== null || apartment == null)
            return "redirect:/add-apartments-rentals";
        if(apartment.isPreference())
            return "redirect:/add-apartments-rentals";

        ApartmentsRentals apartmentsRentals = new ApartmentsRentals();

        apartmentsRentals.setStartDate(dto.getStartDate());
        apartmentsRentals.setEndDate(dto.getEndDate());

        apartmentsRentals.setRentCost(dto.getRentCost());

        client.setApartment(apartment);
        apartment.setClient(client);

        apartmentsRentals.setApartment(apartment);
        apartmentsRentals.setClient(client);

        clientRepository.save(client);
        apartmentRepository.save(apartment);
        apartmentRentalsRepository.save(apartmentsRentals);

        return "redirect:/apartments-rentals";
    }


    @PostMapping("/delete-apartment-rentals")
    public String deleteApartmentsRentals(@RequestParam long id){
        ApartmentsRentals apartmentsRentals = apartmentRentalsRepository.findById(id).orElse(null);
        if(apartmentsRentals == null)
            return "redirect:/apartments-rentals";

        var apartment = apartmentsRentals.getApartment();
        var client = apartmentsRentals.getClient();

        if(client != null) {
            client.setApartment(null);
            clientRepository.save(client);
        }
        if(apartment !=null){
            apartment.setClient(null);
            apartmentRepository.save(apartment);
        }
        apartmentRentalsRepository.delete(apartmentsRentals);
        return "redirect:/apartments-rentals";
    }

    private Iterable<Apartment> getApartments(){
        Iterable<Apartment> apartmentsIterable = apartmentRepository.findByIsPreferenceFalseAndClientIsNull();

        return apartmentsIterable;
    }
    private Iterable<Client> getClients(){
        Iterable<Client> clientsIterable = clientRepository.findByApartmentIsNull();

        return clientsIterable;
    }
}
