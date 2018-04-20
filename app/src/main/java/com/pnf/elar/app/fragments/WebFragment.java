package com.pnf.elar.app.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.io.File;
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
import static android.content.Context.DOWNLOAD_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by pmohan on 10-04-2017.
 */

public class WebFragment extends Fragment {

    Animation animFadein;
    View v;
    String lang, user_typ, Base_url, securityKey = "H67jdS7wwfh", user_id = "", child_id = "";
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
    Bundle bundle;
    String componentName, title_en, title_sw;
    public static boolean flag = false;
    String forumURL;
    ImageView backbutton;
    TextView smartnotes;

    ViewGroup actionBarLayout;
    LinearLayout actionbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity();
        activity = getActivity();

        v = inflater.inflate(R.layout.webview_main, container, false);

        bundle = this.getArguments();
        componentName = bundle.getString("Component_Key");
        title_en = bundle.getString("name_en");
        title_sw = bundle.getString("name_sw");

        session = new UserSessionManager(activity);

        animFadein = AnimationUtils.loadAnimation(activity,
                R.anim.slide_up);

        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        user_typ = user.get(UserSessionManager.TAG_user_type);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        user_id = user.get(UserSessionManager.TAG_user_id);
        child_id = user.get(UserSessionManager.TAG_child_id);

        webView = (WebView) v.findViewById(R.id.webview);
        dialog = new MyCustomProgressDialog(activity);

        smartnotes = (TextView) ((Drawer) getActivity()).findViewById(R.id.text1);
        smartnotes.setVisibility(View.GONE);


        if (lang.equalsIgnoreCase("sw")) {
            ((Drawer) activity).setActionBarTitle(title_sw);
        } else {
            ((Drawer) activity).setActionBarTitle(title_en);

        }

        actionBarLayout = (ViewGroup)getActivity().getLayoutInflater().inflate(
                R.layout.actionbar, null);
        actionbar = (LinearLayout)actionBarLayout.findViewById(R.id.actionbar);
        backbutton = (ImageView) actionBarLayout.findViewById(R.id.img);
        ((Drawer) activity).showbackbutton();



        if (user_typ.equalsIgnoreCase("Parent")) {
//            ((Drawer) activity).setBackForChildEduedu();
//            ((Drawer) activity).Backtomain();
            ((Drawer) activity).Hideserch();
            ((Drawer) activity).HideRefresh();
            user_id = user.get(UserSessionManager.TAG_user_id);

        } else {

            ((Drawer) activity).Hideserch();
            ((Drawer) activity).HideRefresh();
            ((Drawer) activity).Backtomain();


            user_id = user.get(UserSessionManager.TAG_user_id);
        }


                backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webView.canGoBack()) {
                    webView.goBack();
                }
                else {
                    if (user_typ.equalsIgnoreCase("Parent")) {
                        ((Drawer) activity).showParentMain();
//                        Toast.makeText(context, "hai", Toast.LENGTH_SHORT).show();
                       
                    } else {
                        ((Drawer) activity).Backtomain();
//                        getActivity().onBackPressed();


                    }
                }
            }
        });



        webView = (WebView) v.findViewById(R.id.webview);
        dialog = new MyCustomProgressDialog(activity);
        webView.setScrollbarFadingEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(true);
        }
        // Enable Javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                request.setMimeType(mimetype);
                //------------------------COOKIE!!------------------------
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                //------------------------COOKIE!!------------------------
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Downloading file...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                request.setVisibleInDownloadsUi(true);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, URLUtil.guessFileName(url, contentDisposition, mimetype));
                DownloadManager dm = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getActivity(), "Downloading File..", Toast.LENGTH_LONG).show();
                Log.d("LMS", "mime Type is ---> " + mimetype);
                Log.d("LMS", "contentDisposition is ---> " + contentDisposition);
                Log.d("LMS", "url is ---> " + URLUtil.guessFileName(url, contentDisposition, mimetype));
                String fileType = URLUtil.guessFileName(url, contentDisposition, mimetype);
                if (fileType.contains("jpg")) {
                    Toast.makeText(getActivity(), "Image Downloaded Into Gallery", Toast.LENGTH_SHORT).show();
                } else if (mimetype.equalsIgnoreCase("pdf")) {
                    Toast.makeText(getActivity(), "PDF File Downloaded", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "File Downloaded Successfully", Toast.LENGTH_SHORT).show();
                }

//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.parse(url), mimetype);
//                getActivity().startActivity(intent);

            }
        });
//        webView.setInitialScale(1);






        Log.d("LMS", "Base url is ---> " + Base_url);
        loadForumAPI();
        return v;
    }

    private class myWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
