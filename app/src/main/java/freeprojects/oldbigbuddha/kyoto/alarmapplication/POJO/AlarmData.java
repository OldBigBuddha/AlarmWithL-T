package freeprojects.oldbigbuddha.kyoto.alarmapplication.POJO;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

/**
 * Created by BigBuddha on 2017/07/28.
 */

public class AlarmData {

    String title;
    String content;
    LatLng location;
    Calendar calendar;

    public AlarmData(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlarmData)) return false;

        AlarmData alarmData = (AlarmData) o;

        if (!title.equals(alarmData.title)) return false;
        if (!content.equals(alarmData.content)) return false;
        if (location != null ? !location.equals(alarmData.location) : alarmData.location != null)
            return false;
        return calendar != null ? calendar.equals(alarmData.calendar) : alarmData.calendar == null;

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + content.hashCode();
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (calendar != null ? calendar.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AlarmData{" +
                "title='" + title + '\'' +
                '}';
    }
}
