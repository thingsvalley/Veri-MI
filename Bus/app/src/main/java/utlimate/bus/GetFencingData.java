package utlimate.bus;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class GetFencingData extends AppCompatActivity {

    TextView t;

    double lats[],lons[];
    int busnos[],dens[];
    String JSON_STRING;
    String json;

    JSONObject jsonObject;
    JSONArray jsonArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_fencing_data);

        t = (TextView)findViewById(R.id.textViewjson);

        getJSON();
    }

    public void getJSON() {
        new BackgroundTask().execute();

    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String json_url;

        @Override
        protected void onPreExecute() {

            json_url = "afshanshk17.000webhostapp.com/geofence.php";

        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");

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
                jsonArray = jsonObject.getJSONArray("server_response");


                int count = 0, i = 0;
                int question, option3;
                double option1, option2;

               // busnos = new int[50];
                lats = new double[50];
                lons = new double[50];
                //dens = new int[50];


                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);

                   // question = JO.getInt("busno");// key => used in php
                    option1 = JO.getDouble("latitude");
                    option2 = JO.getDouble("longitude");
                    //option3 = JO.getInt("density");


                    count++;

                    //busnos[i] = question;
                    lats[i] = option1;
                    lons[i] = option2;
                    //dens[i] = option3;

                    i++;
                }

               // t.setText(String.valueOf(lats[0]));

                Intent intent = new Intent(GetFencingData.this,MainActivity.class);
                intent.putExtra("lats",lats);
                intent.putExtra("lons",lons);
                startActivity(intent);




            } catch (JSONException e) {
                e.printStackTrace();
            }




        }
    }


}
