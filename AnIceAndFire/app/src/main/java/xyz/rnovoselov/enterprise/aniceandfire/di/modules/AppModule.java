package xyz.rnovoselov.enterprise.aniceandfire.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roman on 01.05.17.
 */

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    Context providesContext() {
        return context;
    }
}
