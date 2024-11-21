/**
 * Register page - Handles user registration with form validation
 * Comment added by Henok L
 */

import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Footer from "../../components/Footer/Footer";
import "./Register.css";
import { useApi } from "../../hooks/useApi";

const Register = () => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
  });
  const [error, setError] = useState("");

  const {
    fetchData: register,
    error: apiError,
    responseStatus,
  } = useApi("api/users", "POST");
  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
    setError("");
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

    // console.log("Registration attempt:", {
    //   name: formData.name,
    //   email: formData.email,
    // });

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
      if (responseStatus >= 200 && responseStatus < 300) {
        console.log("success");
        // router.push(`/dashboard/${userId}`);
      }
    } catch (error) {
      console.error(error);
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

            <button type="submit" className="register-button">
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
