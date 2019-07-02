package utlimate.bus;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import utlimate.bus.R;
import utlimate.bus.Timepass;

/**
 * Created by Aisha on 3/30/2018.
 */

public class ChatActivity extends AppCompatActivity {

    ImageButton i1,i2,i3,i4,i5;
    String MY_PREFS_NAME = "Name";
    TextView t;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for animation
        setContentView(R.layout.chat_activity);


        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://console.dialogflow.com/api-client/demo/embedded/292953a7-f805-415b-ae2a-2938a7da3fe1");
    }
}
