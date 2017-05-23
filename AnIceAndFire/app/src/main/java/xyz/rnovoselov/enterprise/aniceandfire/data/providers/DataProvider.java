package xyz.rnovoselov.enterprise.aniceandfire.data.providers;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xyz.rnovoselov.enterprise.aniceandfire.IceAndFireApplication;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.RestCallTransformer;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.RestService;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.error.ApiError;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.error.NetworkAvailableError;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.responces.HouseResponse;
import xyz.rnovoselov.enterprise.aniceandfire.data.storage.dto.HouseDataDto;
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
        return getHousesList(AppConfig.HOUSES_START_PAGE_NUMBER, preferencesProvider.getLastRequestHousesTime());
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
                .doOnNext(houseRealms -> realmProvider.saveHouseResponceToRealm(houseRealms))
                .flatMap(houseRealms -> Observable.empty());
    }

    //region ================ REALM ================

    /**
     * Метод проверяет есть ли загруженные данные (активные и неактивные) о каких либо домах
     *
     * @return true, если есть загруженные данные, иначе false
     */
    public boolean isSomeHousesDownloaded() {
        return realmProvider.isSomeHousesDownloaded();
    }

    public List<Integer> getListActiveHouses() {
        return realmProvider.getAllHousesIdList();
    }

    public String getHouseNameFromRealm(int id) {
        HouseRealm house = realmProvider.getHouseById(id);
        if (house == null) {
            return "";
        }
        return house.getName();
    }

    //endregion

    //region ================ API ================

    /**
     * Метод трансформирует ответ от АПИ и сохраняет Last-Modified информацию из хедера в обьект {@link HouseResponse}
     *
     * @param response ответ от АПИ
     * @return последовательность обьектов {@link HouseResponse}, либо ошибку
     */
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

    /**
     * Метод загружает информацию о персонаже
     *
     * @param swornMembers список персонажей, о которых необходимо загрузить информацию
     * @return список обьектов типа {@link CharacterRealm}
     */
    private Observable<List<CharacterRealm>> getCharactersFromNetworkObs(List<String> swornMembers) {
        return Observable.from(swornMembers)
                .map(s -> s.split("/"))
                .map(strings -> strings[strings.length - 1])
                .flatMap(s -> restService.getCharacterById(s))
                .compose(new RestCallTransformer<>())
                .map(CharacterRealm::new)
                .toList();
    }

    /**
     * Метод обрабатывает ответ запроса информации о доме с АПИ
     *
     * @param houseDto обьект типа {@link HouseDataDto}, содержащий необходимые для загрузки/обновления данные о доме
     * @return возвращат пустую последовательность, информация одоме сохраняется в Realm
     */
    private Observable<HouseDataDto> processApiHouseResponce(HouseDataDto houseDto) {

        return Observable.just(houseDto)
                .observeOn(Schedulers.io())
                .flatMap(houseDataDto -> restService.getHouse(houseDataDto.getLastModifiedDate(), houseDataDto.getId()))
                .flatMap(this::transformRetrofitHouseResponce)
                .flatMap(houseResponse ->
                        Observable.zip(Observable.just(houseResponse), getCharactersFromNetworkObs(houseResponse.getSwornMembers()),
                                (houseResponse1, characterRealms) -> {
                                    HouseRealm houseRealm = new HouseRealm(houseResponse1);
                                    houseRealm.getCharacters().addAll(characterRealms);
                                    return houseRealm;
                                }))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(houseRealm -> realmProvider.saveHouseResponceToRealm(houseRealm))
                .map(HouseDataDto::new)
                .defaultIfEmpty(houseDto);
    }

    /**
     * Метод получает информацию о списке домов с АПИ и сохранят (или обновляет) данные в Realm
     *
     * @param housesId {@link List} домов типа {@link Integer}: идентификаторы домов, которые необходимо загрузить с АПИ
     * @return метод возвращает пустую последовательность, загруженные данные сохраняются в Realm
     */
    public Observable<HouseDataDto> getHouseFromNetworkAndSaveToRealmObs(List<Integer> housesId) {
        return NetworkStatusChecker.isInternetAvailable()
                .flatMap(aBoolean -> aBoolean ? Observable.from(housesId) : Observable.error(new NetworkAvailableError()))
                .flatMap(integer -> {
                    HouseDataDto houseData = new HouseDataDto(integer, realmProvider.getHouseLastModifiedDate(integer));
                    return Observable.just(houseData);
                })
                .flatMap(this::processApiHouseResponce);
    }

    /**
     * Метод обновляет информацию о всех загруженных и активных домах
     *
     * @return метод возвращает пустую последовательность, обновляя информацию о всех загруженных в Realm домах
     */
    public Observable<HouseDataDto> updateHousesInfo() {
        return realmProvider.getAllHousesIdObs()
                .toList()
                .flatMap(this::getHouseFromNetworkAndSaveToRealmObs);
    }

    //endregion

}