package com.example.jbus;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.jbus.model.Driver;
import com.example.jbus.model.LocationStops;
import com.example.jbus.model.admin;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferranceDrivers;
    private DatabaseReference LocmReferranceDrivers;
    private List<LocationStops> LocationArray ;
    private List<Driver> driversArray ;



    LocationManager locationManager;
    String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        Intent intent = getIntent();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        mDatabase= FirebaseDatabase.getInstance();
        mReferranceDrivers=mDatabase.getReference("driver");
        driversArray = new ArrayList<>();
        LocmReferranceDrivers=mDatabase.getReference("Location");
        LocationArray = new ArrayList<>();


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }



        // Add  markers


        //zoom to just :)
        float zoomLevel = 16.0f; //This goes up to 21
        //     mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(JUST, zoomLevel));

LocmReferranceDrivers.addChildEventListener(new ChildEventListener() {
    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        LocationStops locationStops=dataSnapshot.getValue(LocationStops.class);
        LocationArray.add(locationStops);

        for (LocationStops d1 : LocationArray)
        {
            LatLng stop = new LatLng(Double.parseDouble(d1.getLan()), (Double.parseDouble(d1.getLag())));
            mMap.addMarker(new MarkerOptions().position(stop).icon(BitmapDescriptorFactory.fromResource(R.drawable.rsz_marker)));


        }


    }

    /*          Sho3'l sara
    *
    *
    *
    *
    *
    * */






    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        LocationStops locationStops=dataSnapshot.getValue(LocationStops.class);
        LocationArray.add(locationStops);
        for (LocationStops d1 : LocationArray)
        {
            LatLng stop = new LatLng(Double.parseDouble(d1.getLan()), (Double.parseDouble(d1.getLag())));
            mMap.addMarker(new MarkerOptions().position(stop).icon(BitmapDescriptorFactory.fromResource(R.drawable.rsz_marker)));


        }
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

mReferranceDrivers.addChildEventListener(new ChildEventListener() {
    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Driver driver=dataSnapshot.getValue(Driver.class);
        driversArray.add(driver);
        Toast.makeText(MapsActivity.this, driversArray.get(0).getId(), Toast.LENGTH_SHORT).show();
        for (Driver d1 : driversArray)
        {
            if(d1.getOnline().equals("1"))
            {
                LatLng stop = new LatLng(Double.parseDouble(d1.getLan()), (Double.parseDouble(d1.getLag())));
                mMap.addMarker(new MarkerOptions().position(stop).icon(BitmapDescriptorFactory.fromResource(R.mipmap.flashing_arrow)));
            }



        }


    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        mMap.clear();
        LatLng driverStop;
        Driver driver=dataSnapshot.getValue(Driver.class);
        driversArray.add(driver);

        for (LocationStops d1 : LocationArray)
        {
            LatLng stop = new LatLng(Double.parseDouble(d1.getLan()), (Double.parseDouble(d1.getLag())));
            mMap.addMarker(new MarkerOptions().position(stop).icon(BitmapDescriptorFactory.fromResource(R.drawable.rsz_marker)));


        }

        Toast.makeText(MapsActivity.this, driversArray.get(0).getId(), Toast.LENGTH_SHORT).show();


        for (Driver d1 : driversArray)
        {
            if(d1.getOnline().equals("1"))
            {

                 driverStop = new LatLng(Double.parseDouble(d1.getLan()), (Double.parseDouble(d1.getLag())));
                  mMap.addMarker(new MarkerOptions().position(driverStop).icon(BitmapDescriptorFactory.fromResource(R.mipmap.flashing_arrow)));
            }
            if(d1.getOnline().equals("0"))
            {

                mMap.setIndoorEnabled(false);
            }

        }



        /* . ................ SHO3'L SARA .........................


                 @Override
                    public View getInfoWindow(Marker marker) {

                       // LayoutInflater inflater = getLayoutInflater();
                        //View myLayout = inflater.inflate(R.layout.info_window,null);

                       // View view=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE).Inflate(Resources.getSystem().getLayout(R.layout.))
                                return null;
                    }
                    Context context=getApplicationContext();
                    @Override
                    public View getInfoContents(Marker marker) {

                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View v =  inflater.inflate(R.layout.info_window, null);

                        LatLng latLng = marker.getPosition();
                        ImageView bmarker=(ImageView)v.findViewById(R.id.bmarker);
                        bmarker.setImageResource(R.drawable.blue_marker);
                        ImageView gmarker=(ImageView)v.findViewById(R.id.gmarker);
                        gmarker.setImageResource(R.drawable.green_marker);

                        TextView blue = (TextView) v.findViewById(R.id.blue);
                        TextView green = (TextView) v.findViewById(R.id.green);

                        String m="";
                        blue.setText(" ");
                        int d=SphericalUtil.computeDistanceBetween(latLng, driverMarker)/40;
                        if (d>1)m=" mins";else m=" min";
                        green.setText(d+m);
                        //green.setText(String.valueOf((int)(SphericalUtil.computeDistanceBetween(latLng, driverMarker)/40)));
                        return v;

                    }
                });
                 . ................ SHO3'L SARA .........................



        * */
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});
       // public final Marker addMarker (MarkerOptions options) ;



//
//        mReferranceDrivers.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                float zoomLevel = 16.0f;
//                Driver s = dataSnapshot.getValue(Driver.class);
//
//                LatLng driverMarker = new LatLng(Double.parseDouble(s.getLan()), (Double.parseDouble(s.getLag())));
//
//                mMap.clear();
//                mMap.addMarker(new MarkerOptions().position(driverMarker).title("driver 1"));
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(driverMarker, zoomLevel));
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        mMap.setMyLocationEnabled(true);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MapsActivity.this, "Permission denied to read your GPS", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}