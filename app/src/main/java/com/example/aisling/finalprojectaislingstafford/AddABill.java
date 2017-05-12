package com.example.aisling.finalprojectaislingstafford;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.aisling.finalprojectaislingstafford.Adapters.OtherInGroupListAdapter;
import com.example.aisling.finalprojectaislingstafford.Services.AppController;
import com.example.aisling.finalprojectaislingstafford.Services.Config;
import com.example.aisling.finalprojectaislingstafford.Services.JSONParser;
import com.example.aisling.finalprojectaislingstafford.Services.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddABill extends AppCompatActivity implements View.OnClickListener {


    //JSON node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_COMMENTS = "comments";
    private static final String TAG_FNAME = "fName";
    private static final String TAG_LNAME = "lName";
    private static final String TAG_COMMENTTEST = "commentText";
    /**
     * The Json parser.
     */

    private static final String TAG = AddABill.class.getSimpleName();
// create a json parser object
    JSONParser jsonParser = new JSONParser();
    /**
     * The Name list.
     */




    /**
     * The Comment string.
     */
    String fName;
    String lName;
    /**
     * The Sc.
     */
    Config sc = new Config();
    /**
     * The Btn comment.
     */

    /**
     * The Txt comment.
     */
    TextView txtComment;
    /**
     * The Comments.
     */
// comments JSON Array
    JSONArray comments = null;
    /**
     * The Session.
     */
// Session Manager Class
        SessionManager session;
    /**
     * The Dbct.
     */

    String billname, paidBy,billType;
    Double amount;

    EditText billBox, amountBox;

    private Button saveButton;
  //  Text
    int groupId;

    private ArrayList<String> mStrings;
    // the url get all bars list, at the moment, its just running off local host, but can easily be changed to use an online server.
    private String url_all_comments;
    private OtherInGroupListAdapter mAdapter;
    private ArrayList<String> mNaames;
    private ListView simpleList = null;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_abill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         spinner = (Spinner) findViewById(R.id.bill_type_spin);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bill_type_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        billBox =  (EditText) findViewById(R.id.bill_type_i_owed);
        amountBox = (EditText) findViewById(R.id.amount_box);
        session = new SessionManager(getApplicationContext());

        saveButton = (Button)  findViewById(R.id.btn_save_bill);
        saveButton.setOnClickListener(this);

        HashMap<String, String> user = session.getUserDetails();

        groupId = Integer.parseInt(user.get(SessionManager.KEY_GROUPID));

        simpleList = (ListView) findViewById(R.id.users_in_groupAdd);

        new LoadOtherUsers().execute();
    }

    @Override
    public void onClick(View v) {

        if (v == saveButton) {
            saveBill();

        }



    }

    private void saveBill() {
        billname = billBox.getText().toString().trim();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

    try {
        paidBy = user.get(SessionManager.KEY_ID);

        amount = Double.parseDouble(amountBox.getText().toString().trim());
        billType = spinner.getSelectedItem().toString();

        if(billname.equals("") || amountBox.getText().equals(""))
        {
            Toast.makeText(AddABill.this, "billname and amountBox must be entered to continue ", Toast.LENGTH_LONG).show();
            return;
        } else
        {
            new SaveBill().execute();
            //  userLogin(email, password);
        }
    } catch (NumberFormatException ex) {
        Toast.makeText(AddABill.this, "Please use a number for the amount ", Toast.LENGTH_LONG).show();
        return;
    }


    }

    Activity mContext = AddABill.this;

    // A background async task to load all bars by making http request
    private class LoadOtherUsers extends AsyncTask<String, String, String> {
        // Before starting background threads, show the progress dialog
        @Override
        protected void onPreExecute() {
            Log.d("onPreExecute", "on the onPreExecute part!!");
            super.onPreExecute();
            mStrings = new ArrayList<String>();

        }

        // getting all the images from url
        @Override
        protected String doInBackground(String... args) {
            // Building parameters
            Log.d("doInBackground", "got to here");

            // Tag used to cancel the request
            String tag_string_req = "req_post_comment";


            StringRequest strReq = new StringRequest(Request.Method.POST,
                    Config.URL_OTHERS_IN_GROUP, new Response.Listener<String>() {


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
                            comments = jObj.getJSONArray("usersInGroup");
                            for (int i = 0; i < comments.length(); i++) {

                                JSONObject c = comments.getJSONObject(i);
                                Log.d("users", c.toString());
                                //Storing each json item in varible
                                String fname = c.getString(TAG_FNAME);
                                Log.d("fname", fname);
                                String lname = c.getString(TAG_LNAME);
                                Log.d("lname", lname);
                                String usersObj = fname +" " + lname;
                                mStrings.add(i, usersObj);
                            }

                            // dismiss the dialog after getting all products

                            // updating UI from Background Thread
                            // updating UI from Background Thread
                            runOnUiThread(
                                    new Runnable() {
                                        public void run() {
                                            Log.d("mStrings", mStrings.toString());
                                            mAdapter = new OtherInGroupListAdapter(AddABill.this, mStrings);
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
                            mAdapter = new OtherInGroupListAdapter(AddABill.this, mStrings);
                            simpleList.setAdapter(mAdapter);
                        }
                    });
        }
    }

    // A background async task to load all bars by making http request
    private class SaveBill extends AsyncTask<String, String, String> {
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
                    Config.URL_ADD_BILL, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Login Response:" + response.toString());
                    //     myDialog.dismiss();


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
                    Log.e(TAG, "Login Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    //   myDialog.dismiss();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();



                    params.put("billName", billname);
                    params.put("paidBy", paidBy);
                    params.put("totalAmount", amount +"");
                    params.put("billType", billType);
                    params.put("groupId", groupId +"");

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
            AddABill.this.runOnUiThread(
                    new Runnable() {
                        public void run() {
                            Toast.makeText(AddABill.this, "Bill added Correctly", Toast.LENGTH_LONG).show();

                            Log.d("Users", "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
                        }
                    });
        }


    }

}
