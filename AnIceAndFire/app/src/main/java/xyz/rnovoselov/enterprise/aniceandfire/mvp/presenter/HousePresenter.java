package xyz.rnovoselov.enterprise.aniceandfire.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import dagger.Provides;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import xyz.rnovoselov.enterprise.aniceandfire.data.storage.realm.HouseRealm;
import xyz.rnovoselov.enterprise.aniceandfire.di.scopes.DaggerScope;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.model.HouseModel;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.view.IHouseView;
import xyz.rnovoselov.enterprise.aniceandfire.utils.Constants;

/**
 * Created by roman on 27.04.17.
 */

@InjectViewState
public class HousePresenter extends MvpPresenter<IHouseView> {

    private static final String TAG = Constants.TAG_PREFIX + HousePresenter.class.getSimpleName();

    @Inject
    HouseModel model;

    public HousePresenter() {
        Component component = createDaggerComponent();
        component.inject(this);

    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        if (model.isSomeHousesDownloaded()) {
            getViewState().showProgress();
            model.updateHouseDataInRealm()
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<HouseRealm>() {
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
                        public void onNext(HouseRealm houseRealm) {
                            getViewState().showMessage(houseRealm.getName());
                        }
                    });
        } else {
//            getViewState().showDownloadHouseInfoDialog(new HashMap<>());
        }
    }

    //region ================ DI ================

    private Component createDaggerComponent() {
        return DaggerHousePresenter_Component.builder()
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
    }

    @dagger.Component(modules = Module.class)
    @DaggerScope(HousePresenter.class)
    interface Component {
        void inject(HousePresenter presenter);
    }

    //endregion
}
