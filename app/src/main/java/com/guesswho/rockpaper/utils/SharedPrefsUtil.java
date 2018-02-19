package com.guesswho.rockpaper.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.guesswho.rockpaper.activities.Constants;

/**
 * Created by GuessWh0o on 19.02.2018.
 * Email: developerint97@gmail.com
 */

public class SharedPrefsUtil {
    private Context context;

    public SharedPrefsUtil(Context context) {
        this.context = context;
    }

    public void saveString(String key, String value) {
        SharedPreferences pSharedPref = context.getSharedPreferences(Constants.prefsName,
                Context.MODE_PRIVATE);
        if (pSharedPref != null && key != null && value != null) {
            SharedPreferences.Editor editor = pSharedPref.edit();
            editor.putString(key, value);
            editor.apply();
        }
    }


    //LOADING String

    public String getString(String key) {
        SharedPreferences pSharedPref = context.getSharedPreferences(Constants.prefsName, Context.MODE_PRIVATE);
        if (pSharedPref != null && key != null) {
            return pSharedPref.getString(key, null);
        }
        return null;
    }
}
