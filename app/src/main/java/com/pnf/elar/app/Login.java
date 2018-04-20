package com.pnf.elar.app;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/*import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Util.Model.Model.APIListResponse;
import com.Util.Model.Model.UserTerm;
import com.Util.Model.RetrofitServiceBuilder;
import com.Util.Model.Service.TermListService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.elar.util.NetworkUtil;
import com.elar.util.SmartClassUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.pnf.elar.app.adapter.SearchSchoolBo;


import com.pnf.elar.app.pushnotification.ApplicationConstants;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.service.WebServeRequest;
import com.pnf.elar.app.util.AppLog;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("deprecation")
public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    GoogleCloudMessaging gcmObj;
    String regId = "";
    String msg = "";
    public static final String REG_ID = "regId";

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    Spinner langSpinner;
    TextView forgotText, customerText;
    EditText userNameEditText, passwordEditText;
    Button loginButton;
    UserSessionManager session;
    Locale myLocale;
    String language, str;
    SharedPreferences pref;
    ArrayAdapter<String> adapter2;
    String email, passwrd, tkn;
    String value;
    String securityKey = "H67jdS7wwfh";
    int r = 0;
    static String Base_url = "https://presentation.elar.se/";

    // private String[] spn = { "Presentation","Jensen","Preschool" };

    String Login_Email, Login_Password;
    String token, first_name, user_type, Remember_me;
    String[] component, image, other_accounts, name, Site_url, nm, base;


    List<SearchSchoolBo> mainschoolList = new ArrayList<>();
    List<SearchSchoolBo> searchSchoolList = new ArrayList<>();

    ArrayList<String> userTermArrayList_Title = new ArrayList<>();
    int count;

    // int[] image;

    Dialog dialogSchool;
    Dialog twofactorDialog;

    public String customerUrl = "";

    public int domainCount = 0;
    private int STORAGE_PERMISSION_CODE = 23;


    boolean permissionGranted = false;
    String tag = "Login :: ";

    String code = "";

    MyCustomProgressDialog dialog;


   String user_id ;
   String term_size ;


    private GoogleApiClient mGoogleApiClient;
    SignInButton btnSignIn;
    private static final int RC_SIGN_IN = 007;
    private static final String TAG = Login.class.getSimpleName();
    String Google_email= null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);


        dialog = new MyCustomProgressDialog(Login.this);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        setContentView(R.layout.login);








        getSupportActionBar().hide();
        session = new UserSessionManager(getApplicationContext());

        initView();

        RegisterUser();
        session = new UserSessionManager(Login.this);
        if (session.isUserLoggedIn()) {

            HashMap<String, String> user = session.getUserDetails();
            String language_str = user.get(UserSessionManager.TAG_language);

            if (language_str.equalsIgnoreCase("sw")) {
                setLocale("sv");

            } else {
                setLocale("en");

            }

            try {
                if (NetworkUtil.getInstance(Login.this).isInternet()) {

                    Intent in = new Intent(getApplicationContext(), Drawer.class);
                    startActivity(in);
                    this.finish();
                }
                else {

                        try {
                            AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();

                            alertDialog.setTitle("Info");
                            alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                            alertDialog.setCancelable(false);
                            alertDialog.setButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent in = new Intent(getApplicationContext(), Login.class);
                                    startActivity(in);
                                    finish();

                                }
                            });

                            alertDialog.show();
                        }
                        catch(Exception e)
                        {

                        }

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


        } else {


/*            setLocale("en");*/

            setLocale("sv");
            language = "sw";




            try {
                if (NetworkUtil.getInstance(Login.this).isInternet()) {
                    callCustomerListService();

                }
                else {

                    try {
                        AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();

                        alertDialog.setTitle("Info");
                        alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                        alertDialog.setCancelable(false);
                        alertDialog.setButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(getApplicationContext(), Login.class);
                                startActivity(in);
                                finish();

                            }
                        });

                        alertDialog.show();
                    }
                    catch(Exception e)
                    {

                    }

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


        }


        userNameEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (s.length() > 0) {
                    if ((passwordEditText.getText().toString().trim().length() > 0) && (customerText.getText().toString().trim().length() > 0)) {

                        loginButton.setBackgroundColor(Color.parseColor("#617CBF"));
                    } else {
                        loginButton.setBackgroundColor(Color.parseColor("#F5F5F5"));
                    }

                } else {
                    loginButton.setBackgroundColor(Color.parseColor("#F5F5F5"));

                }

            }
        });
        passwordEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (s.length() > 0) {
                    if ((userNameEditText.getText().toString().trim().length() > 0) && (customerText.getText().toString().trim().length() > 0)) {

                        loginButton.setBackgroundColor(Color.parseColor("#617CBF"));
                    } else {
                        loginButton.setBackgroundColor(Color.parseColor("#F5F5F5"));
                    }

                } else {
                    loginButton.setBackgroundColor(Color.parseColor("#F5F5F5"));

                }

            }
        });

        // //////// ----- base urls ----////////////


        customerText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showSchoolDialog();


            }
        });
        // ///////////////



        //**************************GOOGLE SIGIN*********************************
        btnSignIn = (SignInButton) findViewById(R.id.button_signin);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        btnSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (customerText.getText().toString().trim().length() > 0) {

                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                }
                else {
                    if (language.equals("en")) {
                        msg = "You have to select organization";
                    } else {
                        msg = "Du måste välja organisation";
                    }

                    SmartClassUtil.showToast(getApplicationContext(), msg);
                }
            }
        });
        //___________________________________________________________







        final List<String> langArr = new ArrayList<String>();
        langArr.add("Svenska");
        langArr.add("English");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, langArr);

        adapter.setDropDownViewResource(R.layout.lang_spinner);
        langSpinner.setAdapter(adapter);
        langSpinner.setSelection(0);
        langSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                // s2.setPrompt("elar");

                if (pos == 0) {
                    setLocale("sv");

                    language = "sw";
                    str = langSpinner.getSelectedView().toString();
                    adapter.clear();
                    langArr.add("Svenska");
                    langArr.add("English");
                    adapter.notifyDataSetChanged();


                    //Change by kavin
                    session.Language(language);


                    ((TextView) view).setTypeface(null, Typeface.BOLD);
                    ((TextView) view).setBackgroundColor(Color.parseColor("#666666"));


                  /*  textView.setTypeface(null, Typeface.BOLD); */       // for Bold only


                } else if (pos == 1) {


                    setLocale("en");


                    str = langSpinner.getSelectedView().toString();

                    adapter.clear();
                    langArr.add("Swedish");
                    langArr.add("English");
                    adapter.notifyDataSetChanged();

                    language = "en";

                    session.Language(language);

                    /*((TextView) view).setTypeface(null, Typeface.BOLD);
                    ((TextView)view).setBackgroundColor(Color.RED);*/
                    //((TextView)view).setBackgroundColor(Color.parseColor("#666666"));
                }
