package com.pnf.elar.app.activity.schedule;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.elar.util.SmartClassUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.Streams;
import com.pnf.elar.app.Bo.schedule.ActivityGroupCourseBo;
import com.pnf.elar.app.Bo.schedule.AspectResponseBo;
import com.pnf.elar.app.Bo.schedule.CourseBean;
import com.pnf.elar.app.Bo.schedule.FoodMenuBo;
import com.pnf.elar.app.Bo.schedule.StudentsStudyPartnerBo;
import com.pnf.elar.app.Bo.schedule.StudentsUserEntity;
import com.pnf.elar.app.Bo.schedule.UserCoursesBo;
import com.pnf.elar.app.Bo.schedule.ViewActivityBo;
import com.pnf.elar.app.Drawer;
import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.R;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.API;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.ToStringConverterFactory;
import com.pnf.elar.app.adapter.schedule.AspectAdapter;
import com.pnf.elar.app.adapter.schedule.CourseAdapter;
import com.pnf.elar.app.adapter.schedule.GroupAdapter;
import com.pnf.elar.app.adapter.schedule.StuStudyPartnerAdapter;
import com.pnf.elar.app.adapter.schedule.TypeAdapter;
import com.pnf.elar.app.util.AppLog;
import com.pnf.elar.app.util.Const;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

public class UpdateActivity extends AppCompatActivity {

    @Bind(R.id.backbtnImage)
    ImageView backbtnImage;
    @Bind(R.id.headerNameText)
    TextView headerNameText;
    @Bind(R.id.saveImage)
    ImageView saveImage;
    @Bind(R.id.deleteImage)
    ImageView deleteImage;
    @Bind(R.id.addCourseSp)
    Spinner addCourseSp;
    @Bind(R.id.dropdownImage1)
    ImageView dropdownImage1;
    @Bind(R.id.addGroupSp)
    Button addGroupSp;
    @Bind(R.id.dropdownImage2)
    ImageView dropdownImage2;
    @Bind(R.id.addScheduleTypeSp)
    Spinner addScheduleTypeSp;
    @Bind(R.id.scheduleTypeLayout)
    RelativeLayout scheduleTypeLayout;
    @Bind(R.id.dropdownImage3)
    ImageView dropdownImage3;
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
    @Bind(R.id.scheduleText)
    TextView scheduleText;
    @Bind(R.id.scheduleEditText)
    EditText scheduleEditText;
    @Bind(R.id.descripText)
    TextView descripText;
    @Bind(R.id.descripEditText)
    EditText descripEditText;
    @Bind(R.id.aspetHeaderText)
    TextView aspetHeaderText;
    @Bind(R.id.aspectsRv)
    RecyclerView aspectsRv;
    @Bind(R.id.typeText)
    TextView typeText;
    @Bind(R.id.individualRb)
    RadioButton individualRb;
    @Bind(R.id.uniqueRb)
    RadioButton uniqueRb;
    @Bind(R.id.studyGrpRb)
    RadioButton studyGrpRb;
    @Bind(R.id.uniqueStudyGrpRb)
    RadioButton uniqueStudyGrpRb;
    @Bind(R.id.typeRadioGroup)
    RadioGroup typeRadioGroup;
    @Bind(R.id.stuPartnerText)
    TextView stuPartnerText;
    @Bind(R.id.stuPartnerRv)
    RecyclerView stuPartnerRv;
    @Bind(R.id.deadLineChk)
    CheckBox deadLineChk;
    @Bind(R.id.deadLineText)
    TextView deadLineText;
    @Bind(R.id.dateHeaderText)
    TextView dateHeaderText;
    @Bind(R.id.dateText)
    TextView dateText;
    @Bind(R.id.timeHeaderText)
    TextView timeHeaderText;
    @Bind(R.id.timeText)
    TextView timeText;
    @Bind(R.id.assignmentLayout)
    RelativeLayout assignmentLayout;
    @Bind(R.id.startEndDateLayout)
    LinearLayout startEndDateLayout;


    UserSessionManager session;
    HashMap<String, String> user;
    String language = "", auth_token = "", baseUrl = "", userId = "", securityKey = "H67jdS7wwfh";

    CourseAdapter CourseAdapter;
    TypeAdapter sheduleAdapter;
    AspectAdapter aspectAdapter;
    StuStudyPartnerAdapter stuStudyPartnerAdapter;

    List<UserCoursesBo> courseList = new ArrayList<>();
    List<CourseBean> scheduleList = new ArrayList<>();
    List<CourseBean> groupList = new ArrayList<>();
    List<AspectResponseBo.AspectsEntity> aspectList = new ArrayList<>();
    List<StudentsUserEntity> studentsStudyPartnerList = new ArrayList<>();

    /*List<StudentsUserEntity> studyPartnerList;*/


    String grp_name[];
    boolean grp_selection[];
    AlertDialog.Builder groupDialog;
    List<String> selectedGroup = new ArrayList<>();
    List<Integer> grpPos = new ArrayList<>();

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

    Calendar dateCalendar;
    DatePickerDialog.OnDateSetListener dateDg;
    TimePickerDialog dateTimeDg;
    int dateHour, dateMinute;
    String dateVal = "";

    SimpleDateFormat apiSdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String timeTitle = "";

    String courseId = "";
    String courseName = "";

    String groupIds = "";
    String scheduleType = "1";
    String scheduleTypeName = "";
    String scheduleTitle = "";
    String description = "";


    String sw_endDateError = "Välj giltigt slutdatum";
    String en_endDateError = "Select valid end date";
    String sw_startDateError = "Välj giltigt startdatum";
    String en_startDateError = "Select valid start date";
    String ownBooking = "Your own booking";
    String school = "school";

    Context context;
    String selectedDate = "";
    boolean refresh = false;

    JSONObject reqJsonObj;
    MyCustomProgressDialog dialogLoading;
    String Tag = "Activity";
    int typeRadioGrp = 0;
    Dialog dialogGroup;
    String schemaId = "";


    ViewActivityBo viewActivityBo;

    int view = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTheme(R.style.AppTheme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);
        ButterKnife.bind(this);
        context = getApplicationContext();

