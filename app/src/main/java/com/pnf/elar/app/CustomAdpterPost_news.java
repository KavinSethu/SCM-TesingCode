package com.pnf.elar.app;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

/*import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.elar.util.NetworkUtil;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;
import com.viewpagerindicator.CirclePageIndicator;

@SuppressWarnings("deprecation")
public class CustomAdpterPost_news extends ArrayAdapter<String> {
    private ProgressDialog pDialog;
    String lang, auth_token, Base_url, user_typ, user_id = "", rn_name;
    ViewPager introSlider, intro_vedio_vedio;
    CirclePageIndicator indicator, indicatortwo;
    private final Activity contextA;
    private final String[] news_title;
    private final String[] description;
    private String[] like1;
    private String[] id;
    private final ArrayList<String[]> randm_file;
    private final ArrayList<String[]> rdm_id;
    private final String[] already_liked;
    private final String[] teacher_name;
    private final String[] created;
    private final ArrayList<String[]> lis;
    private final ArrayList<String[]> vdio;
    private final ArrayList<String[]> randm_nme;
    private final ArrayList<String[]> vdio_url;
    TextToSpeech t1;
    UserSessionManager session;
    ImageView curriculm_image, pen, speech;
    ListView list;
    TextView txtTitle, random1;
    ListView dialog_ListView;
    RelativeLayout vedio_sliders;
    RelativeLayout imageview_slider;
    LinearLayout likess;
    String[] listContent = {"January", "February"};
    String[] TAG_s_name;
    Drawable myDrawable;
    String[] alrdy_liked;
    int kk;
    // TextView lik;
    View rowView;
    LayoutInflater inflater;
    String count;
    String dwnload_file_path = "http://ps.pnf-sites.info/picture_diary/viewOtherFiles/305?authentication_token=a0790005b5646c244434da977cd8cd94beb04baf";
    int totalSize = 0;
    ProgressBar pb;
    int downloadedSize = 0;
    TextView cur_val, likecount;
    int imageicon;
    ImageView immm, rndm;
    RelativeLayout top_layout;
    private int colorCount = 0;
    String[] colors = {"#5ABB54", "#2FBCD0", "#617DBE", "#8863A9", "#EC74A9",
            "#F15A6B"};
    private String getColor(int position) {
        if (position == 0) {
            return "#5ABB54";
        }
        if (position == 1) {
            return "#2FBCD0";
        }
        if (position == 2) {
            return "#617DBE";
        }
        if (position == 3) {
            return "#8863A9";
        }
        if (position == 4) {
            return "#EC74A9";
        }
        if (position == 5) {
            return "#F15A6B";
        }
        return "#5ABB54";
    }

    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;

    // File url to download
    private static String file_url = "http://maven.apache.org/maven-1.x/maven.pdf";

    public CustomAdpterPost_news(Activity news_Post, String[] news_title,
                                 String[] description, String[] teacher_name, String[] created,
                                 ArrayList<String[]> lis, ArrayList<String[]> li,
                                 ArrayList<String[]> video_thum_nl, ArrayList<String[]> vdio_urll,
                                 String[] already_liked, String[] like, String[] id, ArrayList<String[]> randm_fl_name, ArrayList<String[]> random_id) {
        // TODO Auto-generated constructor stub
        super(news_Post, R.layout.custom_news_post_news, news_title);
        this.contextA = news_Post;
        this.news_title = news_title;
        this.description = description;
        this.teacher_name = teacher_name;
        this.created = created;
        this.lis = li;
        this.vdio = video_thum_nl;
        this.randm_nme = video_thum_nl;
        this.vdio_url = lis;
        this.already_liked = already_liked;
        this.like1 = like;
        this.id = id;
        this.randm_file = randm_fl_name;
        this.rdm_id = random_id;
    }

    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    public class ViewHolder {
        RelativeLayout top_layout;
    };

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {

        ViewHolder holder = null;
        inflater = contextA.getLayoutInflater();

        rowView = view;
        inflater = (LayoutInflater) contextA
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.custom_news_post_news, parent,
                false);
        holder = new ViewHolder();
        pen = (ImageView) rowView.findViewById(R.id.pen);

        session = new UserSessionManager(getContext());

        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        // auth_token = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        user_typ = user.get(UserSessionManager.TAG_user_type);

        if (user_typ.equalsIgnoreCase("Student")) {
            pen.setVisibility(View.GONE);
        }

        try {
            Log.i("randm_nme", Arrays.deepToString(randm_file.get(position)));
        } catch (Exception e) {
            // TODO: handle exception
        }

        // //////////////////////////////////////////////////////////////////////////////////////////////////////
        imageview_slider = (RelativeLayout) rowView
                .findViewById(R.id.imageview_slider);

        vedio_sliders = (RelativeLayout) rowView
                .findViewById(R.id.vedio_sliders);
        // //////////////
        if (user_typ.equalsIgnoreCase("Parent")) {
            pen.setVisibility(View.GONE);
            user_id = user.get(UserSessionManager.TAG_user_id);
            auth_token = user
                    .get(UserSessionManager.TAG_Authntication_Children);
            Log.i("auth_token_child", auth_token);
        } else {
            auth_token = user.get(UserSessionManager.TAG_Authntication_token);
            Log.i("auth_token", auth_token);

        }

        // //////////////////////////////////////////////////////////////////////////////////////////////
        // curriculm_image=(ImageView)rowView.findViewById(R.id.curriculm_image);
        likess = (LinearLayout) rowView.findViewById(R.id.likess);
        txtTitle = (TextView) rowView.findViewById(R.id.catagry);
        TextView txtTitledate = (TextView) rowView
                .findViewById(R.id.belowfirst);
        random1 = (TextView) rowView.findViewById(R.id.random1);
        rndm = (ImageView) rowView.findViewById(R.id.rndm);

        TextView des = (TextView) rowView.findViewById(R.id.des);
        immm = (ImageView) rowView.findViewById(R.id.immm);

        speech = (ImageView) rowView.findViewById(R.id.speech);
        holder.top_layout = (RelativeLayout) rowView.findViewById(R.id.top);

        likecount = (TextView) rowView.findViewById(R.id.likecount);
        // ////////////////////////////////////////////////////////////////////////
        pen.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if (NetworkUtil.getInstance(contextA).isInternet()) {

                        FragmentManager fragmentManager = contextA.getFragmentManager();
                        Edit_news rFragment = new Edit_news();
                        Bundle bundle = new Bundle();
                        bundle.putString("edit_post_id", id[position]);
                        rFragment.setArguments(bundle);
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, rFragment);
                        ft.commit();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });


        holder.top_layout.setBackgroundColor(Color.parseColor(getColor(colorCount)));
        colorCount++;
        System.out.println("count outside --> "+colorCount);
        if (colorCount == 6) {
            colorCount = 0;
            System.out.println("reInitialized count --> "+colorCount);
        }
        // /////////////////////////////////////////////////////////
     //   for (int i = 0; i < news_title.length; i++) {
//            if (position % 2 == 0) {
//                top_layout.setBackgroundColor(Color.parseColor(colors[0]));
//
//            } else if (position % 3 == 0) {
//                if (position % 5 == 0) {
//                    if (position % 6 == 0) {
//                        top_layout.setBackgroundColor(Color
//                                .parseColor(colors[5]));
//                    }
//                    top_layout.setBackgroundColor(Color.parseColor(colors[3]));
//                }
//                top_layout.setBackgroundColor(Color.parseColor(colors[2]));
//            } else if (position % 4 == 0) {
//                top_layout.setBackgroundColor(Color.parseColor(colors[4]));
//            } else
//
//            {
//                top_layout.setBackgroundColor(Color.parseColor(colors[1]));
//            }



      //  }

        // /////////////////////////////////////////////////
        t1 = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        // ///////////////////////////////////////////////////////
        likecount.setText(already_liked[position]);

        speech.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String discription = description[position];
                t1.speak(discription, TextToSpeech.QUEUE_FLUSH, null);

            }
        });
        // ///////////////////////////////////////////////////////

        try {
            if (!(like1[position].equalsIgnoreCase("yes")))
                ;
            {
                Log.i("like Status..", like1[position]);

                // holder.immm.setVisibility(View.GONE);

                immm.setBackgroundResource(R.drawable.like1);
                if (like1[position].equalsIgnoreCase("yes")) {
                    immm.setBackgroundResource(R.drawable.blue_like);
                    imageicon = 1;
                }

            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        immm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new likeclass(id[position]).execute();

                int lik = Integer.parseInt(already_liked[position]);

                if (like1[position].equalsIgnoreCase("yes")) {
                    int liky = lik - 1;

                    already_liked[position] = Integer.toString(liky);
                    like1[position] = "no";
                } else {
                    int liky = 1 + lik;
                    already_liked[position] = Integer.toString(liky);
                    like1[position] = "yes";
                }

            }
        });

        txtTitle.setText(news_title[position]);

        txtTitledate
                .setText(teacher_name[position] + " # " + created[position]);
        des.setText(Html.fromHtml(description[position]));

        try {

            introSlider = (ViewPager) rowView.findViewById(R.id.intro_pager);
            indicator = (CirclePageIndicator) rowView
                    .findViewById(R.id.indicator);
            if (!(lis.get(position).length == 0)) {

                introSlider.setAdapter(new IntroPageAdapter_news(lis
                        .get(position),// ///////////// workngggg
                        getContext()));
                introSlider.setCurrentItem(0);
                indicator.setViewPager(introSlider);
            } else {
                imageview_slider.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        try {
            intro_vedio_vedio = (ViewPager) rowView
                    .findViewById(R.id.intro_vedio);
            indicatortwo = (CirclePageIndicator) rowView
                    .findViewById(R.id.indicatortwo);

            if (!(vdio.get(position).length == 0)) {

                intro_vedio_vedio.setAdapter(new IntroPageAdaptertwo_news(vdio
                        .get(position), vdio_url.get(position), getContext())); // /////////
                // workngggg
                intro_vedio_vedio.setCurrentItem(0);
                indicatortwo.setViewPager(intro_vedio_vedio);
            } else {
                vedio_sliders.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        try {
            String[] rn_files = randm_file.get(position);

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

                        final String[] rn_files1 = randm_file.get(position);
                        final String[] rn_files_ids = rdm_id.get(position);

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

                                            try {
                                                if (NetworkUtil.getInstance(contextA).isInternet()) {

                                                    new DownloadFileFromURL()
                                                            .execute(dwnload_file_path);
                                                }
                                            } catch (MalformedURLException e) {
                                                e.printStackTrace();
                                            }

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

//                                                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

                                                    OutputStream output = new FileOutputStream(
                                                            "//sdcard//Download//"
                                                                    + rn_name);


                                                  /*  File file;
                                                    FileOutputStream outputStream;
                                                    try {
                                                        file = new File(Environment.getExternalStorageDirectory(), "MyCache");

                                                        outputStream = new FileOutputStream(file);
                                                        outputStream.write(content.getBytes());
                                                        outputStream.close();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }*/

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
                    }
                }
            });

        } catch (Exception e) {
            // TODO: handle exception
        }


        // /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Log.i("randm_nmerandm_nme",
        // Arrays.deepToString(randm_nme.get(position)));

        // try {
        // String[] uiui ={"sfsf","dsfdsf","fdsfd"};
        // adapter = new ArrayAdapter<String>(getContext(),
        // R.layout.spinner_item, uiui);
        // spinner.setAdapter(adapter);
        // spinner.setOnItemSelectedListener(new
        // AdapterView.OnItemSelectedListener() {
        //
        // @Override
        // public void onItemSelected(AdapterView<?> arg0, View arg1,
        // int arg2, long arg3) {
        //
        // int position = spinner.getSelectedItemPosition();
        // //
        // Toast.makeText(getApplicationContext(),"You have selected "+curriculum_title[+position],Toast.LENGTH_LONG).show();
        // Log.i("position ", Integer.toString(position));
        // // TODO Auto-generated method stub
        // // criclum_id = curriculum_id[position];
        // }
        //
        // @Override
        // public void onNothingSelected(AdapterView<?> arg0) {
        // // TODO Auto-generated method stub
        //
        // }
        //
        // });
        //
        //
        // } catch (Exception e) {
        // // TODO: handle exception
        // }
        //
        //

        // //////////////////////////////////////////////////////////////////////////////////////////////

        // ///////////////////////////////////////////////////////////////////////////////////////

        // try {

        //
        // random1.setOnClickListener(new OnClickListener() {
        // Dialog dialog;
        // @Override
        // public void onClick(View v) {
        // try {
        //
        // String[] rn_files1 = randm_nme.get(position);
        //
        // Log.i("====random files..", Arrays.deepToString(rn_files1));
        //
        // if ((rn_files1!=null && rn_files1.length>0)) {
        // dialog = new Dialog(getContext());
        //
        // dialog.setContentView(R.layout.dialoglayout);
        // dialog.setTitle("Random files... ");
        //
        // dialog.setCancelable(true);
        // dialog.setCanceledOnTouchOutside(true);
        //
        // dialog_ListView = (ListView) dialog
        // .findViewById(R.id.dialoglist);
        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(
        // getContext(),
        //
        // android.R.layout.simple_list_item_1,rn_files1);
        // dialog_ListView.setAdapter(adapter);
        // dialog.show();
        //
        //
        // dialog_ListView.setOnItemClickListener(new OnItemClickListener() {
        //
        // @Override
        // public void onItemClick(AdapterView<?> parent,
        // View view, int position, long id) {
        // // TODO Auto-generated method stub
        // dialog.dismiss();
        // new DownloadFileFromURL().execute(file_url);
        //
        // }
        // //////////////////////////////////////////////////////////////////////////////////////////////

        // class DownloadFileFromURL extends AsyncTask<String, String, String> {
        //
        // /**
        // * Before starting background thread
        // * Show Progress Bar Dialog
        // * */
        // @Override
        // protected void onPreExecute() {
        // super.onPreExecute();
        // showDialog(progress_bar_type);
        // }
        //
        // private void showDialog(int progressBarType) {
        //
        // // TODO Auto-generated method stub
        //
        // pDialog = new ProgressDialog(getContext());
        // pDialog.setMessage("Downloading file. Please wait...");
        // pDialog.setIndeterminate(false);
        // pDialog.setMax(100);
        // pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // pDialog.setCancelable(true);
        // pDialog.show();
        //
        // }
        //
        // /**
        // * Downloading file in background thread
        // * */
        // @Override
        // protected String doInBackground(String... f_url) {
        // int count;
        // try {
        // URL url = new URL(f_url[0]);
        // URLConnection conection = url.openConnection();
        // conection.connect();
        // // getting file length
        // int lenghtOfFile = conection.getContentLength();
        //
        // // input stream to read file - with 8k buffer
        // InputStream input = new BufferedInputStream(url.openStream(), 8192);
        //
        // // Output stream to write file
        // OutputStream output = new FileOutputStream("/sdcard/file.pdf");
        //
        // byte data[] = new byte[1024];
        //
        // long total = 0;
        //
        // while ((count = input.read(data)) != -1) {
        // total += count;
        // // publishing the progress....
        // // After this onProgressUpdate will be called
        // publishProgress(""+(int)((total*100)/lenghtOfFile));
        //
        // // writing data to file
        // output.write(data, 0, count);
        // }
        //
        // // flushing output
        // output.flush();
        //
        // // closing streams
        // output.close();
        // input.close();
        //
        // } catch (Exception e) {
        // Log.e("Error: ", e.getMessage());
        // }
        //
        // return null;
        // }
        //
        // /**
        // * Updating progress bar
        // * */
        // protected void onProgressUpdate(String... progress) {
        // // setting progress percentage
        // pDialog.setProgress(Integer.parseInt(progress[0]));
        // }
        //
        // /**
        // * After completing background task
        // * Dismiss the progress dialog
        // * **/
        // @Override
        // protected void onPostExecute(String file_url) {
        // // dismiss the dialog after the file was downloaded
        // // dismissDialog(progress_bar_type);
        //
        // // Displaying downloaded image into image view
        // // Reading image path from sdcard
        // String imagePath =
        // Environment.getExternalStorageDirectory().toString() +
        // "/downloadedfile.jpg";
        // // setting downloaded into image view
        // // my_image.setImageDrawable(Drawable.createFromPath(imagePath));
        // }
        //
        // }
        //
        //
        // /////////////////////////////////////////////////////////////////////////////////////////////
        //
        // });
        //
        // // Log.i("yyyyyyyyyy", Arrays.deepToString(TAG_s_name));
        // }
        // } catch (Exception e) {
        // // TODO: handle exception
        // }
        //
        // }
        // });

        // }else {
        // random1.setVisibility(View.GONE);
        //
        // }
        //
        // if(!(rn_files[1]==null)){
        // random2.setText(rn_files[1]);
        // }else {
        // random2.setVisibility(View.GONE);
        // }
        //
        // if(!(rn_files[2]==null)){
        // random3.setText(rn_files[2]);
        // }else {
        //
        // random3.setVisibility(View.GONE);
        // }

        // } catch (Exception e) {
        // // TODO: handle exception
        // }

        // /////////////////////////////////////////////////////////////////////////////////////////

        // ////////////////////////////////////////////////////////////////////////////////////////

        // ///////////
        // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // new imgessss(pst_id[position]).execute();

        // txtTitle.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // try {
        //
        // if (!(std.get(position).length == 0)) {
        // Dialog dialog = new Dialog(getContext());
        //
        // dialog.setContentView(R.layout.dialoglayout);
        // dialog.setTitle("Students Name.. ");
        //
        // dialog.setCancelable(true);
        // dialog.setCanceledOnTouchOutside(true);
        //
        // dialog_ListView = (ListView) dialog
        // .findViewById(R.id.dialoglist);
        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(
        // getContext(),
        //
        // android.R.layout.simple_list_item_1, std
        // .get(position));
        // dialog_ListView.setAdapter(adapter);
        // dialog.show();
        //
        // // Log.i("yyyyyyyyyy", Arrays.deepToString(TAG_s_name));
        // }
        // } catch (Exception e) {
        // // TODO: handle exception
        // }
        //
        // }
        // });

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
        // UserSessionManager session = new UserSessionManager(getContext());
        //
        // HashMap<String, String> user = session.getUserDetails();
        // String lang = user.get(UserSessionManager.TAG_language);
        // String auth_token =
        // user.get(UserSessionManager.TAG_Authntication_token);
        // String Base_url = user.get(UserSessionManager.TAG_Base_url);

        /*JSONParser jsonParser = new JSONParser();*/
        String ggg;
        String status;

        private MyCustomProgressDialog dialog;
        private String url_create_product = Base_url + "lms_api/news/like_news";

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

            // Toast.makeText(getApplicationContext(),
            // Login_Email+Login_Password,Toast.LENGTH_LONG).show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            String likeNewsResponse = "";
            try {

                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode("H67jdS7wwfh", "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.NewsId + "=" + URLEncoder.encode(ggg, "UTF-8") +
                        "&" + Const.Params.ParentId + "=" + URLEncoder.encode(user_id, "UTF-8");


                likeNewsResponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*// Log.i("ttttttttttttt", ggg);
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("securityKey", "H67jdS7wwfh"));
			params.add(new BasicNameValuePair("authentication_token",
					auth_token));
			params.add(new BasicNameValuePair("language", lang));
			params.add(new BasicNameValuePair("news_id", ggg));
			params.add(new BasicNameValuePair("parent_id", user_id));

			JSONObject json = jsonParser.makeHttpRequest(url_create_product,
					"POST", params);

			// check for success tag
			Log.i("news like......", json.toString());
			try {
				status = json.getString(TAG_staus);

				// Log.e("=-=-=-=-=-=-", status);
				// count = json.getString(TAG_post_like_count);

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
			}
*/
            return likeNewsResponse;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String results) {
            // dismiss the dialog once done
           dialog.dismiss();
            try {
                JSONObject jsonObject = new JSONObject();
                if (results != null && !results.isEmpty())
                {
                    jsonObject = new JSONObject(results);
                    if (jsonObject.has(TAG_staus)) {
                        status = jsonObject.getString(TAG_staus);
                   } else {
                        status = "false";
                    }
                } else {
                    status = "false";
                }

                // Log.i("oooooo", imm[0]);
                if (status.equalsIgnoreCase("true")) {
                    // Toast.makeText(getContext(),
                    // "execute....",Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }else {
                    try {


                        String msg = jsonObject.getString("message");
                        System.out.print(msg);

                        if (lang.equalsIgnoreCase("sw")) {
                            System.out.print("Sw_l");
                            if (msg.equalsIgnoreCase("Autentisering misslyckades")) {
                                Button btnLogout;
                                TextView tvMessage;
                                final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", contextA);
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
                                final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", contextA);
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

}