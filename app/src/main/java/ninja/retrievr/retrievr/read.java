package com.example.eric.retrievr;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentFilter;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Parcelable;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import android.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.nfc.*;
import com.example.eric.retrievr.R;

/**
 * Created by Eric on 4/11/2015.
 */
public class read extends Activity {

    Tag detectedTag;
    TextView txtType,txtSize,txtWrite,txtRead;
    NfcAdapter nfcAdapter;
    IntentFilter[] readTagFilters;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.read);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);  //I added "this" here, might need to delete
        detectedTag =getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
        //txtType  = (TextView) findViewById(R.id.txtType);
        txtType = (TextView) findViewById(R.id.txtType);
        txtSize  = (TextView) findViewById(R.id.txtsize);
        txtWrite = (TextView) findViewById(R.id.txtwrite);
        txtRead  = (TextView) findViewById(R.id.txt_read);

        pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(this,getClass()).
                        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter filter2     = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        readTagFilters = new IntentFilter[]{tagDetected,filter2};

    }

    protected void onNewIntent(Intent intent) {

        if(getIntent().getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)){
            detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            readFromTag(getIntent());
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, readTagFilters, null);
    }


    public void readFromTag(Intent intent){

        Ndef ndef = Ndef.get(detectedTag);


        try{
            ndef.connect();

            txtType.setText(ndef.getType().toString());
            txtSize.setText(String.valueOf(ndef.getMaxSize()));
            txtWrite.setText(ndef.isWritable() ? "True" : "False");
            Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if (messages != null) {
                NdefMessage[] ndefMessages = new NdefMessage[messages.length];
                for (int i = 0; i < messages.length; i++) {
                    ndefMessages[i] = (NdefMessage) messages[i];
                }
                NdefRecord record = ndefMessages[0].getRecords()[0];

                byte[] payload = record.getPayload();
                String text = new String(payload);
                txtRead.setText(text);


                ndef.close();

            }
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Cannot Read From Tag.", Toast.LENGTH_LONG).show();
        }
    }

}


