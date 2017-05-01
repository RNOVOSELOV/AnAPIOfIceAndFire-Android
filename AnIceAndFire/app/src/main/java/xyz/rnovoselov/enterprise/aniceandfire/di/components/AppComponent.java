package xyz.rnovoselov.enterprise.aniceandfire.di.components;

import android.content.Context;

import dagger.Component;
import xyz.rnovoselov.enterprise.aniceandfire.di.modules.AppModule;

/**
 * Created by roman on 01.05.17.
 */

@Component(modules = AppModule.class)
public interface AppComponent {
    Context getContext();
}
