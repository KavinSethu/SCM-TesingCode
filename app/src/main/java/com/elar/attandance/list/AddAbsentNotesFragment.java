package com.elar.attandance.list;

import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/*import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONException;
import org.json.JSONObject;

import com.elar.util.NetworkUtil;
import com.pnf.elar.app.R;
import com.pnf.elar.app.Drawer;

import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.ParentChildComponent;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.activity.schedule.AddActivityActivity;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressWarnings("deprecation")
public class AddAbsentNotesFragment extends Fragment {

    // gcm notification variables
    String regId = "";
    RelativeLayout absence_note;
    LinearLayout mark;
    String studentId, name, user_typ, child_id, child_name, leaveSeleted, txtToDate, edtDateFrom;
    private TextView txtForUserName;
    private TextView edtForDateFrom, txtForToDate;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar2;
    DatePickerDialog.OnDateSetListener date2;
    private Button btnForCancel, btnForSend;
    private EditText edtForNotes;
    UserSessionManager session;
    String auth_key, securityKey, lang, Base_url, Security = "H67jdS7wwfh",
            note;
    private Spinner spinnerForLeaveType;
    Boolean markabsent = false;
    ArrayAdapter<String> adpForSpinner;
    ArrayList<String> listForLeaveType = new ArrayList<String>();
    TextView Date, To, txt11, txt1, markabs;
    View v;
    ImageView markasabsent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        v = inflater.inflate(R.layout.add_absence_notes_fragment, container,
                false);

        session = new UserSessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        //	auth_key = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        regId = user.get(UserSessionManager.TAG_regId);
        user_typ = user.get(UserSessionManager.TAG_user_type);
        child_id = user.get(UserSessionManager.TAG_child_id);
        child_name = user.get(UserSessionManager.TAG_cld_nm);
        auth_key = user.get(UserSessionManager.TAG_Authntication_token);
        absence_note = (RelativeLayout) v.findViewById(R.id.absence_note);

        try {
            Bundle bundle = this.getArguments();
            studentId = bundle.getString("keyForStudentId");
            name = bundle.getString("keyForStudentName");
        } catch (Exception e) {
            // TODO: handle exception
        }
        //////////back to Main Activity ////Set title/////

        if (lang.equalsIgnoreCase("sw")) {
            ((Drawer) getActivity()).setActionBarTitle("Editera frånvaroanmälan");
        } else {
            ((Drawer) getActivity()).setActionBarTitle("Edit Absent Note");
        }
        /////////////

        markasabsent = (ImageView) v.findViewById(R.id.markasabsent);
        txtForUserName = (TextView) v.findViewById(R.id.txtForUserName);
        Date = (TextView) v.findViewById(R.id.Date);
        To = (TextView) v.findViewById(R.id.To);
        edtForDateFrom = (TextView) v.findViewById(R.id.edtForDateFrom);
        txtForToDate = (TextView) v.findViewById(R.id.txtForToDate);
        txt11 = (TextView) v.findViewById(R.id.txt11);
        txt1 = (TextView) v.findViewById(R.id.txt1);
        btnForCancel = (Button) v.findViewById(R.id.btnForCancel);
        btnForSend = (Button) v.findViewById(R.id.btnForSend);
        spinnerForLeaveType = (Spinner) v.findViewById(R.id.spinnerForLeaveType);
        mark = (LinearLayout) v.findViewById(R.id.mark);
        markabs = (TextView) v.findViewById(R.id.markabsent);

        if (user_typ.equalsIgnoreCase("Parent")) {
            absence_note.setBackgroundColor(Color.parseColor("#F15A6D"));
//			auth_key = user.get(UserSessionManager.TAG_Authntication_Children);
            studentId = child_id;
            txtForUserName.setText("" + child_name);
            mark.setVisibility(View.GONE);
//			spinnerForLeaveType.setBackgroundColor(Color.parseColor("#F58C98"));
            btnForCancel.setBackgroundColor(Color.parseColor("#ED7485"));
            btnForSend.setBackgroundColor(Color.parseColor("#ED7485"));
            ((Drawer) getActivity()).setBackForChildEduedu();
            ((Drawer) getActivity()).Hideserch();
            ((Drawer) getActivity()).HideRefresh();

        } else {
//			auth_key = user.get(UserSessionManager.TAG_Authntication_token);
            txtForUserName.setText("" + name);
            ((Drawer) getActivity()).setBackFrompublishtomainAttendance();
        }

        if (lang.equalsIgnoreCase("sw")) {
            Date.setText("Datum:");
            To.setText("Till:");
            txt11.setText("Typ:");
            txt1.setText("Beskrivning:");
            btnForCancel.setText("Avbryt");
            btnForSend.setText("Klar");
            markabs.setText("Markera som frånvarande");
            edtForDateFrom.setText("ÅÅÅÅ-MM-DD");
            txtForToDate.setText("ÅÅÅÅ-MM-DD");

            listForLeaveType.add("Sjukdom");
            listForLeaveType.add("Ledighet");
            listForLeaveType.add("Övrig orsak");
        } else {
            listForLeaveType.add("Sick");
            listForLeaveType.add("Leave");
            listForLeaveType.add("Other");
        }

        adpForSpinner = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, listForLeaveType);
        adpForSpinner
                .setDropDownViewResource(R.layout.spinner_item);
        spinnerForLeaveType.setAdapter(adpForSpinner);
//////////////////// spinner click event//////////
        spinnerForLeaveType.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                // TODO Auto-generated method stub
                /*leaveSeleted = listForLeaveType.get(position);*/

                if (position == 0) {
                    leaveSeleted = "sick";
                } else if (position == 1) {
                    leaveSeleted = "leave";
                } else {
                    leaveSeleted = "other";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
////////////////// mark as absent
        markasabsent.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (markabsent.equals(false)) {
                    markabsent = true;
                    markasabsent.setBackgroundResource(R.drawable.checked);
                } else {
                    markasabsent.setBackgroundResource(R.drawable.checkbox);
                    markabsent = false;
                }
            }
        });
        ///////////////

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

        edtForDateFrom.setText("" + getCurrentDate());
        edtForDateFrom.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stu
                final Context themedContext1 = new ContextThemeWrapper(
                        getActivity(),
                        android.R.style.Theme_Holo
                );
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        DatePickerDialog dpd=  new DatePickerDialog(themedContext1, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH));
                        if (lang.equalsIgnoreCase("sw")) {
                            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Klar", dpd);
                            dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Avbryt", dpd);
                        }else{
                            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ok", dpd);
                            dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancel", dpd);
                        }
                        dpd.show();

                    }else{

                        new DatePickerDialog(getActivity(), date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        txtForToDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Context themedContext1 = new ContextThemeWrapper(
                        getActivity(),
                        android.R.style.Theme_Holo
                );
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        DatePickerDialog dpd= new DatePickerDialog(themedContext1, date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH));
                        if (lang.equalsIgnoreCase("sw")) {
                            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Klar", dpd);
                            dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Avbryt", dpd);
                        }else{
                            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ok", dpd);
                            dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancel", dpd);
                        }
                        dpd.show();

                    }else{

                        new DatePickerDialog(getActivity(), date2, myCalendar2
                                .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                                myCalendar2.get(Calendar.DAY_OF_MONTH)).show();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btnForCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                        if (user_typ.equalsIgnoreCase("parent")) {
                            FragmentManager fragmentManager = getFragmentManager();
                            ParentChildComponent rFragment = new ParentChildComponent();
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            ft.replace(R.id.content_frame, rFragment);
                            ft.commit();
                        } else {
                            FragmentManager fragmentManager = getFragmentManager();
                            UserProfileFragment rFragment = new UserProfileFragment();
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            Bundle bundle = new Bundle();
                            bundle.putString("keyForStudentId", studentId);
                            rFragment.setArguments(bundle);
                            ft.replace(R.id.content_frame, rFragment);
                            ft.commit();
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


            }
        });

        edtForNotes = (EditText) v.findViewById(R.id.editText1);

        btnForSend.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                        note = edtForNotes.getText().toString().trim();

                        if (note.equals("") || note.equals(null)) {
                            edtForNotes.setError("Required field");
    //					Toast.makeText(getActivity(),
    //							"Please fill required field.", Toast.LENGTH_LONG)
    //							.show();
                        } else {

                            txtToDate = txtForToDate.getText().toString();
                            edtDateFrom = edtForDateFrom.getText().toString();
                            if (txtToDate.equalsIgnoreCase("YYYY-MM-DD") || txtToDate.contains("MM-DD")) {
                                txtToDate = "";
                            }
                            if (edtDateFrom.equalsIgnoreCase("YYYY-MM-DD") || txtToDate.contains("MM-DD")) {
                                edtDateFrom = "";
                            }

                            new UpdateAbsentNote().execute();
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });


        new GetRetrieverData().execute();

        return v;
    }

    public void updateEdt() {

        String myFormat = "yyyy-MM-dd"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtForDateFrom.setText(sdf.format(myCalendar.getTime()));
    }

    public void updateEdt2() {

        String myFormat = "yyyy-MM-dd"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txtForToDate.setText(sdf.format(myCalendar2.getTime()));
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

    class GetRetrieverData extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/retrivals/get_absence_note",
                Status = "", message = "";
        String note;
        String created;

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

            String retDataResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_key, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.UserTypeApp + "=" + URLEncoder.encode("android", "UTF-8") +
                        "&" + Const.Params.SickDate + "=" + URLEncoder.encode(getCurrentDate(), "UTF-8") +
                        "&" + Const.Params.StudentId + "=" + URLEncoder.encode(studentId, "UTF-8");


                retDataResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }


