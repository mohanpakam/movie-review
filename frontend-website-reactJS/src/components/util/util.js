// utils.js
import { useNavigate, useLocation } from 'react-router-dom';

  export const handleAddToFavorites = (movie, userId, user, setUser, resetMovieList) => {
    const imdbID  = movie.imdbID;
    console.log("imdbID:" + imdbID +"userId:" + userId);
    fetch(`http://localhost:8080/api/v1/users/add-watchlist/user/${userId}/movie/${imdbID}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then((response) => {
        if (response.ok) {
          console.log('Movie added to favorites:', movie);
        } else {
          console.error('Failed to add movie to favorites:', movie);
        }
      })
      .catch((error) => {
        console.error('Error adding movie to favorites:', error);
      });

      const updatedWatchList = [...user.watchList, movie.imdbID];
      setUser({ ...user, watchList: updatedWatchList });
  };

  export const handleRemoveFromFavorites = (movie, userId, user, setUser, setFavMovieList) => {

    const imdbID  = movie.imdbID;
    console.log("imdbID:" + imdbID +"userId:" + userId);
    fetch(`http://localhost:8080/api/v1/users/remove-watchlist/user/${userId}/movie/${imdbID}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then((response) => {
        if (response.ok) {
          console.log('Movie removed from favorites:', movie);
        } else {
          console.error('Failed to add movie to favorites:', movie);
        }
      })
      .catch((error) => {
        console.error('Error adding movie to favorites:', error);
      });
      const updatedWatchList = [...user.watchList];
      const index = updatedWatchList.indexOf(movie.imdbID);
      if (index !== -1) {
        updatedWatchList.splice(index, 1);
        setUser({ ...user, watchList: updatedWatchList });
      }
  };  

  export const getWatchListMovies = async (userId, setFavMovieList) =>{
    console.log("userId:" + userId);
    try
    {
      const response = await fetch(`http://localhost:8080/api/v1/users/user/${userId}/watchlist`).then((rsp)=>{
        console.log(rsp.data);
        setFavMovieList(rsp.data);
      });
    } 
    catch(err)
    {
      console.log(err);
    }
  };

  