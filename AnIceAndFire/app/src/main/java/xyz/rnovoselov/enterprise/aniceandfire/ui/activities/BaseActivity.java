package xyz.rnovoselov.enterprise.aniceandfire.ui.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import xyz.rnovoselov.enterprise.aniceandfire.R;
import xyz.rnovoselov.enterprise.aniceandfire.utils.Constants;

/**
 * Created by roman on 23.04.17.
 */

public class BaseActivity extends AppCompatActivity {

    protected String TAG = Constants.TAG_PREFIX + getClass().getSimpleName();

    protected AlertDialog progressDialog;

    protected void showProgress() {
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

    protected void hideProgress() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
}
