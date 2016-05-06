package com.esgi.teamst.dailyfeed.xmlParsers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by tracysablon on 05/05/2016.
 */
public class NetworkHandler {

    private boolean isConnected;

    public boolean checkConnectivity(Context context) {

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            isConnected = true;
        } else {
            isConnected = false;
        }

        return  isConnected;
    }
}
