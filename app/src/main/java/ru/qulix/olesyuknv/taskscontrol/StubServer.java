package ru.qulix.olesyuknv.taskscontrol;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Мнимый сервер.
 *
 * @author OlesyukNV
 */
public class StubServer implements TaskServer {

    /**
     * Идентификатор задачи на сервере
     */
    private int idTask = 0;

    /**
     * Хранение всех задач на сервере
     */
    private Set<Task> tasksSet = new LinkedHashSet<Task>();

    @Override
    public void update(Task task) {
        if (tasksSet.contains(task)) {
            tasksSet.remove(task);
            tasksSet.add(task);
        }
    }

    @Override
    public List<Task> load() {
        return new ArrayList<Task>(tasksSet);
    }

    @Override
    public void add(Task task) {
        task.setId(++idTask);
        tasksSet.add(task);
    }

    @Override
    public void remove(Task task) {
        if (tasksSet.contains(task)) {
            tasksSet.remove(task);
        }
    }
}
