package com.example.willthespoderman.babynurtures.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
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

public class EditProfile extends AppCompatActivity implements View.OnClickListener, DateDialog.TheListener {

    private static final String TAG = EditProfile.class.getSimpleName();
    Button btnEditProfile;
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
    private String Fullname[];
    private SessionManager sessionManager;
    private Button updateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

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
        btnEditProfile = (Button) findViewById(R.id.btnEditProfile);
        sessionManager = new SessionManager(getApplicationContext());
        inputBirthday.setText(getIntent().getExtras().getString("keyBday"));
        inputHeight.setText(getIntent().getExtras().getString("keyHeight"));
        inputNationality.setText(getIntent().getExtras().getString("keyNationality"));
        inputGender.setText(getIntent().getExtras().getString("keyGender"));
        inputWeight.setText(getIntent().getExtras().getString("keyWeight"));
        inputCountry.setText(getIntent().getExtras().getString("keyCountry"));
        inputCity.setText(getIntent().getExtras().getString("keyCity"));

        String fullname = getIntent().getExtras().getString("keyFullname");
        Fullname = fullname.split(" ");
        inputFirstName.setText(Fullname[0]);
        inputMiddleName.setText(Fullname[1]);
        inputLastName.setText(Fullname[2]);


        inputCountry.setOnClickListener(this);
        inputCity.setOnClickListener(this);
        btnEditProfile.setOnClickListener(this);
        inputGender.setOnClickListener(this);
        inputBirthday.setOnClickListener(this);
        inputNationality.setOnClickListener(this);
        inputHeight.setOnClickListener(this);
        inputWeight.setOnClickListener(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());


//        btnEditProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String fname = inputFirstName.getText().toString();
//                String midname = inputMiddleName.getText().toString();
//                String lname = inputLastName.getText().toString();
//                String birthday = inputBirthday.getText().toString();
//                String height = inputHeight.getText().toString();
//                String weight = inputWeight.getText().toString();
//                String nationality = inputNationality.getText().toString();
//                //String country = inputCountry.getText().toString();
//                String city = inputCity.getText().toString();
//                String gender = inputGender.getText().toString();
//
////               final SharedPreferences settings = getSharedPreferences("BabyLogin", Context.MODE_PRIVATE);
////                String created_by = settings.getString("keyName", "");
////
////                if (!fname.isEmpty() && !midname.isEmpty() && !lname.isEmpty() && !birthday.isEmpty() && !height.isEmpty() && !weight.isEmpty() && !nationality.isEmpty() && !gender.isEmpty() && !country.isEmpty() && !city.isEmpty()) {
////                    updateprofile(fname, midname, lname, birthday, height, weight, nationality, gender, country, city, created_by);
////                } else {
////                    Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
////                }
//            }
//        });


  }

    public void showDateDialog(View v) {
        FragmentManager manager = getSupportFragmentManager();
        DateDialog dialog = new DateDialog();
        dialog.show(manager, "dialog");
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

    public void updateprofile(final String fname, final String midname, final String lname, final String birthday,
                              final String height, final String weight, final String nationality,final String gender, final String country, final String city, final String created_by){

        String tag_string_req = "req_register";

        pDialog.setMessage("Update Profile ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_UPDATEPROFILE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Update Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite

//                    String uid = jObj.getString("uid");

//                        JSONObject user = jObj.getJSONObject("user");
//                        String email = user.getString("email");
//                        String name = user.getString("name");
//                        String birthday = user.getString("birthday");
//                        String height = user.getString("height");
//                        String weight = user.getString("weight");
//                        String nationality = user.getString("nationality");
//                        String country = user.getString("country");
//                        String city = user.getString("city");
//                        String babysitter = user.getString("babysitter");
//                        String created_at = user.getString("created_at");


                        // Inserting row in users table
                        //db.addUser(name, email, birthday, height, weight, nationality, country, city, babysitter, uid, created_at);

                        // Launch login activity
                        Intent intent = new Intent(
                                EditProfile.this,
                                MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Successfully Update Profile!", Toast.LENGTH_LONG).show();
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
    public void onClick(View v) {


        if (v.getId() == R.id.city) {
            showCityDialog(v);
            sessionManager.isNationality("editProfileCity");
        }
        if (v.getId() == R.id.nationality) {
            showNationalityDialog(v);
            sessionManager.isNationality("editProfileNationality");
        }
        if (v.getId() == R.id.country) {
            showCountryDialog(v);
            sessionManager.isNationality("editProfileCountry");
        }
        if (v.getId() == R.id.weight) {
            showWeightDialog(v);
            sessionManager.isNationality("editProfileWeight");
        }
        if (v.getId() == R.id.height) {
            showHeightDialog(v);
            sessionManager.isNationality("editProfileHeight");
        }
        if (v.getId() == R.id.bday) {
            showDateDialog(v);
        }

        if (v.getId() == R.id.gender) {
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

        if (v.getId() == R.id.btnEditProfile) {
            StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_UPDATEPROFILE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e(TAG, "Update Response: " + response.toString());
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean error = jsonObject.getBoolean("error");
                        if (!error) {
                            Intent i = new Intent();
                            i.putExtra("res", "value");
                            i.putExtra("fname", jsonObject.getString("fname"));
//                            setResult(Activity.RESULT_OK, i);
//                            Intent i = getIntent();
                            setResult(3, i);
                            finish();
                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "EditProfile Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("tag", "updateprofile");
                    params.put("firstname", inputFirstName.getText().toString());
                    params.put("middlename", inputMiddleName.getText().toString());
                    params.put("lastname", inputLastName.getText().toString());
                    params.put("birthday", inputBirthday.getText().toString());
                    params.put("height", inputHeight.getText().toString());
                    params.put("weight", inputWeight.getText().toString());
                    params.put("nationality", inputNationality.getText().toString());
                    params.put("gender", inputGender.getText().toString());
                    params.put("country", inputCountry.getText().toString());
                    params.put("city", inputCity.getText().toString());
                    params.put("id", getIntent().getExtras().getString("keyId"));
                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, "cancel");
        }

    }

    @Override
    public void returnDate(String month, String date, String year) {
        inputBirthday.setText(month+"-"+date+"-"+year);
    }

}
