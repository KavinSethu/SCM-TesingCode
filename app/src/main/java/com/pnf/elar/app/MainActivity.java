package com.pnf.elar.app;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

/*import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.elar.util.NetworkUtil;
import com.elar.util.SmartClassUtil;
import com.google.gson.Gson;
import com.pnf.elar.app.Bo.CurriculamMainTag;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;

//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
//import android.app.Fragment;
//import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MainActivity extends android.app.Fragment implements
        AnimationListener {

    Animation animFadein;
    View v;
    ImageView curriculm_image, pen, immm;
    TextView txtTitle, lik, cur_val, random1, pbls, pst, Fltr, clkserch;
    EditText inputsrch, serchbar;
    ListView list;
    String srch = "";
    Timer timer;
    int count = 0;
    RelativeLayout filter, layout3;
    // Animation animSideup;
    LinearLayout main_layout, Serchlayout;
    // LinearLayout actionbar;
    ViewGroup actionBarLayout;
    String[] array;
    String[] st_ids;
    String cur_id;
    String fr_data, child_edu;
    String t_data;
    ImageView img, srchide, srchshow;
    String lang, auth_token, language;
    static String Base_url, user_typ, cld_n, user_id = "";
    UserSessionManager session;
    TextView MYAccount;
    String[] grps_ids, std_ids;
    String curriculam_id, from_data, to_data;
    Activity activity;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_main, container, false);

        session = new UserSessionManager(getActivity());

        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        // auth_token = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        user_typ = user.get(UserSessionManager.TAG_user_type);
        cld_n = user.get(UserSessionManager.TAG_cld_nm);
        // ///////// if student hide top bar
        main_layout = (LinearLayout) v.findViewById(R.id.main_layout);

        animFadein = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_up);

        // set animation listener
        // animFadein.setAnimationListener(getActivity());

        if (user_typ.equalsIgnoreCase("Student")) {
            main_layout.setVisibility(View.GONE);
        }
        // ////////
        String main_post = "main_post";
        session.create_main_Screen(main_post);
        layout3 = (RelativeLayout) v.findViewById(R.id.layout3);

        // /////////// action bar title
        if (lang.equalsIgnoreCase("sw")) {
            ((Drawer) getActivity()).setActionBarTitle("Läroblogg");
        } else {
            ((Drawer) getActivity()).setActionBarTitle("Edu blog");
        }
        ((Drawer) getActivity()).HideRefresh();

        // ///////////// if parent then work with child Authntication

        if (user_typ.equalsIgnoreCase("Parent")) {
            main_layout.setVisibility(View.GONE);
            ((Drawer) getActivity()).setBackForChildEduedu();
            user_id = user.get(UserSessionManager.TAG_user_id);
            auth_token = user
                    .get(UserSessionManager.TAG_Authntication_Children);
            Log.i("auth_token_child", auth_token);
        } else {
            auth_token = user.get(UserSessionManager.TAG_Authntication_token);
            Log.i("auth_token", auth_token);
            ((Drawer) getActivity()).Backtomain();


        }
        // ///////

        pbls = (TextView) v.findViewById(R.id.pbls);
        pst = (TextView) v.findViewById(R.id.pst);
        Fltr = (TextView) v.findViewById(R.id.Fltr);
        list = (ListView) v.findViewById(R.id.posts);
        filter = (RelativeLayout) v.findViewById(R.id.filter);
        LinearLayout publish = (LinearLayout) v.findViewById(R.id.publish);
        ImageView refresh = (ImageView) v.findViewById(R.id.refresh);
        ImageView serhc = (ImageView) v.findViewById(R.id.serhc);
        srchide = (ImageView) v.findViewById(R.id.srchide);
        srchshow = (ImageView) v.findViewById(R.id.srchshow);
        serchbar = (EditText) v.findViewById(R.id.serchbar);

        try {

            Bundle bundle = MainActivity.this.getArguments();
            if (!(bundle == null)) {
                grps_ids = bundle.getStringArray("grps_ids");
                std_ids = bundle.getStringArray("std_ids");
                curriculam_id = bundle.getString("curriculam_id");
                from_data = bundle.getString("from_data");
                to_data = bundle.getString("to_data");

                new filterclass().execute();
            } else {
                new datacreate().execute();

            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        // ///////// search bar //////////////////

        serchbar.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

                srchide.setVisibility(View.GONE);
                // serchbar.setHint("");

            }
        });

        serchbar.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    srch = serchbar.getText().toString();
                    if (srch.equalsIgnoreCase("")) {

                    } else {
                        InputMethodManager imm = (InputMethodManager) getActivity()
                                .getSystemService(Service.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(serchbar.getWindowToken(),
                                0);
                        new datacreate().execute();
                        // serchbar.setText("");
                        // serchbar.setHint("Search");
                        // srchide.setVisibility(View.VISIBLE);
                    }
                    return true;
                }
                return false;
            }

        });

        //////////////////end search bar//////////////
        //////////// language variable/
        if (lang.equalsIgnoreCase("sw")) {
            pbls.setText("Publicera");
            pst.setText("Inlägg");
            Fltr.setText("Filtrera");
        } else {

        }
        // /////
        publish.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                        FragmentManager fragmentManager = getFragmentManager();

                    /* Creating fragment instance */
                        Publish rFragment = new Publish();

                    /* Passing selected item information to fragment */
                    /* Replace fragment */
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, rFragment);
                        ft.commit();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        filter.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // if (isOnline()) {

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                        FragmentManager frfmnr_mngr = getFragmentManager();

                        Post_filtter post_filtter_fr = new Post_filtter();

                        FragmentTransaction ft = frfmnr_mngr.beginTransaction();

                        ft.replace(R.id.content_frame, post_filtter_fr);

                        ft.commit();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

        return v;
    }

    // ////------first Api call----------//////
    class datacreate extends AsyncTask<String, String, String> {
        ArrayList<String[]> list6 = new ArrayList<String[]>();
        ArrayList<String[]> list7 = new ArrayList<String[]>();
        ArrayList<String[]> list5 = new ArrayList<String[]>();
        ArrayList<String[]> list8 = new ArrayList<String[]>();
        ArrayList<String[]> list9 = new ArrayList<String[]>();
        ArrayList<String[]> list10 = new ArrayList<String[]>();
        ArrayList<String[]> list222 = new ArrayList<String[]>();
        ArrayList<String[]> list1 = new ArrayList<String[]>();
        ArrayList<String[]> curriculum_post_title = new ArrayList<String[]>();
        ArrayList<String[]> listgrps = new ArrayList<String[]>();


        ArrayList<List<CurriculamMainTag>> curiculamMainPost = new ArrayList<>();
        HashMap<String, ArrayList<String[]>> map = new HashMap<String, ArrayList<String[]>>();

        /*JSONParser jsonParserpost = new JSONParser();*/
        List<CurriculamMainTag> crTgaList = new ArrayList<>();

        String posterror = "no";
        String[] catgy;
        String[] descr;
        String[] createddate;
        String[] teacher_name;
        String[] post_user_id;
        String[] post_id;

        String[] curriclm_tg_title;

        String[] liked, already_liked;
        String[] img_name_id;
        String[] cntnt_type;

        String status;

        private MyCustomProgressDialog dialog;
        JSONObject jsonpostmain = null;
        private String url_create_product = Base_url
                + "lms_api/picture_diary/posts";
        private static final String TAG_staus = "status";
        private static final String TAG_posts = "posts";
        private static final String TAG_user_id = "user_id";
        private static final String TAG_teacher_name = "teacher_name";
        private static final String TAG_category = "category";
        private static final String TAG_description = "description";
        private static final String TAG_created = "created";
        private static final String TAG_post_id = "id";
        private static final String TAG_imagename_id = "id";
        private static final String TAG_images = "images";
        private static final String TAG_videos = "videos";
        private static final String TAG_video_imagename = "imagename";
        private static final String TAG_post_student = "students";
        private static final String TAG_post_groups = "groups";
        private static final String TAG_ss_name = "name";
        private static final String TAG_random_files = "random_files";
        private static final String TAG_random_file_name = "random_file_name";
        private static final String TAG_random_already_liked = "picture_diary_like_count";
        private static final String TAG_already_liked = "already_liked";
        private static final String TAG_videoname_mp4 = "videoname_mp4";
        private static final String TAG_associated_curriculum_tags = "associated_curriculum_tags";
        private static final String TAG_curriculum_post_title = "title";
        private static final String TAG_curriculum_post_children = "children";
        private static final String TAG_curriculum_post_subchildren = "subchildren";

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new MyCustomProgressDialog(getActivity());
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            try {
                Log.i("user_id", user_id);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }


            String postResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode("H67jdS7wwfh", "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.DescriptionValue + "=" + URLEncoder.encode(srch, "UTF-8") +
                        "&" + Const.Params.ParentId + "=" + URLEncoder.encode(user_id, "UTF-8") +
                        "&" + Const.Params.DeviceTokenApp + "=" + URLEncoder.encode("", "UTF-8");


                postResponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }

          /*  List<NameValuePair> paramspost = new ArrayList<NameValuePair>();
            paramspost
                    .add(new BasicNameValuePair("securityKey", "H67jdS7wwfh"));
            paramspost.add(new BasicNameValuePair("authentication_token",
                    auth_token));
            paramspost.add(new BasicNameValuePair("language", lang));
            paramspost.add(new BasicNameValuePair("description_value", srch));
            paramspost.add(new BasicNameValuePair("parent_id", user_id));
            paramspost.add(new BasicNameValuePair("device_token_app",""));
            try {
                jsonpostmain = jsonParserpost.makeHttpRequest(
                        url_create_product, "POST", paramspost);

                Log.i("paramspost", paramspost.toString());
            } catch (Exception e) {
                // TODO: handle exception

            }*/


            return postResponse;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {

                if (results != null && !results.isEmpty()) {

                    jsonpostmain = new JSONObject(results);
                }

                String status = jsonpostmain.getString("status");
                if (status.equalsIgnoreCase("true"))
                {


                    if (jsonpostmain != null) {
                        // Log.i("99999......", jsonpostmain.toString());
                        try {
                            status = jsonpostmain.getString(TAG_staus);

                            Log.e("=-=-=-=-=-=-", status + "" + jsonpostmain.toString());
                            try {
                                JSONArray posts = jsonpostmain.optJSONArray(TAG_posts);

                                catgy = new String[posts.length()];
                                descr = new String[posts.length()];
                                createddate = new String[posts.length()];
                                teacher_name = new String[posts.length()];
                                post_user_id = new String[posts.length()];
                                post_id = new String[posts.length()];
                                liked = new String[posts.length()];
                                already_liked = new String[posts.length()];

                                for (int j = 0; j < posts.length(); j++) {


                                    JSONObject c = posts.getJSONObject(j);
                                    //
                                    catgy[j] = c.getString(TAG_category);
                                    descr[j] = c.getString(TAG_description);

                                    createddate[j] = c.getString(TAG_created);

                                    //CHANGE BY KAVIN -HIDE TEACHER NAME
//                                    teacher_name[j] = c.getString(TAG_teacher_name)
//                                            + " # " + c.getString(TAG_created);

                                    teacher_name[j] = c.getString(TAG_created);

                                    post_user_id[j] = c.getString(TAG_user_id);
                                    post_id[j] = c.getString(TAG_post_id);
                                    liked[j] = c.getString(TAG_random_already_liked);
                                    already_liked[j] = c.getString(TAG_already_liked);

                                    // ////////////////////////////////////////////////////////////////////

                                    JSONArray curriclm_tg_post = c
                                            .optJSONArray(TAG_associated_curriculum_tags);
                                    crTgaList = new ArrayList<>();

                                    crTgaList = Arrays.asList(new Gson().fromJson(c.optJSONArray(TAG_associated_curriculum_tags).toString(), CurriculamMainTag[].class));

                                    System.out.println("crTgaList " + " j=  " + j + crTgaList.size());

                                    String[] curriclm_tg_title = new String[curriclm_tg_post
                                            .length()];

                                    for (int e = 0; e < curriclm_tg_post.length(); e++) {
                                        JSONObject f = curriclm_tg_post
                                                .getJSONObject(e);

                                        curriclm_tg_title[e] = f
                                                .getString(TAG_curriculum_post_title);

                                        JSONArray curriclm_tg_chldrn_post = f
                                                .optJSONArray(TAG_curriculum_post_children);
                                        String[] childrn_title = new String[curriclm_tg_chldrn_post
                                                .length()];
                                        for (int g = 0; g < curriclm_tg_chldrn_post
                                                .length(); g++) {
                                            JSONObject h = curriclm_tg_chldrn_post
                                                    .getJSONObject(g);

                                            childrn_title[g] = h
                                                    .getString(TAG_curriculum_post_title);

                                            JSONArray sub_chil = h
                                                    .getJSONArray(TAG_curriculum_post_subchildren);

                                            String[] Sub_child_title = new String[sub_chil
                                                    .length()];

                                            for (int k = 0; k < sub_chil.length(); k++) {

                                                JSONObject n = sub_chil
                                                        .getJSONObject(k);

                                                Sub_child_title[k] = n
                                                        .getString(TAG_curriculum_post_title);

                                            }

                                            list222.add(Sub_child_title);

                                        }
                                        list1.add(childrn_title);
                                        map.put(Integer.toString(j), list222);

                                    }
                                    curriculum_post_title.add(curriclm_tg_title);

                                    curiculamMainPost.add(crTgaList);

                                    // /////////////////////////////////////////////////////////////

                                    JSONArray images = c.optJSONArray(TAG_images);
                                    String[] TAG_ima = new String[images.length()];
                                    for (int l = 0; l < images.length(); l++) {
                                        JSONObject m = images.getJSONObject(l);

                                        TAG_ima[l] = Base_url
                                                + "picture_diary/viewPictureDiaryImages/"
                                                + m.optString(TAG_imagename_id)
                                                + "?authentication_token=" + auth_token;

                                    }

                                    list6.add(TAG_ima);

                                    JSONArray video = c.optJSONArray(TAG_videos);
                                    String[] videoname_mp4 = new String[video.length()];
                                    String[] TAG_vid = new String[video.length()];
                                    for (int k = 0; k < video.length(); k++) {
                                        JSONObject h = video.getJSONObject(k);

                                        TAG_vid[k] = Base_url
                                                + h.getString(TAG_video_imagename);
                                        videoname_mp4[k] = Base_url
                                                + h.getString(TAG_videoname_mp4);

                                    }
                                    list7.add(TAG_vid);
                                    list5.add(videoname_mp4);

                                    JSONArray std_name = c
                                            .optJSONArray(TAG_post_student);
                                    String[] TAG_s_name = new String[std_name.length()];
                                    for (int l = 0; l < std_name.length(); l++) {
                                        JSONObject m = std_name.getJSONObject(l);

                                        TAG_s_name[l] = m.getString(TAG_ss_name);

                                    }
                                    list8.add(TAG_s_name);

                                    if (c.has(TAG_post_groups))

                                    {
                                        JSONArray grp_name = c
                                                .optJSONArray(TAG_post_groups);
                                        String[] TAG_grp_name = new String[grp_name.length()];
                                        for (int l = 0; l < grp_name.length(); l++) {
                                            JSONObject m = grp_name.getJSONObject(l);

                                            TAG_grp_name[l] = m.getString(TAG_ss_name);

                                        }
                                        listgrps.add(TAG_grp_name);
                                    }

                                    JSONArray randm_files = c
                                            .optJSONArray(TAG_random_files);

                                    String[] rndm_file_name = new String[randm_files
                                            .length()];
                                    String[] rndm_file_id = new String[randm_files
                                            .length()];
                                    for (int l = 0; l < randm_files.length(); l++) {
                                        JSONObject me = randm_files.getJSONObject(l);

                                        rndm_file_name[l] = me
                                                .getString(TAG_random_file_name);
                                        rndm_file_id[l] = me.getString(TAG_post_id);

                                    }
                                    list9.add(rndm_file_name);
                                    list10.add(rndm_file_id);

                                }

                                if (status.equalsIgnoreCase("true")) {

                                } else {


                                }
                            } catch (Exception e) {
                                // TODO: handle exception
                                Log.i("internal error", "error..");
                                posterror = "yes";
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else {

                    }


            }else {

                    try {


                        String msg = jsonpostmain.getString("message");
                        System.out.print(msg);

                        if (lang.equalsIgnoreCase("sw")) {
                            System.out.print("Sw_l");
                            if (msg.equalsIgnoreCase("Autentisering misslyckades")) {
                                Button btnLogout;
                                TextView tvMessage;
                                final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", getActivity());
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
                                final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", getActivity());
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
                }


                if (posterror.equalsIgnoreCase("yes")) {
                } else {

                    if (status.equalsIgnoreCase("true")) {

                        try {
                            String[] t = list6.get(0);

                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }

                        CustomAdpterPost adapter = new CustomAdpterPost(
                                getActivity(), catgy, teacher_name, post_user_id, descr, post_id,
                                list6, list7, list8, list9, liked, list5,
                                curriculum_post_title, list1, map, already_liked,
                                list10, curiculamMainPost, listgrps);

                        list.setAdapter(adapter);

                        // //////////////////// Scroll up/down listener /////////

/*                        if ((user_typ.equalsIgnoreCase("Student"))
                                || (user_typ.equalsIgnoreCase("parent"))) {
                            list.setOnScrollListener(new OnScrollListener() {

                                private int mLastFirstVisibleItem;

                                @Override
                                public void onScrollStateChanged(AbsListView view,
                                                                 int scrollState) {
                                    // TODO Auto-generated method stub
                                }

                                @Override
                                public void onScroll(AbsListView view,
                                                     int firstVisibleItem, int visibleItemCount,
                                                     int totalItemCount) {
                                    // TODO Auto-generated method stub

                                    if (mLastFirstVisibleItem < firstVisibleItem) {

//								view.animate()
//							        .translationY(view.getHeight())
//							        .alpha(0.0f)
//							        .setDuration(300)
//							        .setListener(new AnimatorListenerAdapter() {
//							            @Override
//							            public void onAnimationEnd(Animator animation) {
//							                super.onAnimationEnd(animation);
                                        layout3.setVisibility(View.GONE);
//							            }
//							        });


                                    }
                                    if (mLastFirstVisibleItem > firstVisibleItem) {

                                        layout3.setVisibility(View.VISIBLE);
                                    }
                                    mLastFirstVisibleItem = firstVisibleItem;

                                }
                            });
                        } else {
                            list.setOnScrollListener(new OnScrollListener() {

                                private int mLastFirstVisibleItem;

                                @Override
                                public void onScrollStateChanged(AbsListView view,
                                                                 int scrollState) {
                                    // TODO Auto-generated method stub
                                }

                                @Override
                                public void onScroll(AbsListView view,
                                                     int firstVisibleItem, int visibleItemCount,
                                                     int totalItemCount) {
                                    // TODO Auto-generated method stub

                                    if (mLastFirstVisibleItem < firstVisibleItem) {
//									main_layout.animate()
//							        .translationY(main_layout.getBottom())
//							        .alpha(0.0f)
//							        .setDuration(500)
//							        .setListener(new AnimatorListenerAdapter() {
//							            @Override
//							            public void onAnimationEnd(Animator animation) {
//							                super.onAnimationEnd(animation);
                                        main_layout.setVisibility(View.GONE);
                                        layout3.setVisibility(View.GONE);
//											}
//							        });
                                    }
                                    if (mLastFirstVisibleItem > firstVisibleItem) {
                                        main_layout.setVisibility(View.VISIBLE);
                                        layout3.setVisibility(View.VISIBLE);
                                    }
                                    mLastFirstVisibleItem = firstVisibleItem;

                                }
                            });
                        }*/

                    } else {

                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    class filterclass extends AsyncTask<String, String, String> {
        ArrayList<String[]> list6 = new ArrayList<String[]>();
        ArrayList<String[]> list7 = new ArrayList<String[]>();
        ArrayList<String[]> list8 = new ArrayList<String[]>();
        ArrayList<String[]> list9 = new ArrayList<String[]>();
        ArrayList<String[]> list5 = new ArrayList<String[]>();
        ArrayList<String[]> list10 = new ArrayList<String[]>();
        ArrayList<String[]> list222 = new ArrayList<String[]>();
        ArrayList<String[]> list1 = new ArrayList<String[]>();
        HashMap<String, ArrayList<String[]>> map = new HashMap<String, ArrayList<String[]>>();
        ArrayList<String[]> curriculum_post_title = new ArrayList<String[]>();
        ArrayList<String[]> listgrp = new ArrayList<String[]>();

        /*JSONParser jsonParser = new JSONParser();*/


        List<CurriculamMainTag> crTgaList = new ArrayList<>();
        ArrayList<List<CurriculamMainTag>> curiculamMainPost = new ArrayList<>();

        String[] catgy;
        String[] descr;
        String[] createddate;
        String[] post_user_id;
        String[] teacher_name;
        String[] post_id;

        String[] curriclm_tg_title;

        String[] liked, already_liked;
        String[] img_name_id;
        String[] cntnt_type;
        String status;
        JSONArray posts;
        Boolean get_filtered_posts = false;
        private MyCustomProgressDialog dialog;
        private String url_create_productin = Base_url
                + "lms_api/picture_diary/get_filtered_posts";

        private static final String TAG_staus = "status";
        private static final String TAG_posts = "posts";
        private static final String TAG_teacher_name = "teacher_name";
        private static final String TAG_user_id = "user_id";
        private static final String TAG_category = "category";
        private static final String TAG_description = "description";
        private static final String TAG_created = "created";
        private static final String TAG_post_id = "id";
        private static final String TAG_imagename_id = "id";
        private static final String TAG_images = "images";
        private static final String TAG_videos = "videos";
        private static final String TAG_video_imagename = "imagename";
        private static final String TAG_post_student = "students";
        private static final String TAG_post_groups = "groups";
        private static final String TAG_ss_name = "name";
        private static final String TAG_random_files = "random_files";
        private static final String TAG_random_file_name = "random_file_name";
        private static final String TAG_random_already_liked = "picture_diary_like_count";
        private static final String TAG_already_liked = "already_liked";
        private static final String TAG_videoname_mp4 = "videoname_mp4";
        private static final String TAG_associated_curriculum_tags = "associated_curriculum_tags";
        private static final String TAG_curriculum_post_title = "title";
        private static final String TAG_curriculum_post_children = "children";
        private static final String TAG_curriculum_post_subchildren = "subchildren";

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            dialog = new MyCustomProgressDialog(getActivity());
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            String filterPostResponse = "";

            JSONObject.quote(cur_id);

            JSONArray mJSONArray = new JSONArray(Arrays.asList(grps_ids));
            JSONArray ss_id = new JSONArray(Arrays.asList(std_ids));

            String fill = null;
            JSONObject jsontwo = null;

            if (user_typ.equalsIgnoreCase("parent")) {
                fill = "{" + "\"student_ids\"" + ":" + "[]" + ","
                        + "\"group_ids\"" + ":"
                        + mJSONArray
                        + ","
                        + "\"curriculum_tag_ids\""
                        + ":"
                        + JSONObject.quote(curriculam_id)
                        + ","
                        + "\"from_date\""
                        + ":"
                        + JSONObject.quote(from_data)
                        + ","
                        + "\"to_date\""
                        + ":"
                        + JSONObject.quote(to_data)
                        + ","
                        + "\"securityKey\""
                        + ":"
                        + "\"H67jdS7wwfh\""
                        + ","
                        + "\"language\""
                        + ":"
                        + JSONObject.quote(lang)
                        + ","
                        + "\"authentication_token\""
                        + ":"
                        + JSONObject.quote(auth_token) + "}";
            } else {
                fill = "{" + "\"student_ids\"" + ":" + ss_id + ","
                        + "\"group_ids\"" + ":" + mJSONArray + ","
                        + "\"curriculum_tag_ids\"" + ":"
                        + JSONObject.quote(curriculam_id) + ","
                        + "\"from_date\"" + ":" + JSONObject.quote(from_data)
                        + "," + "\"to_date\"" + ":" + JSONObject.quote(to_data)
                        + "," + "\"securityKey\"" + ":" + "\"H67jdS7wwfh\""
                        + "," + "\"language\"" + ":" + JSONObject.quote(lang)
                        + "," + "\"authentication_token\"" + ":"
                        + JSONObject.quote(auth_token) + "}";

            }

            try {


                SmartClassUtil.PrintMessage(" fill "+fill);
                try {
                    String urlParams = "&" + Const.Params.JsonData + "=" + URLEncoder.encode(fill, "UTF-8");


                    filterPostResponse = FormDataWebservice.excutePost(url_create_productin, urlParams, Const.MethodType.POST);
                } catch (Exception e) {
                    e.printStackTrace();
                }


               /* List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("jsonData", fill));

                jsonresponse = jsonParser.makeHttpRequest(url_create_productin,
                        "POST", params);

                Log.i("88888", jsonresponse.toString());*/

            } catch (Exception e) {
                // TODO: handle exception
                get_filtered_posts = true;
                Log.i("generate", "generate");
                e.printStackTrace();
            }


            return filterPostResponse;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {
                JSONObject jsonresponse = new JSONObject();

                if (results != null && !results.isEmpty()) {


                    jsonresponse = new JSONObject(results);

                    if (jsonresponse.has(Const.Params.Status)) {

                        status = jsonresponse.getString(TAG_staus);

                    }

                } else {
                    get_filtered_posts = true;

                }

                if (!get_filtered_posts.equals(true)) {
                    try {
                        status = jsonresponse.getString(TAG_staus);

                        Log.e("=-=-=-=-=-=-", status);

                        posts = jsonresponse.optJSONArray(TAG_posts);

                        if (posts.length() == 0 || posts.equals(null)) {
                            Log.i("postsempty", "postsempty");
                            if(lang.equalsIgnoreCase("en")) {
                                Toast.makeText(getActivity(), "No Results With Selected Filter", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getActivity(), "Inget Sökresultat Som Matchar Valt Filter", Toast.LENGTH_SHORT).show();
                            }
                            new datacreate().execute();
                        } else {

                            catgy = new String[posts.length()];
                            descr = new String[posts.length()];
                            createddate = new String[posts.length()];
                            teacher_name = new String[posts.length()];
                            post_user_id = new String[posts.length()];
                            post_id = new String[posts.length()];
                            liked = new String[posts.length()];
                            already_liked = new String[posts.length()];

                            for (int j = 0; j < posts.length(); j++) {
                                JSONObject c = posts.getJSONObject(j);
                                //
                                catgy[j] = c.getString(TAG_category);
                                descr[j] = c.getString(TAG_description);
                                createddate[j] = c.getString(TAG_created);

                                teacher_name[j] = c.getString(TAG_teacher_name);
                                post_user_id[j] = c.getString(TAG_user_id);
                                post_id[j] = c.getString(TAG_post_id);
                                liked[j] = c.getString(TAG_random_already_liked);
                                already_liked[j] = c.getString(TAG_already_liked);

                                JSONArray curriclm_tg_post = c
                                        .optJSONArray(TAG_associated_curriculum_tags);
                                crTgaList = new ArrayList<>();
                                crTgaList = Arrays.asList(new Gson().fromJson(c.optJSONArray(TAG_associated_curriculum_tags).toString(), CurriculamMainTag[].class));

                                curriclm_tg_title = new String[curriclm_tg_post
                                        .length()];

                                for (int e = 0; j < curriclm_tg_post.length(); j++) {
                                    JSONObject f = curriclm_tg_post
                                            .getJSONObject(j);

                                    curriclm_tg_title[j] = f
                                            .getString(TAG_curriculum_post_title);

                                    JSONArray curriclm_tg_chldrn_post = c
                                            .optJSONArray(TAG_curriculum_post_children);
                                    String[] childrn_title = new String[curriclm_tg_chldrn_post
                                            .length()];
                                    for (int g = 0; g < curriclm_tg_chldrn_post
                                            .length(); g++) {
                                        JSONObject h = curriclm_tg_chldrn_post
                                                .getJSONObject(g);

                                        childrn_title[g] = h
                                                .getString(TAG_curriculum_post_title);

                                        JSONArray sub_chil = h
                                                .getJSONArray(TAG_curriculum_post_subchildren);
                                        // Log.i("lenght.......",
                                        // Integer.toString(sub_chil.length()));
                                        String[] Sub_child_title = new String[sub_chil
                                                .length()];

                                        for (int k = 0; k < sub_chil.length(); k++) {

                                            JSONObject n = sub_chil
                                                    .getJSONObject(k);

                                            Sub_child_title[k] = n
                                                    .getString(TAG_curriculum_post_title);

                                        }
                                        // Log.i("sub child...",
                                        // Arrays.deepToString(Sub_child_title));

                                        list222.add(Sub_child_title);

                                    }
                                    list1.add(childrn_title);
                                    map.put(Integer.toString(j), list222);

                                }
                                curriculum_post_title.add(curriclm_tg_title);
                                curiculamMainPost.add(crTgaList);

                                // ///////////////////////////////////////////////////////////////////////////////////

                                JSONArray images = c.optJSONArray(TAG_images);
                                String[] TAG_ima = new String[images.length()];
                                for (int l = 0; l < images.length(); l++) {
                                    JSONObject m = images.getJSONObject(l);

                                    TAG_ima[l] = Base_url
                                            + "picture_diary/viewPictureDiaryImages/"
                                            + m.optString(TAG_imagename_id)
                                            + "?authentication_token=" + auth_token;

                                }

                                list6.add(TAG_ima);

                                JSONArray video = c.optJSONArray(TAG_videos);
                                // String[] vdo_image_name=new
                                // String[video.length()];
                                String[] videoname_mp4 = new String[video.length()];
                                String[] TAG_vid = new String[video.length()];
                                for (int k = 0; k < video.length(); k++) {
                                    JSONObject h = video.getJSONObject(k);

                                    TAG_vid[k] = Base_url
                                            + h.getString(TAG_video_imagename);
                                    videoname_mp4[k] = Base_url
                                            + h.getString(TAG_videoname_mp4);
                                }
                                list7.add(TAG_vid);
                                list5.add(videoname_mp4);

                                JSONArray std_name = c
                                        .optJSONArray(TAG_post_student);
                                String[] TAG_s_name = new String[std_name.length()];
                                for (int l = 0; l < std_name.length(); l++) {
                                    JSONObject m = std_name.getJSONObject(l);

                                    TAG_s_name[l] = m.getString(TAG_ss_name);

                                }
                                list8.add(TAG_s_name);

                                if (c.has(TAG_post_groups))

                                {
                                    JSONArray grp_name = c
                                            .optJSONArray(TAG_post_groups);
                                    String[] TAG_grp_name = new String[grp_name.length()];
                                    for (int l = 0; l < grp_name.length(); l++) {
                                        JSONObject m = grp_name.getJSONObject(l);

                                        TAG_grp_name[l] = m.getString(TAG_ss_name);

                                    }
                                    listgrp.add(TAG_grp_name);
                                }

                            }

                            if (status.equalsIgnoreCase("true")) {

                            } else {
                                try {


                                    String msg = jsonresponse.getString("message");
                                    System.out.print(msg);

                                    if (lang.equalsIgnoreCase("sw")) {
                                        System.out.print("Sw_l");
                                        if (msg.equalsIgnoreCase("Autentisering misslyckades")) {
                                            Button btnLogout;
                                            TextView tvMessage;
                                            final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", getActivity());
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
                                            final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", getActivity());
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
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i("error", "error");
                }
                if (!get_filtered_posts.equals(true)) {
                    if (status.equalsIgnoreCase("true")) {

                        if (posts.equals("") || posts.equals(null)) {
                            Toast.makeText(getActivity(),
                                    "there is no data in post...",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            CustomAdpterPost adapter = new CustomAdpterPost(
                                    getActivity(), catgy, teacher_name, post_user_id, descr,
                                    post_id, list6, list7, list8, list9, liked,
                                    list5, curriculum_post_title, list1, map,
                                    already_liked, list10, curiculamMainPost, listgrp);
                            list.setAdapter(adapter);
                            // list.smoothScrollToPosition(7);
                /*            if ((user_typ.equalsIgnoreCase("Student"))
                                    || (user_typ.equalsIgnoreCase("parent"))) {
                                list.setOnScrollListener(new OnScrollListener() {

                                    private int mLastFirstVisibleItem;

                                    @Override
                                    public void onScrollStateChanged(
                                            AbsListView view, int scrollState) {
                                        // TODO Auto-generated method stub
                                    }

                                    @Override
                                    public void onScroll(AbsListView view,
                                                         int firstVisibleItem,
                                                         int visibleItemCount, int totalItemCount) {
                                        // TODO Auto-generated method stub

                                        if (mLastFirstVisibleItem < firstVisibleItem) {

                                            layout3.setVisibility(View.GONE);

                                        }
                                        if (mLastFirstVisibleItem > firstVisibleItem) {

                                            layout3.setVisibility(View.VISIBLE);
                                        }
                                        mLastFirstVisibleItem = firstVisibleItem;

                                    }
                                });
                            } else {
                                list.setOnScrollListener(new OnScrollListener() {

                                    private int mLastFirstVisibleItem;

                                    @Override
                                    public void onScrollStateChanged(
                                            AbsListView view, int scrollState) {
                                        // TODO Auto-generated method stub
                                    }

                                    @Override
                                    public void onScroll(AbsListView view,
                                                         int firstVisibleItem,
                                                         int visibleItemCount, int totalItemCount) {
                                        // TODO Auto-generated method stub

                                        if (mLastFirstVisibleItem < firstVisibleItem) {
                                            main_layout.setVisibility(View.GONE);
                                            layout3.setVisibility(View.GONE);
                                        }
                                        if (mLastFirstVisibleItem > firstVisibleItem) {
                                            main_layout.setVisibility(View.VISIBLE);
                                            layout3.setVisibility(View.VISIBLE);
                                        }
                                        mLastFirstVisibleItem = firstVisibleItem;

                                    }
                                });
                            }*/
                            // /////////////////////////////
                        }
                    }
                } else {
                    Log.i("error", "error");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // //////////////////
    // /////////////////
    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }

    public void showserchlayout() {
        // TODO Auto-generated method stub

        Serchlayout.setVisibility(View.VISIBLE);
        // Toast.makeText(getActivity(), "jtkewrfjklerjkl",
        // Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);



        if (context instanceof Activity){
            activity=(Activity) context;
        }

    }
}
