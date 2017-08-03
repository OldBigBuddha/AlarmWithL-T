package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

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

        builder.setSmallIcon(R.mipmap.ic_launcher_round)
//                .setContentTitle(title)
                .setContentTitle("LATE")
//                .setContentText(content)
                .setContentText("Location And LatE")
                .setContentInfo("Alarm")
                .setWhen(System.currentTimeMillis());

        manager.notify(0, builder.build());
    }
}
