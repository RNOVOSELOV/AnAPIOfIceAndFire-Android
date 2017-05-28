package xyz.rnovoselov.enterprise.aniceandfire.data.storage.realm;

import android.support.annotation.NonNull;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import rx.Observable;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.responces.HouseResponse;

/**
 * Created by roman on 11.05.17.
 */

public class HouseRealm extends RealmObject {

    @PrimaryKey
    private Integer id;
    private String url;
    @Required
    private String name;

    private String lastModified;    // last modified in API date
    private Boolean isActive;       // false - if house removed by user

    private String coatOfArms;
    private String words;
    private RealmList<HousesTitles> titles = new RealmList<>();
    private String yearFounded;
    private String diedOut;
    private RealmList<CharacterRealm> characters = new RealmList<>();

    public HouseRealm() {
    }

    public HouseRealm(HouseResponse responce) {
        String[] urlParts = responce.getUrl().split("/");
        id = Integer.parseInt(urlParts[urlParts.length - 1]);
        url = responce.getUrl();
        lastModified = responce.getLastModified();
        name = responce.getName();
        coatOfArms = responce.getCoatOfArms();
        words = responce.getWords();
        yearFounded = responce.getFounded();
        diedOut = responce.getDiedOut();

        isActive = true;

        Observable.from(responce.getTitles())
                .map(HousesTitles::new)
                .subscribe(title -> titles.add(title));
    }

    public Integer getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getCoatOfArms() {
        return coatOfArms;
    }

    public String getWords() {
        return words;
    }

    public RealmList<HousesTitles> getTitles() {
        return titles;
    }

    public String getYearFounded() {
        return yearFounded;
    }

    public String getDiedOut() {
        return diedOut;
    }

    public RealmList<CharacterRealm> getCharacters() {
        return characters;
    }

    public String getLastModified() {
        return lastModified;
    }

    public Boolean getActive() {
        return isActive;
    }
}
