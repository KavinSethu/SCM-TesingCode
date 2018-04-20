package com.pnf.elar.app.activity.schedule;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.elar.util.SmartClassUtil;
import com.pnf.elar.app.Bo.schedule.FoodNoteBo;
import com.pnf.elar.app.Bo.schedule.ScheduleMonthList;
import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.R;
import com.pnf.elar.app.UILApplication;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.API;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.ToStringConverterFactory;
import com.pnf.elar.app.adapter.schedule.AllergieAdapter;
import com.pnf.elar.app.util.Const;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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

public class FoodNoteActivity extends AppCompatActivity {

    @Bind(R.id.backbtnImage)
    ImageView backbtnImage;
    @Bind(R.id.headerNameText)
    TextView headerNameText;
    @Bind(R.id.saveImage)
    ImageView saveImage;
    @Bind(R.id.foodNoteDateLabelText)
    TextView foodNoteDateLabelText;
    @Bind(R.id.foodnoteDatetext)
    TextView foodnoteDatetext;
    @Bind(R.id.fnAttendanceText)
    TextView fnAttendanceText;
    @Bind(R.id.grpLabelText)
    TextView grpLabelText;
    @Bind(R.id.allGrpRadio)
    RadioButton allGrpRadio;
    @Bind(R.id.myGrpRadio)
    RadioButton myGrpRadio;
    @Bind(R.id.fnRadioGroup)
    RadioGroup fnRadioGroup;
    @Bind(R.id.allergiesLabelText)
    TextView allergiesLabelText;
    @Bind(R.id.allergiesRv)
    RecyclerView allergiesRv;
    @Bind(R.id.scheduledText)
    TextView scheduledText;
    @Bind(R.id.currentlyText)
    TextView currentlyText;


    Context context;
    UserSessionManager session;
    HashMap<String, String> user;
    String language = "", auth_token = "", base_url = "", user_id = "", securityKey = "H67jdS7wwfh", userType = "", childId = "";


    List<FoodNoteBo.FinalStudentsEntity> allergieList = new ArrayList<>();
    AllergieAdapter allergieAdapter;

    JSONObject reqJsonObj;
    MyCustomProgressDialog dialog;

    Calendar startCalendar;
    DatePickerDialog.OnDateSetListener startDateDg;
    String dateVal = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    String groupType = "my_group";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*setTheme(R.style.AppTheme);*/
        setTheme(R.style.AppTheme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_foot_note);
        ButterKnife.bind(this);
        getSessionValues();
        initView();
        callAllergieService();
    }

    public void initView() {
        try {
            if (language.equalsIgnoreCase("sw")) {
                headerNameText.setText("Pedagogisk Lunch");
                fnAttendanceText.setText("Närvaro");
                foodNoteDateLabelText.setText("Datum");
                grpLabelText.setText("Sortering");
                allGrpRadio.setText("Alla grupper");
                myGrpRadio.setText("Mina grupper");
                allergiesLabelText.setText("Allergier");
                scheduledText.setText("Planerad");
                currentlyText.setText("För närvarande");
            } else {
                headerNameText.setText(" Food Notes ");

            }

            /*saveImage.setVisibility(View.VISIBLE);*/

            dialog = new MyCustomProgressDialog(FoodNoteActivity.this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);


            setAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            if (("Parent").equalsIgnoreCase(userType)) {
                childId = user.get(UserSessionManager.TAG_child_id);
            } else {

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAdapter() {
        try {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            allergiesRv.setLayoutManager(layoutManager);
            allergieAdapter = new AllergieAdapter(context, allergieList, base_url);
            allergiesRv.setAdapter(allergieAdapter);


            fnRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i) {
                        case R.id.allGrpRadio:
                            clearValues();
                            groupType = "all_group";
                            callAllergieService();
                            break;
                        case R.id.myGrpRadio:
                            clearValues();
                            groupType = "my_group";
                            callAllergieService();
                            break;
                        default:
                            break;
                    }

                }
            });


            startCalendar = Calendar.getInstance();
            dateVal = sdf.format(startCalendar.getTime());
            foodnoteDatetext.setText(sdf.format(startCalendar.getTime()));
            startDateDg = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    try {
                        clearValues();
                        startCalendar.set(Calendar.YEAR, year);
                        startCalendar.set(Calendar.MONTH, monthOfYear);
                        startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        dateVal = sdf.format(startCalendar.getTime());
                        foodnoteDatetext.setText(sdf.format(startCalendar.getTime()));
                        callAllergieService();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            };

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearValues() {
        allergieList.clear();
        allergieAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.backbtnImage, R.id.saveImage, R.id.foodnoteDatetext})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backbtnImage:
                finish();
                break;
            case R.id.saveImage:
                callAllergieService();
                break;
            case R.id.foodnoteDatetext:
                final Context themedContext = new ContextThemeWrapper(
                        FoodNoteActivity.this,
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
                break;
        }
    }

    public void callAllergieService() {

        try {
            reqJsonObj = new JSONObject();
            reqJsonObj.put(Const.Params.SecurityKey, securityKey);
            if (("Parent").equalsIgnoreCase(userType)) {
                reqJsonObj.put(Const.Params.LoginUserId, childId);
            } else {reqJsonObj.put(Const.Params.LoginUserId, user_id);

            }


            reqJsonObj.put(Const.Params.DATE, dateVal);
            reqJsonObj.put(Const.Params.GROUPTYPE, groupType);
            reqJsonObj.put(Const.Params.Language, language);
/*
            {"securityKey":"H67jdS7wwfh","loginUserID":"44","date":"2016-12-29","language":"en","groupType":"my_group"}
*/
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
            Call<FoodNoteBo> response = retrofit.create(API.class).getFootNote(body);
            response.enqueue(new Callback<FoodNoteBo>() {
                @Override
                public void onResponse(Call<FoodNoteBo> call, Response<FoodNoteBo> response) {
                    dialog.dismiss();

                    if (response.body() != null) {
                        FoodNoteBo foodNoteBo = response.body();
                        if (foodNoteBo.getStatus().equalsIgnoreCase(Const.Params.TRUE)) {

                            if (language.equalsIgnoreCase("en")) {
                                scheduledText.setText("Schedule : " + foodNoteBo.getStudent_count_without_absent_dates());
                                currentlyText.setText("Currently : " + foodNoteBo.getPresent_student_count());
                            } else {
                                scheduledText.setText("Planerad : " + foodNoteBo.getStudent_count_without_absent_dates());
                                currentlyText.setText("För närvarande : " + foodNoteBo.getPresent_student_count());
                            }

                            if (foodNoteBo.getFinal_students().size() > 0) {
                                allergieList.addAll(foodNoteBo.getFinal_students());
                                allergieAdapter.notifyDataSetChanged();
                            }
                        } else {


                            SmartClassUtil.showToast(context, foodNoteBo.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<FoodNoteBo> call, Throwable t) {
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
