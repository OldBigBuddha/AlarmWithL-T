package freeprojects.oldbigbuddha.kyoto.alarmapplication.Receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.POJO.AlarmRealmData;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.R;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by BigBuddha on 2017/08/03.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        final String id = intent.getStringExtra("id");

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setContentInfo("Alarm")
                .setWhen(System.currentTimeMillis());

        manager.notify(0, builder.build());

        Realm realm = Realm.getDefaultInstance();
        AlarmRealmData deleteData = realm.where(AlarmRealmData.class).equalTo("geofenceId", id).findFirst();
        realm.beginTransaction();
        // FIXME: 2017/08/18 Error now
        deleteData.deleteFromRealm();
        realm.commitTransaction();


        Log.d("onReceive", "はいったお！！！！！！！！");
    }
}
