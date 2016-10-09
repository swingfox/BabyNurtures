package com.example.willthespoderman.babynurtures.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.willthespoderman.babynurtures.R;
import com.example.willthespoderman.babynurtures.app.AppConfig;
import com.example.willthespoderman.babynurtures.app.AppController;
import com.example.willthespoderman.babynurtures.app.NotificationBackgroundTask;
import com.example.willthespoderman.babynurtures.app.NotificationControl;
import com.example.willthespoderman.babynurtures.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.willthespoderman.babynurtures.R.drawable.v;

public class MainActivity extends Activity implements View.OnClickListener{

    private TextView txtName;
    private TextView txtEmail;
    private ImageView btnMenu;

    AlertDialog levelDialog;
    SessionManager sessionManager;


    private SessionManager session;

    NotificationBackgroundTask task;

    @Override
    protected void onResume() {
        Bundle extras = getIntent().getExtras();
    //    if(getIntent().hasExtra("messages")){
      //      String[] messages = this.getIntent().getExtras().getStringArray("messages");
        //    if(messages!=null && messages.length>0) {
                NotificationControl.clearNotifications(MainActivity.this);
                String username = this.getSharedPreferences("BabyLogin", Context.MODE_PRIVATE).getString("keyName", "");
                if(username!=null)
                    NotificationControl.seenNotifications(username);
          //  }
        //}
            super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: GET notifications
        if(getIntent().getExtras()!=null) {
            String username = getIntent().getExtras().getString("username");
            task = new NotificationBackgroundTask(username,MainActivity.this,
                    MainActivity.class
                    //Notification.class
                    );
            task.execute(username);
        }
        txtName = (TextView) findViewById(R.id.username);
        txtEmail = (TextView) findViewById(R.id.email);
        btnMenu = (ImageView) findViewById(R.id.btnMenu);
        ImageView Notification = (ImageView) findViewById(R.id.Notification);
        ImageView Profile = (ImageView) findViewById(R.id.Profile);
        ImageView message = (ImageView) findViewById(R.id.message);
        //ImageView Status = (ImageView) findViewById(R.id.Status);
        ImageView Todolist = (ImageView) findViewById(R.id.Todolist);

        // session manager
        session = new SessionManager(getApplicationContext());


        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Logout button click event
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String names[] = {"Create New Baby Sitter Account","History","Logout" };

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.custom, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle("Menu Option");
                ListView lv = (ListView) convertView.findViewById(R.id.listView1);
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, names);
                lv.setAdapter(adapter);

                final Intent intent = new Intent(MainActivity.this, Babysitter_Register.class);
                final SharedPreferences sharedPreferences = getSharedPreferences("BabyLogin", Context.MODE_PRIVATE);
                final String firstTime = sharedPreferences.getString("keyFirstTime","");

                if(firstTime.equals("Yes")){
                    names[0] = "Create New Baby Sitter Account";

                /*}else {

                    names[0] = "Edit BabySitter Account";
*/

                }

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String item = (String) adapter.getItem(position);
                        Toast.makeText(MainActivity.this, item + " Selected", Toast.LENGTH_LONG).show();


