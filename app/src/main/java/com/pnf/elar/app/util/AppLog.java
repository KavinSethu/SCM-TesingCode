package com.pnf.elar.app.util;


import android.util.Log;

import com.pnf.elar.app.BuildConfig;


/**
 * The type App log.
 */
public class AppLog {


    /**
     * The constant TAG.
     */
    public static final String TAG = "RoadWarrior";
    /**
     * The constant isDebug.
     */
    public static final boolean isDebug = true;
    /**
     * The constant URL.
     */
    public static final String URL = "url";
    /**
     * The constant PARAMS.
     */
    public static final String PARAMS = "params";
    /**
     * The constant RESPONSE.
     */
    public static final String RESPONSE = "response";

    /**
     * Log.
     *
     * @param tag     the tag
     * @param message the message
     */
    public static final void Log(String tag, String message) {
        if (BuildConfig.DEBUG) {


            try {
                Log.i(tag, message + "");
            } catch (Exception e) {

            }
        }
    }

    /**
     * Log url.
     *
     * @param tag     the tag
     * @param message the message
     */
    public static final void LogURL(String tag, String message) {
        if (BuildConfig.DEBUG) {
            try {
                Log.d(TAG, tag + " \n " + URL + "\n" + message + "");
            } catch (Exception e) {

            }
        }
    }

    /**
     * Log parameters.
     *
     * @param tag     the tag
     * @param message the message
     */
    public static final void LogParameters(String tag, String message) {
        if (BuildConfig.DEBUG) {
            try {
                Log.d(TAG, tag + " \n " + PARAMS + "\n" + message + "");
            } catch (Exception e) {

            }
        }
    }

    /**
     * Log response.
     *
     * @param tag     the tag
     * @param message the message
     */
    public static final void LogResponse(String tag, String message) {
        if (BuildConfig.DEBUG) {
            try {
                Log.d(TAG, tag + " \n " + RESPONSE + "\n" + message + "");
            } catch (Exception e) {

            }
        }
    }

    /**
     * Handle exception.
     *
     * @param tag the tag
     * @param e   the e
     */
    public static final void handleException(String tag, Exception e) {
        if (BuildConfig.DEBUG) {
            if (e != null) {
                Log.d(tag, e.getMessage() + "");
                e.printStackTrace();
            }
        }
    }

}
