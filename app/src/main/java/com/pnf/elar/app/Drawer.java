package com.pnf.elar.app;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
/*
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elar.util.NetworkUtil;
import com.elar.util.SmartClassUtil;
import com.elar.attandance.list.AttandanceMainScreen;
import com.elar.util.TransparentProgressDialog;
import com.pnf.elar.app.activity.schedule.AddFoodMenuActivity;
import com.pnf.elar.app.activity.schedule.AsynchronousActivity;
import com.pnf.elar.app.fragments.EduBlogWebFragment;
import com.pnf.elar.app.fragments.WebFragment;
import com.pnf.elar.app.fragments.NewsWebFragment;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;
import com.squareup.picasso.Picasso;

@SuppressWarnings("deprecation")
public class Drawer extends AppCompatActivity implements OnItemClickListener {

    // ImageView imageView111;
    // private AnimationDrawable loadingViewAnim=null;
    RelativeLayout relative_layout;
    MainActivity mainactivity;
    TransparentProgressDialog pdialog;
    String news_count, eduBlog_count, ParentOrChild, eduBlog_countTeacher, news_countTeacher;

    String[] component_name, component_name_sw, component_img_path,
            component_id, component_status;
    ImageLoadernew imageLoader;
    DrawerLayout dl;
    ListView lvleft;
    FrameLayout flayout;
    TextView t1;
    String counting[];
    Custom_Drawer mainList;
    ActionBarDrawerToggle drawerListener;
    private MediaPlayer mMediaPlayer;
    static int count = 0;
    private ArrayList<String> items;
    String[] mMainMenuTitles, mMainMenuTitles1, mMainMenuTitles2;
    UserSessionManager session;
    String lang="", auth_token = null, Base_url, user_name, password,
            filter_class, publish_screen, main_news, news_main, main_S_screen,
            edit_post, securityKey = "H67jdS7wwfh", auth_token_new = null,
            user_typ, user_type_header, regId;
    int r, s, t = 0;
    ViewGroup actionBarLayout;
    LinearLayout actionbar;
    ImageView img, refresh, serhc, onlyforparent;
    ImageView img2;
    TextView MYAccount, txt2;
    int dryr = 0;
    ViewGroup header, footer;
    String msg;
    int tileLength = 0;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        // ////////////////////////


        session = new UserSessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);


        String oldPass=session.getUserPassword();
        Log.d("oldpass",""+oldPass);


        auth_token = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        user_name = user.get(UserSessionManager.TAG_username);
        password = user.get(UserSessionManager.TAG_password);
        publish_screen = user.get(UserSessionManager.TAG_publish_screen);
        user_typ = user.get(UserSessionManager.TAG_user_type);
        ParentOrChild = user.get(UserSessionManager.TAG_ParentOrChild);
        eduBlog_countTeacher = user.get(UserSessionManager.TAG_eduBlog_countTeacher);
        news_countTeacher = user.get(UserSessionManager.TAG_news_countTeacher);
        // securityKey = user.get(UserSessionManager.TAG_securityKey);
        auth_token_new = user
                .get(UserSessionManager.TAG_Authntication_new_tokn);
        regId = user.get(UserSessionManager.TAG_regId);

        actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.actionbar, null);
        actionbar = (LinearLayout) findViewById(R.id.actionbar);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);

        final int actionBarColor = getResources().getColor(R.color.action_bar);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(actionBarColor));

        relative_layout = (RelativeLayout) findViewById(R.id.relative_layout);

        pdialog = new TransparentProgressDialog(Drawer.this, R.drawable.eight);
        //////////////////////////////////////
        img = (ImageView) findViewById(R.id.img);
        img2 = (ImageView) findViewById(R.id.img2);
        refresh = (ImageView) findViewById(R.id.refresh);
        serhc = (ImageView) findViewById(R.id.serhc);
        MYAccount = (TextView) findViewById(R.id.text1);
        txt2 = (TextView) findViewById(R.id.txt2);
        //	onlyforparent=(ImageView)findViewById(R.id.onlyforparent);

        if (lang.equalsIgnoreCase("sw")) {
            txt2.setText("Mitt konto");


        }
        if (lang.equalsIgnoreCase("en")) {
            txt2.setText("My Account");
        }
        MYAccount.setVisibility(View.GONE);
        // /////////////////////////////////////////////

        refresh.setVisibility(View.GONE);
        serhc.setVisibility(View.GONE);
        img.setVisibility(View.GONE);
        // //////////////
        refresh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    if (NetworkUtil.getInstance(Drawer.this).isInternet()) {
                        FragmentManager fragmentManager = getFragmentManager();
                        MainActivity Setting_frg = new MainActivity();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, Setting_frg);
                        ft.commit();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

        img2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (dryr == 0) {
                    dl.openDrawer(Gravity.RIGHT);
                    dryr = 1;
                } else {
                    dl.closeDrawer(Gravity.RIGHT);
                    dryr = 0;
                }

            }
        });


      /*  img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (NetworkUtil.getInstance(Drawer.this).isInternet())
                    {
                        onBackPressed();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });*/




        ///////////////
        items = new ArrayList<String>();

        // imageView111= (ImageView) findViewById(R.id.imageView111);
        //
        // imageView111.setBackgroundResource(R.anim.loading_animation);
        // loadingViewAnim = (AnimationDrawable) imageView111.getBackground();

        dl = (DrawerLayout) findViewById(R.id.drawerlayout1);

        lvleft = (ListView) findViewById(R.id.drawerlistright1);

        // //////////////


        // flayout=(FrameLayout)findViewById(R.id.drawerframelayout1);

        // t1=(TextView)findViewById(R.id.textView1);
        // counting=getResources().getStringArray(R.array.counting);
        // mMainMenuTitles = getResources().getStringArray(
        // R.array.sender_main_menu_array1);
        // mMainMenuTitles1 = getResources().getStringArray(
        // R.array.sender_main_menu_array2);
        // mMainMenuTitles2 = getResources().getStringArray(
        // R.array.sender_main_menu_array3);
        // mainList = new SenderMainList(this,
        // mMainMenuTitles,mMainMenuTitles1,mMainMenuTitles2);

        // lvleft.setAdapter(mainList);
        // lvleft.setOnItemClickListener(this);

        drawerListener = new ActionBarDrawerToggle(this, dl,
                R.drawable.ic_launcher, R.string.open_Drawer,
                R.string.close_drawer)

        {

            @Override
            public void onDrawerClosed(View arg0) {

            }

            @Override
            public void onDrawerOpened(View arg0) {

            }
        };

        dl.setDrawerListener(drawerListener);
        // drawerListener.setDrawerIndicatorEnabled(true);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        try {

            if (auth_token_new != null) {

                if (NetworkUtil.getInstance(Drawer.this).isInternet()) {
                    new Swich_user(auth_token_new).execute();
                }
//				 Toast.makeText(getApplicationContext(), ".......1",
//				 Toast.LENGTH_SHORT).show();
            } else {


                if (SmartClassUtil.isNetworkAvailable(getApplicationContext())) {
                    new otheraccount().execute();

                } else {
                    SmartClassUtil.showToast(getApplicationContext(), "No active internet connection");
                }
//				 Toast.makeText(getApplicationContext(), "......2",
//				 Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        msg = getIntent().getStringExtra("MsgType");
        if(msg != null){
            if(msg.contains("News") || msg.contains("Nyheter")){
                img.setVisibility(View.VISIBLE);
                session.NewsCountTeacher(news_count);
                FragmentManager fragmentManager = getFragmentManager();
                News_Post rFragment = new News_Post();
                FragmentTransaction ft = fragmentManager
                        .beginTransaction();
                ft.replace(R.id.content_frame, rFragment);
                ft.commit();
            }else if(msg.contains("edu blog") || msg.contains("lärobloggsinlägg")){
                img.setVisibility(View.VISIBLE);
                FragmentManager fragmentManager = getFragmentManager();
                MainActivity rFragment = new MainActivity();
                FragmentTransaction ft = fragmentManager
                        .beginTransaction();
                ft.replace(R.id.content_frame, rFragment);
                ft.commit();
            }else if(msg.contains("Forum")){
                img.setVisibility(View.VISIBLE);
                Bundle bundle = new Bundle();
                FragmentManager fragmentManager = getFragmentManager();
                WebFragment rFragment = new WebFragment();
                bundle.putString("Component_Key","forum");
                bundle.putString("name_en","Forum");
                bundle.putString("name_sw","Forum");
                rFragment.setArguments(bundle);
                FragmentTransaction ft = fragmentManager
                        .beginTransaction();
                ft.replace(R.id.content_frame, rFragment);
                ft.commit();
            }else if(msg.contains("dropped off") || msg.contains("retrieved off") || msg.contains("annan hämtare") || msg.contains("retriever note") || msg.contains("absence note") || msg.contains("frånvaroanmälan")){
                img.setVisibility(View.VISIBLE);
                Intent schedeluIntent = new Intent(Drawer.this, AsynchronousActivity.class);
                startActivity(schedeluIntent);
            }
        }

    }

    public void updateFragment() {
        /* Getting reference to the FragmentManager */
        try {

            if (user_type_header.equalsIgnoreCase("parent")) {

                FragmentManager fragmentManager = getFragmentManager();
                ParentsChild Setting_frg = new ParentsChild();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, Setting_frg);
                ft.commit();

            } else {
                FragmentManager fragmentManager = getFragmentManager();
                My_Account rFragment = new My_Account();
                Bundle bundle = new Bundle();
                bundle.putStringArray("compnt_name", component_name);
                bundle.putStringArray("compnt_name_sw", component_name_sw);
                bundle.putStringArray("compnt_image", component_img_path);
                bundle.putStringArray("compnt_id", component_id);
                bundle.putStringArray("compnt_status", component_status);
                bundle.putInt("tile_Length",tileLength);
                rFragment.setArguments(bundle);
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, rFragment);
                ft.commit();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
    class otheraccount extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/users/login";
        private static final String TAG_STATUS = "status";
        private static final String TAG_TOKEN = "authentication_token";
        private static final String TAG_Component = "ComComponent";
        private static final String TAG_User = "User";
        private static final String TAG_other_accounts = "other_accounts";
        private static final String TAG_id = "id";
        private static final String TAG_name = "name";
        private static final String TAG_name_sw = "name_sw";
        private static final String TAG_img_path = "img_path";
        private static final String TAG_USR_Firstname = "USR_Firstname";
        private static final String TAG_customer_name = "customer_name";
        private static final String TAG_user_type = "user_type";
        private static final String TAG_user_type_sw = "user_type_sw";
        private static final String TAG_news_count = "news_count";
        private static final String TAG_eduBlog_count = "eduBlog_count";
        private static final String TAG_USER_Firstname = "USR_Firstname";
        private static final String TAG_USR_Email = "USR_Email";
        private static final String TAG_username = "username";
        private static final String TAG_USR_Lastname = "USR_Lastname";
        private static final String TAG_image = "USR_image";
        private static final String TAG_app_status = "App_Status";

        private MyCustomProgressDialog dialog;

        String first_name, last_name, user_type, customer_name,
                first_name_header, last_name_header, customer_name_header,
                image_header, main_image_header, user_type_sw, usernameheader;

        String Login_Email, Login_Password, Security = "H67jdS7wwfh";

        String Status, token, Remember_me;

        String[] component, image, other_accounts, name, first_name1,
                user_type1, customer_name1, username1, new_auth_tokn,
                profile_images, user_type1_sw;

        // int[] image;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new MyCustomProgressDialog(Drawer.this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {

            // Log.i("=======", regId);

            String otherResponse = "";
            try {
                /*JSONParser jsonParser = new JSONParser();

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("securityKey", Security));
                params.add(new BasicNameValuePair("username", user_name));
                params.add(new BasicNameValuePair("password", password));
                params.add(new BasicNameValuePair("language", lang));
                params.add(new BasicNameValuePair("device_token_app", regId));
                params.add(new BasicNameValuePair("user_type_app", "android"));
                JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);*/


                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                                    "&" + Const.Params.UserName + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                        Const.Params.Password + "=" + URLEncoder.encode(password, "UTF-8") + "&" + Const.Params.DeviceTokenApp + "=" +
                        URLEncoder.encode(regId, "UTF-8") + "&" + Const.Params.UserTypeApp + "=" +
                        URLEncoder.encode("android", "UTF-8");

                 Log.d("lng1", urlParams);


                otherResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);

                Log.d("otheraccount......", otherResponse);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return otherResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {
                Log.i("other::::::", regId);
                Toast.makeText(getApplicationContext(), "other account ",
                        Toast.LENGTH_SHORT);


                try {

                    JSONObject jsonObj = new JSONObject(results);

                    Status = jsonObj.getString(TAG_STATUS);
                    Log.d("status",""+TAG_STATUS);
                    if (Status.equalsIgnoreCase("true")){


                        if (results != null && results.trim().length() > 0) {


                            Status = jsonObj.getString(TAG_STATUS);
                            token = jsonObj.getString(TAG_TOKEN);

                            JSONArray component = jsonObj.optJSONArray(TAG_Component);

                            tileLength = component.length();
/*
                component=jsonObj.optJSONArray(TAG_Component);
*/

                            component_name = new String[component.length()];
                            component_name_sw = new String[component.length()];
                            component_img_path = new String[component.length()];
                            component_id = new String[component.length()];
                            component_status = new String[component.length()];


                        /*JSONObject schedule = new JSONObject();
                        schedule.put(TAG_name, "Schedule");
                        schedule.put(TAG_name_sw, "Schema");
                        schedule.put(TAG_img_path, "/img/Schedule.png");
                        schedule.put(TAG_id, "113");

                        component.put(schedule);*/

                            for (int j = 0; j < component.length(); j++) {
                                JSONObject c = component.getJSONObject(j);

                                component_name[j] = filterStringToNormal(c
                                        .getString(TAG_name));
                                component_name_sw[j] = filterStringToNormal(c
                                        .getString(TAG_name_sw));
                                component_img_path[j] = Base_url
                                        + c.getString(TAG_img_path);
                                component_id[j] = c.getString(TAG_id);
                                component_status[j] = c.getString(TAG_app_status);

                            }


                            JSONObject user = jsonObj.optJSONObject(TAG_User);

                            user_type = user.getString(TAG_user_type);


                            news_count = user.getString(TAG_news_count);
                            eduBlog_count = user.getString(TAG_eduBlog_count);

                            user_type_sw = user.getString(TAG_user_type_sw);

                            first_name_header = filterStringToNormal(user
                                    .getString(TAG_USR_Firstname) + " " + user
                                    .getString(TAG_USR_Lastname));
                            last_name_header = filterStringToNormal(user
                                    .getString(TAG_USR_Lastname));
                            user_type_header = filterStringToNormal(user
                                    .getString(TAG_user_type));
                            customer_name_header = filterStringToNormal(user
                                    .getString(TAG_customer_name));

                            image_header = user.getString(TAG_image);

                            usernameheader = filterStringToNormal(user
                                    .optString(TAG_username));

                            main_image_header = Base_url + image_header;

                            Log.i("Simple...", user_type_header);

                            // // /////////////////
                            JSONArray comp = jsonObj.optJSONArray(TAG_other_accounts);
                            first_name1 = new String[comp.length()];
                            user_type1 = new String[comp.length()];
                            customer_name1 = new String[comp.length()];
                            username1 = new String[comp.length()];
                            new_auth_tokn = new String[comp.length()];
                            profile_images = new String[comp.length()];
                            user_type1_sw = new String[comp.length()];

                            for (int j = 0; j < comp.length(); j++) {
                                JSONObject c = comp.getJSONObject(j);

                                first_name1[j] = filterStringToNormal(c
                                        .optString(TAG_USR_Firstname) + " " + c
                                        .optString(TAG_USR_Lastname));
                                user_type1[j] = filterStringToNormal(c
                                        .optString(TAG_user_type));
                                user_type1_sw[j] = filterStringToNormal(c
                                        .optString(TAG_user_type_sw));
                                customer_name1[j] = filterStringToNormal(c
                                        .optString(TAG_customer_name));
                                username1[j] = filterStringToNormal(c
                                        .optString(TAG_username));
                                new_auth_tokn[j] = filterStringToNormal(c
                                        .optString(TAG_TOKEN));
                                profile_images[j] = Base_url + c.optString(TAG_image);

                                Log.e("data   data  ", "" + first_name1[j]);
                            }

                            if (Status.equalsIgnoreCase("true")) {

                            } else {
                                // Log.i("res", "false");
                            }
                        } else {
                            SmartClassUtil.showToast(getApplicationContext(), "Service Failed");
                        }
                    }else {

                       String passchange=jsonObj.getString("passchange");
                       String msg=jsonObj.getString("message");

                       if (passchange.equals("yes"))
                       {

                           Button btnLogout;
                           TextView  tvMessage;
                           final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", Drawer.this);
                           btnLogout = (Button) dialogs.findViewById(R.id.alert_logout_bun);
                           tvMessage = (TextView) dialogs.findViewById(R.id.alert_msg);


                           btnLogout.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   dialogs.dismiss();

                                   session.logoutUser();

                               }
                           });

//                           SmartClassUtil.showToast(getApplicationContext(), "Yara ketu password change pana");
                       }else {

                           SmartClassUtil.showToast(getApplicationContext(), ""+msg);
                       }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (Status.equalsIgnoreCase("true")) {

                    if(msg == null ) {
                        updateFragment();
                    }

                    new ImageLoadTasklist(main_image_header, img2).execute();

                    LayoutInflater inflater = getLayoutInflater();
                    header = (ViewGroup) inflater.inflate(R.layout.header,
                            lvleft, false);
                    footer = (ViewGroup) inflater.inflate(R.layout.footer,
                            lvleft, false);

                    relative_layout.addView(header);

//					lvleft.addHeaderView(header, null, false);
//					lvleft.addFooterView(footer, null, false);
                    // //////////////////////////////////////////////
                    TextView lg_out = (TextView) footer
                            .findViewById(R.id.lg_out);
                    TextView stng = (TextView) footer
                            .findViewById(R.id.stng);
                    TextView textView1 = (TextView) header
                            .findViewById(R.id.textView1);
                    TextView textView2 = (TextView) header
                            .findViewById(R.id.textView2);
                    TextView textView3 = (TextView) header
                            .findViewById(R.id.textView3);
                    TextView textView4 = (TextView) header
                            .findViewById(R.id.textView4);
                    TextView Other_Account = (TextView) header
                            .findViewById(R.id.Other_Account);

                    LinearLayout Setting = (LinearLayout) findViewById(R.id.Setting);
                    TextView settings = (TextView) findViewById(R.id.settings);
                    TextView lgout = (TextView) findViewById(R.id.logout);
                    LinearLayout Log_out = (LinearLayout) findViewById(R.id.Log_out);
                    LinearLayout domain  = (LinearLayout) findViewById(R.id.domain);
                    TextView domainName  = (TextView) findViewById(R.id.domainName);

                    if (lang.equalsIgnoreCase("sw")) {
                        stng.setText("Inställningar");
                        lg_out.setText("Logga ut");
                        Other_Account.setText("Länkade Konton");
                    }

                    ImageView header_image = (ImageView) header
                            .findViewById(R.id.header_image);

                    System.out.println("header image" + main_image_header);

                   /* new ImageLoadTasklist(main_image_header, header_image)
                            .execute();
*/
                    Picasso.with(getApplicationContext())
                            .load(Uri.parse(main_image_header))
                            .placeholder(R.drawable.roundedimageview)
                            // optional
                            .error(R.drawable.roundedimageview)         // optional
                            .into(header_image);
                    textView1.setText(customer_name_header);
                    textView2.setText(first_name_header);
                    textView4.setText(usernameheader);

                    if (lang.equalsIgnoreCase("sw")) {
                        textView3.setText(user_type_sw);
                    } else {
                        textView3.setText(user_type);
                    }


                    // ////////////////////////////////////////////////

//					LinearLayout Settingone = (LinearLayout) footer
//							.findViewById(R.id.Setting);
//					LinearLayout Log_out = (LinearLayout) footer
//							.findViewById(R.id.Log_out);
                    String trim = Base_url.replace("http://","");
                    String trimmed = trim.replace("/","");
                    if(lang.equalsIgnoreCase("en")) {
                        if (trimmed.contains("dev")) {
                            domainName.setText("Go to : " + "Dev.elar.se");
                        } else if (trimmed.contains("jensen")) {
                            domainName.setText("Go to : " + "Jensen.elar.se");
                        } else {
                            domainName.setText("Go to : " + "Elar.se");
                        }
                    }else{
                        settings.setText("Inställningar");
                        lgout.setText("Logga ut");
                        if (trimmed.contains("dev")) {
                            domainName.setText("Gå till : " + "Dev.elar.se");
                        } else if (trimmed.contains("jensen")) {
                            domainName.setText("Gå till : " + "Jensen.elar.se");
                        } else {
                            domainName.setText("Gå till : " + "Elar.se");
                        }
                    }
//                    domainName.setText("Go to : "+trimmed);
                    domain.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            try {
                                if(NetworkUtil.getInstance(Drawer.this).isInternet()) {
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(Base_url));
                                    startActivity(i);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    Setting.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub


                            try {
                                if(NetworkUtil.getInstance(Drawer.this).isInternet()) {

                                    img.setVisibility(View.VISIBLE);

                                    dl.closeDrawer(Gravity.RIGHT);
    //
    //							if (lang.equalsIgnoreCase("sw")) {
    //								txt2.setText("Inställningar");
    //							}
    //							if (lang.equalsIgnoreCase("en")) {
    //								txt2.setText("Setting");
    //							}
    //
                                    FragmentManager fragmentManager = getFragmentManager();
                                    Setting Setting_frg = new Setting();

                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, Setting_frg);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    Log_out.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            try {
                                if (NetworkUtil.getInstance(Drawer.this).isInternet()) {

                                    String msg = null;
                                    String yes = null;
                                    String no = null;
                                    String title = null;

                                    if (lang.equalsIgnoreCase("sw")) {
                                        title = "Logga ut";
                                        msg = "Vill du logga ut?";
                                        yes = "Ja";
                                        no = "Nej";
                                    }
                                    if (lang.equalsIgnoreCase("en")) {
                                        title = "Log out";
                                        msg = "Do you want to log out?";
                                        yes = "Yes";
                                        no = "No";
                                    }

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            Drawer.this, R.style.MyAlertDialogStyle);
                                    alertDialogBuilder
                                            .setMessage(msg);
                                    alertDialogBuilder.setTitle(title);

                                    alertDialogBuilder.setPositiveButton(yes,
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(
                                                        DialogInterface arg0, int arg1) {

                                                    new LogoutAPI().execute();


                                                }
                                            });

                                    alertDialogBuilder.setNegativeButton(no,
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int which) {

                                                    // Intent i = new
                                                    // Intent(SettingPage.this,SettingPage.class);
                                                    // startActivity(i);
                                                    // finish();
                                                    // closeContextMenu();

                                                }
                                            });

                                    AlertDialog alertDialog = alertDialogBuilder
                                            .create();
                                    alertDialog.show();

                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    if (lang.equalsIgnoreCase("sw")) {
                        mainList = new Custom_Drawer(Drawer.this, first_name1,
                                user_type1_sw, customer_name1, username1,
                                profile_images);
                    } else {
                        mainList = new Custom_Drawer(Drawer.this, first_name1,
                                user_type1, customer_name1, username1,
                                profile_images);
                    }


                    lvleft.setAdapter(mainList);
                    Log.i("grpname..2.", Arrays.deepToString(username1));
                } else {

                    // failed to creat

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            lvleft.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub

                    try {
                        if(NetworkUtil.getInstance(Drawer.this).isInternet()) {

                            Log.i("sare....", Integer.toString(position - 1));

                            String new_tokn = new_auth_tokn[position];

                            if (new_tokn.equalsIgnoreCase("")) {
                                Toast.makeText(getApplicationContext(),
                                        "There is no Authntication_token......",
                                        Toast.LENGTH_SHORT).show();
                            } else {

                                // Toast.makeText(getApplicationContext(),
                                // new_tokn,
                                // Toast.LENGTH_SHORT).show();
                                //	session.createUserLoginSession(auth_token, user_type);
                                session.NewAuthnticationToken(new_tokn);
                                session.putSelectedGroup("-1");
                                Intent in = new Intent(getApplicationContext(),
                                        Drawer.class);
                                startActivity(in);


                                HashMap<String, String> user = session.getUserDetails();
                           /* lang = user.get(UserSessionManager.TAG_language);
                            auth_token = user.get(UserSessionManager.TAG_Authntication_token);
                            Base_url = user.get(UserSessionManager.TAG_Base_url);
                            user_name = user.get(UserSessionManager.TAG_username);*/


                                // Toast.makeText(getApplicationContext(), new_tokn,
                                // Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    // /////////////////////
    class Swich_user extends AsyncTask<String, String, String> {

        String new_auth, first_name_header, last_name_header,
                customer_name_header, image_header, main_image_header, usernameheader;
        private MyCustomProgressDialog dialog;
        private String url = Base_url + "lms_api/users/switch_user";

        private static final String TAG_STATUS = "status";
        private static final String TAG_TOKEN = "authentication_token";
        private static final String TAG_Component = "ComComponent";
        private static final String TAG_User = "User";
        private static final String TAG_username = "username";
        private static final String TAG_USR_Lastname = "USR_Lastname";
        private static final String TAG_user_type = "user_type";
        private static final String TAG_user_type_sw = "user_type_sw";
        private static final String TAG_other_accounts = "other_accounts";
        private static final String TAG_image = "USR_image";
        private static final String TAG_customer_name = "customer_name";
        private static final String TAG_USR_Firstname = "USR_Firstname";
        private static final String TAG_id = "id";
        private static final String TAG_name = "name";
        private static final String TAG_name_sw = "name_sw";
        private static final String TAG_img_path = "img_path";
        private static final String TAG_news_count = "news_count";
        private static final String TAG_eduBlog_count = "eduBlog_count";
        private static final String TAG_app_status = "App_Status";

        String Login_Email, Login_Password, Security = "H67jdS7wwfh";
        String Status, token, first_name, user_type, Remember_me, user_id, user_type_sw;
        String[] component, image, other_accounts, name, first_name1,
                customer_name1, user_type1, username1, new_auth_tokn,
                profile_images, user_type1_sw;

        // CustomAdapter_drawer adapter2; // /////////////// new code

        public Swich_user(String new_toknone) {
            // TODO Auto-generated constructor stub
            this.new_auth = new_toknone;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new MyCustomProgressDialog(Drawer.this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            if (dialog != null) {
                dialog.show();
            }
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            String switchResponse = "";



                try {

                    String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                            "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(new_auth, "UTF-8") +
                            "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                            "&" + Const.Params.DeviceTokenApp + "=" + URLEncoder.encode(regId, "UTF-8") +
                            "&" + Const.Params.UserTypeApp + "=" + URLEncoder.encode("android", "UTF-8")+
                            "&" + Const.Params.CurrentAuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8")
                            ;


                    switchResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            /*String results = "";
            JSONObject json = new JSONObject();
            try {
                JSONParser jsonParser = new JSONParser();

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                // Log.i("::::::::", regId);
                Log.i("new_auth", new_auth);

                Log.i("purana tokn", auth_token);

                params.add(new BasicNameValuePair("securityKey", Security));
                params.add(new BasicNameValuePair("authentication_token", new_auth));
                params.add(new BasicNameValuePair("language", lang));
                params.add(new BasicNameValuePair("current_authentication_token",
                        auth_token));
                params.add(new BasicNameValuePair("user_type_app", "android"));
                params.add(new BasicNameValuePair("device_token_app", regId));


                json = jsonParser.makeHttpRequest(url, "POST", params);

                Log.d("Create Response", json.toString());


                results = json.toString();


                // Log.d("Create Response.....", json.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }*/

            return switchResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {

                if (results != null && !results.isEmpty()) {
                    JSONObject switchJson = new JSONObject(results);

                    try {

                        JSONObject jsonObj = new JSONObject(switchJson.toString());

                        if(jsonObj.has(Const.Params.Status)) {
                            Status = jsonObj.getString(TAG_STATUS).toString();
                        }

                        Log.i("status- image ----...", results);


                        if (Status.equalsIgnoreCase("true")) {

                            token = jsonObj.getString(TAG_TOKEN).toString();

                            JSONArray component = jsonObj.optJSONArray(TAG_Component);


                            int menulength = component.length();

                            tileLength = component.length();

                            component_name = new String[menulength];
                            component_name_sw = new String[menulength];
                            component_img_path = new String[menulength];
                            component_id = new String[menulength];
                            component_status = new String[menulength];

                            //new code
                        /*JSONObject schedule = new JSONObject();
                        schedule.put(TAG_name, "Schedule");
                        schedule.put(TAG_name_sw, "Schema");
                        schedule.put(TAG_img_path, "/img/Schedule.png");
                        schedule.put(TAG_id, "113");
                        component.put(schedule);*/


                            for (int j = 0; j < component.length(); j++) {
                                JSONObject c = component.getJSONObject(j);

                                component_name[j] = c.getString(TAG_name);
                                component_name_sw[j] = c.getString(TAG_name_sw);
                                component_img_path[j] = Base_url
                                        + c.getString(TAG_img_path);
                                component_id[j] = c.getString(TAG_id);
                                component_status[j] = c.getString(TAG_app_status);

                            }

                       /* JSONObject scheduleSwitch = new JSONObject();
                        scheduleSwitch.put(TAG_name, "Schedule");
                        scheduleSwitch.put(TAG_name_sw, "Schema");
                        scheduleSwitch.put(TAG_img_path, "/img/Schedule.png");
                        scheduleSwitch.put(TAG_id, "113");

                        component.put(scheduleSwitch);*/
                   /* component_name[menulength-1] = "Schedule";
                    component_name_sw[menulength-1] = "Schema";
                    component_img_path[menulength-1] = "http://presentation.elar.se/img/Schedule.png";
                    component_id[menulength-1] = "113";*/

                            JSONObject usr = jsonObj.getJSONObject(TAG_User);
                            news_count = usr.getString(TAG_news_count);
                            eduBlog_count = usr.getString(TAG_eduBlog_count);

                            first_name_header = filterStringToNormal(usr
                                    .getString(TAG_USR_Firstname) + " " + usr
                                    .getString(TAG_USR_Lastname));
                            last_name_header = usr.getString(TAG_USR_Lastname);
                            user_type_header = usr.getString(TAG_user_type);
                            customer_name_header = usr.getString(TAG_customer_name)
                                    .toString();
                            image_header = usr.getString(TAG_image);
                            user_type = usr.getString(TAG_user_type);
                            user_type_sw = usr.getString(TAG_user_type_sw);
                            user_id = usr.getString(TAG_id);
                            main_image_header = Base_url + image_header;
                            usernameheader = filterStringToNormal(usr
                                    .optString(TAG_username));
                            // user_type = usr.getString(TAG_user_type);

                            Log.i("switch ....", user_type_header);
                            //
                            JSONArray comp = jsonObj.optJSONArray(TAG_other_accounts);
                            first_name1 = new String[comp.length()];
                            user_type1 = new String[comp.length()];
                            customer_name1 = new String[comp.length()];
                            username1 = new String[comp.length()];
                            new_auth_tokn = new String[comp.length()];
                            profile_images = new String[comp.length()];
                            user_type1_sw = new String[comp.length()];

                            for (int j = 0; j < comp.length(); j++) {
                                JSONObject c = comp.getJSONObject(j);

                                first_name1[j] = filterStringToNormal(c.optString(TAG_USR_Firstname) + " " + c
                                        .optString(TAG_USR_Lastname));
                                user_type1[j] = c.optString(TAG_user_type);
                                customer_name1[j] = c.optString(TAG_customer_name);
                                username1[j] = c.optString(TAG_username);
                                new_auth_tokn[j] = c.optString(TAG_TOKEN);
                                profile_images[j] = Base_url + c.optString(TAG_image);
                                user_type1_sw[j] = filterStringToNormal(c
                                        .optString(TAG_user_type_sw));

                            }



                        } else {

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Status = "";
                    Toast.makeText(getApplicationContext(), "Service Failed", Toast.LENGTH_SHORT).show();
                }

                if (user_type_header.equalsIgnoreCase("parent")) {
                    String Sw = "SwitchParent";
                    session.createUserSwitchParent(Sw, new_auth, auth_token);   /////// imp
                }

                if (Status.equalsIgnoreCase("true")) {

                    session.createUserLoginSession(token, user_type, user_id);


                    updateFragment();
                    new ImageLoadTasklist(main_image_header, img2).execute();
                    // session.NewAuthnticationToken(new_tokn);

                    LayoutInflater inflater = getLayoutInflater();
                    ViewGroup header = (ViewGroup) inflater.inflate(
                            R.layout.header, lvleft, false);
                    ViewGroup footer = (ViewGroup) inflater.inflate(
                            R.layout.footer, lvleft, false);
                    relative_layout.addView(header);
//					lvleft.addHeaderView(header, null, false);
//					lvleft.addFooterView(footer, null, false);
                    // //////////////////////////////////////////////
                    TextView lg_out = (TextView) footer
                            .findViewById(R.id.lg_out);
                    TextView stng = (TextView) footer
                            .findViewById(R.id.stng);

                    TextView textView1 = (TextView) header
                            .findViewById(R.id.textView1);
                    TextView textView2 = (TextView) header
                            .findViewById(R.id.textView2);
                    TextView textView3 = (TextView) header
                            .findViewById(R.id.textView3);
                    TextView textView4 = (TextView) header
                            .findViewById(R.id.textView4);
                    TextView Other_Account = (TextView) header
                            .findViewById(R.id.Other_Account);

                    if (lang.equalsIgnoreCase("sw")) {
                        stng.setText("Inställningar");
                        lg_out.setText("Logga ut");
                        Other_Account.setText("Länkade Konton");
                    }

                    ImageView header_image = (ImageView) header
                            .findViewById(R.id.header_image);

                    Picasso.with(getApplicationContext())
                            .load(Uri.parse(main_image_header))
                            .placeholder(R.drawable.roundedimageview)
                            // optional
                            .error(R.drawable.roundedimageview)         // optional
                            .into(header_image);

                    System.out.println("src" + main_image_header);

					/*new ImageLoadTasklist(main_image_header, header_image)      /// hendle error
                            .execute();*/

                    textView1.setText(customer_name_header);
                    textView2.setText(first_name_header);
                    textView4.setText(usernameheader);

                    if (lang.equalsIgnoreCase("sw")) {
                        textView3.setText(user_type_sw);
                    } else {
                        textView3.setText(user_type);
                    }
                    LinearLayout Setting = (LinearLayout) findViewById(R.id.Setting);
                    TextView settings = (TextView) findViewById(R.id.settings);
                    LinearLayout Log_out = (LinearLayout) findViewById(R.id.Log_out);
                    TextView lgout = (TextView) findViewById(R.id.logout);
                    LinearLayout domain  = (LinearLayout) findViewById(R.id.domain);
                    TextView domainName  = (TextView) findViewById(R.id.domainName);

                    // ////////////////////////////////////////////////

//					LinearLayout Settingone = (LinearLayout) footer
//							.findViewById(R.id.Setting);
//					LinearLayout Log_out = (LinearLayout) footer
//							.findViewById(R.id.Log_out);

                    String trim = Base_url.replace("http://","");
                    String trimmed = trim.replace("/","");
                    if(lang.equalsIgnoreCase("en")) {
                        if (trimmed.contains("dev")) {
                            domainName.setText("Go to : " + "Dev.elar.se");
                        } else if (trimmed.contains("jensen")) {
                            domainName.setText("Go to : " + "Jensen.elar.se");
                        } else {
                            domainName.setText("Go to : " + "Elar.se");
                        }
                    }else{
                        settings.setText("Inställningar");
                        lgout.setText("Logga ut");
                        if (trimmed.contains("dev")) {
                            domainName.setText("Gå till : " + "Dev.elar.se");
                        } else if (trimmed.contains("jensen")) {
                            domainName.setText("Gå till : " + "Jensen.elar.se");
                        } else {
                            domainName.setText("Gå till : " + "Elar.se");
                        }
                    }
//                    domainName.setText("Go to : "+trimmed);
                    domain.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(Base_url));
                            startActivity(i);
                        }
                    });
                    Setting.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub

                            try {
                                if(NetworkUtil.getInstance(Drawer.this).isInternet()) {

                                    // Intent in = new
                                    // Intent(Drawer.this,Setting.class);
                                    // startActivity(in);
                                    img.setVisibility(View.VISIBLE);

                                    dl.closeDrawer(Gravity.RIGHT);

    //							if (lang.equalsIgnoreCase("sw")) {
    //								txt2.setText("Inställningar");
    //							}
    //							if (lang.equalsIgnoreCase("en")) {
    //								txt2.setText("Setting");
    //							}

                                    FragmentManager fragmentManager = getFragmentManager();

                                /* Creating fragment instance */
                                    Setting Setting_frg = new Setting();

                                /* Passing selected item information to fragment */
                                /* Replace fragment */
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, Setting_frg);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    Log_out.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub

                            try {
                                if (NetworkUtil.getInstance(Drawer.this).isInternet()) {

                                    String msg = null;
                                    String yes = null;
                                    String no = null;
                                    String title = null;

                                    if (lang.equalsIgnoreCase("sw")) {
                                        title = "Logga ut";
                                        msg = "Vill du logga ut?";
                                        yes = "Ja";
                                        no = "Nej";
                                    }
                                    if (lang.equalsIgnoreCase("en")) {
                                        title = "Log out";
                                        msg = "Do you want to log out?";
                                        yes = "Yes";
                                        no = "No";
                                    }

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            Drawer.this, R.style.MyAlertDialogStyle);
                                    alertDialogBuilder
                                            .setMessage(msg);
                                    alertDialogBuilder.setTitle(title);

                                    alertDialogBuilder.setPositiveButton(yes,
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(
                                                        DialogInterface arg0, int arg1) {

                                                    session.logoutUser();

                                                }
                                            });

                                    alertDialogBuilder.setNegativeButton(no,
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int which) {

                                                    // Intent i = new
                                                    // Intent(SettingPage.this,SettingPage.class);
                                                    // startActivity(i);
                                                    // finish();
                                                    //		closeContextMenu();

                                                }
                                            });

                                    AlertDialog alertDialog = alertDialogBuilder
                                            .create();
                                    alertDialog.show();
                                }
                            } catch (MalformedURLException e1) {
                                e1.printStackTrace();
                            }


                        }
                    });

                    if (lang.equalsIgnoreCase("sw")) {
                        mainList = new Custom_Drawer(Drawer.this, first_name1,
                                user_type1_sw, customer_name1, username1,
                                profile_images);
                    } else {
                        mainList = new Custom_Drawer(Drawer.this, first_name1,
                                user_type1, customer_name1, username1,
                                profile_images);
                    }

                    lvleft.setAdapter(mainList);
                    Log.i("grpname..2.", Arrays.deepToString(username1));

                } else {

                    // failed to creat
                    Toast.makeText(getApplicationContext(), "Service Failed", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            lvleft.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub

                    try {
                        if (NetworkUtil.getInstance(Drawer.this).isInternet()) {


                            Log.i("sare....", Integer.toString(position));

                            String new_tokn = new_auth_tokn[position];

                            if (new_tokn.equalsIgnoreCase("")) {
                                Toast.makeText(getApplicationContext(),
                                        "There is no Authntication_token......",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                // Log.i("<<<<<<<", user_type);

                                // session.createUserLoginSession(new_tokn, user_type);
                                session.NewAuthnticationToken(new_tokn);
                                session.putSelectedGroup("-1");
                                Intent in = new Intent(getApplicationContext(),
                                        Drawer.class);
                                startActivity(in);

                                // Toast.makeText(getApplicationContext(), new_tokn,
                                // Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

    }

    // /////////////////////

    class ImageLoadTasklist extends AsyncTask<Void, Void, Bitmap> {
        // ProgressDialog pDialog;
        private String url;
        private ImageView imageViewone;

        public ImageLoadTasklist(String urlm, ImageView imageViewlist) {
            this.url = urlm;
            this.imageViewone = imageViewlist;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnectionlist = new URL(url);
                HttpURLConnection connectionlist = (HttpURLConnection) urlConnectionlist
                        .openConnection();
                connectionlist.setDoInput(true);
                connectionlist.connect();
                InputStream input = connectionlist.getInputStream();
                Bitmap myBitmaplist = BitmapFactory.decodeStream(input);////////////errror
                return myBitmaplist;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap resultlist) {
            super.onPostExecute(resultlist);
            // pDialog.dismiss();
            imageViewone.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewone.setImageBitmap(resultlist);
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);

        drawerListener.onConfigurationChanged(newConfig);
    }

    public Bitmap getCircleBitmap(Bitmap bitmap) {
        // TODO Auto-generated method stub
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // It Attach With your DrawerListener Listener

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);

                return true;

        }

        // if (item != null && item.getItemId() ==R.id.btnMyMenu)
        // {
        if (dl.isDrawerOpen(Gravity.RIGHT)) {
            dl.closeDrawer(Gravity.RIGHT);
        } else {
            dl.openDrawer(Gravity.RIGHT);
        }
        // }
        return false;

    }

    // it synchroniz your upNavigation Button to Open/close Navigation
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();

    }

    public void selectItem(int index) {
        lvleft.setItemChecked(index, true);

        setTitle(counting[index]);

    }

    public void setTitle(String title) {
        getActionBar().setTitle(title);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

       // Toast.makeText(this, "back pressed", Toast.LENGTH_SHORT).show();

        try {
            if(NetworkUtil.getInstance(Drawer.this).isInternet()) {

                Intent i=new Intent(getApplicationContext(),Drawer.class);
                startActivity(i);
                finish();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    public String filterStringToNormal(String data) {
        String input = "Carmen López-Delina Santos";
        input = data;
        String withoutAccent = Normalizer.normalize(input, Normalizer.Form.NFD);
        String output = withoutAccent.replaceAll("[^a-zA-Z ]", "");
        System.out.println(output);
        return data;
    }

    class LogoutAPI extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/users/logout";
        String Status="", message="", note, written_by, student_name, image;

        private MyCustomProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new MyCustomProgressDialog(Drawer.this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        protected String doInBackground(String... args) {

            String logoutResponse = "";


            try {
                try {

                    String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(securityKey, "UTF-8") +
                            "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                            "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                            "&" + Const.Params.DeviceTokenApp + "=" + URLEncoder.encode(regId, "UTF-8") ;


                    logoutResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
/*
                JSONObject jsonObj = new JSONObject(json.toString());
*/

                /*JSONObject json = new JSONObject();
                JSONParser jsonParser = new JSONParser();

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("securityKey", securityKey));
                params.add(new BasicNameValuePair("authentication_token",
                        auth_token));
                params.add(new BasicNameValuePair("language", lang));
                params.add(new BasicNameValuePair("device_token_app", regId));

                Log.d("response ", params.toString());

                json = jsonParser.makeHttpRequest(url, "POST", params);*/

            } catch (Exception e) {
                e.printStackTrace();
            }

            return logoutResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {
                JSONObject logoutJson = new JSONObject();
                if (results != null && !results.isEmpty()) {
                    logoutJson = new JSONObject(results);
                    Log.d("logoutJson",""+logoutJson);
                    if (logoutJson.has(Const.Params.Status)) {
                        Status = logoutJson.getString("status").toString();
                    } else {
                        message = "Service Failed";
                    }
                } else {
                    message = "Service Failed";
                }
                if (Status.equalsIgnoreCase("true")) {
                    session.logoutUser();
                    Intent intent = new Intent(Drawer.this, Login.class);
                    // if you are checking for this in your other Activities
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else {

                    try {


                        String msg = logoutJson.getString("message");
                        System.out.print(msg);

                        if (lang.equalsIgnoreCase("sw")) {
                            System.out.print("Sw_l");
                            if (msg.equalsIgnoreCase("Autentisering misslyckades")) {
                                Button btnLogout;
                                TextView tvMessage;
                                final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", Drawer.this);
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
                                final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", Drawer.this);
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
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                    if(logoutJson.has(Const.Params.Message)) {
                        message = logoutJson.getString(Const.Params.Message).toString();
                    }
                    SmartClassUtil.showToast(getApplicationContext(), message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class My_Account extends Fragment {

        LinearLayout trackBusLayout;
        Drawer dr;
        GridView gv;
        Context context;
        ArrayList prgmName;
        UserSessionManager session;
        LinearLayout actionbar;
        ViewGroup actionBarLayout;
        String srch;
        String lang, username, pass, token, auth_token, auth_token_new = null,
                user_name, password, new_Authntication, regId;
        String Base_url;
        String language;
        String compont;
        String Security = "H67jdS7wwfh";
        String lng;
        View v;
        String[] component_id, component, component_app_status;
        public String[] prgmNameList = {"Retrieval List", "Edu Blog", "Forum",
                "News", "Schedule", "Portfolio"};

        public String[] counter = {"2", "", "2", "", "", ""};
        int tiles = 0;

        @SuppressLint("NewApi")
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            v = inflater.inflate(R.layout.my__account, container, false);

            session = new UserSessionManager(getActivity());

            HashMap<String, String> user = session.getUserDetails();
            lang = user.get(UserSessionManager.TAG_language);
            auth_token = user.get(UserSessionManager.TAG_Authntication_token);
            auth_token_new = user
                    .get(UserSessionManager.TAG_Authntication_new_tokn);
            Base_url = user.get(UserSessionManager.TAG_Base_url);
            user_name = user.get(UserSessionManager.TAG_username);
            password = user.get(UserSessionManager.TAG_password);
            regId = user.get(UserSessionManager.TAG_regId);
            new_Authntication = user
                    .get(UserSessionManager.TAG_Authntication_new_tokn);

            gv = (GridView) v.findViewById(R.id.gridForCategory);
            trackBusLayout=(LinearLayout)v.findViewById(R.id.trackBusLayout);
            trackBusLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                 /*   String url = "https://map-html-kishoreindraganti.c9users.io/hello-world.html";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);*/


                    Intent schedeluIntent = new Intent(getActivity(), AsynchronousActivity.class);
                    getActivity().startActivity(schedeluIntent);

                }
            });

            Bundle bundle = this.getArguments();

            int compLength = bundle.getStringArray("compnt_name").length;

            System.out.println("length" + compLength);

            String[] cmpnt_name = new String[compLength];
            String[] cmpnt_name_sw = new String[compLength];
            String[] cmpnt_image = new String[compLength];
            component_id = new String[compLength];
            component_app_status = new String[compLength];


            cmpnt_name = bundle.getStringArray("compnt_name");
            cmpnt_name_sw = bundle.getStringArray("compnt_name_sw");
            cmpnt_image = bundle.getStringArray("compnt_image");
            component_id = bundle.getStringArray("compnt_id");
            component_app_status = bundle.getStringArray("compnt_status");

            tiles = bundle.getInt("tile_Length");


            if(tiles > 10){
                gv.setNumColumns(3);
                gv.setColumnWidth(100);
                gv.setVerticalSpacing(26);
                gv.setHorizontalSpacing(18);
            }else{
                gv.setNumColumns(2);
            }

            if (lang.equalsIgnoreCase("sw")) {
                gv.setAdapter(new CustomAdapter_drawer(getActivity(),
                        cmpnt_name_sw, cmpnt_image, tiles, component_app_status));
            }
            if (lang.equalsIgnoreCase("en")) {
                gv.setAdapter(new CustomAdapter_drawer(getActivity(),
                        cmpnt_name, cmpnt_image, tiles, component_app_status));
            }

            return v;
        }

        // ////////////////////////////////////////////////////
        public class CustomAdapter_drawer extends BaseAdapter {

            String[] result;
            String[] notification;
            Activity context;
            String[] imageId;
            LayoutInflater inflater = null;
            int tile = 0;
            String[] appStatus;
            View rowView;

            public CustomAdapter_drawer(Activity activity, String[] component,
                                        String[] image, int tiles, String[] app_status) {
                // TODO Auto-generated constructor stub

                context = activity;

                tile = tiles;
                result = component;
                imageId = image;
                appStatus = app_status;
                // notification = counter;

                inflater = (LayoutInflater) (getActivity())
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return result.length;
            }

            @Override
            public Object getItem(int position) {
                // TODO Auto-generated method stub
                return position;
            }

            @Override
            public long getItemId(int position) {
                // TODO Auto-generated method stub
                return position;
            }

            public class Holder {
                TextView tv;
                ImageView imagView;
                TextView notf;
                RelativeLayout grid_relative_layout;
            }

            @Override
            public View getView(final int position, View convertView,
                                ViewGroup parent) {
                // TODO Auto-generated method stub
                Holder holder = new Holder();


                // Log.i("c===+++=...", Arrays.deepToString(imageId));

                if(tile > 10) {
                    rowView = inflater.inflate(R.layout.category_grid_small_layout, null);
                }else{
                    rowView = inflater.inflate(R.layout.category_grid_layout, null);
                }
                holder.tv = (TextView) rowView
                        .findViewById(R.id.txtForListCategory);
                holder.notf = (TextView) rowView
                        .findViewById(R.id.txtForListCategory1);
                holder.imagView = (ImageView) rowView
                        .findViewById(R.id.imgForListCategory);
                holder.grid_relative_layout = (RelativeLayout) rowView.findViewById(R.id.grid_relative_layout);
//                new ImageLoadTaskcliptwo(imageId[position], holder.imagView)
//                        .execute();
//                if(tile > 10){
//                    holder.grid_relative_layout.getLayoutParams().height = 220;
//                    holder.grid_relative_layout.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
//                    holder.notf.getLayoutParams().height = 35;
//                    holder.notf.getLayoutParams().width = 35;
//                    holder.tv.setTextSize(10);
//                    holder.imagView.getLayoutParams().height = 70;
//                    holder.imagView.getLayoutParams().width = 70;
//                }

                Picasso.with(getActivity())
                        .load(imageId[position])
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(holder.imagView);

                holder.tv.setText(result[position]);
                if (component_id[position].equalsIgnoreCase("16")){
                    if(lang.equalsIgnoreCase("sw")) {
                        holder.tv.setText("Portfölj");
                    }else{
                        holder.tv.setText("Portfolio");
                    }
                }

                if (component_id[position].equalsIgnoreCase("latest")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }

                if (component_id[position].equalsIgnoreCase("105")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }

                if (component_id[position].equalsIgnoreCase("67")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (component_id[position].equalsIgnoreCase("29")) {

                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                }
                if (component_id[position].equalsIgnoreCase("28")) {

                    if (eduBlog_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (eduBlog_count.equalsIgnoreCase(eduBlog_countTeacher)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(eduBlog_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (component_id[position].equalsIgnoreCase("23")) {
                    if (news_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (news_count.equalsIgnoreCase(news_countTeacher)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(news_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }

                if (component_id[position].equalsIgnoreCase("27")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (component_id[position].equalsIgnoreCase("63")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                }
                if (component_id[position].equalsIgnoreCase("11")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (component_id[position].equalsIgnoreCase("78")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (component_id[position].equalsIgnoreCase("100")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }
                if (component_id[position].equalsIgnoreCase("87") || component_id[position].equalsIgnoreCase("87q")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (component_id[position].equalsIgnoreCase("85")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (component_id[position].equalsIgnoreCase("73")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                }
                if (component_id[position].equalsIgnoreCase("62")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (component_id[position].equalsIgnoreCase("81")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (component_id[position].equalsIgnoreCase("68")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (component_id[position].equalsIgnoreCase("50")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (component_id[position].equalsIgnoreCase("16")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (component_id[position].equalsIgnoreCase("103")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (component_id[position].equalsIgnoreCase("10")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (component_id[position].equalsIgnoreCase("47")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (component_id[position].equalsIgnoreCase("83")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                }

                if (position == 0) {

                    if (component_id[position].equalsIgnoreCase("67")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("29")) {

                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                    }
                    if (component_id[position].equalsIgnoreCase("28")) {

                        if (eduBlog_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (eduBlog_count.equalsIgnoreCase(eduBlog_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(eduBlog_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("23")) {
                        if (news_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (news_count.equalsIgnoreCase(news_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(news_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }

                    if (component_id[position].equalsIgnoreCase("27")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                    }
                    if (component_id[position].equalsIgnoreCase("63")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("11")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("78")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("100")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                    }
                    if (component_id[position].equalsIgnoreCase("87") || component_id[position].equalsIgnoreCase("87q")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("85")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("73")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("62")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("81")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("68")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("50")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("16")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("103")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("10")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("47")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                }
                if (position == 1) {
                    if (component_id[position].equalsIgnoreCase("67")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }

                    if (component_id[position].equalsIgnoreCase("29")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                    }
                    if (component_id[position].equalsIgnoreCase("28")) {
                        if (eduBlog_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (eduBlog_count.equalsIgnoreCase(eduBlog_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(eduBlog_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("23")) {
                        if (news_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (news_count.equalsIgnoreCase(news_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(news_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }

                    if (component_id[position].equalsIgnoreCase("27")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                    }
                    if (component_id[position].equalsIgnoreCase("63")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("11")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("78")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("100")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                    }
                    if (component_id[position].equalsIgnoreCase("87") || component_id[position].equalsIgnoreCase("87q")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("85")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("73")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("62")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("81")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("68")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("50")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("16")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("103")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("10")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("47")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                }

                if (position == 2) {
                    if (component_id[position].equalsIgnoreCase("67")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }

                    if (component_id[position].equalsIgnoreCase("29")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                    }
                    if (component_id[position].equalsIgnoreCase("28")) {

                        if (eduBlog_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (eduBlog_count.equalsIgnoreCase(eduBlog_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(eduBlog_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("23")) {
                        if (news_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (news_count.equalsIgnoreCase(news_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(news_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }


                    if (component_id[position].equalsIgnoreCase("27")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                    }
                    if (component_id[position].equalsIgnoreCase("63")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("11")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("78")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("100")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                    }
                    if (component_id[position].equalsIgnoreCase("87") || component_id[position].equalsIgnoreCase("87q")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("85")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("73")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("62")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("81")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("68")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("50")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("16")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("103")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("10")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("47")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                }
                if (position == 3) {
                    // holder.notf.setVisibility(View.GONE);

                    if (component_id[position].equalsIgnoreCase("27")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                    }/* else {
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));

                    }*/
                    if (component_id[position].equalsIgnoreCase("63")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("67")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }

                    if (component_id[position].equalsIgnoreCase("29")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                    }
                    if (component_id[position].equalsIgnoreCase("28")) {

                        if (eduBlog_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (eduBlog_count.equalsIgnoreCase(eduBlog_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(eduBlog_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("23")) {
                        if (news_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (news_count.equalsIgnoreCase(news_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(news_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("11")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("78")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("100")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                    }
                    if (component_id[position].equalsIgnoreCase("87") || component_id[position].equalsIgnoreCase("87q")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("85")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("73")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("62")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("81")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("68")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("50")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("16")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("103")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("10")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("47")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                }
                if (position == 4) {
                    // holder.notf.setVisibility(View.GONE);

                    if (component_id[position].equalsIgnoreCase("27")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                    }
                    if (component_id[position].equalsIgnoreCase("63")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("67")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }

                    if (component_id[position].equalsIgnoreCase("29")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                    }
                    if (component_id[position].equalsIgnoreCase("28")) {

                        if (eduBlog_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (eduBlog_count.equalsIgnoreCase(eduBlog_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(eduBlog_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("23")) {
                        if (news_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (news_count.equalsIgnoreCase(news_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(news_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("11")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("78")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("100")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                    }
                    if (component_id[position].equalsIgnoreCase("87") || component_id[position].equalsIgnoreCase("87q")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("85")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("73")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("62")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("81")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("68")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("50")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("16")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("103")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("10")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("47")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                } else if (position == 5) {
                    // holder.notf.setVisibility(View.GONE);

                    if (component_id[position].equalsIgnoreCase("27")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                    }
                    if (component_id[position].equalsIgnoreCase("63")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("67")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }

                    if (component_id[position].equalsIgnoreCase("29")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                    }
                    if (component_id[position].equalsIgnoreCase("28")) {

                        if (eduBlog_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (eduBlog_count.equalsIgnoreCase(eduBlog_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(eduBlog_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("23")) {
                        if (news_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (news_count.equalsIgnoreCase(news_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(news_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("11")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("78")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("100")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                    }
                    if (component_id[position].equalsIgnoreCase("87") || component_id[position].equalsIgnoreCase("87q")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("85")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("73")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("62")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("81")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("68")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("50")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("16")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("103")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("10")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("47")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                } else if (position == 6) {
                    // holder.notf.setVisibility(View.GONE);

                    if (component_id[position].equalsIgnoreCase("27")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                    }
                    if (component_id[position].equalsIgnoreCase("63")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("67")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }

                    if (component_id[position].equalsIgnoreCase("29")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                    }
                    if (component_id[position].equalsIgnoreCase("28")) {

                        if (eduBlog_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (eduBlog_count.equalsIgnoreCase(eduBlog_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(eduBlog_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("23")) {
                        if (news_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (news_count.equalsIgnoreCase(news_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(news_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("11")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("78")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("100")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                    }
                    if (component_id[position].equalsIgnoreCase("87") || component_id[position].equalsIgnoreCase("87q")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("85")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("73")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("62")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("81")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("68")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("50")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("16")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("103")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("10")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("47")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                }
                else if (position == 7) {
                    // holder.notf.setVisibility(View.GONE);

                    if (component_id[position].equalsIgnoreCase("27")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                    }
                    if (component_id[position].equalsIgnoreCase("63")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("67")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }

                    if (component_id[position].equalsIgnoreCase("29")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                    }
                    if (component_id[position].equalsIgnoreCase("28")) {

                        if (eduBlog_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (eduBlog_count.equalsIgnoreCase(eduBlog_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(eduBlog_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("23")) {
                        if (news_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (news_count.equalsIgnoreCase(news_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(news_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("11")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("78")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("100")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                    }
                    if (component_id[position].equalsIgnoreCase("87") || component_id[position].equalsIgnoreCase("87q")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("85")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("73")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("62")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("81")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("68")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("50")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("16")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("103")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("10")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("47")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                }
                else if (position == 8) {
                    // holder.notf.setVisibility(View.GONE);

                    if (component_id[position].equalsIgnoreCase("27")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                    }
                    if (component_id[position].equalsIgnoreCase("63")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("67")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }

                    if (component_id[position].equalsIgnoreCase("29")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                    }
                    if (component_id[position].equalsIgnoreCase("28")) {

                        if (eduBlog_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (eduBlog_count.equalsIgnoreCase(eduBlog_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(eduBlog_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("23")) {
                        if (news_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (news_count.equalsIgnoreCase(news_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(news_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("11")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("78")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("100")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                    }
                    if (component_id[position].equalsIgnoreCase("87") || component_id[position].equalsIgnoreCase("87q")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("85")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("73")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("62")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("81")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("68")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("50")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("16")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("103")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("10")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("47")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                }
                else if (position == 9) {
                    // holder.notf.setVisibility(View.GONE);

                    if (component_id[position].equalsIgnoreCase("27")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                    }
                    if (component_id[position].equalsIgnoreCase("63")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("67")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }

                    if (component_id[position].equalsIgnoreCase("29")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                    }
                    if (component_id[position].equalsIgnoreCase("28")) {

                        if (eduBlog_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (eduBlog_count.equalsIgnoreCase(eduBlog_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(eduBlog_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("23")) {
                        if (news_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (news_count.equalsIgnoreCase(news_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(news_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("11")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("78")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("100")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                    }
                    if (component_id[position].equalsIgnoreCase("87") || component_id[position].equalsIgnoreCase("87q")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("85")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("73")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("62")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("81")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("68")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("50")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("16")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("103")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("10")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("47")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                }
                else if (position == 10) {
                    // holder.notf.setVisibility(View.GONE);

                    if (component_id[position].equalsIgnoreCase("27")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                    }
                    if (component_id[position].equalsIgnoreCase("63")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("67")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }

                    if (component_id[position].equalsIgnoreCase("29")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                    }
                    if (component_id[position].equalsIgnoreCase("28")) {

                        if (eduBlog_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (eduBlog_count.equalsIgnoreCase(eduBlog_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(eduBlog_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("23")) {
                        if (news_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (news_count.equalsIgnoreCase(news_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(news_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("11")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("78")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("100")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                    }
                    if (component_id[position].equalsIgnoreCase("87") || component_id[position].equalsIgnoreCase("87q")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("85")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("73")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("62")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("81")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("68")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("50")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("16")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("103")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("10")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("47")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                }
                else if (position == 11) {
                    // holder.notf.setVisibility(View.GONE);

                    if (component_id[position].equalsIgnoreCase("27")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                    }
                    if (component_id[position].equalsIgnoreCase("63")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("67")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }

                    if (component_id[position].equalsIgnoreCase("29")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                    }
                    if (component_id[position].equalsIgnoreCase("28")) {

                        if (eduBlog_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (eduBlog_count.equalsIgnoreCase(eduBlog_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(eduBlog_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("23")) {
                        if (news_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (news_count.equalsIgnoreCase(news_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(news_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("11")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("78")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("100")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                    }
                    if (component_id[position].equalsIgnoreCase("87") || component_id[position].equalsIgnoreCase("87q")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("85")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("73")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("62")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("81")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("68")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("50")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("16")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("103")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("10")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("47")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                }
                else if (position == 12) {
                    // holder.notf.setVisibility(View.GONE);

                    if (component_id[position].equalsIgnoreCase("27")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                    }
                    if (component_id[position].equalsIgnoreCase("63")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("67")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }

                    if (component_id[position].equalsIgnoreCase("29")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                    }
                    if (component_id[position].equalsIgnoreCase("28")) {

                        if (eduBlog_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (eduBlog_count.equalsIgnoreCase(eduBlog_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(eduBlog_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("23")) {
                        if (news_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (news_count.equalsIgnoreCase(news_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(news_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("11")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("78")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("100")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                    }
                    if (component_id[position].equalsIgnoreCase("87") || component_id[position].equalsIgnoreCase("87q")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("85")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("73")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("62")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("81")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("68")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("50")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("16")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("103")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("10")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("47")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                }
                else if (position == 13) {
                    // holder.notf.setVisibility(View.GONE);

                    if (component_id[position].equalsIgnoreCase("27")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                    }
                    if (component_id[position].equalsIgnoreCase("63")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("67")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }

                    if (component_id[position].equalsIgnoreCase("29")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                    }
                    if (component_id[position].equalsIgnoreCase("28")) {

                        if (eduBlog_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (eduBlog_count.equalsIgnoreCase(eduBlog_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(eduBlog_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("23")) {
                        if (news_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (news_count.equalsIgnoreCase(news_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(news_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("11")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("78")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("100")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                    }
                    if (component_id[position].equalsIgnoreCase("87") || component_id[position].equalsIgnoreCase("87q")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("85")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("73")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("62")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("81")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("68")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("50")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("16")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("103")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("10")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("47")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                }
                else if (position == 14) {
                    // holder.notf.setVisibility(View.GONE);

                    if (component_id[position].equalsIgnoreCase("27")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                    }
                    if (component_id[position].equalsIgnoreCase("63")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("67")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }

                    if (component_id[position].equalsIgnoreCase("29")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                    }
                    if (component_id[position].equalsIgnoreCase("28")) {

                        if (eduBlog_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (eduBlog_count.equalsIgnoreCase(eduBlog_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(eduBlog_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("23")) {
                        if (news_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (news_count.equalsIgnoreCase(news_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(news_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("11")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("78")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("100")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                    }
                    if (component_id[position].equalsIgnoreCase("87") || component_id[position].equalsIgnoreCase("87q")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("85")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("73")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("62")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("81")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("68")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("50")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("16")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("103")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("10")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("47")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                }
                else if (position == 15) {
                    // holder.notf.setVisibility(View.GONE);

                    if (component_id[position].equalsIgnoreCase("27")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                    }
                    if (component_id[position].equalsIgnoreCase("63")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("67")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }

                    if (component_id[position].equalsIgnoreCase("29")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                    }
                    if (component_id[position].equalsIgnoreCase("28")) {

                        if (eduBlog_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (eduBlog_count.equalsIgnoreCase(eduBlog_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(eduBlog_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("23")) {
                        if (news_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (news_count.equalsIgnoreCase(news_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(news_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("11")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("78")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("100")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                    }
                    if (component_id[position].equalsIgnoreCase("87") || component_id[position].equalsIgnoreCase("87q")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("85")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("73")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("62")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("81")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("68")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("50")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("16")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("103")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("10")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("47")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                }
                else if (position == 16) {
                    // holder.notf.setVisibility(View.GONE);

                    if (component_id[position].equalsIgnoreCase("27")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                    }
                    if (component_id[position].equalsIgnoreCase("63")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("67")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }

                    if (component_id[position].equalsIgnoreCase("29")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                    }
                    if (component_id[position].equalsIgnoreCase("28")) {

                        if (eduBlog_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (eduBlog_count.equalsIgnoreCase(eduBlog_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(eduBlog_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("23")) {
                        if (news_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (news_count.equalsIgnoreCase(news_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(news_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("11")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("78")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("100")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                    }
                    if (component_id[position].equalsIgnoreCase("87") || component_id[position].equalsIgnoreCase("87q")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("85")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("73")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("62")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("81")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("68")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("50")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("16")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("103")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("10")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("47")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                }
                else if (position == 17) {
                    // holder.notf.setVisibility(View.GONE);

                    if (component_id[position].equalsIgnoreCase("27")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                    }
                    if (component_id[position].equalsIgnoreCase("63")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("67")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }

                    if (component_id[position].equalsIgnoreCase("29")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                    }
                    if (component_id[position].equalsIgnoreCase("28")) {

                        if (eduBlog_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (eduBlog_count.equalsIgnoreCase(eduBlog_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(eduBlog_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("23")) {
                        if (news_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (news_count.equalsIgnoreCase(news_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(news_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("11")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("78")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("100")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                    }
                    if (component_id[position].equalsIgnoreCase("87") || component_id[position].equalsIgnoreCase("87q")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("85")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("73")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("62")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("81")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("68")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("50")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("16")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("103")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("10")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("47")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                }
                else if (position == 18) {
                    // holder.notf.setVisibility(View.GONE);

                    if (component_id[position].equalsIgnoreCase("27")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                    }
                    if (component_id[position].equalsIgnoreCase("63")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("67")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }

                    if (component_id[position].equalsIgnoreCase("29")) {
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
                    }
                    if (component_id[position].equalsIgnoreCase("28")) {

                        if (eduBlog_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (eduBlog_count.equalsIgnoreCase(eduBlog_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(eduBlog_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                    }
                    if (component_id[position].equalsIgnoreCase("23")) {
                        if (news_count.equalsIgnoreCase("0")) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            if (news_count.equalsIgnoreCase(news_countTeacher)) {
                                holder.notf.setVisibility(View.GONE);
                            } else {
                                holder.notf.setText(news_count);
                            }
                        }
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("11")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("78")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("100")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                    }
                    if (component_id[position].equalsIgnoreCase("87") || component_id[position].equalsIgnoreCase("87q")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("85")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("73")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("62")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("81")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("68")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    }
                    if (component_id[position].equalsIgnoreCase("50")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                    }
                    if (component_id[position].equalsIgnoreCase("16")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                    }
                    if (component_id[position].equalsIgnoreCase("103")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                    }
                    if (component_id[position].equalsIgnoreCase("10")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                    if (component_id[position].equalsIgnoreCase("47")){
                        holder.notf.setVisibility(View.GONE);
                        rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                    }
                }


                rowView.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        final Bundle bundle = new Bundle();

                        if (component_id[position].equalsIgnoreCase("latest")){
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
    //                            Toast.makeText(context,"Under Development",Toast.LENGTH_SHORT).show();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "smart_notes_wall");
                                    bundle.putString("name_en", "Latest");
                                    bundle.putString("name_sw", "Senaste");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }

                        if (component_id[position].equalsIgnoreCase("105")){
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
    //                            Toast.makeText(context,"Under Development",Toast.LENGTH_SHORT).show();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "location_details");
                                    bundle.putString("name_en", "Locations");
                                    bundle.putString("name_sw", "Platser");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }

                        if (component_id[position].equalsIgnoreCase("11")){
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
    //                            Toast.makeText(context,"Under Development",Toast.LENGTH_SHORT).show();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "forum");
                                    bundle.putString("name_en", "Forum");
                                    bundle.putString("name_sw", "Forum");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if(component_id[position].equalsIgnoreCase("100")){
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "edu_step_planner");
                                    bundle.putString("name_en", "Edu steps");
                                    bundle.putString("name_sw", "Lärstegsplaneraren");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if(component_id[position].equalsIgnoreCase("78")){
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "progresschart");
                                    bundle.putString("name_en", "Progress Table");
                                    bundle.putString("name_sw", "Utvecklingsschema");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if(component_id[position].equalsIgnoreCase("87")){
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "quiz_manager");
                                    bundle.putString("name_en", "Quiz Manager");
                                    bundle.putString("name_sw", "Quiz Manager");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if(component_id[position].equalsIgnoreCase("85")){
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "message_manager");
                                    bundle.putString("name_en", "Message Manager");
                                    bundle.putString("name_sw", "Message Manager");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if(component_id[position].equalsIgnoreCase("73")){
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "edublog_statistics");
                                    bundle.putString("name_en", "Edublog Statistics");
                                    bundle.putString("name_sw", "Läroblogg Statistik");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if(component_id[position].equalsIgnoreCase("62")){
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "retrieval_statistics");
                                    bundle.putString("name_en", "Retrieval Statistics");
                                    bundle.putString("name_sw", "Närvaro Statistik");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if(component_id[position].equalsIgnoreCase("81")){
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "contact_information");
                                    bundle.putString("name_en", "Contact Information");
                                    bundle.putString("name_sw", "Kontakt Information");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if(component_id[position].equalsIgnoreCase("68")){
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "school_information");
                                    bundle.putString("name_en", "School Information");
                                    bundle.putString("name_sw", "Skolinformation");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if(component_id[position].equalsIgnoreCase("16")){
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "portfolio");
                                    bundle.putString("name_en", "Portfolio");
                                    bundle.putString("name_sw", "Portfölj");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if(component_id[position].equalsIgnoreCase("103")){
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "evaluation_tables");
                                    bundle.putString("name_en", "Evaluation Matrix");
                                    bundle.putString("name_sw", "Bedömningsmatris");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if(component_id[position].equalsIgnoreCase("50")){
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "survey_manager");
                                    bundle.putString("name_en", "Survey Forms");
                                    bundle.putString("name_sw", "Enkätutskick");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if(component_id[position].equalsIgnoreCase("10")){
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "examination");
                                    bundle.putString("name_en", "Examinations");
                                    bundle.putString("name_sw", "Prov");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if(component_id[position].equalsIgnoreCase("47")){
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "course_videos");
                                    bundle.putString("name_en", "Course Videos");
                                    bundle.putString("name_sw", "Kursen Videor");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();

                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if(component_id[position].equalsIgnoreCase("87q")){
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "quiz_editor");
                                    bundle.putString("name_en", "Quiz Editor");
                                    bundle.putString("name_sw", "Quiz Editeraren");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (component_id[position].equalsIgnoreCase("67")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    img.setVisibility(View.VISIBLE);
                                    Child_Info rFragment = new Child_Info();
                                    FragmentTransaction ft = fragmentManager.beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (component_id[position].equalsIgnoreCase("29")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
                                    if (appStatus[position].equalsIgnoreCase("0")) {
                                        img.setVisibility(View.VISIBLE);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        AttandanceMainScreen rFragment = new AttandanceMainScreen();
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();

                                    } else {
                                        FragmentManager fragmentManager = getFragmentManager();
                                        WebFragment rFragment = new WebFragment();
                                        bundle.putString("Component_Key", "attendance");
                                        bundle.putString("name_en", "Retrieval List");
                                        bundle.putString("name_sw", "Närvarolista");
                                        rFragment.setArguments(bundle);
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    }
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (component_id[position].equalsIgnoreCase("28")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
                                    if (appStatus[position].equalsIgnoreCase("0")) {
                                        Log.d("LMS", "status of edublog component is --> " + appStatus[position]);
                                        session.EduCountTeacher(eduBlog_count);
                                        refresh.setVisibility(View.VISIBLE);
                                        img.setVisibility(View.VISIBLE);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        MainActivity rFragment = new MainActivity();
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    } else {
                                        FragmentManager fragmentManager = getFragmentManager();
                                        WebFragment rFragment = new WebFragment();
                                        bundle.putString("Component_Key", "edublog");
                                        bundle.putString("name_en", "Edu Blog");
                                        bundle.putString("name_sw", "Läroblogg");
                                        rFragment.setArguments(bundle);
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    }
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (component_id[position].equalsIgnoreCase("23")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
                                    if (appStatus[position].equalsIgnoreCase("0")) {
                                        session.NewsCountTeacher(news_count);
                                        img.setVisibility(View.VISIBLE);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        News_Post rFragment = new News_Post();
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    } else {
                                        FragmentManager fragmentManager = getFragmentManager();
                                        WebFragment rFragment = new WebFragment();
                                        bundle.putString("Component_Key", "news");
                                        bundle.putString("name_en", "News");
                                        bundle.putString("name_sw", "Nyheter");
                                        rFragment.setArguments(bundle);
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    }
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }

                        }
                        if (component_id[position].equalsIgnoreCase("27")) {

                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                   /* FragmentManager fragmentManager = getFragmentManager();

                                    ScheduleFragment rFragment = new ScheduleFragment();

                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();*/
                                    Intent schedeluIntent = new Intent(getActivity(), AsynchronousActivity.class);
                                    schedeluIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    getActivity().startActivity(schedeluIntent);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (component_id[position].equalsIgnoreCase("63")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                   /* holder.notf.setVisibility(View.GONE);
                                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));*/
                                    Intent foodMenuIntent = new Intent(getActivity(), AddFoodMenuActivity.class);
                                    startActivity(foodMenuIntent);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if(component_id[position].equalsIgnoreCase("83")){
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    MYAccount.setVisibility(View.GONE);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "week_list");
                                    bundle.putString("name_en", "Week View");
                                    bundle.putString("name_sw", "Veckonärvaro");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (position == 0) {

                                if (component_id[position].equalsIgnoreCase("67")) {
                                    try {
                                        if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                            MYAccount.setVisibility(View.GONE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            img.setVisibility(View.VISIBLE);
                                            Child_Info rFragment = new Child_Info();
                                            FragmentTransaction ft = fragmentManager.beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                }


                            if (component_id[position].equalsIgnoreCase("29")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            AttandanceMainScreen rFragment = new AttandanceMainScreen();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "attendance");
                                            bundle.putString("name_en", "Retrieval List");
                                            bundle.putString("name_sw", "Närvarolista");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("28")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            Log.d("LMS", "status of edublog component is --> " + appStatus[position]);
                                            session.EduCountTeacher(eduBlog_count);
                                            refresh.setVisibility(View.VISIBLE);
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            MainActivity rFragment = new MainActivity();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "edublog");
                                            bundle.putString("name_en", "Edu Blog");
                                            bundle.putString("name_sw", "Läroblogg");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("23")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            session.NewsCountTeacher(news_count);
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            News_Post rFragment = new News_Post();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "news");
                                            bundle.putString("name_en", "News");
                                            bundle.putString("name_sw", "Nyheter");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }

                            }
                            if (component_id[position].equalsIgnoreCase("27")) {

                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                   /* FragmentManager fragmentManager = getFragmentManager();

                                    ScheduleFragment rFragment = new ScheduleFragment();

                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();*/
                                        Intent schedeluIntent = new Intent(getActivity(), AsynchronousActivity.class);
                                        schedeluIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                        getActivity().startActivity(schedeluIntent);
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("63")) {
                               /* holder.notf.setVisibility(View.GONE);
                                rowView.setBackgroundColor(Color.parseColor("#8863A9"));*/
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        Intent foodMenuIntent = new Intent(getActivity(), AddFoodMenuActivity.class);
                                        startActivity(foodMenuIntent);
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        if (position == 1) {

                                if (component_id[position].equalsIgnoreCase("67")) {
                                    try {
                                        if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                            MYAccount.setVisibility(View.GONE);
                                            FragmentManager fragmentManager = getFragmentManager();

                                        /* Creating fragment instance */
                                            Child_Info rFragment = new Child_Info();
                                            img.setVisibility(View.VISIBLE);
                                        /* Passing selected item information to fragment */
                                        /* Replace fragment */
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                }

                            if (component_id[position].equalsIgnoreCase("29")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            AttandanceMainScreen rFragment = new AttandanceMainScreen();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "attendance");
                                            bundle.putString("name_en", "Retrieval List");
                                            bundle.putString("name_sw", "Närvarolista");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("28")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            Log.d("LMS", "status of edublog component is --> " + appStatus[position]);
                                            session.EduCountTeacher(eduBlog_count);
                                            refresh.setVisibility(View.VISIBLE);
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            MainActivity rFragment = new MainActivity();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "edublog");
                                            bundle.putString("name_en", "Edu Blog");
                                            bundle.putString("name_sw", "Läroblogg");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("23")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            session.NewsCountTeacher(news_count);
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            News_Post rFragment = new News_Post();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "news");
                                            bundle.putString("name_en", "News");
                                            bundle.putString("name_sw", "Nyheter");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("27")) {


                                /*FragmentManager fragmentManager = getFragmentManager();


                                ScheduleFragment rFragment = new ScheduleFragment();

                                FragmentTransaction ft = fragmentManager
                                        .beginTransaction();
                                ft.replace(R.id.content_frame, rFragment);
                                ft.commit();*/
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        Intent schedeluIntent = new Intent(getActivity(), AsynchronousActivity.class);
                                        schedeluIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                        getActivity().startActivity(schedeluIntent);
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }

                            if (component_id[position].equalsIgnoreCase("63")) {
                               /* holder.notf.setVisibility(View.GONE);
                                rowView.setBackgroundColor(Color.parseColor("#8863A9"));*/
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        Intent foodMenuIntent = new Intent(getActivity(), AddFoodMenuActivity.class);
                                        startActivity(foodMenuIntent);
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (position == 2) {

                                if (component_id[position].equalsIgnoreCase("67")) {
                                    try {
                                        if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                            MYAccount.setVisibility(View.GONE);
                                            FragmentManager fragmentManager = getFragmentManager();

                                        /* Creating fragment instance */
                                            Child_Info rFragment = new Child_Info();
                                            img.setVisibility(View.VISIBLE);
                                        /* Passing selected item information to fragment */
                                        /* Replace fragment */
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                }

                            if (component_id[position].equalsIgnoreCase("29")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            AttandanceMainScreen rFragment = new AttandanceMainScreen();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "attendance");
                                            bundle.putString("name_en", "Retrieval List");
                                            bundle.putString("name_sw", "Närvarolista");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("28")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            Log.d("LMS", "status of edublog component is --> " + appStatus[position]);
                                            session.EduCountTeacher(eduBlog_count);
                                            refresh.setVisibility(View.VISIBLE);
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            MainActivity rFragment = new MainActivity();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "edublog");
                                            bundle.putString("name_en", "Edu Blog");
                                            bundle.putString("name_sw", "Läroblogg");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("23")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            session.NewsCountTeacher(news_count);
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            News_Post rFragment = new News_Post();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "news");
                                            bundle.putString("name_en", "News");
                                            bundle.putString("name_sw", "Nyheter");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("27")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                                   /* FragmentManager fragmentManager = getFragmentManager();


                                    ScheduleFragment rFragment = new ScheduleFragment();

                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();*/
                                        Intent schedeluIntent = new Intent(getActivity(), AsynchronousActivity.class);
                                        schedeluIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                        getActivity().startActivity(schedeluIntent);
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("63")) {
                               /* holder.notf.setVisibility(View.GONE);
                                rowView.setBackgroundColor(Color.parseColor("#8863A9"));*/
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        Intent foodMenuIntent = new Intent(getActivity(), AddFoodMenuActivity.class);
                                        startActivity(foodMenuIntent);
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                        if (position == 3) {

                            if (component_id[position].equalsIgnoreCase("67")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        FragmentManager fragmentManager = getFragmentManager();

                                    /* Creating fragment instance */
                                        Child_Info rFragment = new Child_Info();
                                        img.setVisibility(View.VISIBLE);
                                    /* Passing selected item information to fragment */
                                    /* Replace fragment */
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("29")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            AttandanceMainScreen rFragment = new AttandanceMainScreen();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "attendance");
                                            bundle.putString("name_en", "Retrieval List");
                                            bundle.putString("name_sw", "Närvarolista");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("28")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            Log.d("LMS", "status of edublog component is --> " + appStatus[position]);
                                            session.EduCountTeacher(eduBlog_count);
                                            refresh.setVisibility(View.VISIBLE);
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            MainActivity rFragment = new MainActivity();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "edublog");
                                            bundle.putString("name_en", "Edu Blog");
                                            bundle.putString("name_sw", "Läroblogg");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("23")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            session.NewsCountTeacher(news_count);
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            News_Post rFragment = new News_Post();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "news");
                                            bundle.putString("name_en", "News");
                                            bundle.putString("name_sw", "Nyheter");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("27")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                                        MYAccount.setVisibility(View.GONE);
                                        FragmentManager fragmentManager = getFragmentManager();


                                   /* ScheduleFragment rFragment = new ScheduleFragment();

                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();*/
                                        Intent schedeluIntent = new Intent(getActivity(), AsynchronousActivity.class);
                                        schedeluIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                        getActivity().startActivity(schedeluIntent);
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }

                            if (component_id[position].equalsIgnoreCase("63")) {
                               /* holder.notf.setVisibility(View.GONE);
                                rowView.setBackgroundColor(Color.parseColor("#8863A9"));*/
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        Intent foodMenuIntent = new Intent(getActivity(), AddFoodMenuActivity.class);
                                        startActivity(foodMenuIntent);
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        if(position==4)
                        {
                            MYAccount.setVisibility(View.GONE);
                            if (component_id[position].equalsIgnoreCase("67")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        FragmentManager fragmentManager = getFragmentManager();

                                    /* Creating fragment instance */
                                        Child_Info rFragment = new Child_Info();
                                        img.setVisibility(View.VISIBLE);
                                    /* Passing selected item information to fragment */
                                    /* Replace fragment */
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("29")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            AttandanceMainScreen rFragment = new AttandanceMainScreen();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "attendance");
                                            bundle.putString("name_en", "Retrieval List");
                                            bundle.putString("name_sw", "Närvarolista");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("28")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            Log.d("LMS", "status of edublog component is --> " + appStatus[position]);
                                            session.EduCountTeacher(eduBlog_count);
                                            refresh.setVisibility(View.VISIBLE);
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            MainActivity rFragment = new MainActivity();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "edublog");
                                            bundle.putString("name_en", "Edu Blog");
                                            bundle.putString("name_sw", "Läroblogg");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("23")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            session.NewsCountTeacher(news_count);
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            News_Post rFragment = new News_Post();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "news");
                                            bundle.putString("name_en", "News");
                                            bundle.putString("name_sw", "Nyheter");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("27")) {


                               /* FragmentManager fragmentManager = getFragmentManager();


                                ScheduleFragment rFragment = new ScheduleFragment();

                                FragmentTransaction ft = fragmentManager
                                        .beginTransaction();
                                ft.replace(R.id.content_frame, rFragment);
                                ft.commit();*/
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        Intent schedeluIntent = new Intent(getActivity(), AsynchronousActivity.class);
                                        schedeluIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                        getActivity().startActivity(schedeluIntent);
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("63")) {
                               /* holder.notf.setVisibility(View.GONE);
                                rowView.setBackgroundColor(Color.parseColor("#8863A9"));*/
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        Intent foodMenuIntent = new Intent(getActivity(), AddFoodMenuActivity.class);
                                        startActivity(foodMenuIntent);
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if(position==5)
                        {
                            if (component_id[position].equalsIgnoreCase("67")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        FragmentManager fragmentManager = getFragmentManager();

                                    /* Creating fragment instance */
                                        Child_Info rFragment = new Child_Info();
                                        img.setVisibility(View.VISIBLE);
                                    /* Passing selected item information to fragment */
                                    /* Replace fragment */
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("29")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            AttandanceMainScreen rFragment = new AttandanceMainScreen();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "attendance");
                                            bundle.putString("name_en", "Retrieval List");
                                            bundle.putString("name_sw", "Närvarolista");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("28")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            Log.d("LMS", "status of edublog component is --> " + appStatus[position]);
                                            session.EduCountTeacher(eduBlog_count);
                                            refresh.setVisibility(View.VISIBLE);
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            MainActivity rFragment = new MainActivity();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "edublog");
                                            bundle.putString("name_en", "Edu Blog");
                                            bundle.putString("name_sw", "Läroblogg");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("23")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            session.NewsCountTeacher(news_count);
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            News_Post rFragment = new News_Post();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "news");
                                            bundle.putString("name_en", "News");
                                            bundle.putString("name_sw", "Nyheter");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("27")) {


                               /* FragmentManager fragmentManager = getFragmentManager();


                                ScheduleFragment rFragment = new ScheduleFragment();

                                FragmentTransaction ft = fragmentManager
                                        .beginTransaction();
                                ft.replace(R.id.content_frame, rFragment);
                                ft.commit();*/
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        Intent schedeluIntent = new Intent(getActivity(), AsynchronousActivity.class);
                                        schedeluIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                        getActivity().startActivity(schedeluIntent);
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("63")) {
                               /* holder.notf.setVisibility(View.GONE);
                                rowView.setBackgroundColor(Color.parseColor("#8863A9"));*/
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        Intent foodMenuIntent = new Intent(getActivity(), AddFoodMenuActivity.class);
                                        startActivity(foodMenuIntent);
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if(position==6)
                        {
                            if (component_id[position].equalsIgnoreCase("67")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        FragmentManager fragmentManager = getFragmentManager();

                                    /* Creating fragment instance */
                                        Child_Info rFragment = new Child_Info();
                                        img.setVisibility(View.VISIBLE);
                                    /* Passing selected item information to fragment */
                                    /* Replace fragment */
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("29")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            AttandanceMainScreen rFragment = new AttandanceMainScreen();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "attendance");
                                            bundle.putString("name_en", "Retrieval List");
                                            bundle.putString("name_sw", "Närvarolista");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("28")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            Log.d("LMS", "status of edublog component is --> " + appStatus[position]);
                                            session.EduCountTeacher(eduBlog_count);
                                            refresh.setVisibility(View.VISIBLE);
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            MainActivity rFragment = new MainActivity();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "edublog");
                                            bundle.putString("name_en", "Edu Blog");
                                            bundle.putString("name_sw", "Läroblogg");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("23")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            session.NewsCountTeacher(news_count);
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            News_Post rFragment = new News_Post();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "news");
                                            bundle.putString("name_en", "News");
                                            bundle.putString("name_sw", "Nyheter");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("27")) {


                             /*   FragmentManager fragmentManager = getFragmentManager();


                                ScheduleFragment rFragment = new ScheduleFragment();

                                FragmentTransaction ft = fragmentManager
                                        .beginTransaction();
                                ft.replace(R.id.content_frame, rFragment);
                                ft.commit();*/
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        Intent schedeluIntent = new Intent(getActivity(), AsynchronousActivity.class);
                                        schedeluIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                        getActivity().startActivity(schedeluIntent);
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }

                            if (component_id[position].equalsIgnoreCase("63")) {
                               /* holder.notf.setVisibility(View.GONE);
                                rowView.setBackgroundColor(Color.parseColor("#8863A9"));*/
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        Intent foodMenuIntent = new Intent(getActivity(), AddFoodMenuActivity.class);
                                        startActivity(foodMenuIntent);
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if(position==7)
                        {
                            MYAccount.setVisibility(View.GONE);
                            if (component_id[position].equalsIgnoreCase("67")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        FragmentManager fragmentManager = getFragmentManager();

                                    /* Creating fragment instance */
                                        Child_Info rFragment = new Child_Info();
                                        img.setVisibility(View.VISIBLE);
                                    /* Passing selected item information to fragment */
                                    /* Replace fragment */
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("29")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            AttandanceMainScreen rFragment = new AttandanceMainScreen();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "attendance");
                                            bundle.putString("name_en", "Retrieval List");
                                            bundle.putString("name_sw", "Närvarolista");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("28")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            Log.d("LMS", "status of edublog component is --> " + appStatus[position]);
                                            session.EduCountTeacher(eduBlog_count);
                                            refresh.setVisibility(View.VISIBLE);
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            MainActivity rFragment = new MainActivity();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "edublog");
                                            bundle.putString("name_en", "Edu Blog");
                                            bundle.putString("name_sw", "Läroblogg");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("23")) {
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        MYAccount.setVisibility(View.GONE);
                                        if (appStatus[position].equalsIgnoreCase("0")) {
                                            session.NewsCountTeacher(news_count);
                                            img.setVisibility(View.VISIBLE);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            News_Post rFragment = new News_Post();
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {
                                            FragmentManager fragmentManager = getFragmentManager();
                                            WebFragment rFragment = new WebFragment();
                                            bundle.putString("Component_Key", "news");
                                            bundle.putString("name_en", "News");
                                            bundle.putString("name_sw", "Nyheter");
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (component_id[position].equalsIgnoreCase("27")) {


                             /*   FragmentManager fragmentManager = getFragmentManager();


                                ScheduleFragment rFragment = new ScheduleFragment();

                                FragmentTransaction ft = fragmentManager
                                        .beginTransaction();
                                ft.replace(R.id.content_frame, rFragment);
                                ft.commit();*/
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        Intent schedeluIntent = new Intent(getActivity(), AsynchronousActivity.class);
                                        schedeluIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                        getActivity().startActivity(schedeluIntent);
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }

                            if (component_id[position].equalsIgnoreCase("63")) {
                               /* holder.notf.setVisibility(View.GONE);
                                rowView.setBackgroundColor(Color.parseColor("#8863A9"));*/
                                try {
                                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                        Intent foodMenuIntent = new Intent(getActivity(), AddFoodMenuActivity.class);
                                        startActivity(foodMenuIntent);
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });

                return rowView;
            }

        }


        // ////////////////////////////////////////////////////
        class ImageLoadTaskcliptwo extends AsyncTask<Void, Void, Bitmap> {
            // ProgressDialog pDialog;
            private String url;
            private ImageView image;

            public ImageLoadTaskcliptwo(String url, ImageView imageView) {
                this.url = url;
                this.image = imageView;
            }

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();

            }

            @Override
            protected Bitmap doInBackground(Void... params) {
                try {
                    URL urlConnection = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) urlConnection
                            .openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    return myBitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                super.onPostExecute(result);
                // pDialog.dismiss();
                image.setScaleType(ImageView.ScaleType.FIT_XY);
                image.setImageBitmap(result);
            }

        }


        // ////////////////////////////////////////////////////
    }

    ///////////////////// set title for action bar /////////
    public void setActionBarTitle(String title) {
        txt2.setText(title);
    }


      public void setActionBarImage(String image) {
        Log.d("Child_img",""+image);
          new ImageLoadTasklist(image, img2).execute();
//          Picasso.with(getApplicationContext())
//                  .load(image)
//                  .placeholder(R.drawable.roundedimageview)
//                  // optional
//                  .error(R.drawable.roundedimageview)         // optional
//                  .into(img2);
    }



    //////////////
    public void setBackForChild() {
        if (lang.equalsIgnoreCase("sw")) {
            txt2.setText("Barnkonton");
        }
        if (lang.equalsIgnoreCase("en")) {
            txt2.setText("Child accounts");
        }

        img.setVisibility(View.VISIBLE);

        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    if(NetworkUtil.getInstance(Drawer.this).isInternet()) {
                        FragmentManager fragmentManager = getFragmentManager();
                        ParentsChild Setting_frg = new ParentsChild();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, Setting_frg);
                        ft.commit();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    ///////////////////////////////////////////////////
    public void setBackForChildEdu() {


        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if(NetworkUtil.getInstance(Drawer.this).isInternet()) {
                        FragmentManager fragmentManager = getFragmentManager();
                        ParentChildComponent Setting_frg = new ParentChildComponent();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, Setting_frg);
                        ft.commit();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /* public void setBackFromSchedule() {





         img.setOnClickListener(new OnClickListener() {

             @Override
             public void onClick(View v) {
                 // TODO Auto-generated method stub
                 FragmentManager fragmentManager = getFragmentManager();
                 ParentChildComponent Setting_frg = new ParentChildComponent();
                 FragmentTransaction ft = fragmentManager.beginTransaction();
                 ft.replace(R.id.content_frame, Setting_frg);
                 ft.commit();
             }
         });
     }
 */
    public void setBackForChildEduedu() {
        // TODO Auto-generated method stub
        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if(NetworkUtil.getInstance(Drawer.this).isInternet()) {

                        String cld_n;
                        if (lang.equalsIgnoreCase("sw")) {
                            HashMap<String, String> user = session.getUserDetails();
                            cld_n = user.get(UserSessionManager.TAG_cld_nm);
                            txt2.setText(cld_n);
                        }
                        if (lang.equalsIgnoreCase("en")) {

                            HashMap<String, String> user = session.getUserDetails();
                            cld_n = user.get(UserSessionManager.TAG_cld_nm);
                            txt2.setText(cld_n);
                        }

                        FragmentManager fragmentManager = getFragmentManager();
                        ParentChildComponent rFragment = new ParentChildComponent();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, rFragment);
                        ft.commit();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void setBackFrompublishtomain() {
        // TODO Auto-generated method stub

        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if(NetworkUtil.getInstance(Drawer.this).isInternet()) {
                        FragmentManager fragmentManager = getFragmentManager();
                        MainActivity rFragment = new MainActivity();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, rFragment);
                        ft.commit();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void setBackFromDraftToPublish() {
        // TODO Auto-generated method stub

        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if(NetworkUtil.getInstance(Drawer.this).isInternet()) {
                        FragmentManager fragmentManager = getFragmentManager();
                        Publish rFragment = new Publish();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, rFragment);
                        ft.commit();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void setBackFromFilterToDraft() {
        // TODO Auto-generated method stub

        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                FragmentManager fragmentManager = getFragmentManager();
                SaveDraft rFragment = new SaveDraft();
                Bundle bundle = new Bundle();
                bundle.putString("my_draft", "");
                bundle.putString("description_value", "");
                bundle.putString("from_date", "");
                bundle.putString("to_date", "");
                rFragment.setArguments(bundle);
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, rFragment);
                ft.commit();

            }
        });

    }

    public void Hideserch() {
        // TODO Auto-generated method stub
        serhc.setVisibility(View.GONE);

    }

    public void Backtomain() {
        // TODO Auto-generated method stub
        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if(NetworkUtil.getInstance(Drawer.this).isInternet()) {
                        Intent in = new Intent(getApplicationContext(), Drawer.class);
                        startActivity(in);
                        finish();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void setBackFrompublishtomainnews() {
        // TODO Auto-generated method stub
        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    if(NetworkUtil.getInstance(Drawer.this).isInternet()) {
                        FragmentManager fragmentManager = getFragmentManager();
                        News_Post rFragment = new News_Post();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, rFragment);
                        ft.commit();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void setBackFrompublishtomainAttendance() {
        // TODO Auto-generated method stub
        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if(NetworkUtil.getInstance(Drawer.this).isInternet()) {
                        FragmentManager fragmentManager = getFragmentManager();
                        AttandanceMainScreen rFragment = new AttandanceMainScreen();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, rFragment);
                        ft.commit();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void showParentMain(){
        img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(NetworkUtil.getInstance(Drawer.this).isInternet()) {
                        FragmentManager fragmentManager = getFragmentManager();
                        ParentChildComponent rFragment = new ParentChildComponent();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, rFragment);
                        ft.commit();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void showbackbutton() {
        // TODO Auto-generated method stub
        img.setVisibility(View.VISIBLE);

    }

    public void BackToParrentChidrn() {
        // TODO Auto-generated method stub
        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    if(NetworkUtil.getInstance(Drawer.this).isInternet()) {
                        FragmentManager fragmentManager = getFragmentManager();
                        ParentsChild rFragment = new ParentsChild();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, rFragment);
                        ft.commit();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void ShowRefresh() {
        // TODO Auto-generated method stub

        refresh.setVisibility(View.VISIBLE);

    }

    public void HideRefresh() {
        // TODO Auto-generated method stub
        refresh.setVisibility(View.GONE);
    }

    public void Showserch() {
        // TODO Auto-generated method stub
        serhc.setVisibility(View.VISIBLE);

        serhc.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                ((MainActivity) getFragmentManager().findFragmentById(R.id.content_frame)).showserchlayout();

            }
        });

    }

    public void Hidebackbutton() {
        // TODO Auto-generated method stub
        img.setVisibility(View.GONE);
    }

    public void BacktomainActivity() {
        // TODO Auto-generated method stub
        FragmentManager fragmentManager = getFragmentManager();
        MainActivity rFragment = new MainActivity();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, rFragment);
        ft.commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }



}