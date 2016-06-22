package com.example.antoski.movierecommendation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;
import com.squareup.picasso.Picasso;

import junit.framework.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestCards extends AppCompatActivity {
    private Context MyContext;
    public int n = 0;
    public String LinkDoSlikaString;
    public List<String> LinkDoSlika;
    public List<String> PlotTwist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cards);

        MyContext = this;


        SwipeDeck cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);

        LinkDoSlika = new ArrayList<>();

        final List<Film> testData = new ArrayList<>();

        PlotTwist = new ArrayList<>();
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(MyContext);
        databaseAccess.open();
        List<Film> film = databaseAccess.recommendMoviesByGenre(GenreActivity.Genre);

        for(int i=0;i<film.size();i++)
        {
            testData.add(film.get(i));


        }
        databaseAccess.close();


        LinkDoSlikaString = null;
        URL urll = null;
        String ProbaUrl = null;
        new JSONTask().execute(urll);

        while(LinkDoSlikaString == null){


        }
        final SwipeDeckAdapter adapter = new SwipeDeckAdapter(testData, this, LinkDoSlika, cardStack);
        cardStack.setAdapter(adapter);




        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Log.i("TestCards", "card was swiped left, position in adapter: " + position);
            }

            @Override
            public void cardSwipedRight(int position) {
                Log.i("TestCards", "card was swiped right, position in adapter: " + position);
            }

            @Override
            public void cardsDepleted() {
                Log.i("TestCards", "no more cards");
            }

            @Override
            public void cardActionDown() {
                Log.i("TestCards", "cardActionDown");
            }

            @Override
            public void cardActionUp() {
                Log.i("TestCards", "cardActionUp");
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Finish activity.
                Intent intent = new Intent(MyContext,MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





    public class SwipeDeckAdapter extends BaseAdapter {

        private List<Film> data;
        private List<String> url;
        private Context context;
        private int pos;
        private SwipeDeck deck;
        public SwipeDeckAdapter(List<Film> data, Context context, List<String> url, SwipeDeck deck) {
            this.data = data;
            this.context = context;
            this.url = url;
            this.deck = deck;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            pos = position;

            View v = convertView;
            if(v == null){
                LayoutInflater inflater = getLayoutInflater();
                // normally use a viewholder
                v = inflater.inflate(R.layout.test_card, parent, false);
            }
            final Film film = data.get(position);

            ((TextView) v.findViewById(R.id.textView2)).setText(film.name + " (" + film.year + ")");

            ImageView slikaa = (ImageView)v.findViewById(R.id.PosterSlika);


     //       slikaa.setImageDrawable(LoadImageFromWebOperations(url));

           // new DownloadFilmInfoTask(slikaa, v, context).execute(url);
            // slikaa.setImageResource(R.drawable.moviepalsplash);
            Picasso.with(getApplicationContext()).load(url.get(pos)).error(R.mipmap.ic_launcher).placeholder(R.drawable.moviepalsplash).fit().into(slikaa);

            ((TextView) v.findViewById(R.id.textViewPlot)).setText(PlotTwist.get(pos));

            final Button buttonLike = (Button)v.findViewById(R.id.buttonLike);
            final Button buttonDisLike = (Button)v.findViewById(R.id.buttonDislike);

            buttonLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(MyContext);
                    databaseAccess.open();

                    databaseAccess.setRecommended(film.id);
                    databaseAccess.insertMovieLiked(film.id, film.name);

                    Toast.makeText(MyContext, R.string.MovieLikedToast, Toast.LENGTH_SHORT).show();

                    databaseAccess.close();
                    buttonLike.setEnabled(false);
                    buttonDisLike.setEnabled(false);
                }
            });

            buttonDisLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(MyContext);
                    databaseAccess.open();

                    databaseAccess.setRecommended(film.id);

                    Toast.makeText(MyContext, R.string.MovieDislikedToast, Toast.LENGTH_SHORT).show();

                    databaseAccess.close();
                    buttonLike.setEnabled(false);
                    buttonDisLike.setEnabled(false);
                }
            });

            /*v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String item = (String)getItem(pos);
                    Log.i("TestCards", item);
                }
            });*/

            return v;
        }
    }


    public class JSONTask extends AsyncTask<URL,String,String> {

        private DatabaseAccess databaseAccess;
        @Override
        protected String doInBackground(URL... params) {

            BufferedReader reader = null;
            databaseAccess = DatabaseAccess.getInstance(MyContext);
            databaseAccess.open();
            String[] idd = new String[100];
            HttpURLConnection connection = null;
            List<Film> films;
          //

            films = databaseAccess.recommendMoviesByGenre(GenreActivity.Genre);
            for(int i = 0; i < films.size(); i++) {
                idd[i] = films.get(i).id;
            }
            URL url = null;


            for(int i =0;i < films.size();i++) {
                String link = "http://www.omdbapi.com/?i="+idd[i]+"&plot=short&r=json";


                try {
                    url = new URL(link);
                    connection = (HttpURLConnection) url.openConnection();

                    connection.connect();

                    InputStream stream = connection.getInputStream();


                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();
                    String line = "";

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    String finalJson = buffer.toString();
                    JSONObject parentObject = new JSONObject(finalJson);
                    String IzvadenoOdJsonURL = parentObject.getString("Poster");
                    String IzvadenPlot = parentObject.getString("Plot");
                    LinkDoSlika.add(i,IzvadenoOdJsonURL);
                    PlotTwist.add(i,IzvadenPlot);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }

                    try {

                        reader.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            databaseAccess.close();
            LinkDoSlikaString = "Yes";
            return null;

        }


        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
  //    Toast.makeText(MyContext,kk,Toast.LENGTH_LONG).show();
          //  findViewById(R.id.swipe_deck).invalidate();
           // findViewById(R.id.PosterSlika).invalidate();
        }

    }

}


