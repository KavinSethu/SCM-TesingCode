package com.elar.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.pnf.elar.app.Login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by VKrishnasamy on 23-09-2016.
 */

public class SmartClassUtil {
    public static String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE};


    public static String[] HOME_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE};
    public static final int PERMISSION_ALL = 7;
    public static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    public static final int REQUEST_PERMISSION_SETTING = 2;

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            Log.d("Network",
                                    "NETWORKNAME: " + anInfo.getTypeName());
                            return true;
                        }
                    }
                }
            }
        }
        //  Toast.makeText(mContext, mContext.getString(R.string.dialog_no_inter_message), Toast.LENGTH_SHORT).show();
        return false;
    }


    public static void showInternetToast(Context ctx, String lanugae) {


        try {

            String title = "Info", msg = "Internet not available, Cross check your internet connectivity and try again", okBtnStr = "OK";
            if (!("en").equalsIgnoreCase(lanugae)) {

                title = "";
                msg = "Internet inte är tillgängliga, dubbelkontrollera din Internet-anslutning och försök igen";
                okBtnStr = "ok";

            }
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(ctx);
            builder.setTitle(title);
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setMessage(msg);
            builder.setPositiveButton(okBtnStr, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setCancelable(false);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void showToast(final Context ctx, final String msg) {


        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();

    }

    public static void PrintMessage(String msg) {
        try {
            Log.e("SmartClass", msg);


        } catch (Exception e) {
            Log.e("SmartClass", e.getMessage());
        }
    }

    public static void showError(String msg) {
        try {
            Log.e("SmartClass", msg);


        } catch (Exception e) {
            Log.e("SmartClass", e.getMessage());
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void checkRuntimePermissionResult(String[] permissions, int[] grantResults, final Activity activity) {
        Map<String, Integer> perms = new HashMap<String, Integer>();
        // Initial
        perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
        perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
        perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
        perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
        perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);

        // Fill with results
        for (int i = 0; i < permissions.length; i++)
            perms.put(permissions[i], grantResults[i]);
        // Check for ACCESS_FINE_LOCATION
        if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                && perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
                ) {

        } else {
            if (!hasPermissions(activity, PERMISSIONS)) {
                boolean isCameraDenied = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA);
                boolean isReadPermistionDenied = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
                boolean isWritePermissionDenied = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                boolean isPhoneState = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_PHONE_STATE);
                boolean isCallPhone = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE);

                if (!isCameraDenied || !isReadPermistionDenied || !isWritePermissionDenied
                        || !isPhoneState || !isCallPhone) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Permission Denied");
                    builder.setMessage("Some permission needed to register. so please switch on camera,storage,phone");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                            intent.setData(uri);
                            activity.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
                    builder.show();

                }
            }
        }
    }

    public static void checkHomeRuntimePermission(final Activity activity) {

        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissionsNeeded = new ArrayList<String>();
            final List<String> permissionsList = new ArrayList<String>();
            if (!addPermission(activity, permissionsList, Manifest.permission.CAMERA))
                permissionsNeeded.add("Camera");
            if (!addPermission(activity, permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
                permissionsNeeded.add("Read External storage");
            if (!addPermission(activity, permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                permissionsNeeded.add("Write External storage");
            if (!addPermission(activity, permissionsList, Manifest.permission.READ_PHONE_STATE))
                permissionsNeeded.add("Phone State");
            if (!addPermission(activity, permissionsList, Manifest.permission.CALL_PHONE))
                permissionsNeeded.add("Call Phone");

           /* if (!addPermission(activity, permissionsList, Manifest.permission.CALL_PHONE))
                permissionsNeeded.add("Phone");
                 if (!addPermission(activity, permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
                permissionsNeeded.add("Location");
            if (!addPermission(activity, permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION))
                permissionsNeeded.add("Coarse Location");
            if (!addPermission(activity, permissionsList, Manifest.permission.CALL_PHONE))
                permissionsNeeded.add("Phone");
            if (!addPermission(activity, permissionsList, Manifest.permission.READ_SMS))
                permissionsNeeded.add("Read SMS");
            if (!addPermission(activity, permissionsList, Manifest.permission.RECEIVE_SMS))
                permissionsNeeded.add("Receive SMS");*/
            /*if (!addPermission(activity, permissionsList, Manifest.permission.READ_CONTACTS))
                permissionsNeeded.add("Read Contacts");
            if (!addPermission(activity, permissionsList, Manifest.permission.WRITE_CONTACTS))
                permissionsNeeded.add("Write Contacts");
*/
            if (permissionsList.size() > 0) {
                if (permissionsNeeded.size() > 0) {
                    // Need Rationale
                    String message = "You need to grant access to ";
                    for (int i = 0; i < permissionsNeeded.size(); i++)
                        message = message + ", " + permissionsNeeded.get(i);
                    showMessageOKCancel(activity, message,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= 23) {
                                        activity.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                                REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                                    }
                                }
                            });
                }
            }

        }

    }

    public static boolean addPermission(Activity context, List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission))
                    return false;
            }
        }
        return true;
    }

    public static void showMessageOKCancel(Activity activity, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
