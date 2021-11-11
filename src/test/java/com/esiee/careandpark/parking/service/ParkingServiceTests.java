package com.esiee.careandpark.parking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import com.esiee.careandpark.parking.model.Parking;
import com.esiee.careandpark.parking.model.Place;
import com.esiee.careandpark.parking.model.exceptions.ParkingNotFoundException;
import com.esiee.careandpark.parking.model.exceptions.ParkingVideException;
import com.esiee.careandpark.parking.model.exceptions.PlaceDejaOccupeeException;
import com.esiee.careandpark.parking.model.exceptions.PlaceExisteDejaException;
import com.esiee.careandpark.parking.model.exceptions.PlaceNotFoundException;
import com.esiee.careandpark.parking.model.reference.EtatPlace;
import com.esiee.careandpark.parking.model.reference.TypePlace;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ParkingServiceTests {

	@Autowired
	private ParkingServiceForClient parkingService;

	@Test
	void contextLoads() {

	}

	<T> int getIterableSize(Iterable<T> it) {
		int count = 0;
		for (@SuppressWarnings("unused") T t : it) count++;
		return count;
	}

	Parking getNewParking() {
		Parking nouveauParking = new Parking();
		nouveauParking.setNom("Parking du gymnase");
		nouveauParking.setAdresse("Gymnase");
		return parkingService.ajouterParking(nouveauParking);
	}

	Place getNewPlace(int id_parking, int numero) {
		Place place = new Place();
		place.setNumero(numero);
		place.setType(TypePlace.NOMINALE);
		return parkingService.ajouterPlace(id_parking, place);
	}

	Parking getNoParking() {
		Parking parking = new Parking();
		parking.setId(-1);
		return parking;
	}

	@Test
	void testAjoutParkingFail() {

		assertThrows(IllegalArgumentException.class, () -> parkingService.ajouterParking(null));

		Parking parking  = new Parking();

		parking.setNom("nom");
		parking.setAdresse(null);
		assertThrows(IllegalArgumentException.class, () -> parkingService.ajouterParking(parking));

		parking.setNom(null);
		parking.setAdresse("adresse");
		assertThrows(IllegalArgumentException.class, () -> parkingService.ajouterParking(parking));

		parking.setNom("nom");
		parking.setAdresse("");
		assertThrows(IllegalArgumentException.class, () -> parkingService.ajouterParking(parking));

		parking.setNom("");
		parking.setAdresse("adresse");
		assertThrows(IllegalArgumentException.class, () -> parkingService.ajouterParking(parking));
	}
	
	@Test
	void testAjoutParking() {
		Parking parking = new Parking();
		Parking created;

		parking.setNom("Parking de la mairie");
		parking.setAdresse("Mairie");
		
		created = parkingService.ajouterParking(parking);

		assertEquals(parking.getNom(), created.getNom());
		assertEquals(parking.getAdresse(), created.getAdresse());
		assertEquals(0, created.getCompteur());

		parking.setNom("Parking du gymnase");
		parking.setAdresse("Gymnase");
		parking.setCompteur(12);
		
		created = parkingService.ajouterParking(parking);

		assertEquals(parking.getNom(), created.getNom());
		assertEquals(parking.getAdresse(), created.getAdresse());
		assertEquals(12, created.getCompteur());
	}

	@Test
	void testObtenirParkings() {
		Iterable<Parking> parkings;

		parkings = parkingService.obtenirParkings();
		int base = getIterableSize(parkings);

		final Parking parking = new Parking();

		Parking p1 = new Parking();
		p1.setNom("Nom 1");
		p1.setAdresse("Adresse 1");
		parkingService.ajouterParking(p1);

		parkings = parkingService.obtenirParkings();
		assertEquals(base + 1, getIterableSize(parkings));

		Parking p2 = new Parking();
		p2.setNom("Nom 2");
		p2.setAdresse("Adresse 2");
		parkingService.ajouterParking(p2);

		Parking p3 = new Parking();
		p3.setNom("Nom 3");
		p3.setAdresse("Adresse 3");
		parkingService.ajouterParking(p3);

		parkings = parkingService.obtenirParkings();
		assertEquals(base + 3, getIterableSize(parkings));
	}

	@Test
	void testObtenirParkingFail() {
		assertThrows(ParkingNotFoundException.class, () -> parkingService.obtenirParking(-1));
	}

	@Test
	void testObtenirParking() {
		Parking parking = new Parking();
		parking.setNom("Parking du gymnase");
		parking.setAdresse("Gymnase");
		parking.setCompteur(12);
		
		parking = parkingService.ajouterParking(parking);

		Parking obtenu = parkingService.obtenirParking(parking.getId());

		assertEquals(parking.getNom(), obtenu.getNom());
		assertEquals(parking.getAdresse(), obtenu.getAdresse());
		assertEquals(parking.getCompteur(), obtenu.getCompteur());
		assertEquals(parking.getId(), obtenu.getId());
	}

	@Test
	void testAjouterPlaceFail() {
		final Parking parking = getNewParking();
		
		final Place place = new Place();
		place.setNumero(1);
		place.setType(TypePlace.NOMINALE);

		assertThrows(ParkingNotFoundException.class, () -> parkingService.ajouterPlace(-1, place));

		assertThrows(IllegalArgumentException.class, () -> parkingService.ajouterPlace(parking.getId(), null));

		place.setNumero(null);
		place.setType(TypePlace.NOMINALE);
		assertThrows(IllegalArgumentException.class, () -> parkingService.ajouterPlace(parking.getId(), place));

		place.setNumero(2);
		place.setType(null);
		assertThrows(IllegalArgumentException.class, () -> parkingService.ajouterPlace(parking.getId(), place));
	}

	@Test
	void testAjouterPlace() {
		final Parking parking = getNewParking();

		Place place = new Place();
		Place created;

		place.setNumero(1);
		place.setType(TypePlace.NOMINALE);
		created = parkingService.ajouterPlace(parking.getId(), place);

		assertEquals(parking.getId(), created.getParkingId());
		assertEquals(place.getNumero(), created.getNumero());
		assertEquals(place.getType(), created.getType());
		assertEquals(EtatPlace.LIBRE, created.getEtat());
		
		place.setNumero(2);
		place.setType(TypePlace.DEUX_ROUES);
		place.setEtat(EtatPlace.OCCUPE);
		created = parkingService.ajouterPlace(parking.getId(), place);

		assertEquals(parking.getId(), created.getParkingId());
		assertEquals(place.getNumero(), created.getNumero());
		assertEquals(place.getType(), created.getType());
		assertEquals(place.getEtat(), created.getEtat());
		
		place.setNumero(2);
		place.setType(TypePlace.NOMINALE);
		assertThrows(PlaceExisteDejaException.class, () -> parkingService.ajouterPlace(parking.getId(), place));
	}
	
	@Test
	void testObtenirPlacesFail() {
		assertThrows(ParkingNotFoundException.class, () -> parkingService.obtenirPlaces(-1));
	}
	
	@Test
	void testObtenirPlaces() {
		Parking parking = getNewParking();

		Iterable<Place> places;

		places = parkingService.obtenirPlaces(parking.getId());
		assertEquals(0, getIterableSize(places));

		getNewPlace(parking.getId(), 1);
		places = parkingService.obtenirPlaces(parking.getId());
		assertEquals(1, getIterableSize(places));

		getNewPlace(parking.getId(), 2);
		getNewPlace(parking.getId(), 3);
		places = parkingService.obtenirPlaces(parking.getId());
		assertEquals(3, getIterableSize(places));
	}

	@Test
	void testObtenirPlaceFail() {
		Parking parking = getNewParking();

		assertThrows(ParkingNotFoundException.class, () -> parkingService.obtenirPlace(-1, -1));
		assertThrows(PlaceNotFoundException.class, () -> parkingService.obtenirPlace(parking.getId(), -1));
	}

	@Test
	void testObtenirPlace() {
		Parking parking = getNewParking();
		Place place = getNewPlace(parking.getId(), 1);

		Place obtenue = parkingService.obtenirPlace(parking.getId(), place.getNumero());
		
		assertEquals(place.getParkingId(), obtenue.getParkingId());
		assertEquals(place.getNumero(), obtenue.getNumero());
		assertEquals(place.getType(), obtenue.getType());
		assertEquals(place.getEtat(), obtenue.getEtat());
	}
	
	@Test
	void testReververPlaceFail() {
		Parking parking = getNewParking();

		final Place place = parkingService.ajouterPlace(parking.getId(), new Place().setNumero(1).setType(TypePlace.NOMINALE).setEtat(EtatPlace.OCCUPE));

		assertThrows(ParkingNotFoundException.class, () -> parkingService.reserverPlace(-1, place.getNumero(), place));
		
		assertThrows(PlaceNotFoundException.class, () -> parkingService.reserverPlace(place.getParkingId(), -1, place));

		assertThrows(IllegalArgumentException.class, () -> parkingService.reserverPlace(place.getParkingId(), place.getNumero(), null));

		final Place occupe = new Place().setEtat(EtatPlace.OCCUPE);

		assertThrows(PlaceDejaOccupeeException.class, () -> parkingService.reserverPlace(place.getParkingId(), place.getNumero(), occupe));
	}
	
	@Test
	void testReververPlace() {
		Parking parking = getNewParking();
		Place place = getNewPlace(parking.getId(), 1);

		Place libre = new Place().setEtat(EtatPlace.LIBRE);
		Place occupe = new Place().setEtat(EtatPlace.OCCUPE);

		Place nouvelle;

		nouvelle = parkingService.reserverPlace(place.getParkingId(), place.getNumero(), libre);
		assertEquals(libre.getEtat(), nouvelle.getEtat());
		
		nouvelle = parkingService.reserverPlace(place.getParkingId(), place.getNumero(), occupe);
		assertEquals(occupe.getEtat(), nouvelle.getEtat());
		
		assertThrows(PlaceDejaOccupeeException.class, () -> parkingService.reserverPlace(place.getParkingId(), place.getNumero(), occupe));
		
		nouvelle = parkingService.reserverPlace(place.getParkingId(), place.getNumero(), libre);
		assertEquals(libre.getEtat(), nouvelle.getEtat());
	}

	@Test
	void testEnregistrerEntreeFail() {
		assertThrows(ParkingNotFoundException.class, () -> parkingService.enregistrerEntree(-1));
	}

	@Test
	void testEnregistrerEntree() {
		Parking parking = getNewParking();
		int compteur;
		
		compteur = parkingService.enregistrerEntree(parking.getId());
		assertEquals(1, compteur);

		compteur = parkingService.enregistrerEntree(parking.getId());
		compteur = parkingService.enregistrerEntree(parking.getId());
		assertEquals(3, compteur);
	}

	@Test
	void testEnregistrerSortieFail() {
		assertThrows(ParkingNotFoundException.class, () -> parkingService.enregistrerSortie(-1));

		Parking parking = getNewParking();
		assertThrows(ParkingVideException.class, () -> parkingService.enregistrerSortie(parking.getId()));
	}

	@Test
	void testEnregistrerSortie() {
		Parking parking = getNewParking();
		int compteur;
		
		compteur = parkingService.enregistrerEntree(parking.getId());
		compteur = parkingService.enregistrerEntree(parking.getId());
		compteur = parkingService.enregistrerEntree(parking.getId());

		
		
		compteur = parkingService.enregistrerSortie(parking.getId());
		assertEquals(2, compteur);

		compteur = parkingService.enregistrerSortie(parking.getId());
		compteur = parkingService.enregistrerSortie(parking.getId());
		assertEquals(0, compteur);
	}

	@Test
	void testObtenirCompteurFail() {
		assertThrows(ParkingNotFoundException.class, () -> parkingService.enregistrerEntree(-1));
	}

	@Test
	void testObtenirCompteur() {
		Parking parking = getNewParking();
		int compteur;
		
		compteur = parkingService.obtenirCompteur(parking.getId());
		assertEquals(0, compteur);

		parkingService.enregistrerEntree(parking.getId());
		parkingService.enregistrerEntree(parking.getId());
		compteur = parkingService.obtenirCompteur(parking.getId());
		assertEquals(2, compteur);

		parkingService.enregistrerSortie(parking.getId());
		compteur = parkingService.obtenirCompteur(parking.getId());
		assertEquals(1, compteur);
	}

}