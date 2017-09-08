package freeprojects.oldbigbuddha.kyoto.alarmapplication.POJO;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by BigBuddha on 2017/07/28.
 */

public class AlarmRealmData extends RealmObject {

    @PrimaryKey
    private String geofenceId;

    private String content;
    private String title;
    private Date date;
    private boolean isLocation;

    private double latitude;
    private double longitude;

    public AlarmRealmData() {}

    public AlarmRealmData(String title, String context,String id) {
        this.title = title;
        this.content = context;
        geofenceId = id;
        date = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlarmRealmData that = (AlarmRealmData) o;

        if (geofenceId != that.geofenceId) return false;
        if (!title.equals(that.title)) return false;
        if (!content.equals(that.content)) return false;
        return date != null ? date.equals(that.date) : that.date == null;

    }

    @Override
    public String toString() {
        return "AlarmRealmData{" +
                "title = " + title +
                ", content = " + content +
                '}';
    }



    public String  getGeofenceId() {
        return geofenceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isLocation() {
        return isLocation;
    }

    public void setLocation(boolean location) {
        isLocation = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
