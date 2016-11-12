package com.example.mohamedali.movieapp.data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mohamedali.movieapp.MovieModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Ali on 9/17/2016.
 */
public class MovieHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "movies.db";
    // Increment if change in DB schema
    private static final int DATABASE_VERSION = 9;

    public MovieHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIES_TABLE =
                "CREATE TABLE " + MovieContract.FavoriteMovieEntry.TABLE_NAME + " (" +
                        MovieContract.FavoriteMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MovieContract.FavoriteMovieEntry.COLUMN_MOVIE_ID + " INTEGER UNIQUE NOT NULL, " +
                        MovieContract.FavoriteMovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                        MovieContract.FavoriteMovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                        MovieContract.FavoriteMovieEntry.COLUMN_RELEASE_DATE + " INTEGER NOT NULL, " +
                        MovieContract.FavoriteMovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                        MovieContract.FavoriteMovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL" +
                        " );";
        db.execSQL(SQL_CREATE_MOVIES_TABLE);
        final String SQL_CREATE_TOP_RATED_MOVIES_TABLE =
                "CREATE TABLE " + MovieContract.TopRatedMovieEntry.TABLE_NAME + " (" +
                        MovieContract.TopRatedMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MovieContract.TopRatedMovieEntry.COLUMN_MOVIE_ID + " INTEGER UNIQUE NOT NULL, " +
                        MovieContract.TopRatedMovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                        MovieContract.TopRatedMovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                        MovieContract.TopRatedMovieEntry.COLUMN_RELEASE_DATE + " INTEGER NOT NULL, " +
                        MovieContract.TopRatedMovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                        MovieContract.TopRatedMovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL" +
                        " );";
        db.execSQL(SQL_CREATE_TOP_RATED_MOVIES_TABLE);
        final String SQL_CREATE_POPULAR_MOVIES_TABLE =
                "CREATE TABLE " + MovieContract.PopularMovieEntry.TABLE_NAME + " (" +
                        MovieContract.PopularMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MovieContract.PopularMovieEntry.COLUMN_MOVIE_ID + " INTEGER UNIQUE NOT NULL, " +
                        MovieContract.PopularMovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                        MovieContract.PopularMovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                        MovieContract.PopularMovieEntry.COLUMN_RELEASE_DATE + " INTEGER NOT NULL, " +
                        MovieContract.PopularMovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                        MovieContract.PopularMovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL" +
                        " );";
        db.execSQL(SQL_CREATE_POPULAR_MOVIES_TABLE);
        final String SQL_CREATE_TRAILERS_TABLE =
                "CREATE TABLE " + MovieContract.MovieTrailersEntry.TABLE_NAME + " (" +
                        MovieContract.MovieTrailersEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                        MovieContract.MovieTrailersEntry.COLUMN_TRAILER_KEY + " TEXT UNIQUE, " +
                        MovieContract.MovieTrailersEntry.COLUMN_TRAILER_NAME + " TEXT" +
                        " );";
        db.execSQL(SQL_CREATE_TRAILERS_TABLE);
        final String SQL_CREATE_REVIEWS_TABLE =
                "CREATE TABLE " + MovieContract.MovieReviewsEntry.TABLE_NAME + " (" +
                        MovieContract.MovieReviewsEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                        MovieContract.MovieReviewsEntry.COLUMN_REVIEW_AUTHOR + " TEXT, " +
                        MovieContract.MovieReviewsEntry.COLUMN_REVIEW_CONTENT + " TEXT UNIQUE, " +
                        MovieContract.MovieReviewsEntry.COLUMN_REVIEW_CONTENT_URL + " TEXT" +
                        " );";
        db.execSQL(SQL_CREATE_REVIEWS_TABLE);

    }
    public long insertMovies(MovieModel movieModels,String state){
        ContentValues contentValues=new ContentValues();
        contentValues.put(MovieContract.FavoriteMovieEntry.COLUMN_MOVIE_ID,movieModels.getId());
        contentValues.put(MovieContract.FavoriteMovieEntry.COLUMN_ORIGINAL_TITLE,movieModels.getName());
        contentValues.put(MovieContract.FavoriteMovieEntry.COLUMN_RELEASE_DATE,movieModels.getReleaseDate());
        contentValues.put(MovieContract.FavoriteMovieEntry.COLUMN_POSTER_PATH,movieModels.getUrl());
        contentValues.put(MovieContract.FavoriteMovieEntry.COLUMN_VOTE_AVERAGE,movieModels.getRate());
        contentValues.put(MovieContract.FavoriteMovieEntry.COLUMN_OVERVIEW,movieModels.getOverview());
        if (state=="favorite")
            return this.getWritableDatabase().insert(MovieContract.FavoriteMovieEntry.TABLE_NAME, null, contentValues);
        else if (state=="popular") {
            return this.getWritableDatabase().insert(MovieContract.PopularMovieEntry.TABLE_NAME, null, contentValues);
        }
        else{
            return this.getWritableDatabase().insert(MovieContract.TopRatedMovieEntry.TABLE_NAME, null, contentValues);
        }
    }

    public long insertTrailers(MovieModel movieModels){
        ContentValues contentValues=new ContentValues();
        contentValues.put(MovieContract.MovieTrailersEntry.COLUMN_MOVIE_ID,movieModels.getId());
        contentValues.put(MovieContract.MovieTrailersEntry.COLUMN_TRAILER_KEY,movieModels.getKey());
        contentValues.put(MovieContract.MovieTrailersEntry.COLUMN_TRAILER_NAME,movieModels.getTrailer());
        return this.getWritableDatabase().insertWithOnConflict(MovieContract.MovieTrailersEntry.TABLE_NAME,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
    }
    public long insertReviews(MovieModel movieModels){
        ContentValues contentValues=new ContentValues();
        contentValues.put(MovieContract.MovieReviewsEntry.COLUMN_MOVIE_ID,movieModels.getId());
        contentValues.put(MovieContract.MovieReviewsEntry.COLUMN_REVIEW_AUTHOR,movieModels.getAuthor());
        contentValues.put(MovieContract.MovieReviewsEntry.COLUMN_REVIEW_CONTENT,movieModels.getContent());
        contentValues.put(MovieContract.MovieReviewsEntry.COLUMN_REVIEW_CONTENT_URL,movieModels.getUrlContent());
        return this.getWritableDatabase().insertWithOnConflict(MovieContract.MovieReviewsEntry.TABLE_NAME,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public List<MovieModel> getMovies(String state){
        String query=null;
        if (state=="favorite")
         query = "Select*from " + MovieContract.FavoriteMovieEntry.TABLE_NAME + "";
        else if (state=="popular")
             query = "Select*from " + MovieContract.PopularMovieEntry.TABLE_NAME + "";
        else
             query = "Select*from " + MovieContract.TopRatedMovieEntry.TABLE_NAME + "";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        List<MovieModel> movieModels=new ArrayList<>();
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                do{
                    MovieModel movieModels1=new MovieModel();
                    movieModels1.setName(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMovieEntry.COLUMN_ORIGINAL_TITLE)));
                    movieModels1.setId((int) cursor.getDouble(cursor.getColumnIndex(MovieContract.FavoriteMovieEntry.COLUMN_MOVIE_ID)));
                    movieModels1.setOverview(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMovieEntry.COLUMN_OVERVIEW)));
                    movieModels1.setUrl(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMovieEntry.COLUMN_POSTER_PATH)));
                    movieModels1.setReleaseDate(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMovieEntry.COLUMN_RELEASE_DATE)));
                    movieModels1.setRate(cursor.getDouble(cursor.getColumnIndex(MovieContract.FavoriteMovieEntry.COLUMN_VOTE_AVERAGE)));
                    movieModels.add(movieModels1);
                }while (cursor.moveToNext());
            }
        }
        return movieModels;
    }

    public List<MovieModel> getTrailers(int id){
        String query = "Select*from " + MovieContract.MovieTrailersEntry.TABLE_NAME + " WHERE "+ MovieContract.FavoriteMovieEntry.COLUMN_MOVIE_ID+"='"+id+"'";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        List<MovieModel> movieModels=new ArrayList<>();
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                do{
                    MovieModel movieModels1=new MovieModel();
                    movieModels1.setTrailer(cursor.getString(cursor.getColumnIndex(MovieContract.MovieTrailersEntry.COLUMN_TRAILER_NAME)));
                    movieModels1.setId((int) cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieTrailersEntry.COLUMN_MOVIE_ID)));
                    movieModels1.setKey(cursor.getString(cursor.getColumnIndex(MovieContract.MovieTrailersEntry.COLUMN_TRAILER_KEY)));
                    movieModels.add(movieModels1);
                }while (cursor.moveToNext());
            }
        }
        return movieModels;
    }
    public List<MovieModel> getReviews(int id){
        String query = "Select*from " + MovieContract.MovieReviewsEntry.TABLE_NAME + " WHERE "+ MovieContract.FavoriteMovieEntry.COLUMN_MOVIE_ID+"='"+id+"'";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        List<MovieModel> movieModels=new ArrayList<>();
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                do{
                    MovieModel movieModels1=new MovieModel();
                    movieModels1.setAuthor(cursor.getString(cursor.getColumnIndex(MovieContract.MovieReviewsEntry.COLUMN_REVIEW_AUTHOR)));
                    movieModels1.setId((int) cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieReviewsEntry.COLUMN_MOVIE_ID)));
                    movieModels1.setContent(cursor.getString(cursor.getColumnIndex(MovieContract.MovieReviewsEntry.COLUMN_REVIEW_CONTENT)));
                    movieModels1.setUrlContent(cursor.getString(cursor.getColumnIndex(MovieContract.MovieReviewsEntry.COLUMN_REVIEW_CONTENT_URL)));
                    movieModels.add(movieModels1);
                }while (cursor.moveToNext());
            }
        }
        return movieModels;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.FavoriteMovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieTrailersEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieReviewsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.PopularMovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.TopRatedMovieEntry.TABLE_NAME);
        onCreate(db);
    }
    public Boolean checkMovie(int id) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + MovieContract.FavoriteMovieEntry.TABLE_NAME + " WHERE "+ MovieContract.FavoriteMovieEntry.COLUMN_MOVIE_ID +"='" + id + "'",null);
        boolean exist = (mCursor.getCount() > 0);
        mCursor.close();
        return exist;
    }
    public Boolean checkReview(String content) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + MovieContract.MovieReviewsEntry.TABLE_NAME + " WHERE "+MovieContract.MovieReviewsEntry.COLUMN_REVIEW_CONTENT_URL +"='" + content + "'",null);
        boolean exist = (mCursor.getCount() > 0);
        mCursor.close();
        return exist;
    }
    public Boolean checkTralers(String key) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + MovieContract.MovieTrailersEntry.TABLE_NAME + " WHERE "+MovieContract.MovieTrailersEntry.COLUMN_TRAILER_KEY +"='" + key + "'",null);
        boolean exist = (mCursor.getCount() > 0);
        mCursor.close();
        return exist;
    }

    public void deleteMovieFromFavorite(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + MovieContract.FavoriteMovieEntry.TABLE_NAME+ " WHERE "+ MovieContract.FavoriteMovieEntry.COLUMN_MOVIE_ID+"='"+id+"'");
    }
    public void deleteAllMovie(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName);
    }

}
