package xyz.rnovoselov.enterprise.aniceandfire.mvp.model;

import javax.inject.Inject;

import xyz.rnovoselov.enterprise.aniceandfire.data.managers.DataManager;
import xyz.rnovoselov.enterprise.aniceandfire.di.components.DaggerModelComponent;
import xyz.rnovoselov.enterprise.aniceandfire.di.components.ModelComponent;
import xyz.rnovoselov.enterprise.aniceandfire.di.modules.ModelModule;

/**
 * Created by roman on 02.05.17.
 */

public abstract class AbstractModel {

    @Inject
    DataManager dataManager;

    public AbstractModel() {
        ModelComponent component = createDaggerComponent();
        component.inject(this);
    }

    private ModelComponent createDaggerComponent() {
        return DaggerModelComponent.builder()
                .modelModule(new ModelModule())
                .build();
    }
}