//			Log.i("auth_key", auth_key);
//			Log.i("studentId", studentId);
//			Log.i("getCurrentDate()", getCurrentDate());
//			Log.i("lang", lang);

			/*List<NameValuePair> params = new ArrayList<NameValuePair>();
JSONParser jsonParser = new JSONParser();
			params.add(new BasicNameValuePair("securityKey", Security));
			params.add(new BasicNameValuePair("authentication_token", auth_key));
			params.add(new BasicNameValuePair("user_type_app", "android"));
			params.add(new BasicNameValuePair("language", lang));
			params.add(new BasicNameValuePair("sickdate", "" + getCurrentDate()));
			params.add(new BasicNameValuePair("student_id", studentId));

			Log.e("Create  -=-=-=-=-=- ", params.toString());

			JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);*/


            return retDataResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            JSONObject jsonObj = new JSONObject();

            try {
                if (results != null && !results.isEmpty()) {

                    jsonObj = new JSONObject(results);
                    if (jsonObj.has(Const.Params.Status)) {
                        Status = jsonObj.getString(Const.Params.Status).toString();

                    }


                } else {
                }


                try {


                    if (Status.equalsIgnoreCase("true")) {

                        JSONObject data = jsonObj.getJSONObject("data");
                        note = URLDecoder.decode(data.getString("description")).toString();

                        String leaveType = URLDecoder.decode(data.getString("leave_type")).toString();

                        if (("sick").equalsIgnoreCase(leaveType)) {
                            spinnerForLeaveType.setSelection(0);
                        } else if (("leave").equalsIgnoreCase(leaveType)) {
                            spinnerForLeaveType.setSelection(1);
                        } else {
                            spinnerForLeaveType.setSelection(2);
                        }

                        txtForToDate.setText(created);
                        edtForNotes.setText(note);
//					URLEncoder.encode(note).toString())
//					note  = Html.fromHtml(Html.fromHtml(data.getString("description")).toString());
                        // note = Html.fromHtml(data.getString("description"));
                        created = data.getString("created");

                    } else if (Status.equalsIgnoreCase("false")) {

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

                        if (jsonObj.has(Const.Params.Message)) {

                            message = jsonObj.getString(Const.Params.Message).toString();
                        }
                        /*SmartClassUtil.showToast(getActivity(),message);*/


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    class UpdateAbsentNote extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/retrivals/update_absence_note",
                Status = "", message = "";

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

            String updataResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_key, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.ToSickDate + "=" + URLEncoder.encode(txtToDate, "UTF-8") +
                        "&" + Const.Params.StudentId + "=" + URLEncoder.encode(studentId, "UTF-8") +
                        "&" + Const.Params.SickDate + "=" + URLEncoder.encode(edtDateFrom, "UTF-8") +
                        "&" + Const.Params.SickDescription + "=" + URLEncoder.encode(note.toString(), "UTF-8") +
                        "&" + Const.Params.LeaveType + "=" + URLEncoder.encode(leaveSeleted, "UTF-8") +
                        "&" + Const.Params.DeviceTokenApp + "=" + URLEncoder.encode(regId, "UTF-8") +
                        "&" + Const.Params.Approved + "=" + URLEncoder.encode("0", "UTF-8");


                updataResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }


            /*JSONParser jsonParser = new JSONParser();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

//			Log.i("studentId", studentId);
//			Log.i("auth_key", auth_key);
//			Log.i("leaveSeleted", leaveSeleted);
//			Log.i(" to date", txtForToDate.getText().toString());
//			Log.i("from date", edtForDateFrom.getText().toString());


params.add(new BasicNameValuePair("securityKey", Security));
                params.add(new BasicNameValuePair("authentication_token", auth_key));
                params.add(new BasicNameValuePair("to_sickdate", txtToDate));
                params.add(new BasicNameValuePair("language", lang));
                params.add(new BasicNameValuePair("sickdate", edtDateFrom));
                params.add(new BasicNameValuePair("student_id", studentId));  ///////////////////// question /////////
                params.add(new BasicNameValuePair("sick_description", URLEncoder.encode(note).toString()));
                params.add(new BasicNameValuePair("leave_type", leaveSeleted));
                params.add(new BasicNameValuePair("device_token_app", regId));
                params.add(new BasicNameValuePair("approved", "0"));


            Log.e("Create  -=-=-=-=-=- ", params.toString());

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);*/

            try {


            } catch (Exception e) {
                e.printStackTrace();
            }

            return updataResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {
                JSONObject updateJson = new JSONObject();


                if (results != null && !results.isEmpty()) {
                    updateJson = new JSONObject(results);
                    if (updateJson.has(Const.Params.Status)) {


                        Status = updateJson.getString(Const.Params.Status).toString();
                        if (updateJson.has(Const.Params.Message)) {

                            message = updateJson.getString(Const.Params.Message).toString();
                        }
                    } else {
                        message = "Service Failed";

                    }


                } else {
                    message = "Service Failed";
                }


                if (Status.equalsIgnoreCase("true")) {

                    adpForSpinner
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerForLeaveType.setAdapter(adpForSpinner);

                    if (user_typ.equalsIgnoreCase("parent")) {
                        String msgone, ok;
                        if (lang.equalsIgnoreCase("sw")) {
                            msgone = "Frånvaroanmälan har skickats in";
                            ok = "Ok";
                        } else {
                            msgone = "Note of absence has been added";
                            ok = "Ok";
                        }

                        Toast.makeText(getActivity(), msgone, Toast.LENGTH_SHORT).show();
                        FragmentManager fragmentManager = getFragmentManager();
                        ParentChildComponent rFragment = new ParentChildComponent();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, rFragment);
                        ft.commit();

//						AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
//				        alertDialog.setTitle("");
//				        alertDialog.setMessage("Absent Note added successfully...");
//				        alertDialog.setIcon(R.drawable.tick);
//				        alertDialog.setButton(ok, new DialogInterface.OnClickListener() {
//						public void onClick(DialogInterface dialog, int which) 
//						{
//							FragmentManager fragmentManager = getFragmentManager();
//							ParentChildComponent rFragment = new ParentChildComponent();
//							FragmentTransaction ft = fragmentManager.beginTransaction();
//							ft.replace(R.id.content_frame, rFragment);
//							ft.commit();
//						}
//				});
//
//				// Showing Alert Message
//				alertDialog.show();

                    } else {

                        FragmentManager fragmentManager = getFragmentManager();
                        UserProfileFragment rFragment = new UserProfileFragment();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("keyForStudentId", studentId);
                        rFragment.setArguments(bundle);
                        ft.replace(R.id.content_frame, rFragment);
                        ft.commit();


                    }


                } else {

//					Toast.makeText(getActivity(), "  " + message,
//							Toast.LENGTH_LONG).show();

                    try {


                        String msg = updateJson.getString("message");
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
}
