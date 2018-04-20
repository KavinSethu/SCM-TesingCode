package com.pnf.elar.app;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.security.Security;
import java.util.HashMap;
import java.util.Locale;
/*
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elar.util.NetworkUtil;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.API;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.ToStringConverterFactory;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("deprecation")
public class Setting extends Fragment {

    ImageView vibrationImage, Sounds, edu_blogImage, individual_PostImage, Posts_For_Groups,
            Posts_For_School, commentsImage, Coments_OwnDiscussion, newsImage,
            Group_Posts, SchoolPosts, Attandance, Individual_In_School,
            IndividualInCommonGroups, absence_NoteImage, RetrievalNote, languageImage,forum_button,iv_whole_school,iv_forum_group,iv_course,iv_comments_post,forum_mypost_button,iv_retrievallist,iv_schedule,iv_edublog,iv_news,iv_grade;

    String vibration="", sound="", language="", edu_blog="", individual_post="", group_post="",
            school_post="", comments="", own_comments="", news="", news_group_post="",
            news_school_post="", news_course_post="", attendence_notification="",
            attendence_individual_school="", attendence_individual_group="",
            absence_note="", retriever_note="", called_by = null, forum = "", forum_school_post = "", forum_group_post = "", forum_course_post= "", forum_comment_post = "", forum_only_mypost = "", wv_retrievallist = "", wv_edublog = "", wv_schedule = "", wv_news = "", evalution = "";

    TextView GENERAL, NOTIFICATION, Vibrationtext, SoundsText, LanguageText,
            EduBlog, IndividualPost, PostsForGroups, PostsForSchool,
            CommentsText, ComentsInOwnDiscussion, NewsText, GroupPosts,
            SchoolPostsText, AttandanceText, IndividualInSchool,
            IndividualGroups, AbsenceNote, RetrievalNoteText,version,tv_version,ForumText,tv_whole_school,tv_forum_group,tv_course,tv_comments_post,mypost,tv_retrievallist,tv_schedule,tv_edublog,tv_news,tv_grade,tv_events;

    int langu = 0;

    String regId, user_id;

    UserSessionManager session;
    String lang, auth_token, Base_url, user_typ, auth_token_normal_settings;

    View v,view_attendence,view_schedule;
    LinearLayout attendane_Layout,schedule_layout;

    private MyCustomProgressDialog dialog;

    String viewType = "";
    int viewStatus = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        session = new UserSessionManager(getActivity());

        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        auth_token = user.get(UserSessionManager.TAG_Authntication_token);
        auth_token_normal_settings = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        regId = user.get(UserSessionManager.TAG_regId);
        user_id = user.get(UserSessionManager.TAG_user_id);
        user_typ = user.get(UserSessionManager.TAG_user_type);

        if(user_typ.equalsIgnoreCase("Parent")){
            auth_token = user.get(UserSessionManager.TAG_Authntication_Children);
            ((Drawer) getActivity()).showParentMain();
        }else{
            auth_token = user.get(UserSessionManager.TAG_Authntication_token);
            ((Drawer) getActivity()).Backtomain();


        }

        if (lang.equalsIgnoreCase("sw")) {
            langu = 1;

        } else {
            langu = 0;
        }

        // init();

        v = inflater.inflate(R.layout.activity_setting, container, false);

        NOTIFICATION = (TextView) v.findViewById(R.id.NOTIFICATION);
        GENERAL = (TextView) v.findViewById(R.id.GENERAL);
        Vibrationtext = (TextView) v.findViewById(R.id.Vibrationtext);
        SoundsText = (TextView) v.findViewById(R.id.SoundsText);
        LanguageText = (TextView) v.findViewById(R.id.LanguageText);
        EduBlog = (TextView) v.findViewById(R.id.EduBlog);
        IndividualPost = (TextView) v.findViewById(R.id.IndividualPost);
        PostsForGroups = (TextView) v.findViewById(R.id.PostsForGroups);

        PostsForSchool = (TextView) v.findViewById(R.id.PostsForSchool);
        CommentsText = (TextView) v.findViewById(R.id.CommentsText);
        ComentsInOwnDiscussion = (TextView) v
                .findViewById(R.id.ComentsInOwnDiscussion);
        NewsText = (TextView) v.findViewById(R.id.NewsText);
        GroupPosts = (TextView) v.findViewById(R.id.GroupPosts);
        SchoolPostsText = (TextView) v.findViewById(R.id.SchoolPostsText);
        AttandanceText = (TextView) v.findViewById(R.id.AttandanceText);
        IndividualInSchool = (TextView) v.findViewById(R.id.IndividualInSchool);
        IndividualGroups = (TextView) v.findViewById(R.id.IndividualGroups);
        AbsenceNote = (TextView) v.findViewById(R.id.AbsenceNote);
        RetrievalNoteText = (TextView) v.findViewById(R.id.RetrievalNoteText);

        languageImage = (ImageView) v.findViewById(R.id.Language);
        vibrationImage = (ImageView) v.findViewById(R.id.Vibration);
        Sounds = (ImageView) v.findViewById(R.id.Sounds);
        edu_blogImage = (ImageView) v.findViewById(R.id.Edu_Blog);
        individual_PostImage = (ImageView) v.findViewById(R.id.Individual_Post);
        Posts_For_Groups = (ImageView) v.findViewById(R.id.Posts_For_Groups);
        Posts_For_School = (ImageView) v.findViewById(R.id.Posts_For_School);
        commentsImage = (ImageView) v.findViewById(R.id.Comments);
        Coments_OwnDiscussion = (ImageView) v
                .findViewById(R.id.Coments_OwnDiscussion);
        newsImage = (ImageView) v.findViewById(R.id.News);
        Group_Posts = (ImageView) v.findViewById(R.id.Group_Posts);
        SchoolPosts = (ImageView) v.findViewById(R.id.SchoolPosts);
        Attandance = (ImageView) v.findViewById(R.id.Attandance);
        Individual_In_School = (ImageView) v
                .findViewById(R.id.Individual_In_School);
        IndividualInCommonGroups = (ImageView) v
                .findViewById(R.id.IndividualInCommonGroups);
        absence_NoteImage = (ImageView) v.findViewById(R.id.Absence_Note);
        RetrievalNote = (ImageView) v.findViewById(R.id.RetrievalNote);
        version = (TextView) v.findViewById(R.id.version);
        tv_version = (TextView) v.findViewById(R.id.tv_version);
        ForumText = (TextView) v.findViewById(R.id.ForumText);
        tv_whole_school = (TextView) v.findViewById(R.id.tv_whole_school);
        tv_forum_group = (TextView) v.findViewById(R.id.tv_forum_group);
        tv_course = (TextView) v.findViewById(R.id.tv_course);
        tv_comments_post = (TextView) v.findViewById(R.id.tv_comments_post);

        forum_button = (ImageView) v.findViewById(R.id.forum_button);
        iv_whole_school = (ImageView) v.findViewById(R.id.iv_whole_school);
        iv_forum_group = (ImageView) v.findViewById(R.id.iv_forum_group);
        iv_course = (ImageView) v.findViewById(R.id.iv_course);
        iv_comments_post = (ImageView) v.findViewById(R.id.iv_comments_post);

        tv_retrievallist = (TextView) v.findViewById(R.id.tv_retrievallist);
        tv_schedule = (TextView) v.findViewById(R.id.tv_schedule);
        tv_edublog = (TextView) v.findViewById(R.id.tv_edublog);
        tv_news = (TextView) v.findViewById(R.id.tv_news);

        iv_retrievallist = (ImageView) v.findViewById(R.id.iv_retrievallist);
        iv_schedule = (ImageView) v.findViewById(R.id.iv_schedule);
        iv_edublog = (ImageView) v.findViewById(R.id.iv_edublog);
        iv_news = (ImageView) v.findViewById(R.id.iv_news);

        attendane_Layout = (LinearLayout) v.findViewById(R.id.attendane_Layout);
        view_attendence = (View) v.findViewById(R.id.view_attendence);
        schedule_layout = (LinearLayout) v.findViewById(R.id.schedule_layout);
        schedule_layout.setVisibility(View.GONE);
        view_schedule = (View) v.findViewById(R.id.view_schedule);
        view_schedule.setVisibility(View.GONE);

        tv_grade = (TextView) v.findViewById(R.id.tv_grade);
        iv_grade = (ImageView) v.findViewById(R.id.iv_grade);
        tv_events = (TextView) v.findViewById(R.id.tv_events);

        if (lang.equalsIgnoreCase("sw")) {

            GENERAL.setText("Övergripande inställningar");
            SoundsText.setText("Ljud");
            LanguageText.setText("Språk");
            NOTIFICATION.setText("Notiser");
            EduBlog.setText("Edublog");
            IndividualPost.setText("Individuell post");
            PostsForGroups.setText("Inlägg för grupper");
            PostsForSchool.setText("Inlägg för skolan");
            CommentsText.setText("kommentarer");
            ComentsInOwnDiscussion.setText("Kommentarer i egen diskussion");
            NewsText.setText("Nyheter");
            GroupPosts.setText("Gruppinlägg");
            SchoolPostsText.setText("Skolnyheter");
            AttandanceText.setText("Närvaro");
            IndividualInSchool.setText("Avlämning & Upphämtning");
            IndividualGroups.setText("Enbart relaterade grupper");
            AbsenceNote.setText("Frånvaroanmälan");
            RetrievalNoteText.setText("Annan hämtare");
            ForumText.setText("Forum");
            tv_whole_school.setText("Hela skolan relaterade inlägg");
            tv_forum_group.setText("Grupprelaterade inlägg");
            tv_course.setText("Kursrelaterade inlägg");
            tv_comments_post.setText("Kommentarer till inlägg");
            tv_retrievallist.setText("Närvaro");
            tv_schedule.setText("Schema");
            tv_edublog.setText("Edublog");
            tv_news.setText("Nyheter");
            tv_grade.setText("Publiserade omdömen");
            tv_events.setText("Händelser");

        } else {
            GENERAL.setText("General");
            SoundsText.setText("Sounds");
            LanguageText.setText("Language");
            NOTIFICATION.setText("Push notification");
            EduBlog.setText("Edu Blog");
            IndividualPost.setText("Individual Post");
            PostsForGroups.setText("Posts For Groups");
            PostsForSchool.setText("Posts For School");
            CommentsText.setText("Comments");
            ComentsInOwnDiscussion.setText("Comments In Own Discussion");
            NewsText.setText("News");
            GroupPosts.setText("Group Posts");
            SchoolPostsText.setText("School Posts");
            AttandanceText.setText("Attandance");
            IndividualInSchool.setText("Individual In School");
            IndividualGroups.setText("Individual In Common Groups");
            AbsenceNote.setText("Absence Note");
            RetrievalNoteText.setText("Retrieval Note");
            version.setText("Version");
            ForumText.setText("Forum");
            tv_whole_school.setText("Whole school related posts");
            tv_forum_group.setText("Group related posts");
            tv_course.setText("Forum Course");
            tv_comments_post.setText("Comments on posts");
            tv_retrievallist.setText("Attendance");
            tv_schedule.setText("Schedule");
            tv_edublog.setText("Edu Blog");
            tv_news.setText("News");
            tv_grade.setText("Published Evaluation");
            tv_events.setText("Events");

        }
//		// ///////////Back to mainActivity ////////

        if (lang.equalsIgnoreCase("sw")) {
            ((Drawer) getActivity()).setActionBarTitle("Inställningar");
        } else {
            ((Drawer) getActivity()).setActionBarTitle("Settings");
        }

        if(user_typ.equalsIgnoreCase("Teacher")){
            attendane_Layout.setVisibility(View.VISIBLE);
            view_attendence.setVisibility(View.VISIBLE);
        }else{
            attendane_Layout.setVisibility(View.GONE);
            view_attendence.setVisibility(View.GONE);
        }
//		// ////////////
        String versionName = BuildConfig.VERSION_NAME;
        tv_version.setText(versionName);

//		((Drawer)getActivity()).hideforall();
        new NotificationSetting().execute();
        new GetWebVersionSettings().execute();
        // ///////////////
        commentsImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "comments";
                        if (comments.equalsIgnoreCase("0")) {
                            commentsImage.setBackgroundResource(R.drawable.onbutton1);
                            comments = "1";

                        } else {
                            commentsImage.setBackgroundResource(R.drawable.offbutton1);
                            comments = "0";
                        }

                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                new UpdateSetting().execute();
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Log.i("comments_button", comments);
            }
        });

        // ///////////////
        vibrationImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "Vibration";
                        if (vibration.equalsIgnoreCase("0")) {
                            vibrationImage.setBackgroundResource(R.drawable.onbutton1);
                            vibration = "1";
                        } else {
                            vibrationImage.setBackgroundResource(R.drawable.offbutton1);
                            vibration = "0";
                        }
                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Log.i("Vibration_button", vibration);
            }
        });
        // /////////
        Sounds.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "Sounds";
                        if (sound.equalsIgnoreCase("0")) {
                            Sounds.setBackgroundResource(R.drawable.onbutton1);
                            sound = "1";
                        } else {
                            Sounds.setBackgroundResource(R.drawable.offbutton1);
                            sound = "0";
                        }
                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Log.i("Sounds_button", sound);
            }
        });
        // //////////////
        edu_blogImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "edu_blog";
                        if (edu_blog.equalsIgnoreCase("0")) {
                            edu_blogImage.setBackgroundResource(R.drawable.onbutton1);
                            edu_blog = "1";
                        } else {
                            edu_blogImage.setBackgroundResource(R.drawable.offbutton1);
                            edu_blog = "0";
                        }
                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Log.i("Edu_Blog_button", edu_blog);
            }
        });
        // /////////
        individual_PostImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "individual_post";
                        if (individual_post.equalsIgnoreCase("0")) {
                            individual_PostImage.setBackgroundResource(R.drawable.onbutton1);
                            individual_post = "1";
                        } else {
                            individual_PostImage.setBackgroundResource(R.drawable.offbutton1);
                            individual_post = "0";
                        }
                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                // Log.i("Individual_Post_button", individual_post);
            }
        });
        // //////////////////
        Posts_For_Groups.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "group_post";
                        if (group_post.equalsIgnoreCase("0")) {
                            Posts_For_Groups.setBackgroundResource(R.drawable.onbutton1);
                            group_post = "1";
                        } else {
                            Posts_For_Groups.setBackgroundResource(R.drawable.offbutton1);
                            group_post = "0";
                        }
                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Log.i("Posts_For_Groups_button", group_post);
            }
        });
        ///////////
        languageImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {

                        called_by = "Language";
                        if (language.equalsIgnoreCase("0")) {
                            //	Language.setBackgroundResource(R.drawable.tooglebtnsw);
                            language = "1";
                            session.createUserLoginSession("sw");
                            Locale locale = new Locale("sv");
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    /*
                        new UpdateSetting().execute();
    */
                        } else {
                            //	Language.setBackgroundResource(R.drawable.tooglebtnen);
                            language = "0";
                            session.createUserLoginSession("en");
                            Locale locale = new Locale("en");
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getResources().updateConfiguration(config, getResources().getDisplayMetrics());

                        }


                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


