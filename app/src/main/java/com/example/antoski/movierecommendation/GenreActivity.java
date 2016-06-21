package com.example.antoski.movierecommendation;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.net.InetAddress;

public class GenreActivity extends AppCompatActivity {

    public static String Genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        final Spinner spinner = (Spinner) findViewById(R.id.spinnerGenres);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genres_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        final Button SrcButton = (Button)findViewById(R.id.buttonSearchMovies);
        if (SrcButton != null) {
            SrcButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!spinner.getSelectedItem().toString().equals("Select genre")) {
                        if(isNetworkAvailable()) {
                            Genre = spinner.getSelectedItem().toString();

                            Intent intent = new Intent(GenreActivity.this, TestCards.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(GenreActivity.this, "Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
