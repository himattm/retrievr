package ninja.retrievr.retrievr;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseTwitterUtils;
import com.parse.SaveCallback;


/**
 * Created by mattmckenna on 4/11/15.
 */
public class RetrievrApp extends Application {

    public void onCreate() {
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
//        ParseTwitterUtils.initialize(consumerKey, consumerSecret);
//        ParseFacebookUtils.initialize(this);

        Parse.initialize(this, "326Ce5L5lxxfMFHbfa1wE7OFLvphQ2FC2MxeJlWW", "PftH2OrYoAwvR1XcHrZxLLGGWIEQBqT2mDelM6oY");

        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }
}
