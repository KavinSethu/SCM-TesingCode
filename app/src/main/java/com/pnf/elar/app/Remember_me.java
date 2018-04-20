package com.pnf.elar.app;

import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Remember_me extends ActionBarActivity {
	UserSessionManager session;
	Locale myLocale;
	String lang, username, pass, token;
	Button yes, no;
	String email, passwrd, tkn;
	String user_type;
	String user_id;
	String term_size;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setTheme(R.style.AppTheme);
		setContentView(R.layout.activity_remember_me);

		getSupportActionBar().hide();

		session = new UserSessionManager(getApplicationContext());

		no = (Button) findViewById(R.id.btn1);
		yes = (Button) findViewById(R.id.btn2);

		try {

			Intent in = getIntent();
			user_type = in.getStringExtra("user_type");
			user_id = in.getStringExtra("user_id");
			term_size = in.getStringExtra("term_size");

		} catch (Exception e) {
			// TODO: handle exception
		}

		no.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// session.logoutUser();



			/*	if (term_size.equalsIgnoreCase("0")){

				Intent in = new Intent(getApplicationContext(), Drawer.class);
				in.putExtra("user_type", user_type);
				startActivity(in);
				finish();
			}else
			{
				Intent in = new Intent(getApplicationContext(), TermsOfUseActivity.class);
				in.putExtra("user_type", user_type);
				in.putExtra("user_id", user_id);
				startActivity(in);
				finish();
			}*/



				Intent in = new Intent(getApplicationContext(), Drawer.class);
				in.putExtra("user_type", user_type);
				startActivity(in);
				finish();
			}
		});

		yes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

	/*			if (term_size.equalsIgnoreCase("0")) {
				Intent in = new Intent(getApplicationContext(), Drawer.class);
				in.putExtra("user_type", user_type);
				startActivity(in);
				String Yes = "yes";
				session.rememberme(Yes);
				finish();
				}

				else {
					Intent in = new Intent(getApplicationContext(), TermsOfUseActivity.class);
					in.putExtra("user_type", user_type);
					in.putExtra("user_id", user_id);
					startActivity(in);
					String Yes = "yes";
					session.rememberme(Yes);
					finish();
				}*/


				Intent in = new Intent(getApplicationContext(), Drawer.class);
				in.putExtra("user_type", user_type);
				startActivity(in);
				String Yes = "yes";
				session.rememberme(Yes);
				finish();
			}
		});
	}

	// public void setLocale(String lang) {
	//
	// myLocale = new Locale(lang);
	// Resources res = getResources();
	// DisplayMetrics dm = res.getDisplayMetrics();
	// Configuration conf = res.getConfiguration();
	// conf.locale = myLocale;
	// res.updateConfiguration(conf, dm);
	// Intent refresh = new Intent(this, Login.class);
	// startActivity(refresh);
	//
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {

		Intent in = new Intent(getApplicationContext(), Remember_me.class);
		startActivity(in);

		return;
	}
}
