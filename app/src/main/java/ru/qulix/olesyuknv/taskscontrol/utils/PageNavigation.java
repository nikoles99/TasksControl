package ru.qulix.olesyuknv.taskscontrol.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import ru.qulix.olesyuknv.taskscontrol.R;

/**
 * Постраничная навигация
 *
 * @author QULIX-OLESYUKNV
 */
public class PageNavigation extends LinearLayout {

    /**
     * количество записей на одной странице
     */
    private static final int INCREMENT = 9;

    private ImageView nextPage;

    private ImageView previousPage;

    /**
     * позиция первого элемента списка
     */
    private int startPosition = 0;

    /**
     * позиция последнего элемента списка
     */
    private int finishPosition = INCREMENT;
    private boolean existData = true;
    private PageNavigationListener listener;

    public PageNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.page_navigation, this, true);
        setUpViews();
    }

    private void setUpViews() {
        nextPage = (ImageView) findViewById(R.id.nextPage);
        setNextPageButtonListener(nextPage);

        previousPage = (ImageView) findViewById(R.id.previousPage);
        setPreviousPageButtonListener(previousPage);
    }


    private void setNextPageButtonListener(final ImageView nextPage) {
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousPage.setVisibility(View.VISIBLE);

                if (!existData) {
                    setExistData(true);
                    return;
                }
                nextPage();
                listener.sendMessage();
            }
        });
    }

    private void setPreviousPageButtonListener(final ImageView previousPage) {
        previousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextPage.setVisibility(View.VISIBLE);
                previousPage();
                listener.sendMessage();

                if (startPosition <= 0) {
                    previousPage.setVisibility(View.INVISIBLE);
                    setExistData(true);
                }
            }
        });
    }

    private void nextPage() {
        startPosition += INCREMENT;
        finishPosition += INCREMENT;
    }

    private void previousPage() {
        startPosition -= INCREMENT;
        finishPosition -= INCREMENT;
    }

    public void setExistData(boolean exist) {

        if (!exist) {
            nextPage.setVisibility(INVISIBLE);
        }
        this.existData = exist;
    }

    public int getFinishPosition() {
        return finishPosition;
    }

    public int getStartPosition() {
        return startPosition;
    }

    /**
     * Слушатель, отправляющий уведомления
     */
    public interface PageNavigationListener {
        void sendMessage();
    }

    public void setListener(PageNavigationListener listener) {
        this.listener = listener;
    }
}
