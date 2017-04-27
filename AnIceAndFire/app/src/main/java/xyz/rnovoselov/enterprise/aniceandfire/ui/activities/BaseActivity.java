package xyz.rnovoselov.enterprise.aniceandfire.ui.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;

import xyz.rnovoselov.enterprise.aniceandfire.R;
import xyz.rnovoselov.enterprise.aniceandfire.utils.Constants;

/**
 * Created by roman on 23.04.17.
 */

public class BaseActivity extends MvpAppCompatActivity {

    protected String TAG = Constants.TAG_PREFIX + getClass().getSimpleName();

    protected AlertDialog progressDialog;

    protected void showProgressDialog() {
        if (progressDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_progress, null);
            progressDialog = new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setView(view)
                    .create();
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.show();
    }

    protected void hideProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
}
