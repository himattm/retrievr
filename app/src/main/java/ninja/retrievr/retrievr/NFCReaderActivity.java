package ninja.retrievr.retrievr;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by mattmckenna on 4/11/15.
 */
public class NFCReaderActivity extends Activity {

    private ImageView squareReticule;

    private NfcAdapter scanAdapter;
    private PendingIntent pendingIntent;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        scanAdapter = NfcAdapter.getDefaultAdapter(this); // if nfc is null, throw error

        pendingIntent = PendingIntent.getActivity(this,0,new Intent(this,getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),0);


        squareReticule = (ImageView)findViewById(R.id.squareReticule);
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
    protected void onNewIntent(Intent intent) {
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
//        Toast.makeText(this, tagFromIntent.toString(), Toast.LENGTH_LONG).show();

        Intent wrapperIntent = new Intent(getApplicationContext(), WriteActivity.class);

        wrapperIntent.putExtra(getString(R.string.nfc_intent_extras), intent.getParcelableExtra(NfcAdapter.EXTRA_TAG));

        startActivity(wrapperIntent);
    }
}
