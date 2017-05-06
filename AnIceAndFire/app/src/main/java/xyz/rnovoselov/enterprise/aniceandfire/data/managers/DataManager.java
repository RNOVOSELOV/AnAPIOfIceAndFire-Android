package xyz.rnovoselov.enterprise.aniceandfire.data.managers;

import android.util.Log;

import javax.inject.Inject;

import io.reactivex.Observable;
import xyz.rnovoselov.enterprise.aniceandfire.IceAndFireApplication;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.RestCallTransformer;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.RestService;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.responces.HouseResponce;
import xyz.rnovoselov.enterprise.aniceandfire.di.components.DaggerDataManagerComponent;
import xyz.rnovoselov.enterprise.aniceandfire.di.components.DataManagerComponent;
import xyz.rnovoselov.enterprise.aniceandfire.di.modules.LocalModule;
import xyz.rnovoselov.enterprise.aniceandfire.di.modules.NetworkModule;
import xyz.rnovoselov.enterprise.aniceandfire.utils.AppConfig;
import xyz.rnovoselov.enterprise.aniceandfire.utils.Constants;

/**
 * Created by roman on 27.04.17.
 */

public class DataManager {

    private static final String TAG = Constants.TAG_PREFIX + DataManager.class.getSimpleName();

    @Inject
    PreferencesManager preferencesManager;
    @Inject
    RestService restService;

    public DataManager() {
        DataManagerComponent component = DaggerDataManagerComponent.builder()
                .appComponent(IceAndFireApplication.getAppComponent())
                .localModule(new LocalModule())
                .networkModule(new NetworkModule())
                .build();
        component.inject(this);
    }

    /*
    private static class DataManagerHolder {
        private final static DataManager instance = new DataManager();
    }

    public static DataManager getInstance() {
        return DataManagerHolder.instance;
    }
    */

    /**
     * Возвращает время последнего обновления данных о домах на сервере
     *
     * @return значение типа {@link String}, в формате "Thu, 01 Jan 1970 00:00:00 GMT"
     */
    public String getLastModifiedTimestamp() {
        return preferencesManager.getLastProductUpdate();
    }

    /**
     * Возврашает булево значение, с помощью которого можем определить есть ли уже каккие то данные в БД
     *
     * @return true,  если при предыдущих запусках получилось созранить
     * какие-то данные о домах в БД, иначе возвращает false
     */
    public boolean isInfoAboutHousesAreDownloaded() {
        return !preferencesManager.getLastProductUpdate().equals(AppConfig.DEFAULT_LAST_UPDATE_DATE);
    }

    public Observable<HouseResponce> getHousesFromNetwork() {
        return restService.getHouses(getLastModifiedTimestamp())
                .compose(new RestCallTransformer<>())
                .doOnNext(houseResponces -> {
                    Log.e("TAG", "getHousesFromNetwork " + houseResponces.size() + " " + Thread.currentThread().getName());
                })
                .flatMap(Observable::fromIterable);
    }
}