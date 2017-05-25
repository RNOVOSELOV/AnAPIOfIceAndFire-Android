package xyz.rnovoselov.enterprise.aniceandfire.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by roman on 25.05.17.
 */

public class AppHelpers {

    /**
     * Метод рекурсивно обходит вложенные контексты и возвращает {@link Activity}
     *
     * @param context объект {@link Context},  по которому необходимо найти {@link Activity}, в которую вложен данный context
     * @return {@link Activity}, в которую обернут данный {@link Context}
     */
    @Nullable
    public static Activity getActivityFromViewContext(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    /**
     * Метод возвращает ширину экрана (активити) в пикселях (px)
     *
     * @param activity обьект {@link Activity}, ширину которой необходимо вернуть
     * @return ширина активити
     */
    // Ширины экрана в px
    public static int getScreenWidthPx(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }

    /**
     * Метод возвращает плотность пикселей на девайсе
     *
     * @param context {@link Context} контекст
     * @return плотность пикселей на устройстве
     */
    public static float getDensity(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.density;
    }

    /**
     * Метод конвертирует значение пикселей, не зависящих от плотности экрана в фактическое колличество пикселей на устройстве (на дюйм)
     *
     * @param dp      значение количества пикселей не зависящих от плотности экрана на дюйм (dp)
     * @param context обьект типа {@link Context}
     * @return фактическое количество пикселей на дюйм (px)
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

    /**
     * Метод конвертирует фактическое колличество пикселей на устройстве в значение пикселей, не зависящих от плотности экрана
     *
     * @param px      фактическое количество пикселей на дюйм (px)
     * @param context обьект типа {@link Context}
     * @return значение количества пикселей не зависящих от плотности экрана на дюйм (dp)
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }
}
