package com.example.aisling.finalprojectaislingstafford;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import com.example.aisling.finalprojectaislingstafford.Adapters.SavingsGoalsListAdapter;
import com.example.aisling.finalprojectaislingstafford.DTO.SavingsGoalDTO;
import com.example.aisling.finalprojectaislingstafford.DatabaseArrays.DatabaseOthersInGroup;
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

public class ViewSavingsGoalsActivity extends AppCompatActivity  {


    //JSON node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_GOALLIST = "savinggoals";
    private static final String TAG_GOALID = "goalId";
    private static final String TAG_GOALNAME = "goalName";
    private static final String TAG_TARGAMOUNT = "targetAmount";
    private static final String TAG_CURRAMOUNT = "currentAmount";
    private static final String TAG_GROUPID = "groupId";
    /**
     * The Json parser.
     */

    private static final String TAG = AddABill.class.getSimpleName();
    // create a json parser object
    JSONParser jsonParser = new JSONParser();
    /**
     * The Name list.
     */
    ArrayList<HashMap<String, String>> nameList;
    /**
     * The Comment list.
     */
    ArrayList<HashMap<String, String>> commentList;
    /**
     * The Dbc.
     */
    DatabaseOthersInGroup dbc = new DatabaseOthersInGroup();



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

    int groupId;
    String billname, paidBy,billType;
    Double amount;

    EditText billBox, amountBox;

    private Button saveButton;
    //  Text


    private ArrayList<SavingsGoalDTO> mStrings;
    // the url get all bars list, at the moment, its just running off local host, but can easily be changed to use an online server.
    private String url_all_comments;
    private SavingsGoalsListAdapter mAdapter;
    private ArrayList<String> mNaames;
    private ListView simpleList = null;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_goals);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Moving to add Group", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                fabButton();

            }
        });

        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();

        groupId = Integer.parseInt(user.get(SessionManager.KEY_GROUPID));

        simpleList = (ListView) findViewById(R.id.users_in_saving_groupS);

        new LoadOtherUsers().execute();
    }

    public void fabButton() {
        Intent i = new Intent(this, AddGroupGoal.class);
        startActivity(i);
        finish();
    }




    Activity mContext = ViewSavingsGoalsActivity.this;



    // A background async task to load all bars by making http request
    private class LoadOtherUsers extends AsyncTask<String, String, String> {
        // Before starting background threads, show the progress dialog
        @Override
        protected void onPreExecute() {
            Log.d("onPreExecute", "on the onPreExecute part!!");
            super.onPreExecute();
            mStrings = new ArrayList<SavingsGoalDTO>();

        }

        // getting all the images from url
        @Override
        protected String doInBackground(String... args) {
            // Building parameters
            Log.d("doInBackground", "got to here");

            // Tag used to cancel the request
            String tag_string_req = "req_post_comment";


            StringRequest strReq = new StringRequest(Request.Method.POST,
                    Config.URL_OTHERS_IN_SAVE_GROUP, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                        Log.d("ViewSavingResponse",response );
                    try {
                        JSONObject jObj = new JSONObject(response);

                        Log.d("Jsonobj", jObj.toString());

                        boolean error = jObj.getBoolean("error");


                        // Check for error node in json
                        if (error == false) {
                            // user successfully logged in
                            // Create login session

                            //   JSONObject comment = jObj.getJSONObject("comments");
                            comments = jObj.getJSONArray("savinggoalsInGroup");
                            for (int i = 0; i < comments.length(); i++) {

                                JSONObject c = comments.getJSONObject(i);
                                Log.d("usersInGroup", c.toString());
                                //Storing each json item in varible
                                String goadId = c.getString(TAG_GOALID);
                                Log.d("TAG_GOALID", goadId);
                                String goalName = c.getString(TAG_GOALNAME);
                                Log.d("TAG_GOALNAME", goalName);

                                String currAmount = c.getString(TAG_CURRAMOUNT);
                                Log.d("TAG_CURRAMOUNT", currAmount);
                                String targAmount = c.getString(TAG_TARGAMOUNT);
                                Log.d("TAG_TARGAMOUNT", targAmount);
                                String groupId = c.getString(TAG_GROUPID);

                                // String usersObj = fname +" " + lname;
                                SavingsGoalDTO saveObj = new SavingsGoalDTO(Integer.parseInt(goadId), goalName, Double.parseDouble(targAmount), Double.parseDouble(currAmount), Integer.parseInt(groupId));
                                mStrings.add(i, saveObj);
                            }

                            // dismiss the dialog after getting all products

                            // updating UI from Background Thread
                            // updating UI from Background Thread
                            runOnUiThread(
                                    new Runnable() {
                                        public void run() {
                                            Log.d("mStrings", mStrings.toString());
                                            mAdapter = new SavingsGoalsListAdapter(ViewSavingsGoalsActivity.this, mStrings);
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
                    params.put("groupId", groupId +"");

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
                            mAdapter = new SavingsGoalsListAdapter(ViewSavingsGoalsActivity.this, mStrings);
                            simpleList.setAdapter(mAdapter);
                        }
                    });
        }
    }



}
