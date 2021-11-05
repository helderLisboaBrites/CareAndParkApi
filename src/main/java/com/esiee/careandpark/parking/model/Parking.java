package com.esiee.careandpark.parking.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import javax.persistence.Entity;

import com.esiee.careandpark.parking.model.exceptions.PlaceNotFoundException;
import com.esiee.careandpark.parking.model.reference.EtatPlace;
import com.esiee.careandpark.parking.model.reference.TypePlace;

@Entity
public class Parking {
	
	private String nom;
	private List<Place> places;
	private String adresse;
	private EnumMap<TypePlace, Integer> compteurs;
	

	/**
	 * Initie un parking avec le nombres de place par type d�finis par les param�tres.
	 * Toutes les places se voient affect�e un num�ro unique et tous les num�ros se suivent	 *  
	 * @param nbPlaceNominale
	 * @param nbPlaceHandicape
	 * @param nbPlacebus
	 * @param nbPlace2roues
	 */
	public Parking(int nbPlacesNominales,int nbPlacesHandicapes,int nbPlacesBus,int nbPlacesDeuxRoues, String adresse) {
		if (nbPlacesNominales<0 || nbPlacesHandicapes<0 || nbPlacesBus<0 || nbPlacesDeuxRoues<0) {
			throw new InstantiationError("interdit de mettre des nombres negatifs");
		}
		if (adresse==null) {
			throw new InstantiationError("interdit adresse null");
		}		
			
		this.places = new ArrayList<Place>();
				
		List<Place> placesNominale = createListePlaceForType(nbPlacesNominales, TypePlace.NOMINALE, 1);
		places.addAll(placesNominale);
		
		List<Place> placesHandicape = createListePlaceForType(nbPlacesHandicapes, TypePlace.HANDICAPE,places.size()+1);
		places.addAll(placesHandicape);
		
		List<Place> placesBus = createListePlaceForType(nbPlacesBus, TypePlace.BUS, places.size()+1);
		places.addAll(placesBus);
		
		List<Place> places2roues = createListePlaceForType(nbPlacesDeuxRoues, TypePlace.DEUX_ROUES, places.size()+1);
		places.addAll(places2roues);


		compteurs = new EnumMap<>(TypePlace.class);
		compteurs.put(TypePlace.NOMINALE, 0);
		compteurs.put(TypePlace.HANDICAPE, 0);
		compteurs.put(TypePlace.BUS, 0);
		compteurs.put(TypePlace.DEUX_ROUES, 0);
	}
	
	public Parking() {
		
	}
	
	private List<Place> createListePlaceForType(int nombre,TypePlace typePlace,int numeroDepart){
		if (nombre<0) {
			throw new InstantiationError("le nombre de place pour le type "+typePlace+" doit etre >= 0");
		}
		List<Place> places = new ArrayList<Place>();
		for (int i = 0; i < nombre; i++) {
			Place place = new Place(typePlace, i+numeroDepart);
			places.add(place);
		}
		
		return places;
	}
	
	/**
	 * renvoie toutes les places libre qui correspondent au type de place recherch�
	 * @param type
	 * @return
	 */
	public List<Place> searchPlaceLibre(TypePlace type){
		
		if(type == null) throw new InstantiationError("interdit adresse null");
		
		List<Place> listePlacesLibres = new ArrayList<Place>();
		
		for(Place place : this.places) {
			if(place.getType().equals(type) && place.getEtat() != EtatPlace.Occupe) {
				listePlacesLibres.add(place);
			}
		}
		return listePlacesLibres;
	}

	/**
	 * renvoie la place de numéro numero
	 * @param numero
	 * @return
	 * @throws PlaceNotFoundException
	 */
	public Place getPlace(int numero) throws PlaceNotFoundException {
		// places numero négatif ?
		
		for(Place place : places) {
			if(place.getNumero() == numero) {
				return place;
			}
		}
		
		throw new PlaceNotFoundException(numero);
	}
	
	/**
	 * le statut de la place de numéro numero passe à occupe
	 * @param numero
	 * @throws PlaceNotFoundException si la place de numéro numero n'existe pas
	 */
	public void occuperPlace(int numero) throws PlaceNotFoundException{
		Place place = getPlace(numero);
		
		place.setEtat(EtatPlace.Occupe);
	}
	
	/**
	 * le statut de la place de numéro numero passe à occupe
	 * @param numero
	 */
	public void libererPlace(int numero) throws PlaceNotFoundException{
		Place place = getPlace(numero);
		
		place.setEtat(EtatPlace.Libre);
	}
	
	public String getAdresse() {
		return adresse;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	protected List<Place> getPlaces() {
		return places;
	}

	public int getNombreVehicules(TypePlace type) {
		if(type == null) {
			throw new IllegalArgumentException("le type de place ne doit pas être null");
		}
		return compteurs.get(type);
	}

	public void enregistrerEntreeVehicule(TypePlace type) {
		if(type == null) {
			throw new IllegalArgumentException("le type de place ne doit pas être null");
		}
		compteurs.put(type, compteurs.get(type) + 1);
	}

	public void enregistrerSortieVehicule(TypePlace type) {
		if(type == null) {
			throw new IllegalArgumentException("le type de place ne doit pas être null");
		}
		
		int nb = compteurs.get(type);
		
		if(nb == 0) {
			throw new IllegalStateException("le nombre de "+type+" est déjà de 0");
		}
		
		compteurs.put(type, nb - 1);
	}


}
