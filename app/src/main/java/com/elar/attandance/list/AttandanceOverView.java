package com.elar.attandance.list;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
/*
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONArray;
import org.json.JSONObject;

import com.pnf.elar.app.R;
import com.pnf.elar.app.Drawer;

import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class AttandanceOverView extends Fragment {

	private ListView listForAttOverView;

	private ArrayList<String> absent_day_students_count = new ArrayList<String>();
	private ArrayList<String> absent_note_students_count = new ArrayList<String>();
	private ArrayList<String> left_students_count = new ArrayList<String>();
	private ArrayList<String> name = new ArrayList<String>();
	private ArrayList<String> not_retrieved_students_count = new ArrayList<String>();
	private ArrayList<String> total_student_count = new ArrayList<String>();

	View v;
	UserSessionManager session;
	String auth_key, securityKey, lang, Base_url, Security = "H67jdS7wwfh";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.attandance_over_view, container, false);
		listForAttOverView = (ListView) v.findViewById(R.id.listForAttOverView);

		session = new UserSessionManager(getActivity());
		HashMap<String, String> user = session.getUserDetails();
		lang = user.get(UserSessionManager.TAG_language);
		auth_key = user.get(UserSessionManager.TAG_Authntication_token);
		Base_url = user.get(UserSessionManager.TAG_Base_url);
		
        //////////back to Main Activity ////Set title/////
		((Drawer)getActivity()).setBackFrompublishtomainAttendance();
		if(lang.equalsIgnoreCase("sw"))
		{
			((Drawer)getActivity()).setActionBarTitle("Närvaroöversikt");
		}else {
			((Drawer)getActivity()).setActionBarTitle("Attendance overview");
		}
		/////////////
		
		

		new GetAttandanceOW().execute();

		return v;
	}

	public class AtandanceListAdapter extends BaseAdapter {

		private Activity activity;
		private LayoutInflater inflater = null;
		TextView Absent,Not_retrieved,Absent_notes;

		private ArrayList<String> absent_day_students_count = new ArrayList<String>();
		private ArrayList<String> absent_note_students_count = new ArrayList<String>();
		private ArrayList<String> left_students_count = new ArrayList<String>();
		private ArrayList<String> name = new ArrayList<String>();
		private ArrayList<String> not_retrieved_students_count = new ArrayList<String>();
		private ArrayList<String> total_student_count = new ArrayList<String>();

		public AtandanceListAdapter(Activity a,
				ArrayList<String> absent_day_students_count,
				ArrayList<String> absent_note_students_count,
				ArrayList<String> left_students_count, ArrayList<String> name,
				ArrayList<String> not_retrieved_students_count,
				ArrayList<String> total_student_count) {
			activity = a;
			this.absent_day_students_count = absent_day_students_count;
			this.absent_note_students_count = absent_note_students_count;
			this.left_students_count = left_students_count;
			this.name = name;
			this.not_retrieved_students_count = not_retrieved_students_count;
			this.total_student_count = total_student_count;

			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return absent_day_students_count.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {

			View vi = convertView;
			if (convertView == null)
				vi = inflater.inflate(R.layout.layout_for_att_over_view, null);
			TextView txtForName = (TextView) vi.findViewById(R.id.txtForName);
			Absent= (TextView) vi.findViewById(R.id.Absent);
			Not_retrieved= (TextView) vi.findViewById(R.id.Not_retrieved);
			Absent_notes= (TextView) vi.findViewById(R.id.Absent_notes);
			
			if(lang.equalsIgnoreCase("sw"))
			{
				Absent.setText("Frånvarande");
				Not_retrieved.setText("Ej markerade som närvarande");
				Absent_notes.setText("Frånvaroanmälda");
			}

			TextView txtForTotalMain = (TextView) vi
					.findViewById(R.id.txtForTotalMain);

			TextView totalForNotRet = (TextView) vi
					.findViewById(R.id.totalForNotRet);

			TextView totalForAbsent = (TextView) vi
					.findViewById(R.id.totalForAbsent);

			TextView txtForAbsentNotes = (TextView) vi
					.findViewById(R.id.txtForAbsentNotes);

			txtForName.setText(name.get(position));
			txtForTotalMain.setText("" + left_students_count.get(position)
					+ "/" + total_student_count.get(position));

			txtForAbsentNotes.setText(""
					+ absent_note_students_count.get(position));

			totalForNotRet.setText(""
					+ not_retrieved_students_count.get(position));
			totalForAbsent
					.setText("" + absent_day_students_count.get(position));

			return vi;
		}
	}

	class GetAttandanceOW extends AsyncTask<String, String, String> {

		private String url = Base_url
				+ "lms_api/retrivals/get_attendence_overview";
		String Status="";

		private MyCustomProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new MyCustomProgressDialog(getActivity());
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			dialog.show();

		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {

			String attdResponse = "";
			try {
				String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
						"&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_key, "UTF-8") +
						"&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
						"&" + Const.Params.DATE + "=" +URLEncoder.encode(getCurrentDate(), "UTF-8") +
						"&" + Const.Params.UserTypeApp + "=" +URLEncoder.encode("android", "UTF-8");


				attdResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
			} catch (Exception e) {
				e.printStackTrace();
			}

			/*JSONParser jsonParser = new JSONParser();

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("securityKey", Security));
			params.add(new BasicNameValuePair("authentication_token", auth_key));
			params.add(new BasicNameValuePair("user_type_app", "android"));
			params.add(new BasicNameValuePair("language", lang));
			params.add(new BasicNameValuePair("date", "" + getCurrentDate()));
			Log.e("Create Response -=-=-=-=-=- ", params.toString());
			// Log.e("url", "url:- " + url);

			JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);
*/
/*
			Log.e("Create Response.....", json.toString());
*/

			/*try {

				absent_day_students_count.clear();
				absent_note_students_count.clear();
				left_students_count.clear();
				name.clear();
				not_retrieved_students_count.clear();
				total_student_count.clear();

				JSONObject jsonObj = new JSONObject(json.toString());
				Status = jsonObj.getString("status").toString();

				JSONArray attendence = json.getJSONArray("attendence");
				for (int i = 0; i < attendence.length(); i++) {
					JSONObject attendenceData = attendence.getJSONObject(i);

					String absent_day_students_count1 = attendenceData
							.optString("absent_day_students_count");
					String absent_note_students_count1 = attendenceData
							.optString("absent_note_students_count");
					String left_students1_count = attendenceData
							.optString("left_students_count");
					String name1 = attendenceData.optString("name");
					String not_retrieved_students_count1 = attendenceData
							.optString("not_retrieved_students_count");
					String total_student_count1 = attendenceData
							.optString("total_student_count");

					absent_day_students_count.add(absent_day_students_count1);
					absent_note_students_count.add(absent_note_students_count1);
					left_students_count.add(left_students1_count);
					name.add(name1);
					not_retrieved_students_count
							.add(not_retrieved_students_count1);
					total_student_count.add(total_student_count1);

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
*/
			return attdResponse;
		}

		protected void onPostExecute(String results) {
			// dismiss the dialog once done
			dialog.dismiss();
			try {

				JSONObject jsonObj = new JSONObject();


				absent_day_students_count.clear();
				absent_note_students_count.clear();
				left_students_count.clear();
				name.clear();
				not_retrieved_students_count.clear();
				total_student_count.clear();

				if(results!=null&& !results.isEmpty())
				{

					jsonObj = new JSONObject(results);
					if(jsonObj.has("status"))
					{

						Status = jsonObj.getString("status").toString();
					}
					else
					{
					}


				}
				else
				{
				}



					if (Status.equalsIgnoreCase("true")) {

						JSONArray attendence = jsonObj.getJSONArray("attendence");
						for (int i = 0; i < attendence.length(); i++) {
							JSONObject attendenceData = attendence.getJSONObject(i);

							String absent_day_students_count1 = attendenceData
									.optString("absent_day_students_count");
							String absent_note_students_count1 = attendenceData
									.optString("absent_note_students_count");
							String left_students1_count = attendenceData
									.optString("left_students_count");
							String name1 = attendenceData.optString("name");
							String not_retrieved_students_count1 = attendenceData
									.optString("not_retrieved_students_count");
							String total_student_count1 = attendenceData
									.optString("total_student_count");

							absent_day_students_count.add(absent_day_students_count1);
							absent_note_students_count.add(absent_note_students_count1);
							left_students_count.add(left_students1_count);
							name.add(name1);
							not_retrieved_students_count
									.add(not_retrieved_students_count1);
							total_student_count.add(total_student_count1);
						}

						AtandanceListAdapter adp = new AtandanceListAdapter(
								getActivity(), absent_day_students_count,
								absent_note_students_count, left_students_count,
								name, not_retrieved_students_count,
								total_student_count);

						listForAttOverView.setAdapter(adp);

					} else {
						try {


							String msg = jsonObj.getString("message");
							System.out.print(msg);

							if (lang.equalsIgnoreCase("sw")) {
								System.out.print("Sw_l");
								if (msg.equalsIgnoreCase("Autentisering misslyckades")) {
									Button btnLogout;
									TextView tvMessage;
									final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", getActivity());
									btnLogout = (Button) dialogs.findViewById(R.id.alert_logout_bun);
									tvMessage = (TextView) dialogs.findViewById(R.id.alert_msg);
									tvMessage.setText(msg);

									btnLogout.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											dialogs.dismiss();

											session.logoutUser();

										}
									});

								}
							} else {
								System.out.print("Eng_l");
								if (msg.equalsIgnoreCase("Authentication Failed")) {
									Button btnLogout;
									TextView tvMessage;
									final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", getActivity());
									btnLogout = (Button) dialogs.findViewById(R.id.alert_logout_bun);
									tvMessage = (TextView) dialogs.findViewById(R.id.alert_msg);
									tvMessage.setText(msg);

									btnLogout.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											dialogs.dismiss();

											session.logoutUser();

										}
									});
								}
							}
						} catch (NullPointerException e) {
							e.printStackTrace();
						}
					}

			} catch (Exception e) {
			}
		}

	}

	public String getCurrentDate() {
		String currentDate = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// get current date time with Date()
		Date date = new Date();
		System.out.println(dateFormat.format(date));

		// get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime()));
		currentDate = dateFormat.format(cal.getTime());
		// Log.e("current date", currentDate);
		return currentDate;
	}

}
