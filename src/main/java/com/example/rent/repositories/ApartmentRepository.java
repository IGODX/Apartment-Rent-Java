package com.example.rent.repositories;

import com.example.rent.models.Apartment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentRepository extends CrudRepository<Apartment, Long> {
    List<Apartment> findByIsPreferenceFalse();

    List<Apartment> findByIsPreferenceFalseAndClientIsNull();

}
