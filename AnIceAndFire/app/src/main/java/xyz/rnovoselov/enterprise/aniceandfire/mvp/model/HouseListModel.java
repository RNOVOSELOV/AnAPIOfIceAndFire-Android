package xyz.rnovoselov.enterprise.aniceandfire.mvp.model;

import rx.Observable;
import xyz.rnovoselov.enterprise.aniceandfire.data.storage.dto.HouseDataDto;

/**
 * Created by roman on 27.05.17.
 */

public class HouseListModel extends AbstractModel {

    public HouseListModel() {
    }

    public Observable<HouseDataDto> getHouses() {
        return dataProvider.getHousesFromRealm();
    }
}
