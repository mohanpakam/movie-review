import {useEffect, useRef, useState} from 'react';
import api from '../../api/axiosConfig';
import {useParams} from 'react-router-dom';
import {Container, Row, Col} from 'react-bootstrap';
import ReviewForm from '../reviewForm/ReviewForm';
import React from 'react'
import DisplayReview from '../review/review';
import { useLocation } from 'react-router-dom';

const Reviews = ({userId, myReview, setMyReview}) => {

    const location = useLocation();
    const movie = location.state.movie;
    const [rating, setRating]  = useState(0);
    const [revText, setRevText]  = useState("");
    const [reviews, setReviews] = useState([]);
    //const revText = useRef();
    let params = useParams();
    const omdbId = params.movieId;
    console.log("movie:" + movie.Poster);

    useEffect(()=>{
        getMyReview(userId, omdbId);
        getReviews(omdbId);
    },[])

    const addReview = async (e) =>{
        e.preventDefault();
        const reviewObj = {userId: userId, comment:revText, imdbId:omdbId, movieId : omdbId, rating: rating};

        try
        {
            const response = await api.post("http://localhost:8080/api/v1/reviews",reviewObj);
            console.log("Response:" + response.data);

            const updatedReviews = [...reviews, response.data];
    
            setMyReview(response.data);
            setRevText("");    
            setReviews(updatedReviews);
        }
        catch(err)
        {
            console.error(err);
        }

    }

    const getMyReview = async (userId, imdbId) => {
        if(!userId)
            return;
        try 
        {
            const response = await api.get(`http://localhost:8080/api/v1/reviews/user/${userId}/movie/${imdbId}`);
            console.log("My Review retrieved:"+ response.data);
            setMyReview(response.data);
        } 
        catch (error) 
        {
            setMyReview([]);
          console.error(error);
        }
      }

    const getReviews = async (omdbId) => {
        console.log("ImDB Id:" + omdbId);
        try 
        {
            const response = await api.get(`http://localhost:8080/api/v1/reviews/all/movie/${omdbId}`);
            console.log("Reviews retrieved:"+ response.data);
            setReviews(response.data);
        } 
        catch (error) 
        {
          console.error(error);
        }
      }
      console.log(reviews) 
  return (
    <Container>
        <Row>
            <Col><h3>Reviews</h3></Col>
        </Row>
        <Row className="mt-2">
            <Col>
                <img src={movie?.Poster} alt="" />
            </Col>
            <Col>
                { userId && myReview.reviewId? (
                    <>
                    <Row><Col><h2>Your Review:</h2></Col></Row>
                    <Row>
                            <Col>
                                <hr />
                            </Col>
                        </Row>
                <Row>
                <Col>
                    <DisplayReview review={myReview} />     
                </Col>
            </Row></>) : userId &&
                    <>
                        <Row>
                            <Col>
                                <ReviewForm handleSubmit={addReview} 
                                    revText={revText} rating={rating} setRevText={setRevText} setRating={setRating} labelText = "Write a Review?" />  
                            </Col>
                        </Row>
                        <Row>
                            <Col>
                                <hr />
                            </Col>
                        </Row>
                    </>
                }
                {
                    reviews.length === 0 ? (
                    <p>No reviews yet.</p>
                    ) : (
                    reviews?.map((r) => {
                        console.log(!myReview || r.reviewId !== myReview.reviewId);
                        return (!myReview || r.reviewId !== myReview.reviewId) ?
                                <DisplayReview key={r.reviewId} review={r} />
                            : null;
                    }))
                }
            </Col>
        </Row>
        <Row>
            <Col>
                <hr />
            </Col>
        </Row>        
    </Container>
  )
}

export default Reviews
