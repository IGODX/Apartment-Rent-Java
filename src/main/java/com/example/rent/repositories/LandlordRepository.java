package com.example.rent.repositories;

import com.example.rent.models.Landlord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandlordRepository extends CrudRepository<Landlord, Long> {
}
