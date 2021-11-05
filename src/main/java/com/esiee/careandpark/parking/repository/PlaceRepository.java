package com.esiee.careandpark.parking.repository;

import com.esiee.careandpark.parking.model.Place;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends CrudRepository<Place,Integer> {
    
}
