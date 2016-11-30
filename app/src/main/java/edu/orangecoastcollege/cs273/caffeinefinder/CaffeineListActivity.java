package edu.orangecoastcollege.cs273.caffeinefinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class CaffeineListActivity extends AppCompatActivity implements OnMapReadyCallback{

    private DBHelper db;
    private List<Location> allLocationsList;
    private ListView locationsListView;
    private LocationListAdapter locationListAdapter;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caffeine_list);

        deleteDatabase(DBHelper.DATABASE_NAME);
        db = new DBHelper(this);
        db.importLocationsFromCSV("locations.csv");

        allLocationsList = db.getAllCaffeineLocations();
        locationsListView = (ListView) findViewById(R.id.locationsListView);
        locationListAdapter = new LocationListAdapter(this, R.layout.location_list_item, allLocationsList);
        locationsListView.setAdapter(locationListAdapter);

        // Hook up our support map frgment to this activity
        SupportMapFragment caffeinMapFragment = (SupportMapFragment) getSupportFragmentManager().
                                                    findFragmentById(R.id.caffeinMapsFragment);
        caffeinMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Use the latitude and longitude for each Location to create a Marker on the GoogleMaps
        mMap = googleMap;
        for(Location location : allLocationsList)
        {
            LatLng coordinate = new LatLng(location.getLatitude(), location.getLongitude());
            // Add a marker at that coordinate
            mMap.addMarker(new MarkerOptions().position(coordinate).title(location.getName()));
        }

        // change the camera view to our current position:
        LatLng currentPosition = new LatLng(33.671025, -117.911305);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(currentPosition).zoom(14).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.moveCamera(cameraUpdate);


    }
}
