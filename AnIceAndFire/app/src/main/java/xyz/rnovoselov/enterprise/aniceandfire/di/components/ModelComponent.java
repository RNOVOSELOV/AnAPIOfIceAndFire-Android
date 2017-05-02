package xyz.rnovoselov.enterprise.aniceandfire.di.components;

import javax.inject.Singleton;

import dagger.Component;
import xyz.rnovoselov.enterprise.aniceandfire.di.modules.ModelModule;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.model.AbstractModel;

/**
 * Created by roman on 02.05.17.
 */

@Component(modules = ModelModule.class)
@Singleton
public interface ModelComponent {
    void inject(AbstractModel abstractModel);
}
