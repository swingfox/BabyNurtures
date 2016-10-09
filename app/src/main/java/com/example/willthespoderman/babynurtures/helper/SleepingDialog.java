package com.example.willthespoderman.babynurtures.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.willthespoderman.babynurtures.R;
import com.example.willthespoderman.babynurtures.app.AppConfig;
import com.example.willthespoderman.babynurtures.app.AppController;
import com.example.willthespoderman.babynurtures.app.NotificationControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by edmore on 11/13/2015.
 */
public class SleepingDialog extends DialogFragment implements AdapterView.OnItemClickListener {

    private ArrayList<String> list = new ArrayList<String>();
    ListView mylist;
    private ArrayAdapter<String> adapter;
    SharedPreferences sharedPreferences;
    String[] sleeping = new String[]{"Baby Sleeping"," Baby Wake-up"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Select the List");
        sharedPreferences = getActivity().getSharedPreferences("BabyLogin", Context.MODE_PRIVATE);
        View view = inflater.inflate(R.layout.dialogfragment, null, false);
        mylist = (ListView) view.findViewById(R.id.list);

//       getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Log.e("Wew", "Pass");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, sleeping);
        mylist.setAdapter(adapter);
        mylist.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        dismiss();
        /*BabyRegister.inputNationality.setText(adapter.getItem(position));*/
        dismiss();
        String sender = sharedPreferences.getString("keyName", "");
        String message = adapter.getItem(position);
        if(NotificationControl.sendNotificationSender(message, sender)){
            Toast.makeText(this.getContext(),"Successfully send notification",Toast.LENGTH_SHORT).show();
        }
        /*
        Toast.makeText(getActivity().getApplicationContext(), adapter.getItem(position) + sharedPreferences.getString("keyName", ""), Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_NOTIFY, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

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
                params.put("tag", "notificationActivities");
                params.put("username", sharedPreferences.getString("keyName", ""));
                params.put("toParent", sharedPreferences.getString("keyWhoCreated", ""));
                params.put("message", adapter.getItem(position));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);*/
    }
}
