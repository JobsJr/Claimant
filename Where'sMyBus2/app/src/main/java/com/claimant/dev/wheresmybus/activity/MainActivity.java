package com.claimant.dev.wheresmybus.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.claimant.dev.wheresmybus.R;
import com.claimant.dev.wheresmybus.adapter.PlatformRecyclerViewAdapter;
import com.claimant.dev.wheresmybus.provider.PlatformInfoContract;
import com.claimant.dev.wheresmybus.tasks.ParserTask;
import com.claimant.dev.wheresmybus.ui.LoadContentProgressDialog;
import com.claimant.dev.wheresmybus.utils.Utils;

public class MainActivity extends AppCompatActivity implements ParserTask.OnParseCompleted, LoaderManager.LoaderCallbacks<Cursor> {
    private LoadContentProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;
    PlatformRecyclerViewAdapter mRecyclerViewAdapter;
    private static final int LOADER_ID = 2000;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initViews();
        displayProgress();
        //make request based on flag
        if (!Utils.getSyncStatus(this)) {
            handleRequest();
        } else {
            startLoading();
        }
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
                    finish();
                }
            });
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.platform_list_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void displayProgress() {
        mProgressDialog = new LoadContentProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        //TODO:uncomment
//        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setProgressNumberFormat(null);
        mProgressDialog.setProgressPercentFormat(null);
        mProgressDialog.show();
    }

    private void handleRequest() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET,
                "http://www.travel2karnataka.com/bangalore_bus_routes.htm",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!TextUtils.isEmpty(response)) {
                            new ParserTask(response, MainActivity.this).execute();
                        } else {
                            //TODO:Handle this case
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("rajeev", "Failure!!");

            }
        });

        queue.add(stringRequest);
    }

    @Override
    public void onParseCompleted() {
        //start loading the recyclerView
        startLoading();
    }

    private void startLoading() {
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, PlatformInfoContract.PlatformItems.CONTENT_URI, PlatformInfoContract.PlatformItems.PROJECTION_ALL, null, null,
                PlatformInfoContract.PlatformItems.DEFAULT_SORT_ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mRecyclerViewAdapter = new PlatformRecyclerViewAdapter(this, data);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //DO NOTHING
    }
}
