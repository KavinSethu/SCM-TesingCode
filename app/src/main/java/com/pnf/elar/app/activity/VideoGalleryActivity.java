package com.pnf.elar.app.activity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.elar.util.SmartClassUtil;
import com.pnf.elar.app.Post_play_video;
import com.pnf.elar.app.R;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.views.ImageViewTouch;

import junit.framework.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VideoGalleryActivity extends AppCompatActivity {
    public static final String TAG = "PhotoGalleryActivity";
    private ArrayList<String> images;
    private GalleryPagerAdapter adapter;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.thumbnails)
    LinearLayout thumbnails;
    @Bind(R.id.download_btn)
    ImageButton closeButton;
    @Bind(R.id.back_btn)
    ImageButton back_btn;
    @Bind(R.id.mediaHeaderText)
    TextView mediaHeaderText;
    UserSessionManager session;
    String lang;
    MediaController mediacontroller;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        ButterKnife.bind(this);
        images = (ArrayList<String>) getIntent().getSerializableExtra(Const.CommonParams.EXTRA_NAME);
        Assert.assertNotNull(images);
        adapter = new GalleryPagerAdapter(this);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(6); // how many images to load into memory

        getSessionValues();
        mediaHeaderText.setText("Video");
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Log.d(TAG, "Back clicked"+pager.getCurrentItem());
                finish();*/

/*
                String title=String.valueOf(Calendar.getInstance().getTimeInMillis());
*/


                if (SmartClassUtil.isNetworkAvailable(getApplicationContext())&& isDownloadManagerAvailable(getApplicationContext()) )
                {

                    String title = images.get(pager.getCurrentItem()).substring(images.get(pager.getCurrentItem()).lastIndexOf("/"));

                    downloadFile(images.get(pager.getCurrentItem()), title);
                }
                else
               {
                }

            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void getSessionValues()
    {
        session = new UserSessionManager(VideoGalleryActivity.this);

        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
    }
    public static boolean isDownloadManagerAvailable(Context context) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setClassName("com.android.providers.downloads.ui", "com.android.providers.downloads.ui.DownloadList");
            List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
                    PackageManager.MATCH_DEFAULT_ONLY);
            return list.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void downloadFile(String imageUrl,String title){

        File root = new File(Environment.getExternalStorageDirectory()
                + File.separator + "SmartClass" + File.separator + "Media" + File.separator + "SmartClass Videos" + File.separator);
        if (root.exists()) {

        } else {
            root.mkdirs();
        }
        String DownloadUrl = "Paste Url to download a pdf file here…";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(imageUrl));
        request.setDescription("Donwload in progress");   //appears the same in Notification bar while downloading
        request.setTitle(title);
        request.setDestinationUri(Uri.parse(imageUrl));
        request.setVisibleInDownloadsUi(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        /*Uri dst_uri = Uri.parse("file:///mnt/sdcard/0/SmartClass/Media/SmartClass Images/"+title+".jpg");*/
        request.setDestinationInExternalPublicDir("/SmartClass/Media/SmartClass Videos", title);
        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
    class GalleryPagerAdapter extends PagerAdapter {
        Context context;
        LayoutInflater inflater;
        public GalleryPagerAdapter(Context context) {
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return images.size();
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = inflater.inflate(R.layout.activity_post_play_video, container, false);
            container.addView(itemView);
            // Get the border size to show around each image
            int borderSize = thumbnails.getPaddingTop();
            // Get the size of the actual thumbnail image
            int thumbnailSize = ((FrameLayout.LayoutParams)
                    pager.getLayoutParams()).bottomMargin - (borderSize*2);
            // Set the thumbnail layout parameters. Adjust as required
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(thumbnailSize, thumbnailSize);
            params.setMargins(0, 0, borderSize, 0);
            // You could also set like so to remove borders
            //ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
            //        ViewGroup.LayoutParams.WRAP_CONTENT,
            //        ViewGroup.LayoutParams.WRAP_CONTENT);
          /*  final ImageView thumbView = new ImageView(context);
            thumbView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            thumbView.setLayoutParams(params);
            thumbView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Thumbnail clicked");
                   // Set the pager position when thumbnail clicked
                    pager.setCurrentItem(position);
                }
            });
            thumbnails.addView(thumbView);*/

            final VideoView play_video =
                    (VideoView) itemView.findViewById(R.id.play_video);
            play_video.setVisibility(View.VISIBLE);


      final     ProgressDialog  pDialog = new ProgressDialog(VideoGalleryActivity.this);
            // Set progressbar title
            pDialog.setTitle("");
            // Set progressbar message
            String msg="";
            if(lang.equalsIgnoreCase("en"))
            {

            }
            else
            {

            }
            pDialog.setMessage("Video loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            // Show progressbar
            /*pDialog.show();*/
            if (mediacontroller == null) {
                mediacontroller = new MediaController(VideoGalleryActivity.this);
                mediacontroller.setAnchorView(play_video);
                //  ply_video.setMediaController(mediacontroller);
            }
            try {
                play_video.setMediaController(mediacontroller);
                play_video.setVideoPath(images.get(position));
                play_video.start();


            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                /*pDialog.dismiss();*/
            }
            play_video.requestFocus();

            play_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    // TODO Auto-generated method stub

                    play_video.seekTo(position);
                    if (position == 0) {
/*
                        pDialog.dismiss();
*/
                        play_video.start();

                    }


                }
            });


            // Asynchronously load the image and set the thumbnail and pager view
           /* Glide.with(context)
                    .load(images.get(position))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                            imageView.setImageBitmap(bitmap);
                            thumbView.setImageBitmap(bitmap);
                            imageView.setImageBitmapReset(bitmap, 0, true);
                        }
                    });*/

           /* Picasso.with(context).load(images.get(position)).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    imageView.setImageBitmap(bitmap);
                    thumbView.setImageBitmap(bitmap);
                    imageView.setImageBitmapReset(bitmap, 0, true);

                    System.out.println("height "+height+" width "+width);


              *//*  if(bitmap.getHeight()<200)
                {

                    imgView.getLayoutParams().height=height+(height/3);
                    imgView.setImageBitmap(bitmap);


                    imgView.setScaleType(ImageView.ScaleType.FIT_XY);
                } else if(bitmap.getWidth()<200)
                {
                    imgView.getLayoutParams().width=height+(width/3);
                    imgView.setImageBitmap(bitmap);


                    imgView.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                else {*//*
                    *//*imgView.setImageBitmap(bitmap);*//*
                *//*}*//*
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });*/
            return itemView;
        }
      /*  @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }*/


    }
}
