package com.example.rajeevkr.wheresmybus.ui;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.rajeevkr.wheresmybus.R;

/**
 * Created by rajeevkr on 5/29/16.
 */

public class LoadContentProgressDialog extends ProgressDialog {
    public LoadContentProgressDialog(Context context) {
        super(context, R.style.LoadContentDialog);
    }
}
