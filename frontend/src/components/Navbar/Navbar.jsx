/**
 * Navbar component - This is our main navigation bar with text logo, links, and login/logout button
 * Comment added by Henok L
 */

import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { FiLogIn, FiLogOut } from "react-icons/fi";
import "./Navbar.css";

const Navbar = () => {
  const navigate = useNavigate();

  // Check if userId exists and is not null
  const isUserLoggedIn = localStorage.getItem("email") !== null;

  const handleLogout = () => {
    // Remove user information from localStorage
    localStorage.removeItem("userId");
    localStorage.removeItem("name");
    localStorage.removeItem("email");

    // Redirect to home page after logout
    navigate("/");
  };

  return (
    <nav className="navbar">
      <div className="nav-left">
        <ul className="nav-links">
          <li>
            <Link to="/">Home</Link>
          </li>
          <li>
            <Link to="/cancel-ticket">Cancel Ticket</Link>
          </li>
          <li>
            <Link to="/about">About Us</Link>
          </li>
          {localStorage.getItem("email") && (
            <li>
              <Link to="/tickets" state={{ upcoming: true }}>
                Upcoming Shows
              </Link>
            </li>
          )}
        </ul>
      </div>

      <div className="nav-center">
        <Link to="/" className="navbar-logo">
          ACMEPLEX
        </Link>
      </div>

      <div className="nav-right">
        {isUserLoggedIn ? (
          <Link onClick={handleLogout} className="login-button">
            <FiLogOut className="logout-icon" />
            Logout
          </Link>
        ) : (
          <Link to="/login" className="login-button">
            <FiLogIn className="login-icon" />
            Login
          </Link>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
