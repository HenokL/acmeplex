/**
 * Tickets page - Handles movie ticket booking with seat selection
 * Comment added by Henok L
 */

import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { 
  Container,
  Paper,
  Typography,
  FormControl,
  Select,
  MenuItem,
  Box,
  Grid,
  Button
} from '@mui/material';
import { 
  LocalMovies,
  CalendarMonth,
  TheaterComedy,
  WeekendOutlined 
} from '@mui/icons-material';
import Footer from '../../components/Footer/Footer';
import './Tickets.css';

const Tickets = () => {
    const navigate = useNavigate();
    const [selectedMovie, setSelectedMovie] = useState('');
    const [selectedDate, setSelectedDate] = useState('');
    const [selectedTheater, setSelectedTheater] = useState('');
    const [selectedTime, setSelectedTime] = useState(''); 
    const [selectedSeats, setSelectedSeats] = useState([]);
  
  // I am using some Mock data for selections here and this will be replaced with actual data from the backend
  // Comment added by Henok L
  const movies = ["Inception", "The Dark Knight", "Interstellar"];
  const dates = ["2024-11-20", "2024-11-21", "2024-11-22"];
  const theaters = ["Theater 1", "Theater 2", "Theater 3"];

  const times = [
    "10:00 AM",
    "1:30 PM", 
    "4:00 PM",
    "7:30 PM",
    "10:00 PM"
  ];
  
  
  // NOTE For Yousef: Here I just put some Mock occupied seats
  const occupiedSeats = ['A2', 'B4', 'C3', 'D1'];
  const ticketPrice = 15;

  const rows = ['A', 'B', 'C', 'D', 'E'];
  const seatsPerRow = 8;

  // Handles seat selection and deselection logic
  const handleSeatSelect = (seatId) => {
    if (occupiedSeats.includes(seatId)) return;
    
    setSelectedSeats(prev => 
      prev.includes(seatId) 
        ? prev.filter(id => id !== seatId)
        : [...prev, seatId]
    );
  };

  // Determines seat color based on its status
  const getSeatColor = (seatId) => {
    if (occupiedSeats.includes(seatId)) return 'occupied';
    if (selectedSeats.includes(seatId)) return 'selected';
    return 'available';
  };

  const canPurchase = selectedMovie && selectedDate && selectedTime && selectedTheater && selectedSeats.length > 0;

  // Handles purchase action and navigation to payment
  const handlePurchase = () => {
    const ticketData = {
      movie: selectedMovie,
      date: selectedDate,
      time: selectedTime,
      theater: selectedTheater,
      seats: selectedSeats,
      total: selectedSeats.length * ticketPrice
    };
    navigate('/payment', { state: { ticketData } });
  };

  return (
    <div className="tickets-page">
      <Container maxWidth="md" sx={{ my: 4 }}>
        <Typography variant="h4" gutterBottom className="tickets-title">
          Get your Tickets
        </Typography>

        <Paper elevation={3} className="booking-form">
          <Box className="selection-container">
            <LocalMovies className="selection-icon" />
            <FormControl fullWidth>
              <Typography variant="subtitle1">Select Movie</Typography>
              <Select
                value={selectedMovie}
                onChange={(e) => setSelectedMovie(e.target.value)}
                displayEmpty
              >
                <MenuItem value="" disabled>Choose a movie</MenuItem>
                {movies.map((movie) => (
                  <MenuItem key={movie} value={movie}>{movie}</MenuItem>
                ))}
              </Select>
            </FormControl>
          </Box>

          <Box className="selection-container">
            <CalendarMonth className="selection-icon" />
            <FormControl fullWidth>
              <Typography variant="subtitle1">Select Date</Typography>
              <Select
                value={selectedDate}
                onChange={(e) => setSelectedDate(e.target.value)}
                displayEmpty
              >
                <MenuItem value="" disabled>Choose a date</MenuItem>
                {dates.map((date) => (
                  <MenuItem key={date} value={date}>{date}</MenuItem>
                ))}
              </Select>
            </FormControl>
          </Box>

          <Box className="selection-container">
            <CalendarMonth className="selection-icon" />
            <FormControl fullWidth>
                <Typography variant="subtitle1">Select Time</Typography>
                <Select
                value={selectedTime}
                onChange={(e) => setSelectedTime(e.target.value)}
                displayEmpty
                >
                <MenuItem value="" disabled>Choose a time</MenuItem>
                {times.map((time) => (
                    <MenuItem key={time} value={time}>{time}</MenuItem>
                ))}
                </Select>
            </FormControl>
            </Box>

          <Box className="selection-container">
            <TheaterComedy className="selection-icon" />
            <FormControl fullWidth>
              <Typography variant="subtitle1">Select Theater</Typography>
              <Select
                value={selectedTheater}
                onChange={(e) => setSelectedTheater(e.target.value)}
                displayEmpty
              >
                <MenuItem value="" disabled>Choose a theater</MenuItem>
                {theaters.map((theater) => (
                  <MenuItem key={theater} value={theater}>{theater}</MenuItem>
                ))}
              </Select>
            </FormControl>
          </Box>

          {selectedMovie && selectedDate && selectedTheater && (
            <Box className="seats-section">
              <Typography variant="h6" gutterBottom align="center">
                Select Your Seats
              </Typography>
              
              <Box className="screen">
                <Typography variant="body2" align="center">Screen</Typography>
              </Box>

              <Box className="seats-container">
                {rows.map((row) => (
                  <Grid container key={row} justifyContent="center" spacing={1}>
                    {[...Array(seatsPerRow)].map((_, index) => {
                      const seatId = `${row}${index + 1}`;
                      return (
                        <Grid item key={seatId}>
                          <Box
                            className={`seat ${getSeatColor(seatId)}`}
                            onClick={() => handleSeatSelect(seatId)}
                          >
                            <WeekendOutlined />
                            <Typography variant="caption">{seatId}</Typography>
                          </Box>
                        </Grid>
                      );
                    })}
                  </Grid>
                ))}
              </Box>

              <Box className="seat-legend">
                <Box className="legend-item">
                  <Box className="seat available"><WeekendOutlined /></Box>
                  <Typography variant="caption">Available</Typography>
                </Box>
                <Box className="legend-item">
                  <Box className="seat selected"><WeekendOutlined /></Box>
                  <Typography variant="caption">Selected</Typography>
                </Box>
                <Box className="legend-item">
                  <Box className="seat occupied"><WeekendOutlined /></Box>
                  <Typography variant="caption">Occupied</Typography>
                </Box>
              </Box>

              <Box className="booking-summary">
                <Typography variant="subtitle1">
                  Selected Seats: {selectedSeats.join(', ')}
                </Typography>
                <Typography variant="subtitle1">
                  Total: ${selectedSeats.length * ticketPrice}
                </Typography>
                <Button
                  variant="contained"
                  color="primary"
                  disabled={!canPurchase}
                  onClick={handlePurchase}
                  className="purchase-button"
                >
                  Get Tickets
                </Button>
              </Box>
            </Box>
          )}
        </Paper>
      </Container>
      <Footer />
    </div>
  );
};

export default Tickets;

