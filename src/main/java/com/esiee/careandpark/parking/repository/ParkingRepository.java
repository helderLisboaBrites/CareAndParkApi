package com.esiee.careandpark.parking.repository;

import com.esiee.careandpark.parking.model.Parking;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends CrudRepository<Parking,Integer> {
    
}
