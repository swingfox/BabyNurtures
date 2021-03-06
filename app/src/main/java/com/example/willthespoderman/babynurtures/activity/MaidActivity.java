package com.example.willthespoderman.babynurtures.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.willthespoderman.babynurtures.R;

public class MaidActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maid);

        Button logout = (Button) findViewById(R.id.logout);
        ImageView Todolist = (ImageView) findViewById(R.id.Todolist);
        logout.setOnClickListener(this);
        Todolist.setOnClickListener(this);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        switch(v.getId()){

            case R.id.logout:



                startActivity(new Intent(this, MainActivity.class));

                break;


            case R.id.Todolist:
                startActivity(new Intent(this, maidlist.class));
                break;



        }
    }
}
