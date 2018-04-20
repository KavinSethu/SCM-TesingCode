package com.pnf.elar.app;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

public class UserSessionManager {

	SharedPreferences pref,pre;

	Editor editor,edtr;
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	private static final String PREFER_NAME = "AndroidExamplePref";
	private static final String PRE_NAME = "AndroidPref";

	// All Shared Preferences Keys
	private static final String IS_USER_LOGIN = "IsUserLoggedIn";

	// User name (make variable public to access from outside)
	public static final String TAG_username = "username";
	public static final String TAG_password = "password";
	public static final String TAG_language = "language";
	public static final String TAG_Base_url = "Base_url";
	public static final String TAG_Authntication_token = "token";
	public static final String TAG_Authntication_new_tokn = "abc";
	public static final String TAG_Authntication_Children = "child";
	public static final String TAG_child_id = "child_id";
	public static final String TAG_cld_nm = "cld_nm";
	public static final String TAG_prnt_id = "prnt_id";
	public static final String TAG_securityKey = "securityKey";
	public static final String TAG_Remember = "yes";
	public static final String TAG_user_type = "user_type";
	public static final String TAG_user_id = "user_id";
	public static final String TAG_cur_ids = "cur_ids";
	public static final String TAG_regId = "regId";
	public static final String TAG_publish_screen = "publish_screen";
	public static final String TAG_main_post = "edit_main";
	public static final String TAG_main_news = "main_news";
	public static final String TAG_main_news_main = "main_news_main";
	public static final String TAG_ParentOrChild = "ParentOrChild";
	public static final String TAG_SwitchParent = "SwitchP";
	public static final String TAG_newTokn = "newT";
	public static final String TAG_OldTokn = "OldT";
	public static final String TAG_eduBlog_count = "eduBlog_count";
	public static final String TAG_eduBlog_countTeacher = "eduBlog_countTeacher";
	public static final String TAG_news_countTeacher = "news_countTeacher";
	public static final String TAG_news_count = "news_count";
	public static final String TAG_child_image = "child_image";

	public static final String TAG_USR_FirstName="USR_FirstName";
	public static final String TAG_USR_LastName="USR_LastName";

	public static final String TAG_select_Group="select_group";
	public static final String TAG_select_Child_Image="select_child_image";

	

	public UserSessionManager(Context context) {

//		if (true)
//		{
//			Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show();
//		}
//		else {
			this._context = context;
			pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
			editor = pref.edit();
			pre = _context.getSharedPreferences(PRE_NAME, PRIVATE_MODE);
			edtr = pre.edit();
//		}
	}

	public void createUserLoginSession(String token, String user_type,
			String user_id) {

		editor.putBoolean(IS_USER_LOGIN, true);

		editor.putString(TAG_Authntication_token, token);
		editor.putString(TAG_user_type, user_type);
		editor.putString(TAG_user_id, user_id);
		editor.commit();

	}

	public void createUserLoginSession(String Login_Email,
			String Login_Password, String language, String securityKey,
			String Base_url, String regId) {

		editor.putBoolean(IS_USER_LOGIN, true);
		editor.putString(TAG_username, Login_Email);
		editor.putString(TAG_password, Login_Password);

		editor.putString(TAG_securityKey, securityKey);
		editor.putString(TAG_Base_url, Base_url);
		editor.putString(TAG_regId, regId);
		editor.commit();

	}
	public void putChildImage(String image) {

		editor.putString(TAG_child_image, image);
		editor.commit();
	}

	/*public void putgetUserType(String type)
	{

		editor.putString(TAGCH)

	}*/
	public String getUserId()
	{
		return pref.getString(TAG_user_id,"");
	}
	public void NewAuthnticationToken(String new_tokn) {
		// TODO Auto-generated method stub

		editor.putBoolean(IS_USER_LOGIN, true);

		editor.putString(TAG_Authntication_new_tokn, new_tokn);
		editor.commit();
	}


