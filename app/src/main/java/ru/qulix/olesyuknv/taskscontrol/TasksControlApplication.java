package ru.qulix.olesyuknv.taskscontrol;

import com.example.server.TaskServer;

import android.app.Application;
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
        server = new HttpTaskServer(new UrlSetting() {
            @Override
            public String getUrl() {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(TasksControlApplication.this);
                return sharedPreferences.getString("URL", "http://192.168.9.117:8080/server/Servlet").trim();
            }
        });
    }

    /**
     * Получение сервера, посылающего http запросы
     */
    public TaskServer getServer() {
        return server;
    }

}
