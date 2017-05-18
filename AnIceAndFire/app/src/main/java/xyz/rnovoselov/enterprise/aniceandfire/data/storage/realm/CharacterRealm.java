package xyz.rnovoselov.enterprise.aniceandfire.data.storage.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.responces.CharacterResponse;

/**
 * Created by roman on 11.05.17.
 */

public class CharacterRealm extends RealmObject {

    @PrimaryKey
    private Integer id;
    @Required
    private String name;

    public CharacterRealm() {
    }

    public CharacterRealm(CharacterResponse response) {
        String[] urlParts = response.getUrl().split("/");
        id = Integer.parseInt(urlParts[urlParts.length - 1]);
        name = response.getName();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
