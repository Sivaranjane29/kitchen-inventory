

/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kitcheninventory.R;

import es.dmoral.toasty.Toasty;


public class CommonUtils {

    Dialog mDialogProgress;
    TextView txtLoadingMessage;
    private Context mCtx;
    private AlertDialog mAlert;
    private boolean blnReturnValue = false;

    public CommonUtils(Context context) {
        mCtx = context;
    }

    public void showSuccess(String strMessage) {
        Toasty.success(mCtx, strMessage, Toast.LENGTH_SHORT, true).show();
    }

    public void showError(String strMessage) {
        Toasty.error(mCtx, strMessage, Toast.LENGTH_LONG, true).show();
    }


    public void showWarning(String strMessage) {
        Toasty.warning(mCtx, strMessage, Toast.LENGTH_SHORT, true).show();
    }

    public void showInfo(String strMessage) {
        Toasty.info(mCtx, strMessage, Toast.LENGTH_LONG, true).show();
    }

    public void progress(String strMessage, boolean blnAction) {
        if (blnAction) {

            if (strMessage.trim().length() == 0) {
                strMessage = "Loading..Please wait";
            }
            txtLoadingMessage.setText(strMessage);
            if (!mDialogProgress.isShowing()) {
                mDialogProgress.show();
            }
        } else {
            if (mDialogProgress.isShowing()) {
                mDialogProgress.dismiss();
            }
        }

    }

    public boolean isNetworkAvailable() {

        ConnectivityManager cm =
                (ConnectivityManager) mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
