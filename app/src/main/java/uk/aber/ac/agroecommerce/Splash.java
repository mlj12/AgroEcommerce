package uk.aber.ac.agroecommerce;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends Activity {

    final private static int SPLASH_TIME_OUT = 4000; // 4 secs
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

            @Override

            public void run() {

                Intent i = new Intent(Splash.this,MainActivity.class);

                startActivity(i);

                // close this activity

                finish();

            }

        }, SPLASH_TIME_OUT); // wait for 5 seconds
    }
}
