package com.pnf.elar.app.activity.schedule;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.elar.util.SmartClassUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pnf.elar.app.Bo.schedule.ScheduleMonthList;
import com.pnf.elar.app.Drawer;
import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.R;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.decorators.EventDecorator;
import com.pnf.elar.app.decorators.HighlightWeekendsDecorator;
import com.pnf.elar.app.decorators.MySelectorDecorator;
import com.pnf.elar.app.decorators.OneDayDecorator;
import com.pnf.elar.app.service.WebServeRequest;
import com.pnf.elar.app.util.AppLog;
import com.pnf.elar.app.util.Const;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.media.CamcorderProfile.get;

/*import com.marcohc.robotocalendar.RobotoCalendarView;*/

/*import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;*/

public abstract class ScheduleMainActivity extends AppCompatActivity implements MonthLoader.MonthChangeListener, View.OnClickListener, WeekView.EmptyViewClickListener, WeekView.EventClickListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener, OnDateSelectedListener, OnMonthChangedListener {
    //RobotoCalendarView.RobotoCalendarListener
    OneDayDecorator oneDayDecorator = new OneDayDecorator();

  /*  ImageView backbtnImage;
    TextView headerNameText;*/


    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_MONTH_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    @Bind(R.id.backbtnImage)
    ImageView backbtnImage;
    @Bind(R.id.headerNameText)
    TextView headerNameText;
    @Bind(R.id.saveImage)
    ImageView saveImage;
    @Bind(R.id.actionMonthView)
    Button actionMonthView;
    @Bind(R.id.actionWeekView)
    Button actionWeekView;
    @Bind(R.id.actionDayView)
    Button actionDayView;
    @Bind(R.id.actionToday)
    Button actionToday;
    @Bind(R.id.layouthead)
    LinearLayout layouthead;
    @Bind(R.id.monthCalendeView)
    MaterialCalendarView monthCalendeView;
    @Bind(R.id.weekView)
    WeekView weekView;
    @Bind(R.id.activity_schedule_main)
    RelativeLayout activityScheduleMain;
    @Bind(R.id.foodNoteBtn)
    Button foodNoteBtn;
    @Bind(R.id.addFoodMenuBtn)
    TextView addFoodMenuBtn;
    @Bind(R.id.addFoodMenuLayout)
    LinearLayout addFoodMenuLayout;
    @Bind(R.id.addMenuImage)
    ImageView addMenuImage;


    private int mWeekViewType = TYPE_MONTH_VIEW;
    private WeekView mWeekView;
    /*
        Button action_day_view, action_today, action_week_view, action_month_view;
    */
    int year, month;


    int first = 0;
    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat api_df = new SimpleDateFormat("yyyy-MM-dd");

    /*MaterialCalendarView monthCalendeView;*/


    UserSessionManager session;
    HashMap<String, String> user;
    String language = "", auth_token = "", base_url = "", user_id = "",childId="", securityKey = "H67jdS7wwfh",userType="";


    String selectedType = "month";

   // String Base_url = "";

    private List<? extends WeekViewEvent> mNextPeriodEvents;
    ScheduleMonthList scheduleMonthList;

    HashSet<WeekViewEvent> activitySet = new HashSet<>();
    /*List<WeekViewEvent> events = new ArrayList<>();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_main);
        ButterKnife.bind(this);
        getSessionValue();
        initView();


        Calendar calendar = new GregorianCalendar(2013, 4, 28);


    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    public void getSessionValue() {
        try {
            session = new UserSessionManager(getBaseContext());
            user = session.getUserDetails();
            language = user.get(UserSessionManager.TAG_language);
            auth_token = user.get(UserSessionManager.TAG_Authntication_token);
            base_url = user.get(UserSessionManager.TAG_Base_url);
            user_id = user.get(UserSessionManager.TAG_user_id);
            userType = user.get(UserSessionManager.TAG_user_type);
            if (("Teacher").equalsIgnoreCase(userType)) {


                addMenuImage.setVisibility(View.VISIBLE);
            }
            else if(("Parent").equalsIgnoreCase(userType))
            {
                addMenuImage.setVisibility(View.GONE);
                childId=user.get(UserSessionManager.TAG_child_id);
            }
            else
            {
                addMenuImage.setVisibility(View.GONE);

            }

            /*Log.e("baseur", base_url);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initView() {

        try {


            mWeekView = (WeekView) findViewById(R.id.weekView);
            // Get a reference for the week view in the layout.
            // Show a toast message about the touched event.
            mWeekView.setOnEventClickListener(this);
            // The week view has infinite scrolling horizontally. We have to provide the events of a
            // month every time the month changes on the week view.
            // Set long press listener for events.
            mWeekView.setEventLongPressListener(this);
            mWeekView.setEmptyViewClickListener(this);
            mWeekView.setOnClickListener(this);


            // Set long press listener for empty view
        /*mWeekView.setEmptyViewLongPressListener(this);*/