//				FragmentManager fragmentManager = getFragmentManager();
//				Setting Setting_frg = new Setting();
//				FragmentTransaction ft = fragmentManager.beginTransaction();
//				ft.replace(R.id.content_frame, Setting_frg);
//				ft.commit();

            }
        });
        // ////////////
        Posts_For_School.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "school_post";
                        if (school_post.equalsIgnoreCase("0")) {
                            Posts_For_School.setBackgroundResource(R.drawable.onbutton1);
                            school_post = "1";
                        } else {
                            Posts_For_School.setBackgroundResource(R.drawable.offbutton1);
                            school_post = "0";
                        }
                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Log.i("Posts_For_School_button", school_post);
            }
        });
        // //////////
        Coments_OwnDiscussion.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "own_comments";
                        if (own_comments.equalsIgnoreCase("0")) {
                            Coments_OwnDiscussion
                                    .setBackgroundResource(R.drawable.onbutton1);
                            own_comments = "1";
                        } else {
                            Coments_OwnDiscussion
                                    .setBackgroundResource(R.drawable.offbutton1);
                            own_comments = "0";
                        }
                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Log.i("Coments_OwnDiscussion_button", own_comments);
            }
        });
        // //////////////
        newsImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                called_by = "news";
                if (news.equalsIgnoreCase("0")) {
                    newsImage.setBackgroundResource(R.drawable.onbutton1);
                    news = "1";
                } else {
                    newsImage.setBackgroundResource(R.drawable.offbutton1);
                    news = "0";
                }
                new UpdateSetting().execute();
                // Log.i("News_button", news);
            }
        });
        // /////////
        Group_Posts.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "news_group_post";
                        if (news_group_post.equalsIgnoreCase("0")) {
                            Group_Posts.setBackgroundResource(R.drawable.onbutton1);
                            news_group_post = "1";
                        } else {
                            Group_Posts.setBackgroundResource(R.drawable.offbutton1);
                            news_group_post = "0";
                        }
                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Log.i("Group_Posts_button", news_group_post);
            }
        });
        ////////////
        SchoolPosts.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "news_school_post";
                        if (news_school_post.equalsIgnoreCase("0")) {
                            SchoolPosts.setBackgroundResource(R.drawable.onbutton1);
                            news_school_post = "1";
                        } else {
                            SchoolPosts.setBackgroundResource(R.drawable.offbutton1);
                            news_school_post = "0";
                        }
                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Log.i("SchoolPosts_button", news_school_post);
            }
        });
        //////////////
        Attandance.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "attendence_notification";
                        if (attendence_notification.equalsIgnoreCase("0")) {
                            Attandance.setBackgroundResource(R.drawable.onbutton1);
                            attendence_notification = "1";

                        } else {
                            Attandance.setBackgroundResource(R.drawable.offbutton1);
                            attendence_notification = "0";
                        }
                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Log.i("Attandance_button", attendence_notification);
            }
        });

        // ///////////
        Individual_In_School.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "attendence_individual_school";
                        if (attendence_individual_school.equalsIgnoreCase("0")) {
                            Individual_In_School
                                    .setBackgroundResource(R.drawable.onbutton1);
                            attendence_individual_school = "1";

                        } else {
                            Individual_In_School
                                    .setBackgroundResource(R.drawable.offbutton1);
                            attendence_individual_school = "0";
                        }
                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Log.i("Individual_In_School_button",
                // attendence_individual_school);
            }
        });
        // ////////////////
        IndividualInCommonGroups.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "attendence_individual_group";
                        if (attendence_individual_group.equalsIgnoreCase("0")) {
                            IndividualInCommonGroups
                                    .setBackgroundResource(R.drawable.onbutton1);
                            attendence_individual_group = "1";
                        } else {
                            IndividualInCommonGroups
                                    .setBackgroundResource(R.drawable.offbutton1);
                            attendence_individual_group = "0";
                        }
                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Log.i("IndividualInCommonGroups_button",
                // attendence_individual_group);
            }
        });
        // ////////////////
        absence_NoteImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "absence_note";
                        if (absence_note.equalsIgnoreCase("0")) {
                            absence_NoteImage.setBackgroundResource(R.drawable.onbutton1);
                            absence_note = "1";
                        } else {
                            absence_NoteImage.setBackgroundResource(R.drawable.offbutton1);
                            absence_note = "0";
                        }
                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Log.i("Absence_Note_button", absence_note);
            }
        });
        // ///////////////////
        RetrievalNote.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "retriever_note";
                        if (retriever_note.equalsIgnoreCase("0")) {
                            RetrievalNote.setBackgroundResource(R.drawable.offbutton1);
                            retriever_note = "1";
                        } else {
                            RetrievalNote.setBackgroundResource(R.drawable.onbutton1);
                            retriever_note = "0";
                        }
                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Log.i("RetrievalNote_button", absence_note);
            }
        });

        forum_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "forum";
                        if (forum.equalsIgnoreCase("0")) {
                            forum_button.setBackgroundResource(R.drawable.offbutton1);
                            forum = "1";
                        } else {
                            forum_button.setBackgroundResource(R.drawable.onbutton1);
                            forum = "0";
                        }
                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Log.i("RetrievalNote_button", absence_note);
            }
        });

        iv_whole_school.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "forum_school_post";
                        if (forum_school_post.equalsIgnoreCase("0")) {
                            iv_whole_school.setBackgroundResource(R.drawable.offbutton1);
                            forum_school_post = "1";
                        } else {
                            iv_whole_school.setBackgroundResource(R.drawable.onbutton1);
                            forum_school_post = "0";
                        }
                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Log.i("RetrievalNote_button", absence_note);
            }
        });

        iv_forum_group.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "forum_group_post";
                        if (forum_group_post.equalsIgnoreCase("0")) {
                            iv_forum_group.setBackgroundResource(R.drawable.offbutton1);
                            forum_group_post = "1";
                        } else {
                            iv_forum_group.setBackgroundResource(R.drawable.onbutton1);
                            forum_group_post = "0";
                        }
                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Log.i("RetrievalNote_button", absence_note);
            }
        });

        iv_course.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "forum_course_post";
                        if (forum_course_post.equalsIgnoreCase("0")) {
                            iv_course.setBackgroundResource(R.drawable.offbutton1);
                            forum_course_post = "1";
                        } else {
                            iv_course.setBackgroundResource(R.drawable.onbutton1);
                            forum_course_post = "0";
                        }
                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Log.i("RetrievalNote_button", absence_note);
            }
        });

        iv_comments_post.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "forum_comment_post";
                        if (forum_comment_post.equalsIgnoreCase("0")) {
                            iv_comments_post.setBackgroundResource(R.drawable.offbutton1);
                            forum_comment_post = "1";
                        } else {
                            iv_comments_post.setBackgroundResource(R.drawable.onbutton1);
                            forum_comment_post = "0";
                        }
                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Log.i("RetrievalNote_button", absence_note);
            }
        });

        iv_retrievallist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "attendance";
                        if (wv_retrievallist.equalsIgnoreCase("0")) {
                            iv_retrievallist.setBackgroundResource(R.drawable.offbutton1);
                            wv_retrievallist = "1";
                        } else {
                            iv_retrievallist.setBackgroundResource(R.drawable.onbutton1);
                            wv_retrievallist = "0";
                        }

                        new UpdateWebVersionSettings().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        iv_schedule.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "schedule";
                        if (wv_schedule.equalsIgnoreCase("0")) {
                            iv_schedule.setBackgroundResource(R.drawable.offbutton1);
                            wv_schedule = "1";
                        } else {
                            iv_schedule.setBackgroundResource(R.drawable.onbutton1);
                            wv_schedule = "0";
                        }
                        new UpdateWebVersionSettings().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        iv_edublog.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "edublog";
                        if (wv_edublog.equalsIgnoreCase("0")) {
                            iv_edublog.setBackgroundResource(R.drawable.offbutton1);
                            wv_edublog = "1";
                        } else {
                            iv_edublog.setBackgroundResource(R.drawable.onbutton1);
                            wv_edublog = "0";
                        }
                        new UpdateWebVersionSettings().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        iv_news.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "news";
                        if (wv_news.equalsIgnoreCase("0")) {
                            iv_news.setBackgroundResource(R.drawable.offbutton1);
                            wv_news = "1";
                        } else {
                            iv_news.setBackgroundResource(R.drawable.onbutton1);
                            wv_news = "0";
                        }
                        new UpdateWebVersionSettings().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        iv_grade.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if(NetworkUtil.getInstance(getActivity()).isInternet()) {
                        called_by = "evaluation";
                        if (evalution.equalsIgnoreCase("0")) {
                            iv_grade.setBackgroundResource(R.drawable.offbutton1);
                            evalution = "1";
                        } else {
                            iv_grade.setBackgroundResource(R.drawable.onbutton1);
                            evalution = "0";
                        }
                        new UpdateSetting().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Log.i("RetrievalNote_button", absence_note);
            }
        });

