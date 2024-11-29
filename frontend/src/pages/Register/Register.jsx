/**
 * Register Page - Handles user registration with form validation.
 * Users can create a new account by providing their details.
 * Redirects to the home page upon successful registration.
 *
 * Written by: Henok Lamiso and Yousef Fatouraee
 */
import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import Footer from "../../components/Footer/Footer";
import "./Register.css";
import { useApi } from "../../hooks/useApi";

const Register = () => {
  const navigate = useNavigate(); // Hook to navigate programmatically
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
  }); // Form data state
  const [error, setError] = useState(""); // Client-side validation error state
  const [apiErrorMessage, setApiErrorMessage] = useState(""); // API error message state

  // API hook for registration functionality
  const {
    data,
    loading,
    error: apiError,
    responseStatus,
    fetchData: register,
  } = useApi("api/users", "POST");

  /**
   * Handles input changes for the form fields.
   * Updates the corresponding field in the `formData` state.
   * Resets error messages on user interaction.
   *
   * @param {Event} e - The input change event.
   */
  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value, // Dynamically update based on input name
    });
    setError(""); // Reset client-side errors
    setApiErrorMessage(""); // Reset API errors
  };

  /**
   * Handles form submission for registration.
   * Validates inputs and calls the registration API.
   * Displays errors or redirects to the home page upon success.
   *
   * @param {Event} e - The form submit event.
   */
  const handleSubmit = async (e) => {
    e.preventDefault();

    // Basic validation
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
      // Call the registration API
      const { result, status, error } = await register({
        headers: {
          "Content-Type": "application/json",
        },
        body: {
          name: formData.name,
          email: formData.email,
          password: formData.password,
        },
      });

      if (status === 201) {
        if (result) {
          // Store user details in localStorage
          localStorage.setItem("email", result.email || "");
          localStorage.setItem("name", result.name || "");
          localStorage.setItem("userId", result.userId || "");
          localStorage.setItem(
            "membershipExpired",
            result.membershipExpired || "true"
          );

          // Redirect to the home page
          navigate("/");
        } else {
          console.warn("No data returned by the API.");
        }
      } else {
        setApiErrorMessage(error || "An error occurred during registration.");
      }
    } catch (err) {
      console.error("Error during registration:", err);
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

          {/* Registration Form */}
          <form onSubmit={handleSubmit}>
            {/* Full Name Field */}
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

            {/* Email Field */}
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

            {/* Password Field */}
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

            {/* Confirm Password Field */}
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

            {/* Error Messages */}
            {error && <span className="error-message">{error}</span>}
            {apiErrorMessage && (
              <span className="error-message">{apiErrorMessage}</span>
            )}

            {/* Submit Button */}
            <button
              type="submit"
              className="register-button"
              disabled={loading}
            >
              {loading ? "Registering..." : "Create Account"}
            </button>
          </form>

          {/* Login Redirect */}
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
