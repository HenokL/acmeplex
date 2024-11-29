/*
 * This is the component for the MembershipStatus fee charging page.
 */

import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import {
  Container,
  Paper,
  Typography,
  TextField,
  Button,
  Box,
  Grid,
  Divider,
  CircularProgress,
} from "@mui/material";
import {
  Person,
  Email,
  CreditCard,
  Cancel,
  Movie,
  EventSeat,
} from "@mui/icons-material";
import Footer from "../Footer/Footer";
import "./MembershipStatus.css";
import { useApi } from "../../hooks/useApi";

const MembershipStatus = () => {
  const navigate = useNavigate();
  const [isProcessing, setIsProcessing] = useState(false);
  const [error, setError] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [formData, setFormData] = useState({
    fullName: localStorage.getItem("name") || "",
    email: localStorage.getItem("email") || "",
    cardNumber: localStorage.getItem("creditCardNumber") || "", // As long as the card number is 16 digits, it will be accepted
    expiryDate: localStorage.getItem("creditCardExpiryDate") || "", // As long as the expiry date is in the format MM/YY, it will be accepted
    cvv: localStorage.getItem("creditCardCVV") || "",
  });
  const [validationErrors, setValidationErrors] = useState({});
  const {
    data,
    loading,
    error: apiError,
    responseStatus,
    fetchData: pay,
  } = useApi(formData.email && `api/user/${formData.email}/membership`, "POST");

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
    if (validationErrors[name]) {
      setValidationErrors((prev) => ({
        ...prev,
        [name]: "",
      }));
    }
  };

  const validateForm = () => {
    const errors = {};

    if (!formData.fullName.trim()) {
      errors.fullName = "Name is required";
    }

    if (!formData.email.trim()) {
      errors.email = "Email is required";
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      errors.email = "Invalid email format";
    }

    if (!formData.cardNumber.trim()) {
      errors.cardNumber = "Card number is required";
    } else if (!/^\d{16}$/.test(formData.cardNumber.replace(/\s/g, ""))) {
      errors.cardNumber = "Invalid card number";
    }

    if (!formData.expiryDate.trim()) {
      errors.expiryDate = "Expiry date is required";
    } else if (!/^(0[1-9]|1[0-2])\/([0-9]{2})$/.test(formData.expiryDate)) {
      errors.expiryDate = "Invalid format (MM/YY)";
    }

    if (!formData.cvv.trim()) {
      errors.cvv = "CVV is required";
    } else if (!/^\d{3,4}$/.test(formData.cvv)) {
      errors.cvv = "Invalid CVV";
    }

    setValidationErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handlePayment = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    setIsProcessing(true);
    setError("");

    try {
      // Handle card payment
      // Submit ticket creation
      const requestBody = {
        creditCardNumber: formData.cardNumber + "",
        creditCardName: formData.fullName,
        creditCardCV: formData.cvv + "",
        creditCardExpiryDate: formData.expiryDate + "",
      };
      // Call the book API
      const { result, status, error } = await pay({
        headers: { "Content-Type": "application/json" },
        body: requestBody,
      });
      // Handle response based on status
      if (status === 200) {
        localStorage.setItem("membershipExpired", "false");
        alert(result);
        navigate("/");
      } else {
        setError(error || "Payment failed");
      }
    } catch (error) {
      console.error("Payment error:", error);
      setError("Payment processing failed. Please try again.");
    } finally {
      setIsProcessing(false);
    }
  };

  if (isLoading) {
    return (
      <Box
        display="flex"
        justifyContent="center"
        alignItems="center"
        minHeight="100vh"
      >
        <CircularProgress />
      </Box>
    );
  }

  return (
    <div className="membership-page">
      <Container maxWidth="md" sx={{ my: 4 }}>
        <Box className="welcome-section">
          {formData.fullName && (
            <Typography variant="h4" className="welcome-text" gutterBottom>
              Hi, {formData.fullName}!
            </Typography>
          )}
          <Typography variant="body1" className="welcome-message" gutterBottom>
            Please pay your membership fee to benefit from all the exclusive
            access.
          </Typography>
        </Box>

        <Grid container spacing={3}>
          <Grid item xs={12} md={4}>
            <Paper elevation={3} className="receipt-section">
              <Box p={3}>
                <Typography variant="h6" gutterBottom>
                  Your Membership Details:
                </Typography>

                <Box className="receipt-item">
                  <Cancel className="receipt-icon" />
                  <Typography>No 15% fee for cancellations</Typography>
                </Box>

                <Box className="receipt-item">
                  <Movie className="receipt-icon" />
                  <Typography>Early access to upcoming movies</Typography>
                </Box>

                <Box className="receipt-item">
                  <EventSeat className="receipt-icon" />
                  <Typography>Buy 10% of seats early</Typography>
                </Box>

                <Divider sx={{ my: 2 }} />

                <Box className="receipt-total">
                  <Typography variant="h6">Total:</Typography>
                  <Typography variant="h6">$20.00</Typography>
                </Box>
              </Box>
            </Paper>
          </Grid>

          <Grid item xs={12} md={8}>
            <Paper elevation={3} className="payment-form">
              <Box p={3}>
                <form onSubmit={handlePayment}>
                  <Typography variant="h6" gutterBottom>
                    Payment Information
                  </Typography>

                  <Grid container spacing={2}>
                    <Grid item xs={12}>
                      <TextField
                        fullWidth
                        label="Full Name"
                        name="fullName"
                        value={formData.fullName}
                        onChange={handleInputChange}
                        error={!!validationErrors.fullName}
                        helperText={validationErrors.fullName}
                        required
                        InputProps={{
                          startAdornment: (
                            <Person sx={{ mr: 1, color: "action.active" }} />
                          ),
                        }}
                      />
                    </Grid>

                    <Grid item xs={12}>
                      <TextField
                        fullWidth
                        label="Email"
                        name="email"
                        type="email"
                        value={formData.email}
                        onChange={handleInputChange}
                        error={!!validationErrors.email}
                        helperText={validationErrors.email}
                        required
                        InputProps={{
                          startAdornment: (
                            <Email sx={{ mr: 1, color: "action.active" }} />
                          ),
                        }}
                      />
                    </Grid>

                    <Grid item xs={12}>
                      <TextField
                        fullWidth
                        label="Card Number"
                        name="cardNumber"
                        value={formData.cardNumber}
                        onChange={handleInputChange}
                        error={!!validationErrors.cardNumber}
                        helperText={validationErrors.cardNumber}
                        required
                        InputProps={{
                          startAdornment: (
                            <CreditCard
                              sx={{ mr: 1, color: "action.active" }}
                            />
                          ),
                          maxLength: 16,
                        }}
                      />
                    </Grid>

                    <Grid item xs={6}>
                      <TextField
                        fullWidth
                        label="Expiry Date"
                        name="expiryDate"
                        placeholder="MM/YY"
                        value={formData.expiryDate}
                        onChange={handleInputChange}
                        error={!!validationErrors.expiryDate}
                        helperText={validationErrors.expiryDate}
                        required
                        inputProps={{ maxLength: 5 }}
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
                        error={!!validationErrors.cvv}
                        helperText={validationErrors.cvv}
                        required
                        inputProps={{ maxLength: 4 }}
                      />
                    </Grid>
                  </Grid>

                  {error && (
                    <Typography color="error" sx={{ mt: 2 }}>
                      {error}
                    </Typography>
                  )}

                  <Button
                    type="submit"
                    variant="contained"
                    fullWidth
                    disabled={isProcessing}
                    className="submit-button"
                    sx={{ mt: 3, position: "relative" }}
                  >
                    {isProcessing ? (
                      <CircularProgress
                        size={24}
                        sx={{ color: "white", position: "absolute" }}
                      />
                    ) : (
                      "Complete Payment"
                    )}
                  </Button>
                </form>
              </Box>
            </Paper>
          </Grid>
        </Grid>
      </Container>
      <Footer />
    </div>
  );
};

export default MembershipStatus;
