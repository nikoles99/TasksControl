package ru.qulix.olesyuknv.taskscontrol.project;

import java.io.IOException;
import java.util.List;

import com.example.models.Project;
import com.example.server.TaskServer;

import ru.qulix.olesyuknv.taskscontrol.TasksControlLoader;

import android.app.Activity;

/**
 * Поток добавления проекта
 *
 * @author Q-OLN
 */
public class AddProjectLoader extends TasksControlLoader<Project, Void> {

    public AddProjectLoader(TaskServer server, Activity activity) {
        super(server, activity);
    }

    @Override
    protected Void processing(List<Project> projects) throws IOException {
        for (Project project : projects) {
            getServer().add(project);
        }
        return null;
    }
}