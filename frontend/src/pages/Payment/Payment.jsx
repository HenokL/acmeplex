/**
 * Payment page - Handles payment processing and displays order summary
 * Written by: Henok Lamiso and Yousef Fatouraee
 */

import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import {
  Container,
  Paper,
  Typography,
  TextField,
  Button,
  Box,
  Grid,
  Divider,
  FormControlLabel,
  Switch,
} from "@mui/material";
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


const Payment = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const ticketData = location.state?.ticketData;
  
  console.log("Ticket Data:", ticketData);
  const [error, setError] = useState("");
  const [validationErrors, setValidationErrors] = useState({});
  const [submittedOnce, setSubmittedOnce] = useState(false);
  const [isGuest, setIsGuest] = useState(true);
  const [showPaymentSection, setShowPaymentSection] = useState(false);
  const [useCreditsForPayment, setUseCreditsForPayment] = useState(false);

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

  const {
    data: credit,
    loading: creditLoading,
    error: creditError,
  } = useApi(
    formData.email ? `api/credits/${formData.email}` : "",
    "GET"
  );

  const {
    data: deductedCredit,
    loading: deductCreditLoading,
    error: deductCreditError,
    responseStatus: deductCreditResponseStatus,
    fetchData: deductCredit,
  } = useApi("api/credits/deductCredits", "POST");

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const validatePersonalInfo = () => {
    const errors = {};
    if (!formData.fullName) {
      errors.fullName = "Full Name is required.";
    }
    if (!formData.email) {
      errors.email = "Email is required.";
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      errors.email = "Email address is invalid.";
    }
    return errors;
  };

  const validatePaymentInfo = () => {
    const errors = {};
    if (!useCreditsForPayment) {
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
    }
    return errors;
  };

  const handlePersonalInfoSubmit = async () => {
    const errors = validatePersonalInfo();
    if (Object.keys(errors).length > 0) {
      setValidationErrors(errors);
      return;
    }

    try {
      const response = await fetch(`/api/credits/${formData.email}`);
      const creditData = await response.json();
      if (creditData > 0) {
        setUseCreditsForPayment(true);
      }
      setShowPaymentSection(true);
    } catch (error) {
      console.error("Error checking credits:", error);
      setShowPaymentSection(true);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSubmittedOnce(true);
  
    const errors = {
      ...validatePersonalInfo(),
      ...validatePaymentInfo(),
    };
  
    if (Object.keys(errors).length > 0) {
      setValidationErrors(errors);
      return;
    }
  
    try {
      const totalAmount = ticketData.total * 1.05;
  
      if (useCreditsForPayment) {
        // Handle credit payment
        const creditDeductionResult = await deductCredit({
          headers: { "Content-Type": "application/json" },
          body: {
            email: formData.email,
            creditsUsed: Math.min(credit, totalAmount)
          }
        });
  
        if (creditDeductionResult.status === 200) {
          // Here I set it to Generate the ticket number (1-99)
          const ticketNumber = Math.floor(Math.random() * 99) + 1;
          
          navigate("/confirmation", {
            state: {
              ticketData: {
                ...ticketData,
                ticketId: ticketNumber,
                paymentMethod: 'CREDITS',
                amountPaid: Math.min(credit, totalAmount)
              }
            }
          });
          return;
        } else {
          setError("Failed to process credit payment");
          return;
        }
      }
  
      // Handle card payment
      const requestBody = {
        showtimeId: Number(ticketData.showtimeId),
        price: totalAmount,
        seats: ticketData.seats,
        creditCardNumber: parseInt(formData.cardNumber),
        creditCardName: formData.fullName,
        creditCardCV: parseInt(formData.cvv, 10),
        email: formData.email
      };

      const { result, status, error } = await book({
        headers: { "Content-Type": "application/json" },
        body: requestBody
      });
  
      if (status === 201) {
        navigate("/confirmation", {
          state: {
            ticketData: {
              ...ticketData,
              ticketId: result,
              paymentMethod: 'CARD',
              amountPaid: totalAmount
            }
          }
        });
      } else {
        setError(error || "Payment failed");
      }
  
    } catch (err) {
      console.error("Payment error:", err);
      setError("An unexpected error occurred");
    }
  };

  return (
    <div className="payment-page">
      <Container maxWidth="md" sx={{ my: 4 }}>
        <Typography variant="h4" gutterBottom>
          Complete Your Purchase
        </Typography>

        <Grid container spacing={3}>
          {/* Receipt Section */}
          <Grid item xs={12} md={4}>
            <Paper elevation={3} className="receipt-section">
              <Typography variant="h6" gutterBottom>
                Order Summary
              </Typography>

              <Box className="receipt-item">
              <LocalMovies />
              <Typography variant="body1">{ticketData?.movie}</Typography>
            </Box>

              <Box className="receipt-item">
                <TheaterComedy />
                <Typography>Theater {ticketData?.theater}</Typography>
              </Box>
              <Box className="receipt-item">
                <EventNote />
                <Typography>{ticketData?.date}</Typography>
              </Box>
              <Box className="receipt-item">
                <AccessTime />
                <Typography>{ticketData?.time}</Typography>
              </Box>
              <Box className="receipt-item">
              <Receipt />
              <Typography>
                Seats: {
                  ticketData?.seats?.map(seat => 
                    typeof seat === 'object' ? seat.seatNumber || seat.label : seat
                  ).join(", ")
                }
              </Typography>
            </Box>
              <Divider sx={{ my: 2 }} />
              <Box className="receipt-total">
                <Typography variant="subtitle1">Subtotal:</Typography>
                <Typography variant="subtitle1">${ticketData?.total.toFixed(2)}</Typography>
              </Box>
              <Box className="receipt-total">
                <Typography variant="subtitle1">Service Fee (5%):</Typography>
                <Typography variant="subtitle1">
                  ${(ticketData?.total * 0.05).toFixed(2)}
                </Typography>
              </Box>
              <Box className="receipt-total">
                <Typography variant="h6">Total:</Typography>
                <Typography variant="h6">
                  ${(ticketData?.total * 1.05).toFixed(2)}
                </Typography>
              </Box>
            </Paper>
          </Grid>
          

          {/* Payment Form Section */}
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
                    error={!!validationErrors.fullName}
                    helperText={validationErrors.fullName}
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
                    error={!!validationErrors.email}
                    helperText={validationErrors.email}
                    required
                    disabled={!isGuest}
                  />
                </Box>
                

                {!showPaymentSection && (
                  <Button
                    variant="contained"
                    fullWidth
                    onClick={handlePersonalInfoSubmit}
                    className="continue-button"
                    sx={{
                      backgroundColor: '#032541 !important',
                      '&:hover': {
                        backgroundColor: '#043658 !important',
                      }
                    }}
                  >
                    Continue to Payment
                  </Button>
                )}

                {showPaymentSection && (
                  <>
                    {credit > 0 && (
                      <Box sx={{ my: 2 }}>
                        <FormControlLabel
                          control={
                            <Switch
                              checked={useCreditsForPayment}
                              onChange={(e) => setUseCreditsForPayment(e.target.checked)}
                            />
                          }
                          label={`Use Available Credits ($${credit})`}
                        />
                      </Box>
                    )}

                    {(!useCreditsForPayment || credit < ticketData?.total * 1.05) && (
                      <>
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
                            error={!!validationErrors.cardNumber}
                            helperText={validationErrors.cardNumber}
                            required={!useCreditsForPayment}
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
                              error={!!validationErrors.expiryDate}
                              helperText={validationErrors.expiryDate}
                              required={!useCreditsForPayment}
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
                              required={!useCreditsForPayment}
                            />
                          </Grid>
                        </Grid>
                      </>
                    )}

                    {error && (
                      <Typography color="error" sx={{ mt: 2 }}>
                        {error}
                      </Typography>
                    )}

                    <Button
                      type="submit"
                      variant="contained"
                      fullWidth
                      className="submit-button"
                      sx={{ mt: 3 }}
                    >
                      Complete Payment
                    </Button>
                  </>
                )}
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

