package com.pnf.elar.app;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/*import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elar.util.NetworkUtil;
import com.google.gson.Gson;
import com.pnf.elar.app.Bo.CurriculamMainTag;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.viewpagerindicator.CirclePageIndicator;

@SuppressWarnings("deprecation")
public class CustomAdpterPost extends ArrayAdapter<String> {

    String count, lang, auth_token, rn_name;
    static String Base_url, user_typ, user_id;
    ViewHolder holder;
    ViewPager introSlider, introSlidertwo;
    CirclePageIndicator indicator, indicatortwo;
    private final Activity contextA;
    private final String[] catgry;
    private final String[] tec;
    private final String[] post_user_id;
    private final String[] dess;
    private final String[] pst_id;
    String[] curriclm_tg_title;
    UserSessionManager session;

    // private static LayoutInflater inflater = null;
    private final ArrayList<String[]> lis;
    private final ArrayList<String[]> vdio;
    private final ArrayList<String[]> vdio_url;
    private final ArrayList<String[]> std;
    private final ArrayList<String[]> randm_nme;
    private final ArrayList<String[]> randm_id;
    ArrayList<String[]> curriculum_post_title;
    ArrayList<String[]> listgrp;
    ArrayList<String[]> list1;
    public static HashMap<String, ArrayList<String[]>> map;
    TextView random1;

    ImageView curriculm_image, pen, rndm;
    ListView list;
    TextView txtTitle;
    ListView dialog_ListView;
    RelativeLayout vedio_sliders;
    RelativeLayout imageview_slider;
    LinearLayout likess;
    String[] listContent = {"January", "February"};
    String[] TAG_s_name;
    Drawable myDrawable;
    String[] like, alrdy_liked;
    int kk;
    // TextView lik;
    View rowView;
    LayoutInflater inflater;
    // String count;
    String dwnload_file_path;
    int totalSize = 0;
    ProgressBar pb;
    int downloadedSize = 0;
    TextView cur_val;
    int imageicon;
    ImageView immm;

    private ProgressDialog pDialog;
    List<List<CurriculamMainTag>> crTgaList;

    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;

    // File url to download
    private static String file_url = "http://api.androidhive.info/progressdialog/hive.jpg";

    public CustomAdpterPost(Activity context, String[] web,
                            String[] imageIdthumb, String[] post_user_id, String[] vediopostedOn12, String[] post_idc,
                            ArrayList<String[]> lis, ArrayList<String[]> li,
                            ArrayList<String[]> st, ArrayList<String[]> randm, String[] liked,
                            ArrayList<String[]> vdio_urll,
                            ArrayList<String[]> curriculum_post_title1,
                            ArrayList<String[]> list12,
                            HashMap<String, ArrayList<String[]>> map1, String[] already_liked,
                            ArrayList<String[]> randm_id, List<List<CurriculamMainTag>> crTgaList, ArrayList<String[]> listgrp) {
        super(context, R.layout.custompost, web);
        this.contextA = context;
        this.catgry = web;
        this.tec = imageIdthumb;
        this.post_user_id = post_user_id;
        this.dess = vediopostedOn12;
        this.pst_id = post_idc;
        this.lis = lis;
        this.vdio = li;
        this.std = st;
        this.randm_nme = randm;
        this.like = liked;
        this.vdio_url = vdio_urll;
        this.curriculum_post_title = curriculum_post_title1;
        this.list1 = list12;
        this.map = map1;
        this.alrdy_liked = already_liked;
        this.randm_id = randm_id;
        this.crTgaList = crTgaList;
        this.listgrp=listgrp;
        // imageLoader=new ImageLoader(context);............................

    }

    private class ViewHolder {
        ImageView immm;
        TextView lik;
        // TextView txtDesc;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        // imageicon=0;

        holder = new ViewHolder();

        inflater = contextA.getLayoutInflater();

        inflater = (LayoutInflater) contextA
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.custompost, parent, false);
        pen = (ImageView) rowView.findViewById(R.id.pen);

        session = new UserSessionManager(getContext());

        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        //  auth_token = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        user_typ = user.get(UserSessionManager.TAG_user_type);


        if (user_typ.equalsIgnoreCase("Student")) {
            pen.setVisibility(View.GONE);
        }

        // ///////////////////////////////////////////////
        rndm = (ImageView) rowView.findViewById(R.id.rndm);
        holder.immm = (ImageView) rowView.findViewById(R.id.immm);
        holder.lik = (TextView) rowView.findViewById(R.id.likecount);
        // holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
        // holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
        rowView.setTag(holder);
        ////////////////////////////

        user_id = user.get(UserSessionManager.TAG_user_id);
        if (user_typ.equalsIgnoreCase("Parent")) {


            pen.setVisibility(View.GONE);
            auth_token = user
                    .get(UserSessionManager.TAG_Authntication_Children);
            Log.i("parent", "parent");
        } else {

            if (user_id.equalsIgnoreCase(post_user_id[position])) {
                pen.setVisibility(View.VISIBLE);

            } else {
                pen.setVisibility(View.GONE);

            }
            auth_token = user.get(UserSessionManager.TAG_Authntication_token);
            Log.i("auth_token", auth_token);

        }

        // //////////////////////////////////////////////////////////////////////////////////////////////////////
        imageview_slider = (RelativeLayout) rowView
                .findViewById(R.id.imageview_slider);

        vedio_sliders = (RelativeLayout) rowView
                .findViewById(R.id.vedio_sliders);

        // //////////////////////////////////////////////////////////
        holder = (ViewHolder) rowView.getTag();
        // ///////////////////////////////////////////////////////////
        curriculm_image = (ImageView) rowView
                .findViewById(R.id.curriculm_image);
        likess = (LinearLayout) rowView.findViewById(R.id.likess);
        txtTitle = (TextView) rowView.findViewById(R.id.catagry);
        TextView txtTitledate = (TextView) rowView
                .findViewById(R.id.belowfirst);
        random1 = (TextView) rowView.findViewById(R.id.random1);

        TextView des = (TextView) rowView.findViewById(R.id.des);
        // immm = (ImageView) rowView.findViewById(R.id.immm);

        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) contextA.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Message",des.getText().toString());
        clipboard.setPrimaryClip(clip);
        kk = Integer.parseInt(like[position]);
        // //////////////// ///////////////////////////////////

        // //////////////////////////////////////////

        Log.i("cur.........", Arrays.deepToString(curriclm_tg_title));

        if (!(alrdy_liked[position].equalsIgnoreCase("yes")))
        {

            holder.immm.setBackgroundResource(R.drawable.like1);
            if (alrdy_liked[position].equalsIgnoreCase("yes")) {
                holder.immm.setBackgroundResource(R.drawable.like2);
                imageicon = 1;
            }

        }

        if (!(curriculum_post_title.get(position) != null && curriculum_post_title
                .get(position).length > 0)) {

            curriculm_image.setVisibility(View.GONE);
        } else {

            curriculm_image.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent li = new Intent(getContext(),
                            Curriculam_tag_post.class);
                   /* li.putExtra("tagsss", curriculum_post_title.get(position));// /////////////
                    li.putExtra("child_title", list1);
                    li.putExtra("Sub_child_title", map);*/

                    Log.e("new ", crTgaList.get(position).size() + " -- " + new Gson().toJson(crTgaList.get(position)));

                    li.putExtra("crTagPost", new Gson().toJson(crTgaList.get(position)));
                    // rowView.
                    contextA.startActivity(li);
                    /*contextA.finish();*/

                }
            });
        }

        // //////////// Edit_blog button ///////////////////////
        pen.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if (NetworkUtil.getInstance(contextA).isInternet()) {

                        Log.i("pst_id[position]", pst_id[position]);

                        FragmentManager fragmentManager = ((Activity) getContext())
                                .getFragmentManager();
                        Edit_post Setting_frg = new Edit_post();
                        Bundle bundle = new Bundle();
                        bundle.putString("post_id", pst_id[position]);
                        Setting_frg.setArguments(bundle);
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, Setting_frg);
                        ft.commit();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

        // /////////////////////////////////////////////

        holder.immm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new likeclass(pst_id[position]).execute();
                // holder.lik.setText(count);
                int lik = Integer.parseInt(like[position]);

                if (alrdy_liked[position].equalsIgnoreCase("yes")) {
                    int liky = lik - 1;

                    like[position] = Integer.toString(liky);
                    alrdy_liked[position] = "no";
                } else {
                    int liky = 1 + lik;
                    like[position] = Integer.toString(liky);
                    alrdy_liked[position] = "yes";
                }

            }
        });
        //
        // /////////////////////////////////////////////////////////

        holder.lik.setText(like[position]);

        // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        try {
            if (catgry[position].equalsIgnoreCase("student")) {

                if (std.get(position).length > 2) {

                    String[] pp = std.get(position);
                    txtTitle.setText(pp[0] + "# " + pp[1] + "#...");
                } else if (std.get(position).length == 2) {
                    String[] pp = std.get(position);
                    txtTitle.setText(pp[0] + "# " + pp[1] + "#");
                } else  if (std.get(position).length == 1) {
                    String[] pp = std.get(position);

                    txtTitle.setText(pp[0] + "#");


                }


            } else if (catgry[position].equalsIgnoreCase("class")) {
                if (listgrp.get(position).length == 0) {
                    txtTitle.setText(catgry[position]);
                }
                else if(listgrp.get(position).length==1)
                {
                    String[] grp = listgrp.get(position);

                    txtTitle.setText(grp[0] + "# ");

                }
                else if(listgrp.get(position).length==2)
                {
                    String[] grp = listgrp.get(position);

                    txtTitle.setText(grp[0] + "# " + grp[1] + "#");

                }
                else if (listgrp.get(position).length >= 3) {
                    String[] grp = listgrp.get(position);

                    txtTitle.setText(grp[0] + "# " + grp[1] + "# More");


                }




            } else {
                txtTitle.setText(catgry[position]+"#");

               /* if (catgry[position].equalsIgnoreCase("preschool")) {

                    if (lang.equalsIgnoreCase("sw")) {
                        txtTitle.setText("Alla");
                    } else {
                        txtTitle.setText("All");
                    }

                } else {
                    txtTitle.setText(catgry[position]);
                }*/
            }

            txtTitledate.setText(tec[position]);
            des.setText(Html.fromHtml(dess[position]));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        // ///
        // /////////////////////////image Slider////////////////////////////////////////////////////////
        try {

            introSlider = (ViewPager) rowView.findViewById(R.id.intro_pager);
            indicator = (CirclePageIndicator) rowView
                    .findViewById(R.id.indicator);
            if (!(lis.get(position).length == 0)) {

                String img[] = new String[lis.get(position).length];
                img = lis.get(position);

                introSlider.setAdapter(new IntroPageAdapter(img,
                        getContext()));
                introSlider.setCurrentItem(0);
                indicator.setViewPager(introSlider);
            } else {
                imageview_slider.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        // /////////////////////////////////video slider//////////////////////////////////////////////////
        try {
            introSlidertwo = (ViewPager) rowView
                    .findViewById(R.id.intro_pagertwo);
            indicatortwo = (CirclePageIndicator) rowView
                    .findViewById(R.id.indicatortwo);

            if (!(vdio.get(position).length == 0)) {

                introSlidertwo.setAdapter(new IntroPageAdaptertwo(vdio
                        .get(position), vdio_url.get(position), getContext()));
                introSlidertwo.setCurrentItem(0);
                indicatortwo.setViewPager(introSlidertwo);
            } else {
                vedio_sliders.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }


        try {
            String[] rn_files = randm_nme.get(position);

            //	Log.i("random files..", Arrays.deepToString(rn_files));
            if (rn_files.length > 0 && rn_files != null) {
//				Log.i("one", "one");
//				rndm.setVisibility(View.GONE);
            } else {
//				Log.i("two", "two");
                rndm.setVisibility(View.GONE);
            }
            random1.setText(rn_files[0] + "...");

            random1.setOnClickListener(new OnClickListener() {
                Dialog dialog;

                @Override
                public void onClick(View v) {
                    try {
                        String title;
                        if (lang.equalsIgnoreCase("sw")) {
                            title = "Slumpmässig filer..";
                        } else {
                            title = "Random files..";
                        }

                        final String[] rn_files1 = randm_nme.get(position);
                        final String[] rn_files_ids = randm_id.get(position);

                        if ((rn_files1 != null && rn_files1.length > 0)) {
                            dialog = new Dialog(getContext());

                            dialog.setContentView(R.layout.dialoglayout);
                            dialog.setTitle(title);
                            dialog.setCancelable(true);
                            dialog.setCanceledOnTouchOutside(true);

                            dialog_ListView = (ListView) dialog
                                    .findViewById(R.id.dialoglist);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                    getContext(),

                                    android.R.layout.test_list_item,
                                    rn_files1);
                            dialog_ListView.setAdapter(adapter);
                            dialog.show();

                            dialog_ListView
                                    .setOnItemClickListener(new OnItemClickListener() {

                                        @Override
                                        public void onItemClick(
                                                AdapterView<?> parent,
                                                View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            dialog.dismiss();

                                            String rn_id = rn_files_ids[position];
                                            rn_name = rn_files1[position];

                                            dwnload_file_path = Base_url
                                                    + "picture_diary/viewOtherFiles/"
                                                    + rn_id
                                                    + "?authentication_token="
                                                    + auth_token;

                                            new DownloadFileFromURL()
                                                    .execute(dwnload_file_path);

                                        }

                                        // //////////////////////////////////////////////////////////////////////////////////////////////

                                        class DownloadFileFromURL
                                                extends
                                                AsyncTask<String, String, String> {

                                            /**
                                             * Before starting background thread
                                             * Show Progress Bar Dialog
                                             * */
                                            @Override
                                            protected void onPreExecute() {
                                                super.onPreExecute();
                                                showDialog(progress_bar_type);
                                            }

                                            private void showDialog(
                                                    int progressBarType) {
                                                String title;
                                                if (lang.equalsIgnoreCase("sw")) {
                                                    title = "nedladdning fil.Vänligen vänta...";
                                                } else {
                                                    title = "Downloading file. Please wait...";
                                                }

                                                // TODO Auto-generated method
                                                // stub

                                                pDialog = new ProgressDialog(
                                                        getContext());
                                                pDialog.setMessage(title);
                                                pDialog.setIndeterminate(false);
                                                pDialog.setMax(100);
                                                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                                pDialog.setCancelable(true);
                                                pDialog.show();

                                            }

                                            /**
                                             * Downloading file in background
                                             * thread
                                             * */
                                            @Override
                                            protected String doInBackground(
                                                    String... f_url) {
                                                int count;
                                                try {
                                                    URL url = new URL(f_url[0]);
                                                    URLConnection conection = url
                                                            .openConnection();
                                                    conection.connect();
                                                    // getting file length
                                                    int lenghtOfFile = conection
                                                            .getContentLength();

                                                    // input stream to read file
                                                    // - with 8k buffer
                                                    InputStream input = new BufferedInputStream(
                                                            url.openStream(),
                                                            8192);

                                                    // Output stream to write
                                                    // file
                                                    OutputStream output = new FileOutputStream(
                                                            "/sdcard/"
                                                                    + rn_name);

                                                    byte data[] = new byte[1024];

                                                    long total = 0;

                                                    while ((count = input
                                                            .read(data)) != -1) {
                                                        total += count;
                                                        // publishing the
                                                        // progress....
                                                        // After this
                                                        // onProgressUpdate will
                                                        // be called
                                                        publishProgress(""
                                                                + (int) ((total * 100) / lenghtOfFile));

                                                        // writing data to file
                                                        output.write(data, 0,
                                                                count);
                                                    }

                                                    // flushing output
                                                    output.flush();

                                                    // closing streams
                                                    output.close();
                                                    input.close();

                                                } catch (Exception e) {
                                                    Log.e("Error: ",
                                                            e.getMessage());
                                                }

                                                return null;
                                            }

                                            /**
                                             * Updating progress bar
                                             * */
                                            protected void onProgressUpdate(
                                                    String... progress) {
                                                // setting progress percentage
                                                pDialog.setProgress(Integer
                                                        .parseInt(progress[0]));
                                            }

                                            /**
                                             * After completing background task
                                             * Dismiss the progress dialog
                                             * **/
                                            @Override
                                            protected void onPostExecute(
                                                    String file_url) {

                                                pDialog.dismiss();

                                            }

                                        }

                                        // ///////////////////////////////////////////////////////////////////////////////////////////

                                    });

                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }


        // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // new imgessss(pst_id[position]).execute();

        txtTitle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String title;
                    if (lang.equalsIgnoreCase("sw")) {
                        title = "Taggade individer..";
                    } else {
                        title = "Tagged users..";
                    }


                    if (!(std.get(position).length == 0)) {
                        Dialog dialog = new Dialog(getContext());

                        dialog.setContentView(R.layout.dialoglayout);
                        dialog.setTitle(title);

                        dialog.setCancelable(true);
                        dialog.setCanceledOnTouchOutside(true);

                        dialog_ListView = (ListView) dialog
                                .findViewById(R.id.dialoglist);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                getContext(),

                                android.R.layout.test_list_item, std
                                .get(position));
                        dialog_ListView.setAdapter(adapter);
                        dialog.show();

                        // Log.i("yyyyyyyyyy", Arrays.deepToString(TAG_s_name));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // TODO: handle exception
                }

            }
        });

        return rowView;
    }

    // //////////////////////////////////////////////////////////////////////

    // ////////////////////////////////////////////////////////////////

    public TextView findViewById(int catagry) {
        // TODO Auto-generated method stub
        return null;
    }

    // /////////////////////////////Json
    // class/////////////////////////////////////////////////////////////

    class likeclass extends AsyncTask<String, String, String> {

        /*JSONParser jsonParser = new JSONParser();*/
        String ggg;
        String status = "";

        private MyCustomProgressDialog dialog;
        private String url_create_product = Base_url
                + "lms_api/picture_diary/like_post";

        private static final String TAG_staus = "status";
        private static final String TAG_post_like_count = "post_like_count";

        public likeclass(String string) {
            // TODO Auto-generated constructor stub
            this.ggg = string;
        }

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new MyCustomProgressDialog(contextA);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            String likeResponse = "";
            try {

                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode("H67jdS7wwfh", "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.PostId + "=" + URLEncoder.encode(ggg, "UTF-8") +
                        "&" + Const.Params.ParentId + "=" + URLEncoder.encode(user_id, "UTF-8");


                likeResponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }
         /*   // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("securityKey", "H67jdS7wwfh"));
            params.add(new BasicNameValuePair("authentication_token",
                    auth_token));
            params.add(new BasicNameValuePair("language", lang));
            params.add(new BasicNameValuePair("post_id", ggg));
            params.add(new BasicNameValuePair("parent_id", user_id));

            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check for success tag
            Log.i("json data......", json.toString());
            try {
                status = json.getString(TAG_staus);

                // Log.e("=-=-=-=-=-=-", status);
                count = json.getString(TAG_post_like_count);

                // Log.i("count value ", count);
                if (status.equalsIgnoreCase("true")) {

                    // successfully created product

                    // Toast.makeText(getApplicationContext(),
                    // "Sign in Sucessfully", Toast.LENGTH_LONG).show();
                } else {
                    // failed to create product
                    // Toast.makeText(getApplicationContext(),
                    // "invalid business_email and business_password",
                    // Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            return likeResponse;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {

                JSONObject jsonObject = new JSONObject();
                if (results != null && !results.isEmpty()) {
                    jsonObject = new JSONObject(results);
                    if (jsonObject.has(TAG_staus)) {
                        status = jsonObject.getString(TAG_staus);
                        // Log.e("=-=-=-=-=-=-", status);
                        count = jsonObject.getString(TAG_post_like_count);

                    }

                }


                // Log.e("=-=-=-=-=-=-", status);


                // Log.i("oooooo", imm[0]);
                if (status.equalsIgnoreCase("true")) {
                    // Toast.makeText(getContext(),
                    // "execute....",Toast.LENGTH_SHORT).show();

                    notifyDataSetChanged();

                    // Intent in = new Intent(getContext(),MainActivity.class);
                    // contextA.startActivity(in);
                    // contextA.finish();
                    //
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // ///////////////////////////////////////////////////////////////////////

    // ///////////////////////////////////////////////////////////////////////

}