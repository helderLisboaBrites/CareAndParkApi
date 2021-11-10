package com.esiee.careandpark.parking.controller;

import java.net.URI;

import com.esiee.careandpark.parking.model.Parking;
import com.esiee.careandpark.parking.model.Place;
import com.esiee.careandpark.parking.service.ParkingServiceForClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin
@RestController
public class ParkingController {
    
    @Autowired
    private ParkingServiceForClient parkingService;

    // ---------------------------- /parkings/

    @GetMapping("/parkings")
    public @ResponseBody Iterable<Parking> obtenirParkings(){
        return parkingService.obtenirParkings();
    }

    @PostMapping("/parkings")
    public ResponseEntity<Parking> ajouterParking(@Validated(Parking.ValidationAjout.class) @RequestBody Parking parking){
        
        Parking nouveauParking = parkingService.ajouterParking(parking);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nouveauParking.getId())
                .toUri();

        return ResponseEntity.created(location).body(nouveauParking);
    }

    // ---------------------------- /parkings/id/

    @GetMapping("/parkings/{id_parking}")
    public @ResponseBody Parking obtenirParking(@PathVariable int id_parking){
        return parkingService.obtenirParking(id_parking);
    }


    // ---------------------------- /parkings/id/places


    @GetMapping("/parkings/{id_parking}/places")
    public Iterable<Place> obtenirPlaces(@PathVariable int id_parking){
        return parkingService.obtenirPlaces(id_parking);

    }

    @PostMapping("/parkings/{id_parking}/places")
    public ResponseEntity<Place> ajouterPlaces(@PathVariable int id_parking, @RequestBody @Validated(Place.ValidationAjout.class) Place place){

        Place nouvellePlace = parkingService.ajouterPlace(id_parking, place);

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().build("");

        return ResponseEntity.created(location).body(nouvellePlace);
    }

    // ---------------------------- /parkings/id/places/numero

    @GetMapping("/parkings/{id_parking}/places/{numero}")
    public @ResponseBody Place obtenirPlace(@PathVariable int id_parking, @PathVariable int numero){
        return parkingService.obtenirPlace(id_parking, numero);
    }
   
    @PutMapping("/parkings/{id_parking}/places/{numero}")
    public @ResponseBody Place reserverPlace(@PathVariable int id_parking, @PathVariable int numero, @RequestBody @Validated(Place.ValidationEtat.class) Place demande){

        return parkingService.reserverPlace(id_parking, numero, demande);
    }

    // ---------------------------- /parkings/id/compteur

    @GetMapping("/parkings/{id_parking}/compteur")
    public @ResponseBody int obtenirCompteur(@PathVariable int id_parking){
        return parkingService.obtenirCompteur(id_parking);
    }

    @PostMapping("/parkings/{id_parking}/compteur/entree")
    public @ResponseBody int  enregistrerEntree(@PathVariable int id_parking){
        return parkingService.enregistrerEntree(id_parking);
    }

    @PostMapping("/parkings/{id_parking}/compteur/sortie")
    public @ResponseBody int enregistrerSortie(@PathVariable int id_parking){
        return parkingService.enregistrerSortie(id_parking);
    }


}
