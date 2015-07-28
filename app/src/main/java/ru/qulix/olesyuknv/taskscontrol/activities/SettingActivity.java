package ru.qulix.olesyuknv.taskscontrol.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import ru.qulix.olesyuknv.taskscontrol.R;

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
}