package ninja.retrievr.retrievr;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.IOException;
import java.nio.charset.Charset;


public class WriteActivity extends Activity {

    private ImageView squareReticule;
    private TextView rescanTextView;
    private EditText newItemName;

    private NfcAdapter scanAdapter;
    private PendingIntent pendingIntent;
    private Tag tagFromIntent;
    private byte[] hexIDBytes;
    private String hexID;

    private boolean writeProtect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        // Get the intent from the NFCReaderActivity
        Intent nfcIntent = getIntent();
        tagFromIntent = nfcIntent.getParcelableExtra(getString(R.string.nfc_intent_extras));

        hexIDBytes = tagFromIntent.getId();

        hexID = convertByteArraytoString(hexIDBytes);
        Log.d("HEX ID",hexID);

//        Toast.makeText(this, hexID, Toast.LENGTH_LONG).show();

        scanAdapter = NfcAdapter.getDefaultAdapter(this); // if nfc is null, throw error

        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);


        newItemName = (EditText) findViewById(R.id.newItemNameEditText);

        squareReticule = (ImageView) findViewById(R.id.newItemSquareReticule);
        rescanTextView = (TextView) findViewById(R.id.textViewRescanNFC);
        Animation pulseAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pulse);
        squareReticule.startAnimation(pulseAnimation);
        rescanTextView.startAnimation(pulseAnimation);
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanAdapter.disableForegroundDispatch(this); // prevent activity from intercepting nfc when closed
    }

    @Override
    protected void onResume() {
        super.onResume();
        scanAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
    }

    @Override
    protected void onNewIntent(Intent intent) {

        if (itemNamesValid()) {

            Intent takeUsHome = new Intent(this, MainActivity.class);

            if (writeToParseDB()) {
                startActivity(takeUsHome);
            } else {
                Toast.makeText(this, "Something's not right here,\nplease try again", Toast.LENGTH_LONG).show();
            }
        } else {
            newItemName.setError("Please enter an item and scan again");
        }
    }

    public String getItemName() {
        return newItemName.getText().toString();
    }

    // Determines if the New Item EditText field has been populated by the user
    public boolean itemNamesValid() {
        return getItemName().length() >= 1;
    }

    public boolean writeToParseDB() {
        try {
            Log.d("ParseWrite","Begin-------------------");
            ParseObject currentUser = ParseUser.getCurrentUser();

            ParseObject newItem = new ParseObject(getString(R.string.newItemClass));

            newItem.put(getString(R.string.newItemName), getItemName()); // String
            newItem.put(getString(R.string.newItemTagID), hexID); // String

            Log.d("CurrentUser","Start");
            newItem.put(getString(R.string.newItemCurrentUser), currentUser); // Object
            Log.d("CurrentUser","End");

            newItem.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null) {
                        Log.d("SaveinBackground","Save Done");
                        finish();
                    } else {
                        Log.e("SaveinBackground", e.getMessage());
                    }
                }
            });

            return true; // Save to database successful
        } catch (Exception e) {
            return false;
        }
    }

    public String convertByteArraytoString(byte[] array){
        String result = "";

        for(Byte b : array){
            result += b.toString();
        }
        return result;
    }


}
