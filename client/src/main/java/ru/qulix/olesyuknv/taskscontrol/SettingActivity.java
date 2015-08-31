package ru.qulix.olesyuknv.taskscontrol;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Настройки приложения
 *
 * @author Q-OLN
 */
public class SettingActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ((TasksControlApplication) getApplicationContext()).updateUrlSetting();
    }
}