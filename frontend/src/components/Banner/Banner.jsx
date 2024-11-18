/**
 * Banner component - Displays rotating movie trailers with navigation controls
 * Comment added by Henok L
 */

import React, { useState, useEffect } from 'react';
import { FiChevronLeft, FiChevronRight } from 'react-icons/fi';
import './Banner.css';

// Movie content data with trailers and titles
// I am using just moke movies and here and we can replace this with actual data from the backend
const movieContent = [
    {
        trailer: "https://www.youtube.com/embed/U2Qp5pL3ovA?autoplay=1&mute=1&controls=0&loop=1&playlist=U2Qp5pL3ovA&showinfo=0&vq=hd1080",
        title: "Dune: Part Two"
    },
    {
        trailer: "https://www.youtube.com/embed/LNlrGhBpYjc?autoplay=1&mute=1&controls=0&loop=1&playlist=LNlrGhBpYjc&showinfo=0&vq=hd1080",
        title: "THE SUBSTANCE"
    },
    {
        trailer: "https://www.youtube.com/embed/ErpfwVIA2IY?autoplay=1&mute=1&controls=0&loop=1&playlist=ErpfwVIA2IY&showinfo=0&vq=hd1080",
        title: "KUNG FU PANDA 4"
    },

    {
        trailer: "https://www.youtube.com/embed/ZtYTwUxhAoI?autoplay=1&mute=1&controls=0&loop=1&playlist=ZtYTwUxhAoI&showinfo=0&vq=hd1080",
        title: "READY OR NOT"
    },
    
    {
        trailer: "https://www.youtube.com/embed/mVSFFYCxavI?autoplay=1&mute=1&controls=0&loop=1&playlist=mVSFFYCxavI&showinfo=0&vq=hd1080",
        title: "1992"
    },
    {
        trailer: "https://www.youtube.com/embed/hDZ7y8RP5HE?autoplay=1&mute=1&controls=0&loop=1&playlist=hDZ7y8RP5HE&showinfo=0&vq=hd1080",
        title: "Moana 2"
    },
    {
        trailer: "https://www.youtube.com/embed/jGQiq1ZuCW8?autoplay=1&mute=1&controls=0&loop=1&playlist=jGQiq1ZuCW8&showinfo=0&vq=hd1080",
        title: "Spellbound"
    }
];

const Banner = () => {
    const [currentSlide, setCurrentSlide] = useState(0);

    const nextSlide = () => {
        setCurrentSlide((prev) => (prev + 1) % movieContent.length);
    };

    const prevSlide = () => {
        setCurrentSlide((prev) => (prev === 0 ? movieContent.length - 1 : prev - 1));
    };

    const goToSlide = (index) => {
        setCurrentSlide(index);
    };

    // Auto-rotation effect for slides
    useEffect(() => {
        const timer = setInterval(nextSlide, 7000);
        return () => clearInterval(timer);
    }, []);

    // This part will just Render the banner component
    return (
        <div className="banner-container">
            <div className="banner">
                {movieContent.map((movie, index) => (
                    <div
                        key={index}
                        className={`banner-slide ${index === currentSlide ? 'active' : ''}`}
                    >
                        <iframe
                            className="banner-video"
                            src={movie.trailer}
                            title={`${movie.title} Trailer`}
                            frameBorder="0"
                            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                            allowFullScreen
                        />
                        <div className="banner-overlay"></div>
                        <h2 className="movie-title">{movie.title}</h2>
                    </div>
                ))}
                
                <button className="nav-button prev" onClick={prevSlide}>
                    <FiChevronLeft />
                </button>
                <button className="nav-button next" onClick={nextSlide}>
                    <FiChevronRight />
                </button>
                
                <div className="slide-indicators">
                    {movieContent.map((_, index) => (
                        <button
                            key={index}
                            className={`indicator ${index === currentSlide ? 'active' : ''}`}
                            onClick={() => goToSlide(index)}
                            aria-label={`Go to slide ${index + 1}`}
                        />
                    ))}
                </div>
            </div>
        </div>
    );
};

export default Banner;
