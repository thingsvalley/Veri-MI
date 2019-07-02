package utlimate.bus;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MimsActivity extends AppCompatActivity {

    EditText e;
    Button b;


    List<String> locationList,locationList2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mims_activity_main);


        e = (EditText)findViewById(R.id.editTextmims);

        b = (Button)findViewById(R.id.button);


    }

    public void send(View v)
    {
        if(isInternetConnection()) {

            checkMimsNumber();
        }else {

             Toast.makeText(MimsActivity.this,"Please check internet connection",Toast.LENGTH_LONG).show();
        }
    }

    private void checkMimsNumber() {
        String method = "sendmims";
        String mims = e.getText().toString();
        BackgroundTask backgroundTask = new BackgroundTask(this);

    new BackgroundTask(this) {

        @Override
        public void onPostExecute(String result) {

            if (result!=null)
            { System.out.println("fetch result="+result);

                //fetching the data from php array which is sent as jason object
                Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();

                result= result.replaceAll("\\[", "").replaceAll("\\]","");
                result=result.replace("\"", "");
                locationList2 = Arrays.asList(result.split(","));

                Intent i = new Intent(ctx,MainActivity.class);
                i.putExtra("result",result);
                startActivity(i);
                System.out.println("fetch result="+result);
                finish();


            } else {
                Toast.makeText(MimsActivity.this, "Failed to load", Toast.LENGTH_LONG).show();
            }


        }
    } .execute(method, mims);

//this URL connects to database


}

    public  boolean isInternetConnection()
    {
        ConnectivityManager connectivityManager =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
