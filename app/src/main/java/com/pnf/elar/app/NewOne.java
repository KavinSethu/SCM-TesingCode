package com.pnf.elar.app;

/*import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/

import android.app.Fragment;
		import android.os.Bundle;
		import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
		import android.view.ViewGroup;
		import android.widget.LinearLayout;
		import android.widget.TextView;

@SuppressWarnings("deprecation")
public class NewOne extends Fragment {

	TextView User_FstName, User_LastName, adtnl_info, des, allerg;
	String fst_name, last_name, image;
	LinearLayout Gardians;
	View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		v = inflater.inflate(R.layout.activity_new_one, container, false);

		User_FstName = (TextView) v.findViewById(R.id.User_FstName);
		User_LastName = (TextView) v.findViewById(R.id.User_LastName);
		Gardians = (LinearLayout) v.findViewById(R.id.Gardians);
		adtnl_info = (TextView) v.findViewById(R.id.adtnl_info);
		des = (TextView) v.findViewById(R.id.des);
		allerg = (TextView) v.findViewById(R.id.allerg);

		/*new ProfileDetail().execute();*/
		// ////

		return v;

	}

	/*// //////
	class ProfileDetail extends AsyncTask<String, String, String> {

		String Status, contact_info, information, allergy_name;
		String[] Parents_Name;
		private static final String BASE_URL = "lms_api/retrivals/user_profile";
		private MyCustomProgressDialog dialog;
		private static final String url = "http://ps.pnf-sites.info/lms_api/retrivals/user_profile";

		private static final String TAG_STATUS = "status";
		private static final String TAG_student = "student";
		private static final String TAG_USR_FirstName = "USR_FirstName";
		private static final String TAG_USR_LastName = "USR_LastName";
		private static final String TAG_USR_image = "USR_image";
		private static final String TAG_parent = "parent";
		private static final String TAG_User = "User";
		private static final String TAG_allergy = "allergy";
		private static final String TAG_contact_info = "contact_info";
		private static final String TAG_allergy_name = "allergy_name";
		private static final String TAG_information = "information";

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new MyCustomProgressDialog(getActivity());
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			dialog.show();
		}

		*//**
		 * Creating product
		 * *//*
		protected String doInBackground(String... args) {


			JSONParser jsonParser = new JSONParser();

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("securityKey", "H67jdS7wwfh"));
			params.add(new BasicNameValuePair("authentication_token",
					"f009415940c48425d5502d128fcee2e36235b443"));
			params.add(new BasicNameValuePair("language", "sw"));
			params.add(new BasicNameValuePair("student_id", "1"));
			params.add(new BasicNameValuePair("date", "2016-01-12"));

			Log.d("Create Response", params.toString());

			JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

			Log.d("urlllllll", json.toString());

			try {

				JSONObject jsonObj = new JSONObject(json.toString());

				Status = jsonObj.getString(TAG_STATUS);
				JSONObject Stdnt = jsonObj.getJSONObject(TAG_student);
				fst_name = Stdnt.getString(TAG_USR_FirstName) + " "
						+ Stdnt.getString(TAG_USR_FirstName);
				last_name = Stdnt.getString(TAG_USR_LastName);
				image = Stdnt.getString(TAG_USR_image);

				JSONArray parent = jsonObj.optJSONArray(TAG_parent);
				Parents_Name = new String[parent.length()];
				for (int j = 0; j < parent.length(); j++) {
					JSONObject c = parent.getJSONObject(j);
					JSONObject usr = c.getJSONObject(TAG_User);
					Parents_Name[j] = usr.optString(TAG_USR_FirstName);// +" "+c.optString(TAG_USR_LastName);

				}
				JSONObject allergy = jsonObj.getJSONObject(TAG_allergy);
				contact_info = allergy.getString(TAG_contact_info);
				information = allergy.getString(TAG_information);
				allergy_name = allergy.getString(TAG_allergy_name);

				if (Status.equalsIgnoreCase("true")) {

				} else {

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			dialog.dismiss();

			try {

				if (Status.equalsIgnoreCase("true")) {

					// Log.i("fst_name", fst_name);
					// Log.i("last_name", last_name);
					User_FstName.setText(fst_name);
					adtnl_info.setText(contact_info);
					allerg.setText(allergy_name);
					des.setText(information);

					Log.i("Parents_Name", Arrays.deepToString(Parents_Name));

					Gardians.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							try {

								// if (!(std.get(position).length == 0)) {
								Dialog dialog = new Dialog(getActivity());

								dialog.setContentView(R.layout.dialoglayout);
								dialog.setTitle("Guardians Name ");

								dialog.setCancelable(true);
								dialog.setCanceledOnTouchOutside(true);

								ListView dialog_ListView = (ListView) dialog
										.findViewById(R.id.dialoglist);
								ArrayAdapter<String> adapter = new ArrayAdapter<String>(
										getActivity(),

										android.R.layout.simple_list_item_1,
										Parents_Name);
								dialog_ListView.setAdapter(adapter);
								dialog.show();

								// }
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					});

				} else {

				}
			} catch (Exception e) {
			}
		}

	}*/

	// //////
	// //////
	// //////

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
}
