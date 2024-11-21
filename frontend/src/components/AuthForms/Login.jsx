import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import Footer from "../../components/Footer/Footer";
import { useApi } from "../../hooks/useApi"; // Make sure useApi hook is imported
import "./Login.css";

const Login = () => {
  const navigate = useNavigate(); // Hook to navigate programmatically
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const {
    data,
    loading,
    error: apiError,
    responseStatus,
    fetchData: login,
  } = useApi("api/login", "POST"); // Adjust this to match your API

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(""); // Clear the error message at the start of the submission

    if (!username || !password) {
      setError("Please fill in all fields");
      return;
    }

    try {
      // Perform login API request
      await login({
        headers: {
          "Content-Type": "application/json",
        },
        body: {
          email: username,
          password,
        },
      });

      // Handle success response (assuming response contains user data)
      if (responseStatus === 200) {
        if (data) {
          // Store user data in localStorage
          localStorage.setItem("userId", data.userId || "");
          localStorage.setItem("email", username || "");
          localStorage.setItem("name", data.name || "");

          // Redirect to home page
          navigate("/");
        } else {
          console.warn("No data returned by the API.");
        }
      } else if (responseStatus === 404) {
        // Handle error if user not found
        setError("Invalid credentials, please try again.");
      } else {
        setError(apiError || "Please try again later!");
      }
    } catch (error) {
      // Handle network or unexpected errors
      console.error("Error during login:", error);
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

          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="username">Username</label>
              <input
                type="text"
                id="username"
                value={username}
                onChange={(e) => {
                  setUsername(e.target.value);
                  setError(""); // Reset error on input change
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
                  setError(""); // Reset error on input change
                }}
                placeholder="Enter your password"
                className="form-input"
              />
            </div>

            {error && <span className="error-message">{error}</span>}

            <button type="submit" className="login-button" disabled={loading}>
              {loading ? "Loading..." : "Sign In"}
            </button>
          </form>

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
