package com.esiee.careandpark.parking.controller;

import com.esiee.careandpark.parking.model.Place;
import com.esiee.careandpark.parking.model.reference.EtatPlace;
import com.esiee.careandpark.parking.service.ParkingServiceForClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingController {
    
    @Autowired
    private ParkingServiceForClient parkingService;

    @GetMapping("/places")
    public Iterable<Place> getPlaces(){
        return parkingService.;
    }

    @GetMapping("/places/{id}")
    public @ResponseBody Place getPlaceById(@PathVariable int id){
        return parkingService.getPlaceById(id);
    }

    @PutMapping("/places/{id}")
    public ResponseEntity<Void> reserverPlace(@ResponseBody Place place){
        Place place1;
        if(place.getEtat().equals(EtatPlace.Occupe)){
            place1 = parkingService.stationner(place);
        }else{
            place1 = parkingService.libererPlace(place);
        }
        if(place1 == null){
            return ResponseEntity.noContent()
        }
    }

}
