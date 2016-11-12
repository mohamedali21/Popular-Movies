package com.example.mohamedali.movieapp.API;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.mohamedali.movieapp.BuildConfig;
import com.example.mohamedali.movieapp.MovieModel;
import com.example.mohamedali.movieapp.NetworkResponse;
import com.example.mohamedali.movieapp.data.MovieContract;
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
 * Created by Mohamed Ali on 8/19/2016.
 */
public class FetchMovieTask extends AsyncTask<String,Void,String>{
    String param;
    private final String LOG_TAG = FetchMovieTask.class.getSimpleName();
    NetworkResponse networkResponse;
    Context context;
    List<MovieModel> movieModels;
    public FetchMovieTask(Context con){
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
            // http://api.themoviedb.org/3/movie/API#movie
            final String MOVIE_BASE_URL =
                    "http://api.themoviedb.org/3/movie/";
            final String APPID_PARAM = "api_key";
            param=params[0];
            Uri builtUri = Uri.parse(MOVIE_BASE_URL+params[0]).buildUpon()
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
                movieModels = new ArrayList<>();
                JSONObject object = new JSONObject(mstring);
                JSONArray array = object.getJSONArray("results");
                int j=array.length();
                if (param=="popular")
                    movieHelper.deleteAllMovie(MovieContract.PopularMovieEntry.TABLE_NAME);
                else
                    movieHelper.deleteAllMovie(MovieContract.TopRatedMovieEntry.TABLE_NAME);
                for (int i = 0; i < array.length(); i++) {
                    MovieModel movie = new MovieModel();
                    JSONObject MovieObject = array.getJSONObject(i);
                    String url = MovieObject.getString("poster_path");
                    String name = MovieObject.getString("original_title");
                    double rate = MovieObject.getDouble("vote_average");
                    String releaseDate = MovieObject.getString("release_date");
                    String overview = MovieObject.getString("overview");
                    int id = MovieObject.getInt("id");
                    movie.setName(name);
                    movie.setUrl(url);
                    movie.setRate(rate);
                    movie.setOverview(overview);
                    movie.setReleaseDate(releaseDate);
                    movie.setId(id);
                    movieModels.add(movie);
                    if (param=="popular"){
                        if (sql.isOpen()) {
                            Log.d("VALUE", movieHelper.insertMovies(movie,param) + "");
                        } else
                            Log.d("DB", "Closed");
                    }
                    else {
                        if (sql.isOpen()) {
                            Log.d("VALUE", movieHelper.insertMovies(movie,param) + "");
                        } else
                            Log.d("DB", "Closed");
                    }
                }
                networkResponse.OnSuccess(movieModels);
            } catch (JSONException e) {
                e.printStackTrace();
            }
           //Toast.makeText(context,mstring, Toast.LENGTH_LONG).show();

        }
        else
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}
