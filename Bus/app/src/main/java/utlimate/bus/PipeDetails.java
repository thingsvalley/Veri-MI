package utlimate.bus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

import static utlimate.bus.geotag.GeotagMainActivity.isImageUploaded;

/**
 * Created by Chandrala on 22/03/2018.
 */

public class PipeDetails extends AsyncTask<String,Void,String> {

    Context ctx;
    Activity activity;
    List<String> locationList,locationList2;
    String JSON_STRING;
    PipeDetails(Density density, Density ctx)
    {
        this.ctx = ctx;
        this.activity=density;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String regurl = "https://afshanshk17.000webhostapp.com/app_insert.php";

        String method = params[0];
        if(method.equals("sendReport"))
        {
            String mims = params[1];


            try {
                URL url = new URL(regurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));


                String data = URLEncoder.encode("mims","UTF-8")+"="+URLEncoder.encode(mims,"UTF-8")+"&"+
                        URLEncoder.encode("mpipe_size","UTF-8")+"="+URLEncoder.encode(params[2],"UTF-8")+"&"+
                        URLEncoder.encode("mpipe_qty","UTF-8")+"="+URLEncoder.encode(params[3],"UTF-8")+"&"+
                        URLEncoder.encode("lpipe_size","UTF-8")+"="+URLEncoder.encode(params[4],"UTF-8")+"&"+
                        URLEncoder.encode("lpipe_qty","UTF-8")+"="+URLEncoder.encode(params[5],"UTF-8")+"&"+
                        URLEncoder.encode("cvalve_size","UTF-8")+"="+URLEncoder.encode(params[6],"UTF-8")+"&"+
                        URLEncoder.encode("cvalve_qty","UTF-8")+"="+URLEncoder.encode(params[7],"UTF-8")+"&"+
                        URLEncoder.encode("fvalve_size","UTF-8")+"="+URLEncoder.encode(params[8],"UTF-8")+"&"+
                        URLEncoder.encode("fvalve_qty","UTF-8")+"="+URLEncoder.encode(params[9],"UTF-8")+"&"+
                        URLEncoder.encode("assembly_size","UTF-8")+"="+URLEncoder.encode(params[10],"UTF-8")+"&"+
                        URLEncoder.encode("assembly_qty","UTF-8")+"="+URLEncoder.encode(params[11],"UTF-8")+"&"+
                        URLEncoder.encode("ventury_size","UTF-8")+"="+URLEncoder.encode(params[12],"UTF-8")+"&"+
                        URLEncoder.encode("screen_filter_size","UTF-8")+"="+URLEncoder.encode(params[13],"UTF-8")+"&"+
                URLEncoder.encode("aadhar","UTF-8")+"="+URLEncoder.encode(params[14],"UTF-8");

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

      //  Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
        isImageUploaded=false;
        Intent intent=new  Intent(ctx,Timepass.class);
        ctx.startActivity(intent);
        activity.finish();

    }

}
