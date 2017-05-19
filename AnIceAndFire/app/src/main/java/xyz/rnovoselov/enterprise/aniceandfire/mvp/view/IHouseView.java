package xyz.rnovoselov.enterprise.aniceandfire.mvp.view;

import com.arellomobile.mvp.MvpView;

import java.util.Map;

/**
 * Created by roman on 26.04.17.
 */

public interface IHouseView extends MvpView {

    void showProgress();

    void hideProgress();

    void showMessage(String message);

    void showError(Throwable exception);

    void showDownloadHouseInfoDialog(Map<Integer, String> houses);

}
