package com.example.willthespoderman.babynurtures.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.willthespoderman.babynurtures.R;
import com.example.willthespoderman.babynurtures.helper.Diaper;
import com.example.willthespoderman.babynurtures.helper.MoodDialog;
import com.example.willthespoderman.babynurtures.helper.Playing;
import com.example.willthespoderman.babynurtures.helper.SessionManager;
import com.example.willthespoderman.babynurtures.helper.SleepingDialog;

import java.util.HashMap;
import java.util.Map;

/*
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
*/


public class BabysitterAct extends AppCompatActivity implements View.OnClickListener {
    private static Button btnEat;
    private static Button btnPlaying;
    private static Button btnSleep;
    private static Button btnMood;
    RequestQueue queue;
    StringRequest stringRequest;
    String BabysitterEmailAdd;
    String ParentUsername;
    String action;

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        queue = Volley.newRequestQueue(this);

        btnEat = (Button) findViewById(R.id.btnEat);
        btnEat.setOnClickListener(this);
        btnPlaying = (Button) findViewById(R.id.btnPlaying);
        btnPlaying.setOnClickListener(this);

        btnSleep = (Button) findViewById(R.id.btnSleep);
        btnSleep.setOnClickListener(this);

        btnMood = (Button) findViewById(R.id.btnMood);
        btnMood.setOnClickListener(this);

        sessionManager = new SessionManager(this);
        SharedPreferences sharedPreferences = getSharedPreferences("BabyLogin", Context.MODE_PRIVATE);
        BabysitterEmailAdd = sharedPreferences.getString("keyName", "");
        ParentUsername = sharedPreferences.getString("keyWhoCreated", "");
    }

    public void showDiaper(View v) {
        FragmentManager manager = getSupportFragmentManager();
        Diaper dialog = new Diaper();
        dialog.show(manager, "dialog");
    }

    public void showPlaying(View v) {
        Intent i = new Intent(this,PlayingActivity.class);
        startActivity(i);
    }

    public void showSleepingDialog(View v) {
        FragmentManager manager = getSupportFragmentManager();
        SleepingDialog dialog = new SleepingDialog();
        dialog.show(manager, "dialog");
    }

    public void showMoodDialog(View v) {
        FragmentManager manager = getSupportFragmentManager();
        MoodDialog dialog = new MoodDialog();
        dialog.show(manager, "dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_babysitter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnPlaying) {
            showPlaying(v);
        }

        if (v.getId() == R.id.btnSleep) {
            showSleepingDialog(v);
        }

        if (v.getId() == R.id.btnMood) {
            showMoodDialog(v);
        }

        else if (v.getId() == R.id.btnEat) {
            showDiaper(v);
            action = "Done changing diaper";
            String url ="http://172.31.11.32:80/android_login_api/notification.php";
            stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Log.e("Error", s.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e("Error", volleyError.toString());
                }
            }) {
                protected Map<String,String> getParams() {
                    Map<String,String>params = new HashMap<String,String>();
                    params.put("tag", "notification");
                    params.put("action", action);
                    params.put("EmailTo", ParentUsername);
                    params.put("EmailFrom", BabysitterEmailAdd);
                    return params;
                }
            };
            //switch
        }
       // queue.add(stringRequest);
       // saveToParse();
    }

}
