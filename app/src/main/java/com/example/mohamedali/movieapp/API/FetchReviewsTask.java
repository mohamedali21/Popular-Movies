package com.example.mohamedali.movieapp.API;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.mohamedali.movieapp.API.FetchMovieTask;
import com.example.mohamedali.movieapp.BuildConfig;
import com.example.mohamedali.movieapp.MovieModel;
import com.example.mohamedali.movieapp.NetworkResponse;
import com.example.mohamedali.movieapp.data.MovieHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Ali on 8/31/2016.
 */
public class FetchReviewsTask extends AsyncTask<String,Void,String> {
    private final String LOG_TAG = FetchMovieTask.class.getSimpleName();
    NetworkResponse networkResponse;
    Context context;
    public FetchReviewsTask(Context con){
        context=con;
    }

    public void setNetworkResponse(NetworkResponse networkResponse) {
        this.networkResponse = networkResponse;
    }

    @Override
    protected String doInBackground(String... params) {

        // If there's no zip code, there's nothing to look up.  Verify size of params.
        if (params.length == 0) {
            return null;
        }

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String movieJsonStr = null;

        try {
            // Construct the URL for the themoviedb query
            // Possible parameters are avaiable at OWM's movie API page, at
            // http://themoviedb.org/API#movie
            final String MOVIE_BASE_URL =
                    "http://api.themoviedb.org/3/movie/";
            final String s="/reviews";
            final String APPID_PARAM = "api_key";

            Uri builtUri = Uri.parse(MOVIE_BASE_URL+params[0]+s).buildUpon()
                    .appendQueryParameter(APPID_PARAM, BuildConfig.OPEN_MOVIE_DB_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());

            Log.v(LOG_TAG, "Built URI " + builtUri.toString());

            // Create the request to themoviedb, and open the connection
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
                // buffer for ddebugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            movieJsonStr = buffer.toString();
            Log.d("JSON", movieJsonStr);
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the movie data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        return movieJsonStr;
    }

    @Override
    protected void onPostExecute(String mstring) {
        // super.onPostExecute(string);
        if (mstring != null)
        {
            try {
                MovieHelper movieHelper=new MovieHelper(context);
                SQLiteDatabase sql = movieHelper.getWritableDatabase();
                List<MovieModel> movieModels = new ArrayList<>();
                JSONObject object = new JSONObject(mstring);
                int id=object.getInt("id");
                JSONArray array = object.getJSONArray("results");
                for (int i = 0; i < array.length(); i++) {
                    MovieModel movie = new MovieModel();
                    JSONObject MovieObject = array.getJSONObject(i);
                    String content = MovieObject.getString("content");
                    String url=MovieObject.getString("url");
                    String author=MovieObject.getString("author");
                    movie.setAuthor(author);
                    movie.setContent(content);
                    movie.setId(id);
                    movie.setUrlContent(url);
                    movieModels.add(movie);
                    if (sql.isOpen()) {
                       if (!(movieHelper.checkReview(url)))
                            Log.d("VALUE", movieHelper.insertReviews(movie) + "");
                    } else
                        Log.d("DB", "Closed");
                }
                networkResponse.OnSuccess(movieModels);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Toast.makeText(context, mstring, Toast.LENGTH_LONG).show();

        }
        else
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}