package ru.qulix.olesyuknv.taskscontrol;

import java.util.List;

import com.example.models.Employee;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Отображение Employee
 *
 * @author Q-OLN
 */
public class EmployeeAdapter extends TasksControlAdapter<Employee> {

    public EmployeeAdapter(Context context, List<Employee> employees) {
        super(context, employees);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.item_employee_adapter, parent, false);
        }

        Employee employee = (Employee) getItem(position);

        ((TextView) view.findViewById(R.id.id)).setText(employee.getId() + ".");
        ((TextView) view.findViewById(R.id.surname)).setText(employee.getSurname());
        ((TextView) view.findViewById(R.id.name)).setText(employee.getName());
        ((TextView) view.findViewById(R.id.middleName)).setText(employee.getMiddleName());
        ((TextView) view.findViewById(R.id.post)).setText(employee.getPost());
        return view;
    }
}
