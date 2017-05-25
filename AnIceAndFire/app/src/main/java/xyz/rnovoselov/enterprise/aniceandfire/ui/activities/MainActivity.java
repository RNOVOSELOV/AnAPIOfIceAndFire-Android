package xyz.rnovoselov.enterprise.aniceandfire.ui.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.rnovoselov.enterprise.aniceandfire.R;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.view.IMainView;

public class MainActivity extends BaseActivity implements IMainView {

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.coordinator_main)
    CoordinatorLayout coordinatorContainer;
    @BindView(R.id.no_any_data_tv)
    TextView noAnyDataTv;
    @BindView(R.id.houses_rv)
    RecyclerView housesRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initToolbar();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    //region ================ IMainView ================

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(coordinatorContainer, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showError(Throwable exception) {
        showMessage(exception.getMessage());
    }

    @Override
    public void showProgressMessage(String message) {
        super.showProgressMessage(message);
    }

    //endregion
}