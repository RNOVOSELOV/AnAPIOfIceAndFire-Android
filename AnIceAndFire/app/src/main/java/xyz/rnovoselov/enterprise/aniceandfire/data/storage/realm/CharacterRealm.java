package xyz.rnovoselov.enterprise.aniceandfire.data.storage.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by roman on 11.05.17.
 */

public class CharacterRealm extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String name;

    public CharacterRealm() {
    }

    public int getCharacterId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
