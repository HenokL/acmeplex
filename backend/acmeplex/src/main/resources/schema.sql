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


-- Insertion
-- Script to insert into the Database tables
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
('Braveheart', 2.8, 'History');

-- Insert data into the Showtime table
INSERT INTO Showtime (movieId, startTime, endTime, showtimeDate) VALUES
(1, '14:00:00', '16:30:00', '2024-11-20'),
(5, '17:00:00', '19:45:00', '2024-11-21'),
(10, '20:00:00', '22:30:00', '2024-11-22'),
(12, '09:00:00', '11:30:00', '2024-11-23'),
(15, '12:00:00', '14:30:00', '2024-11-24'),
(8, '16:00:00', '18:00:00', '2024-11-25'),
(14, '19:00:00', '21:30:00', '2024-11-26'),
(18, '21:00:00', '23:30:00', '2024-11-27');

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
(320.25, 2233445566778899, 'Michael Brown', 321, '2024-11-13'),
(80.60, 3344556677889900, 'Sarah Davis', 654, '2024-11-12'),
(200.40, 4455667788990011, 'David Wilson', 987, '2024-11-11'),
(180.30, 5566778899001122, 'Olivia Moore', 432, '2024-11-10'),
(120.90, 6677889900112233, 'James Taylor', 876, '2024-11-09'),
(210.80, 7788990011223344, 'Sophia Lee', 543, '2024-11-08'),
(99.99, 8899001122334455, 'Lucas Harris', 210, '2024-11-07');

-- Insert data into the Credit table
INSERT INTO Credit (email, creditAmount, issuedDate) VALUES
('john.doe@example.com', 50.00, '2024-11-01'),
('jane.smith@example.com', 120.00, '2024-11-02'),
('emily.johnson@example.com', 200.00, '2024-11-03'),
('michael.brown@example.com', 150.00, '2024-11-04'),
('sarah.davis@example.com', 80.00, '2024-11-05'),
('david.wilson@example.com', 300.00, '2024-11-06'),
('olivia.moore@example.com', 250.00, '2024-11-07'),
('james.taylor@example.com', 180.00, '2024-11-08'),
('sophia.lee@example.com', 220.00, '2024-11-09'),
('lucas.harris@example.com', 130.00, '2024-11-10');

-- Insert data into the Ticket table (including seatId)
INSERT INTO Ticket (email, price, status, purchaseDate, movieId, showtimeId) VALUES
('john.doe@example.com', 15.50, 'Booked', '2024-11-16', 1, 3),
('alice.jones@example.com', 18.00, 'Booked', '2024-11-16', 2, 3),
('bob.smith@example.com', 20.00, 'Cancelled', '2024-11-17', 1, 4),
('charlie.brown@example.com', 12.00, 'Booked', '2024-11-17', 3, 5),
('david.white@example.com', 16.50, 'Booked', '2024-11-18', 2, 4),
('eva.green@example.com', 14.00, 'Booked', '2024-11-18', 3, 5),
('frank.harris@example.com', 17.00, 'Booked', '2024-11-19', 1, 6),
('grace.miller@example.com', 19.00, 'Booked', '2024-11-19', 2, 6),
('lucas.harris@example.com', 22.00, 'Booked', '2024-11-20', 3, 7),
('emily.johnson@example.com', 20.00, 'Booked', '2024-11-21', 4, 7);

-- Insert data into the Seat table
INSERT INTO Seat (seatNumber, seatRow, showtimeId, ticketId) VALUES
(1, 1, 3, 1),  
(2, 1, 3, 2),
(3, 2, 3, 2), 
(4, 2, 3, 2),
(5, 3, 3, 1), 
(6, 3, 3, 3), 
(7, 4, 3, 1), 
(8, 4, 3, 2), 
(9, 5, 3, 2),  
(10, 5, 3, 1), 
(11, 1, 4, 3), 
(12, 1, 4, 4), 
(13, 2, 4, 4), 
(14, 2, 4, 4),  
(15, 3, 4, 5),  
(16, 3, 4, 5),  
(17, 4, 4, 5);