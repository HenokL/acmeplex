/**
 * Payment page - Handles payment processing and displays order summary
 * Comment added by Henok L
 */

import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

// Here I am importing a components from Material UI
import {
  Container,
  Paper,
  Typography,
  TextField,
  Button,
  Box,
  Grid,
  Divider
} from '@mui/material';

// Here I am importing some icons from Material UI
import {
  Person,
  Email,
  CreditCard,
  EventNote,
  TheaterComedy,
  AccessTime,
  LocalMovies,
  Receipt
} from '@mui/icons-material';

import Footer from '../../components/Footer/Footer';
import './Payment.css';

// This will Renders payment form and order summary, handles payment processing
const Payment = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const ticketData = location.state?.ticketData;

  const [formData, setFormData] = useState({
    fullName: '',
    email: '',
    cardNumber: '',
    expiryDate: '',
    cvv: ''
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    navigate('/confirmation', { 
      state: { 
        ticketData: location.state.ticketData 
      } 
    });
  };

  const isFormValid = () => {
    return Object.values(formData).every(value => value.trim() !== '');
  };

  return (
    <div className="payment-page">
      <Container maxWidth="md" sx={{ my: 4 }}>
        <Typography variant="h4" gutterBottom className="payment-title">
          Complete Your Purchase
        </Typography>

        <Grid container spacing={3}>
          {/* Receipt Summary */}
          <Grid item xs={12} md={4}>
            <Paper elevation={3} className="receipt-section">
              <Typography variant="h6" className="receipt-header">
                <Receipt sx={{ mr: 1 }} />
                Order Summary
              </Typography>
              <Divider />
              
              <Box className="receipt-details">
                <Box className="receipt-item">
                  <LocalMovies />
                  <Typography variant="body1">{ticketData?.movie}</Typography>
                </Box>
                <Box className="receipt-item">
                  <EventNote />
                  <Typography variant="body1">{ticketData?.date}</Typography>
                </Box>
                <Box className="receipt-item">
                  <AccessTime />
                  <Typography variant="body1">{ticketData?.time}</Typography>
                </Box>
                <Box className="receipt-item">
                  <TheaterComedy />
                  <Typography variant="body1">{ticketData?.theater}</Typography>
                </Box>
                
                <Divider sx={{ my: 2 }} />
                
                <Box className="receipt-item">
                  <Typography variant="body1">Selected Seats:</Typography>
                  <Typography variant="body1">{ticketData?.seats.join(', ')}</Typography>
                </Box>
                
                <Box className="receipt-total">
                  <Typography variant="h6">Total:</Typography>
                  <Typography variant="h6">${ticketData?.total}</Typography>
                </Box>
              </Box>
            </Paper>
          </Grid>

          {/* Payment Form */}
          <Grid item xs={12} md={8}>
            <Paper elevation={3} className="payment-form">
              <form onSubmit={handleSubmit}>
                <Typography variant="h6" gutterBottom>
                  Personal Information
                </Typography>
                
                <Box className="form-field">
                  <Person className="field-icon" />
                  <TextField
                    fullWidth
                    label="Full Name"
                    name="fullName"
                    value={formData.fullName}
                    onChange={handleInputChange}
                    required
                  />
                </Box>

                <Box className="form-field">
                  <Email className="field-icon" />
                  <TextField
                    fullWidth
                    label="Email"
                    name="email"
                    type="email"
                    value={formData.email}
                    onChange={handleInputChange}
                    required
                  />
                </Box>

                <Typography variant="h6" gutterBottom sx={{ mt: 3 }}>
                  Payment Details
                </Typography>

                <Box className="form-field">
                  <CreditCard className="field-icon" />
                  <TextField
                    fullWidth
                    label="Card Number"
                    name="cardNumber"
                    value={formData.cardNumber}
                    onChange={handleInputChange}
                    required
                  />
                </Box>

                <Grid container spacing={2}>
                  <Grid item xs={6}>
                    <TextField
                      fullWidth
                      label="Expiry Date"
                      name="expiryDate"
                      placeholder="MM/YY"
                      value={formData.expiryDate}
                      onChange={handleInputChange}
                      required
                    />
                  </Grid>
                  <Grid item xs={6}>
                    <TextField
                      fullWidth
                      label="CVV"
                      name="cvv"
                      type="password"
                      value={formData.cvv}
                      onChange={handleInputChange}
                      required
                    />
                  </Grid>
                </Grid>

                <Button
                  type="submit"
                  variant="contained"
                  fullWidth
                  disabled={!isFormValid()}
                  className="submit-button"
                >
                  Make Payment and Get Ticket
                </Button>
              </form>
            </Paper>
          </Grid>
        </Grid>
      </Container>
      <Footer />
    </div>
  );
};

export default Payment;

