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
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aisling.finalprojectaislingstafford.DTO.SavingsGoalDTO;
import com.example.aisling.finalprojectaislingstafford.DTO.User;
import com.example.aisling.finalprojectaislingstafford.R;

import java.util.ArrayList;
import java.util.Collections;


/**
 * The type Around custom list adapter.
 */
public class SavingsGoalsListAdapter extends ArrayAdapter<SavingsGoalDTO> {

    private final Activity context;
    private final ArrayList<SavingsGoalDTO> savingGoalsList;

    private int progressStatus = 0;
    private int maxStatus =0;

    //   private loadTheBloodyImage loadTheBloodyImage;
//
    /**
     * Instantiates a new Around custom list adapter.
     *
     * @param context  the context
     * @param itemname the itemname
     */
    public SavingsGoalsListAdapter(Activity context, ArrayList<SavingsGoalDTO> itemname) {
        super(context, R.layout.each_saving_goal, itemname);
        // TODO Auto-generated constructor stu
        this.context = context;
        Log.d("SavingsGoalsListAdapter", "Got the the adapter");
        this.savingGoalsList = itemname;
        Log.d("savingGoalsList", savingGoalsList.toString());


    }

    public View getView(int position, View view, ViewGroup parent) {
        Log.d("cccccccc", "Gfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.each_saving_goal, null, true);
        Log.d("cccccccc", "Gfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
        Log.d("getSavingsGoal.", savingGoalsList.get(position).getSavingsGoal());
        Log.d("cccccccc", "Gfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
        TextView txtUName = (TextView) rowView.findViewById(R.id.saving_title);
        TextView txtCurrent = (TextView) rowView.findViewById(R.id.saving_current);
        TextView txtGoal = (TextView) rowView.findViewById(R.id.saving_goal);

        txtUName.setText(savingGoalsList.get(position).getSavingsGoal());
        txtCurrent.setText(savingGoalsList.get(position).getCurrentAmount() +"");
        txtGoal.setText(savingGoalsList.get(position).getTargetAmount() +"");
        ProgressBar firstBar = (ProgressBar)rowView.findViewById(R.id.progressBar_for_save);
        maxStatus = (int) Math.round(savingGoalsList.get(position).getTargetAmount());
        progressStatus =(int) Math.round(savingGoalsList.get(position).getCurrentAmount());
        firstBar.setMax(maxStatus);
        firstBar.setProgress(progressStatus);

        Log.d("maxStatus", maxStatus+"");
        Log.d("progressStatus", progressStatus+"");

        return rowView;

    }


}