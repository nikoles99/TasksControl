package ru.qulix.olesyuknv.taskscontrol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.models.StatusTask;
import com.example.models.Task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Отображение Task
 *
 * @author Q-OLN
 */
public class TaskAdapter extends BaseAdapter {

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

    private LayoutInflater layoutInflater;
    private List<Task> tasks;

    public TaskAdapter(Context context, List<Task> tasks) {
        this.tasks = tasks;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateTasksList(List<Task> list) {
        tasks.clear();
        tasks.addAll(list);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_for_custom_adapter, parent, false);
        }

        Task task = (Task) getItem(position);
        ((TextView) view.findViewById(R.id.name)).setText(task.getName());
        Integer statusTaskImage = STATUSES_TASKS.get(task.getStatus());
        ((ImageView) view.findViewById(R.id.statusTask)).setImageResource(statusTaskImage);

        return view;
    }
}