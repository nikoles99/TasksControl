package com.example.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.example.models.StatusTask;
import com.example.models.Task;
import com.example.utils.DateFormatUtility;

/**
 * Мнимый сервер.
 *
 * @author Q-OLN
 */
public class StubServer implements TaskServer {

    private static final long SERVER_DELAY_MS = 1000;

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * Идентификатор задачи на сервере
     */
    private int idTask = 0;

    /**
     * Хранение всех задач на сервере
     */
    private Set<Task> tasksSet = new HashSet<Task>();

    public StubServer() {
        initialData();
    }

    private void generateTaskId(Task task) {
        task.setId(++idTask);
    }

    private void imitationServerWork() {
        try {
            TimeUnit.MILLISECONDS.sleep(SERVER_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public List<Task> load(int start, int finish) {
        imitationServerWork();

        if (start < 0 || start > tasksSet.size()) {
            return new ArrayList<Task>();
        }
        List<Task> list = new ArrayList<Task>(tasksSet);
        return list.subList(start, Math.min(finish, list.size()));

    }

    @Override
    public void add(Task task) {
        imitationServerWork();
        generateTaskId(task);
        tasksSet.add(task);

    }

    @Override
    public void update(Task task) {
        imitationServerWork();

        if (tasksSet.remove(task)) {
            tasksSet.add(task);
        }

    }

    @Override
    public void remove(Task task) {
        imitationServerWork();
        tasksSet.remove(task);
    }

    private void initialData() {
        tasksSet.add(createTask("Русский", 1, "10.02.2011", "10.02.2015", StatusTask.COMPLETED));
        tasksSet.add(createTask("name2", 2, "1.08.2014", "10.02.2015", StatusTask.IN_PROCESS));
        tasksSet.add(createTask("name3", 3, "18.01.2010", "10.02.2015", StatusTask.NOT_STARTED));
        tasksSet.add(createTask("name4", 4, "20.12.2009", "10.02.2015", StatusTask.POSTPONED));
        tasksSet.add(createTask("task5", 5, "20.12.2009", "10.02.2015", StatusTask.POSTPONED));
        tasksSet.add(createTask("task6", 4, "20.12.2009", "10.02.2015", StatusTask.IN_PROCESS));
        tasksSet.add(createTask("task7", 4, "20.12.2009", "10.02.2015", StatusTask.POSTPONED));
        tasksSet.add(createTask("task8", 4, "20.12.2009", "10.02.2015", StatusTask.IN_PROCESS));
        tasksSet.add(createTask("task9", 4, "20.12.2009", "10.02.2015", StatusTask.POSTPONED));
        tasksSet.add(createTask("task10", 4, "20.12.2009", "10.02.2015", StatusTask.NOT_STARTED));
        tasksSet.add(createTask("task11", 4, "20.12.2009", "10.02.2015", StatusTask.COMPLETED));
        tasksSet.add(createTask("task12", 4, "20.12.2009", "10.02.2015", StatusTask.POSTPONED));
        tasksSet.add(createTask("task13", 4, "20.12.2009", "10.02.2015", StatusTask.IN_PROCESS));
        tasksSet.add(createTask("task14", 4, "20.12.2009", "10.02.2015", StatusTask.POSTPONED));
        tasksSet.add(createTask("task15", 4, "20.12.2009", "10.02.2015", StatusTask.COMPLETED));
        tasksSet.add(createTask("task16", 4, "20.12.2009", "10.02.2015", StatusTask.NOT_STARTED));
        tasksSet.add(createTask("task17", 4, "20.12.2009", "10.02.2015", StatusTask.POSTPONED));
        tasksSet.add(createTask("task18", 4, "20.12.2009", "10.02.2015", StatusTask.COMPLETED));
        tasksSet.add(createTask("task19", 4, "20.12.2009", "10.02.2015", StatusTask.POSTPONED));
        tasksSet.add(createTask("task20", 4, "20.12.2009", "10.02.2015", StatusTask.IN_PROCESS));
        tasksSet.add(createTask("task21", 4, "20.12.2009", "10.02.2015", StatusTask.COMPLETED));
        tasksSet.add(createTask("task22", 4, "20.12.2009", "10.02.2015", StatusTask.NOT_STARTED));
        tasksSet.add(createTask("task23", 4, "20.12.2009", "10.02.2015", StatusTask.COMPLETED));
        tasksSet.add(createTask("task24", 4, "20.12.2009", "10.02.2015", StatusTask.IN_PROCESS));
    }

    private Task createTask(String name, int workTime, String start, String finish, StatusTask statusTask) {
        Date startDate = getDate(start);
        Date finishDate = getDate(finish);
        Task task = new Task(name, workTime, startDate, finishDate, statusTask);
        setTaskId(task);
        return task;
    }

    private Date getDate(String date) {
        return DateFormatUtility.format(date);
    }

    private void setTaskId(Task task) {
        task.setId(++idTask);
    }
}
