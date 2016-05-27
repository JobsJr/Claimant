package com.example.rajeevkr.wheresmybus.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.rajeevkr.wheresmybus.parser.PlatformParser;

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
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Please wait while the data loads!!");
        mProgressDialog.show();

    }

    @Override
    protected Void doInBackground(Void... params) {
        PlatformParser parser = new PlatformParser(mContext);
        parser.startDocParsing(mHtmlResponse);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
