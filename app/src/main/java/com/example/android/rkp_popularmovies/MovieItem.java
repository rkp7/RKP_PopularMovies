package com.example.android.rkp_popularmovies;

import java.util.Date;

/**
 * Created by RKP on 21-06-2016.
 */
public class MovieItem {
    int movieID;
    String originalTitle;
    String imageThumbnail;
    String plotSynopsis;
    double userRating;
    Date releaseDate;

    //Date releaseDate;

    /*
    constructor for Movie class to store
    the information in class variables
      */
    MovieItem(int mID, String title, String thumbnail, String overview,
              double rating, Date date) {
        this.movieID = mID;
        this.originalTitle = title;
        this.imageThumbnail = thumbnail;
        this.plotSynopsis = overview;
        this.userRating = rating;
        this.releaseDate = date;
    }
}
