package utlimate.bus;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Chandrala on 19/03/2018.
 */
public class FetchLocationTask extends AsyncTask<String, Void, String> {

    static int responseCode;

    @Override
    protected String doInBackground(String... urls) {
        // params comes from the execute() call: params[0] is the url.
        try {

            return String.valueOf(downloadUrl(urls[0]));
        } catch (IOException e) {
            return null;
        }
    }

    private String downloadUrl(String newurl) throws IOException {
        InputStream is = null;
        try {

            URL url = new URL(newurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(35000 /* milliseconds */);
            conn.setConnectTimeout(35000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            Log.e("url",""+newurl);


            conn.connect();
            responseCode = conn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();

            return sb.toString();
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public  int getResponseCode()
    {
        return responseCode;
    }
}

