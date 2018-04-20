package com.pnf.elar.app.activity.schedule;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elar.util.NetworkUtil;
import com.elar.util.SmartClassUtil;
import com.pnf.elar.app.Bo.schedule.ScheduleMonthList;
import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.R;
import com.pnf.elar.app.UILApplication;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.API;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.ToStringConverterFactory;
import com.pnf.elar.app.adapter.schedule.SchemaAdapter;
import com.pnf.elar.app.util.AppLog;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.RecyclerUtils;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
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


public class ActivityListByDateActivity extends AppCompatActivity {

    boolean modified = false;
    @Bind(R.id.activityRv)
    RecyclerView activityRv;
    @Bind(R.id.backbtnImage)
    ImageView backbtnImage;
    @Bind(R.id.titleText)
    TextView titleText;
    @Bind(R.id.headerNameText)
    TextView activityDateText;
    @Bind(R.id.saveImage)
    ImageView saveImage;
    //Session Values
    UserSessionManager session;
    HashMap<String, String> user;
    String language = "", auth_token = "", base_url = "", user_id = "", securityKey = "H67jdS7wwfh", userType = "", childId = "";
    Context context;
    String firstName, lastName;
    //API Request and Response
    List<ScheduleMonthList.SchemaCalendarEntity> activityList = new ArrayList<>();
    SchemaAdapter activityAdapter;
    JSONObject reqJsonObj;
    String selectedDate = "";
    String apiDate = "";
    MyCustomProgressDialog dialog;
    String Tag = "ActiivtyList";
    int absenceNote, retriverNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_by_date);
        ButterKnife.bind(this);
        getSessionValues();
    }

    public void getSessionValues() {
        try {
            context = getApplicationContext();
            session = UILApplication.getUserSessionManager(context);
            user = session.getUserDetails();
            language = user.get(UserSessionManager.TAG_language);
            auth_token = user.get(UserSessionManager.TAG_Authntication_token);
            base_url = user.get(UserSessionManager.TAG_Base_url);
            user_id = user.get(UserSessionManager.TAG_user_id);
            userType = user.get(UserSessionManager.TAG_user_type);
            if (userType.equalsIgnoreCase("Parent")) {
                childId = user.get(UserSessionManager.TAG_child_id);
            }
            if(language.equalsIgnoreCase("sw")){
                titleText.setText("Aktiviteter");
            }
            selectedDate = getIntent().getStringExtra(Const.CommonParams.SELECTED_DATE);
            apiDate = getIntent().getStringExtra(Const.CommonParams.API_DATE);
            SmartClassUtil.PrintMessage(selectedDate);
            activityDateText.setText(apiDate);
            saveImage.setVisibility(View.VISIBLE);
            saveImage.setImageResource(R.drawable.plus);

            setAdapter();

            callActivityService();
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
    }

    private void setAdapter() {
        try {
            activityAdapter = new SchemaAdapter(context, activityList,language);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            activityRv.setLayoutManager(layoutManager);
            activityRv.setItemAnimator(new DefaultItemAnimator());
            activityRv.setAdapter(activityAdapter);

            dialog = new MyCustomProgressDialog(ActivityListByDateActivity.this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);


            activityRv.addOnItemTouchListener(
                    new RecyclerUtils.RecyclerItemClickListener(getApplicationContext(), new RecyclerUtils.RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            // TODO Handle item click
                            if (("Teacher").equalsIgnoreCase(userType)) {
                                try {
                                    if (NetworkUtil.getInstance(context).isInternet()) {
                                        Intent updateIntent = new Intent(ActivityListByDateActivity.this, UpdateActivity.class);
                                        updateIntent.putExtra(Const.CommonParams.SCHEMAID, activityList.get(position).getSchema().getId());
                                        updateIntent.putExtra(Const.CommonParams.SELECTED_DATE, selectedDate);
                                        startActivityForResult(updateIntent, Const.CommonParams.ADDACTIVITY);
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    if (NetworkUtil.getInstance(context).isInternet()) {
                                        Intent viewIntent = new Intent(ActivityListByDateActivity.this, ViewactivityStudentParent.class);
                                        viewIntent.putExtra(Const.CommonParams.SCHEMAID, activityList.get(position).getSchema().getId());
                                        startActivity(viewIntent);
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }


                        }
                    })
            );

        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }

    }

    private void callActivityService() {
        try {
            reqJsonObj = new JSONObject();
            reqJsonObj.put(Const.Params.SecurityKey, securityKey);
            if (userType.equalsIgnoreCase("Parent")) {
                reqJsonObj.put(Const.Params.LoginUserId, childId);
            } else {
                reqJsonObj.put(Const.Params.LoginUserId, user_id);

            }
            reqJsonObj.put(Const.Params.DATE, apiDate);
            reqJsonObj.put(Const.Params.Language, language);
            dialog.show();

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(new ToStringConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqJsonObj.toString());
            Call<ScheduleMonthList> response = retrofit.create(API.class).getActivityList(body);
            response.enqueue(new Callback<ScheduleMonthList>() {
                @Override
                public void onResponse(Call<ScheduleMonthList> call, Response<ScheduleMonthList> response) {
                    dialog.dismiss();

                    if (response.body() != null) {
                        ScheduleMonthList scheduleMonthList = response.body();
                        if (scheduleMonthList.getStatus().equalsIgnoreCase(Const.Params.TRUE)) {
                            absenceNote = scheduleMonthList.getAbsent_note_count();
                            retriverNote = scheduleMonthList.getRetriever_note_count();
                            firstName = scheduleMonthList.getFirst_name();
                            lastName = scheduleMonthList.getLast_name();
                            activityList.clear();
                            if (!scheduleMonthList.getSchemaCalendar().isEmpty()) {
                                activityList.addAll(scheduleMonthList.getSchemaCalendar());
                                activityAdapter.notifyDataSetChanged();
                            }
                        } else {


                            SmartClassUtil.showToast(context, scheduleMonthList.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ScheduleMonthList> call, Throwable t) {
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {

            Intent mainIntent = new Intent();
            mainIntent.putExtra(Const.CommonParams.REFRESH, true);
            setResult(RESULT_OK, mainIntent);
            finish();
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
    }

    @OnClick({R.id.backbtnImage, R.id.saveImage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveImage:
                showDialog();
                break;
            case R.id.backbtnImage:
                onBackPressed();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean refresh = false;
        switch (requestCode) {
            case Const.CommonParams.ADDACTIVITY:
                if (resultCode == RESULT_OK && data != null) {
                    refresh = data.getBooleanExtra(Const.CommonParams.REFRESH, false);
                    if (refresh) {
                        modified = true;
                        Intent mainIntent = new Intent();
                        mainIntent.putExtra(Const.CommonParams.REFRESH, modified);
                        setResult(RESULT_OK, mainIntent);
                        finish();
                    } else
                        modified = false;
                }
                break;
            case Const.CommonParams.ADD_ABSENTNOTE:
                if (resultCode == RESULT_OK && data != null) {
                    refresh = data.getBooleanExtra(Const.CommonParams.REFRESH, false);
                    if (refresh)
                        callActivityService();
                }
                break;
            default:
                break;
        }
    }

    private void showDialog() {
        try {
            final Dialog dialogVIew = new Dialog(ActivityListByDateActivity.this);
            dialogVIew.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogVIew.setContentView(R.layout.dialog_add_options);
            dialogVIew.setCancelable(true);
            LinearLayout addActivityLayout = (LinearLayout) dialogVIew.findViewById(R.id.addActivityLayout);
            LinearLayout addVacLayout = (LinearLayout) dialogVIew.findViewById(R.id.addVacLayout);
            LinearLayout addAbsenceLayout = (LinearLayout) dialogVIew.findViewById(R.id.addAbsenceLayout);
            LinearLayout addRetriverLayout = (LinearLayout) dialogVIew.findViewById(R.id.addRetriverLayout);


            TextView activityLabelText = (TextView) dialogVIew.findViewById(R.id.activityLabelText);
            TextView vaccationLbelText = (TextView) dialogVIew.findViewById(R.id.vaccationLbelText);
            TextView absdgLabelText = (TextView) dialogVIew.findViewById(R.id.absdgLabelText);
            TextView retriverText = (TextView) dialogVIew.findViewById(R.id.retriverText);
            if (language.equalsIgnoreCase("sw")) {
                activityLabelText.setText("Skapa bokning");
                vaccationLbelText.setText("Ledighetsansökan");
/*
                retriverText.setText("Lägg Retriever");
*/
            }
            if (userType.equalsIgnoreCase("Parent")) {
                if (retriverNote == 0) {
                    addRetriverLayout.setVisibility(View.GONE);

                } else if (retriverNote == 1) {

                    if (language.equalsIgnoreCase("en")) {
                        retriverText.setText("Retriever Note");
                    } else {
                        retriverText.setText("retriever Obs");
                    }
                    addRetriverLayout.setVisibility(View.VISIBLE);

                } else {
                    if (language.equalsIgnoreCase("en")) {
                        retriverText.setText("Add Retriever");
                    } else {
                        retriverText.setText("Annan hämtare");
                    }
                    addRetriverLayout.setVisibility(View.VISIBLE);
                }
            } else if (userType.equalsIgnoreCase("Teacher")) {
                addRetriverLayout.setVisibility(View.GONE);
            } else {
                if (retriverNote == 1) {

                    if (language.equalsIgnoreCase("en")) {
                        retriverText.setText("Retriever Note");
                    } else {
                        retriverText.setText("retriever Obs");
                    }
                    addRetriverLayout.setVisibility(View.VISIBLE);

                } else {

                    addRetriverLayout.setVisibility(View.GONE);
                }
            }


            if (userType.equalsIgnoreCase("Parent") || userType.equalsIgnoreCase("Teacher")) {
                if (absenceNote == 0) {
                    addAbsenceLayout.setVisibility(View.GONE);

                } else if (absenceNote == 1) {

                    if (language.equalsIgnoreCase("en")) {
                        absdgLabelText.setText("Absence Note");
                    } else {
                        absdgLabelText.setText("frånvaro Obs");
                    }

                    addAbsenceLayout.setVisibility(View.VISIBLE);
                } else {
                    if (language.equalsIgnoreCase("en")) {
                        absdgLabelText.setText("Add Absence Note");
                    } else {
                        absdgLabelText.setText("Frånvaromeddelande");
                    }
                    addAbsenceLayout.setVisibility(View.VISIBLE);

                }

            } else {
                if (absenceNote == 1) {

                    if (language.equalsIgnoreCase("en")) {
                        absdgLabelText.setText("Absence Note");
                    } else {
                        absdgLabelText.setText("frånvaro Obs");
                    }

                    addAbsenceLayout.setVisibility(View.VISIBLE);
                } else {
                    addAbsenceLayout.setVisibility(View.GONE);
                }


                /*addActivityLayout.setVisibility(View.GONE);*/
                addVacLayout.setVisibility(View.GONE);
            }
            addActivityLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogVIew.dismiss();


                    if (("Teacher").equalsIgnoreCase(userType)) {

                        try {
                            if (NetworkUtil.getInstance(context).isInternet()) {
                                Intent addactIntent = new Intent(ActivityListByDateActivity.this, AddActivityActivity.class);
                                addactIntent.putExtra(Const.CommonParams.SELECTED_DATE, selectedDate);
                                startActivityForResult(addactIntent, Const.CommonParams.ADDACTIVITY);
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                    } else if (("Parent").equalsIgnoreCase(userType)) {
                        try {
                            if (NetworkUtil.getInstance(context).isInternet()) {
                                Intent addParentIntent = new Intent(ActivityListByDateActivity.this, AddActivityParent.class);
                                addParentIntent.putExtra(Const.CommonParams.SELECTED_DATE, selectedDate);
                                startActivityForResult(addParentIntent, Const.CommonParams.ADDACTIVITY);
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                    } else {
                        try {
                            if (NetworkUtil.getInstance(context).isInternet()) {
                                Intent addStudentIntent = new Intent(ActivityListByDateActivity.this, AddActivityStudent.class);
                                addStudentIntent.putExtra(Const.CommonParams.SELECTED_DATE, selectedDate);
                                startActivityForResult(addStudentIntent, Const.CommonParams.ADDACTIVITY);
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }

/*                    Intent addactIntent = new Intent(ActivityListByDateActivity.this, AddActivityActivity.class);
                    addactIntent.putExtra(Const.CommonParams.SELECTED_DATE, selectedDate);
                    startActivityForResult(addactIntent, Const.CommonParams.ADDACTIVITY);*/
                }
            });
            addVacLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogVIew.dismiss();
                    if (("Teacher").equalsIgnoreCase(userType)) {
                        Intent vaccIntent = new Intent(ActivityListByDateActivity.this, AddVaccationActivity.class);
                        vaccIntent.putExtra(Const.CommonParams.API_DATE, apiDate);
                        startActivityForResult(vaccIntent, Const.CommonParams.ADDACTIVITY);
                    } else if (("Parent").equalsIgnoreCase(userType)) {
                        Intent vaccPrentIntent = new Intent(ActivityListByDateActivity.this, AddWholedaySickParentActivity.class);
                        vaccPrentIntent.putExtra(Const.CommonParams.API_DATE, apiDate);
                        startActivityForResult(vaccPrentIntent, Const.CommonParams.ADDACTIVITY);
                    }
                }
            });

            addRetriverLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogVIew.dismiss();
                    Intent viewAbsIntent = new Intent(ActivityListByDateActivity.this, AddRetriverParentActivity.class);
                    viewAbsIntent.putExtra(Const.CommonParams.SELECTED_DATE, selectedDate);
                    viewAbsIntent.putExtra(Const.CommonParams.API_DATE, apiDate);
                    startActivityForResult(viewAbsIntent, Const.CommonParams.ADD_ABSENTNOTE);
                   /* if (retriverNote == 1) {

                    } else {
                        Intent addAbsIntent = new Intent(ActivityListByDateActivity.this, AddRetriverParentActivity.class);
                        addAbsIntent.putExtra(Const.CommonParams.SELECTED_DATE, selectedDate);
                        addAbsIntent.putExtra(Const.CommonParams.API_DATE, apiDate);
                        *//*addAbsIntent.putExtra(Const.CommonParams.NAME, firstName + " " + lastName);*//*
                        startActivityForResult(addAbsIntent, Const.CommonParams.ADD_ABSENTNOTE);
                    }*/
                    overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
                }
            });
            addAbsenceLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogVIew.dismiss();
                    if (absenceNote == 1) {
                        Intent viewAbsIntent = new Intent(ActivityListByDateActivity.this, ViewAbsentNoteActivity.class);
                        viewAbsIntent.putExtra(Const.CommonParams.SELECTED_DATE, selectedDate);
                        viewAbsIntent.putExtra(Const.CommonParams.API_DATE, apiDate);
                        startActivityForResult(viewAbsIntent, Const.CommonParams.ADD_ABSENTNOTE);
                    } else {
                        Intent addAbsIntent = new Intent(ActivityListByDateActivity.this, AddAbsenceNoteTeacherActivity.class);
                        addAbsIntent.putExtra(Const.CommonParams.SELECTED_DATE, selectedDate);
                        addAbsIntent.putExtra(Const.CommonParams.API_DATE, apiDate);
                        addAbsIntent.putExtra(Const.CommonParams.NAME, firstName + " " + lastName);
                        startActivityForResult(addAbsIntent, Const.CommonParams.ADD_ABSENTNOTE);
                    }
                    overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
                }
            });
            dialogVIew.show();
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
    }

}
