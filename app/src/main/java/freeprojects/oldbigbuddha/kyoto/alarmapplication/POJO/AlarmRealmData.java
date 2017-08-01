package freeprojects.oldbigbuddha.kyoto.alarmapplication.POJO;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by BigBuddha on 2017/07/28.
 */

public class AlarmRealmData extends RealmObject {

    @PrimaryKey
    private String alarmData;


    public String getAlarmData() {
        return alarmData;
    }

    public void updateAlarmData(String alarmData) {
        this.alarmData = alarmData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlarmRealmData)) return false;

        AlarmRealmData that = (AlarmRealmData) o;

        return alarmData.equals(that.alarmData);

    }

    @Override
    public int hashCode() {
        return alarmData.hashCode();
    }

    @Override
    public String toString() {
        return "AlarmRealmData{" +
                "alarmData='" + alarmData + '\'' +
                '}';
    }
}
