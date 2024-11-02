// components/DashboardLayout.jsx

import React from 'react';

const DashBoardLayout = ({ children }) => {
  return (
    <div className="flex">
      <aside className="w-1/4 bg-gray-800 text-white h-screen p-4">
        <h1 className="text-2xl mb-4">Movie Ticket Reservation</h1>
        <nav>
          <ul>
            <li className="mb-2">
              <a href="/dashboard" className="hover:underline">Home</a>
            </li>
            <li className="mb-2">
              <a href="/dashboard/movies" className="hover:underline">Upcoming Movies</a>
            </li>
            <li className="mb-2">
              <a href="/dashboard/reservations" className="hover:underline">My Reservations</a>
            </li>
            <li className="mb-2">
              <a href="/dashboard/profile" className="hover:underline">Profile</a>
            </li>
            <li className="mb-2">
              <a href="/logout" className="hover:underline">Logout</a>
            </li>
          </ul>
        </nav>
      </aside>
      <main className="flex-1 p-8">
        {children}
      </main>
    </div>
  );
};

export default DashBoardLayout;
