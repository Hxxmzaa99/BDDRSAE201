-- phpMyAdmin SQL Dump
-- version 6.0.0-dev
-- https://www.phpmyadmin.net/
--
-- Hôte : 192.168.30.23
-- Généré le : Mar. 21 Mai 2024 à 08:10
-- Version du serveur : 8.0.18
-- Version de PHP : 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `TableSAEHamza`
--

-- --------------------------------------------------------

--
-- Structure de la table `ADHERENT_BASE`
--

-- Création de la table `ADHERENT_BASE`
CREATE TABLE `ADHERENT_BASE` (
  `NUMERO_ADHERENT` int(11) NOT NULL,
  `Nom` varchar(255) DEFAULT NULL,
  `Prenom` varchar(255) DEFAULT NULL,
  `DATE_DE_NAISSANCE` date DEFAULT NULL,
  `TELEPHONE` varchar(20) DEFAULT NULL,
  `Telephone_Bis` varchar(20) DEFAULT NULL,
  `BENEVOLE` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`NUMERO_ADHERENT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Création de la table `VILLE`
CREATE TABLE `VILLE` (
  `Ville` varchar(255) NOT NULL,
  `Latitude_Ville` decimal(9,6) DEFAULT NULL,
  `Longitude_Ville` decimal(9,6) DEFAULT NULL,
  `Etat` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Ville`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Création de la table `ADRESSE`
CREATE TABLE `ADRESSE` (
  `NUMERO_ADHERENT` int(11) NOT NULL,
  `Rue` varchar(255) DEFAULT NULL,
  `Ville` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`NUMERO_ADHERENT`),
  FOREIGN KEY (`NUMERO_ADHERENT`) REFERENCES `ADHERENT_BASE` (`NUMERO_ADHERENT`),
  FOREIGN KEY (`Ville`) REFERENCES `VILLE` (`Ville`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Création de la table `ADHESION`
CREATE TABLE `ADHESION` (
  `NUMERO_ADHERENT` int(11) NOT NULL,
  `ANNEE` int(11) NOT NULL,
  `DATE_ADHESION` date DEFAULT NULL,
  `MONTANT` decimal(10,2) DEFAULT NULL,
  `DON` decimal(10,2) DEFAULT NULL,
  `MOYEN_DE_PAIEMENT` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`NUMERO_ADHERENT`, `ANNEE`),
  FOREIGN KEY (`NUMERO_ADHERENT`) REFERENCES `ADHERENT_BASE` (`NUMERO_ADHERENT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
