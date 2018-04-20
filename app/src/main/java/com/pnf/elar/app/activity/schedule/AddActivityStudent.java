package com.pnf.elar.app.activity.schedule;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.elar.util.NetworkUtil;
import com.elar.util.SmartClassUtil;
import com.pnf.elar.app.Bo.schedule.ChildrensBo;
import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.R;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.API;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.ToStringConverterFactory;
import com.pnf.elar.app.adapter.schedule.ChildrensAdapter;
import com.pnf.elar.app.util.AppLog;
import com.pnf.elar.app.util.Const;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddActivityStudent extends AppCompatActivity {

    @Bind(R.id.backbtnImage)
    ImageView backbtnImage;
    @Bind(R.id.headerNameText)
    TextView headerNameText;
    @Bind(R.id.saveImage)
    ImageView saveImage;
    @Bind(R.id.childrenHeaderText)
    TextView childrenHeaderText;
    @Bind(R.id.childrenRv)
    RecyclerView childrenRv;
    @Bind(R.id.startHeaderText)
    TextView startHeaderText;
    @Bind(R.id.startDateText)
    TextView startDateText;
    @Bind(R.id.startTimeHeaderText)
    TextView startTimeHeaderText;
    @Bind(R.id.startTimeText)
    TextView startTimeText;
    @Bind(R.id.endDateHeaderText)
    TextView endDateHeaderText;
    @Bind(R.id.endDateText)
    TextView endDateText;
    @Bind(R.id.endTimeHeaderText)
    TextView endTimeHeaderText;
    @Bind(R.id.endTimeText)
    TextView endTimeText;
    @Bind(R.id.startEndDateLayout)
    LinearLayout startEndDateLayout;
    @Bind(R.id.scheduleText)
    TextView scheduleText;
    @Bind(R.id.scheduleEditText)
    EditText scheduleEditText;
    @Bind(R.id.descripText)
    TextView descripText;
    @Bind(R.id.descripEditText)
    EditText descripEditText;


    UserSessionManager session;
    HashMap<String, String> user;
    String language = "", auth_token = "", baseUrl = "", userId = "", securityKey = "H67jdS7wwfh";
    Context context;
    String selectedDate;

    MyCustomProgressDialog dialogLoading;


    Calendar startCalendar;
    DatePickerDialog.OnDateSetListener startDateDg;
    TimePickerDialog startTimeDg;
    int startHour, startMinute;
    String startDateVal = "";

    Calendar enddateCalendar;
    DatePickerDialog.OnDateSetListener endDateDg;
    TimePickerDialog endTimeDg;
    String endDateVal = "";
    int endHour, endMinute;

    String timeTitle = "";
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");


    String sw_endDateError = "Välj giltigt slutdatum";
    String en_endDateError = "Select valid end date";

    String Tag="Activity Parent";

    JSONObject reqJsonObj;


    String scheduleTitle,description;


    JSONArray childList=new JSONArray();
    boolean refresh=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parent);
        ButterKnife.bind(this);
        getSessionValue();
        initView();

    }
    public void initView()
    {
        childrenHeaderText.setVisibility(View.GONE);
        childrenRv.setVisibility(View.GONE);
        dialogLoading = new MyCustomProgressDialog(AddActivityStudent.this);
        dialogLoading.setIndeterminate(true);
        dialogLoading.setCancelable(false);


        saveImage.setVisibility(View.VISIBLE);
            /*setOnClickListeners();*/
        setLanguage();
        setStartEndDate();
        setTimePicker();

    }

    public void getSessionValue() {
        try {
            context=getApplicationContext();
            session = new UserSessionManager(this);
            user = session.getUserDetails();
            language = user.get(UserSessionManager.TAG_language);
            auth_token = user.get(UserSessionManager.TAG_Authntication_token);
            baseUrl = user.get(UserSessionManager.TAG_Base_url);
            userId = user.get(UserSessionManager.TAG_user_id);

/*
            childId=user.get(UserSessionManager.TAG_child_id);
*/

            /*SmartClassUtil.PrintMessage("child ids : "+childId);*/

            selectedDate = getIntent().getStringExtra(Const.CommonParams.SELECTED_DATE);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLanguage() {
        try {

            if (language.equalsIgnoreCase("en")) {
                headerNameText.setText("Add Activity");
                startHeaderText.setText("Start Date");
                endDateHeaderText.setText("End Date");
                startTimeHeaderText.setText("Start Time");
                endTimeHeaderText.setText("End Time");
                scheduleText.setText("Schedule Title");
                descripText.setText("Description");
                childrenHeaderText.setText("Childrens");
                timeTitle = "Select time";
            } else {
                startHeaderText.setText("Start datum");
                endDateHeaderText.setText("Slutdatum");
                startTimeHeaderText.setText("Starttid");
                endTimeHeaderText.setText("Sluttid");
                headerNameText.setText("Skapa Bokning");
                scheduleText.setText("Titel");
                descripText.setText("Beskrivning");
                childrenHeaderText.setText("Barns");
                timeTitle = "Välj Tid";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @OnClick({R.id.backbtnImage,  R.id.saveImage, R.id.startDateText, R.id.endDateText, R.id.startTimeText, R.id.endTimeText})
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.backbtnImage:
                onBackPressed();
                break;
            case R.id.saveImage:

                try {
                    if (NetworkUtil.getInstance(context).isInternet()) {
                        checkValidation();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.startDateText:

                final Context themedContext = new ContextThemeWrapper(
                        AddActivityStudent.this,
                        android.R.style.Theme_Holo
                );
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        DatePickerDialog dpd = new DatePickerDialog(themedContext, startDateDg, startCalendar
                                .get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                                startCalendar.get(Calendar.DAY_OF_MONTH));
                        if (language.equalsIgnoreCase("sw")) {
                            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Klar", dpd);
                            dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Avbryt", dpd);
                        }else{
                            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ok", dpd);
                            dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancel", dpd);
                        }
                        dpd.show();

                    }else{
                        new DatePickerDialog(context, startDateDg, startCalendar
                                .get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                                startCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                try {
//                    new DatePickerDialog(AddActivityStudent.this, startDateDg, startCalendar
//                            .get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
//                            startCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                break;
            case R.id.endDateText:

                final Context themedContext1 = new ContextThemeWrapper(
                        AddActivityStudent.this,
                        android.R.style.Theme_Holo
                );
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        DatePickerDialog dpd = new DatePickerDialog(themedContext1, endDateDg, enddateCalendar
                                .get(Calendar.YEAR), enddateCalendar.get(Calendar.MONTH),
                                enddateCalendar.get(Calendar.DAY_OF_MONTH));
                        if (language.equalsIgnoreCase("sw")) {
                            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Klar", dpd);
                            dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Avbryt", dpd);
                        }else{
                            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ok", dpd);
                            dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancel", dpd);
                        }
                        dpd.show();

                    }else{
                        new DatePickerDialog(context, endDateDg, enddateCalendar
                                .get(Calendar.YEAR), enddateCalendar.get(Calendar.MONTH),
                                enddateCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                try {
//                    new DatePickerDialog(AddActivityStudent.this, endDateDg, enddateCalendar
//                            .get(Calendar.YEAR), enddateCalendar.get(Calendar.MONTH),
//                            enddateCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                break;

            case R.id.startTimeText:
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    final Context themedContext3 = new ContextThemeWrapper(
                            AddActivityStudent.this,
                            android.R.style.Theme_Holo
                    );

                    startHour = startCalendar.get(Calendar.HOUR_OF_DAY);
                    startMinute = startCalendar.get(Calendar.MINUTE);
                    startTimeText.setText(startHour + ":" + startMinute);
                    startTimeDg = new TimePickerDialog(themedContext3,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {
                                    // TODO Auto-generated method stub

                        /*startTimeText.setText(hourOfDay + ":" + minute);*/
                            /*aL.set(index, hourOfDay + ":" + minute);*/

                                    startCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    startCalendar.set(Calendar.MINUTE, minute);
                        /*if (startCalendar.getTime().before(Calendar.getInstance().getTime())) {
                            startHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                            endHour = Calendar.getInstance().get(Calendar.MINUTE);
                            SmartClassUtil.showToast(context, "Select valid time");
                        } else {*/
                                    startHour = hourOfDay;
                                    startMinute = minute;
/*
                        }
*/
                                    startTimeText.setText(startHour + ":" + startMinute);
                                }

                            }, startHour, startMinute, true);// Yes 24 hour time
                    startTimeDg.setTitle(timeTitle);
                    if (language.equalsIgnoreCase("sw")) {
                        startTimeDg.setTitle("Välj tid");
                        startTimeDg.setButton(DatePickerDialog.BUTTON_POSITIVE, "Klar", startTimeDg);
                        startTimeDg.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Avbryt", startTimeDg);
                    }else{
                        startTimeDg.setTitle("Select Time");
                        startTimeDg.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ok", startTimeDg);
                        startTimeDg.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancel", startTimeDg);
                    }
                    startTimeDg.show();
                }else{
                    startHour = startCalendar.get(Calendar.HOUR_OF_DAY);
                    startMinute = startCalendar.get(Calendar.MINUTE);
                    startTimeText.setText(startHour + ":" + startMinute);
                    startTimeDg = new TimePickerDialog(AddActivityStudent.this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {
                                    // TODO Auto-generated method stub

                        /*startTimeText.setText(hourOfDay + ":" + minute);*/
                            /*aL.set(index, hourOfDay + ":" + minute);*/

                                    startCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    startCalendar.set(Calendar.MINUTE, minute);
                        /*if (startCalendar.getTime().before(Calendar.getInstance().getTime())) {
                            startHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                            endHour = Calendar.getInstance().get(Calendar.MINUTE);
                            SmartClassUtil.showToast(context, "Select valid time");
                        } else {*/
                                    startHour = hourOfDay;
                                    startMinute = minute;
/*
                        }
*/
                                    startTimeText.setText(startHour + ":" + startMinute);
                                }

                            }, startHour, startMinute, true);// Yes 24 hour time
                    startTimeDg.setTitle(timeTitle);
                }
                break;
            case R.id.endTimeText:
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

                    final Context themedContext4 = new ContextThemeWrapper(
                            AddActivityStudent.this,
                            android.R.style.Theme_Holo
                    );

                    enddateCalendar.add(Calendar.HOUR_OF_DAY, 1);
                    endHour = enddateCalendar.get(Calendar.HOUR_OF_DAY);
                    endMinute = enddateCalendar.get(Calendar.MINUTE);
                    endTimeText.setText(endHour + ":" + endMinute);


                    endTimeDg = new TimePickerDialog(themedContext4,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    enddateCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    enddateCalendar.set(Calendar.MINUTE, minute);
                                    // TODO Auto-generated method stub
                                    if (enddateCalendar.getTime().before(startCalendar.getTime())) {
                            /*aL.set(index, hourOfDay + ":" + minute);*/
                                        endHour = startHour + 1;
                                        endMinute = startMinute;
                                        SmartClassUtil.showToast(context, "Select valid time");
                                    } else {
                                        endHour = hourOfDay;
                                        endMinute = minute;
                                    }
                                    endTimeText.setText(endHour + ":" + endMinute);

                                }

                            }, endHour, endMinute, true);// Yes 24 hour time
                    endTimeDg.setTitle(timeTitle);

                    if (language.equalsIgnoreCase("sw")) {
                        endTimeDg.setTitle("Välj tid");
                        endTimeDg.setButton(DatePickerDialog.BUTTON_POSITIVE, "Klar", endTimeDg);
                        endTimeDg.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Avbryt", endTimeDg);
                    }else{
                        endTimeDg.setTitle("Select Time");
                        endTimeDg.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ok", endTimeDg);
                        endTimeDg.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancel", endTimeDg);
                    }
                    endTimeDg.show();

                }else{
                    enddateCalendar.add(Calendar.HOUR_OF_DAY, 1);
                    endHour = enddateCalendar.get(Calendar.HOUR_OF_DAY);
                    endMinute = enddateCalendar.get(Calendar.MINUTE);
                    endTimeText.setText(endHour + ":" + endMinute);


                    endTimeDg = new TimePickerDialog(AddActivityStudent.this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    enddateCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    enddateCalendar.set(Calendar.MINUTE, minute);
                                    // TODO Auto-generated method stub
                                    if (enddateCalendar.getTime().before(startCalendar.getTime())) {
                            /*aL.set(index, hourOfDay + ":" + minute);*/
                                        endHour = startHour + 1;
                                        endMinute = startMinute;
                                        SmartClassUtil.showToast(context, "Select valid time");
                                    } else {
                                        endHour = hourOfDay;
                                        endMinute = minute;
                                    }
                                    endTimeText.setText(endHour + ":" + endMinute);

                                }

                            }, endHour, endMinute, true);// Yes 24 hour time
                    endTimeDg.setTitle(timeTitle);
                }
                break;

        }
    }


    public void setStartEndDate() {
        try {
            /*Log.e("sdsd", selectedDate);*/
            SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
            Date selDate = df.parse(selectedDate);
            startCalendar = Calendar.getInstance();
            startCalendar.setTime(selDate);

            startDateVal = sdf.format(startCalendar.getTime());
            startDateText.setText(sdf.format(startCalendar.getTime()));
            startDateDg = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    try {
                        startCalendar.set(Calendar.YEAR, year);
                        startCalendar.set(Calendar.MONTH, monthOfYear);
                        startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                      /*  if (startCalendar.getTime().before(Calendar.getInstance().getTime())) {
                            if (language.equals("en")) {
                                SmartClassUtil.showToast(context, en_startDateError);
                            } else {
                                SmartClassUtil.showToast(context, sw_startDateError);

                            }
                            startCalendar = Calendar.getInstance();
                        } else {
                        }*/
                        startDateVal = sdf.format(startCalendar.getTime());
                        startDateText.setText(sdf.format(startCalendar.getTime()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            };

            enddateCalendar = startCalendar;
            endDateVal = sdf.format(enddateCalendar.getTime());
            endDateText.setText(sdf.format(enddateCalendar.getTime()));
            endDateDg = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    try {
                        // TODO Auto-generated method stub
                        enddateCalendar.set(Calendar.YEAR, year);
                        enddateCalendar.set(Calendar.MONTH, monthOfYear);
                        enddateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

/*
                    if (enddateCalendar.getTime().before(Calendar.getInstance().getTime())) {
                        SmartClassUtil.showToast(context,"Please select upcomming date");
                        enddateCalendar = Calendar.getInstance();
                    }else*/
                        if (enddateCalendar.getTime().before(startCalendar.getInstance().getTime())) {

                            if (language.equals("en")) {
                                SmartClassUtil.showToast(context, en_endDateError);
                            } else {
                                SmartClassUtil.showToast(context, sw_endDateError);

                            }
                            enddateCalendar.setTime(startCalendar.getTime());


                        } else {


                        }

                        endDateVal = sdf.format(enddateCalendar.getTime());
                        endDateText.setText(sdf.format(enddateCalendar.getTime()));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            };




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTimePicker() {
        try
        {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

                final Context themedContext = new ContextThemeWrapper(
                        context,
                        android.R.style.Theme_Holo
                );

                startHour = startCalendar.get(Calendar.HOUR_OF_DAY);
                startMinute = startCalendar.get(Calendar.MINUTE);
                startTimeText.setText(startHour + ":" + startMinute);
                startTimeDg = new TimePickerDialog(themedContext,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // TODO Auto-generated method stub

                        /*startTimeText.setText(hourOfDay + ":" + minute);*/
                            /*aL.set(index, hourOfDay + ":" + minute);*/

                                startCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                startCalendar.set(Calendar.MINUTE, minute);
                        /*if (startCalendar.getTime().before(Calendar.getInstance().getTime())) {
                            startHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                            endHour = Calendar.getInstance().get(Calendar.MINUTE);
                            SmartClassUtil.showToast(context, "Select valid time");
                        } else {*/
                                startHour = hourOfDay;
                                startMinute = minute;
/*
                        }
*/
                                startTimeText.setText(startHour + ":" + startMinute);
                            }

                        }, startHour, startMinute, true);// Yes 24 hour time
                startTimeDg.setTitle(timeTitle);
                if (language.equalsIgnoreCase("sw")) {
                    startTimeDg.setTitle("Välj tid");
                    startTimeDg.setButton(DatePickerDialog.BUTTON_POSITIVE, "Klar", startTimeDg);
                    startTimeDg.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Avbryt", startTimeDg);
                }else{
                    startTimeDg.setTitle("Select Time");
                    startTimeDg.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ok", startTimeDg);
                    startTimeDg.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancel", startTimeDg);
                }
                startTimeDg.show();


                enddateCalendar.add(Calendar.HOUR_OF_DAY, 1);
                endHour = enddateCalendar.get(Calendar.HOUR_OF_DAY);
                endMinute = enddateCalendar.get(Calendar.MINUTE);
                endTimeText.setText(endHour + ":" + endMinute);


                endTimeDg = new TimePickerDialog(themedContext,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                enddateCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                enddateCalendar.set(Calendar.MINUTE, minute);
                                // TODO Auto-generated method stub
                                if (enddateCalendar.getTime().before(startCalendar.getTime())) {
                            /*aL.set(index, hourOfDay + ":" + minute);*/
                                    endHour = startHour + 1;
                                    endMinute = startMinute;
                                    SmartClassUtil.showToast(context, "Select valid time");
                                } else {
                                    endHour = hourOfDay;
                                    endMinute = minute;
                                }
                                endTimeText.setText(endHour + ":" + endMinute);

                            }

                        }, endHour, endMinute, true);// Yes 24 hour time
                endTimeDg.setTitle(timeTitle);

                if (language.equalsIgnoreCase("sw")) {
                    endTimeDg.setTitle("Välj tid");
                    endTimeDg.setButton(DatePickerDialog.BUTTON_POSITIVE, "Klar", endTimeDg);
                    endTimeDg.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Avbryt", endTimeDg);
                }else{
                    endTimeDg.setTitle("Select Time");
                    endTimeDg.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ok", endTimeDg);
                    endTimeDg.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancel", endTimeDg);
                }
                endTimeDg.show();
            /*mTimePicker.show();*/

            }else {
                startHour = startCalendar.get(Calendar.HOUR_OF_DAY);
                startMinute = startCalendar.get(Calendar.MINUTE);
                startTimeText.setText(startHour + ":" + startMinute);
                startTimeDg = new TimePickerDialog(AddActivityStudent.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // TODO Auto-generated method stub

                        /*startTimeText.setText(hourOfDay + ":" + minute);*/
                            /*aL.set(index, hourOfDay + ":" + minute);*/

                                startCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                startCalendar.set(Calendar.MINUTE, minute);
                        /*if (startCalendar.getTime().before(Calendar.getInstance().getTime())) {
                            startHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                            endHour = Calendar.getInstance().get(Calendar.MINUTE);
                            SmartClassUtil.showToast(context, "Select valid time");
                        } else {*/
                                startHour = hourOfDay;
                                startMinute = minute;
/*
                        }
*/
                                startTimeText.setText(startHour + ":" + startMinute);
                            }

                        }, startHour, startMinute, true);// Yes 24 hour time
                startTimeDg.setTitle(timeTitle);

                if (language.equalsIgnoreCase("sw")) {
                    startTimeDg.setTitle("Välj tid");
                    startTimeDg.setButton(DatePickerDialog.BUTTON_POSITIVE, "Klar", startTimeDg);
                    startTimeDg.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Avbryt", startTimeDg);
                }else{
                    startTimeDg.setTitle("Select Time");
                    startTimeDg.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ok", startTimeDg);
                    startTimeDg.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancel", startTimeDg);
                }
//                startTimeDg.show();


                enddateCalendar.add(Calendar.HOUR_OF_DAY, 1);
                endHour = enddateCalendar.get(Calendar.HOUR_OF_DAY);
                endMinute = enddateCalendar.get(Calendar.MINUTE);
                endTimeText.setText(endHour + ":" + endMinute);


                endTimeDg = new TimePickerDialog(AddActivityStudent.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                enddateCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                enddateCalendar.set(Calendar.MINUTE, minute);
                                // TODO Auto-generated method stub
                                if (enddateCalendar.getTime().before(startCalendar.getTime())) {
                            /*aL.set(index, hourOfDay + ":" + minute);*/
                                    endHour = startHour + 1;
                                    endMinute = startMinute;
                                    SmartClassUtil.showToast(context, "Select valid time");
                                } else {
                                    endHour = hourOfDay;
                                    endMinute = minute;
                                }
                                endTimeText.setText(endHour + ":" + endMinute);

                            }

                        }, endHour, endMinute, true);// Yes 24 hour time
                endTimeDg.setTitle(timeTitle);

                if (language.equalsIgnoreCase("sw")) {
                    endTimeDg.setTitle("Välj tid");
                    endTimeDg.setButton(DatePickerDialog.BUTTON_POSITIVE, "Klar", endTimeDg);
                    endTimeDg.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Avbryt", endTimeDg);
                }else{
                    endTimeDg.setTitle("Select Time");
                    endTimeDg.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ok", endTimeDg);
                    endTimeDg.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancel", endTimeDg);
                }
