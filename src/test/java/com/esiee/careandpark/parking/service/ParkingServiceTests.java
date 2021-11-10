package com.esiee.careandpark.parking.service;

import com.esiee.careandpark.parking.model.Parking;

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

	@Test
	void testAjoutParkingFail() {
		// parking sans nom
		// parking sans adresse
		// parking null
	}
	
	@Test
	void testAjoutParking() {
		// parking nom correct adresse correcte
		// vérifier nom adresse et compteur à 0
	}

	@Test
	void testObtenirParkings() {
		//obtenirparking()
		// vérifier liste vide

		// ajouterparking() - obtenirparkings()
		// vérifier taille 		
	}

	@Test
	void testObtenirParkingFail() {
		// id existe pas
	}

	@Test
	void testObtenirParking() {
		// ajouterparking() - obtenirparking()
		// vérifier 
	}

	@Test
	void testAjouterPlaceFail() {
		// id parking existe pas
		// place null
		// place pas de numero ?
		// place type null
		// place existe déjà
	}

	@Test
	void testAjouterPlace() {
		// ajouterplace() 
		// vérifier idparking numero type etat
	}
	
	@Test
	void testObtenirPlacesFail() {
		// parking existe pas
	}
	
	@Test
	void testObtenirPlaces() {
		// obtenirplaces()
		// vérifier liste vide

		// ajouterplace() - obtenirplaces()
		// vérifier taille liste
	}

	@Test
	void testObtenirPlaceFail() {
		// parking existe pas
		// place existe pas
	}

	@Test
	void testObtenirPlace() {
		// ajouterplace() - obtenirplace()
		// vérifier
	}
	
	@Test
	void testReververPlaceFail() {
		// parking existe pas
		// place existe pas
		// place null
		// nouvel etat null
		// nouvel etat occupé et place déjà occupee
	}
	
	@Test
	void testReververPlace() {
		// ajouterplace()
		// verifier etat change
	}

	@Test
	void testEnregistrerEntreeFail() {
		// parking existe pas
	}

	@Test
	void testEnregistrerEntree() {
		// ajouterparking() - enregistrerentree()
		// verifier incrémentation
	}

	@Test
	void testEnregistrerSortieFail() {
		// parking existe pas

		// ajouterparking() - enregistrersortie()
		// verifier erreur compteur negatif
	}

	@Test
	void testEnregistrerSortie() {

		// enregistrerentree() - enregistrersortie()
		// verifier decrementation
	}

	@Test
	void testObtenirCompteurFail() {
		// parking existe pas
	}

	@Test
	void testObtenirCompteur() {
		// ajouterparking() - obtenircompteur()
	}

}