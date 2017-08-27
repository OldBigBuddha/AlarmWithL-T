package freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragmennts;


import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.MainActivity;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.Receivers.AlarmReceiver;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragmennts.Dialogs.DateDialogFragment;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragmennts.Dialogs.TimeDialogFragment;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.POJO.AlarmRealmData;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.R;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.FragmentSettingBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements PlaceSelectionListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public FragmentSettingBinding mBinding;

    private final String TAG = getClass().getSimpleName();

    private GoogleMap mGoogleMap;
    private LatLng mTargetLocation;

    private Marker mMarker;
    private Circle mCircle;
    private MarkerOptions mMarkerOptions;
    private CircleOptions mCircleOptions;

    private boolean isLocation = true;
    private boolean isDate = false;

    private AlarmRealmData mData;
    private Calendar mSchedule;

    private GoogleApiClient mClient;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSchedule = Calendar.getInstance();
        mClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("config", "onCreateView");
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        if (getArguments() != null) {
            Gson gson = new Gson();
            mData = gson.fromJson(getArguments().getString("data"), AlarmRealmData.class);
            mSchedule.setTime(mData.getDate());
        } else {
            mData = null;
        }
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mData != null) {
            mBinding.etTitle.setText(mData.getTitle());
            mBinding.etContext.setText(mData.getContent());
        }

        mBinding.tvDate.setText(formatDate());
        mBinding.tvTime.setText(formatTime());
        initMap();
        initOnClick();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onPlaceSelected(Place place) {
        String name = place.getName().toString();
        mTargetLocation = place.getLatLng();
        Log.i("LocationInfo", name + "\n" + mTargetLocation.toString());
        makeMarker(name);
    }

    @Override
    public void onError(Status status) {
        Log.e("Error", status.toString());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mTargetLocation = latLng;
                makeMarker(null);
            }
        });
    }

    private void makeMarker(String name) {
        markerRemove();
        mMarkerOptions.position(mTargetLocation);
        mCircleOptions.center(mTargetLocation).radius(100);
        if (name != null) {
            mMarkerOptions.title(name);
        }
        mMarker = mGoogleMap.addMarker(mMarkerOptions);
        mCircle = mGoogleMap.addCircle(mCircleOptions);
        moveCamera();
    }

    private void markerRemove() {
        if (mMarker != null) {
            mMarker.remove();
            mCircle.remove();
        }
    }

    private void moveCamera() {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mTargetLocation, 17));
    }

    private void initOnClick() {
        mBinding.switchDate.setOnCheckedChangeListener(dataListener);
        mBinding.switchLocation.setOnCheckedChangeListener(localeListener);
        mBinding.btAddDate.setOnClickListener(onAddSchedule);
        mBinding.btAddTime.setOnClickListener(onAddTime);
    }


    // Initialize Google Map
    private void initMap() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);

        CustomMapFragment mapFragment = (CustomMapFragment) getActivity().getFragmentManager().findFragmentById(R.id.google_map_fragment);
        mapFragment.setParent(mBinding.scroll);
        mapFragment.getMapAsync(this);

        mMarkerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mCircleOptions = new CircleOptions().fillColor(Color.argb(97, 93, 185, 139)).strokeColor(Color.argb(200, 93, 185, 139));

    }

    // Pass AlarmData to Activity
    public AlarmRealmData getAlarmData() {
        Log.d(TAG, "isData=" + isDate + ",isLocation=" + isLocation);
        if (!TextUtils.isEmpty(mBinding.etTitle.getText()) && !TextUtils.isEmpty(mBinding.etContext.getText())) { //Checking empty
            AlarmRealmData data = new AlarmRealmData(mBinding.etTitle.getText().toString(), mBinding.etContext.getText().toString(), System.currentTimeMillis() + "");
            if (isLocation) {
                Log.d(TAG, "Location is true");
                // Check Permission
                if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return null;
                }

                LocationServices.GeofencingApi.addGeofences(mClient, settingGeofence(data), settingAlarm(data));
                Log.i("RegistrationLocation", "Lat = " + mTargetLocation.latitude + ", Lng = " + mTargetLocation.longitude );
            }
            if (isDate) {
                Log.d(TAG, "Data is true");

                mSchedule.set(Calendar.SECOND, 0);
                data.setDate(mSchedule.getTime()); // Set when show

                // Debugging data when show Alarm
                Log.i("mSchedule", mSchedule.get(Calendar.YEAR) + "/" + (mSchedule.get(Calendar.MONTH) + 1) + "/" + mSchedule.get(Calendar.DAY_OF_MONTH));
                Log.i("mSchedule", mSchedule.get(Calendar.HOUR_OF_DAY) + ":" + mSchedule.get(Calendar.MINUTE));

                AlarmManager manager = (AlarmManager)getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                manager.set(AlarmManager.RTC_WAKEUP, mSchedule.getTimeInMillis(), settingAlarm(data) );
            }

            // Re-initialize EditText Field
            mBinding.etTitle.setText("");
            mBinding.etContext.setText("");
            return data;

        } else if (TextUtils.isEmpty(mBinding.etTitle.getText())) {
            Snackbar snackbar = Snackbar.make(mBinding.parent, getString(R.string.message_error_null_title), Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else if (TextUtils.isEmpty(mBinding.etContext.getText())) {
            Snackbar snackbar = Snackbar.make(mBinding.parent, getString(R.string.message_error_null_context), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        return null;
    }

    // Setting Geofence
    public GeofencingRequest settingGeofence(AlarmRealmData data) {
        Geofence geofence = new Geofence.Builder()
                .setRequestId(data.getGeofenceId())
                .setCircularRegion(mTargetLocation.latitude, mTargetLocation.longitude, 1000)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .build();

        GeofencingRequest request = new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER )
                .addGeofence(geofence)
                .build();
        return request;
    }

    // Setting Alarm
    public PendingIntent settingAlarm(AlarmRealmData data) {
        Context context = getActivity().getApplicationContext();
        Intent intent = new Intent(context, AlarmReceiver.class);
        // Put notification's data
        intent.putExtra("title", data.getTitle());
        intent.putExtra("content", data.getContent());
        intent.putExtra("id", data.getGeofenceId());
        // For cancel notification
        intent.setType(data.getGeofenceId());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        return pendingIntent;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mClient != null) {
            mClient.connect();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mClient != null) {
            mClient.disconnect();
        }
    }

    // To set a date
    private String formatDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(mSchedule.getTime());
    }
    private String formatTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(mSchedule.getTime());
    }

        private CompoundButton.OnCheckedChangeListener dataListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mBinding.switchDate.setChecked(isChecked);
            mBinding.expandDate.toggle();
            isDate = !isChecked;
            if (!mBinding.switchLocation.isChecked() && !isChecked) {
                mBinding.switchLocation.setChecked(true);
                mBinding.expandLocation.expand();
                isLocation = !isChecked;
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener localeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mBinding.switchLocation.setChecked(isChecked);
            mBinding.expandLocation.toggle();
            isLocation = isChecked;
            if (!mBinding.switchDate.isChecked() && !isChecked) {
                mBinding.switchDate.setChecked(true);
                mBinding.expandDate.expand();
                isDate = !isChecked;
            }
        }
    };

    private View.OnClickListener onAddSchedule = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DateDialogFragment fragment = DateDialogFragment.newInstance();
            fragment.setOnDataFragmentListener(new DateDialogFragment.OnDataDialogFragmentListener() {
                @Override
                public void onClickPositive(int year, int month, int day) {
                    mSchedule.set(year, month, day);
                    mBinding.tvDate.setText(formatDate());
                }
            });
            fragment.show(getActivity().getSupportFragmentManager(), "DataDialogFragment");
        }
    };

    private View.OnClickListener onAddTime = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TimeDialogFragment fragment = TimeDialogFragment.newInstance();
            fragment.setOnTimeDialogFragmentListener(new TimeDialogFragment.OnTimeDialogFragmentListener() {
                @Override
                public void onClickPositive(int hour, int minute) {
                    mSchedule.set(Calendar.HOUR_OF_DAY, hour);
                    mSchedule.set(Calendar.MINUTE, minute);
                    mBinding.tvTime.setText(formatTime());
                }
            });
            fragment.show(getActivity().getSupportFragmentManager(), "TimeDialogFragment");
        }
    };

}
