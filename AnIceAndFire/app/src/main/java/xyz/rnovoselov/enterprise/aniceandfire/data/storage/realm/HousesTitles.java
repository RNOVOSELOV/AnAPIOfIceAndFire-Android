package xyz.rnovoselov.enterprise.aniceandfire.data.storage.realm;

import io.realm.RealmObject;

/**
 * Created by roman on 15.05.17.
 */

public class HousesTitles extends RealmObject {

    private String title;

    public HousesTitles() {
    }

    public HousesTitles(String title) {
        this.title = title;
    }

    public String getTitles() {
        return title;
    }
}
