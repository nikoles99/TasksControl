package ru.qulix.olesyuknv.taskscontrol.activities;

import java.text.DateFormat;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;

import android.content.Intent;

import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import ru.qulix.olesyuknv.taskscontrol.R;
import ru.qulix.olesyuknv.taskscontrol.models.StatusTask;
import ru.qulix.olesyuknv.taskscontrol.models.Task;
import ru.qulix.olesyuknv.taskscontrol.TasksControlApplication;
import ru.qulix.olesyuknv.taskscontrol.threads.AddTask;
import ru.qulix.olesyuknv.taskscontrol.threads.RemoveTask;
import ru.qulix.olesyuknv.taskscontrol.server.TaskServer;
import ru.qulix.olesyuknv.taskscontrol.threads.UpdateTask;


/**
 * Форма создания и изменения задачи.
 *
 * @author QULIX-OLESYUKNV
 */
public class InputTaskActivity extends Activity {

    private EditText nameTask;
    private EditText workTime;
    private Button startDate;
    private Button finishDate;
    private Spinner statusWork;
    private int idTask;

    public static final String TASK_POSITION = "Position";
    public static final String ACTION = "Action";
    public static final int CHANGE_TASK_FLAG = 3;
    public static final int ADD_TASK_FLAG = 2;
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_task);

        nameTask = (EditText) findViewById(R.id.nameTask);
        workTime = (EditText) findViewById(R.id.workHours);
        startDate = (Button) findViewById(R.id.startDate);
        finishDate = (Button) findViewById(R.id.finishDate);

        statusWork = (Spinner) findViewById(R.id.status);
        setSpinnerParameters(statusWork);

        ImageView cancelButton = (ImageView) findViewById(R.id.cancelButton);
        cancelButtonOnClick(cancelButton);

        ImageView saveButton = (ImageView) findViewById(R.id.saveButton);
        saveButtonOnClick(saveButton);

        setDateOnClick(startDate);
        setDateOnClick(finishDate);
        fillTheFields();

    }

    /**
     * создание задачи, изменение выбранной задачи
     *
     * @param saveButton
     */
    private void saveButtonOnClick(ImageView saveButton) {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isFieldsEmpty()) {
                    Task task = getTask(idTask);
                    Intent intent = getIntent();
                    TaskServer server = ((TasksControlApplication) getApplicationContext()).getServer();

                    switch (intent.getIntExtra(ACTION, REQUEST_CODE)) {
                        case ADD_TASK_FLAG:
                            new AddTask(server).execute(task);
                            break;
                        case CHANGE_TASK_FLAG:
                            Log.e("Error", String.valueOf(task.hashCode()));
                            new UpdateTask(server).execute(task);
                            break;
                    }

                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(InputTaskActivity.this, "You must input correct all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * закрытие формы, удаление выбранной задачи
     *
     * @param cancelButton
     */
    private void cancelButtonOnClick(ImageView cancelButton) {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                switch (intent.getIntExtra(ACTION, REQUEST_CODE)) {
                    case CHANGE_TASK_FLAG:
                        new RemoveTask(((TasksControlApplication) getApplicationContext()).getServer()).execute(getTask(idTask));
                        setResult(RESULT_OK, intent);
                        break;
                }
                finish();
            }
        });
    }

    /**
     * Отображение свойств выбранной задачи
     */
    private void fillTheFields() {
        switch (getIntent().getIntExtra(ACTION, REQUEST_CODE)) {
            case CHANGE_TASK_FLAG:
                Task task = (Task) getIntent().getSerializableExtra(TASK_POSITION);
                nameTask.setText(task.getName());
                workTime.setText(String.valueOf(task.getWorkTime()));
                startDate.setText(getStringFromData(task.getStartDate()));
                finishDate.setText(getStringFromData(task.getFinishDate()));
                idTask = task.getId();
                break;
        }
    }

    /**
     *
     * @return Заполена форма ввода задачи или нет
     */
    private boolean isFieldsEmpty() {
        return !(TextUtils.isEmpty(workTime.getText()) ||
                TextUtils.isEmpty(nameTask.getText()) ||
                TextUtils.isEmpty(startDate.getText()) ||
                TextUtils.isEmpty(finishDate.getText()) ||
                getDataFromString(startDate.getText().toString()).after(getDataFromString(finishDate.getText().toString())));
    }

    /**
     * Получение задачи по ID
     * @param idTask
     * @return созданную задачу
     */
    private Task getTask(int idTask) {
        StatusTask status = (StatusTask) statusWork.getSelectedItem();
        Date dateStartWork = getDataFromString(startDate.getText().toString());
        Date dateFinishWork = getDataFromString(finishDate.getText().toString());
        Task task = new Task(nameTask.getText().toString(), Integer.parseInt(workTime.getText().
                toString()), dateStartWork, dateFinishWork, status);
        task.setId(idTask);
        return task;
    }

    /**
     * Диалог ввода времени
     * @param date время начала, время завершения
     */
    private void setDateOnClick(final Button date) {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatePickerDialog.OnDateSetListener timeDialog = setDataPickerDialog(date, calendar);
                new DatePickerDialog(InputTaskActivity.this, timeDialog, day, month, year).show();
            }
        });
    }

    /**
     * Ввод выбранного времени
     * @param date
     * @param calendar
     * @return
     */
    private DatePickerDialog.OnDateSetListener setDataPickerDialog(final Button date, final Calendar calendar) {
        DatePickerDialog.OnDateSetListener timeDialog = (new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                calendar.set(selectedYear, selectedMonth, selectedDay);
                Date today = calendar.getTime();
                date.setText(getStringFromData(today));
            }
        });
        return timeDialog;
    }

    /**
     * @param statusWork Статус задачи
     * @return заполненный выпадающий список статусов
     */
    public Spinner setSpinnerParameters(Spinner statusWork) {
        ArrayAdapter<StatusTask> adapter = new ArrayAdapter<StatusTask>(this,
                android.R.layout.simple_spinner_item, StatusTask.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusWork.setAdapter(adapter);
        return statusWork;
    }

    public Date getDataFromString(String date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException();
        }
    }


    public String getStringFromData(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }


}
