import './Hero.css';
import Carousel from 'react-material-ui-carousel';
import { Paper } from '@mui/material';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCirclePlay } from '@fortawesome/free-solid-svg-icons';
import {Link, useNavigate} from "react-router-dom";
import Button from 'react-bootstrap/Button';


const Hero = ({movies}) => {

    const navigate = useNavigate();

    function reviews(movie)
    {
        console.log("Poster: "+movie.Poster +" imdbId: "+movie.imdbID);
        navigate(`/Reviews/${movie.imdbID}`, { state: { movie } });
    }

  return (
    <div className ='movie-carousel-container'>
      <Carousel>
        {
            movies?.map((movie) =>{
                console.log(movie);
                return(
                    
                    <Paper key={movie.imdbID}>
                        <div className = 'movie-card-container'>
                            <div className="movie-card" style={{"--img": `url(${movie.Poster})`}}>
                                <div className="movie-detail">
                                    <div className="movie-poster">
                                        <img src={movie.Poster} alt="" />
                                    </div>
                                    <div className="movie-title">
                                        <h4>{movie.Title}</h4>
                                    </div>
                                    <div className="movie-buttons-container">
                                        <div className="movie-review-button-container">
                                            <Button variant ="info" onClick={() => reviews(movie)} >Reviews</Button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </Paper>
                )
            })
        }
      </Carousel>
    </div>
  )
}

export default Hero