/*
                langSpinner.setPrompt((getResources().getString(R.string.lang)));
*/
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });


     /*   Username.setHint(getResources().getString(R.string.username));
        pass.setHint(getResources().getString(R.string.password));
        forgotText.setText(getResources().getString(R.string.forgot));
        loginButton.setText(getResources().getString(R.string.login));*/

        loginButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (permissionGranted) {
/*
                        Toast.makeText(ctx, "Under Development", Toast.LENGTH_LONG).show();
*/
                            gotoLoginFunction();


                        } else {
                            if (!SmartClassUtil.hasPermissions(Login.this, SmartClassUtil.HOME_PERMISSIONS)) {
                                ActivityCompat.requestPermissions(Login.this, SmartClassUtil.HOME_PERMISSIONS, SmartClassUtil.PERMISSION_ALL);
                            }
                        }
                    } else {
                        gotoLoginFunction();

                    }


                } else {
                    try {
                        AlertDialog alertDialog = new AlertDialog.Builder(
                                Login.this).create();
                        // alertDialog(Login.this,alertDialog.THEME_HOLO_LIGHT
                        // );
                        alertDialog.setTitle("Info");
                        alertDialog
                                .setMessage("Internet not available, Cross check your internet connectivity and try again");
                        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                        alertDialog.setButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                    }
                                });

                        alertDialog.show();
                    } catch (Exception e) {

                    }
                }

            }
        });

        forgotText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent in = new Intent(Login.this, Forgot_password.class);
                startActivity(in);
            }
        });






    }




    public void gotoLoginFunction() {
        Login_Email = userNameEditText.getText().toString();
        Login_Password = passwordEditText.getText().toString();
        if (Login_Email.trim().length() == 0) {

            SmartClassUtil.showToast(getApplicationContext(), getString(R.string.user_validation));

        } else if (Login_Password.trim().length() == 0) {

            SmartClassUtil.showToast(getApplicationContext(), getString(R.string.pwd_validation));

        } else if (customerText.getText().toString().trim().length() == 0) {

            String msg = "";

            if (language.equals("en")) {
                msg = "You have to select organization";
            } else {
                msg = "Du måste välja organisation";
            }

            SmartClassUtil.showToast(getApplicationContext(), msg);
        } else {
            new LoginService().execute();

        }
    }

    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
  /*  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) ) {
                    showDialogOK("Storage Permission required for this app",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            requestStoragePermission();
                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:
                                            // proceed with logic by disabling the related features or quit the app.
                                            break;
                                    }
                                }
                            });
                }
                //permission is denied (and never ask again is  checked)
                //shouldShowRequestPermissionRationale will return false
                else {
                    Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                            .show();
                    //                            //proceed with logic by disabling the related features or quit the app.
                }

            }
        }
    }*/
    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

   /* public void refresh(View view) {          //refresh is onClick name given to the button
        onRestart();
    }

    @Override
    protected void onRestart() {

        // TODO Auto-generated method stub
        super.onRestart();
        Intent i = new Intent(Login.this, Login.class);  //your class
        startActivity(i);
        finish();

    }*/

    public void initView() {
        customerText = (TextView) findViewById(R.id.customerText);
        langSpinner = (Spinner) findViewById(R.id.langSpinner);
        userNameEditText = (EditText) findViewById(R.id.userNameEditText);

        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        forgotText = (TextView) findViewById(R.id.forgotText);

        loginButton = (Button) findViewById(R.id.loginButton);

    }

    // ////////////// check internet ////////////
    private boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
                .getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            // Toast.makeText(getApplicationContext(),
            // "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    // ////////////////////////register user to GCM ////////////////
    private void RegisterUser() {
        // TODO Auto-generated method stub
        registerInBackground();
    }

    private void registerInBackground() {
        // TODO Auto-generated method stub

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging
                                .getInstance(getApplicationContext());

                    }
                    regId = gcmObj
                            .register(ApplicationConstants.GOOGLE_PROJ_ID);
                    msg = "Registration ID :" + regId;

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                if (!TextUtils.isEmpty(regId)) {
                    // storeRegIdinSharedPref(getApplicationContext(), regId);
                    // Toast.makeText(
                    // getApplicationContext(),
                    // "Registered with GCM Server successfully.\n\n"
                    // + msg, Toast.LENGTH_SHORT).show();

                    // Toast.makeText(getApplicationContext(), regId,
                    // Toast.LENGTH_LONG).show();
                    Log.d("LMS","registered background1 ---> " +msg);
                } else {
                    // Toast.makeText(
                    // getApplicationContext(),
                    // "Reg ID Creation Failed.\n\nEither you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time."
                    // + msg, Toast.LENGTH_LONG).show();
                    Log.d("LMS","registered background2 ---> " +msg);
                }
            }
        }.execute(null, null, null);

    }

    public void callCustomerListService() {
        if (isOnline()) {

            new Customerdata().execute();

          /*  String reponse = FormDataWebservice.excutePost("http://presentation.elar.se/lms_api/picture_diary/posts", "");

            System.out.println("response ---- "+reponse);*/


        } else {
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(
                        Login.this).create();

                alertDialog.setTitle("Info");
                alertDialog
                        .setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                            }
                        });

                alertDialog.show();
            } catch (Exception e) {

            }

        }
    }



    // /////////////////////////////////////////////////////
    class Customerdata extends AsyncTask<String, String, String> {
        MyCustomProgressDialog dialog;
        public String BASE_URL = "https://presentation.elar.se/";
        private ProgressDialog pDialog;
        /*
                private static final String url = BASE_URL
                        + "lms_api/users/get_schools";
        */


        private static final String sub_url = "quiz_app/getCustomerList";


        public String url = "";

        public String domainHeader = "";
        private static final String TAG_STATUS = "status";
        private static final String TAG_schools = "schools";
        private static final String TAG_Customer = "customerList";
        private static final String TAG_name = "name";
        /*
                private static final String TAG_url = "url";
        */
        private static final String TAG_url = "domain_name";
        private static final String TAG_Id = "id";
        Boolean error = false;
        String Status;
        JSONObject cusJsonResponse = new JSONObject();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (domainCount == 0) {
                url = BASE_URL + sub_url;
                domainHeader = "Presentation";

            } else if (domainCount == 1) {
                domainHeader = "Elar";
                BASE_URL = "https://elar.se/";
                url = BASE_URL + sub_url;
            } else if (domainCount == 2) {
                domainHeader = "Jensen";

                BASE_URL = "https://jensen.elar.se/";
                url = BASE_URL + sub_url;
            } else if (domainCount == 3) {
                domainHeader = "Dev";

                BASE_URL = "https://dev.elar.se/";
                url = BASE_URL + sub_url;

                Log.d("LMS","URL ---> "+url);
            }


            dialog = new MyCustomProgressDialog(Login.this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();


        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {


            JSONObject paramsObject = new JSONObject();
            try {
                paramsObject.put("securityKey", "sfA786ula4");


            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {

                Log.e("Create Response", url + " domain pos : " + domainCount);
                /*json = jsonParser.makeHttpRequest(url, "POST", paramsObject);*/
                cusJsonResponse = new JSONObject(WebServeRequest.postJSONRequest(url, paramsObject.toString()));
                Log.e("urlllllll", cusJsonResponse.toString());

            } catch (Exception e) {
                // TODO: handle exceptionToast.makeText(Login.this,
                e.printStackTrace();
            }


            return cusJsonResponse.toString();
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done

            try {
                domainCount += 1;
                dialog.dismiss();

                if (results != null && results.trim().length() > 0) {

                    cusJsonResponse = new JSONObject(results);


                    /*Status = cusJsonResponse.getString(TAG_STATUS);*/
                    Status = "true";

                  /*  if(Status==null)
                    {

                    }*/

                    if (Status.equalsIgnoreCase("true")) {
                        JSONArray schools = cusJsonResponse.optJSONArray(TAG_Customer);

                        Log.d("customerList", schools.length() + "");
                        name = new String[schools.length()];
                        Site_url = new String[schools.length()];
                       /* mainschoolList = new ArrayList<>();
                        searchSchoolList = new ArrayList<>();*/

                        for (int j = 0; j < schools.length(); j++) {
                            JSONObject c = schools.getJSONObject(j);


                            mainschoolList.add(new SearchSchoolBo(c.optLong(TAG_Id), c.optString(TAG_name), c.optString(TAG_url), domainHeader));

                        }
                        searchSchoolList.addAll(mainschoolList);
                        if (domainCount <= 3) {
                            new Customerdata().execute();
                        } else if (domainCount > 3) {




                            if (!SmartClassUtil.hasPermissions(Login.this, SmartClassUtil.HOME_PERMISSIONS)) {
                                ActivityCompat.requestPermissions(Login.this, SmartClassUtil.HOME_PERMISSIONS, SmartClassUtil.PERMISSION_ALL);
                            }

                        }

                    } else {


                    }
                } else {
                    SmartClassUtil.showToast(getApplicationContext(), "Service Failed");

                }
            } catch (Exception e) {
            }
        }

    }

    public List<SearchSchoolBo> getSchoolList(String searchKey) {
        List<SearchSchoolBo> filterList = new ArrayList<>();


        for (SearchSchoolBo schoolBo : mainschoolList) {
            if (schoolBo.getName().toLowerCase().contains(searchKey)) {


                filterList.add(schoolBo);
            }
        }
        Log.d("search key", filterList.size() + "");

        return filterList;
    }

    public void showSchoolDialog() {
        try {
            dialogSchool = new Dialog(Login.this);
            dialogSchool.requestWindowFeature(Window.FEATURE_NO_TITLE); //before

            dialogSchool.setContentView(R.layout.dialogportallayout);


            dialogSchool.setCancelable(true);
            dialogSchool.setCanceledOnTouchOutside(true);

            searchSchoolList = new ArrayList<>();

            /*searchSchoolList.addAll(mainschoolList);*/

            TextView titleText = (TextView) dialogSchool.findViewById(R.id.titleText);

            AutoCompleteTextView schoolAutoComplete = (AutoCompleteTextView) dialogSchool.findViewById(R.id.schoolAutoComplete);
            final ListView dialog_ListView = (ListView) dialogSchool
                    .findViewById(R.id.dialoglist);


            if (language.equalsIgnoreCase("sw")) {


                schoolAutoComplete.setHint("Sök, Minst tre bokstäver");
                titleText.setText("Hitta din organisation");


            } else {
                schoolAutoComplete.setHint("Search, minimum three letters");
                titleText.setText("Find your organization");
            }


            final SchoolAdapter adapter = new SchoolAdapter(Login.this, searchSchoolList);

                   /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            Login.this, android.R.layout.test_list_item,
                            Site_url);*/
            dialog_ListView.setAdapter(adapter);
            dialogSchool.show();
            /*dialog_ListView.setVisibility(View.VISIBLE);*/


            schoolAutoComplete.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    searchSchoolList = new ArrayList<SearchSchoolBo>();

                    Log.d("search key", s.toString());
                    if (s.length() >= 3) {

                        /*dialog_ListView.setVisibility(View.VISIBLE);*/
                        searchSchoolList.addAll(getSchoolList(s.toString().toLowerCase()));
                        SchoolAdapter adapter1 = new SchoolAdapter(Login.this, searchSchoolList);

                        dialog_ListView.setAdapter(adapter1);

                    } else {
                        //searchSchoolList.addAll(mainschoolList);
                        searchSchoolList = new ArrayList<SearchSchoolBo>();

                        SchoolAdapter adapter1 = new SchoolAdapter(Login.this, searchSchoolList);
                        dialog_ListView.setAdapter(adapter1);
                        /*adapter.notifyDataSetChanged();*/
                        /*dialog_ListView.setVisibility(View.GONE);*/
                    }

/*
                            adapter.notifyDataSetChanged();
*/

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ////////////////////////////////////

    class LoginService extends AsyncTask<String, String, String> {

        MyCustomProgressDialog dialog;
        String user_type, user_id;
        private String url = Base_url + "lms_api/users/login";

        private static final String TAG_STATUS = "status";
        private static final String TAG_TOKEN = "authentication_token";
        private static final String TAG_Component = "ComComponent";
        private static final String TAG_User = "User";
        private static final String TAG_other_accounts = "other_accounts";
        private static final String TAG_name = "name";
        private static final String TAG_img_path = "img_path";
        private static final String TAG_USR_Firstname = "USR_Firstname";
        private static final String TAG_customer_name = "customer_name";
        private static final String TAG_user_type = "user_type";
        private static final String TAG_USER_Firstname = "USR_Firstname";

        private static final String TAG_id = "id";
        private static final String USR_image = "USR_image";
        private ProgressDialog pDialog;

        private static final String TWO_FACTOR = "Twofactor";
        private static final String RANDOMNUMBER = "randomnumber";
        private static final String USERNAME = "username";

        String status_for_login;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new MyCustomProgressDialog(Login.this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {

            // Log.i("Base_url", Base_url);

            /*JSONParser jsonParser = new JSONParser();*/
            String loginResponse = "";

            try {

/*
                List<NameValuePair> params = new ArrayList<NameValuePair>();
*/


                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(securityKey, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(language, "UTF-8") +
                        "&" + Const.Params.UserName + "=" + URLEncoder.encode(Login_Email, "UTF-8") + "&" +
                        Const.Params.Password + "=" + URLEncoder.encode(Login_Password, "UTF-8") + "&" + Const.Params.DeviceTokenApp + "=" +
                        URLEncoder.encode(regId, "UTF-8") + "&" + Const.Params.UserTypeApp + "=" +
                        URLEncoder.encode("android", "UTF-8");

              /*  params.add(new BasicNameValuePair("securityKey", securityKey));
                params.add(new BasicNameValuePair("username", Login_Email));
                params.add(new BasicNameValuePair("password", Login_Password));
                params.add(new BasicNameValuePair("language", language));
                params.add(new BasicNameValuePair("device_token_app", regId));
                params.add(new BasicNameValuePair("user_type_app", "android"));

                Log.e("Create Response", params.toString());



                JSONObject json1 = jsonParser.makeHttpRequest(url, "POST", params);*/


                loginResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);

                Log.e("login response", urlParams);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return loginResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done

            if (dialog.isShowing())
                dialog.dismiss();

            try {


                if (results.equals("") || results.equals(null)) {


                    SmartClassUtil.showToast(getApplicationContext(), "Service Failed");
                    // .show();

                } else {
                    try {
                        JSONObject loginResponse = new JSONObject(results);
                        status_for_login = loginResponse.getString(TAG_STATUS);
                        if (status_for_login.equals("true")) {
                            if(loginResponse.has(TWO_FACTOR)) {


                                if (loginResponse.getString(TWO_FACTOR).equalsIgnoreCase("true")) {

                                    showtwoFactorLogin(loginResponse);

                                } else {

                                    gotoNext(loginResponse);
                                }

                            }
                            else
                            {
                                gotoNext(loginResponse);
                            }
                        } else {
                            SmartClassUtil.showToast(getApplicationContext(), getString(R.string.invalid_user_pwd));
                            dialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (dialog.isShowing())
                            dialog.dismiss();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                dialog.dismiss();
            }
        }

    }


    public void showtwoFactorLogin(final JSONObject loginObject) {

        String TAG_USR_Email = "USR_Email";
        String RANDOMNUMBER = "randomnumber";
        String USERNAME = "username";
        String TAG_User = "User";


/*
        The user allvar050 requires two factor login and a code has been sent to hema.tamaraiselvan@kaaylabs.com
*/


        try {
            code = loginObject.getString(RANDOMNUMBER);

            new Timer().schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            code = "";

                        }
                    },
                    120000
            );


            twofactorDialog = new Dialog(Login.this);
            twofactorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
            twofactorDialog.setContentView(R.layout.twofactordialog);
            twofactorDialog.setCancelable(true);
            twofactorDialog.setCanceledOnTouchOutside(true);


            TextView twofactorText = (TextView) twofactorDialog.findViewById(R.id.twofactorText);
            TextView twofactorMailText = (TextView) twofactorDialog.findViewById(R.id.twofactorMailText);
            final EditText factorCodeText = (EditText) twofactorDialog.findViewById(R.id.factorCodeText);
            Button factorLoginBtn = (Button) twofactorDialog.findViewById(R.id.factorLoginBtn);


            if (language.equalsIgnoreCase("sw")) {
                twofactorText.setText("Två Factor inloggning krävs");
                twofactorMailText.setText("Användaren " + loginObject.getJSONObject(TAG_User).getString(USERNAME) + " kräver två faktor inloggning och en kod har skickats till " + loginObject.getJSONObject(TAG_User).getString(TAG_USR_Email));
                factorCodeText.setHint("Skriv koden här");
                factorLoginBtn.setText("Logga in");
            } else {
                twofactorMailText.setText("The user " + loginObject.getJSONObject(TAG_User).getString(USERNAME) + " requires two factor login and a code has been sent to " + loginObject.getJSONObject(TAG_User).getString(TAG_USR_Email));

            }

            factorLoginBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    String factorCode = factorCodeText.getText().toString().trim();
                    if (!factorCode.isEmpty()) {
                        if (factorCode.equalsIgnoreCase(code)) {
                            twofactorDialog.dismiss();
                            gotoNext(loginObject);

                        } else {
                            if (language.equalsIgnoreCase("en")) {
                                SmartClassUtil.showToast(getApplicationContext(), "Authentication Number Mismatched");
                            } else {
                                SmartClassUtil.showToast(getApplicationContext(), "Autentisering Number oöverensstämmelse");
                            }
                        }

                    } else {
                        if (language.equalsIgnoreCase("en")) {
                            SmartClassUtil.showToast(getApplicationContext(), "Enter valid Authentication Number");
                        } else {
                            SmartClassUtil.showToast(getApplicationContext(), "Ange giltig Authentication Antal");
                        }
                    }


                }
            });

            /*string*/

            twofactorDialog.show();
        } catch (Exception e) {
            AppLog.handleException(tag, e);
        }


    }

    public void gotoNext(JSONObject responseJson) {
        String user_type, user_id;
        String TAG_TOKEN = "authentication_token";
        String TAG_User = "User";
        String TAG_user_type = "user_type";
        final String TAG_id = "id";
        final String USR_image = "USR_image";

        try {
            token = responseJson.getString(TAG_TOKEN);

            // Log.i("status_for_login", status_for_login);

            JSONObject user = responseJson.getJSONObject(TAG_User); // TAG_user_type

            user_type = user.getString(TAG_user_type);
            user_id = user.getString(TAG_id);

            if (user_type.equalsIgnoreCase("parent")) {
                session.createUserparentorchild("loginparent");
            }


            Log.i("user_id", user_type);
            session.createUserLoginSession(language);
            session.createUserLoginSession(token, user_type, user_id);

            session.createUserLoginSession(Login_Email, Login_Password,
                    language, securityKey, Base_url, regId);
            session.putSelectedGroup("-1");


            if (user_type.equalsIgnoreCase("student")) {
                                /*TAG_child_image*/
                if (user.has(USR_image)) {

                    session.putChildImage(Base_url + user.getString(USR_image));
                } else {
                    session.putChildImage(Base_url);
                }
            }
            userNameEditText.setText("");
            passwordEditText.setText("");


            //GET TERMS OF USE LIST
            new GetTermList().execute();







        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // //////////////////////////////////////

    public void setLocale(String lang) {

        Log.d("SetLocal",""+lang);

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        // res.updateConfiguration(conf, dm);
        // Intent refresh = new Intent(this, MainActivity.class);
        // startActivity(refresh);

        getResources().updateConfiguration(conf,
                getResources().getDisplayMetrics());
        refreshTextchange();

    }

    private void refreshTextchange() {
        userNameEditText.setHint(getResources().getString(R.string.username));
        passwordEditText.setHint(getResources().getString(R.string.password));
        forgotText.setText(getResources().getString(R.string.forgot));
        loginButton.setText(getResources().getString(R.string.login));
        customerText.setHint(getResources().getString(R.string.select_customer));
/*
        langSpinner.setPrompt((getResources().getString(R.string.lang)));
*/

        // Intent refresh = new Intent(this,Login.class);
        // startActivity(refresh);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

   /* @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        finish();

        super.onBackPressed();
    }*/


    /*Adapter*/

    public class SchoolAdapter extends ArrayAdapter<SearchSchoolBo> {
        TextView schoolText, schoolDomainText;
        LinearLayout schoolLayout;
        private final Activity context;
        private final List<SearchSchoolBo> searchedSchool;

        public SchoolAdapter(Activity edit_post, List<SearchSchoolBo> searchedSchool
        ) {
            // TODO Auto-generated constructor stub
            super(edit_post, R.layout.item_schools, searchedSchool);
            this.context = edit_post;
            this.searchedSchool = searchedSchool;


        }


        @Override
        public View getView(final int position, View view, ViewGroup parent) {


            LayoutInflater inflater = context.getLayoutInflater();


            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.item_schools, parent, false);

            schoolLayout = (LinearLayout) rowView.findViewById(R.id.schoolLayout);
            schoolText = (TextView) rowView.findViewById(R.id.schoolText);
            schoolDomainText = (TextView) rowView.findViewById(R.id.schoolDomainText);


            schoolText.setText(searchedSchool.get(position).getName());

            schoolDomainText.setText(searchedSchool.get(position).getDomain_name());

            schoolText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("click ", position + "");
                    Base_url = searchedSchool.get(position).getDomain_name();
                    customerText.setTextColor(Color.parseColor("#617DBE"));
                    customerText.setText(searchedSchool.get(position).getName());
                    setLoginButton();
                    dialogSchool.dismiss();

                }
            });

            schoolDomainText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("click ", position + "");
                    Base_url = searchedSchool.get(position).getDomain_name();

                    customerText.setText(searchedSchool.get(position).getName());
                    customerText.setTextColor(Color.parseColor("#617DBE"));

                    setLoginButton();
                    dialogSchool.dismiss();

                }
            });

            return rowView;
        }
    }

    public void setLoginButton() {
        if ((userNameEditText.getText().toString().trim().length() > 0) && (passwordEditText.getText().toString().trim().length() > 0)) {

            loginButton.setBackgroundColor(Color.parseColor("#617DBE"));


        } else {
            loginButton.setBackgroundColor(Color.parseColor("#F5F5F5"));
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case SmartClassUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                SmartClassUtil.checkRuntimePermissionResult(permissions, grantResults, Login.this);
            }
            break;
            case SmartClassUtil.PERMISSION_ALL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                } else {
                    // functionality that depends on this permission.
                    System.out.println("Location Permission not granted .");
                    if (!SmartClassUtil.hasPermissions(Login.this, SmartClassUtil.HOME_PERMISSIONS)) {
                        SmartClassUtil.checkHomeRuntimePermission(Login.this);
                    }
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }





    class GetTermList extends AsyncTask<String, String, String> {

        JSONObject TermListObject = new JSONObject();


         private String url = Base_url + "mobile_api/get_userterms_formobile";


        public String updatedTime = "";
        MyCustomProgressDialog tdialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            tdialog = new MyCustomProgressDialog(Login.this);
            tdialog.setCancelable(false);
            tdialog.show();

        }

        protected String doInBackground(String... args) {

            HashMap<String, String> user = session.getUserDetails();
            String user_id = user.get(UserSessionManager.TAG_user_id);
            String sec_key = "H67jdS7wwfh";
            String platform = "android";

            JSONObject paramsObject = new JSONObject();
            try {
                paramsObject.put("securityKey", sec_key);
                paramsObject.put(Const.Params.LoginUserId, user_id);
                paramsObject.put(Const.Params.PlarForm, platform);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {

                TermListObject = new JSONObject(WebServeRequest.postJSONRequest(url, paramsObject.toString()));
                Log.e("ul", TermListObject.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }


            return TermListObject.toString();
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done


            // Toast.makeText(TermsOfUseActivity.this, "" + results, Toast.LENGTH_LONG).show();

            try {
                JSONObject jsonObj = new JSONObject(results);


                String Status = jsonObj.getString("status");

                if (Status.equalsIgnoreCase("true")) {

                    JSONArray TermsArray = jsonObj.getJSONArray("term_list");

                    ArrayList<UserTerm> userTermArrayList = new ArrayList<>();


                    for (int j = 0; j < TermsArray.length(); j++) {

                        JSONObject jsonObject = TermsArray.getJSONObject(j);

                        UserTerm userTerm = new UserTerm();
                        JSONObject termObject = jsonObject.getJSONObject("UserTerm");
                        userTerm.setTerm_title(termObject.getString("term_title"));
                        userTerm.setTerm_description(termObject.getString("term_description"));
                        userTermArrayList.add(userTerm);

                        userTermArrayList_Title.add(termObject.getString("term_title"));

                    }

                    term_size= String.valueOf(userTermArrayList.size());

                    Log.d("TermsSize:",""+userTermArrayList_Title.size());

                    HashMap<String, String> user = session.getUserDetails();
                    String user_id = user.get(UserSessionManager.TAG_user_id);

                    Intent in = new Intent(Login.this, Remember_me.class);
                    in.putExtra("user_type", user_type);
                    in.putExtra("user_id", user_id);
                    in.putExtra("term_size", term_size);
                    startActivity(in);
                    finish();


                } else {
                    try {


                        String msg = jsonObj.getString("message");

                        String notes = jsonObj.getString("term_list");
                        JSONArray TermsArray = new JSONArray(notes);
                        term_size= String.valueOf(TermsArray.length());
                        System.out.print(msg);

                        HashMap<String, String> user = session.getUserDetails();
                        String lang = user.get(UserSessionManager.TAG_language);

                        if (lang.equalsIgnoreCase("sw")) {
                            System.out.print("Sw_l");
                            if (msg.equalsIgnoreCase("Autentisering misslyckades")) {
                                Button btnLogout;
                                TextView tvMessage;
                                final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", Login.this);
                                btnLogout = (Button) dialogs.findViewById(R.id.alert_logout_bun);
                                tvMessage = (TextView) dialogs.findViewById(R.id.alert_msg);
                                tvMessage.setText(msg);

                                btnLogout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogs.dismiss();

                                        session.logoutUser();

                                    }
                                });

                            }
                        } else {
                            System.out.print("Eng_l");
                            if (msg.equalsIgnoreCase("Authentication Failed")) {
                                Button btnLogout;
                                TextView tvMessage;
                                final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", Login.this);
                                btnLogout = (Button) dialogs.findViewById(R.id.alert_logout_bun);
                                tvMessage = (TextView) dialogs.findViewById(R.id.alert_msg);
                                tvMessage.setText(msg);

                                btnLogout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogs.dismiss();

                                        session.logoutUser();

                                    }
                                });
                            }
                        }






                        HashMap<String, String> user2 = session.getUserDetails();
                        String user_id = user2.get(UserSessionManager.TAG_user_id);

                        Intent in = new Intent(Login.this, Remember_me.class);
                        in.putExtra("user_type", user_type);
                        in.putExtra("user_id", user_id);
                        in.putExtra("term_size", term_size);
                        startActivity(in);
                        finish();

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            tdialog.dismiss();


        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {

                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = null;
            String personPhotoUrl = null;


            try {
                 personName = acct.getDisplayName();
                 personPhotoUrl = acct.getPhotoUrl().toString();
                Google_email = acct.getEmail();
            }catch (NullPointerException e)
            {
                e.printStackTrace();
            }


            Log.e(TAG, "Name: " + personName + ", email: " + Google_email
                    + ", Image: " + personPhotoUrl);

           /* txtName.setText(personName);
            txtEmail.setText(email);
            Glide.with(getApplicationContext()).load(personPhotoUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfilePic);*/

            //Toast.makeText(Login.this, ""+Google_email+"\n"+personName, Toast.LENGTH_SHORT).show();

            new Get_google_user_email().execute();


        } else {
            // Signed out, show unauthenticated UI.

        }
    }


    class Get_google_user_email extends AsyncTask<String, String, String> {

        JSONObject google_user_email_Object = new JSONObject();


        private String url_google_user_email = Base_url + "mobile_api/login_using_email";


        public String updatedTime = "";
        MyCustomProgressDialog tdialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            tdialog = new MyCustomProgressDialog(Login.this);
            tdialog.setCancelable(false);
            tdialog.show();

        }

        protected String doInBackground(String... args) {

            HashMap<String, String> user = session.getUserDetails();
            String sec_key = "H67jdS7wwfh";


            JSONObject paramsObject = new JSONObject();
            try {

                paramsObject.put("securityKey", sec_key);
                paramsObject.put("google_user_email", Google_email);


            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {

                google_user_email_Object = new JSONObject(WebServeRequest.postJSONRequest(url_google_user_email, paramsObject.toString()));
                Log.e("url_google_user_email", google_user_email_Object.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return google_user_email_Object.toString();
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done


             Toast.makeText(Login.this, "" + results, Toast.LENGTH_LONG).show();

            try {
                JSONObject jsonObj = new JSONObject(results);


                String Status = jsonObj.getString("status");

                if (Status.equalsIgnoreCase("true")) {


                    Toast.makeText(Login.this, "Success Gmail ID Fetched", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(Login.this, "Failed ! Gmail ID Fetched", Toast.LENGTH_SHORT).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            tdialog.dismiss();


        }

    }

}
