package xyz.rnovoselov.enterprise.aniceandfire.mvp.view;

import com.arellomobile.mvp.MvpView;

/**
 * Created by roman on 26.04.17.
 */

public interface ISplashView extends MvpView {

    void showProgress();

    void hideProgress();

    void showMessage(String message);

    void showError(Throwable exception);

}
