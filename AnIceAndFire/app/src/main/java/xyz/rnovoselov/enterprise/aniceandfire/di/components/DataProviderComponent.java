package xyz.rnovoselov.enterprise.aniceandfire.di.components;

import javax.inject.Singleton;

import dagger.Component;
import xyz.rnovoselov.enterprise.aniceandfire.data.providers.DataProvider;
import xyz.rnovoselov.enterprise.aniceandfire.di.modules.LocalModule;
import xyz.rnovoselov.enterprise.aniceandfire.di.modules.NetworkModule;

/**
 * Created by roman on 01.05.17.
 */

@Component(dependencies = AppComponent.class, modules = {NetworkModule.class, LocalModule.class})
@Singleton
public interface DataProviderComponent {
    void inject(DataProvider dataProvider);
}
