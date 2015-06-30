package ru.qulix.olesyuknv.taskscontrol;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The imaginary server.
 *
 * @author OlesyukNV
 */
public class StubServer implements Server {

    /**
     * List for keep tasks.
     */
    private static int idTask = 0;

    private static Map<Integer, Task> map = new HashMap<Integer, Task>();


   /* map.put(1, new Task("name1", 1, new Date(), new SimpleDateFormat("dd.MM.yyyy").parse("10.20.2014"), StatusTask.NOT_STARTED));
    map.put(2, new Task("name2", 2, new Date(), new SimpleDateFormat("dd.MM.yyyy").parse("1.12.2011"), StatusTask.POSTPONED));
    map.put(3, new Task("name3", 3, new Date(), new SimpleDateFormat("dd.MM.yyyy").parse("15.01.1995"), StatusTask.COMPLETED));
    map.put(4, new Task("name4", 4, new Date(), new SimpleDateFormat("dd.MM.yyyy").parse("8.02.2001"), StatusTask.IN_PROCESS));
    map.put(5, new Task("name5", 5, new Date(), new SimpleDateFormat("dd.MM.yyyy").parse("16.12.2005"), StatusTask.NOT_STARTED));*/


    /**
     * @param task task for changes
     */
    @Override
    public void updateTask(Task task) {
        map.put(task.getId(), task);
    }

    /**
     * @return list of tasks
     */
    @Override
    public List<Task> loadTasks() {
        return new ArrayList<Task>(map.values());
    }

    /**
     * @param task task for adding
     */
    @Override
    public void addTask(Task task) {
        task.setId(++idTask);
        map.put(task.getId(), task);
    }

    /**
     * @param task delete task
     */
    @Override
    public void removeTask(Task task) {
        int key = task.getId();
        if (map.containsKey(key)) {
            map.remove(key);
            return;
        }
    }
}
