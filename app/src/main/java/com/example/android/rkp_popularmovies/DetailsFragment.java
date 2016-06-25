package com.example.android.rkp_popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;


/**
 * Created by RKP on 25-06-2016.
 */
public class DetailsFragment extends Fragment {

    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Base URL for retrieving the poster image
        final String BASE_URL_FOR_POSTERS = "http://image.tmdb.org/t/p/w185/";

        // Gets the current context for later usage
        Context currentContext = getContext();

        // Get reference to the root view of layout for DetailsFragment
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        // Get the intent reference and check for Movie data
        Intent intent = getActivity().getIntent();
        if(intent != null && intent.hasExtra("Movie")) {
            // Obtain parcelable MovieItem objects
            Bundle b = intent.getExtras();
            MovieItem movieItem = (MovieItem) b.getParcelable("Movie");

            // Load the poster image into the ImageView
            ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_poster_imageview);
            Picasso.with(currentContext).load(BASE_URL_FOR_POSTERS + movieItem.imageThumbnail)
                    .into(imageView);

            // Set the title of the movie in the TextView
            TextView titleTextView = (TextView) rootView.findViewById(R.id.movie_title_textview);
            titleTextView.setText(movieItem.originalTitle);

            // Set the date of the movie in the TextView
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            TextView releaseDateTextView = (TextView) rootView.
                        findViewById(R.id.release_date_textview);
            releaseDateTextView.setText("Release Date: " +
                    simpleDateFormat.format(movieItem.releaseDate));

            // Set the rating of the movie in the TextView
            TextView voteAverageTextView = (TextView) rootView.findViewById(R.id.movie_rating_textview);
            voteAverageTextView.setText("Rating: " + Double.toString(movieItem.userRating));

            // Set the synopsis of the movie in the TextView
            TextView overviewTextView = (TextView) rootView.findViewById(R.id.plot_synopsis_textview);
            overviewTextView.setText("Overview: \n\n" + movieItem.plotSynopsis);
        }

        return rootView;
    }
}
