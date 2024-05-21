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

-- Sauvegarder les anciens paramètres de jeu de caractères
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;

-- Base de données : `TableSAEHamza`

-- --------------------------------------------------------
-- Structure de la table `Adherents`
-- --------------------------------------------------------

CREATE TABLE `Adherents` (
  `NUMERO_ADHERENT` int(11) NOT NULL,
  `Nom` varchar(255) DEFAULT NULL,
  `Prenom` varchar(255) DEFAULT NULL,
  `DATE_DE_NAISSANCE` date DEFAULT NULL,
  `Rue` varchar(255) DEFAULT NULL,
  `Ville` varchar(255) DEFAULT NULL,
  `TELEPHONE` varchar(20) DEFAULT NULL,
  `BÉNÉVOLE` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`NUMERO_ADHERENT`),
  KEY `Ville` (`Ville`),
  CONSTRAINT `Adherents_ibfk_1` FOREIGN KEY (`Ville`) REFERENCES `Ville` (`Ville`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------
-- Structure de la table `Ville`
-- --------------------------------------------------------

CREATE TABLE `Ville` (
  `Ville` varchar(255) NOT NULL,
  `Latitude_Ville` float DEFAULT NULL,
  `Longitude_Ville` float DEFAULT NULL,
  `Etat` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Ville`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------
-- Structure de la table `Adhesions`
-- --------------------------------------------------------

CREATE TABLE `Adhesions` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NUMERO_ADHERENT` int(11) NOT NULL,
  `Annee` int(11) NOT NULL,
  `DATE_ADHESION` date DEFAULT NULL,
  `MONTANT` decimal(10,2) DEFAULT NULL,
  `DON` decimal(10,2) DEFAULT NULL,
  `MOYEN_DE_PAIEMENT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`NUMERO_ADHERENT`, `Annee`),
  KEY `ID` (`ID`),
  CONSTRAINT `Adhesions_ibfk_1` FOREIGN KEY (`NUMERO_ADHERENT`) REFERENCES `Adherents` (`NUMERO_ADHERENT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Fin de la transaction
COMMIT;

-- Restaurer les anciens paramètres de jeu de caractères
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
