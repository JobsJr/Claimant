package com.example.rajeevkr.wheresmybus.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rajeevkr.wheresmybus.R;
import com.example.rajeevkr.wheresmybus.tasks.ParserTask;
import com.example.rajeevkr.wheresmybus.ui.LoadContentProgressDialog;
import com.example.rajeevkr.wheresmybus.utils.Utils;

public class MainActivity extends AppCompatActivity implements ParserTask.OnParseCompleted {
    private LoadContentProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;

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
        }
    }

    /**
     * Initialize all the UI widgets here
     */
    private void initViews() {
        mRecyclerView=(RecyclerView)findViewById(R.id.platform_list_rv);

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
    }
}
