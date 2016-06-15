package com.claimant.dev.wheresmybus.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Activity that displays Search results
 * Created by rajeevkr on 6/14/16.
 */

public class SearchResultsActivity extends Activity {
    private static final String TAG = "SearchResultsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "In onCreate");

    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            Log.d(TAG, "In SearchResultsActivity");
            String query = intent.getStringExtra(SearchManager.QUERY);
        }
    }


}
