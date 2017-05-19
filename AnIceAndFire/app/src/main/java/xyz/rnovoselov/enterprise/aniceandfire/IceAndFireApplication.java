package xyz.rnovoselov.enterprise.aniceandfire;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import xyz.rnovoselov.enterprise.aniceandfire.di.components.AppComponent;
import xyz.rnovoselov.enterprise.aniceandfire.di.components.DaggerAppComponent;
import xyz.rnovoselov.enterprise.aniceandfire.di.modules.AppModule;

/**
 * Created by roman on 27.04.17.
 */

public class IceAndFireApplication extends Application {

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                .build());

        createComponent();
    }

    private void createComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }
}
