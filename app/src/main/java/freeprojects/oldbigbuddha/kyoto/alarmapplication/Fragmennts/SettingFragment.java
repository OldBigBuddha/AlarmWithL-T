package freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragmennts;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.AlarmReceiver;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragmennts.Dialogs.DateDialogFragment;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragmennts.Dialogs.TimeDialogFragment;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.POJO.AlarmRealmData;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.R;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivityNewCreateBinding;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.FragmentSettingBinding;
import io.realm.Realm;

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
    private MarkerOptions mMarkerOptions;

    private boolean isLocation = true;
    private boolean isDate     = false;

    private Calendar mSchedule;

    private Realm mRealm;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_setting, container, false );
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSchedule = Calendar.getInstance();
        mBinding.tvDate.setText( formatDate() );
        mBinding.tvTime.setText( formatTime() );
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
        if (name != null) {
            mMarkerOptions.title(name);
        }
        mMarker = mGoogleMap.addMarker(mMarkerOptions);
        moveCamera();
    }

    private void markerRemove() {
        if (mMarker != null) {
            mMarker.remove();
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
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);

        CustomMapFragment mapFragment = (CustomMapFragment)getActivity().getFragmentManager().findFragmentById(R.id.google_map_fragment);
        mapFragment.setParent(mBinding.scroll);
        mapFragment.getMapAsync(this);

        mMarkerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

    }

    // Pass AlarmData to Activity
    public AlarmRealmData getAlarmData() {
        Log.d(TAG, "isData=" + isDate + ",isLocation=" + isLocation);
        if (!TextUtils.isEmpty(mBinding.etTitle.getText()) && !TextUtils.isEmpty(mBinding.etContext.getText())) { //Checking empty
            AlarmRealmData data = new AlarmRealmData(mBinding.etTitle.getText().toString(), mBinding.etContext.getText().toString(), System.currentTimeMillis());
            if (isLocation) {
                // TODO: 2017/08/06 Geofence
                Log.d(TAG, "Location is true");
            }
            if (isDate) {
                // Save when show Notification
                Log.d(TAG, "Data is true");
                data.setDate(mSchedule.getTime());

                mSchedule.set(Calendar.SECOND, 0);
                data.setDate(mSchedule.getTime()); // Set when show

                Context context = getActivity().getApplicationContext();
                Intent intent = new Intent(context, AlarmReceiver.class);
                // Put notification's data
                intent.putExtra("title", data.getTitle());
                intent.putExtra("content", data.getContent());
                // For cancel notification
                intent.setType(data.getMadeDate() + "");

                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

                // Debugging data when show Alarm
                Log.d("mSchedule", mSchedule.get(Calendar.YEAR) + "/" + (mSchedule.get(Calendar.MONTH) + 1) + "/" + mSchedule.get(Calendar.DAY_OF_MONTH));
                Log.d("mSchedule", mSchedule.get(Calendar.HOUR_OF_DAY) + ":" + mSchedule.get(Calendar.MINUTE));

                AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                manager.set(AlarmManager.RTC_WAKEUP, mSchedule.getTimeInMillis(), pendingIntent);
            }

            // Re-initialize EditTexts
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
