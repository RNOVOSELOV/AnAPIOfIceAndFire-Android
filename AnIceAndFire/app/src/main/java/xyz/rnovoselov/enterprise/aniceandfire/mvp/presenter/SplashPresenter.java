package xyz.rnovoselov.enterprise.aniceandfire.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import xyz.rnovoselov.enterprise.aniceandfire.mvp.view.ISplashView;

/**
 * Created by roman on 27.04.17.
 */

@InjectViewState
public class SplashPresenter extends MvpPresenter<ISplashView> {



    public boolean isSomeDataDownloaded () {
        return false;
    }
}
