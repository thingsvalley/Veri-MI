package utlimate.bus;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import utlimate.bus.geotag.GeotagMainActivity;

import static utlimate.bus.geotag.GeotagMainActivity.isImageUploaded;

public class Density extends AppCompatActivity {

    int mainlines,mainlineq,laterals,lateralq,controls,controlq,flushs,flushq,bypassq,screens;
    double bypasss,venturys;
    EditText t1,t2,t3,t4,t5,t6,t7,t8,t9,t10;

    int mainlinesize[] = {90,75,63,50};
    int mainlineqty[]={5,10,15,20,25};
    int lateralsize[] ={16,12};
    int lateralqty[]={5,10,15,20,25};
    int controlvalvesize[] = {90,75,63,50};
    int controlvalveqty[]={5,10,15,20,25};
    int flushvalvesize[] = {90,75,63,50};
    int flushvalveqty[]={5,10,15,20,25};
    double bypasssize[] ={1.25,1.75};
    int bypassqty[]={2,4,6,8,10};
    double venturysize[]={0.01905,0.0254,0.0381,0.0508};
    int screenfiltersize[]={10,20,30,40};

    Button b,uploadbtn;
    Spinner mainline, mainline1,lateral,lateral1,control,control1,flush,flush1,bypass,bypass1,ventury,screen;
    String result;

    List<String> locationList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_density);


        result = getIntent().getExtras().getString("result");

        result= result.replaceAll("\\[", "").replaceAll("\\]","");
        result=result.replace("\"", "");
        locationList2 = Arrays.asList(result.split(","));

        t1 = (EditText) findViewById(R.id.year1);
        t2 = (EditText) findViewById(R.id.village1);
        t3 = (EditText) findViewById(R.id.beneficiary1);
        t4 = (EditText) findViewById(R.id.adhar1);
        t5 = (EditText) findViewById(R.id.mobile1);
        t6 = (EditText) findViewById(R.id.crop1);
        t7 = (EditText) findViewById(R.id.area1);
        t8 = (EditText) findViewById(R.id.sanction1);
        b = (Button)findViewById(R.id.buttonsubmit);
        uploadbtn=(Button)findViewById(R.id.upload);
       // t9 = (TextView)findViewById(R.id.den9);
        //t10 = (TextView)findViewById(R.id.den10);

        //Spinner for mainline size

         mainline = (Spinner) findViewById(R.id.mainlinesize);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.mainline_size, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mainline.setAdapter(adapter1);


        //Spinner for mainline quantity

         mainline1 = (Spinner) findViewById(R.id.mainlineqty);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.mainline_qty, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mainline1.setAdapter(adapter2);



        //Spinner for lateral size
        lateral = (Spinner) findViewById(R.id.lateralsize);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.lateral_size, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lateral.setAdapter(adapter3);



        //Spinner for lateral quantity

        lateral1 = (Spinner) findViewById(R.id.lateralqty);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.mainline_qty, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lateral1.setAdapter(adapter4);




        //Spinner for control valve
        control = (Spinner) findViewById(R.id.controlsize);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this,
                R.array.lateral_size, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        control.setAdapter(adapter5);



        //Spinner for control valve quantity

        control1 = (Spinner) findViewById(R.id.controlqty);
        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(this,
                R.array.mainline_qty, android.R.layout.simple_spinner_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        control1.setAdapter(adapter6);


        //Spinner for Flush valve
        flush = (Spinner) findViewById(R.id.flushsize);
        ArrayAdapter<CharSequence> adapter7 = ArrayAdapter.createFromResource(this,
                R.array.lateral_size, android.R.layout.simple_spinner_item);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flush.setAdapter(adapter7);

        //Spinner for flush valve quantity

        flush1 = (Spinner) findViewById(R.id.flushqty);
        ArrayAdapter<CharSequence> adapter8 = ArrayAdapter.createFromResource(this,
                R.array.mainline_qty, android.R.layout.simple_spinner_item);
        adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flush1.setAdapter(adapter8);



        //Spinner for By pass Assembly size
         bypass = (Spinner) findViewById(R.id.bypasssize);
        ArrayAdapter<CharSequence> adapter9 = ArrayAdapter.createFromResource(this,
                R.array.lateral_size, android.R.layout.simple_spinner_item);
        adapter9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bypass.setAdapter(adapter9);

        //Spinner for By pass assembly quantity

         bypass1 = (Spinner) findViewById(R.id.bypassqty);
        ArrayAdapter<CharSequence> adapter10 = ArrayAdapter.createFromResource(this,
                R.array.mainline_qty, android.R.layout.simple_spinner_item);
        adapter10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bypass1.setAdapter(adapter10);


        //Spinner for ventury
         ventury = (Spinner) findViewById(R.id.venturysize);
        ArrayAdapter<CharSequence> adapter11 = ArrayAdapter.createFromResource(this,
                R.array.lateral_size, android.R.layout.simple_spinner_item);
        adapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ventury.setAdapter(adapter11);


        //Spinner for Screen Filter

         screen = (Spinner) findViewById(R.id.screensize);
        ArrayAdapter<CharSequence> adapter12 = ArrayAdapter.createFromResource(this,
                R.array.mainline_qty, android.R.layout.simple_spinner_item);
        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        screen.setAdapter(adapter12);

        displayAdhar();

    }


    public void submit(View v) {

        if (isImageUploaded) {
            Toast.makeText(getApplicationContext(), mainline.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

            Toast.makeText(getApplicationContext(), mainline1.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

            String method = "sendReport";
            String mims = locationList2.get(1);

            String aadhar = locationList2.get(3);

            PipeDetails backgroundTask = new PipeDetails(Density.this,this);
            backgroundTask.execute(method, mims, mainline.getSelectedItem().toString(), mainline1.getSelectedItem().toString(), lateral.getSelectedItem().toString(), lateral1.getSelectedItem().toString(),
                    control.getSelectedItem().toString(), control1.getSelectedItem().toString(), flush.getSelectedItem().toString(), flush1.getSelectedItem().toString(),
                    bypass.getSelectedItem().toString(), bypass1.getSelectedItem().toString(), ventury.getSelectedItem().toString(), screen.getSelectedItem().toString(), aadhar);
        }else {
            Toast.makeText(Density.this,"Please upload field images",Toast.LENGTH_SHORT).show();
        }

    }


    public void upload(View v)
    {
        Intent intent= new Intent(Density.this,GeotagMainActivity.class);
        startActivity(intent);
       // finish();
    }



    public void displayAdhar()
    {
        t1.setText(locationList2.get(0));
        t2.setText(locationList2.get(1));
        t3.setText(locationList2.get(2));
        t4.setText(locationList2.get(3));
        t5.setText(locationList2.get(4));
        t6.setText(locationList2.get(5));
        t7.setText(locationList2.get(6));
        t8.setText(locationList2.get(7));




    }



}
