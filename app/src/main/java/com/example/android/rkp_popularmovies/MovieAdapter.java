package com.example.android.rkp_popularmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by RKP on 21-06-2016.
 */
public class MovieAdapter extends ArrayAdapter<MovieItem> {

    /**
     * Custom constructor for customizing adapter
     * The context is used to inflate the layout file, and the List is the data we want
     * to populate into the lists
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param movies         A List of MovieItem objects
     */
    public MovieAdapter(Activity context, List<MovieItem> movies) {
        super(context, 0, movies);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The AdapterView position that is requesting a view
     * @param convertView The recycled view to populate.
     *                    (search online for "android view recycling" to learn more)
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        // Base URL for retrieving the poster image
        final String BASE_URL_FOR_POSTERS = "http://image.tmdb.org/t/p/w185/";

        // Gets the MovieItem object from the ArrayAdapter at the appropriate position
        MovieItem movie = getItem(position);
        // Gets the current context for later usage
        Context currentContext = getContext();

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if(convertView == null) {
            convertView = LayoutInflater.from(currentContext).inflate(
                    R.layout.movie_item, parent, false);
        }

        // Obtain the ImageView of poster thumnail for future reference
        ImageView thumbnailView = (ImageView) convertView.findViewById(R.id.movie_image_thumbnail);


        // Load the poster thumbnail using Picasso in the above referenced ImageView
        Picasso.with(currentContext).load(BASE_URL_FOR_POSTERS + movie.imageThumbnail).into(thumbnailView);

        return convertView;
    }
}
