package edu.orangecoastcollege.cs273.caffeinefinder;

import android.location.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CaffeineDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView locationListNameTextView;
    private TextView locationListAddressTextView;
    private TextView locationListPhoneTextView;

    private Location caffeineLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caffeine_details);

        // get Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationListNameTextView = (TextView) findViewById(R.id.locationListNameTextView);
        locationListAddressTextView = (TextView) findViewById(R.id.locationListAddressTextView);
        locationListPhoneTextView = (TextView) findViewById(R.id.locationListPhoneTextView);

        // Get parcel from Intent
        caffeineLocation = getIntent().getExtras().getParcelable("selectedCaffeineLocation");

        locationListNameTextView.setText(caffeineLocation.getName());
        locationListAddressTextView.setText(caffeineLocation.getAddress()
                + ", " + caffeineLocation.getCity() + " "
                + caffeineLocation.getState() + " "
                + caffeineLocation.getZipCode());

        locationListPhoneTextView.setText(caffeineLocation.getPhone());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng caffeineStore = new LatLng(caffeineLocation.getLatitude(), caffeineLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(caffeineStore).title(caffeineLocation.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(caffeineStore, 14));

    }
}
