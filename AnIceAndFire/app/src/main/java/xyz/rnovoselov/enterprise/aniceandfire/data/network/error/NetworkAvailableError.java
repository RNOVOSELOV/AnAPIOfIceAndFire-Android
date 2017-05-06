package xyz.rnovoselov.enterprise.aniceandfire.data.network.error;

import xyz.rnovoselov.enterprise.aniceandfire.IceAndFireApplication;
import xyz.rnovoselov.enterprise.aniceandfire.R;

/**
 * Created by roman on 05.05.17.
 */

public class NetworkAvailableError extends Throwable {
    public NetworkAvailableError() {
        super(IceAndFireApplication.getAppComponent().getContext().getString(R.string.error_net_unavailable));
    }
}
