package xyz.rnovoselov.enterprise.aniceandfire.mvp.model;

import java.util.List;

import rx.Observable;
import xyz.rnovoselov.enterprise.aniceandfire.data.storage.dto.HouseDataDto;

/**
 * Created by roman on 01.05.17.
 */

public class HouseModel extends AbstractModel {

    public HouseModel() {
    }

    public boolean isSomeHousesDownloaded() {
        return dataProvider.isSomeHousesDownloaded();
    }

    public Observable<HouseDataDto> updateHouseDataInRealm() {
        return dataProvider.updateHousesInfo();
    }

    public Observable<HouseDataDto> getHousesAndSaveToRealm(List<Integer> housesId) {
        return dataProvider.getHouseFromNetworkAndSaveToRealmObs(housesId);
    }

    public String getHouseName(int id) {
        return dataProvider.getHouseNameFromRealm(id);
    }
}
