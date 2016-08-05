package com.utk.user.helpman;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by utk on 20-03-2016.
 */
public class SharedPrefManager {

    private static final String PREFERENCE_FILE_NAME = "PREF";
    private SharedPreferences sharedPreferences;
    private Context mContext;
    private Editor editor;

    public SharedPrefManager(Context context) {
        this.mContext = context;
        sharedPreferences = context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getValue(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void setValue(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void clearSharedPreferences() {
        editor.clear();
        editor.commit();
    }
}
