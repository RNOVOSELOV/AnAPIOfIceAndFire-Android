package xyz.rnovoselov.enterprise.aniceandfire.data.providers;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import xyz.rnovoselov.enterprise.aniceandfire.IceAndFireApplication;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.RestCallTransformer;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.RestService;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.error.NetworkAvailableError;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.responces.HouseResponce;
import xyz.rnovoselov.enterprise.aniceandfire.data.storage.realm.HouseRealm;
import xyz.rnovoselov.enterprise.aniceandfire.di.components.DaggerDataProviderComponent;
import xyz.rnovoselov.enterprise.aniceandfire.di.components.DataProviderComponent;
import xyz.rnovoselov.enterprise.aniceandfire.di.modules.LocalModule;
import xyz.rnovoselov.enterprise.aniceandfire.di.modules.NetworkModule;
import xyz.rnovoselov.enterprise.aniceandfire.utils.AppConfig;
import xyz.rnovoselov.enterprise.aniceandfire.utils.Constants;
import xyz.rnovoselov.enterprise.aniceandfire.utils.NetworkStatusChecker;
import xyz.rnovoselov.enterprise.aniceandfire.utils.RestUtils;

/**
 * Created by roman on 27.04.17.
 */

public class DataProvider {

    private static final String TAG = Constants.TAG_PREFIX + DataProvider.class.getSimpleName();

    @Inject
    PreferencesProvider preferencesProvider;
    @Inject
    RestService restService;
    @Inject
    RealmProvider realmProvider;

    public DataProvider() {
        DataProviderComponent component = DaggerDataProviderComponent.builder()
                .appComponent(IceAndFireApplication.getAppComponent())
                .localModule(new LocalModule())
                .networkModule(new NetworkModule())
                .build();
        component.inject(this);
    }

    /**
     * Возвращает время последнего обновления данных о домах на сервере
     *
     * @return значение типа {@link String}, в формате "Thu, 01 Jan 1970 00:00:00 GMT"
     */
    private String getLastModifiedTimestamp() {
        return preferencesProvider.getLastProductUpdate();
    }

    /**
     * рекурсивная функия для получения списка домов с пагинированных страниц АПИ
     *
     * @param pageNumber   - номер страницы с которой необходимо получить список домов (начинается с 1)
     * @param lastModified - параметр для использования Last-Modified модели кеширования данных на сервере.
     *                     Передаем дату последней загрухки данных в формате "Thu, 01 Jan 1970 00:00:00 GMT", чтобы сервер отправил нам данные только в случае их изменения
     * @return поток данных со списком всех домов
     */
    private Observable<Response<List<HouseResponce>>> getHousesList(int pageNumber, String lastModified) {
        if (pageNumber == 0) {
            return Observable.empty();
        }
        return restService.getHouses(lastModified, pageNumber, AppConfig.HOUSES_PER_QUERY)
                .flatMap(listResponse -> Observable.just(listResponse)
                        .mergeWith(getHousesList(RestUtils.getNextHousePageNumber(pageNumber, listResponse.headers().get("link")), lastModified)));
    }

    /**
     * Метод запускает рекурсивную функцию получения пагинированных страниц списка домов с АПИ
     *
     * @return поток данных, в качестве испускаемых значений которого является список домов с одной из пагинированных страниц
     */
    private Observable<Response<List<HouseResponce>>> getHousesList() {
        return getHousesList(AppConfig.HOUSES_START_PAGE_NUMBER, getLastModifiedTimestamp());
    }

    /**
     * Получаем список всех домов с АПИ
     *
     * @return поток данных типа {@link HouseResponce}
     */
    public Observable<HouseResponce> getHousesFromNetworkObs() {
        return NetworkStatusChecker.isInternetAvailable()
                .flatMap(aBoolean -> aBoolean ? getHousesList() : Observable.error(new NetworkAvailableError()))
                .compose(new RestCallTransformer<>())
                .flatMap(Observable::from)
                .map(HouseRealm::new)
                .toList()
                .doOnNext(houseRealms -> realmProvider.saveHouseResponceToRealm(houseRealms))
                .flatMap(houseRealms -> Observable.empty());
    }
}