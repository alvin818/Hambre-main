package com.irs.main.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.irs.main.R;

/**
 * Created by jeff on 3/3/17.
 */

//TODO add name field, add catagory (food, desert or fruit etc...) dropdown


public class UploadPhoto extends AppCompatActivity{

    private Button chosePhotoButton, saveButton, cancelButton, cameraButton;
    private Bitmap pic;
    private ImageView selectedPic;
    private String culture, diet;
    private Spinner cultureSpinner, dietSpinner;

    //TODO add all cultures in alphabetical order available in yelp API found at:
    //https://www.yelp.com/developers/documentation/v2/all_category_list

    //TODO also change switch statement below for cultureSpinner.OnItemSelected

    private static final String[] paths = {"American (new)","American (traditional)", "Chinese", "Cuban",
            "Indian", "Italian","Japanese", "Korean","Mexican","Russian" ,"Thai" };
    private static final String[] dietPaths = {"None", "Vegetarian", "Vegan",
        "Kosher", "Gluten Free"};

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        selectedPic = (ImageView)findViewById(R.id.selected_pic);
        cultureSpinner = (Spinner)findViewById(R.id.culture_spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(UploadPhoto.this,
                android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cultureSpinner.setAdapter(adapter);
        dietSpinner = (Spinner)findViewById(R.id.dietary_spinner);
        ArrayAdapter<String>dietAdapter = new ArrayAdapter<String>(UploadPhoto.this,
                android.R.layout.simple_spinner_item, dietPaths);
        dietSpinner.setAdapter(dietAdapter);
        cultureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0: //American (new)
                        culture = "newamerican";
                        break;
                    case 1: //American (traditional)
                        culture = "tradamerican";
                        break;
                    case 2: //chinese
                        culture = "Chinese, All";
                        break;
                    case 3: //Cuban
                        culture = "cuban";
                        break;
                    case 4: //Indian
                        culture = "indpak";
                        break;
                    case 5: //Italian
                        culture = "italian, All";
                        break;
                    case 6: //Japanese
                        culture = "japanese, All";
                        break;
                    case 7: //Korean
                        culture = "korean, All";
                        break;
                    case 8: //Mexican
                        culture = "mexican, All";
                        break;
                    case 9: //Russian
                        culture = "russian, All";
                        break;
                    case 10: //Thai
                        culture = "thai, All";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dietSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0://None
                        diet = "None";
                        break;
                    case 1://Vegetarian
                        diet = "vegetarian, All";
                        break;
                    case 2://Vegan
                        diet = "vegan, All";
                        break;
                    case 3://Kosher
                        diet = "kosher";
                        break;
                    case 4:
                        diet = "gluten_free, All";
                        break;
                    default:
                        diet = "none";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chosePhotoButton = (Button)findViewById(R.id.chose_photo_button);
        chosePhotoButton.setOnClickListener(new Button.OnClickListener(){
           @Override
            public void onClick(View v){
                pickImage();
           }
        });
        saveButton = (Button)findViewById(R.id.save_photo_buttton);
        saveButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                System.out.println("Culture: " + culture +"\nDiet: " + diet);
                Toast.makeText(UploadPhoto.this, "photo submitted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        cancelButton = (Button)findViewById(R.id.cancel_photo_button);
        cancelButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }

        });
        cameraButton = (Button)findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent Intent3=new   Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                startActivity(Intent3);
            }
        });


    }

    public void pickImage(){
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        //TODO change these to crop and save as vertical phone pic
        //TODO figure out why cropping screen is so small
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 512);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 2);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data){
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 1) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                pic = extras.getParcelable("data");
                selectedPic.setImageBitmap(pic);
            }
        }
        /*
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ImageView imageview = (ImageView) findViewById(R.id.ImageView01);
            imageview.setImageBitmap(image);
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            selectedPic.setImageBitmap(imageBitmap);
        }*/
    }
}
