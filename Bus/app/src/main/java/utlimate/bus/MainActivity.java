package utlimate.bus;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerDragListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSION_LOCATION = 34839;
    private static final String GEOFENCE_ID = TAG;
    private static final String KEY_POINT = "key_points";
    List<String> locationList,locationList2;
    private GoogleMap mMap;
    private List<Marker> mMarkers = new ArrayList<>();
    private List<LatLng> mLatLngList = new ArrayList<>();
    private Polygon mPolygon;
    private Marker mCenterMarker;
    private Circle mCircle;
    public static boolean isInGeoFence=false;
    private GoogleApiClient mGoogleApiClient;
    ProgressBar progressBar;
    String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar=findViewById(R.id.progressbar);



        //call for fetching from database

        fetchLocationFromDb();
//        SupportMapFragment mapFragment =
//                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = createGoogleApiClient();
        }
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
        if (savedInstanceState != null) {
            mLatLngList = savedInstanceState.getParcelableArrayList(KEY_POINT);
        }
    }


    private void fetchLocationFromDb() {
//        FetchLocationTask backgroundTask = new FetchLocationTask(this);
//        backgroundTask.execute();

        //progressBar.setVisibility(View.VISIBLE);


       // Toast.makeText(getApplicationContext(),locationList2.get(0),Toast.LENGTH_LONG).show();


        new FetchLocationTask() {

            @Override
            public void onPostExecute(String result) {

                progressBar.setVisibility(View.GONE);
                System.out.println("fetch result="+result);
                if (getResponseCode() == 200) {

                    //fetching the data from php array which is sent as jason object

                    System.out.println("fetch result="+result);
                    result= result.replaceAll("\\[", "").replaceAll("\\]","");
                    result=result.replace("\"", "");
                    locationList = Arrays.asList(result.split(","));
                    Log.e("response", "contents = " + result);
                    SupportMapFragment mapFragment =
                            (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(MainActivity.this);

                } else {
                    Toast.makeText(MainActivity.this, "Failed to load", Toast.LENGTH_LONG).show();
                }


            }
        }.execute("https://afshanshk17.000webhostapp.com/geofence.php");

//this URL connects to database


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                } else {
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ArrayList<LatLng> points = convertMarkersToLatlng(mMarkers);
        outState.putParcelableArrayList(KEY_POINT, points);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, GeofenceEditorActivity.class));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
         mMap.setOnMapLongClickListener(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSION_LOCATION);
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }
        if (mLatLngList != null) {
            for (LatLng latLng : mLatLngList) {
                mMarkers.add(mMap.addMarker(new MarkerOptions().position(latLng).draggable(true)));
            }
            redraw();
        }
        drawPolygon();

    }
    private void drawPolygon() {


        res = getIntent().getExtras().getString("result");

        res= res.replaceAll("\\[", "").replaceAll("\\]","");
        res=res.replace("\"", "");
        locationList2 = Arrays.asList(res.split(","));

        //Getting the four co-ordinates to plot the geo-fence

        LatLng point1 = new LatLng( Double.parseDouble(locationList2.get(0)),Double.parseDouble(locationList2.get(1)));
        mMarkers.add(mMap.addMarker(new MarkerOptions().position(point1)));

        LatLng point2 =new LatLng(Double.parseDouble(locationList2.get(2)),Double.parseDouble(locationList2.get(3)));
        mMarkers.add(mMap.addMarker(new MarkerOptions().position(point2)));

        LatLng point3=new LatLng( Double.parseDouble(locationList2.get(4)),Double.parseDouble(locationList2.get(5)));
        mMarkers.add(mMap.addMarker(new MarkerOptions().position(point3)));

        LatLng point4 =new LatLng( Double.parseDouble(locationList2.get(6)),Double.parseDouble(locationList2.get(7)));
        mMarkers.add(mMap.addMarker(new MarkerOptions().position(point4)));

        PolygonOptions options = new PolygonOptions();
        options.add( point1, point2, point3 ,point4);

        options.fillColor( getResources()
                .getColor( R.color.fill_color ) );
        options.strokeColor( getResources()
                .getColor( R.color.stroke_color ) );
        options.strokeWidth( 2 );


        CameraPosition position = CameraPosition.builder().target( point1 ).zoom( 18f ).bearing( 0.0f ).tilt( 0.0f ).build();
        mMap.addPolygon( options );
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
        List<LatLng> latLngs = new ArrayList<>();
        latLngs.add(point1);
        latLngs.add(point2);
        latLngs.add(point3);
        latLngs.add(point4);

        Log.i(TAG, "computeArea " + SphericalUtil.computeArea(latLngs));
//        mMap.setOnMarkerDragListener(this);
        update();
    }

   @Override
    public void onMapLongClick(LatLng latLng) {
        mMarkers.add(mMap.addMarker(new MarkerOptions().position(latLng).draggable(true)));
        mMap.setOnMarkerDragListener(this);
        update();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        update();
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected()");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended()");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed()");
    }

    @Override
    public void onResult(@NonNull Result result) {
        Log.d(TAG, "onResult()");
    }

    private void update() {
        if (mMarkers.size() <= 2) {
            return;
        }
        redraw();
        refreshGeofences();
    }

    private void redraw() {
        if (mMarkers.size() <= 2) {
            return;
        }
        if (mPolygon != null) {
            mPolygon.remove();
        }
        mPolygon = mMap.addPolygon(new PolygonOptions().addAll(convertMarkersToLatlng(mMarkers))
                .strokeColor(Color.RED).fillColor(Color.BLUE));

        if (mCircle != null) {
            mCircle.remove();
        }
        mCircle = mMap.addCircle(getCircularFence());

        if (mCenterMarker != null) {
            mCenterMarker.remove();
        }
//        mCenterMarker = mMap.addMarker(new MarkerOptions().position(mCircle.getCenter()));
    }

    private CircleOptions getCircularFence() {
        double latMin = 180;
        double latMax = -180;
        double longMin = 180;
        double longMax = -180;

        for (Marker marker : mMarkers) {
            if (marker.getPosition().latitude < latMin) {
                latMin = marker.getPosition().latitude;
            }
            if (marker.getPosition().latitude > latMax) {
                latMax = marker.getPosition().latitude;
            }
            if (marker.getPosition().longitude < longMin) {
                longMin = marker.getPosition().longitude;
            }
            if (marker.getPosition().longitude > longMax) {
                longMax = marker.getPosition().longitude;
            }
        }
        double latMid = (latMin + latMax) / 2;
        double longMid = (longMin + longMax) / 2;
        float[] result = new float[1];
        Location.distanceBetween(latMid, longMid, latMax, longMax, result);
        double radius = result[0];
        return new CircleOptions()
                .center(new LatLng(latMid, longMid))
                .radius(radius)
                .strokeColor(Color.GREEN);
    }

    private GoogleApiClient createGoogleApiClient() {
        return new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void refreshGeofences() {
        removeGeofences();
        List<Geofence> geofences = new ArrayList<>();
        Geofence geofence = new Geofence.Builder()
                .setRequestId(GEOFENCE_ID)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setCircularRegion(mCircle.getCenter().latitude, mCircle.getCenter().longitude,
                        (float) mCircle.getRadius())
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER
                        | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT)
                .setLoiteringDelay(1000)
                .build();
        geofences.add(geofence);
        addGeofences(geofences);
    }

    private void addGeofences(List<Geofence> geofenceList) {
        if (geofenceList == null) {
            throw new IllegalArgumentException("Argument 'geofenceList' cannot be null.");
        }

        if (!mGoogleApiClient.isConnected()) {
            Log.d(TAG, "Google API client is not connected!");
            return;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    createGeofencingRequest(geofenceList),
                    GeofenceTransitionIntentService.newPendingIntent(this, convertMarkersToLatlng(mMarkers))
            ).setResultCallback(this);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION_LOCATION);
        }
    }

    private void removeGeofences() {
        Log.d(TAG, "removeGeofences()");
        if (mGoogleApiClient == null) {
            Log.d(TAG, "Failed to remove geofence because Google API client is null!");
            return;
        }
        List<String> geofences = new ArrayList<>();
        geofences.add(GEOFENCE_ID);
        LocationServices.GeofencingApi.removeGeofences(mGoogleApiClient, geofences);
    }

    private static GeofencingRequest createGeofencingRequest(List<Geofence> geofenceList) {
        if (geofenceList == null) {
            throw new IllegalArgumentException("Argument 'geofenceList' cannot be null.");
        }
        return new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_DWELL)
                .addGeofences(geofenceList)
                .build();
    }

    private static ArrayList<LatLng> convertMarkersToLatlng(List<Marker> markers) {
        ArrayList<LatLng> points = new ArrayList<>();
        for (Marker marker : markers) {
            points.add(marker.getPosition());
        }
        return points;
    }


}