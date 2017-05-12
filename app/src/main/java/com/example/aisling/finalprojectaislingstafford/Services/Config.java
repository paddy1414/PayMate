package com.example.aisling.finalprojectaislingstafford.Services;


import javax.net.ssl.SSLEngineResult;

public class Config
{

        //Address of our scripts of the CRUD
//COLLEGE IP ADDRESS: 10.102.11.9

    private static String SEVER = "http://192.168.1.8/paymate_webstuff/";
        public static final String URL_ADD=SEVER +"addUser.php";
        public static final String URL_GET_USER = SEVER +"getUser.php?id=";
        public static final String URL_UPDATE_USER = SEVER +"updateUser.php?id=";
        public static final String URL_LOGIN = SEVER +"login1.php";
        public static String URL_OTHERS_IN_GROUP = SEVER +"get_others_inGroup.php";
        public static String URL_OTHERS_IN_SAVE_GROUP = SEVER +"getUsersWithinTheGroup.php";
        public static String URL_ADD_NEW_GOAL = SEVER +"addANewGoal.php";
        public static String URL_MONEY_I_OWE = SEVER +"money_i_owe.php";
        public static String URL_MONEY_OWE_ME = SEVER +"money_owed_to_me.php";

    public static final String URL_PAY_OFF_DEBT=SEVER +"removeFromUserBill.php";
        public static final String URL_ADD_BILL=SEVER +"addBill.php";
    public static final String URL_GET_BILL_HISTORY= SEVER +"billHistory.php";

        //Keys that will be used to send the request to php scripts
        public static final String KEY_USER_ID = "id";
        public static final String KEY_FIRST_NAME = "firstName";
        public static final String KEY_LAST_NAME = "lastName";
        public static final String KEY_GENDER = "gender";
        public static final String KEY_EMAIL = "email";
        public static final String KEY_PASSWORD = "password";
        public static final String KEY_PROFILE_IMAGE_URL = "profileImageUrl";

        public static final String KEY_ADDRESS_LINE_1 = "addressLine1";
        public static final String KEY_ADDRESS_LINE_2 = "addressLine2";
        public static final String KEY_TOWN = "town";
        public static final String KEY_COUNTY = "county";


        //JSON Tags
        public static final String TAG_JSON_ARRAY="result";
        public static final String TAG_ID = "userId";
        public static final String TAG_FIRST_NAME = "firstName";
        public static final String TAG_LAST_NAME = "lastName";
        public static final String TAG_EMAIL = "email";
        public static final String TAG_PASSWORD = "password";
        public static final String TAG_PROFILE_IMAGE_URL= "profileImageURL";

        //employee id to pass with intent
        public static final String USER_ID = "user_id";
}
