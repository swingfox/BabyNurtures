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
public class Country extends DialogFragment implements AdapterView.OnItemClickListener {

    private ArrayList<String> list = new ArrayList<String>();
    ListView mylist;
    private ArrayAdapter<String> adapter;
        private SharedPreferences sharedPreferences;
    String[] country = new String[]{"Afghanistan",
            "Albania",
            "Algeria",
            "Andorra",
            "Angola",
            "Antigua and Barbuda",
            "Argentina",
            "Armenia",
            "Aruba",
            "Australia",
            "Austria",
            "Azerbaijan",
            "Bahamas, The",
            "Bahrain",
            "Bangladesh",
            "Barbados",
            "Belarus",
            "Belgium",
            "Belize",
            "Benin",
            "Bhutan",
            "Bolivia",
            "Bosnia and Herzegovina",
            "Botswana",
            "Brazil",
            "Brunei",
            "Bulgaria",
            "Burkina Faso",
            "Burma",
            "Burundi",
            "Cambodia",
            "Cameroon",
            "Canada",
            "Cape Verde",
            "Central African Republic",
            "Chad",
            "Chile",
            "China",
            "Colombia",
            "Comoros",
            "Congo, Democratic Republic of the",
            "Congo, Republic of the",
            "Costa Rica",
            "Cote d'Ivoire",
            "Croatia",
            "Cuba",
            "Curacao",
            "Cyprus",
            "Czech Republic",
            "Denmark",
            "Djibouti",
            "Dominica",
            "Dominican Republic",
            "East Timor (see Timor-Leste)",
            "Ecuador",
            "Egypt",
            "El Salvador",
            "Equatorial Guinea",
            "Eritrea",
            "Estonia",
            "Ethiopia",
            "Fiji",
            "Finland",
            "France",
            "Iceland",
            "India",
            "Indonesia",
            "Iran",
            "Iraq",
            "Ireland",
            "Israel",
            "Italy",
            "Jamaica",
            "Japan",
            "Jordan",
            "Palau",
            "Palestinian Territories",
            "Panama",
            "Papua New Guinea",
            "Paraguay",
            "Peru",
            "Philippines",
            "Poland",
            "Portugal"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Select Country");
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
                android.R.layout.simple_list_item_1, country);
        mylist.setAdapter(adapter);
        mylist.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         dismiss();

            if (sharedPreferences.getString("keyNationality", "").equals("editProfileCountry")) {
                    EditProfile.inputCountry.setText(adapter.getItem(position));
            } else {
                    BabyRegister.inputCountry.setText(adapter.getItem(position));
            }


    }
}
