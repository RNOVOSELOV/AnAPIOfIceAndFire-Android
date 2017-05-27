package xyz.rnovoselov.enterprise.aniceandfire.data.errors;

import xyz.rnovoselov.enterprise.aniceandfire.IceAndFireApplication;
import xyz.rnovoselov.enterprise.aniceandfire.R;

/**
 * Created by roman on 19.05.17.
 */

public class NoDownloadedDataErrors extends Throwable {
    public NoDownloadedDataErrors() {
        super(IceAndFireApplication.getAppComponent().getContext().getString(R.string.error_no_houses_downloaded));
    }
}