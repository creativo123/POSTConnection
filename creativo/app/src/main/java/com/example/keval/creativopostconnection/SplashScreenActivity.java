package com.example.keval.creativopostconnection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

/**
 * Created by keval on 27-03-2017.
 * for more visit www.creativek.me
 */

public class SplashScreenActivity extends Activity {

    public static int SPLASH_TIMEOUT = 1500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(home);
                finish();
            }
        }, SPLASH_TIMEOUT);
    }
}
