package com.pnf.elar.app.util;

import com.google.gson.Gson;

/**
 * Created by anantharaj on 17/6/16.
 */
public class JSONUtils {
    private static final Gson gson = new Gson();

    public static boolean isJSONValid(String JSON_STRING) {
        try {
            gson.fromJson(JSON_STRING, Object.class);
            return true;
        } catch (com.google.gson.JsonSyntaxException ex) {
            return false;
        }

    }
}
