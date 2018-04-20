package com.pnf.elar.app;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;

/*import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;

@SuppressWarnings("deprecation")
public class Forgot_password extends ActionBarActivity {
	
	Button submit;
	EditText LoginEmail;
	String Login_Email,Security="H67jdS7wwfh";
	String Status, Message;
	UserSessionManager session;
	String lang;
	
	
	Locale myLocale;
	private static final String BASE_URL ="http://presentation.elar.se/";

	private MyCustomProgressDialog dialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		 setTheme(R.style.AppTheme);
		setContentView(R.layout.activity_forgot_password);
		
		getSupportActionBar().hide();

		dialog = new MyCustomProgressDialog(Forgot_password.this);
		session = new UserSessionManager(getApplicationContext());
		HashMap<String, String> user = session.getUserDetails();


		lang = user.get(UserSessionManager.TAG_language);




		
		
		/*session = new UserSessionManager(getApplicationContext());
		HashMap<String, String> user = session.getUserDetails();

        lang = user.get(UserSessionManager.TAG_language);*/

        Log.d("Language",""+lang);
       
       // Log.i("language", lang);
//        
//        if (lang == "en") {
//
//			
//        	setLocale("en");
//			
//		} else if (lang == "sv") {
//
//			
//			Log.i("rr7777rrr", "running");
//			setLocale("sv");
//		}
//        	
		
		submit=(Button)findViewById(R.id.button);
		LoginEmail = (EditText) findViewById(R.id.login);
		
		
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Login_Email = LoginEmail.getText().toString();
				
			
				new submit().execute();
				
				
				
				
			}
		});
		
		
	}


//	public void setLocale(String lang) {
//
//		myLocale = new Locale(lang);
//		Resources res = getResources();
//		DisplayMetrics dm = res.getDisplayMetrics();
//		Configuration conf = res.getConfiguration();
//		conf.locale = myLocale;
//		res.updateConfiguration(conf, dm);
//		Intent refresh = new Intent(this, Forgot_password.class);
//		startActivity(refresh);
//
//	}

	
	class submit extends AsyncTask<String, String, String> {



		private static final String url = BASE_URL+"lms_api/users/forgot_password?language=sw";

		private static final String TAG_STATUS = "status";
		 String TAG_MESSAGE = Const.Params.Message;
		String lng;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			  dialog.setIndeterminate(true);
			  dialog.setCancelable(false);
			  dialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {

			/*JSONParser jsonParser = new JSONParser();

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			*/
			//Log.i("2222222222", lang);

			try {
				if(lang.equalsIgnoreCase("sv")){
					lng="sw";
				}
				else {
					lng="en";
				}

			}catch (NullPointerException e)
			{
				e.printStackTrace();
			}
			



			String forgetResponse = "";
			try {
				String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
						"&" + Const.Params.Email + "=" + URLEncoder.encode(Login_Email, "UTF-8") +
						"&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8");


				forgetResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
			} catch (Exception e) {
				e.printStackTrace();
			}

			/*params.add(new BasicNameValuePair("securityKey",Security));
			params.add(new BasicNameValuePair("email",Login_Email));
			params.add(new BasicNameValuePair("language",lng));
	

			
			
			
			JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

			Log.d("Create Response", json.toString());

			try {
				Status = json.getString(TAG_STATUS).toString();
				Message = json.getString(TAG_MESSAGE).toString();

				if (Status.equalsIgnoreCase("true")) {

				} else {

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
*/
			return forgetResponse;
		}

		protected void onPostExecute(String results) {
			// dismiss the dialog once done
			dialog.dismiss();
			try {

				JSONObject forgetResponseJson=new JSONObject();


				if(!results.isEmpty()&&results!=null) {

					forgetResponseJson=new JSONObject(results);
					if(forgetResponseJson.has(Const.Params.Status)) {
						Status = forgetResponseJson.getString(TAG_STATUS).toString();
						Message = forgetResponseJson.getString(TAG_MESSAGE).toString();
					}
					else
					{

					}
				}

				if (Status.equalsIgnoreCase("true")) {

					//Toast.makeText(Forgot_password.this, "Success", Toast.LENGTH_SHORT).show();

					Button btnClose;
					TextView tvTitle, tvMessage;
					final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.go_to_login, "Alert", Forgot_password.this);
					btnClose = (Button) dialogs.findViewById(R.id.btn_close);
					tvMessage = (TextView) dialogs.findViewById(R.id.tvMessage);


					btnClose.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							dialogs.dismiss();

							Intent in = new Intent(Forgot_password.this, Login.class);
							in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(in);
							finish();

						}
					});
					


				} else {
					// failed to create product

					if(Message.trim().length()>0) {
						Toast.makeText(getApplicationContext(), Message + "....",
								Toast.LENGTH_LONG).show();
					}
				}
			} catch (Exception e) {
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forgot_password, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		// as you specify a parent activity in AndroidManifest.xml.
				/*int id = item.getItemId();
				if (id == R.id.action_settings) {
					return true;
				}*/
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
	    // do something on back.
		Intent in = new Intent(Forgot_password.this, Login.class);
		
		startActivity(in);
		
		
	    return;
	}
}
