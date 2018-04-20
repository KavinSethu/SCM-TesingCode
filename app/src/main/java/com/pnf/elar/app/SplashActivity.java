package com.pnf.elar.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;




import com.crashlytics.android.Crashlytics;
import java.util.HashMap;

import io.fabric.sdk.android.Fabric;


public class SplashActivity extends Activity {
    private static final int SPLASH_TIME_OUT = 4000;
    ImageView imgLogo;
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_fade);
        imgLogo.startAnimation(animation);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {

			/*
             * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                try {
                    session = new UserSessionManager(getApplicationContext());
                    HashMap<String, String> user = session.getUserDetails();
                    Intent in = new Intent(SplashActivity.this, Login.class);
                    startActivity(in);
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, SPLASH_TIME_OUT);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return false;
    }

}
