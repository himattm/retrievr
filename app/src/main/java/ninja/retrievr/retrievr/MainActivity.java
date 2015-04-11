package com.example.eric.retrievr;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.nfc.*;

import static android.nfc.NfcAdapter.ReaderCallback;

public class MainActivity extends ActionBarActivity {
    public static boolean IsItFuckingWorking = false;
    private TextView myText = null;

    private NfcAdapter mNfcAdapter;

    @Override
    protected void onResume() {
        // protected void onCreate(Bundle savedInstanceState) {
        super.onResume();
        // super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // LinearLayout lView = (LinearLayout) findViewById(R.id.mylinearlayout);
        // myText.setText("My Text");
        // lView.addView(myText);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
        }

        if (!mNfcAdapter.isEnabled()) {
            IsItFuckingWorking = false;
            Toast.makeText(this, "This NFC on this device is not active.", Toast.LENGTH_LONG).show();
        } else {
            IsItFuckingWorking = true;
            Toast.makeText(this, "This device supports NFC.", Toast.LENGTH_LONG).show();

        }

    }
    protected void onNewIntent() {
        Toast.makeText(this, "NFC Shit received.", Toast.LENGTH_LONG).show();
        Intent intent = null;
        super.onNewIntent(intent);
        intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

    }





/*
    if(mNfcAdapter==null)
    {
        // Stop here, we definitely need NFC
        Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
        finish();

        IsItFuckingWorking = false;

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    if(!mNfcAdapter.isEnabled())

    {
        mTextView.setText("NFC is disabled. ARSEHOLE");
    }

    else

    {
        mTextView.setText("NFC F'N AVAILABLE!");
        IsItFuckingWorking = true;

    }
*/




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
