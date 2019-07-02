package utlimate.bus;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class BusMain extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    String provider;
    String lat,lon;
    TextView t1,t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_main);


        t1 = (TextView) findViewById(R.id.lat);
        t2 = (TextView) findViewById(R.id.lon);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Creating an empty criteria object
        Criteria criteria = new Criteria();

        // Getting the name of the provider that meets the criteria
        provider = locationManager.getBestProvider(criteria, false);

        if (provider != null && !provider.equals("")) {

            // Get the location from the given provider
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = locationManager.getLastKnownLocation(provider);

            locationManager.requestLocationUpdates(provider, 20000, 1, this);

            if(location!=null)
                onLocationChanged(location);
            else
                Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(getBaseContext(), "No Provider Found", Toast.LENGTH_SHORT).show();
        }




    }

    @Override
    public void onLocationChanged(Location location) {

        t1.setText("Latitude : "+location.getLatitude());
        t2.setText("Longitude : "+location.getLongitude());


        String lat = Double.toString(location.getLatitude());
        String lon = Double.toString(location.getLongitude());
        String busno = "1";
        String density = "0";


        String method = "sendData";


        // on click of verify button
//        String aadharNo= t1.getText().toString();
//        AadharVerifyTask backgroundTask = new AadharVerifyTask(this);
//        backgroundTask.execute(aadharNo);

        //NewBackgroundTask backgroundTask = new NewBackgroundTask(this);
        //backgroundTask.execute(method,busno,lat,lon,density);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
