package ru.qulix.olesyuknv.taskscontrol.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import android.util.Log;

import ru.qulix.olesyuknv.taskscontrol.utils.DateFormatUtility;
import ru.qulix.olesyuknv.taskscontrol.models.StatusTask;
import ru.qulix.olesyuknv.taskscontrol.models.Task;

/**
 * Мнимый сервер.
 *
 * @author QULIX-OLESYUKNV
 */
public class StubServer implements TaskServer {

    private static final long SERVER_DELAY_MS = 1000;
    private static final String LOG_TAG = StubServer.class.getName();

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
            Log.e(LOG_TAG, e.getMessage(), e);
        }
    }

    @Override
    public List<Task> load(int start, int finish) {
        imitationServerWork();
        try {
            lock.readLock().lock();
            if (start < 0 || start > tasksSet.size()) {
                return new ArrayList<Task>();
            }
            List<Task> list = new ArrayList<Task>(tasksSet);
            return list.subList(start, Math.min(finish, list.size()));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void add(Task task) {
        imitationServerWork();
        try {
            lock.writeLock().lock();
            generateTaskId(task);
            tasksSet.add(task);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void update(Task task) {
        imitationServerWork();
        try {
            lock.writeLock().lock();

            if (tasksSet.remove(task)) {
                tasksSet.add(task);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void remove(Task task) {
        imitationServerWork();
        try {
            lock.writeLock().lock();
            tasksSet.remove(task);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void initialData() {
        tasksSet.add(createTask("name1", 1, "10.02.2011", "10.02.2015", StatusTask.COMPLETED));
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
