package ru.qulix.olesyuknv.taskscontrol;

import com.example.server.TaskServer;

import ru.qulix.olesyuknv.taskscontrol.server.HttpTaskServer;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Получение сервера.
 *
 * @author Q-OLN
 */
public class TasksControlApplication extends Application {

    private static final String URL = "URL";

    private TaskServer server;

    @Override
    public void onCreate() {
        super.onCreate();
        server = new HttpTaskServer();
        updateUrlSetting();
    }

    /**
     * Получение сервера, посылающего http запросы
     */
    public TaskServer getServer() {
        return server;
    }

    /**
     * обновление настроек URL
     */
    public void updateUrlSetting() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(TasksControlApplication.this);
        ((HttpTaskServer) server).setUrl(sharedPreferences.getString(URL, "http://192.168.9.117:8080/server/Servlet").trim());
    }
}
