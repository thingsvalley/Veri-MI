package utlimate.bus;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shreyas on 03/09/17.
 */

public class AadharVerifyTask extends AsyncTask<String,Void,String> {

    String JSON_STRING;
    ProgressBar progressBar;
    Context ctx;
    AadharVerifyTask(AdharMainActivity ctx, ProgressBar progressBar)
    {
        this.progressBar=progressBar;
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String regurl = "https://afshanshk17.000webhostapp.com/app_attribute_display.php";//   addd aadhar url(database)

        String method = params[0];
        if(method.equals("sendAdhar"))
        {
            String aadhar = params[1];


            try {
                URL url = new URL(regurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

                String data = URLEncoder.encode("aadhar","UTF-8")+"="+URLEncoder.encode(aadhar,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream is = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");

                }

                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();

                //is.close();

                return stringBuilder.toString().trim();

                //return "AadharCard Sent";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {

//        Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();

        progressBar.setVisibility(View.GONE);


        Intent i = new Intent(ctx,Density.class);
        i.putExtra("result",result);
        ctx.startActivity(i);

    }
}