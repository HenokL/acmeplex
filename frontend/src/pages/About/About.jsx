// This our about page
// NOTE for other Dev: This page got noting to do with backend, unless
// we want to store the team members data in the backend and fetch it here


import React from 'react';
import Footer from '../../components/Footer/Footer';
import { FaLinkedin, FaGithub } from 'react-icons/fa';
import './About.css';

const teamMembers = [
  {
    name: 'Henok L.',
    linkedin: 'https://www.linkedin.com/in/henoklamiso/',
    github: 'https://github.com/henokl',
    image: '/images/DevTeam/henok.jpeg'
  },
  {
    name: 'Riley K.',
    linkedin: 'https://www.linkedin.com/in/riley-koppang/',
    github: 'https://github.com/Koppang-Dev',
    image: '/images/DevTeam/riley.jpeg'
  },
  {
    name: 'Falmata O.',
    linkedin: 'https://www.linkedin.com/in/falmata-obsa-a3594b287/',
    github: 'https://github.com/falmatao',
    image: '/images/DevTeam/obsa.png'
  },
  {
    name: 'Yousef F.',
    linkedin: 'https://www.linkedin.com/in/youseffatouraee/',
    github: 'https://github.com/modulessoft',
    image: '/images/DevTeam/yousef.jpeg'
  },
];

const About = () => {
  return (
    <div className="about-container">
      <h1>Meet our Team</h1>
      <div className="team-grid">
        {teamMembers.map((member, index) => (
          <div className="team-member" key={index}>
            <div className="member-photo">
              <img
                src={member.image || `https://via.placeholder.com/150?text=${member.name.split(' ')[0]}`}
                alt={member.name}
              />
            </div>
            <h2>{member.name}</h2>
            <div className="social-links">
              <a href={member.linkedin} target="_blank" rel="noopener noreferrer">
                <FaLinkedin />
              </a>
              <a href={member.github} target="_blank" rel="noopener noreferrer">
                <FaGithub />
              </a>
            </div>
          </div>
        ))}
      </div>
      <Footer />
    </div>
  );
};

export default About;