package com.example.antoski.movierecommendation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        final ListView SettingList = (ListView)findViewById(R.id.SettingsListView);

        String[] Lista = {"Reset", "Select Language","Report a problem" , "About us" };

        ArrayList<String> List = new ArrayList<String>();

        List.addAll(Arrays.asList(Lista));

        StringArrayAdapter adapter = new StringArrayAdapter(List,this);

        SettingList.setAdapter(adapter);

        SettingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    new AlertDialog.Builder(SettingsActivity.this)
                            .setTitle("Reset your movie list")
                            .setMessage("Are you sure you want to reset your movie list?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(SettingsActivity.this);
                                    databaseAccess.open();

                                    databaseAccess.resetDatabase();

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
                else if(position == 1) {

                }
                else if(position == 2) {
                    Intent intent = new Intent(SettingsActivity.this, ReportProblem.class);
                    startActivity(intent);
                }
                else if(position == 3){

                }
            }
        });
    }
}
