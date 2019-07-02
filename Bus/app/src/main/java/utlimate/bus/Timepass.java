package utlimate.bus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import utlimate.bus.pre_inspection.*;

public class Timepass extends AppCompatActivity implements View.OnClickListener {

//    ImageButton i1,i2,i3,i4,i5;

    CardView i1,i2,i3,i4,i5,i6;
    String MY_PREFS_NAME = "Name";
    TextView t;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for animation
        context=Timepass.this;
        setContentView(R.layout.activity_timepass);

//        i1 = (ImageButton)findViewById(R.id.imageButton);
//        i2 = (ImageButton)findViewById(R.id.imageButton2);
//        i3 = (ImageButton)findViewById(R.id.imageButton3);
//        i4 = (ImageButton)findViewById(R.id.imageButton4);
//        i5 = (ImageButton)findViewById(R.id.imageButton5);
//        t = (TextView)findViewById(R.id.username);

        //card view
        i1 = (CardView)findViewById(R.id.imageButton);
        i2 = (CardView)findViewById(R.id.imageButton2);
        i3 = (CardView)findViewById(R.id.imageButton3);
        i4 = (CardView)findViewById(R.id.imageButton4);
        i5 = (CardView)findViewById(R.id.imageButton5);
        i6 = (CardView)findViewById(R.id.imageButton6);
//        t = (TextView)findViewById(R.id.username);


        //we did this
        i1.setOnClickListener(this);
        i2.setOnClickListener(this);
        i3.setOnClickListener(this);
        i4.setOnClickListener(this);
        i5.setOnClickListener(this);
        i6.setOnClickListener(this);


//        t.setText("Hi "+getUserName()+"!");

        initializeViews();


    }

//    /*This function calls the google map on click of button*/
//    public void map(View v)
//    {
//        Intent i = new Intent(Timepass.this,MimsActivity.class);
//        startActivity(i);
//        overridePendingTransition(R.anim.slide_up,R.anim.fade_out);
//
//    }
//
//    public void density(View v)
//    {
//        Intent i = new Intent(Timepass.this,AdharMainActivity.class);
//        startActivity(i);
//        overridePendingTransition(R.anim.slide_up,R.anim.fade_out);
//
//    }
//
//    public void developer(View v)
//    {
//        Intent i = new Intent(Timepass.this,Developer.class);
//        startActivity(i);
//        overridePendingTransition(R.anim.slide_up,R.anim.fade_out);
//
//    }
//
//
//    public void seat(View v)
//    {
//        Intent i = new Intent(Timepass.this,Seat.class);
//        startActivity(i);
//        overridePendingTransition(R.anim.slide_up,R.anim.fade_out);
//
//    }
//
//
//    public void location(View v)
//    {
//        Intent i = new Intent(Timepass.this,Location.class);
//        startActivity(i);
//        overridePendingTransition(R.anim.slide_up,R.anim.fade_out);
//
//    }

    String getUserName()
    {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String usrname = prefs.getString("first_name",null);

        return usrname;
    }


    //function for animation

    public void initializeViews()
    {
//        Button btn=(Button)findViewById(R.id.imageButton);
//        btn.setOnClickListener(this);
    }

    private void goToNextActivity(int animationIn, int animationOut) {
        Intent intent = new Intent(context,MimsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(animationIn, animationOut);
    }

    @Override
    public void onClick(View view) {

        Intent i;
        switch(view.getId())
        {
            case R.id.imageButton : i = new Intent(Timepass.this,Seat.class);
                                     startActivity(i);
                                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                                    break;


            case R.id.imageButton2 : i = new Intent(Timepass.this,AdharMainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;

            case R.id.imageButton3 : i = new Intent(Timepass.this, FormActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;


            case R.id.imageButton4 : i = new Intent(Timepass.this,ProfileActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;


            case R.id.imageButton5 : i = new Intent(Timepass.this,Developer.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;


            case R.id.imageButton6 : i = new Intent(Timepass.this,ChatActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;
        }

    }

//    public void onClick(View v) {
//         if(v.getId() == R.id.relativeLayout)
//            goToNextActivity(R.anim.slide_down, R.anim.fade_out);
//        } else if (v.getId() == R.id.fade_out) {
//            goToNextActivity(R.anim.fade_out, R.anim.fade_out);
//
//        } else if (v.getId() == R.id.slide_down) {
//
//            goToNextActivity(R.anim.slide_down, R.anim.fade_out);
//        } else if (v.getId() == R.id.slide_up) {
//            goToNextActivity(R.anim.slide_up, R.anim.fade_out);
//        } else if (v.getId() == R.id.slide_from_left) {
//            goToNextActivity(R.anim.slide_in_from_left, R.anim.fade_out);
//        }
    }






