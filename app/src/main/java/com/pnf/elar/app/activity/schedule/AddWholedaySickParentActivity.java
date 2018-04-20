package com.pnf.elar.app.activity.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elar.util.SmartClassUtil;
import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.R;
import com.pnf.elar.app.UILApplication;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.API;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.ToStringConverterFactory;
import com.pnf.elar.app.util.AppLog;
import com.pnf.elar.app.util.Const;

import org.json.JSONObject;

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

public class AddWholedaySickParentActivity extends AppCompatActivity {

    @Bind(R.id.wholeDayText)
    TextView wholeDayText;
    @Bind(R.id.closeSickImage)
    ImageView closeSickImage;
    @Bind(R.id.descLabelText)
    TextView descLabelText;
    @Bind(R.id.descriptionEditText)
    EditText descriptionEditText;
    @Bind(R.id.saveBtn)
    Button saveBtn;
    @Bind(R.id.activity_add_vaccation_parent)
    RelativeLayout activityAddVaccationParent;


    String tag = "vaccation for Parent";
    UserSessionManager session;
    HashMap<String, String> user;
    String language = "", auth_token = "", baseUrl = "", userId = "", securityKey = "H67jdS7wwfh", userType = "", childId = "";
    Context context;
    String apiDate;

    JSONObject reqJsonObj;
    MyCustomProgressDialog dialogLoading;
    boolean refresh=false;

    String description="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vaccation_parent);
        ButterKnife.bind(this);
        getSeeionValues();
        setLanguage();

        getWholeDaySIckApi();
    }


    public void getSeeionValues() {


        try {
            context = getApplicationContext();
            session = UILApplication.getUserSessionManager(context);
            user = session.getUserDetails();
            language = user.get(UserSessionManager.TAG_language);
            auth_token = user.get(UserSessionManager.TAG_Authntication_token);
            baseUrl = user.get(UserSessionManager.TAG_Base_url);
            userId = user.get(UserSessionManager.TAG_user_id);
            userType = user.get(UserSessionManager.TAG_user_type);
            if (userType.equalsIgnoreCase("Parent")) {
                childId = user.get(UserSessionManager.TAG_child_id);
            }
            apiDate = getIntent().getStringExtra(Const.CommonParams.API_DATE);

            dialogLoading = new MyCustomProgressDialog(AddWholedaySickParentActivity.this);
            dialogLoading.setIndeterminate(true);
            dialogLoading.setCancelable(false);

        } catch (Exception e) {
            AppLog.handleException(tag, e);
        }
    }

    public void setLanguage()

    {
        if (("en").equalsIgnoreCase(language)) {
            wholeDayText.setText("Mark sjuk hela dagen");
            descriptionEditText.setText("Beskrivning:");
            saveBtn.setText("Spara ändringar");
            descriptionEditText.setHint("Vänligen lämna anteckningar här");
        }

    }

    @OnClick(R.id.saveBtn)
    public void onClick() {

        description=descriptionEditText.getText().toString().trim();
        if (description.length() == 0) {
            if (("en").equalsIgnoreCase(language)) {
                descLabelText.setError("Please fill out this field");
            } else {

                descLabelText.setError("Var vänlig fyll i det här fältet");

            }

        }
        else
        {
            callVaccationApi();
        }

    }


    private void callVaccationApi() {
        try {


            if (SmartClassUtil.isNetworkAvailable(context)) {

                    /*callAspectsApi();*/

                reqJsonObj = new JSONObject();
                reqJsonObj.put(Const.Params.SecurityKey, securityKey);
                reqJsonObj.put(Const.Params.LoginUserId, userId);
                reqJsonObj.put(Const.Params.Language, language);
                reqJsonObj.put(Const.Params.SickDescription,description);
                reqJsonObj.put(Const.Params.SickDate,apiDate);
                reqJsonObj.put(Const.Params.StudentId,childId);
                reqJsonObj.put(Const.Params.isChecked,"");

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
                Call<String> response = retrofit.create(API.class).addwholedaysickforParent(body);
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
                                    if (activityJson.getString(Const.Params.Status).equalsIgnoreCase(Const.Params.success)) {
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

    private void getWholeDaySIckApi() {
        try {


            if (SmartClassUtil.isNetworkAvailable(context)) {

                    /*callAspectsApi();*/

                reqJsonObj = new JSONObject();
                reqJsonObj.put(Const.Params.SecurityKey, securityKey);
                reqJsonObj.put(Const.Params.LoginUserId, userId);
                reqJsonObj.put(Const.Params.Language, language);
                reqJsonObj.put(Const.Params.SickDate,apiDate);
                reqJsonObj.put(Const.Params.StudentId,childId);


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
                Call<String> response = retrofit.create(API.class).getWholedaySickdetailsforstudent(body);
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
                                        if (activityJson.has(Const.Params.MSG)) {
                                            descriptionEditText.setText(activityJson.getString(Const.Params.MSG));
                                        }
                                    } else {

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
