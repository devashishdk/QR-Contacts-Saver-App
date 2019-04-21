package com.devashishthakur.contactsapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {

    ImageView imageQR,profile;
    ImageButton scanner;
    Toolbar toolbar;
    TextView tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Setting Up Tool Bar
        setUpToolBar();

        //Declaring Some Widgets

        imageQR = (ImageView) findViewById(R.id.ImageQR);
        profile = (ImageView) findViewById(R.id.profile);
        scanner = (ImageButton) findViewById(R.id.scan);

        tx = (TextView)findViewById(R.id.toolbar_title);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/font_app.ttf");

        tx.setTypeface(custom_font);

        final Activity activity = this;
        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.setCaptureActivity(CapturedActivity.class);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            }
        });

        //SharedPreference
        SharedPreferences sharedPreferences = getSharedPreferences("shared",MODE_PRIVATE);

        //Reading Data From Shared Preference

        String name = sharedPreferences.getString("name","");
        String number = sharedPreferences.getString("number","");
        String email = sharedPreferences.getString("email","");
        String number1 = sharedPreferences.getString("number1","");
        String address = sharedPreferences.getString("address","");
        String company = sharedPreferences.getString("company","");
        String job = sharedPreferences.getString("job","");

        //Move to profile

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        //Concatinating all the data into a single screne so that it can be coded into a barcode

        String text2Qr = "name:"+name+";contact:"+number+";email:"+email+";contact2:"+number1+";address:"+address+";company:"+
                company+";job:"+job;

        //Barcode

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageQR.setImageBitmap(bitmap);
        }
        catch (WriterException e){
            e.printStackTrace();
        }
    }

    //Settings


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.settings):
                Intent intent = new Intent(MainActivity.this,InfoActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }


    void setUpToolBar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                /*
                intent.putExtra(ContactsContract.Intents.Insert.NAME,"Devashish");
                intent.putExtra(ContactsContract.Intents.Insert.PHONE,"32352342");
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL,"dksanu3@gmail.com");


                */

                String arrStr[] = result.getContents().split(";");
                for(int i =0 ; i <arrStr.length ; i++)
                {
                    String arrSubDiv[] = arrStr[i].split(":");
                    if(arrSubDiv[0].equals("name") && !(arrSubDiv[1].equals("-")))
                    {
                        intent.putExtra(ContactsContract.Intents.Insert.NAME,arrSubDiv[1]);
                    }
                    if(arrSubDiv[0].equals("contact") && !(arrSubDiv[1].equals("-")))
                    {
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE,arrSubDiv[1]);

                    }
                    if(arrSubDiv[0].equals("contact2") && !(arrSubDiv[1].equals("-")))
                    {
                        intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE,arrSubDiv[1]);
                    }
                    if(arrSubDiv[0].equals("email") && !(arrSubDiv[1].equals("-")))
                    {
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL,arrSubDiv[1]);
                    }
                    if(arrSubDiv[0].equals("address") && !(arrSubDiv[1].equals("-")))
                    {
                        intent.putExtra(ContactsContract.Intents.Insert.POSTAL,arrSubDiv[1]);
                    }
                    if(arrSubDiv[0].equals("company") && !(arrSubDiv[1].equals("-")))
                    {
                        intent.putExtra(ContactsContract.Intents.Insert.COMPANY,arrSubDiv[1]);
                    }
                    if(arrSubDiv[0].equals("job") && !(arrSubDiv[1].equals("-")))
                    {
                        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE,arrSubDiv[1]);
                    }
                }
                intent.putExtra(ContactsContract.Intents.Insert.POSTAL_TYPE,"Address");

                startActivity(intent);
                //Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

