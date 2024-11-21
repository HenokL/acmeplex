/**
 * Login Page - Handles user authentication with form validation.
 * Allows users to log in by providing their username and password.
 * Redirects authenticated users to the home page.
 * Written by: Henok Lamiso and Yousef Fatouraee
 */
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import Footer from "../../components/Footer/Footer";
import { useApi } from "../../hooks/useApi";
import "./Login.css";

const Login = () => {
  const navigate = useNavigate(); // Hook to navigate programmatically
  const [username, setUsername] = useState(""); // Username state
  const [password, setPassword] = useState(""); // Password state
  const [error, setError] = useState(""); // Error message state

  // API hook for login functionality
  const {
    data,
    loading,
    error: apiError,
    responseStatus,
    fetchData: login,
  } = useApi("api/login", "POST");

  /**
   * Handles form submission for user login.
   * Validates input fields and calls the login API.
   * Redirects users on successful login or displays error messages.
   * @param {Event} e - The form submit event.
   */
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(""); // Reset any existing error message

    // Validate inputs
    if (!username || !password) {
      setError("Please fill in all fields");
      return;
    }

    try {
      // Call the login API
      const { result, status, error } = await login({
        headers: { "Content-Type": "application/json" },
        body: { email: username, password },
      });

      // Handle response based on status
      if (status === 200) {
        if (result) {
          // Save user data to localStorage
          localStorage.setItem("userId", result.userId || "");
          localStorage.setItem("email", username || "");
          localStorage.setItem("name", result.name || "");

          // Redirect to the home page
          navigate("/");
        } else {
          console.warn("No data returned by the API.");
        }
      } else if (status === 404) {
        setError("Invalid credentials, please try again.");
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

  return (
    <div className="login-page">
      <div className="login-container">
        <div className="login-form">
          <h1>Hello!</h1>
          <p className="form-description">Sign in to continue to ACMEPLEX</p>

<<<<<<< HEAD
=======
          {/* Login Form */}
>>>>>>> 16a4448e445787b541db4a63e6a7c168ffb8b632
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="username">Username</label>
              <input
                type="text"
                id="username"
                value={username}
                onChange={(e) => {
                  setUsername(e.target.value);
                  setError(""); // Clear error on input change
                }}
                placeholder="Enter your username"
                className="form-input"
              />
            </div>

            <div className="form-group">
              <label htmlFor="password">Password</label>
              <input
                type="password"
                id="password"
                value={password}
                onChange={(e) => {
                  setPassword(e.target.value);
                  setError(""); // Clear error on input change
                }}
                placeholder="Enter your password"
                className="form-input"
              />
            </div>

            {/* Display error messages */}
            {error && <span className="error-message">{error}</span>}

            {/* Submit button */}
            <button type="submit" className="login-button" disabled={loading}>
              {loading ? "Loading..." : "Sign In"}
            </button>
          </form>

          {/* Redirect to registration page */}
          <div className="form-footer">
            <div className="signup-prompt">
              <p>New to ACMEPLEX?</p>
              <Link to="/register" className="signup-link">
                Create an Account
              </Link>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default Login;
