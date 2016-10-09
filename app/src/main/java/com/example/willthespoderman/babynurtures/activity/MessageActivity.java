package com.example.willthespoderman.babynurtures.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.willthespoderman.babynurtures.R;
import com.example.willthespoderman.babynurtures.helper.SessionManager;

/**
 * Created by Darren Portilla on 09/10/2016.
 */

public  class MessageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagebox);

    }
}