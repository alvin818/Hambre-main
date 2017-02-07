package com.example.jun.hambre_main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class DietRestriction extends AppCompatActivity {
    private RadioGroup prefs;
    private Button save;
    private Button cancel;
    String newPref = "";

    protected static int preferenceID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_restriction);

        prefs = (RadioGroup)findViewById(R.id.choices);
        save = (Button)findViewById(R.id.save);
        cancel = (Button)findViewById(R.id.cancel);

        Bundle bundle = getIntent().getExtras();
        //set the preference to the preference of the user on the server
        switch(preferenceID){
            case 0:
                prefs.check(R.id.none);
                break;
            case 1:
                prefs.check(R.id.veggie);
                break;
            case 2:
                prefs.check(R.id.vegan);
                break;
            case 3:
                prefs.check(R.id.kosher);
                break;
            case 4:
                prefs.check(R.id.noGlu);
                break;
            default:
                prefs.check(R.id.none);
                break;

        }

        prefs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.none:
                        preferenceID = 0;
                        break;
                    case R.id.veggie:
                        preferenceID = 1;
                        break;
                    case R.id.vegan:
                        preferenceID = 2;
                        break;
                    case R.id.kosher:
                        preferenceID = 3;
                        break;
                    case R.id.noGlu:
                        preferenceID = 4;
                        break;
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if not guest mode
                //push update to server);
                //database.getUser(username)
                //username.updateDiet(newPref, prefIndex);
                Toast.makeText(DietRestriction.this, "Changes saved!", Toast.LENGTH_SHORT).show();
                returnToPrev();
                //else
                //propagate the data throughout the app via preferenceID
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DietRestriction.this, "Changes discarded", Toast.LENGTH_SHORT).show();
                returnToPrev();
            }
        });
    }

    private void returnToPrev(){
        Intent i = new Intent(DietRestriction.this, Preferences.class);
        startActivity(i);
    }
}
