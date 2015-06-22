package ru.qulix.olesyuknv.taskscontrol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater lInflater;
    private List<Task> tasks;

    public CustomAdapter(Context context, List<Task> tasks) {
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

        Task task = (Task)getItem(position);
        ((TextView) view.findViewById(R.id.name)).setText(task.getName());

       /* switch (task.getStatus()){
            case "not started":
                ((ImageView) view.findViewById(R.id.status_task)).setImageResource(R.drawable.icon_nostartwork);
                break;
            case "in process":
                ((ImageView) view.findViewById(R.id.status_task)).setImageResource(R.drawable.icon_inprocess);
                break;
            case "completed":
                ((ImageView) view.findViewById(R.id.status_task)).setImageResource(R.drawable.icon_compleatwork);
                break;
            case "postponed":
                ((ImageView) view.findViewById(R.id.status_task)).setImageResource(R.drawable.icon_pausework);
                break;
        }*/

        return view;
    }
}
