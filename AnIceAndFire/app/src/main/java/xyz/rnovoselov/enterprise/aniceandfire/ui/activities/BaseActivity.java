package xyz.rnovoselov.enterprise.aniceandfire.ui.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.rnovoselov.enterprise.aniceandfire.R;
import xyz.rnovoselov.enterprise.aniceandfire.ui.dialogs.DialogProgress;
import xyz.rnovoselov.enterprise.aniceandfire.utils.Constants;

/**
 * Created by roman on 23.04.17.
 */

public class BaseActivity extends MvpAppCompatActivity {

    protected String TAG = Constants.TAG_PREFIX + getClass().getSimpleName();

    protected DialogProgress progressDialog = new DialogProgress();

    protected void showProgressDialog() {
        progressDialog.show(this);
    }

    protected void hideProgressDialog() {
        progressDialog.hide();
    }

    protected void showProgressMessage(String text) {
        progressDialog.showProgressMessage(text);
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideProgressDialog();
    }
}
