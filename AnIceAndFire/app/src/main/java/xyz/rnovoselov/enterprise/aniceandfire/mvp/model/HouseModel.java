package xyz.rnovoselov.enterprise.aniceandfire.mvp.model;

import java.util.List;

import rx.Observable;
import xyz.rnovoselov.enterprise.aniceandfire.data.storage.realm.HouseRealm;

/**
 * Created by roman on 01.05.17.
 */

public class HouseModel extends AbstractModel {

    public HouseModel() {
    }

    public boolean isSomeHousesDownloaded() {
        return dataProvider.isSomeHousesDownloaded();
    }

    public Observable<HouseRealm> updateHouseDataInRealm() {
        return dataProvider.updateHousesInfo();
        //return dataProvider.getHouseFromNetworkAndSaveToRealmObs(7);
    }

    public List<Integer> getActiveHousesList () {
        return dataProvider.getListActiveHouses();
    }
}