                        if (item == names[2]) { // LOGOUT

                            logoutUser();
                            finish();

                        }
                        else if (item == names[1]) { // HISTORY

                            startActivity(new Intent(MainActivity.this,History.class));

                        }
                        else if(item == names[0]&& names[0].equals("Create New Baby Sitter Account")){
                            intent.putExtra("keyUser",sharedPreferences.getString("keyName",""));
                            startActivity(intent);
                        }else{
                            adapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, names[0] + "  Updated", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                levelDialog = alertDialog.create();
                levelDialog.show();
            }
        });


        Notification.setOnClickListener(this);
        Profile.setOnClickListener(this);
        message.setOnClickListener(this);
        //Status.setOnClickListener(this);
        Todolist.setOnClickListener(this);
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false,"", "","","");
        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        if(task!=null && !task.isCancelled())
            task.cancel(true);

        super.finish();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {

            case R.id.Notification:
                startActivity(new Intent(this, Notification.class));
                break;

            case R.id.Profile:
                // startActivity(new Intent(this, babylist.class));
                final String names[] = {"Parent", "Baby Sitter", "Baby Profile"};

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.custom, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle("Menu Option");
                ListView lv = (ListView) convertView.findViewById(R.id.listView1);
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, names);
                lv.setAdapter(adapter);

                final Intent intent = new Intent(MainActivity.this, babylist.class);
                final SharedPreferences sharedPreferences = getSharedPreferences("BabyLogin", Context.MODE_PRIVATE);
                final String firstTime = sharedPreferences.getString("keyFirstTime", "");

                if (firstTime.equals("Yes")) {
                    names[0] = "Create New Baby Sitter Account";



                /*}else {

                    names[0] = "Edit BabySitter Account";
*/

                }

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String item = (String) adapter.getItem(position);
                        Toast.makeText(MainActivity.this, item + " Selected", Toast.LENGTH_LONG).show();


                        if (item == names[2]) { // LOGOUT
                            startActivity(new Intent(MainActivity.this, babylist.class));
                            //       logoutUser();
                            //       finish();

                        } else if (item == names[1]) { // HISTORY

                            startActivity(new Intent(MainActivity.this, History.class));

                        } else if (item == names[0] && names[0].equals("Baby Profile")) {
                            //  intent.putExtra("keyUser",sharedPreferences.getString("keyName",""));
                            startActivity(intent);
                        } else {
                            adapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, names[0] + "  Updated", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                levelDialog = alertDialog.create();
                levelDialog.show();


                break;

            case R.id.Todolist:
                startActivity(new Intent(this, Choose_Child.class));
                break;


            case R.id.message:
                startActivity(new Intent(this, MessageActivity.class));
                break;


//            case R.id.Status:
//                startActivity(new Intent(this, Choose_Child_Task.class));
//                break;
        }

    }

    public static class RegisterActivity extends Activity {
        private static final String TAG = RegisterActivity.class.getSimpleName();
        private Button btnRegister;
        private TextView btnLinkToLogin;
        private EditText inputUsername;
        private EditText inputEmail;
        private EditText inputPassword;

        private ProgressDialog pDialog;
        private SessionManager session;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            inputUsername = (EditText) findViewById(R.id.username);
            inputEmail = (EditText) findViewById(R.id.email);
            inputPassword = (EditText) findViewById(R.id.password);

            btnRegister = (Button) findViewById(R.id.btnRegister);
            btnLinkToLogin = (TextView) findViewById(R.id.btnLinkToLoginScreen);

            // Progress dialog
            pDialog = new ProgressDialog(this);
            pDialog.setCancelable(false);

            // Session manager
            session = new SessionManager(getApplicationContext());
            SharedPreferences sharedPreferences = getSharedPreferences("BabyLogin", Context.MODE_PRIVATE);
            String type = sharedPreferences.getString("keyType","");
            // Check if user is already logged in or not
            if (session.isLoggedIn()&& type.equals("1")) {
                // User is already logged in. Take him to main activity
                Intent intent = new Intent(RegisterActivity.this,
                        Notification.class);
                startActivity(intent);
                finish();
            }

            // Register Button Click event
            btnRegister.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    String username = inputUsername.getText().toString();
                    String email = inputEmail.getText().toString();
                    String password = inputPassword.getText().toString();


                    if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                        registerUser(username, email, password);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
                    }
                }
            });

            // Link to Login Screen
            btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(),
                            LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            });

        }

        /**
         * Function to store user in MySQL database will post params(tag, name,
         * email, password) to register url
         * */
        private void registerUser(final String username, final String email,
                                  final String password) {
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

                            JSONObject json = jObj.getJSONObject("user");
                            String email = json.getString("email");
                            String username = json.getString("username");
                            String firstTime = json.getString("first_time");
                            String type = json.getString("type");
                            // user successfully logged in
                            // Create login session
                            session.setLogin(true, username, email, firstTime, type);
                            // Launch login activity
                            Intent intent = new Intent(
                                    RegisterActivity.this,
                                    LoginActivity.class);
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
                    params.put("tag", "register");
                    params.put("email", email);
                    params.put("password", password);
                    params.put("username", username);


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
}

