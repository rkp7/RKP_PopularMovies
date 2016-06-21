package com.example.android.rkp_popularmovies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private MovieAdapter movieAdapter;

    // Sample data identical to data which shall be retrieved from theMovieDB API
    MovieItem[] movieItem = {
            new MovieItem("X-Men: Apocalypse", "http://image.tmdb.org/t/p/w185//zSouWWrySXshPCT4t3UKCQGayyo.jpg",
                    "asdaS",8.5, "2016-12-12"),
            new MovieItem("Jurassic World", "http://image.tmdb.org/t/p/w185//jjBgi2r5cRt36xF6iNUEhzscEcb.jpg",
                    "asdaS",8.1, "2016-12-12"),
            new MovieItem("Warcraft", "http://image.tmdb.org/t/p/w185//ckrTPz6FZ35L5ybjqvkLWzzSLO7.jpg",
                    "asdaS",8.1, "2016-12-12"),
            new MovieItem("Finding Dory", "http://image.tmdb.org/t/p/w185//fCxP3bjrDuHLzagbgO3f27Nr2Kq.jpg",
                    "asdaS",8.1, "2016-12-12")
    };

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get the custom MovieAdapter reference for the list of MovieItem objects
        movieAdapter = new MovieAdapter(getActivity(), Arrays.asList(movieItem));

        // Get a reference to the GridView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.movies_grid);
        gridView.setAdapter(movieAdapter);

        // Inflate the layout for this fragment
        return rootView;
    }

}
