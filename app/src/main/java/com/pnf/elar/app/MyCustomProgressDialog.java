package com.pnf.elar.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;


public class MyCustomProgressDialog extends ProgressDialog {

	public MyCustomProgressDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private AnimationDrawable animation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_custom_progress_dialog);
		ImageView la = (ImageView) findViewById(R.id.animation);
		la.setBackgroundResource(R.drawable.loading_animation);
		animation = (AnimationDrawable) la.getBackground();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void show() {
		super.show();
		/*runOnUiThread(new Runnable() {
			@Override
			public void run() {*/

				animation.start();
			/*}
		});*/
	}

	@Override
	public void dismiss() {
		super.dismiss();
		/*runOnUiThread(new Runnable() {
			@Override
			public void run() {*/
				animation.stop();
			/*}
		});*/
	}
}
