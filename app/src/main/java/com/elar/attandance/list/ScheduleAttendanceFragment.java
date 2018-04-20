package com.elar.attandance.list;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/*import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pnf.elar.app.R;
import com.pnf.elar.app.Drawer;

import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.AppLog;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ScheduleAttendanceFragment extends Fragment implements
		OnClickListener {

	private ArrayList<String> listForMonDR = new ArrayList<String>();
	private ArrayList<String> listForTuesDR = new ArrayList<String>();
	private ArrayList<String> listForWedDR = new ArrayList<String>();
	private ArrayList<String> listForThusDR = new ArrayList<String>();
	private ArrayList<String> listForFriDR = new ArrayList<String>();
	private ArrayList<String> listForSatDR = new ArrayList<String>();
	private ArrayList<String> listForSunDR = new ArrayList<String>();

	private ArrayList<String> listForMonRT = new ArrayList<String>();
	private ArrayList<String> listForTuesRT = new ArrayList<String>();
	private ArrayList<String> listForWedRT = new ArrayList<String>();
	private ArrayList<String> listForThusRT = new ArrayList<String>();
	private ArrayList<String> listForFriRT = new ArrayList<String>();
	private ArrayList<String> listForSatRT = new ArrayList<String>();
	private ArrayList<String> listForSunRT = new ArrayList<String>();

	private ArrayList<String> listForGroupIdToPass = new ArrayList<String>();
	private ArrayList<String> listForGroupId = new ArrayList<String>();
	private ArrayList<String> listForGroupName = new ArrayList<String>();
	private ArrayList<Boolean> listForCheckStatus = new ArrayList<Boolean>();

	private ArrayList<String> listForStudentToPass = new ArrayList<String>();
	private ArrayList<String> listForStudentId = new ArrayList<String>();
	private ArrayList<String> listForStudentName = new ArrayList<String>();
	private ArrayList<Boolean> listForStuddentCheckStatus = new ArrayList<Boolean>();

	UserSessionManager session;
	String auth_key, securityKey, lang, Base_url, Security = "H67jdS7wwfh",
			note;

	// Response
	String responseServer;

	AlertDialog.Builder ad;

	View v;
	int numberOfWeeks = 1;

	private TextView txtForSelectGroup;
	private TextView txtForSelectChildern;
	private TextView txtForPickFromDate;
	private TextView txtForPickToDate;
	private Spinner txtForWeekInterval;
	private ListView listForSchedule;
	private RelativeLayout layoutForBottomButtons;
	private Button btnForBack, btnForSave;

	private ArrayList<String> listForIntervalCount = new ArrayList<String>();

	Calendar myCalendar;
	DatePickerDialog.OnDateSetListener date;
	Calendar myCalendar2;
	DatePickerDialog.OnDateSetListener date2;
    TextView Target , Groups , Childerns , Period , From , To , Intervall , Weeks  ;
	boolean selectItem = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.schedule_attandance_fragment, container,
				false);
		// TODO Auto-generated method stub

		session = new UserSessionManager(getActivity());
		HashMap<String, String> user = session.getUserDetails();
		lang = user.get(UserSessionManager.TAG_language);
		auth_key = user.get(UserSessionManager.TAG_Authntication_token);
		Base_url = user.get(UserSessionManager.TAG_Base_url);

		txtForSelectGroup = (TextView) v.findViewById(R.id.txtForSelectGroup);
		txtForSelectChildern = (TextView) v
				.findViewById(R.id.txtForSelectChildern);
		txtForPickFromDate = (TextView) v.findViewById(R.id.txtForPickFromDate);
		txtForPickToDate = (TextView) v.findViewById(R.id.txtForPickToDate);
		txtForWeekInterval = (Spinner) v.findViewById(R.id.txtForWeekInterval);
		listForSchedule = (ListView) v.findViewById(R.id.listForSchedule);
		layoutForBottomButtons = (RelativeLayout) v
				.findViewById(R.id.layoutForBottomButtons);
		btnForBack = (Button) v.findViewById(R.id.btnForBack);
		btnForSave = (Button) v.findViewById(R.id.btnForSave);
		Target=(TextView) v.findViewById(R.id.Target);
		Groups=(TextView) v.findViewById(R.id.Groups);
		Childerns=(TextView) v.findViewById(R.id.Childerns);
		Period=(TextView) v.findViewById(R.id.Period);
		From=(TextView) v.findViewById(R.id.From);
		To=(TextView) v.findViewById(R.id.To);
		Intervall=(TextView) v.findViewById(R.id.Intervall);
		Weeks=(TextView) v.findViewById(R.id.Weeks);
		
		if(lang.equalsIgnoreCase("sw"))
		{
			Target.setText("Mål");
			Groups.setText("grupper");
			txtForSelectGroup.setText("Välj grupper");
			Childerns.setText("childerns");
			txtForSelectChildern.setText("utvalda individer");
			Period.setText("Period");
			From.setText("Från");
			To.setText("Till");
			Intervall.setText("intervall");
			Weeks.setText("veckor");
			btnForBack.setText("Tillbaka");
			btnForSave.setText("Spara");
		}
		else {
			
		}
		
		
		
		
		txtForSelectGroup.setOnClickListener(this);
		txtForSelectChildern.setOnClickListener(this);
		txtForPickFromDate.setOnClickListener(this);
		txtForPickToDate.setOnClickListener(this);

		btnForBack.setOnClickListener(this);
		btnForSave.setOnClickListener(this);
		
		
//////////back to Main Activity ////Set title/////
		((Drawer)getActivity()).setBackFrompublishtomainAttendance();
		if(lang.equalsIgnoreCase("sw"))
		{
			((Drawer)getActivity()).setActionBarTitle("schema Närvaro");
		}else {
			((Drawer)getActivity()).setActionBarTitle("Schedule Attendance");
		}
		/////////////

		txtForPickFromDate.setText("" + getCurrentDate());

		myCalendar = Calendar.getInstance();
		date = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, monthOfYear);
				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				updateEdt();
			}

		};

		myCalendar2 = Calendar.getInstance();
		date2 = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				myCalendar2.set(Calendar.YEAR, year);
				myCalendar2.set(Calendar.MONTH, monthOfYear);
				myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				updateEdt2();
			}

		};

		new GetAllGroup().execute();
		listForIntervalCount.add("1");

		setAdapterToSpinner(listForIntervalCount);

		AdapterForScheduleList adp = new AdapterForScheduleList(getActivity(),
				listForIntervalCount, 1);
		listForSchedule.setAdapter(adp);
		setListViewHeightBasedOnChildrener(listForSchedule);

		txtForWeekInterval
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub

						listForMonDR.clear();
						listForTuesDR.clear();
						listForWedDR.clear();
						listForThusDR.clear();
						listForFriDR.clear();
						listForSatDR.clear();
						listForSunDR.clear();

						listForMonRT.clear();
						listForTuesRT.clear();
						listForWedRT.clear();
						listForThusRT.clear();
						listForFriRT.clear();
						listForSatRT.clear();
						listForSunRT.clear();

						if (selectItem == false) {
							numberOfWeeks = 1;
							AdapterForScheduleList adp = new AdapterForScheduleList(
									getActivity(), listForIntervalCount,
									numberOfWeeks);
							listForSchedule.setAdapter(adp);
							setListViewHeightBasedOnChildrener(listForSchedule);
							listForMonDR.add("");
							listForTuesDR.add("");
							listForWedDR.add("");
							listForThusDR.add("");
							listForFriDR.add("");
							listForSatDR.add("");
							listForSunDR.add("");

							listForMonRT.add("");
							listForTuesRT.add("");
							listForWedRT.add("");
							listForThusRT.add("");
							listForFriRT.add("");
							listForSatRT.add("");
							listForSunRT.add("");
						} else {
							numberOfWeeks = position + 1;
							AdapterForScheduleList adp = new AdapterForScheduleList(
									getActivity(), listForIntervalCount,
									numberOfWeeks);
							listForSchedule.setAdapter(adp);
							setListViewHeightBasedOnChildrener(listForSchedule);

							for (int i = 0; i < numberOfWeeks; i++) {
								listForMonDR.add("");
								listForTuesDR.add("");
								listForWedDR.add("");
								listForThusDR.add("");
								listForFriDR.add("");
								listForSatDR.add("");
								listForSunDR.add("");

								listForMonRT.add("");
								listForTuesRT.add("");
								listForWedRT.add("");
								listForThusRT.add("");
								listForFriRT.add("");
								listForSatRT.add("");
								listForSunRT.add("");
							}
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});

		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.txtForSelectGroup:
			selectGroupMethod();
			break;

		case R.id.txtForSelectChildern:
			selectIndividual();
			break;

		case R.id.txtForPickFromDate:
			new DatePickerDialog(getActivity(), date,
					myCalendar.get(Calendar.YEAR),
					myCalendar.get(Calendar.MONTH),
					myCalendar.get(Calendar.DAY_OF_MONTH)).show();

			break;

		case R.id.txtForPickToDate:
			new DatePickerDialog(getActivity(), date2,
					myCalendar2.get(Calendar.YEAR),
					myCalendar2.get(Calendar.MONTH),
					myCalendar2.get(Calendar.DAY_OF_MONTH)).show();

			break;

		case R.id.btnForBack:
			FragmentManager fragmentManager = getFragmentManager();
			AttandanceMainScreen rFragment = new AttandanceMainScreen();
			FragmentTransaction ft = fragmentManager.beginTransaction();
			ft.replace(R.id.content_frame, rFragment);
			ft.commit();
			break;

		case R.id.btnForSave:

			if (listForGroupIdToPass.size() > 0) {
				new SaveScheduleData().execute();
			} else {
				txtForSelectGroup.setError("Required Field");
				Toast.makeText(getActivity(),
						"Please Select Atleast one group", Toast.LENGTH_LONG)
						.show();
			}

			// test();

			Log.e("data", " drop: " + listForMonDR + " - ret: " + listForMonRT);
			Log.e("data", " drop: " + listForTuesDR + " - ret: "
					+ listForTuesRT);
			Log.e("data", " drop: " + listForWedDR + " - ret: " + listForWedRT);
			Log.e("data", " drop: " + listForThusDR + " - ret: "
					+ listForThusRT);
			Log.e("data", " drop: " + listForFriDR + " - ret: " + listForFriRT);
			Log.e("data", " drop: " + listForSatDR + " - ret: " + listForSatRT);
			Log.e("data", " drop: " + listForSunDR + " - ret: " + listForSunRT);
			Log.e("student id", "" + listForStudentToPass);
			Log.e("group id", "" + listForGroupIdToPass);
			Log.e("to date", "" + txtForPickToDate.getText().toString());
			Log.e("from date", "" + txtForPickFromDate.getText().toString());
			Log.e("interval", "" + numberOfWeeks);

			break;

		default:
			break;
		}

	}

	class AdapterForScheduleList extends BaseAdapter {

		private Activity activity;
		private LayoutInflater inflater = null;
		private int count;

		ArrayList<String> listForIntervalCount = new ArrayList<String>();

		public AdapterForScheduleList(Activity a,
				ArrayList<String> listForIntervalCount, int count) {
			activity = a;
			this.listForIntervalCount = listForIntervalCount;

			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.count = count;
		}

		public int getCount() {
			return count;
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
				vi = inflater.inflate(R.layout.layout_for_schulde_list, null);

			final TextView txtForDropOff1 = (TextView) vi
					.findViewById(R.id.txtForDropOff1);
			final TextView txtForRetrieval1 = (TextView) vi
					.findViewById(R.id.txtForRetrieval1);
			final TextView txtForDropOff2 = (TextView) vi
					.findViewById(R.id.txtForDropOff2);
			final TextView txtForRetrieval2 = (TextView) vi
					.findViewById(R.id.txtForRetrieval2);
			final TextView txtForDropOff3 = (TextView) vi
					.findViewById(R.id.txtForDropOff3);
			final TextView txtForRetrieval3 = (TextView) vi
					.findViewById(R.id.txtForRetrieval3);
			final TextView txtForDropOff4 = (TextView) vi
					.findViewById(R.id.txtForDropOff4);
			final TextView txtForRetrieval4 = (TextView) vi
					.findViewById(R.id.txtForRetrieval4);
			final TextView txtForDropOff5 = (TextView) vi
					.findViewById(R.id.txtForDropOff5);
			final TextView txtForRetrieval5 = (TextView) vi
					.findViewById(R.id.txtForRetrieval5);
			final TextView txtForDropOff6 = (TextView) vi
					.findViewById(R.id.txtForDropOff6);
			final TextView txtForRetrieval6 = (TextView) vi
					.findViewById(R.id.txtForRetrieval6);

			final TextView txtForDropOff7 = (TextView) vi
					.findViewById(R.id.txtForDropOff7);
			final TextView txtForRetrieval7 = (TextView) vi
					.findViewById(R.id.txtForRetrieval7);

			txtForDropOff1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					showTimePicker(txtForDropOff1, listForMonDR, position);

				}
			});

			txtForDropOff2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showTimePicker(txtForDropOff2, listForTuesDR, position);
				}
			});

			txtForDropOff3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showTimePicker(txtForDropOff3, listForWedDR, position);

				}
			});

			txtForDropOff4.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showTimePicker(txtForDropOff4, listForThusDR, position);

				}
			});

			txtForDropOff5.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showTimePicker(txtForDropOff5, listForFriDR, position);
				}
			});

			txtForDropOff6.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showTimePicker(txtForDropOff6, listForSatDR, position);
				}
			});

			txtForDropOff7.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showTimePicker(txtForDropOff7, listForSunDR, position);
				}
			});

			txtForRetrieval1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showTimePicker(txtForRetrieval1, listForMonRT, position);

				}
			});

			txtForRetrieval2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showTimePicker(txtForRetrieval2, listForTuesRT, position);
				}
			});

			txtForRetrieval3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showTimePicker(txtForRetrieval3, listForWedRT, position);
				}
			});

			txtForRetrieval4.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showTimePicker(txtForRetrieval4, listForThusRT, position);
				}
			});

			txtForRetrieval5.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showTimePicker(txtForRetrieval5, listForFriRT, position);
				}
			});

			txtForRetrieval6.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showTimePicker(txtForRetrieval6, listForSatRT, position);
				}
			});

			txtForRetrieval7.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showTimePicker(txtForRetrieval7, listForSunRT, position);
				}
			});

			return vi;
		}

		public void showTimePicker(final TextView txt,
				final ArrayList<String> aL, final int index) {
			Calendar mcurrentTime = Calendar.getInstance();
			int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
			int minute = mcurrentTime.get(Calendar.MINUTE);
			TimePickerDialog mTimePicker;
			mTimePicker = new TimePickerDialog(getActivity(),
					new TimePickerDialog.OnTimeSetListener() {

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {
							// TODO Auto-generated method stub
							txt.setText(hourOfDay + ":" + minute);
							aL.set(index, hourOfDay + ":" + minute);
						}

					}, hour, minute, true);// Yes 24 hour time
			mTimePicker.setTitle("Select Time");
			mTimePicker.show();
		}

	}

	protected void setListViewHeightBasedOnChildrener(ListView im_vdo2) {
		// TODO Auto-generated method stub

		ListAdapter listAdapter = im_vdo2.getAdapter();
		if (listAdapter == null)
			return;

		int desiredWidth = MeasureSpec.makeMeasureSpec(im_vdo2.getWidth(),
				MeasureSpec.UNSPECIFIED);
		int totalHeight = 0;
		View view = null;

		for (int i = 0; i < listAdapter.getCount(); i++) {
			view = listAdapter.getView(i, view, im_vdo2);

			if (i == 0)
				view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
						LayoutParams.MATCH_PARENT));

			view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
			totalHeight += view.getMeasuredHeight();

		}

		ViewGroup.LayoutParams params = im_vdo2.getLayoutParams();
		params.height = totalHeight
				+ ((im_vdo2.getDividerHeight()) * (listAdapter.getCount()));

		im_vdo2.setLayoutParams(params);
		im_vdo2.requestLayout();

	}

	private int mLastFirstVisibleItem;
	private OnScrollListener lead_scrolllist = new OnScrollListener() {
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (view.getId() == listForSchedule.getId()) {
				final int currentFirstVisibleItem = listForSchedule
						.getFirstVisiblePosition();

				if (currentFirstVisibleItem > mLastFirstVisibleItem) {
					layoutForBottomButtons.setVisibility(View.GONE);
				} else if (currentFirstVisibleItem < mLastFirstVisibleItem) {

					layoutForBottomButtons.setVisibility(View.VISIBLE);
				}

				mLastFirstVisibleItem = currentFirstVisibleItem;
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {

		}
	};

	public void updateEdt() {

		String myFormat = "yyyy-MM-dd"; // In which you need put here
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

		txtForPickFromDate.setText(sdf.format(myCalendar.getTime()));
	}

	public void updateEdt2() {

		// try {
		// DateFormat format = new SimpleDateFormat("yyyy-MM-DD", Locale.US);
		//
		// String date1 = txtForPickFromDate.getText().toString();
		//
		// Date date = format.parse(date1);
		// DateFormat format2 = new SimpleDateFormat("yyyy-MM-DD", Locale.US);
		// String _date2 = format2.format(myCalendar2.getTime());
		//
		// Date date2 = format2.parse(_date2);
		//
		// txtForPickToDate.setText("" + date2);
		// txtForWeekInterval.setText("" + getDateDiffString(date, date2));
		//
		// Log.e("weeks getting: ----", "" + getDateDiffString(date, date2));
		// } catch (Exception e) {
		// // TODO: handle exception
		// e.printStackTrace();
		// Log.e("date error", "" + e);
		// }
		//

		try {
			String myFormat = "yyyy-MM-dd"; // In which you need put here
			SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
			txtForPickToDate.setText("" + sdf.format(myCalendar2.getTime()));

			String date1 = txtForPickFromDate.getText().toString();
			String date2 = sdf.format(myCalendar2.getTime()).toString();

			Date d1 = sdf.parse(date1);
			Date d2 = sdf.parse(date2);

			String dateDifference = getDateDiffString(d1, d2);
			// txtForWeekInterval.setText("" + dateDifference);

			numberOfWeeks = Integer.parseInt(dateDifference);

			ArrayList<String> aL = new ArrayList<String>();

			for (int i = 1; i <= numberOfWeeks; i++) {
				aL.add("" + i);
			}
			setAdapterToSpinner(aL);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public String getDateDiffString(Date dateOne, Date dateTwo) {
		Log.e("=--=-", "   date1:-  " + dateOne
				+ "  date2:- " + dateTwo);
		long timeOne = dateOne.getTime();
		long timeTwo = dateTwo.getTime();
		long oneDay = 1000 * 60 * 60 * 24;
		long delta = (timeTwo - timeOne) / oneDay;
		long weeks = (delta + 1) / 7;

		if (weeks > 0) {
			weeks = weeks;
		}

		if (weeks == 0) {
			weeks = 1;
		}

		if (weeks < 0) {
			weeks = -1;
		}

		return "" + weeks;
	}

	public void selectGroupMethod() {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		final boolean[] checked = new boolean[listForGroupName.size()];

		String[] groupName = new String[listForGroupName.size()];

		for (int i = 0; i < listForGroupName.size(); i++) {
			groupName[i] = listForGroupName.get(i);
			checked[i] = listForCheckStatus.get(i);
		}

		// String array for alert dialog multi choice items
		// String[] colors = new String[] { "Red", "Green", "Blue", "Purple",
		// "Olive" };

		final List<String> colorsList = Arrays.asList(groupName);
		builder.setMultiChoiceItems(groupName, checked,
				new DialogInterface.OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {

						// Update the current focused item's checked status
						checked[which] = isChecked;

						// Get the current focused item
						String currentItem = colorsList.get(which);

					}
				});

		// Specify the dialog is not cancelable
		builder.setCancelable(false);

		// Set a title for alert dialog
		builder.setTitle("Select Group");

		// Set the positive/yes button click listener
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Do something when click positive button
				// tv.setText("Your preferred colors..... \n");
				listForCheckStatus.clear();
				int count = 0;
				for (int i = 0; i < checked.length; i++) {
					boolean checkedNew = checked[i];
					if (checkedNew) {
						listForCheckStatus.add(true);
						count = count + 1;
						txtForSelectGroup.setText("" + count + " Groups");
						listForGroupIdToPass.add(listForGroupId.get(i)
								.toString());
						new GetUserAccToGroup().execute();
						// tv.setText(tv.getText() + colorsList.get(i) + "\n");
					} else {
						listForCheckStatus.add(false);
					}
					Log.e("", "" + listForCheckStatus.get(i));
				}
			}
		});

		// Set the negative/no button click listener
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Do something when click the negative button
			}
		});

		// Set the neutral/cancel button click listener
		builder.setNeutralButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Do something when click the neutral button
					}
				});

		AlertDialog dialog = builder.create();
		// Display the alert dialog on interface
		dialog.show();
	}

	class GetAllGroup extends AsyncTask<String, String, String> {

		private String url = Base_url + "lms_api/retrivals/all_groups";

		private static final String TAG_STATUS = "status";
		private static final String TAG_TOKEN = "authentication_token";
		private static final String TAG_Component = "data";
		private static final String TAG_User = "User";
		private static final String TAG_other_accounts = "other_accounts";
		private static final String TAG_name = "name";
		private static final String TAG_img_path = "img_path";
		private static final String TAG_id = "id";
		private static final String TAG_USR_Firstname = "USR_Firstname";

		private ProgressDialog pDialog;
		String[] listForGroupId_array, listForGroupId_id;

		String Login_Email, Login_Password, Security = "H67jdS7wwfh";
		String Status="", token, first_name, user_type, Remember_me;
		String[] component, image, other_accounts, name, image_id, grp_id;
		// CustomAdapter_drawer adapter2;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// pDialog = new ProgressDialog(getActivity());
			// pDialog.setMessage("loading...");
			// pDialog.setIndeterminate(false);
			// pDialog.setCancelable(false);
			// pDialog.show();

		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {

			String allGroupsResponse = "";
			try {
				String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
						"&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_key, "UTF-8") +
						"&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
						"&" + Const.Params.UserTypeApp + "=" + URLEncoder.encode("android", "UTF-8");


				allGroupsResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
			} catch (Exception e) {
				e.printStackTrace();
			}

/*
			JSONParser jsonParser = new JSONParser();
*/

		/*	List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("securityKey", Security));
			params.add(new BasicNameValuePair("authentication_token", auth_key));
			params.add(new BasicNameValuePair("user_type_app", "android"));
			params.add(new BasicNameValuePair("language", lang));

			JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

			Log.e("Create Response.....", json.toString());

			try {

				if (Status.equalsIgnoreCase("true")) {

				} else {

				}

			} catch (Exception e) {
				e.printStackTrace();
			}*/

			return allGroupsResponse;
		}

		protected void onPostExecute(String results) {
			// dismiss the dialog once done
			// pDialog.dismiss();
			try {

				JSONObject jsonObj = new JSONObject();

				listForGroupId.clear();
				listForGroupName.clear();

				if(results!=null&&!results.isEmpty())
				{

					jsonObj = new JSONObject(results);
					if(jsonObj.has("status"))
					{
						Status = jsonObj.getString(TAG_STATUS).toString();


					}



				}





				if (Status.equalsIgnoreCase("true")) {
					JSONArray comp = jsonObj.optJSONArray(TAG_Component);
					listForGroupId_id = new String[comp.length()];
					listForGroupId_array = new String[comp.length()];
					// grp_id = new String[comp.length()];
					for (int j = 0; j < comp.length(); j++) {
						JSONObject c = comp.getJSONObject(j);
						listForGroupId_array[j] = c.optString(TAG_name);
						listForGroupId_id[j] = c.optString(TAG_id);
						listForGroupId.add(c.optString(TAG_id));
						listForGroupName.add(c.optString(TAG_name));
						listForCheckStatus.add(false);

						// component[j] = c.optString(TAG_name);
						// grp_id[j] = c.optString(TAG_id);

					}

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
					// failed to create product
					Toast.makeText(getActivity(), " Invalid ",
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
			}
		}
	}

	class GetUserAccToGroup extends AsyncTask<Void, Void, Void> {

		String status="", message;
		private MyCustomProgressDialog dialog;
		String groupStudResponse = "";


		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			dialog = new MyCustomProgressDialog(getActivity());
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... voids) {

			String url=Base_url
					+ "lms_api/picture_diary/get_group_students";
			try {

				JSONObject jsonobj = new JSONObject();
				JSONArray jsonArr = new JSONArray();

				for (int i = 0; i < listForGroupIdToPass.size(); i++) {
					jsonArr.put(i, listForGroupIdToPass.get(i).toString());
				}

				jsonobj.put("authentication_token", auth_key);
				jsonobj.put("group_ids", jsonArr);
				jsonobj.put("language", lang);
				jsonobj.put("securityKey", Security);

				AppLog.Log("stu ",jsonobj.toString());

				String urlParams = "&" + Const.Params.JsonData + "=" + URLEncoder.encode(jsonobj.toString(), "UTF-8");


				groupStudResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
			} catch (Exception e) {
				e.printStackTrace();
			}



/*
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Base_url
					+ "lms_api/picture_diary/get_group_students");
			try {

				JSONObject jsonobj = new JSONObject();
				JSONArray jsonArr = new JSONArray();

				for (int i = 0; i < listForGroupIdToPass.size(); i++) {
					jsonArr.put(i, listForGroupIdToPass.get(i).toString());
				}

				jsonobj.put("authentication_token", auth_key);
				jsonobj.put("group_ids", jsonArr);
				jsonobj.put("language", lang);
				jsonobj.put("securityKey", Security);

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("jsonData", jsonobj
						.toString()));

				Log.e("mainToPost", "mainToPost" + nameValuePairs.toString());

				// Use UrlEncodedFormEntity to send in proper format which we
				// need
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httppost);

				Log.e("-=-=-=-=-=-=-=  ", "" + response);

				InputStream inputStream = response.getEntity().getContent();
				InputStreamToStringExample str = new InputStreamToStringExample();
				responseServer = str.getStringFromInputStream(inputStream);

				JSONObject json = new JSONObject(responseServer);

				// listForStudentToPass.clear();
				listForStudentId.clear();
				listForStudentName.clear();
				// listForStuddentCheckStatus.clear();

				status = json.getString("status");
				JSONArray studentArray = json.getJSONArray("students");
				for (int i = 0; i < studentArray.length(); i++) {
					JSONObject data = studentArray.getJSONObject(i);

					// listForStudentToPass.clear();
					listForStudentId.add(data.getString("id"));
					listForStudentName.add(data.getString("name"));
					listForStuddentCheckStatus.add(false);

					// listForStuddentCheckStatus.clear();
				}

				// message = json.getString("me ssage");
				Log.e("response", "response -----" + responseServer);

			} catch (Exception e) {
				e.printStackTrace();
			}*/
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
/*
			Log.e("responseServer", "" + groupStudResponse);
*/
			dialog.dismiss();

			try {
				JSONObject json = new JSONObject();

				// listForStudentToPass.clear();
				listForStudentId.clear();
				listForStudentName.clear();
				// listForStuddentCheckStatus.clear();

				if(groupStudResponse!=null&&!groupStudResponse.isEmpty())
				{

					json = new JSONObject(groupStudResponse);
					if(json.has("status")) {
						status = json.getString("status");
					}

				}






				if (status.equals("true")) {

					JSONArray studentArray = json.getJSONArray("students");
					for (int i = 0; i < studentArray.length(); i++) {
						JSONObject data = studentArray.getJSONObject(i);

						// listForStudentToPass.clear();
						listForStudentId.add(data.getString("id"));
						listForStudentName.add(data.getString("name"));
						listForStuddentCheckStatus.add(false);

						// listForStuddentCheckStatus.clear();
					}
					// Toast.makeText(getActivity(), "" + message,
					// Toast.LENGTH_LONG)
					// .show();
				} else {
					// Toast.makeText(getActivity(), "" + message,
					// Toast.LENGTH_LONG)
					// .show();

					try {


						String msg = json.getString("message");
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
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}

	}

	public static class InputStreamToStringExample {

		public static void main(String[] args) throws IOException {

			// intilize an InputStream
			InputStream is = new ByteArrayInputStream(
					"file content..blah blah".getBytes());

			String result = getStringFromInputStream(is);

			System.out.println(result);
			System.out.println("Done");

		}

		// convert InputStream to String
		private static String getStringFromInputStream(InputStream is) {

			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();

			String line;
			try {

				br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return sb.toString();
		}

	}

	public void selectIndividual() {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		final boolean[] checked = new boolean[listForStudentName.size()];

		String[] groupName = new String[listForStudentName.size()];

		for (int i = 0; i < listForStudentName.size(); i++) {
			groupName[i] = listForStudentName.get(i);
			checked[i] = listForStuddentCheckStatus.get(i);
		}

		// if (listForStuddentCheckStatus.size() > 0) {
		// for (int j = 0; j < listForStuddentCheckStatus.size(); j++) {
		// checked[j] = listForStuddentCheckStatus.get(j);
		// }
		// }

		// String array for alert dialog multi choice items
		// String[] colors = new String[] { "Red", "Green", "Blue", "Purple",
		// "Olive" };

		final List<String> colorsList = Arrays.asList(groupName);
		builder.setMultiChoiceItems(groupName, checked,
				new DialogInterface.OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {

						// Update the current focused item's checked status
						checked[which] = isChecked;

						// Get the current focused item
						String currentItem = colorsList.get(which);

					}
				});

		// Specify the dialog is not cancelable
		builder.setCancelable(false);

		// Set a title for alert dialog
		builder.setTitle("Select User");

		// Set the positive/yes button click listener
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Do something when click positive button
				// tv.setText("Your preferred colors..... \n");
				listForStuddentCheckStatus.clear();
				int count = 0;
				for (int i = 0; i < checked.length; i++) {
					boolean checkedNew = checked[i];
					if (checkedNew) {
						listForStuddentCheckStatus.add(true);
						count = count + 1;
						txtForSelectChildern.setText("" + count + " Users");
						listForStudentToPass.add(listForStudentId.get(i)
								.toString());
						// new GetUserAccToGroup().execute();
						// tv.setText(tv.getText() + colorsList.get(i) + "\n");
					} else {
						listForStuddentCheckStatus.add(false);
					}

				}
				Log.e("", "" + listForStudentToPass);
			}
		});

		// Set the negative/no button click listener
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Do something when click the negative button
			}
		});

		// Set the neutral/cancel button click listener
		builder.setNeutralButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Do something when click the neutral button
					}
				});

		AlertDialog dialog = builder.create();
		// Display the alert dialog on interface
		dialog.show();
	}

	public void setAdapterToSpinner(ArrayList<String> aL) {
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
				getActivity(), R.layout.layout_for_spinner_item, aL);
		spinnerArrayAdapter
				.setDropDownViewResource(R.layout.layout_for_spinner_item);
		txtForWeekInterval.setAdapter(spinnerArrayAdapter);
		selectItem = true;
	}

	class SaveScheduleData extends AsyncTask<Void, Void, Void> {

		String status="", message="";
		private MyCustomProgressDialog dialog;
		JSONObject jsonobj = new JSONObject();
		String scheduleResponse="";
		String url=Base_url
				+ "lms_api/retrivals/schedule_attendence";


		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			try {

				JSONArray sid = new JSONArray();
				JSONArray gid = new JSONArray();

				JSONArray obj = new JSONArray();
				try {
					for (int i = 0; i < numberOfWeeks; i++) {

						JSONArray obj1 = new JSONArray();

						for (int j = 0; j < 7; j++) {
							JSONObject list2 = new JSONObject();

							if (j == 0) {
								list2.put("std_left_time", listForMonDR.get(i));
								list2.put("std_retrieval_time",
										listForMonRT.get(i));
							}

							if (j == 1) {
								list2.put("std_left_time", listForTuesDR.get(i));
								list2.put("std_retrieval_time",
										listForTuesRT.get(i));
							}

							if (j == 2) {
								list2.put("std_left_time", listForWedDR.get(i));
								list2.put("std_retrieval_time",
										listForWedRT.get(i));
							}

							if (j == 3) {
								list2.put("std_left_time", listForThusDR.get(i));
								list2.put("std_retrieval_time",
										listForThusRT.get(i));
							}

							if (j == 4) {
								list2.put("std_left_time", listForFriDR.get(i));
								list2.put("std_retrieval_time",
										listForFriRT.get(i));
							}

							if (j == 5) {
								list2.put("std_left_time", listForSatDR.get(i));
								list2.put("std_retrieval_time",
										listForSatRT.get(i));
							}

							if (j == 6) {
								list2.put("std_left_time", listForSunDR.get(i));
								list2.put("std_retrieval_time",
										listForSunRT.get(i));
							}

							obj1.put(list2);
						}

						obj.put(obj1);
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				for (int i = 0; i < listForStudentToPass.size(); i++) {
					sid.put(i, listForStudentToPass.get(i));
				}

				for (int i = 0; i < listForGroupIdToPass.size(); i++) {
					gid.put(i, listForGroupIdToPass.get(i));
				}

				jsonobj.put("authentication_token", auth_key);
				jsonobj.put("securityKey", Security);
				jsonobj.put("language", lang);
				jsonobj.put("from_date", txtForPickFromDate.getText()
						.toString());
				jsonobj.put("to_date", txtForPickToDate.getText().toString());
				jsonobj.put("interval", numberOfWeeks);
				jsonobj.put("group_id", gid);
				jsonobj.put("student_id", sid);
				jsonobj.put("intervals", obj);



				dialog = new MyCustomProgressDialog(getActivity());
				dialog.setIndeterminate(true);
				dialog.setCancelable(false);
				dialog.show();

			}catch (Exception e)
			{
				e.printStackTrace();
			}

		}

		@Override
		protected Void doInBackground(Void... voids) {


			try {
				String urlParams = "&" + Const.Params.JsonData + "=" + URLEncoder.encode(jsonobj.toString(), "UTF-8");


				scheduleResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);



				/*HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(Base_url
						+ "lms_api/retrivals/schedule_attendence");

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("jsonData", jsonobj
						.toString()));

				Log.e("mainToPost", "mainToPost" + nameValuePairs.toString());

				// Use UrlEncodedFormEntity to send in proper format which we
				// need
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httppost);

				Log.e("-=-=-=-=-=-=-=  ", "" + response);

				InputStream inputStream = response.getEntity().getContent();
				InputStreamToStringExample str = new InputStreamToStringExample();
				responseServer = str.getStringFromInputStream(inputStream);
				JSONObject json = new JSONObject(responseServer);

				status = json.getString("status");
				message = json.getString(Const.Params.Message);
				Log.e("response", "response -----" + responseServer);
*/


			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			// Log.e("responseServer", "" + responseServer);
			dialog.dismiss();
			try {
				JSONObject json = new JSONObject();
				if (!scheduleResponse.isEmpty() && scheduleResponse != null) {
					 json = new JSONObject(scheduleResponse);
					if(json.has("status")) {

						status = json.getString("status");
						if(json.has(Const.Params.Message)) {
							message = json.getString(Const.Params.Message);
						}

					}



				}


				if (status.equals("true")) {
					Toast.makeText(getActivity(), "" + message, Toast.LENGTH_LONG)
							.show();
					FragmentManager fragmentManager = getFragmentManager();
					AttandanceMainScreen rFragment = new AttandanceMainScreen();
					FragmentTransaction ft = fragmentManager.beginTransaction();
					ft.replace(R.id.content_frame, rFragment);
					ft.commit();
				} else {

					try {


						String msg = json.getString("message");
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

					Toast.makeText(getActivity(), "" + message, Toast.LENGTH_LONG)
							.show();
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}

	}

	public void test() {
		JSONArray obj = new JSONArray();
		try {
			for (int i = 0; i < numberOfWeeks; i++) {

				JSONArray obj1 = new JSONArray();

				for (int j = 0; j < 7; j++) {
					JSONObject list2 = new JSONObject();

					if (j == 0) {
						list2.put("std_left_time", listForMonDR.get(i));
						list2.put("std_retrieval_time", listForMonRT.get(i));
					}

					if (j == 1) {
						list2.put("std_left_time", listForTuesDR.get(i));
						list2.put("std_retrieval_time", listForTuesRT.get(i));
					}

					if (j == 2) {
						list2.put("std_left_time", listForWedDR.get(i));
						list2.put("std_retrieval_time", listForWedRT.get(i));
					}

					if (j == 3) {
						list2.put("std_left_time", listForThusDR.get(i));
						list2.put("std_retrieval_time", listForThusRT.get(i));
					}

					if (j == 4) {
						list2.put("std_left_time", listForFriDR.get(i));
						list2.put("std_retrieval_time", listForFriRT.get(i));
					}

					if (j == 5) {
						list2.put("std_left_time", listForSatDR.get(i));
						list2.put("std_retrieval_time", listForSatRT.get(i));
					}

					if (j == 6) {
						list2.put("std_left_time", listForSunDR.get(i));
						list2.put("std_retrieval_time", listForSunRT.get(i));
					}

					obj1.put(list2);
				}

				obj.put(obj1);

				Log.e("sample", "" + obj);
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
