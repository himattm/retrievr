package ninja.retrievr.retrievr;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParsePushBroadcastReceiver;
import com.parse.ParseUser;

import org.json.JSONObject;

/**
 * Created by mattmckenna on 4/12/15.
 */
public class PushIntercepter extends ParsePushBroadcastReceiver {

    public PushIntercepter(){
        super();
        System.out.println("initializing push interceptor");
    }

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            System.out.println(json);
            if (json.has("id")) {
                // special 'item found' message
                String id = json.getString("id");
                if (id.equals(ParseUser.getCurrentUser().getObjectId())) {
                    // my item has been found!
                    json.remove("id");
                    intent.getExtras().putString("com.parse.Data", json.toString());
                    super.onPushReceive(context, intent);
                } else {
                    // not your item, don't do anything
                }
            } else {
                // regular message, carry on.
                super.onPushReceive(context, intent);
            }

        } catch (Exception e) {
            Log.d("Broadcast", "JSON Exception: " + e.getMessage());
        }

    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}
