package com.claimant.dev.wheresmybus.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.claimant.dev.wheresmybus.adapter.PlatformRecyclerViewAdapter;
import com.claimant.dev.wheresmybus.provider.PlatformInfoContract;
import com.claimant.dev.wheresmybus.tasks.ParserTask;
import com.claimant.dev.wheresmybus.ui.LoadContentProgressDialog;
import com.claimant.dev.wheresmybus.utils.Endpoints;
import com.claimant.dev.wheresmybus.utils.Utils;

/**
 * Created by rajeevkr on 6/15/16.
 */

public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "SearchFragment";
    private RecyclerView mRecyclerView;
    private static final int LOADER_ID = 2001;
    private PlatformRecyclerViewAdapter mRecyclerViewAdapter;


    public static SearchFragment newInstanse(FragmentManager fragmentManager, Activity activity, int containerId, Bundle args) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SearchFragment searchFragment = new SearchFragment();
        searchFragment.setArguments(args);
        fragmentTransaction.replace(containerId, searchFragment, TAG);
        fragmentTransaction.commitAllowingStateLoss();
        return searchFragment;
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
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //Do nothing
    }
}
