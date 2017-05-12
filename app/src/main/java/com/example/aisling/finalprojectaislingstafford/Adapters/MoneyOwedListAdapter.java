package com.example.aisling.finalprojectaislingstafford.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aisling.finalprojectaislingstafford.DTO.MoneyOwedDTO;
import com.example.aisling.finalprojectaislingstafford.PaymentOptionsActivity;
import com.example.aisling.finalprojectaislingstafford.R;

import java.util.ArrayList;

/**
 * Created by Patrick on 05/05/2017.
 */

public class MoneyOwedListAdapter extends ArrayAdapter<MoneyOwedDTO> {

    private final Activity context;
    private final ArrayList<MoneyOwedDTO> itemname;



    //   private loadTheBloodyImage loadTheBloodyImage;
//
    /**
     * Instantiates a new Around custom list adapter.
     *
     * @param context  the context
     * @param itemname the itemname
     */
    Button btn;
    private boolean moneyOwe = false;

    public MoneyOwedListAdapter(Activity context, ArrayList<MoneyOwedDTO> itemname, boolean moneyOwe) {
        super(context, R.layout.each_user_you_owe, itemname);
        // TODO Auto-generated constructor stu
        this.context = context;
        this.moneyOwe =moneyOwe;
        this.itemname = itemname;
    }


    public View getView(int position, View view, ViewGroup parent) {
        final int possi = position;
        LayoutInflater inflater = context.getLayoutInflater();
    if(moneyOwe == true) {
        View rowView = inflater.inflate(R.layout.each_user_you_owe, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.users_name_for_listOwe);

        TextView txtBillType = (TextView) rowView.findViewById(R.id.bill_type_i_owed);

        TextView txtAmount = (TextView) rowView.findViewById(R.id.amount_i_owed);

        txtAmount.setText(itemname.get(position).getAmount() + "");
        txtTitle.setText(itemname.get(position).getName());

        txtBillType.setText(itemname.get(position).getBillType());

         btn =(Button) rowView.findViewById(R.id.btn_pay_back);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PaymentOptionsActivity.class);

             //   Intent intent = new Intent("my.special.broadcast.action");
                Bundle bundle = new Bundle();
                bundle.putInt("ubBill", itemname.get(possi).getUbId());
                bundle.putInt("billId", itemname.get(possi).getBillId());
                bundle.putDouble("amount", itemname.get(possi).getAmount());


//Add the bundle to the intent
                intent.putExtras(bundle);
                getContext().startActivity(intent);
              //  getActivity().sendBroadcast(intent);



                Toast.makeText(getContext(),itemname.get(possi).getName() , Toast.LENGTH_LONG).show();

            }
        });

        return rowView;
    } else {
        View rowView = inflater.inflate(R.layout.each_user_owe_me, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.bill_history_amount);

        TextView txtBillType = (TextView) rowView.findViewById(R.id.bill_type_i_owed);

        TextView txtAmount = (TextView) rowView.findViewById(R.id.amount_owed_list);

        txtAmount.setText(itemname.get(position).getAmount() + "");
        txtTitle.setText(itemname.get(position).getName());

        txtBillType.setText(itemname.get(position).getBillType());

        return rowView;
    }

      //  }


    }



}