//                endTimeDg.show();
            /*mTimePicker.show();*/


            }

        }catch (Exception e)
        {
            AppLog.handleException(Tag,e);
        }
    }

    public void checkValidation()
    {




        scheduleTitle = scheduleEditText.getText().toString().trim();
        description = descripEditText.getText().toString().trim();



       /* if (courseId.length() == 0 || ("0").equalsIgnoreCase(courseId)) {
            if (language.equals("en")) {
                SmartClassUtil.showToast(context, "Select course");

            } else {
                SmartClassUtil.showToast(context, "Välj kurs");

            }

        } else*/

       if (scheduleTitle.length() == 0) {
            if (language.equals("en")) {
                SmartClassUtil.showToast(context, "Enter vaild schedule title");
            } else {
                SmartClassUtil.showToast(context, "Ange vaild schema titel");
            }
        }
        else
        {
            callAddActivityApi();
        }


    }


    private void callAddActivityApi() {
        try {


            if (SmartClassUtil.isNetworkAvailable(context)) {

                    /*callAspectsApi();*/

                reqJsonObj = new JSONObject();
                reqJsonObj.put(Const.Params.SecurityKey, securityKey);
                reqJsonObj.put(Const.Params.LoginUserId, userId);
                reqJsonObj.put(Const.Params.Language, language);
                reqJsonObj.put(Const.Params.FROM_DATE,startDateVal+" "+startTimeText.getText().toString());
                reqJsonObj.put(Const.Params.TO_DATE,endDateVal+" "+endTimeText.getText().toString());
                reqJsonObj.put(Const.Params.SCHEDULETYPE,"own_booking");
                reqJsonObj.put(Const.Params.Title,scheduleTitle);
                reqJsonObj.put(Const.Params.Description,description);
                reqJsonObj.put(Const.Params.ID,"");
                JSONArray jsonArray=new JSONArray();
                reqJsonObj.put(Const.Params.childrenList,childList);
                dialogLoading.show();

                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(new ToStringConverterFactory())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqJsonObj.toString());
                Call<String> response = retrofit.create(API.class).addactivityforStudent(body);
                response.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        dialogLoading.dismiss();
                        try {
                            if (response.body() != null) {
                                String activityResponse = response.body();
                                JSONObject activityJson = new JSONObject();

                                if (activityResponse != null && activityResponse.trim().length() != 0) {

                                    activityJson = new JSONObject(activityResponse);
                                    if (activityJson.getString(Const.Params.Status).equalsIgnoreCase(Const.Params.TRUE)) {
                                        refresh = true;
                                        if (activityJson.has(Const.Params.MSG)) {
                                            SmartClassUtil.showToast(context, activityJson.getString(Const.Params.MSG));
                                        }

                                        Intent mainIntent = new Intent();
                                        mainIntent.putExtra(Const.CommonParams.REFRESH, refresh);
                                        setResult(RESULT_OK, mainIntent);
                                        finish();
                                    } else {
                                        if (activityJson.has(Const.Params.MSG)) {
                                            SmartClassUtil.showToast(context, activityJson.getString(Const.Params.MSG));
                                        }
                                    }


                                } else {

                                }

                            }
                        } catch (Exception e) {
                            AppLog.handleException(Tag, e);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        dialogLoading.dismiss();
                    }
                });

            } else {
                SmartClassUtil.showToast(context, language);

            }


        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            Intent mainIntent = new Intent();
            mainIntent.putExtra(Const.CommonParams.REFRESH, refresh);
            setResult(RESULT_OK, mainIntent);
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
    }
}
