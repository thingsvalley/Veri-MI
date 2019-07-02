package utlimate.bus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
public class AdharMainActivity extends AppCompatActivity {
    Button b1, b2;
    EditText e, ed2;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adhar_activity_main);

        e = (EditText)findViewById(R.id.editTextadhar);
        progressBar = findViewById(R.id.progressbar);
       /* b1 = (Button)findViewById(R.id.button);
        ed1 = (EditText)findViewById(R.id.editText);
        b2 = (Button)findViewById(R.id.button2);

        tx1.setVisibility(View.GONE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
           /* public void onClick(View v) {
                if(ed1.getText().toString().equals("ka123")) {
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();

                            tx1.setVisibility(View.VISIBLE);
                    tx1.setBackgroundColor(Color.RED);

                }
            }
        });*

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }*/
    }


    public void sendAdhar(View v)
    {
        progressBar.setVisibility(View.VISIBLE);
        String method="sendAdhar";
        String adhar = e.getText().toString();
        AadharVerifyTask backgroundTask = new AadharVerifyTask(this,progressBar);
        backgroundTask.execute(method,adhar);
    }
}

