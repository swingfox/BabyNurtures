package com.example.willthespoderman.babynurtures.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.willthespoderman.babynurtures.R;

/**
 * Created by Darren Portilla on 09/10/2016.
 */

public class MoodActivity  extends AppCompatActivity {
    private EditText txtMessage;
    private Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_actmood);

        }
}
