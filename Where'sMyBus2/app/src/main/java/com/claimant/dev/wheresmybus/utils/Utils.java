package com.claimant.dev.wheresmybus.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rajeevkr on 5/29/16.
 */

public class Utils {
    private static final String PREFS_NAME = "sync_status";
    private static final String KEY_SYNC_STATUS = "key_sync_status";

    public static void setSyncStatus(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_SYNC_STATUS, Boolean.TRUE);
        editor.apply();
    }

    public static boolean getSyncStatus(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean value = sharedPreferences.getBoolean(KEY_SYNC_STATUS, Boolean.FALSE);
        return value;
    }
}
