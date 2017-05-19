package xyz.rnovoselov.enterprise.aniceandfire.ui.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.rnovoselov.enterprise.aniceandfire.R;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.presenter.HousePresenter;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.view.IHouseView;

public class SplashActivity extends BaseActivity implements IHouseView {

    @BindView(R.id.coordinator_splash)
    CoordinatorLayout coordinator;

    @InjectPresenter
    HousePresenter housePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    //region ================ ISplashView ================

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
        Snackbar.make(coordinator, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showError(Throwable exception) {
        showMessage(exception.getMessage());
    }

    @Override
    public void showDownloadHouseInfoDialog(Map<Integer, String> houses) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_download_houses, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setView(view)
                .create();

        dialog.show();
    }

    //endregion
}
