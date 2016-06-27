# RKP_PopularMovies

Project 1 in Udacity's Android Developer Nanodegree program

Android application which fulfills following requirements:

1. Upon launch, present the user with an grid arrangement of movie posters.
2. Allow your user to change sort order via a setting:

      a. The sort order can be by most popular, or by top rated
3. Allow the user to tap on a movie poster and transition to a details screen with additional information such as:

      a. original title

      b. movie poster image thumbnail

      c. A plot synopsis (called overview in the api)

      d. user rating (called vote_average in the api)

      e. release date


The Movie Database (www.themoviedb.org) API has been used for retrieving details of movies. The API key needs to be inserted in build.gradle (Module: App) file as the value of 'THE_MOVIE_DB_API_KEY'. You can obtain your API key from the mentioned website.

This project uses Picasso, a powerful library that helps to handle image loading and caching
