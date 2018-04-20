package com.pnf.elar.app;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/*
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pnf.elar.app.Bo.CurriculamEduPost;
import com.pnf.elar.app.Bo.CurriculamMainTag;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


@SuppressWarnings("deprecation")
public class Curriculum_tags extends AppCompatActivity {
    String part1;
    ArrayList<String> cur_ids = new ArrayList<String>();
    LinearLayout actionbar;
    ViewGroup actionBarLayout;
    ListView curriculmtG_listone;
    UserSessionManager session;
    String lang, auth_token, Base_url;
    ImageView img, img2, refresh, serhc;
    TextView MYAccount, txt2;


    HashSet selectCurTagAdd = new HashSet();

    List<String> currriculamTag = new ArrayList<>();
    List<String> tagChildList = new ArrayList<>();
    List<String> tagSubChildList = new ArrayList<>();

    Dialog tagDialog;

    int i = 0, j, k;


    LinearLayout editTagLinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum_tags);
        initView();
        session = new UserSessionManager(getApplicationContext());
        // ////////////////////////////////////////////////////
        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        auth_token = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);

        // ////////////////////////////////////////////////////////////////////////////

        actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.actionbar, null);
        actionbar = (LinearLayout) findViewById(R.id.actionbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);

        final int actionBarColor = getResources().getColor(R.color.action_bar);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(actionBarColor));

        img = (ImageView) findViewById(R.id.img);
        img2 = (ImageView) findViewById(R.id.img2);
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
        img2.setVisibility(View.INVISIBLE);
//        img2.setBackgroundColor(Color.parseColor("#FFFFFF"));

        img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*session.cur_tag_ids(part1);*/
                finish();
            }
        });

        curriculmtG_listone = (ListView) findViewById(R.id.curriculmtG_listone);

        try {
            new curriculmtag().execute();
        } catch (Exception e) {
            // TODO: handle exception
        }
        Log.d("tags", "curriculam in edit");
    }

    public void initView()
    {
        editTagLinear=(LinearLayout)findViewById(R.id.editTagLinear);
    }
    // ////////////////////////////////////////////////////////////////////////////////////////////

    public class curriculmtag extends AsyncTask<String, String, String> {
        ArrayList<String[]> list1 = new ArrayList<String[]>();
        ArrayList<String[]> list222 = new ArrayList<String[]>();
        ArrayList<String[]> list2223 = new ArrayList<String[]>();
        Map<String, ArrayList<String[]>> map = new HashMap<String, ArrayList<String[]>>();
        Map<String, ArrayList<String[]>> map1 = new HashMap<String, ArrayList<String[]>>();

        List<CurriculamMainTag> curMainList = new ArrayList<>();


        /*JSONParser jsonParser = new JSONParser();*/
        JSONObject getCurTagJson;

        String[] curriclm_tg_id;
        String[] curriclm_tg_title;
        String[] curriclm_tg_chld_id;
        String status = "";
        private MyCustomProgressDialog dialog;
        private String url_create_product = Base_url
                + "lms_api/picture_diary/get_curriculum_tags";

        private static final String TAG_staus = "status";
        private static final String TAG_curriculum_tags = "curriculum_tags";
        private static final String TAG_curriculum_tags_id = "id";
        private static final String TAG_curriculum_tags_title = "title";
        private static final String TAG_curriculum_tags_children = "children";
        private static final String TAG_children_title = "title";
        private static final String TAG_children_id = "id";
        private static final String TAG_children_subchildren = "subchildren";
        private static final String TAG_children_subchildren_id = "id";
        private static final String TAG_children_subchildren_title = "title";
        private static final String TAG_children_subchildren_parent_id = "parent_id";
        private static final String TAG_children_subchildren_root_parent_id = "root_parent_id";

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new MyCustomProgressDialog(Curriculum_tags.this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            String getCrTagResponse = "";
            try {

                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode("H67jdS7wwfh", "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8");


                getCrTagResponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }

           /* // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("securityKey", "H67jdS7wwfh"));
            params.add(new BasicNameValuePair("authentication_token",
                    auth_token));
            params.add(new BasicNameValuePair("language", lang));

            getCurTagJson = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check for success tag
            Log.i("json data......", getCurTagJson.toString());*/


            return getCrTagResponse;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();

            try {



                try {


                    if (results != null && results.trim().length()>0) {

                        JSONObject tagJson = new JSONObject(results);
                        if(tagJson.has(TAG_staus)) {
                            status = tagJson.getString(TAG_staus);
                        }


                        Log.e("=-=-=-=-=-=-", status);

                        if (status.equalsIgnoreCase("true")) {
                            JSONArray curriclm_tg = tagJson.optJSONArray(TAG_curriculum_tags);

                     /*   curriclm_tg_id = new String[curriclm_tg.length()];
                        curriclm_tg_title = new String[curriclm_tg.length()];*/
                            GsonBuilder gsonBUilder = new GsonBuilder();
                            Gson gson = gsonBUilder.create();

                            if (curriclm_tg.length() > 0) {

                                curMainList = Arrays.asList(gson.fromJson(tagJson.optJSONArray(TAG_curriculum_tags).toString(), CurriculamMainTag[].class));
                            }

                            setValueswithview();
                        }else {
                            try {


                                String msg = tagJson.getString("message");
                                System.out.print(msg);

                                if (lang.equalsIgnoreCase("sw")) {
                                    System.out.print("Sw_l");
                                    if (msg.equalsIgnoreCase("Autentisering misslyckades")) {
                                        Button btnLogout;
                                        TextView tvMessage;
                                        final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", Curriculum_tags.this);
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
                                        final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert",Curriculum_tags.this);
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


                    } else {

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }




              /*  if (getCurTagJson != null) {
                    status = getCurTagJson.getString(TAG_staus);

                }

                if (status.equalsIgnoreCase("true")) {


                    try {

                        Log.e("=-=-=-=-=-=-", status);

                        JSONArray curriclm_tg = getCurTagJson.optJSONArray(TAG_curriculum_tags);
                        curriclm_tg_id = new String[curriclm_tg.length()];
                        curriclm_tg_title = new String[curriclm_tg.length()];

                        GsonBuilder gsonBUilder = new GsonBuilder();
                        Gson gson = gsonBUilder.create();

                        if (curriclm_tg.length() > 0) {

                            curMainList = Arrays.asList(gson.fromJson(getCurTagJson.optJSONArray(TAG_curriculum_tags).toString(), CurriculamMainTag[].class));
                        }

                        curriclm_tg_id = new String[curriclm_tg.length()];
                        curriclm_tg_title = new String[curriclm_tg.length()];

                        for (int j = 0; j < curMainList.size(); j++) {
                            JSONObject c = curriclm_tg.getJSONObject(j);

                            curriclm_tg_id[j] = curMainList.get(j).getId();
                            curriclm_tg_title[j] = curMainList.get(j).getTitle();


                            JSONArray curriclm_tg_chldrn = c
                                    .optJSONArray(TAG_curriculum_tags_children);
                            String[] children_id = new String[curriclm_tg_chldrn
                                    .length()]; // //////////////generate_error////////////
                            String[] childrn_title = new String[curriclm_tg_chldrn
                                    .length()];


                            for (int l = 0; l < curMainList.get(j).getChildren().size(); l++) {
                                JSONObject m = curriclm_tg_chldrn.getJSONObject(l);

                                children_id[l] = curMainList.get(j).getChildren().get(l).getId(); // //////////////generate_error////////////
                                childrn_title[l] = curMainList.get(j).getChildren().get(l).getTitle();

                                JSONArray sub_chil = m
                                        .getJSONArray(TAG_children_subchildren);
                                // Log.i("lenght.......",
                                // Integer.toString(sub_chil.length()));
                                String[] Sub_child_id = new String[sub_chil.length()];
                                String[] Sub_child_title = new String[sub_chil.length()];
                                String[] Sub_child_parent_id = new String[sub_chil
                                        .length()];
                                String[] Sub_child_root_parent_id = new String[sub_chil
                                        .length()];
                                String[] ids = new String[sub_chil.length()];

                                for (int k = 0; k < sub_chil.length(); k++) {

                                    JSONObject n = sub_chil.getJSONObject(k);

                                    Sub_child_id[k] = curMainList.get(j).getChildren().get(l).getSubchildren().get(k).getId();
                                    curMainList.get(j).getChildren().get(l).getSubchildren().get(k).setSelected(false);


                                    Iterator iterator = Publish.curTagsSetPublish.iterator();

                                    // check values
                                    while (iterator.hasNext()) {

                                        String curTagId = curMainList.get(j).getChildren().get(l).getSubchildren().get(k).getId();
                                        if ((iterator.next() + "").equalsIgnoreCase(curTagId)) {
                                            curMainList.get(j).getChildren().get(l).getSubchildren().get(k).setSelected(true);
                                            selectCurTagAdd.add(curTagId);


                                            String parentId = curMainList.get(j).getChildren().get(l).getSubchildren().get(k).getParent_id();
                                            String roootParentId = curMainList.get(j).getChildren().get(l).getSubchildren().get(k).getRoot_parent_id();
                                            String subChiildID = curMainList.get(j).getChildren().get(l).getSubchildren().get(k).getId();


                                            currriculamTag.add(roootParentId);
                                            tagChildList.add(parentId);
                                            tagSubChildList.add(subChiildID);

                                        }

                                    }


                                    Sub_child_title[k] = curMainList.get(j).getChildren().get(l).getSubchildren().get(k).getId();
                                    Sub_child_parent_id[k] = curMainList.get(j).getChildren().get(l).getSubchildren().get(k).getParent_id();
                                    Sub_child_root_parent_id[k] = curMainList.get(j).getChildren().get(l).getSubchildren().get(k).getRoot_parent_id();
                                    ids[k] = Sub_child_id[k] + ","
                                            + Sub_child_parent_id[k] + ","
                                            + Sub_child_root_parent_id[k] + "="
                                            + Sub_child_title[k];

                                }
                                // Log.i("sub child...",
                                // Arrays.deepToString(Sub_child_title));

                                list222.add(ids);
                                list2223.add(ids);

                            }
                            list1.add(childrn_title);
                            map.put(Integer.toString(j), list222);
                            map1.put(Integer.toString(j), list2223);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    CustomAdptercurculm_listone adapter = new CustomAdptercurculm_listone(
                            Curriculum_tags.this, curMainList, list1, map);
                    curriculmtG_listone.setAdapter(adapter);
                    // Helper.getListViewSize(curriculmtG_listone);///////////wrking//////////////
                } else {

                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        public void setValueswithview() {


            for (i = 0; i < curMainList.size(); i++) {

                LayoutInflater inflater = null;
                inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mLinearView = inflater.inflate(R.layout.curlm_main_tag, null);

                final TextView tagRootTitle = (TextView) mLinearView.findViewById(R.id.tagRootTitle);

                final LinearLayout mLinearScrollSecond = (LinearLayout) mLinearView.findViewById(R.id.linearChild);


                mLinearScrollSecond.setVisibility(View.VISIBLE);


                final String mainTagTitle = curMainList.get(i).getTitle();
                final String mainTagDescrip = curMainList.get(i).getDescription();
                tagRootTitle.setText(mainTagTitle);

                tagRootTitle.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent tagDetailsIntent = new Intent(Curriculum_tags.this, CurTagDetailsActivity.class);

                        tagDetailsIntent.putExtra("tagTitle", mainTagTitle);
                        tagDetailsIntent.putExtra("tagDescrp", mainTagDescrip);
                        startActivity(tagDetailsIntent);

                        overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
                    }
                });


                for (j = 0; j < curMainList.get(i).getChildren().size(); j++) {

                    LayoutInflater inflater2 = null;
                    inflater2 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View mLinearView2 = inflater2.inflate(R.layout.curlm_child1_tag, null);

                    TextView tagChildTitle = (TextView) mLinearView2.findViewById(R.id.tagChildTitle);

                    final LinearLayout mLinearScrollThird = (LinearLayout) mLinearView2.findViewById(R.id.linearSubChild);

                    mLinearScrollThird.setVisibility(View.VISIBLE);


                    final String tag1Title = curMainList.get(i).getChildren().get(j).getTitle();

                    final String tag1Descrip = curMainList.get(i).getChildren().get(j).getDescription();
                    tagChildTitle.setText(tag1Title);


                    tagChildTitle.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent tagDetailsIntent = new Intent(Curriculum_tags.this, CurTagDetailsActivity.class);

                            tagDetailsIntent.putExtra("tagTitle", tag1Title);
                            tagDetailsIntent.putExtra("tagDescrp", tag1Descrip);
                            startActivity(tagDetailsIntent);
                            overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
                        }
                    });


                    for (k = 0; k < curMainList.get(i).getChildren().get(j).getSubchildren().size(); k++) {

                        LayoutInflater inflater3 = null;
                        inflater3 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View mLinearView3 = inflater3.inflate(R.layout.curlm_subchild_tag, null);

                        TextView tagSubChildTitle = (TextView) mLinearView3.findViewById(R.id.tagSubChildTitle);

                        final CheckBox tagSubChildCheckBox = (CheckBox) mLinearView3.findViewById(R.id.tagSubChildCheckBox);
                       /* TextView mItemPrice = (TextView) mLinearView3.findViewById(R.id.textViewItemPrice);*/
                        final String itemName = curMainList.get(i).getChildren().get(j).getSubchildren().get(k).getTitle();
                        final String itemPrice = curMainList.get(i).getChildren().get(j).getSubchildren().get(k).getTitle();
                        tagSubChildTitle.setText(itemName);


                        curMainList.get(i).getChildren().get(j).getSubchildren().get(k).setSelected(false);


                        Iterator iterator = Publish.curTagsSetPublish.iterator();

                        // check values
                        while (iterator.hasNext()) {

                            String curTagId = curMainList.get(i).getChildren().get(j).getSubchildren().get(k).getId();
                            if ((iterator.next() + "").equalsIgnoreCase(curTagId)) {
                                curMainList.get(i).getChildren().get(j).getSubchildren().get(k).setSelected(true);
                                selectCurTagAdd.add(curTagId);


                                String parentId = curMainList.get(i).getChildren().get(j).getSubchildren().get(k).getParent_id();
                                String roootParentId = curMainList.get(i).getChildren().get(j).getSubchildren().get(k).getRoot_parent_id();
                                String subChiildID = curMainList.get(i).getChildren().get(j).getSubchildren().get(k).getId();


                                currriculamTag.add(roootParentId);
                                tagChildList.add(parentId);
                                tagSubChildList.add(subChiildID);

                            }

                        }




                        if (curMainList.get(i).getChildren().get(j).getSubchildren().get(k).isSelected()) {

                            tagSubChildCheckBox.setChecked(true);

                        } else {
                            tagSubChildCheckBox.setChecked(false);
                        }


                        final CurriculamEduPost.SubchildrenEntity subchildrenEntity = curMainList.get(i).getChildren().get(j).getSubchildren().get(k);

                        tagSubChildTitle.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                if (tagSubChildCheckBox.isChecked()) {


                                    tagSubChildCheckBox.setChecked(false);
                                    selectCurTagAdd.remove(subchildrenEntity.getId());
                                    removeTag(tagSubChildList.indexOf(subchildrenEntity.getId()));
                                } else {

                                    tagSubChildCheckBox.setChecked(true);
                                    selectCurTagAdd.add(subchildrenEntity.getId());

                                    addTag(subchildrenEntity);


                                }
                            }
                        });

                        tagSubChildCheckBox.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                if (tagSubChildCheckBox.isChecked()) {

                                    addTag(subchildrenEntity);
                                    selectCurTagAdd.add(subchildrenEntity.getId());


                                } else {

                                    selectCurTagAdd.remove(subchildrenEntity.getId());

                                    removeTag(tagSubChildList.indexOf(subchildrenEntity.getId()));

                                }
                            }
                        });
                        mLinearScrollThird.addView(mLinearView3);
                    }

                    mLinearScrollSecond.addView(mLinearView2);

                }

                editTagLinear.addView(mLinearView);
            }
        }


        public void addTag(CurriculamEduPost.SubchildrenEntity subChildrenObj) {
            String parentId = subChildrenObj.getParent_id();
            String roootParentId = subChildrenObj.getRoot_parent_id();
            String subChiildID = subChildrenObj.getId();


            currriculamTag.add(roootParentId);
            tagChildList.add(parentId);
            tagSubChildList.add(subChiildID);
        }

        public void removeTag(int removePos) {
                    /*String parentId=curMainList.get(j).getChildren().get(l).getSubchildren().get(k).getParent_id();
                    String roootParentId=curMainList.get(j).getChildren().get(l).getSubchildren().get(k).getRoot_parent_id();
                    String subChiildID=curMainList.get(j).getChildren().get(l).getSubchildren().get(k).getId();*/


            currriculamTag.remove(removePos);
            tagChildList.remove(removePos);
            tagSubChildList.remove(removePos);
        }


          }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.curriculum_tags, menu);
        MenuItem doneMenuItem = menu.findItem(R.id.doneCurTag);

        if(lang.equalsIgnoreCase("en"))
        {
            doneMenuItem.setTitle("DONE");
        }
        else
        {
            doneMenuItem.setTitle("KLART");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.doneCurTag) {
            Publish.curTagsSetPublish.clear();
            Publish.curTagsSetPublish.addAll(selectCurTagAdd);
            saveCurTagString();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void saveCurTagString() {
        List<String> tagIds = new ArrayList<>();
        if (Publish.curTagsSetPublish.size() > 0) {


            for (int i = 0; i < tagSubChildList.size(); i++) {
                tagIds.add(currriculamTag.get(i));
                tagIds.add(tagChildList.get(i));
                tagIds.add(tagSubChildList.get(i));

            }


            System.out.println("reqJson----" + tagIds.toString() );

        } else {


        }
        String json = new Gson().toJson(tagIds);
        session.cur_tag_ids(tagIds.toString());


    }
  /*  // /////////
    // /////////

    public class CustomAdptercurculm_listone extends ArrayAdapter<CurriculamMainTag> {

        ListView list2;
        private final Activity context;
        *//**//*private final String[] web;*//**//*
        private final ArrayList<String[]> listone;
        Map<String, ArrayList<String[]>> map1;
        // Map<String, ArrayList<String[]>> map123;

        private LayoutInflater inflater = null;
        public ImageLoadernew imageLoader;
        private final List<CurriculamMainTag> curMainTagList;
        // LayoutInflater inflater;
        // static ImageView imageView;

        public CustomAdptercurculm_listone(Curriculum_tags curriculum_tags,
                                           List<CurriculamMainTag> curMainTagList, ArrayList<String[]> listone,
                                           Map<String, ArrayList<String[]>> map) {
            super(curriculum_tags, R.layout.curlm_main_tag, curMainTagList);
            this.context = curriculum_tags;
            this.curMainTagList = curMainTagList;
            this.listone = listone;
            this.map1 = map;
            // this.map123=map12;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {

            *//**//*Log.i("thummmmmm.....", Arrays.deepToString(web));*//**//*
            LayoutInflater inflater = context.getLayoutInflater();

            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.curlm_main_tag, parent,
                    false);

            TextView txtTitle = (TextView) rowView
                    .findViewById(R.id.cur_parent);
            txtTitle.setText(curMainTagList.get(position).getTitle());

            // try {
            // Log.i("chldennnnn...",
            // Arrays.deepToString(listone.get(position)));
            // } catch (Exception e) {
            // // TODO: handle exception
            // }

            // Log.i("hiiiiiiiiii.....","hlooooooooooo");
            // ArrayList<String[]> values = null;


            txtTitle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //showTagDetails(curMainTagList.get(position).getTitle(),curMainTagList.get(position).getDescription());


                    Intent tagDetailsIntent = new Intent(context, CurTagDetailsActivity.class);

                    tagDetailsIntent.putExtra("tagTitle",curMainTagList.get(position).getTitle());
                    tagDetailsIntent.putExtra("tagDescrp",curMainTagList.get(position).getDescription());
                    context.startActivity(tagDetailsIntent);

                    overridePendingTransition(R.anim.slide_down, R.anim.slide_up);

                }
            });

            for (Map.Entry<String, ArrayList<String[]>> entry : map1.entrySet()) {

                // String key = entry.getKey();

                ArrayList<String[]> values = entry.getValue();

                try {
                    list2 = (ListView) rowView.findViewById(R.id.curlm_listtwo);
                    // CustomAdptercurculm_listtwo adapter = new
                    // CustomAdptercurculm_listtwo(getContext() ,
                    // listone.get(position),map1.get(position));
                    CustomAdptercurculm_listtwo adapter = new CustomAdptercurculm_listtwo(
                            getContext(), curMainTagList.get(position).getChildren(), values);

                    list2.setAdapter(adapter);
                    // Helper.getListViewSize(list2);
                    // setListViewHeightBasedOnChildren(list2);
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }

            // Log.i("=========", Arrays.deepToString(values.get(position)));

            return rowView;
        }

        private void setListViewHeightBasedOnChildren(ListView list22) {
            // TODO Auto-generated method stub

            ListAdapter listAdapter = list22.getAdapter();
            if (listAdapter == null)
                return;

            int desiredWidth = MeasureSpec.makeMeasureSpec(list22.getWidth(),
                    MeasureSpec.UNSPECIFIED);
            int totalHeight = 0;
            View view = null;

            for (int i = 0; i < listAdapter.getCount(); i++) {
                view = listAdapter.getView(i, view, list22);

                if (i == 0)
                    view.setLayoutParams(new ViewGroup.LayoutParams(
                            desiredWidth, LayoutParams.MATCH_PARENT));

                view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
                totalHeight += view.getMeasuredHeight();

            }

            ViewGroup.LayoutParams params = list22.getLayoutParams();
            params.height = totalHeight
                    + ((list22.getDividerHeight()) * (listAdapter.getCount()));

            list22.setLayoutParams(params);
            list22.requestLayout();

        }

        // ///////
        // ///////
        // ///////
        public class CustomAdptercurculm_listtwo extends ArrayAdapter<CurriculamEduPost> {
            List<Model> list = new ArrayList<Model>();
            ArrayList<String> checkedValue;
            ListView cu;
            private final Context context;
            *//**//*
                        private final String[] child;
            *//**//*
            private final ArrayList<String[]> sub1;
            int pn;

            private LayoutInflater inflater = null;


            private List<CurriculamEduPost> curChildrenList;
            public ImageLoadernew imageLoader;

            // LayoutInflater inflater;
            // static ImageView imageView;

            public CustomAdptercurculm_listtwo(Context curriculum_tags,
                                               List<CurriculamEduPost> curChildrenList, ArrayList<String[]> sub) {
                super(curriculum_tags, R.layout.curlm_child1_tag,
                        curChildrenList);
                this.context = curriculum_tags;
                this.curChildrenList = curChildrenList;
                this.sub1 = sub;
            }

            @Override
            public View getView(final int position, View view, ViewGroup parent) {

                this.pn = position;

                // Log.i("thummmmmm.....", imageId[0]);
                LayoutInflater inflater = ((Activity) context)
                        .getLayoutInflater();

                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View rowView = inflater.inflate(R.layout.curlm_child1_tag, parent,
                        false);

                cu = (ListView) rowView.findViewById(R.id.curriculm_list_three);
                TextView txtTitle = (TextView) rowView
                        .findViewById(R.id.cur_list_three);
                txtTitle.setText(curChildrenList.get(position).getTitle());
                txtTitle.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //showTagDetails(curChildrenList.get(position).getTitle(),curChildrenList.get(position).getDescription());
                        Intent tagDetailsIntent = new Intent(context, CurTagDetailsActivity.class);

                        tagDetailsIntent.putExtra("tagTitle",curChildrenList.get(position).getTitle());
                        tagDetailsIntent.putExtra("tagDescrp",curChildrenList.get(position).getDescription());
                        context.startActivity(tagDetailsIntent);

                        Activity activity=(Activity)context;
                        activity.overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
                    }
                });
                // ///////////////////////////////////////////////////////

                // CustomAdptercurculm_listthree adapter = new
                // CustomAdptercurculm_listthree(getContext() ,
                // sub1.get(position));

                CustomAdptercurculm_listthree adapter = new CustomAdptercurculm_listthree(
                        getContext(), curChildrenList.get(position).getSubchildren());
                cu.setAdapter(adapter);
                // cu.setOnItemClickListener(new CheckBoxClick());
                setListViewHeightBasedOnChildrentwo(cu);

                cu.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        // TODO Auto-generated method stub

                        TextView label = (TextView) view.getTag(R.id.label);

                        // Toast.makeText(view.getContext(),
                        // label.getText().toString()+" "+isCheckedOrNot(checkbox),
                        // Toast.LENGTH_LONG).show();

                    }
                });

                return rowView;
            }

            private void setListViewHeightBasedOnChildrentwo(ListView cu2) {
                // TODO Auto-generated method stub

                ListAdapter listAdapter = cu2.getAdapter();
                if (listAdapter == null)
                    return;

                int desiredWidth = MeasureSpec.makeMeasureSpec(cu2.getWidth(),
                        MeasureSpec.UNSPECIFIED);
                int totalHeight = 0;
                View view = null;

                for (int i = 0; i < listAdapter.getCount(); i++) {
                    view = listAdapter.getView(i, view, cu2);

                    if (i == 0)
                        view.setLayoutParams(new ViewGroup.LayoutParams(
                                desiredWidth, LayoutParams.MATCH_PARENT));

                    view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
                    totalHeight += view.getMeasuredHeight();

                }

                ViewGroup.LayoutParams params = cu2.getLayoutParams();
                params.height = totalHeight
                        + ((cu2.getDividerHeight()) * (listAdapter.getCount()));

                cu2.setLayoutParams(params);
                cu2.requestLayout();

            }

            class CustomAdptercurculm_listthree extends ArrayAdapter<CurriculamEduPost.SubchildrenEntity> {
                CheckBox chk;
                private final Context context;
                *//**//*private final String[] child;*//**//*
                private String[] index;
                private LayoutInflater inflater = null;
                public ImageLoadernew imageLoader;
                private final List<CurriculamEduPost.SubchildrenEntity> curSubChildrenList;
                // LayoutInflater inflater;
                // static ImageView imageView;

                public CustomAdptercurculm_listthree(Context context2,
                                                     List<CurriculamEduPost.SubchildrenEntity> curSubChildrenList) {
                    // TODO Auto-generated constructor stub
                    super(context2, R.layout.curlm_subchild_tag, curSubChildrenList);
                    this.context = context2;
                    this.curSubChildrenList = curSubChildrenList;
                  *//**//*  this.index = new String[child.length];
                    for (int i = 0; i < child.length; i++) {
                        index[i] = "0";
                    }*//**//*
                }

                @Override
                public View getView(final int position, View view,
                                    ViewGroup parent) {

                    // Log.i("child[position]....", child[position]);
                    LayoutInflater inflater = ((Activity) context)
                            .getLayoutInflater();

                    inflater = (LayoutInflater) context
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View rowView = inflater.inflate(R.layout.curlm_subchild_tag,
                            parent, false);

                    chk = (CheckBox) rowView.findViewById(R.id.chk);
                    TextView txtTitle = (TextView) rowView
                            .findViewById(R.id.label);

                    txtTitle.setText(curSubChildrenList.get(position).getTitle());

*//**//*
                    if (!(index[position].equalsIgnoreCase("0"))) {

                        chk.setBackgroundResource(R.drawable.nonclick);
                        if (index[position].equalsIgnoreCase("1")) {
                            chk.setBackgroundResource(R.drawable.click);
                            // imageicon=1;
                        }

                    }*//**//*

                    if (curSubChildrenList.get(position).isSelected()) {
                        chk.setChecked(true);
                    } else {
                        chk.setChecked(false);

                    }
                    txtTitle.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Log.e("check", curSubChildrenList.get(position).isSelected() + "");

                            if (curSubChildrenList.get(position).isSelected()) {
                                curSubChildrenList.get(position).setSelected(false);
                                selectCurTagAdd.remove(curSubChildrenList.get(position).getId());
                                removeTag(tagSubChildList.indexOf(curSubChildrenList.get(position).getId()));

                            } else {
                                curSubChildrenList.get(position).setSelected(true);
                                selectCurTagAdd.add(curSubChildrenList.get(position).getId());
                                addTag(position);

                            }
                            notifyDataSetChanged();
                        }
                    });

                    chk.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Log.e("check 1", curSubChildrenList.get(position).isSelected() + "");

                            if (curSubChildrenList.get(position).isSelected()) {
                                curSubChildrenList.get(position).setSelected(false);
                                selectCurTagAdd.remove(curSubChildrenList.get(position).getId());
                                removeTag(tagSubChildList.indexOf(curSubChildrenList.get(position).getId()));


                            } else {
                                curSubChildrenList.get(position).setSelected(true);
                                selectCurTagAdd.add(curSubChildrenList.get(position).getId());
                                addTag(position);


                            }
                            notifyDataSetChanged();
                            // TODO Auto-generated method stub

                            // Log.i("index[position]",
                            // Arrays.deepToString(index));

							*//**//*if (index[position].equalsIgnoreCase("0")) {
                                // Toast.makeText(getContext(), child[position],
								// Toast.LENGTH_SHORT).show();
								index[position] = "1";

							*//**//**//**//*	String[] parts = child[position].split("=");
								part1 = part1 + "," + parts[0];*//**//**//**//*

								cur_ids.add(part1);

								notifyDataSetChanged();
							} else {
								index[position] = "0";
							//	notifyDataSetChanged();
							}*//**//*

                        }
                    });


                    return rowView;
                }

                public void addTag(int addPos) {
                    String parentId = curSubChildrenList.get(addPos).getParent_id();
                    String roootParentId = curSubChildrenList.get(addPos).getRoot_parent_id();
                    String subChiildID = curSubChildrenList.get(addPos).getId();


                    currriculamTag.add(roootParentId);
                    tagChildList.add(parentId);
                    tagSubChildList.add(subChiildID);
                }

                public void removeTag(int removePos) {


                    currriculamTag.remove(removePos);
                    tagChildList.remove(removePos);
                    tagSubChildList.remove(removePos);
                }

            }
        }*//*
    }*/

   /* public void showTagDetails(String dgTitle, String dgDescrp) {
        tagDialog = new Dialog(Curriculum_tags.this);
        tagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        tagDialog.setContentView(R.layout.dialog_curriculamtag);
        tagDialog.setCancelable(true);
        tagDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;


        ImageView closeTagImage = (ImageView) tagDialog.findViewById(R.id.closeTagImage);
        TextView tagHeaderText = (TextView) tagDialog.findViewById(R.id.tagHeaderText);
        final TextView tagDescrpText = (TextView) tagDialog.findViewById(R.id.tagDescrpText);


        tagHeaderText.setText(dgTitle);
        tagDescrpText.setText(dgDescrp);


        closeTagImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tagDialog.dismiss();
            }
        });

        tagDialog.show();



    }*/
}
