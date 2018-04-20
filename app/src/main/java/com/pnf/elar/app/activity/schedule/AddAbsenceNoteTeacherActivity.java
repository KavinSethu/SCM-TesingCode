package com.pnf.elar.app.activity.schedule;


import android.app.DatePickerDialog;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.elar.util.SmartClassUtil;
import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.R;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.AppLog;
import com.pnf.elar.app.util.Const;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAbsenceNoteTeacherActivity extends AppCompatActivity {

    @Bind(R.id.userheaderText)
    TextView userheaderText;
    @Bind(R.id.userNameText)
    TextView userNameText;

    @Bind(R.id.fromDateLabel)
    TextView fromDateLabel;
    @Bind(R.id.fromDateText)
    TextView fromDateText;
    @Bind(R.id.toDateLabel)
    TextView toDateLabel;
    @Bind(R.id.toDateText)
    TextView toDateText;
    @Bind(R.id.dateLayout)
    LinearLayout dateLayout;
    @Bind(R.id.leaveTypeLabel)
    TextView leaveTypeLabel;
    @Bind(R.id.leaveTypeSpinner)
    Spinner leaveTypeSpinner;
    @Bind(R.id.noteLabel)
    TextView noteLabel;
    @Bind(R.id.leaveNoteEditText)
    EditText leaveNoteEditText;
    @Bind(R.id.markAbsentChk)
    CheckBox markAbsentChk;
    @Bind(R.id.markAbsentText)
    TextView markAbsentText;
    @Bind(R.id.markLayout)
    LinearLayout markLayout;
    @Bind(R.id.btnForCancel)
    Button btnForCancel;
    @Bind(R.id.btnForSend)
    Button btnForSend;
    @Bind(R.id.buttonLayout)
    RelativeLayout buttonLayout;
    @Bind(R.id.activity_add_absence_note)
    RelativeLayout activityAddAbsenceNote;

    boolean modified = false;
    UserSessionManager session;
    HashMap<String, String> user;
    String language, auth_token, base_url, securityKey = "H67jdS7wwfh", user_id = "", regId = "", firstName = "", lastName = "",usertype="";
    Context context;

    Calendar fromCalendar;
    DatePickerDialog.OnDateSetListener fromDateDg;
    String fromDateVal = "";

    Calendar toCalendar;
    DatePickerDialog.OnDateSetListener toDateDg;
    String toDateVal = "";

    String sw_toDateError = "Välj giltigt hittills";
    String en_toDateError = "Select valid to date";
    String sw_fromDateError = "Välj giltigt datum";
    String en_fromDateError = "Select valid date";
    String selectedDate = "";
    String apiDate = "";
    String TAG = "Absence Note";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");

    String description = "", leaveSeleted = "";
    String markAbsent = "0";

    ArrayAdapter<String> adpForSpinner;
    ArrayList<String> listForLeaveType = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_absence_note);
        ButterKnife.bind(this);
        getSessionValues();

    }

    public void getSessionValues() {
        try {
            context = getApplicationContext();
            session = new UserSessionManager(this);
            user = session.getUserDetails();
            language = user.get(UserSessionManager.TAG_language);
            auth_token = user.get(UserSessionManager.TAG_Authntication_token);
            base_url = user.get(UserSessionManager.TAG_Base_url);

            usertype = user.get(UserSessionManager.TAG_user_type);
            if(usertype.equalsIgnoreCase("Parent"))
            {
                user_id=user.get(UserSessionManager.TAG_child_id);
               String childName = user.get(UserSessionManager.TAG_cld_nm);
                userNameText.setText(Html.fromHtml(childName));
            }else
            {
                user_id = user.get(UserSessionManager.TAG_user_id);
                userNameText.setText(Html.fromHtml(getIntent().getStringExtra(Const.CommonParams.NAME)));
            }

            regId = user.get(UserSessionManager.TAG_regId);
            selectedDate = getIntent().getStringExtra(Const.CommonParams.SELECTED_DATE);
            apiDate = getIntent().getStringExtra(Const.CommonParams.API_DATE);
            setLanguageVariables();
            setFromToDate();
            setLeaveType();
        } catch (Exception e) {
            AppLog.handleException(TAG, e);
        }
    }

    public void setLanguageVariables() {
        try {
            if (language.equalsIgnoreCase("sw")) {
                fromDateLabel.setText("Datum:");
                toDateText.setText("Till:");
                leaveTypeLabel.setText("Typ:");
                noteLabel.setText("Beskrivning:");
                btnForCancel.setText("Avbryt");
                btnForSend.setText("Klar");
                markAbsentText.setText("Markera som frånvarande");
                fromDateText.setText("ÅÅÅÅ-MM-DD");
                toDateText.setText("ÅÅÅÅ-MM-DD");
                userheaderText.setText("frånvaro Beskrivning - " + apiDate);

            } else {
                userheaderText.setText("Absence description - " + apiDate);
            }

        } catch (Exception e) {
            AppLog.handleException(TAG, e);
        }
    }

    public void setFromToDate() {
        try {
            Log.e("sdsd", selectedDate);
            final Date selDate = df.parse(selectedDate);
            fromCalendar = Calendar.getInstance();
            fromCalendar.setTime(selDate);
            fromDateVal = sdf.format(fromCalendar.getTime());
            fromDateText.setText(sdf.format(fromCalendar.getTime()));
            fromDateDg = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    try {
                        fromCalendar.set(Calendar.YEAR, year);
                        fromCalendar.set(Calendar.MONTH, monthOfYear);
                        fromCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        Date fromDate = sdf.parse(sdf.format(fromCalendar.getTime()));
                        if (fromDate.after(selDate) || (fromDate.equals(selDate))) {
                            fromDateVal = sdf.format(fromCalendar.getTime());
                            fromDateText.setText(sdf.format(fromCalendar.getTime()));
                        } else {
                            if (language.equals("en")) {
                                SmartClassUtil.showToast(context, en_fromDateError);
                            } else {
                                SmartClassUtil.showToast(context, sw_fromDateError);
                            }
                            fromCalendar.setTime(selDate);
                            fromDateVal = sdf.format(fromCalendar.getTime());
                            fromDateText.setText(sdf.format(fromCalendar.getTime()));
                        }
                        if (language.equals("sw")) {
                            toDateText.setText("ÅÅÅÅ-MM-DD");
                        } else {
                            toDateText.setText("YYY-MM-DD");
                        }
                    } catch (Exception e) {
                        AppLog.handleException(TAG, e);
                    }
                }

            };

            toCalendar = Calendar.getInstance();
            toCalendar.setTime(fromCalendar.getTime());
            toDateVal = sdf.format(toCalendar.getTime());
            toDateDg = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    try {
                        // TODO Auto-generated method stub
                        toCalendar.set(Calendar.YEAR, year);
                        toCalendar.set(Calendar.MONTH, monthOfYear);
                        toCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        Date fromDate = sdf.parse(sdf.format(fromCalendar.getTime()));
                        Date toDate = sdf.parse(sdf.format(toCalendar.getTime()));
                        if (toDate.after(fromDate) || toDate.equals(fromDate)) {
                            toDateVal = sdf.format(toCalendar.getTime());
                            toDateText.setText(sdf.format(toCalendar.getTime()));
                        } else {
                            if (("en").equals(language)) {
                                SmartClassUtil.showToast(context, en_toDateError);
                            } else {
                                SmartClassUtil.showToast(context, sw_toDateError);
                            }
                            toCalendar.setTime(fromCalendar.getTime());
                            toDateVal = sdf.format(toCalendar.getTime());
                            toDateText.setText(sdf.format(toCalendar.getTime()));
                        }
                    } catch (Exception e) {
                        AppLog.handleException(TAG, e);
                    }
                }

            };
        } catch (Exception e) {
            AppLog.handleException(TAG, e);
        }
    }

    public void setLeaveType() {
        try {
            if (("sw").equalsIgnoreCase(language)) {
                listForLeaveType.add("Sjukdom");
                listForLeaveType.add("Ledighet");
                listForLeaveType.add("Övrig orsak");
            } else {
                listForLeaveType.add("Sick");
                listForLeaveType.add("Leave");
                listForLeaveType.add("Other");
            }

            adpForSpinner = new ArrayAdapter<String>(context, R.layout.spinner_item, listForLeaveType);
            adpForSpinner.setDropDownViewResource(R.layout.spinner_item);
            leaveTypeSpinner.setAdapter(adpForSpinner);
            leaveTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent,
                                           View view, int position, long id) {
                    // TODO Auto-generated method stub

                    if(position==0)
                    {
                        leaveSeleted="sick";
                    }
                    else if(position==1)
                    {
                        leaveSeleted="leave";
                    }
                    else
                    {
                        leaveSeleted="other";
                    }
                     /*= listForLeaveType.get(position);*/
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            });
            markAbsentChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        markAbsent = "1";
                    } else {
                        markAbsent = "0";
                    }
                }
            });
        } catch (Exception e) {
            AppLog.handleException(TAG, e);
        }

    }

    @OnClick({R.id.fromDateText, R.id.toDateText, R.id.btnForCancel, R.id.btnForSend})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fromDateText:
                try {
                    new DatePickerDialog(AddAbsenceNoteTeacherActivity.this, fromDateDg, fromCalendar
                            .get(Calendar.YEAR), fromCalendar.get(Calendar.MONTH),
                            fromCalendar.get(Calendar.DAY_OF_MONTH)).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.toDateText:
                try {
                    new DatePickerDialog(AddAbsenceNoteTeacherActivity.this, toDateDg, toCalendar
                            .get(Calendar.YEAR), toCalendar.get(Calendar.MONTH),
                            toCalendar.get(Calendar.DAY_OF_MONTH)).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnForCancel:
                setRefresh();
                break;
            case R.id.btnForSend:

                description = leaveNoteEditText.getText().toString().trim();
                if (description.length() == 0) {
                    if (language.equalsIgnoreCase("en"))
                        leaveNoteEditText.setError(Html.fromHtml("<font color='red'>Required field</font>"));
                    else
                        leaveNoteEditText.setError(Html.fromHtml("<font color='red'>Obligatoriskt fält</font>"));
                } else {
                    new UpdateAbsentNote().execute();
                }
                break;
            default:
                break;
        }
    }

    class UpdateAbsentNote extends AsyncTask<String, String, String> {

        private String url = base_url + "lms_api/retrivals/update_absence_note",
                status = "", message = "";

        private MyCustomProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new MyCustomProgressDialog(AddAbsenceNoteTeacherActivity.this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        protected String doInBackground(String... args) {

            String updataResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(securityKey, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(language, "UTF-8") +
                        "&" + Const.Params.ToSickDate + "=" + URLEncoder.encode(fromDateVal, "UTF-8") +
                        "&" + Const.Params.StudentId + "=" + URLEncoder.encode(user_id, "UTF-8") +
                        "&" + Const.Params.SickDate + "=" + URLEncoder.encode(fromDateVal, "UTF-8") +
                        "&" + Const.Params.SickDescription + "=" + URLEncoder.encode(description, "UTF-8") +
                        "&" + Const.Params.LeaveType + "=" + URLEncoder.encode(leaveSeleted, "UTF-8") +
                        "&" + Const.Params.DeviceTokenApp + "=" + URLEncoder.encode(regId, "UTF-8") +
                        "&" + Const.Params.Approved + "=" + URLEncoder.encode(markAbsent, "UTF-8");
                updataResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                AppLog.handleException(TAG, e);
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
                        status = updateJson.getString(Const.Params.Status).toString();
                        if (status.equalsIgnoreCase(Const.Params.TRUE)) {
                            if (language.equalsIgnoreCase("sw")) {
                                message = "Frånvaroanmälan har skickats in";
                            } else {
                                message = "Note of absence has been added";
                            }
                            modified = true;
                        } else {
                            if (updateJson.has(Const.Params.Message)) {
                                message = updateJson.getString(Const.Params.Message).toString();
                            }
                        }
                        SmartClassUtil.showToast(context, message);
                        setRefresh();
                    } else {
                    }
                } else {
                }
            } catch (Exception e) {
                AppLog.handleException(TAG, e);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setRefresh();
    }

    public void setRefresh() {
        try {
            Intent mainIntent = new Intent();
            mainIntent.putExtra(Const.CommonParams.REFRESH, modified);
            setResult(RESULT_OK, mainIntent);
            finish();
        } catch (Exception e) {
            AppLog.handleException(TAG, e);
        }
    }
}
