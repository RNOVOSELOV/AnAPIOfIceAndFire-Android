package xyz.rnovoselov.enterprise.aniceandfire.data.errors;

import xyz.rnovoselov.enterprise.aniceandfire.IceAndFireApplication;
import xyz.rnovoselov.enterprise.aniceandfire.R;

/**
 * Created by roman on 05.05.17.
 */

public class NetworkAvailableError extends AbstractApplicationError {
    public NetworkAvailableError() {
        super(IceAndFireApplication.getAppComponent().getContext().getString(R.string.error_net_unavailable));
    }
}
