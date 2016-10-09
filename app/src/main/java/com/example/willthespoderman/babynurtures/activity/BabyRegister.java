package com.example.willthespoderman.babynurtures.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
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
import com.example.willthespoderman.babynurtures.helper.CityDialog;
import com.example.willthespoderman.babynurtures.helper.Country;
import com.example.willthespoderman.babynurtures.helper.DateDialog;
import com.example.willthespoderman.babynurtures.helper.Height;
import com.example.willthespoderman.babynurtures.helper.Nationality;
import com.example.willthespoderman.babynurtures.helper.SessionManager;
import com.example.willthespoderman.babynurtures.helper.Weight;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BabyRegister extends AppCompatActivity implements View.OnClickListener, DateDialog.TheListener {

    private static final String TAG = BabyRegister.class.getSimpleName();
    Button register;
    private EditText inputFirstName;
    private EditText inputMiddleName;
    private EditText inputLastName;
    private EditText inputBirthday;
    public static EditText inputHeight;
    public static EditText inputWeight;
    public  static EditText inputNationality;
    public static EditText inputCountry;
    public static EditText inputCity;
    private EditText inputGender;
    private SessionManager session;
    private ProgressDialog pDialog;
    private SessionManager sessionManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_register);

        inputFirstName = (EditText) findViewById(R.id.firstname);
        inputMiddleName = (EditText) findViewById(R.id.middlename);
        inputLastName = (EditText) findViewById(R.id.lastname);
        inputBirthday = (EditText) findViewById(R.id.bday);
        inputHeight = (EditText) findViewById(R.id.height);
        inputWeight = (EditText) findViewById(R.id.weight);
        inputNationality = (EditText) findViewById(R.id.nationality);
        inputCountry = (EditText) findViewById(R.id.country);
        inputCity = (EditText) findViewById(R.id.city);
        inputGender = (EditText) findViewById(R.id.gender);
        register = (Button) findViewById(R.id.btnRegister);
        sessionManager = new SessionManager(getApplicationContext());
        inputCountry.setOnClickListener(this);
        inputCity.setOnClickListener(this);
        inputGender.setOnClickListener(this);
        inputBirthday.setOnClickListener(this);
        inputNationality.setOnClickListener(this);
        inputHeight.setOnClickListener(this);
        inputWeight.setOnClickListener(this);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = inputFirstName.getText().toString();
                String midname = inputMiddleName.getText().toString();
                String lname = inputLastName.getText().toString();
                String birthday = inputBirthday.getText().toString();
                String height = inputHeight.getText().toString();
                String weight = inputWeight.getText().toString();
                String nationality = inputNationality.getText().toString();
                String country = inputCountry.getText().toString();
                String city = inputCity.getText().toString();
                String gender = inputGender.getText().toString();


                final SharedPreferences settings = getSharedPreferences("BabyLogin", Context.MODE_PRIVATE);
                String created_by = settings.getString("keyName", "");

                if (!fname.isEmpty() && !midname.isEmpty() && !lname.isEmpty() && !birthday.isEmpty() && !height.isEmpty() && !weight.isEmpty() && !nationality.isEmpty() && !gender.isEmpty() && !country.isEmpty() && !city.isEmpty()) {
                    registerUser(fname, midname, lname, birthday, height, weight, nationality, gender, country, city, created_by);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    /**
     * Display date picker in dialog
     * @param v
     */
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DateDialog();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showNationalityDialog(View v) {
        FragmentManager manager = getSupportFragmentManager();
        Nationality dialog = new Nationality();
        dialog.show(manager, "dialog");
    }

    public void showCityDialog(View v) {
        FragmentManager manager = getSupportFragmentManager();
        CityDialog dialog = new CityDialog();
        dialog.show(manager, "dialog");
    }

    public void showCountryDialog(View v) {
        FragmentManager manager = getSupportFragmentManager();
        Country dialog = new Country();
        dialog.show(manager, "dialog");
    }

    public void showWeightDialog(View v) {
        FragmentManager manager = getSupportFragmentManager();
        Weight dialog = new Weight();
        dialog.show(manager, "dialog");
    }

    public void showHeightDialog(View v) {
        FragmentManager manager = getSupportFragmentManager();
        Height dialog = new Height();
        dialog.show(manager, "dialog");
    }

    public void registerUser(final String fname, final String midname, final String lname, final String birthday,
                             final String height, final String weight, final String nationality,final String gender, final String country, final String city, final String created_by){

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        /*String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String email = user.getString("email");
                        String name = user.getString("name");
                        String birthday = user.getString("birthday");
                        String height = user.getString("height");
                        String weight = user.getString("weight");
                        String nationality = user.getString("nationality");
                        String country = user.getString("country");
                        String city = user.getString("city");
                        String babysitter = user.getString("babysitter");
                        String created_at = user.getString("created_at");*/

                        // Inserting row in users table
                        //db.addUser(name, email, birthday, height, weight, nationality, country, city, babysitter, uid, created_at);

                       // Launch login activity
                        Intent intent = new Intent(
                                BabyRegister.this,
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
                params.put("tag", "registerBaby");
                params.put("firstname", fname);
                params.put("middlename", midname);
                params.put("lastname", lname);
                params.put("birthday", birthday);
                params.put("height", height);
                params.put("weight", weight);
                params.put("nationality", nationality);
                params.put("gender", gender);
                params.put("country", country);
                params.put("city", city);
                params.put("created_by", created_by);


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
        getMenuInflater().inflate(R.menu.menu_baby_register, menu);
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



        if (v.getId() == R.id.bday) {
            showDatePickerDialog(v);
        }
        if (v.getId() == R.id.city) {
            showCityDialog(v);
            sessionManager.isNationality("babyRegisterCity");
        }
        if (v.getId() == R.id.nationality) {
            showNationalityDialog(v);
            sessionManager.isNationality("babyRegisterNationality");
        }
        if (v.getId() == R.id.country) {
            showCountryDialog(v);
            sessionManager.isNationality("babyRegisterCountry");
        }
        if (v.getId() == R.id.weight) {
            showWeightDialog(v);
            sessionManager.isNationality("babyRegisterWeight");
        }
        if (v.getId() == R.id.height) {
            showHeightDialog(v);
            sessionManager.isNationality("babyRegisterHeight");
        }
        else if(v.getId()==R.id.gender){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Subjects");
            final CharSequence[] items = {"Male", "Female"};
            final boolean[] states = {false, false};
            builder.setTitle("Select Gender");
            builder.setSingleChoiceItems(items, 2, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    SparseBooleanArray CheCked = ((AlertDialog) dialog).getListView().getCheckedItemPositions();
                    if (CheCked.get(0)) {
                        inputGender.setText("Male");
                    }
                    if (CheCked.get(1)) {
                        inputGender.setText("Female");
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    dialog.cancel();
                }
            });
            builder.create().show();
        }

    }

    @Override
    public void returnDate(String month, String date, String year) {
        inputBirthday.setText(month + "-" + date + "-" + year);
    }
}
