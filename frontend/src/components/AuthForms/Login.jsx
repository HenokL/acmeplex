// Login.jsx
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import Footer from '../../components/Footer/Footer';
import './Login.css';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!username || !password) {
      setError('Please fill in all fields');
      return;
    }
    // TODO NOTE for Yousef: Add the API call here to Login
    console.log('Login attempt:', { username });
  };

  return (
    <div className="login-page">
      <div className="login-container">
        <div className="login-form">
          <h1>Hello!</h1>
          <p className="form-description">
            Sign in to continue to ACMEPLEX
          </p>
          
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="username">
                Username
              </label>
              <input
                type="text"
                id="username"
                value={username}
                onChange={(e) => {
                  setUsername(e.target.value);
                  setError('');
                }}
                placeholder="Enter your username"
                className="form-input"
              />
            </div>

            <div className="form-group">
              <label htmlFor="password">
                Password
              </label>
              <input
                type="password"
                id="password"
                value={password}
                onChange={(e) => {
                  setPassword(e.target.value);
                  setError('');
                }}
                placeholder="Enter your password"
                className="form-input"
              />
            </div>

            {error && <span className="error-message">{error}</span>}

            <button type="submit" className="login-button">
              Sign In
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