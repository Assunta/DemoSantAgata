package com.oropallo.assunta.demosantagata;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.appolica.interactiveinfowindow.InfoWindow;
import com.appolica.interactiveinfowindow.InfoWindowManager;
import com.appolica.interactiveinfowindow.customview.TouchInterceptFrameLayout;
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {
    private static final String RECYCLER_VIEW = "RECYCLER_VIEW_MARKER";
    private static final String FORM_VIEW = "FORM_VIEW_MARKER";
    private final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 0;
    private MapView mapView;

    private InfoWindowManager infoWindowManager;

    private InfoWindow recyclerWindow;
    private InfoWindow formWindow;

    private Location mLastLocation;
    private Marker mCurrLocationMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        final TouchInterceptFrameLayout mapViewContainer = (TouchInterceptFrameLayout) findViewById(R.id.mapViewContainer);

        mapView.getMapAsync(this);

        infoWindowManager = new InfoWindowManager(getSupportFragmentManager());
        infoWindowManager.onParentViewCreated(mapViewContainer, savedInstanceState);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.maps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        InfoWindow infoWindow = null;
        switch (marker.getSnippet()) {
            case RECYCLER_VIEW:
                infoWindow = recyclerWindow;
                break;
            case FORM_VIEW:
                infoWindow = formWindow;
                break;
        }

        if (infoWindow != null) {
            infoWindowManager.toggle(infoWindow, true);
        }

        return true;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        LatLng coordinates = new LatLng(41.089440, 14.504100);
//        googleMap.addMarker(new MarkerOptions().position(coordinates).title(match.match.LocationAddress));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 20));
        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_ACCESS_FINE_LOCATION);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        else{
            googleMap.setMyLocationEnabled(true);
           //TODO settare il marker per la posizione attuale
        }


        mapView.onResume();


        infoWindowManager.onMapReady(googleMap);

        final Marker marker1 = googleMap.addMarker(new MarkerOptions().position(new LatLng(41.089526, 14.504013)).snippet(RECYCLER_VIEW));
        final Marker marker2 = googleMap.addMarker(new MarkerOptions().position(new LatLng(41.090010, 14.503754)).snippet(FORM_VIEW));

        final int offsetX = (int) getResources().getDimension(R.dimen.marker_offset_x);
        final int offsetY = (int) getResources().getDimension(R.dimen.marker_offset_y);

        final InfoWindow.MarkerSpecification markerSpec =
                new InfoWindow.MarkerSpecification(offsetX, offsetY);

        recyclerWindow = new InfoWindow(marker1, markerSpec, new InfoFragment());
        formWindow = new InfoWindow(marker2, markerSpec, new InfoFragment());

        googleMap.setOnMarkerClickListener(this);
    }

//    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_ACCESS_FINE_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                } else {
//
//                }
//                break;
//            }
//        }
//    }
}
