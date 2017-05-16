package xyz.rnovoselov.enterprise.aniceandfire.data.providers;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import xyz.rnovoselov.enterprise.aniceandfire.IceAndFireApplication;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.RestCallTransformer;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.RestService;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.error.ApiError;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.error.NetworkAvailableError;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.responces.HouseResponse;
import xyz.rnovoselov.enterprise.aniceandfire.data.storage.realm.CharacterRealm;
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
    private String getLastRequestDate() {
        return preferencesProvider.getLastRequestHousesTime();
    }

    /**
     * рекурсивная функия для получения списка домов с пагинированных страниц АПИ
     *
     * @param pageNumber   - номер страницы с которой необходимо получить список домов (начинается с 1)
     * @param lastModified - параметр для использования Last-Modified модели кеширования данных на сервере.
     *                     Передаем дату последней загрухки данных в формате "Thu, 01 Jan 1970 00:00:00 GMT", чтобы сервер отправил нам данные только в случае их изменения
     * @return поток данных со списком всех домов
     */
    private Observable<Response<List<HouseResponse>>> getHousesList(int pageNumber, String lastModified) {
        if (pageNumber == 0) {
            return Observable.empty();
        }
        return restService.getHouses(lastModified, pageNumber, AppConfig.HOUSES_PER_QUERY)
                .flatMap(listResponse -> Observable.just(listResponse)
                        .mergeWith(getHousesList(RestUtils.getNextHousePageNumber(pageNumber, listResponse.headers().get(Constants.HEADER_LINK)), lastModified)));
    }

    /**
     * Метод запускает рекурсивную функцию получения пагинированных страниц списка домов с АПИ
     *
     * @return поток данных, в качестве испускаемых значений которого является список домов с одной из пагинированных страниц
     */
    private Observable<Response<List<HouseResponse>>> getHousesList() {
        return getHousesList(AppConfig.HOUSES_START_PAGE_NUMBER, getLastRequestDate());
    }

    /**
     * Получаем список всех домов с АПИ
     *
     * @return поток данных типа {@link HouseResponse}
     */
    public Observable<HouseResponse> getHousesFromNetworkObs() {
        return NetworkStatusChecker.isInternetAvailable()
                .flatMap(aBoolean -> aBoolean ? getHousesList() : Observable.error(new NetworkAvailableError()))
                .doOnNext(listResponse -> {
                    String requestDate = listResponse.headers().get(Constants.HEADER_DATE);
                    if (!(requestDate == null || requestDate.isEmpty())) {
                        preferencesProvider.saveLastRequestHousesTime(requestDate);
                    }
                })
                .compose(new RestCallTransformer<>())
                .flatMap(Observable::from)
                .map(HouseRealm::new)
                .toList()
//                .doOnNext(houseRealms -> realmProvider.saveHouseResponceToRealm(houseRealms))
                .flatMap(houseRealms -> Observable.empty());
    }

    private Observable<HouseResponse> transformRetrofitHouseResponce(Response<HouseResponse> response) {
        switch (response.code()) {
            case 200:
                String lastModified = response.headers().get(Constants.HEADER_LAST_MODIFIED);
                return Observable.just(response.body())
                        .doOnNext(houseResponse -> houseResponse.setLastModified(lastModified));
            case 304:
                return Observable.empty();
            default:
                return Observable.error(new ApiError(response.code()));
        }
    }

    public Observable<List<CharacterRealm>> getCharactersFromNetworkObs(List<String> swornMembers) {
        return Observable.from(swornMembers)
                .map(s -> s.split("/"))
                .map(strings -> strings[strings.length - 1])
                .flatMap(s -> restService.getCharacterById(s))
                .compose(new RestCallTransformer<>())
                .map(CharacterRealm::new)
                .toList();
    }

    // add last modified from realm to query

    public Observable getHouseFromNetworkObs(int houseId) {
        return NetworkStatusChecker.isInternetAvailable()
                .flatMap(aBoolean -> aBoolean ? restService.getHouse(getLastRequestDate(), String.valueOf(houseId)) : Observable.error(new NetworkAvailableError()))
                .flatMap(this::transformRetrofitHouseResponce)
                .flatMap(houseResponse ->
                        Observable.zip(Observable.just(houseResponse), getCharactersFromNetworkObs(houseResponse.getSwornMembers()),
                                (houseResponse1, characterRealms) -> {
                                    HouseRealm houseRealm = new HouseRealm(houseResponse1);
                                    houseRealm.getCharacters().addAll(characterRealms);
                                    return houseRealm;
                                }))
                .doOnNext(houseRealm -> realmProvider.saveHouseResponceToRealm(houseRealm))
                .flatMap(houseRealm -> Observable.empty());
    }
}