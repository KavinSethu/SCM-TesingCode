package com.pnf.elar.app;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
/*
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar.LayoutParams;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;

@SuppressWarnings("deprecation")
public class Edit_news extends Fragment {
    String imagePath, VideoPath, i_path, V_path;
    String[] imagename, videonameone, video_imagename, random_imagename,
            random_file_name, name, id, img_vdo, imgsss, videoss;
    ListView im_vdo;
    ArrayList<String> encoded_code = new ArrayList<String>();
    ArrayList<String> encoded_code_video = new ArrayList<String>();
    ArrayAdapter_newsEdit aa;
    Button selectedgroup, publish;
    EditText title_edit, description_edit;
    String post_id;
    LinearLayout upload;
    ViewGroup actionBarLayout;
    LinearLayout actionbar;
    int chooser_click;
    ImageView back;
    UserSessionManager session;
    String lang, auth_token, Base_url;
    private static int LOAD_IMAGE_RESULTS = 1;
    private static int LOAD_Video_RESULTS = 1;
    ArrayList<String> al = new ArrayList<String>();
    View v;
    TextView Recipients, Attach_file;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_edit_news, container, false);

        session = new UserSessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        auth_token = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);

        String news_create = "news_create";
        session.create_main_Screen("");
        session.createnews(news_create);

        try {
            Bundle bundle = this.getArguments();

            post_id = bundle.getString("edit_post_id");

        } catch (Exception e) {
            // TODO: handle exception
        }

        new filterclass().execute();

        ////////// back to Main Activity ////Set title/////
        ((Drawer) getActivity()).setBackFrompublishtomainnews();
        if (lang.equalsIgnoreCase("sw")) {
            ((Drawer) getActivity()).setActionBarTitle("Editera inlägg");
        } else {
            ((Drawer) getActivity()).setActionBarTitle("Edit News post");
        }
        /////////////

        publish = (Button) v.findViewById(R.id.publish);
        selectedgroup = (Button) v.findViewById(R.id.selectedgroup);
        title_edit = (EditText) v.findViewById(R.id.title);
        description_edit = (EditText) v.findViewById(R.id.description);
        upload = (LinearLayout) v.findViewById(R.id.upload);
        im_vdo = (ListView) v.findViewById(R.id.im_vdo);
        Recipients = (TextView) v.findViewById(R.id.Recipients);
        Attach_file = (TextView) v.findViewById(R.id.Attach_file);

        if (lang.equalsIgnoreCase("sw")) {
            Recipients.setText("Mottagare");
            selectedgroup.setText("Valda grupper");
            publish.setText("Publicera");
            Attach_file.setText("Bifoga fil");
        } else {

        }

        im_vdo.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                // TODO Auto-generated method stub
                String title, yes, no;

                if (lang.equalsIgnoreCase("sw")) {
                    title = "är du Säker,du brist till radera punkt ?";
                    yes = "ja";
                    no = "Nej";
                } else {
                    title = "Are you sure,You want to delete item ?";
                    yes = "yes";
                    no = "no";
                }

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());
                alertDialogBuilder
                        .setMessage(title);

                alertDialogBuilder.setPositiveButton(yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) { /////////// new

                                al.remove(position);
                                aa.notifyDataSetChanged();
                                setListViewHeightBasedOnChildrener(im_vdo);

                            }
                        });

                alertDialogBuilder.setNegativeButton(no,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                // Intent i = new
                                // Intent(SettingPage.this,SettingPage.class);
                                // startActivity(i);
                                // finish();
                                // closeContextMenu();

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        publish.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(description_edit.getWindowToken(), 0);

                imgsss = new String[encoded_code.size()];

                for (int i = 0; i < encoded_code.size(); i++) {
                    imgsss[i] = encoded_code.get(i);
                }

                Log.i("Array Size image...", Integer.toString(imgsss.length));

                videoss = new String[encoded_code_video.size()];

                for (int i = 0; i < encoded_code_video.size(); i++) {
                    videoss[i] = encoded_code_video.get(i);
                }
                //	Log.i("Array Size vedio...", Integer.toString(videoss.length));

                new fil().execute();
            }
        });

        upload.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String title;

                if (lang.equalsIgnoreCase("sw")) {
                    title = "Bifoga   filer";
                } else {
                    title = "ATTACH   MEDIA";
                }

                final Dialog dialog = new Dialog(getActivity());
                // Include dialog.xml file
                dialog.setContentView(R.layout.custom_dialog_news);
                // Set dialog title
                dialog.setTitle(title);
                dialog.setCancelable(false);

                Button cancel = (Button) dialog.findViewById(R.id.cancel);

                TextView imageuploadstext = (TextView) dialog.findViewById(R.id.imageuploadstext);
                TextView videouploadtext = (TextView) dialog.findViewById(R.id.videouploadtext);
                LinearLayout uploadImage = (LinearLayout) dialog.findViewById(R.id.uploadImage);
                LinearLayout uploadVedio = (LinearLayout) dialog.findViewById(R.id.uploadVedio);

                if (lang.equalsIgnoreCase("sw")) {
                    imageuploadstext.setText("Bildgalleri");
                    videouploadtext.setText("Videogalleri");
                    cancel.setText("Avbryt");
                }

                dialog.show();

                cancel.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });

                // //////////////////////////////////////////////////////
                uploadImage.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        chooser_click = 1;

                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, LOAD_IMAGE_RESULTS);
                        dialog.dismiss();

                    }
                });

                // ///////////////////////////////////////////////////// //

                // //////////////////////////////////////////
                uploadVedio.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        chooser_click = 2;

                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, LOAD_Video_RESULTS);
                        dialog.dismiss();

                    }
                });

                // //////////////////////////////////////////////////////////////////
                selectedgroup.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        String title;

                        if (lang.equalsIgnoreCase("sw")) {
                            title = "Vald grupper..";
                        } else {
                            title = "Selected Groups";
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                getActivity());
                        builder.setTitle(title);
                        builder.setItems(name,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int item) {

                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();

                    }
                });

                // ///////////////////////////////////////////////////////////////////
            }

        });

        return v;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // //////////////////////////////////////////////////////////////////////////////////////

    class filterclass extends AsyncTask<String, String, String> {

        /*JSONParser jsonParser = new JSONParser();*/
        String title, description;
        String status;
        private MyCustomProgressDialog dialog;
        private String url_create_product = Base_url + "lms_api/news/view_news";

        private static final String TAG_staus = "status";
        private static final String TAG_response = "response";
        private static final String TAG_description = "description";
        private static final String TAG_title = "title";
        private static final String TAG_images = "images";
        private static final String TAG_images_imagename = "imagename";
        private static final String TAG_videos = "videos";
        private static final String TAG_videoname_mp4 = "videoname_mp4";
        private static final String TAG_random_files = "random_files";
        private static final String TAG_imagename = "imagename";
        private static final String TAG_random_file_name = "random_file_name";
        private static final String TAG_video_imagename = "imagename";
        private static final String TAG_groups = "groups";
        private static final String TAG_name = "name";
        private static final String TAG_id = "id";

        /**
         * Before starting background thread Show Progress Dialog
         */
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
            String viewNewsresponse = "";
            try {

                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode("H67jdS7wwfh", "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.ID + "=" + URLEncoder.encode(post_id, "UTF-8");


                viewNewsresponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }
          /*  // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("securityKey", "H67jdS7wwfh"));
            params.add(new BasicNameValuePair("authentication_token",
                    auth_token));
            params.add(new BasicNameValuePair("language", lang));
            params.add(new BasicNameValuePair("id", post_id));
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check for success tag
            Log.i("json data......", json.toString());
            try {
                status = json.getString(TAG_staus);

                Log.e("=-=-=-=-=-=-", status);

                JSONArray response = json.optJSONArray(TAG_response);


                for (int j = 0; j < response.length(); j++) {
                    JSONObject c = response.getJSONObject(j);

                    description = c.getString(TAG_description);
                    title = c.getString(TAG_title);

                    JSONArray images = c.optJSONArray(TAG_images);

                    imagename = new String[images.length()];
                    for (int l = 0; l < images.length(); l++) {
                        JSONObject f = images.getJSONObject(l);
                        //
                        imagename[l] = f.getString(TAG_images_imagename);
                    }
                    Log.i("imagename", Arrays.deepToString(imagename));

                    JSONArray videoname = c.optJSONArray(TAG_videos);

                    videonameone = new String[videoname.length()];
                    video_imagename = new String[videoname.length()];

                    for (int k = 0; k < videoname.length(); k++) {
                        JSONObject o = videoname.getJSONObject(k);
                        //
                        videonameone[k] = o.getString(TAG_videoname_mp4);
                        video_imagename[k] = o.getString(TAG_video_imagename);
                    }
                    Log.i("video_imagename",
                            Arrays.deepToString(video_imagename));



                    JSONArray groups = c.optJSONArray(TAG_groups);

                    name = new String[groups.length()];
                    id = new String[groups.length()];

                    for (int m = 0; m < groups.length(); m++) {
                        JSONObject t = groups.getJSONObject(m);
                        //
                        name[m] = t.getString(TAG_name);
                        id[m] = t.getString(TAG_id);
                    }

                }

                if (status.equalsIgnoreCase("true")) {

                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            return viewNewsresponse;
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
                    if(jsonObject.has(Const.Params.Status))
                    {

                        status = jsonObject.getString(TAG_staus);

                    }



                }

                Log.e("=-=-=-=-=-=-", status);



           /* if (status.equalsIgnoreCase("true")) {

            } else {

            }*/
                if (status.equalsIgnoreCase("true")) {
                    JSONArray response = jsonObject.optJSONArray(TAG_response);


                    for (int j = 0; j < response.length(); j++) {
                        JSONObject c = response.getJSONObject(j);

                        description = c.getString(TAG_description);
                        title = c.getString(TAG_title);

                        JSONArray images = c.optJSONArray(TAG_images);

                        imagename = new String[images.length()];
                        for (int l = 0; l < images.length(); l++) {
                            JSONObject f = images.getJSONObject(l);
                            //
                            imagename[l] = f.getString(TAG_images_imagename);
                        }
                        Log.i("imagename", Arrays.deepToString(imagename));

                        JSONArray videoname = c.optJSONArray(TAG_videos);

                        videonameone = new String[videoname.length()];
                        video_imagename = new String[videoname.length()];

                        for (int k = 0; k < videoname.length(); k++) {
                            JSONObject o = videoname.getJSONObject(k);
                            //
                            videonameone[k] = o.getString(TAG_videoname_mp4);
                            video_imagename[k] = o.getString(TAG_video_imagename);
                        }
                        Log.i("video_imagename",
                                Arrays.deepToString(video_imagename));


                        JSONArray groups = c.optJSONArray(TAG_groups);

                        name = new String[groups.length()];
                        id = new String[groups.length()];

                        for (int m = 0; m < groups.length(); m++) {
                            JSONObject t = groups.getJSONObject(m);
                            //
                            name[m] = t.getString(TAG_name);
                            id[m] = t.getString(TAG_id);
                        }

                    }


                    // //////////////////////////////////////////////////
                    /*if (Build.VERSION.SDK_INT >= 24)
                    {
                        description_edit.setText(Html.fromHtml(description,Html.FROM_HTML_MODE_LEGACY));
                        title_edit.setText(Html.fromHtml(title,Html.FROM_HTML_MODE_LEGACY));


                    }
                    else*/
                    /*{*/
                        description_edit.setText(Html.fromHtml(description));
                        title_edit.setText(Html.fromHtml(title));
/*
                    }
*/
                    img_vdo = combine(imagename, videonameone);

                    for (int i = 0; i < img_vdo.length; i++) {
                        al.add(img_vdo[i]);
                    }

                    Log.i("list size ", Integer.toString(al.size()));

                    // Log.i("121221212121", Arrays.deepToString(img_vdo));

                    aa = new ArrayAdapter_newsEdit(getActivity(), al); // ////////////////////
                    // new
                    // ///////////////
                    im_vdo.setAdapter(aa);
                    aa.notifyDataSetChanged();
                    setListViewHeightBasedOnChildrener(im_vdo);

                    // /////////////////////////////////////////
                }else {
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
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        public String[] combine(String[] a, String[] b) {
            int length = a.length + b.length;
            String[] result = new String[length];
            System.arraycopy(a, 0, result, 0, a.length);
            System.arraycopy(b, 0, result, a.length, b.length);
            return result;
        }

        // //////////////////////////////////////////////////////////////////////////////////////
        protected void setListViewHeightBasedOnChildrener(ListView im_vdo2) {
            // TODO Auto-generated method stub

            ListAdapter listAdapter = im_vdo2.getAdapter();
            if (listAdapter == null)
                return;

            int desiredWidth = MeasureSpec.makeMeasureSpec(im_vdo2.getWidth(),
                    MeasureSpec.UNSPECIFIED);
            int totalHeight = 0;
            View view = null;

            for (int i = 0; i < listAdapter.getCount(); i++) {
                view = listAdapter.getView(i, view, im_vdo2);

                if (i == 0)
                    view.setLayoutParams(new ViewGroup.LayoutParams(
                            desiredWidth, LayoutParams.MATCH_PARENT));

                view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
                totalHeight += view.getMeasuredHeight();

            }

            ViewGroup.LayoutParams params = im_vdo2.getLayoutParams();
            params.height = totalHeight
                    + ((im_vdo2.getDividerHeight()) * (listAdapter.getCount()));

            im_vdo2.setLayoutParams(params);
            im_vdo2.requestLayout();

        }
    }

    // //////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (chooser_click == 1) {
            if (requestCode == LOAD_IMAGE_RESULTS
                    && resultCode == Activity.RESULT_OK && data != null) {
                // Let's read picked image data - its URI
                Uri pickedImage = data.getData();
                // Let's read picked image path using content resolver
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(
                        pickedImage, filePath, null, null, null);
                cursor.moveToFirst();
                imagePath = cursor
                        .getString(cursor.getColumnIndex(filePath[0]));
                i_path = imagePath;

//				Log.i("image_working", "yes..");
//
//				Log.i("imagePathimagePath", imagePath);

                cursor.close();

                // //////////////////////////////////////////

                InputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(imagePath);
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }// You can get an inputStream using any IO API
                byte[] bytes;
                byte[] buffer = new byte[8192];
                int bytesRead;
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                try {
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bytes = output.toByteArray();
                String encodedString = Base64.encodeToString(bytes,
                        Base64.DEFAULT);
                output.reset();

                //	Log.i("base6444444", encodedString);

                encoded_code.add(encodedString);

                // /////////////////////////////////////////

            }
        }

        // /////////////////////////////////////////////////////////////
        if (chooser_click == 2) {
            if (requestCode == LOAD_Video_RESULTS
                    && resultCode == Activity.RESULT_OK && data != null) {
                // Let's read picked image data - its URI
                Uri pickedImage = data.getData();
                // Let's read picked image path using content resolver
                String[] filePath = {MediaStore.Video.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(
                        pickedImage, filePath, null, null, null);
                cursor.moveToFirst();
                imagePath = cursor
                        .getString(cursor.getColumnIndex(filePath[0]));

                // encoded_code_video.

//				Log.i("video_working", "yes..");
//
//				Log.i("videopath...", imagePath);

                cursor.close();

                // ////////////////////////////////////////////

                InputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(imagePath);
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }// You can get an inputStream using any IO API
                byte[] bytes;
                byte[] buffer = new byte[8192];
                int bytesRead;
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                try {
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bytes = output.toByteArray();
                String encodedString_video = Base64.encodeToString(bytes,
                        Base64.DEFAULT);
                output.reset();

                //	Log.i("base64...", encodedString_video);

                encoded_code_video.add(encodedString_video);

                // ///////////////////////////////////////////

            }
        }
        // ///////////////////////////////////////////////////
        // try {
        //
        //
        // if (requestCode == PICK_REQUEST_CODE)
        // {
        // if (resultCode == RESULT_OK)
        // {
        // Uri uri = data.getData();
        //
        // if (uri.getScheme().toString().compareTo("content")==0)
        // {
        // Cursor cursor =getContentResolver().query(uri, null, null, null,
        // null);
        // if (cursor.moveToFirst())
        // {
        // int column_index =
        // cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//Instead
        // of "MediaStore.Images.Media.DATA" can be used "_data"
        // Uri filePathUri = Uri.parse(cursor.getString(column_index));
        // String file_name = filePathUri.getLastPathSegment().toString();
        // String file_path=filePathUri.getPath();
        //
        // Log.i("File url..... ", file_path);
        // //
        // Toast.makeText(this,"File Name & PATH are:"+file_name+"\n"+file_path,
        // Toast.LENGTH_LONG).show();
        // }
        // }
        // }
        // }
        //
        //
        // } catch (Exception e) {
        // // TODO: handle exception
        // }
        //
        //
        //

        // //////////////////////////////////////////////////

        al.add(imagePath);
        // step ii: notify to adapter
        // tvDescr.setText(imagePath);
        aa = new ArrayAdapter_newsEdit(getActivity(), al);
        im_vdo.setAdapter(aa);
        aa.notifyDataSetChanged();
        setListViewHeightBasedOnChildrener(im_vdo);

    }

    // /////////////////////////////////////////////////////////////////////////////////
    protected void setListViewHeightBasedOnChildrener(ListView im_vdo2) {
        // TODO Auto-generated method stub

        ListAdapter listAdapter = im_vdo2.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = MeasureSpec.makeMeasureSpec(im_vdo2.getWidth(),
                MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, im_vdo2);

            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        LayoutParams.MATCH_PARENT));

            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = im_vdo2.getLayoutParams();
        params.height = totalHeight
                + ((im_vdo2.getDividerHeight()) * (listAdapter.getCount()));

        im_vdo2.setLayoutParams(params);
        im_vdo2.requestLayout();

    }

    // ////////////////////////////////////////////////////////////////////////////////////////

    class fil extends AsyncTask<String, String, String> {
        JSONArray grpid;
        JSONArray ss_id;
        JSONArray oimg=new JSONArray();
        JSONArray oimg_video=new JSONArray();
        /*JSONParser jsonParser = new JSONParser();*/

        String status;
        private MyCustomProgressDialog dialog;
        private String url_create_product = Base_url + "lms_api/news/edit_news";
        private static final String TAG_staus = "status";
        String urlParams = "";

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            try {
                oimg = new JSONArray(Arrays.asList(imgsss));
                oimg_video = new JSONArray(Arrays.asList(videoss));


               /* String fg = "{" + "\"authentication_token\"" + ":"
                        + JSONObject.quote(auth_token) + "," + "\"description\""
                        + ":"
                        + JSONObject.quote(description_edit.getText().toString())
                        + "," + "\"random_files\"" + ":" + "[" + "]" + ","
                        + "\"id\"" + ":" + JSONObject.quote(post_id) + ","
                        + "\"language\"" + ":" + JSONObject.quote(lang) + ","
                        + "\"images\"" + ":" + oimg + "," + "\"ids_to_delete\""
                        + ":" + "[" + "]" + "," + "\"title\"" + ":"
                        + JSONObject.quote(title_edit.getText().toString()) + ","
                        + "\"videos\"" + ":" + oimg_video + "}";*/
                JSONObject reqJsonObject = new JSONObject();

                reqJsonObject.put(Const.Params.SecurityKey, "H67jdS7wwfh");
                reqJsonObject.put(Const.Params.AuthToken, auth_token);
                reqJsonObject.put(Const.Params.Description, description_edit.getText().toString().replaceAll("\\n", "<br />"));
                reqJsonObject.put(Const.Params.RandomFiles, new JSONArray());
                reqJsonObject.put(Const.Params.ID, post_id);
                reqJsonObject.put(Const.Params.Language, lang);
                reqJsonObject.put(Const.Params.IMAGES, oimg);
                reqJsonObject.put(Const.Params.IdsToDelete, new JSONArray());
                reqJsonObject.put(Const.Params.Title, title_edit.getText().toString());
                reqJsonObject.put(Const.Params.Videos, oimg_video);
                /*URLEncoder.encode(jsonParam.toString(),"UTF-8")*/
                urlParams = "&" + Const.Params.JsonData + "=" + URLEncoder.encode(reqJsonObject.toString(), "UTF-8");

                Log.e("edit news",reqJsonObject.toString());


                dialog = new MyCustomProgressDialog(getActivity());
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {

            String editNewsResponse = "";
            try {

                editNewsResponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }


			/*try {

				oimg = new JSONArray(Arrays.asList(imgsss));
				oimg_video = new JSONArray(Arrays.asList(videoss));

				String fg = "{" + "\"authentication_token\"" + ":"
						+ JSONObject.quote(auth_token) + "," + "\"description\""
						+ ":"
						+ JSONObject.quote(description_edit.getText().toString())
						+ "," + "\"random_files\"" + ":" + "[" + "]" + ","
						+ "\"id\"" + ":" + JSONObject.quote(post_id) + ","
						+ "\"language\"" + ":" + JSONObject.quote(lang) + ","
						+ "\"images\"" + ":" + oimg + "," + "\"ids_to_delete\""
						+ ":" + "[" + "]" + "," + "\"title\"" + ":"
						+ JSONObject.quote(title_edit.getText().toString()) + ","
						+ "\"videos\"" + ":" + oimg_video + "}";

			} catch (Exception e) {
				// TODO: handle exception
			}

			String fg = "{" + "\"authentication_token\"" + ":"
					+ JSONObject.quote(auth_token) + "," + "\"description\""
					+ ":"
					+ JSONObject.quote(description_edit.getText().toString())
					+ "," + "\"random_files\"" + ":" + "[" + "]" + ","
					+ "\"id\"" + ":" + JSONObject.quote(post_id) + ","
					+ "\"language\"" + ":" + JSONObject.quote(lang) + ","
					+ "\"images\"" + ":" + oimg + "," + "\"ids_to_delete\""
					+ ":" + "[" + "]" + "," + "\"title\"" + ":"
					+ JSONObject.quote(title_edit.getText().toString()) + ","
					+ "\"videos\"" + ":" + oimg_video + "}";

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("jsonData", fg));

			JSONObject json = jsonParser.makeHttpRequest(url_create_product,
					"POST", params);

			// check for success tag
			Log.i("json data......", json.toString());
			try {
				status = json.getString(TAG_staus);

				Log.e("=-=-=-=-=-=-", status);

				if (status.equalsIgnoreCase("true")) {

				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
*/
            return editNewsResponse;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {

                JSONObject jsonObj = new JSONObject();


                if (!results.isEmpty() && results != null) {
                    jsonObj = new JSONObject(results);
                    if (jsonObj.has(Const.Params.Status)) {
                        status = jsonObj.getString(TAG_staus);
                    } else {
                    }
                } else {
                }
                if (status.equalsIgnoreCase("true")) {
                    FragmentManager frfmnr_mngr = getFragmentManager();
                    News_Post post_filtter_fr = new News_Post();
                    FragmentTransaction ft = frfmnr_mngr.beginTransaction();
                    ft.replace(R.id.content_frame, post_filtter_fr);
                    ft.commit();
                }else {
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

    // /////////////////////////////////////////////////////////////////////////////////////
}
