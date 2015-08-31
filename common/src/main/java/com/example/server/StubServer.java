package com.example.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.example.models.Employee;
import com.example.models.Project;
import com.example.utils.TaskValidator;
import com.example.models.StatusTask;
import com.example.models.Task;
import com.example.utils.DateFormatUtility;

/**
 * Мнимый сервер.
 *
 * @author Q-OLN
 */
public class StubServer implements TaskServer {

    /**
     * Время имитации работы сервера
     */
    private static final long SERVER_DELAY_MS = 1000;

    /**
     * Идентификатор проекта на сервере
     */
    private int idProject = 0;

    /**
     * Идентификатор задачи на сервере
     */
    private int idTask = 0;

    /**
     * Идентификатор сотрудника на сервере
     */
    private int idEmployee = 0;

    /**
     * Хранение всех проектов на сервере
     */
    private Set<Project> projectsSet = new HashSet<Project>();

    /**
     * Хранение всех задач на сервере
     */
    private Set<Task> tasksSet = new HashSet<Task>();

    /**
     * Хранение всех содрудников на сервере
     */
    private Set<Employee> employeesSet = new HashSet<Employee>();

    /**
     * Конструктор, создание стабовых данных
     */
    public StubServer() {
        initialData();
    }

    private void generateId(Project project) {
        project.setId(++idProject);
    }

    private void generateId(Task task) {
        task.setId(++idTask);
    }

    private void generateId(Employee employee) {
        employee.setId(++idEmployee);
    }

