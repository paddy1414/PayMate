package com.example.aisling.finalprojectaislingstafford;

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
import android.widget.RadioGroup;
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

import java.util.HashMap;
import java.util.Map;

public class AddGroupGoal extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = AddGroupGoal.class.getSimpleName();

    // UI references.
    private EditText currAmountBox;
    private EditText goalAmountBox;
    private EditText goalNameBox;

    private String currAmount;
    private String goalAmount;
    private String goalName;

    private Button saveBtn;

    // Session Manager Class
    SessionManager session;
    /**
     * The Dbct.
     */

    int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_savings_goal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currAmountBox = (EditText) findViewById(R.id.current_box);
        goalAmountBox = (EditText) findViewById(R.id.goal_amountbox);
        goalNameBox = (EditText) findViewById(R.id.goal_name_box);

        session = new SessionManager(getApplicationContext());

        saveBtn = (Button) findViewById(R.id.btn_save_new_goal);
        saveBtn.setOnClickListener(this);
        HashMap<String, String> user = session.getUserDetails();

        groupId = Integer.parseInt(user.get(SessionManager.KEY_GROUPID));

      //  nextButton = (Button) findViewById(R.id.register_next_button);

    }

    private void addGroupGoal() {
        currAmount = currAmountBox.getText().toString().trim();
        goalAmount = goalAmountBox.getText().toString().trim();
        goalName = goalNameBox.getText().toString().trim();

        Log.d("currAmount", goalName);
        new AddNewGoal().execute();
    }

    @Override
    public void onClick(View v) {

        if (v == saveBtn) {
            addGroupGoal();
        }

    }

    // A background async task to load all bars by making http request
    private class AddNewGoal extends AsyncTask<String, String, String> {
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
                    Config.URL_ADD_NEW_GOAL, new Response.Listener<String>() {


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

                        //    JSONObject user = jObj.getJSONObject("success");
                            Log.d("Jsonobj", jObj.toString());

                            addResult();


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
                    Log.d("goalName", goalName);
                    Log.d("currAmount", currAmount);
                    Log.d("goalAmount", goalAmount);
                    Log.d("groupdId", groupId +"");

                    params.put("goalName", goalName);
                    params.put("targetAmount", goalAmount);
                    params.put("currentAmount", currAmount);
                    params.put("groupdId", groupId+"");

                    Log.d("Streng sent", params.toString());
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
            AddGroupGoal.this.runOnUiThread(
                    new Runnable() {
                        public void run() {

                            Log.d("Users", "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
                        }
                    });
        }

    }

    public void addResult() {

        Toast.makeText(getApplicationContext(),
                "Goal Added Success", Toast.LENGTH_LONG).show();

        Intent i = new Intent(this, ViewSavingsGoalsActivity.class);
        startActivity(i);
        finish();


    }





}
