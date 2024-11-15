import React from 'react';
import { Link } from 'react-router-dom';
import { FiLogIn } from 'react-icons/fi';
import './Navbar.css';

const Navbar = () => {
  return (
    <nav className="navbar">
      <div className="nav-left">
        <ul className="nav-links">
          <li><Link to="/">Home</Link></li>
          <li><Link to="/cancel-ticket">Cancel Ticket</Link></li>
          <li><Link to="/about">About Us</Link></li>
        </ul>
      </div>
      
      <div className="nav-center">
        <Link to="/" className="navbar-logo">ACMEPLEX</Link>
      </div>

      <div className="nav-right">
        <Link to="/login" className="login-button">
          <FiLogIn className="login-icon" />
          Login
        </Link>
      </div>
    </nav>
  );
};

export default Navbar;
