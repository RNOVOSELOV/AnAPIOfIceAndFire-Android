package xyz.rnovoselov.enterprise.aniceandfire.utils;

/**
 * Created by roman on 07.05.17.
 */

public class RestUtils {

    /**
     * Метод возвращает номер следующей страницы со списком домов на основании анализа хедера
     *
     * @param currentPageNumber - номер текущей обрабатываемой страницы
     * @param link              - строка "link" в хедере обрабаываемой пагинированной страницы списка домов.
     *                          В данной строке хранятся ссылки на первую, последнюю, следующую и предыдущую пагинированные страницы
     * @return номер следующую пагинированную страницу, либо 0, если обрабатываемая страница последняя
     */
    public static int getNextHousePageNumber(int currentPageNumber, String link) {
        if (link.contains("next")) {
            return currentPageNumber + 1;
        } else {
            return 0;
        }
    }
}
