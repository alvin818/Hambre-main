package com.irs.main.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.irs.main.R;
import com.irs.yelp.BusinessModel;

public class SelectRestaurantController extends AppCompatActivity implements Runnable {

    ListView list;
    BusinessModel[] businesses;
    String [] names;
    String [] url;
    String [] imageUrl;
    double [] ratings;
    String [] prices;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //animate Activity transition
        overridePendingTransition(R.anim.animation_activity_enter,
                R.anim.animation_activity_leave);

        setContentView(R.layout.activity_select_restaurant);

        try {
            run();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("He's dead Jim");
        }

    }

    private void openMaps(String coordinates, String label) {
        String uriBegin = "geo:" + coordinates;
        String query = coordinates + "(" + label + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void run() {
        businesses = BusinessModel.toBusinessModel(getIntent().getParcelableArrayExtra("model"));
        names = new String[businesses.length];
        url = new String[businesses.length];
        imageUrl = new String[businesses.length];
        ratings = new double[businesses.length];
        prices = new String[businesses.length];
        for(int i = 0; i < businesses.length; i++){
            names[i] = businesses[i].name();
            url[i] = businesses[i].url();
            imageUrl[i] = businesses[i].image_url();
            ratings[i] = businesses[i].rating();
            prices[i] = businesses[i].price();
        }
        RestaurantList adapter = new
                RestaurantList(SelectRestaurantController.this,
                names, url, imageUrl, ratings, prices);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        //open yelp page of selected restaurant in browser
        list.setOnItemClickListener( new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url[position]));
                startActivity(browserIntent);
            }
        });

    }
}