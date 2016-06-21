package com.example.android.rkp_popularmovies;

/**
 * Created by RKP on 21-06-2016.
 */
public class MovieItem {
    String originalTitle;
    String imageThumbnail;
    String plotSynopsis;
    double userRating;
    String releaseDate;

    /*
    constructor for Movie class to store
    the information in class variables
      */
    MovieItem(String title, String thumbnail, String overview,
              double rating, String date) {
        this.originalTitle = title;
        this.imageThumbnail = thumbnail;
        this.plotSynopsis = overview;
        this.userRating = rating;
        this.releaseDate = date;
    }
}
