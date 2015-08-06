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
 * View "Постраничная навигация"
 *
 * @author Q-OLN
 */
public class PageView extends LinearLayout {

    private ImageView nextPage;

    private ImageView previousPage;

    /**
     * Флаг о наличии загружаемых данных
     */
    private boolean existData = true;

    private NavigationListener listener;

    private PageNavigation pageNavigation;

    public PageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        pageNavigation = new PageNavigation();
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
        pageNavigation.nextPage();
        listener.onPage();
    }

    private void previousPageOnClick() {
        nextPage.setVisibility(View.VISIBLE);
        pageNavigation.previousPage();
        listener.onPage();

        if (pageNavigation.getStartPosition() <= 0) {
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

    public void setPageSize(int size) {
        pageNavigation.setPageSize(size);
        setExistData(true);
        previousPage.setVisibility(INVISIBLE);
        nextPage.setVisibility(VISIBLE);
    }


    public int  getStartPosition() {
        return pageNavigation.getStartPosition();
    }

    public int  getFinishPosition() {
        return pageNavigation.getFinishPosition();
    }

    public void setListener(NavigationListener listener) {
        this.listener = listener;
    }
}


