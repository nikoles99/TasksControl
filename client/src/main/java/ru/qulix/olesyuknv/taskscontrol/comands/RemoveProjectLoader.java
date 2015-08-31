package ru.qulix.olesyuknv.taskscontrol.comands;

import java.io.IOException;
import java.util.List;

import com.example.models.Project;
import com.example.server.TaskServer;

import android.app.Activity;

import ru.qulix.olesyuknv.taskscontrol.TasksControlLoader;

/**
 * Поток удаления проекта
 *
 * @author Q-OLN
 */
public class RemoveProjectLoader extends TasksControlLoader<Project, Void> {

    public RemoveProjectLoader(TaskServer server, Activity activity) {
        super(server, activity);
    }

    @Override
    protected Void processing(List<Project> projects) throws IOException {
        for (Project project : projects) {
            getServer().remove(project);
        }
        return null;
    }
}
