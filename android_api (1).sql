-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 09, 2016 at 12:58 AM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.6.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `android_api`
--

-- --------------------------------------------------------

--
-- Table structure for table `baby`
--

CREATE TABLE `baby` (
  `id` int(11) NOT NULL,
  `profile_picture` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `middlename` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `birthday` varchar(255) NOT NULL,
  `height` varchar(255) NOT NULL,
  `weight` varchar(255) NOT NULL,
  `nationality` varchar(255) NOT NULL,
  `gender` varchar(50) NOT NULL,
  `city` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `baby`
--

INSERT INTO `baby` (`id`, `profile_picture`, `firstname`, `middlename`, `lastname`, `birthday`, `height`, `weight`, `nationality`, `gender`, `city`, `country`, `created_by`, `created_at`, `updated_at`) VALUES
(28, 'http://192.168.43.67/android_login_api/picture/Default.png', 'IanBaby', 'B', 'Osias', '11-1-2015', '27.0 - 28.3 inches', '16.7 - 19.7 pounds', 'Chinese', '', 'Bisilig City', 'Austria', 'IanParent', '2015-11-29 19:26:24', NULL),
(29, 'http://192.168.8.102/android_login_api/picture/Cute-Babies-With-Blue-Eyes-4.jpg', 'john', 'a', 'mcCarter', '12-11-1900', '27.0 - 28.3 inches', '14.8 - 17.5 pounds', 'Argentines', 'Female', 'Bago City', 'Antigua and Barbuda', 'devs', '2015-12-11 17:43:22', NULL),
(30, 'http://192.168.254.3/android_login_api/picture/Default.png', 'asd', 's', 'asd', '12-1-2015', '25.3 - 26.5 inches', '13.0 - 15.2 pounds', 'Americans', 'Male', 'Bago City', 'Andorra', 'user', '2015-12-12 07:02:03', NULL),
(31, 'http://192.168.8.102/android_login_api/picture/Default.png', 'ralph', 'm', 'recto', '12-15-2020', '23.6 - 24.7 inches', '13.0 - 15.2 pounds', 'Argentines', 'Female', 'Bacolod City', 'Antigua and Barbuda', 'toshiba', '2015-12-15 16:13:33', NULL),
(32, 'http://172.31.11.32:80/android_login_api/picture/Default.png', 'baby', 'nurture', 'app', '10-7-1996', '34.1 - 36.1 inches', '19.8 - 22.9 pounds', 'Japanese', 'Male', 'Iloilo City', 'Australia', 'cy', '2016-10-07 17:02:08', NULL),
(33, 'http://172.31.11.32:80/android_login_api/picture/Default.png', 'Alvin', 'boy', 'capaning', '11-7-2016', '27.7 - 28.9 inches', '16.7 - 19.7 pounds', 'Australians', 'Male', 'Cabanatuan City', 'Bangladesh', 'cy', '2016-10-07 17:03:39', NULL),
(34, 'http://172.31.11.32:80/android_login_api/picture/Default.png', 'Angie', 'Mae', 'Gomez', '10-8-2016', '25.3 - 26.5 inches', '16.7 - 19.7 pounds', 'Americans', 'Female', 'Balanga City', 'Aruba', 'acnologia', '2016-10-07 17:14:59', NULL),
(35, 'http://172.31.11.32:80/android_login_api/picture/Default.png', 'babylabs', 'anako', 'babykho', '11-8-2017', '26.1 - 27.2 inches', '18.2 - 21.1 pounds', 'Canadians', 'Female', 'Bais City', 'Aruba', 'binggwapa', '2016-10-07 17:22:25', NULL),
(36, 'http://172.31.11.32:80/android_login_api/picture/Default.png', 'john', 'spi', 'sun', '11-8-2016', '36.5 - 38.6 inches', '33.3 - 39.1 pounds', 'British', 'Male', 'Cebu City', 'France', 'nica', '2016-10-08 14:46:36', NULL),
(37, 'http://172.31.11.32:80/android_login_api/picture/Default.png', 'Baby John', 'Spi', 'Sun', '12-8-2016', '39.2 - 41.5 inches', '36.3 - 44.0 pounds', 'British', 'Male', 'Cebu City', 'Philippines', 'acnologia', '2016-10-08 17:47:09', NULL),
(38, 'http://172.31.11.32:80/android_login_api/picture/Default.png', 'darren', 'hdjs', 'dhs', '10-11-2016', '25.3 - 26.5 inches', '16.7 - 19.7 pounds', 'Canadians', 'Male', 'Bais City', 'Australia', 'david', '2016-10-08 23:21:31', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `babysitter`
--

CREATE TABLE `babysitter` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `encrypted_password` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `type` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `babysitter`
--

INSERT INTO `babysitter` (`id`, `name`, `encrypted_password`, `salt`, `created_by`, `created_at`, `updated_at`, `type`) VALUES
(16, 'BSIAN', 'qgExk27RvmZrUT7+A0ZLlmlT2FE0YmE3NDM2NGZk', '4ba74364fd', 'IanParent', '2015-11-29 19:26:40', NULL, '0'),
(17, 'tol', 'A+n7u+fykFbirDKME316M7VUUHwxMGUxNWRhMjNk', '10e15da23d', 'devs', '2015-12-11 17:42:51', NULL, '0'),
(18, 'user2', '98sxKwghRoa9sorq0xgcCZDwKJgxM2NkN2ZkOGQ3', '13cd7fd8d7', 'user', '2015-12-12 07:01:33', NULL, '0'),
(19, 'inday', 'QpvnXtij3pR6khZcSLnC1DtZ+lk1YTdlNDVhNThh', '5a7e45a58a', 'devs', '2015-12-15 16:11:24', NULL, '0'),
(20, 'razer', 'fXDdH2Pxd9CJOSd0u5H7mGXxUNY0NWUyNjhjNzA4', '45e268c708', 'toshiba', '2015-12-15 16:12:52', NULL, '0'),
(21, 'james', 'james', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00', ''),
(27, 'benja', 'e/yc1r2SBgfkV9NGV0iaVkhDfCE4YTg4YTc2NDk3', '8a88a76497', 'acnologia', '2016-10-07 17:13:45', NULL, '0'),
(28, 'jolie', 'IAeqYqveFxNJcPJLPmNGQVu6tiUyN2Q3OTkwOTUz', '27d7990953', 'nica', '2016-10-08 14:44:49', NULL, '0'),
(29, 'ycri', 'b944lQSEitiP/1ABjICSWBQ6ofc1ODAwMGI4ZjVk', '58000b8f5d', 'nica', '2016-10-08 14:52:33', NULL, '0'),
(30, 'luhan', 'xY2TulKVV0HIodjfbrBdHeD99HlhMDkzMDgxZDUy', 'a093081d52', 'acnologia', '2016-10-08 16:20:51', NULL, '0'),
(31, 'Bench', 'TAQmWbxRRWWxIbwQfp/WCkL7rvM1ODBjYjYwYmQ4', '580cb60bd8', 'david', '2016-10-08 23:22:43', NULL, '0');

-- --------------------------------------------------------

--
-- Table structure for table `chat`
--

CREATE TABLE `chat` (
  `message_id` int(11) NOT NULL,
  `sender` varchar(255) NOT NULL,
  `receiver` varchar(255) NOT NULL,
  `message` varchar(255) NOT NULL,
  `isRead` int(2) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `id` int(11) NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  `sender` varchar(255) DEFAULT NULL,
  `receiver` varchar(255) DEFAULT NULL,
  `seen` tinyint(1) NOT NULL DEFAULT '0',
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notifications`
--

INSERT INTO `notifications` (`id`, `message`, `sender`, `receiver`, `seen`, `date`) VALUES
(1, 'Currently changing Diaper', 'angie', 'cy', 1, '2016-10-07 16:46:35'),
(2, 'Done changing Diaper', 'angie', 'cy', 1, '2016-10-07 16:46:38'),
(3, 'feed the baby', 'cy', 'angie', 0, '2016-10-07 17:04:26'),
(4, 'feed the baby', 'cy', 'bench', 0, '2016-10-07 17:04:26'),
(5, 'feed the baby', 'cy', 'angie', 0, '2016-10-07 17:04:42'),
(6, 'feed the baby', 'cy', 'bench', 0, '2016-10-07 17:04:42'),
(7, 'Updated feed the baby to feed the baby', 'cy', 'angie', 0, '2016-10-07 17:04:50'),
(8, 'play with baby', 'cy', 'angie', 0, '2016-10-07 17:05:03'),
(9, 'play with baby', 'cy', 'bench', 0, '2016-10-07 17:05:03'),
(10, 'kill the baby', 'cy', 'angie', 0, '2016-10-07 17:05:27'),
(11, 'kill the baby', 'cy', 'bench', 0, '2016-10-07 17:05:27'),
(12, 'Deleted feed the baby', 'cy', 'angie', 0, '2016-10-07 17:05:50'),
(13, 'Deleted play with baby', 'cy', 'angie', 0, '2016-10-07 17:05:52'),
(14, 'Deleted feed the baby', 'cy', 'angie', 0, '2016-10-07 17:05:52'),
(15, 'Deleted kill the baby', 'cy', 'angie', 0, '2016-10-07 17:05:53'),
(16, 'feed the baby', 'cy', 'angie', 0, '2016-10-07 17:06:29'),
(17, 'feed the baby', 'cy', 'bench', 0, '2016-10-07 17:06:29'),
(18, 'feed angie', 'acnologia', 'benja', 1, '2016-10-07 17:15:30'),
(19, 'Updated feed angie to feed angie rn', 'acnologia', 'benja', 1, '2016-10-07 17:15:47'),
(20, 'Updated feed angie rn to feed angie rn', 'acnologia', 'benja', 1, '2016-10-07 17:15:47'),
(21, 'Finished feed angie rn', 'benja', 'acnologia', 1, '2016-10-07 17:16:40'),
(22, 'wala ni sud', 'ian', 'Tester', 0, '2016-10-07 17:23:24'),
(23, 'feed john', 'nica', 'jolie', 1, '2016-10-08 14:47:39'),
(24, 'ghjkll', 'nica', 'jolie', 1, '2016-10-08 14:49:10'),
(25, 'Finished ghjkll', 'jolie', 'nica', 1, '2016-10-08 14:50:00'),
(26, 'Done changing Diaper', 'jolie', 'nica', 1, '2016-10-08 14:50:13'),
(27, ' Baby Wake-up', 'ycri', 'nica', 1, '2016-10-08 14:53:45'),
(28, 'Deleted feed john', 'nica', 'jolie', 1, '2016-10-08 14:54:27'),
(29, 'Playing', 'benja', 'acnologia', 1, '2016-10-08 15:08:33'),
(30, 'FeedbackMana maam', 'luhan', 'acnologia', 1, '2016-10-08 16:22:47'),
(31, 'Baby Sleeping', 'benja', 'acnologia', 1, '2016-10-08 16:52:11'),
(32, 'Happy', 'benja', 'acnologia', 1, '2016-10-08 16:52:33'),
(33, 'Currently changing Diaper', 'benja', 'acnologia', 1, '2016-10-08 16:52:50'),
(34, 'Crying', 'benja', 'acnologia', 1, '2016-10-08 17:17:29'),
(35, 'dgajwh', 'acnologia', 'benja', 1, '2016-10-08 17:37:12'),
(36, 'dgajwh', 'acnologia', 'luhan', 0, '2016-10-08 17:37:12'),
(37, 'Updated dgajwh to zzzz', 'acnologia', 'benja', 1, '2016-10-08 17:37:42'),
(38, 'sgagana', 'acnologia', 'benja', 1, '2016-10-08 17:37:51'),
(39, 'sgagana', 'acnologia', 'luhan', 0, '2016-10-08 17:37:51'),
(40, 'Updated sgagana to xxxx', 'acnologia', 'benja', 1, '2016-10-08 17:38:08'),
(41, 'Sad', 'jolie', 'nica', 1, '2016-10-08 17:50:45'),
(42, 'Playing', 'jolie', 'nica', 1, '2016-10-08 17:50:48'),
(43, ' Baby Wake-up', 'jolie', 'nica', 1, '2016-10-08 17:50:49'),
(44, 'wala ni sud', 'ian', 'Tester', 0, '2016-10-08 23:21:47'),
(45, 'feed the baby', 'david', 'Bench', 0, '2016-10-08 23:23:35'),
(46, 'Updated feed th baby to eat the baby', 'david', 'Bench', 0, '2016-10-08 23:23:45'),
(47, 'Playing', 'benja', 'acnologia', 1, '2016-10-08 23:30:22'),
(48, 'Sad', 'benja', 'acnologia', 1, '2016-10-08 23:30:25'),
(49, 'Playing', 'benja', 'acnologia', 1, '2016-10-09 06:15:22'),
(50, 'Playing', 'benja', 'acnologia', 1, '2016-10-09 06:15:27'),
(51, 'Sad', 'benja', 'acnologia', 1, '2016-10-09 06:15:29'),
(52, 'Currently changing Diaper', 'benja', 'acnologia', 1, '2016-10-09 06:15:31'),
(53, ' Baby Wake-up', 'benja', 'acnologia', 1, '2016-10-09 06:15:36'),
(54, 'Playing', 'benja', 'acnologia', 1, '2016-10-09 06:19:03'),
(55, 'Finished zzzz', 'benja', 'acnologia', 1, '2016-10-09 06:19:13'),
(56, 'Currently changing Diaper', 'benja', 'acnologia', 1, '2016-10-09 06:33:28'),
(57, ' Baby Wake-up', 'benja', 'acnologia', 1, '2016-10-09 06:33:30'),
(58, 'Sad', 'benja', 'acnologia', 1, '2016-10-09 06:34:43'),
(59, 'Done changing Diaper', 'benja', 'acnologia', 1, '2016-10-09 06:34:44'),
(60, 'Done changing Diaper', 'benja', 'acnologia', 1, '2016-10-09 06:34:59'),
(61, 'Deleted xxxx', 'acnologia', 'benja', 1, '2016-10-09 06:37:18'),
(62, 'Sad', 'benja', 'acnologia', 1, '2016-10-09 06:39:15'),
(63, 'Currently changing Diaper', 'benja', 'acnologia', 1, '2016-10-09 06:39:17'),
(64, ' Baby Wake-up', 'benja', 'acnologia', 1, '2016-10-09 06:39:20'),
(65, 'Done changing Diaper', 'benja', 'acnologia', 1, '2016-10-09 06:39:24'),
(66, 'Sad', 'benja', 'acnologia', 1, '2016-10-09 06:54:40'),
(67, 'Crying', 'benja', 'acnologia', 1, '2016-10-09 06:54:42');

-- --------------------------------------------------------

--
-- Table structure for table `todolist`
--

CREATE TABLE `todolist` (
  `uid` int(11) NOT NULL,
  `task` varchar(255) NOT NULL,
  `startTime` varchar(255) NOT NULL,
  `endTime` varchar(255) NOT NULL,
  `created_for` varchar(255) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  `finished` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `todolist`
--

INSERT INTO `todolist` (`uid`, `task`, `startTime`, `endTime`, `created_for`, `created_by`, `created_at`, `updated_at`, `finished`) VALUES
(5, 'feed the baby', ' 7:30 AM ', ' 9:00 AM ', 'Alvin boy capaning', 'cy', '2016-10-07 17:06:29', NULL, 0),
(6, 'feed angie rn', '6:00 AM', '6:00 AM', 'Angie Mae Gomez', 'acnologia', '2016-10-07 17:15:30', NULL, 1),
(7, 'paimnon ug tambal', ' 7:30 AM ', ' 8:00 AM ', 'babylabs anako babykho', 'binggwapa', '2016-10-07 17:23:24', NULL, 0),
(9, 'ghjkll', ' 9:30 AM ', ' 10:00 AM ', 'john spi sun', 'nica', '2016-10-08 14:49:10', NULL, 1),
(10, 'zzzz', '6:00 AM', '6:00 AM', 'Angie Mae Gomez', 'acnologia', '2016-10-08 17:37:12', NULL, 1),
(12, 'eat the baby', '6:00 AM', '6:00 AM', 'darren hdjs dhs', 'david', '2016-10-08 23:21:47', NULL, 0),
(13, 'feed the baby', ' 7:00 AM ', ' 8:00 AM ', 'darren hdjs dhs', 'david', '2016-10-08 23:23:35', NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `uid` int(11) NOT NULL,
  `unique_id` varchar(23) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `encrypted_password` varchar(80) NOT NULL,
  `salt` varchar(10) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `first_time` varchar(255) NOT NULL,
  `type` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`uid`, `unique_id`, `username`, `email`, `encrypted_password`, `salt`, `created_at`, `updated_at`, `first_time`, `type`) VALUES
(34, '565ae0be2c5160.66726545', 'IanParent', 'ianosias@gmail.com', '0baS072/a0DFIonvjQkgCKpPH89jYjBhNzJiYmZm', 'cb0a72bbff', '2015-11-29 19:25:50', NULL, 'No', '1'),
(35, '566a9a85157171.25487669', 'devs', 'daves@sexy.com', 'DLC+toOaUZ2gNr0ggh0HgbrBdfJlYjZhOGQ2NzNm', 'eb6a8d673f', '2015-12-11 17:42:29', NULL, 'No', '1'),
(36, '566b55c2eab165.87759265', 'user', 'user@yahoo.com', '0UHVML8dU8xg+tj0uCsEMCT75xljZDUyMzNmYmE3', 'cd5233fba7', '2015-12-12 07:01:22', NULL, 'No', '1'),
(37, '566fcb7310ff06.57743080', 'toshiba', 'ivan@yahoo.com', 'rCH40i78LAPACozJKDTxiQsZ9kkzNmY5M2E2OGFl', '36f93a68ae', '2015-12-15 16:12:35', NULL, 'No', '1'),
(38, '57f7525e6496b3.66895623', 'darrenlouisp', 'darrenlouisp@gmail.com', 'XDzG6fmJ6D6IHo/UuADHqDdD00k5NDUxYTMxNDkx', '9451a31491', '2016-10-07 15:44:30', NULL, 'No', '1'),
(42, '57f7672cb94875.55399047', 'acnologia', 'ren@gmail.com', '+kkfunqqRxU222KzcMsGBRUcjDc0MWJjNWIzMGI2', '41bc5b30b6', '2016-10-07 17:13:16', NULL, 'No', '1'),
(43, '57f76910800f41.08041325', 'binggwapa', 'binggwapa@gmail.com', 'ZDO1n4qj4EPUTAIwG0OKklIBEIowOTE3NDFhZDc0', '091741ad74', '2016-10-07 17:21:20', NULL, 'Yes', '1'),
(44, '57f895b0c05a96.55859369', 'nica', 'benica@yahoo.com', 'DVeywR821kn7IFT6I8UURNjqKftkMzgxZjMyZWU5', 'd381f32ee9', '2016-10-08 14:44:00', NULL, 'No', '1'),
(45, '57f8a992bc9e13.16687857', 'angieG', 'angie@yahoo.com', 'QWV0IZaus9NhaQ9A2wz7wRxrHKExYWVjNDllNDk5', '1aec49e499', '2016-10-08 16:08:50', NULL, 'Yes', '1'),
(46, '57f90e69382301.48701426', 'david', 'd@yahoo.com', 'uRHngsEFny4+TElTCEPJCwopYpRkYjA0NzcxMzJi', 'db0477132b', '2016-10-08 23:19:05', NULL, 'No', '1'),
(47, '57f9716f6316b6.58647914', 'david1', 'd1@yahoo.com', 'gLKCymr1ZfyLYwhSu7A1O8eJbtc2NmZkNjE5ZDUz', '66fd619d53', '2016-10-09 06:21:35', NULL, 'Yes', '1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `baby`
--
ALTER TABLE `baby`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `babysitter`
--
ALTER TABLE `babysitter`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `chat`
--
ALTER TABLE `chat`
  ADD PRIMARY KEY (`message_id`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `todolist`
--
ALTER TABLE `todolist`
  ADD PRIMARY KEY (`uid`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`uid`),
  ADD UNIQUE KEY `unique_id` (`unique_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `baby`
--
ALTER TABLE `baby`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;
--
-- AUTO_INCREMENT for table `babysitter`
--
ALTER TABLE `babysitter`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;
--
-- AUTO_INCREMENT for table `chat`
--
ALTER TABLE `chat`
  MODIFY `message_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=68;
--
-- AUTO_INCREMENT for table `todolist`
--
ALTER TABLE `todolist`
  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
