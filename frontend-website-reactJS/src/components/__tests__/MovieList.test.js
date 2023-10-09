import React from "react";
import { render, cleanup, screen } from "@testing-library/react";
import MovieList from "../movielist/MovieList";
import { Routes, Route, BrowserRouter as Router } from "react-router-dom";
import "@testing-library/jest-dom";

test("Display movie list with logged in user", () => {
  const movies = [
    {
      Title: "Saw",
      Year: "2004",
      Type: "movie",
      Poster:
        "https://m.media-amazon.com/images/M/MV5BM2M1MzI1MWYtYmM2ZC00OWY3LTk0ZGMtNmRkNzU1NzEzMWE5XkEyXkFqcGdeQXVyODUwOTkwODk@._V1_SX300.jpg",
      Reviews: null,
      imdbID: "IN_WATCHLIST",
    },
    {
      Title: "Saw - unfav",
      Year: "2004",
      Type: "movie",
      Poster:
        "https://m.media-amazon.com/images/M/MV5BM2M1MzI1MWYtYmM2ZC00OWY3LTk0ZGMtNmRkNzU1NzEzMWE5XkEyXkFqcGdeQXVyODUwOTkwODk@._V1_SX300.jpg",
      Reviews: null,
      imdbID: "NOT_IN_WATCHLIST",
    },
  ];
  const user = {
    name: "test",
    emailId: "test@gmail.com",
    userId: "1234",
    watchList: ["IN_WATCHLIST", "tt0489270", "tt0387564"],
    movieWatchList: [
      {
        Title: "Saw - unfav",
        Year: "2004",
        Type: "movie",
        Poster:
          "https://m.media-amazon.com/images/M/MV5BM2M1MzI1MWYtYmM2ZC00OWY3LTk0ZGMtNmRkNzU1NzEzMWE5XkEyXkFqcGdeQXVyODUwOTkwODk@._V1_SX300.jpg",
        Reviews: null,
        imdbID: "IN_WATCHLIST",
      },
    ],
  };

  const setUser = jest.fn();
  render(
    <div className="test-container">
      <Router>
        <Routes>
          <Route
            path="/"
            element={
              <MovieList
                movies={movies}
                userId={user.userId}
                user={user}
                setUser={setUser}
              />
            }
          ></Route>
        </Routes>
      </Router>
    </div>
  );
  const favMovie = screen.getByTestId("rem-fav-IN_WATCHLIST");
  expect(favMovie).toBeInTheDocument();
  const newMovie = screen.getByTestId("add-fav-NOT_IN_WATCHLIST");
  expect(newMovie).toBeInTheDocument();
});

test("Display movie list without logged in user", () => {
  const movies = [
    {
      Title: "Saw",
      Year: "2004",
      Type: "movie",
      Poster:
        "https://m.media-amazon.com/images/M/MV5BM2M1MzI1MWYtYmM2ZC00OWY3LTk0ZGMtNmRkNzU1NzEzMWE5XkEyXkFqcGdeQXVyODUwOTkwODk@._V1_SX300.jpg",
      Reviews: null,
      imdbID: "tt0387564",
    },
  ];
  const user = {};
  const userId = null;
  const setUser = jest.fn();
  render(
    <div className="test-container">
      <Router>
        <Routes>
          <Route
            path="/"
            element={
              <MovieList
                movies={movies}
                userId={userId}
                user={user}
                setUser={setUser}
              />
            }
          ></Route>
        </Routes>
      </Router>
    </div>
  );
  const movieList = screen.getByTestId("Saw");
  expect(movieList).toBeInTheDocument();
  expect(movieList).toHaveTextContent("Saw");
});
