package com.example.willthespoderman.babynurtures.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by WILL THE SPODERMAN on 8/19/2015.
 */
public class SessionManager {

    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "BabyLogin";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn, String username, String email, String firstTime, String type) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.putString("keyName", username);
        editor.putString("keyEmail",email);
        editor.putString("keyFirstTime", firstTime);
        editor.putString("keyType",type);
        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void isNationality(String nationality) {
        editor.putString("keyNationality", nationality);
        // commit changes
        editor.commit();
    }

    public void whoCreatedTheBabySitter(String created) {
        editor.putString("keyWhoCreated", created);
        editor.commit();
    }

    public void showList(boolean isLoggedIn, String task, String startTime, String endTime, String created_by){
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.putString("keyTask",task);
        editor.putString("keyStart", startTime);
        editor.putString("keyEnd", endTime);
        editor.putString("keyCreator", created_by);

        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

}