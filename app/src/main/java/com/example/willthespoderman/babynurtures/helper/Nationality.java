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



public class Nationality extends DialogFragment implements AdapterView.OnItemClickListener {
    private ArrayList<String> list = new ArrayList<String>();
    ListView mylist;
    private ArrayAdapter<String> adapter;
    private SharedPreferences sharedPreferences;

    String[] nationality = new String[]{"Afghans","Algerians","Americans","Argentines","Australians","Barbadians","Belgians","British","Bulgarians","Canadians","Chinese","Colombians","Cubans","Dominicans (Republic)","Dominicans (Commonwealth)","Dutch","Egyptians","English","Estonians","Filipinos","French citizens","Georgians","Germans","Ghanaians","Greeks","Grenadians","Guatemalans","Guinea-Bissau nationals","Guyanese","DHaitians","Hondurans","Hong Kong","Hungarians","Icelanders","Indians","Indonesians","Iranians (Persians)","Iraqis","Irish","Israelis",
            "Italians","Jamaicans","Japanese"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Select Nationality");
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
                android.R.layout.simple_list_item_1, nationality);
        mylist.setAdapter(adapter);
        mylist.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dismiss();
        if (sharedPreferences.getString("keyNationality", "").equals("editProfileNationality")) {
            EditProfile.inputNationality.setText(adapter.getItem(position));
        } else {
            BabyRegister.inputNationality.setText(adapter.getItem(position));
        }


    }
}
