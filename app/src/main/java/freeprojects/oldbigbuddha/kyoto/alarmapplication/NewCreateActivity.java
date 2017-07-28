package freeprojects.oldbigbuddha.kyoto.alarmapplication;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_create);



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

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom( latLng, 11 ));
    }

    @Override
    public void onError(Status status) {
        Log.i("Error", status.toString());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}