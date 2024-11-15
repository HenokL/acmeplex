
import React, { useState } from 'react';
import { FaPlay } from 'react-icons/fa';
import { IoMdClose } from 'react-icons/io';
import './MovieCard.css';

const moviesData = [
    {
        id: 1,
        title: "Dune: Part Two",
        poster: "/images/dune2.jpg",
        trailer: "https://www.youtube.com/embed/U2Qp5pL3ovA"
    },
    {
        id: 2,
        title: "THE SUBSTANCE",
        poster: "/images/substance.jpg",
        trailer: "https://www.youtube.com/embed/LNlrGhBpYjc"
    },
    {
        id: 3,
        title: "KUNG FU PANDA 4",
        poster: "/images/kungfupanda4.jpg",
        trailer: "https://www.youtube.com/embed/ErpfwVIA2IY"
    },
    {
        id: 4,
        title: "READY OR NOT",
        poster: "/images/readyornot.jpg",
        trailer: "https://www.youtube.com/embed/ZtYTwUxhAoI"
    },
    {
        id: 5,
        title: "1992",
        poster: "/images/1992.jpg",
        trailer: "https://www.youtube.com/embed/mVSFFYCxavI"
    },
    {
        id: 6,
        title: "Moana 2",
        poster: "/images/moana2.jpg",
        trailer: "https://www.youtube.com/embed/hDZ7y8RP5HE"
    },
    {
        id: 7,
        title: "Spellbound",
        poster: "/images/spellbound.jpg",
        trailer: "https://www.youtube.com/embed/jGQiq1ZuCW8"
    },
    {
        id: 8,
        title: "Civil War",
        poster: "/images/civilwar.jpg",
        trailer: "https://www.youtube.com/embed/aDyQxtg0V2w?autoplay=1&mute=1&controls=0&loop=1&playlist=aDyQxtg0V2w&showinfo=0&vq=hd1080"
    },
    {
        id: 9,
        title: "Godzilla x Kong",
        poster: "/images/godzillakong.jpg",
        trailer: "https://www.youtube.com/embed/odM92ap8_c0"
    },
    {
        id: 10,
        title: "Kingdom of the Planet of the Apes",
        poster: "/images/apes.jpeg",
        trailer: "https://www.youtube.com/embed/XtFI7SNtVpY?autoplay=1&mute=1&controls=0&loop=1&playlist=XtFI7SNtVpY&showinfo=0&vq=hd1080"
    },
    {
        id: 11,
        title: "Ghostbusters: Frozen Empire",
        poster: "/images/ghostbusters.jpeg",
        trailer: "https://www.youtube.com/embed/HpOBXh02rVc?autoplay=1&mute=1&controls=0&loop=1&playlist=HpOBXh02rVc&showinfo=0&vq=hd1080"
    },
    {
        id: 12,
        title: "Deadpool & Wolverine",
        poster: "/images/deadpool.jpg",
        trailer: "https://www.youtube.com/embed/73_1biulkYk?autoplay=1&mute=1&controls=0&loop=1&playlist=73_1biulkYk&showinfo=0&vq=hd1080"
    }
];

const MovieCard = () => {
    const [selectedTrailer, setSelectedTrailer] = useState(null);

    return (
        <div className="movie-section">
            <h2>Movies</h2>
            <div className="movie-grid">
                {moviesData.map((movie) => (
                    <div key={movie.id} className="movie-card">
                        <img src={movie.poster} alt={movie.title} className="movie-poster" />
                        <div className="movie-info">
                            <h3>{movie.title}</h3>
                            <button 
                                className="trailer-button"
                                onClick={() => setSelectedTrailer(movie.trailer)}
                            >
                                <FaPlay /> Watch Trailer
                            </button>
                        </div>
                    </div>
                ))}
            </div>

            {selectedTrailer && (
                <div className="trailer-modal">
                    <div className="modal-content">
                        <button className="close-button" onClick={() => setSelectedTrailer(null)}>
                            <IoMdClose />
                        </button>
                        <iframe
                            src={`${selectedTrailer}?autoplay=1`}
                            title="Movie Trailer"
                            frameBorder="0"
                            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                            allowFullScreen
                        />
                    </div>
                </div>
            )}
        </div>
    );
};

export default MovieCard;

