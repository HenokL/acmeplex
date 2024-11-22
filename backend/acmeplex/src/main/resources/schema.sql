-- Drop the tables in reverse order of dependencies

-- Creates the database tables
CREATE DATABASE IF NOT EXISTS acmeplex;
USE acmeplex;

-- Drop tables if they exist to ensure a fresh start
DROP TABLE IF EXISTS Receipt;
DROP TABLE IF EXISTS Seat;
DROP TABLE IF EXISTS Ticket;
DROP TABLE IF EXISTS Payment;
DROP TABLE IF EXISTS Credit;
DROP TABLE IF EXISTS Showtime;
DROP TABLE IF EXISTS Movie;
DROP TABLE IF EXISTS RegisteredUser;

-- Creates the Movie table if it doesn't already exist
CREATE TABLE IF NOT EXISTS Movie (
    movieId INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    duration DOUBLE,
    genre VARCHAR(50)
);

-- Creates the Showtime table if it doesn't already exist
  
CREATE TABLE IF NOT EXISTS Showtime (
    showtimeId INT AUTO_INCREMENT PRIMARY KEY,
    movieId INT NOT NULL, 
    startTime VARCHAR(255) NOT NULL,
    endTime VARCHAR(255) NOT NULL, 
    showtimeDate Date NOT NULL, 
    FOREIGN KEY (movieId) REFERENCES Movie(movieId)
);

-- Creates the RegisteredUser table if it doesn't already exist
CREATE TABLE IF NOT EXISTS RegisteredUser (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);

-- Creates the Payment table if it doesn't already exist
CREATE TABLE IF NOT EXISTS Payment (
    paymentId INT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE,
    creditCardNumber BIGINT,
    creditCardName VARCHAR(255),
    creditCardCV INT,
    paymentDate DATE
);

-- Creates the Ticket table if it doesn't already exist
CREATE TABLE IF NOT EXISTS Ticket (
    ticketId INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255),
    price DOUBLE,
    status VARCHAR(50),
    purchaseDate DATE,
    movieId INT,
    showtimeId INT,
    FOREIGN KEY (movieId) REFERENCES Movie(movieId),
    FOREIGN KEY (showtimeId) REFERENCES Showtime(showtimeId)
);

-- Creates the Seat table if it doesn't already exist
CREATE TABLE IF NOT EXISTS Seat (
    seatId INT AUTO_INCREMENT PRIMARY KEY,
    seatNumber INT NOT NULL,
    seatRow INT NOT NULL,
    showtimeId INT NOT NULL,
    ticketId INT NOT NULL,
    FOREIGN KEY (showtimeId) REFERENCES Showtime(showtimeId),
    FOREIGN KEY (ticketId) REFERENCES Ticket(ticketId)
);

 
-- Creates the Receipt table if it doesn't already exist
CREATE TABLE IF NOT EXISTS Receipt (
    receiptId INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255),
    paymentId INT,
    ticketId INT,
    FOREIGN KEY (paymentId) REFERENCES Payment(paymentId),
    FOREIGN KEY (ticketId) REFERENCES Ticket(ticketId)
);



-- Creates the credit table if it doesn't already exist
CREATE TABLE IF NOT EXISTS Credit (
    creditID INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    creditAmount DOUBLE DEFAULT 0,
    issuedDate Date NOT NULL
);

INSERT INTO Movie (title, duration, genre) VALUES
('Inception', 2.5, 'Sci-Fi'),
('The Dark Knight', 2.75, 'Action'),
('Interstellar', 2.9, 'Sci-Fi'),
('Titanic', 3.2, 'Romance'),
('Avengers: Endgame', 3.0, 'Action'),
('Toy Story', 1.5, 'Animation'),
('The Lion King', 1.8, 'Animation'),
('Frozen', 1.7, 'Animation'),
('Joker', 2.0, 'Drama'),
('Parasite', 2.1, 'Thriller'),
('Shutter Island', 2.2, 'Thriller'),
('Spider-Man: Homecoming', 2.3, 'Action'),
('Doctor Strange', 2.4, 'Fantasy'),
('Guardians of the Galaxy', 2.1, 'Sci-Fi'),
('The Matrix', 2.5, 'Sci-Fi'),
('The Godfather', 3.0, 'Crime'),
('Pulp Fiction', 2.6, 'Crime'),
('Forrest Gump', 2.2, 'Drama'),
('Gladiator', 2.5, 'Action'),
('Braveheart', 2.8, 'History'),
('Dune', 2.6, 'Sci-Fi'),
('The Avengers', 2.8, 'Action'),
('The Grand Budapest Hotel', 1.9, 'Comedy'),
('The Revenant', 2.5, 'Drama'),
('Mad Max: Fury Road', 2.0, 'Action'),
('The Shining', 2.3, 'Horror'),
('The Social Network', 2.1, 'Drama'),
('The Matrix Reloaded', 2.5, 'Sci-Fi'),
('La La Land', 2.0, 'Musical'),
('Inglourious Basterds', 2.3, 'War');

