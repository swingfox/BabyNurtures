package com.example.willthespoderman.babynurtures.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.example.willthespoderman.babynurtures.R;
import com.example.willthespoderman.babynurtures.app.NotificationControl;

import java.io.IOException;

public class Notification extends AppCompatActivity {

    ListView notificationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        // TODO Retrieve all Notifications

        String username = this.getSharedPreferences("BabyLogin", Context.MODE_PRIVATE).getString("keyName", "");
        String[] messages;
        try {
            messages = NotificationControl.getAllNotificationsInMessage(username);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,messages);
            notificationList = (ListView)this.findViewById(R.id.list_notif);
            notificationList.setAdapter(adapter);

            notificationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ((SwipeLayout)(notificationList.getChildAt(position - notificationList.getFirstVisiblePosition()))).open(true);
                }
            });
            notificationList.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.e("ListView", "OnTouch");
                    return false;
                }
            });
            notificationList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(), "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
            notificationList.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    Log.e("ListView", "onScrollStateChanged");
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });

            notificationList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("ListView", "onItemSelected:" + position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Log.e("ListView", "onNothingSelected:");
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

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
