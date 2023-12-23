package com.example.rent.controllers;

import com.example.rent.models.Apartment;
import com.example.rent.models.Client;
import com.example.rent.models.User;
import com.example.rent.repositories.ClientRepository;
import com.example.rent.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;


    @GetMapping("/add-user")
    public String getAddUser(@RequestParam boolean type, Model model){
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("type", type);
        return "addUser";
    }



    @GetMapping("/edit-user")
    public String getEditUser(@RequestParam long id , @RequestParam(required = false) long clientId, Model model){
        User user = userRepository.findById(id).orElse(null);
        model.addAttribute("user", user);

        if(clientId != 0) {
            var apartmentPreference = clientRepository.findApartmentPreferenceByClientId(clientId);
            if(apartmentPreference == null)
                apartmentPreference = new Apartment();
            model.addAttribute("apartmentPreference", apartmentPreference);
            model.addAttribute("clientId", clientId);
        }
        return "editUser";
    }

}
