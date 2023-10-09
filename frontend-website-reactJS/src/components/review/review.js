import React from 'react';
import {Row, Col} from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHeart } from '@fortawesome/free-solid-svg-icons';

const DisplayReview = ({ review}) => {
    const ratingIcons = Array.from({ length: review.rating }, (_, index) => (
        <FontAwesomeIcon key={index} icon={faHeart} color='red' />
      ));

  return ( 
    <> 
    <Row>
      <Col>
        <div>
          <p className="rating">Rating: {ratingIcons}</p>
          <p className="name">Name: {review.name}</p>
          <p  className="created-at">Created At: {review.createdAt}</p>
          <p className="comment">{review.comment}</p>
        </div>
      </Col>
    </Row>
    <Row>
                            <Col>
                                <hr />
                            </Col>
                        </Row>
    </>
  );
};

export default DisplayReview;