        try {
            getSessionValue();
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getSessionValue() {
        try {
            session = new UserSessionManager(this);
            user = session.getUserDetails();
            language = user.get(UserSessionManager.TAG_language);
            auth_token = user.get(UserSessionManager.TAG_Authntication_token);
            baseUrl = user.get(UserSessionManager.TAG_Base_url);
            userId = user.get(UserSessionManager.TAG_user_id);

            selectedDate = getIntent().getStringExtra(Const.CommonParams.SELECTED_DATE);

            schemaId = getIntent().getStringExtra(Const.CommonParams.SCHEMAID);
            dateCalendar = Calendar.getInstance();
            startCalendar = Calendar.getInstance();
            enddateCalendar = enddateCalendar.getInstance();

            /*schemaId = "949";*/


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void initView() {
        try {

            dialogLoading = new MyCustomProgressDialog(UpdateActivity.this);
            dialogLoading.setIndeterminate(true);
            dialogLoading.setCancelable(false);

           /* addCourseSp = (Spinner) findViewById(R.id.addCourseSp);
            addGroupSp = (Button) findViewById(R.id.addGroupSp);
            addScheduleTypeSp = (Spinner) findViewById(R.id.addScheduleTypeSp);
            backbtnImage = (ImageView) findViewById(R.id.backbtnImage);
            saveImage = (ImageView) findViewById(R.id.saveImage);
            headerNameText = (TextView) findViewById(R.id.headerNameText);
           startDateText = (TextView) findViewById(R.id.startDateText);
            startTimeText = (TextView) findViewById(R.id.startTimeText);
            endDateText = (TextView) findViewById(R.id.endDateText);
            endTimeText = (TextView) findViewById(R.id.endTimeText);
            scheduleText = (TextView) findViewById(R.id.scheduleText);
            scheduleEditText = (EditText) findViewById(R.id.scheduleEditText);
            descripText = (TextView) findViewById(R.id.descripText);
            descripEditText = (EditText) findViewById(R.id.descripEditText);
            startHeaderText = (TextView) findViewById(R.id.startHeaderText);
            startTimeHeaderText = (TextView) findViewById(R.id.startTimeHeaderText);
            endDateHeaderText = (TextView) findViewById(R.id.endDateHeaderText);
            endTimeHeaderText = (TextView) findViewById(R.id.endTimeHeaderText);*/

            saveImage.setVisibility(View.VISIBLE);
            /*setOnClickListeners();*/
            setLanguage();
            /*Group, Course,Schedult type*/

            /*Date and Time*/
            /*setCourseSpinner();*/


            if (SmartClassUtil.isNetworkAvailable(context)) {
                setScheduleSpinner();
                callGroupCourseApi();


            } else {
                SmartClassUtil.showToast(context, language);
            }


            /*setGroupSpinner();*/
//            setTimePicker();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setLanguage() {
        try {

            if (language.equalsIgnoreCase("en")) {
                addGroupSp.setText("Select Group");
                headerNameText.setText("Add Activity");
                startHeaderText.setText("Start Date");
                endDateHeaderText.setText("End Date");
                startTimeHeaderText.setText("Start Time");
                endTimeHeaderText.setText("End Time");
                scheduleText.setText("Schedule Title");
                descripText.setText("Description");
                timeTitle = "Select time";
                courseList.add(new UserCoursesBo(new UserCoursesBo.CouCourseEntity("0", "Select Course")));
            } else {
                addGroupSp.setText("Välj grupp");
                startHeaderText.setText("Start datum");
                endDateHeaderText.setText("Slutdatum");
                startTimeHeaderText.setText("Starttid");
                endTimeHeaderText.setText("Sluttid");
                headerNameText.setText("Skapa Bokning");
                scheduleText.setText("Titel");
                descripText.setText("Beskrivning");
                timeTitle = "Välj Tid";
                courseList.add(new UserCoursesBo(new UserCoursesBo.CouCourseEntity("0", "Välj Kurs")));
                typeText.setText("Typ");
                individualRb.setText("Individuellt");
                uniqueRb.setText("Unik");
                studyGrpRb.setText("studiegrupper");
                uniqueStudyGrpRb.setText("Unika Studiegrupper");
                dateHeaderText.setText("Datum");
                timeHeaderText.setText("Tid");

                ownBooking = "Din egen bokning";
                school = "skola";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnClickListeners() {
        try {
            setLanguage();
            /*backbtn.setOnClickListener(this);
            addGroupSp.setOnClickListener(this);
            saveImage.setOnClickListener(this);
            startDateText.setOnClickListener(this);
            endDateText.setOnClickListener(this);
            startTimeText.setOnClickListener(this);
            endTimeText.setOnClickListener(this);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.backbtnImage, R.id.addGroupSp, R.id.saveImage, R.id.startDateText, R.id.endDateText, R.id.startTimeText, R.id.endTimeText, R.id.deleteImage,R.id.timeText,R.id.dateText,R.id.dropdownImage2})
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.backbtnImage:
                onBackPressed();
                break;
            case R.id.saveImage:
                checkValidation();
                break;
            case R.id.addGroupSp:
                /*setGroupDialog();*/
                setGroup();
                break;
            case R.id.dropdownImage2:
                setGroup();
                break;
            case R.id.startDateText:

                final Context themedContext = new ContextThemeWrapper(
                        UpdateActivity.this,
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
                        new DatePickerDialog(UpdateActivity.this, startDateDg, startCalendar
                                .get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                                startCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


//                try {
//                    new DatePickerDialog(UpdateActivity.this, startDateDg, startCalendar
//                            .get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
//                            startCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                break;
            case R.id.endDateText:

                final Context themedContext1 = new ContextThemeWrapper(
                        UpdateActivity.this,
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
                        new DatePickerDialog(UpdateActivity.this, endDateDg, enddateCalendar
                                .get(Calendar.YEAR), enddateCalendar.get(Calendar.MONTH),
                                enddateCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                try {
//                    new DatePickerDialog(UpdateActivity.this, endDateDg, enddateCalendar
//                            .get(Calendar.YEAR), enddateCalendar.get(Calendar.MONTH),
//                            enddateCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                break;
            case R.id.dateText:

                final Context themedContext2 = new ContextThemeWrapper(
                        UpdateActivity.this,
                        android.R.style.Theme_Holo
                );
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        DatePickerDialog dpd = new DatePickerDialog(themedContext2, dateDg, dateCalendar
                                .get(Calendar.YEAR), dateCalendar.get(Calendar.MONTH),
                                dateCalendar.get(Calendar.DAY_OF_MONTH));
                        if (language.equalsIgnoreCase("sw")) {
                            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Klar", dpd);
                            dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Avbryt", dpd);
                        }else{
                            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ok", dpd);
                            dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancel", dpd);
                        }
                        dpd.show();

                    }else{
                        new DatePickerDialog(UpdateActivity.this, dateDg, dateCalendar
                                .get(Calendar.YEAR), dateCalendar.get(Calendar.MONTH),
                                dateCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                try {
//                    new DatePickerDialog(UpdateActivity.this, dateDg, dateCalendar
//                            .get(Calendar.YEAR), dateCalendar.get(Calendar.MONTH),
//                            dateCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                break;
            case R.id.startTimeText:
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

                    final Context themedContext3 = new ContextThemeWrapper(
                            UpdateActivity.this,
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
                    } else {
                        startTimeDg.setTitle("Select Time");
                        startTimeDg.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ok", startTimeDg);
                        startTimeDg.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancel", startTimeDg);
                    }
                    startTimeDg.show();
                }else{
                    startHour = startCalendar.get(Calendar.HOUR_OF_DAY);
                    startMinute = startCalendar.get(Calendar.MINUTE);
                    startTimeText.setText(startHour + ":" + startMinute);
                    startTimeDg = new TimePickerDialog(UpdateActivity.this,
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
                    } else {
                        startTimeDg.setTitle("Select Time");
                        startTimeDg.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ok", startTimeDg);
                        startTimeDg.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancel", startTimeDg);
                    }
                    startTimeDg.show();
                }
                break;
            case R.id.endTimeText:
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

                    final Context themedContext4 = new ContextThemeWrapper(
                            UpdateActivity.this,
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


                    endTimeDg = new TimePickerDialog(UpdateActivity.this,
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
                }
                break;
            case R.id.timeText:
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

                    final Context themedContext5 = new ContextThemeWrapper(
                            UpdateActivity.this,
                            android.R.style.Theme_Holo
                    );
                    dateHour = dateCalendar.get(Calendar.HOUR_OF_DAY);
                    dateMinute = dateCalendar.get(Calendar.MINUTE);
                    timeText.setText(dateHour + ":" + dateMinute);
                    dateTimeDg = new TimePickerDialog(themedContext5,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {
                                    // TODO Auto-generated method stub

                        /*startTimeText.setText(hourOfDay + ":" + minute);*/
                            /*aL.set(index, hourOfDay + ":" + minute);*/

                                    dateCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    dateCalendar.set(Calendar.MINUTE, minute);
                        /*if (startCalendar.getTime().before(Calendar.getInstance().getTime())) {
                            startHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                            endHour = Calendar.getInstance().get(Calendar.MINUTE);
                            SmartClassUtil.showToast(context, "Select valid time");
                        } else {*/
                                    dateHour = hourOfDay;
                                    dateMinute = minute;
/*
                        }
*/
                                    timeText.setText(dateHour + ":" + dateMinute);
                                }

                            }, dateHour, dateMinute, true);// Yes 24 hour time
                    dateTimeDg.setTitle(timeTitle);

                    if (language.equalsIgnoreCase("sw")) {
                        dateTimeDg.setTitle("Välj tid");
                        dateTimeDg.setButton(DatePickerDialog.BUTTON_POSITIVE, "Klar", dateTimeDg);
                        dateTimeDg.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Avbryt", dateTimeDg);
                    } else {
                        dateTimeDg.setTitle("Select Time");
                        dateTimeDg.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ok", dateTimeDg);
                        dateTimeDg.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancel", dateTimeDg);
                    }
                    dateTimeDg.show();
                }else{
                    dateHour = dateCalendar.get(Calendar.HOUR_OF_DAY);
                    dateMinute = dateCalendar.get(Calendar.MINUTE);
                    timeText.setText(dateHour + ":" + dateMinute);
                    dateTimeDg = new TimePickerDialog(UpdateActivity.this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {
                                    // TODO Auto-generated method stub

                        /*startTimeText.setText(hourOfDay + ":" + minute);*/
                            /*aL.set(index, hourOfDay + ":" + minute);*/

                                    dateCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    dateCalendar.set(Calendar.MINUTE, minute);
                        /*if (startCalendar.getTime().before(Calendar.getInstance().getTime())) {
                            startHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                            endHour = Calendar.getInstance().get(Calendar.MINUTE);
                            SmartClassUtil.showToast(context, "Select valid time");
                        } else {*/
                                    dateHour = hourOfDay;
                                    dateMinute = minute;
/*
                        }
*/
                                    timeText.setText(dateHour + ":" + dateMinute);
                                }

                            }, dateHour, dateMinute, true);// Yes 24 hour time
                    dateTimeDg.setTitle(timeTitle);
                    if (language.equalsIgnoreCase("sw")) {
                        dateTimeDg.setTitle("Välj tid");
                        dateTimeDg.setButton(DatePickerDialog.BUTTON_POSITIVE, "Klar", dateTimeDg);
                        dateTimeDg.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Avbryt", dateTimeDg);
                    } else {
                        dateTimeDg.setTitle("Select Time");
                        dateTimeDg.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ok", dateTimeDg);
                        dateTimeDg.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancel", dateTimeDg);
                    }
                    dateTimeDg.show();
                }
                break;
            case R.id.deleteImage:
                deleteAlert();
                break;
        }
    }

    public void deleteAlert(){
        String msg = null;
        String yes = null;
        String no = null;
        String title = null;

        if (language.equalsIgnoreCase("sw")) {
            title = " Är du säker!";
            msg = "Vill du radera?";
            yes = "Ja";
            no = "Nej";
        }
        if (language.equalsIgnoreCase("en")) {
            title = " Are you sure!";
            msg = "Do you want to delete?";
            yes = "Yes";
            no = "No";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                UpdateActivity.this);
        alertDialogBuilder
                .setMessage(msg);
        alertDialogBuilder.setTitle(title);

        alertDialogBuilder.setPositiveButton(yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface arg0, int arg1) {

                        callDeleteApi();

                    }
                });

        alertDialogBuilder.setNegativeButton(no,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which) {

                        // Intent i = new
                        // Intent(SettingPage.this,SettingPage.class);
                        // startActivity(i);
                        // finish();
                        //		closeContextMenu();

                    }
                });

       AlertDialog alertDialog = alertDialogBuilder
                .create();
        alertDialog.show();
    }

    public void setStartEndDate() {
        try {
            /*Log.e("sdsd", selectedDate);*/
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date selDate = df.parse(selectedDate);
            /*startCalendar = Calendar.getInstance();*/
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


            dateCalendar = Calendar.getInstance();
            dateCalendar.setTime(selDate);
            dateVal = sdf.format(dateCalendar.getTime());
            dateText.setText(dateVal);
            dateDg = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    try {
                        dateCalendar.set(Calendar.YEAR, year);
                        dateCalendar.set(Calendar.MONTH, monthOfYear);
                        dateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                      /*  if (startCalendar.getTime().before(Calendar.getInstance().getTime())) {
                            if (language.equals("en")) {
                                SmartClassUtil.showToast(context, en_startDateError);
                            } else {
                                SmartClassUtil.showToast(context, sw_startDateError);

                            }
                            startCalendar = Calendar.getInstance();
                        } else {
                        }*/
                        dateVal = sdf.format(dateCalendar.getTime());
                        dateText.setText(sdf.format(dateCalendar.getTime()));
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
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

                final Context themedContext = new ContextThemeWrapper(
                        UpdateActivity.this,
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

                dateHour = dateCalendar.get(Calendar.HOUR_OF_DAY);
                dateMinute = dateCalendar.get(Calendar.MINUTE);
                timeText.setText(dateHour + ":" + dateMinute);
                dateTimeDg = new TimePickerDialog(themedContext,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // TODO Auto-generated method stub

                        /*startTimeText.setText(hourOfDay + ":" + minute);*/
                            /*aL.set(index, hourOfDay + ":" + minute);*/

                                dateCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                dateCalendar.set(Calendar.MINUTE, minute);
                        /*if (startCalendar.getTime().before(Calendar.getInstance().getTime())) {
                            startHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                            endHour = Calendar.getInstance().get(Calendar.MINUTE);
                            SmartClassUtil.showToast(context, "Select valid time");
                        } else {*/
                                dateHour = hourOfDay;
                                dateMinute = minute;
/*
                        }
*/
                                timeText.setText(dateHour + ":" + dateMinute);
                            }

                        }, dateHour, dateMinute, true);// Yes 24 hour time
                dateTimeDg.setTitle(timeTitle);

                if (language.equalsIgnoreCase("sw")) {
                    dateTimeDg.setTitle("Välj tid");
                    dateTimeDg.setButton(DatePickerDialog.BUTTON_POSITIVE, "Klar", dateTimeDg);
                    dateTimeDg.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Avbryt", dateTimeDg);
                }else{
                    dateTimeDg.setTitle("Select Time");
                    dateTimeDg.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ok", dateTimeDg);
                    dateTimeDg.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancel", dateTimeDg);
                }
                dateTimeDg.show();

            } else {
                startHour = startCalendar.get(Calendar.HOUR_OF_DAY);
                startMinute = startCalendar.get(Calendar.MINUTE);
                startTimeText.setText(startHour + ":" + startMinute);
                startTimeDg = new TimePickerDialog(UpdateActivity.this,
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


                enddateCalendar.add(Calendar.HOUR_OF_DAY, 1);
                endHour = enddateCalendar.get(Calendar.HOUR_OF_DAY);
                endMinute = enddateCalendar.get(Calendar.MINUTE);
                endTimeText.setText(endHour + ":" + endMinute);


                endTimeDg = new TimePickerDialog(UpdateActivity.this,
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
            /*mTimePicker.show();*/


                dateHour = dateCalendar.get(Calendar.HOUR_OF_DAY);
                dateMinute = dateCalendar.get(Calendar.MINUTE);
                timeText.setText(dateHour + ":" + dateMinute);
                dateTimeDg = new TimePickerDialog(UpdateActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // TODO Auto-generated method stub

                        /*startTimeText.setText(hourOfDay + ":" + minute);*/
                            /*aL.set(index, hourOfDay + ":" + minute);*/

                                dateCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                dateCalendar.set(Calendar.MINUTE, minute);
                        /*if (startCalendar.getTime().before(Calendar.getInstance().getTime())) {
                            startHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                            endHour = Calendar.getInstance().get(Calendar.MINUTE);
                            SmartClassUtil.showToast(context, "Select valid time");
                        } else {*/
                                dateHour = hourOfDay;
                                dateMinute = minute;
/*
                        }
*/
                                timeText.setText(dateHour + ":" + dateMinute);
                            }

                        }, dateHour, dateMinute, true);// Yes 24 hour time
                dateTimeDg.setTitle(timeTitle);
            }
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
    }


    public void setCourseSpinner() {
        try {

          /*  courseList.add(new CourseBean("1", "Test"));
            courseList.add(new CourseBean("2", "Test 1"));
            courseList.add(new CourseBean("3", "Test 2"));*/
            CourseAdapter = new CourseAdapter(UpdateActivity.this, R.layout.item_course_spinner, courseList);
            // Set adapter to spinner
            addCourseSp.setAdapter(CourseAdapter);
            // Listener called when spinner item selected
            addCourseSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                    // your code here
                    ImageView img = (ImageView) v.findViewById(R.id.dropdownImage1);
                    img.setVisibility(View.VISIBLE);
                    String str_course_id = ((TextView) v.findViewById(R.id.idText)).getText().toString();
                    String crsName = ((TextView) v.findViewById(R.id.nameText)).getText().toString();
                    SmartClassUtil.PrintMessage("Course Id :: " + str_course_id);
                    courseId = str_course_id;
                    courseName = crsName;
                    if (!("0").equalsIgnoreCase(str_course_id)) {

                        if (courseId.trim().length() > 0)
                            callAspectsApi();
                    } else {
                        if (("0").equalsIgnoreCase(courseId)) {
                            courseId = "0";
                            courseName = "";
                            aspectList.clear();
                            aspectAdapter.notifyDataSetChanged();
                        }

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setScheduleSpinner() {
        try {

            aspectAdapter = new AspectAdapter(context, aspectList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            aspectsRv.setLayoutManager(layoutManager);
            aspectsRv.setAdapter(aspectAdapter);

            if (("en").equalsIgnoreCase(language)) {
                /*scheduleList.add(new CourseBean("0", "Select schedule type"));*/
                scheduleList.add(new CourseBean("1", "Lesson"));
                scheduleList.add(new CourseBean("2", "Activity "));
                scheduleList.add(new CourseBean("3", "Examination"));
                scheduleList.add(new CourseBean("4", "Assignment"));
            } else {
                /*scheduleList.add(new CourseBean("0", "Välj schematyp"));*/
                scheduleList.add(new CourseBean("1", "Lektion"));
                scheduleList.add(new CourseBean("2", "Aktivitet "));
                scheduleList.add(new CourseBean("3", "Undersökning"));
                scheduleList.add(new CourseBean("4", "Uppdrag"));
            }
            sheduleAdapter = new TypeAdapter(UpdateActivity.this, R.layout.item_course_spinner, scheduleList);
            // Set adapter to spinner
            addScheduleTypeSp.setAdapter(sheduleAdapter);
            // Listener called when spinner item selected
            addScheduleTypeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {

                    ImageView img = (ImageView) v.findViewById(R.id.dropdownImage1);
                    img.setVisibility(View.VISIBLE);
                    // your code here
                    String strScheduleId = ((TextView) v.findViewById(R.id.idText)).getText().toString();
                    SmartClassUtil.PrintMessage("Course Id :: " + strScheduleId);
                    scheduleType = strScheduleId;
                    if (("1").equalsIgnoreCase(strScheduleId) || ("2").equalsIgnoreCase(strScheduleId)) {
                        showAspect(false);
                        assignmentLayout.setVisibility(View.GONE);
                        startEndDateLayout.setVisibility(View.VISIBLE);
                    } else if (("4").equalsIgnoreCase(strScheduleId)) {
                        showAspect(true);
                        assignmentLayout.setVisibility(View.VISIBLE);
                        startEndDateLayout.setVisibility(View.GONE);
                    } else if (("3").equalsIgnoreCase(strScheduleId)) {
                        assignmentLayout.setVisibility(View.GONE);
                        startEndDateLayout.setVisibility(View.VISIBLE);
                        showAspect(true);
                    } else {
                        assignmentLayout.setVisibility(View.GONE);
                        startEndDateLayout.setVisibility(View.VISIBLE);
                        scheduleType = "";
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAspect(boolean showAspect) {
        try {

            if (showAspect) {


                if (!("0").equalsIgnoreCase(courseId)) {

                } else {

                    String erCourse = "Please select course";
                    if (language.equalsIgnoreCase("sw")) {
                        erCourse = "Välj kurs";
                    }
                /*addScheduleTypeSp.setSelection(0);*/
                    SmartClassUtil.showToast(context, erCourse);

                }

                aspetHeaderText.setVisibility(View.VISIBLE);
                aspectsRv.setVisibility(View.VISIBLE);


            } else {
                aspetHeaderText.setVisibility(View.GONE);
                aspectsRv.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
    }

    public void setGroupSpinner() {
        try {
            stuStudyPartnerAdapter = new StuStudyPartnerAdapter(context, studentsStudyPartnerList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            stuPartnerRv.setLayoutManager(layoutManager);
            stuPartnerRv.setAdapter(stuStudyPartnerAdapter);
          /*  groupList.add(new CourseBean("1", "Test", false));
            groupList.add(new CourseBean("2", "Test 1", false));
            groupList.add(new CourseBean("3", "Test 2", false));*/
            grp_name = new String[groupList.size()];
            grp_selection = new boolean[groupList.size()];

            for (int i = 0; i < groupList.size(); i++) {

                grp_name[i] = groupList.get(i).getName();
                grp_selection[i] = groupList.get(i).isSelected();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {


            typeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {


                    switch (i) {
                        case R.id.individualRb:
                            typeRadioGrp = 1;
                            show(false);
                            break;
                        case R.id.uniqueRb:
                            typeRadioGrp = 2;
                            show(true);
                            if (language.equalsIgnoreCase("en")) {
                                stuPartnerText.setText("Students");
                            } else {
                                stuPartnerText.setText("studenter");
                            }
                            if (!selectedGroup.isEmpty())
                                callStudentsStudyPartnerAPi();
                            break;
                        case R.id.studyGrpRb:
                            typeRadioGrp = 3;
                            show(false);
                            break;
                        case R.id.uniqueStudyGrpRb:
                            typeRadioGrp = 4;
                            show(true);
                            if (language.equalsIgnoreCase("en")) {
                                stuPartnerText.setText("Study Partners");
                            } else {
                                stuPartnerText.setText("studie Partners");
                            }
                            if (!selectedGroup.isEmpty())
                                callStudentsStudyPartnerAPi();
                            break;
                        default:
                            break;


                    }

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }




/*
        groupAdapter = new GroupAdapter(UpdateActivity.this, R.layout.item_course_spinner, groupList);
*/


        /*setGroup();*/
       /* // Set adapter to spinner
        addGroupSp.setAdapter(groupAdapter);

        // Listener called when spinner item selected
        addGroupSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                // your code here
                String str_course_id = ((TextView) v.findViewById(R.id.idText)).getText().toString();


                SmartClassUtil.PrintMessage("Course Id :: " + str_course_id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });*/
    }

    public void show(boolean show) {
        try {

            if (show) {
                stuPartnerRv.setVisibility(View.VISIBLE);
                stuPartnerText.setVisibility(View.VISIBLE);


            } else {
                stuPartnerRv.setVisibility(View.GONE);
                stuPartnerText.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
    }


    public void setGroup() {
        try {
            dialogGroup = new Dialog(UpdateActivity.this);
            dialogGroup.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
            dialogGroup.setContentView(R.layout.dialog_activitygroup);
            dialogGroup.setCancelable(true);
            dialogGroup.setCanceledOnTouchOutside(true);
            TextView titlegrpText = (TextView) dialogGroup.findViewById(R.id.titlegrpText);
            Button grpOkbtn = (Button) dialogGroup.findViewById(R.id.grpOkbtn);
            Button grpCancelbtn = (Button) dialogGroup.findViewById(R.id.grpCancelbtn);

            String title, ok, cancel;

            if (language.equalsIgnoreCase("en")) {
            /*titlegrpText.setText("Select Group");*/
                title = "Groups..";
                ok = "Ok";
                cancel = "Cancel";
            } else {
                title = "grupper..";
                ok = "Okej";
                cancel = "Avbryt";
            }


            titlegrpText.setText(title);
            grpOkbtn.setText(ok);
            grpCancelbtn.setText(cancel);


            RecyclerView grpRv = (RecyclerView) dialogGroup
                    .findViewById(R.id.grpRv);

            final GroupAdapter groupAdapter = new GroupAdapter(context, groupList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            grpRv.setLayoutManager(layoutManager);
            grpRv.setAdapter(groupAdapter);


            final List<CourseBean> finalGrpList = new ArrayList<>();

            finalGrpList.addAll(groupList);
            for (int i = 0; i < grpPos.size(); i++) {

                groupList.get(grpPos.get(i)).setSelected(true);

            }


            grpOkbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        selectedGroup.clear();
                        grpPos.clear();

                        scheduleTypeLayout.setVisibility(View.VISIBLE);


                        for (int i = 0; i < groupList.size(); i++) {
                            if (groupList.get(i).isSelected() == true) {
                                selectedGroup.add(groupList.get(i).getId());
                                grpPos.add(i);
                            }
                        }

                        if (selectedGroup.size() == 0) {
                            if (language.equalsIgnoreCase("en")) {
                                addGroupSp.setText("Select group");
                            } else {
                                addGroupSp.setText("Välj grup");
                            }
                        } else if (selectedGroup.size() == 1) {
                            if (language.equalsIgnoreCase("en")) {
                                addGroupSp.setText("1 Group ");
                            } else {
                                addGroupSp.setText("1 Grupp ");
                            }


                            if (selectedGroup.get(0).equalsIgnoreCase("-1")) {
                                scheduleTypeLayout.setVisibility(View.GONE);
                                assignmentLayout.setVisibility(View.GONE);
                                startEndDateLayout.setVisibility(View.VISIBLE);
                                aspetHeaderText.setVisibility(View.GONE);
                                aspectsRv.setVisibility(View.GONE);

                            } else {
                                showScheduleTypeByGroup();
                                callStudentsStudyPartnerAPi();
                            }
                        } else if (selectedGroup.size() > 1) {
                            showScheduleTypeByGroup();
                            if (language.equalsIgnoreCase("en")) {
                                addGroupSp.setText(selectedGroup.size() + " Groups ");
                            } else {
                                addGroupSp.setText(selectedGroup.size() + " grupper ");
                            }
                            callStudentsStudyPartnerAPi();
                        }


                        dialogGroup.dismiss();
                    } catch (Exception e) {
                        AppLog.handleException(Tag, e);
                    }

                }
            });

            grpCancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogGroup.dismiss();

                }
            });

            dialogGroup.show();
        /*grpList.setAdapter(groupAdapter);*/
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
    }

    public void showScheduleTypeByGroup() {
        try {
            if (scheduleType.equalsIgnoreCase("1") || scheduleType.equalsIgnoreCase("2")) {

                assignmentLayout.setVisibility(View.GONE);
                startEndDateLayout.setVisibility(View.VISIBLE);
                aspetHeaderText.setVisibility(View.GONE);
                aspectsRv.setVisibility(View.GONE);

            } else if (scheduleType.equalsIgnoreCase("3")) {
                assignmentLayout.setVisibility(View.GONE);
                startEndDateLayout.setVisibility(View.VISIBLE);
                aspetHeaderText.setVisibility(View.VISIBLE);
                aspectsRv.setVisibility(View.VISIBLE);
            } else if (scheduleType.equalsIgnoreCase("4")) {
                assignmentLayout.setVisibility(View.VISIBLE);
                startEndDateLayout.setVisibility(View.GONE);
                aspetHeaderText.setVisibility(View.VISIBLE);
                aspectsRv.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }

    }

    /*  public void setGroupDialog() {
          try {


              String title = "", ok = "", Cancel = "";

              if (language.equalsIgnoreCase("sw")) {
                  title = "grupper..";
                  ok = "Okej";
                  Cancel = "Avbryt";
              } else {

              }

              groupDialog = new AlertDialog.Builder(UpdateActivity.this);
              groupDialog.setTitle(title);
              groupDialog.setMultiChoiceItems(grp_name, grp_selection,
                      new DialogInterface.OnMultiChoiceClickListener() {

                          @Override
                          public void onClick(DialogInterface arg0, int arg1,
                                              boolean arg2) {
                              // TODO Auto-generated method stub
                              AppLog.Log("check pos", arg1 + "");
                              if (groupList.get(arg1).getId().equalsIgnoreCase("-1")) {
                                  grp_selection = new boolean[groupList.size()];
                                  for (int i = 0; i < groupList.size(); i++) {
                                      grp_selection[i] = false;
                                  }
                                  grp_selection[arg1] = true;


                              }
                          }
                      });
              groupDialog.setPositiveButton(ok, new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      // TODO Auto-generated method stub
                      getSelectedGroup();
                  } // //okej //// Avbryt

                  // //////////////////////////////////////////////////////////////////////////////////////
                  private void getSelectedGroup() {
                      // TODO Auto-generated method stub
                      try {

                          selectedGroup.clear();
                          grpPos.clear();
                          for (int i = 0; i < grp_selection.length; i++) {
                              if (grp_selection[i] == true) {
                                  selectedGroup.add(groupList.get(i).getId());
                                  grpPos.add(i);
                              }
                          }

                          SmartClassUtil.PrintMessage(new JSONArray(Arrays.asList(selectedGroup)).toString());

                          if (selectedGroup.size() == 0) {
                              if (language.equalsIgnoreCase("en")) {
                                  addGroupSp.setText("Select group");
                              } else {
                                  addGroupSp.setText("Välj grup");
                              }
                          } else if (selectedGroup.size() == 1) {
                              if (language.equalsIgnoreCase("en")) {
                                  addGroupSp.setText("1 Group ");
                              } else {
                                  addGroupSp.setText("1 Grupp ");
                              }

                              callStudentsStudyPartnerAPi();
                          } else if (selectedGroup.size() > 1) {
                              if (language.equalsIgnoreCase("en")) {
                                  addGroupSp.setText(selectedGroup.size() + " Groups ");
                              } else {
                                  addGroupSp.setText(selectedGroup.size() + " grupper ");
                              }
                              callStudentsStudyPartnerAPi();
                          }

                      } catch (Exception e) {
                          e.printStackTrace();
                      }
                  }
              });
              groupDialog.setNegativeButton(Cancel,
                      new DialogInterface.OnClickListener() {

                          @Override
                          public void onClick(DialogInterface dialog,
                                              int which) {
                              // TODO Auto-generated method stub
                              try {
                                  grp_name = new String[groupList.size()];
                                  grp_selection = new boolean[groupList.size()];
                                  for (int i = 0; i < groupList.size(); i++) {
                                      grp_name[i] = groupList.get(i).getName();
                                      grp_selection[i] = false;
                                  }
                                  for (int i = 0; i < grpPos.size(); i++) {
                                      grp_selection[grpPos.get(i)] = true;
                                  }
                                  SmartClassUtil.PrintMessage(new JSONArray(Arrays.asList(selectedGroup)).toString());
                                  dialog.dismiss();
                              } catch (Exception e) {
                                  e.printStackTrace();
                              }

                          }
                      });

              groupDialog.show();
          } catch (Exception e) {
              e.printStackTrace();
          }

      }

  */
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


    private void callGroupCourseApi() {
        try {
            reqJsonObj = new JSONObject();
            reqJsonObj.put(Const.Params.SecurityKey, securityKey);
            reqJsonObj.put(Const.Params.LoginUserId, userId);
            reqJsonObj.put(Const.Params.Language, language);
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
            Call<ActivityGroupCourseBo> response = retrofit.create(API.class).getCoursesAndGroups(body);
            response.enqueue(new Callback<ActivityGroupCourseBo>() {
                @Override
                public void onResponse(Call<ActivityGroupCourseBo> call, Response<ActivityGroupCourseBo> response) {
                    dialogLoading.dismiss();
                    if (response.body() != null) {
                        ActivityGroupCourseBo activityGroupCourseBo = response.body();
                        if (activityGroupCourseBo.getStatus().equalsIgnoreCase(Const.Params.TRUE)) {

                            if (activityGroupCourseBo.getUsercourses() != null) {

                                courseList.addAll(activityGroupCourseBo.getUsercourses());
                            }
                            if (activityGroupCourseBo.getGroups() != null) {
                                groupList.addAll(activityGroupCourseBo.getGroups());
                                groupList.add(new CourseBean("-1", ownBooking, false));
                                groupList.add(new CourseBean("-2", school, false));


                            }
                            setCourseSpinner();
                            setGroupSpinner();
                            callViewActivityApi();
                        } else {
                            SmartClassUtil.showToast(context, activityGroupCourseBo.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ActivityGroupCourseBo> call, Throwable t) {
                    dialogLoading.dismiss();
                }
            });
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
    }


    private void callAspectsApi() {
        try {


            if (SmartClassUtil.isNetworkAvailable(context)) {

                    /*callAspectsApi();*/

                reqJsonObj = new JSONObject();
                reqJsonObj.put(Const.Params.SecurityKey, securityKey);
                reqJsonObj.put(Const.Params.LoginUserId, userId);
                reqJsonObj.put(Const.Params.COURSE_ID, courseId);
                reqJsonObj.put(Const.Params.Language, language);
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
                Call<AspectResponseBo> response = retrofit.create(API.class).getAspectsList(body);
                response.enqueue(new Callback<AspectResponseBo>() {
                    @Override
                    public void onResponse(Call<AspectResponseBo> call, Response<AspectResponseBo> response) {
                        dialogLoading.dismiss();
                        if (response.body() != null) {
                            aspectList.clear();
                            AspectResponseBo aspectResponseBo = response.body();
                            if (aspectResponseBo.getStatus().equalsIgnoreCase(Const.Params.TRUE)) {
                                for (int i = 0; i < aspectResponseBo.getAspects().size(); i++) {
                                    AspectResponseBo.AspectsEntity aspectsEntity = aspectResponseBo.getAspects().get(i);

                                    boolean checkStatus = false;
                                    if (view == 0) {
                                        checkStatus = viewAspects(aspectsEntity.getAspect().getId());

                                    }

                                    aspectsEntity.getAspect().setSelected(checkStatus);
                                    aspectList.add(aspectsEntity);
                                }

                                view = 1;
                                aspectAdapter.notifyDataSetChanged();


                            } else {
                                SmartClassUtil.showToast(context, aspectResponseBo.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AspectResponseBo> call, Throwable t) {
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

    private void callStudentsStudyPartnerAPi() {
        try {
            if (SmartClassUtil.isNetworkAvailable(context)) {

                reqJsonObj = new JSONObject();
                reqJsonObj.put(Const.Params.SecurityKey, securityKey);
                reqJsonObj.put(Const.Params.LoginUserId, userId);
                reqJsonObj.put(Const.Params.GroupIds, new JSONArray(selectedGroup));
                reqJsonObj.put(Const.Params.Language, language);
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
                Call<StudentsStudyPartnerBo> response = retrofit.create(API.class).getGroupStudentsList(body);
                response.enqueue(new Callback<StudentsStudyPartnerBo>() {
                    @Override
                    public void onResponse(Call<StudentsStudyPartnerBo> call, Response<StudentsStudyPartnerBo> response) {
                        dialogLoading.dismiss();
                        if (response.body() != null) {
                            StudentsStudyPartnerBo studentsStudyPartnerBo = response.body();
                            if (studentsStudyPartnerBo.getStatus().equalsIgnoreCase(Const.Params.TRUE)) {

                                if (typeRadioGrp == 2) {

                                    if (studentsStudyPartnerBo.getStudents() != null && !studentsStudyPartnerBo.getStudents().isEmpty()) {

                                        studentsStudyPartnerList.clear();
                                        for (int i = 0; i < studentsStudyPartnerBo.getStudents().size(); i++) {


                                            StudentsUserEntity studentsUserEntity = studentsStudyPartnerBo.getStudents().get(i);
                                            studentsUserEntity.setSelected(false);
                                            studentsStudyPartnerList.add(studentsUserEntity);

                                        }

                                    }
                                } else if (typeRadioGrp == 4) {

                                    if (studentsStudyPartnerBo.getPartners() != null && !studentsStudyPartnerBo.getPartners().isEmpty()) {

                                        studentsStudyPartnerList.clear();
                                        for (int i = 0; i < studentsStudyPartnerBo.getPartners().size(); i++) {
                                            StudentsUserEntity studentsUserEntity = studentsStudyPartnerBo.getPartners().get(i);
                                            studentsUserEntity.setSelected(false);
                                            studentsStudyPartnerList.add(studentsUserEntity);
                                        }


                                    }

                                }

                                stuPartnerRv.setVisibility(View.VISIBLE);
                                stuStudyPartnerAdapter.notifyDataSetChanged();

                            } else {
                                SmartClassUtil.showToast(context, studentsStudyPartnerBo.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<StudentsStudyPartnerBo> call, Throwable t) {
                        dialogLoading.dismiss();
                    }
                });
            } else {
                SmartClassUtil.showInternetToast(context, language);

            }
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
    }


    public void checkValidation() {
        scheduleTitle = scheduleEditText.getText().toString().trim();
        description = descripEditText.getText().toString().trim();
       /* if (courseId.length() == 0 || ("0").equalsIgnoreCase(courseId)) {
            if (language.equals("en")) {
                SmartClassUtil.showToast(context, "Select course");

            } else {
                SmartClassUtil.showToast(context, "Välj kurs");

            }

        } else*/
        if (selectedGroup.size() == 0) {
            if (language.equals("en")) {
                SmartClassUtil.showToast(context, "Select group");
            } else {
                SmartClassUtil.showToast(context, "Välj grupp");
            }
        } else if (scheduleTitle.length() == 0) {
            if (language.equals("en")) {
                SmartClassUtil.showToast(context, "Enter vaild schedule title");
            } else {
                SmartClassUtil.showToast(context, "Ange vaild schema titel");
            }
        } else

        {
            if (selectedGroup.size() == 1) {
                if (selectedGroup.get(0).equalsIgnoreCase("-1")) {

                    if (enddateCalendar.getTime().before(startCalendar.getTime())) {
                        if (language.equals("en")) {
                            SmartClassUtil.showToast(context, en_endDateError);
                        } else {
                            SmartClassUtil.showToast(context, sw_endDateError);
                        }
                    } else {
                        makeOwnBookingJson();
                    }
                } else {

                    //if (selectedGroup.get(0).equalsIgnoreCase("-2"))
                    scheduleTypeName = getScheduleName();
                    groupIds = getGroupIds();
                    if (scheduleType.equalsIgnoreCase("1") || (scheduleType.equalsIgnoreCase("2"))) {

                        if (enddateCalendar.getTime().before(startCalendar.getTime())) {
                            if (language.equals("en")) {
                                SmartClassUtil.showToast(context, en_endDateError);
                            } else {
                                SmartClassUtil.showToast(context, sw_endDateError);
                            }
                        } else {
                            makeLessionActivityJson();
                        }


                    } else if (scheduleType.equalsIgnoreCase("3")) {
                        if (enddateCalendar.getTime().before(startCalendar.getTime())) {
                            if (language.equals("en")) {
                                SmartClassUtil.showToast(context, en_endDateError);
                            } else {
                                SmartClassUtil.showToast(context, sw_endDateError);
                            }
                        } else {
                            makeExaminationJson();
                        }
                    } else {
                        makeAssignMentJson();
                    }
                }
            } else if (selectedGroup.size() > 1) {
                scheduleTypeName = getScheduleName();
                groupIds = getGroupIds();
                if (scheduleType.equalsIgnoreCase("1") || (scheduleType.equalsIgnoreCase("2"))) {

                    if (enddateCalendar.getTime().before(startCalendar.getTime())) {
                        if (language.equals("en")) {
                            SmartClassUtil.showToast(context, en_endDateError);
                        } else {
                            SmartClassUtil.showToast(context, sw_endDateError);
                        }
                    } else {
                        makeLessionActivityJson();
                    }
                } else if (scheduleType.equalsIgnoreCase("3")) {
                    if (enddateCalendar.getTime().before(startCalendar.getTime())) {
                        if (language.equals("en")) {
                            SmartClassUtil.showToast(context, en_endDateError);
                        } else {
                            SmartClassUtil.showToast(context, sw_endDateError);
                        }
                    } else {
                        makeExaminationJson();
                    }
                } else if (scheduleType.equalsIgnoreCase("4")) {
                    makeAssignMentJson();
                }
            }
        }




          /*  if (language.equals("en")) {
                SmartClassUtil.showToast(context, "Select schedule type");

            } else {
                SmartClassUtil.showToast(context, "Välj schematyp");

            }*/





        /*else if (scheduleType.equalsIgnoreCase("4"))
        {




        }*/


        /*else if (description.length() == 0) {
            if (language.equals("en")) {
                SmartClassUtil.showToast(context, "Enter valid decription");
            } else {
                SmartClassUtil.showToast(context, "Ange giltig decription");

            }

        }*/


    }


    public JSONObject makeOwnBookingJson() {
        JSONObject jsonObject = new JSONObject();
        try {


            jsonObject.put(Const.Params.SecurityKey, securityKey);
            jsonObject.put(Const.Params.ClassId, "-1");
            jsonObject.put(Const.Params.ID, schemaId);
            jsonObject.put(Const.Params.LoginUserId, userId);
            jsonObject.put(Const.Params.SCHEDULETYPE, scheduleType);
            jsonObject.put(Const.Params.Description, description);
            jsonObject.put(Const.Params.Title, scheduleTitle);
            jsonObject.put(Const.Params.COURSE, courseId);
            jsonObject.put(Const.Params.FROM_DATE, startDateVal + " " + startTimeText.getText().toString());
            jsonObject.put(Const.Params.TO_DATE, endDateVal + " " + endTimeText.getText().toString());
            jsonObject.put(Const.Params.Type, "");
            jsonObject.put(Const.Params.dateTimeAssignment, "");
            jsonObject.put(Const.Params.deadline, "");
            jsonObject.put(Const.Params.students, new JSONArray());
            jsonObject.put(Const.Params.studyPartners, new JSONArray());
            jsonObject.put(Const.Params.aspects, new JSONArray());
            jsonObject.put(Const.Params.teachers, "");
            jsonObject.put(Const.Params.courseObject, "");
            jsonObject.put(Const.Params.buildingId, "");
            jsonObject.put(Const.Params.roomIs, "");
            jsonObject.put(Const.Params.customerId, "");
            jsonObject.put(Const.Params.Language, language);


            callAddActivityApi(jsonObject);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;

    }

    public void makeLessionActivityJson() {
        JSONObject jsonObject = new JSONObject();
        try {


            jsonObject.put(Const.Params.SecurityKey, securityKey);
            jsonObject.put(Const.Params.ClassId, groupIds);
            jsonObject.put(Const.Params.ID, schemaId);
            jsonObject.put(Const.Params.LoginUserId, userId);
            jsonObject.put(Const.Params.SCHEDULETYPE, scheduleTypeName);
            jsonObject.put(Const.Params.Description, description);
            jsonObject.put(Const.Params.Title, scheduleTitle);
            jsonObject.put(Const.Params.COURSE, courseId);
            jsonObject.put(Const.Params.FROM_DATE, startDateVal + " " + startTimeText.getText().toString());
            jsonObject.put(Const.Params.TO_DATE, endDateVal + " " + endTimeText.getText().toString());
            jsonObject.put(Const.Params.Type, "");
            jsonObject.put(Const.Params.dateTimeAssignment, "");
            jsonObject.put(Const.Params.deadline, "");
            jsonObject.put(Const.Params.students, new JSONArray());
            jsonObject.put(Const.Params.studyPartners, new JSONArray());
            jsonObject.put(Const.Params.aspects, new JSONArray());
            jsonObject.put(Const.Params.teachers, "");
            jsonObject.put(Const.Params.courseObject, "");
            jsonObject.put(Const.Params.buildingId, "");
            jsonObject.put(Const.Params.roomIs, "");
            jsonObject.put(Const.Params.customerId, "");
            jsonObject.put(Const.Params.Language, language);


            callAddActivityApi(jsonObject);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void makeExaminationJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray aspectArray = new JSONArray();
            aspectArray = getSelectedAspects();
            jsonObject.put(Const.Params.SecurityKey, securityKey);
            jsonObject.put(Const.Params.ClassId, groupIds);
            jsonObject.put(Const.Params.ID, schemaId);
            jsonObject.put(Const.Params.LoginUserId, userId);
            jsonObject.put(Const.Params.SCHEDULETYPE, scheduleTypeName);
            jsonObject.put(Const.Params.Description, description);
            jsonObject.put(Const.Params.Title, scheduleTitle);
            jsonObject.put(Const.Params.COURSE, courseId);
            jsonObject.put(Const.Params.FROM_DATE, startDateVal + " " + startTimeText.getText().toString());
            jsonObject.put(Const.Params.TO_DATE, endDateVal + " " + endTimeText.getText().toString());
            jsonObject.put(Const.Params.Type, "");
            jsonObject.put(Const.Params.dateTimeAssignment, "");
            jsonObject.put(Const.Params.deadline, "");
            jsonObject.put(Const.Params.students, new JSONArray());
            jsonObject.put(Const.Params.studyPartners, new JSONArray());
            jsonObject.put(Const.Params.aspects, aspectArray);
            jsonObject.put(Const.Params.teachers, "");
            jsonObject.put(Const.Params.courseObject, "");
            jsonObject.put(Const.Params.buildingId, "");
            jsonObject.put(Const.Params.roomIs, "");
            jsonObject.put(Const.Params.customerId, "");
            jsonObject.put(Const.Params.Language, language);
            jsonObject.put(Const.Params.COURSE_ID, courseId);
            callAddActivityApi(jsonObject);
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
    }

    public void makeAssignMentJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray aspectArray = new JSONArray();
            aspectArray = getSelectedAspects();
            JSONArray students = new JSONArray();
            JSONArray studyPartners = new JSONArray();
            String deadLine = "";
            String type = getTypeName();

            if (deadLineChk.isChecked()) {
                deadLine = "1";
            } else {
                deadLine = "0";
            }


            if (typeRadioGrp == 2) {
                students = getStudentStudyPartner();
            } else if (typeRadioGrp == 4) {
                studyPartners = getStudentStudyPartner();
            } else {
                students = new JSONArray();
                studyPartners = new JSONArray();
            }


            jsonObject.put(Const.Params.SecurityKey, securityKey);
            jsonObject.put(Const.Params.ClassId, groupIds);
            jsonObject.put(Const.Params.ID, schemaId);
            jsonObject.put(Const.Params.LoginUserId, userId);
            jsonObject.put(Const.Params.SCHEDULETYPE, scheduleTypeName);
            jsonObject.put(Const.Params.Description, description);
            jsonObject.put(Const.Params.Title, scheduleTitle);
            jsonObject.put(Const.Params.COURSE, courseId);
            jsonObject.put(Const.Params.FROM_DATE, dateVal + " " + timeText.getText().toString().trim());
            jsonObject.put(Const.Params.TO_DATE, dateVal + " " + timeText.getText().toString().trim());
            jsonObject.put(Const.Params.Type, type);
            jsonObject.put(Const.Params.dateTimeAssignment, dateVal + " " + timeText.getText().toString().trim());
            jsonObject.put(Const.Params.deadline, deadLine);
            jsonObject.put(Const.Params.students, students);
            jsonObject.put(Const.Params.studyPartners, studyPartners);
            jsonObject.put(Const.Params.aspects, aspectArray);
            jsonObject.put(Const.Params.teachers, "");
            jsonObject.put(Const.Params.courseObject, "");
            jsonObject.put(Const.Params.buildingId, "");
            jsonObject.put(Const.Params.roomIs, "");
            jsonObject.put(Const.Params.customerId, "");
            jsonObject.put(Const.Params.Language, language);
            jsonObject.put(Const.Params.COURSE_ID, courseId);
            callAddActivityApi(jsonObject);
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
    }

    public JSONArray getSelectedAspects() {
        JSONArray aspects = new JSONArray();
        try {
            for (int i = 0; i < aspectList.size(); i++) {
                if (aspectList.get(i).getAspect().isSelected()) {
                    aspects.put(aspectList.get(i).getAspect().getId());
                }
            }
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
        return aspects;
    }


    public JSONArray getStudentStudyPartner() {
        JSONArray stupartner = new JSONArray();
        try {
            for (int i = 0; i < studentsStudyPartnerList.size(); i++) {
                if (studentsStudyPartnerList.get(i).isSelected()) {
                    stupartner.put(studentsStudyPartnerList.get(i).getId());
                }
            }


            AppLog.Log(Tag, stupartner.toString());
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
        return stupartner;
    }

    public String getScheduleName() {
        String scheduleName = "";
        /*for (int i = 0; i < scheduleList.size(); i++) {
            if (scheduleType.equalsIgnoreCase(scheduleList.get(i).getId())) {
                scheduleName = scheduleList.get(i).getName();
            }
        }*/
        try {
            if (scheduleType.equalsIgnoreCase("1")) {
                scheduleName = "lesson";
            } else if (scheduleType.equalsIgnoreCase("2")) {
                scheduleName = "activity";
            } else if (scheduleType.equalsIgnoreCase("3")) {
                scheduleName = "examination";
            } else if (scheduleType.equalsIgnoreCase("4")) {
                scheduleName = "assignment";
            }
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
        return scheduleName;
    }

    public String getTypeName() {
        String typeName = "";
        /*for (int i = 0; i < scheduleList.size(); i++) {
            if (scheduleType.equalsIgnoreCase(scheduleList.get(i).getId())) {
                scheduleName = scheduleList.get(i).getName();
            }
        }*/
        try {
            if (typeRadioGrp == 1) {
                typeName = "individually";
            } else if (typeRadioGrp == 2) {
                typeName = "unique";
            } else if (typeRadioGrp == 3) {
                typeName = "studypartners";
            } else if (typeRadioGrp == 4) {
                typeName = "uniquestudypartners";
            }
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
        return typeName;
    }

    public String getGroupIds() {
        String grpIds = "";
        for (int i = 0; i < selectedGroup.size(); i++) {
            if (i == selectedGroup.size() - 1) {
                grpIds += selectedGroup.get(i);
            } else {
                grpIds += selectedGroup.get(i) + ",";
            }
        }
        return grpIds;

    }

    private void callAddActivityApi(JSONObject reqObject) {
        try {
            if (SmartClassUtil.isNetworkAvailable(context)) {
                    /*callAspectsApi();*/
                reqJsonObj = reqObject;
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
                Call<String> response = retrofit.create(API.class).addActivityForTeacher(body);
                response.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        dialogLoading.dismiss();
                        try {
                            if (response.body() != null) {
                                aspectList.clear();
                                String activityResponse = response.body();
                                JSONObject activityJson = new JSONObject();

                                if (activityResponse != null && activityResponse.trim().length() != 0) {

                                    activityJson = new JSONObject(activityResponse);
                                    if (activityJson.getString("error").equalsIgnoreCase(Const.Params.FALSE)) {
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
                SmartClassUtil.showInternetToast(context, language);

            }
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
    }

    private void callViewActivityApi() {


        try {

/*
            GsonBuilder builder = new GsonBuilder();


            Gson gson = builder.create();

            String response = "{\n" +
                    "    \"data\": {\n" +
                    "        \"Schema\": {\n" +
                    "            \"id\": \"949\",\n" +
                    "            \"class_id\": \"0\",\n" +
                    "            \"user_id\": \"44\",\n" +
                    "            \"customer_id\": \"1\",\n" +
                    "            \"cou_course_id\": \"308\",\n" +
                    "            \"parent_schema_id\": \"0\",\n" +
                    "            \"tutoring_period_id\": \"0\",\n" +
                    "            \"building_id\": null,\n" +
                    "            \"room_id\": null,\n" +
                    "            \"examination_id\": \"121\",\n" +
                    "            \"aspect_ids\": \"414,415\",\n" +
                    "            \"assigned_teacher_ids\": \"\",\n" +
                    "            \"deadline\": \"1\",\n" +
                    "            \"schedule_type\": \"assignment\",\n" +
                    "            \"type\": \"unique\",\n" +
                    "            \"title\": \"Test examination\",\n" +
                    "            \"description\": \"\",\n" +
                    "            \"fromDate\": \"2017-02-06 01:09:00\",\n" +
                    "            \"toDate\": \"2017-02-06 02:09:00\",\n" +
                    "            \"tutoring_student_or_studupartner\": \"\",\n" +
                    "            \"with_parent\": \"no\",\n" +
                    "            \"tutoring_time\": \"0\",\n" +
                    "            \"book_tutoring_student_id\": \"0\",\n" +
                    "            \"added_by_teacher\": \"1\",\n" +
                    "            \"created\": \"2017-02-06 07:15:11\",\n" +
                    "            \"modified\": \"2017-02-06 10:48:25\"\n" +
                    "        },\n" +
                    "        \"ClaClass\": {\n" +
                    "            \"name\": null\n" +
                    "        },\n" +
                    "        \"User\": {\n" +
                    "            \"id\": \"44\",\n" +
                    "            \"USR_CUS_Rid\": \"1\",\n" +
                    "            \"group_id\": \"2\",\n" +
                    "            \"username\": \"AllVar050\",\n" +
                    "            \"USR_FirstName\": \"Eva\",\n" +
                    "            \"USR_LastName\": \"Perthus\",\n" +
                    "            \"USR_Birthday\": \"1990-01-01 00:00:00\",\n" +
                    "            \"USR_Email\": \"hema.tamaraiselvan@kaaylabs.com\",\n" +
                    "            \"USR_StreetAddress\": \"10 Market Road\",\n" +
                    "            \"start\": \"2013-11-01 00:00:00\",\n" +
                    "            \"end\": \"2017-11-30 00:00:00\"\n" +
                    "        },\n" +
                    "        \"CouCourse\": {\n" +
                    "            \"id\": \"308\",\n" +
                    "            \"COU_Name\": \"new2\"\n" +
                    "        },\n" +
                    "        \"TutoringPeriod\": {\n" +
                    "            \"id\": null,\n" +
                    "            \"teacher_id\": null,\n" +
                    "            \"class_id\": null,\n" +
                    "            \"group_type\": null,\n" +
                    "            \"title\": null,\n" +
                    "            \"start\": null,\n" +
                    "            \"end\": null,\n" +
                    "            \"tutoring_student_or_studupartner\": null,\n" +
                    "            \"with_parent\": null,\n" +
                    "            \"tutoring_time\": null,\n" +
                    "            \"created\": null,\n" +
                    "            \"modified\": null\n" +
                    "        },\n" +
                    "        \"Examination\": {\n" +
                    "            \"id\": \"121\",\n" +
                    "            \"customer_id\": \"1\",\n" +
                    "            \"teacher_id\": \"44\",\n" +
                    "            \"course_id\": \"308\",\n" +
                    "            \"group_id\": \"0\",\n" +
                    "            \"group_type\": \"\",\n" +
                    "            \"exam_type\": \"public\",\n" +
                    "            \"name\": \"Test examination\",\n" +
                    "            \"exam_date\": \"2017-02-06 01:09:00\",\n" +
                    "            \"exam_date_end\": \"2017-02-06 02:09:00\",\n" +
                    "            \"total_points\": \"0.0\",\n" +
                    "            \"exam_image_count\": \"0\",\n" +
                    "            \"exam_task_count\": \"0\",\n" +
                    "            \"lock_status\": \"1\",\n" +
                    "            \"created\": \"2017-02-06 07:15:11\",\n" +
                    "            \"modified\": \"2017-02-06 07:15:11\"\n" +
                    "        },\n" +
                    "        \"Student\": {\n" +
                    "            \"id\": null,\n" +
                    "            \"USR_CUS_Rid\": null,\n" +
                    "            \"group_id\": null,\n" +
                    "            \"username\": null,\n" +
                    "            \"USR_FirstName\": null,\n" +
                    "            \"USR_LastName\": null,\n" +
                    "            \"USR_Birthday\": null,\n" +
                    "            \"USR_Email\": null,\n" +
                    "            \"USR_StreetAddress\": null,\n" +
                    "            \"start\": null,\n" +
                    "            \"end\": null\n" +
                    "        },\n" +
                    "        \"SchemasStudypartner\": [],\n" +
                    "        \"Attendance\": {},\n" +
                    "        \"SchemaGroup\": [\n" +
                    "            {\n" +
                    "                \"id\": \"282\",\n" +
                    "                \"teacher_id\": \"44\",\n" +
                    "                \"group_id\": \"40\",\n" +
                    "                \"schema_id\": \"949\",\n" +
                    "                \"created\": \"2017-02-06 10:48:25\",\n" +
                    "                \"modified\": \"2017-02-06 10:48:25\",\n" +
                    "                \"status\": \"1\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"id\": \"283\",\n" +
                    "                \"teacher_id\": \"44\",\n" +
                    "                \"group_id\": \"11\",\n" +
                    "                \"schema_id\": \"949\",\n" +
                    "                \"created\": \"2017-02-06 10:48:25\",\n" +
                    "                \"modified\": \"2017-02-06 10:48:25\",\n" +
                    "                \"status\": \"1\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"SchemaTeacher\": [\n" +
                    "            {\n" +
                    "                \"id\": \"351\",\n" +
                    "                \"schema_id\": \"949\",\n" +
                    "                \"teacher_id\": \"44\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"Studentdoc\": [],\n" +
                    "        \"Sick\": []\n" +
                    "    },\n" +
                    "    \"status\": \"true\"\n" +
                    "}";

            viewActivityBo = gson.fromJson(response, ViewActivityBo.class);

            AppLog.Log(Tag, viewActivityBo.getData().getSchema().getTitle());
            AppLog.Log(Tag, viewActivityBo.getData().getSchema().getDescription());*/


            if (SmartClassUtil.isNetworkAvailable(context)) {

                    /*callAspectsApi();*/

                reqJsonObj = new JSONObject();
                reqJsonObj.put(Const.Params.SecurityKey, securityKey);
                reqJsonObj.put(Const.Params.LoginUserId, userId);
                reqJsonObj.put(Const.Params.SCHEMAID, schemaId);
                reqJsonObj.put(Const.Params.Language, language);
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
                Call<ViewActivityBo> response = retrofit.create(API.class).getActivity(body);
                response.enqueue(new Callback<ViewActivityBo>() {
                    @Override
                    public void onResponse(Call<ViewActivityBo> call, Response<ViewActivityBo> response) {
                        dialogLoading.dismiss();
                        if (response.body() != null) {
                            aspectList.clear();
                            viewActivityBo = response.body();
                            if (viewActivityBo.getStatus().equalsIgnoreCase(Const.Params.TRUE)) {
               /* setFromToDate(viewActivityBo.getData().getSchema().getFromDate(), viewActivityBo.getData().getSchema().getToDate());
                setDateTime(viewActivityBo.getData().getSchema().getFromDate());*/


                                if (userId.equalsIgnoreCase(viewActivityBo.getData().getSchemaTeacher().get(0).getTeacher_id())) {
                                    deleteImage.setVisibility(View.VISIBLE);
                                }
                                if (viewActivityBo.getData().getSchema().getTitle() != null) {
                                    scheduleEditText.setText(viewActivityBo.getData().getSchema().getTitle());
                                }
                                if (viewActivityBo.getData().getSchema().getDescription() != null) {
                                    descripEditText.setText(viewActivityBo.getData().getSchema().getDescription());
                                }


                                scheduleTypeName = viewActivityBo.getData().getSchema().getSchedule_type();
                                scheduleType = getScheduleId();
                                if (viewActivityBo.getData().getCouCourse() != null) {
                                    courseId = viewActivityBo.getData().getCouCourse().getId();
                                    courseName = viewActivityBo.getData().getCouCourse().getCOU_Name();
                                    for (int i = 0; i < courseList.size(); i++) {
                                        if (courseList.get(i).getCouCourse().getCOU_Name().equalsIgnoreCase(viewActivityBo.getData().getCouCourse().getCOU_Name())) {
                                            addCourseSp.setSelection(i);
                                        }
                                    }


                                }


                                if (viewActivityBo.getData().getSchemaGroup().size() == 1) {

                                    if (language.equalsIgnoreCase("en")) {
                                        addGroupSp.setText("1 Group ");
                                    } else {
                                        addGroupSp.setText("1 Grupp ");
                                    }


                                    if (viewActivityBo.getData().getSchemaGroup().get(0).getGroup_id().equalsIgnoreCase("-1")) {
                                        scheduleTypeLayout.setVisibility(View.GONE);
                                        assignmentLayout.setVisibility(View.GONE);
                                        startEndDateLayout.setVisibility(View.VISIBLE);
                                        aspetHeaderText.setVisibility(View.GONE);
                                        aspectsRv.setVisibility(View.GONE);

                                        setFromToDate(viewActivityBo.getData().getSchema().getFromDate(), viewActivityBo.getData().getSchema().getToDate());

                                        groupList.get(groupList.size() - 2).setSelected(true);

                                        grpPos.add(groupList.size() - 2);
                                        selectedGroup.add("-1");

                                    } else {


                                        showScheduleTypeByGroup();
                                        viewGroups();
                                        if (scheduleType.equalsIgnoreCase("1") || scheduleType.equalsIgnoreCase("2")) {

                                            setFromToDate(viewActivityBo.getData().getSchema().getFromDate(), viewActivityBo.getData().getSchema().getToDate());
                                            setDateTime(viewActivityBo.getData().getSchema().getFromDate());


                                        } else if (scheduleType.equalsIgnoreCase("3")) {


                                            /*setStartEndDate();*/
                                            setDateTime(viewActivityBo.getData().getSchema().getFromDate());

                                            setFromToDate(viewActivityBo.getData().getSchema().getFromDate(), viewActivityBo.getData().getSchema().getToDate());

                                        } else if (scheduleType.equalsIgnoreCase("4")) {


                                            setDateTime(viewActivityBo.getData().getSchema().getFromDate());
                                            setFromToDate(viewActivityBo.getData().getSchema().getFromDate(), viewActivityBo.getData().getSchema().getToDate());

                                            typeRadioGrp = getTypeId(viewActivityBo.getData().getSchema().getType());

                                            if (viewActivityBo.getData().getSchema().getDeadline().equalsIgnoreCase("1")) {
                                                deadLineChk.setChecked(true);
                                            } else {
                                                deadLineChk.setChecked(false);
                                            }
                                        }


                                    }


                                } else {
                                    viewGroups();
                                    showScheduleTypeByGroup();

                                    if (language.equalsIgnoreCase("en")) {
                                        addGroupSp.setText(selectedGroup.size() + " Groups ");
                                    } else {
                                        addGroupSp.setText(selectedGroup.size() + " grupper ");
                                    }
                                    showScheduleTypeByGroup();

                                    if (scheduleType.equalsIgnoreCase("1") || scheduleType.equalsIgnoreCase("2")) {

                                        setFromToDate(viewActivityBo.getData().getSchema().getFromDate(), viewActivityBo.getData().getSchema().getToDate());
                                        setDateTime(viewActivityBo.getData().getSchema().getFromDate());


                                    } else if (scheduleType.equalsIgnoreCase("3")) {
                        /*viewAspects();*/

                                        setFromToDate(viewActivityBo.getData().getSchema().getFromDate(), viewActivityBo.getData().getSchema().getToDate());
                                        setDateTime(viewActivityBo.getData().getSchema().getFromDate());


                                    } else if (scheduleType.equalsIgnoreCase("4")) {


                        /*viewAspects();*/
                                        setFromToDate(viewActivityBo.getData().getSchema().getFromDate(), viewActivityBo.getData().getSchema().getToDate());
                                        setDateTime(viewActivityBo.getData().getSchema().getFromDate());
                                        typeRadioGrp = getTypeId(viewActivityBo.getData().getSchema().getType());

                                        if (viewActivityBo.getData().getSchema().getDeadline().equalsIgnoreCase("1")) {
                                            deadLineChk.setChecked(true);
                                        } else {
                                            deadLineChk.setChecked(false);
                                        }

                                    }


                                }


                            } else {
                                /*SmartClassUtil.showToast(context, aspectResponseBo.getMessage());*/
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ViewActivityBo> call, Throwable t) {
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

    public String getScheduleId() {
        String scheduleType = "";
        /*for (int i = 0; i < scheduleList.size(); i++) {
            if (scheduleType.equalsIgnoreCase(scheduleList.get(i).getId())) {
                scheduleName = scheduleList.get(i).getName();
            }
        }*/
        try {
            if (scheduleTypeName.equalsIgnoreCase("lesson")) {
                scheduleType = "1";
                addScheduleTypeSp.setSelection(0);
            } else if (scheduleTypeName.equalsIgnoreCase("activity")) {
                addScheduleTypeSp.setSelection(1);
                scheduleType = "2";
            } else if (scheduleTypeName.equalsIgnoreCase("examination")) {
                addScheduleTypeSp.setSelection(2);
                scheduleType = "3";
            } else if (scheduleTypeName.equalsIgnoreCase("assignment")) {
                addScheduleTypeSp.setSelection(3);
                scheduleType = "4";
            }
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
        return scheduleType;
    }

    public int getTypeId(String typeName) {
        /*for (int i = 0; i < scheduleList.size(); i++) {
            if (scheduleType.equalsIgnoreCase(scheduleList.get(i).getId())) {
                scheduleName = scheduleList.get(i).getName();
            }
        }*/
        try {
            if (typeName.equalsIgnoreCase("individually")) {
                typeRadioGrp = 1;
                individualRb.setChecked(true);
            } else if (typeName.equalsIgnoreCase("unique")) {
                typeRadioGrp = 2;
                uniqueRb.setChecked(true);
            } else if (typeName.equalsIgnoreCase("studypartners")) {
                typeRadioGrp = 3;
                studyGrpRb.setChecked(true);
            } else if (typeName.equalsIgnoreCase("uniquestudypartners")) {
                typeRadioGrp = 4;
                uniqueStudyGrpRb.setChecked(true);
            }
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
        return typeRadioGrp;
    }

    public void setFromToDate(String fromDate, String toDate) {
        try {


            /*startCalendar = Calendar.getInstance();*/
            /*enddateCalendar = Calendar.getInstance();*/
            startCalendar.setTime(apiSdf.parse(fromDate));
            startDateVal = sdf.format(startCalendar.getTime());
            startDateText.setText(startDateVal);

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


            enddateCalendar.setTime(apiSdf.parse(toDate));
            endDateVal = sdf.format(enddateCalendar.getTime());
            endDateText.setText(endDateVal);
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


            startHour = startCalendar.get(Calendar.HOUR);
            startMinute = startCalendar.get(Calendar.MINUTE);
            startTimeText.setText(startHour + ":" + startMinute);
            startTimeDg = new TimePickerDialog(UpdateActivity.this,
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


            endHour = enddateCalendar.get(Calendar.HOUR);
            endMinute = enddateCalendar.get(Calendar.MINUTE);

            endTimeText.setText(endHour + ":" + endMinute);
            endTimeDg = new TimePickerDialog(UpdateActivity.this,
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


        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }

    }

    public void setDateTime(String dateStr) {
        try {

            /*dateCalendar = Calendar.getInstance();*/
            dateCalendar.setTime(apiSdf.parse(dateStr));
            dateVal = sdf.format(dateCalendar.getTime());
            dateText.setText(dateVal);

            dateDg = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    try {
                        dateCalendar.set(Calendar.YEAR, year);
                        dateCalendar.set(Calendar.MONTH, monthOfYear);
                        dateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                      /*  if (startCalendar.getTime().before(Calendar.getInstance().getTime())) {
                            if (language.equals("en")) {
                                SmartClassUtil.showToast(context, en_startDateError);
                            } else {
                                SmartClassUtil.showToast(context, sw_startDateError);

                            }
                            startCalendar = Calendar.getInstance();
                        } else {
                        }*/
                        dateVal = sdf.format(dateCalendar.getTime());
                        dateText.setText(sdf.format(dateCalendar.getTime()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            };


            dateHour = dateCalendar.get(Calendar.HOUR);
            dateMinute = dateCalendar.get(Calendar.MINUTE);
            timeText.setText(dateHour + ":" + dateMinute);


            dateTimeDg = new TimePickerDialog(UpdateActivity.this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            // TODO Auto-generated method stub

                        /*startTimeText.setText(hourOfDay + ":" + minute);*/
                            /*aL.set(index, hourOfDay + ":" + minute);*/

                            dateCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            dateCalendar.set(Calendar.MINUTE, minute);
                        /*if (startCalendar.getTime().before(Calendar.getInstance().getTime())) {
                            startHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                            endHour = Calendar.getInstance().get(Calendar.MINUTE);
                            SmartClassUtil.showToast(context, "Select valid time");
                        } else {*/
                            dateHour = hourOfDay;
                            dateMinute = minute;
/*
                        }
*/
                            timeText.setText(dateHour + ":" + dateMinute);
                        }

                    }, dateHour, dateMinute, true);// Yes 24 hour time


        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }

    }


    public void viewGroups() {
        selectedGroup.clear();
        grpPos.clear();

        for (int i = 0; i < viewActivityBo.getData().getSchemaGroup().size(); i++) {
            checkGroup(viewActivityBo.getData().getSchemaGroup().get(i).getGroup_id());
        }


    }

    public void checkGroup(String id) {

        for (int i = 0; i < groupList.size(); i++) {

            if (id.equalsIgnoreCase(groupList.get(i).getId())) {

                groupList.get(i).setSelected(true);
                selectedGroup.add(id);
                grpPos.add(i);
            }

        }
    }

    public boolean viewAspects(String aspectId) {
        /*selectedGroup.clear();*/
        List<String> aspects = new ArrayList<>();
        boolean bl = false;
        try {
            if (viewActivityBo.getData().getSchema().getAspect_ids() != null && !viewActivityBo.getData().getSchema().getAspect_ids().isEmpty()) {
                String[] words = viewActivityBo.getData().getSchema().getAspect_ids().split("\\,");//splits the string based on whitespace
                for (String w : words) {
                    aspects.add(w);
                    if (aspectId.equalsIgnoreCase(w))
                        bl = true;

                    AppLog.Log(Tag + " Aspect ", w);
                    /*checkAspects(w);*/
                }


            }
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
        return bl;

    }

    public void checkAspects(String id) {

        for (int i = 0; i < aspectList.size(); i++) {

            if (id.equalsIgnoreCase(aspectList.get(i).getAspect().getId())) {

                aspectList.get(i).getAspect().setSelected(true);

            }

        }

        aspectAdapter.notifyDataSetChanged();

    }

    public void callDeleteApi() {


        try {


            if (SmartClassUtil.isNetworkAvailable(context)) {

                    /*callAspectsApi();*/

                reqJsonObj = new JSONObject();
                reqJsonObj.put(Const.Params.SecurityKey, securityKey);
                reqJsonObj.put(Const.Params.LoginUserId, userId);
                reqJsonObj.put(Const.Params.SCHEMAID, schemaId);
                reqJsonObj.put(Const.Params.Language, language);
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
                Call<String> response = retrofit.create(API.class).deleteActivity(body);
                response.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        dialogLoading.dismiss();
                        try {
                            if (response.body() != null) {
                                String deleteResponse = response.body();
                               JSONObject deleteJson = new JSONObject(deleteResponse);
                                if (deleteJson.getString(Const.Params.Status).equalsIgnoreCase(Const.Params.TRUE)) {
                                    if(deleteJson.has(Const.Params.MSG))
                                    {
                                        SmartClassUtil.showToast(context, deleteJson.getString(Const.Params.MSG));
                                    }

                                    refresh=true;
//                                    onBackPressed();
                                    Intent i = new Intent(context,ActivityListByDateActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();
                                } else {
                                    if(deleteJson.has(Const.Params.MSG))
                                    {
                                        SmartClassUtil.showToast(context, deleteJson.getString(Const.Params.MSG));
                                    }
                                    refresh=false;
                                }
                            }
                        }catch (Exception e)
                        {
                            AppLog.handleException(Tag,e);
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


}