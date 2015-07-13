package ru.qulix.olesyuknv.taskscontrol.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ru.qulix.olesyuknv.taskscontrol.utils.DateType;
import ru.qulix.olesyuknv.taskscontrol.models.StatusTask;
import ru.qulix.olesyuknv.taskscontrol.models.Task;

/**
 * Мнимый сервер.
 *
 * @author QULIX-OLESYUKNV
 */
public class StubServer implements TaskServer {

    private static final long SERVER_DELAY_MS = 1000;

    private final Lock LOCKER = new ReentrantLock();

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
            TimeUnit.MICROSECONDS.sleep(SERVER_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("StubServer", e);
            //Log.e("StubServer", e.printStackTrace());
        }
    }

    @Override
    public List<Task> load(int start, int finish) {
       // LOCKER.lock();
        imitationServerWork();
        List<Task> list = new ArrayList<Task>(tasksSet);
        if (start >= 0 && finish <= list.size()) {
            return list.subList(start, finish);
        } else if (start >= 0 && start <= list.size() && finish >= list.size()) {
            return list.subList(start, list.size());
        }
      //  LOCKER.unlock();
        return new ArrayList<Task>();
    }

    @Override
    public void add(Task task) {
      //  LOCKER.lock();
        imitationServerWork();
        generateTaskId(task);
        tasksSet.add(task);
       // LOCKER.unlock();
    }

    @Override
    public void update(Task task) {
      //  LOCKER.lock();
        imitationServerWork();
        if (tasksSet.remove(task)) {
            tasksSet.add(task);
        }
      //  LOCKER.unlock();
    }

    @Override
    public void remove(Task task) {
      //  LOCKER.lock();
        imitationServerWork();
        tasksSet.remove(task);
       // LOCKER.unlock();
    }

    public int getTasksSet() {
        return tasksSet.size();
    }

    private void initialData() {
        tasksSet.add(setTaskId(new Task("name", 1, new DateType().getData("10.02.2011"),
                new DateType().getData("10.02.2015"), StatusTask.COMPLETED)));
        tasksSet.add(setTaskId(new Task("name", 2, new DateType().getData("1.08.2014"),
                new DateType().getData("10.02.2015"), StatusTask.IN_PROCESS)));
        tasksSet.add(setTaskId(new Task("name", 3, new DateType().getData("18.01.2010"),
                new DateType().getData("10.02.2015"), StatusTask.NOT_STARTED)));
        tasksSet.add(setTaskId(new Task("name", 4, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED)));
        tasksSet.add(setTaskId(new Task("task5", 5, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED)));
        tasksSet.add(setTaskId(new Task("task6", 4, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED)));
        tasksSet.add(setTaskId(new Task("task7", 4, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED)));
        tasksSet.add(setTaskId(new Task("task8", 4, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED)));
        tasksSet.add(setTaskId(new Task("task9", 4, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED)));
        tasksSet.add(setTaskId(new Task("task10", 4, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED)));
        tasksSet.add(setTaskId(new Task("task11", 4, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED)));
        tasksSet.add(setTaskId(new Task("task12", 4, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED)));
        tasksSet.add(setTaskId(new Task("task13", 4, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED)));
        tasksSet.add(setTaskId(new Task("task14", 4, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED)));
        tasksSet.add(setTaskId(new Task("task15", 4, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED)));
        tasksSet.add(setTaskId(new Task("task16", 4, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED)));
        tasksSet.add(setTaskId(new Task("name", 4, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED)));
        tasksSet.add(setTaskId(new Task("task18", 4, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED)));
        tasksSet.add(setTaskId(new Task("task19", 4, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED)));
        tasksSet.add(setTaskId(new Task("task20", 4, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED)));
        tasksSet.add(setTaskId(new Task("task21", 4, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED)));
        tasksSet.add(setTaskId(new Task("task22", 4, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED)));
        tasksSet.add(setTaskId(new Task("task23", 4, new DateType().getData("20.12.2009"),
                new DateType().getData("10.02.2015"), StatusTask.POSTPONED)));
    }
    private Task setTaskId(Task task){
        task.setId(++idTask);
        return task;
    }
}
