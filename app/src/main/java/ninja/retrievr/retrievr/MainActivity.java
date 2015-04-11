package ninja.retrievr.retrievr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.ui.ParseLoginActivity;
import com.parse.ui.ParseLoginBuilder;


public class MainActivity extends Activity {


    private ParseUser currentUser;

    private int builderRequestCode = 1217;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentUser = ParseUser.getCurrentUser();

        if (currentUser == null) {
            // user not logged in, present them with login screen
            ParseLoginBuilder builder = new ParseLoginBuilder(MainActivity.this);

            builder.setAppLogo(R.drawable.dog_retrievr_text_small); // The Center Image

            builder.setFacebookLoginEnabled(true);
            builder.setTwitterLoginEnabled(true);

            startActivityForResult(builder.build(), builderRequestCode);

        }


        // initialize the ListView
    }

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
        } else if (id == R.id.logout_overflow) {
            ParseUser.logOut();
            Toast.makeText(this, getString(R.string.successful_logout), Toast.LENGTH_SHORT).show();
            Intent mainActivityIntent = new Intent(this, MainActivity.class);

            startActivity(mainActivityIntent);
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == builderRequestCode) {
            if (resultCode == RESULT_CANCELED) {
                this.finish();
            }
        }
    }

}
