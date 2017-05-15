package xyz.rnovoselov.enterprise.aniceandfire.data.network.responces;

import java.util.List;

/**
 * Created by roman on 04.05.17.
 */

public class HouseResponce {

    private String url;
    private String name;
    private String region;
    private String coatOfArms;
    private String words;
    private List<String> titles = null;
    private List<String> seats = null;
    private String currentLord;
    private String heir;
    private String overlord;
    private String founded;
    private String founder;
    private String diedOut;
    private List<String> ancestralWeapons = null;
    private List<String> cadetBranches = null;
    private List<String> swornMembers = null;

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getCoatOfArms() {
        return coatOfArms;
    }

    public String getWords() {
        return words;
    }

    public List<String> getTitles() {
        return titles;
    }

    public List<String> getSeats() {
        return seats;
    }

    public String getCurrentLord() {
        return currentLord;
    }

    public String getHeir() {
        return heir;
    }

    public String getOverlord() {
        return overlord;
    }

    public String getFounded() {
        return founded;
    }

    public String getFounder() {
        return founder;
    }

    public String getDiedOut() {
        return diedOut;
    }

    public List<String> getAncestralWeapons() {
        return ancestralWeapons;
    }

    public List<String> getCadetBranches() {
        return cadetBranches;
    }

    public List<String> getSwornMembers() {
        return swornMembers;
    }
}
