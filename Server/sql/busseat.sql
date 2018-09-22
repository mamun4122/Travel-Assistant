-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 25, 2015 at 10:15 PM
-- Server version: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `mamun`
--

-- --------------------------------------------------------

--
-- Table structure for table `busseat`
--

CREATE TABLE IF NOT EXISTS `busseat` (
  `id` int(11) NOT NULL,
  `A1` tinyint(1) NOT NULL DEFAULT '0',
  `A2` tinyint(1) NOT NULL DEFAULT '0',
  `A3` tinyint(1) NOT NULL DEFAULT '0',
  `A4` tinyint(1) NOT NULL DEFAULT '0',
  `B1` tinyint(1) NOT NULL DEFAULT '0',
  `B2` tinyint(1) NOT NULL DEFAULT '0',
  `B3` tinyint(1) NOT NULL DEFAULT '0',
  `B4` tinyint(1) NOT NULL DEFAULT '0',
  `C1` tinyint(1) NOT NULL DEFAULT '0',
  `C2` tinyint(1) NOT NULL DEFAULT '0',
  `C3` tinyint(1) NOT NULL DEFAULT '0',
  `C4` tinyint(1) NOT NULL DEFAULT '0',
  `D1` tinyint(1) NOT NULL DEFAULT '0',
  `D2` tinyint(1) NOT NULL DEFAULT '0',
  `D3` tinyint(1) NOT NULL DEFAULT '0',
  `D4` tinyint(1) NOT NULL DEFAULT '0',
  `E1` tinyint(1) NOT NULL DEFAULT '0',
  `E2` tinyint(1) NOT NULL DEFAULT '0',
  `E3` tinyint(1) NOT NULL DEFAULT '0',
  `E4` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `busseat`
--

INSERT INTO `busseat` (`id`, `A1`, `A2`, `A3`, `A4`, `B1`, `B2`, `B3`, `B4`, `C1`, `C2`, `C3`, `C4`, `D1`, `D2`, `D3`, `D4`, `E1`, `E2`, `E3`, `E4`) VALUES
(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
(3, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1),
(4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(6, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `busseat`
--
ALTER TABLE `busseat`
 ADD UNIQUE KEY `id` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
