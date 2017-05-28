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
import xyz.rnovoselov.enterprise.aniceandfire.data.storage.dto.HouseDataDto;
import xyz.rnovoselov.enterprise.aniceandfire.di.components.AppComponent;
import xyz.rnovoselov.enterprise.aniceandfire.di.scopes.DaggerScope;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.model.SplashModel;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.view.ISplashView;
import xyz.rnovoselov.enterprise.aniceandfire.utils.Constants;

/**
 * Created by roman on 27.04.17.
 */

@InjectViewState
public class SplashPresenter extends MvpPresenter<ISplashView> {

    private static final String TAG = Constants.TAG_PREFIX + SplashPresenter.class.getSimpleName();

    @Inject
    SplashModel model;

    @Inject
    ResourceProvider resourceProvider;

    private List<Integer> defaultHousesSelectedItems;

    public SplashPresenter() {
        Component component = createDaggerComponent();
        component.inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        if (model.isSomeHousesDownloaded()) {
            updateDownloadDefaultHouses();
        } else {
            defaultHousesSelectedItems = new ArrayList<>();
            getViewState().showDownloadHouseInfoDialog(defaultHousesSelectedItems);
        }
    }

    public void addHouseSelectedItem(Integer item) {
        defaultHousesSelectedItems.add(item);
    }

    public void removeHouseSelectedItem(Integer item) {
        defaultHousesSelectedItems.remove(item);
    }

    private void updateDownloadDefaultHouses() {
        getViewState().showProgress();
        getViewState().showProgressMessage(resourceProvider.getStringResource(R.string.splash_houses_get_info_message));
        model.updateHouseDataInRealm()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(updateDataSubscriber);
    }

    public void startDownloadDefaultHouses() {
        getViewState().showProgress();
        getViewState().showProgressMessage(resourceProvider.getStringResource(R.string.splash_houses_get_info_message));
        model.getHousesAndSaveToRealm(defaultHousesSelectedItems)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(updateDataSubscriber);
    }

    private Subscriber<HouseDataDto> updateDataSubscriber = new Subscriber<HouseDataDto>() {
        @Override
        public void onCompleted() {
            getViewState().hideProgress();
            getViewState().openMainActivity();
            detachView(getViewState());
        }

        @Override
        public void onError(Throwable t) {
            getViewState().hideProgress();
            getViewState().showError(t);
            getViewState().openMainActivity();
            Log.e(TAG, "ERROR: " + t.toString());
        }

        @Override
        public void onNext(HouseDataDto houseDataDto) {
            if (houseDataDto.getName().isEmpty()) {
                String name = model.getHouseName(houseDataDto.getId());
                if (!name.isEmpty()) {
                    getViewState().showProgressMessage(resourceProvider.getStringResource(R.string.splash_houses_get_info_message)
                            + "\n" + name);
                }
            } else {
                getViewState().showProgressMessage(resourceProvider.getStringResource(R.string.splash_houses_get_info_message)
                        + "\n" + houseDataDto.getName());
            }
        }
    };

    //region ================ DI ================

    private Component createDaggerComponent() {
        return DaggerSplashPresenter_Component.builder()
                .appComponent(IceAndFireApplication.getAppComponent())
                .module(new Module())
                .build();
    }

    @dagger.Module
    class Module {
        @Provides
        @DaggerScope(SplashPresenter.class)
        SplashModel provideSplashModel() {
            return new SplashModel();
        }

        @Provides
        @DaggerScope(SplashPresenter.class)
        ResourceProvider provideResources(Context context) {
            return new ResourceProvider(context);
        }
    }

    @dagger.Component(dependencies = AppComponent.class, modules = Module.class)
    @DaggerScope(SplashPresenter.class)
    interface Component {
        void inject(SplashPresenter presenter);
    }

    //endregion
}
