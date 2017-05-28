package xyz.rnovoselov.enterprise.aniceandfire.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import xyz.rnovoselov.enterprise.aniceandfire.data.storage.dto.HouseDataDto;

/**
 * Created by roman on 27.05.17.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IHouseListView extends MvpView {

    void showProgress();

    void hideProgress();

    void showMessage(String message);

    void showError(Throwable exception);

    void showHousesList(List<HouseDataDto> houses);
}
