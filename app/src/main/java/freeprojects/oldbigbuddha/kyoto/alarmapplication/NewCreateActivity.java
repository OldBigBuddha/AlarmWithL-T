package freeprojects.oldbigbuddha.kyoto.alarmapplication;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
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

public class NewCreateActivity extends AppCompatActivity implements PlaceSelectionListener, OnMapReadyCallback {

    private GoogleMap googleMap;

    private Switch mSwitchData, mSwitchLocation;
    private ExpandableLinearLayout mExpandableData, mExpandableLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_create);

        mSwitchData = (Switch)findViewById(R.id.switch_data);
        mSwitchLocation = (Switch)findViewById(R.id.switch_location);
        mExpandableData = (ExpandableLinearLayout)findViewById(R.id.expand_date_setting_linear);
        mExpandableLocation = (ExpandableLinearLayout)findViewById(R.id.expand_location_setting_linear);

        Log.d("mSwitchData", mSwitchData.isChecked() + "");
        Log.d("mSwitchLocation", mSwitchLocation.isChecked() + "");


        mSwitchData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("DataOnCheckedChange", isChecked + "");
//                changeStatus(buttonView, isChecked);
                if (isChecked) {
                    mExpandableData.expand();
                    mSwitchData.setChecked( true );
                } else {
                    mExpandableData.collapse();
                    mSwitchData.setChecked( false );
                }
                Log.d("DataOnCheckedChange", isChecked + "");
            }
        });

        mSwitchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("LocationOnCheckedChange", isChecked + "");
//                changeStatus(buttonView, isChecked);
                if (isChecked) {
                    mExpandableLocation.expand();
                    mSwitchLocation.setChecked( true );
                } else {
                    mExpandableLocation.collapse();
                    mSwitchLocation.setChecked( false );
                }
                Log.d("LocationOnCheckedChange", isChecked + "");
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

    public void changeStatus(CompoundButton button, boolean isChecked) {
        if (isChecked) {
            mExpandableData.collapse();
            button.setChecked( true );
        } else {
            mExpandableData.expand();
            button.setChecked( false );
        }
    }
}