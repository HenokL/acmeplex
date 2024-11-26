/*
Note added by Henok:
This component is used to show a loading spinner when the isLoading prop is true.
*/
import React from 'react';
import './LoadingSpinner.css';

const LoadingSpinner = ({ isLoading }) => {
  if (!isLoading) return null;
  
  return (
    <div className={`loading-container ${isLoading ? 'show' : ''}`}>
      <div className="film-reel"></div>
      <div className="loading-text">Loading...</div>
    </div>
  );
};

export default LoadingSpinner;