//            dialog = new MyCustomProgressDialog(activity);
            if (!dialog.isShowing()) {
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
//                view.loadUrl(url);
            }
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

//            view.clearCache(true);
//            view.loadUrl("about:blank");
            if (url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse(url));
                startActivity(intent);
            } else if (url.startsWith("http:") || url.startsWith("https:")) {
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, final String url) {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }, 2000);
            super.onPageFinished(view, url);
        }

    }

    private class myWebChromeClient extends WebChromeClient {
        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            WebFragment.this.startActivityForResult(
                    Intent.createChooser(i, "File Browser"),
                    FILECHOOSER_RESULTCODE);
        }

        public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            if (uploadMessage != null) {
                uploadMessage.onReceiveValue(null);
                uploadMessage = null;
            }

            uploadMessage = filePathCallback;

            Intent intent = fileChooserParams.createIntent();
            try {
                startActivityForResult(intent, REQUEST_SELECT_FILE);
            } catch (Exception e) {
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
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void loadForumAPI() {
        try {
            dialog.show();

            if (SmartClassUtil.isNetworkAvailable(context)) {

                    /*callAspectsApi();*/

                JSONObject reqJsonObj = new JSONObject();
                reqJsonObj.put(Const.Params.SecurityKey, securityKey);
                if ((componentName.equalsIgnoreCase("quiz_manager") || componentName.equalsIgnoreCase("examination")) && user_typ.equalsIgnoreCase("Parent")) {

                    reqJsonObj.put(Const.Params.LoginUserId, child_id);

                } else {
                    reqJsonObj.put(Const.Params.LoginUserId, user_id);
                }
                reqJsonObj.put(Const.Params.Language, lang);
                reqJsonObj.put(Const.Params.Component_Name, componentName);

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
                                Log.d("LMS", "GetAuthenticationToken response ---> " + activityResponse);
                                if (activityResponse != null && activityResponse.trim().length() != 0) {

                                    activityJson = new JSONObject(activityResponse);
                                    if (activityJson.getString(Const.Params.authentication_number_scm) != null && !activityJson.getString(Const.Params.authentication_number_scm).isEmpty()) {
                                        Log.d("LMS", "GetAuthenticationToken response authentication token is--->  " + activityJson.getString(Const.Params.authentication_number_scm));
                                        if (componentName.equalsIgnoreCase("absence_noteP")) {
                                            forumURL = Base_url + "?authentication_token_SCM=" + activityJson.getString(Const.Params.authentication_number_scm) + "&component_name=" + componentName + "&stud_id=" + child_id;
                                        } else if (componentName.equalsIgnoreCase("location_details")) {
                                            forumURL = Base_url + "?authentication_token_SCM=" + activityJson.getString(Const.Params.authentication_number_scm) + "&component_name=" + componentName + "&stud_id=" + child_id;
                                        } else if (componentName.equalsIgnoreCase("recovery_notice") ||
                                                componentName.equalsIgnoreCase("contact_information") ||
                                                componentName.equalsIgnoreCase("school_information")) {
                                            forumURL = Base_url + "?authentication_token_SCM=" + activityJson.getString(Const.Params.authentication_number_scm) + "&component_name=" + componentName + "&stud_id=" + child_id;
                                        }else if(user_typ.equalsIgnoreCase("Parent")) {
                                            forumURL = Base_url + "?authentication_token_SCM=" + activityJson.getString(Const.Params.authentication_number_scm) + "&component_name=" + componentName + "&stud_id=" + child_id;
                                        }
                                        else
                                         {
                                            forumURL = Base_url + "?authentication_token_SCM=" + activityJson.getString(Const.Params.authentication_number_scm) + "&component_name=" + componentName;
                                        }
                                        Log.d("LMS", "GetAuthenticationToken URL is --->  " + forumURL);
                                        webView.setWebViewClient(new myWebViewClient());
                                        webView.setWebChromeClient(new myWebChromeClient());
                                        webView.loadUrl(forumURL);
                                    } else {

                                    }


                                } else {
                                    if (lang.equalsIgnoreCase("en")) {
                                        SmartClassUtil.showToast(context, "Service Failed, Please Try Again");
                                    } else {
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
                if (lang.equalsIgnoreCase("en")) {
                    SmartClassUtil.showToast(context, "No Internet Connection");
                } else {
                    SmartClassUtil.showToast(context, "Ingen internetanslutning");
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                uploadMessage = null;
            }
        } else {
            if (requestCode == FILECHOOSER_RESULTCODE) {
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


        if (context instanceof Activity) {
            activity = (Activity) context;
        }

    }
}
