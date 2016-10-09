package com.example.willthespoderman.babynurtures.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.willthespoderman.babynurtures.EditTodoActivity;
import com.example.willthespoderman.babynurtures.R;
import com.example.willthespoderman.babynurtures.app.AppConfig;
import com.example.willthespoderman.babynurtures.app.AppController;
import com.example.willthespoderman.babynurtures.helper.SessionManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class todolist extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.RegisterActivity.class.getSimpleName();

    Spinner startlist1, Endlist1;
    EditText todoTask1;
    private SessionManager session;
    TextView passer;
    SharedPreferences sharedPreferences;
    String created_by ;
    String created_for ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

        sharedPreferences = getSharedPreferences("BabyLogin", Context.MODE_PRIVATE);

        TextView babyName = (TextView) findViewById(R.id.BabyName);

        babyName.setText(getIntent().getExtras().getString("keyFullname"));

        created_for = getIntent().getExtras().getString("keyFullname");

        String[] time = new String[]{" 6:00 AM "," 6:30 AM " ," 7:00 AM "," 7:30 AM "," 8:00 AM "," 9:00 AM "," 9:30 AM "," 10:00 AM "," 10:30 AM "," 11:00 AM "," 11:30 AM "," 12:00 PM "," 12:30 AM "," 1:00 PM "," 1:30 PM "," 2:00 PM "," 2:30 PM "," 3:00 PM "," 3:30 PM "," 4:00 PM "," 4:30 PM "," 5:00 PM "," 5:30 PM "," 6:00 PM "," 6:30 PM "," 7:00 PM "," 7:30 PM "," 8:00 PM ",};

        startlist1 = (Spinner)findViewById(R.id.todoStart);
        Endlist1 = (Spinner)findViewById(R.id.todoEnd);
        todoTask1 = (EditText) findViewById(R.id.todoTask);
         passer = new TextView(this);


        ArrayAdapter<String> startadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,time);
        startlist1.setAdapter(startadapter);


        ArrayAdapter<String> endadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,time);
        Endlist1.setAdapter(endadapter);



        Button save = (Button) findViewById(R.id.bSave);

        session = new SessionManager(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("BabyLogin", Context.MODE_PRIVATE);
        String type = sharedPreferences.getString("keyType","");
        // Check if user is already logged in or not
        if (!session.isLoggedIn()&& type.equals("0")) {
            logoutUser();
        }else{



            save.setOnClickListener(this);

        }

        created_by = sharedPreferences.getString("keyName", "");
        created_for = getIntent().getExtras().getString("keyFullname");
        viewList(created_by, created_for);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todolist, menu);
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
        Intent intent = new Intent(todolist.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewList(created_by, created_for);

    }

    @Override
    public void onClick(View v) {


        switch(v.getId()){

            case R.id.bSave:

                    String task = todoTask1.getText().toString().trim();
                    String startTime = startlist1.getSelectedItem().toString();
                    String endTime = Endlist1.getSelectedItem().toString();
                    final SharedPreferences settings = getSharedPreferences("BabyLogin", Context.MODE_PRIVATE);
                    String created_by = settings.getString("keyName","");
                    Log.d("Save","Created_BY "+created_by);
                    if(task.equals("")) {
                        Toast.makeText(getApplicationContext(),"Please fill the task field!", Toast.LENGTH_SHORT).show();
                    }else{

                        addToList(task, startTime, endTime, created_for, created_by);
                        viewList(created_by, created_for);
                    }
                /*StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_NOTIFY, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.e("Response: ", s.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Error: ", volleyError.toString());
                    }
                }) {
                    //keyEmail
                    protected Map<String, String> getParams() {
                        // Posting parameters to URL_LOGIN
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("tag", "notificationTodoList");
                        params.put("task", todoTask1.getText().toString());
                        params.put("start", startlist1.getSelectedItem().toString());
                        params.put("username", sharedPreferences.getString("keyName", ""));
                        params.put("end", Endlist1.getSelectedItem().toString());
                         params.put("task", task);
                        params.put("startTime", startTime);
                        params.put("endTime", endTime);
                        params.put("created_for", created_for);
                        params.put("created_by",created_by);
                        return params;
                    }
                };
                AppController.getInstance().addToRequestQueue(stringRequest);*/
                break;



        }

    }

    private void addToList(final String task, final String startTime, final String endTime, final String created_for, final String created_by) {

        String tag_string_req = "req_list";

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Todo Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL

                        // Now store the user in sqlite
                        JSONObject task = jObj.getJSONObject("task");
                        String tasker = task.getString("task");
                        String startTimer = task.getString("startTime");
                        String endTimer = task.getString("endTime");
                        String created_by_user = task.getString("created_by");
                        String created_for_user = task.getString("created_for");

                        // user successfully logged in
                        // Create login session
                        session.showList(true, tasker, startTimer, endTimer, created_by_user);


                        // Inserting row in users table


                        // Launch login activity
                        /*Intent intent = new Intent(
                                todolist.this,
                                LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Successfully added to To-do-list", Toast.LENGTH_SHORT).show();
                        finish();*/
                        todoTask1.setText("");
                        startlist1.setSelection(0);
                        Endlist1.setSelection(0);
                        Toast.makeText(getApplicationContext(), "Successfully added to To-do-list", Toast.LENGTH_SHORT).show();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Todo Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "todo");
                params.put("task", task);
                params.put("startTime", startTime);
                params.put("endTime", endTime);
                params.put("created_for", created_for);
                params.put("created_by",created_by);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void viewList(final String created_by, final String created_for) {

       String tag_string_req = "req_list";

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Display Response: " + response.toString());




                try{
                    JSONArray jObj = new JSONArray(response);
                    if(jObj.length()==0){
                        Toast.makeText(getApplicationContext(),
                                "No Available Activity Yet!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    TableLayout list  = (TableLayout)findViewById(R.id.todolist);
                    int childCount = list.getChildCount();
                    if(childCount>1)
                        list.removeViews(1,childCount-1);
                    for(int i = 0; i < jObj.length() - 1; i++){

                        JSONObject json = jObj.getJSONObject(i);
                        TableRow row = new TableRow(todolist.this);

                        TextView start = new TextView(todolist.this);
                        start.setText(json.getString("startTime")+"-"+json.getString("endTime"));
                        row.addView(start);

                        TextView task = new TextView(todolist.this);
                        task.setText(json.getString("task"));
                        row.addView(task);
                        if(json.getString("finished").contentEquals("0")){
                            Button edit = new Button(todolist.this);
                            edit.setText("Edit");
                            final String todoId = json.getString("uid");
                            final String starttime = json.getString("startTime");
                            final String endtime = json.getString("endTime");
                            final String taskStr = json.getString("task");
                            edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(todolist.this, EditTodoActivity.class);
                                    Bundle extra = new Bundle();
                                    extra.putString("uid", todoId);
                                    extra.putString("startTime", starttime);
                                    extra.putString("endTime", endtime);
                                    extra.putString("task", taskStr);
                                    intent.putExtras(extra);
                                    startActivity(intent);
                                }
                            });
                            Button delete = new Button(todolist.this);
                            delete.setText("Delete");
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    HttpClient client = new DefaultHttpClient();
                                    HttpGet get = new HttpGet(AppConfig.URL_DELETE_TODO + "?todoId=" + todoId);
                                    try {
                                        HttpResponse response = client.execute(get);
                                        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                                        String s = "";
                                        while (true) {
                                            String temp = reader.readLine();
                                            if (temp == null)
                                                break;
                                            s += temp;
                                        }
                                        Toast.makeText(todolist.this, "Successfully deleted todo", Toast.LENGTH_SHORT).show();

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } finally {
                                        viewList(created_by, created_for);
                                    }
                                }
                            });
                            row.addView(edit);
                            row.addView(delete);
                        }
                        list.addView(row);
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Todo Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "ViewList");
                params.put("created_by", created_by);
                params.put("created_for",created_for);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }




}
