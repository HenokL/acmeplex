/**
 * CancelTicket page which Handles ticket cancellation process with confirmation
 * Comment added by Henok L
 */

import React, { useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { FaTicketAlt, FaCheckCircle } from 'react-icons/fa';
import { MdEmail } from 'react-icons/md';
import Footer from '../../components/Footer/Footer';
import './CancelTicket.css';

const CancelTicket = () => {
    const [ticketNumber, setTicketNumber] = useState('');
    const [email, setEmail] = useState('');
    const [showConfirmation, setShowConfirmation] = useState(false);
    const [isSuccess, setIsSuccess] = useState(false);

    // This will Handles the initial form submission and validation
    const handleSubmit = (e) => {
        e.preventDefault();
        if (ticketNumber && email) {
            setShowConfirmation(true);
        }
    };

    // Processes ticket cancellation and shows success message
    const handleConfirm = () => {
        setShowConfirmation(false);
        setIsSuccess(true);
        setTimeout(() => {
            setIsSuccess(false);
            setTicketNumber('');
            setEmail('');
        }, 3000);
    };

    // This part will Render the cancel ticket form
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

                        <button type="submit" className="submit-btn">
                            Cancel Ticket
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
                                <button onClick={handleConfirm} className="confirm-btn">Yes, Cancel</button>
                                <button onClick={() => setShowConfirmation(false)} className="cancel-btn">No, Keep Ticket</button>
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

