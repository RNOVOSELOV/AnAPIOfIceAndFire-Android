package xyz.rnovoselov.enterprise.aniceandfire.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import xyz.rnovoselov.enterprise.aniceandfire.data.managers.DataManager;
import xyz.rnovoselov.enterprise.aniceandfire.di.scopes.DaggerScope;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.model.SplashModel;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.view.ISplashView;
import xyz.rnovoselov.enterprise.aniceandfire.utils.Constants;

/**
 * Created by roman on 27.04.17.
 */

@InjectViewState
public class SplashPresenter extends MvpPresenter<ISplashView> {

    private static final String TAG = Constants.TAG_PREFIX + DataManager.class.getSimpleName();

    @Inject
    SplashModel model;

    public SplashPresenter() {
        Component component = createDaggerComponent();
        component.inject(this);
        getViewState().showProgress();
        model.getHousesFromNetwork()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(houseResponce -> {
                            Log.e(TAG, houseResponce.getName() + " " + Thread.currentThread().getName());
                        },
                        throwable -> {
                            getViewState().showError(throwable);
                            Log.e(TAG, throwable.toString());
                        }
                );
        getViewState().hideProgress();
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
