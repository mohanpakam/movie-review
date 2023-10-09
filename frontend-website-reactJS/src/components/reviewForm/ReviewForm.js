import { useState } from 'react';
import {Form,Button} from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHeart } from '@fortawesome/free-solid-svg-icons';
import './reviewform.css';

const ReviewForm = ({handleSubmit,revText,labelText,defaultValue, setRevText,rating, setRating}) => {


  const handleRevTextChange = (event) => {
    setRevText(event.target.value);
  };
return (

    <Form>
        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
            <Form.Label>{labelText}</Form.Label>
            <Form.Label>Rating:</Form.Label>
        <div>
          {[1, 2, 3, 4, 5].map((value) => (
            <FontAwesomeIcon
            key={value}
            icon={faHeart}
            className={`heart-icon ${rating >= value ? 'selected' : ''}`}
            onClick={() => setRating(value)}
          />
          ))}
        </div>
            <Form.Control onChange={handleRevTextChange} as="textarea" rows={3} defaultValue={defaultValue} />
        </Form.Group>
        <Button variant="outline-info" onClick={handleSubmit}>Submit</Button>
    </Form>

  )
}

export default ReviewForm
