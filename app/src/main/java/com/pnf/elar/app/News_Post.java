package com.pnf.elar.app;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;

/*import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONArray;
import org.json.JSONObject;

import com.elar.util.NetworkUtil;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class News_Post extends Fragment implements AnimationListener {
    public News_Post() {
        // empty constructor
    }
    ListView list;
    String srch;
    Timer timer;
    int count = 0;
    RelativeLayout filter;
    ArrayAdapter<String> adapter;
    UserSessionManager session;
    Locale myLocale;
    String lang, auth_token, Base_url, user_typ, user_id = "";
    // Animation animSideup;
    LinearLayout main_layout;
    LinearLayout actionbar;
    ViewGroup actionBarLayout;
    String[] array;
    String[] st_ids;
    String[] grp_id, grp_name, type, newArray;
    String[] curriculum_title;
    String cur_id, selected_title, selected_id;
    String fr_data;
    String t_data;
    String lng;
    String selectnews, selectid="", select_type="";
    TextView selecteditem;
    ImageView img;
    TextView MYAccount, pblsh, News;
    View v;
    Activity activity;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.news__post, container, false);

        activity = getActivity();

        session = new UserSessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        auth_token = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        user_typ = user.get(UserSessionManager.TAG_user_type);

        //////////disable top bar when student login //////
        main_layout = (LinearLayout) v.findViewById(R.id.main_layout);
        if (user_typ.equalsIgnoreCase("Student")) {
            main_layout.setVisibility(View.GONE);
        }
        //////--------------------///////////
        if (user_typ.equalsIgnoreCase("Parent")) {
            main_layout.setVisibility(View.GONE);
            user_id = user.get(UserSessionManager.TAG_user_id);
            auth_token = user
                    .get(UserSessionManager.TAG_Authntication_Children);
            Log.i("auth_token_child", auth_token);
            ((Drawer) activity).setBackForChildEduedu();
            ((Drawer) activity).Hideserch();
            ((Drawer) activity).HideRefresh();
        } else {
            auth_token = user.get(UserSessionManager.TAG_Authntication_token);
            Log.i("auth_token", auth_token);
            ((Drawer) getActivity()).Backtomain();

        }



////////// navigation drawer //////////////		
        if (lang.equalsIgnoreCase("sw")) {
            ((Drawer) activity).setActionBarTitle("Nyheter");
        } else {
            ((Drawer) activity).setActionBarTitle("News");
        }

///////////////

        list = (ListView) v.findViewById(R.id.posts);
        filter = (RelativeLayout) v.findViewById(R.id.filter);
        LinearLayout publish = (LinearLayout) v.findViewById(R.id.publish);
        ImageView refresh = (ImageView) v.findViewById(R.id.refresh);
        ImageView serhc = (ImageView) v.findViewById(R.id.serch);
        selecteditem = (TextView) v.findViewById(R.id.selecteditem);
        pblsh = (TextView) v.findViewById(R.id.pblsh);
        News = (TextView) v.findViewById(R.id.News);


        String main_news = "main_news";
        session.create_main_Screen_news(main_news);

        if (lang.equalsIgnoreCase("sw")) {
            pblsh.setText("Publicera");
            News.setText("Inlägg");
            selecteditem.setText("Senast");
        } else {

        }


        publish.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                        FragmentManager frfmnr_mngr = getFragmentManager();

                        News_main post_filtter_fr = new News_main();

                        FragmentTransaction ft = frfmnr_mngr.beginTransaction();

                        ft.replace(R.id.content_frame, post_filtter_fr);

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

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                        String title;

                        if (lang.equalsIgnoreCase("sw")) {
                            title = "Filtrera";
                        } else {
                            title = "Filter";
                        }


                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setTitle(title);
                        builder.setItems(grp_name, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                // Do something with the selection

                                select_type = type[item];
                                selectid = grp_id[item];
                                selecteditem.setText(grp_name[item]);
                                // Toast.makeText(getApplicationContext(),selectid+"---"+select_type, Toast.LENGTH_LONG).show();
                                new datacreate().execute();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

        try {
            new getgroups().execute();



        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return v;

    }


    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
//		Intent refresh = new Intent(getActivity(), News_Post.class);
//		startActivity(refresh);

    }

    private boolean isOnline() {
        // TODO Auto-generated method stub

        ConnectivityManager conMgr = (ConnectivityManager) getActivity()
                .getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            // Toast.makeText(getApplicationContext(),
            // "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    protected void showInputDialog() {
        // TODO Auto-generated method stub

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.srch_input, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView
                .findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // resultText.setText("Hello, " + editText.getText());
                        srch = editText.getText().toString();
                        // if(srch.equalsIgnoreCase(""))
                        // {
                        // editText.setHint("field blank....");
                        // editText.setFocusable(true);
                        // }

                        new datacreate().execute();

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    private class datacreate extends AsyncTask<String, String, String> {
        ArrayList<String[]> list6 = new ArrayList<String[]>();
        ArrayList<String[]> list7 = new ArrayList<String[]>();
        ArrayList<String[]> list5 = new ArrayList<String[]>();
        ArrayList<String[]> list8 = new ArrayList<String[]>();
        ArrayList<String[]> list9 = new ArrayList<String[]>();
        ArrayList<String[]> list10 = new ArrayList<String[]>();
        ArrayList<String[]> random_id = new ArrayList<String[]>();

        ArrayList<String[]> list222 = new ArrayList<String[]>();
        ArrayList<String[]> list1 = new ArrayList<String[]>();
        ArrayList<String[]> curriculum_post_title = new ArrayList<String[]>();
        Map<String, ArrayList<String[]>> map = new HashMap<String, ArrayList<String[]>>();

        /*JSONParser jsonParser = new JSONParser();*/

        String[] News_title, id;
        String[] description;
        String[] created;
        String[] teacher_name;


        String[] post_id;

        String[] curriclm_tg_title;

        String[] liked, already_liked, like;
        String[] img_name_id;
        String[] cntnt_type;
        String status="";
        private MyCustomProgressDialog dialog;
        private String url_create_product = Base_url + "lms_api/news/get_news";
        private static final String TAG_staus = "status";
        private static final String TAG_response = "response";
        private static final String TAG_id = "id";
        private static final String TAG_title = "title";
        private static final String TAG_description = "description";
        private static final String TAG_teacher_name = "teacher_name";
        private static final String TAG_created = "created";
        private static final String TAG_news_like_count = "news_like_count";
        private static final String TAG_already_liked = "already_liked";


        private static final String TAG_post_id = "id";
        private static final String TAG_imagename_id = "id";
        private static final String TAG_images = "images";
        private static final String TAG_videos = "videos";
        private static final String TAG_video_imagename = "imagename";
        private static final String TAG_post_student = "students";
        private static final String TAG_ss_name = "name";
        private static final String TAG_random_files = "random_files";
        private static final String TAG_random_file_name = "random_file_name";
        private static final String TAG_random_already_liked = "picture_diary_like_count";

        private static final String TAG_videoname_mp4 = "videoname_mp4";

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new MyCustomProgressDialog(activity);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();


        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {

            // Log.i("datacreate", "datacreate working ...");
            // Building Parameters

            String newsResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode("H67jdS7wwfh", "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8")+
                        "&" + Const.Params.ClassId + "=" + URLEncoder.encode(selectid, "UTF-8")+
                        "&" + Const.Params.Type + "=" + URLEncoder.encode(select_type, "UTF-8")+
                        "&" + Const.Params.ParentId + "=" + URLEncoder.encode(user_id, "UTF-8")
                        ;


                newsResponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }

            /*List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("securityKey", "H67jdS7wwfh"));
            params.add(new BasicNameValuePair("authentication_token",
                    auth_token));
            params.add(new BasicNameValuePair("language", lang));
            params.add(new BasicNameValuePair("class_id", selectid));
            params.add(new BasicNameValuePair("type", select_type));
            params.add(new BasicNameValuePair("parent_id", user_id));

            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check for success tag
            // Log.i("json data lng......",lng);
            try {
                status = json.getString(TAG_staus);

                //	Log.e("=-=-=-=-=-=-", status);

                JSONArray posts = json.optJSONArray(TAG_response);

                News_title = new String[posts.length()];
                id = new String[posts.length()];
                description = new String[posts.length()];
                teacher_name = new String[posts.length()];
                created = new String[posts.length()];
                already_liked = new String[posts.length()];
                like = new String[posts.length()];

//				post_id = new String[posts.length()];
//				liked = new String[posts.length()];

                for (int j = 0; j < posts.length(); j++) {
                    JSONObject c = posts.getJSONObject(j);
                    //
                    News_title[j] = c.getString(TAG_title);
                    id[j] = c.getString(TAG_id);
                    description[j] = c.getString(TAG_description);
                    teacher_name[j] = c.getString(TAG_teacher_name);
                    created[j] = c.getString(TAG_created);
                    already_liked[j] = c.getString(TAG_news_like_count);
                    like[j] = c.getString(TAG_already_liked);

////////////////////////////////////////////////////////////

                    JSONArray images = c.optJSONArray(TAG_images);
                    String[] TAG_ima = new String[images.length()];
                    for (int l = 0; l < images.length(); l++) {
                        JSONObject m = images.getJSONObject(l);

                        TAG_ima[l] = Base_url + "news/viewPictureDiaryImages/"
                                + m.optString(TAG_imagename_id)
                                + "?authentication_token=" + auth_token;

                    }

                    //	Log.i("images...", Arrays.deepToString(TAG_ima));
                    list6.add(TAG_ima);
//
                    JSONArray video = c.optJSONArray(TAG_videos);
                    // String[] vdo_image_name=new
                    // String[video.length()];
                    String[] videoname_mp4 = new String[video.length()];
                    String[] TAG_vid = new String[video.length()];

                    for (int k = 0; k < video.length(); k++) {
                        JSONObject h = video.getJSONObject(k);
                        //						TAG_vid[k] = Base_url+"files/news/images/"
//								+ h.getString(TAG_video_imagename);
                        TAG_vid[k] = h.getString(TAG_video_imagename);

                        videoname_mp4[k] = Base_url
                                + "files/news/flv/" + h.getString(TAG_videoname_mp4);
                    }

                    //	Log.i("videos...", Arrays.deepToString(TAG_vid));
                    list7.add(TAG_vid);
                    list5.add(videoname_mp4);


                    JSONArray randm_files = c.optJSONArray(TAG_random_files);

                    String[] rndm_file_name = new String[randm_files
                            .length()];
                    String[] rndm_file_id = new String[randm_files
                            .length()];
                    for (int l = 0; l < randm_files.length(); l++) {
                        JSONObject me = randm_files.getJSONObject(l);

                        rndm_file_name[l] = me
                                .getString(TAG_video_imagename);
                        rndm_file_id[l] = me.getString(TAG_post_id);

                    }
                    list10.add(rndm_file_name);
                    random_id.add(rndm_file_id);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
*/
            return newsResponse;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {
                JSONObject jsonObject=new JSONObject();


                if(results!=null&&!results.isEmpty())
                {

                    jsonObject=new JSONObject(results);
                    if(jsonObject.has(Const.Params.Status)) {
                        status = jsonObject.getString(TAG_staus);
                    }



                }







                if (status.equalsIgnoreCase("true")) {

                    //	Log.e("=-=-=-=-=-=-", status);

                    JSONArray posts = jsonObject.optJSONArray(TAG_response);

                    News_title = new String[posts.length()];
                    id = new String[posts.length()];
                    description = new String[posts.length()];
                    teacher_name = new String[posts.length()];
                    created = new String[posts.length()];
                    already_liked = new String[posts.length()];
                    like = new String[posts.length()];

//				post_id = new String[posts.length()];
//				liked = new String[posts.length()];

                    for (int j = 0; j < posts.length(); j++) {
                        JSONObject c = posts.getJSONObject(j);
                        //
                        News_title[j] = c.getString(TAG_title);
                        id[j] = c.getString(TAG_id);
                        description[j] = c.getString(TAG_description);
                        teacher_name[j] = c.getString(TAG_teacher_name);
                        created[j] = c.getString(TAG_created);
                        already_liked[j] = c.getString(TAG_news_like_count);
                        like[j] = c.getString(TAG_already_liked);

////////////////////////////////////////////////////////////

                        JSONArray images = c.optJSONArray(TAG_images);
                        String[] TAG_ima = new String[images.length()];
                        for (int l = 0; l < images.length(); l++) {
                            JSONObject m = images.getJSONObject(l);

                            TAG_ima[l] = Base_url + "news/viewPictureDiaryImages/"
                                    + m.optString(TAG_imagename_id)
                                    + "?authentication_token=" + auth_token;

                        }

                        //	Log.i("images...", Arrays.deepToString(TAG_ima));
                        list6.add(TAG_ima);
//
                        JSONArray video = c.optJSONArray(TAG_videos);
                        // String[] vdo_image_name=new
                        // String[video.length()];
                        String[] videoname_mp4 = new String[video.length()];
                        String[] TAG_vid = new String[video.length()];

                        for (int k = 0; k < video.length(); k++) {
                            JSONObject h = video.getJSONObject(k);
                            //						TAG_vid[k] = Base_url+"files/news/images/"
//								+ h.getString(TAG_video_imagename);
                            TAG_vid[k] = h.getString(TAG_video_imagename);

                            videoname_mp4[k] = Base_url
                                    + "files/news/flv/" + h.getString(TAG_videoname_mp4);
                        }

                        //	Log.i("videos...", Arrays.deepToString(TAG_vid));
                        list7.add(TAG_vid);
                        list5.add(videoname_mp4);


                        JSONArray randm_files = c.optJSONArray(TAG_random_files);

                        String[] rndm_file_name = new String[randm_files
                                .length()];
                        String[] rndm_file_id = new String[randm_files
                                .length()];
                        for (int l = 0; l < randm_files.length(); l++) {
                            JSONObject me = randm_files.getJSONObject(l);

                            rndm_file_name[l] = me
                                    .getString(TAG_video_imagename);
                            rndm_file_id[l] = me.getString(TAG_post_id);

                        }
                        list10.add(rndm_file_name);
                        random_id.add(rndm_file_id);

                    }


                    if((activity) != null) {
                        CustomAdpterPost_news adapter = new CustomAdpterPost_news(activity,
                                News_title, description, teacher_name, created, list5, list6, list7, list9, already_liked, like, id, list10, random_id);
                        list.setAdapter(adapter);
                    }


//////////////////////Scroll up/down listener /////////	


            /*        if ((user_typ.equalsIgnoreCase("Student")) || (user_typ.equalsIgnoreCase("parent"))) {

                    } else {
                        list.setOnScrollListener(new OnScrollListener() {

                            private int mLastFirstVisibleItem;

                            @Override
                            public void onScrollStateChanged(AbsListView view, int
                                    scrollState) {
                                // TODO Auto-generated method stub
                            }

                            @Override
                            public void onScroll(AbsListView view, int firstVisibleItem,
                                                 int visibleItemCount, int totalItemCount) {
                                // TODO Auto-generated method stub

                                if (mLastFirstVisibleItem < firstVisibleItem) {
                                    main_layout.setVisibility(View.GONE);

                                }
                                if (mLastFirstVisibleItem > firstVisibleItem) {
                                    main_layout.setVisibility(View.VISIBLE);

                                }
                                mLastFirstVisibleItem = firstVisibleItem;

                            }
                        });
                    }*/

                }else{
                    dialog.dismiss();

                    try {


                        String msg = jsonObject.getString("message");
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
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    // class filterclass extends AsyncTask<String, String, String> {
    // ArrayList<String[]> list6 = new ArrayList<String[]>();
    // ArrayList<String[]> list7 = new ArrayList<String[]>();
    // ArrayList<String[]> list8 = new ArrayList<String[]>();
    // ArrayList<String[]> list9 = new ArrayList<String[]>();
    // ArrayList<String[]> list5 = new ArrayList<String[]>();
    //
    // ArrayList<String[]> list222 = new ArrayList<String[]>();
    // ArrayList<String[]> list1 = new ArrayList<String[]>();
    // Map<String, ArrayList<String[]>> map = new HashMap<String,
    // ArrayList<String[]>>();
    // ArrayList<String[]> curriculum_post_title = new ArrayList<String[]>();
    //
    // JSONParser jsonParser = new JSONParser();
    //
    // String[] catgy;
    // String[] descr;
    // String[] createddate;
    // String[] teacher_name;
    // String[] post_id;
    //
    // String[] curriclm_tg_title;
    //
    // String[] liked , already_liked;
    // String[] img_name_id;
    // String[] cntnt_type;
    // String status;
    // private ProgressDialog pDialog;
    // private static final String url_create_product =
    // "http://ps.pnf-sites.info/lms_api/picture_diary/get_filtered_posts";
    //
    // private static final String TAG_staus = "status";
    // private static final String TAG_posts = "posts";
    // private static final String TAG_teacher_name = "teacher_name";
    // private static final String TAG_category = "category";
    // private static final String TAG_description = "description";
    // private static final String TAG_created = "created";
    // private static final String TAG_post_id = "id";
    // private static final String TAG_imagename_id = "id";
    // private static final String TAG_images = "images";
    // private static final String TAG_videos = "videos";
    // private static final String TAG_video_imagename = "imagename";
    // private static final String TAG_post_student = "students";
    // private static final String TAG_ss_name = "name";
    // private static final String TAG_random_files = "random_files";
    // private static final String TAG_random_file_name = "random_file_name";
    // private static final String TAG_random_already_liked =
    // "picture_diary_like_count";
    // private static final String TAG_already_liked = "already_liked";
    // private static final String TAG_videoname_mp4 = "videoname_mp4";
    // private static final String TAG_associated_curriculum_tags =
    // "associated_curriculum_tags";
    // private static final String TAG_curriculum_post_title = "title";
    // private static final String TAG_curriculum_post_children = "children";
    // private static final String TAG_curriculum_post_subchildren =
    // "subchildren";
    //
    // /**
    // * Before starting background thread Show Progress Dialog
    // * */
    // @Override
    // protected void onPreExecute() {
    // super.onPreExecute();
    // pDialog = new ProgressDialog(News_Post.this);
    // pDialog.setMessage("get data.......");
    // pDialog.setIndeterminate(false);
    // pDialog.setCancelable(false);
    //
    // pDialog.show();
    //
    // // Toast.makeText(getApplicationContext(),
    // // Login_Email+Login_Password,Toast.LENGTH_LONG).show();
    // }
    //
    // /**
    // * Creating product
    // * */
    // protected String doInBackground(String... args) {
    //
    // JSONObject.quote(cur_id);
    // // Log.i("iiiiiiiiiiiii", JSONObject.quote(cur_id));
    // JSONArray mJSONArray = new JSONArray(Arrays.asList(array));
    // JSONArray ss_id = new JSONArray(Arrays.asList(st_ids));
    //
    // String fill = "{" + "\"student_ids\"" + ":" + ss_id + ","
    // + "\"group_ids\"" + ":" + mJSONArray + ","
    // + "\"curriculum_tag_ids\"" + ":" + JSONObject.quote(cur_id)
    // + "," + "\"from_date\"" + ":" + JSONObject.quote(fr_data)
    // + "," + "\"to_date\"" + ":" + JSONObject.quote(t_data)
    // + "," + "\"securityKey\"" + ":" + "\"H67jdS7wwfh\"" + ","
    // + "\"language\"" + ":" + "\"en\"" + ","
    // + "\"authentication_token\"" + ":"
    // + "\"f009415940c48425d5502d128fcee2e36235b443\"" + "}";
    //
    // // String
    // //
    // fg="{"+"\"student_ids\""+":"+JSONObject.quote(fr_data)+","+"\"images\""+":"+JSONObject.quote(fr_data)+","
    // // +"\"videos\""+":"+JSONObject.quote(fr_data)+","
    // // +"\"random_files\""+":"+JSONObject.quote(fr_data)+","
    // // + "\"group_ids\""+":"+JSONObject.quote(fr_data)+","
    // // +"\"securityKey\""+":"+"\"H67jdS7wwfh\""+","
    // //
    // +"\"curriculum_tag_ids\""+":"+JSONObject.quote(fr_data)+","+"\"language\""+":"+"\"en\""+","
    // //
    // +"\"authentication_token\""+":"+"\"a0790005b5646c244434da977cd8cd94beb04baf\""+","
    // //
    // +"\"description\""+":"+"\"2015-12-07\""+","+"\"mail\""+":"+"\"yes\""+"}";
    //
    // // Building Parameters
    // List<NameValuePair> params = new ArrayList<NameValuePair>();
    // params.add(new BasicNameValuePair("jsonData", fill));
    //
    // JSONObject json = jsonParser.makeHttpRequest(url_create_product,
    // "POST", params);
    //
    // // check for success tag
    // Log.i("json data......", json.toString());
    // try {
    // status = json.getString(TAG_staus);
    //
    // Log.e("=-=-=-=-=-=-", status);
    //
    // JSONArray posts = json.optJSONArray(TAG_posts);
    //
    // catgy = new String[posts.length()];
    // descr = new String[posts.length()];
    // createddate = new String[posts.length()];
    // teacher_name = new String[posts.length()];
    // post_id = new String[posts.length()];
    // liked = new String[posts.length()];
    // already_liked = new String[posts.length()];
    // for (int j = 0; j < posts.length(); j++) {
    // JSONObject c = posts.getJSONObject(j);
    // //
    // catgy[j] = c.getString(TAG_category);
    // descr[j] = c.getString(TAG_description);
    // createddate[j] = c.getString(TAG_created);
    // teacher_name[j] = c.getString(TAG_teacher_name);
    // post_id[j] = c.getString(TAG_post_id);
    // liked[j] = c.getString(TAG_random_already_liked);
    // already_liked[j] = c.getString(TAG_already_liked);
    // // Log.i("categoryyyyy", catgy[j]);
    // // Log.i("descr...........", descr[j]);
    // // Log.i("createddate", createddate[j]);
    //
    // //
    // //////////////////////////////////////////////////////////////////////////////////////
    //
    // JSONArray curriclm_tg_post = c
    // .optJSONArray(TAG_associated_curriculum_tags);
    //
    // curriclm_tg_title = new String[curriclm_tg_post.length()];
    //
    // for (int e = 0; j < curriclm_tg_post.length(); j++) {
    // JSONObject f = curriclm_tg_post.getJSONObject(j);
    //
    // curriclm_tg_title[j] = f
    // .getString(TAG_curriculum_post_title);
    //
    // // Log.i("categoryyyyy", catgy[j]);
    // // Log.i("descr...........", descr[j]);
    // // Log.i("createddate", createddate[j]);
    //
    // JSONArray curriclm_tg_chldrn_post = c
    // .optJSONArray(TAG_curriculum_post_children);
    // String[] childrn_title = new String[curriclm_tg_chldrn_post
    // .length()];
    // for (int g = 0; g < curriclm_tg_chldrn_post.length(); g++) {
    // JSONObject h = curriclm_tg_chldrn_post
    // .getJSONObject(g);
    //
    // childrn_title[g] = h
    // .getString(TAG_curriculum_post_title);
    //
    // JSONArray sub_chil = h
    // .getJSONArray(TAG_curriculum_post_subchildren);
    // // Log.i("lenght.......",
    // // Integer.toString(sub_chil.length()));
    // String[] Sub_child_title = new String[sub_chil
    // .length()];
    //
    // for (int k = 0; k < sub_chil.length(); k++) {
    //
    // JSONObject n = sub_chil.getJSONObject(k);
    //
    // Sub_child_title[k] = n
    // .getString(TAG_curriculum_post_title);
    //
    // }
    // // Log.i("sub child...",
    // // Arrays.deepToString(Sub_child_title));
    //
    // list222.add(Sub_child_title);
    //
    // }
    // list1.add(childrn_title);
    // map.put(Integer.toString(j), list222);
    //
    // }
    // curriculum_post_title.add(curriclm_tg_title);
    //
    // //
    // /////////////////////////////////////////////////////////////////////////////////////
    //
    // JSONArray images = c.optJSONArray(TAG_images);
    // String[] TAG_ima = new String[images.length()];
    // for (int l = 0; l < images.length(); l++) {
    // JSONObject m = images.getJSONObject(l);
    //
    // TAG_ima[l] =
    // "http://ps.pnf-sites.info/picture_diary/viewPictureDiaryImages/"
    // + m.optString(TAG_imagename_id)
    // + "?authentication_token=f009415940c48425d5502d128fcee2e36235b443";
    //
    // }
    //
    // list6.add(TAG_ima);
    //
    // JSONArray video = c.optJSONArray(TAG_videos);
    // // String[] vdo_image_name=new
    // // String[video.length()];
    // String[] videoname_mp4 = new String[video.length()];
    // String[] TAG_vid = new String[video.length()];
    // for (int k = 0; k < video.length(); k++) {
    // JSONObject h = video.getJSONObject(k);
    //
    // TAG_vid[k] = "http://ps.pnf-sites.info/"
    // + h.getString(TAG_video_imagename);
    // videoname_mp4[k] = "http://ps.pnf-sites.info/"
    // + h.getString(TAG_videoname_mp4);
    // }
    // list7.add(TAG_vid);
    // list5.add(videoname_mp4);
    //
    // JSONArray std_name = c.optJSONArray(TAG_post_student);
    // String[] TAG_s_name = new String[std_name.length()];
    // for (int l = 0; l < std_name.length(); l++) {
    // JSONObject m = std_name.getJSONObject(l);
    //
    // TAG_s_name[l] = m.getString(TAG_ss_name);
    //
    // }
    // list8.add(TAG_s_name);
    //
    // JSONArray randm_files = c.optJSONArray(TAG_random_files);
    // String[] rndm_file_name = new String[randm_files.length()];
    // for (int l = 0; l < randm_files.length(); l++) {
    // JSONObject me = randm_files.getJSONObject(l);
    //
    // rndm_file_name[l] = me.getString(TAG_random_file_name);
    //
    // }
    // list9.add(rndm_file_name);
    //
    // }
    //
    // if (status.equalsIgnoreCase("true")) {
    //
    // // successfully created product
    //
    // // Toast.makeText(getApplicationContext(),
    // // "Sign in Sucessfully", Toast.LENGTH_LONG).show();
    // } else {
    // // failed to create product
    // // Toast.makeText(getApplicationContext(),
    // // "invalid business_email and business_password",
    // // Toast.LENGTH_LONG).show();
    // }
    // } catch (JSONException e) {
    // e.printStackTrace();
    // }
    //
    // return null;
    // }
    //
    // /**
    // * After completing background task Dismiss the progress dialog
    // * **/
    // protected void onPostExecute(String file_url) {
    // // dismiss the dialog once done
    // pDialog.dismiss();
    //
    // // Log.i("oooooo", imm[0]);
    // if (status.equalsIgnoreCase("true")) {
    //
    // try {
    // String[] t = list6.get(0);
    // Log.i("@@@@@@@@@@@@", Arrays.deepToString(t));
    // } catch (Exception e) {
    // // TODO: handle exception
    // }
    //
    // // String[] cat=
    // //
    // {"http://ps.pnf-sites.info/picture_diary/viewPictureDiaryImages/52?authentication_token=a0790005b5646c244434da977cd8cd94beb04baf","http://img.youtube.com/vi/BIAyb-1uwTg/mqdefault.jpg","http://img.youtube.com/vi/BIAyb-1uwTg/mqdefault.jpg"};
    //
    // CustomAdpterPost adapter = new CustomAdpterPost(
    // News_Post.this, catgy, teacher_name, descr, post_id,
    // list6, list7, list8, list9, liked, list5,
    // curriculum_post_title, list1, map , already_liked);
    // list.setAdapter(adapter);
    //
    // list.setOnScrollListener(new OnScrollListener() {
    // private int mLastFirstVisibleItem;
    //
    // @Override
    // public void onScrollStateChanged(AbsListView view,
    // int scrollState) {
    // // TODO Auto-generated method stub
    //
    // }
    //
    // @Override
    // public void onScroll(AbsListView view,
    // int firstVisibleItem, int visibleItemCount,
    // int totalItemCount) {
    // // TODO Auto-generated method stub
    // if (mLastFirstVisibleItem < firstVisibleItem) {
    //
    // // main_layout.setVisibility(View.GONE);
    // // actionbar.setVisibility(View.GONE);
    // // main_layout.setVisibility(View.GONE);
    // }
    // if (mLastFirstVisibleItem > firstVisibleItem) {
    // // main_layout.setVisibility(View.VISIBLE);
    // // actionbar.setVisibility(View.VISIBLE);
    // }
    // mLastFirstVisibleItem = firstVisibleItem;
    // }
    // });
    //
    // }
    //
    // }
    // }

    // ////////////////////////////////////////////////////////////////////////////////////////////////
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

/////////////////////////////////////////////////////////	

    class getgroups extends AsyncTask<String, String, String> {

        /*JSONParser jsonParser = new JSONParser();*/

        String status="";
        private ProgressDialog pDialog;
        private String url_create_product = Base_url + "lms_api/news/get_classes";

        private static final String TAG_staus = "status";
        private static final String TAG_response = "response";
        private static final String TAG_groups_id = "id";
        private static final String TAG_groups_name = "name";
        private static final String TAG_type = "type";
        private static final String TAG_curriculum_tags = "curriculum_tags";
        private static final String TAG_curriculum_id = "id";
        private static final String TAG_curriculum_title = "title";
        private MyCustomProgressDialog dialog;
        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new MyCustomProgressDialog(activity);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            String classResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode("H67jdS7wwfh", "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8");


                classResponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }

 /*
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("securityKey", "H67jdS7wwfh"));
            params.add(new BasicNameValuePair("authentication_token",
                    auth_token));
            params.add(new BasicNameValuePair("language", lang));

           if (JSONUtils.isJSONValid(jsonParser.makeHttpRequest(url_create_product,
                    "POST", params).toString())) {

                json = jsonParser.makeHttpRequest(url_create_product,
                        "POST", params);
                try {
                    status = json.getString(TAG_staus);

                    Log.e("=-=-=-=-=-=-", json.toString());

                    JSONArray grps_name = json.optJSONArray(TAG_response);


                    if(json.has(TAG_response)) {
                        grp_id = new String[grps_name.length()];
                        grp_name = new String[grps_name.length()];
                        type = new String[grps_name.length()];
                        //		_selections = new boolean[grps_name.length()];
                        for (int j = 0; j < grps_name.length(); j++) {
                            JSONObject c = grps_name.getJSONObject(j);

                            grp_id[j] = c.getString(TAG_groups_id);
                            grp_name[j] = c.getString(TAG_groups_name);
                            type[j] = c.getString(TAG_type);

//					Log.i("categoryyyyy", grp_id[j]);
//					Log.i("descr...........", grp_name[j]);

                        }
                    }
                    else
                    {
*//*
                        SmartClassUtil.showToast(getActivity(),"Service Failed");
*//*

                        status="false";
                    }

                    if (status.equalsIgnoreCase("true")) {

                    } else {

                    }
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            } else {
                status = "false";


            }

            // check for success tag
            Log.i("json data......", params.toString());
            Log.i("Req", url_create_product);*/

            return classResponse;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            // pDialog.dismiss();
            dialog.dismiss();
            try {

                JSONObject jsonObject=new JSONObject();

                if(results!=null&&!results.isEmpty())
                {

                    jsonObject=new JSONObject(results);

                    if(jsonObject.has(Const.Params.Status))
                    {
                        status = jsonObject.getString(TAG_staus);

                    }

                }



                Log.i("getgroups", "getgroups working ...");
                if (status.equalsIgnoreCase("true")) {

                    if (jsonObject.has(TAG_response)) {
                        JSONArray grps_name = jsonObject.optJSONArray(TAG_response);

                        grp_id = new String[grps_name.length()];
                        grp_name = new String[grps_name.length()];
                        type = new String[grps_name.length()];
                        //		_selections = new boolean[grps_name.length()];
                        for (int j = 0; j < grps_name.length(); j++) {
                            JSONObject c = grps_name.getJSONObject(j);

                            grp_id[j] = c.getString(TAG_groups_id);
                            grp_name[j] = c.getString(TAG_groups_name);
                            type[j] = c.getString(TAG_type);

//					Log.i("categoryyyyy", grp_id[j]);
//					Log.i("descr...........", grp_name[j]);

                        }
                    }
                    newArray = new String[type.length];
//				Log.i("9898989898", Arrays.deepToString(type));

                    for (int i = 0; i < type.length; i++) {
                        if (type[i].equalsIgnoreCase("")) {
                            newArray[i] = "Latest";
                        } else {
                            newArray[i] = type[i];
                        }
                    }

                    new datacreate().execute();

                } else if(status.equalsIgnoreCase("false")){

                    try {


                        String msg = jsonObject.getString("message");
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

                    /*SmartClassUtil.showToast(getActivity(), "Service Failed");*/
                    new datacreate().execute();

                }
            }catch (Exception e)
            {
                e.printStackTrace();
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
