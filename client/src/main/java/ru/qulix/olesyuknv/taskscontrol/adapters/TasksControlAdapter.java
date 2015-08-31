package ru.qulix.olesyuknv.taskscontrol.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

/**
 * Отображение сущностей приложения
 *
 * @author Q-OLN
 */
public abstract class TasksControlAdapter<T>  extends BaseAdapter {

    private List<T> list;
    private LayoutInflater layoutInflater;

    public TasksControlAdapter(Context context, List<T> list) {
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Обновить список
     *
     * @param list новый список обьектов
     */
    public void update(List<T> list) {
        this.list.clear();
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }
}
