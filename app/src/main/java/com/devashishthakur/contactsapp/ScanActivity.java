package com.devashishthakur.contactsapp;
import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends AppCompatActivity {
    private Button scan_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        scan_btn = (Button) findViewById(R.id.scan_btn);
        final Activity activity = this;
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
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
                    if(arrSubDiv[0].equals("name"))
                    {
                        intent.putExtra(ContactsContract.Intents.Insert.NAME,arrSubDiv[1]);
                    }
                    if(arrSubDiv[0].equals("contact"))
                    {
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE,arrSubDiv[1]);

                    }
                    if(arrSubDiv[0].equals("contact2"))
                    {
                        intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE,arrSubDiv[1]);
                    }
                    if(arrSubDiv[0].equals("email"))
                    {
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL,arrSubDiv[1]);
                    }
                }
                intent.putExtra(ContactsContract.Intents.Insert.POSTAL_TYPE,"dasd dsa dsa dsaj ndsajn jasdnjnas dsa ");

                startActivity(intent);
                Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
