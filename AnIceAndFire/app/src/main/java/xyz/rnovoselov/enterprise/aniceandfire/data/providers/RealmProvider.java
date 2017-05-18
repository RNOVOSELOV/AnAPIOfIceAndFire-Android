package xyz.rnovoselov.enterprise.aniceandfire.data.providers;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;
import xyz.rnovoselov.enterprise.aniceandfire.data.storage.realm.HouseRealm;

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

    private void deleteFromRealm(Class<? extends RealmObject> entityRealmClass, int id) {
        Realm realm = Realm.getDefaultInstance();
        RealmObject entity = realm.where(entityRealmClass).equalTo("id", id).findFirst();

        if (entity != null) {
            realm.executeTransaction(realm1 -> entity.deleteFromRealm());
            realm.close();
        }
    }

    public Observable<String> getHouseLastModifiedDate(int houseId) {
        RealmResults<HouseRealm> house = getQueryRealmInstance().where(HouseRealm.class)
                .equalTo("id", houseId)
                .findAllAsync();
        return convertRealmResultToRxJavaObservable(house)
                .first()
                .map(HouseRealm::getLastModified);
    }

    public Observable<Integer> getAllHousesId() {
        RealmResults<HouseRealm> managedHouses = getQueryRealmInstance().where(HouseRealm.class)
                .equalTo("isActive", true)
                .findAllAsync();
        return convertRealmResultToRxJavaObservable(managedHouses)
                .map(HouseRealm::getId);
    }

    private <T extends RealmObject> Observable<T> convertRealmResultToRxJavaObservable(RealmResults<T> results) {
        return results
                .asObservable()                         // realm converts to RxJava v1 only
                .filter(RealmResults::isLoaded)
                .first()                                // hot observable to cold
                .flatMap(Observable::from);
    }

    private Realm getQueryRealmInstance() {
        if (realmInstance == null || realmInstance.isClosed()) {
            realmInstance = Realm.getDefaultInstance();
        }
        return realmInstance;
    }
}
