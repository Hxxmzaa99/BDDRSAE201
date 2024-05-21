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
-- Structure de la table `Adherents`
--

CREATE TABLE `Adherents` (
  `NUMERO_ADHERENT` int(11) NOT NULL,
  `Nom` varchar(255) DEFAULT NULL,
  `Prenom` varchar(255) DEFAULT NULL,
  `DATE_DE_NAISSANCE` date DEFAULT NULL,
  `Rue` varchar(255) DEFAULT NULL,
  `Ville` varchar(255) DEFAULT NULL,
  `Latitude_Ville` float DEFAULT NULL,
  `Longitude_Ville` float DEFAULT NULL,
  `Etat` varchar(255) DEFAULT NULL,
  `TELEPHONE` varchar(20) DEFAULT NULL,
  `BÉNÉVOLE` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `Adhesions`
--

CREATE TABLE `Adhesions` (
  `ID` int(11) NOT NULL,
  `NUMERO_ADHERENT` int(11) DEFAULT NULL,
  `Annee` int(11) DEFAULT NULL,
  `DATE_ADHESION` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `Dons`
--

CREATE TABLE `Dons` (
  `ID` int(11) NOT NULL,
  `NUMERO_ADHERENT` int(11) DEFAULT NULL,
  `Annee` int(11) DEFAULT NULL,
  `MONTANT` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `Paiements`
--

CREATE TABLE `Paiements` (
  `ID` int(11) NOT NULL,
  `NUMERO_ADHERENT` int(11) DEFAULT NULL,
  `Annee` int(11) DEFAULT NULL,
  `MONTANT` decimal(10,2) DEFAULT NULL,
  `MOYEN_DE_PAIEMENT` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `Adherents`
--
ALTER TABLE `Adherents`
  ADD PRIMARY KEY (`NUMERO_ADHERENT`);

--
-- Index pour la table `Adhesions`
--
ALTER TABLE `Adhesions`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `NUMERO_ADHERENT` (`NUMERO_ADHERENT`);

--
-- Index pour la table `Dons`
--
ALTER TABLE `Dons`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `NUMERO_ADHERENT` (`NUMERO_ADHERENT`);

--
-- Index pour la table `Paiements`
--
ALTER TABLE `Paiements`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `NUMERO_ADHERENT` (`NUMERO_ADHERENT`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `Adhesions`
--
ALTER TABLE `Adhesions`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Dons`
--
ALTER TABLE `Dons`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Paiements`
--
ALTER TABLE `Paiements`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `Adhesions`
--
ALTER TABLE `Adhesions`
  ADD CONSTRAINT `Adhesions_ibfk_1` FOREIGN KEY (`NUMERO_ADHERENT`) REFERENCES `Adherents` (`NUMERO_ADHERENT`);

--
-- Contraintes pour la table `Dons`
--
ALTER TABLE `Dons`
  ADD CONSTRAINT `Dons_ibfk_1` FOREIGN KEY (`NUMERO_ADHERENT`) REFERENCES `Adherents` (`NUMERO_ADHERENT`);

--
-- Contraintes pour la table `Paiements`
--
ALTER TABLE `Paiements`
  ADD CONSTRAINT `Paiements_ibfk_1` FOREIGN KEY (`NUMERO_ADHERENT`) REFERENCES `Adherents` (`NUMERO_ADHERENT`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
