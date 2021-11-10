package com.esiee.careandpark.parking.service;

import java.util.Optional;

import com.esiee.careandpark.parking.model.Parking;
import com.esiee.careandpark.parking.model.Place;
import com.esiee.careandpark.parking.model.exceptions.ParkingNotFoundException;
import com.esiee.careandpark.parking.model.exceptions.ParkingVideException;
import com.esiee.careandpark.parking.model.exceptions.PlaceDejaOccupeeException;
import com.esiee.careandpark.parking.model.exceptions.PlaceExisteDejaException;
import com.esiee.careandpark.parking.model.exceptions.PlaceNotFoundException;
import com.esiee.careandpark.parking.model.reference.EtatPlace;
import com.esiee.careandpark.parking.repository.ParkingRepository;
import com.esiee.careandpark.parking.repository.PlaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ParkingServiceForClient {
	
	@Autowired
	ParkingRepository parkingRepo;

	@Autowired
	PlaceRepository placeRepo;	
	
	public ParkingServiceForClient() {
		
	}
	
	
	// /**
	//  * rercherche une place dans un parking pour un type de place donné
	//  * @param parkingName
	//  * @param typePlace
	//  */
	// public void recherchePlace(String parkingName,TypePlace typePlace) {
		
	// }

	// private List<Place> createListePlaceForType(int nombre,TypePlace typePlace,int numeroDepart){
	// 	if (nombre<0) {
	// 		throw new InstantiationError("le nombre de place pour le type "+typePlace+" doit etre >= 0");
	// 	}
	// 	List<Place> places = new ArrayList<Place>();
	// 	for (int i = 0; i < nombre; i++) {
	// 		Place place = new Place(typePlace, i+numeroDepart);
	// 		places.add(place);
	// 	}
		
	// 	return places;
	// }
	
	// /**
	//  * renvoie toutes les places libre qui correspondent au type de place recherch�
	//  * @param type
	//  * @return
	//  */
	// public List<Place> searchPlaceLibre(TypePlace type){
		
	// 	if(type == null) throw new InstantiationError("interdit adresse null");
		
	// 	List<Place> listePlacesLibres = new ArrayList<Place>();
		
	// 	for(Place place : this.places) {
	// 		if(place.getType().equals(type) && place.getEtat() != EtatPlace.Occupe) {
	// 			listePlacesLibres.add(place);
	// 		}
	// 	}
	// 	return listePlacesLibres;
	// }


    public Iterable<Parking> obtenirParkings() {
        return parkingRepo.findAll();
    }

    public Parking ajouterParking(Parking parking) {

		if(parking == null) {
			throw new IllegalArgumentException("Le parking à créer ne peut pas être null");
		}
		
		if(parking.getNom()== null || parking.getNom().isEmpty()) {
			throw new IllegalArgumentException("Le parking à créer doit avoir un nom");
		}

		if(parking.getAdresse()== null || parking.getAdresse().isEmpty()) {
			throw new IllegalArgumentException("Le parking à créer doit avoir une adresse");
		}

		Parking nouevau = parkingRepo.saveAndFlush(parking);

        return nouevau;
    }

    public Parking obtenirParking(int id_parking) {

		Optional<Parking> parking = parkingRepo.findById(id_parking);

		if(!parking.isPresent()) {
			throw new ParkingNotFoundException(id_parking);
		}

		return parking.get();
    }

	public Iterable<Place> obtenirPlaces(int id_parking) {

        if(!parkingRepo.existsById(id_parking)) {
            throw new ParkingNotFoundException(id_parking);
        }

		Iterable<Place> places = placeRepo.getAllByParkingId(id_parking);
		
		return places;
	}

	public Place ajouterPlace(int id_parking, Place place) {

		if(place == null) {
			throw new IllegalArgumentException("La place à créer ne peut pas être null");
		}
		
		if(place.getNumero() == null) {
			throw new IllegalArgumentException("La place à créer doit avoir un numéro");
		}
		
		if(place.getType() == null) {
			throw new IllegalArgumentException("La place à créer doit avoir un type");
		}

        if(!parkingRepo.existsById(id_parking)) {
            throw new ParkingNotFoundException(id_parking);
        }

		place.setParkingId(id_parking);
		place.setEtat(EtatPlace.LIBRE);

		if(placeRepo.existsByNumeroAndParkingId(place.getNumero(), id_parking)) {
			throw new PlaceExisteDejaException(place.getNumero(), id_parking);
		}

		return placeRepo.saveAndFlush(place);
	}

    public Place obtenirPlace(int id_parking, int numero) {

		Optional<Place> place = placeRepo.findByParkingIdAndNumero(id_parking, numero);

		if(!place.isPresent()) {
			throw new PlaceNotFoundException(numero);
		}

		return place.get();
    }

	public Place reserverPlace(int id_parking, int numero, Place demande) {

		if(demande == null) {
			throw new IllegalArgumentException("La place à réserver ne peut pas être null");
		}
		
		if(demande.getEtat() == null) {
			throw new IllegalArgumentException("La place à réserver doit avoir un nouvel état");
		}

		Place place = obtenirPlace(id_parking, numero);

		// ignore all fields except etat
		if(demande.getEtat() == null) {
			
		}
		
		if(place.getEtat() == EtatPlace.OCCUPE && demande.getEtat() == EtatPlace.OCCUPE) {
			throw new PlaceDejaOccupeeException(numero);
		}

		place.setEtat(demande.getEtat()); 
		placeRepo.saveAndFlush(place);

		return place;
	}


	public int obtenirCompteur(int id_parking) {

		return obtenirParking(id_parking).getCompteur();
	}

	public int enregistrerEntree(int id_parking) {
		
		Parking parking = obtenirParking(id_parking);
		parking.incrementerCompteur();
		parkingRepo.saveAndFlush(parking);
		return parking.getCompteur();
	}

	public int enregistrerSortie(int id_parking) {
		
		Parking parking = obtenirParking(id_parking);

		try {
			parking.decrementerCompteur();
		} catch (IllegalStateException e) {
			throw new ParkingVideException(id_parking);
		}

		parkingRepo.saveAndFlush(parking);
		return parking.getCompteur();
	}











}
