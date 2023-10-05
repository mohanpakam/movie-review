# Requirements 
Below are the requirements of the project
1 - Scenario: User Registration

    Given: 	a new user arrives at the registration page
    When: 	they fill out the registration form with valid information
		And they click the "Register" button
    Then:	they should be registered as a user
		and they should be logged in to their account


2 - Scenario: Adding a Movie
  
    Given: 	a logged-in user wants to add a new movie
    When: 	they navigate to the "Add Movie" page
    		And they fill out the movie details form
    		And they click the "Add Movie" button
    Then:	the movie should be added to the system
    		And they should be redirected to the movie details page

3 - Scenario: Reviewing a Movie

    Given:	a logged-in user wants to review a movie
    When:	they navigate to the "Review Movie" page
    		And they select a movie to review
    		And they provide a rating and a review comment
    		And they click the "Submit Review" button
    Then: 	the review should be associated with the movie and the user
    		And the movie's average rating should be updated

4 - Scenario: Viewing Movie Details

    Given:	a user wants to view the details of a movie
    When:	they go to the movie details page
    Then:	they should see information about the movie
    		And they should see a list of reviews for that movie

5 - Scenario: User Profile

    Given:	a logged-in user wants to view their profile
    When: 	they go to their profile page
    Then:	they should see their user details
    		And a list of movies they have reviewed

6 - Scenario: Many-to-Many Relationship

    Given: 	multiple users have reviewed multiple movies
    When: 	viewing a movie's details
    Then: 	the list of reviewers should include the names of users who reviewed the movie

# Technology Stack
1. Spring Boot
2. React JS
3. Docker
4. MongoDB
