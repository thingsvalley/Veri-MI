package utlimate.bus.pre_inspection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import utlimate.bus.R;
import utlimate.bus.Timepass;
import utlimate.bus.geotag.GeotagMainActivity;


public class FormActivity extends AppCompatActivity {

    int mainlines,mainlineq,laterals,lateralq,controls,controlq,flushs,flushq,bypassq,screens;
    double bypasss,venturys;
    EditText t1,t2,t3,t4,t5,t6,t7,t8,t9,t10;



    Button b,uploadbtn;
    Spinner mainline, mainline1,lateral,lateral1,control,control1,flush,flush1,bypass,bypass1,ventury,screen;
    String result;
    ProgressBar progressBar;

    List<String> locationList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_layout);


//        result = getIntent().getExtras().getString("result");
//
//        result= result.replaceAll("\\[", "").replaceAll("\\]","");
//        result=result.replace("\"", "");
//        locationList2 = Arrays.asList(result.split(","));

        t1 = (EditText) findViewById(R.id.mimss);
        t2 = (EditText) findViewById(R.id.areaoffield);
        t3 = (EditText) findViewById(R.id.ph);
        t4 = (EditText) findViewById(R.id.cropplant);
        t5 = (EditText) findViewById(R.id.stype);
        t6 = (EditText) findViewById(R.id.sthick);
        t7 = (EditText) findViewById(R.id.nom);
        t8 = (EditText) findViewById(R.id.nnn);
        b = (Button)findViewById(R.id.btnsub);
        uploadbtn=(Button)findViewById(R.id.upl);
        progressBar = findViewById(R.id.progressbar);

        t1.setEnabled(true);
        t2.setEnabled(true);
        t3.setEnabled(true);
        t4.setEnabled(true);
        t5.setEnabled(true);
        t6.setEnabled(true);
        t7.setEnabled(true);
        t8.setEnabled(true);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                String mimss = t1.getText().toString();
                String areaoffield = t2.getText().toString();
                String ph = t3.getText().toString();
                String cropplant = t4.getText().toString();
                String stype = t5.getText().toString();
                String sthick = t6.getText().toString();
                String nom = t7.getText().toString();
                String nnn = t8.getText().toString();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(FormActivity.this,"Submitted Successfully.",Toast.LENGTH_SHORT).show();
                Intent intent=new  Intent(FormActivity.this,Timepass.class);
                startActivity(intent);
                finish();

//            FormDetails backgroundTask = new FormDetails(FormActivity.this,FormActivity.this);
//            backgroundTask.execute(mimss,areaoffield,ph,cropplant,stype,sthick,nom,nnn);

            }
        });

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(FormActivity.this,PreGeotagMainActivity.class);
                intent.putExtra("isFormData",true);
                startActivity(intent);
            }
        });
    }
        // t9 = (TextView)findViewById(R.id.den9);
        //t10 = (TextView)findViewById(R.id.den10);









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
