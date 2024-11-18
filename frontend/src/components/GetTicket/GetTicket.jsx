import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from '@mui/material';
import LocalActivityIcon from '@mui/icons-material/LocalActivity';
import './GetTicket.css';

const GetTicket = () => {
  const navigate = useNavigate();

  const handleGetTickets = () => {
    navigate('/tickets');
  };

  return (
    <div className="get-ticket-container">
      <Button
        variant="contained"
        startIcon={<LocalActivityIcon />}
        onClick={handleGetTickets}
        className="get-ticket-button"
      >
        Get Tickets
      </Button>
    </div>
  );
};

export default GetTicket;