            // Set up a date time interpreter to interpret how the date and time will be formatted in
            // the week view. This is optional.
          /*  MonthLoader.MonthChangeListener mMonthChangeListener = new MonthLoader.MonthChangeListener() {
                @Override
                public List<WeekViewEvent> onMonthChange( int newYear,  int newMonth) {
                    // Populate the week view with some events.

                    *//*events = getEvents(newYear, newMonth);*//*



                    List<WeekViewEvent> eventsList = new ArrayList<WeekViewEvent>();

                    String mApiResult = callWeekService(newMonth, newYear);


                    if (mApiResult != null) {

                        // .show();

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();


                        scheduleMonthList = gson.fromJson(mApiResult, ScheduleMonthList.class);


                        if (scheduleMonthList.getStatus().equalsIgnoreCase("true")) {
                            WeekViewEvent event=null;
                            for (int i = 0; i < scheduleMonthList.getSchemaCalendar().size(); i++) {


                                ScheduleMonthList.SchemaCalendarEntity schemaCalendarEntity = scheduleMonthList.getSchemaCalendar().get(i);
                                Calendar startCalendar = Calendar.getInstance();
                                Calendar endCalendar = Calendar.getInstance();

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



                            *//*Thu Sep 22 03:00:43 EDT 2016*//*
                                try {
                                    startCalendar.setTime(sdf.parse(schemaCalendarEntity.getSchema().getStart()));

                                    if (schemaCalendarEntity.getSchema().getEnd().trim().length() == 0) {
                                        endCalendar = startCalendar;

                                    } else {
                                        endCalendar.setTime(sdf.parse(schemaCalendarEntity.getSchema().getEnd()));
                                    }
                                    event = new WeekViewEvent(Long.parseLong(schemaCalendarEntity.getSchema().getId()), schemaCalendarEntity.getSchema().getTitle(), startCalendar, endCalendar, df.format(startCalendar.getTime()));



                                } catch (Exception e) {
                                    e.printStackTrace();

                                }


                                eventsList.add(event);

                            }





                        }
                    } else {
                        System.out.println("mApiResult null ");
                    }



                    System.out.println("evvvvvvv" + eventsList.size());

                    if(eventsList.size()>0)
                    {
                        System.out.println("event "+eventsList.get(0).getEventDate()+" "+eventsList.get(0).getName());
                    }


                    events.addAll(eventsList);

                    mWeekView.notifyDatasetChanged();
                    return events;
                }
            };*/
            mWeekView.setMonthChangeListener(this);
            mWeekView.setNumberOfVisibleDays(7);
            setupDateTimeInterpreter(true);

            monthCalendeView = (MaterialCalendarView) findViewById(R.id.monthCalendeView);

       /* monthCalendeView.setRobotoCalendarListener(this);
        monthCalendeView.setShortWeekDays(false);

        monthCalendeView.showDateTitle(true);

        monthCalendeView.updateView();
        monthCalendeView.markCircleImage1(Calendar.getInstance());
        monthCalendeView.markCircleImage1(Calendar.getInstance());*/
            monthCalendeView.setOnDateChangedListener(this);
            monthCalendeView.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
            monthCalendeView.setOnMonthChangedListener(this);

            Calendar instance = Calendar.getInstance();
            monthCalendeView.setSelectedDate(instance.getTime());
            monthCalendeView.addDecorators(
                    new MySelectorDecorator(this),
                    new HighlightWeekendsDecorator(),
                    oneDayDecorator
            );
            monthCalendeView.setCurrentDate(instance);


            if(userType.equalsIgnoreCase("Teacher")){
                foodNoteBtn.setVisibility(View.VISIBLE);
            }else{
                foodNoteBtn.setVisibility(View.GONE);
            }


