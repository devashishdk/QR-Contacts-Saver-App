package com.devashishthakur.contactsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    ImageButton editProfile;
    ImageView home;
    Toolbar toolbar;
    TextView nameView,emailView,mob1View,mob2View,addressView,jobView,companyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUpToolBar();

        //Declarations
        nameView = (TextView) findViewById(R.id.name);
        mob1View = (TextView) findViewById(R.id.phone);
        addressView = (TextView) findViewById(R.id.address);
        companyView = (TextView) findViewById(R.id.company);
        jobView = (TextView) findViewById(R.id.jobtitle);


        emailView = (TextView) findViewById(R.id.email);
        mob2View = (TextView) findViewById(R.id.phone2);

        home = (ImageView) findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //Shared Preferences
        //Getting all the data from the shared preference to show them in activity
        SharedPreferences sharedPreferences = getSharedPreferences("shared",MODE_PRIVATE);
        String name = sharedPreferences.getString("name","");
        String number = sharedPreferences.getString("number","");
        String number2 = sharedPreferences.getString("number1","");
        String email = sharedPreferences.getString("email","");
        String address = sharedPreferences.getString("address","");
        String job = sharedPreferences.getString("job","");
        String company = sharedPreferences.getString("company","");
        //Setting texts

        nameView.setText(name);
        mob1View.setText(number);
        emailView.setText(email);
        mob2View.setText(number2);
        jobView.setText(job);
        addressView.setText(address);
        companyView.setText(company);
        //Moving to edit profile with the data already in profile

        editProfile = (ImageButton) findViewById(R.id.edit_profile_button);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,EditProfileActivity.class);
                intent.putExtra("name",nameView.getText());
                intent.putExtra("number1",mob1View.getText());
                intent.putExtra("number2",mob2View.getText());
                intent.putExtra("email",emailView.getText());
                intent.putExtra("address",addressView.getText());
                intent.putExtra("company",companyView.getText());
                intent.putExtra("job",jobView.getText());
                startActivity(intent);
                finish();
            }
        });
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.settings):
                break;
        }
        return true;
    }
    */

    void setUpToolBar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }
}
