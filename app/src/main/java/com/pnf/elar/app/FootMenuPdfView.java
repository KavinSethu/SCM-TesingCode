package com.pnf.elar.app;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elar.util.SmartClassUtil;
import com.pnf.elar.app.util.Const;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import static com.pnf.elar.app.util.Const.CommonParams.PDFNAME;

public class FootMenuPdfView extends AppCompatActivity {
    ViewGroup actionBarLayout;
    LinearLayout actionbar;

    ImageView img, img2 ;
    TextView  txt2;


    WebView pdfWebView;
    int downloadedSize = 0;
    int totalSize = 0;


    String pdfUrl = "";
    String title = "";
    ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_menu_pdf_view);
        initView();

        setActionBar();


    }

    public void setActionBar() {
      /*  actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.actionbar, null);
        actionbar = (LinearLayout) findViewById(R.id.actionbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);
*/

     /*   final int actionBarColor = getResources().getColor(R.color.action_bar);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(actionBarColor));


        img = (ImageView) findViewById(R.id.img);
        img2 = (ImageView) findViewById(R.id.img2);
        refresh = (ImageView) findViewById(R.id.refresh);
        serhc = (ImageView) findViewById(R.id.serhc);
        MYAccount = (TextView) findViewById(R.id.text1);
        txt2 = (TextView) findViewById(R.id.txt2);


*//*
        img2.setBackgroundColor(Color.parseColor("#666666"));
*//*
        img2.setImageResource(R.drawable.download);

        MYAccount.setVisibility(View.GONE);*/
        img = (ImageView) findViewById(R.id.img);
        img2 = (ImageView) findViewById(R.id.img2);
        txt2 = (TextView) findViewById(R.id.txt2);

        txt2.setText("PDF File");
        txt2.setText(title);
        txt2.setText(getIntent().getStringExtra(PDFNAME));
        txt2.setSelected(true);

     /*   refresh.setVisibility(View.GONE);
        serhc.setVisibility(View.GONE);*/

        SmartClassUtil.PrintMessage("pdf  : "+getIntent().getStringExtra("pdfUrl"));
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {

                        if (isDownloadManagerAvailable(getApplicationContext()))


                       downloadFile(pdfUrl, title);
/*
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.adobe.com/content/dam/Adobe/en/devnet/acrobat/pdfs/pdf_open_parameters.pdf"));
                        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                        startActivity(browserIntent);*/
                        /*startDownload();*/
                    }
                }).start();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*txt2.setText("");*/


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

    public void downloadFile(String imageUrl, String title) {

        File root = new File(Environment.getExternalStorageDirectory()
                + File.separator + "SmartClass" + File.separator + "Documents" + File.separator);
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

        Uri dst_uri = Uri.parse("file:///mnt/sdcard/0/SmartClass/Documents/" + title);
        // + File.separator + "SmartClass" + File.separator + "Media" + File.separator + "SmartClass Images" + File.separator+title+".jpg");

        /*request.setDestinationInExternalFilesDir(getApplicationContext(),null, title+".pdf");*/
        /*request.setDestinationUri(dst_uri);*/
        request.setDestinationInExternalPublicDir("/SmartClass/Documents", title);

        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    public void initView() {
        pdfWebView = (WebView) findViewById(R.id.pdfWebView);
        pdfWebView.getSettings().setJavaScriptEnabled(true);
        pdfWebView.getSettings().setPluginState(WebSettings.PluginState.ON);

        WebSettings settings = pdfWebView.getSettings();

        settings.setDomStorageEnabled(true);
        pdfWebView.setWebViewClient(new Callback());

        pdfUrl = getIntent().getStringExtra(Const.CommonParams.PDFURL);
        title =getIntent().getStringExtra(Const.CommonParams.PDFNAME);

        pdfWebView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url="+pdfUrl);



    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return (false);
        }

        private void startDownload() {
            /*String url = getIntent().getStringExtra("pdfUrl");
            new DownloadFileAsync().execute(url);*/
        }



        class DownloadFileAsync extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
/*
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
*/
            }

            @Override
            protected String doInBackground(String... aurl) {
                int count;

                try {

                    URL url = new URL(aurl[0]);
                    URLConnection conexion = url.openConnection();
                    conexion.connect();

                    int lenghtOfFile = conexion.getContentLength();
                    Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream("/sdcard/ssclass.pdf");

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();
                } catch (Exception e) {
                }
                return null;

            }

            protected void onProgressUpdate(String... progress) {
                Log.d("ANDRO_ASYNC", progress[0]);
/*
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
*/
            }

            @Override
            protected void onPostExecute(String unused) {
/*
            dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
*/

                Toast.makeText(getApplicationContext(), "File downloaded ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

    /*public void download(String url)
    {
        new DownloadFile().execute(getIntent().getStringExtra("pdfUrl").toString(), "sclas.pdf");
    }
    downloadFile(String dwnload_file_path) {

    }*/

   /* void downloadFile(){

        try {

            progressBar = new ProgressDialog(getApplicationContext());
            progressBar.setCancelable(true);
            progressBar.setMessage("File downloading ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();


            URL url = new URL(getIntent().getStringExtra("pdfUrl").toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            //connect
            urlConnection.connect();

            //set the path where we want to save the file
            File SDCardRoot = Environment.getExternalStorageDirectory();
            //create a new file, to save the downloaded file
            File file = new File(SDCardRoot,"file.pdf");

            FileOutputStream fileOutput = new FileOutputStream(file);

            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            //this is the total size of the file which we are downloading
            totalSize = urlConnection.getContentLength();

            runOnUiThread(new Runnable() {
                public void run() {
                    progressBar.setMax(totalSize);
                }
            });

            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                // update the progressbar //
                runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setProgress(downloadedSize);
                        float per = ((float)downloadedSize/totalSize) * 100;
*//*
                        cur_val.setText("Downloaded " + downloadedSize + "KB / " + totalSize + "KB (" + (int)per + "%)" );
*//*
                    }
                });
            }
            //close the output stream when complete //
            fileOutput.close();
            runOnUiThread(new Runnable() {
                public void run() {
                    progressBar.dismiss(); // if you want close it..
                }
            });

        } catch (final MalformedURLException e) {
            showError("Error : MalformedURLException " + e);
            e.printStackTrace();
        } catch (final IOException e) {
            showError("Error : IOException " + e);
            e.printStackTrace();
        }
        catch (final Exception e) {
            showError("Error : Please check your internet connection " + e);
        }
    }

    void showError(final String err){
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(FootMenuPdfView.this, err, Toast.LENGTH_LONG).show();
            }
        });
    }

    void showProgress(String file_path){

    }
*/

