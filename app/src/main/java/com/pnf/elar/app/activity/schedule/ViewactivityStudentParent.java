package com.pnf.elar.app.activity.schedule;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.elar.util.SmartClassUtil;
import com.pnf.elar.app.Bo.schedule.ScheduleMonthList;
import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.R;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.API;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.ToStringConverterFactory;
import com.pnf.elar.app.util.AppLog;
import com.pnf.elar.app.util.Const;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

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

public class ViewactivityStudentParent extends AppCompatActivity {
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


    @Bind(R.id.endLineView)
    View endLineView;
    @Bind(R.id.startLineView)
    View startLineView;

    UserSessionManager session;
    HashMap<String, String> user;
    String language = "", auth_token = "", baseUrl = "", userId = "", securityKey = "H67jdS7wwfh";
    Context context;
    String schemaId = "";

    MyCustomProgressDialog dialogLoading;
    String tag = "View Activity Student";
    JSONObject reqJsonObj;
    String scheduleTitle,description;
    JSONArray childList=new JSONArray();

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
    String selectedDate;
    String timeTitle = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
    String sw_endDateError = "Välj giltigt slutdatum";
    String en_endDateError = "Select valid end date";
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

    public void initView() {
        childrenHeaderText.setVisibility(View.GONE);
        childrenRv.setVisibility(View.GONE);
        dialogLoading = new MyCustomProgressDialog(ViewactivityStudentParent.this);
        dialogLoading.setIndeterminate(true);
        dialogLoading.setCancelable(false);


        saveImage.setVisibility(View.VISIBLE);
        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleTitle = scheduleEditText.getText().toString();
                description = descripEditText.getText().toString();
                if(scheduleTitle.isEmpty()){
                    SmartClassUtil.showToast(context, "Please Enter Schedule Title");
                }else if(description.isEmpty()){
                    SmartClassUtil.showToast(context, "Please Enter Description");
                }else{
                    callEditActivityApi();
                }

            }
        });
