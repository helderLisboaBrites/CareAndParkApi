package com.esiee.careandpark.parking.model;

import java.io.Serializable;

public class PlaceId implements Serializable {
    private int numero;
    private int parkingId;

    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getParkingId() {
        return parkingId;
    }
    public void setParkingId(int parkingId) {
        this.parkingId = parkingId;
    }
}
