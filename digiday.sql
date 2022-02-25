-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 24 fév. 2022 à 16:14
-- Version du serveur : 10.4.21-MariaDB
-- Version de PHP : 7.1.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `digiday`
--

-- --------------------------------------------------------

--
-- Structure de la table `company`
--

CREATE TABLE `company` (
  `id` int(11) NOT NULL,
  `remaining_rtt` int(11) DEFAULT NULL,
  `rtt_taken` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `company`
--

INSERT INTO `company` (`id`, `remaining_rtt`, `rtt_taken`) VALUES
(1, 5, 2);

-- --------------------------------------------------------

--
-- Structure de la table `company_holiday`
--

CREATE TABLE `company_holiday` (
  `id` int(11) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `company_holiday`
--

INSERT INTO `company_holiday` (`id`, `comment`, `date`, `status`, `type`, `company_id`) VALUES
(1, 'Jour de l\'an', '2022-01-01', NULL, 'PUBLIC_HOLIDAY', 1),
(2, 'Lundi de Pâques', '2022-04-18', NULL, 'PUBLIC_HOLIDAY', 1),
(3, 'Fête du travail', '2022-05-01', NULL, 'PUBLIC_HOLIDAY', 1),
(4, 'Victoire 1945', '2022-05-08', NULL, 'PUBLIC_HOLIDAY', 1),
(5, 'Jeudi de l\'Ascension', '2022-05-26', NULL, 'PUBLIC_HOLIDAY', 1),
(6, 'Lundi de Pentecôte', '2022-06-06', NULL, 'PUBLIC_HOLIDAY', 1),
(7, 'Fête nationale', '2022-07-14', NULL, 'PUBLIC_HOLIDAY', 1),
(8, 'Assomption', '2022-08-15', NULL, 'PUBLIC_HOLIDAY', 1),
(9, 'Toussaint', '2022-11-01', NULL, 'PUBLIC_HOLIDAY', 1),
(10, 'Armistice 1918', '2022-11-11', NULL, 'PUBLIC_HOLIDAY', 1),
(11, 'Jour de Noël', '2022-12-25', NULL, 'PUBLIC_HOLIDAY', 1),
(12, '', '2022-08-16', 'VALIDATED', 'COMPANY_RTT', 1),
(13, 'Pont de la Toussaint', '2022-10-31', 'INITIAL', 'COMPANY_RTT', 1);

-- --------------------------------------------------------

--
-- Structure de la table `department`
--

CREATE TABLE `department` (
  `id` int(11) NOT NULL,
  `label` varchar(255) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `department`
--

INSERT INTO `department` (`id`, `label`, `company_id`) VALUES
(1, 'Management', 1),
(2, 'Développement', 1),
(3, 'Commercial', 1),
(4, 'Service après vente', 1);

-- --------------------------------------------------------

--
-- Structure de la table `leave_`
--

CREATE TABLE `leave_` (
  `id` int(11) NOT NULL,
  `end_date` date DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `leave_`
--

INSERT INTO `leave_` (`id`, `end_date`, `reason`, `start_date`, `status`, `type`, `user_id`) VALUES
(1, '2022-03-18', '', '2022-03-07', 'VALIDATED', 'PAID_LEAVE', 2),
(2, '2022-08-12', '', '2022-08-01', 'PENDING_VALIDATION', 'PAID_LEAVE', 2),
(3, '2022-12-30', '', '2022-12-26', 'PENDING_VALIDATION', 'PAID_LEAVE', 2),
(4, '2022-01-07', 'Ceci est ma raison', '2022-01-03', 'VALIDATED', 'UNPAID_LEAVE', 3),
(5, '2022-01-14', '', '2022-01-10', 'VALIDATED', 'RTT', 3),
(6, '2022-12-30', 'Trop long, doit être refusé par le batch de nuit', '2022-10-31', 'INITIAL', 'PAID_LEAVE', 3),
(7, '2021-12-31', '', '2021-12-27', 'REJECTED', 'PAID_LEAVE', 4),
(8, '2022-01-07', '', '2022-01-03', 'PENDING_VALIDATION', 'PAID_LEAVE', 4),
(9, '2022-12-30', '', '2022-12-26', 'REJECTED', 'PAID_LEAVE', 4),
(10, '2021-12-31', 'requête validée dans le passé', '2021-12-27', 'VALIDATED', 'PAID_LEAVE', 5),
(11, '2022-03-18', 'requête validée dans le futur', '2022-03-07', 'VALIDATED', 'PAID_LEAVE', 5),
(12, '2022-06-03', 'C\'est juste pour mettre un congé entre deux mois pour vous embêter <3', '2022-05-30', 'VALIDATED', 'UNPAID_LEAVE', 5),
(13, '2022-07-22', '', '2022-07-18', 'REJECTED', 'RTT', 5),
(14, '2022-07-29', '', '2022-07-25', 'PENDING_VALIDATION', 'PAID_LEAVE', 5),
(15, '2022-12-30', 'Devrait être acceptée par le batch de nuit', '2022-12-26', 'INITIAL', 'PAID_LEAVE', 5),
(16, '2022-03-11', '', '2022-02-28', 'VALIDATED', 'PAID_LEAVE', 6),
(17, '2022-03-18', '', '2022-03-14', 'VALIDATED', 'RTT', 6),
(18, '2022-03-18', '', '2022-03-07', 'VALIDATED', 'PAID_LEAVE', 7),
(19, '2022-03-25', '', '2022-03-14', 'VALIDATED', 'PAID_LEAVE', 8),
(20, '2022-04-01', '', '2022-03-21', 'VALIDATED', 'PAID_LEAVE', 9),
(21, '2022-04-22', 'Attention au jour férié au milieu !', '2022-04-11', 'INITIAL', 'PAID_LEAVE', 10),
(22, '2022-04-22', 'Attention au jour férié au milieu !', '2022-04-11', 'VALIDATED', 'PAID_LEAVE', 11),
(23, '2022-04-22', 'Attention au jour férié au milieu !', '2022-04-11', 'VALIDATED', 'PAID_LEAVE', 12),
(24, '2022-04-19', 'Test unitaire suppression date future', '2022-04-04', 'VALIDATED', 'PAID_LEAVE', 12),
(25, '2022-04-19', 'Test unitaire suppression - status = PENDING_VALIDATION', '2022-04-04', 'PENDING_VALIDATION', 'PAID_LEAVE', 12),
(26, '2022-04-19', 'Test unitaire suppression - suppression ok', '2022-04-04', 'VALIDATED', 'PAID_LEAVE', 12),
(27, '2022-04-19', 'Test unitaire supression - date début = date du jour', '2022-02-22', 'VALIDATED', 'PAID_LEAVE', 12),
(28, '2022-04-19', 'Test unitaire supression - status = initial', '2022-02-22', 'INITIAL', 'PAID_LEAVE', 12),
(29, '2022-04-19', 'Test unitaire supression - status = initial', '2022-02-22', 'REJECTED', 'PAID_LEAVE', 12);

-- --------------------------------------------------------

--
-- Structure de la table `leave_counters`
--

CREATE TABLE `leave_counters` (
  `id` int(11) NOT NULL,
  `paid_leaves_taken` int(11) DEFAULT NULL,
  `remaining_paid_leaves` int(11) DEFAULT NULL,
  `remaining_rtt` int(11) DEFAULT NULL,
  `rtt_taken` int(11) DEFAULT NULL,
  `unpaid_leaves_taken` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `leave_counters`
--

INSERT INTO `leave_counters` (`id`, `paid_leaves_taken`, `remaining_paid_leaves`, `remaining_rtt`, `rtt_taken`, `unpaid_leaves_taken`, `user_id`) VALUES
(1, 0, 25, 6, 0, 0, 1),
(2, 10, 15, 6, 0, 0, 2),
(3, 0, 25, 1, 5, 5, 3),
(4, 0, 25, 6, 0, 0, 4),
(5, 15, 10, 6, 0, 5, 5),
(6, 10, 15, 1, 5, 0, 6),
(7, 10, 15, 6, 0, 0, 7),
(8, 10, 15, 6, 0, 0, 8),
(9, 10, 15, 6, 0, 0, 9),
(10, 0, 25, 6, 0, 0, 10),
(11, 9, 16, 6, 0, 0, 11),
(12, 9, 16, 6, 0, 0, 12);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `department_id` int(11) DEFAULT NULL,
  `leave_counters_id` int(11) DEFAULT NULL,
  `manager_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `email`, `first_name`, `last_name`, `password`, `role`, `department_id`, `leave_counters_id`, `manager_id`) VALUES
(1, 'jean-eudes.marouflard@yopmail.com', 'Jean-Eudes', 'Marouflard', '123456', 'MANAGER', 1, 1, NULL),
(2, 'marguerite.duchene@yopmail.com', 'Marguerite', 'Duchêne', '123456', 'MANAGER', 3, 2, 1),
(3, 'christian.dumaine@yopmail.com', 'Christian', 'Du Maine', '123456', 'EMPLOYEE', 3, 3, 2),
(4, 'jean-jacques.martin@yopmail.com', 'Jean-Jacques', 'Martin', '123456', 'ADMINISTRATOR', 1, 4, 1),
(5, 'juliette.marie@yopmail.com', 'Juliette', 'Marie', '123456', 'MANAGER', 2, 5, 1),
(6, 'paul.fleuve@yopmail.com', 'Paul', 'Fleuve', '123456', 'EMPLOYEE', 2, 6, 5),
(7, 'christine.maitre@yopmail.com', 'Christine', 'Maitre', '123456', 'EMPLOYEE', 2, 7, 5),
(8, 'adrien.marcher@yopmail.com', 'Adrien', 'Marcher', '123456', 'EMPLOYEE', 2, 8, 5),
(9, 'hubert.creux@yopmail.com', 'Hubert', 'Creux', '123456', 'EMPLOYEE', 2, 9, 5),
(10, 'gaelle.sachet@yopmail.com', 'Gaëlle', 'Sachet', '123456', 'MANAGER', 4, 10, 1),
(11, 'marc.jarre@yopmail.com', 'Marc', 'Jarre', '123456', 'EMPLOYEE', 4, 11, 10),
(12, 'simone.muret@yopmail.com', 'Simone', 'Muret', '123456', 'EMPLOYEE', 4, 12, 10);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `company`
--
ALTER TABLE `company`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `company_holiday`
--
ALTER TABLE `company_holiday`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK165ej7visymb9brao78j7jpb8` (`company_id`);

--
-- Index pour la table `department`
--
ALTER TABLE `department`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKh1m88q0f7sc0mk76kju4kcn6f` (`company_id`);

--
-- Index pour la table `leave_`
--
ALTER TABLE `leave_`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrcp8av9qvasxhkbblj105dqo6` (`user_id`);

--
-- Index pour la table `leave_counters`
--
ALTER TABLE `leave_counters`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKf2n6iy8yrb1r9opjgxsb0sn2q` (`user_id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgkh2fko1e4ydv1y6vtrwdc6my` (`department_id`),
  ADD KEY `FKnu35beuh1xy7lpitcurocs15d` (`leave_counters_id`),
  ADD KEY `FKl9blkgio1nb00hot7kaxoy7q9` (`manager_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `company`
--
ALTER TABLE `company`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `company_holiday`
--
ALTER TABLE `company_holiday`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT pour la table `department`
--
ALTER TABLE `department`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `leave_`
--
ALTER TABLE `leave_`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT pour la table `leave_counters`
--
ALTER TABLE `leave_counters`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `company_holiday`
--
ALTER TABLE `company_holiday`
  ADD CONSTRAINT `FK165ej7visymb9brao78j7jpb8` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);

--
-- Contraintes pour la table `department`
--
ALTER TABLE `department`
  ADD CONSTRAINT `FKh1m88q0f7sc0mk76kju4kcn6f` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);

--
-- Contraintes pour la table `leave_`
--
ALTER TABLE `leave_`
  ADD CONSTRAINT `FKrcp8av9qvasxhkbblj105dqo6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `leave_counters`
--
ALTER TABLE `leave_counters`
  ADD CONSTRAINT `FKf2n6iy8yrb1r9opjgxsb0sn2q` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FKgkh2fko1e4ydv1y6vtrwdc6my` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`),
  ADD CONSTRAINT `FKl9blkgio1nb00hot7kaxoy7q9` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKnu35beuh1xy7lpitcurocs15d` FOREIGN KEY (`leave_counters_id`) REFERENCES `leave_counters` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
