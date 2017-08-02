package freeprojects.oldbigbuddha.kyoto.alarmapplication.POJO;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by BigBuddha on 2017/07/28.
 */

public class AlarmRealmData extends RealmObject {

    private int geofenceId;
    private String title;
    private String context;
    private Date date;

    public AlarmRealmData() {}

    public AlarmRealmData(String title, String context) {
        this.title = title;
        this.context = context;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlarmRealmData that = (AlarmRealmData) o;

        if (geofenceId != that.geofenceId) return false;
        if (!title.equals(that.title)) return false;
        if (!context.equals(that.context)) return false;
        return date != null ? date.equals(that.date) : that.date == null;

    }

    @Override
    public String toString() {
        return "AlarmRealmData{" +
                "title='" + title + '\'' +
                ", context='" + context + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int result = geofenceId;
        result = 31 * result + title.hashCode();
        result = 31 * result + context.hashCode();
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    public int getGeofenceId() {
        return geofenceId;
    }

    public void setGeofenceId(int geofenceId) {
        this.geofenceId = geofenceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
