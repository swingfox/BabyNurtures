package com.example.willthespoderman.babynurtures.models;

/**
 * Created by WILL THE SPODERMAN on 10/8/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.willthespoderman.babynurtures.R;
import com.example.willthespoderman.babynurtures.app.AppController;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Baby> tutors;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<Baby> tutors) {
        this.activity = activity;
        this.tutors = tutors;
    }



    @Override
    public int getCount() {
        return tutors.size();
    }

    @Override
    public Object getItem(int location) {
        return tutors.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_tutors, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView tutorID = (TextView) convertView.findViewById(R.id.tutor_id);
        //TextView rating = (TextView) convertView.findViewById(R.id.rating);
        //TextView genre = (TextView) convertView.findViewById(R.id.genre);
        //TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

        /**
         * Getting movie data for the row
         */
        Baby t = tutors.get(position);

        /**
         * Thumbnail image
         */
        thumbNail.setImageUrl(t.getThumbnailUrl(), imageLoader);
        tutorID.setText(t.getBabyId());
        /**
         * Tutor name
         */
        title.setText(t.getBabyName());

        /**
         * Tutor id
         */
        tutorID.setText(String.valueOf(t.getBabyId()));

        // rating
        //rating.setText("Rating: " + String.valueOf(t.getRating()));

        // genre
//        String genreStr = "";
//        for (String str : t.getGenre()) {
//            genreStr += str + ", ";
//        }
//        genreStr = genreStr.length() > 0 ? genreStr.substring(0,
//                genreStr.length() - 2) : genreStr;
//        genre.setText(genreStr);

        // release year
        //year.setText(String.valueOf(t.getYear()));

        return convertView;
    }
}
