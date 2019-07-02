package utlimate.bus;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    public GoogleMap mMap;
    public Marker obus1,obus2,obus3,obus4,obus5,obus6,obus7,obus8,obus9,obus10;

    double lats[],lons[];
    int busnos[],dens[];
    String JSON_STRING;
    String json;

    JSONObject jsonObject;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //getCord();
        getJSON(null);

        timer(null);


    }


    public  void getJSON(View view)
    {
        new BackgroundTask().execute();

    }

    class BackgroundTask extends AsyncTask<Void,Void,String>
    {

        String json_url;

        @Override
        protected void onPreExecute() {

            json_url = "http://ultimatecorp.esy.es/jsonbus.php";

        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(JSON_STRING+"\n");

                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json = result;





            try {
                jsonObject = new JSONObject(json);
                jsonArray  = jsonObject.getJSONArray("server_response");


                int count = 0, i = 0;
                int question,option3;
                double option1,option2;

                busnos = new int[50];
                lats = new double[50];
                lons = new double[50];
                dens = new int[50];



                while(count<jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);

                    question = JO.getInt("busno");// key => used in php
                    option1 = JO.getDouble("latitude");
                    option2 = JO.getDouble("longitude");
                    option3 = JO.getInt("density");



                    count++;

                    busnos[i] = question;
                    lats[i] = option1;
                    lons[i] = option2;
                    dens[i] = option3;

                    i++;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }




            LatLng bus1 = new LatLng(lats[0],lons[0]);
            obus1.setPosition(bus1);

            LatLng bus2 = new LatLng(lats[1],lons[1]);
            obus2.setPosition(bus2);

            LatLng bus3 = new LatLng(lats[2],lons[2]);
            obus3.setPosition(bus3);

            LatLng bus4 = new LatLng(lats[3],lons[3]);
            obus4.setPosition(bus4);

            LatLng bus5 = new LatLng(lats[4],lons[4]);
            obus5.setPosition(bus5);

            LatLng bus6 = new LatLng(lats[5],lons[5]);
            obus6.setPosition(bus6);

            LatLng bus7 = new LatLng(lats[6],lons[6]);
            obus7.setPosition(bus7);

            LatLng bus8 = new LatLng(lats[7],lons[7]);
            obus8.setPosition(bus8);

            LatLng bus9 = new LatLng(lats[8],lons[8]);
            obus9.setPosition(bus9);

            LatLng bus10 = new LatLng(lats[9],lons[9]);
            obus10.setPosition(bus10);

        }
    }


    void timer(View view)
    {
        new CountDownTimer(5000, 1000){
            public void onTick(long millisUntilFinished){

                //Toast.makeText(getApplicationContext(),"efqwfe",Toast.LENGTH_LONG).show();
            }
            public  void onFinish(){

                getJSON(null);
                timer(null);

            }
        }.start();
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(15.832464,74.500516) , 15.0f) );


        // Add a marker in Winterfell and move the camera
           // LatLng bus1 = new LatLng(15.931215,76.19977833);
           // mMap.addMarker(new MarkerOptions().position(bus1).title("Bus Number: 1").snippet("KA22 SH 007").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(bus1));
        //mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(15.831215, 74.49977833) , 15.5f) ); // Ghar


        LatLng bus1 = new LatLng(50,6);

       MarkerOptions b1 = new MarkerOptions().position(bus1).title("Bus Number: 1").snippet("KA22 SH 001").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
       obus1 = mMap.addMarker(b1);


        LatLng bus2 = new LatLng(50,6);

        MarkerOptions b2 = new MarkerOptions().position(bus2).title("Bus Number: 2").snippet("KA22 SH 002").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        obus2 = mMap.addMarker(b2);


        LatLng bus3 = new LatLng(50,6);

        MarkerOptions b3 = new MarkerOptions().position(bus3).title("Bus Number: 3").snippet("KA22 SH 003").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        obus3 = mMap.addMarker(b3);


        LatLng bus4 = new LatLng(50,6);

        MarkerOptions b4 = new MarkerOptions().position(bus4).title("Bus Number: 4").snippet("KA22 SH 004").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        obus4 = mMap.addMarker(b4);


        LatLng bus5 = new LatLng(50,6);

        MarkerOptions b5 = new MarkerOptions().position(bus5).title("Bus Number: 5").snippet("KA22 SH 005").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        obus5 = mMap.addMarker(b5);


        LatLng bus6 = new LatLng(50,6);

        MarkerOptions b6 = new MarkerOptions().position(bus6).title("Bus Number: 6").snippet("KA22 SH 006").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        obus6 = mMap.addMarker(b6);


        LatLng bus7 = new LatLng(50,6);

        MarkerOptions b7 = new MarkerOptions().position(bus7).title("Bus Number: 7").snippet("KA22 SH 007").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        obus7 = mMap.addMarker(b7);


        LatLng bus8 = new LatLng(50,6);

        MarkerOptions b8 = new MarkerOptions().position(bus8).title("Bus Number: 8").snippet("KA22 SH 008").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        obus8 = mMap.addMarker(b8);


        LatLng bus9 = new LatLng(50,6);

        MarkerOptions b9 = new MarkerOptions().position(bus9).title("Bus Number: 9").snippet("KA22 SH 009").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        obus9 = mMap.addMarker(b9);


        LatLng bus10 = new LatLng(50,6);

        MarkerOptions b10 = new MarkerOptions().position(bus10).title("Bus Number: 10").snippet("KA22 SH 0010").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        obus10 = mMap.addMarker(b10);

    }


}
