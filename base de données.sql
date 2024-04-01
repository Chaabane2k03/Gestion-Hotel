/*Création de la base de données */
CREATE DATABASE IF NOT EXISTS hotel;
USE hotel;

/*Création des tables */

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `iduser` int NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `password` varchar(32) NOT NULL,
  `idclient` int DEFAULT NULL,
  `typeuser` int NOT NULL,
  PRIMARY KEY (`iduser`),
  KEY `idclient_idx` (`idclient`),
  CONSTRAINT `idclient` FOREIGN KEY (`idclient`) REFERENCES `client` (`idclient`)
);

DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
  `idclient` int NOT NULL AUTO_INCREMENT,
  `nom_client` varchar(45) NOT NULL,
  `prenom_client` varchar(45) NOT NULL,
  `adresse` varchar(45) DEFAULT NULL,
  `num_tel` varchar(8) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`idclient`)
);

DROP TABLE IF EXISTS `chambre`;
CREATE TABLE `chambre` (
  `numchambre` int NOT NULL,
  `capacity` int NOT NULL,
  `typechambre` varchar(45) NOT NULL,
  `status` int NOT NULL,
  `etage` int NOT NULL,
  PRIMARY KEY (`numchambre`)
);

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE `reservation` (
  `idreservation` int NOT NULL AUTO_INCREMENT,
  `id_client_reservant` int NOT NULL,
  `date_reservation` date NOT NULL,
  `typereservation` int NOT NULL,
  `check_in_date` date NOT NULL,
  `check_out_date` date NOT NULL,
  `souhait_particulier` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idreservation`),
  KEY `id_client_reservant_idx` (`id_client_reservant`),
  CONSTRAINT `id_client_reservant` FOREIGN KEY (`id_client_reservant`) REFERENCES `client` (`idclient`)
);

DROP TABLE IF EXISTS `res_cham`;
CREATE TABLE `res_cham` (
  `idreservation` int NOT NULL,
  `numchambre` int NOT NULL,
  PRIMARY KEY (`idreservation`,`numchambre`),
  KEY `fk_Res_Cham_chambre1_idx` (`numchambre`),
  CONSTRAINT `fk_Res_Cham_chambre1` FOREIGN KEY (`numchambre`) REFERENCES `chambre` (`numchambre`),
  CONSTRAINT `fk_Res_Cham_reservation1` FOREIGN KEY (`idreservation`) REFERENCES `reservation` (`idreservation`)
);

DROP TABLE IF EXISTS `paiement`;
CREATE TABLE `paiement` (
  `idpaiement` int NOT NULL AUTO_INCREMENT,
  `idreservation` int NOT NULL,
  `montant` float NOT NULL,
  `date` date NOT NULL,
  `surcharge` float DEFAULT NULL,
  `typepaiement` varchar(45) NOT NULL,
  PRIMARY KEY (`idpaiement`),
  KEY `idreservation_idx` (`idreservation`)
);
ALTER TABLE `paiement` ADD CONSTRAINT `idreservation` FOREIGN KEY (`idreservation`) REFERENCES `reservation` (`idreservation`);