package com.example.willthespoderman.babynurtures.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.willthespoderman.babynurtures.R;
import com.example.willthespoderman.babynurtures.app.NotificationControl;

/**
 * Created by Darren Portilla on 09/10/2016.
 */

public class PlayingActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText txtMessage;
    private Button btnSend;
    private TextView txtActivity;
    SharedPreferences sharedPreferences;
    private String activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actplay);

        sharedPreferences = getSharedPreferences("BabyLogin", Context.MODE_PRIVATE);


        txtMessage = (EditText) findViewById(R.id.txtMessage);
        txtActivity = (TextView) findViewById(R.id.txtActivity);
        Bundle b = new Bundle();
        if(b!=null) {
            activity = this.getIntent().getExtras().getString(("activity"));
            txtActivity.setText(activity+':');
        }

        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnSend:
                String sender = sharedPreferences.getString("keyName", "");
                String message = "(" + activity + ") " +txtMessage.getText();
                if(NotificationControl.sendNotificationSender(message, sender)){
                    Toast.makeText(this,"Successfully send notification " + message,Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
