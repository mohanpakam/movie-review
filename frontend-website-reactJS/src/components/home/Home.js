import Hero from '../hero/Hero';

const Home = ({movies, setMovie}) => {
  return (
    <Hero movies = {movies} setMovie = {setMovie} />
  )
}

export default Home
