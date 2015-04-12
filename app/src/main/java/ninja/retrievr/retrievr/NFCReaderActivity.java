package ninja.retrievr.retrievr;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by mattmckenna on 4/11/15.
 */
public class NFCReaderActivity extends Activity {

    private ImageView squareReticule;

    private NfcAdapter scanAdapter;
    private PendingIntent pendingIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        scanAdapter = NfcAdapter.getDefaultAdapter(this); // if nfc is null, throw error

        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);


        squareReticule = (ImageView) findViewById(R.id.squareReticule);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pulse);
        squareReticule.startAnimation(animation);
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
    protected void onNewIntent(final Intent intent) {
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
//        Toast.makeText(this, tagFromIntent.toString(), Toast.LENGTH_LONG).show();

        byte[] tagIDBytes = tagFromIntent.getId();

        String tagID = convertByteArraytoString(tagIDBytes);

        // Query DB
        // Make desicion on new tag or existing tag
        // Launch spcified activity

        tagExists(tagID, new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e != null) {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                } else {
                    if (parseObjects.size() > 0) {
                        // IF already in your things
                        // Get the only element in the list and check if the user object ID is the same as the current user
                        Toast.makeText(getApplicationContext(), "You have already added this object", Toast.LENGTH_SHORT).show();
                        if (parseObjects.get(0).get(getString(R.string.newItemCurrentUser)).toString().equals(ParseUser.getCurrentUser().getObjectId())) {
                            Toast.makeText(getApplicationContext(), "You have already added this item", Toast.LENGTH_SHORT).show();
                        } else { // If in someone elses things
                            // TODO Add Push

                        }
                    } else { // New Tag Activity

                        Intent wrapperIntent = new Intent(getApplicationContext(), WriteActivity.class);
                        wrapperIntent.putExtra(getString(R.string.nfc_intent_extras), intent.getParcelableExtra(NfcAdapter.EXTRA_TAG));
                        startActivity(wrapperIntent);

                    }// END New Tag Activity
                }
            }
        });


    }

    public void tagExists(String tagID, FindCallback<ParseObject> callBack) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(getString(R.string.newItemClass));
        query.whereEqualTo(getString(R.string.newItemTagID), tagID);

        query.findInBackground(callBack);
    }


    public String convertByteArraytoString(byte[] array) {
        String result = "";

        for (Byte b : array) {
            result += b.toString();
        }
        return result;
    }
}
