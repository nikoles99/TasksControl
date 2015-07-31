package ru.qulix.olesyuknv.taskscontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import ru.qulix.olesyuknv.taskscontrol.utils.NavigationListener;
import ru.qulix.olesyuknv.taskscontrol.utils.PageNavigation;

/**
 * @author Q-OLN
 */
public class PageView extends LinearLayout implements PageNavigation {

    private ImageView nextPage;

    private ImageView previousPage;

    private boolean existData = true;

    private NavigationListener listener;

    /**
     * позиция первого элемента списка
     */
    private int startPosition;

    /**
     * позиция последнего элемента списка
     */
    private int finishPosition;

    /**
     * количество записей на одной странице
     */
    private int pageSize;

    public PageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.page_navigation, this, true);
        setUpViews();
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

        if (!existData) {
            setExistData(true);
            return;
        }
        nextPage();
        listener.onPage();
    }

    private void previousPageOnClick() {
        nextPage.setVisibility(View.VISIBLE);
        previousPage();
        listener.onPage();

        if (getStartPosition() <= 0) {
            previousPage.setVisibility(View.INVISIBLE);
            setExistData(true);
        }
    }

    public void setExistData(boolean exist) {

        if (!exist) {
            nextPage.setVisibility(INVISIBLE);
        }
        this.existData = exist;
    }

    @Override
    public void setPageSize(int size) {
        pageSize = size;
        setExistData(true);
        nextPage.setVisibility(VISIBLE);
        previousPage.setVisibility(INVISIBLE);
        startPosition = 0;
        finishPosition = pageSize;
    }

    @Override
    public void nextPage() {
        startPosition += pageSize;
        finishPosition += pageSize;
    }

    @Override
    public void previousPage() {
        startPosition -= pageSize;
        finishPosition -= pageSize;
    }

    @Override
    public int getFinishPosition() {
        return finishPosition;
    }

    @Override
    public int getStartPosition() {
        return startPosition;
    }


    public void setListener(NavigationListener listener) {
        this.listener = listener;
    }
}


