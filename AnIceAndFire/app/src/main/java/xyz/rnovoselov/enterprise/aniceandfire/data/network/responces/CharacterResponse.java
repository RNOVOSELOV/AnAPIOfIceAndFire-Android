package xyz.rnovoselov.enterprise.aniceandfire.data.network.responces;

import java.util.List;

/**
 * Created by roman on 16.05.17.
 */


public class CharacterResponse {

    private String url;
    private String name;
    private String gender;
    private String culture;
    private String born;
    private String died;
    private List<String> titles = null;
    private List<String> aliases = null;
    private String father;
    private String mother;
    private String spouse;
    private List<String> allegiances = null;
    private List<String> books = null;
    private List<String> povBooks = null;
    private List<String> tvSeries = null;
    private List<String> playedBy = null;

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getCulture() {
        return culture;
    }

    public String getBorn() {
        return born;
    }

    public String getDied() {
        return died;
    }

    public List<String> getTitles() {
        return titles;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getFather() {
        return father;
    }

    public String getMother() {
        return mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public List<String> getAllegiances() {
        return allegiances;
    }

    public List<String> getBooks() {
        return books;
    }

    public List<String> getPovBooks() {
        return povBooks;
    }

    public List<String> getTvSeries() {
        return tvSeries;
    }

    public List<String> getPlayedBy() {
        return playedBy;
    }
}
