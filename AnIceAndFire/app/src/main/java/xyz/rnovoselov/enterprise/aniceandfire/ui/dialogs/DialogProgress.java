package xyz.rnovoselov.enterprise.aniceandfire.ui.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.rnovoselov.enterprise.aniceandfire.R;

/**
 * Created by roman on 21.05.17.
 */

public class DialogProgress {

    private AlertDialog progressDialog;

    @BindView(R.id.message_text)
    TextView messageTextView;

    public void show(Context context) {
        if (progressDialog == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null);
            ButterKnife.bind(this, view);
            progressDialog = new AlertDialog.Builder(context)
                    .setCancelable(false)
                    .setView(view)
                    .create();
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void showProgressMessage(String message) {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                messageTextView.setText(message);
            }
        }
    }

    public void hide() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
}
