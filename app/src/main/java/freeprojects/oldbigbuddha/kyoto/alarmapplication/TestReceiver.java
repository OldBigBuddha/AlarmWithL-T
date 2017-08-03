package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * Created by lifeistech on 2017/08/03.
 */

public class TestReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder( context );

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Test")
                .setContentText( intent.getStringExtra("text") )
                .setSubText("Three Line")
                .setContentInfo("info")
                .setWhen( System.currentTimeMillis() );

        manager.notify(0, builder.build());
        Log.d("onReceive", "はいったお！！！！！！！！");

    }
}
