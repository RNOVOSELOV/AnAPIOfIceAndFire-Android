package xyz.rnovoselov.enterprise.aniceandfire.data.providers;

import java.util.List;

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

    public void saveHouseResponceToRealm(List<HouseRealm> houseResponce) {
        Realm realm = Realm.getDefaultInstance();
//        HouseRealm houseRealm = new HouseRealm(houseResponce);

        // добавить героев

        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(houseResponce));
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

    public Observable<HouseRealm> getAllHouses() {
        RealmResults<HouseRealm> managedHouses = getQueryRealmInstance().where(HouseRealm.class).findAllAsync();
        return convertRealmResultToRxJavaObservable(managedHouses);
    }

    private <T extends RealmObject> Observable<T> convertRealmResultToRxJavaObservable(RealmResults<T> results) {
        return results
                .asObservable()                         // realm converts to v1 only
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
