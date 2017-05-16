package xyz.rnovoselov.enterprise.aniceandfire.mvp.model;

import rx.Observable;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.responces.HouseResponse;

/**
 * Created by roman on 01.05.17.
 */

public class HouseModel extends AbstractModel{

    public HouseModel() {

    }

    public Observable getHousesFromNetwork () {
        return dataProvider.getHouseFromNetworkObs(7);
    }
}
