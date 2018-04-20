package com.pnf.elar.app.activity.schedule;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.elar.util.SmartClassUtil;
import com.pnf.elar.app.Bo.schedule.FoodMenuBo;
import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.R;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.API;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.ToStringConverterFactory;
import com.pnf.elar.app.util.AppLog;
import com.pnf.elar.app.util.Const;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddVaccationActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView backbtn;
    ImageView saveImage;
    TextView headerNameText;
    TextView startHeaderText1;
    TextView startDateText1;
    TextView endDateHeaderText1;
    TextView endDateText1;
    TextView descripNoteText;
    EditText descripNoteEditText;

    Calendar startCalendar;
    DatePickerDialog.OnDateSetListener startDateDg;
    Calendar enddateCalendar;
    DatePickerDialog.OnDateSetListener endDateDg;
    UserSessionManager session;
    HashMap<String, String> user;
    String language = "";
    String authToken = "";
    String baseUrl = "";
    String securityKey = "H67jdS7wwfh";
    String userId = "";
    Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String description = "";
    String startDateVal = "";
    String endDateVal = "";
    String swEndDateError = "Välj giltigt slutdatum";
    String enEndDateError = "Select valid end date";
    boolean refresh = false;
   String selectedDate = "";
    JSONObject reqJsonObj;
    MyCustomProgressDialog dialogLoading;
    String vaction = "Vacation";
    String Tag = vaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vaccation);
        initView();
    }
    public void getSessionValue() {
        try {
            context = getApplicationContext();
            session = new UserSessionManager(this);
            user = session.getUserDetails();
            language = user.get(UserSessionManager.TAG_language);
            authToken = user.get(UserSessionManager.TAG_Authntication_token);
            baseUrl = user.get(UserSessionManager.TAG_Base_url);
            userId = user.get(UserSessionManager.TAG_user_id);
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }
    }
    private void initView() {
        try {
            getSessionValue();
            backbtn = (ImageView) findViewById(R.id.backbtnImage);
            saveImage = (ImageView) findViewById(R.id.saveImage);
            headerNameText = (TextView) findViewById(R.id.headerNameText);
            startHeaderText1 = (TextView) findViewById(R.id.startHeaderText1);
            endDateHeaderText1 = (TextView) findViewById(R.id.endDateHeaderText1);
            startDateText1 = (TextView) findViewById(R.id.startDateText1);
            endDateText1 = (TextView) findViewById(R.id.endDateText1);
            descripNoteText = (TextView) findViewById(R.id.descripNoteText);
            descripNoteEditText = (EditText) findViewById(R.id.descripNoteEditText);
            selectedDate = getIntent().getStringExtra(Const.CommonParams.API_DATE);
            setStartEndDate();
            setOnClickListeners();
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }

    }

    private void setOnClickListeners() {
        try {
            setLanguages();
            backbtn.setOnClickListener(this);
            saveImage.setOnClickListener(this);
            startDateText1.setOnClickListener(this);
            endDateText1.setOnClickListener(this);
            saveImage.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            AppLog.handleException(Tag, e);
        }

    }

    private void setLanguages() {
        try {
            if (language.equalsIgnoreCase("en")) {
                startHeaderText1.setText("Start Date");
                endDateHeaderText1.setText("End Date");
                headerNameText.setText("Add Vacation");
                descripNoteText.setText("Description");
                vaction = "Vacation";
            } else {
                startHeaderText1.setText("Start datum");
                endDateHeaderText1.setText("Slutdatum");
                headerNameText.setText("lägga vacation");
                descripNoteText.setText("Beskrivning");
                vaction = "Semester";

            }


            dialogLoading = new MyCustomProgressDialog(AddVaccationActivity.this);
            dialogLoading.setIndeterminate(true);
            dialogLoading.setCancelable(false);
        } catch (Exception e) {
            AppLog.handleException(Tag,e);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtnImage:
                onBackPressed();
                break;
            case R.id.startDateText1:
                try {
                    new DatePickerDialog(AddVaccationActivity.this, startDateDg, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH)).show();
                } catch (Exception e) {AppLog.handleException(Tag, e);}
                break;
            case R.id.endDateText1:
                try {
                    new DatePickerDialog(AddVaccationActivity.this, endDateDg, enddateCalendar.get(Calendar.YEAR), enddateCalendar.get(Calendar.MONTH), enddateCalendar.get(Calendar.DAY_OF_MONTH)).show();
                } catch (Exception e) {
                    AppLog.handleException(Tag, e);}
                break;
            case R.id.saveImage:
                checkValidation();
                break;
        }
    }

    private void checkValidation() {
        try {
            description = descripNoteEditText.getText().toString().trim();
            if (enddateCalendar.getTime().before(startCalendar.getTime())) {
                if (("en").equals(language)) {
                    SmartClassUtil.showToast(getApplicationContext(), enEndDateError);
                } else {
                    SmartClassUtil.showToast(getApplicationContext(), swEndDateError);
                }
            } else if (description.length() == 0) {
                if (("en").equals(language)) {
                    SmartClassUtil.showToast(getApplicationContext(), "Enter valid description");
                } else {
                    SmartClassUtil.showToast(getApplicationContext(), "Ange giltig beskrivning");
                }
            } else {
                callAddVacationService();
            }
        }catch (Exception e)
        {
            AppLog.handleException(Tag,e);
        }

    }

    private void setStartEndDate() {
        try {

            Date selDate = sdf.parse(selectedDate);
            startCalendar = Calendar.getInstance();
            startCalendar.setTime(selDate);
            startDateVal = sdf.format(startCalendar.getTime());
            startDateText1.setText(sdf.format(startCalendar.getTime()));
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
                        startDateText1.setText(sdf.format(startCalendar.getTime()));
                        enddateCalendar.setTime(sdf.parse(startDateVal));
                        endDateVal = sdf.format(enddateCalendar.getTime());
                        endDateText1.setText(sdf.format(enddateCalendar.getTime()));
                    } catch (Exception e) {
                        AppLog.handleException(Tag, e);
                    }
                }
            };
            enddateCalendar = Calendar.getInstance();
            enddateCalendar.setTime(selDate);
            endDateVal = sdf.format(enddateCalendar.getTime());
            endDateText1.setText(sdf.format(enddateCalendar.getTime()));
            endDateDg = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    try {
                        // TODO Auto-generated method stub
                        enddateCalendar.set(Calendar.YEAR, year);
                        enddateCalendar.set(Calendar.MONTH, monthOfYear);
                        enddateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        Date endDate = sdf.parse(sdf.format(enddateCalendar.getTime()));
                        Date startDate = sdf.parse(sdf.format(startCalendar.getTime()));
                        if (endDate.before(startDate)) {
                            if (("en").equals(language)) {
                                SmartClassUtil.showToast(getApplicationContext(), enEndDateError);
                            } else {
                                SmartClassUtil.showToast(getApplicationContext(), swEndDateError);
                            }
                            enddateCalendar.setTime(startCalendar.getTime());
                        } else {
                        }
                        endDateVal = sdf.format(enddateCalendar.getTime());
                        endDateText1.setText(sdf.format(enddateCalendar.getTime()));
                    } catch (Exception e) {
                        AppLog.handleException(Tag,e);
                    }
                }
            };
        } catch (Exception e) {
            AppLog.handleException(Tag,e);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra(Const.CommonParams.REFRESH, refresh);
        setResult(RESULT_OK, intent);
    }

    private void callAddVacationService() {
        try {
            reqJsonObj = new JSONObject();
            reqJsonObj.put(Const.Params.SecurityKey, securityKey);
            reqJsonObj.put(Const.Params.LoginUserId, userId);
            reqJsonObj.put(Const.Params.FROMDATEVACATION, startDateVal);
            reqJsonObj.put(Const.Params.TODATEVACATION, endDateVal);
            reqJsonObj.put(Const.Params.REQDESCRIPTION, description);
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
            Call<FoodMenuBo> response = retrofit.create(API.class).addVacation(body);
            response.enqueue(new Callback<FoodMenuBo>() {
                @Override
                public void onResponse(Call<FoodMenuBo> call, Response<FoodMenuBo> response) {
                    dialogLoading.dismiss();
                    if (response.body() != null) {
                        FoodMenuBo foodMenuBo = response.body();
                        if (foodMenuBo.getStatus().equalsIgnoreCase(Const.Params.TRUE)) {
                            SmartClassUtil.showToast(context, vaction + " " + foodMenuBo.getMessage());
                            onBackPressed();
                        } else {
                            SmartClassUtil.showToast(context, foodMenuBo.getMessage());
                        }
                    }
                }
                @Override
                public void onFailure(Call<FoodMenuBo> call, Throwable t) {
                    dialogLoading.dismiss();
                }
            });
        } catch (Exception e) {
            AppLog.handleException(Tag,e);
        }
    }
}
