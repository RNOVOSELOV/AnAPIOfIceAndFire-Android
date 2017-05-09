package xyz.rnovoselov.enterprise.aniceandfire.mvp.model;

import io.reactivex.Flowable;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.responces.HouseResponce;

/**
 * Created by roman on 01.05.17.
 */

public class HouseModel extends AbstractModel{

    public HouseModel() {

    }

    public Flowable<HouseResponce> getHousesFromNetwork () {
        return dataManager.getHousesFromNetworkObs();
    }
}
