package com.devashishthakur.contactsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {

    ImageButton save,cancel;
    ImageView profilePage;
    EditText username,mobile,mobile1,email,company,address,job;

    public  void saveInfo(String name, String number,String email,String number2,String address,String company,String job)
    {
        //Fuction to save info into shared preference each time it is edited

        SharedPreferences sharedPreferences = getSharedPreferences("shared",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Name

        if(name == null || name.length() > 2)
            editor.putString("name",name);
        else
            editor.putString("name"," ");

        //Number

        if(number == null || number.length() > 2)
            editor.putString("number",number);
        else
            editor.putString("number"," ");


        //EMail

        if(email == null || email.length() > 2)
            editor.putString("email",email);
        else
            editor.putString("email"," ");


        //Number2

        if(number2 == null || number2.length() > 2)
            editor.putString("number1",number2);
        else
            editor.putString("number1"," ");

        //Address

        if(address == null || address.length() > 2)
            editor.putString("address",address);
        else
            editor.putString("address"," ");

        //Company

        if(company == null || company.length() > 2)
            editor.putString("company",company);
        else
            editor.putString("company"," ");

        if(job == null || job.length() > 2)
            editor.putString("job",job);
        else
            editor.putString("job"," ");

        editor.commit();
    }

    public void displayData()
    {
        /*
        SharedPreferences sharedPreferences = getSharedPreferences("shared",MODE_PRIVATE);
        String name = sharedPreferences.getString("name","");
        String number = sharedPreferences.getString("number1","");
        String email = sharedPreferences.getString("email","");

        Toast.makeText(EditProfileActivity.this ,name + number,
                Toast.LENGTH_LONG).show();
               */
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //Declaring and setting text of all the edit texts

        email = (EditText) findViewById(R.id.Editemail);
        email.setText(getIntent().getStringExtra("email"));
        mobile1 = (EditText) findViewById(R.id.Editphone2);
        mobile1.setText(getIntent().getStringExtra("number2"));
        company = (EditText) findViewById(R.id.Editcompany);
        company.setText(getIntent().getStringExtra("company"));
        job = (EditText) findViewById(R.id.Editjobtitle);
        job.setText(getIntent().getStringExtra("job"));
        address = (EditText) findViewById(R.id.Editaddress);
        address.setText(getIntent().getStringExtra("address"));

        save = (ImageButton) findViewById(R.id.save_profile_button);

        username = (EditText) findViewById(R.id.Editname);
        username.setText(getIntent().getStringExtra("name"));

        mobile = (EditText) findViewById(R.id.Editphone);
        mobile.setText(getIntent().getStringExtra("number1"));

        cancel = (ImageButton) findViewById(R.id.cancel_profile_button);
        profilePage = (ImageView) findViewById(R.id.profile_page);
        //ON click of SAVE... Save the data into shared preference

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo(username.getText().toString(),mobile.getText().toString(),
                        email.getText().toString(),mobile1.getText().toString(),
                        address.getText().toString(),company.getText().toString(),job.getText().toString());
                displayData();
                Intent intent = new Intent(EditProfileActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        profilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this,ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this,ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }




}
