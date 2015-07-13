package ru.qulix.olesyuknv.taskscontrol.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android.util.Log;

import ru.qulix.olesyuknv.taskscontrol.DateType;
import ru.qulix.olesyuknv.taskscontrol.models.StatusTask;
import ru.qulix.olesyuknv.taskscontrol.models.Task;

/**
 * Мнимый сервер.
 *
 * @author QULIX-OLESYUKNV
 */
public class StubServer implements TaskServer {

    private static final long SERVER_DELAY_MS = 1000;

    private static final Lock LOCKER = new ReentrantLock();

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
        LOCKER.lock();
        task.setId(++idTask);
        LOCKER.unlock();
    }

    private void imitationServerWork() {
        try {
            TimeUnit.MICROSECONDS.sleep(SERVER_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Log.e("InterruptedException", e.getMessage());
        }
    }

    @Override
    public List<Task> load(int startPosition, int finishPosition) {
        LOCKER.lock();
        imitationServerWork();
        List<Task> list = new ArrayList<Task>(tasksSet);

        if (startPosition >= 0 && finishPosition <= list.size()) {
            return list.subList(startPosition, finishPosition);
        } else if (startPosition >= 0 && startPosition <= list.size() && finishPosition >= list.size()) {
            return list.subList(startPosition, list.size());
        }
        LOCKER.unlock();
        return new ArrayList<Task>();
    }

    @Override
    public void add(Task task) {
        LOCKER.lock();
        imitationServerWork();
        generateTaskId(task);
        tasksSet.add(task);
        LOCKER.unlock();
    }

    @Override
    public void update(Task task) {
        LOCKER.lock();
        imitationServerWork();
        if (tasksSet.remove(task)) {
            tasksSet.add(task);
        }
        LOCKER.unlock();
    }

    @Override
    public void remove(Task task) {
        LOCKER.lock();
        imitationServerWork();
        tasksSet.remove(task);
        LOCKER.unlock();

    }

    private void initialData() {
        DateType dateType = new DateType();
        Task task1 = new Task("name", 1, dateType.getData("10.02.2011"),
                new DateType().getData("10.02.2015"), StatusTask.COMPLETED);
        generateTaskId(task1);
        tasksSet.add(task1);

        Task task2 = new Task("name", 2, dateType.getData("1.08.2014"),
                new DateType().getData("10.02.2015"), StatusTask.IN_PROCESS);
        generateTaskId(task2);
        tasksSet.add(task2);

        Task task3 = new Task("name", 3, dateType.getData("18.01.2010"),
                new DateType().getData("10.02.2015"), StatusTask.NOT_STARTED);
        generateTaskId(task3);
        tasksSet.add(task3);

        Task task4 = new Task("name", 4, dateType.getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task4);
        tasksSet.add(task4);

        Task task5 = new Task("task5", 5, dateType.getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task5);
        tasksSet.add(task5);

        Task task6 = new Task("task6", 4, dateType.getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task6);
        tasksSet.add(task6);

        Task task7 = new Task("task7", 4, dateType.getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task7);
        tasksSet.add(task7);

        Task task8 = new Task("task8", 4, dateType.getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task8);
        tasksSet.add(task8);

        Task task9 = new Task("task9", 4, dateType.getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task9);
        tasksSet.add(task9);

        Task task10 = new Task("task10", 4, dateType.getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task10);
        tasksSet.add(task10);

        Task task11 = new Task("task11", 4, dateType.getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task11);
        tasksSet.add(task11);

        Task task12 = new Task("task12", 4, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task12);
        tasksSet.add(task12);

        Task task13 = new Task("task13", 4, dateType.getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task13);
        tasksSet.add(task13);

        Task task14 = new Task("task14", 4, dateType.getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task14);
        tasksSet.add(task14);

        Task task15 = new Task("task15", 4, dateType.getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task15);
        tasksSet.add(task15);

        Task task16 = new Task("task16", 4, dateType.getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task16);
        tasksSet.add(task16);

        Task task17 = new Task("name", 4, dateType.getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task17);
        tasksSet.add(task17);

        Task task18 = new Task("task18", 4, dateType.getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task18);
        tasksSet.add(task18);

        Task task19 = new Task("task19", 4, dateType.getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task19);
        tasksSet.add(task19);

        Task task20 = new Task("task20", 4, dateType.getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task20);
        tasksSet.add(task20);

        Task task21 = new Task("task21", 4, dateType.getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task21);
        tasksSet.add(task21);

        Task task22 = new Task("task22", 4, dateType.getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task22);
        tasksSet.add(task22);

        Task task23 = new Task("task23", 4, dateType.getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED);
        generateTaskId(task23);
        tasksSet.add(task23);


    }
}
