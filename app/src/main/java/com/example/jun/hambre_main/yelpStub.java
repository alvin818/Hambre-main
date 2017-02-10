package com.example.jun.hambre_main;

import com.google.gson.Gson;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jun.yelp.BusinessModel;
import com.example.jun.yelp.BusinessResponseModel;
import com.example.jun.yelp.YelpApi;
import com.example.jun.yelp.YelpUtilities;

import java.util.HashMap;

/**
 * This class is a stub class to test the integration of Yelp API into the system
 * allow the user to input a cuisine type/city name to see the responses of yelp api
 */

public class yelpStub extends AppCompatActivity {
    private Location location;
    private EditText cuisine;
    private Button show;
    private TextView first;
    private TextView second;
    private TextView third;
    private TextView fourth;

    private BusinessModel business1;

    private int LOCATION_ACCESS_VALUE = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // gps
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // get permissions
        final YelpLocationListener locationListener = new YelpLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        setContentView(R.layout.yelpstub);

        cuisine = (EditText)findViewById(R.id.culture);
        show = (Button)findViewById(R.id.show);
        first = (TextView)findViewById(R.id.first);
        second = (TextView)findViewById(R.id.second);
        third = (TextView)findViewById(R.id.third);
        fourth = (TextView)findViewById(R.id.fourth);

        show.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String culture = cuisine.getText().toString();
                if(culture == "")
                    Toast.makeText(yelpStub.this, "enter cuisine culture", Toast.LENGTH_SHORT).show();
                else {
                    YelpApi api = new YelpApi();

                    locationListener.onLocationChanged(location);

                    // build params
                    HashMap<String, String> params = new HashMap<>();
                    params.put("latitude", String.valueOf(location.getLatitude()));
                    params.put("longitude", String.valueOf(location.getLongitude()));
                    params.put("categories", "food");
                    params.put("term", culture);
                    params.put("radius_filter", "40000");

                    BusinessResponseModel businessResponse = api.businessSearch(params);
                    try{
                        business1 = (businessResponse.businesses())[0];
                        first.setText(business1.name());
                        BusinessModel business2 = (businessResponse.businesses())[1];
                        second.setText(business2.name());
                        BusinessModel business3 = (businessResponse.businesses())[2];
                        third.setText(business3.name());
                        BusinessModel business4 = (businessResponse.businesses())[3];
                        fourth.setText(business4.name());
                    }catch(Exception e){
                        first.setText("something went wrong");
                        e.printStackTrace();
                    }
                }
            }
        });

        first.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String coordinates = business1.coordinates().toString();
                String label = business1.name();
                String uriBegin = "geo:" + coordinates;
                String query = coordinates + "(" + label + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}
