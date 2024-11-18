/**
 * Home component - Main landing page displaying banner, movie cards and ticket booking option
 * Comment added by Henok L
 */

import React from 'react'; // Just importing react
import Banner from '../../components/Banner/Banner';
import './Home.css';
import MovieCard from '../../components/MovieCard/MovieCard';
import GetTicket from '../../components/GetTicket/GetTicket';
import Footer from '../../components/Footer/Footer';


// this will render the main page layout with banner, ticket button, movie listings and footer
const Home = () => {
  return (
    <div className="home">
      <Banner/>
      <GetTicket />
      <MovieCard/>
      <Footer/>
    </div>
  );
};

export default Home;