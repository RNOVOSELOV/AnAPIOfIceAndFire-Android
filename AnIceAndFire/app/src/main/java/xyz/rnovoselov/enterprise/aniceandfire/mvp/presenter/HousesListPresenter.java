package xyz.rnovoselov.enterprise.aniceandfire.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.Provides;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import xyz.rnovoselov.enterprise.aniceandfire.data.storage.dto.HouseDataDto;
import xyz.rnovoselov.enterprise.aniceandfire.di.scopes.DaggerScope;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.model.HouseListModel;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.view.IHouseListView;
import xyz.rnovoselov.enterprise.aniceandfire.utils.Constants;

/**
 * Created by roman on 27.05.17.
 */

@InjectViewState
public class HousesListPresenter extends MvpPresenter<IHouseListView> {
    private static final String TAG = Constants.TAG_PREFIX + HousesListPresenter.class.getSimpleName();

    @Inject
    HouseListModel model;

    private List<HouseDataDto> housesList;

    public HousesListPresenter() {
        Component component = createDaggerComponent();
        component.inject(this);
        housesList = new ArrayList<>();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showProgress();
        model.getHouses()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HouseDataDto>() {

                    @Override
                    public void onCompleted() {
                        getViewState().hideProgress();
                        getViewState().showHousesList(housesList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().hideProgress();
                        getViewState().showError(e);
                    }

                    @Override
                    public void onNext(HouseDataDto houseDataDto) {
                        housesList.add(houseDataDto);
                    }
                });

    }

    //region ================ DI ================

    private Component createDaggerComponent() {
        return DaggerHousesListPresenter_Component.builder()
                .module(new Module())
                .build();
    }

    @dagger.Module
    class Module {
        @Provides
        @DaggerScope(HousesListPresenter.class)
        HouseListModel provideHouseListModel() {
            return new HouseListModel();
        }
    }

    @dagger.Component(modules = Module.class)
    @DaggerScope(HousesListPresenter.class)
    interface Component {
        void inject(HousesListPresenter presenter);
    }

    //endregion
}
