/*
 * Copyright (c)  2017.  Patrick Norton  All Rights Reserved
 * Email: paddy1414@live.ie
 * Github: https://github.com/paddy1414
 * LinkedIn: www.linkedin.com/in/patricknorton9112
 * Youtube: https://www.youtube.com/channel/UCtYIreGkS7cQa_YwVR-Xqyw
 *
 *
 */

package com.example.aisling.finalprojectaislingstafford.Services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;

import com.example.aisling.finalprojectaislingstafford.LoginActivity;
import com.example.aisling.finalprojectaislingstafford.MainActivity;

import java.util.HashMap;


/**
 * The type Session manager.
 */
public class SessionManager {
    /**
     * The constant KEY_FNAME.
     */
// User name (make variable public to access from outside)
    public static final String KEY_FNAME = "fname";
    /**
     * The constant KEY_LNAME.
     */
// User name (make variable public to access from outside)
    public static final String KEY_LNAME = "lname";
    /**
     * The constant KEY_PASSWORD.
     */
// User name (make variable public to access from outside)
    public static final String KEY_PASSWORD = "password";
    /**
     * The constant KEY_EMAIL.
     */
// Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // Email address (make variable public to access from outside)
    public static final String KEY_GROUPID = "groupid";

    // Email address (make variable public to access from outside)
    public static final String KEY_ADDRID = "addrid";
    /**
     * The constant KEY_ID.
     */
    public static final String KEY_ID = "id";
    // Sharedpref file name
    private static final String PREF_NAME = "LastOrdersLoginPref";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    /**
     * The Pref.
     */
// Shared Preferences
    SharedPreferences pref;
    /**
     * The Editor.
     */
// Editor for Shared preferences
    Editor editor;
    /**
     * The Context.
     */
// Context
    Context _context;
    /**
     * The Private mode.
     */
// Shared pref mode
    int PRIVATE_MODE = 0;

    /**
     * Instantiates a new Session manager.
     *
     * @param context the context
     */
// Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }



    public void createLoginSession(int uId, String unique_id, int addrId, int groupId, String uEmail, String uPass, String fName, String lName) {
        Log.d("loginSessh", fName + " " + lName + " " + uEmail + " " + uPass);
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_ID, uId + "");
        // Storing name in pref
        editor.putString(KEY_FNAME, fName);

        // Storing email in pref
        editor.putString(KEY_LNAME, lName);

        // Storing pass in pref
        editor.putString(KEY_PASSWORD, uPass);

        // Storing email in pref
        editor.putString(KEY_EMAIL, uEmail);

        // Storing email in pref
        editor.putString(KEY_GROUPID, groupId +"");

        // Storing email in pref
        editor.putString(KEY_ADDRID, addrId +"");



        // commit changes
        editor.commit();


    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        } else {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

        }

    }


    /**
     * Get stored session data
     *
     * @return the user details
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_ID, pref.getString(KEY_ID +"", null));

        // user fname
        user.put(KEY_FNAME, pref.getString(KEY_FNAME, null));

        // user lName
        user.put(KEY_LNAME, pref.getString(KEY_LNAME, null));

        // user password
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // user email id
        user.put(KEY_GROUPID, pref.getString(KEY_GROUPID +"", null));

        // user email id
        user.put(KEY_ADDRID, pref.getString(KEY_ADDRID +"" , null));

        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Toast.makeText(_context, "You have now Been Logged out", Toast.LENGTH_SHORT).show();
        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     *
     * @return the boolean
     */
// Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
