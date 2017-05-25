package xyz.rnovoselov.enterprise.aniceandfire.ui.views;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import xyz.rnovoselov.enterprise.aniceandfire.R;
import xyz.rnovoselov.enterprise.aniceandfire.utils.AppConfig;
import xyz.rnovoselov.enterprise.aniceandfire.utils.AppHelpers;

/**
 * Created by roman on 25.05.17.
 */

public class AspectRatioImageView extends AppCompatImageView {

    private final float aspectRatio;

    public AspectRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView);
        aspectRatio = typedArray.getFloat(R.styleable.AspectRatioImageView_aspect_ratio, AppConfig.DEFAULT_ASPECT_RATIO_IV);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int newWidth;
        int newHeight;

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            newWidth = getMeasuredWidth();
        } else {
            // В альбомном режиме ширина картинки это треть экрана
            Activity activity = AppHelpers.getActivityFromViewContext(getContext());
            if (activity != null) {
                newWidth = AppHelpers.getScreenWidthPx(activity) / 3;
            } else {
                newWidth = (int) (AppHelpers.getDensity(getContext()) * 50);
            }
        }

        newHeight = (int) (newWidth / aspectRatio);
        setMeasuredDimension(newWidth, newHeight);
    }
}
