package ru.qulix.olesyuknv.taskscontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    int logoTimer = 0;

                    while (logoTimer < 5000) {
                        sleep(100);
                        logoTimer = logoTimer + 100;
                    }
                    startActivity(new Intent("android.intent.action.MainActivity"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };
        logoTimer.start();
    }
}
