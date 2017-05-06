package xyz.rnovoselov.enterprise.aniceandfire.data.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import xyz.rnovoselov.enterprise.aniceandfire.utils.Constants;

import static xyz.rnovoselov.enterprise.aniceandfire.utils.AppConfig.DEFAULT_LAST_UPDATE_DATE;
import static xyz.rnovoselov.enterprise.aniceandfire.utils.Constants.HOUSES_LAST_UPDATED_TIMESTAMP;


/**
 * Created by roman on 27.04.17.
 */

public class PreferencesManager {

    private SharedPreferences preferences;

    public PreferencesManager(Context context) {
        preferences = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    /**
     * Сохранение в {@link SharedPreferences} времени последних изменений на сервере касательно списка домов
     *
     * @param lastModified - время, полученное с сервера
     */
    public void saveLastModifiedTime(String lastModified) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(HOUSES_LAST_UPDATED_TIMESTAMP, lastModified);
        editor.apply();
    }

    /**
     * Получение из {@link SharedPreferences} времени последних изменений на сервере касательно списка домов
     *
     * @return время последнего обновления данных
     */
    @NonNull
    public String getLastProductUpdate() {
        return preferences.getString(HOUSES_LAST_UPDATED_TIMESTAMP, DEFAULT_LAST_UPDATE_DATE);
    }
}
