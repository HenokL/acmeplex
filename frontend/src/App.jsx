/**
 * App component - Main application component handling routing and layout
 * Comment added by Henok L
 */

import React from 'react'; // Just importing react
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'; // Importing react-router-dom components

// Importing components
import Navbar from './components/Navbar/Navbar';
import Home from './pages/Home/Home';
import CancelTicket from './pages/CancelTicket/CancelTicket';
import About from './pages/About/About';
import Login from './components/AuthForms/Login';

import './styles/global.css'; // Importing global styles
import Register from './pages/Register/Register';
import Tickets from './pages/Tickets/Tickets';
import Payment from './pages/Payment/Payment';
import Confirmation from './pages/Confirmation/Confirmation';

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/cancel-ticket" element={<CancelTicket />} />
          <Route path="/about" element={<About />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/tickets" element={<Tickets />} />
          <Route path="/payment" element={<Payment />} />
          <Route path="/confirmation" element={<Confirmation />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;