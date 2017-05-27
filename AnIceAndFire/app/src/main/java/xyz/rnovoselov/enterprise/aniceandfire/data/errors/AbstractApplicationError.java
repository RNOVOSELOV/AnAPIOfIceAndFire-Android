package xyz.rnovoselov.enterprise.aniceandfire.data.errors;

/**
 * Created by roman on 27.05.17.
 */

/**
 * Класс необходим для определения типа возникшей ошибки.
 * В продакшене ошибки приложения отображаются в {@link android.support.design.widget.Snackbar},
 * при возникновении других ошибок посылается отчет в коллектор ошибок приложения
 */
public abstract class AbstractApplicationError extends Throwable {
    AbstractApplicationError(String s) {
        super(s);
    }
}
