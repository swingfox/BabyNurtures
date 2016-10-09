package com.example.willthespoderman.babynurtures;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.willthespoderman.babynurtures.app.AppConfig;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class EditTodoActivity extends AppCompatActivity {
    Spinner startTime, endTime;
    EditText task;
    String uid = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);
        String[] time = new String[]{"6:00 AM","6:30 AM","7:00 AM","7:30 AM","8:00 AM","9:00 AM","9:30 AM","10:00 AM","10:30 AM","11:00 AM","11:30 AM","12:00 PM","12:30 AM","1:00 PM","1:30 PM","2:00 PM","2:30 PM","3:00 PM","3:30 PM","4:00 PM","4:30 PM","5:00 PM","5:30 PM","6:00 PM","6:30 PM","7:00 PM","7:30 PM","8:00 PM",};
        startTime = (Spinner)findViewById(R.id.todoStart);
        endTime = (Spinner)findViewById(R.id.todoEnd);
        task = (EditText) findViewById(R.id.todoTask);
        ArrayAdapter<String> startadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,time);
        startTime.setAdapter(startadapter);
        ArrayAdapter<String> endadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,time);
        endTime.setAdapter(endadapter);

        if(getIntent().getExtras()!=null) {
            uid = getIntent().getStringExtra("uid");
            String task = getIntent().getStringExtra("task");
            String start = getIntent().getStringExtra("startTime");
            String end = getIntent().getStringExtra("endTime");
            this.task.setText(task);
            startTime.setSelection(findPosition(time, start));
            endTime.setSelection(findPosition(time, end));
        }
    }

    public void updateTodo(View view){
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(AppConfig.URL_EDIT_TODO);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("uid", uid));
            nvps.add(new BasicNameValuePair("task", task.getText().toString()));
            nvps.add(new BasicNameValuePair("startTime", startTime.getSelectedItem().toString()));
            nvps.add(new BasicNameValuePair("endTime", endTime.getSelectedItem().toString()));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            HttpResponse response = client.execute(httpPost);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String responseString = "";
            while(true){
                String temp = reader.readLine();
                if(temp==null)
                    break;
                responseString+= temp;
            }
            if(responseString.contentEquals("true")) {
                Toast.makeText(EditTodoActivity.this,"Succefully edited todo",Toast.LENGTH_SHORT).show();
                finish();
            }
            else
                Toast.makeText(EditTodoActivity.this,"Updating of todo was unsuccessful",Toast.LENGTH_SHORT).show();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private int findPosition(String []array, String item){
        int result = -1;
        for(int i=0;i<array.length;i++)
            if(array[i].contentEquals(item))
            {
                result = i;
                break;
            }
        return  result;
    }
}
