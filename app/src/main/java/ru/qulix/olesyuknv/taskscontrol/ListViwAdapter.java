package ru.qulix.olesyuknv.taskscontrol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CustomAdapter display name and status task
 *
 * @author OlesyukNV
 */
public class ListViwAdapter extends BaseAdapter {

    private LayoutInflater lInflater;
    private List<Task> tasks;

    /**
     * Map of status icons
     */
    private final Map<StatusTask, Integer> STATUS_STATUSES = new HashMap<StatusTask, Integer>(){{
        put(StatusTask.NOT_STARTED, R.drawable.icon_nostartwork);
        put(StatusTask.COMPLETED, R.drawable.icon_compleatwork);
        put(StatusTask.POSTPONED, R.drawable.icon_pausework);
        put(StatusTask.IN_PROCESS, R.drawable.icon_inprocess);
    }};

    /**
     * Constructor and set status icons
     *
     * @param context
     * @param tasks   list of tasks
     */
    public ListViwAdapter(Context context, List<Task> tasks) {
        this.tasks = tasks;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            view = lInflater.inflate(R.layout.item_for_custom_adapter, parent, false);
        }

        Task task = (Task) getItem(position);
        ((TextView) view.findViewById(R.id.name)).setText(task.getName());
        Integer statusTaskImage = STATUS_STATUSES.get(task.getStatus());
        ((ImageView) view.findViewById(R.id.statusTask)).setImageResource(statusTaskImage);

        return view;
    }
}
