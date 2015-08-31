package ru.qulix.olesyuknv.taskscontrol.adapters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.models.StatusTask;
import com.example.models.Task;
import com.example.utils.DateFormatUtility;

import ru.qulix.olesyuknv.taskscontrol.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Отображение Task
 *
 * @author Q-OLN
 */
public class TaskAdapter extends TasksControlAdapter<Task> {

    /**
     * Статусы задач.
     */
    private static final Map<StatusTask, Integer> STATUSES_TASKS = new HashMap<StatusTask, Integer>() {
        {
            put(StatusTask.NOT_STARTED, R.drawable.icon_no_start_work);
            put(StatusTask.COMPLETED, R.drawable.icon_compleatwork);
            put(StatusTask.POSTPONED, R.drawable.icon_pausework);
            put(StatusTask.IN_PROCESS, R.drawable.icon_inprocess);
        }
    };

    public TaskAdapter(Context context, List<Task> tasks) {
        super(context, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.item_task_adapter, parent, false);
        }

        Task task = (Task) getItem(position);
        ((TextView) view.findViewById(R.id.id)).setText(task.getId() + ".");
        ((TextView) view.findViewById(R.id.nameTask)).setText(task.getName());
        ((TextView) view.findViewById(R.id.startDate)).setText(DateFormatUtility.format(task.getStartDate()));
        ((TextView) view.findViewById(R.id.finishDate)).setText(DateFormatUtility.format(task.getFinishDate()));
        Integer statusTaskImage = STATUSES_TASKS.get(task.getStatus());
        ((ImageView) view.findViewById(R.id.statusTask)).setImageResource(statusTaskImage);
        return view;
    }
}