package com.example.willthespoderman.babynurtures.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.willthespoderman.babynurtures.R;
import com.example.willthespoderman.babynurtures.helper.SessionManager;

public class Choose_User extends AppCompatActivity {
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose__user);

        Button parent = (Button) findViewById(R.id.btnParent);
        Button babysitter = (Button) findViewById(R.id.btnBabySitter);

        session = new SessionManager(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("BabyLogin", Context.MODE_PRIVATE);
        String type = sharedPreferences.getString("keyType","");
        // Check if user is already logged in or not

        if (session.isLoggedIn()&& type.equals("1")) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Choose_User.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        if(session.isLoggedIn()&& type.equals("0")){

            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Choose_User.this, Babysitter_Main.class);
            startActivity(intent);
            finish();
        }





        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Choose_User.this,LoginActivity.class);
                startActivity(intent);

            }
        });

        babysitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choose_User.this,Babysitter_Login.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose__user, menu);
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
}
