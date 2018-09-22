-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 25, 2015 at 10:14 PM
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
-- Table structure for table `busdate`
--

CREATE TABLE IF NOT EXISTS `busdate` (
`id` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  `route` varchar(64) NOT NULL,
  `date` varchar(64) NOT NULL,
  `time` varchar(10) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `busdate`
--

INSERT INTO `busdate` (`id`, `name`, `route`, `date`, `time`) VALUES
(1, 'Hanif', 'Dhaka To Chittagong', '13-14-15', '8.00 AM'),
(3, 'Shaymoli', 'Dhaka To Chittagong', '13-15-20', '8.00 AM'),
(4, 'shaymoli', 'Dhaka To Rajshahi', '18-19-20', '10.00 PM'),
(6, 'Hanif', 'Dhaka To Chittagong', '14-15-16', '10.00 AM');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `busdate`
--
ALTER TABLE `busdate`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `id` (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `busdate`
--
ALTER TABLE `busdate`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
