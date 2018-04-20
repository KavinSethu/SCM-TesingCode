package com.pnf.elar.app.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.elar.util.SmartClassUtil;
import com.pnf.elar.app.Drawer;
import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.R;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.API;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.ToStringConverterFactory;
import com.pnf.elar.app.util.Const;

import org.json.JSONObject;

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

import static android.app.Activity.RESULT_OK;

/**
 * Created by pmohan on 18-08-2017.
 */

public class EduBlogWebFragment extends Fragment {

    Animation animFadein;
    View v;
    String lang,user_typ,Base_url,securityKey = "H67jdS7wwfh",user_id = "";
    UserSessionManager session;
    WebView webView;
    MyCustomProgressDialog dialog;
    Context context;
    private ValueCallback<Uri> mUploadMessage;
    private Uri mCapturedImageURI = null;
    private static final int FILECHOOSER_RESULTCODE = 1;
    private static final int INPUT_FILE_REQUEST_CODE = 1;
    public static final int REQUEST_SELECT_FILE = 100;
    private ValueCallback<Uri[]> uploadMessage;
    Activity activity;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity();
        activity = getActivity();

        v = inflater.inflate(R.layout.webview_main, container, false);

        session = new UserSessionManager(activity);

        animFadein = AnimationUtils.loadAnimation(activity,
                R.anim.slide_up);

        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        user_typ = user.get(UserSessionManager.TAG_user_type);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        user_id = user.get(UserSessionManager.TAG_user_id);

        if (lang.equalsIgnoreCase("sw")) {
            ((Drawer) activity).setActionBarTitle("Läroblogg");
        } else {
            ((Drawer) activity).setActionBarTitle("Edu Blog");
        }
        ((Drawer) activity).showbackbutton();
        if (user_typ.equalsIgnoreCase("Parent")) {
            ((Drawer) activity).setBackForChildEduedu();
            ((Drawer) activity).Hideserch();
            ((Drawer) activity).HideRefresh();
            user_id = user.get(UserSessionManager.TAG_child_id);

        } else {

            ((Drawer) activity).Hideserch();
            ((Drawer) activity).HideRefresh();
            ((Drawer) activity).Backtomain();


            user_id = user.get(UserSessionManager.TAG_user_id);
        }

        webView = (WebView) v.findViewById(R.id.webview);
        dialog = new MyCustomProgressDialog(activity);
        webView.setScrollbarFadingEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        // Enable Javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

//        webView.setInitialScale(1);
        Log.d("LMS","Base url is ---> "+Base_url);
        loadEduBlogAPI();
        return v;
    }
    private class myWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
//            dialog = new MyCustomProgressDialog(getActivity());
            if(!dialog.isShowing()) {
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();
                view.loadUrl(url);
            }
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, final String url) {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            },2000);
            super.onPageFinished(view, url);
        }

    }

    public class myWebChromeClient extends WebChromeClient {
        public void openFileChooser( ValueCallback uploadMsg, String acceptType ) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            EduBlogWebFragment.this.startActivityForResult(
                    Intent.createChooser(i, "File Browser"),
                    FILECHOOSER_RESULTCODE);
        }

        public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams)
        {
            if (uploadMessage != null) {
                uploadMessage.onReceiveValue(null);
                uploadMessage = null;
            }

            uploadMessage = filePathCallback;

            Intent intent = fileChooserParams.createIntent();
            try
            {
                startActivityForResult(intent, REQUEST_SELECT_FILE);
            } catch (Exception e)
            {
                uploadMessage = null;
                Toast.makeText(activity, "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    private void loadEduBlogAPI() {
        try {
            dialog.show();

            if (SmartClassUtil.isNetworkAvailable(context)) {

                    /*callAspectsApi();*/

                JSONObject reqJsonObj = new JSONObject();
                reqJsonObj.put(Const.Params.SecurityKey, securityKey);
                reqJsonObj.put(Const.Params.LoginUserId, user_id);
                reqJsonObj.put(Const.Params.Language, lang);
                reqJsonObj.put(Const.Params.Component_Name,"edublog");

                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Base_url)
                        .addConverterFactory(new ToStringConverterFactory())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqJsonObj.toString());
                Call<String> response = retrofit.create(API.class).loadWebView(body);
                response.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        dialog.dismiss();
                        try {
                            if (response.body() != null) {
                                String activityResponse = response.body();
                                JSONObject activityJson = new JSONObject();
                                Log.d("LMS","EduBlogWebFragment response ---> "+activityResponse);
                                if (activityResponse != null && activityResponse.trim().length() != 0) {

                                    activityJson = new JSONObject(activityResponse);
                                    if (activityJson.getString(Const.Params.authentication_number_scm)!= null && !activityJson.getString(Const.Params.authentication_number_scm).isEmpty()) {
                                        Log.d("LMS","EduBlogWebFragment response authentication token is--->  "+activityJson.getString(Const.Params.authentication_number_scm));
                                        String eduURL = Base_url+"?authentication_token_SCM="+activityJson.getString(Const.Params.authentication_number_scm)+"&component_name=edublog";
                                        Log.d("LMS","EduBlogWebFragment URL is --->  "+eduURL);
                                        webView.setWebViewClient(new EduBlogWebFragment.myWebViewClient());
                                        webView.setWebChromeClient(new EduBlogWebFragment.myWebChromeClient());
                                        webView.loadUrl(eduURL);
                                    } else {

                                    }


                                } else {
                                    if(lang.equalsIgnoreCase("en")) {
                                        SmartClassUtil.showToast(context, "Service Failed, Please Try Again");
                                    }else{
                                        SmartClassUtil.showToast(context, "Tjänsten misslyckades, försök igen");
                                    }
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        dialog.dismiss();
                    }
                });

            } else {
                dialog.dismiss();
                if(lang.equalsIgnoreCase("en")) {
                    SmartClassUtil.showToast(context, "No Internet Connection");
                }else{
                    SmartClassUtil.showToast(context, "Ingen internetanslutning");
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            if (requestCode == REQUEST_SELECT_FILE)
            {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                uploadMessage = null;
            }
        } else  {
            if(requestCode==FILECHOOSER_RESULTCODE)
            {
                if (null == mUploadMessage) return;
                Uri result = intent == null || resultCode != RESULT_OK ? null
                        : intent.getData();
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);



        if (context instanceof Activity){
            activity=(Activity) context;
        }

    }
}
