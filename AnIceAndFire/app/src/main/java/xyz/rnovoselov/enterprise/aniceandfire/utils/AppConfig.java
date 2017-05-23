package xyz.rnovoselov.enterprise.aniceandfire.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by roman on 01.05.17.
 */

public class AppConfig {
    public static final String BASE_URL = "http://anapioficeandfire.com/api/";
    public static final String DEFAULT_LAST_UPDATE_DATE = "Thu, 01 Jan 1970 00:00:00 GMT";
    public static final int HOUSES_START_PAGE_NUMBER = 1;
    public static final int HOUSES_PER_QUERY = 50;

    public static final String API_VERSION_HEADER_KEY = "Accept";
    public static final String API_VERSION_HEADER_VALUE = "application/vnd.anapioficeandfire+json; version=1";

    public static final int MAX_CONNECTION_TIMEOUT = 5000;
    public static final int MAX_READ_TIMEOUT = 5000;
    public static final int MAX_WRITE_TIMEOUT = 5000;

    private static class DefaultHousesHolder {
        private final static Map<String, Integer> defaultHouses = new HashMap<>();

        static {
            defaultHouses.put("House Arryn of the Eyrie", 7);
            defaultHouses.put("House Baratheon of Storm's End", 17);
            defaultHouses.put("House Greyjoy of Pyke", 169);
            defaultHouses.put("House Lannister of Casterly Rock", 229);
            defaultHouses.put("House Stark of Winterfell", 362);
            defaultHouses.put("House Targaryen of King's Landing", 378);
            defaultHouses.put("House Tully of Riverrun", 395);
        }
    }

    public static Map<String, Integer> getDefaultHousesMap() {
        return DefaultHousesHolder.defaultHouses;
    }
}

/*
    public static final int[] DEFAULT_HOUSES_ID = {7, 17, 169, 229, 362, 378, 395};
    public static final String[] DEFAULT_HOUSES = {
            "House Arryn of the Eyrie",
            "House Baratheon of Storm's End",
            "House Greyjoy of Pyke",
            "House Lannister of Casterly Rock",
            "House Stark of Winterfell",
            "House Targaryen of King's Landing",
            "House Tully of Riverrun"
    };
*/