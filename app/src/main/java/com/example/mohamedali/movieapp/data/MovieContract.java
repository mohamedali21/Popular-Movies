package com.example.mohamedali.movieapp.data;

import android.provider.BaseColumns;

/**
 * Created by Mohamed Ali on 9/17/2016.
 */
public class MovieContract {
    public static final class FavoriteMovieEntry implements BaseColumns {

        /**
         * Table name
         */
        public static final String TABLE_NAME = "movies";

        /**
         * TMDb id of movie
         */
        public static final String COLUMN_MOVIE_ID = "movie_id";

        /**
         * Original title of movie
         */
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";

        /**
         * Release date of movie
         */
        public static final String COLUMN_RELEASE_DATE = "release_date";

        /**
         * TMDb path to poster
         */
        public static final String COLUMN_POSTER_PATH = "poster_path";

        /**
         * TMDb user vote average
         */
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        /**
         * TMDb overview
         */
        public static final String COLUMN_OVERVIEW = "overview";

    }
    public static final class TopRatedMovieEntry implements BaseColumns {

        /**
         * Table name
         */
        public static final String TABLE_NAME = "top_rated_movies";

        /**
         * TMDb id of movie
         */
        public static final String COLUMN_MOVIE_ID = "movie_id";

        /**
         * Original title of movie
         */
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";

        /**
         * Release date of movie
         */
        public static final String COLUMN_RELEASE_DATE = "release_date";

        /**
         * TMDb path to poster
         */
        public static final String COLUMN_POSTER_PATH = "poster_path";

        /**
         * TMDb user vote average
         */
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        /**
         * TMDb overview
         */
        public static final String COLUMN_OVERVIEW = "overview";

    }
    public static final class PopularMovieEntry implements BaseColumns {

        /**
         * Table name
         */
        public static final String TABLE_NAME = "popular_movies";

        /**
         * TMDb id of movie
         */
        public static final String COLUMN_MOVIE_ID = "movie_id";

        /**
         * Original title of movie
         */
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";

        /**
         * Release date of movie
         */
        public static final String COLUMN_RELEASE_DATE = "release_date";

        /**
         * TMDb path to poster
         */
        public static final String COLUMN_POSTER_PATH = "poster_path";

        /**
         * TMDb user vote average
         */
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        /**
         * TMDb overview
         */
        public static final String COLUMN_OVERVIEW = "overview";

    }
    public static final class MovieTrailersEntry implements BaseColumns {

        /**
         * Table name
         */
        public static final String TABLE_NAME = "movie_trailers";
        /**
         * TMDb id of movie
         */
        public static final String COLUMN_MOVIE_ID = "movie_id";
        /**
         * TMDb trailer name of movie
         */
        public static final String COLUMN_TRAILER_NAME = "movie_trailer_namae";
        /**
         * TMDb trailer key of movie
         */
        public static final String COLUMN_TRAILER_KEY = "movie_trailer_key";

    }
    public static final class MovieReviewsEntry implements BaseColumns {

        /**
         * Table name
         */
        public static final String TABLE_NAME = "movie_reviews";
        /**
         * TMDb id of movie
         */
        public static final String COLUMN_MOVIE_ID = "movie_id";
        /**
         * TMDb review author of movie
         */
        public static final String COLUMN_REVIEW_AUTHOR = "review_author";
        /**
         * TMDb review content of movie
         */
        public static final String COLUMN_REVIEW_CONTENT= "review_content";
        /**
         * TMDb review url content of movie
         */
        public static final String COLUMN_REVIEW_CONTENT_URL= "review_content_url";

    }
}
