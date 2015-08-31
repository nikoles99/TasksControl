package ru.qulix.olesyuknv.taskscontrol.adapters;

import java.util.List;

import com.example.models.Project;

import ru.qulix.olesyuknv.taskscontrol.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Отображение Project
 *
 * @author Q-OLN
 */
public class ProjectAdapter extends TasksControlAdapter<Project> {

    public ProjectAdapter(Context context, List<Project> projects) {
        super(context, projects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.item_project_adapter, parent, false);
        }

        Project projects = (Project) getItem(position);

        ((TextView) view.findViewById(R.id.id)).setText(projects.getId() + ".");
        ((TextView) view.findViewById(R.id.description)).setText(projects.getDescription());
        ((TextView) view.findViewById(R.id.projectName)).setText(projects.getName());
        ((TextView) view.findViewById(R.id.projectSubName)).setText("(" + projects.getSubName() + ")");
        return view;
    }
}
