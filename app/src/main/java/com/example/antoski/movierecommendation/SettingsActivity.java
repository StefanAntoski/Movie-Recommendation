package com.example.antoski.movierecommendation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

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

                    final Configuration config;
                    final Resources res = SettingsActivity.this.getResources();
                    config = new Configuration(res.getConfiguration());
                    final DisplayMetrics dm = res.getDisplayMetrics();

                    PopupMenu popupmenu = new PopupMenu(SettingsActivity.this,view);
                    popupmenu.inflate(R.menu.languages);

                    popupmenu.show();

                    popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if(item.getItemId() == R.id.AngliskiJazik)
                            {
                                config.locale = Locale.ENGLISH;
                            }

                            if(item.getItemId() == R.id.MakedonskiJazik)
                            {
                                android.content.res.Configuration conf = res.getConfiguration();

                                conf.locale = new Locale("mk");
                               // Toast.makeText(SettingsActivity.this,"Македонски",Toast.LENGTH_LONG).show();
                                res.updateConfiguration(conf,dm);
                                return false;

                            }
                       return false;
                        }
                    });
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
