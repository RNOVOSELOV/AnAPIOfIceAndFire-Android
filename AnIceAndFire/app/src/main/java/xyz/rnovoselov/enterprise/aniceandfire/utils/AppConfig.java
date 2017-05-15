package xyz.rnovoselov.enterprise.aniceandfire.utils;

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
}