	public void putSelectedGroup(String select_group)
	{

		editor.putString(TAG_select_Group, select_group);
		editor.commit();

	}

	public String getSelectedGroup()
	{
		return pref.getString(TAG_select_Group,"");
	}

	public void putSelectedChildImage(String select_image)
	{

		editor.putString(TAG_select_Child_Image, select_image);
		editor.commit();


	}

	public String getSelectedChildImage()
	{
		return pref.getString(TAG_select_Child_Image,"");
	}





	public void rememberme(String yes) {
		// TODO Auto-generated method stub
		editor.putBoolean(IS_USER_LOGIN, true);

		editor.putString(TAG_Remember, yes);

		editor.commit();

	}

	public void childrenauthoken(String prnt_id, String string, String cld_nm ,String child_id , String USR_image,String USR_FirstName,String USR_LastName) {
		// TODO Auto-generated method stub

		editor.putBoolean(IS_USER_LOGIN, true);
		editor.putString(TAG_prnt_id, prnt_id);
		editor.putString(TAG_Authntication_Children, string);
		editor.putString(TAG_cld_nm, cld_nm);
		editor.putString(TAG_child_id, child_id);
		editor.putString(TAG_child_image, USR_image);
		editor.putString(TAG_USR_FirstName,USR_FirstName);
		editor.putString(TAG_USR_LastName,USR_LastName);

		editor.commit();

	}

	public HashMap<String, String> getUserDetails() {

		HashMap<String, String> user = new HashMap<String, String>();

		user.put(TAG_username, pref.getString(TAG_username, null));
		user.put(TAG_password, pref.getString(TAG_password, null));
		user.put(TAG_language, pref.getString(TAG_language, null));
		user.put(TAG_Base_url, pref.getString(TAG_Base_url, null));
		user.put(TAG_Authntication_token,
				pref.getString(TAG_Authntication_token, null));
		user.put(TAG_Authntication_new_tokn,
				pref.getString(TAG_Authntication_new_tokn, null));
		user.put(TAG_Authntication_Children,
				pref.getString(TAG_Authntication_Children, null));
		user.put(TAG_user_type, pref.getString(TAG_user_type, null));

/*
		user.put(TAG_cur_ids, pref.getString(TAG_cur_ids, null));
*/

		user.put(TAG_regId, pref.getString(TAG_regId, null));
		user.put(TAG_publish_screen, pref.getString(TAG_publish_screen, null));
		user.put(TAG_main_post, pref.getString(TAG_main_post, null));
		user.put(TAG_main_news, pref.getString(TAG_main_news, null));
		user.put(TAG_main_news_main, pref.getString(TAG_main_news_main, null));
		user.put(TAG_user_id, pref.getString(TAG_user_id, null));
		user.put(TAG_ParentOrChild, pref.getString(TAG_ParentOrChild, null));
		user.put(TAG_SwitchParent, pref.getString(TAG_SwitchParent, null));
		user.put(TAG_newTokn, pref.getString(TAG_newTokn, null));
		user.put(TAG_OldTokn, pref.getString(TAG_OldTokn, null));
		user.put(TAG_prnt_id, pref.getString(TAG_prnt_id, null));
		user.put(TAG_cld_nm, pref.getString(TAG_cld_nm, null));
		user.put(TAG_child_id, pref.getString(TAG_child_id, null));
		user.put(TAG_eduBlog_count, pref.getString(TAG_eduBlog_count, null));
		user.put(TAG_news_count, pref.getString(TAG_news_count, null));
		user.put(TAG_child_image, pref.getString(TAG_child_image, null));
		user.put(TAG_eduBlog_countTeacher, pref.getString(TAG_eduBlog_countTeacher, null));
		user.put(TAG_news_countTeacher, pref.getString(TAG_news_countTeacher, null));
		user.put(TAG_USR_FirstName,pref.getString(TAG_USR_FirstName,null));
		user.put(TAG_USR_LastName,pref.getString(TAG_USR_LastName,null));

		user.put(TAG_select_Group,pref.getString(TAG_select_Group,null));
		return user;
	}

