import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faVideoSlash } from "@fortawesome/free-solid-svg-icons";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import { Link, NavLink, useNavigate, Form } from "react-router-dom";
import { useState } from "react";
import { debounce } from "lodash";

const Header = ({
  user,
  setUser,
  movie,
  setMovie,
  getMovies,
  setUserId,
  setMyReview,
}) => {

  const navigate = useNavigate();

  function handleSignOut(event) {
    setUser({});
    setMyReview({});
    setUserId(null);
    document.getElementById("signInDiv").hidden = false;
    navigate("/"); // Navigate to the Home page
  }
  const handleOnChange = debounce((query) => {
    getMovies(query);
  }, 700);

  return (
    <Navbar bg="dark" variant="dark" expand="lg">
      <Container fluid>
        <Navbar.Brand as={Link} to="/" style={{ color: "gold" }}>
          <FontAwesomeIcon icon={faVideoSlash} />
          Gold
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="navbarScroll" />
        <Navbar.Collapse id="navbarScroll">
          <Nav
            className="me-auto my-2 my-lg-0"
            style={{ maxHeight: "100px" }}
            navbarScroll
          >
            {user.name && (
              <>
                <NavLink className="nav-link" to="/">
                  Home
                </NavLink>
                <NavLink className="nav-link" to="/watchList">
                  Watchlist
                </NavLink>
              </>
            )}
            <input
              type="text"
              value={movie}
              style={{ width: "300px" }}
              className="form-control"
              placeholder="Search a movie ..."
              onChange={(event) => handleOnChange(event.target.value)}
            />
          </Nav>
          <div id="signInDiv">{console.log(user)}</div>
          {user.name && (
            <>
              <h3>{user.name}</h3>
              <img src={user.picture}></img>
              <Button onClick={(e) => handleSignOut(e)}>Sign Out</Button>
            </>
          )}
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Header;
