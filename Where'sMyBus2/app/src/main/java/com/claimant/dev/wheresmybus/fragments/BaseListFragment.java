package com.claimant.dev.wheresmybus.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.claimant.dev.wheresmybus.R;
import com.claimant.dev.wheresmybus.activity.MainActivity;
import com.claimant.dev.wheresmybus.adapter.PlatformRecyclerViewAdapter;
import com.claimant.dev.wheresmybus.provider.PlatformInfoContract;
import com.claimant.dev.wheresmybus.tasks.ParserTask;
import com.claimant.dev.wheresmybus.ui.LoadContentProgressDialog;
import com.claimant.dev.wheresmybus.utils.Endpoints;
import com.claimant.dev.wheresmybus.utils.Utils;

/**
 * Created by rajeevkr on 6/15/16.
 */

public class BaseListFragment extends Fragment implements ParserTask.OnParseCompleted, LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "list_fragment";
    private RecyclerView mRecyclerView;
    private LoadContentProgressDialog mProgressDialog;
    private static final int LOADER_ID = 2000;
    private PlatformRecyclerViewAdapter mRecyclerViewAdapter;


    public static BaseListFragment newInstanse(FragmentManager fragmentManager, Activity activity, int containerId, Bundle args) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BaseListFragment baseListFragment = new BaseListFragment();
        baseListFragment.setArguments(args);
        fragmentTransaction.replace(containerId, baseListFragment, TAG);
        fragmentTransaction.commitAllowingStateLoss();
        return baseListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.platform_list_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        displayProgress();
        //make request based on flag
        if (!Utils.getSyncStatus(getActivity())) {
            handleRequest();
        } else {
            startLoading();
        }
    }

    private void handleRequest() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET,
                Endpoints.ENDPOINTS_ENDPOINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!TextUtils.isEmpty(response)) {
                            new ParserTask(response,
                                    getActivity(),
                                    BaseListFragment.this).execute();
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

    private void displayProgress() {
        mProgressDialog = new LoadContentProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        //TODO:uncomment
//        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setProgressNumberFormat(null);
        mProgressDialog.setProgressPercentFormat(null);
        mProgressDialog.show();
    }

    @Override
    public void onParseCompleted() {

    }

    private void startLoading() {
        getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), PlatformInfoContract.PlatformItems.CONTENT_URI, PlatformInfoContract.PlatformItems.PROJECTION_ALL, null, null,
                PlatformInfoContract.PlatformItems.DEFAULT_SORT_ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mRecyclerViewAdapter = new PlatformRecyclerViewAdapter(getActivity(), data);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //Do nothing
    }
}
