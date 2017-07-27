package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by BigBuddha on 2017/07/27.
 */

public class AlarmRealmData extends RealmObject {

    @PrimaryKey
    private String alarmData;

    public AlarmRealmData(String alarmData) {
        setAlarmData(alarmData);
    }

    public void setAlarmData(String alarmData) {
        this.alarmData = alarmData;
    }

    public String getAlarmData() {
        return alarmData;
    }
}
