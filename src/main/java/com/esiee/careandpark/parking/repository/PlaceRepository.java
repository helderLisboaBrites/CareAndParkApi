package com.esiee.careandpark.parking.repository;

import java.util.Optional;

import com.esiee.careandpark.parking.model.Place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place,Integer> {
    Iterable<Place> getAllByParkingId(int id_parking);
    Optional<Place> findByParkingIdAndNumero(int id_parking, int numero);
}
