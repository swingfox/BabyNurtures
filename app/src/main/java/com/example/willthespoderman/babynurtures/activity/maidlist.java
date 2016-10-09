package com.example.willthespoderman.babynurtures.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.willthespoderman.babynurtures.R;
import com.example.willthespoderman.babynurtures.app.AppConfig;
import com.example.willthespoderman.babynurtures.app.AppController;
import com.example.willthespoderman.babynurtures.app.NotificationControl;
import com.example.willthespoderman.babynurtures.helper.SessionManager;
import com.example.willthespoderman.babynurtures.models.CustomListAdapter;
import com.example.willthespoderman.babynurtures.models.todo;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class maidlist extends AppCompatActivity {
    private static final String TAG = maidlist.class.getSimpleName();
    TextView starter, ender, tasker, babyname;
    //Button bClea;
    Button bApply;
    StringBuilder builderTask;
    StringBuilder builderStartTime;
    StringBuilder builderEndTime;
    private SessionManager session;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    ListView lv;
    CustomListAdapter customListAdapter;
    private List<todo> tutorList = new ArrayList<todo>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maidlist);

        babyname = (TextView) findViewById(R.id.babyName);
       // bClear = (Button) findViewById(R.id.bClear);
        bApply = (Button) findViewById(R.id.bApply);
        sharedPreferences = getSharedPreferences("BabyLogin", Context.MODE_PRIVATE);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());
        sharedPreferences = getSharedPreferences("BabyLogin", Context.MODE_PRIVATE);

        // Check if user is already logged in or not
        if (!session.isLoggedIn()) {
            logoutUser();
        }else{

            Map<String, ?> allEntries = sharedPreferences.getAll();
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            }
            String type = sharedPreferences.getString("keyType","");
            if(type.equals("1")) {


                String created_by = sharedPreferences.getString("keyName", "");
                String created_for = getIntent().getExtras().getString("keyFullname");
                babyname.setText("For " + getIntent().getExtras().getString("keyFullname"));
                viewList(created_by, created_for);
            }
            if(type.equals("0")){

                String created_by = getIntent().getExtras().getString("keyName");
                String created_for = getIntent().getExtras().getString("keyFullname");
                babyname.setText("For " + getIntent().getExtras().getString("keyFullname"));
                viewList(created_by, created_for);
                Log.d(TAG, " created by " + created_by + " created for" +created_for);

            }



        }



        /*bClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String created_by = getIntent().getExtras().getString("keyName");
                wipeData(created_by);

            }
        });*/





        bApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(maidlist.this);

                builder.setMessage("Send Feedback?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // CODE ADDED by Ian
                        String sender = sharedPreferences.getString("keyName", "");
                        EditText messageBox = ((EditText)findViewById(R.id.todoSend));
                        String message = "Feedback"+ messageBox.getText().toString();
                        NotificationControl.sendNotificationSender(message, sender);
                        Toast.makeText(maidlist.this, "Feedback successfully sent!", Toast.LENGTH_SHORT).show();
                        messageBox.setText("");
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.create().show();
            }
        });


    }

    private void wipeData(final String created_by) {

        String tag_string_req = "req_wipe";

        pDialog.setMessage("Please Wait...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_TODO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.d(TAG, "Display Response: " + response.toString());
                hideDialog();



                try{
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        // Launch main activity

                        Toast.makeText(getApplicationContext(), "Successfully Deleted All Task!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        starter.setText(builderStartTime.toString());
                        ender.setText(builderEndTime.toString());
                        tasker.setText(builderTask.toString());

                    }
                    //ender.setText(builder.toString());
                    //  tasker.setText(builder.toString());


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
                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "DeleteList");
                params.put("created_by",created_by);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


    }

    private void viewList(final String created_by, final String created_for) {

        String tag_string_req = "req_list";


        pDialog.setMessage("Loading Please Wait ...");
        showDialog();




        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Display Response: " + response.toString());
                hideDialog();



                try{
                    JSONArray jObj = new JSONArray(response);
                    if(jObj.length()==0){
                       Toast.makeText(getApplicationContext(),
                                "No Available Activitie Yet!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    TableLayout list  = (TableLayout)findViewById(R.id.todolist);
                    int childCount = list.getChildCount();
                    if(childCount>1)
                        list.removeViews(1,childCount-1);
                    for(int i = 0; i < jObj.length() - 1; i++){

                        JSONObject json = jObj.getJSONObject(i);
                        TableRow row = new TableRow(maidlist.this);

                        TextView start = new TextView(maidlist.this);
                        start.setText(json.getString("startTime")+"-"+json.getString("endTime"));
                        row.addView(start);

                        TextView task = new TextView(maidlist.this);
                        task.setText(json.getString("task"));
                        row.addView(task);

                        if(json.getString("finished").contentEquals("0")){
                            Button button = new Button(maidlist.this);
                            button.setText("Done");
                            final String todoId = json.getString("uid");
                            final String taskFinished = json.getString("task");
                            final String sender = sharedPreferences.getString("keyName", "");
                                    //task.getText().toString();
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Update database that this particular todo is finished using todo id reference
                                    try {
                                        HttpClient client = new DefaultHttpClient();
                                        HttpPost post = new HttpPost(AppConfig.URL_FINISH_TODO);
                                        List <NameValuePair> nvps = new ArrayList <>();
                                        nvps.add(new BasicNameValuePair("todoId", todoId));
                                        nvps.add(new BasicNameValuePair("task", taskFinished));
                                        nvps.add(new BasicNameValuePair("receiver", created_by));
                                        nvps.add(new BasicNameValuePair("sender", sender));

                                        post.setEntity(new UrlEncodedFormEntity(nvps));
                                        HttpResponse response = client.execute(post);
                                        InputStream body = response.getEntity().getContent();
                                        BufferedReader br = new BufferedReader(new InputStreamReader(body));
                                        String line = "";
                                        String result = "";
                                        while((line=br.readLine())!=null){
                                            result += line;
                                        }
                                        Toast.makeText(maidlist.this,result,Toast.LENGTH_SHORT).show();
                                        viewList(created_by,created_for);

                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    } catch (ClientProtocolException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                            row.addView(button);
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
                hideDialog();

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



    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maidlist, menu);
        return true;
    }
    private void logoutUser() {
        session.setLogin(false, "" ,"","","");



        // Launching the login activity
        Intent intent = new Intent(maidlist.this, LoginActivity.class);
        startActivity(intent);
        finish();
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
