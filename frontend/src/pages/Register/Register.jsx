/**
 * Register page - Handles user registration with form validation
 * Comment added by Henok L
 */
import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import Footer from "../../components/Footer/Footer";
import "./Register.css";
import { useApi } from "../../hooks/useApi";

const Register = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
  });
  const [error, setError] = useState("");
  const [apiErrorMessage, setApiErrorMessage] = useState("");

  const {
    data,
    loading,
    error: apiError,
    responseStatus,
    fetchData: register,
  } = useApi("api/users", "POST");

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
    setError("");
    setApiErrorMessage("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (
      !formData.name ||
      !formData.email ||
      !formData.password ||
      !formData.confirmPassword
    ) {
      setError("Please fill in all fields");
      return;
    }

    if (formData.password !== formData.confirmPassword) {
      setError("Passwords do not match");
      return;
    }

    if (!formData.email.includes("@")) {
      setError("Please enter a valid email address");
      return;
    }

    // Reset error messages
    setError("");
    setApiErrorMessage("");

    try {
      await register({
        headers: {
          "Content-Type": "application/json",
        },
        body: {
          name: formData.name,
          email: formData.email,
          password: formData.password,
        },
      });

      // Successful registration
      if (responseStatus === 201) {
        if (data) {
          // Store user information in localStorage
          localStorage.setItem("email", data.email || "");
          localStorage.setItem("name", data.name || "");
          localStorage.setItem("userId", data.userId || "");
          navigate("/"); // Redirect to home page
        } else {
          console.warn("No data returned by the API.");
        }
      } else {
        // Handle errors from the server
        setApiErrorMessage(
          apiError || "An error occurred during registration."
        );
      }
    } catch (error) {
      console.error("Error during registration:", error);
      setApiErrorMessage("An unexpected error occurred. Please try again.");
    }
  };

  return (
    <div className="register-page">
      <div className="register-container">
        <div className="register-form">
          <h1>Create Account</h1>
          <p className="form-description">
            Join ACMEPLEX to unlock exclusive benefits
          </p>

          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="name">Full Name</label>
              <input
                type="text"
                id="name"
                name="name"
                value={formData.name}
                onChange={handleChange}
                placeholder="Enter your full name"
                className="form-input"
              />
            </div>

            <div className="form-group">
              <label htmlFor="email">Email</label>
              <input
                type="email"
                id="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                placeholder="Enter your email"
                className="form-input"
              />
            </div>

            <div className="form-group">
              <label htmlFor="password">Password</label>
              <input
                type="password"
                id="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                placeholder="Create a password"
                className="form-input"
              />
            </div>

            <div className="form-group">
              <label htmlFor="confirmPassword">Confirm Password</label>
              <input
                type="password"
                id="confirmPassword"
                name="confirmPassword"
                value={formData.confirmPassword}
                onChange={handleChange}
                placeholder="Confirm your password"
                className="form-input"
              />
            </div>

            {error && <span className="error-message">{error}</span>}
            {apiErrorMessage && (
              <span className="error-message">{apiErrorMessage}</span>
            )}

            <button
              type="submit"
              className="register-button"
              disabled={loading}
            >
              Create Account
            </button>
          </form>

          <div className="form-footer">
            <div className="login-prompt">
              <p>Already have an account?</p>
              <Link to="/login" className="login-link">
                Sign In
              </Link>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default Register;
