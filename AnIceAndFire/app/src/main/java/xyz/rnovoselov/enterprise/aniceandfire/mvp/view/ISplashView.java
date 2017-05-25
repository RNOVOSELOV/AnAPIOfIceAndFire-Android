package xyz.rnovoselov.enterprise.aniceandfire.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

/**
 * Created by roman on 26.04.17.
 */

public interface ISplashView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void showProgress();

    void hideProgress();

    void showMessage(String message);

    void showError(Throwable exception);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showProgressMessage(String message);

    void showDownloadHouseInfoDialog(final List<Integer> selected);

    @StateStrategyType(SingleStateStrategy.class)
    void openMainActivity();
}