    private void imitationServerWork() {
        try {
            TimeUnit.MILLISECONDS.sleep(SERVER_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void add(Project project) throws IOException {
        imitationServerWork();
        generateId(project);
        projectsSet.add(project);

        for (Task task : project.getTasks()) {
            add(task);
        }
    }

    @Override
    public void remove(Project project) throws IOException {
        imitationServerWork();
        projectsSet.remove(project);
        tasksSet.removeAll(project.getTasks());
    }

    @Override
    public void update(Project newProject) throws IOException {
        imitationServerWork();
        Project project = getProjectByID(newProject.getId());

        if (project != null) {
            remove(project);
            add(newProject);
        }
    }

    @Override
    public List<Project> loadProjects(int start, int finish) throws IOException {
        imitationServerWork();

        if (start < 0 || start > projectsSet.size()) {
            return new ArrayList<>();
        }
        List<Project> list = new ArrayList<Project>(projectsSet);
        return list.subList(start, Math.min(finish, list.size()));
    }


    @Override
    public void add(Task task) throws IOException {
        TaskValidator.checkName(task);
        imitationServerWork();
        generateId(task);
        tasksSet.add(task);
        Project project = getProjectByID(task.getProjectId());

        if (project != null) {
            project.addTask(task);
        }
    }

    @Override
    public void remove(Task task) throws IOException {
        imitationServerWork();
        tasksSet.remove(task);

        for (Project project : projectsSet) {
            project.removeTask(task);
        }
    }

    @Override
    public void update(Task task) throws IOException {
        TaskValidator.checkName(task);
        imitationServerWork();
        remove(task);
        add(task);
    }

    @Override
    public List<Task> loadTasks(int start, int finish) throws IOException {
        imitationServerWork();

        if (start < 0 || start > tasksSet.size()) {
            return new ArrayList<>();
        }
        List<Task> list = new ArrayList<Task>(tasksSet);
        return list.subList(start, Math.min(finish, list.size()));

    }


    @Override
    public void add(Employee employee) throws IOException {
        imitationServerWork();
        generateId(employee);
        employeesSet.add(employee);
    }

    @Override
    public void remove(Employee employee) throws IOException {
        imitationServerWork();
        employeesSet.remove(employee);

        for (Task task : tasksSet) {
            task.removeEmployee(employee);
        }
    }

    @Override
    public void update(Employee employee) throws IOException {
        imitationServerWork();
        employeesSet.remove(employee);
        employeesSet.add(employee);

        for (Task task : tasksSet) {
            if (task.removeEmployee(employee)) {
                task.addEmployee(employee);
            }
        }
    }

    @Override
    public List<Employee> loadEmployees(int start, int finish) throws IOException {
        imitationServerWork();

        if (start < 0 || start > employeesSet.size()) {
            return new ArrayList<Employee>();
        }
        List<Employee> list = new ArrayList<Employee>(employeesSet);
        return list.subList(start, Math.min(finish, list.size()));
    }

    private void initialData() {
        createTask("task1", 1, "10.02.2011", "10.02.2015", StatusTask.COMPLETED,
                createEmployee("Olesyuk", "Vladimir", "Vladimirovich", "Policeman"),
                createProject("Android Game", "AG", "MyFirstProject"));

        createTask("task2", 2, "1.08.2014", "10.02.2015", StatusTask.NOT_STARTED,
                createEmployee("Ivanov", "Ivan", "Ivanovich", "JuniorDeveloper"),
                createProject("Bank Soft", "BS", "BankApplication"));

        createTask("task3", 4, "20.12.2009", "10.02.2015", StatusTask.POSTPONED,
                createEmployee("Serbunov", "Vladimir", "Vladimirovich", "Banker"),
                createProject("Angry Birds", "AB", "FunnyGame"));

        createTask("task4", 5, "20.12.2009", "10.02.2015", StatusTask.NOT_STARTED,
                createEmployee("Osheichik", "Oleg", "Albertovich", "Mechanic"),
                createProject("Tasks Control", "TC", "Control tasks client"));

        createTask("task5", 1, "10.02.2011", "10.02.2015", StatusTask.COMPLETED,
                createEmployee("VAmim", "Holod", "Evgeniyvich", "Energy"),
                createProject("Bsuir TimeTable", "BTT", "Timetable for University"));

        createTask("task6", 4, "20.12.2009", "10.02.2015", StatusTask.IN_PROCESS,
                createEmployee("Butrim", "Evgeniy", "Alexandroivich", "Energy"),
                createProject("Viber", "V", "Communicate application"));

        createTask("task7", 4, "20.12.2009", "10.02.2015", StatusTask.POSTPONED,
                createEmployee("Chernic", "Artem", "Anatolievich", "Policeman"),
                createProject("Vkontakte", "VK", "client VK.com"));

        createTask("task8", 1, "10.02.2011", "10.02.2015", StatusTask.NOT_STARTED,
                createEmployee("Kasai", "Igor", "Ivanovich", "RoadBuilder"),
                createProject("Adobe Reader", "AR", "books reader"));

        createTask("task9", 1, "10.02.2011", "10.02.2015", StatusTask.COMPLETED,
                createEmployee("Lavshyk", "Mihail", "Vladimirovich", "Builder"),
                createProject("Microsoft Office", "MO", "Word processor"));

        createTask("task10", 1, "10.02.2011", "10.02.2015", StatusTask.POSTPONED,
                createEmployee("Kechko", "Edyard", "Anatolievich", "SystemAdministrator"),
                createProject("Translator", "T", "Google Translator"));

        createTask("task11", 1, "10.02.2011", "10.02.2015", StatusTask.COMPLETED,
                createEmployee("Dmitriy", "Isachenko", "Alexandroivich", "JuniorDeveloper"),
                createProject("Total Commander", "TC", "explorer"));

        createTask("task12", 4, "20.12.2009", "10.02.2015", StatusTask.NOT_STARTED,
                createEmployee("Olesyuk", "Nikita", "Vladimirovich", "JuniorDeveloper"),
                createProject("AIMP", "A", "Player"));
    }

    private int createProject(String name, String subName, String description) {
        Project project = new Project(name, subName, description);
        project.setId(++idProject);
        projectsSet.add(project);
        return project.getId();
    }

    private Employee createEmployee(String surname, String name, String middleName, String post) {
        Employee employee = new Employee(surname, name, middleName, post);
        employee.setId(++idEmployee);
        employeesSet.add(employee);
        return employee;
    }

    private void createTask(String name, int workTime, String start, String finish, StatusTask statusTask,
                            Employee employee, int projectId) {
        Date startDate = getDate(start);
        Date finishDate = getDate(finish);
        Task task = new Task(name, workTime, startDate, finishDate, statusTask, projectId);
        task.addEmployee(employee);
        task.setId(++idTask);
        tasksSet.add(task);
        Project project = getProjectByID(task.getProjectId());
        if (project != null) {
            project.addTask(task);
        }
    }

    private Date getDate(String date) {
        return DateFormatUtility.format(date);
    }

    private Project getProjectByID(int id) {
        for (Project project : projectsSet) {
            if (project.getId() == id) {
                return project;
            }
        }
        return null;
    }
}
