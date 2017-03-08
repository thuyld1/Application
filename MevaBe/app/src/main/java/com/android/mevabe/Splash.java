package com.android.mevabe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by thuyld on 3/8/17.
 */
public class Splash extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGHT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


    }

    @Override
    protected void onResume() {
        super.onResume();

        // Init data for application
        initialData();

        // Go to main screen
        goToMainScreen();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Init data for application
     */
    private void initialData() {

    }

    /**
     * Go to main screen
     */
    private void goToMainScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent;

                MainActivity act = new MainActivity();
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }


}
