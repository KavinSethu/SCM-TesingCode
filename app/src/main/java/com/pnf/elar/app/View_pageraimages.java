package com.pnf.elar.app;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


import android.os.AsyncTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.elar.util.SmartClassUtil;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;
import com.pnf.elar.app.views.ImageViewTouch;

public class View_pageraimages extends AppCompatActivity {
    private static final String TAG = "Touch";


    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    String savedItemClicked;


    String stringArray;
    public ImageLoadernew imageLoader;
    ViewGroup actionBarLayout;
    LinearLayout actionbar;
    ImageView img, img2, refresh, serhc;
    TextView MYAccount, txt2;


    Bitmap bitmap;
    ProgressDialog pDialog;
    String fileName;


    ImageViewTouch mainImage;
    ImageView imgDisplay1;

    UserSessionManager session;
    String lang;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    /*private GoogleApiClient client;*/

    NotificationManager mNotifyManager;
    NotificationCompat.Builder build;
    int id = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pageraimages);


        session = new UserSessionManager(getApplicationContext());
        // ////////////////////////////////////////////////////
        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);

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

        txt2.setText("");


        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                View_pageraimages.this.finish();
            }
        });


        img2.setBackgroundResource(R.drawable.download);


        imageLoader = new ImageLoadernew(getApplicationContext());
        try {
            Intent li = getIntent();
            stringArray = li.getStringExtra("View_pager_images");


            fileName = li.getStringExtra("View_pager_images").substring(li.getStringExtra("View_pager_images").lastIndexOf('/') + 1);
            Log.e("sss", stringArray + "   file  " + fileName);


            txt2.setText("");
        } catch (Exception e) {
            // TODO: handle exception
        }

        mainImage = (ImageViewTouch) findViewById(R.id.imgDisplay);

        if (SmartClassUtil.isNetworkAvailable(getApplicationContext())) {


            new DownLoadImage().execute(stringArray);
        } else {

            if (lang.equalsIgnoreCase("en")) {


                SmartClassUtil.showToast(getApplicationContext(), "No internet connection");
            } else {
                SmartClassUtil.showToast(getApplicationContext(), "Ingen internetanslutning");

            }
        }


        img2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                String title=String.valueOf(Calendar.getInstance().getTimeInMillis());


                if (SmartClassUtil.isNetworkAvailable(getApplicationContext())&& isDownloadManagerAvailable(getApplicationContext()) )
                {

                        downloadFile(stringArray, title);


                }
                else {

                    new LoadImage().execute(stringArray);


                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pDialog.dismiss();
                        }
                    }, 1000);
                }

              /*  mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                build = new NotificationCompat.Builder(View_pageraimages.this);
                build.setContentTitle(title)
                        .setContentText("Download in progress")
                        .setSmallIcon(R.drawable.app_icon);
                Notification notif = build.build();
                notif.flags |= Notification.FLAG_AUTO_CANCEL;
                mNotifyManager.notify(id, notif);
                new generatePictureStyleNotification(View_pageraimages.this,title, "Image Downloading", stringArray).execute();
*/

             /*   new LoadImage().execute(stringArray);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pDialog.dismiss();
                    }
                }, 1000);*/
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        /*client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();*/
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
                + File.separator + "SmartClass" + File.separator + "Media" + File.separator + "SmartClass Images" + File.separator);
        if (root.exists()) {

        } else {
            root.mkdirs();
        }

        String DownloadUrl = "Paste Url to download a pdf file here…";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(imageUrl));
        request.setDescription("Donwload in progress");   //appears the same in Notification bar while downloading
        request.setTitle(title+".jpg");
        request.setDestinationUri(Uri.parse(imageUrl));
        request.setVisibleInDownloadsUi(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }

        Uri dst_uri = Uri.parse("file:///mnt/sdcard/0/SmartClass/Media/SmartClass Images/"+title+".jpg");
               // + File.separator + "SmartClass" + File.separator + "Media" + File.separator + "SmartClass Images" + File.separator+title+".jpg");

        /*request.setDestinationInExternalFilesDir(getApplicationContext(),null, title+".jpg");*/
        /*request.setDestinationUri(dst_uri);*/
        request.setDestinationInExternalPublicDir("/SmartClass/Media/SmartClass Images", title+".jpg");

        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("View_pageraimages Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

   /* @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       *//* client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());*//*
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }*/


    class LoadImage extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(View_pageraimages.this);
            if (lang.equalsIgnoreCase("en")) {

                pDialog.setTitle("File for download");
            } else {
                pDialog.setTitle("Fil för nedladdning");
            }
            pDialog.setMessage(fileName + ".jpg");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... arg0) {
            String dwnld = "";
            try {
                dwnld = saveToInternalStorage(fileName);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return dwnld;
        }

        protected void onPostExecute(String image) {
            pDialog.dismiss();

            if (image.equalsIgnoreCase("true")) {
                if (lang.equalsIgnoreCase("en")) {

                    SmartClassUtil.showToast(getApplicationContext(), "Image saved");

                } else {
                    SmartClassUtil.showToast(getApplicationContext(), "Bilden sparats");


                }


            } else {
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));


                if (lang.equalsIgnoreCase("en")) {
                    SmartClassUtil.showToast(getApplicationContext(), "Image not saved");

                } else {
                    SmartClassUtil.showToast(getApplicationContext(), "Bilden inte sparas");


                }


            }
        }
    }


    private class DownLoadImage extends AsyncTask<String, String, Bitmap> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(View_pageraimages.this);
            pDialog.setMessage(getString(R.string.loading_image));
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {
            pDialog.dismiss();
            if (image != null) {
                mainImage.setImageBitmap(image);
                mainImage.setImageBitmapReset(image, 0, true);


            } else {


                if (lang.equalsIgnoreCase("en")) {
                    SmartClassUtil.showToast(getApplicationContext(), "Image Does Not exist or Network Error");

                } else {
                    SmartClassUtil.showToast(getApplicationContext(), "Bild finns inte eller nätverks fel");

                }


            }
        }
    }

    public String saveToInternalStorage(String imageName) {
        String imageDwonload = "false";

        mainImage.buildDrawingCache();
        Bitmap bm = mainImage.getDrawingCache();

        OutputStream fOut = null;
        Uri outputFileUri;
        try {


            File root = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "SmartClass" + File.separator + "Media" + File.separator + "SmartClass Images" + File.separator);
            if (root.exists()) {

            } else {
                root.mkdirs();
            }
            File sdImageMainDirectory = new File(root, imageName + ".jpg");
            outputFileUri = Uri.fromFile(sdImageMainDirectory);
            fOut = new FileOutputStream(sdImageMainDirectory);
        } catch (Exception e) {
            if (lang.equalsIgnoreCase("en")) {
                SmartClassUtil.showToast(getApplicationContext(), "Error occured. Please try again later.");
            } else {
                SmartClassUtil.showToast(getApplicationContext(), "Ett fel uppstod. Försök igen senare.");

            }

            imageDwonload = "false";
        }
        try {
            bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            imageDwonload = "true";

        } catch (Exception e) {
        }

        return imageDwonload;
    }

    public class generatePictureStyleNotification extends AsyncTask<String, Void, Bitmap> {

        private Context mContext;
        private String title, message, imageUrl;

        public generatePictureStyleNotification(Context context, String title, String message, String imageUrl) {
            super();
            this.mContext = context;
            this.title = title;
            this.message = message;
            this.imageUrl = imageUrl;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            InputStream in;
            try {
                URL url = new URL(this.imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);

                saveToInternalStorage(title);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);


            Intent intent =  new Intent(Intent.ACTION_VIEW);
            intent.putExtra("key", "value");
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "SmartClass" + File.separator + "Media" + File.separator + "SmartClass Images" + File.separator+title+".jpg");
            Uri path = Uri.fromFile(file);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setDataAndType(path, "image/*");
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
/*
            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
*/


            /*build.setContentText("Download complete");*/
            // Removes the progress bar
            build.setProgress(0, 0, false);
            build.setContentIntent(pendingIntent)
                    .setContentTitle(title)
                    .setContentText("Download complete")
                    .setSmallIcon(R.drawable.app_icon)
                    .setLargeIcon(result);

            Notification notif = new Notification.Builder(mContext)
                    .setContentIntent(pendingIntent)
                    .setContentTitle(title)
                    .setContentText("Download complete")
                    .setSmallIcon(R.drawable.app_icon)
                    .setLargeIcon(result)
                    .setStyle(new Notification.BigPictureStyle().bigPicture(result))
                    .build();

            /*mNotifyManager.notify(id, build.build());*/

         /*   build.setStyle(new Notification.BigPictureStyle().bigPicture(result));
            build.setStyle(new Notification.BigPictureStyle().bigPicture(result));*/
           /* Notification notif =


                    build.build();*/
            /*notif.*/
            notif.defaults |= Notification.DEFAULT_SOUND;
            notif.defaults |= Notification.DEFAULT_VIBRATE;

            notif.flags |= Notification.FLAG_AUTO_CANCEL;
            mNotifyManager.notify(id, notif);




        }



    }
 /*   public Uri getImageContentUri(File imageFile) {


        imageFile = new File("/sdcard/SmartClass/1.jpg");
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();

            System.out.println("getImageContentUri" + Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id));
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);

                System.out.println("getImageContentUri" + getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values));

                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ImageView view = (ImageView) v;
        // make the image scalable as a matrix
        view.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;

        // Handle touch events here...
        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN: //first finger down only
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                Log.d(TAG, "mode=DRAG");
                mode = DRAG;
                break;
            case MotionEvent.ACTION_UP: //first finger lifted
            case MotionEvent.ACTION_POINTER_UP: //second finger lifted
                mode = NONE;
                Log.d(TAG, "mode=NONE");
                break;
            case MotionEvent.ACTION_POINTER_DOWN: //second finger down
                oldDist = spacing(event); // calculates the distance between two points where user touched.
                Log.d(TAG, "oldDist=" + oldDist);
                // minimal distance between both the fingers
                if (oldDist > 5f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event); // sets the mid-point of the straight line between two points where user touched.
                    mode = ZOOM;
                    Log.d(TAG, "mode=ZOOM");
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) { //movement of first finger
                    matrix.set(savedMatrix);
                    if (view.getLeft() >= -392) {
                        matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
                    }
                } else if (mode == ZOOM) { //pinch zooming
                    float newDist = spacing(event);
                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 5f) {
                        matrix.set(savedMatrix);
                        scale = newDist / oldDist; //thinking I need to play around with this value to limit it**
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }

        // Perform the transformation
        view.setImageMatrix(matrix);

        return true; // indicate event was handled
    }



    *//**
     * Determine the space between the first two fingers
     *//*
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    *//**
     * Calculate the mid point of the first two fingers
     *//*
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
*/
}
