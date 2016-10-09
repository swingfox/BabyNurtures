package com.example.willthespoderman.babynurtures.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.willthespoderman.babynurtures.R;
import com.example.willthespoderman.babynurtures.app.AppConfig;
import com.example.willthespoderman.babynurtures.app.AppController;
import com.example.willthespoderman.babynurtures.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Babysitter_Register extends AppCompatActivity {
    private static final String TAG = Babysitter_Register.class.getSimpleName();

    ProgressDialog pDialog;
    String created_by;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter__register);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        sessionManager = new SessionManager(getApplicationContext());
        final EditText bsPassword = (EditText) findViewById(R.id.BSpassword);
        final EditText bsName = (EditText) findViewById(R.id.BSname);

        Button btnReg = (Button) findViewById(R.id.btnReg);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = bsPassword.getText().toString();
                String name = bsName.getText().toString();
                created_by = getIntent().getExtras().getString("keyUser");
                Log.d(TAG, " " + created_by + "   " + password + "   " + name);

                if(!name.isEmpty() && !password.isEmpty()){
                    sharedPreferences = getSharedPreferences("BabyLogin",MODE_PRIVATE);
                    String userName = sharedPreferences.getString("keyName","");
                    createBabySitter(name,password,created_by, userName);
                }else{
                    Toast.makeText(getApplicationContext(), "Please Fill All Details!", Toast.LENGTH_SHORT).show();
                }

            }
        });





    }

    private void createBabySitter(final String username, final String password, final String created_by, final String mainUser) {

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        sessionManager.setLogin(false,"", "","","");

                        JSONObject json = jObj.getJSONObject("test");
                        String username = json.getString("username");
                        String email = json.getString("email");
                        String firstTime = json.getString("first_time");
                        String type = json.getString("type");

                        sessionManager.setLogin(true,username,email,firstTime,type);


                        // Launch login activity
                        Intent intent = new Intent(
                                Babysitter_Register.this,
                                MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Successfully Registered!", Toast.LENGTH_LONG).show();
                        finish();
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
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "babySitregister");
                params.put("username", username);
                params.put("password", password);
                params.put("created_by",created_by);
                params.put("mainUser",mainUser);

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
        getMenuInflater().inflate(R.menu.menu_babysitter__register, menu);
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
