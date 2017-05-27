package xyz.rnovoselov.enterprise.aniceandfire.data.providers;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;
import xyz.rnovoselov.enterprise.aniceandfire.data.storage.realm.HouseRealm;
import xyz.rnovoselov.enterprise.aniceandfire.data.errors.NoDownloadedDataErrors;
import xyz.rnovoselov.enterprise.aniceandfire.utils.AppConfig;

/**
 * Created by roman on 11.05.17.
 */

public class RealmProvider {

    private Realm realmInstance;

    public RealmProvider() {
    }

    public void saveHouseResponceToRealm(HouseRealm houseRealm) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(houseRealm));
        realm.close();
    }

    public void saveHouseResponceToRealm(List<HouseRealm> houseRealm) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(houseRealm));
        realm.close();
    }

    public Observable<Integer> getAllHousesIdObs() {
        RealmResults<HouseRealm> managedHouses = getQueryRealmInstance().where(HouseRealm.class)
                .equalTo("isActive", true)
                .findAllAsync();
        return convertRealmResultToRxJavaObservable(managedHouses)
                .map(HouseRealm::getId)
                .switchIfEmpty(Observable.error(new NoDownloadedDataErrors()));
    }

    public List<Integer> getAllHousesIdList() {
        RealmResults<HouseRealm> managedHouses = getQueryRealmInstance().where(HouseRealm.class)
                .equalTo("isActive", true)
                .findAll();
        List<Integer> list = new ArrayList<>();
        for (HouseRealm house : managedHouses) {
            list.add(house.getId());
        }
        return list;
    }

    public HouseRealm getHouseById(int id) {
        return getQueryRealmInstance().where(HouseRealm.class)
                .equalTo("id", id)
                .findFirst();
    }

    public String getHouseLastModifiedDate(int houseId) {
        HouseRealm house = getHouseById(houseId);
        if (house == null) {
            return AppConfig.DEFAULT_LAST_UPDATE_DATE;
        }
        return house.getLastModified();
    }

    public boolean isSomeHousesDownloaded() {
        RealmResults<HouseRealm> managedHouses = getQueryRealmInstance().where(HouseRealm.class)
                .equalTo("isActive", true)
                .findAll();
        return !(managedHouses.isEmpty());
    }

    private <T extends RealmObject> Observable<T> convertRealmResultToRxJavaObservable(RealmResults<T> results) {
        return results
                .asObservable()                         // realm converts to RxJava v1 only
                .filter(RealmResults::isLoaded)
                .first()                                // hot observable to cold
                .flatMap(Observable::from);
    }

    private void deleteFromRealm(Class<? extends RealmObject> entityRealmClass, int id) {
        Realm realm = Realm.getDefaultInstance();
        RealmObject entity = realm.where(entityRealmClass).equalTo("id", id).findFirst();

        if (entity != null) {
            realm.executeTransaction(realm1 -> entity.deleteFromRealm());
            realm.close();
        }
    }

    private Realm getQueryRealmInstance() {
        if (realmInstance == null || realmInstance.isClosed()) {
            realmInstance = Realm.getDefaultInstance();
        }
        return realmInstance;
    }
}