        /*action_week_view = (Button) findViewById(R.id.action_week_view);
        action_day_view = (Button) findViewById(R.id.action_day_view);
        action_today = (Button) findViewById(R.id.action_today);
        action_month_view = (Button) findViewById(R.id.action_month_view);*/
       /* backbtnImage = (ImageView) findViewById(R.id.backbtnImage);
        headerNameText = (TextView) findViewById(R.id.headerNameText);
*/
            if (language.equalsIgnoreCase("en")) {
                headerNameText.setText("Schedule");
               /*
                actionMonthView.setText("MONTH");
                actionWeekView.setText("WEEK");
                actionDayView.setText("DAY");
                actionToday.setText("TODAY");*/

            } else {
                headerNameText.setText("Schema");
                actionMonthView.setText("MÅNAD");
                actionWeekView.setText("VECKA");
                actionDayView.setText("DAG");
                actionToday.setText("I DAG");

                foodNoteBtn.setText("Pedagogisk Lunch");
                addFoodMenuBtn.setText("mat-menyn");
            }


            actionMonthView.setBackgroundColor(Color.parseColor("#666666"));
            mWeekView.setVisibility(View.GONE);


            callMonthService(instance.get(Calendar.MONTH), instance.get(Calendar.YEAR));
            setOnclickListeners();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void callMonthService(int month, int year) {
        session = new UserSessionManager(getBaseContext());

        user = session.getUserDetails();
        language = user.get(UserSessionManager.TAG_language);
        auth_token = user.get(UserSessionManager.TAG_Authntication_token);
        base_url = user.get(UserSessionManager.TAG_Base_url);
        user_id = user.get(UserSessionManager.TAG_user_id);

        JSONObject reqJson = new JSONObject();
        month = month + 1;

        try {
            reqJson.put(Const.Params.Month, String.valueOf(month));
            reqJson.put(Const.Params.Year, String.valueOf(year));
            if(("Parent").equalsIgnoreCase(userType))
            {
                reqJson.put(Const.Params.LoginUserId, childId);
            }
            else {
                reqJson.put(Const.Params.LoginUserId, user_id);
            }
            reqJson.put(Const.Params.SecurityKey, securityKey);
            reqJson.put(Const.Params.Language, language);


            MonthSchemaService monthSchemaService = new MonthSchemaService();
            if (AsyncTask.Status.RUNNING == monthSchemaService.getStatus()) {
                monthSchemaService.cancel(true);
                monthSchemaService = new MonthSchemaService();
            } else if (AsyncTask.Status.FINISHED == monthSchemaService.getStatus()) {
                monthSchemaService = new MonthSchemaService();
            }
            monthSchemaService.execute(reqJson.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public String callWeekService(int month, int year) {


        JSONObject reqJson = new JSONObject();
        month = month;

        try {
            reqJson.put(Const.Params.Month, String.valueOf(month));
            reqJson.put(Const.Params.Year, String.valueOf(year));
            if(("Parent").equalsIgnoreCase(userType))
            {
                reqJson.put(Const.Params.LoginUserId, childId);
            }
            else {
                reqJson.put(Const.Params.LoginUserId, user_id);
            }
            reqJson.put(Const.Params.SecurityKey, securityKey);
            reqJson.put(Const.Params.Language, language);


            WeekSchemaService monthSchemaService = new WeekSchemaService();
            if (AsyncTask.Status.RUNNING == monthSchemaService.getStatus()) {
                monthSchemaService.cancel(true);
                monthSchemaService = new WeekSchemaService();
            } else if (AsyncTask.Status.FINISHED == monthSchemaService.getStatus()) {
                monthSchemaService = new WeekSchemaService();
            }
            return monthSchemaService.execute(reqJson.toString()).get();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

  /*  public JSONObject makeMonthJson(String month,String year)
    {
        JSONObject reqJson=new JSONObject();

        try
        {
            reqJson.put(Const.Params.Month,month);
            reqJson.put(Const.Params.Year,year);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return reqJson;

    }*/

    public void setOnclickListeners() {
        actionWeekView.setOnClickListener(this);
        actionDayView.setOnClickListener(this);
        actionToday.setOnClickListener(this);
        actionMonthView.setOnClickListener(this);
        monthCalendeView.setOnClickListener(this);
        backbtnImage.setOnClickListener(this);
        foodNoteBtn.setOnClickListener(this);
        addFoodMenuLayout.setOnClickListener(this);
    }

    public List<WeekViewEvent> getEvents(int newYear, int newMonth) {


        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();


        Calendar startTime = Calendar.getInstance();


        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        endTime.set(Calendar.MONTH, newMonth - 1);


        WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime), startTime, endTime, df.format(startTime.getTime()));


        System.out.println("start" + startTime.getTime());
        System.out.println("end" + endTime.getTime());


        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 30);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 4);
        endTime.set(Calendar.MINUTE, 30);
        endTime.set(Calendar.MONTH, newMonth - 1);
        event = new WeekViewEvent(10, getEventTitle(startTime), startTime, endTime, df.format(startTime.getTime()));
        event.setColor(getResources().getColor(R.color.event_color_02));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 4);
        startTime.set(Calendar.MINUTE, 20);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 5);
        endTime.set(Calendar.MINUTE, 0);
        event = new WeekViewEvent(10, getEventTitle(startTime), startTime, endTime, df.format(startTime.getTime()));
        event.setColor(getResources().getColor(R.color.event_color_03));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 5);
        startTime.set(Calendar.MINUTE, 30);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 2);
        endTime.set(Calendar.MONTH, newMonth - 1);
        event = new WeekViewEvent(2, getEventTitle(startTime), startTime, endTime, df.format(startTime.getTime()));
        event.setColor(getResources().getColor(R.color.event_color_02));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 5);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        startTime.add(Calendar.DATE, 1);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 3);
        endTime.set(Calendar.MONTH, newMonth - 1);
        event = new WeekViewEvent(3, getEventTitle(startTime), startTime, endTime, df.format(startTime.getTime()));
        event.setColor(getResources().getColor(R.color.event_color_03));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 15);
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 3);
        event = new WeekViewEvent(4, getEventTitle(startTime), startTime, endTime, df.format(startTime.getTime()));
        event.setColor(getResources().getColor(R.color.event_color_04));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 1);
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 3);
        event = new WeekViewEvent(5, getEventTitle(startTime), startTime, endTime, df.format(startTime.getTime()));
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, startTime.getActualMaximum(Calendar.DAY_OF_MONTH));
        startTime.set(Calendar.HOUR_OF_DAY, 15);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 3);
        event = new WeekViewEvent(5, getEventTitle(startTime), startTime, endTime, df.format(startTime.getTime()));
        event.setColor(getResources().getColor(R.color.event_color_02));
        events.add(event);

     /*   //AllDay event
        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 23);
        event = new WeekViewEvent(7, getEventTitle(startTime), null, startTime, endTime, true);
        event.setColor(getResources().getColor(R.color.event_color_04));
        events.add(event);
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 8);
        startTime.set(Calendar.HOUR_OF_DAY, 2);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.DAY_OF_MONTH, 10);
        endTime.set(Calendar.HOUR_OF_DAY, 23);
        event = new WeekViewEvent(8, "all day 1", null, startTime, endTime, true);
        event.setColor(getResources().getColor(R.color.event_color_03));
        events.add(event);

        // All day event until 00:00 next day
        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 10);
        startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);
        startTime.set(Calendar.MILLISECOND, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.DAY_OF_MONTH, 11);


        event = new WeekViewEvent(8, "all day 2", null, startTime, endTime, true);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);*/

        return events;
    }

    @Override
    public void onClick(View view) {
        setupDateTimeInterpreter(view.getId() == R.id.actionWeekView);
/*
        mWeekView.setDateTimeInterpreter(mWeekView.getDateTimeInterpreter());
*/

        switch (view.getId()) {
            case R.id.actionToday:
                mWeekViewType = TYPE_DAY_VIEW;
                monthCalendeView.setVisibility(View.GONE);

                mWeekView.setVisibility(View.VISIBLE);

                mWeekView.setNumberOfVisibleDays(1);
                actionToday.setBackgroundColor(Color.parseColor("#666666"));
                actionDayView.setBackgroundColor(Color.parseColor("#617CBF"));
                actionMonthView.setBackgroundColor(Color.parseColor("#617CBF"));
                actionWeekView.setBackgroundColor(Color.parseColor("#617CBF"));
//                mWeekView.goToToday();
                if(selectedType.equalsIgnoreCase("month")){
                    monthCalendeView.setVisibility(View.VISIBLE);
                    monthCalendeView.setCurrentDate(mWeekView.getFirstVisibleDate());
                    callMonthService(monthCalendeView.getCurrentDate().getMonth(), monthCalendeView.getCurrentDate().getYear());
                }else if(selectedType.equalsIgnoreCase("week")){

                    if (mWeekViewType != TYPE_WEEK_VIEW) {
                        mWeekViewType = TYPE_WEEK_VIEW;

                        mWeekView.setNumberOfVisibleDays(7);

                        System.out.println("monthCalendeView.getCurrentDate() " + monthCalendeView.getCurrentDate().getDate() + "  " + monthCalendeView.getSelectedDate());
                        if (selectedType.equals("month")) {
                            selectedType = "week";
                        /*mWeekView.goToDate(monthCalendeView.getCurrentDate().getCalendar());*/
                            Calendar calendar = Calendar.getInstance();
                            if (monthCalendeView.getCurrentDate().getDate() == monthCalendeView.getSelectedDate().getDate()) {

                                calendar.setTime(monthCalendeView.getCurrentDate().getDate());
                                mWeekView.goToDate(calendar);
                            } else {
                                calendar.setTime(monthCalendeView.getCurrentDate().getDate());
                                mWeekView.goToDate(calendar);
                            }


                        } else {
                        /*mWeekView.goToDate(mWeekView.getFirstVisibleDate());*/
                            mWeekView.goToDate(mWeekView.getFirstVisibleDate());
                        }
                        // Lets change some dimensions to best fit the view.
                        mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                        mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                        mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    }
                }else{
                    Calendar calendar = Calendar.getInstance();
                    mWeekView.goToDate(calendar);
                    mWeekView.goToHour(Calendar.HOUR);
                }
                break;
            case R.id.actionDayView:
                monthCalendeView.setVisibility(View.GONE);
                mWeekView.setVisibility(View.VISIBLE);
                actionDayView.setBackgroundColor(Color.parseColor("#666666"));
                actionToday.setBackgroundColor(Color.parseColor("#617CBF"));
                actionWeekView.setBackgroundColor(Color.parseColor("#617CBF"));
                actionMonthView.setBackgroundColor(Color.parseColor("#617CBF"));
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    mWeekView.setNumberOfVisibleDays(1);
                    Calendar calendar = Calendar.getInstance();

                    if (mWeekViewType == TYPE_MONTH_VIEW) {

                        calendar.setTime(monthCalendeView.getCurrentDate().getDate());
                        mWeekView.goToDate(calendar);
                    } else {
                        mWeekView.goToDate(calendar);
                        mWeekView.goToHour(Calendar.HOUR);
                    }

                    mWeekViewType = TYPE_DAY_VIEW;



                  /*  if (monthCalendeView.getCurrentDate().getDate() == monthCalendeView.getSelectedDate().getDate()) {


                    } else if (monthCalendeView.getCurrentDate().getDate().before(monthCalendeView.getSelectedDate().getDate())) {
                        calendar.set(Calendar.WEEK_OF_YEAR, monthCalendeView.getCurrentDate().getCalendar().get(Calendar.WEEK_OF_YEAR));
                        mWeekView.goToDate(calendar);
                    }*/


                   /* if (selectedType.equals("month")) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(monthCalendeView.getCurrentDate().getDate());

                        mWeekView.goToDate(calendar);
                        *//*mWeekView.goToDate(monthCalendeView.getCurrentDate().getCalendar());*//*
                    } else {
                        mWeekView.goToDate(mWeekView.getFirstVisibleDate());
                    }
*/

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                Calendar calendar1 = Calendar.getInstance();
                mWeekView.goToHour(Calendar.HOUR);
                selectedType = "day";
                break;

            case R.id.actionWeekView:
                monthCalendeView.setVisibility(View.GONE);
                mWeekView.setVisibility(View.VISIBLE);

                actionWeekView.setBackgroundColor(Color.parseColor("#666666"));
                actionDayView.setBackgroundColor(Color.parseColor("#617CBF"));
                actionToday.setBackgroundColor(Color.parseColor("#617CBF"));
                actionMonthView.setBackgroundColor(Color.parseColor("#617CBF"));


                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    mWeekViewType = TYPE_WEEK_VIEW;

                    mWeekView.setNumberOfVisibleDays(7);

                    System.out.println("monthCalendeView.getCurrentDate() " + monthCalendeView.getCurrentDate().getDate() + "  " + monthCalendeView.getSelectedDate());
                    if (selectedType.equals("month")) {
                        selectedType = "week";
                        /*mWeekView.goToDate(monthCalendeView.getCurrentDate().getCalendar());*/
                        Calendar calendar = Calendar.getInstance();
                        if (monthCalendeView.getCurrentDate().getDate() == monthCalendeView.getSelectedDate().getDate()) {

                            calendar.setTime(monthCalendeView.getCurrentDate().getDate());
                            mWeekView.goToDate(calendar);
                        } else {
                            calendar.setTime(monthCalendeView.getCurrentDate().getDate());
                            mWeekView.goToDate(calendar);
                            mWeekView.goToHour(Calendar.HOUR);
                        }


                    } else {
                        /*mWeekView.goToDate(mWeekView.getFirstVisibleDate());*/
                        mWeekView.goToDate(mWeekView.getFirstVisibleDate());
                    }
                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                }
                selectedType = "week";
                break;
            case R.id.actionMonthView:
                actionWeekView.setBackgroundColor(Color.parseColor("#617CBF"));
                actionDayView.setBackgroundColor(Color.parseColor("#617CBF"));
                actionToday.setBackgroundColor(Color.parseColor("#617CBF"));
                actionMonthView.setBackgroundColor(Color.parseColor("#666666"));

                if (mWeekViewType != TYPE_MONTH_VIEW) {

                    mWeekViewType = TYPE_MONTH_VIEW;
                }


                mWeekView.setVisibility(View.GONE);
                monthCalendeView.setVisibility(View.VISIBLE);
                monthCalendeView.setCurrentDate(mWeekView.getFirstVisibleDate());
                selectedType = "month";


                callMonthService(monthCalendeView.getCurrentDate().getMonth(), monthCalendeView.getCurrentDate().getYear());
                break;

         /*   case R.id.weekView:

                Toast.makeText(this, "Clicked  weekView", Toast.LENGTH_SHORT).show();
                break;*/

            case R.id.backbtnImage:
                finish();
                break;
            case R.id.foodNoteBtn:
                Intent foodNoteIntent = new Intent(ScheduleMainActivity.this, FoodNoteActivity.class);
                startActivity(foodNoteIntent);
                break;
            case R.id.addFoodMenuLayout:
                Intent foodMenuIntent = new Intent(ScheduleMainActivity.this, AddFoodMenuActivity.class);
                startActivity(foodMenuIntent);
                break;
            default:
                break;


        }


    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        *//*setupDateTimeInterpreter(id == R.id.action_week_view);*//*

        mWeekView.setDateTimeInterpreter(mWeekView.getDateTimeInterpreter());
        switch (id) {
            case R.id.action_today:
                mWeekView.goToToday();
                return true;
            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;

            case R.id.action_week_view:

                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;


                    mWeekView.setNumberOfVisibleDays(7);


                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     *
     * @param shortDate True if the date values should be short.
     */
    private void setupDateTimeInterpreter(final boolean shortDate) {


        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {


                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }

    protected String getEventTitle(Calendar time) {

        return String.format("Test of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
/*
        return String.format("Test 1 of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
*/
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        try {

           /* System.out.println("event " + "Clicked " + event.getEventDate() + " sie" + event.getStartTime().getTime());
            Toast.makeText(this, "Clicked " + event.getEventDate() + " sie" + event.getStartTime().getTime(), Toast.LENGTH_SHORT).show();

            Intent foodNote = new Intent(ScheduleMainActivity.this, ActivityListByDateActivity.class);
            //2016-11-11
            foodNote.putExtra(Const.CommonParams.SELECTED_DATE, convertDate(event.getEventDate()));
            startActivityForResult(foodNote, Const.CommonParams.CALRESULT);*/


            SmartClassUtil.PrintMessage("test" + mWeekView.getDateTimeInterpreter());
            gotoActivity(event.getStartTime().getTime(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String convertDate(String clickdate) {
        String formattedDate = "";
        try {
            DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = originalFormat.parse(clickdate);
            formattedDate = targetFormat.format(date);  // 20120821
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    @Override
    public void onEmptyViewClicked(Calendar time) {
       /* Toast.makeText(this, "Empty view Click: " + getEventTitle(time), Toast.LENGTH_SHORT).show();

  */
        gotoActivity(time.getTime(), false);
      /*  Intent foodNote = new Intent(ScheduleMainActivity.this, ActivityListByDateActivity.class);
        //2016-11-11

        *//*String clickDate= time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH);*//*
        Date date = time.getTime();


        foodNote.putExtra(Const.CommonParams.SELECTED_DATE, new SimpleDateFormat("yyyy-MM-dd").format(date));
        startActivityForResult(foodNote, Const.CommonParams.CALRESULT);
*/
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        /*Toast.makeText(this, "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
        /*Toast.makeText(this, "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();*/
    }

    public WeekView getWeekView() {
        return mWeekView;
    }

    public DateTimeInterpreter getDateTimeInterpreter() {
        DateTimeInterpreter dateTimeInterpreter = mWeekView.getDateTimeInterpreter();
        if (mWeekView.getDateTimeInterpreter() == null) {

            dateTimeInterpreter = mWeekView.getDateTimeInterpreter();

            dateTimeInterpreter = new DateTimeInterpreter() {
                @Override
                public String interpretDate(Calendar date) {
                    try {


                        mWeekView.getDateTimeInterpreter().interpretDate(date);
/*
                        System.out.println("set  ll" + date.toString());
*/


                        return date.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "";
                    }
                }

                @Override
                public String interpretTime(int hour) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, 0);

                    return String.valueOf(hour);
                }
            };
        }
        return dateTimeInterpreter;
    }


   /* public List<WeekViewEvent> getEventByDate(String eventDate) {


        List<WeekViewEvent> eventTempList = new ArrayList<>();


        for (int i = 0; i < events.size(); i++) {
            System.out.println("e ::  " + events.get(i).getEventDate() + " click " + eventDate);

            if (events.get(i).getEventDate().equals(eventDate)) {

                eventTempList.add(events.get(i));
            }
        }


        return eventTempList;

    }*/

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        //If you change a decorate, you need to invalidate decorators
        try {
            oneDayDecorator.setDate(date.getDate());

            widget.invalidateDecorators();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date.getDate());
            mWeekView.goToDate(calendar);
            gotoActivity(date.getDate(), true);

            mWeekView.goToDate(calendar);
            monthCalendeView.setCurrentDate(date.getDate());
        } catch (Exception e) {
            AppLog.handleException("Schedule", e);
        }

    }

    public void gotoActivity(Date selDate, boolean curTime) {
        Calendar selcalendar = Calendar.getInstance();
        selcalendar.setTime(selDate);
        if (curTime) {
            selcalendar.set(Calendar.HOUR_OF_DAY, Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
            selcalendar.set(Calendar.MINUTE, Calendar.getInstance().get(Calendar.MINUTE));
            selcalendar.set(Calendar.SECOND, Calendar.getInstance().get(Calendar.SECOND));


        }
        Log.e("sss", selDate.toString());
        //2016-11-11
        Intent foodNote = new Intent(ScheduleMainActivity.this, ActivityListByDateActivity.class);
        foodNote.putExtra(Const.CommonParams.SELECTED_DATE, new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(selcalendar.getTime()));
        foodNote.putExtra(Const.CommonParams.API_DATE, api_df.format(selDate));

        startActivityForResult(foodNote, Const.CommonParams.CALRESULT);
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        //noinspection ConstantConditions

       /* oneDayDecorator.setDate(date.getDate());

        Calendar cal = Calendar.getInstance();
        widget.invalidateDecorators();*/


      /*  String month = String.valueOf(date.getMonth());
        if (month.equals(String.valueOf(cal.get(Calendar.MONTH)))) {
            monthCalendeView.setSelectedDate(cal.getTime());
            oneDayDecorator.setDate(cal.getTime());

        } else {
            monthCalendeView.setSelectedDate(date.getDate());
            oneDayDecorator.setDate(date.getDate());


        }*/
        callMonthService(date.getMonth(), date.getYear());

/*
        Toast.makeText(getApplicationContext(), date.getDate() + "" + date.getMonth() + " " + date.getYear(), Toast.LENGTH_SHORT).show();
*/
    }

   /* @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onDayClick(Calendar daySelectedCalendar) {
        Toast.makeText(this, "onDayClick: " + daySelectedCalendar.getTime(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDayLongClick(Calendar daySelectedCalendar) {
        Toast.makeText(this, "onDayLongClick: " + daySelectedCalendar.getTime(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRightButtonClick() {
        Toast.makeText(this, "onRightButtonClick!"+monthCalendeView.getCakendar().get(Calendar.MONTH)+" year : "+monthCalendeView.getCakendar().get(Calendar.YEAR), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLeftButtonClick() {
        Toast.makeText(this, "onLeftButtonClick!"+monthCalendeView.getCakendar().get(Calendar.MONTH)+" year : "+monthCalendeView.getCakendar().get(Calendar.YEAR), Toast.LENGTH_SHORT).show();
    }*/


    class MonthSchemaService extends AsyncTask<String, String, String> {

        MyCustomProgressDialog dialog;

        private String url = base_url + "mobile_api/getSchemaDetailsForMonth";


        String status;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new MyCustomProgressDialog(ScheduleMainActivity.this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {


            String monthResponse = "";

            try {
                JSONObject monthReqJson = new JSONObject(args[0]);

              /* String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode("H67jdS7wwfh", "UTF-8") +
                        "&" + Const.Params.LoginUserId + "=" + URLEncoder.encode(user_id, "UTF-8") +
                        "&" + Const.Params.Month + "=" + URLEncoder.encode(monthReqJson.getString("month"), "UTF-8")+
                        "&"+ "&" + Const.Params.Year + "=" + URLEncoder.encode(monthReqJson.getString("year"), "UTF-8");
*/


                monthResponse = WebServeRequest.postJSONRequest(url, monthReqJson.toString());

                Log.e("month response", monthResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return monthResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done

            if (dialog.isShowing())
                dialog.dismiss();

            try {


                if (!results.isEmpty() && results != null) {


                    // .show();

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();


                    scheduleMonthList = gson.fromJson(results, ScheduleMonthList.class);

                    if (scheduleMonthList.getStatus().equalsIgnoreCase("true")) {

                        ArrayList<CalendarDay> dates = new ArrayList<>();
                        for (int i = 0; i < scheduleMonthList.getSchemaCalendar().size(); i++) {

                            System.out.println("dates " + scheduleMonthList.getSchemaCalendar().get(i).getDay() + "-" + (Integer.parseInt(scheduleMonthList.getSchemaCalendar().get(i).getMonth()) - 1) + "-" + scheduleMonthList.getSchemaCalendar().get(i).getYear());


                            Date schemaData = new SimpleDateFormat("dd-mm-yyy").parse(scheduleMonthList.getSchemaCalendar().get(i).getDay() + "-" + (Integer.parseInt(scheduleMonthList.getSchemaCalendar().get(i).getMonth()) - 1) + "-" + scheduleMonthList.getSchemaCalendar().get(i).getYear());


                            Calendar calendar = Calendar.getInstance();

                            int date = Integer.parseInt(scheduleMonthList.getSchemaCalendar().get(i).getDay());
                            int year = Integer.parseInt(scheduleMonthList.getSchemaCalendar().get(i).getYear());
                            int month = Integer.parseInt(scheduleMonthList.getSchemaCalendar().get(i).getMonth()) - 1;

                            calendar.set(year, month, date);
                            /*calendar.setTime(schemaData);*/

/*
                            for (int i = 0; i < 30; i++) {
*/
                            CalendarDay day = CalendarDay.from(calendar);
                            dates.add(day);


                            /*    calendar.add(Calendar.DATE, 5);
                            }*/
                        }
                        System.out.println("dates size" + dates.size());
                        monthCalendeView.addDecorator(new EventDecorator(Color.parseColor("#617DBE"), dates));

                    }


                } else {
                    SmartClassUtil.showToast(getApplicationContext(), "Service Failed");

                }


            } catch (Exception e) {
                e.printStackTrace();
                dialog.dismiss();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        callMonthService(monthCalendeView.getCurrentDate().getMonth(), monthCalendeView.getCurrentDate().getYear());
    }

    class WeekSchemaService extends AsyncTask<String, List<WeekViewEvent>, String> {

        MyCustomProgressDialog dialog;

        private String url = base_url + "mobile_api//WeekAPIAndroid";


        String status;
        JSONObject weekReqJson = new JSONObject();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new MyCustomProgressDialog(ScheduleMainActivity.this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }


        protected String doInBackground(String... args) {


            String weekResponse = "";

            try {
                weekReqJson = new JSONObject(args[0]);


                weekResponse = WebServeRequest.postJSONRequest(url, weekReqJson.toString());

                Log.e("weekResponse", weekResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return weekResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done



            try {
                if (dialog.isShowing())
                    dialog.dismiss();

            } catch (Exception e) {
                e.printStackTrace();
                dialog.dismiss();

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AppLog.Log("Error data1 "," 121 ");

        /*AppLog.Log("Error data ",data.toString());*/
        if (requestCode == Const.CommonParams.CALRESULT && resultCode == RESULT_OK ) ;
        {
            if (data != null) {

                String bl=data.getBooleanExtra(Const.CommonParams.REFRESH, false)+"";
                AppLog.Log("Error",bl);

                if (data.getBooleanExtra(Const.CommonParams.REFRESH, false)) {

                  /*  Intent intent = getIntent();
                    finish();
                    startActivity(intent);*/



                }
            }
        }
    }

    @Override
    public void onBackPressed() {

        Intent in = new Intent(getApplicationContext(), Drawer.class);
        startActivity(in);
        finish();

//        super.onBackPressed();
    }
}
