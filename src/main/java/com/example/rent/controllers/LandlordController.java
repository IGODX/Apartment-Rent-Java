package com.example.rent.controllers;

import com.example.rent.models.Client;
import com.example.rent.models.Landlord;
import com.example.rent.models.User;
import com.example.rent.repositories.UserRepository;
import com.example.rent.services.LandlordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.rent.repositories.LandlordRepository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;

@Controller
public class LandlordController {
    @Autowired
    private LandlordRepository landlordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LandlordService landlordService;


    @GetMapping("/landlords")
    public String getHome(Model model){
        Iterable<Landlord> landlords = landlordRepository.findAll();
        model.addAttribute("landlords", landlords);
        return "showLandlords";
    }

    @PostMapping("/delete-landlord")
    public String deleteLandlord(@RequestParam long id, Model model) {
        landlordService.deleteLandlord(id);
        return "redirect:/landlords";
    }
    @PostMapping("/add-landlord")
    public String postAddLandlord(@ModelAttribute User user, Model model){
        if(user == null)
            return "addUser";
        userRepository.save(user);
        landlordRepository.save(new Landlord(0, user, new HashSet<>()));
        return "redirect:/landlords";
    }


    @PostMapping("/edit-landlord")
    public String postEditLandlord(@ModelAttribute User user, Model model){
        if(user == null)
            return "addUser";
        userRepository.save(user);
        return "redirect:/landlords";
    }
}
