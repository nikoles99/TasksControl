package ru.qulix.olesyuknv.taskscontrol;

import java.util.ArrayList;
import java.util.List;

/**
 * The imaginary server.
 * Using pattern Singleton
 * @author OlesyukNV
 */
public class StubServer implements Server{

    /**
     * List for keep tasks.
     */
    private List<Task> tasks = new ArrayList<Task>();

    /**
     * link on the SubServer object
     */
    private static volatile StubServer instance;

    /**
     * Constructor
     * @return link on the SubServer object
     */
    public static synchronized StubServer getInstance() {
        if (instance == null) {
            instance = new StubServer();
        }
        return instance;
    }

    /**
     *
     * @param task task for changes
     * @param position position this task
     */
    @Override
    public void updateDataOnServer(Task task, int position) {
        tasks.get(position).setName(task.getName());
        tasks.get(position).setWorkTime(task.getWorkTime());
        tasks.get(position).setStartDate(task.getStartDate());
        tasks.get(position).setFinishDate(task.getFinishDate());
        tasks.get(position).setStatus(task.getStatus());
    }

    /**
     *
     * @return list of tasks
     */
    @Override
    public List<Task> loadDataFromServer() {
        return tasks;
    }

    /**
     *
     * @param task task for adding
     */
    @Override
    public void addDataOnServer(Object task) {
        tasks.add((Task) task);
    }

    /**
     *
     * @param position position delete task
     */
    @Override
    public void removeTask(int position) {
        tasks.remove(position);
    }
}
