package xyz.rnovoselov.enterprise.aniceandfire.data.network.responces;

import java.util.List;

/**
 * Created by roman on 04.05.17.
 */

public class HouseResponce {

    public String url;
    public String name;
    public String region;
    public String coatOfArms;
    public String words;
    public List<String> titles = null;
    public List<String> seats = null;
    public String currentLord;
    public String heir;
    public String overlord;
    public String founded;
    public String founder;
    public String diedOut;
    public List<String> ancestralWeapons = null;
    public List<String> cadetBranches = null;
    public List<String> swornMembers = null;

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
