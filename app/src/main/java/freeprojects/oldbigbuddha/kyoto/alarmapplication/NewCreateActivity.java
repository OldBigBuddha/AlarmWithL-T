package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragmennts.Dialogs.DateDialogFragment;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragmennts.Dialogs.TimeDialogFragment;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.POJO.AlarmRealmData;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivityNewCreateBinding;
import io.realm.Realm;

//@RuntimePermissions
public class NewCreateActivity extends AppCompatActivity implements PlaceSelectionListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String TAG = "NewCreateActivity";

    ActivityNewCreateBinding mBinding;

    private GoogleMap googleMap;

    private boolean isLocation = true;
    private boolean isDate     = false;

    private String mTitle, mContent;
    private Calendar mSchedule;
    private Geofence mGeofence;

    private Realm mRealm;

    private Toolbar mToolbar;

    private GoogleApiClient mClient;

    private CompoundButton.OnCheckedChangeListener dataListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mBinding.switchDate.setChecked(isChecked);
            isDate = !isDate;
            mBinding.expandDate.toggle();
        }
    };

    private CompoundButton.OnCheckedChangeListener localeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mBinding.switchLocation.setChecked( isChecked );
            isLocation = !isLocation;
            mBinding.expandLocation.toggle();
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
            fragment.show(getSupportFragmentManager(), "DataDialogFragment");
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
            fragment.show(getSupportFragmentManager(), "TimeDialogFragment");
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_create);

        initToolbar();
        initOnClick();
//        initClient();
        initMap();

        mRealm = Realm.getDefaultInstance();
        mTitle = "";
        mContent = "";
        mSchedule = Calendar.getInstance();
        mGeofence = null;

        mBinding.tvDate.setText(formatDate());
        mBinding.tvTime.setText(formatTime());

//        mClient.connect();
    }

    @Override
    public void onPlaceSelected(Place place) {
        String name = place.getName().toString();
        LatLng latLng = place.getLatLng();
        Log.i("LocationInfo", name + "\n" + latLng.toString());

        MarkerOptions marker = new MarkerOptions();
        marker.position(latLng);
        marker.title(name);
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        googleMap.addMarker(marker);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
    }

    @Override
    public void onError(Status status) {
        Log.i("Error", status.toString());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_create_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title, context;
        View parent = mBinding.parentLayout;
        switch (item.getItemId()) {
            case R.id.create_menu: {
                if (!TextUtils.isEmpty(mBinding.etTitle.getText()) && !TextUtils.isEmpty(mBinding.etContext.getText())) {
                    title = mBinding.etTitle.getText().toString();
                    context = mBinding.etContext.getText().toString();

                    AlarmRealmData data = new AlarmRealmData(title, context, System.currentTimeMillis());
                    if (isDate) {
                        mSchedule.set(Calendar.SECOND, 0);
                        data.setDate(mSchedule.getTime());

                        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                        intent.putExtra("title", data.getTitle());
                        intent.putExtra("content", data.getContent());
                        intent.setType(data.getMadeDate() + "");

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                        Log.d("mSchedule", mSchedule.get(Calendar.YEAR) + "/" + mSchedule.get(Calendar.MONTH) + "/" + mSchedule.get(Calendar.DAY_OF_MONTH));
                        Log.d("mSchedule", mSchedule.get(Calendar.HOUR_OF_DAY) + ":" + mSchedule.get(Calendar.MINUTE));

                        AlarmManager manager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                        manager.set(AlarmManager.RTC_WAKEUP, mSchedule.getTimeInMillis(), pendingIntent);
                        Log.d(TAG, "case Date");
                    }

                    mRealm.beginTransaction();
                    mRealm.copyToRealm(data);
                    mRealm.commitTransaction();

                    mBinding.etTitle.setText("");
                    mBinding.etContext.setText("");

                    onBackPressed();
                } else if (TextUtils.isEmpty(mBinding.etTitle.getText())) {
                    Snackbar snackbar = Snackbar.make(parent, getString(R.string.message_error_null_title), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else if (TextUtils.isEmpty(mBinding.etContext.getText())) {
                    Snackbar snackbar = Snackbar.make(parent, getString(R.string.message_error_null_context), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                break;
            }
            default: {
                onBackPressed();
            }
        }
        return true;
    }

    public void initOnClick() {
        mBinding.switchDate.setOnCheckedChangeListener(dataListener);
        mBinding.switchLocation.setOnCheckedChangeListener(localeListener);
        mBinding.btAddDate.setOnClickListener(onAddSchedule);
        mBinding.btAddTime.setOnClickListener(onAddTime);
    }

    public String formatDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(mSchedule.getTime());
    }

    public String formatTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(mSchedule.getTime());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("OnConnected", "start");
        mGeofence = new Geofence.Builder()
                .setRequestId(mTitle)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_EXIT)
                .setCircularRegion(34.706682, 135.500974, 10)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build();

        List<Geofence> geofences = new ArrayList<>();
        geofences.add(mGeofence);

        Intent intent = new Intent(getApplicationContext(), TestReceiver.class);
        PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            return;
        }
        LocationServices.GeofencingApi.addGeofences(mClient, geofences, pending);
        Log.d("OnConnected", "finish");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("onConnectionSuspended", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("onConnectionFailed", connectionResult.getErrorMessage()+"");
    }

    @Override
    public void onLocationChanged(Location location) {
        String locationData = "{\"緯度\"=\"" + location.getLatitude() + "\",\"軽度\"" + location.getLongitude() + "}";
        Log.d("onLocationChanged", locationData);
    }

    @Override
    protected void onResume() {
        if (mClient != null) {
            mClient.connect();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mClient != null) {
            mClient.disconnect();
        }
        super.onPause();
    }

    public void initClient() {
        mClient = new GoogleApiClient.Builder( getApplicationContext() )
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    public void initMap() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);

        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.google_map_fragment);
        mapFragment.getMapAsync(this);

    }

 }