	public void curid()
	{
		edtr.clear();
	}

	public void logoutUser() {

		// Clearing all user data from Shared Preferences
		editor.clear();
		editor.commit();

		// After logout redirect user to Login Activity
		Intent i = new Intent(_context, Login.class);

		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		// Staring Login Activity
		_context.startActivity(i);
	}

	public boolean isUserLoggedIn() {
		return pref.getBoolean(IS_USER_LOGIN, false);
	}

	public boolean isOpened() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	public void cur_tag_ids(String part1) {
		// TODO Auto-generated method stub
		editor.putBoolean(IS_USER_LOGIN, true);
		editor.putString(TAG_cur_ids, part1);
		editor.commit();
	}

	public String getCur_tag_ids() {

		return pref.getString(TAG_cur_ids, "");
	}

	public String getUserPassword()
	{
		String pass=TAG_password;
		return pass;
	}


	public void createpublish(String publish_screen) {
		// TODO Auto-generated method stub
		editor.putBoolean(IS_USER_LOGIN, true);
		editor.putString(TAG_publish_screen, publish_screen);
		editor.commit();

	}

	public void create_main_Screen(String main_post) {
		// TODO Auto-generated method stub

		editor.putBoolean(IS_USER_LOGIN, true);
		editor.putString(TAG_main_post, main_post);
		editor.commit();

	}

	public void createnews(String news_create) {
		// TODO Auto-generated method stub

		editor.putBoolean(IS_USER_LOGIN, true);
		editor.putString(TAG_main_news, news_create);
		editor.commit();

	}

	public void create_main_Screen_news(String main_news) {
		// TODO Auto-generated method stub

		editor.putBoolean(IS_USER_LOGIN, true);
		editor.putString(TAG_main_news_main, main_news);
		editor.commit();

	}

	public void createUserLoginSession(String language) {
		// TODO Auto-generated method stub
		editor.putBoolean(IS_USER_LOGIN, true);
		editor.putString(TAG_language, language);
		editor.commit();
	}

	//CHNAGE BY KAVIN
	public void Language(String language) {
		// TODO Auto-generated method stub
		editor.putString(TAG_language, language);
		editor.commit();
	}

	public void createUserparentorchild(String string) {
		// TODO Auto-generated method stub
		editor.putBoolean(IS_USER_LOGIN, true);
		editor.putString(TAG_ParentOrChild, string);
		editor.commit();

	}

	public void createUserSwitchParent(String sw, String newTokn, String OldTokn) {
		// TODO Auto-generated method stub
		editor.putBoolean(IS_USER_LOGIN, true);
		editor.putString(TAG_SwitchParent, sw);
		editor.putString(TAG_newTokn, newTokn);
		editor.putString(TAG_OldTokn, OldTokn);
		editor.commit();
	}

	public void Educount(String eduBlog_count) {
		// TODO Auto-generated method stub
		editor.putBoolean(IS_USER_LOGIN, true);
		editor.putString(TAG_eduBlog_count, eduBlog_count);
		editor.commit();
	}

	public void Newscount(String news_count) {
		// TODO Auto-generated method stub
		editor.putBoolean(IS_USER_LOGIN, true);
		editor.putString(TAG_news_count, news_count);
		editor.commit();
	}

	public void EduCountTeacher(String eduBlog_count)
	{
		// TODO Auto-generated method stub
		editor.putBoolean(IS_USER_LOGIN, true);
		editor.putString(TAG_eduBlog_countTeacher, eduBlog_count);
		editor.commit();
	}

	public void NewsCountTeacher(String news_count) {
		// TODO Auto-generated method stub
		editor.putBoolean(IS_USER_LOGIN, true);
		editor.putString(TAG_news_countTeacher, news_count);
		editor.commit();
	}

}