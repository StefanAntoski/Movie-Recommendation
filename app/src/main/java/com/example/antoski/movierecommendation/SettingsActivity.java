package com.example.antoski.movierecommendation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

        ListView SettingList = (ListView)findViewById(R.id.SettingsListView);

        String[] Lista = {"Reset", "Select Language","Report a problem" , "About us" };

        ArrayList<String> List = new ArrayList<String>();

        List.addAll(Arrays.asList(Lista));

        StringArrayAdapter adapter = new StringArrayAdapter(List,this);

        SettingList.setAdapter(adapter);





    }
}