-- Insert data into the Showtime table
INSERT INTO Showtime (movieId, startTime, endTime, showtimeDate) VALUES
(1, '14:00:00', '16:30:00', '2024-11-20'),
(5, '17:00:00', '19:45:00', '2024-11-21'),
(10, '20:00:00', '22:30:00', '2024-11-22'),
(12, '09:00:00', '11:30:00', '2024-11-23'),
(15, '12:00:00', '14:30:00', '2024-11-24'),
(8, '16:00:00', '18:00:00', '2024-11-25'),
(14, '19:00:00', '21:30:00', '2024-11-26'),
(18, '21:00:00', '23:30:00', '2024-11-27'),
(21, '14:00:00', '16:30:00', '2025-05-20'),
(22, '17:00:00', '19:45:00', '2025-05-21'),
(23, '20:00:00', '22:00:00', '2025-05-22'),
(24, '09:00:00', '11:30:00', '2025-05-23'),
(25, '12:00:00', '14:00:00', '2025-05-24'),
(26, '16:00:00', '18:00:00', '2025-05-25'),
(27, '19:00:00', '21:30:00', '2025-05-26'),
(28, '21:00:00', '23:30:00', '2025-05-27'),
(29, '14:00:00', '16:30:00', '2025-06-01'),
(30, '17:00:00', '19:30:00', '2025-06-02');

-- Insert data into the RegisteredUser table
INSERT INTO RegisteredUser (name, email, password) VALUES
('John Doe', 'john.doe@example.com', 'password123'),
('Jane Smith', 'jane.smith@example.com', 'securePass!'),
('Emily Johnson', 'emily.johnson@example.com', 'myPassword2024'),
('Michael Brown', 'michael.brown@example.com', '12345abcde'),
('Sarah Davis', 'sarah.davis@example.com', 'password!@#'),
('David Wilson', 'david.wilson@example.com', 'P@ssw0rd123'),
('Olivia Moore', 'olivia.moore@example.com', 'password456'),
('James Taylor', 'james.taylor@example.com', '123abc456'),
('Sophia Lee', 'sophia.lee@example.com', 'securepassword789'),
('Lucas Harris', 'lucas.harris@example.com', 'qwertyuiop');

-- Insert data into the Payment table
INSERT INTO Payment (amount, creditCardNumber, creditCardName, creditCardCV, paymentDate) VALUES
(100.50, 1234567890123456, 'John Doe', 123, '2024-11-16'),
(250.75, 9876543210987654, 'Jane Smith', 456, '2024-11-15'),
(150.00, 1122334455667788, 'Emily Johnson', 789, '2024-11-14'),
(320.25, 2233445566778899, 'Michael Brown', 101, '2024-11-13'),
(200.50, 3344556677889900, 'Sarah Davis', 202, '2024-11-12');

-- Insert data into the Ticket table
INSERT INTO Ticket (email, price, status, purchaseDate, movieId, showtimeId) VALUES
('john.doe@example.com', 10.0, 'Booked', '2024-11-20', 1, 1),
('jane.smith@example.com', 15.0, 'Booked', '2024-11-21', 5, 2),
('emily.johnson@example.com', 12.0, 'Booked', '2024-11-22', 10, 3),
('michael.brown@example.com', 18.0, 'Booked', '2024-11-23', 12, 4),
('sarah.davis@example.com', 14.0, 'Booked', '2024-11-24', 15, 5),
('sarah.davis@example.com', 100.0, 'Booked', '2024-11-24', 30, 18);


-- Insert data into the Seat table
INSERT INTO Seat (seatNumber, seatRow, showtimeId, ticketId) VALUES
(1, 1, 1, 1),
(2, 1, 2, 2),
(3, 1, 3, 3),
(4, 1, 4, 4),
(5, 1, 5, 5),
(1, 1, 18, 6),
(1, 2, 18, 6),
(1, 3, 18, 6),
(1, 4, 18, 6),
(1, 5, 18, 6);

-- Insert data into the Receipt table
INSERT INTO Receipt (email, paymentId, ticketId) VALUES
('john.doe@example.com', 1, 1),
('jane.smith@example.com', 2, 2),
('emily.johnson@example.com', 3, 3),
('michael.brown@example.com', 4, 4),
('sarah.davis@example.com', 5, 5);

-- Insert data into the Credit table
INSERT INTO Credit (email, creditAmount, issuedDate) VALUES
('john.doe@example.com', 100.0, '2024-11-10'),
('jane.smith@example.com', 200.0, '2024-11-11'),
('emily.johnson@example.com', 150.0, '2024-11-12'),
('michael.brown@example.com', 300.0, '2024-11-13'),
('sarah.davis@example.com', 250.0, '2024-11-14');