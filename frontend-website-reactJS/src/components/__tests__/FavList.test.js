import React from "react";
import { render, cleanup, screen } from "@testing-library/react";
import FavMovieList from "../movielist/FavMovieList";
import { Routes, Route, BrowserRouter as Router } from "react-router-dom";
import "@testing-library/jest-dom";

test("Display movie list with logged in user", () => {
  const movies = [
    {
      Title: "The Magical World of Disney",
      Year: "1954–1997",
      Type: null,
      Poster:
        "https://m.media-amazon.com/images/M/MV5BNzEzMzQzMDc0Nl5BMl5BanBnXkFtZTcwMTk5ODczMQ@@._V1_SX300.jpg",
      Reviews: null,
      imdbID: "tt0046593",
    },
    {
      Title: "Dexter",
      Year: "2006–2013",
      Type: null,
      Poster:
        "https://m.media-amazon.com/images/M/MV5BZjkzMmU5MjMtODllZS00OTA5LTk2ZTEtNjdhYjZhMDA5ZTRhXkEyXkFqcGdeQXVyOTA3MTMyOTk@._V1_SX300.jpg",
      Reviews: null,
      imdbID: "tt0773262",
    },
  ];
  const user = {
    name: "test",
    emailId: "test@gmail.com",
    userId: "1234",
    watchList: ["IN_WATCHLIST", "tt0489270", "tt0387564"],
    movieWatchList: [
      {
        Title: "Saw - fav",
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
              <FavMovieList userId={"1234"} user={user} setUser={setUser} />
            }
          ></Route>
        </Routes>
      </Router>
    </div>
  );
  const favMovie = screen.getByTestId("del-fav-IN_WATCHLIST");
  // console.log(favMovie);
  expect(favMovie).toBeInTheDocument();
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
              <FavMovieList
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
  expect(screen.queryByTestId("rem-fav-IN_WATCHLIST")).toBeNull();
});
