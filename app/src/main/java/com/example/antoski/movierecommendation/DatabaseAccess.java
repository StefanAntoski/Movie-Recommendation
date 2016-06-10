package com.example.antoski.movierecommendation;

/**
 * Created by krpet on 10.6.2016.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}