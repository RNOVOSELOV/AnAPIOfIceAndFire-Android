package xyz.rnovoselov.enterprise.aniceandfire.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.Provides;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import xyz.rnovoselov.enterprise.aniceandfire.IceAndFireApplication;
import xyz.rnovoselov.enterprise.aniceandfire.R;
import xyz.rnovoselov.enterprise.aniceandfire.data.providers.ResourceProvider;
import xyz.rnovoselov.enterprise.aniceandfire.data.storage.realm.HouseRealm;
import xyz.rnovoselov.enterprise.aniceandfire.di.components.AppComponent;
import xyz.rnovoselov.enterprise.aniceandfire.di.scopes.DaggerScope;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.model.HouseModel;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.view.ISplashView;
import xyz.rnovoselov.enterprise.aniceandfire.utils.AppConfig;
import xyz.rnovoselov.enterprise.aniceandfire.utils.Constants;

/**
 * Created by roman on 27.04.17.
 */

@InjectViewState
public class HousePresenter extends MvpPresenter<ISplashView> {

    private static final String TAG = Constants.TAG_PREFIX + HousePresenter.class.getSimpleName();

    @Inject
    HouseModel model;

    @Inject
    ResourceProvider resourceProvider;

    private List<Integer> defaultHousesSelectedItems = new ArrayList<>(7);

    public HousePresenter() {
        Component component = createDaggerComponent();
        component.inject(this);

    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        if (model.isSomeHousesDownloaded()) {
            getViewState().showProgress();
            getViewState().showProgressMessage(resourceProvider.getStringResource(R.string.houses_update_info_message));
            model.updateHouseDataInRealm()
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<HouseRealm>() {
                        @Override
                        public void onCompleted() {
                            getViewState().hideProgress();
                            getViewState().openMainActivity();
                        }

                        @Override
                        public void onError(Throwable t) {
                            getViewState().showError(t);
                            getViewState().hideProgress();
                            getViewState().openMainActivity();
                            Log.e(TAG, "ERROR: " + t.toString());
                        }

                        @Override
                        public void onNext(HouseRealm houseRealm) {

                        }
                    });
        } else {
            getViewState().showDownloadHouseInfoDialog(defaultHousesSelectedItems);
        }
    }

    public void addHouseSelectedItem(Integer item) {
        defaultHousesSelectedItems.add(item);
    }

    public void removeHouseSelectedItem(Integer item) {
        defaultHousesSelectedItems.remove(item);
    }

    public void startDownloadDefaultHouses() {
        for (int i = 0; i < defaultHousesSelectedItems.size(); i++) {
            defaultHousesSelectedItems.set(i, AppConfig.DEFAULT_HOUSES_ID[defaultHousesSelectedItems.get(i)]);
        }

        getViewState().showProgress();
        getViewState().showProgressMessage(resourceProvider.getStringResource(R.string.houses_get_info_message));
        model.getHousesAndSaveToRealm(defaultHousesSelectedItems)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HouseRealm>() {
                    @Override
                    public void onCompleted() {
                        getViewState().hideProgress();
                        getViewState().openMainActivity();
                    }

                    @Override
                    public void onError(Throwable t) {
                        getViewState().showError(t);
                        getViewState().hideProgress();
                        getViewState().openMainActivity();
                        Log.e(TAG, "ERROR: " + t.toString());
                    }

                    @Override
                    public void onNext(HouseRealm houseRealm) {

                    }
                });
    }

    //region ================ DI ================

    private Component createDaggerComponent() {
        return DaggerHousePresenter_Component.builder()
                .appComponent(IceAndFireApplication.getAppComponent())
                .module(new Module())
                .build();
    }

    @dagger.Module
    class Module {
        @Provides
        @DaggerScope(HousePresenter.class)
        HouseModel provideHouseModel() {
            return new HouseModel();
        }

        @Provides
        @DaggerScope(HousePresenter.class)
        ResourceProvider provideResources(Context context) {
            return new ResourceProvider(context);
        }
    }

    @dagger.Component(dependencies = AppComponent.class, modules = Module.class)
    @DaggerScope(HousePresenter.class)
    interface Component {
        void inject(HousePresenter presenter);
    }

    //endregion
}
