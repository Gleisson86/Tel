package com.tel.gleisson.android.tel.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.tel.gleisson.android.tel.R;
import com.tel.gleisson.android.tel.util.ClasseUtil;

/**
 * Created by Gleisson 15/10/2016.
 */

public class BaseActivity extends ClasseUtil {

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public BaseActivity(Context context) {
        super(context);
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

}