//        scheduleEditText.setEnabled(false);
//        descripEditText.setEnabled(false);
//        startDateText.setEnabled(false);
//        endDateText.setEnabled(false);
        childrenRv.setVisibility(View.GONE);
        childrenHeaderText.setVisibility(View.GONE);
        startTimeHeaderText.setVisibility(View.GONE);
        startTimeHeaderText.setVisibility(View.GONE);
        endTimeHeaderText.setVisibility(View.GONE);
        endTimeHeaderText.setVisibility(View.GONE);
        startLineView.setVisibility(View.GONE);
        endLineView.setVisibility(View.GONE);
        startCalendar = Calendar.getInstance();
        enddateCalendar = Calendar.getInstance();
            /*setOnClickListeners();*/
        setLanguage();
    }

    public void getSessionValue() {
        try {
            context = getApplicationContext();
            session = new UserSessionManager(this);
            user = session.getUserDetails();
            language = user.get(UserSessionManager.TAG_language);
            auth_token = user.get(UserSessionManager.TAG_Authntication_token);
            baseUrl = user.get(UserSessionManager.TAG_Base_url);
            userId = user.get(UserSessionManager.TAG_user_id);
            schemaId = getIntent().getStringExtra(Const.CommonParams.SCHEMAID);
/*
            childId=user.get(UserSessionManager.TAG_child_id);
*/

            /*SmartClassUtil.PrintMessage("child ids : "+childId);*/


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLanguage() {
        try {

            if (language.equalsIgnoreCase("en")) {
                headerNameText.setText("View Activity");
                startHeaderText.setText("Start Date");
                endDateHeaderText.setText("End Date");
                startTimeHeaderText.setText("Start Time");
                endTimeHeaderText.setText("End Time");
                scheduleText.setText("Schedule Title");
                descripText.setText("Description");
                childrenHeaderText.setText("Childrens");

            } else {
                startHeaderText.setText("Start datum");
                endDateHeaderText.setText("Slutdatum");
                startTimeHeaderText.setText("Starttid");
                endTimeHeaderText.setText("Sluttid");
                headerNameText.setText("Visa aktivitet");
                scheduleText.setText("Titel");
                descripText.setText("Beskrivning");
                childrenHeaderText.setText("Barns");

            }


            callViewActivityApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.backbtnImage,R.id.startDateText, R.id.endDateText, R.id.startTimeText, R.id.endTimeText})
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.backbtnImage:
                onBackPressed();
                break;
            case R.id.startDateText:
                try {
                    startDateDg = new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            // TODO Auto-generated method stub
                            try {
                                startCalendar.set(Calendar.YEAR, year);
                                startCalendar.set(Calendar.MONTH, monthOfYear);
                                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                startDateVal = sdf.format(startCalendar.getTime());
                                startDateText.setText(sdf.format(startCalendar.getTime()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    };

                    new DatePickerDialog(ViewactivityStudentParent.this, startDateDg, startCalendar
                            .get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                            startCalendar.get(Calendar.DAY_OF_MONTH)).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.endDateText:
                try {
                    endDateDg = new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            try {
                                // TODO Auto-generated method stub
                                enddateCalendar.set(Calendar.YEAR, year);
                                enddateCalendar.set(Calendar.MONTH, monthOfYear);
                                enddateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

//                                if (enddateCalendar.getTime().before(startCalendar.getInstance().getTime())) {
//
//                                    if (language.equals("en")) {
//                                        SmartClassUtil.showToast(context, en_endDateError);
//                                    } else {
//                                        SmartClassUtil.showToast(context, sw_endDateError);
//
//                                    }
//                                    enddateCalendar.setTime(startCalendar.getTime());
//
//
//                                }

                                endDateVal = sdf.format(enddateCalendar.getTime());
                                endDateText.setText(sdf.format(enddateCalendar.getTime()));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    };
                    new DatePickerDialog(ViewactivityStudentParent.this, endDateDg, enddateCalendar
                            .get(Calendar.YEAR), enddateCalendar.get(Calendar.MONTH),
                            enddateCalendar.get(Calendar.DAY_OF_MONTH)).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.startTimeText:
                startTimeDg = new TimePickerDialog(ViewactivityStudentParent.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // TODO Auto-generated method stub

                        /*startTimeText.setText(hourOfDay + ":" + minute);*/
                            /*aL.set(index, hourOfDay + ":" + minute);*/

                                startCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                startCalendar.set(Calendar.MINUTE, minute);
                                startCalendar.set(Calendar.SECOND, 0);
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
                                startTimeText.setText(sdfTime.format(startCalendar.getTime()));
                            }

                        }, startHour, startMinute, true);// Yes 24 hour time
                startTimeDg.setTitle(timeTitle);
                startTimeDg.show();
                break;
            case R.id.endTimeText:
                endTimeDg = new TimePickerDialog(ViewactivityStudentParent.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                enddateCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                enddateCalendar.set(Calendar.MINUTE, minute);
                                enddateCalendar.set(Calendar.SECOND, 0);
                                // TODO Auto-generated method stub
//                                if (enddateCalendar.getTime().before(startCalendar.getTime())) {
//                            /*aL.set(index, hourOfDay + ":" + minute);*/
//                                    endHour = startHour + 1;
//                                    endMinute = startMinute;
//                                    SmartClassUtil.showToast(context, "Select valid time");
//                                } else {
//                                    endHour = hourOfDay;
//                                    endMinute = minute;
//                                }
                                endTimeText.setText(sdfTime.format(enddateCalendar.getTime()));

                            }

                        }, endHour, endMinute, true);// Yes 24 hour time
                endTimeDg.setTitle(timeTitle);
                endTimeDg.show();
                break;
            default:
                break;


        }
    }

    private void callViewActivityApi() {
        try {
            dialogLoading.show();

            if (SmartClassUtil.isNetworkAvailable(context)) {

                    /*callAspectsApi();*/

                JSONObject reqJsonObj = new JSONObject();
                reqJsonObj.put(Const.Params.SecurityKey, securityKey);
                reqJsonObj.put(Const.Params.LoginUserId, userId);
                reqJsonObj.put(Const.Params.Language, language);
                reqJsonObj.put(Const.Params.SCHEMAID, schemaId);

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
                Call<ScheduleMonthList.SchemaCalendarEntity> response = retrofit.create(API.class).getSchemadetailsforstudent(body);
                response.enqueue(new Callback<ScheduleMonthList.SchemaCalendarEntity>() {
                    @Override
                    public void onResponse(Call<ScheduleMonthList.SchemaCalendarEntity> call, Response<ScheduleMonthList.SchemaCalendarEntity> response) {
                        dialogLoading.dismiss();
                        try {
                            if (response.body() != null) {
                                ScheduleMonthList.SchemaCalendarEntity schemaCalendarEntity = response.body();
                                JSONObject activityJson = new JSONObject();

                                if (schemaCalendarEntity.getStatus().equalsIgnoreCase(Const.Params.TRUE)) {
                                    String start = schemaCalendarEntity.getSchema().getStart();
                                    String startTimeDate[] = start.split("\\s+");
                                    String end = schemaCalendarEntity.getSchema().getEnd();
                                    String endTimeDate[] = end.split("\\s+");
                                    startDateText.setText(startTimeDate[0]);
                                    startTimeText.setText(startTimeDate[1]);
                                    endDateText.setText(endTimeDate[0]);
                                    endTimeText.setText(endTimeDate[1]);
                                    descripEditText.setText(Html.fromHtml(schemaCalendarEntity.getSchema().getDescription()));
                                    scheduleEditText.setText(Html.fromHtml(schemaCalendarEntity.getSchema().getTitle()));

                                } else {

                                }
                            }
                        } catch (Exception e) {
                            AppLog.handleException(tag, e);
                        }
                    }

                    @Override
                    public void onFailure(Call<ScheduleMonthList.SchemaCalendarEntity> call, Throwable t) {
                        dialogLoading.dismiss();
                    }
                });

            } else {
                SmartClassUtil.showToast(context, language);

            }


        } catch (Exception e) {
            AppLog.handleException(tag, e);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            finish();
        } catch (Exception e) {
            AppLog.handleException(tag, e);
        }
    }

    private void callEditActivityApi() {
        try {


            if (SmartClassUtil.isNetworkAvailable(context)) {

                    /*callAspectsApi();*/

                scheduleTitle = scheduleEditText.getText().toString();
                description = descripEditText.getText().toString();
                reqJsonObj = new JSONObject();
                reqJsonObj.put(Const.Params.SecurityKey, securityKey);
                reqJsonObj.put(Const.Params.LoginUserId, userId);
                reqJsonObj.put(Const.Params.Language, language);
                reqJsonObj.put(Const.Params.FROM_DATE,startDateText.getText().toString()+" "+startTimeText.getText().toString());
                reqJsonObj.put(Const.Params.TO_DATE,endDateText.getText().toString()+" "+endTimeText.getText().toString());
                reqJsonObj.put(Const.Params.SCHEDULETYPE,"own_booking");
                reqJsonObj.put(Const.Params.Title,scheduleTitle);
                reqJsonObj.put(Const.Params.Description,description);
                reqJsonObj.put(Const.Params.ID,schemaId);
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
                                    if(activityJson.getString(Const.Params.Status).equalsIgnoreCase(Const.Params.TRUE)) {
                                        refresh = true;
                                        Intent mainIntent = new Intent();
                                        mainIntent.putExtra(Const.CommonParams.REFRESH, refresh);
                                        setResult(RESULT_OK, mainIntent);
                                        dialogLoading.dismiss();
                                        finish();
                                        SmartClassUtil.showToast(context, activityJson.getString(Const.Params.MSG));
                                    }else{
                                        SmartClassUtil.showToast(context, activityJson.getString(Const.Params.Message));
                                    }

                                } else {
                                    SmartClassUtil.showToast(context, "Service Failed");
                                }

                            }
                        } catch (Exception e) {
                            AppLog.handleException(tag, e);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        t.printStackTrace();
                        dialogLoading.dismiss();
                    }
                });

            } else {
                SmartClassUtil.showToast(context, language);

            }


        } catch (Exception e) {
            AppLog.handleException(tag, e);
        }
    }


}