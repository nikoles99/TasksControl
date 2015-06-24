package ru.qulix.olesyuknv.taskscontrol;

import java.util.ArrayList;
import java.util.List;

/**
 * The imaginary server.
 * Using pattern Singleton
 * @author OlesyukNV
 */
public class StubServer implements Server {

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
    public void updateDataOnServer(Object task, int position) {

    }

    /**
     *
     * @return list of tasks
     */
    @Override
    public List<Object> loadDataFromServer() {
        return null;
    }

    /**
     *
     * @param task task for adding
     */
    @Override
    public void addDataOnServer(Object task) {

    }

    /**
     *
     * @param position position delete task
     */
    @Override
    public void removeTask(int position) {

    }
}
