package xyz.rnovoselov.enterprise.aniceandfire.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.rnovoselov.enterprise.aniceandfire.R;
import xyz.rnovoselov.enterprise.aniceandfire.data.storage.dto.HouseDataDto;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.presenter.HousesListPresenter;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.view.IHouseListView;
import xyz.rnovoselov.enterprise.aniceandfire.ui.activities.MainActivity;
import xyz.rnovoselov.enterprise.aniceandfire.ui.adapters.HouseListAdapter;
import xyz.rnovoselov.enterprise.aniceandfire.utils.Constants;

/**
 * Created by roman on 27.05.17.
 */

public class HouseListFragment extends MvpAppCompatFragment implements IHouseListView {
    private static final String TAG = Constants.TAG_PREFIX + HouseListFragment.class.getSimpleName();

    @BindView(R.id.no_any_data_tv)
    TextView noHousesAvailableInfoTxt;

    @BindView(R.id.houses_rv)
    RecyclerView housesListRv;


    private HouseListAdapter adapter;

    @InjectPresenter
    HousesListPresenter presenter;

    public HouseListFragment() {
        Log.e(TAG, "HouseListFragment () ");
    }

    public static HouseListFragment newInstance() {
        return new HouseListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new HouseListAdapter(getActivity(), new ArrayList<>(), new HouseListAdapter.HouseListViewHolder.HouseClickListener() {
            @Override
            public void onShowMoreClick(int position) {
                showMessage("More " + position);
            }

            @Override
            public void onShowCharactersClick(int position) {
                showMessage("char " + position);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_houses_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        housesListRv.setLayoutManager(manager);
        housesListRv.swapAdapter(adapter, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

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

    @Override
    public void showHousesList(List<HouseDataDto> houses) {
        adapter.setHousesList(houses);
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
