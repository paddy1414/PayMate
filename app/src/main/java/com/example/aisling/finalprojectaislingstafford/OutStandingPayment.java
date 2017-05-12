package com.example.aisling.finalprojectaislingstafford;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.aisling.finalprojectaislingstafford.Adapters.MoneyOwedListAdapter;
import com.example.aisling.finalprojectaislingstafford.DTO.MoneyOwedDTO;
import com.example.aisling.finalprojectaislingstafford.Services.AppController;
import com.example.aisling.finalprojectaislingstafford.Services.Config;
import com.example.aisling.finalprojectaislingstafford.Services.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OutStandingPayment extends AppCompatActivity implements View.OnClickListener {

    Button btnIOwe;
    Button btnOwedToMe;
    Button btnPayBack;
    // comments JSON Array
    JSONArray comments = null;

    private ArrayList<MoneyOwedDTO> mStrings;

    private MoneyOwedListAdapter mAdapter;
    private ListView simpleList = null;
    private static String URlSend = Config.URL_MONEY_I_OWE;

    int groupId;
    int userId;

    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_standing_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */


        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();

        userId = Integer.parseInt(user.get(SessionManager.KEY_ID));

        groupId = Integer.parseInt(user.get(SessionManager.KEY_GROUPID));

        btnIOwe = (Button) findViewById(R.id.you_owe_button);

        btnIOwe.setOnClickListener(this);

        btnOwedToMe = (Button) findViewById(R.id.you_are_owed_button);

        btnOwedToMe.setOnClickListener(this);

    //    btnPayBack =(Button) findViewById(R.id.btn_pay_back);
     //   btnPayBack.setOnClickListener(this);

        simpleList = (ListView) findViewById(R.id.users_owed_money);

        new LoadMoneyIOwe().execute();


    }

    @Override
    public void onClick(View v) {
        if(v==btnIOwe) {
            URlSend = Config.URL_MONEY_I_OWE;
            new LoadMoneyIOwe().execute();

        } if (v==btnOwedToMe) {
            Log.d("WhoOwesMe", URlSend);
            URlSend = Config.URL_MONEY_OWE_ME;
            new LoadMoneyYouOweMe().execute();
        }
    }



    // A background async task to load all bars by making http request
    private class LoadMoneyIOwe extends AsyncTask<String, String, String> {
        // Before starting background threads, show the progress dialog
        @Override
        protected void onPreExecute() {
            Log.d("onPreExecute", "on the onPreExecute part!!");
            super.onPreExecute();
            mStrings = new ArrayList<MoneyOwedDTO>();

        }

        // getting all the images from url
        @Override
        protected String doInBackground(String... args) {
            // Building parameters
            Log.d("doInBackground", "got to here");

            // Tag used to cancel the request
            String tag_string_req = "req_post_comment";


            StringRequest strReq = new StringRequest(Request.Method.POST,
                    URlSend, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    Log.d("responseStiny", response);
                    try {
                        JSONObject jObj = new JSONObject(response);

                        Log.d("Jsonobj", jObj.toString());



                        boolean error = jObj.getBoolean("error");


                        // Check for error node in json
                        if (error == false) {
                            // user successfully logged in
                            // Create login session

                            //   JSONObject comment = jObj.getJSONObject("comments");
                            comments = jObj.getJSONArray("whoiowe");
                            for (int i = 0; i < comments.length(); i++) {


                                JSONObject c = comments.getJSONObject(i);
                                Log.d("users", c.toString());
                                int ubId = Integer.parseInt(c.getString("ubId"));
                                int billId = Integer.parseInt(c.getString("billId"));
                                Log.d("billId", billId +"");

                                //Storing each json item in varible
                                String fname = c.getString("firstName");
                                Log.d("fname", fname);
                                String lname = c.getString("lastName");
                                String billName = c.getString("billName");
                                String totalAmount = c.getString("amountOwed");
                                Log.d("totalAmount", totalAmount);
                               // MoneyOwedDTO mo = new MoneyOwedDTO(fname+" " + lname, billName, Double.parseDouble(totalAmount));
                               // Log.d("mo money", mo.toString());
                                mStrings.add(i, new MoneyOwedDTO(ubId, billId, fname+" " + lname, billName, Double.parseDouble(totalAmount)));
                            }

                            // dismiss the dialog after getting all products

                            // updating UI from Background Thread
                            // updating UI from Background Thread
                            runOnUiThread(
                                    new Runnable() {
                                        public void run() {
                                            Log.d("mStrings", mStrings.toString());
                                            mAdapter = new MoneyOwedListAdapter(OutStandingPayment.this, mStrings, true);
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

                    Log.d("Streng sentLoadOth", params.toString());
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
                            mAdapter = new MoneyOwedListAdapter(OutStandingPayment.this, mStrings, true);
                            simpleList.setAdapter(mAdapter);
                        }
                    });
        }
    }

    // A background async task to load all bars by making http request
    private class LoadMoneyYouOweMe extends AsyncTask<String, String, String> {
        // Before starting background threads, show the progress dialog
        @Override
        protected void onPreExecute() {
            Log.d("onPreExecute", "on the onPreExecute part!!");
            super.onPreExecute();
            mStrings = new ArrayList<MoneyOwedDTO>();

        }

        // getting all the images from url
        @Override
        protected String doInBackground(String... args) {
            // Building parameters
            Log.d("doInBackground", "got to here");

            // Tag used to cancel the request
            String tag_string_req = "req_post_comment";


            StringRequest strReq = new StringRequest(Request.Method.POST,
                    Config.URL_MONEY_OWE_ME, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    Log.d("whoOE ME", response);
                    try {
                        JSONObject jObj = new JSONObject(response);

                        Log.d("Jsonobj", jObj.toString());



                        boolean error = jObj.getBoolean("error");


                        // Check for error node in json
                        if (error == false) {
                            // user successfully logged in
                            // Create login session

                            //   JSONObject comment = jObj.getJSONObject("comments");
                            comments = jObj.getJSONArray("WhoOwesMe");
                            for (int i = 0; i < comments.length(); i++) {

                                JSONObject c = comments.getJSONObject(i);
                                Log.d("WhoOwesMe", c.toString());
                                int ubId = Integer.parseInt(c.getString("ubId"));
                                int billId = Integer.parseInt(c.getString("billId"));
                                Log.d("billId", billId +"");
                                //Storing each json item in varible
                                String fname = c.getString("firstName");
                                Log.d("fname", fname);
                                String lname = c.getString("lastName");
                                String billName = c.getString("billName");
                                String totalAmount = c.getString("amountOwed");
                                Log.d("totalAmount", totalAmount);
                                // MoneyOwedDTO mo = new MoneyOwedDTO(fname+" " + lname, billName, Double.parseDouble(totalAmount));
                                // Log.d("mo money", mo.toString());
                                mStrings.add(i, new MoneyOwedDTO(ubId, billId, fname+" " + lname, billName, Double.parseDouble(totalAmount)));
                            }

                            // dismiss the dialog after getting all products

                            // updating UI from Background Thread
                            // updating UI from Background Thread
                            runOnUiThread(
                                    new Runnable() {
                                        public void run() {
                                            Log.d("mStrings", mStrings.toString());
                                            mAdapter = new MoneyOwedListAdapter(OutStandingPayment.this, mStrings, false);
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
                    params.put("groupID", groupId +"");

                    Log.d("Streng sentLoadOth", params.toString());
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
                            mAdapter = new MoneyOwedListAdapter(OutStandingPayment.this, mStrings, false);
                            simpleList.setAdapter(mAdapter);
                        }
                    });
        }
    }


}
