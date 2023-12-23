package com.example.rent.repositories;

import com.example.rent.models.Apartment;
import com.example.rent.models.Client;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    List<Client> findAll(Specification<Client> spec);
    List<Client> findByApartmentIsNull();

    @Query("SELECT c.apartmentPreference FROM Client c WHERE c.id = :clientId")
    Apartment findApartmentPreferenceByClientId(@Param("clientId") long clientId);

}
