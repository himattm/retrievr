package ninja.retrievr.retrievr;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseTwitterUtils;


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
    }
}