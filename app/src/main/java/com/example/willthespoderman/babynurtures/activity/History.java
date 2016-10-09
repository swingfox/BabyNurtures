package com.example.willthespoderman.babynurtures.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.willthespoderman.babynurtures.R;
import com.example.willthespoderman.babynurtures.app.NotificationControl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ArrayAdapter<String> adapter;
        String username = this.getSharedPreferences("BabyLogin", Context.MODE_PRIVATE).getString("keyName", "");
        String[] messages = new String[0];
        SimpleDateFormat format = new SimpleDateFormat();
        try {
            JSONArray array = NotificationControl.getALlNotificationInJSONArray(username);
            messages = new String[array.length()];
            for(int i =0;i < array.length();i++){
                JSONObject obj = array.getJSONObject(i);
                String str = "";
                if(obj.getString("sender").contentEquals(username))
                    str = "SENT to "+obj.getString("receiver");
                else if(obj.getString("receiver").contentEquals(username))
                    str = "RECEIVED from "+ obj.getString("sender");
                str += ":\n"+obj.getString("message");
                String dateStr = obj.getString("date");
                format.applyPattern("yyyy-MM-dd hh:mm:ss");
                Date date = format.parse(dateStr);
                format.applyPattern("MM/d/yyyy hh:mm a");
                str+= "("+format.format(date)+")";
                messages[i] = str;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,messages);
            ListView historyList = (ListView) findViewById(R.id.historylist);
            historyList.setAdapter(adapter);
        }
    }

}
