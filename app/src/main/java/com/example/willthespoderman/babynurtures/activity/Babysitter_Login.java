package com.example.willthespoderman.babynurtures.activity;

/**
 * Created by WILL THE SPODERMAN on 8/19/2015.
 */
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.willthespoderman.babynurtures.R;
import com.example.willthespoderman.babynurtures.app.AppConfig;
import com.example.willthespoderman.babynurtures.app.AppController;
import com.example.willthespoderman.babynurtures.app.NotificationControl;
import com.example.willthespoderman.babynurtures.helper.SessionManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Babysitter_Login extends Activity {
    // LogCat tag
    private static final String TAG = MainActivity.RegisterActivity.class.getSimpleName();
    private Button btnLogin;
    private TextView btnLinkToRegister;
    private EditText inputUsername;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private NotificationManager notificationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter__login);

        inputUsername = (EditText) findViewById(R.id.Sitterusername);
        inputPassword = (EditText) findViewById(R.id.Sitterpassword);
        btnLogin = (Button) findViewById(R.id.btn_submit);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("BabyLogin", Context.MODE_PRIVATE);
        String type = sharedPreferences.getString("keyType","");
        // Check if user is already logged in or not
        if (session.isLoggedIn()&& type.equals("0")) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Babysitter_Login.this, Choose_Child_Task.class);
            startActivity(intent);
            finish();
        }
        notificationManager = (NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String username = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();

                // Check for empty data in the form
                if (username.trim().length() > 0 && password.trim().length() > 0) {
                    checkLogin(username, password);
                    AppConfig.subscribeWithEmail(username, "Babysitter");
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

    }


    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String username, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    // Check for error node in json
                    if (!error) {
                        JSONObject json = jObj.getJSONObject("user");
                        String username = json.getString("name");
                        String type = json.getString("type");
                        String createdBy = json.getString("created_by");
                        //boolean flag = json.getBoolean("flag");
                        //Toast.makeText(getApplicationContext(),"FLAG: " + flag, Toast.LENGTH_LONG).show();
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true, username, "","",type);
                        /**
                         * Get the parent username
                         */
                        session.whoCreatedTheBabySitter(createdBy);
                        Log.e("TAG", createdBy);

                        // Launch main activity
                        Intent intent = new Intent(Babysitter_Login.this,
                                Babysitter_Main.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        // TODO: Add user information to next Activity
                        Bundle bundle = new Bundle();
                        bundle.putString("username", username);
                        intent.putExtras(bundle);
                        //
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Successfully login!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {


                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");

                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                if(error.getMessage() == null){
                    String errorMsg;
                    errorMsg = "Unable to reach server! Server offline!";
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_LONG).show();
                    hideDialog();
                }else{
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();}
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "loginSitter");
                params.put("username", username);
                params.put("password", password);

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
}
