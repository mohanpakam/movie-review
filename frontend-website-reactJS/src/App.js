import './App.css';
import api from './api/axiosConfig';
import {useState, useEffect} from 'react';
import Layout from './components/Layout';
import {Routes, Route, useNavigate} from 'react-router-dom';
import Home from './components/home/Home';
import Header from './components/header/Header';
import Trailer from './components/trailer/Trailer';
import Reviews from './components/reviews/Reviews';
import NotFound from './components/notFound/NotFound';
import jwtDecode from 'jwt-decode';
import MovieList from './components/movielist/MovieList';
import FavMovieList from './components/movielist/FavMovieList';


function App() {

  const navigate = useNavigate();
  const [movies, setMovies] = useState([]);
  const [movie, setMovie] = useState();
  const [reviews, setReviews] = useState([]);
  const [myReview, setMyReview] = useState([]);
  const [user, setUser] = useState({});
  const [userId, setUserId] = useState();

  const getMovies = async (movieName) =>{
    navigate('/');
    console.log(movieName)
    try
    {
      const response = await api.get('http://localhost:8080/api/v1/movies/omdb/' + movieName).then((rsp)=>{
        setMovies(rsp.data);
        console.log(rsp.data);
      });
    } 
    catch(err)
    {
      console.log(err);
    }
  }

  const createUser = async(user) =>{
    console.log("creating user")
    const postData = {
      name: user.name,
      emailId: user.email,
    };
    try 
    {
        console.log(postData);
        const response = (await api.post('http://localhost:8080/api/v1/users/new',postData, {
          headers: {
            'Content-Type': 'application/json'
          },
        }));
        console.log(response.data);
        setUserId(response.data.userId);
        setUser(response.data);
        console.log("userId:" + response.data.watchList);
    } 
    catch (error) 
    {
      console.error(error);
    }
  }

  function hadleCallbackResponse(response){
    console.log("Encoded JWT ID Token: " + response.credential);
    var userObj = jwtDecode(response.credential);

    setUser(userObj);
    createUser(userObj)
    document.getElementById("signInDiv").hidden = true;
    // document.getElementById("userInfo").hidden = false;
    navigate('/'); // Navigate to the Home page
  }

  useEffect(() => {
    // document.getElementById("userInfo").hidden = true;
    console.log("before the google accounts id")
    /* global google */
    google.accounts.id.initialize({
      client_id: "1047033251798-2apa1t6oujcopd0gminiv4mdagc5afds.apps.googleusercontent.com",
      callback : hadleCallbackResponse
    });

    google.accounts.id.renderButton(
      document.getElementById("signInDiv"),
      {
        theme: "outline", size: "large"
      }
    )
    console.log("after the google accounts id")

    getMovies('disney');
  },[])

  return (
    <div className="App">

      <Header user= {user} setUser ={setUser} movie = {movie} setMovie={setMovie} getMovies={getMovies} setUserId = {setUserId} setMyReview = {setMyReview}/>
      <Routes>
          <Route path="/" element={<Layout/>}>
            
            <Route path="/" element={<MovieList movies={movies} userId={userId} user = {user} setUser = {setUser} />}></Route>
            <Route path="/Reviews/:movieId" element ={<Reviews userId = {userId} myReview = {myReview} setMyReview = {setMyReview}/>}></Route>
            <Route path="/watchList" element={<FavMovieList userId={userId} user = {user} setUser = {setUser} watch={true} />}></Route>
            <Route path="*" element = {<NotFound/>}></Route>
          </Route>
      </Routes>

    </div>
  );
}

export default App;
