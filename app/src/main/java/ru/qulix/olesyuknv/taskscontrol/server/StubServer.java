package ru.qulix.olesyuknv.taskscontrol.server;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import ru.qulix.olesyuknv.taskscontrol.models.ConvertDate;
import ru.qulix.olesyuknv.taskscontrol.models.StatusTask;
import ru.qulix.olesyuknv.taskscontrol.models.Task;

/**
 * Мнимый сервер.
 *
 * @author QULIX-OLESYUKNV
 */
public class StubServer implements TaskServer {


    private static final long SERVER_SECONDS_DELAY = 1;
    /**
     * Идентификатор задачи на сервере
     */
    private int idTask = 0;

    /**
     * Хранение всех задач на сервере
     */
    private Set<Task> tasksSet = new LinkedHashSet<Task>();

    public StubServer() {
        initialData();
    }

    private void generateTaskId(Task task) {
        task.setId(++idTask);
    }

    private void imitationServerWork() {
        try {
            TimeUnit.SECONDS.sleep(SERVER_SECONDS_DELAY);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public synchronized List<Task> load() {
        imitationServerWork();
        return new ArrayList<Task>(tasksSet);
    }

    @Override
    public synchronized void add(Task task) {
        imitationServerWork();
        generateTaskId(task);
        tasksSet.add(task);
    }

    @Override
    public synchronized void update(Task task) {
        imitationServerWork();
        if (tasksSet.remove(task)) {
            tasksSet.add(task);
        }
    }

    @Override
    public synchronized void remove(Task task) {
        imitationServerWork();
        tasksSet.remove(task);
    }

    private void initialData() {
        Task task1 = new Task("name1", 1, new ConvertDate().getDataFromString("10.02.2011"),
                new ConvertDate().getDataFromString("10.02.2015"), StatusTask.COMPLETED);
        generateTaskId(task1);
        tasksSet.add(task1);

        Task task2 = new Task("name2", 2, new ConvertDate().getDataFromString("1.08.2014"),
                new ConvertDate().getDataFromString("10.02.2015"), StatusTask.IN_PROCESS);
        generateTaskId(task2);
        tasksSet.add(task2);

        Task task3 = new Task("name3", 3, new ConvertDate().getDataFromString("18.01.2010"),
                new ConvertDate().getDataFromString("10.02.2015"), StatusTask.NOT_STARTED);
        generateTaskId(task3);
        tasksSet.add(task3);

        Task task4 = new Task("name4", 4, new ConvertDate().getDataFromString("20.12.2009"),
                new ConvertDate().getDataFromString("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task4);
        tasksSet.add(task4);

        Task task5 = new Task("name1", 1, new ConvertDate().getDataFromString("10.02.2011"),
                new ConvertDate().getDataFromString("10.02.2015"), StatusTask.COMPLETED);
        generateTaskId(task5);
        tasksSet.add(task5);

        Task task6 = new Task("name1", 1, new ConvertDate().getDataFromString("10.02.2011"),
                new ConvertDate().getDataFromString("10.02.2015"), StatusTask.COMPLETED);
        generateTaskId(task6);
        tasksSet.add(task6);
    }
}
