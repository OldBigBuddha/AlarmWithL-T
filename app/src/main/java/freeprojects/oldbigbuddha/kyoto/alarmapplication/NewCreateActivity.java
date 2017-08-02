package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import java.util.Calendar;
import java.util.Date;

import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
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

import net.cachapa.expandablelayout.ExpandableLayout;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.POJO.AlarmRealmData;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivityNewCreateBinding;
import io.realm.Realm;

//@RuntimePermissions
public class NewCreateActivity extends AppCompatActivity implements PlaceSelectionListener, OnMapReadyCallback {

    private static final String TAG = "NewCreateActivity";

    ActivityNewCreateBinding mBinding;

    private GoogleMap googleMap;

    private String mTitle, mContent;
    private Date mSchedule;
    private Geofence mGeofence;

    private Realm mRealm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_create);
        mBinding.setVm( new NewCreateViewModel(mBinding));

        initToolbar();

        mBinding.expandDate.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableDate", "State: " + state);
            }
        });

        mBinding.expandLocation.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLocation", "State: " + state);
            }
        });


        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);

        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.google_map_fragment);
        mapFragment.getMapAsync(this);

        mRealm = Realm.getDefaultInstance();
        mTitle = "";
        mContent = "";
        mSchedule = null;
        mGeofence = null;

        Log.d(TAG,"onCreate");

    }

    @Override
    public void onPlaceSelected(Place place) {
        String name = place.getName().toString();
        LatLng latLng = place.getLatLng();
        Log.i("LocationInfo", name + "\n" + latLng.toString());

        MarkerOptions marker = new MarkerOptions();
        marker.position(latLng);
        marker.title(name);
        marker.icon(BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_GREEN ));
        googleMap.addMarker( marker );

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom( latLng, 17 ));
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
        Toolbar toolbar = mBinding.toolBar;
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
                    AlarmRealmData data = new AlarmRealmData(title, context);
                    if (mSchedule != null) data.setDate(mSchedule);
                    saveData(data);
                    mBinding.etTitle.setText("");
                    mBinding.etContext.setText("");
                    onBackPressed();
                } else if (TextUtils.isEmpty(mBinding.etTitle.getText())) {
                    Snackbar snackbar = Snackbar.make(parent, getString(R.string.message_error_null_title) ,Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else if (TextUtils.isEmpty(mBinding.etContext.getText())) {
                    Snackbar snackbar = Snackbar.make(parent, getString(R.string.message_error_null_context) ,Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                break;
            }
            default: {
                onBackPressed();
            }
        }
        Log.d(TAG, "Selected Menu Item");
        return true;
    }

    public void saveData(AlarmRealmData data) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(data);
        Log.d("Realm", "saved");
        Log.d("toString", data.toString());
        mRealm.commitTransaction();
    }
}