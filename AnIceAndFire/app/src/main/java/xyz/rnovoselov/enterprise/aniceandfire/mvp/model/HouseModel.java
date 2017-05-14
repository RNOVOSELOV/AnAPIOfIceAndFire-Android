package xyz.rnovoselov.enterprise.aniceandfire.mvp.model;

import rx.Observable;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.responces.HouseResponce;

/**
 * Created by roman on 01.05.17.
 */

public class HouseModel extends AbstractModel{

    public HouseModel() {

    }

    public Observable<HouseResponce> getHousesFromNetwork () {
        return dataProvider.getHousesFromNetworkObs();
    }
}
