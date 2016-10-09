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
public class Height extends DialogFragment implements AdapterView.OnItemClickListener {

    private ArrayList<String> list = new ArrayList<String>();
    ListView mylist;
    private ArrayAdapter<String> adapter;
    String[] height = new String[]{"18.9 - 19.8 inches","19.1 - 20.1 inches","23.0 - 24.1 inches","23.6 - 24.7 inches","25.3 - 26.5 inches","26.1 - 27.2 inches","27.0 - 28.3 inches","27.7 - 28.9 inches","28.5 - 29.8 inches","29.2 - 30.5 inches","29.9 - 31.2 inches","30.5 - 31.8 inches","31.0 - 32.5 inches","31.7 - 33.1 inches","32.1 - 33.8 inches","32.7 - 34.3 inches","33.2 - 34.9 inches","33.8 - 35.4 inches","33.7 - 35.6 inches","34.1 - 36.1 inches","34.6 - 36.6 inches","35.0 - 37.0 inches","35.4 - 37.4 inches","35.8 - 37.8 inches","36.0 - 38.1 inches","36.5 - 38.6 inches","38.6 - 41.0 inches","39.2 - 41.5 inches"
            };
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Select Height");
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
                android.R.layout.simple_list_item_1, height);
        mylist.setAdapter(adapter);
        mylist.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dismiss();
        if (sharedPreferences.getString("keyNationality", "").equals("editProfileHeight")) {
            EditProfile.inputHeight.setText(adapter.getItem(position));
        } else {
            BabyRegister.inputHeight.setText(adapter.getItem(position));
        }
    }
}
