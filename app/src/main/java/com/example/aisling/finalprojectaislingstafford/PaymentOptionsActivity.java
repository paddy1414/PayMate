package com.example.aisling.finalprojectaislingstafford;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.aisling.finalprojectaislingstafford.Services.AppController;
import com.example.aisling.finalprojectaislingstafford.Services.Config;
import com.example.aisling.finalprojectaislingstafford.Services.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PaymentOptionsActivity extends AppCompatActivity implements View.OnClickListener {

    private static String dateString;
    private static TextView dateView;

    private String  paymentType;
    private int ubId, userId, billId;
    private double amount;
    private Button cashBtn, cardBtn;

    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userId = Integer.parseInt(user.get(SessionManager.KEY_ID));

        cashBtn = (Button) findViewById(R.id.paid_with_cash_button);
        cashBtn.setOnClickListener(this);

        cardBtn = (Button) findViewById(R.id.payWithCardbutton);
        cardBtn.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();

        billId = bundle.getInt("billId");
        ubId = bundle.getInt("ubBill");
        amount = bundle.getDouble("amount");




            final Button datePickerButton = (Button) findViewById(R.id.date_picker_button);
            datePickerButton.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    showDatePickerDialog();
                }
            });

        }

    @Override
    public void onClick(View v) {
        if(v==cashBtn) {
            paymentType = "cash";
            new PayOffBill().execute();
            Toast.makeText(getApplicationContext(),
                    "paid with cash", Toast.LENGTH_LONG).show();

        } if (v==cardBtn) {
            paymentType = "card";
            new PayOffBill().execute();
            Toast.makeText(getApplicationContext(),
                    "paid with card", Toast.LENGTH_LONG).show();
        }

    }

    public static class DatePickerFragment extends DialogFragment implements
                DatePickerDialog.OnDateSetListener
        {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {

                // Use the current date as the default date in the picker

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // Create a new instance of DatePickerDialog and return it
                return new DatePickerDialog(getActivity(), this, year, month, day);
            }

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                setDateString(year, monthOfYear, dayOfMonth);

                dateView.setText(dateString);
            }

        }


    private static void setDateString(int year, int monthOfYear, int dayOfMonth) {

        // Increment monthOfYear for Calendar/Date -> Time Format setting
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;

        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;

        dateString = year + "-" + mon + "-" + day;
    }

    private void showDatePickerDialog()
    {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }


    // A background async task to load all bars by making http request
    private class PayOffBill extends AsyncTask<String, String, String> {
        // Before starting background threads, show the progress dialog

        @Override
        protected void onPreExecute() {
            Log.d("onPreExecute", "on the onPreExecute part!!");
            super.onPreExecute();
            //   myDialog = new ProgressDialog(LoginActivity.this);
            //    myDialog.setMessage("Logging you in.  Please wait....");
            //     myDialog.setIndeterminate(false);
            //     myDialog.setCancelable(false);
            //     myDialog.show();
            Log.d("OnPreExecute", "got to here");
        }

        // getting all the images from url
        @Override
        protected String doInBackground(String... args) {

            Log.d("doInBackground", "got to here");

            // Tag used to cancel the request
            String tag_string_req = "req_login";
            Log.d("tag_string_req", tag_string_req);

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    Config.URL_PAY_OFF_DEBT, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    Log.d("PAY_OFF_DEBT", response);
                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");

                        // Check for error node in json
                        if (error == false) {

                            // user successfully logged in
                            // Create login session

                            JSONObject user = jObj.getJSONObject(response);
                            Log.d("Jsonobj", jObj.toString());
                            Log.d("response", response);



                        } else {
                            // Error in login. Get the error message
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        //      myDialog.dismiss();
                        e.printStackTrace();
                        // If you need update UI, simply do this:
                        runOnUiThread(new Runnable() {
                            public void run() {
                                // update your UI component here. in order to display friendly error report
                                Toast.makeText(getApplicationContext(), R.string.server_is_down,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });


                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    //   myDialog.dismiss();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();



                    params.put("ubId", ubId +"");
                    params.put("userId", userId +"");
                    params.put("billId", billId +"");
                    params.put("paymentType", paymentType);
                    params.put("amount", amount +"");
                    Log.d("Streng sent123", params.toString());
                    return params;
                }

            };
            Log.d("Sting reqsss", strReq.toString());

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            // updating UI from Background Thread
            PaymentOptionsActivity.this.runOnUiThread(
                    new Runnable() {
                        public void run() {
                            Toast.makeText(PaymentOptionsActivity.this, "Bill added Correctly", Toast.LENGTH_LONG).show();

                            Log.d("Users", "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
                        }
                    });
        }


    }


}
