package xyz.rnovoselov.enterprise.aniceandfire.mvp.presenter;

import android.os.Handler;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import dagger.Provides;
import xyz.rnovoselov.enterprise.aniceandfire.data.managers.DataManager;
import xyz.rnovoselov.enterprise.aniceandfire.di.scopes.DaggerScope;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.model.SplashModel;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.view.ISplashView;

/**
 * Created by roman on 27.04.17.
 */

@InjectViewState
public class SplashPresenter extends MvpPresenter<ISplashView> {

    @Inject
    SplashModel model;

    public SplashPresenter() {
        Component component = createDaggerComponent();
        component.inject(this);
/*

        getViewState().showMessage("Start");
        getViewState().showProgress();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getViewState().hideProgress();
//                getViewState().showMessage(isSomeDataDownloaded() ? "TRUE" : "FALSE");
            }
        }, 3000);
*/


    }

    public boolean isSomeDataDownloaded() {
        return false; //DataManager.getInstance().isInfoAboutHousesAreDownloaded();
    }

    //region ================ DI ================

    private Component createDaggerComponent() {
        return DaggerSplashPresenter_Component.builder()
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(SplashPresenter.class)
        SplashModel provideSplashModel() {
            return new SplashModel();
        }
    }

    @dagger.Component(modules = SplashPresenter.Module.class)
    @DaggerScope(SplashPresenter.class)
    public interface Component {
        void inject(SplashPresenter presenter);
    }

    //endregion
}
