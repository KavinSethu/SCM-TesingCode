package com.pnf.elar.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.pnf.elar.app.Bo.CurriculamMainTag;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class Curriculam_tag_post extends AppCompatActivity {

    ArrayList<String[]> curriculum_post_title;
    String[] string;
    Bundle list124;
    Context con;
    Map<String, ArrayList<String[]>> map;
    ViewGroup actionBarLayout;
    LinearLayout actionbar;
    ListView curriculmtG_listone;
    ImageView back, img, img2, refresh, serhc;
    TextView backalso, MYAccount, txt2;
    UserSessionManager session;
    String lang, auth_token, Base_url;


    List<CurriculamMainTag> tagPostList;

    int i = 0, j, k;

    LinearLayout editTagLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum_tags);

        initView();
        /////////////////////////////////////////////////
        session = new UserSessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        auth_token = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        /////////////////////////////////////////////////////////////
        actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.actionbar, null);
        actionbar = (LinearLayout) findViewById(R.id.actionbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);

        final int actionBarColor = getResources().getColor(R.color.action_bar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(actionBarColor));

        img = (ImageView) findViewById(R.id.img);
        img2 = (ImageView) findViewById(R.id.img2);
        img2.setVisibility(View.INVISIBLE);
        refresh = (ImageView) findViewById(R.id.refresh);
        serhc = (ImageView) findViewById(R.id.serhc);
        MYAccount = (TextView) findViewById(R.id.text1);
        txt2 = (TextView) findViewById(R.id.txt2);

        if (lang.equalsIgnoreCase("sw")) {
            txt2.setText("Läroplan Taggar");
        }
        if (lang.equalsIgnoreCase("en")) {
            txt2.setText("Curriculum Tags");
        }
        // /////////////////////////////////////////////
        MYAccount.setVisibility(View.GONE);
        refresh.setVisibility(View.GONE);
        serhc.setVisibility(View.GONE);
//        img2.setBackgroundColor(Color.parseColor("#FFFFFF"));

        img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // ((Drawer) getApplicationContext()).BacktomainActivity();

                finish();
            }
        });

        Intent li = getIntent();
        String[] stringArray = li.getStringArrayExtra("tagsss");


        tagPostList = Arrays.asList(new Gson().fromJson(li.getStringExtra("crTagPost"), CurriculamMainTag[].class));


        /*Log.d("tag_post", li.getStringArrayExtra("tagsss").toString());*/
        Log.i("nwnwnw", Arrays.deepToString(stringArray));

        setValueswithview();




    }




    public void initView() {
        editTagLinear = (LinearLayout) findViewById(R.id.editTagLinear);


    }

    public void setValueswithview() {


        for (i = 0; i < tagPostList.size(); i++) {

            LayoutInflater inflater = null;
            inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View mLinearView = inflater.inflate(R.layout.curlm_main_tag, null);

            final TextView tagRootTitle = (TextView) mLinearView.findViewById(R.id.tagRootTitle);

            final LinearLayout mLinearScrollSecond = (LinearLayout) mLinearView.findViewById(R.id.linearChild);


            mLinearScrollSecond.setVisibility(View.VISIBLE);


            final String mainTagTitle = tagPostList.get(i).getTitle();
            final String mainTagDescrip = tagPostList.get(i).getDescription();
            tagRootTitle.setText(mainTagTitle);

            tagRootTitle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent tagDetailsIntent = new Intent(Curriculam_tag_post.this, CurTagDetailsActivity.class);

                    tagDetailsIntent.putExtra("tagTitle", mainTagTitle);
                    tagDetailsIntent.putExtra("tagDescrp", mainTagDescrip);
                    startActivity(tagDetailsIntent);

                    overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
                }
            });


            for (j = 0; j < tagPostList.get(i).getChildren().size(); j++) {

                LayoutInflater inflater2 = null;
                inflater2 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mLinearView2 = inflater2.inflate(R.layout.curlm_child1_tag, null);

                TextView tagChildTitle = (TextView) mLinearView2.findViewById(R.id.tagChildTitle);

                final LinearLayout mLinearScrollThird = (LinearLayout) mLinearView2.findViewById(R.id.linearSubChild);

                mLinearScrollThird.setVisibility(View.VISIBLE);


                final String tag1Title = tagPostList.get(i).getChildren().get(j).getTitle();

                final String tag1Descrip = tagPostList.get(i).getChildren().get(j).getDescription();
                tagChildTitle.setText(tag1Title);


                tagChildTitle.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent tagDetailsIntent = new Intent(Curriculam_tag_post.this, CurTagDetailsActivity.class);

                        tagDetailsIntent.putExtra("tagTitle", tag1Title);
                        tagDetailsIntent.putExtra("tagDescrp", tag1Descrip);
                        startActivity(tagDetailsIntent);
                        overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
                    }
                });


                for (k = 0; k < tagPostList.get(i).getChildren().get(j).getSubchildren().size(); k++) {

                    LayoutInflater inflater3 = null;
                    inflater3 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View mLinearView3 = inflater3.inflate(R.layout.curlm_subchild_tag, null);

                    TextView tagSubChildTitle = (TextView) mLinearView3.findViewById(R.id.tagSubChildTitle);

                    final CheckBox tagSubChildCheckBox = (CheckBox) mLinearView3.findViewById(R.id.tagSubChildCheckBox);

                    tagSubChildCheckBox.setVisibility(View.GONE);
                       /* TextView mItemPrice = (TextView) mLinearView3.findViewById(R.id.textViewItemPrice);*/
                    final String itemName = tagPostList.get(i).getChildren().get(j).getSubchildren().get(k).getTitle();
                    final String itemPrice = tagPostList.get(i).getChildren().get(j).getSubchildren().get(k).getTitle();
                    tagSubChildTitle.setText(itemName);


                    mLinearScrollThird.addView(mLinearView3);
                }

                mLinearScrollSecond.addView(mLinearView2);

            }

            editTagLinear.addView(mLinearView);
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

    }
}
