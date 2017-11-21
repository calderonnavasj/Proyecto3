package com.example.josue.p3;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener  {

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
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        cargaStilo(googleMap);

        // Set a listener for marker click.
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_menu_send))
                        .anchor(0.0f,1.0f).position(latLng));
            }
        });
    }

    public void datosSitio(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
        View vie = getLayoutInflater().inflate(R.layout.dialog_sitio, null);
        final EditText nombr = (EditText) vie.findViewById(R.id.ident);
        final EditText tel = (EditText) vie.findViewById(R.id.descripcion);
        Spinner miSpinner=(Spinner) vie.findViewById(R.id.spinner2);
        final EditText cat = (EditText) miSpinner.getSelectedItem().toString();
        Button button = (Button) vie.findViewById(R.id.button);
        builder.setView(vie);
        final AlertDialog dialog = builder.create();
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nombr.getText().toString().isEmpty() && !tel.getText().toString().isEmpty() && !cat.toString().isEmpty()) {
                    try {
                        ContactoOpenHelper helper= new ContactoOpenHelper(MapsActivity.this);
                        SQLiteDatabase database = helper.getWritableDatabase();
                        ContentValues values= new ContentValues();
                        values.put(SitioReader.Sitio.NOMBRE,nombr.getText().toString());
                        values.put(SitioReader.Sitio.CATEGORIA, cat.toString());
                        values.put(SitioReader.Sitio.TELEFONO,tel.getText().toString());
                        long insert = database.insert(SitioReader.Sitio.TABLE_NAME, null, values);
                        database.close();
                        Toast.makeText(Contactos.this, "El sitio " + nombr.getText() + " ha sido agregado exitosamente.", Toast.LENGTH_SHORT).show();
                        Refrescar();
                        dialog.dismiss();
                    } catch (Exception e) {
                        Toast.makeText(Contactos.this, "Ha ocurrido un error al agregar este dato. Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Contactos.this, "Rellene todos los campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void cargaStilo(GoogleMap googleMap){
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

    public boolean onMarkerClick(final Marker marker) {
        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();
        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }
        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }
}
