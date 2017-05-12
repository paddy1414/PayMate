package com.example.aisling.finalprojectaislingstafford.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aisling.finalprojectaislingstafford.DTO.BillHistoryDTO;
import com.example.aisling.finalprojectaislingstafford.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Patrick on 12/05/2017.
 */

public class BillsHistoryListAdapter extends ArrayAdapter<BillHistoryDTO> {

    private final Activity context;
    private final ArrayList<BillHistoryDTO> itemname;


    //   private loadTheBloodyImage loadTheBloodyImage;
//

    /**
     * Instantiates a new Around custom list adapter.
     *
     * @param context  the context
     * @param itemname the itemname
     */
    public BillsHistoryListAdapter(Activity context, ArrayList<BillHistoryDTO> itemname) {
        super(context, R.layout.each_view_old_bills, itemname);
        // TODO Auto-generated constructor stu
        this.context = context;

        this.itemname = itemname;


    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.each_view_old_bills, null, true);
//1
        TextView txtTitle = (TextView) rowView.findViewById(R.id.bill_history_name);
        txtTitle.setText(itemname.get(position).getBillName());

//2
        TextView txtDate = (TextView) rowView.findViewById(R.id.bill_histry_date);
        txtDate.setText(itemname.get(position).getDatePaid() + "");

//3
        TextView txtType = (TextView) rowView.findViewById(R.id.bill_history_type);
        txtType.setText(itemname.get(position).getBillType());
//4
        TextView txtAmount = (TextView) rowView.findViewById(R.id.bill_history_amount);
        txtAmount.setText(itemname.get(position).getAmount() + "");

        //   loadTheBloodyImage = new loadTheBloodyImage(context);
        //  loadTheBloodyImage.DisplayImage(itemname.get(position).getBarPic(), imageView);

        return rowView;

    }
}


