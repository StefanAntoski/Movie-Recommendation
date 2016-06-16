package com.example.antoski.movierecommendation;

import android.content.Context;
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
import android.widget.TextView;

import com.daprlabs.cardstack.SwipeDeck;

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

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testData.add(Integer.toString(n++));
            }
        });

        final SwipeDeckAdapter adapter = new SwipeDeckAdapter(testData, this);
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

    public class SwipeDeckAdapter extends BaseAdapter {

        private List<String> data;
        private Context context;
        private int pos;

        public SwipeDeckAdapter(List<String> data, Context context) {
            this.data = data;
            this.context = context;
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
