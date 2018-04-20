package com.pnf.elar.app;

import java.util.HashMap;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Environment;

@SuppressWarnings("deprecation")
public class Post_play_video extends ActionBarActivity {

    String t;
    ViewGroup actionBarLayout;
    LinearLayout actionbar;
    ImageView img, img2, refresh, serhc;
    TextView MYAccount, txt2;
    UserSessionManager session;
    String lang;
    MediaController mediacontroller;
    VideoView ply_video;
    ProgressBar progress = null;

    int position = 0;

    String fileName = "";
    String videoUrl = "";

    ProgressDialog pdialaog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_play_video);

        session = new UserSessionManager(Post_play_video.this);

        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);

////////////////////////Action bar ///////////////////////////////

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

        MYAccount.setVisibility(View.GONE);
        refresh.setVisibility(View.GONE);
        serhc.setVisibility(View.GONE);


        txt2.setText(getString(R.string.video));

        /*if (lang.equalsIgnoreCase("sw")) {
            txt2.setText("");
        } else {
            txt2.setText("");
        }*/

        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Post_play_video.this.finish();
            }
        });
        img2.setBackgroundResource(R.drawable.download);

//////////////////////////////////////////////////////////////
        Intent rere = getIntent();
        Bundle extras = rere.getExtras();
        if (extras != null)

            t = extras.getString("v_url");
        videoUrl = t;

        Log.i("url;;;;;;;", t);

        ply_video = (VideoView) findViewById(R.id.play_video);


        // Show progressbar
            /*pDialog.show();*/
        if (mediacontroller == null) {
            mediacontroller = new MediaController(Post_play_video.this);
            mediacontroller.setAnchorView(ply_video);
            //  ply_video.setMediaController(mediacontroller);
        }
        try {
            showDialog();
            ply_video.setMediaController(mediacontroller);
            ply_video.setVideoPath(t);
            ply_video.start();


        } catch (Exception e) {
            // TODO: handle exception
            pdialaog.dismiss();
        }
        ply_video.requestFocus();

        ply_video.setOnPreparedListener(new OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                pdialaog.dismiss();
                ply_video.seekTo(position);
                if (position == 0) {
                    ply_video.start();

                }


            }
        });
        ply_video.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d("video", "setOnErrorListener ");
                pdialaog.dismiss();
                return true;
            }
        });

        img2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fileName = videoUrl.substring(videoUrl.lastIndexOf("/"));
                downloadFile(videoUrl, fileName);

              /*  ProgressBack PB = new ProgressBack();
                PB.execute("");*/
            }
        });

    }

    public void showDialog() {
        String dialogMsg = "";
        if (lang.equalsIgnoreCase("en")) {
            dialogMsg = " Loading.....";

        } else {
            dialogMsg = " Läser in.....";
        }
        pdialaog = ProgressDialog.show(Post_play_video.this, null, dialogMsg, true);
        pdialaog.setCancelable(true);
        pdialaog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        outState.putInt("CurrentPosition", ply_video.getCurrentPosition());
        ply_video.pause();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);

        position = savedInstanceState.getInt("CurrentPosition");
        ply_video.seekTo(position);
    }

    public void downloadFile(String videoUrl, String title) {

        File root = new File(Environment.getExternalStorageDirectory()
                + File.separator + "SmartClass" + File.separator + "Media" + File.separator + "SmartClass Videos" + File.separator);
        if (root.exists()) {

        } else {
            root.mkdirs();
        }

        String DownloadUrl = "Paste Url to download a pdf file here…";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(videoUrl));
        request.setDescription("Donwload in progress");   //appears the same in Notification bar while downloading
        request.setTitle(title);
        request.setDestinationUri(Uri.parse(videoUrl));
        request.setVisibleInDownloadsUi(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }

        Uri dst_uri = Uri.parse("file:///mnt/sdcard/0/SmartClass/Media/SmartClass Videos/" + title + ".jpg");
        // + File.separator + "SmartClass" + File.separator + "Media" + File.separator + "SmartClass Images" + File.separator+title+".jpg");

        /*request.setDestinationInExternalFilesDir(getApplicationContext(),null, title+".jpg");*/
        /*request.setDestinationUri(dst_uri);*/
        request.setDestinationInExternalPublicDir("/SmartClass/Media/SmartClass Videos", title);

        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

   /* class ProgressBack extends AsyncTask<String, String, String> {
        ProgressDialog PD;

        @Override
        protected void onPreExecute() {
            PD = ProgressDialog.show(Post_play_video.this, null, getString(R.string.wait_progress), true);
            PD.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... arg0) {
            if (DownloadFile(videoUrl, fileName))
                return "true";
            else
                return "false";
        }

        protected void onPostExecute(String result) {

            PD.dismiss();
            if (result.equalsIgnoreCase("true")) {
                Toast.makeText(getApplicationContext(), getString(R.string.download_completed), Toast.LENGTH_SHORT).show();


            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.download_failed), Toast.LENGTH_SHORT).show();
            }

        }
        }
*/



   /* public boolean DownloadFile(String fileURL, String fileName) {

        boolean v_download = false;
        try {
            String RootDir = Environment.getExternalStorageDirectory()
                    + File.separator + "SmartClass" + File.separator + "Media" + File.separator + "SmartClass Videos" + File.separator;
            File RootFile = new File(RootDir);
            if (RootFile.exists()) {

            } else {
                RootFile.mkdir();
            }
            // File root = Environment.getExternalStorageDirectory();
            URL u = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            FileOutputStream f = new FileOutputStream(new File(RootFile,
                    fileName));
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;

            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
            v_download = true;


        } catch (Exception e) {
            v_download = false;
            Log.d("Error....", e.toString());
        }
        return v_download;
    }*/

}
