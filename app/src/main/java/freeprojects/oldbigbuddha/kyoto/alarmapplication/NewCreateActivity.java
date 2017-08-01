package freeprojects.oldbigbuddha.kyoto.alarmapplication;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.common.api.Status;
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

import permissions.dispatcher.RuntimePermissions;

//@RuntimePermissions
public class NewCreateActivity extends AppCompatActivity implements PlaceSelectionListener, OnMapReadyCallback {

    private GoogleMap googleMap;

    private Switch mSwitchData, mSwitchLocation;
    private ExpandableLayout mExpandableDate, mExpandableLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_create);

        initToolbar();

        mSwitchData = (Switch)findViewById(R.id.switch_data);
        mSwitchData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSwitchData.setChecked( isChecked );
                mExpandableDate.toggle();
            }
        });
        mExpandableDate = (ExpandableLayout)findViewById(R.id.expand_date);
        mExpandableDate.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableDate", "State: " + state);
            }
        });

        mSwitchLocation = (Switch)findViewById(R.id.switch_location);
        mSwitchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSwitchLocation.setChecked( isChecked );
                mExpandableLocation.toggle();
            }
        });
        mExpandableLocation = (ExpandableLayout)findViewById(R.id.expand_location);
        mExpandableLocation.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLocation", "State: " + state);
            }
        });


        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);

        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.google_map_fragment);
        mapFragment.getMapAsync(this);

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
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
}