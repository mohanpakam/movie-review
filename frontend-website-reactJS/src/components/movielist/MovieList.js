import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Container from 'react-bootstrap/Container';
import {Row, Col, Card} from 'react-bootstrap';
import './MovieList.css';
import { handleAddToFavorites, handleRemoveFromFavorites, handleMovieClick } from '../util/util';



const MovieList = ({ movies, userId, user, setUser }) => {
    console.log("User:" + JSON.stringify(user.watchList));
  const navigate = useNavigate();
  const [selectedMovie, setSelectedMovie] = useState(null);

  const handleMovieClick = (movie) => {
    navigate(`/Reviews/${movie.imdbID}`, { state: { movie } });
  };

  return (
    <Container>
      <Row xs={1} sm={2} md={3} lg={4} xl={6} className="g-4">
        {movies.map((movie) => (

          <Col key={movie.imdbID}>
            <Card>
              <div
                key={movie.imdbID}
                data-testid={movie.imdbID}
                className="movie-poster"
                onClick={() => handleMovieClick(movie)}
              >
                <Card.Img variant="top" src={movie.Poster} />
                <div className="movie-title-overlay" data-testid={movie.Title} >{movie.Title}</div>
              </div>
              { userId && (!user.watchList || !user.watchList.includes(movie.imdbID)) ? (
                <div className="card-button-container" data-testid={`add-fav-${movie.imdbID}`}>
                <button className="card-button" onClick={() => handleAddToFavorites(movie, userId, user, setUser)}>
                  Add to Favorites
                </button></div>
              ): ( userId && 
                <div className="card-button-container" data-testid={`rem-fav-${movie.imdbID}`}>
                <button className="card-button" onClick={() => handleRemoveFromFavorites(movie, userId, user, setUser)}>
                  Remove from Favorites
                </button></div>
              )
              }
              
            </Card>
          </Col>
        ))}
      </Row>
    </Container>
  );
};

export default MovieList;