package com.elar.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ListSession {

	SharedPreferences pref;

	Editor editor;
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	private static final String PREFER_NAME = "AndroidExamplePref";

	// All Shared Preferences Keys
	private static final String IS_USER_LOGIN = "IsUserLoggedIn";

	// User name (make variable public to access from outside)
	public static final String TAG_fName = "fName";
	public static final String TAG_uType = "uType";
	public static final String TAG_cName = "cName";
	public static final String TAG_uName = "uName";
	public static final String TAG_image = "image";

	public ListSession(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	public void addListUserData(Set<String> fName, Set<String> uType,
			Set<String> cName, Set<String> uName, Set<String> image) {

		editor.putStringSet(TAG_fName, fName);
		editor.putStringSet(TAG_uType, uType);
		editor.putStringSet(TAG_cName, cName);
		editor.putStringSet(TAG_uName, uName);
		editor.putStringSet(TAG_image, image);
		editor.commit();
	}
}