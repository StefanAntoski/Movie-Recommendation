package com.example.antoski.movierecommendation;

/**
 * Created by krpet on 10.6.2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all names of films from the database.
     *
     * @return a List of quotes
     */
    public String getFilmNames() {
        String res = "";
        Cursor cursor = database.rawQuery("select name from MoviesDB", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            res += cursor.getString(0) + "\n";
            cursor.moveToNext();
        }
        cursor.close();
        return res;
    }

    public List<Film> recommendMoviesByGenre(String genre) {
        List<Film> res = new ArrayList<>();
        String sqlCommand = "select MoviesDB.id, MoviesDB.name, MoviesDB.year " +
                "from MoviesDB, MoviesGenres " +
                "where MoviesDB.id=MoviesGenres.id and MoviesDB.recommend=0 and MoviesGenres.genre='" + genre + "'";
        Cursor cursor = database.rawQuery(sqlCommand, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film film = new Film(cursor.getString(0), cursor.getString(1), cursor.getInt(2));
            res.add(film);
            cursor.moveToNext();
        }
        cursor.close();
        return res;
    }

    public List<Film> recommendMoviesByYear(int year) {
        List<Film> res = new ArrayList<>();
        String sqlCommand = "select id, name, year " +
                "from MoviesDB " +
                "where recommend=0 and year=" + year + "";
        Cursor cursor = database.rawQuery(sqlCommand, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film film = new Film(cursor.getString(0), cursor.getString(1), cursor.getInt(2));
            res.add(film);
            cursor.moveToNext();
        }
        cursor.close();
        return res;
    }

    public Film getFilmByName(String name) {
        String sqlCommand = "select id, name, year " +
                "from MovieDB " +
                "where name='" + name + "'";
        Cursor cursor = database.rawQuery(sqlCommand, null);
        cursor.moveToFirst();
        Film film = new Film(cursor.getString(0), cursor.getString(1), cursor.getInt(2));
        cursor.close();
        return film;
    }

    public void setRecommended(String id) {
        String sqlCommand = "update MoviesDB " +
                "set recommend=1 " +
                "where id='" + id + "'";
        database.execSQL(sqlCommand);
    }

    public boolean isRecommended(String id) {
        String sqlCommand = "select recommend " +
                "from MoviesDB " +
                "where id='" + id + "'";
        Cursor cursor = database.rawQuery(sqlCommand, null);
        cursor.moveToFirst();
        boolean isRec;
        if (cursor.getInt(0) == 0)
            isRec = false;
        else
            isRec = true;
        return isRec;
    }

    public void insertMovieLiked(String id, String name) {
        String sqlCommand = "insert into MoviesLiked " +
                "values('" + id + "', '" + name + "')";
        database.execSQL(sqlCommand);
    }

    public List<Film> getLikedMovies() {
        List<Film> res = new ArrayList<>();
        String sqlCommand = "select * " +
                "from MoviesLiked";
        Cursor cursor = database.rawQuery(sqlCommand, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film film = new Film();
            film.id = cursor.getString(0);
            film.name = cursor.getString(1);
            res.add(film);
            cursor.moveToNext();
        }
        cursor.close();
        return res;
    }
}