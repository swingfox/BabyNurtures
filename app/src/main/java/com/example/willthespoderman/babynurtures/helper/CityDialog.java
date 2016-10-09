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



public class CityDialog extends DialogFragment implements AdapterView.OnItemClickListener {
    private ArrayList<String> list = new ArrayList<String>();
    ListView mylist;

    private ArrayAdapter<String> adapter;
    private SharedPreferences sharedPreferences;
    String[] city = new String[]{"Alaminos City","Angeles City","Antipolo City","Bacolod City","Bago City","Baguio City","Bais City","Balanga City","Batangas City","Bayawan City","Bisilig City","Butuan City","Cabanatuan City","Cadiz City","Cagayan de Oro City","Calamba City","Calapan City","Calbayog City","Caloocan City","Candon City","Canlaon City","Cauayan City","Cavite City","Cebu City","Cotabato City","Dagupan City","Danao City","Dapitan City","Davao City","Digos City","Dipolog City","Dumaguete City","Escalante City","Gapan City","General Santos City","Gingoog City","Himamaylan City","Iligan City","Iloilo City","Iriga City",
            "Isabela City","Island Garden City of Samal","Kabankalan City","Kidapawan City","Koronadal City","La Carlota City","Laoag City","Lapu-Lapu City","Las Piñas City","Legazpi City","Ligao City","Lipa City","Lucena City","Maasin City","Makati City","Malabon City","Malaybalay City","Malolos City","Mandaluyong City","Mandaue City","Manila","Maragondon","Marawi City","Masbate City","Muntinlupa City","Naga City","Olongapo City","Ormoc City","Oroquieta City","Ozamis City","Pagadian City","Palayan City","Parañaque City","Pasay City","Pasig City","Passi City","Puerto Princesa City","Quezon City","Roxas City","Sagay City","San Carlos City, Negros Occidental","San Carlos City, Pangasinan","San Fernando City, La Union","San Fernando City, Pampanga","San Jose City","San Jose del Monte City","San Pablo City","Santa Rosa City","Santiago City","Muñ City","Silay City","Sipalay City","Sorsogon City","Surigao City","Tabaco City","Tacloban City","Tacurong City",
            "Tagaytay City","Tagbilaran City","Tagum City","Talisay City, Cebu", "Talisay City, Negros Occidental","Tanauan City","Tangub City","Tanjay City","Tarlac City","Taguig City","Toledo City","Trece Martires City","Tuguegarao City","Urdaneta City","Valencia City","Valenzuela City","Victorias City","Vigan City","Zamboanga City"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Select City");
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
                android.R.layout.simple_list_item_1, city);
        mylist.setAdapter(adapter);
        mylist.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dismiss();
        if (sharedPreferences.getString("keyNationality", "").equals("editProfileCity")) {
            EditProfile.inputCity.setText(adapter.getItem(position));
        } else {
            BabyRegister.inputCity.setText(adapter.getItem(position));
        }
    }
}
