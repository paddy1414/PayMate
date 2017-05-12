package com.example.aisling.finalprojectaislingstafford;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.aisling.finalprojectaislingstafford.Services.SessionManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addABillBtn;
    private Button outStandingBtn;
    private Button groupGoalsBtn;
    private Button oldBillBtn;
    private Button logoutBtn;
    private Button viewProfile;

    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewProfile = (Button) findViewById(R.id.view_profile);
        viewProfile.setOnClickListener(this);

        addABillBtn = (Button) findViewById(R.id.add_bill_button);
        addABillBtn.setOnClickListener(this);

        outStandingBtn = (Button) findViewById(R.id.outstanding_group_payments_button);
        outStandingBtn.setOnClickListener(this);

        groupGoalsBtn = (Button) findViewById(R.id.group_saving_goals_button);
        groupGoalsBtn.setOnClickListener(this);

        oldBillBtn = (Button) findViewById(R.id.view_previous_bills_button);
        oldBillBtn.setOnClickListener(this);

        logoutBtn = (Button) findViewById(R.id.logout);
        logoutBtn.setOnClickListener(this);

        // Session class instance
        session = new SessionManager(getApplicationContext());

        if(!session.isLoggedIn()) {
            session.checkLogin();
        } else {
            // get user data from session
            HashMap<String, String> user = session.getUserDetails();

            // txtId.setText(user.get(SessionManager.KEY_ID));

            //String fName =user.get(SessionManager.KEY_FNAME);
            Log.d("KEY_FNAME", user.get(SessionManager.KEY_FNAME) + "");
            Log.d("KEY_LNAME", user.get(SessionManager.KEY_LNAME));
            Log.d("KEY_EMAIL", user.get(SessionManager.KEY_EMAIL));
            Log.d("KEY_GROUPID", user.get(SessionManager.KEY_GROUPID) + "");
            Log.d("KEY_PASSWORD", user.get(SessionManager.KEY_PASSWORD) + "");
        }

    }

    @Override
    public void onClick(View v) {

        if (v == viewProfile) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
        if (v == addABillBtn) {
            Intent intent = new Intent(MainActivity.this, AddABill.class);
            startActivity(intent);
        }
        if (v == outStandingBtn) {
            Intent intent = new Intent(MainActivity.this, OutStandingPayment.class);
            startActivity(intent);
        }
        if (v == groupGoalsBtn) {
            Intent intent = new Intent(MainActivity.this, ViewSavingsGoalsActivity.class);
            startActivity(intent);
        }
        if (v == oldBillBtn) {
            Intent intent = new Intent(MainActivity.this, ViewOldBills.class);
            startActivity(intent);
        }
        if (v == logoutBtn) {
            session.logoutUser();
           // Intent intent = new Intent(MainActivity.this, LoginActivity.class);
          //  startActivity(intent);
        }

    }





}
