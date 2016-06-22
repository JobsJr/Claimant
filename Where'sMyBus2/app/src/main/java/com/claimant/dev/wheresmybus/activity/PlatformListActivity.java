package com.claimant.dev.wheresmybus.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.claimant.dev.wheresmybus.R;
import com.claimant.dev.wheresmybus.fragments.BaseListFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


public class PlatformListActivity extends AppCompatActivity {
    private static final String TAG = "PlatformListActivity";

    private BaseListFragment mBaseListFragment;
    private MaterialSearchView mSearchView;

    public static void start(Context context) {
        Intent starter = new Intent(context, PlatformListActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        if (savedInstanceState == null) {
            mBaseListFragment = BaseListFragment.newInstanse(getSupportFragmentManager(), this, R.id.fragment_content_container, null);
        }

        initViews();
    }

    /**
     * Initialize all the UI widgets here
     */
    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Where's My Bus");
            toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_18dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            setSupportActionBar(toolbar);
        }

        MaterialSearchView materialSearchView = (MaterialSearchView) findViewById(R.id.search_view);
        materialSearchView.setVisibility(View.GONE);

        View searchContainer = findViewById(R.id.search_items_view);
        searchContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchResultsActivity.start(PlatformListActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
