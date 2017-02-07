package com.example.jun.hambre_main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Landing extends AppCompatActivity {
    private Button guest;       //continue as guest
    private Button goog;        //google login
    private Button fb;          //FB login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        fb = (Button) findViewById(R.id.Facebook);
        goog = (Button) findViewById(R.id.Google);
        guest = (Button) findViewById(R.id.Guest);


        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Landing.this, Preferences.class));
            }
        });

        fb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                login(LoginType.FACEBOOK);
            }
        });

        goog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                login(LoginType.GOOGLE);
            }
        });
    }

    private void login(LoginType type){
        Intent i = new Intent(Landing.this, Login.class);
        Bundle bundle = new Bundle();
        bundle.putString("type", type.toString());
        startActivity(i);
    }
}
