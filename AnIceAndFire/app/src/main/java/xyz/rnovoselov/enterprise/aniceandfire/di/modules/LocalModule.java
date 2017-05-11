package xyz.rnovoselov.enterprise.aniceandfire.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import xyz.rnovoselov.enterprise.aniceandfire.data.managers.PreferencesManager;
import xyz.rnovoselov.enterprise.aniceandfire.data.managers.RealmManager;

/**
 * Created by roman on 01.05.17.
 */

@Module
public class LocalModule {

    @Provides
    @Singleton
    PreferencesManager providePreferencesManager(Context context) {
        return new PreferencesManager(context);
    }

    @Provides
    @Singleton
    RealmManager provideRealManager() {
        return new RealmManager();
    }
}
