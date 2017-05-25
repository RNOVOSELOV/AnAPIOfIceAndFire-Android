package xyz.rnovoselov.enterprise.aniceandfire.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by roman on 24.05.17.
 */

public interface IMainView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void showProgress();

    void hideProgress();

    void showMessage(String message);

    void showError(Throwable exception);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showProgressMessage(String message);
}
