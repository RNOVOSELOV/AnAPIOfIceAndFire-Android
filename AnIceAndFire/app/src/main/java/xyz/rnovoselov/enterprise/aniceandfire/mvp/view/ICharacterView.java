package xyz.rnovoselov.enterprise.aniceandfire.mvp.view;

import com.arellomobile.mvp.MvpView;

/**
 * Created by roman on 14.05.17.
 */

public interface ICharacterView extends MvpView {

    void showProgress();

    void hideProgress();

    void showMessage(String message);

    void showError(Throwable exception);
}
