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

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater lInflater;
    private List<Task> tasks;
    Map<StatusTask, Integer> map = new HashMap<StatusTask, Integer>();

    public CustomAdapter(Context context, List<Task> tasks) {
        this.tasks = tasks;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        map.put(StatusTask.NOT_STARTED, R.drawable.icon_nostartwork);
        map.put(StatusTask.COMPLETED, R.drawable.icon_compleatwork);
        map.put(StatusTask.POSTPONED, R.drawable.icon_pausework);
        map.put(StatusTask.IN_PROCESS, R.drawable.icon_inprocess);
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

        Task task = (Task)getItem(position);
        ((TextView) view.findViewById(R.id.name)).setText(task.getName());
        Integer statusTaskImage = map.get(task.getStatus());
        ((ImageView) view.findViewById(R.id.status_task)).setImageResource(statusTaskImage);

        return view;
    }
}
