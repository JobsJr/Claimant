package com.claimant.dev.wheresmybus.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.claimant.dev.wheresmybus.activity.MainActivity;
import com.claimant.dev.wheresmybus.parser.PlatformParser;

/**
 * Starts progressbar ,hands over the parsing job to @{@link PlatformParser}
 * in its @doInBackground.Once parsing is completed cancels the progressbar
 * Created by rajeevkr on 5/22/16.
 */
public class ParserTask extends AsyncTask<Void, Void, Void> {
    protected String mHtmlResponse;
    protected Context mContext;
    private ProgressDialog mProgressDialog;

    public ParserTask(String response, Context context) {
        mHtmlResponse = response;
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        PlatformParser parser = new PlatformParser(mContext);
        parser.startDocParsing(mHtmlResponse);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (mContext instanceof MainActivity) {
            ((MainActivity) mContext).onParseCompleted();
        }
    }

    public static interface OnParseCompleted {
        void onParseCompleted();
    }
}
