package ninja.retrievr.retrievr;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by mattmckenna on 4/11/15.
 */
public class NFCActivity extends Activity {

    private ImageView squareReticule;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        squareReticule = (ImageView)findViewById(R.id.squareReticule);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pulse);
        squareReticule.startAnimation(animation);

    }

}