//        forum_mypost_button.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                called_by = "forum_only_mypost";
//                if (forum_only_mypost.equalsIgnoreCase("0")) {
//                    forum_mypost_button.setBackgroundResource(R.drawable.offbutton1);
//                    forum_only_mypost = "1";
//                } else {
//                    forum_mypost_button.setBackgroundResource(R.drawable.onbutton1);
//                    forum_only_mypost = "0";
//                }
//                new UpdateSetting().execute();
//                // Log.i("RetrievalNote_button", absence_note);
//            }
//        });
        // ///////////


        return v;
    }

    // private void init() {
    // // TODO Auto-generated method stub
    //
    // if (vibration.equalsIgnoreCase("1")) {
    // Vibration.setBackgroundResource(R.drawable.onicon);
    // } else {
    // Vibration.setBackgroundResource(R.drawable.officon);
    // }
    // // //////
    // if (sound.equalsIgnoreCase("1")) {
    // Sounds.setBackgroundResource(R.drawable.onicon);
    // } else {
    // Sounds.setBackgroundResource(R.drawable.officon);
    // }
    //
    // // /////////
    // if (edu_blog.equalsIgnoreCase("1")) {
    // Edu_Blog.setBackgroundResource(R.drawable.onicon);
    // } else {
    // Edu_Blog.setBackgroundResource(R.drawable.officon);
    // }
    // // /////////
    // if (individual_post.equalsIgnoreCase("1")) {
    // Individual_Post.setBackgroundResource(R.drawable.onicon);
    // } else {
    // Individual_Post.setBackgroundResource(R.drawable.officon);
    // }
    // // /////////
    // if (group_post.equalsIgnoreCase("1")) {
    // Posts_For_Groups.setBackgroundResource(R.drawable.onicon);
    // } else {
    // Posts_For_Groups.setBackgroundResource(R.drawable.officon);
    // }
    // // /////////
    // if (school_post.equalsIgnoreCase("1")) {
    // Posts_For_School.setBackgroundResource(R.drawable.onicon);
    // } else {
    // Posts_For_School.setBackgroundResource(R.drawable.officon);
    // }
    // // /////////
    // if (comments.equalsIgnoreCase("1")) {
    // Comments.setBackgroundResource(R.drawable.onicon);
    // } else {
    // Comments.setBackgroundResource(R.drawable.officon);
    // }
    // // /////////
    // if (own_comments.equalsIgnoreCase("1")) {
    // Coments_OwnDiscussion.setBackgroundResource(R.drawable.onicon);
    // } else {
    // Coments_OwnDiscussion.setBackgroundResource(R.drawable.officon);
    // }
    // // /////////
    //
    // }

    // /////////
    class NotificationSetting extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/news/get_settings";

        private static final String TAG_status = "status";
        private static final String TAG_response = "response";
        private static final String TAG_vibration = "vibration";
        private static final String TAG_sound = "sound";
        private static final String TAG_language = "language";
        private static final String TAG_edu_blog = "edu_blog";

        private static final String TAG_individual_post = "individual_post";
        private static final String TAG_group_post = "group_post";
        private static final String TAG_school_post = "school_post";
        private static final String TAG_comments = "comments";
        private static final String TAG_own_comments = "own_comments";

        private static final String TAG_news = "news";
        private static final String TAG_news_group_post = "news_group_post";
        private static final String TAG_news_school_post = "news_school_post";
        private static final String TAG_news_course_post = "news_course_post";

        private static final String TAG_attendence = "attendence";
        private static final String TAG_attendence_individual_school = "attendence_individual_school";
        private static final String TAG_attendence_individual_group = "attendence_individual_group";
        private static final String TAG_absence_note = "absence_note";
        private static final String TAG_retriever_note = "retriever_note";
        private static final String TAG_forum = "forum";
        private static final String TAG_forum_school_post = "forum_school_post";
        private static final String TAG_forum_group_post = "forum_group_post";
        private static final String TAG_forum_course_post = "forum_course_post";
        private static final String TAG_forum_comment_post = "forum_comment_post";
        private static final String TAG_forum_only_mypost = "forum_only_mypost";
        private static final String TAG_evalution = "evaluation";

        private MyCustomProgressDialog dialog;

        String Login_Email, Login_Password, Security = "H67jdS7wwfh";
        String Status, token, first_name, user_type, Remember_me;
        String[] component, image, other_accounts, name, first_name1,
                customer_name1, user_type1, username1, new_auth_tokn;

        // CustomAdapter_drawer adapter2;

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

            // Log.i("url", url);
            // Log.i("authentication_token", auth_token);
            // Log.i("lang", lang);
            // Log.i("device_token", regId);
            String settingsResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token_normal_settings, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.DeviceToken + "=" + URLEncoder.encode(regId, "UTF-8") +
                        "&" + Const.Params.UserId + "=" + URLEncoder.encode(user_id, "UTF-8");


                settingsResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*JSONParser jsonParser = new JSONParser();

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("securityKey", Security));
			params.add(new BasicNameValuePair("authentication_token",
					auth_token));
			params.add(new BasicNameValuePair("language", lang));
			params.add(new BasicNameValuePair("device_token", regId));
			params.add(new BasicNameValuePair("user_id", user_id));

			Log.d("Create Response", params.toString());

			JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

			// Log.d("Create Response.....", json.toString());

			try {

				JSONObject jsonObj = new JSONObject(json.toString());

				Status = jsonObj.getString(TAG_status);
				JSONArray response = jsonObj.optJSONArray(TAG_response);

				for (int j = 0; j < response.length(); j++) {
					JSONObject c = response.getJSONObject(j);
					vibration = c.getString(TAG_vibration);
					sound = c.getString(TAG_sound);
					language = c.getString(TAG_language);

					JSONObject edu_notification = c.getJSONObject(TAG_edu_blog);

					edu_blog = edu_notification.getString(TAG_edu_blog);
					individual_post = edu_notification
							.getString(TAG_individual_post);
					group_post = edu_notification.getString(TAG_group_post);
					school_post = edu_notification.getString(TAG_school_post);
					comments = edu_notification.getString(TAG_comments);
					own_comments = edu_notification.getString(TAG_own_comments);

					JSONObject news_notification = c.getJSONObject(TAG_news);

					news = news_notification.getString(TAG_news);
					news_group_post = news_notification
							.getString(TAG_news_group_post);
					news_school_post = news_notification
							.getString(TAG_news_school_post);
					news_course_post = news_notification
							.getString(TAG_news_course_post);

					JSONObject attendence = c.getJSONObject(TAG_attendence);

					attendence_notification = attendence
							.getString(TAG_attendence);
					attendence_individual_school = attendence
							.getString(TAG_attendence_individual_school);
					attendence_individual_group = attendence
							.getString(TAG_attendence_individual_group);
					absence_note = attendence.getString(TAG_absence_note);
					retriever_note = attendence.getString(TAG_retriever_note);


//					 Log.i("vibration", vibration);
//					 Log.i("sound", sound);
//					 Log.i("language", language);
//					 Log.i("edu_blog", edu_blog);
//					 Log.i("individual_post", individual_post);
//					 Log.i("group_post", group_post);
//					 Log.i("school_post", school_post);
//					 Log.i("comments", comments);
//					 Log.i("own_comments", own_comments);
//					 Log.i("news", news);
//					 Log.i("news_group_post", news_group_post);
//					 Log.i("news_school_post", news_school_post);   /////////////// test ////////////////
//					 Log.i("news_course_post", news_course_post);
//					 Log.i("attendence_notification",
//					 attendence_notification);
//					 Log.i("attendence_individual_school",
//					 attendence_individual_school);
//					 Log.i("attendence_individual_group",
//					 attendence_individual_group);
//					 Log.i("absence_note", absence_note);
//					 Log.i("retriever_note", retriever_note);

				}

				if (Status.equalsIgnoreCase("true")) {

				} else {

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
*/
            return settingsResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {

                JSONObject jsonObj = new JSONObject();
                if (!results.isEmpty() && results != null) {

                    jsonObj = new JSONObject(results);

                    if (jsonObj.has(Const.Params.Status)) {
                        Status = jsonObj.getString(TAG_status);


                    } else {
                        Status = "false";
                    }

                } else {
                    Status = "false";
                }

                if (Status.equalsIgnoreCase("true")) {


                    JSONArray response = jsonObj.optJSONArray(TAG_response);

                    for (int j = 0; j < response.length(); j++) {
                        JSONObject c = response.getJSONObject(j);
                        vibration = c.getString(TAG_vibration);
                        sound = c.getString(TAG_sound);
                        language = c.getString(TAG_language);

                        JSONObject edu_notification = c.getJSONObject(TAG_edu_blog);

                        edu_blog = edu_notification.getString(TAG_edu_blog);
                        individual_post = edu_notification
                                .getString(TAG_individual_post);
                        group_post = edu_notification.getString(TAG_group_post);
                        school_post = edu_notification.getString(TAG_school_post);
                        comments = edu_notification.getString(TAG_comments);
                        own_comments = edu_notification.getString(TAG_own_comments);

                        JSONObject news_notification = c.getJSONObject(TAG_news);

                        news = news_notification.getString(TAG_news);
                        news_group_post = news_notification
                                .getString(TAG_news_group_post);
                        news_school_post = news_notification
                                .getString(TAG_news_school_post);
                        news_course_post = news_notification
                                .getString(TAG_news_course_post);

                        JSONObject attendence = c.getJSONObject(TAG_attendence);

                        attendence_notification = attendence
                                .getString(TAG_attendence);
                        attendence_individual_school = attendence
                                .getString(TAG_attendence_individual_school);
                        attendence_individual_group = attendence
                                .getString(TAG_attendence_individual_group);
                        absence_note = attendence.getString(TAG_absence_note);
                        retriever_note = attendence.getString(TAG_retriever_note);

                        JSONObject forum_notification = c.getJSONObject(TAG_forum);

                        forum = forum_notification.getString(TAG_forum);
                        forum_school_post = forum_notification.getString(TAG_forum_school_post);
                        forum_group_post = forum_notification.getString(TAG_forum_group_post);
                        forum_course_post = forum_notification.getString(TAG_forum_course_post);
                        forum_comment_post = forum_notification.getString(TAG_forum_comment_post);
                        forum_only_mypost = forum_notification.getString(TAG_forum_only_mypost);

                        JSONObject eval = c.getJSONObject(TAG_evalution);
                        evalution = eval.getString(TAG_evalution);

                    }


                    if (vibration.equalsIgnoreCase("1")) {
                        vibrationImage.setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        vibrationImage.setBackgroundResource(R.drawable.offbutton1);
                    }
                    // //////
                    if (sound.equalsIgnoreCase("1")) {
                        Sounds.setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        Sounds.setBackgroundResource(R.drawable.offbutton1);
                    }

                    // /////////
                    if (edu_blog.equalsIgnoreCase("1")) {
                        edu_blogImage.setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        edu_blogImage.setBackgroundResource(R.drawable.offbutton1);
                    }
                    // /////////
                    if (individual_post.equalsIgnoreCase("1")) {
                        individual_PostImage
                                .setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        individual_PostImage
                                .setBackgroundResource(R.drawable.offbutton1);
                    }
                    // /////////
                    if (group_post.equalsIgnoreCase("1")) {
                        Posts_For_Groups
                                .setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        Posts_For_Groups
                                .setBackgroundResource(R.drawable.offbutton1);
                    }
                    // /////////
                    if (school_post.equalsIgnoreCase("1")) {
                        Posts_For_School
                                .setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        Posts_For_School
                                .setBackgroundResource(R.drawable.offbutton1);
                    }
                    // /////////
                    if (comments.equalsIgnoreCase("1")) {
                        commentsImage.setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        commentsImage.setBackgroundResource(R.drawable.offbutton1);
                    }
                    // /////////
                    if (own_comments.equalsIgnoreCase("1")) {
                        Coments_OwnDiscussion
                                .setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        Coments_OwnDiscussion
                                .setBackgroundResource(R.drawable.offbutton1);
                    }
                    // /////////
                    if (news.equalsIgnoreCase("1")) {
                        newsImage.setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        newsImage.setBackgroundResource(R.drawable.offbutton1);
                    }
                    // /////////
                    if (news_group_post.equalsIgnoreCase("1")) {
                        Group_Posts.setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        Group_Posts.setBackgroundResource(R.drawable.offbutton1);
                    }
                    // /////////
                    if (news_school_post.equalsIgnoreCase("1")) {
                        SchoolPosts.setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        SchoolPosts.setBackgroundResource(R.drawable.offbutton1);
                    }
                    // /////////
                    if (attendence_notification.equalsIgnoreCase("1")) {
                        Attandance.setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        Attandance.setBackgroundResource(R.drawable.offbutton1);
                    }
                    // /////////
                    if (attendence_individual_school.equalsIgnoreCase("1")) {
                        Individual_In_School
                                .setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        Individual_In_School
                                .setBackgroundResource(R.drawable.offbutton1);
                    }
                    // /////////
                    if (attendence_individual_group.equalsIgnoreCase("1")) {
                        IndividualInCommonGroups
                                .setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        IndividualInCommonGroups
                                .setBackgroundResource(R.drawable.offbutton1);
                    }
                    // /////////
                    if (absence_note.equalsIgnoreCase("1")) {
                        absence_NoteImage.setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        absence_NoteImage.setBackgroundResource(R.drawable.offbutton1);
                    }
                    // /////////
                    if (retriever_note.equalsIgnoreCase("1")) {
                        RetrievalNote.setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        RetrievalNote.setBackgroundResource(R.drawable.offbutton1);
                    }
                    // /////////
                    if (lang.equalsIgnoreCase("sw")) {
                        languageImage.setBackgroundResource(R.drawable.swtoggle); // ///////////////
                        // working
                        // on
                        // it
                        language="1";
                    } else {
                        language="0";
                        languageImage.setBackgroundResource(R.drawable.entoggel);
                    }

                    if (forum.equalsIgnoreCase("1")) {
                        forum_button.setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        forum_button.setBackgroundResource(R.drawable.offbutton1);
                    }

                    if (forum_school_post.equalsIgnoreCase("1")) {
                        iv_whole_school.setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        iv_whole_school.setBackgroundResource(R.drawable.offbutton1);
                    }

                    if (forum_group_post.equalsIgnoreCase("1")) {
                        iv_forum_group.setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        iv_forum_group.setBackgroundResource(R.drawable.offbutton1);
                    }

                    if (forum_course_post.equalsIgnoreCase("1")) {
                        iv_course.setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        iv_course.setBackgroundResource(R.drawable.offbutton1);
                    }

                    if (forum_comment_post.equalsIgnoreCase("1")) {
                        iv_comments_post.setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        iv_comments_post.setBackgroundResource(R.drawable.offbutton1);
                    }

                    if (evalution.equalsIgnoreCase("1")) {
                        iv_grade.setBackgroundResource(R.drawable.onbutton1);
                    } else {
                        iv_grade.setBackgroundResource(R.drawable.offbutton1);
                    }

//                    if (forum_only_mypost.equalsIgnoreCase("1")) {
//                        forum_mypost_button.setBackgroundResource(R.drawable.onbutton1);
//                    } else {
//                        forum_mypost_button.setBackgroundResource(R.drawable.offbutton1);
//                    }
                    // /////////

                } else {

                    // failed to creat

                    try {


                        String msg = jsonObj.getString("message");
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
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    // ////////////////
    class UpdateSetting extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/news/update_settings";

        private static final String TAG_status = "status";
        final String TAG_message = Const.Params.Message;
        private static final String TAG_response = "response";
        private static final String TAG_vibration = "vibration";
        private static final String TAG_sound = "sound";
        private static final String TAG_language = "language";
        private static final String TAG_edu_blog = "edu_blog";

        private static final String TAG_individual_post = "individual_post";
        private static final String TAG_group_post = "group_post";
        private static final String TAG_school_post = "school_post";
        private static final String TAG_comments = "comments";
        private static final String TAG_own_comments = "own_comments";

        private static final String TAG_news = "news";
        private static final String TAG_news_group_post = "news_group_post";
        private static final String TAG_news_school_post = "news_school_post";
        private static final String TAG_news_course_post = "news_course_post";

        private static final String TAG_attendence = "attendence";
        private static final String TAG_attendence_individual_school = "attendence_individual_school";
        private static final String TAG_attendence_individual_group = "attendence_individual_group";
        private static final String TAG_absence_note = "absence_note";
        private static final String TAG_retriever_note = "retriever_note";
        private static final String TAG_forum = "forum";
        private static final String TAG_forum_school_post = "forum_school_post";
        private static final String TAG_forum_group_post = "forum_group_post";
        private static final String TAG_forum_course_post = "forum_course_post";
        private static final String TAG_forum_comment_post = "forum_comment_post";
        private static final String TAG_forum_only_mypost = "forum_only_mypost";
        private static final String TAG_evalution = "evaluation";


        private MyCustomProgressDialog dialog;

        String Login_Email, Login_Password, Security = "H67jdS7wwfh";
        String Status, message, token, first_name, user_type, Remember_me;
        String[] component, image, other_accounts, name, first_name1,
                customer_name1, user_type1, username1, new_auth_tokn;
        String individual_post_update;
        // CustomAdapter_drawer adapter2;
        /*JSONParser jsonParser = new JSONParser();*/

        /*List<NameValuePair> params = new ArrayList<NameValuePair>();*/
        JSONObject jsonObjresponse = new JSONObject();
        String urlParams = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            try {
                if (langu == 0) {
                    dialog = new MyCustomProgressDialog(getActivity());
                    dialog.setIndeterminate(true);
                    dialog.setCancelable(false);
                    dialog.show();
                }

                urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token_normal_settings, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.DeviceToken + "=" + URLEncoder.encode(regId, "UTF-8") +
                        "&" + Const.Params.UserId + "=" + URLEncoder.encode(user_id, "UTF-8");




				/*params.add(new BasicNameValuePair("securityKey", Security));
				params.add(new BasicNameValuePair("authentication_token",
						auth_token));
				params.add(new BasicNameValuePair("language", lang));
				params.add(new BasicNameValuePair("user_id", user_id));
				params.add(new BasicNameValuePair("device_token", regId));*/
                if (called_by.equalsIgnoreCase("Vibration")) {
                    urlParams += "&" + Const.Params.Vibration + "=" + URLEncoder.encode(vibration, "UTF-8");
/*
					params.add(new BasicNameValuePair("vibration", vibration));
*/
                }
                if (called_by.equalsIgnoreCase("Sounds")) {
                    urlParams += "&" + Const.Params.Sound + "=" + URLEncoder.encode(sound, "UTF-8");

/*
					params.add(new BasicNameValuePair("sound", sound));
*/
                }
                if (called_by.equalsIgnoreCase("Language")) {
                    urlParams += "&" + Const.Params.LanguageDevice + "=" + URLEncoder.encode(language, "UTF-8");

/*
					params.add(new BasicNameValuePair("language_device", language));
*/
                }
                if (called_by.equalsIgnoreCase("edu_blog"))            /////////////////// error
                {

                    urlParams += "&" + Const.Params.EduBlog + "=" + URLEncoder.encode(edu_blog, "UTF-8");

					/*params.add(new BasicNameValuePair("edu_blog", edu_blog));*/
                }
                if (called_by.equalsIgnoreCase("individual_post")) {
                    urlParams += "&" + Const.Params.IndividualPost + "=" + URLEncoder.encode(individual_post, "UTF-8");
					/*params.add(new BasicNameValuePair("individual_post", individual_post));*/
                }
                if (called_by.equalsIgnoreCase("group_post")) {
                    urlParams += "&" + Const.Params.GroupPost + "=" + URLEncoder.encode(group_post, "UTF-8");
					/*params.add(new BasicNameValuePair("group_post", group_post));*/
                }
                if (called_by.equalsIgnoreCase("school_post")) {
                    urlParams += "&" + Const.Params.SchoolPost + "=" + URLEncoder.encode(school_post, "UTF-8");
					/*params.add(new BasicNameValuePair("school_post", school_post));*/
                }
                if (called_by.equalsIgnoreCase("Comments")) {
                    urlParams += "&" + Const.Params.COmments + "=" + URLEncoder.encode(comments, "UTF-8");
					/*params.add(new BasicNameValuePair("comments", comments));*/
                }
                if (called_by.equalsIgnoreCase("own_comments")) {
                    urlParams += "&" + Const.Params.OwnCOmments + "=" + URLEncoder.encode(own_comments, "UTF-8");
/*
					params.add(new BasicNameValuePair("own_comments", own_comments));
*/
                }
                if (called_by.equalsIgnoreCase("news")) {
                    urlParams += "&" + Const.Params.News + "=" + URLEncoder.encode(news, "UTF-8");
					/*params.add(new BasicNameValuePair("news", news));*/
                }
                if (called_by.equalsIgnoreCase("news_group_post")) {
                    urlParams += "&" + Const.Params.NewsGroupPost + "=" + URLEncoder.encode(news_group_post, "UTF-8");
					/*params.add(new BasicNameValuePair("news_group_post",
							news_group_post));*/
                }
                if (called_by.equalsIgnoreCase("news_school_post")) {
                    urlParams += "&" + Const.Params.NewsSchoolPost + "=" + URLEncoder.encode(news_school_post, "UTF-8");
					/*params.add(new BasicNameValuePair("news_school_post",
							news_school_post));*/
                }
                if (called_by.equalsIgnoreCase("attendence_notification")) {
                    urlParams += "&" + Const.Params.Attendance + "=" + URLEncoder.encode(attendence_notification, "UTF-8");
					/*params.add(new BasicNameValuePair("attendence",
							attendence_notification));*/
                }
                if (called_by.equalsIgnoreCase("attendence_individual_school")) {
                    urlParams += "&" + Const.Params.AttendanceIndividualSchool + "=" + URLEncoder.encode(attendence_individual_school, "UTF-8");
					/*params.add(new BasicNameValuePair("attendence_individual_school",
							attendence_individual_school));*/
                }
                if (called_by.equalsIgnoreCase("attendence_individual_group")) {
                    urlParams += "&" + Const.Params.AttendanceIndividualGroup + "=" + URLEncoder.encode(attendence_individual_group, "UTF-8");
				/*	params.add(new BasicNameValuePair("attendence_individual_group",
							attendence_individual_group));*/
                }
                if (called_by.equalsIgnoreCase("absence_note")) {
                    urlParams += "&" + Const.Params.AbsenceNote + "=" + URLEncoder.encode(absence_note, "UTF-8");
/*
					params.add(new BasicNameValuePair("absence_note", absence_note));
*/
                }
                if (called_by.equalsIgnoreCase("retriever_note")) {
                    urlParams += "&" + Const.Params.RetriverNote + "=" + URLEncoder.encode(retriever_note, "UTF-8");
/*
					params.add(new BasicNameValuePair("retriever_note", retriever_note));
*/
                }
                if (called_by.equalsIgnoreCase("forum")) {
                    urlParams += "&" + Const.Params.Forum + "=" + URLEncoder.encode(forum, "UTF-8");
/*
					params.add(new BasicNameValuePair("retriever_note", retriever_note));
*/
                }
                if (called_by.equalsIgnoreCase("forum_school_post")) {
                    urlParams += "&" + Const.Params.Forum_School + "=" + URLEncoder.encode(forum_school_post, "UTF-8");
/*
					params.add(new BasicNameValuePair("retriever_note", retriever_note));
*/
                }
                if (called_by.equalsIgnoreCase("forum_group_post")) {
                    urlParams += "&" + Const.Params.Forum_Group + "=" + URLEncoder.encode(forum_group_post, "UTF-8");
/*
					params.add(new BasicNameValuePair("retriever_note", retriever_note));
*/
                }
                if (called_by.equalsIgnoreCase("forum_course_post")) {
                    urlParams += "&" + Const.Params.Forum_Course + "=" + URLEncoder.encode(forum_course_post, "UTF-8");
/*
					params.add(new BasicNameValuePair("retriever_note", retriever_note));
*/
                }
                if (called_by.equalsIgnoreCase("forum_comment_post")) {
                    urlParams += "&" + Const.Params.Forum_Comment + "=" + URLEncoder.encode(forum_comment_post, "UTF-8");
/*
					params.add(new BasicNameValuePair("retriever_note", retriever_note));
*/
                }
                if (called_by.equalsIgnoreCase("forum_only_mypost")) {
                    urlParams += "&" + Const.Params.Forum_Post + "=" + URLEncoder.encode(forum_only_mypost, "UTF-8");
/*
					params.add(new BasicNameValuePair("retriever_note", retriever_note));
*/
                }

                if (called_by.equalsIgnoreCase("evaluation")) {
                    urlParams += "&" + Const.Params.Evaluation + "=" + URLEncoder.encode(evalution, "UTF-8");
/*
					params.add(new BasicNameValuePair("retriever_note", retriever_note));
*/
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            String settingsResponse = "";
			/*Log.i("authentication_token", auth_token);
			Log.i("language", lang);
			Log.i("vibration", vibration);
			Log.i("sound", sound);
			Log.i("language", language);
			Log.i("edu_blog", edu_blog);
			Log.i("individual_post", individual_post);
			Log.i("comments", comments);
			Log.i("own_comments", own_comments);
			Log.i("news", news);
			Log.i("news_group_post", news_group_post);
			Log.i("news_school_post", news_school_post);
			Log.i("news_course_post", news_course_post);
			Log.i("attendence_notification",
					attendence_notification);
			*//* Log.i("attendence_individual_school",
			 attendence_individual_school);
			 Log.i("attendence_individual_group",
			 attendence_individual_group);*//*
			Log.i("absence_note", absence_note);
			Log.i("retriever_note", retriever_note);


			Log.d("responc......", params.toString());

			JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);*/


            // Log.d("Create Response.....", json.toString());

            try {
                settingsResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);

/*
				if (Status.equalsIgnoreCase("true")) {

				} else {

				}*/

            } catch (Exception e) {
                e.printStackTrace();
            }

            return settingsResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done

            try {
                if (langu == 0) {
                    dialog.dismiss();
                }

                System.out.println("empty " + results);

                if (results != null && !results.isEmpty()) {


                    jsonObjresponse = new JSONObject(results);
                    if (jsonObjresponse.has(Const.Params.Status)) {

                        Status = jsonObjresponse.getString(TAG_status);
                    } else {
                        Status = "false";
                    }
                } else {

                    Status = "false";
                }
                if (Status.equalsIgnoreCase("true")) {


                    Status = jsonObjresponse.getString(TAG_status);
                    message = jsonObjresponse.getString(TAG_message);

                    JSONArray response = jsonObjresponse.optJSONArray(TAG_response);

                    for (int j = 0; j < response.length(); j++) {
                        JSONObject c = response.getJSONObject(j);
                        vibration = c.getString(TAG_vibration);
                        sound = c.getString(TAG_sound);
                        language = c.getString(TAG_language);

                        JSONObject edu_notification = c.getJSONObject(TAG_edu_blog);

                        edu_blog = edu_notification.getString(TAG_edu_blog);
                        individual_post_update = edu_notification
                                .getString(TAG_individual_post);
                        group_post = edu_notification.getString(TAG_group_post);
                        school_post = edu_notification.getString(TAG_school_post);
                        comments = edu_notification.getString(TAG_comments);
                        own_comments = edu_notification.getString(TAG_own_comments);

                        JSONObject news_notification = c.getJSONObject(TAG_news);

                        news = news_notification.getString(TAG_news);
                        news_group_post = news_notification
                                .getString(TAG_news_group_post);
                        news_school_post = news_notification
                                .getString(TAG_news_school_post);
                        news_course_post = news_notification
                                .getString(TAG_news_course_post);

                        JSONObject attendence = c.getJSONObject(TAG_attendence);

                        attendence_notification = attendence
                                .getString(TAG_attendence);
                        attendence_individual_school = attendence
                                .getString(TAG_attendence_individual_school);
                        attendence_individual_group = attendence
                                .getString(TAG_attendence_individual_group);
                        absence_note = attendence.getString(TAG_absence_note);
                        retriever_note = attendence.getString(TAG_retriever_note);

                        JSONObject forum_notification = c.getJSONObject(TAG_forum);

                        forum = forum_notification.getString(TAG_forum);
                        forum_school_post = forum_notification.getString(TAG_forum_school_post);
                        forum_group_post = forum_notification.getString(TAG_forum_group_post);
                        forum_course_post = forum_notification.getString(TAG_forum_course_post);
                        forum_comment_post = forum_notification.getString(TAG_forum_comment_post);
                        forum_only_mypost = forum_notification.getString(TAG_forum_only_mypost);


                        JSONObject eval = c.getJSONObject(TAG_evalution);
                        evalution = eval.getString(TAG_evalution);


                    }
                    if (language.equalsIgnoreCase("1")) {
                        ((Drawer) getActivity()).setActionBarTitle("Inställningar");

                        GENERAL.setText("Övergripande inställningar");
                        SoundsText.setText("Ljud");
                        LanguageText.setText("Språk");
                        NOTIFICATION.setText("Notiser");
                        EduBlog.setText("Edublog");
                        IndividualPost.setText("Individuell post");
                        PostsForGroups.setText("Inlägg för grupper");
                        PostsForSchool.setText("Inlägg för skolan");
                        CommentsText.setText("kommentarer");
                        ComentsInOwnDiscussion.setText("Kommentarer i egen diskussion");
                        NewsText.setText("Nyheter");
                        GroupPosts.setText("Gruppinlägg");
                        SchoolPostsText.setText("Skolnyheter");
                        AttandanceText.setText("Närvaro");
                        IndividualInSchool.setText("Avlämning & Upphämtning");
                        IndividualGroups.setText("Enbart relaterade grupper");
                        AbsenceNote.setText("Frånvaroanmälan");
                        RetrievalNoteText.setText("Annan hämtare");
                        ForumText.setText("Forum");
                        tv_whole_school.setText("Hela skolan relaterade inlägg");
                        tv_forum_group.setText("Grupprelaterade inlägg");
                        tv_course.setText("Kursrelaterade inlägg");
                        tv_comments_post.setText("Kommentarer till inlägg");
                        tv_grade.setText("Publiserade omdömen");
                        tv_events.setText("Händelser");

                    } else {
                        ((Drawer) getActivity()).setActionBarTitle("Setting");

                        GENERAL.setText("General");
                        SoundsText.setText("Sounds");
                        LanguageText.setText("Language");
                        NOTIFICATION.setText("Push notification");
                        EduBlog.setText("Edu Blog");
                        IndividualPost.setText("Individual Post");
                        PostsForGroups.setText("Posts For Groups");
                        PostsForSchool.setText("Posts For School");
                        CommentsText.setText("Comments");
                        ComentsInOwnDiscussion.setText("Comments In Own Discussion");
                        NewsText.setText("News");
                        GroupPosts.setText("Group Posts");
                        SchoolPostsText.setText("School Posts");
                        AttandanceText.setText("Attendance");
                        IndividualInSchool.setText("Individual In School");
                        IndividualGroups.setText("Individual In Common Groups");
                        AbsenceNote.setText("Absence Note");
                        RetrievalNoteText.setText("Retrieval Note");
                        ForumText.setText("Forum");
                        tv_whole_school.setText("Whole school related posts");
                        tv_forum_group.setText("Group related posts");
                        tv_course.setText("Forum Course");
                        tv_comments_post.setText("Comments on posts");
                        tv_grade.setText("Published Evaluation");
                        tv_events.setText("Events");
                    }

                    if (langu == 0) {
                        dialog.dismiss();


                        if (vibration.equalsIgnoreCase("1")) {
                            vibrationImage.setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            vibrationImage.setBackgroundResource(R.drawable.offbutton1);
                        }
                        // //////
                        if (sound.equalsIgnoreCase("1")) {
                            Sounds.setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            Sounds.setBackgroundResource(R.drawable.offbutton1);
                        }

                        // /////////
                        if (edu_blog.equalsIgnoreCase("1")) {
                            edu_blogImage.setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            edu_blogImage.setBackgroundResource(R.drawable.offbutton1);
                        }
                        // /////////
                        if (individual_post_update.equalsIgnoreCase("1")) {
                            individual_PostImage
                                    .setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            individual_PostImage
                                    .setBackgroundResource(R.drawable.offbutton1);
                        }
                        // /////////
                        if (group_post.equalsIgnoreCase("1")) {
                            Posts_For_Groups
                                    .setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            Posts_For_Groups
                                    .setBackgroundResource(R.drawable.offbutton1);
                        }
                        // /////////
                        if (school_post.equalsIgnoreCase("1")) {
                            Posts_For_School
                                    .setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            Posts_For_School
                                    .setBackgroundResource(R.drawable.offbutton1);
                        }
                        // /////////
                        if (comments.equalsIgnoreCase("1")) {
                            commentsImage.setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            commentsImage.setBackgroundResource(R.drawable.offbutton1);
                        }
                        // /////////
                        if (own_comments.equalsIgnoreCase("1")) {
                            Coments_OwnDiscussion
                                    .setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            Coments_OwnDiscussion
                                    .setBackgroundResource(R.drawable.offbutton1);
                        }
                        // /////////
                        if (news.equalsIgnoreCase("1")) {
                            newsImage.setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            newsImage.setBackgroundResource(R.drawable.offbutton1);
                        }
                        // /////////
                        if (news_group_post.equalsIgnoreCase("1")) {
                            Group_Posts.setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            Group_Posts.setBackgroundResource(R.drawable.offbutton1);
                        }
                        // /////////
                        if (news_school_post.equalsIgnoreCase("1")) {
                            SchoolPosts.setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            SchoolPosts.setBackgroundResource(R.drawable.offbutton1);
                        }
                        // /////////
                        if (attendence_notification.equalsIgnoreCase("1")) {
                            Attandance.setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            Attandance.setBackgroundResource(R.drawable.offbutton1);
                        }
                        // /////////
                        if (attendence_individual_school.equalsIgnoreCase("1")) {
                            Individual_In_School
                                    .setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            Individual_In_School
                                    .setBackgroundResource(R.drawable.offbutton1);
                        }
                        // /////////
                        if (attendence_individual_group.equalsIgnoreCase("1")) {
                            IndividualInCommonGroups
                                    .setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            IndividualInCommonGroups
                                    .setBackgroundResource(R.drawable.offbutton1);
                        }
                        // /////////
                        if (absence_note.equalsIgnoreCase("1")) {
                            absence_NoteImage.setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            absence_NoteImage.setBackgroundResource(R.drawable.offbutton1);
                        }
                        // /////////
                        if (retriever_note.equalsIgnoreCase("1")) {
                            RetrievalNote.setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            RetrievalNote.setBackgroundResource(R.drawable.offbutton1);
                        }
                        // /////////
                        if (language.equalsIgnoreCase("1")) {
                            languageImage.setBackgroundResource(R.drawable.swtoggle);
                        } else {
                            languageImage.setBackgroundResource(R.drawable.entoggel);
                        }
                        if (forum.equalsIgnoreCase("1")) {
                            forum_button.setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            forum_button.setBackgroundResource(R.drawable.offbutton1);
                        }
                        if (forum_school_post.equalsIgnoreCase("1")) {
                            iv_whole_school.setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            iv_whole_school.setBackgroundResource(R.drawable.offbutton1);
                        }
                        if (forum_group_post.equalsIgnoreCase("1")) {
                            iv_forum_group.setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            iv_forum_group.setBackgroundResource(R.drawable.offbutton1);
                        }
                        if (forum_course_post.equalsIgnoreCase("1")) {
                            iv_course.setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            iv_course.setBackgroundResource(R.drawable.offbutton1);
                        }
                        if (forum_comment_post.equalsIgnoreCase("1")) {
                            iv_comments_post.setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            iv_comments_post.setBackgroundResource(R.drawable.offbutton1);
                        }
                        if (evalution.equalsIgnoreCase("1")) {
                            iv_grade.setBackgroundResource(R.drawable.onbutton1);
                        } else {
                            iv_grade.setBackgroundResource(R.drawable.offbutton1);
                        }
//                        if (forum_only_mypost.equalsIgnoreCase("1")) {
//                            forum_mypost_button.setBackgroundResource(R.drawable.onbutton1);
//                        } else {
//                            forum_mypost_button.setBackgroundResource(R.drawable.offbutton1);
//                        }


                    } else {
                        // Log.i("now", "language");
                        FragmentManager fragmentManager = getFragmentManager();
                        Setting rFragment = new Setting();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, rFragment);
                        ft.commit();
                        langu = 0;
                    }


                    // /////////

                } else {

                    // failed to creat

                    try {


                        String msg = jsonObjresponse.getString("message");
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
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    class GetWebVersionSettings extends AsyncTask<String, String, String> {


        String url = Base_url + "lms_api/users/webgui_get_setting";
        private MyCustomProgressDialog dialog;
        String Security = "H67jdS7wwfh";
        JSONObject jsonObjresponse = new JSONObject();
        String urlParams = "";

        private static final String TAG_retreivalList = "Retrieval_List";
        private static final String TAG_news = "News";
        private static final String TAG_edublog = "Edu_Blog";
        private static final String TAG_schedule = "Schedule";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            try {
                dialog = new MyCustomProgressDialog(getActivity());
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            String response = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8");


                response = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        protected void onPostExecute(String results) {
            super.onPreExecute();
            dialog.dismiss();
            Log.d("LMS","GetWebVersionSettings---> "+results);

            try {
                JSONObject jsonObj = new JSONObject(results);

                String Status = jsonObj.getString("status");
                String AppSettings = jsonObj.getString("App_Settings");

                JSONObject jsonObject = new JSONObject(AppSettings);
                wv_edublog = jsonObject.getString(TAG_edublog);
                wv_schedule = jsonObject.getString(TAG_schedule);
                wv_news = jsonObject.getString(TAG_news);
                wv_retrievallist = jsonObject.getString(TAG_retreivalList);

                Log.d("LMS"," Status -- >" + Status +" edublog --> "+ wv_edublog +" schedule --> "+ wv_schedule+" news --> "+ wv_news+" attendance --> "+ wv_retrievallist );


                if(wv_edublog.equalsIgnoreCase("1")){
                    iv_edublog.setBackgroundResource(R.drawable.onbutton1);
                }else{
                    iv_edublog.setBackgroundResource(R.drawable.offbutton1);
                }
                if(wv_schedule.equalsIgnoreCase("1")){
                    iv_schedule.setBackgroundResource(R.drawable.onbutton1);
                }else{
                    iv_schedule.setBackgroundResource(R.drawable.offbutton1);
                }
                if(wv_news.equalsIgnoreCase("1")){
                    iv_news.setBackgroundResource(R.drawable.onbutton1);
                }else{
                    iv_news.setBackgroundResource(R.drawable.offbutton1);
                }
                if(wv_retrievallist.equalsIgnoreCase("1")){
                    iv_retrievallist.setBackgroundResource(R.drawable.onbutton1);
                }else{
                    iv_retrievallist.setBackgroundResource(R.drawable.offbutton1);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }

    private class UpdateWebVersionSettings extends AsyncTask<String, String, String> {


        String url = Base_url + "lms_api/users/webgui_Update_setting";
        private MyCustomProgressDialog dialog;
        String Security = "H67jdS7wwfh";
        String urlParams = "";

        private static final String TAG_retreivalList = "Retrieval_List";
        private static final String TAG_news = "News";
        private static final String TAG_edublog = "Edu_Blog";
        private static final String TAG_schedule = "Schedule";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            try {
                dialog = new MyCustomProgressDialog(getActivity());
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            String response = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8");

                if (called_by.equalsIgnoreCase("attendance")) {
                    urlParams += "&" + Const.Params.ViewType + "=" + URLEncoder.encode("retrieval_list", "UTF-8");
                    urlParams += "&" + Const.Params.ViewStatus + "=" + URLEncoder.encode(wv_retrievallist, "UTF-8");
                }
                if (called_by.equalsIgnoreCase("schedule")) {
                    urlParams += "&" + Const.Params.ViewType + "=" + URLEncoder.encode("schedule", "UTF-8");
                    urlParams += "&" + Const.Params.ViewStatus + "=" + URLEncoder.encode(wv_schedule, "UTF-8");
                }
                if (called_by.equalsIgnoreCase("edublog")) {
                    urlParams += "&" + Const.Params.ViewType + "=" + URLEncoder.encode("edublog", "UTF-8");
                    urlParams += "&" + Const.Params.ViewStatus + "=" + URLEncoder.encode(wv_edublog, "UTF-8");
                }
                if (called_by.equalsIgnoreCase("news")) {
                    urlParams += "&" + Const.Params.ViewType + "=" + URLEncoder.encode("news", "UTF-8");
                    urlParams += "&" + Const.Params.ViewStatus + "=" + URLEncoder.encode(wv_news, "UTF-8");
                }

                response = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        protected void onPostExecute(String results) {
            super.onPreExecute();
            dialog.dismiss();
            Log.d("LMS","UpdateWebVersionSettings response ---> "+results);

            try {
            JSONObject jsonObj = new JSONObject(results);

            String Status = jsonObj.getString("status");
            String AppSettings = jsonObj.getString("App_Settings");

            JSONObject jsonObject = new JSONObject(AppSettings);
            wv_edublog = jsonObject.getString(TAG_edublog);
            wv_schedule = jsonObject.getString(TAG_schedule);
            wv_news = jsonObject.getString(TAG_news);
            wv_retrievallist = jsonObject.getString(TAG_retreivalList);

            Log.d("LMS"," Status -- >" + Status +" edublog --> "+ wv_edublog +" schedule --> "+ wv_schedule+" news --> "+ wv_news+" attendance --> "+ wv_retrievallist );


            if(wv_edublog.equalsIgnoreCase("1")){
                iv_edublog.setBackgroundResource(R.drawable.onbutton1);
            }else{
                iv_edublog.setBackgroundResource(R.drawable.offbutton1);
            }
            if(wv_schedule.equalsIgnoreCase("1")){
                iv_schedule.setBackgroundResource(R.drawable.onbutton1);
            }else{
                iv_schedule.setBackgroundResource(R.drawable.offbutton1);
            }
            if(wv_news.equalsIgnoreCase("1")){
                iv_news.setBackgroundResource(R.drawable.onbutton1);
            }else{
                iv_news.setBackgroundResource(R.drawable.offbutton1);
            }
            if(wv_retrievallist.equalsIgnoreCase("1")){
                iv_retrievallist.setBackgroundResource(R.drawable.onbutton1);
            }else{
                iv_retrievallist.setBackgroundResource(R.drawable.offbutton1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        }


    }

}