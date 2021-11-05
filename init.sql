#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS db;
USE db;
#------------------------------------------------------------
# Table: Parking
#------------------------------------------------------------

CREATE TABLE Parking IF NOT EXISTS(
        id      Int  Auto_increment  NOT NULL ,
        nom     Varchar (50) NOT NULL ,
        adresse Varchar (50) NOT NULL
	,CONSTRAINT Parking_PK PRIMARY KEY (id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Place
#------------------------------------------------------------

CREATE TABLE Place IF NOT EXISTS(
        id         Int  Auto_increment  NOT NULL ,
        type       Varchar (50) NOT NULL ,
        numero     Int NOT NULL ,
        etat       TINYINT NOT NULL ,
        id_Parking Int NOT NULL
	,CONSTRAINT Place_PK PRIMARY KEY (id)

	,CONSTRAINT Place_Parking_FK FOREIGN KEY (id_Parking) REFERENCES Parking(id)
)ENGINE=InnoDB;

