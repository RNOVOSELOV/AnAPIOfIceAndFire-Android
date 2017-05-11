package xyz.rnovoselov.enterprise.aniceandfire.data.storage.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.responces.HouseResponce;

/**
 * Created by roman on 11.05.17.
 */

public class HouseRealm extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String name;

    private RealmList<CharacterRealm> characters = new RealmList<>();


    public HouseRealm() {
    }

    public HouseRealm(HouseResponce responce) {
        String[] urlParts = responce.getUrl().split("/");
        id = Integer.parseInt(urlParts[urlParts.length - 1]);
        name = responce.getName();
    }

    public int getHouseId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RealmList<CharacterRealm> getCharacters() {
        return characters;
    }
}
