package ru.qulix.olesyuknv.taskscontrol.server;

import java.util.List;

import ru.qulix.olesyuknv.taskscontrol.models.Task;

/**
 * ������ ����������� �������, ���������, ������� � �������� ������.
 *
 * @author QULIX-OLESYUKNV
 */
public interface TaskServer {

    /**
     * @param task ������ ��� ���������
     */
    void update(Task task);

    /**
     * @return tasks ������ �����
     */
    List<Task> load();

    /**
     * @param task ������ ��� ����������
     */
    void add(Task task);

    /**
     * @param task ������ ��� ��������
     */
    void remove(Task task);
}
