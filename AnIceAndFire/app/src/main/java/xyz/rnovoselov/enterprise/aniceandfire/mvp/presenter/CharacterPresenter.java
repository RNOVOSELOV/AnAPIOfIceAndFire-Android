package xyz.rnovoselov.enterprise.aniceandfire.mvp.presenter;

import com.arellomobile.mvp.MvpPresenter;

import java.security.PublicKey;

import javax.inject.Inject;

import dagger.Provides;
import xyz.rnovoselov.enterprise.aniceandfire.di.scopes.DaggerScope;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.model.CharacterModel;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.view.ICharacterView;
import xyz.rnovoselov.enterprise.aniceandfire.utils.Constants;

/**
 * Created by roman on 14.05.17.
 */

public class CharacterPresenter extends MvpPresenter<ICharacterView> {

    private static final String TAG = Constants.TAG_PREFIX + HousePresenter.class.getSimpleName();

    @Inject
    CharacterModel model;

    public CharacterPresenter () {
        Component component = createDaggerComponent();
        component.inject(this);
    }

    //region ================ DI ================

    private Component createDaggerComponent() {
        return DaggerCharacterPresenter_Component.builder()
                .module(new Module())
                .build();
    }

    @dagger.Module
    class Module {
        @Provides
        @DaggerScope(CharacterPresenter.class)
        CharacterModel providesCharacterModel() {
            return new CharacterModel();
        }
    }

    @dagger.Component(modules = Module.class)
    @DaggerScope(CharacterPresenter.class)
    interface Component {
        void inject(CharacterPresenter presenter);
    }

    //endregion
}
