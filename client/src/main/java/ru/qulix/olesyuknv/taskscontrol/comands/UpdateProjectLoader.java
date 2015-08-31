package ru.qulix.olesyuknv.taskscontrol.comands;

import java.io.IOException;
import java.util.List;

import com.example.models.Project;
import com.example.server.TaskServer;

import android.app.Activity;

import ru.qulix.olesyuknv.taskscontrol.TasksControlLoader;

/**
 * Поток обновления проекта
 *
 * @author Q-OLN
 */
public class UpdateProjectLoader extends TasksControlLoader<Project, Void> {

    public UpdateProjectLoader(TaskServer server, Activity activity) {
        super(server, activity);
    }

    @Override
    protected Void processing(List<Project> projects) throws IOException {
        for (Project project : projects) {
            getServer().update(project);
        }
        return null;
    }
}
