import React from 'react';
import { Link } from 'react-router-dom';
import './Footer.css';

const Footer = () => {
    return (
        <footer className="footer">
            <div className="footer-content">
            <div className="footer-section">
                <h3>About ACMEPLEX</h3>
                <p>
                ACMEPLEX is a Movie Theater Ticket Reservation App developed by Henok L., Riley K., Falmata O., 
                and Yousef F., MEng Software Engineering students at the University of Calgary. 
                This application was built as a group project for ENSF 614: Advanced Systems Analysis and Software Design.
                </p>
            </div>
                <div className="footer-section">
                    <h3>Quick Links</h3>
                    <ul>
                        <li><Link to="/">Home</Link></li>
                        <li><Link to="/cancel-ticket">Cancel Ticket</Link></li>
                        <li><Link to="/about">About Us</Link></li>
                    </ul>
                </div>
                <div className="footer-section">
                    <h3>Contact</h3>
                    <ul>
                        <li>Email: Acmeplex94@gmail.com</li>
                        <li>Phone: (123) 456-7890</li>
                    </ul>
                </div>
            </div>
            <div className="footer-bottom">
                <p>&copy; 2024 ACMEPLEX. All rights reserved.</p>
            </div>
        </footer>
    );
};

export default Footer;