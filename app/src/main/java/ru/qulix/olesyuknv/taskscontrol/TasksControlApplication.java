package ru.qulix.olesyuknv.taskscontrol;

import com.example.server.TaskServer;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ru.qulix.olesyuknv.taskscontrol.utils.UrlSetting;

/**
 * Получение сервера.
 *
 * @author Q-OLN
 */
public class TasksControlApplication extends Application {

    private TaskServer server;

    @Override
    public void onCreate() {
        super.onCreate();
        server = new HttpTaskServer(TasksControlApplication.this, new UrlSetting() {
            @Override
            public String getUrl(Context context) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                return sharedPreferences.getString("URL", "http://192.168.9.117:8080/server/Servlet").trim();
            }
        });
    }

    public TaskServer getServer() {
        return server;
    }

}
