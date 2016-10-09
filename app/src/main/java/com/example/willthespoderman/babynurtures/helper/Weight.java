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

import com.example.willthespoderman.babynurtures.R;
import com.example.willthespoderman.babynurtures.activity.BabyRegister;
import com.example.willthespoderman.babynurtures.activity.EditProfile;

import java.util.ArrayList;

/**
 * Created by edmore on 11/11/2015.
 */
public class Weight extends DialogFragment implements AdapterView.OnItemClickListener {

    private ArrayList<String> list = new ArrayList<String>();
    ListView mylist;
    private ArrayAdapter<String> adapter;
    private SharedPreferences sharedPreferences;
    String[] weight = new String[]{"6.5 - 7.8 pounds","6.7 - 8.1 pounds","18.9 - 19.8 inches","11.8 - 14.0 pounds","13.0 - 15.2 pounds","14.8 - 17.5 pounds","16.2 - 18.8 pounds","16.7 - 19.7 pounds","18.2 - 21.1 pounds","18.2 - 21.4 pounds","19.8 - 22.9 pounds","19.5 - 23.0 pounds","21.1  - 24.5 pounds","20.8 - 24.5 pounds","22.4 - 26.0 pounds","22.0 - 26.0 pounds","23.6 - 27.5 pounds","23.3 - 27.5 pounds","24.8 - 28.9 pounds","25.8 - 30.0 pounds","27.0 - 31.2 pounds ","26.7 - 31.1 pounds","27.8 - 32.2 pounds","27.6 - 32.3 pounds","28.6 - 33.2 pounds","28.4 - 33.4 pounds","29.5 - 34.3 pounds","32.2 - 38.5 pounds","33.3 - 39.1 pounds","36.3 - 44.0 pounds","37.5 - 44.7 pounds"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Select Weight");
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
                android.R.layout.simple_list_item_1, weight);
        mylist.setAdapter(adapter);
        mylist.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dismiss();
        if (sharedPreferences.getString("keyNationality", "").equals("editProfileWeight")) {
            EditProfile.inputWeight.setText(adapter.getItem(position));
        } else {
            BabyRegister.inputWeight.setText(adapter.getItem(position));
        }
    }
}
