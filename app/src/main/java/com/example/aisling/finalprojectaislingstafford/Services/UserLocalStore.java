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
import android.content.SharedPreferences;

import com.example.aisling.finalprojectaislingstafford.DTO.User;


public class UserLocalStore {

    /**
     * The constant SP_NAME.
     */
    public static final String SP_NAME = "userDetails";

    /**
     * The User local database.
     */
    SharedPreferences userLocalDatabase;

    /**
     * Instantiates a new User local store.
     *
     * @param context the context
     */
    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    /**
     * Store user data.
     *
     * @param user the user
     */
    public void storeUserData(User user) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putInt("id", user.getuId());
        userLocalDatabaseEditor.putString("fName", user.getfName());
        userLocalDatabaseEditor.putString("lName", user.getlName());
        userLocalDatabaseEditor.putString("email", user.getuEmail());
        userLocalDatabaseEditor.putString("uPassword", user.getuPass());
        userLocalDatabaseEditor.putInt("groupId", user.getGroupId());
        userLocalDatabaseEditor.putInt("addrId", user.getAddrId());
        userLocalDatabaseEditor.commit();
    }

    /**
     * Sets user logged in.
     *
     * @param loggedIn the logged in
     */
    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        userLocalDatabaseEditor.commit();
    }

    /**
     * Clear user data.
     */
    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.commit();
    }

    /**
     * Gets logged in user.
     *
     * @return the logged in user
     */
    public User getLoggedInUser() {
        if (userLocalDatabase.getBoolean("loggedIn", false) == false) {
            return null;
        }

        int id = userLocalDatabase.getInt("id", -1);
        String uID = userLocalDatabase.getString("uId", "");
        String fName = userLocalDatabase.getString("fName", "");
        String lName = userLocalDatabase.getString("lName", "");
        String email = userLocalDatabase.getString("email", "");
        String uPassword = userLocalDatabase.getString("uPassword", "");
        int groupId = userLocalDatabase.getInt("groupId",-1);
        int addrId = userLocalDatabase.getInt("addrId" ,-1);


        User user = new  User(id,uID, addrId, groupId, email,uPassword, fName, lName );
        return user;
    }
}
