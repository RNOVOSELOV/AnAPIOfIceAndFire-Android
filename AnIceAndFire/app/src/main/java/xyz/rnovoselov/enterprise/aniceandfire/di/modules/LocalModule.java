package xyz.rnovoselov.enterprise.aniceandfire.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import xyz.rnovoselov.enterprise.aniceandfire.data.providers.PreferencesProvider;
import xyz.rnovoselov.enterprise.aniceandfire.data.providers.RealmProvider;

/**
 * Created by roman on 01.05.17.
 */

@Module
public class LocalModule {

    @Provides
    @Singleton
    PreferencesProvider providePreferences(Context context) {
        return new PreferencesProvider(context);
    }

    @Provides
    @Singleton
    RealmProvider provideReal() {
        return new RealmProvider();
    }
}
