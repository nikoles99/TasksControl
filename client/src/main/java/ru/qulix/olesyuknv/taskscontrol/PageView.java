package ru.qulix.olesyuknv.taskscontrol;

import ru.qulix.olesyuknv.taskscontrol.utils.NavigationListener;
import ru.qulix.olesyuknv.taskscontrol.utils.PageNavigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * View "Постраничная навигация"
 *
 * @author Q-OLN
 */
public class PageView extends LinearLayout {

    private ImageView nextPage;

    private ImageView previousPage;

    private NavigationListener listener;

    private PageNavigation pageNavigation;

    private Context context;

    public PageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        pageNavigation = new PageNavigation();
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.page_navigation, this, true);
        applyAppParams();
        setUpViews();
        disable();
    }

    private void setUpViews() {
        nextPage = (ImageView) findViewById(R.id.nextPage);
        nextPage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPageOnClick();
            }
        });

        previousPage = (ImageView) findViewById(R.id.previousPage);
        previousPage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                previousPageOnClick();
            }
        });
    }

    private void nextPageOnClick() {
        previousPage.setVisibility(View.VISIBLE);
        nextPage.setEnabled(false);
        previousPage.setEnabled(false);
        pageNavigation.nextPage();
        listener.onPage();
    }

    private void previousPageOnClick() {
        nextPage.setVisibility(View.VISIBLE);
        nextPage.setEnabled(false);
        previousPage.setEnabled(false);
        pageNavigation.previousPage();
        listener.onPage();
    }

    private void updateStateImageView(boolean exist) {
        nextPage.setEnabled(true);
        nextPage.setVisibility(exist ? VISIBLE : INVISIBLE);

        previousPage.setEnabled(true);
        previousPage.setVisibility(pageNavigation.getStartPosition() <= 0 ? INVISIBLE : VISIBLE);
    }

    /**
     * Отключить постраничную навигацию
     */
    public void disable() {
        nextPage.setVisibility(INVISIBLE);
        previousPage.setVisibility(INVISIBLE);
    }

    /**
     * Установка флага наличия данных
     *
     * @param exist флаг наличия данных
     */
    public void setExistData(boolean exist) {
        updateStateImageView(exist);
    }

    /**
     * Установка размера станицы со стартовыми настройками
     *
     * @param size размера станицы
     */
    public void setPageSize(int size) {
        pageNavigation.setPageSize(size);
    }

    /**
     * Получение начальной позиции страницы
     *
     * @return начальную позицию
     */
    public int getStartPosition() {
        return pageNavigation.getStartPosition();
    }

    /**
     * Получение конечной позиции страницы
     *
     * @return конечную позицию
     */
    public int getFinishPosition() {
        return pageNavigation.getFinishPosition();
    }

    /**
     * Применить настройки
     */
    public void applyAppParams() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        setPageSize(Integer.valueOf(sharedPreferences.getString(context.getString(R.string.page_size), "9").trim()));
    }

    /**
     * Установка слушателя, отправляющего уведомления
     *
     * @param listener слушатель
     */
    public void setListener(NavigationListener listener) {
        this.listener = listener;
    }
}