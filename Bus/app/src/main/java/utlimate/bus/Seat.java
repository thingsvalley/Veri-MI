package utlimate.bus;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utlimate.bus.data_model.TaskDataModel;

public class Seat extends AppCompatActivity {

    TextView txtLoading,t2,t3,t4,t5,t6,t7,t8,t9,t10;

    String greetings[];

    double lats[],lons[];
    String mims[],name[],address[],phone[];
    String tmims,tname,taddress,tphone;

    String JSON_STRING;
    String json;

    JSONObject jsonObject;
    JSONArray jsonArray;


    List<String> locationList,locationList2;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<TaskDataModel> taskArrayList=new ArrayList<>();
    int c=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        txtLoading = (TextView)findViewById(R.id.textViewbus1);
        recyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);




        getJSON(null);

       // timer(null);
    }

    public void getJSON(View view) {
        if(isInternetConnection()) {

            new NewBackgroundTask().execute();

        }else {

            Toast.makeText(Seat.this,"Please check internet connection",Toast.LENGTH_LONG).show();
        }

    }

    class NewBackgroundTask extends AsyncTask<Void, Void, String> {

        String json_url;

        @Override
        protected void onPreExecute() {

            json_url = "https://afshanshk17.000webhostapp.com/json_get_data.php";

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

            txtLoading.setVisibility(View.GONE);
          //  Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            json = result;


            try {
                jsonObject = new JSONObject(json);
                jsonArray = jsonObject.getJSONArray("server_response");


                int count = 0, i = 0;


                mims = new String[50];
                name = new String[50];
                address = new String[50];
                phone = new String[50];


                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);

                    //Toast.makeText(getApplicationContext(),JO.getString("mims"),Toast.LENGTH_LONG).show();


                    tmims = JO.getString("mims");// key => used in php
                    tname = JO.getString("name");
                    taddress = JO.getString("address");
                    tphone = JO.getString("phone");

                 //   Toast.makeText(getApplicationContext(),tmims,Toast.LENGTH_LONG).show();

                    count++;



                    mims[i] = tmims;
                    name[i] = tname;
                    address[i] = taddress;
                    phone[i] = tphone;

                    TaskDataModel taskDataModel=new TaskDataModel();
                    taskDataModel.setAddress(taddress);
                    taskDataModel.setMims(tmims);
                    taskDataModel.setName(tname);
                    taskDataModel.setPhoneNumber(tphone);

                    taskArrayList.add(taskDataModel);

                    i++;
                }

              setdataToAdapter();

//                for(int j=0;j<mims.length;j++) {
//                   // t1.setText(""+(j+1));
//                    t1.setText(mims[0] + "\n" + name[0] + "\n" + address[0] + "\n" + phone[0] + "\n\n\n***********************************");
//                    c++;
//                }

            } catch (JSONException e) {
                e.printStackTrace();
            }




        }
    }

    private void setdataToAdapter() {
        mAdapter = new RecyclerAdapter(this, taskArrayList);
        recyclerView.setAdapter(mAdapter);
    }

    public void checkInternet(String mims)
    {
        if(isInternetConnection()) {

            checkMimsNumber(mims);
        }else {

            Toast.makeText(Seat.this,"Please check internet connection",Toast.LENGTH_LONG).show();
        }
    }


    public void checkMimsNumber(String mims) {
        String method = "sendmims";

        new BackgroundTask(this) {

            @Override
            public void onPostExecute(String result) {

                if (result != null) {
                    System.out.println("fetch result=" + result);

                    //fetching the data from php array which is sent as jason object
//                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();

                    result = result.replaceAll("\\[", "").replaceAll("\\]", "");
                    result = result.replace("\"", "");
                    locationList2 = Arrays.asList(result.split(","));

                    Intent i = new Intent(Seat.this, MainActivity.class);
                    i.putExtra("result", result);
                    startActivity(i);
                    finish();
                    System.out.println("fetch result=" + result);


                } else {
                    Toast.makeText(Seat.this, "Failed to load", Toast.LENGTH_LONG).show();
                }


            }
        }.execute(method, mims);


    }
    public  boolean isInternetConnection()
    {
        ConnectivityManager connectivityManager =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
