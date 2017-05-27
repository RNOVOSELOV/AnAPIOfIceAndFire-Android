package xyz.rnovoselov.enterprise.aniceandfire.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by roman on 27.05.17.
 */

public interface IHouseListView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void showProgress();

    void hideProgress();

    void showMessage(String message);

    void showError(Throwable exception);
}
