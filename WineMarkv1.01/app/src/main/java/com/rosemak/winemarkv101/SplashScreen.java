package com.rosemak.winemarkv101;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by stevierose on 3/5/15.
 */
public class SplashScreen extends Activity {

    private static int SPLASH_SCREEN_DELAY = 3000;
    private boolean splashing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        if (!splashing) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);

                    finish();

                }

            }, SPLASH_SCREEN_DELAY);

            splashing = true;
        }

    }
}
