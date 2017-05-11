package xyz.rnovoselov.enterprise.aniceandfire.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import dagger.Provides;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xyz.rnovoselov.enterprise.aniceandfire.data.managers.DataManager;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.responces.HouseResponce;
import xyz.rnovoselov.enterprise.aniceandfire.di.scopes.DaggerScope;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.model.HouseModel;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.view.IHouseView;
import xyz.rnovoselov.enterprise.aniceandfire.utils.Constants;

/**
 * Created by roman on 27.04.17.
 */

@InjectViewState
public class HousePresenter extends MvpPresenter<IHouseView> {

    private static final String TAG = Constants.TAG_PREFIX + DataManager.class.getSimpleName();

    @Inject
    HouseModel model;

    public HousePresenter() {
        Component component = createDaggerComponent();
        component.inject(this);

        getViewState().showProgress();
        model.getHousesFromNetwork()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HouseResponce>() {
                    @Override
                    public void onCompleted() {
                        getViewState().hideProgress();
                    }

                    @Override
                    public void onError(Throwable t) {
                        getViewState().showError(t);
                        getViewState().hideProgress();
                        Log.e(TAG, "ERROR: " + t.toString());
                    }

                    @Override
                    public void onNext(HouseResponce houseResponce) {

                    }
                });
    }

    //region ================ DI ================

    private Component createDaggerComponent() {
        return DaggerHousePresenter_Component.builder()
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(HousePresenter.class)
        HouseModel provideSplashModel() {
            return new HouseModel();
        }
    }

    @dagger.Component(modules = HousePresenter.Module.class)
    @DaggerScope(HousePresenter.class)
    public interface Component {
        void inject(HousePresenter presenter);
    }

    //endregion
}
