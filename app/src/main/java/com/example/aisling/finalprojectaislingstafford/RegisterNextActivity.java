package com.example.aisling.finalprojectaislingstafford;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.aisling.finalprojectaislingstafford.DTO.User;
import com.example.aisling.finalprojectaislingstafford.Services.AppController;
import com.example.aisling.finalprojectaislingstafford.Services.Config;
import com.example.aisling.finalprojectaislingstafford.Services.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterNextActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RegisterNextActivity.class.getSimpleName();
    private EditText addressLine1;
    private EditText addressLine2;
    private EditText town;
    private EditText county;

    private String enteredAddressLine1;
    private String enteredAddressLine2;
    private String enteredTown;
    private String enteredCounty;

    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String password;
    private String imageURl;


    private Button registerButton;


    User useMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_next);

  //      Bundle extras = getIntent().getExtras();

//        if (extras != null) {
//            firstName = extras.getString("enteredFirstName");
//            lastName = extras.getString("LAST_NAME");
//            gender = extras.getString("GENDER");
//            email = extras.getString("EMAIL");
//            password = extras.getString("PASSWORD");
//            imageURl = extras.getString("IMAGE_URL");
//        }

        Bundle bundle = getIntent().getExtras();

//Extract the data…
        firstName = bundle.getString("FIRST_NAME");
           lastName = bundle.getString("LAST_NAME");
            gender = bundle.getString("GENDER");
            email = bundle.getString("EMAIL");
            password = bundle.getString("PASSWORD");
            imageURl = bundle.getString("IMAGE_URL");

        addressLine1 = (EditText) findViewById(R.id.Address_line_1);
        addressLine2 = (EditText) findViewById(R.id.Address_line_2);
        town = (EditText) findViewById(R.id.Town);
        county = (EditText) findViewById(R.id.County);


        registerButton = (Button) findViewById(R.id.register_button);

        registerButton.setOnClickListener(this);

    }


    private void addUser() {
        enteredAddressLine1 = addressLine1.getText().toString();
        enteredAddressLine2 = addressLine2.getText().toString();
        enteredTown = town.getText().toString();
        enteredCounty = county.getText().toString();

        new  RegisterUser().execute();

    }
    public void onClick(View v)
    {

        if (v == registerButton)
        {
            addUser();
        }
    }

    // A background async task to load all bars by making http request
    private class RegisterUser extends AsyncTask<String, String, String> {
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
                    Config.URL_ADD, new Response.Listener<String>() {


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

                            JSONObject user = jObj.getJSONObject("user");
                            Log.d("Jsonobj", jObj.toString());
                      //      String uid = jObj.getString("uid");

                            String fname = user.getString("firstName");
                            String lName = user.getString("lastName");
                            String password = user.getString("password");

                            String addressId = user.getString("addressId");
                            String groupId = user.getString("groupId");
                  //          String email = user.getString("email");


                            // Inserting row in users table



                            registerResult();


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
                    Log.d("email", email);
                    Log.d("email", "email");
                    Log.d("password", password);
                    Log.d("password", "password");

                    params.put(Config.KEY_FIRST_NAME, firstName);
                    params.put(Config.KEY_LAST_NAME, lastName);
                    params.put(Config.KEY_GENDER, gender);
                    params.put(Config.KEY_EMAIL, email);
                    params.put(Config.KEY_PASSWORD, password);
                    params.put(Config.KEY_PROFILE_IMAGE_URL, imageURl);

                    params.put("addressLine1", enteredAddressLine1);
                    params.put("addressLine2", enteredAddressLine2);
                    params.put("town", enteredTown);
                    params.put("county", enteredCounty);

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
            RegisterNextActivity.this.runOnUiThread(
                    new Runnable() {
                        public void run() {

                            Log.d("Users", "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
                        }
                    });
        }


    }


    private void registerResult() {

        Toast.makeText(getApplicationContext(),
                "Register Successful! Please Log in to Continue", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();

    }

}
