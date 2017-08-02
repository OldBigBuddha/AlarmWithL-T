package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by lifeistech on 2017/08/02.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init( getApplicationContext() );
    }
}
