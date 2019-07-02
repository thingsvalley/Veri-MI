package utlimate.bus;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String MY_PREFS_NAME = "Name";

    EditText efirst,elast,eusn,eroll,edivision;

    public int i=0;
    TextView textViewGreeting;


    Button login;

    String greetings[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        greetings = new String[15];

        greetings[0] = "Hello!";
        greetings[1] = "வணக்கம்";
        greetings[2] = "ہیلو";
        greetings[3] = "नमस्कार";
        greetings[4] = "ನಮಸ್ಕಾರ";
        greetings[5] = "ਸਤ ਸ੍ਰੀ ਅਕਾਲ";
        greetings[6] = "प्रनाम";
        greetings[7] = "હેલ્લો";
        greetings[8] = "आदाब";
        greetings[9] = "नमस्कारः";
        greetings[10] = "హలో";


        efirst = (EditText) findViewById(R.id.edittext_first_name);
       /* elast = (EditText) findViewById(R.id.edittext_last_name);
        eusn = (EditText) findViewById(R.id.edittext_usn);
        eroll = (EditText) findViewById(R.id.edittext_rollno);
        edivision = (EditText) findViewById(R.id.edittext_division);*/


        textViewGreeting = (TextView)findViewById(R.id.greeting);

        timer(null);
// -------------------------------------------------------------------------------------------------

        // Take me in -------------------------------------------------------------------------------------
        login = (Button)findViewById(R.id.take_me_in);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                putUserName();

                Intent i = new Intent(Register.this,Timepass.class);
                startActivity(i);
                finish();
            }
        });


      /*  Spinner spinner= (Spinner) findViewById(R.id.district);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.district_names, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);*/


    }


    void timer(View view)
    {
        new CountDownTimer(15000, 1500){
            public void onTick(long millisUntilFinished){

                if(i>10)
                    i = 0;

                textViewGreeting.setText(greetings[i]);
                i = i+1;

            }
            public  void onFinish(){

                timer(null);

            }
        }.start();
    }



    void putUserName()
    {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        editor.putString("first_name", efirst.getText().toString());

        /*editor.putString("name", efirst.getText().toString().toUpperCase().trim()+" "+elast.getText().toString().toUpperCase().trim());

        editor.putString("usn", eusn.getText().toString().toUpperCase().trim());

        editor.putString("rollno", eroll.getText().toString());

        editor.putString("division", edivision.getText().toString().toUpperCase().trim());*/

        editor.commit();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
