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

    public HouseDataDto(int id, String lastModifiedDate) {
        this.id = id;
        this.lastModifiedDate = lastModifiedDate;
        this.name = "";
        this.words = "";
    }

    public HouseDataDto(int id, String lastModifiedDate, String name) {
        this.id = id;
        this.lastModifiedDate = lastModifiedDate;
        this.name = name;
        this.words = "";
    }

    public HouseDataDto(HouseRealm houseRealm) {
        this.id = houseRealm.getId();
        this.lastModifiedDate = houseRealm.getLastModified();
        this.name = houseRealm.getName();
        this.words = houseRealm.getWords();
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

    //endregion

    //region ================ Parcelable ================

    protected HouseDataDto(Parcel in) {
        id = in.readInt();
        lastModifiedDate = in.readString();
        name = in.readString();
        words = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(lastModifiedDate);
        dest.writeString(name);
        dest.writeString(words);
    }

    //endregion
}
