package com.elar.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.provider.Settings;
import android.widget.Toast;

import com.pnf.elar.app.Drawer;
import com.pnf.elar.app.R;

import java.net.MalformedURLException;


/**
 * Created by Siva Kumar on 14-04-2016.
 */
    public class NetworkUtil {

    private Context _context;
    private static NetworkUtil networkUtil;

    public NetworkUtil(Context context){
        this._context = context;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public static NetworkUtil getInstance(Context context){

        if(networkUtil == null){
            synchronized (NetworkUtil.class){
                networkUtil = new NetworkUtil(context);
            }
        }
        return  networkUtil;

    }

    public boolean isInternet() throws MalformedURLException {

        boolean isConnectionAvail = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) _context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if(netInfo != null)
                return netInfo.isConnected();
            else
                Toast.makeText(_context, _context.getResources().getString(R.string.network_turnon_error), Toast.LENGTH_SHORT).show();
            return isConnectionAvail;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnectionAvail;
    }


   /* public void showNetworkSettingsAlert(final Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle(context.getResources().getString(R.string.internet));

        alertDialog.setMessage(context.getResources().getString(R.string.error_internet));

        alertDialog.setPositiveButton(R.string.set_network, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                context.startActivity(intent);
            }
        });

        //On pressing cancel button
        alertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }*/


    public void showAlert(final Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle(context.getResources().getString(R.string.internet));

        alertDialog.setMessage(context.getResources().getString(R.string.error_internet));

        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context, Drawer.class);
                context.startActivity(intent);
            }
        });

//        //On pressing cancel button
//        alertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which)
//            {
//                dialog.cancel();
//            }
//        });

        alertDialog.show();
    }

}

