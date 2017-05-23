package xyz.rnovoselov.enterprise.aniceandfire.data.storage.dto;

import xyz.rnovoselov.enterprise.aniceandfire.data.storage.realm.HouseRealm;

/**
 * Created by roman on 22.05.17.
 */

public class HouseDataDto {
    private int id;
    private String lastModifiedDate;
    private String name;

    public HouseDataDto(int id, String lastModifiedDate) {
        this.id = id;
        this.lastModifiedDate = lastModifiedDate;
        this.name = "";
    }

    public HouseDataDto(int id, String lastModifiedDate, String name) {
        this.id = id;
        this.lastModifiedDate = lastModifiedDate;
        this.name = name;
    }

    public HouseDataDto(HouseRealm houseRealm) {
        this.id = houseRealm.getId();
        this.lastModifiedDate = houseRealm.getLastModified();
        this.name = houseRealm.getName();
    }

    public int getId() {
        return id;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
