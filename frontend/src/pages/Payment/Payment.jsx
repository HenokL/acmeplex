/**
 * Payment page - Handles payment processing and displays order summary
 * Written by: Henok Lamiso and Yousef Fatouraee
 */

import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

// Here I am importing a components from Material UI
import {
  Container,
  Paper,
  Typography,
  TextField,
  Button,
  Box,
  Grid,
  Divider,
} from "@mui/material";

// Here I am importing some icons from Material UI
import {
  Person,
  Email,
  CreditCard,
  EventNote,
  TheaterComedy,
  AccessTime,
  LocalMovies,
  Receipt,
} from "@mui/icons-material";

import Footer from "../../components/Footer/Footer";
import "./Payment.css";
import { useApi } from "../../hooks/useApi";

// This will Renders payment form and order summary, handles payment processing
const Payment = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const ticketData = location.state?.ticketData;
  const [error, setError] = useState("");
  const [validationErrors, setValidationErrors] = useState({});
  const [submittedOnce, setSubmittedOnce] = useState(false);

  const [formData, setFormData] = useState({
    fullName: "",
    email: "",
    cardNumber: "",
    expiryDate: "",
    cvv: "",
  });
  const {
    data,
    loading,
    error: apiError,
    responseStatus,
    fetchData: book,
  } = useApi("api/tickets/book", "POST");

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };
  // Form validation function
  const validateForm = () => {
    const errors = {};

    if (!formData.fullName) {
      errors.fullName = "Full Name is required.";
    }

    if (!formData.email) {
      errors.email = "Email is required.";
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      errors.email = "Email address is invalid.";
    }

    if (!formData.cardNumber) {
      errors.cardNumber = "Card Number is required.";
    } else if (!/^\d{16}$/.test(formData.cardNumber)) {
      errors.cardNumber = "Card Number must be 16 digits.";
    }

    if (!formData.expiryDate) {
      errors.expiryDate = "Expiry Date is required.";
    } else if (!/^\d{2}\/\d{2}$/.test(formData.expiryDate)) {
      errors.expiryDate = "Expiry Date format is invalid. Use MM/YY.";
    }

    if (!formData.cvv) {
      errors.cvv = "CVV is required.";
    } else if (!/^\d{3}$/.test(formData.cvv)) {
      errors.cvv = "CVV must be 3 digits.";
    }

    setValidationErrors(errors);
    return Object.keys(errors).length === 0;
  };

  // Revalidate form when formData changes
  useEffect(() => {
    // Only validate the form if not all fields are empty
    if (
      Object.values(formData).some((value) => value.trim() !== "") &&
      submittedOnce
    ) {
      validateForm();
    }
  }, [formData]);

  // Handle data submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    setSubmittedOnce(true); // to prevent form validation when form is not submitted yet
    if (!validateForm()) {
      return;
    }
    const ticketData = location.state.ticketData;
    try {
      const requestBody = {
        showtimeId: ticketData.showtimeId,
        price: ticketData.total.toFixed(1) * 1.05, // Ensure price is a float
        seats: ticketData.seats,
        creditCardNumber: parseInt(formData.cardNumber), // Ensure creditCardNumber is an int
        creditCardName: formData.fullName,
        creditCardCV: parseInt(formData.cvv, 10), // Ensure creditCardCV is an int
        email: formData.email,
      };
      // Call the book API
      const { result, status, error } = await book({
        headers: { "Content-Type": "application/json" },
        body: requestBody,
      });
      // Handle response based on status
      if (status === 201) {
        if (result) {
          // Redirect to the home page
          const ticketData = {
            ...location.state.ticketData, // Keep the existing properties
            ticketId: result, // Add the new ticketId key-value pair
          };
          navigate("/confirmation", {
            state: {
              ticketData,
            },
          });
        } else {
          console.warn("No data returned by the API.");
        }
      } else if (status === 404) {
        setError(error);
      } else {
        setError(error || "Please try again later!");
      }
    } catch (err) {
      console.error("Error during login:", err);
      setError(
        "An unexpected error occurred. Please check your network connection and try again."
      );
    }
  };

  const isFormValid = () => {
    return Object.values(formData).every((value) => value.trim() !== "");
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
                  <Typography variant="body1">
                    {ticketData?.seatsAlphabetic.join(", ")}
                  </Typography>
                </Box>

                <Divider sx={{ my: 2 }} />

                {/* GST Calculation */}
                <Box className="receipt-item">
                  <Typography variant="body1">GST (5%):</Typography>
                  <Typography variant="body1">
                    ${((ticketData?.total || 0) * 0.05).toFixed(2)}
                  </Typography>
                </Box>

                {/* Updated Total with GST */}
                <Box className="receipt-total">
                  <Typography variant="h6">Total:</Typography>
                  <Typography variant="h6">
                    {ticketData?.total * 1.05}
                  </Typography>
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
                    error={!!validationErrors.fullName}
                    helperText={validationErrors.fullName}
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
                    error={!!validationErrors.email}
                    helperText={validationErrors.email}
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
                    error={!!validationErrors.cardNumber}
                    helperText={validationErrors.cardNumber}
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
                      error={!!validationErrors.expiryDate}
                      helperText={validationErrors.expiryDate}
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
                      error={!!validationErrors.cvv}
                      helperText={validationErrors.cvv}
                    />
                  </Grid>
                </Grid>

                {/* Display general error message */}
                {error && <span className="error-message">{error}</span>}

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
