-- Creates the database tables

CREATE DATABASE IF NOT EXISTS acmeplex;
USE acmeplex;

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

-- Creates the Seat table if it doesn't already exist
CREATE TABLE IF NOT EXISTS Seat (
    seatId INT AUTO_INCREMENT PRIMARY KEY,
    seatNumber INT NOT NULL,
    seatRow INT NOT NULL,
    showtimeId INT NOT NULL,
    FOREIGN KEY (showtimeId REFERENCES Showtime(showtimeId))
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
    paymentDate DATE,
);

-- Creates the Ticket table if it doesn't already exist
CREATE TABLE IF NOT EXISTS Ticket (
    ticketId INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255),
    price DOUBLE,
    status VARCHAR(50),
    purchaseDate DATE,
    movieId INT,
    seatId INT,
    showtimeId INT,
    FOREIGN KEY (movieId) REFERENCES Movie(movieId),
    FOREIGN KEY (seatId) REFERENCES Seat(seatId),
    FOREIGN KEY (showtimeId) REFERENCES Showtime(showtimeId)
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
