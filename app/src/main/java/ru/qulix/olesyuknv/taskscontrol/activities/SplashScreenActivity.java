package ru.qulix.olesyuknv.taskscontrol.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import ru.qulix.olesyuknv.taskscontrol.R;

/**
 * SplashScreen приложения.
 *
 * @author QULIX-OLESYUKNV
 */
public class SplashScreenActivity extends Activity {

    private static final long SPLASH_SCREEN_DELAY = 5 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

/**
 * Отображение SplashScreen до запуска приложения
 */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    finish();
                }

            }
        }, SPLASH_SCREEN_DELAY);
    }
}
