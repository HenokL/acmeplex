// app/dashboard/page.jsx

import React from 'react';
import DashBoardLayout from './layout';

const dashboard = () => {
  return (
    <DashBoardLayout>
      <h2 className="text-3xl font-bold mb-4">Welcome to Your Dashboard!</h2>
      <p>Here you can manage your movie reservations and view upcoming movies.</p>
      {/* You can add more components like statistics, notifications, etc. */}
    </DashBoardLayout>
  );
};

export default dashboard;
