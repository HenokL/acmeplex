// src/app/components/LoginLayout.jsx
import React from 'react';

const RegisterLayout = ({ children }) => {
  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <div className="w-full max-w-sm p-8 bg-white rounded-lg shadow-md">
        {children}
      </div>
    </div>
  );
};

export default RegisterLayout;
