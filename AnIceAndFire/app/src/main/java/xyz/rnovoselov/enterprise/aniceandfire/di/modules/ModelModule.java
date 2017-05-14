package xyz.rnovoselov.enterprise.aniceandfire.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import xyz.rnovoselov.enterprise.aniceandfire.data.providers.DataProvider;

/**
 * Created by roman on 02.05.17.
 */

@Module
public class ModelModule {
    @Provides
    @Singleton
    DataProvider provideDataManager() {
        return new DataProvider();
    }
}
