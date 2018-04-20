package com.elar.attandance.list;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
/*
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONArray;
import org.json.JSONObject;

import com.pnf.elar.app.R;
import com.pnf.elar.app.Drawer;
import com.pnf.elar.app.ImageLoadernew;

import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.Publish.BitmapHelper;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NoteHistoryFragment extends Fragment {
	
	private static int LOAD_IMAGE_RESULTS = 1;
	View v;
	String studentId ,imagePath,i_path;
	Button btnForEditProfile;
	private TextView txtForUserName;

	private ListView listForNoteHistory;

	// / array list for list view --------------------- start
	private ArrayList<String> listForLeaveType = new ArrayList<String>();
	private ArrayList<String> listForType = new ArrayList<String>();
	private ArrayList<String> listForRetriever = new ArrayList<String>();
	private ArrayList<String> listForContact = new ArrayList<String>();
	private ArrayList<String> listForDesWritenBy = new ArrayList<String>();
	private ArrayList<String> listForDesc = new ArrayList<String>();
	private ArrayList<String> listForImage = new ArrayList<String>();
	private ArrayList<String> listForDate = new ArrayList<String>();
	// / array list for list view --------------------- stop
	private ArrayList<String> listForGruardian = new ArrayList<String>();

	private TextView txtForGuardian1, txtForGuardian2, txtForGuardian;

	String auth_key, securityKey, lang, Base_url, Security = "H67jdS7wwfh";
	UserSessionManager session;

	ImageView imgForUserImage;
	ImageLoadernew imgLoader;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.note_history_fragment, container, false);

		txtForGuardian1 = (TextView) v.findViewById(R.id.txtForGuardian1);
		txtForGuardian2 = (TextView) v.findViewById(R.id.txtForGuardian2);
		txtForGuardian = (TextView) v.findViewById(R.id.txtForGuardian);
		imgForUserImage = (ImageView) v.findViewById(R.id.imgForUserImage);
		btnForEditProfile=(Button) v.findViewById(R.id.btnForEditProfile);

		session = new UserSessionManager(getActivity());
		HashMap<String, String> user = session.getUserDetails();
		lang = user.get(UserSessionManager.TAG_language);
		auth_key = user.get(UserSessionManager.TAG_Authntication_token);
		Base_url = user.get(UserSessionManager.TAG_Base_url);
		
		btnForEditProfile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, LOAD_IMAGE_RESULTS);
				
			}
		});
		
        //////////back to Main Activity ////Set title/////
		((Drawer)getActivity()).setBackFrompublishtomainAttendance();
		if(lang.equalsIgnoreCase("sw"))
		{
			((Drawer)getActivity()).setActionBarTitle("Senaste meddelanden");
		}else {
			((Drawer)getActivity()).setActionBarTitle("Note History");
		}
		/////////////

		txtForUserName = (TextView) v.findViewById(R.id.txtForUserName);
		
		if(lang.equalsIgnoreCase("sw"))
		{
			txtForGuardian.setText("Vårdnadshavare");
			btnForEditProfile.setText("Editar");
		}
		else {
			
		}
		
		Bundle bundle = this.getArguments();
		studentId = bundle.getString("keyForStudentId");
		String name = bundle.getString("keyForStudentName");
		txtForUserName.setText("" + name);

		listForNoteHistory = (ListView) v.findViewById(R.id.listForNoteHistory);
		new GetData().execute();

		return v;
	}

	class GetData extends AsyncTask<String, String, String> {

		private String url = Base_url + "lms_api/retrivals/note_history",
				Status="", image;

		private MyCustomProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new MyCustomProgressDialog(getActivity());
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			dialog.show();

		}

		protected String doInBackground(String... args) {


			String noteHistoryResponse = "";
			try {
				String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
						"&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_key, "UTF-8") +
						"&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
						"&" + Const.Params.UserTypeApp + "=" + URLEncoder.encode("android", "UTF-8")+

						"&" + Const.Params.StudentId + "=" + URLEncoder.encode(studentId, "UTF-8");


				noteHistoryResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*JSONParser jsonParser = new JSONParser();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("securityKey", Security));
			params.add(new BasicNameValuePair("authentication_token", auth_key));
			params.add(new BasicNameValuePair("user_type_app", "android"));
			params.add(new BasicNameValuePair("language", lang));
			params.add(new BasicNameValuePair("student_id", studentId));

			Log.e("Create Response -=-=-=-=-=- ", params.toString());

			JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

			try {
				listForDate.clear();
				listForDesc.clear();
				listForImage.clear();
				listForLeaveType.clear();
				listForContact.clear();
				listForRetriever.clear();
				listForType.clear();
				listForDesWritenBy.clear();
				listForGruardian.clear();


				JSONObject jsonObj = new JSONObject(json.toString());
				Status = jsonObj.getString("status").toString();

				if (Status.equalsIgnoreCase("true")) {
					JSONArray retriever_note = jsonObj
							.optJSONArray("retriever_note");
					Log.e("retriever_note", "" + retriever_note);
					for (int i = 0; i < retriever_note.length(); i++) {
						JSONObject data = retriever_note.getJSONObject(i);
						String date1 = data.optString("data");
						String description = data.optString("description");
						String image = data.optString("image");
						String leave_type = data.optString("leave_type");
						String phone = data.optString("phone");
						String retriever_name = data
								.optString("retriever_name");
						String type = data.optString("type");
						String written_by = data.optString("written_by");

						listForDate.add(date1);
						listForDesc.add(description);
						listForImage.add(image);
						listForLeaveType.add(leave_type);
						listForContact.add(phone);
						listForRetriever.add(retriever_name);
						listForType.add(type);
						listForDesWritenBy.add(written_by);
					}

					JSONArray parent = jsonObj.optJSONArray("parent");
					Log.i("parent", "" + parent);
					for (int i = 0; i < parent.length(); i++) {
						JSONObject data = parent.optJSONObject(i);
						JSONObject user = data.getJSONObject("User");

						String USR_FirstName = user.optString("USR_FirstName");
						String USR_LastName = user.optString("USR_LastName");

						String name = filterStringToNormal(USR_FirstName + " "
								+ USR_LastName);

						listForGruardian.add(name);

						Log.e("listForGruardian", "" + listForGruardian);
					}

					JSONObject student = jsonObj.getJSONObject("student");
					image = student.getString("USR_image");

				} else {
					Log.e("", "No data");
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
*/
			return noteHistoryResponse;
		}

		protected void onPostExecute(String results) {
			dialog.dismiss();
			JSONObject jsonObj = new JSONObject();

			try {
				listForDate.clear();
				listForDesc.clear();
				listForImage.clear();
				listForLeaveType.clear();
				listForContact.clear();
				listForRetriever.clear();
				listForType.clear();
				listForDesWritenBy.clear();
				listForGruardian.clear();
				if(!results.isEmpty()&&results!=null)
				{


					jsonObj = new JSONObject(results);
					if(jsonObj.has("status"))
					{

						Status = jsonObj.getString("status").toString();

					}




				}








				if (Status.equalsIgnoreCase("true")) {
					JSONArray retriever_note = jsonObj
							.optJSONArray("retriever_note");
					Log.e("retriever_note", "" + retriever_note);
					for (int i = 0; i < retriever_note.length(); i++) {
						JSONObject data = retriever_note.getJSONObject(i);
						String date1 = data.optString("data");
						String description = data.optString("description");
						String image = data.optString("image");
						String leave_type = data.optString("leave_type");
						String phone = data.optString("phone");
						String retriever_name = data
								.optString("retriever_name");
						String type = data.optString("type");
						String written_by = data.optString("written_by");

						listForDate.add(date1);
						listForDesc.add(description);
						listForImage.add(image);
						listForLeaveType.add(leave_type);
						listForContact.add(phone);
						listForRetriever.add(retriever_name);
						listForType.add(type);
						listForDesWritenBy.add(written_by);
					}

					JSONArray parent = jsonObj.optJSONArray("parent");
					Log.i("parent", "" + parent);
					for (int i = 0; i < parent.length(); i++) {
						JSONObject data = parent.optJSONObject(i);
						JSONObject user = data.getJSONObject("User");

						String USR_FirstName = user.optString("USR_FirstName");
						String USR_LastName = user.optString("USR_LastName");

						String name = filterStringToNormal(USR_FirstName + " "
								+ USR_LastName);

						listForGruardian.add(name);

						Log.e("listForGruardian", "" + listForGruardian);
					}

					JSONObject student = jsonObj.getJSONObject("student");
					image = student.getString("USR_image");

				} else {
					Log.e("", "No data");

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

				if (Status.equalsIgnoreCase("true")) {

					AtandanceListAdapter adp = new AtandanceListAdapter(
							getActivity(), listForRetriever, listForContact,
							listForDesWritenBy, listForDesc, listForImage,
							listForDate, listForLeaveType, listForType);
					listForNoteHistory.setAdapter(adp);

					if (listForGruardian.size() == 0) {
						txtForGuardian1.setVisibility(View.GONE);
						txtForGuardian2.setVisibility(View.GONE);
						txtForGuardian.setVisibility(View.GONE);
					}

					if (listForGruardian.size() == 1) {
						txtForGuardian1.setText(listForGruardian.get(0));
						txtForGuardian2.setVisibility(View.GONE);
					}

					if (listForGruardian.size() > 2) {
						txtForGuardian1.setText(listForGruardian.get(0));
						txtForGuardian2.setText(listForGruardian.get(1));
					}

					imgLoader.DisplayImage(Base_url + image, imgForUserImage);

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
					}catch (NullPointerException e)
					{
						e.printStackTrace();
					}

					Toast.makeText(getActivity(), " Invalid ",
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
			}
		}

	}

	public class AtandanceListAdapter extends BaseAdapter {

		private Activity activity;
		private LayoutInflater inflater = null;

		// private ArrayList<String> listForId = new ArrayList<String>();
		private ArrayList<String> listForRetriever = new ArrayList<String>();
		private ArrayList<String> listForContact = new ArrayList<String>();
		private ArrayList<String> listForDesWritenBy = new ArrayList<String>();
		private ArrayList<String> listForDesc = new ArrayList<String>();
		private ArrayList<String> listForImage = new ArrayList<String>();
		private ArrayList<String> listForDate = new ArrayList<String>();
		private ArrayList<String> listForLeaveType = new ArrayList<String>();
		private ArrayList<String> listForType = new ArrayList<String>();

		public AtandanceListAdapter(Activity a,
				ArrayList<String> listForRetriever,
				ArrayList<String> listForContact,
				ArrayList<String> listForDesWritenBy,
				ArrayList<String> listForDesc, ArrayList<String> listForImage,
				ArrayList<String> listForDate,
				ArrayList<String> listForLeaveType,
				ArrayList<String> listForType) {
			activity = a;
			// this.listForId = listForId;
			this.listForRetriever = listForRetriever;
			this.listForContact = listForContact;
			this.listForImage = listForImage;
			this.listForDesWritenBy = listForDesWritenBy;
			this.listForDesc = listForDesc;
			this.listForDate = listForDate;
			this.listForLeaveType = listForLeaveType;
			this.listForType = listForType;

			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return listForRetriever.size();
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
				vi = inflater.inflate(R.layout.layout_for_note_history_list,
						null);
			TextView txtForRetrievrName = (TextView) vi
					.findViewById(R.id.txtForRetrievrName);
			TextView txtForContactDetail = (TextView) vi
					.findViewById(R.id.txtForContactDetail);
			TextView txtForDescrption = (TextView) vi
					.findViewById(R.id.txtForDescrption); // / written By
			TextView txtForDescrption2 = (TextView) vi
					.findViewById(R.id.txtForDescrption2);
			TextView txtForDateF = (TextView) vi.findViewById(R.id.txtForDateF);

			ImageView imageForRetriver = (ImageView) vi
					.findViewById(R.id.imageForRetriver);

			// heading text view
			TextView txtForHeadingMain = (TextView) vi
					.findViewById(R.id.txtForHeadingMain);

			TextView txtForHeading1 = (TextView) vi
					.findViewById(R.id.txtForHeading1);
			TextView txtForHeading3 = (TextView) vi
					.findViewById(R.id.txtForHeading3);
			TextView txtForHeading4=(TextView)vi.findViewById(R.id.txtForHeading4);
			RelativeLayout layoutForRetriver = (RelativeLayout) vi
					.findViewById(R.id.layoutForRetriver);

			txtForHeading3.setVisibility(View.GONE);
			layoutForRetriver.setVisibility(View.GONE);
			txtForRetrievrName.setVisibility(View.GONE);
			txtForContactDetail.setVisibility(View.GONE);

			imageForRetriver.setVisibility(View.GONE);

			if(lang.equalsIgnoreCase("en"))
			{

				txtForHeading4.setText("Descrption");
			}
			else
			{
				txtForHeading4.setText("Beskrivning");
			}
			if (listForType.get(position).equals("absence_note")) {

				if(lang.equalsIgnoreCase("en")) {

					txtForHeadingMain.setText("Absent Note");
				}
				else
				{
					txtForHeadingMain.setText("Frånvaroanmälan");
				}

				imageForRetriver.setVisibility(View.GONE);
				txtForHeading1.setVisibility(View.GONE);
				txtForHeading3.setVisibility(View.GONE);
				layoutForRetriver.setVisibility(View.GONE);
				txtForRetrievrName.setVisibility(View.GONE);
				txtForContactDetail.setVisibility(View.GONE);
				txtForDescrption.setText("Written By: "
						+ listForDesWritenBy.get(position));
				txtForDescrption2.setText("" + listForDesc.get(position));
				txtForDateF.setText("" + listForDate.get(position));
			} else {
				if(lang.equalsIgnoreCase("en")) {


					txtForHeadingMain.setText("Retriever Note");
					txtForHeading1.setText("Retriever name");
					txtForHeading3.setText("Contact details");
				}
				else
				{
					txtForHeadingMain.setText("Annan hämtare");
					txtForHeading1.setText("Namn");
					txtForHeading3.setText("Kontaktnummer");

				}
				txtForHeading1.setVisibility(View.VISIBLE);
				imageForRetriver.setVisibility(View.VISIBLE);
				txtForHeading3.setVisibility(View.VISIBLE);
				layoutForRetriver.setVisibility(View.VISIBLE);
				txtForRetrievrName.setVisibility(View.VISIBLE);
				txtForContactDetail.setVisibility(View.VISIBLE);
				txtForRetrievrName.setText("" + listForRetriever.get(position));
				txtForContactDetail.setText("" + listForContact.get(position));
				txtForDescrption.setText("Written By: "
						+ listForDesWritenBy.get(position));
				txtForDescrption2.setText("" + listForDesc.get(position));
				txtForDateF.setText("" + listForDate.get(position));
			}

			return vi;
		}
	}

	public String filterStringToNormal(String data) {
		// String input = "Carmen López-Delina Santos";
		// input = data;
		// String text =
		// "This - word ! has \\ /allot # of % special % characters";
		data = data.replaceAll("[^a-zA-Z0-9 -]", "");
		System.out.println(data);
		return data;
	}
	//////////
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
			if (requestCode == LOAD_IMAGE_RESULTS
					&& resultCode == Activity.RESULT_OK && data != null) {
				// Let's read picked image data - its URI
				Uri pickedImage = data.getData();
				// Let's read picked image path using content resolver
				String[] filePath = { MediaStore.Images.Media.DATA };
				Cursor cursor = getActivity().getContentResolver().query(
						pickedImage, filePath, null, null, null);

				cursor.moveToFirst();
				imagePath = cursor
						.getString(cursor.getColumnIndex(filePath[0]));
				i_path = imagePath;

//				Log.i("image_working", "yes..");
//
//				Log.i("imagePathimagePath", imagePath);

				cursor.close();    

				// //////////////////////////////////////////

				InputStream inputStream = null;
				try {
					inputStream = new FileInputStream(imagePath);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}// You can get an inputStream using any IO API
			//	Bitmap tt = BitmapFactory.decodeFile(imagePath);   ////// new 
				Bitmap tt = BitmapHelper.decodeFile(imagePath, 50, 50, true);
				byte[] bytes;
				byte[] buffer = new byte[8192];
				int bytesRead;
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				try {
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						output.write(buffer, 0, bytesRead);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
//				bytes = output.toByteArray();
				tt.compress(CompressFormat.JPEG, 50,output);    ///// new
				bytes = output.toByteArray();
				
				
				try {
				String encodedString = Base64.encodeToString(bytes,
							Base64.DEFAULT);
				} catch (Exception e) {
					// TODO: handle exception
				}
				imgForUserImage.setImageBitmap(tt);
			}
		}
	/////////

}
