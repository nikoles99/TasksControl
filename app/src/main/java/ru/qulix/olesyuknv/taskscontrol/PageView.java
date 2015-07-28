package ru.qulix.olesyuknv.taskscontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import ru.qulix.olesyuknv.taskscontrol.utils.PageNavigation;

/**
 *
 * @author Q-OLN
 */
public class PageView extends LinearLayout {

    private ImageView nextPage;

    private ImageView previousPage;

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
        listener.sendMessage();
    }

    private void previousPageOnClick() {
        nextPage.setVisibility(View.VISIBLE);
        pageNavigation.previousPage();
        listener.sendMessage();

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

    public PageNavigation getPageNavigation() {
        return pageNavigation;
    }

    public void setDefaultParams() {
        setExistData(true);
        nextPage.setVisibility(VISIBLE);
        previousPage.setVisibility(INVISIBLE);
    }

    /**
     * Слушатель, отправляющий уведомления
     */
    public interface NavigationListener {
        void sendMessage();
    }

    public void setListener(NavigationListener listener) {
        this.listener = listener;
    }
}
