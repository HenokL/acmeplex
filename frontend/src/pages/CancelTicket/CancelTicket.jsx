/**
 * CancelTicket page which Handles ticket cancellation process with confirmation
 * Written by: Henok Lamiso and Yousef Fatouraee
 */
import React, { useEffect, useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { FaTicketAlt, FaCheckCircle } from "react-icons/fa";
import { MdEmail } from "react-icons/md";
import Footer from "../../components/Footer/Footer";
import { useApi } from "../../hooks/useApi"; // Import the useApi hook
import "./CancelTicket.css";

const CancelTicket = () => {
  const [ticketNumber, setTicketNumber] = useState("");
  const [email, setEmail] = useState("");
  const [showConfirmation, setShowConfirmation] = useState(false);
  const [showEmail, setShowEmail] = useState(true);
  const [isSuccess, setIsSuccess] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const { fetchData, loading, error } = useApi(
    `api/tickets/cancel/${ticketNumber}`,
    "PUT"
  );

  // Handles the initial form submission and validation
  const handleSubmit = (e) => {
    e.preventDefault();
    if (ticketNumber && email) {
      setErrorMessage(""); // Clear previous errors
      setShowConfirmation(true); // Show confirmation modal
    } else {
      setErrorMessage("Both Ticket Number and Email are required."); // Validation error
    }
  };

  // Processes ticket cancellation and shows success message
  const handleConfirm = async () => {
    setShowConfirmation(false); // Close the confirmation modal

    const response = await fetchData({
      headers: { "Content-Type": "application/json" },
      body: { email }, // Request body
    });

    if (response?.result) {
      // If successful response
      setIsSuccess(true);
      setErrorMessage(""); // Clear any error message
    } else if (response?.error) {
      // If an error occurs
      setErrorMessage(response.error); // Show the backend message
      setIsSuccess(false);
    }
  };
  useEffect(() => {
    const storedEmail = localStorage.getItem("email");
    if (storedEmail) {
      setShowEmail(false);
      setEmail(storedEmail); // Set email from localStorage if available
    }
  }, []);
  // Renders the cancel ticket form
  return (
    <>
      <div className="cancel-page-container">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="cancel-ticket-container"
        >
          <h2>Cancel Your Ticket</h2>

          <form onSubmit={handleSubmit} className="cancel-form">
            <div className="form-group">
              <FaTicketAlt className="input-icon" />
              <input
                type="text"
                value={ticketNumber}
                onChange={(e) => setTicketNumber(e.target.value)}
                placeholder="Enter Ticket Number"
                required
              />
            </div>
            {showEmail && (
              <div className="form-group">
                <MdEmail className="input-icon" />
                <input
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  placeholder="Enter Email Address"
                  required
                />
              </div>
            )}
            {/* Error message */}
            {errorMessage && <p className="error-message">{errorMessage}</p>}

            <button type="submit" className="submit-btn" disabled={loading}>
              {loading ? "Processing..." : "Cancel Ticket"}
            </button>
          </form>

          <AnimatePresence>
            {isSuccess && (
              <motion.div
                initial={{ opacity: 0, scale: 0.5 }}
                animate={{ opacity: 1, scale: 1 }}
                exit={{ opacity: 0, scale: 0.5 }}
                className="success-message"
              >
                <FaCheckCircle />
                <p>Ticket Cancelled Successfully!</p>
              </motion.div>
            )}
          </AnimatePresence>
        </motion.div>

        {showConfirmation && (
          <div className="confirmation-modal">
            <motion.div
              initial={{ scale: 0.5 }}
              animate={{ scale: 1 }}
              className="modal-content"
            >
              <h3>Confirm Cancellation</h3>
              <p>Are you sure you want to cancel this ticket?</p>
              <p className="ticket-info">Ticket Number: {ticketNumber}</p>
              <div className="modal-buttons">
                <button onClick={handleConfirm} className="confirm-btn">
                  Yes, Cancel
                </button>
                <button
                  onClick={() => setShowConfirmation(false)}
                  className="cancel-btn"
                >
                  No, Keep Ticket
                </button>
              </div>
            </motion.div>
          </div>
        )}
      </div>
      <Footer />
    </>
  );
};

export default CancelTicket;
