/**
 * Confirmation component - This Displays successful payment confirmation and ticket details
 */
import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import {
  Container,
  Paper,
  Typography,
  Button,
  Box,
} from '@mui/material';
import {
  CheckCircleOutline,
  Email,
  Home
} from '@mui/icons-material';
import './Confirmation.css';
import Footer from '../../components/Footer/Footer';

const Confirmation = () => {
  // Get ticket data from navigation state
  const location = useLocation();
  const navigate = useNavigate();

  // Extract ticket details passed from payment page
  const ticketData = location.state?.ticketData;

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  // This part will Render confirmation page with success animation and order detail
  return (
    <div className="confirmation-page">
      <Container maxWidth="sm" sx={{ my: 4 }}>
        <Paper elevation={3} className="confirmation-paper">
          <div className="success-animation">
            <CheckCircleOutline className="check-icon" />
          </div>

          <Typography variant="h4" className="confirmation-title">
            Payment Successful!
          </Typography>

          <Typography variant="body1" className="confirmation-message">
            Thank you for your purchase. Your tickets have been confirmed.
          </Typography>

          <Box className="email-notification">
            <Email color="primary" />
            <Typography variant="body2">
              A confirmation email will be sent to your inbox shortly.
            </Typography>
          </Box>

          <Paper elevation={1} className="order-details">
            <Typography variant="h6" gutterBottom>
              Order Details
            </Typography>
            <Box className="details-grid">
              <Typography variant="body2">Movie:</Typography>
              <Typography variant="body1">{ticketData?.movie}</Typography>

              <Typography variant="body2">Date:</Typography>
              <Typography variant="body1">{ticketData?.date}</Typography>

              <Typography variant="body2">Time:</Typography>
              <Typography variant="body1">{ticketData?.time}</Typography>

              <Typography variant="body2">Theater:</Typography>
              <Typography variant="body1">{ticketData?.theater}</Typography>

              <Typography variant="body2">Seats:</Typography>
              <Typography variant="body1">{ticketData?.seats?.join(', ')}</Typography>

              <Typography variant="body2">Total Paid:</Typography>
              <Typography variant="body1">${ticketData?.total}</Typography>
            </Box>
          </Paper>

          <Button
            variant="contained"
            startIcon={<Home />}
            onClick={() => navigate('/')}
            className="home-button"
          >
            Back to Home
          </Button>
        </Paper>
      </Container>
      <Footer />
   
    </div>
  );
};

export default Confirmation;

