package xyz.rnovoselov.enterprise.aniceandfire.ui.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.rnovoselov.enterprise.aniceandfire.R;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.presenter.SplashPresenter;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.view.ISplashView;

public class SplashActivity extends BaseActivity implements ISplashView {

    @BindView(R.id.coordinator_splash)
    CoordinatorLayout coordinator;

    @InjectPresenter
    SplashPresenter presenter;

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

    //endregion
}
