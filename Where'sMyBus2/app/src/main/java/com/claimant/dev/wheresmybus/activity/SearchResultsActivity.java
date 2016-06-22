package com.claimant.dev.wheresmybus.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.claimant.dev.wheresmybus.R;
import com.claimant.dev.wheresmybus.fragments.BaseListFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

/**
 * Activity that displays Search results
 * Created by rajeevkr on 6/14/16.
 */

public class SearchResultsActivity extends AppCompatActivity {
    private static final String TAG = "SearchResultsActivity";
    private BaseListFragment mBaseListFragment;
    private MaterialSearchView materialSearchView;

    public static void start(Context context) {
        Intent starter = new Intent(context, SearchResultsActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Log.d(TAG, "In onCreate");
        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putString(BaseListFragment.BUNDLE_KEY_START_MODE, BaseListFragment.BUNDLE_START_MODE_SEARCH);
            mBaseListFragment = BaseListFragment.newInstanse(getSupportFragmentManager(), this, R.id.fragment_content_container, bundle);
        }
        initViews();
    }


    /**
     * Initialize all the UI widgets here
     */
    private void initViews() {
        Toolbar appBarLayout = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout.setVisibility(View.GONE);
        View searchContainer = findViewById(R.id.search_items_view);
        searchContainer.setVisibility(View.GONE);
        materialSearchView = (MaterialSearchView) findViewById(R.id.search_view);
        View searchBackButton = materialSearchView.findViewById(R.id.action_up_btn);
        searchBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        materialSearchView.setCursorDrawable(R.drawable.drawable_cursor);
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (mBaseListFragment != null) {
                    mBaseListFragment.setQueryParams(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return true;
            }
        });

        materialSearchView.post(new Runnable() {
            @Override
            public void run() {
                materialSearchView.showSearch();
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (isKeyBoardOpen()) {
            closeKeyBoard();
        } else {
            finish();
        }

    }

    private void closeKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        View view = this.getCurrentFocus();
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            materialSearchView.clearFocus();
        }
    }

    private boolean isKeyBoardOpen() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText() || materialSearchView.hasFocus()) {
            return true;
        } else {
            return false;
        }
    }
}
