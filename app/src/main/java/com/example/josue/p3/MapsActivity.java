package com.example.josue.p3;

import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.widget.LinearLayout;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        switch (EstiloMapa.estilo){
            case "standard":
                MapStyleOptions style1 = MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.standard);
                googleMap.setMapStyle(style1);
                break;
            case "dark":
                MapStyleOptions style2 = MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.dark);
                googleMap.setMapStyle(style2);
                break;
            case "night":
                MapStyleOptions style3 = MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.night);
                googleMap.setMapStyle(style3);
                break;
            case "retro":
                MapStyleOptions style4 = MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.retro);
                googleMap.setMapStyle(style4);
                break;
            case "silver":
                MapStyleOptions style5 = MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.silver);
                googleMap.setMapStyle(style5);
                break;
            case "aubergine":
                MapStyleOptions style6 = MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.aubergine);
                googleMap.setMapStyle(style6);
                break;
        }


    }
}
