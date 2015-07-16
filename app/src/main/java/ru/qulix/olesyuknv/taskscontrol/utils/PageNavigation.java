package ru.qulix.olesyuknv.taskscontrol.utils;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import ru.qulix.olesyuknv.taskscontrol.R;
import ru.qulix.olesyuknv.taskscontrol.activities.MainActivity;

/**
 * Постраничная навигация
 *
 * @author QULIX-OLESYUKNV
 */
public class PageNavigation extends LinearLayout {

    /**
     * количество записей на одной странице
     */
    private static final int INCREMENT = 10;

    private ImageView nextPage;

    private ImageView previousPage;

    private MainActivity activity;

    /**
     * позиция первого элемента списка
     */
    private int startPosition = 0;

    /**
     * позиция последнего элемента списка
     */
    private int finishPosition = INCREMENT;

    public PageNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((Activity) getContext()).getLayoutInflater().inflate(R.layout.page_navigation, this, true);
        setUpViews();
        this.activity = (MainActivity) context;
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
                nextPage();
                activity.loadDataFromServer();
            }
        });
    }

    private void setPreviousPageButtonListener(final ImageView previousPage) {
        previousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextPage.setVisibility(View.VISIBLE);
                previousPage();
                activity.loadDataFromServer();

                if (isDataExist()) {
                    previousPage.setVisibility(View.INVISIBLE);
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

    private boolean isDataExist() {
        return startPosition <= 0;
    }

    public int getFinishPosition() {
        return finishPosition;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public ImageView getNextPage() {
        return nextPage;
    }


}
