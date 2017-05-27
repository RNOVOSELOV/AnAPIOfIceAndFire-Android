package xyz.rnovoselov.enterprise.aniceandfire.ui.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import xyz.rnovoselov.enterprise.aniceandfire.mvp.view.IHouseListView;
import xyz.rnovoselov.enterprise.aniceandfire.ui.activities.MainActivity;

/**
 * Created by roman on 27.05.17.
 */

public class HouseListFragment extends Fragment implements IHouseListView {



    //region ================ IHouseListView ================

    @Override
    public void showProgress() {
        MainActivity activity = getMainActivity();
        if (activity != null) {
            activity.showProgress();
        }
    }

    @Override
    public void hideProgress() {
        MainActivity activity = getMainActivity();
        if (activity != null) {
            activity.hideProgress();
        }
    }

    @Override
    public void showMessage(String message) {
        MainActivity activity = getMainActivity();
        if (activity != null) {
            activity.showMessage(message);
        }
    }

    @Override
    public void showError(Throwable exception) {
        MainActivity activity = getMainActivity();
        if (activity != null) {
            activity.showError(exception);
        }
    }

    //endregion

    @Nullable
    private MainActivity getMainActivity() {
        if (getActivity() instanceof MainActivity) {
            return ((MainActivity) getActivity());
        }
        return null;
    }
}
