package xyz.rnovoselov.enterprise.aniceandfire.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.rnovoselov.enterprise.aniceandfire.R;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.presenter.HousePresenter;
import xyz.rnovoselov.enterprise.aniceandfire.mvp.view.ISplashView;
import xyz.rnovoselov.enterprise.aniceandfire.utils.AppConfig;

public class SplashActivity extends BaseActivity implements ISplashView {

    @BindView(R.id.coordinator_splash)
    CoordinatorLayout coordinator;

    @InjectPresenter
    HousePresenter housePresenter;

    AlertDialog selectDefaultHousesDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStop() {
        if (selectDefaultHousesDialog != null) {
            if (selectDefaultHousesDialog.isShowing()) {
                selectDefaultHousesDialog.dismiss();
            }
        }
        super.onStop();
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
    public void showProgressMessage(String message) {
        super.showProgressMessage(message);
    }

    @Override
    public void showError(Throwable exception) {
        showMessage(exception.getMessage());
    }

    @Override
    public void showDownloadHouseInfoDialog(final List<Integer> selected) {

        List<String> housesList = new ArrayList<>(AppConfig.getDefaultHousesMap().keySet());    // Список ключей мапы
        Collections.sort(housesList, String::compareTo);                                        // Сортировка списка
        String[] housesArray = housesList.toArray(new String[housesList.size()]);               // Получение массива из списка
        boolean[] isCheckedFields = new boolean[housesArray.length];

        for (int i = 0; i < housesArray.length; i++) {
            if (selected.contains(AppConfig.getDefaultHousesMap().get(housesArray[i]))) {
                isCheckedFields[i] = true;
            } else {
                isCheckedFields[i] = false;
            }
        }

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("Выберите дом для загрузки")
                .setMultiChoiceItems(housesArray, isCheckedFields, (DialogInterface dialog13, int which, boolean isChecked) -> {
                    Integer houseId = AppConfig.getDefaultHousesMap().get(housesArray[which]);
                    if (isChecked) {
                        housePresenter.addHouseSelectedItem(houseId);
                    } else {
                        housePresenter.removeHouseSelectedItem(houseId);
                    }
                })
                .setPositiveButton("Загрузить", (dialog12, which) -> housePresenter.startDownloadDefaultHouses())
                .setNegativeButton("Пропустить", (dialog1, which) -> openMainActivity());

        selectDefaultHousesDialog = dialogBuilder.create();
        selectDefaultHousesDialog.show();
    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityCompat.startActivity(this, intent, null);
        ActivityCompat.finishAfterTransition(this);
    }

    //endregion
}
