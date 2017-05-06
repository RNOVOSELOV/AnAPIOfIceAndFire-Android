package xyz.rnovoselov.enterprise.aniceandfire.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import io.reactivex.Observable;
import xyz.rnovoselov.enterprise.aniceandfire.IceAndFireApplication;

/**
 * Created by roman on 05.05.17.
 */

public class NetworkStatusChecker {
    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = ((ConnectivityManager) IceAndFireApplication.getAppComponent().getContext().getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static Observable<Boolean> isInternetAvailable () {
        return Observable.just(isNetworkAvailable());
    }
}
