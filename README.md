# Requirements 

	Please create and share via github a project that implements a simple CRUD system. That is to say, create a UI that connects to middleware that connects to a data store, and is capable of creating, updating, deleting and listing data.
	An HTML based UI that is implemented with:
	AngularJS (or React or even jQuery) for SPA (or another front end)
	Bootstrap for styling (or some other modern UI styling/component framework)
	Unit, Functional and/or End to End tests in any framework
 

	Middleware that models at least two entities in a master detail relationship and is called from the UI above
	Use Spring Boot, or legacy Spring, or any modern application framework
	Model any master detail relationship, i.e. Customers -> Addresses or Foo -> Bar  ( bonus points if you model a many to many relationship, i.e. Addresses <- Customers <-> Offices -> Addresses )
	Provide a REST like API to the UI (at least implement GET and POST, PUT and DELETE optional) that exposes CRUD functionality
	Some unit tests from any test framework (JUnit, TestNG, Spock etc)
 
	A data store to back the model from the middleware

	It can be in memory like H2 or Schemaless like MongoDB or Redis or structured like any-SQL, if required provide any scripts to create the data structures/tables

	Below are the requirements of the project
	Here are the revised and corrected sentences:

# Scope of Work
Design and develop a "Movie Review/Watchlist" Web application where a User can Login using Google Authentication, Search for Movies, Leave a review and Maintain a Watchlist of Favourite movies.

AC1: **Public Page to Display "Disney" Movies by Default**
   - **Given:** A guest.
   - **When:** They land on the home page.
   - **Then:** Ten movies should be visible by default when searching for the keyword "Disney."

AC2: **View Reviews About a Movie**
   - **Given:** A guest.
   - **When:** They click on a movie poster.
   - **Then:** Display all reviews for that movie.

AC3: **Search for a Movie**
   - **Given:** A guest.
   - **When:** They enter a name in the "Search" box.
   - **Then:** Display ten movies that contain the entered name.

AC4: **Google Login**
   - **Given:** A guest.
   - **When:** They click the "Sign In" button at the top right corner.
   - **Then:** The guest should be able to successfully log in to the "Movie Reviewer" app after authenticating with Google.

AC5: **Watchlist Page**
   - **Given:** A guest.
   - **When:** They successfully log in to the app.
   - **Then:** Display the same results that were shown based on the search or default "Disney" movies.
   - **And:** Each tile should display an "Add to Watchlist" button if the movie is not in the user's watchlist.
   - **And:** Each tile should display a "Remove from Watchlist" button if the movie is in the watchlist.

AC6: **Ability to Add to Watchlist**
   - **Given:** A user.
   - **When:** They click the "Add to Watchlist" button.
   - **Then:** Add that movie to the user's watchlist.

AC7: **Ability to Remove from Watchlist on Search Screen**
   - **Given:** A user.
   - **When:** They click the "Remove from Watchlist" button.
   - **Then:** Remove that movie from the user's watchlist.

AC8: **Display All Watchlist Items on Watchlist Screen**
   - **Given:** A user.
   - **When:** They click the "Watchlist" link in the header section.
   - **Then:** Display all the movies that the user added to their watchlist.

AC9: **Ability to Remove from Watchlist on Watchlist Screen**
   - **Given:** A user.
   - **When:** They click the "Remove from Watchlist" button.
   - **Then:** Remove that movie from the user's watchlist.

AC10: **Rate a Movie**
   - **Given:** A user.
   - **When:** They click on a movie poster.
   - **Then:** Display a form where the user can rate the movie (out of 5), enter a comment, and submit, if the user hasn't already written a review for that movie.

AC11: **Restrict Users from Entering Multiple Reviews**
   - **Given:** A user.
   - **When:** They click on a movie poster for which they have previously written a review.
   - **Then:** Display reviews from all users, including the logged-in user.

AC12: **Sign Out**
   - **Given:** A user.
   - **When:** They click the "Sign Out" button.
   - **Then:** Navigate to the Search/Home page, including any search results.

# Technology Stack
1. Spring Boot
2. React JS
3. Docker
4. MongoDB

# Note Worthy Mentions

# Installation

# Comments
