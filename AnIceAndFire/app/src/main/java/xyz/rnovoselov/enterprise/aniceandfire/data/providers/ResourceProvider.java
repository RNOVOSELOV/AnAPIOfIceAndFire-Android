package xyz.rnovoselov.enterprise.aniceandfire.data.providers;

import android.content.Context;

import xyz.rnovoselov.enterprise.aniceandfire.utils.Constants;

/**
 * Created by roman on 21.05.17.
 */

public class ResourceProvider {
    private static final String TAG = Constants.TAG_PREFIX + ResourceProvider.class.getSimpleName();

    private Context context;

    public ResourceProvider(Context context) {
        this.context = context;
    }

    public String getStringResource(int resourceId) {
        return context.getString(resourceId);
    }
}
