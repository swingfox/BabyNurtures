package com.example.willthespoderman.babynurtures.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.willthespoderman.babynurtures.R;
import com.example.willthespoderman.babynurtures.models.CustomListAdapter;

public class Babysitter_List extends AppCompatActivity {

    ListView lv;
    CustomListAdapter customListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter__list);

      //  lv = (ListView) findViewById(R.id.list_of_task);

        //customListAdapter = new CustomListAdapter(Babysitter_List.this, Tracker.array);

     //   ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_babysitter__list, Tracker.array);
     //   Toast.makeText(getApplicationContext(),Tracker.array.get(0),Toast.LENGTH_LONG).show();

       // lv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_babysitter__list, menu);
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
