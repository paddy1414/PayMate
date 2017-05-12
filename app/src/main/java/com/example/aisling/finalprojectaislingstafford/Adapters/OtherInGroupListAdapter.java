/*
 * Copyright (c)  2017.  Patrick Norton  All Rights Reserved
 * Email: paddy1414@live.ie
 * Github: https://github.com/paddy1414
 * LinkedIn: www.linkedin.com/in/patricknorton9112
 * Youtube: https://www.youtube.com/channel/UCtYIreGkS7cQa_YwVR-Xqyw
 *
 *
 */

package com.example.aisling.finalprojectaislingstafford.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aisling.finalprojectaislingstafford.DTO.User;
import com.example.aisling.finalprojectaislingstafford.R;

import java.util.ArrayList;
import java.util.Collections;


/**
 * The type Around custom list adapter.
 */
public class OtherInGroupListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> itemname;


 //   private loadTheBloodyImage loadTheBloodyImage;
//
    /**
     * Instantiates a new Around custom list adapter.
     *
     * @param context  the context
     * @param itemname the itemname
     */
    public OtherInGroupListAdapter(Activity context, ArrayList<String> itemname) {
        super(context, R.layout.each_user, itemname);
        // TODO Auto-generated constructor stu
        this.context = context;

        this.itemname = itemname;

            Collections.sort(itemname);



    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.each_user, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.users_name_for_list);


        txtTitle.setText(itemname.get(position));

     //   loadTheBloodyImage = new loadTheBloodyImage(context);
      //  loadTheBloodyImage.DisplayImage(itemname.get(position).getBarPic(), imageView);

        return rowView;

    }


}