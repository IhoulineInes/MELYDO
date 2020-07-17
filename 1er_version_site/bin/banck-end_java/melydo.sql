-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3308
-- Généré le :  mer. 03 juin 2020 à 10:41
-- Version du serveur :  5.7.28
-- Version de PHP :  7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `melydo`
--

-- --------------------------------------------------------

--
-- Structure de la table `like`
--

DROP TABLE IF EXISTS `like`;
CREATE TABLE IF NOT EXISTS `like` (
  `id_user` int(11) NOT NULL,
  `id_musique` int(11) NOT NULL,
  `date` varchar(255) NOT NULL,
  PRIMARY KEY (`id_musique`,`id_user`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `like`
--

INSERT INTO `like` (`id_user`, `id_musique`, `date`) VALUES
(16, 17, 'Tue Jun 02 19:27:36 CEST 2020'),
(16, 18, 'Tue Jun 02 19:27:42 CEST 2020');

-- --------------------------------------------------------


--
-- Structure de la table `musique`
--

DROP TABLE IF EXISTS `musique`;
CREATE TABLE IF NOT EXISTS `musique` (
  `id_musique` int(11) NOT NULL,
  `genre` int(11) NOT NULL,
  `nom_musique` varchar(255) NOT NULL,
  `date` varchar(255) NOT NULL,
  PRIMARY KEY (`id_musique`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `like`
--

INSERT INTO `like` (`id_user`, `id_musique`, `date`) VALUES
(16, 17, 'Tue Jun 02 19:27:36 CEST 2020'),
(16, 18, 'Tue Jun 02 19:27:42 CEST 2020');

-- --------------------------------------------------------

--
-- Structure de la table `session`
--

DROP TABLE IF EXISTS `session`;
CREATE TABLE IF NOT EXISTS `session` (
  `id_user` int(11) NOT NULL,
  `date` varchar(255) NOT NULL,
  `cle` varchar(255) NOT NULL,
  `root` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `cle` (`cle`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `session`
--

INSERT INTO `session` (`id_user`, `date`, `cle`, `root`) VALUES
(16, 'Tue May 12 23:37:32 CEST 2020', 'GzoqRW03XETLnkMjsnav2UVoB22', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(255) NOT NULL,
  `mdp` varchar(255) NOT NULL,
  `mail` varchar(255) NOT NULL,
  `pref` varchar(255) NOT NULL,
  `date` varchar(255) NOT NULL,
  `nbr_connexion` int(255) DEFAULT '0',
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `mail` (`mail`),
  UNIQUE KEY `login_2` (`login`),
  UNIQUE KEY `id_user` (`id_user`)
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id_user`, `login`, `mdp`, `mail`, `pref`, `date`, `nbr_connexion`) VALUES
(12,'namide','1234','namide@mail.com','rock','Mon Apr 06 18:34:40 CEST 2020', 20),
(14,'anni','1234','anni@mail.com','jazz','Wed Apr 15 11:27:32 CEST 2020', 1),
(16,'ness','1234','ness@mail.com','jazz','Wed Apr 22 21:27:58 CEST 2020', 0);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
