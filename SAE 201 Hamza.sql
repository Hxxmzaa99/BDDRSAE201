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
  `Numero_adherent` int(11) NOT NULL,
  `Nom` varchar(255) DEFAULT NULL,
  `Prenom` varchar(255) DEFAULT NULL,
  `Naissance` date DEFAULT NULL,
  `Telephone` varchar(20) DEFAULT NULL,
  `Telephone_Bis` varchar(20) DEFAULT NULL,
  `Benevole` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Numero_adherent`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Création de la table `VILLE`
CREATE TABLE `VILLE` (
  `Ville` varchar(255) NOT NULL,
  `Latitude_Ville` decimal(9,6) DEFAULT NULL,
  `Longitude_Ville` decimal(9,6) DEFAULT NULL,
  `Etat` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Ville`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Création de la table `ADRESSE`
CREATE TABLE `ADRESSE` (
  `Numero_adherent` int(11) NOT NULL,
  `Rue` varchar(255) DEFAULT NULL,
  `Ville` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Numero_adherent`),
  FOREIGN KEY (`Numero_adherent`) REFERENCES `ADHERENT_BASE` (`Numero_adherent`),
  FOREIGN KEY (`Ville`) REFERENCES `VILLE` (`Ville`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Création de la table `ADHESION`
CREATE TABLE `ADHESION` (
  `Numero_adherent` int(11) NOT NULL,
  `Annee` int(11) NOT NULL,
  `Date_adhesion` date DEFAULT NULL,
  `Montant` decimal(10,2) DEFAULT NULL,
  `Don` decimal(10,2) DEFAULT NULL,
  `Paiement` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Numero_adherent`, `Annee`),
  FOREIGN KEY (`Numero_adherent`) REFERENCES `ADHERENT_BASE` (`Numero_adherent`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;




COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
