package com.example.android.rkp_popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by RKP on 21-06-2016.
 */
public class MovieItem implements Parcelable{
    int movieID;
    String originalTitle;
    String imageThumbnail;
    String plotSynopsis;
    double userRating;
    Date releaseDate;

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

    protected MovieItem(Parcel in) {
        movieID = in.readInt();
        originalTitle = in.readString();
        imageThumbnail = in.readString();
        plotSynopsis = in.readString();
        userRating = in.readDouble();

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            releaseDate = simpleDateFormat.parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(movieID);
        parcel.writeString(originalTitle);
        parcel.writeString(imageThumbnail);
        parcel.writeString(plotSynopsis);
        parcel.writeDouble(userRating);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        parcel.writeString(simpleDateFormat.format(releaseDate));
    }
}
