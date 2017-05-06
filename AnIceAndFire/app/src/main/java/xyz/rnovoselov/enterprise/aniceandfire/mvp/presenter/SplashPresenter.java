package xyz.rnovoselov.enterprise.aniceandfire.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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

        model.getHousesFromNetwork()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(houseResponce -> {
                            Log.e("TAG", houseResponce.getName() + " " + Thread.currentThread().getName());
                        },
                        throwable -> getViewState().showError(throwable)
                );
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
