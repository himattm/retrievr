package ninja.retrievr.retrievr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {


    private ParseUser currentUser;

    private int builderRequestCode = 1217;

    private int mScrollOffset = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Log in Sequence
        currentUser = ParseUser.getCurrentUser();

        if (currentUser == null) {
            // user not logged in, present them with login screen
            ParseLoginBuilder builder = new ParseLoginBuilder(MainActivity.this);

            builder.setAppLogo(R.drawable.dog_retrievr_text_small); // The Center Image

//            builder.setFacebookLoginEnabled(true);
//            builder.setTwitterLoginEnabled(true);

            startActivityForResult(builder.build(), builderRequestCode);
            // END Login Sequence
        } else {
            // View Initialization
            final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);

            populateList(recyclerView);

            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    if (Math.abs(dy) > mScrollOffset) {
                        if (dy > 0) {
                            fab.hide(true);
                        } else {
                            fab.show(true);
                        }
                    }
                }
            });

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nfcActivityIntent = new Intent(v.getContext(), NFCReaderActivity.class);
                    startActivity(nfcActivityIntent);

//                Toast.makeText(v.getContext(), "Scan NFC Now", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void populateList(final RecyclerView recyclerView) {
        if (currentUser != null) {
            ParseRelation<ParseObject> relation = currentUser.getRelation("item");
            relation.getQuery().findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> results, ParseException e) {
                    if (e != null) {
                        // There was an error
                    } else {
                        // results have all the Posts the current user liked.
                        ArrayList<RetrievrItem> items = new ArrayList<>();
                        for (ParseObject item : results) {
                            items.add(new RetrievrItem((String) item.get("name")));
                        }

                        recyclerView.setAdapter(new RetrievrRecyclerAdapter(items));
                    }
                }
            });
        }
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
            } else {
                Intent mainActivityIntent = new Intent(this, MainActivity.class);

                startActivity(mainActivityIntent);
                this.finish();
            }

        }
    }

}
