package ru.qulix.olesyuknv.taskscontrol.utils;

import android.content.Context;

/**
 * Адрес сетевого ресурса
 *
 * @author Q-ONL
 */
public interface UrlSetting {

    /**
     * получение URL
     *
     * @param context
     * @return
     */
    String getUrl(Context context);
}
