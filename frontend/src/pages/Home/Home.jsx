
import React from 'react';
import Banner from '../../components/Banner/Banner';
import './Home.css';
import MovieCard from '../../components/MovieCard/MovieCard';
import Footer from '../../components/Footer/Footer';

const Home = () => {
  return (
    <div className="home">
      <Banner/>
      <MovieCard/>
      <Footer/>
    </div>
  );
};

export default Home;