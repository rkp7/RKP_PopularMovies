package com.example.android.rkp_popularmovies;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment{
    // class variable for custom ArrayAdapter
    private MovieAdapter movieAdapter;
    // class variable for ArrayList which is the basis of custom ArrayAdapter
    private ArrayList<MovieItem> movieItemsArrayList;

    private static final int CODE_PREFERENCES = 1;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if(savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            movieItemsArrayList = new ArrayList<MovieItem>();
            updateMovieList();
        }
        else {
            movieItemsArrayList = savedInstanceState.getParcelableArrayList("movies");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds refresh option to menu.
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivityForResult(new Intent(getActivity(), SettingsActivity.class),
                    CODE_PREFERENCES);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CODE_PREFERENCES) {
            updateMovieList();
        }
    }

    void updateMovieList() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortCategory = sharedPreferences.getString(getString(R.string.pref_sort_key), getString(R.string.pref_popular_sort));

        FetchMovieTask movieTask = new FetchMovieTask();
        movieTask.execute(sortCategory);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", movieItemsArrayList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the reference to the root view
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Initialize the custom MovieAdapter reference for the list of MovieItem objects
        movieAdapter = new MovieAdapter(getActivity(), movieItemsArrayList);

        // Get a reference to the GridView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.movies_grid);
        gridView.setAdapter(movieAdapter);

        // Inflate the layout for this fragment
        return rootView;
    }


    public class FetchMovieTask extends AsyncTask<String, Void, MovieItem[]> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected MovieItem[] doInBackground(String... strings) {
            // We pass only one parameter. Condition checking is required.

            if(strings.length != 1) {
                return null;
            }

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJSONStr = null;

            final String BASE_URL = "https://api.themoviedb.org/3/movie/";
            final String SORT_CATEGORY = strings[0];

            try {

                // Construct the URL for TheMovieDB query
                Uri uri = Uri.parse(BASE_URL + SORT_CATEGORY).buildUpon()
                        .appendQueryParameter("api_key", BuildConfig.THE_MOVIE_DB_API_KEY).build();

                URL url = new URL(uri.toString());
                Log.v("URL: ", uri.toString());

                // Create the request to TheMovieDB, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                movieJSONStr = buffer.toString();
                Log.v("JSON: ", movieJSONStr);
                return getMovieDataFromJSON(movieJSONStr);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the movie data, there's no point in attemping
                // to parse it.
                return null;
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSON Error ", e);
            } catch (ParseException e) {
                Log.e(LOG_TAG, "Error ", e);
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(MovieItem[] movieItems) {
            if(movieItems != null) {
                movieAdapter.clear();
                //movieItemsArrayList.clear();
                for(MovieItem m: movieItems) {
                    movieAdapter.add(m);
                    //movieItemsArrayList.add(m);
                }
            }
        }

        /**
         * Parse JSON format and retrieve information regarding movies
         *
         * @param movieJSONStr
         * @return Array of objects of custom MovieItem class holding the movie details
         *          retrieved from TheMovieDB API
         * @throws JSONException
         * @throws ParseException
         */
        private MovieItem[] getMovieDataFromJSON(String movieJSONStr)
                throws JSONException, ParseException {
            // These are the names of the JSON objects that need to be extracted.
            final String TMDB_RESULTS = "results";
            final String TMDB_POSTER_PATH = "poster_path";
            final String TMDB_TITLE = "original_title";
            final String TMDB_ID = "id";
            final String TMDB_RELEASE_DATE = "release_date";
            final String TMDB_USER_RATING = "vote_average";
            final String TMDB_PLOT = "overview";

            // Parse the JSON format to obtain required data
            JSONObject movieJSON = new JSONObject(movieJSONStr);
            JSONArray movieArray = movieJSON.getJSONArray(TMDB_RESULTS);

            int noOfMovies = movieArray.length();
            MovieItem movieItems[] = new MovieItem[noOfMovies];

            // Obtain required movie details and store in custom MovieItem objects
            for(int i=0; i < noOfMovies; i++) {

                JSONObject movieObject = movieArray.getJSONObject(i);
                movieItems[i] = new MovieItem(movieObject.getInt(TMDB_ID),
                        movieObject.getString(TMDB_TITLE),
                        movieObject.getString(TMDB_POSTER_PATH),
                        movieObject.getString(TMDB_PLOT),
                        movieObject.getDouble(TMDB_USER_RATING),
                        convertStringToDate(movieObject.getString(TMDB_RELEASE_DATE)));

            }

            return  movieItems;
        }

        /**
         * Method for conversion of String to Date object type
         *
         * @param dateString
         * @return an object of Date type holding the equivalent date from String format
         * @throws ParseException
         */
        private Date convertStringToDate(String dateString) throws ParseException {
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date d = simpleDateFormat.parse(dateString);
            return d;
        }
    }
}
