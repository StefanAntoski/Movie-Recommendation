package com.example.antoski.movierecommendation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoviesLikedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_liked);

        ListView lw = (ListView)findViewById(R.id.listViewMoviesLiked);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(MoviesLikedActivity.this);
        databaseAccess.open();

        final List<Film> films = databaseAccess.getLikedMovies();
        final ArrayList<String> filmStrings = new ArrayList<String>();

        for(int i = 0; i < films.size(); i++) {
            filmStrings.add(films.get(i).toString());
        }

        final StringArrayAdapter adapter = new StringArrayAdapter(filmStrings,this);

        lw.setAdapter(adapter);

        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MoviesLikedActivity.this)
                        .setTitle("Remove film from list")
                        .setMessage("Are you sure you want to remove the film from your list?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(MoviesLikedActivity.this);
                                databaseAccess.open();

                                databaseAccess.deleteMovieLiked(films.get(position).id);
                                filmStrings.remove(position);
                                adapter.notifyDataSetChanged();

                                databaseAccess.close();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }
}
