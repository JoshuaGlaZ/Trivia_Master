-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 27, 2024 at 09:37 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jkr_master`
--

-- --------------------------------------------------------

--
-- Table structure for table `players`
--

CREATE TABLE `players` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `score` int(11) NOT NULL,
  `difficulty` enum('Easy','Medium','Hard') NOT NULL,
  `type` enum('History','Geography','Math') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `players`
--

INSERT INTO `players` (`id`, `name`, `score`, `difficulty`, `type`) VALUES
(1, 'Joshua', 6000, 'Medium', 'History'),
(2, 'Daniel', 7000, 'Hard', 'Math');

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `id` int(11) NOT NULL,
  `question` text NOT NULL,
  `answer_a` varchar(50) NOT NULL,
  `answer_b` varchar(50) NOT NULL,
  `answer_c` varchar(50) NOT NULL,
  `answer_d` varchar(50) NOT NULL,
  `correct_answer` varchar(50) NOT NULL,
  `difficulty` enum('Easy','Medium','Hard') NOT NULL,
  `type` enum('History','Geography','Math') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`id`, `question`, `answer_a`, `answer_b`, `answer_c`, `answer_d`, `correct_answer`, `difficulty`, `type`) VALUES
(1, 'What is the capital of Indonesia?', 'Banten', 'Jakarta', 'Surabaya', 'Bandung', 'Jakarta', 'Easy', 'Geography'),
(2, 'What is the capital of France?', 'London', 'Rome', 'Paris', 'Berlin', 'Paris', 'Easy', 'Geography'),
(3, 'What is the currency used in Japan?', 'Yuan', 'Yen', 'Won', 'Rupiah', 'Yen', 'Easy', 'Geography'),
(4, 'What is the largest ocean on Earth?', 'Arctic Ocean', 'Atlantic Ocean', 'Indian Ocean', 'Pacific Ocean', 'Pacific Ocean', 'Easy', 'Geography'),
(5, 'Which continent is home to the Great Wall of China?', 'Europe', 'Africa', 'Asia', 'South America', 'Asia', 'Easy', 'Geography'),
(6, 'Who was the fifth president of Indonesia?', 'Susilo Bambang Yudhoyono', 'Joko Widodo', 'Megawati Sukarnoputri', 'Abdurrahman Wahid', 'Megawati Sukarnoputri', 'Easy', 'History'),
(7, 'Who was the first president of Indonesia?', 'Sukarno', 'Suharto', 'B. J. Habibie', 'Abdurrahman Wahid', 'Sukarno', 'Easy', 'History'),
(8, 'When did Indonesia declare independence?', '1932', '1944', '1945', '1954', '1945', 'Easy', 'History'),
(9, 'Which nation invaded Indonesia?', 'Malaya', 'Turkey', 'Dutch', 'Thailand', 'Dutch', 'Easy', 'History'),
(10, 'When is the Indonesian Independence Day?', '20th May', '17th August', '1st July', '30th October', '17th August', 'Easy', 'History'),
(11, 'What is the sum of 15 and 7?', '9', '10', '15', '18', '18', 'Easy', 'Math'),
(12, 'What is the sum of 5 and 17?', '16', '22', '24', '35', '24', 'Easy', 'Math'),
(13, 'What is the multiplication of -1 and 8?', '7', '8', '-8', '-18', '-8', 'Easy', 'Math'),
(14, 'What is the multiplication of 12 and 3?', '12', '15', '24', '36', '36', 'Easy', 'Math'),
(15, 'What is value of pi?', '3.14', '22/7', '3.14159', 'π', 'π', 'Easy', 'Math'),
(16, 'What is the official language of Brazil?', 'Spanish', 'Portuguese', 'French', 'English', 'Portuguese', 'Medium', 'Geography'),
(17, 'What is the capital of Australia?', 'Sydney', 'Melbourne', 'Canberra', 'Perth', 'Canberra', 'Medium', 'Geography'),
(18, 'What is the name of the largest desert in the world (by area)?', 'Sahara Desert', 'Gobi Desert', 'Arabian Desert', 'Australian Desert', 'Sahara Desert', 'Medium', 'Geography'),
(19, 'What is the neighboring country of Indonesia?', 'Thailand', 'Bali', 'Singapore', 'New Zealand', 'Singapore', 'Medium', 'Geography'),
(20, 'What is the currency of Vietnam?', 'Dong', 'Rupee', 'Kip', 'Kyat', 'Dong', 'Medium', 'Geography'),
(21, 'During which war was the atomic bomb first used?', 'World War I', 'World War II', 'Korean War', 'Vietnam War', 'World War II', 'Medium', 'History'),
(22, 'Who is considered the father of modern physics?', 'Isaac Newton', 'Albert Einstein', 'Galileo Galilei', 'Stephen Hawking', 'Albert Einstein', 'Medium', 'History'),
(23, 'The Berlin Wall, a symbol of the Cold War, divided which two cities?', 'Moscow and Leningrad', 'London and Paris', 'East Berlin & West Berlin', 'North Korean & South Korea', 'East Berlin & West Berlin', 'Medium', 'History'),
(24, 'Who was the leader of the Soviet Union during World War II?', 'Vladimir Putin', 'Nikita Khrushchev', 'Joseph Stalin', 'Mikhail Gorbachev', 'Joseph Stalin', 'Medium', 'History'),
(25, 'What is the name of the first artificial Earth satellite launched by the Soviet Union?', 'Sputnik 1', 'Explorer 1', 'Luna 1', 'Apollo 1', 'Sputnik 1', 'Medium', 'History'),
(26, 'A train travels 200 km in 4 hours. What is the average speed of the train in km/h?', '40 km/h', '50 km/h', '55 km/h', '60 km/h', '50 km/h', 'Medium', 'Math'),
(27, 'A simple equation is given: 3x + 5 = 13. What is the value of x?', '1', '2', '4', '6', '2', 'Medium', 'Math'),
(28, 'What is the value of 6!?', '6', '36', '360', '720', '720', 'Medium', 'Math'),
(29, 'What is the value of - | 18 - 20 |?', '-2', '2', '4', '-4', '-2', 'Medium', 'Math'),
(30, 'What is NOT a prime number?', '2', '3', '4', '5', '4', 'Medium', 'Math'),
(31, 'Which of the following countries borders both the Atlantic Ocean and the Pacific Ocean?', 'Brazil', 'Canada', 'China', 'Australia', 'Canada', 'Hard', 'Geography'),
(32, 'The Great Barrier Reef, the world\'s largest coral reef system, is located off the coast of which country?', 'Australia', 'Indonesia', 'Japan', 'India', 'Australia', 'Hard', 'Geography'),
(33, 'Mount Kilimanjaro is the highest mountain in Africa. Which country is home to Mount Kilimanjaro?', 'Egypt', 'Tanzania', 'Morocco', 'Uganda', 'Tanzania', 'Hard', 'Geography'),
(34, 'The capital of France is Paris. On which major river is Paris located?', 'Rhine River', 'Thames River', 'Seine River', 'Danube River', 'Seine River', 'Hard', 'Geography'),
(35, 'What is the currency of Hungary?', 'Forint', 'Shilling', 'Krone', 'Euro', 'Forint', 'Hard', 'Geography'),
(36, 'Invented in 1957, what was the first high-level programming language?', 'C++', 'Java', 'COBOL', 'FORTRAN', 'FORTRAN', 'Hard', 'History'),
(37, 'What is the name of the woman who is considered the \"first computer programmer\" for her work on Charles Babbage\'s Analytical Engine?', 'Grace Hopper', 'Margaret Hamilton', 'Ada Lovelace', 'Radia Perlman', 'Ada Lovelace', 'Hard', 'History'),
(38, 'C is a general-purpose programming language developed by whom?', 'Bjarne Stroustrup', 'Guido van Rossum', 'Dennis Ritchie', 'James Gosling', 'Dennis Ritchie', 'Hard', 'History'),
(39, 'What was the first object-oriented programming language?', 'COBOL', 'Simula 67', 'Visual Basic', 'Pascal', 'Simula 67', 'Hard', 'History'),
(40, 'What programming language was developed by Google and is known for its speed and efficiency?', 'Go', 'Delphi', 'Swift', 'Kotlin', 'Go', 'Hard', 'History'),
(41, 'Solve the system of equations: 2x + y = 5, x - 3y = -1', 'x = 1, y = 3', 'x = 2, y = 1', 'x = -1, y = 2', 'x = 3, y = -1', 'x = -1, y = 2', 'Hard', 'Math'),
(42, 'Find the sine (sin) of a 30-degree angle', '0.5', '0.707', '1', '1.5', '0.5', 'Hard', 'Math'),
(43, 'A circle has a circumference of 12π cm. What is the radius of the circle?', '2cm', '3cm', '6cm', '12cm', '6cm', 'Hard', 'Math'),
(44, 'A sequence is given: 2, 5, 8, 11, ... What is the 6th term in the sequence?', '15', '16', '17', '18', '17', 'Hard', 'Math'),
(45, 'What is the derivative of the function f(x) = x^2?', '2x', 'x', '1', '2', '2x', 'Hard', 'Math');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `players`
--
ALTER TABLE `players`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `players`
--
ALTER TABLE `players`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
