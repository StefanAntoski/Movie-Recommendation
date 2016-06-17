package com.example.antoski.movierecommendation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestCards extends AppCompatActivity {

    public int n = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cards);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        SwipeDeck cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);






        final ArrayList<String> testData = new ArrayList<>();
        testData.add(Integer.toString(n++));
        testData.add(Integer.toString(n++));
        testData.add(Integer.toString(n++));
        testData.add(Integer.toString(n++));
        testData.add(Integer.toString(n++));

        String url = "http://ia.media-imdb.com/images/M/MV5BNzQ0NDgwODQ3NV5BMl5BanBnXkFtZTgwOTYxNjc2ODE@._V1_UX182_CR0,0,182,268_AL_.jpg";




        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testData.add(Integer.toString(n++));
            }
        });

        final SwipeDeckAdapter adapter = new SwipeDeckAdapter(testData, this, url, cardStack);
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



    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "Poster");
            return d;
        } catch (Exception e) {
            return null;
        }
    }


    public class SwipeDeckAdapter extends BaseAdapter {

        private List<String> data;
        private String url;
        private Context context;
        private int pos;
        private SwipeDeck deck;

        public SwipeDeckAdapter(List<String> data, Context context, String url, SwipeDeck deck) {
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
            ((TextView) v.findViewById(R.id.textView2)).setText(data.get(pos));

            ImageView slikaa = (ImageView)v.findViewById(R.id.PosterSlika);


            slikaa.setImageDrawable(LoadImageFromWebOperations(url));

           // new DownloadFilmInfoTask(slikaa, v, context).execute(url);
            // slikaa.setImageResource(R.drawable.moviepalsplash);
    //        Picasso.with(getApplicationContext()).load(R.drawable.moviepalsplash).error(R.mipmap.ic_launcher).into(slikaa);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String item = (String)getItem(pos);
                    Log.i("TestCards", item);
                }
            });

            return v;
        }
    }

}
