package com.example.willthespoderman.babynurtures.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.example.willthespoderman.babynurtures.helper.SessionManager;
import com.example.willthespoderman.babynurtures.models.Baby;
import com.example.willthespoderman.babynurtures.models.CustomListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class babylist extends AppCompatActivity {

    private static final String TAG =babylist.class.getSimpleName();
    ProgressDialog pDialog;
    ListView lv;
    CustomListAdapter customListAdapter;
    private List<Baby> babylist = new ArrayList<Baby>();
    SessionManager sessionManager;
    TextView noResult;
    Button register;
    String fullName, address, contactNum, email, subj, gradeHandle, gender;
    private Handler handler;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3) {
            Intent i = getIntent();
            finish();
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babylist);

        sessionManager = new SessionManager(getApplicationContext());


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        lv = (ListView) findViewById(R.id.list_of_baby);

        customListAdapter = new CustomListAdapter(babylist.this, babylist);
        lv.setAdapter(customListAdapter);

        noResult = (TextView) findViewById(R.id.NoResult);


        register = (Button) findViewById(R.id.btnRegister);

        final SharedPreferences settings = getSharedPreferences("BabyLogin", Context.MODE_PRIVATE);
        final String created_by = settings.getString("keyName", "");

        Toast.makeText(getApplicationContext(),
                created_by, Toast.LENGTH_LONG).show();
        handler = new Handler();
//
        displayBaby(created_by);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        babylist.this,
                        BabyRegister.class);
                startActivity(intent);


            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Baby index = babylist.get(position);
                String picture = index.getThumbnailUrl();
                String fullname = index.getBabyName();
                String bday = index.getBabyBirthday();
                String height = index.getBabyHeight();
                String weight = index.getBabyWeight();
                String nationality = index.getBabyNationality();
                String city = index.getBabyCity();
                String babyId = index.getBabyId();
                String country = index.getBabyCountry();
                String gender = index.getBabyGender();

                //Log.d("WEW",""+babyId+ " " + picture + " ," + " ," + fullname + " ," + bday + " ," + height + " ," + weight + " ," + nationality + " ," + city + " ," + country);
                Toast.makeText(getBaseContext(), babyId, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(babylist.this, profile.class);
                intent.putExtra("keyId", babyId);
                intent.putExtra("keyProfilePic", picture);
                intent.putExtra("keyFullname", fullname);
                intent.putExtra("keyBday", bday);
                intent.putExtra("keyHeight", height);
                intent.putExtra("keyWeight", weight);
                intent.putExtra("keyNationality", nationality);
                intent.putExtra("keyCity", city);intent.putExtra("keyCountry", country);
                intent.putExtra("keyGender", gender);
                intent.putExtra("babyId",babyId);
                startActivityForResult(intent, 3);
            }
        });


        //noResult.setText("No Registered Baby Yet!");

    }

    private void displayBaby(final String created_by) {

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Searching ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_BABY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Search Response: " + response.toString());
                hideDialog();

                try {

                    JSONArray jObjs = new JSONArray(response);
                   /* if(jObjs.length() == 0){


                        Toast.makeText(getApplicationContext(),
                                "No Result", Toast.LENGTH_LONG).show();
                    }else{*/

                    for(int i = 0; i < jObjs.length(); i++) {
                        JSONObject obj = jObjs.getJSONObject(i);
                        if(obj.isNull("firstname")){
                            Log.d(TAG, "Response: Your In!");
                            noResult.setText("No Registered Baby yet!");


                        }else{

                            Baby baby = new Baby();
                            fullName = obj.getString("firstname")+ " "+ obj.getString("middlename")+" "+obj.getString("lastname");
                            baby.setThumbnailUrl(obj.getString("profile_picture"));
                            baby.setBabyId(obj.getString("id"));
                            baby.setBabyName(fullName);
                            baby.setBabyBirthday(obj.getString("birthday"));
                            baby.setBabyHeight(obj.getString("height"));
                            baby.setBabyWeight(obj.getString("weight"));
                            baby.setBabyNationality(obj.getString("nationality"));
                            baby.setBabyGender(obj.getString("gender"));
                            baby.setBabyCity(obj.getString("city"));
                            baby.setBabyCountry(obj.getString("country"));

                            babylist.add(baby);
                            noResult.setText("");
                        }
                    }

                    customListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Search Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "displayBaby");
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
        getMenuInflater().inflate(R.menu.menu_babylist, menu);
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
