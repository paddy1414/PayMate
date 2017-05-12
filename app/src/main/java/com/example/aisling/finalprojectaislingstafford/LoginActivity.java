package com.example.aisling.finalprojectaislingstafford;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.AutoCompleteTextView;
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
import com.example.aisling.finalprojectaislingstafford.Services.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    private static final String TAG = LoginActivity.class.getSimpleName();

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private static final int REGISTER_REQUEST = 0;
    private String id;
    private Button mEmailSignInButton;
    private Button registerButton;
    public static final String USER_NAME = "USERNAME";
    String email;
    String password;

    User useMe;

    // Session Manager Class
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();

        id = intent.getStringExtra(Config.KEY_USER_ID);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mEmailSignInButton.setOnClickListener(this);


          registerButton = (Button)  findViewById(R.id.register_button);
        registerButton.setOnClickListener(this);

        mLoginFormView = findViewById(R.id.login_form);

        mProgressView = findViewById(R.id.login_progress);

        // Session Manager
        session = new SessionManager(getApplicationContext());

    }

    private void login() {
        email = mEmailView.getText().toString().trim();
        password = mPasswordView.getText().toString().trim();
        Log.d("login?", "here");

        if(email.equals("") || password.equals(""))
        {
            Toast.makeText(LoginActivity.this, "Email and Password must be entered to continue ", Toast.LENGTH_LONG).show();

            return;
        }

        else
        {
            new LoginUser().execute();
          //  userLogin(email, password);
        }

    }


    private void loginResult(User user) {
  //     if (user != null) {
            //if (!user.getfName().equals("") & !user.getEmail().equals("")) {
            Log.d("User", user.toString() + " /nUser from the success if");

       // useMe = new User(userId,uid, addressId, groupId, email,pass, fname, lName );

        //     Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
            session.createLoginSession(user.getuId(), user.getUnique_id(), user.getAddrId(), user.getGroupId(), user.getuEmail(), user.getuPass(), user.getfName(), user.getlName());
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
    /*    } else {
            //     Log.d("User", user.toString() + " /nUser from the error else");
            showErrorMessage();
        } */
    }

    private void showErrorMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        dialogBuilder.setMessage("Incorrect user details");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }
  //  ProgressDialog myDialog;

    // A background async task to load all bars by making http request
    private class LoginUser extends AsyncTask<String, String, String> {
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
                    Config.URL_LOGIN, new Response.Listener<String>() {


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
                            String userIdS = user.getString("userId");
                            int userId =Integer.parseInt(userIdS);
                            String uid = jObj.getString("uid");

                            String fname = user.getString("firstName");
                            String lName = user.getString("lastName");
                            String email = user.getString("email");

                            String pass = user.getString("password");
                            int addressId = user.getInt("addressId");
                            int groupId = user.getInt("groupId");


                            // Inserting row in users table

                     //           public User(int uId, int addrId, int groupId, String uEmail, String uPass, String fName, String lName, String unique_id) {

                                useMe = new User(userId,uid, addressId, groupId, email,pass, fname, lName );
                            //        useMe = new User(uid, fname, lName, email);


                            loginResult(useMe);

                            Log.d("UserMeLogin", useMe.toString());

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
                    params.put("email", email);
                    params.put("password", password);

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
            LoginActivity.this.runOnUiThread(
                    new Runnable() {
                        public void run() {

                            Log.d("Users", "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
                        }
                    });
        }


    }

    @Override
    public void onClick(View v) {

        if (v == mEmailSignInButton) {
            login();

        }

        if (v == registerButton) {
            startActivity(new Intent(this, RegisterActivity.class));


        }

    }
}








