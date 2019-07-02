package utlimate.bus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Check extends AppCompatActivity {

    final String PREFS_NAME = "check";

    String MY_PREFS_NAME = "Name";

    String usrname;

    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);


                Intent i = new Intent(Check.this, Register.class);
                startActivity(i);
                // close this activity
                finish();

//                if (settings.getBoolean("my_first_time", true)) {
//                    //the app is being launched for first time, kai tar kar
//                    settings.edit().putBoolean("my_first_time", false).commit();
//
//
//
//                    Intent i = new Intent(Check.this, Register.class);
//                    startActivity(i);
//                    // close this activity
//                    finish();
//                }
//
//                else{
//                    Intent i = new Intent(Check.this,Timepass.class);
//                    startActivity(i);
//                    finish();
//                }


            }
        }, SPLASH_TIME_OUT);



    }
}
