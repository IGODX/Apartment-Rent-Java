package com.example.rent.repositories;

import com.example.rent.models.ApartmentsRentals;
import com.example.rent.models.Client;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentRentalsRepository extends CrudRepository<ApartmentsRentals, Long> {
    List<ApartmentsRentals> findAll(Specification<ApartmentsRentals> spec);

    @Query("SELECT AVG(e.rentCost) FROM ApartmentsRentals e")
    Double findAverageRentCost();

    @Query("SELECT MAX(e.rentCost) FROM ApartmentsRentals e")
    Double findMaxRentCost();

    @Query("SELECT MIN(e.rentCost) FROM ApartmentsRentals e")
    Double findMinRentCost();

    ApartmentsRentals findByClient_Id(Long clientId);
}
