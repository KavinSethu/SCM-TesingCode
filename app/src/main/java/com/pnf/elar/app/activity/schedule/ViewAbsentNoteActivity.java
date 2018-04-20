package com.pnf.elar.app.activity.schedule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elar.util.NetworkUtil;
import com.elar.util.SmartClassUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pnf.elar.app.Bo.schedule.GetAbsentNoteBo;
import com.pnf.elar.app.Bo.schedule.ScheduleMonthList;
import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.R;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.API;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.ToStringConverterFactory;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.AppLog;
import com.pnf.elar.app.util.Const;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URLEncoder;
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

public class ViewAbsentNoteActivity extends AppCompatActivity {

    @Bind(R.id.closeAbsImage)
    ImageView closeAbsImage;
    @Bind(R.id.headerAbsText)
    TextView headerAbsText;
    @Bind(R.id.nameAbsText)
    TextView nameAbsText;
    @Bind(R.id.desclabelText)
    TextView desclabelText;
    @Bind(R.id.descAbsText)
    TextView descAbsText;
    @Bind(R.id.writtenLabelText)
    TextView writtenLabelText;
    @Bind(R.id.writtenText)
    TextView writtenText;
    @Bind(R.id.activity_view_absent_note)
    RelativeLayout activityViewAbsentNote;
    @Bind(R.id.deleteImage)
    ImageView deleteImage;


    UserSessionManager session;
    HashMap<String, String> user;
    String language, auth_token, base_url, securityKey = "H67jdS7wwfh", user_id = "", regId = "", firstName = "", lastName = "", apiDate = "", userType = "";
    Context context;
    String childId = "";
    String sickId = "";


    String tag = "View Note";
    MyCustomProgressDialog dialog;
    boolean refresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_absent_note);
        ButterKnife.bind(this);
        getSessionValues();
    }

    @OnClick({R.id.closeAbsImage, R.id.deleteImage})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.closeAbsImage:
                onBackPressed();
                break;
            case R.id.deleteImage:
                try {
                    if (NetworkUtil.getInstance(context).isInternet()) {
                        callDeleteApi();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    public void getSessionValues() {
        try {
            context = getApplicationContext();
            session = new UserSessionManager(this);
            user = session.getUserDetails();
            language = user.get(UserSessionManager.TAG_language);
            auth_token = user.get(UserSessionManager.TAG_Authntication_token);
            base_url = user.get(UserSessionManager.TAG_Base_url);
            userType = user.get(UserSessionManager.TAG_user_type);
            user_id = user.get(UserSessionManager.TAG_user_id);
            if (userType.equalsIgnoreCase("Parent")) {
                /*user_id = user.get(UserSessionManager.tag_child_id);*/
                childId = user.get(UserSessionManager.TAG_child_id);
            } else {


            }


            regId = user.get(UserSessionManager.TAG_regId);
            firstName = user.get(UserSessionManager.TAG_USR_FirstName);
            lastName = user.get(UserSessionManager.TAG_USR_LastName);
            apiDate = getIntent().getStringExtra(Const.CommonParams.API_DATE);

            dialog = new MyCustomProgressDialog(ViewAbsentNoteActivity.this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            setLanguageVariables();


        } catch (Exception e) {
            AppLog.handleException(tag, e);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            Intent mainIntent = new Intent();
            mainIntent.putExtra(Const.CommonParams.REFRESH, false);
            setResult(RESULT_OK, mainIntent);
            finish();
        } catch (Exception e) {
            AppLog.handleException(tag, e);
        }
    }

    public void setLanguageVariables() {
        try {
            if (language.equalsIgnoreCase("sw")) {
                headerAbsText.setText("frånvarande anteckning");
                desclabelText.setText("Beskrivning : ");
                writtenLabelText.setText("Skriven av  : ");
            } else {

            }

            new GetAbsentNote().execute();

        } catch (Exception e) {
            AppLog.handleException(tag, e);
        }
    }

    class GetAbsentNote extends AsyncTask<String, String, String> {

        private String url = base_url + "lms_api/retrivals/get_absence_note",
                status = "", message = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog.show();

        }

        protected String doInBackground(String... args) {

            String updataResponse = "";
            try {
                String loginuserId = "";


                if (userType.equalsIgnoreCase("Parent")) {
                    loginuserId = childId;
                } else {
                    loginuserId = user_id;
                }


                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(securityKey, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(language, "UTF-8") +
                        "&" + Const.Params.StudentId + "=" + URLEncoder.encode(loginuserId, "UTF-8") +
                        "&" + Const.Params.UserTypeApp + "=" + URLEncoder.encode("android", "UTF-8") +
                        "&" + Const.Params.SickDate + "=" + URLEncoder.encode(apiDate, "UTF-8");
                updataResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                AppLog.handleException(tag, e);
            }
            return updataResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {

                if (results != null && !results.isEmpty()) {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();


                    GetAbsentNoteBo getAbsentNoteBo = gson.fromJson(results, GetAbsentNoteBo.class);

                    if (getAbsentNoteBo.getStatus().equalsIgnoreCase(Const.Params.TRUE)) {
                        if (language.equalsIgnoreCase("en")) {
                            headerAbsText.setText("Absent note  - " + getAbsentNoteBo.getData().getSickdate());

                        } else {
                            headerAbsText.setText("frånvarande anteckning - " + getAbsentNoteBo.getData().getSickdate());
                        }
                        nameAbsText.setText(getAbsentNoteBo.getData().getStudent_name());
                        descAbsText.setText(getAbsentNoteBo.getData().getDescription());
                        writtenText.setText(getAbsentNoteBo.getData().getWritten_by() + " ," + getAbsentNoteBo.getData().getCreated());
                        sickId = getAbsentNoteBo.getData().getWholedaysick_id();

                        if (userType.equalsIgnoreCase("Parent") || userType.equalsIgnoreCase("Teacher")) {
                            deleteImage.setVisibility(View.VISIBLE);
                        } else {
                            deleteImage.setVisibility(View.GONE);
                        }


                    } else {

                    }


                } else {
                }
            } catch (Exception e) {
                AppLog.handleException(tag, e);
            }
        }
    }

    private void callDeleteApi() {
        try {
            dialog.show();

            if (SmartClassUtil.isNetworkAvailable(context)) {

                    /*callAspectsApi();*/

                JSONObject reqJsonObj = new JSONObject();
                reqJsonObj.put(Const.Params.SecurityKey, securityKey);
                reqJsonObj.put(Const.Params.LoginUserId, user_id);
                reqJsonObj.put(Const.Params.Language, language);
                reqJsonObj.put(Const.Params.ID, sickId);

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
                Call<String> response = retrofit.create(API.class).delete_absent_desc(body);
                response.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        dialog.dismiss();
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
                            AppLog.handleException(tag, e);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        dialog.dismiss();
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
