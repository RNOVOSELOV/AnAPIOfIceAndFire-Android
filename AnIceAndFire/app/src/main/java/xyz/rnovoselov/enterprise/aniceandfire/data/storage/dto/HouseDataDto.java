package xyz.rnovoselov.enterprise.aniceandfire.data.storage.dto;

import android.os.Parcel;
import android.os.Parcelable;

import xyz.rnovoselov.enterprise.aniceandfire.data.storage.realm.HouseRealm;

/**
 * Created by roman on 22.05.17.
 */

public class HouseDataDto implements Parcelable {
    private int id;
    private String lastModifiedDate;
    private String name;
    private String words;
    private String coatOfArms;

    public HouseDataDto(int id, String lastModifiedDate) {
        this.id = id;
        this.lastModifiedDate = lastModifiedDate;
        this.name = "";
        this.words = "";
        coatOfArms = "";
    }

    public HouseDataDto(int id, String lastModifiedDate, String name) {
        this.id = id;
        this.lastModifiedDate = lastModifiedDate;
        this.name = name;
        this.words = "";
        coatOfArms = "";
    }

    public HouseDataDto(HouseRealm houseRealm) {
        this.id = houseRealm.getId();
        this.lastModifiedDate = houseRealm.getLastModified();
        this.name = houseRealm.getName();
        this.words = houseRealm.getWords();
        coatOfArms = houseRealm.getCoatOfArms();

    }

    //region ================= Getters ==================

    public int getId() {
        return id;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getName() {
        return name;
    }

    public String getWords() {
        return words;
    }

    public String getCoatOfArms() {
        return coatOfArms;
    }

    //endregion

    //region ================ Parcelable ================

    protected HouseDataDto(Parcel in) {
        id = in.readInt();
        lastModifiedDate = in.readString();
        name = in.readString();
        words = in.readString();
        coatOfArms = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(lastModifiedDate);
        dest.writeString(name);
        dest.writeString(words);
        dest.writeString(coatOfArms);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HouseDataDto> CREATOR = new Creator<HouseDataDto>() {
        @Override
        public HouseDataDto createFromParcel(Parcel in) {
            return new HouseDataDto(in);
        }

        @Override
        public HouseDataDto[] newArray(int size) {
            return new HouseDataDto[size];
        }
    };

    //endregion
}
