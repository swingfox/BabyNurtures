package com.example.willthespoderman.babynurtures.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.willthespoderman.babynurtures.R;
import com.example.willthespoderman.babynurtures.app.NotificationBackgroundTask;
import com.example.willthespoderman.babynurtures.app.NotificationControl;
import com.example.willthespoderman.babynurtures.helper.SessionManager;
import com.example.willthespoderman.babynurtures.utils.Tracker;

import java.io.IOException;
import java.util.Objects;

public class Babysitter_Main extends AppCompatActivity implements View.OnClickListener{
    private SessionManager session;
    Button btnactivity;
    TextView counter;
    NotificationBackgroundTask task;


    @Override
    protected void onResume() {
        NotificationControl.clearNotifications(Babysitter_Main.this);
        String username = this.getSharedPreferences("BabyLogin", Context.MODE_PRIVATE).getString("keyName", "");
        if(username!=null)
            NotificationControl.seenNotifications(username);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter__main);
        // TODO: GET notifications
        if(getIntent().getExtras()!=null) {
            String username = getIntent().getExtras().getString("username");
            task = new NotificationBackgroundTask(username,Babysitter_Main.this,
                    Babysitter_Main.class
                    //Notification.class
                    );
            task.execute(username);
        }
        btnactivity = (Button) findViewById(R.id.btnactivity);
        btnactivity.setOnClickListener(this);

        session = new SessionManager(getApplicationContext());

        Button todo = (Button) findViewById(R.id.todo);
        counter = (TextView) findViewById(R.id.counter);
        if(Tracker.switchTextView)
        counter.setText("COUNT:  "+ Integer.toString(Tracker.counter));
        else
        counter.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(),Integer.toString(Tracker.counter),Toast.LENGTH_LONG).show();
        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Babysitter_Main.this, Choose_Child_Task.class));




            }
        });


        Button logout = (Button) findViewById(R.id.btnLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_babysitter__main, menu);
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

    private void logoutUser() {
        session.setLogin(false,"", "","","");

        // Launching the login activity
        Intent intent = new Intent(Babysitter_Main.this, Choose_User.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        if(task!=null && !task.isCancelled())
            task.cancel(true);
        super.finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnactivity:
                startActivity(new Intent(this, BabysitterAct.class));

        }

    }
}
