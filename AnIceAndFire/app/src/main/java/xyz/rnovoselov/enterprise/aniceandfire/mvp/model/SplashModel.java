package xyz.rnovoselov.enterprise.aniceandfire.mvp.model;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.responces.HouseResponce;

/**
 * Created by roman on 01.05.17.
 */

public class SplashModel extends AbstractModel{

    public SplashModel () {

    }

    public Flowable<HouseResponce> getHousesFromNetwork () {
        return dataManager.getHousesFromNetwork();
    }
}
