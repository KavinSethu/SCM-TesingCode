package com.pnf.elar.app.activity.schedule;


import android.graphics.Color;

import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pnf.elar.app.Bo.schedule.ScheduleMonthList;
import com.pnf.elar.app.activity.schedule.ScheduleMainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A basic example of how to use week view library.
 * Created by Raquib-ul-Alam Kanak on 1/3/2014.
 * Website: http://alamkanak.github.io
 */
public class AsynchronousActivity extends ScheduleMainActivity {


    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {

        List<WeekViewEvent> events = new ArrayList<>();
        // Populate the week view with some events.

      /*  List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        endTime.set(Calendar.MONTH, newMonth - 1);
        WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 30);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 4);
        endTime.set(Calendar.MINUTE, 30);
        endTime.set(Calendar.MONTH, newMonth-1);
        event = new WeekViewEvent(10, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_02));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 4);
        startTime.set(Calendar.MINUTE, 20);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 5);
        endTime.set(Calendar.MINUTE, 0);
        event = new WeekViewEvent(10, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_03));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 5);
        startTime.set(Calendar.MINUTE, 30);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 2);
        endTime.set(Calendar.MONTH, newMonth-1);
        event = new WeekViewEvent(2, getEventTitle(startTime), startTime, endTime);
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
        event = new WeekViewEvent(3, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_03));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 15);
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 3);
        event = new WeekViewEvent(4, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_04));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 1);
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 3);
        event = new WeekViewEvent(5, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, startTime.getActualMaximum(Calendar.DAY_OF_MONTH));
        startTime.set(Calendar.HOUR_OF_DAY, 15);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 3);
        event = new WeekViewEvent(5, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_02));
        events.add(event);

        //AllDay event
        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 23);
        event = new WeekViewEvent(7, getEventTitle(startTime),null, startTime, endTime, true);
        event.setColor(getResources().getColor(R.color.event_color_04));
        events.add(event);
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 8);
        startTime.set(Calendar.HOUR_OF_DAY, 2);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.DAY_OF_MONTH, 10);
        endTime.set(Calendar.HOUR_OF_DAY, 23);
        event = new WeekViewEvent(8, getEventTitle(startTime),null, startTime, endTime, true);
        event.setColor(getResources().getColor(R.color.event_color_03));
        events.add(event);

        // All day event until 00:00 next day
        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 10);
        startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);
        startTime.set(Calendar.MILLISECOND, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.DAY_OF_MONTH, 11);
        event = new WeekViewEvent(8, getEventTitle(startTime), null, startTime, endTime, true);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);*/
        List<WeekViewEvent> eventsList = new ArrayList<WeekViewEvent>();

        String mApiResult = callWeekService(newMonth, newYear);


        if (mApiResult != null) {

            // .show();

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();


            scheduleMonthList = gson.fromJson(mApiResult, ScheduleMonthList.class);


            if (scheduleMonthList.getStatus().equalsIgnoreCase("true")) {
                WeekViewEvent event = null;
                for (int i = 0; i < scheduleMonthList.getSchemaCalendar().size(); i++) {


                    ScheduleMonthList.SchemaCalendarEntity schemaCalendarEntity = scheduleMonthList.getSchemaCalendar().get(i);
                    Calendar startCalendar = Calendar.getInstance();
                    Calendar endCalendar = Calendar.getInstance();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



/*
                            Thu Sep 22 03:00:43 EDT 2016
*/
                    try {
                        startCalendar.setTime(sdf.parse(schemaCalendarEntity.getSchema().getStart()));

                        if (schemaCalendarEntity.getSchema().getEnd().trim().length() == 0) {
                            endCalendar = startCalendar;

                        } else {
                            endCalendar.setTime(sdf.parse(schemaCalendarEntity.getSchema().getEnd()));
                        }


                        event = new WeekViewEvent(Long.parseLong(schemaCalendarEntity.getSchema().getId()), schemaCalendarEntity.getSchema().getTitle(), startCalendar, endCalendar, df.format(startCalendar.getTime()));
                        /*if(i%2==0)
                        {*/
                        event.setColor(Color.parseColor(schemaCalendarEntity.getSchema().getLmscolors()));
                        /*}
                        else
                        {

                            event.setColor(Color.parseColor("#2FBCD0"));
                        }*/

                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                    eventsList.add(event);

                  /*  for(int j=0;i<events.size();i++)
                    {
                        if(events.get(j).getId()==event.getId())
                        {

                        }
                        else
                        {
                    }

                }
*/

                }
            } else {
                System.out.println("mApiResult null ");
            }
        }

        System.out.println("evvvvvvv" + eventsList.size());

        if (eventsList.size() > 0) {
            System.out.println("event " + eventsList.get(0).getEventDate() + " " + eventsList.get(0).getName());
        }


        events.addAll(eventsList);
        getWeekView().clearAnimation();
        getWeekView().notifyDatasetChanged();
        return events;

    }
}

/*
package com.pnf.elar.app.activity.schedule;

import android.util.Log;
import android.widget.Toast;

import com.alamkanak.weekview.WeekViewEvent;
import com.elar.util.SmartClassUtil;
import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.activity.schedule.apiClient.Event;

import com.google.gson.Gson;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.API;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.ToStringConverterFactory;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


*/
/**
 * An example of how events can be fetched from network and be displayed on the week view.
 * Created by Raquib-ul-Alam Kanak on 1/3/2014.
 * Website: http://alamkanak.github.io
 * <p>
 * Checks if an event falls into a specific year and month.
 *
 * @param event The event to check for.
 * @param year  The year.
 * @param month The month.
 * @return True if the event matches the year and month.
 *//*

public class AsynchronousActivity extends ScheduleMainActivity implements Callback<List<Event>> {

    private List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    boolean calledNetwork = false;

    UserSessionManager session;
    HashMap<String, String> user;
    String language = "", auth_token = "", base_url = "", user_id = "", securityKey = "H67jdS7wwfh";
    MyCustomProgressDialog dialog;

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {

        // Download events from network if it hasn't been done already. To understand how events are
        // downloaded using retrofit, visit http://square.github.io/retrofit

        getSessionValue();

       */
/* dialog = new MyCustomProgressDialog(AsynchronousActivity.this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();*//*

        if (!calledNetwork) {

           */
/* MyJsonService service = retrofit.create(MyJsonService.class);
            service.listEvents(this);
            *//*

            final JSONObject mainJSONObject = new JSONObject();
            //serviceCaller is the interface initialized with retrofit.create...
*/
/*
            AppLog.LogParameters(TAG, mainJSONObject.toString());
*//*

            try {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(base_url)
                        .addConverterFactory(new ToStringConverterFactory())
                        .addConverterFactory(GsonConverterFactory.create())

                        .client(client)
                        .build();
            */
/*dialog.show();*//*

            */
/*return retrofit.create(API.class);*//*


                mainJSONObject.put("securityKey", securityKey);
                mainJSONObject.put("loginUserID", user_id);
                mainJSONObject.put("month", newMonth);
                mainJSONObject.put("year", newYear);
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mainJSONObject.toString());
                Call<List<Event>> response = retrofit.create(API.class).getEventList(body);
                response.enqueue(this);
                SmartClassUtil.PrintMessage("req :: " + mainJSONObject.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }



*/
/*
            {"securityKey":"H67jdS7wwfh","loginUserID":"44","month":"06","year":"2016"}
*//*


         */
/*   Call<List<Event>> response = retrofit.create(API.class).getEventList(body);

            response.enqueue(new Callback<List<com.pnf.elar.app.activity.schedule.apiClient.Event>>() {
                @Override
                public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {

                    events.clear();


                    for (Event event : response.body()) {
                        events.add(event.toWeekViewEvent());
                    }
                    getWeekView().notifyDatasetChanged();

                }

                @Override
                public void onFailure(Call<List<Event>> call, Throwable t) {

                }
            });
*//*



          */
/*  response.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    *//*
*/
/*dialog.dismiss();*//*
*/
/*
                    events.clear();
                    try {




                }



                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    *//*
*/
/*dialog.dismiss();*//*
*/
/*
                }
            });*//*


           */
/* response.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {


                    GsonBuilder gsonBUilder = new GsonBuilder();
                    Gson gson = gsonBUilder.create();

                    if (!response.body().isEmpty()&&response.body()!=null) {




*//*
*/
/*
                        FeedBackAppReponse feedBackAppReponse = gson.fromJson(response.body(), FeedBackAppReponse.class);
*//*
*/
/*


                    } else {
*//*
*/
/*
                        FeedBackAppUtil.showToast(context, getString(R.string.service_failed));
*//*
*/
/*

                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.getLocalizedMessage();
                }
            });*//*



            calledNetwork = true;
        }

        // Return only the events that matches newYear and newMonth.
        List<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : events) {
            if (eventMatches(event, newYear, newMonth)) {
                matchedEvents.add(event);
            }
        }
      */
/*  dialog.dismiss();
        dialog.hide();*//*

        return events;
    }

    */
/**
 * Checks if an event falls into a specific year and month.
 *
 * @param event The event to check for.
 * @param year  The year.
 * @param month The month.
 * @return True if the event matches the year and month.
 *//*

    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1) || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }

    public void getSessionValue() {
        try {


            session = new UserSessionManager(this);

            user = session.getUserDetails();
            language = user.get(UserSessionManager.TAG_language);
            auth_token = user.get(UserSessionManager.TAG_Authntication_token);
            base_url = user.get(UserSessionManager.TAG_Base_url);
            user_id = user.get(UserSessionManager.TAG_user_id);
            Log.i("url", base_url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   */
/* @Override
    public void success(List<Event> events, Response response) {
        this.events.clear();


        *//*
*/
/*String res="[\n" +
                "{\n" +
                "name: \"Event 1\",\n" +
                "dayOfMonth: 3,\n" +
                "startTime: \"01:00\",\n" +
                "endTime: \"02:00\",\n" +
                "color: \"#F57F68\"\n" +
                "},\n" +
                "{\n" +
                "name: \"Event 2\",\n" +
                "dayOfMonth: 3,\n" +
                "startTime: \"04:00\",\n" +
                "endTime: \"05:00\",\n" +
                "color: \"#87D288\"\n" +
                "},\n" +
                "{\n" +
                "name: \"Event 3\",\n" +
                "dayOfMonth: 3,\n" +
                "startTime: \"10:00\",\n" +
                "endTime: \"11:00\",\n" +
                "color: \"#F8B552\"\n" +
                "},\n" +
                "{\n" +
                "name: \"Event 4\",\n" +
                "dayOfMonth: 3,\n" +
                "startTime: \"15:00\",\n" +
                "endTime: \"16:00\",\n" +
                "color: \"#59DBE0\"\n" +
                "},\n" +
                "{\n" +
                "name: \"Event 5\",\n" +
                "dayOfMonth: 4,\n" +
                "startTime: \"04:00\",\n" +
                "endTime: \"05:00\",\n" +
                "color: \"#F57F68\"\n" +
                "}]";


        *//*
*/
/**//*
*/
/*events*//*
*/
/**//*
*/
/*
        String result=new Gson().toJson(events);

        System.out.println("response"+response.toString());
    events = Arrays.asList(yourString);*//*
*/
/*


        for (Event event : events) {
            this.events.add(event.toWeekViewEvent());
        }
        getWeekView().notifyDatasetChanged();
    }

    @Override
    public void failure(RetrofitError error) {
        error.printStackTrace();
        Toast.makeText(this, "Service Failed", Toast.LENGTH_SHORT).show();
    }*//*


    @Override
    public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
*/
/*
        setProgressBarIndeterminateVisibility(false);
*//*

       */
/* ArrayAdapter<Question> adapter = (ArrayAdapter<Question>) getListAdapter();
        adapter.clear();
        adapter.addAll(response.body().items);*//*

        */
/*dialog.hide();
        dialog.dismiss();*//*

        try {

            this.events.clear();
            Log.d("res", "   " + response.body());
*/
/*
            JSONObject jsonObject = new JSONObject(response.body());
*//*


            List<Event> newEvent = response.body();
            //Arrays.asList(new Gson().fromJson(jsonObject.getJSONArray("schemaCalendar").toString(), Event[].class));


            for (Event event : newEvent) {
                this.events.add(event.toWeekViewEvent());
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Store the returned events in a global variable for later use.
                            */
/*mEventModels = eventModels;*//*


                    // This line will trigger the method 'onMonthChange()' again.
                    getWeekView().notifyDatasetChanged();


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onFailure(Call<List<Event>> call, Throwable t) {
        Toast.makeText(AsynchronousActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

}*/
