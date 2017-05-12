package com.example.aisling.finalprojectaislingstafford;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.aisling.finalprojectaislingstafford.Adapters.BillsHistoryListAdapter;
import com.example.aisling.finalprojectaislingstafford.Adapters.SavingsGoalsListAdapter;
import com.example.aisling.finalprojectaislingstafford.DTO.BillHistoryDTO;
import com.example.aisling.finalprojectaislingstafford.DTO.SavingsGoalDTO;
import com.example.aisling.finalprojectaislingstafford.Services.AppController;
import com.example.aisling.finalprojectaislingstafford.Services.Config;
import com.example.aisling.finalprojectaislingstafford.Services.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ViewOldBills extends AppCompatActivity {

    private ListView simpleList = null;
    private BillsHistoryListAdapter mAdapter;
    // Session Manager Class
    SessionManager session;
    /**
     * The Dbct.
     */
    int userId;

    // comments JSON Array
    JSONArray comments = null;


    private ArrayList<BillHistoryDTO> mStrings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_old_bills);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();

        userId = Integer.parseInt(user.get(SessionManager.KEY_ID));

        simpleList = (ListView) findViewById(R.id.bill_history_list);

        new LoadBillHistory().execute();
    }

    // A background async task to load all bars by making http request
    private class LoadBillHistory extends AsyncTask<String, String, String> {
        // Before starting background threads, show the progress dialog
        @Override
        protected void onPreExecute() {
            Log.d("onPreExecute", "on the onPreExecute part!!");
            super.onPreExecute();
            mStrings = new ArrayList<BillHistoryDTO>();

        }

        // getting all the images from url
        @Override
        protected String doInBackground(String... args) {
            // Building parameters
            Log.d("doInBackground", "got to here");

            // Tag used to cancel the request
            String tag_string_req = "req_post_comment";


            StringRequest strReq = new StringRequest(Request.Method.POST,
                    Config.URL_GET_BILL_HISTORY, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    Log.d("ViewOldBills",response );
                    try {
                        JSONObject jObj = new JSONObject(response);

                        Log.d("Jsonobj", jObj.toString());

                        boolean error = jObj.getBoolean("error");


                        // Check for error node in json
                        if (error == false) {
                            // user successfully logged in
                            // Create login session

                            //   JSONObject comment = jObj.getJSONObject("comments");
                            comments = jObj.getJSONArray("billhistory");
                            for (int i = 0; i < comments.length(); i++) {

                                JSONObject c = comments.getJSONObject(i);
                                Log.d("billhistory", c.toString());
                                //Storing each json item in varible
                                String billName = c.getString("billName");
                                Log.d("billName", billName);
                                String paymentType = c.getString("paymentType");
                                Log.d("paymentType", paymentType);
                                String amount = c.getString("amount");
                                Log.d("amount", amount);


                                String datePaid = c.getString("datePaid");
                                Log.d("datePaid", datePaid);


                                // String usersObj = fname +" " + lname;
                                BillHistoryDTO bhObj = new BillHistoryDTO(billName, paymentType, Double.parseDouble(amount), datePaid);
                                mStrings.add(i, bhObj);
                            }

                            // dismiss the dialog after getting all products

                            // updating UI from Background Thread
                            // updating UI from Background Thread
                            runOnUiThread(
                                    new Runnable() {
                                        public void run() {
                                            Log.d("mStrings", mStrings.toString());
                                            mAdapter = new BillsHistoryListAdapter(ViewOldBills.this, mStrings);
                                            simpleList.setAdapter(mAdapter);
                                        }
                                    });

                            Log.d("comment", comments.toString());

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
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("userId", userId +"");

                    Log.d("Streng sent", params.toString());
                    return params;
                }

            };
            Log.d("Sting reqsss", strReq.toString());

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

            return null;
        }

        protected void onPostExecute(String file_url) {

            // dismiss the dialog after getting all products

            // updating UI from Background Thread
            // updating UI from Background Thread
            runOnUiThread(
                    new Runnable() {
                        public void run() {
                            Log.d("mStrings", mStrings.toString());
                            mAdapter = new BillsHistoryListAdapter(ViewOldBills.this, mStrings);
                            simpleList.setAdapter(mAdapter);
                        }
                    });
        }
    }

}
