package ru.qulix.olesyuknv.taskscontrol;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The imaginary server.
 *
 * @author OlesyukNV
 */
public class StubServer implements TaskServer {

    private static int idTask = 0;

    private static Set<Task> taskSet = new LinkedHashSet<Task>();

    @Override
    public void update(Task task) {
        if (taskSet.contains(task)) {
            taskSet.remove(task);
            taskSet.add(task);
        }

    }

    @Override
    public List<Task> load() {
        return new ArrayList<Task>(taskSet);
    }

    @Override
    public void add(Task task) {
        task.setId(++idTask);
        taskSet.add(task);
    }

    @Override
    public void remove(Task task) {
        if (taskSet.contains(task)) {
            taskSet.remove(task);
            return;
        }
    }
}
