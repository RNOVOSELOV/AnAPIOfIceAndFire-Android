package xyz.rnovoselov.enterprise.aniceandfire.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import xyz.rnovoselov.enterprise.aniceandfire.data.managers.DataManager;

/**
 * Created by roman on 02.05.17.
 */

@Module
public class ModelModule {
    @Provides
    @Singleton
    DataManager provideDataManager() {
        return new DataManager();
    }
}
