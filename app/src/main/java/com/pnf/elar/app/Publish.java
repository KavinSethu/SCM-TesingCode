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
import java.util.HashSet;
import java.util.List;

/*import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.elar.util.SmartClassUtil;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.pnf.elar.app.Bo.ImageVideoBean;
import com.pnf.elar.app.Bo.RetriveDraftBo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.AppLog;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar.LayoutParams;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

@SuppressWarnings("deprecation")
public class Publish extends Fragment {
    // ImageLoader imageLoader;
    ArrayList<ImageVideoBean> selectedItems;

    ImageLoader imageLoader;
    DisplayImageOptions options;
    ArrayList<String> imageUrls;
    Context cn;
    String imagePath, VideoPath, i_path, V_path;
    String[] curriculamtagids = null;
    String base64, image_or_vedio, from_where;
    /*String[] imgsss, videoss;*/
    String[] other_files;

    JSONArray imageArr, videoArr;

    ArrayList<ImageVideoBean> encoded_code = new ArrayList<ImageVideoBean>();

    ArrayList<String> encoded_code_others = new ArrayList<String>();
    ListView im_vdo;
    String mail_staus = "", FilePath;
    int chooser_click;
    ImageView refresh, serhc, img;
    ArrayList<String> al;
    ArrayAdaptert aa;
    TextView MYAccount;
    UserSessionManager session;
    HashMap<String, String> user;
    String lang, auth_token, Base_url, curiculam_ids;
    private static int LOAD_IMAGE_RESULTS = 1;
    private static int LOAD_Video_RESULTS = 1;
    private static int PICK_REQUEST_CODE = 1;
    private static final int PICKFILE_RESULT_CODE = 1;
    int chk_status;
    String[] grp_id;
    String[] grp_name;
    View v;
    CheckBox notifyMailChk;
    AlertDialog.Builder ad;

    LinearLayout actionbar, postpage, upload, Retrieve_draft;
    ViewGroup actionBarLayout;
    Button groupsBtn, studentsBtn, publishtwo, SAVE_DRAFT;
    boolean[] _selections;
    boolean[] _selectionstwo;
    String[] selectedGroup;
    String[] std_id;
    String[] std_name;
    String[] STDcount;
    TextView CURRICULUM_TAGS, Create, Posts, New_Post, spn3, NOTIFY_BY_MAIL,
            descriptionText, spn4;
    ArrayAdapter<String> adapter;
    EditText descriptionEditText;


    public static HashSet curTagsSetPublish;

    String[] selectedCurTagPublis;

    public String useDraftVal = "";
    public String draftType = "Publish_unchange";

    RetriveDraftBo.DraftsEntity retriveResponse;


    JSONArray idstodelete = new JSONArray();

    String descriptionVal = "";

    String save_as_draft = "0";


    /*
        HashSet<String>  idstoDelte=new HashSet<>();
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_publish, container, false);
        getSessionUserDetails();

        initView(v);

        if (lang.equalsIgnoreCase("sw")) {
            Create.setText("Publisera");
            Posts.setText("Inlägg");
            New_Post.setText("Spara minne");
            spn3.setText("HÄMTA UTKAST");
            groupsBtn.setText("Gruppurval");
            studentsBtn.setText("Användare");
            CURRICULUM_TAGS.setText("Läroplan Taggar");
            NOTIFY_BY_MAIL.setText("Notifiera med epost");
            descriptionText.setText("Beskrivning"); ///////////////// working
            spn4.setText("Bifoga fil");
            SAVE_DRAFT.setText("SPARA UTKAST");
            publishtwo.setText("PUBLICERA");

        } else {

        }


        // ///////////


        curTagsSetPublish = new HashSet();


        getDraftValues();

        setImageVideoClick();

        new filterclass().execute();
        al = new ArrayList<String>();
        Retrieve_draft.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                FragmentManager fragmentManager = getFragmentManager();
                SaveDraft Setting_frg = new SaveDraft();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, Setting_frg);
                ft.commit();

            }
        });


        SAVE_DRAFT.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                save_as_draft = "1";
                from_where = "save_draft";

                if (chk_status == 1) {
                    mail_staus = "yes";
                } else {
                    mail_staus = "no";
                }
/*
                imgsss = new String[encoded_code.size()];
*/


                imageArr = new JSONArray();
                videoArr = new JSONArray();
                for (int i = 0; i < encoded_code.size(); i++) {

                    ImageVideoBean imageBean = encoded_code.get(i);

                    if (imageBean.getType().equalsIgnoreCase("image")) {

                        if (imageBean.getId().equalsIgnoreCase("0")) {

                            imageArr.put(imageBean.getName());
                        }
                    }

                }


/*
                videoss = new String[encoded_code_video.size()];
*/

                for (int i = 0; i < encoded_code.size(); i++) {
                    ImageVideoBean imageBean = encoded_code.get(i);

                    if (imageBean.getType().equalsIgnoreCase("video")) {

                        if (imageBean.getId().equalsIgnoreCase("0")) {

                            videoArr.put(imageBean.getName());
                        }
                    }
                }

                other_files = new String[encoded_code_others.size()];

                for (int i = 0; i < encoded_code_others.size(); i++) {
                    other_files[i] = encoded_code_others.get(i);
                }

                try {
                    curiculam_ids = session.getCur_tag_ids();


                    selectedCurTagPublis = new String[curTagsSetPublish.size()];
                    List<String> curmTagList = new ArrayList<String>(curTagsSetPublish);

                    selectedCurTagPublis = curmTagList.toArray(new String[curmTagList.size()]);
                    descriptionVal = descriptionEditText.getText().toString();


                    if (selectedCurTagPublis == null) {
                        selectedCurTagPublis = new String[0];
                    }
                    // Log.i("curriculamtagids",
                    // Arrays.deepToString(curriculamtagids));
                } catch (Exception e) {
                    // TODO: handle exception
                }

                if (useDraftVal.length() == 0) {

                    new Save_post_draft().execute();
                } else {

                    new PublishDraftUnchange().execute();

                }


            }
        });


        publishtwo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                save_as_draft = "0";
                try {


                    if (useDraftVal.length() == 0) {

                        setPublishValues();
                        new Save_post_draft().execute();

                    } else {

                        if (draftType.equalsIgnoreCase("Publish_unchange")) {


                        } else {
                            setPublishValues();

                        }

                        new PublishDraftUnchange().execute();

                    }


                } catch (Exception e) {
                    // TODO: handle exception
                }

            }
        });

        notifyMailChk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!notifyMailChk.isChecked()) {
                    chk_status = 0;
                } else if (notifyMailChk.isChecked()) {
                    chk_status = 1;
                }

                if (useDraftVal.length() > 0) {
                    draftType = "publish_updated_draft";
                }

            }
        });

        CURRICULUM_TAGS.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent in = new Intent(getActivity(), Curriculum_tags.class);
                startActivity(in);

            }
        });

        postpage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                FragmentManager fragmentManager = getFragmentManager();
                MainActivity Setting_frg = new MainActivity();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, Setting_frg);
                ft.commit();
            }
        });

        setGroupStudentClick();
        return v;
    }

    public void setPublishValues() {

        from_where = "save_post";

        if (chk_status == 1) {
            mail_staus = "yes";
        } else {
            mail_staus = "no";
        }

        // Log.i("mail_Status....", mail_staus);
        //
        // Log.i("list Size...", Integer.toString(encoded_code.size()));

        imageArr = new JSONArray();
        videoArr = new JSONArray();
        for (int i = 0; i < encoded_code.size(); i++) {

            ImageVideoBean imageBean = encoded_code.get(i);

            if (imageBean.getType().equalsIgnoreCase("image")) {

                if (imageBean.getId().equalsIgnoreCase("0")) {

                    imageArr.put(imageBean.getName());
                }
            }

        }

        for (int i = 0; i < encoded_code.size(); i++) {
            ImageVideoBean imageBean = encoded_code.get(i);

            if (imageBean.getType().equalsIgnoreCase("video")) {

                if (imageBean.getId().equalsIgnoreCase("0")) {

                    videoArr.put(imageBean.getName());
                }
            }
        }

        other_files = new String[encoded_code_others.size()];

        for (int i = 0; i < encoded_code_others.size(); i++) {
            other_files[i] = encoded_code_others.get(i);
        }
        curiculam_ids = session.getCur_tag_ids();
        descriptionVal = descriptionEditText.getText().toString();
    }

    public void getSessionUserDetails() {
        session = new UserSessionManager(getActivity());
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        auth_token = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        selectedItems = new ArrayList<ImageVideoBean>();
        String publish_screen = "publish_screen";
        session.create_main_Screen("");
        session.createpublish(publish_screen);
    }

    public void initView(View v) {

        MYAccount = (TextView) v.findViewById(R.id.text1);
        refresh = (ImageView) v.findViewById(R.id.refresh);
        serhc = (ImageView) v.findViewById(R.id.serhc);
        publishtwo = (Button) v.findViewById(R.id.publishtwo);
        groupsBtn = (Button) v.findViewById(R.id.groupsBtn);
        postpage = (LinearLayout) v.findViewById(R.id.postpage);
        studentsBtn = (Button) v.findViewById(R.id.studentsBtn);
        CURRICULUM_TAGS = (TextView) v.findViewById(R.id.CURRICULUM_TAGS);
        notifyMailChk = (CheckBox) v.findViewById(R.id.notifyMailChk);
        upload = (LinearLayout) v.findViewById(R.id.upload);
        im_vdo = (ListView) v.findViewById(R.id.im_vdo);
        descriptionEditText = (EditText) v.findViewById(R.id.descriptionEditText);
        Create = (TextView) v.findViewById(R.id.Create);
        Posts = (TextView) v.findViewById(R.id.Posts);
        New_Post = (TextView) v.findViewById(R.id.New_Post);
        spn3 = (TextView) v.findViewById(R.id.retriveDraftText);

        NOTIFY_BY_MAIL = (TextView) v.findViewById(R.id.NOTIFY_BY_MAIL);
        descriptionText = (TextView) v.findViewById(R.id.descriptionText);
        spn4 = (TextView) v.findViewById(R.id.spn4);
        SAVE_DRAFT = (Button) v.findViewById(R.id.SAVE_DRAFT);

        Retrieve_draft = (LinearLayout) v.findViewById(R.id.Retrieve_draft);

        // //////// back to Main Activity ////Set title/////
        ((Drawer) getActivity()).setBackFrompublishtomain();
        if (lang.equalsIgnoreCase("sw")) {
            ((Drawer) getActivity()).setActionBarTitle("Publicera");
        } else {
            ((Drawer) getActivity()).setActionBarTitle("Publish");
        }
        ((Drawer) getActivity()).Hideserch();
        ((Drawer) getActivity()).HideRefresh();
    }

    public void getDraftValues() {
        try {
            Bundle bundle = Publish.this.getArguments();
            if (bundle != null) {
                useDraftVal = bundle.getString("draftObject");


                retriveResponse = new Gson().fromJson(useDraftVal, RetriveDraftBo.DraftsEntity.class);

                setValues();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setValues() {
       /* if (Build.VERSION.SDK_INT >= 24) {

            descriptionEditText.setText(Html.fromHtml(retriveResponse.getPictureDiary().getDescription(), Html.FROM_HTML_MODE_LEGACY));
        }
        else
        {*/
        descriptionEditText.setText(Html.fromHtml(retriveResponse.getPictureDiary().getDescription()));
      /*  }*/
        curTagsSetPublish = new HashSet();

        if (retriveResponse.getPicturediaryKnowledgeArea().size() > 0) {

            for (int c = 0; c < retriveResponse.getPicturediaryKnowledgeArea().size(); c++) {

                curTagsSetPublish.add(retriveResponse.getPicturediaryKnowledgeArea().get(c).getKnowledgeArea().getId());
            }


            setCurriculamTag();
        }

        if (retriveResponse.getPictureDiary().getNotify_email().equalsIgnoreCase("0")) {
            notifyMailChk.setChecked(false);

        } else {
            notifyMailChk.setChecked(true);
        }


        if (retriveResponse.getImages().size() > 0) {
/*
            selectedItems.add(0, imagePath);
*/

            for (int i = 0; i < retriveResponse.getImages().size(); i++) {


                selectedItems.add(new ImageVideoBean(retriveResponse.getImages().get(i).getId(), retriveResponse.getImages().get(i).getImagename(), "image"));
                encoded_code.add(new ImageVideoBean(retriveResponse.getImages().get(i).getId(), retriveResponse.getImages().get(i).getImagename(), "image"));

            }
            aa = new ArrayAdaptert(getActivity(), selectedItems);


            im_vdo.setAdapter(aa);
            aa.notifyDataSetChanged();
            setListViewHeightBasedOnChildrener(im_vdo);
        }
        if (retriveResponse.getVideos().size() > 0) {


            for (int i = 0; i < retriveResponse.getVideos().size(); i++) {
                selectedItems.add(new ImageVideoBean(retriveResponse.getVideos().get(i).getId(), retriveResponse.getVideos().get(i).getVideoname_mp4(), "video"));
                encoded_code.add(new ImageVideoBean(retriveResponse.getVideos().get(i).getId(), retriveResponse.getVideos().get(i).getVideoname_mp4(), "video"));
            }
            aa = new ArrayAdaptert(getActivity(), selectedItems);


            im_vdo.setAdapter(aa);
            aa.notifyDataSetChanged();
            setListViewHeightBasedOnChildrener(im_vdo);
        }




        /*else {
            aa = new ArrayAdaptert(getActivity(), selectedItems);
        }*/


    }


    public void setImageVideoClick() {
        im_vdo.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int arg2, long arg3) {

                String title, yes, no;

                if (lang.equalsIgnoreCase("sw")) {
                    title = "Vill du ta bort vald fil?";
                    yes = "ja";
                    no = "Nej";
                } else {
                    title = "Do you want to remove selected file?";
                    yes = "yes";
                    no = "no";
                }


                if (encoded_code.size() > 0) {
                    String itemdata = encoded_code.get(arg2).getId();
                }

                // al.remove(arg2);
                // Toast.makeText(getApplicationContext(), item, 0).show();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());
                alertDialogBuilder.setMessage(title);

                alertDialogBuilder.setPositiveButton(yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                                String itemId = selectedItems.get(arg2).getId();
                                if (!itemId.equalsIgnoreCase("0")) {
                                    idstodelete.put(itemId);
                                }
                                selectedItems.remove(arg2);
                                encoded_code.remove(arg2);


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
                                // ////////////////////// closeContextMenu();

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

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
                dialog.setContentView(R.layout.custom_dialog);
                // Set dialog title
                dialog.setTitle(title);
                dialog.setCancelable(false);

                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                TextView imageuploadstext = (TextView) dialog
                        .findViewById(R.id.imageuploadstext);
                TextView videouploadtext = (TextView) dialog
                        .findViewById(R.id.videouploadtext);
                LinearLayout uploadImage = (LinearLayout) dialog
                        .findViewById(R.id.uploadImage);
                LinearLayout uploadVedio = (LinearLayout) dialog
                        .findViewById(R.id.uploadVedio);

                if (lang.equalsIgnoreCase("sw")) {
                    imageuploadstext.setText("Bildgalleri");
                    videouploadtext.setText("Videogalleri");
                    cancel.setText("Avbryt");
                }
                // ImageView UploadFiles = (ImageView) dialog
                // .findViewById(R.id.UploadFiles);

                dialog.show();

                cancel.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });

                // //////////////////////////
                uploadImage.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();

                        image_or_vedio = "image";

                        final Dialog Forimages = new Dialog(getActivity());
                        // Include dialog.xml file
                        Forimages.setContentView(R.layout.ac_image_grid);
                        // Set dialog title
                        if (lang.equalsIgnoreCase("sw")) {
                            Forimages.setTitle("Bildgalleri..");
                        } else {
                            Forimages.setTitle("Images..");
                        }
                        Forimages.setCancelable(false);

                        final ImageAdapter imageAdapter;

                        final String[] columns = {
                                MediaStore.Images.Media.DATA,
                                MediaStore.Images.Media._ID};
                        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
                        Cursor imagecursor = getActivity().managedQuery(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                columns, null, null, orderBy + " DESC");

                        imageUrls = new ArrayList<String>();

                        for (int i = 0; i < imagecursor.getCount(); i++) {
                            imagecursor.moveToPosition(i);
                            int dataColumnIndex = imagecursor
                                    .getColumnIndex(MediaStore.Images.Media.DATA);
                            imageUrls.add(imagecursor
                                    .getString(dataColumnIndex));

                            // System.out.println("=====> Array path => "+imageUrls.get(i));
                        }

                        options = new DisplayImageOptions.Builder()
                                .showStubImage(R.drawable.stub_image)
                                .showImageForEmptyUri(
                                        R.drawable.image_for_empty_url)
                                .cacheInMemory().cacheOnDisc().build();

                        imageAdapter = new ImageAdapter(getActivity(),
                                imageUrls);

                        GridView gridView = (GridView) Forimages
                                .findViewById(R.id.gridview);
                        gridView.setAdapter(imageAdapter);

                        Button b1 = (Button) Forimages
                                .findViewById(R.id.button1);
                        if (lang.equalsIgnoreCase("sw")) {
                            b1.setText("Välj");
                        }

                        b1.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
/*
                                selectedItems = imageAdapter.getCheckedItems();
*/

                                List<ImageVideoBean> tempImageList = imageAdapter.getCheckedItems();

                                // ///////
                                int siz = tempImageList.size();
                                for (int i = 0; i < siz; i++) {
                                    InputStream inputStream = null;
                                    try {
                                        inputStream = new FileInputStream(
                                                tempImageList.get(i).getName());
                                    } catch (FileNotFoundException e1) {
                                        // TODO Auto-generated catch block
                                        e1.printStackTrace();
                                    }// You can get an inputStream using any IO
                                    // API
                                    // Bitmap tt =
                                    // BitmapFactory.decodeFile(imagePath);
                                    // ////// new
                                    Bitmap tt = BitmapHelper.decodeFile(
                                            tempImageList.get(i).getName(), 50, 50, true);
                                    byte[] bytes;
                                    byte[] buffer = new byte[8192];
                                    int bytesRead;
                                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                                    try {
                                        while ((bytesRead = inputStream
                                                .read(buffer)) != -1) {
                                            output.write(buffer, 0, bytesRead);
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    // bytes = output.toByteArray();
                                    tt.compress(CompressFormat.JPEG, 20, output); // ///
                                    // new

                                    String encodedString = null;

                                    try {
                                        bytes = output.toByteArray();
                                        encodedString = Base64.encodeToString(
                                                bytes, Base64.DEFAULT);

                                        Log.i("encodedString", encodedString);
                                    } catch (OutOfMemoryError e) {
                                        // TODO: handle exception
                                    } catch (Exception e) {
                                        // TODO: handle exception
                                    }

                                    encoded_code.add(new ImageVideoBean("0", encodedString, "image"));
                                }

                                selectedItems.addAll(tempImageList);
                                Log.i("encoded_code",
                                        Integer.toString(encoded_code.size()));

                                imgvedioAddtolist();

//								aa = new ArrayAdaptert(getActivity(),
//										selectedItems);
//								im_vdo.setAdapter(aa);
//								aa.notifyDataSetChanged();
//								setListViewHeightBasedOnChildrener(im_vdo);
                                Forimages.dismiss();
                            }
                        });
                        //
                        Forimages.show();

                    }
                });

                // ///////////////////////////////////
                uploadVedio.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        image_or_vedio = "vedio";

                        chooser_click = 2;

                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, LOAD_Video_RESULTS);
                        dialog.dismiss();

                    }
                });

            }
        });

    }

    public void setGroupStudentClick() {
        groupsBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String title, ok, Cancel;

                if (lang.equalsIgnoreCase("sw")) {
                    title = "Valda grupper";
                    ok = "Klar";
                    Cancel = "Avbryt";
                } else {
                    title = "Groups..";
                    ok = "Ok";
                    Cancel = "Cancel";
                }

                ad = new AlertDialog.Builder(getActivity());
                ad.setTitle(title);

                ad.setMultiChoiceItems(grp_name, _selections,
                        new OnMultiChoiceClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1,
                                                boolean arg2) {
                                // TODO Auto-generated method stub

                            }
                        });
                ad.setPositiveButton(ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        printSelectedPlanets();

                        if (selectedGroup != null && selectedGroup.length > 0) {
                            if (selectedGroup[0].equalsIgnoreCase("all")) {
                                if (lang.equalsIgnoreCase("sw")) {
                                    groupsBtn.setText("Alla");
                                    studentsBtn.setText("Alla");

                                } else {
                                    groupsBtn.setText("All");
                                    studentsBtn.setText("All");
                                }

                                new getstudent().execute();
                            } else {
                                if (lang.equalsIgnoreCase("sw")) {
                                    groupsBtn.setText(Integer.toString(selectedGroup.length) + " Grupp");
                                    studentsBtn.setText("Användare");
                                } else {
                                    groupsBtn.setText(Integer.toString(selectedGroup.length) + " Group Select");
                                    studentsBtn.setText("Users");
                                }

                                new getstudent().execute();
                            }


                        } else {

                            if (lang.equalsIgnoreCase("sw")) {
                                groupsBtn.setText("Gruppurval");
                                //studentsBtn.setText("Användare");
                            } else {
                                groupsBtn.setText("Groups");
                                //studentsBtn.setText("Users");
                            }


                        }

                        if (useDraftVal.length() > 0) {
                            draftType = "publish_updated_draft";
                        }
                        // Studnts.setText("Student Selected :");

                    } // //okej //// Avbryt

                    // //////////////////////////////////////////////////////////////////////////////////////
                    private void printSelectedPlanets() {
                        // TODO Auto-generated method stub
                        String[] d = new String[_selections.length];
                        int count = 0;
                        for (int i = 0; i < grp_name.length; i++) {

                            if (_selections[i] == true) {
                                Log.i("true", Integer.toString(i));
                                d[i] = Integer.toString(i);
                                count = count + 1;
                                // hm.add( Integer.toString(i));
                            }
                        }
                        Log.i("tttttt", Arrays.deepToString(d));

                        Log.i("hhhhhhhhhhh", Integer.toString(count));

                        String[] p = new String[count];
                        int j = 0;
                        for (int i = 0; i < d.length; i++) {

                            if (!(d[i] == null)) {
                                p[j] = d[i];
                                j++;

                            }

                        }
                        Log.i("@@@@@@@@@@@@@@", Arrays.deepToString(p));
                        // Log.i("ppppppppppppp", p[0]);
                        selectedGroup = new String[count];
                        String n;
                        int g;
                        for (int i = 0; i < count; i++) {
                            n = p[i];
                            g = Integer.parseInt(n);

                            selectedGroup[i] = grp_id[g];

                        }
                        Log.i("=============", Arrays.deepToString(selectedGroup));
                        // grps.setText(count + "  Groups Selected:");

                    }
                    // //////////////////////////////////////////////////////////////////////////////////////

                });
                ad.setNegativeButton(Cancel,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub

                            }
                        });
                ad.show();

            }
        });

        studentsBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String title, ok, cancel;

                if (lang.equalsIgnoreCase("sw")) {
                    title = "Individuellt taggade individer";
                    ok = "Klar";
                    cancel = "Avbryt";
                } else {
                    title = "Groups..";
                    ok = "Ok";
                    cancel = "Cancel";
                }

                if (!groupsBtn.getText().toString().equalsIgnoreCase("all")) {
                    ad = new AlertDialog.Builder(getActivity());
                    ad.setTitle(title);

                    ad.setMultiChoiceItems(std_name, _selectionstwo,
                            new OnMultiChoiceClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1,
                                                    boolean arg2) {
                                    // TODO Auto-generated method stub

                                }
                            });
                    ad.setPositiveButton(ok,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    try {
                                        printSelectedPlanetstwo();
                                        if (useDraftVal.length() > 0) {
                                            draftType = "publish_updated_draft";
                                        }
                                    } catch (Exception e) {
                                        // TODO: handle exception
                                    }

                                }

                                // /////////////////////////////////////////////////////////////////////////////////
                                private void printSelectedPlanetstwo() {
                                    // TODO Auto-generated method stub
                                    String[] d = new String[_selectionstwo.length];
                                    int count = 0;
                                    for (int i = 0; i < std_name.length; i++) {
                                        Log.i("ME", std_name[i] + " selected: "
                                                + _selectionstwo[i] + i);

                                        if (_selectionstwo[i] == true) {
                                            Log.i("true", Integer.toString(i));
                                            d[i] = Integer.toString(i);
                                            count = count + 1;
                                            // hm.add( Integer.toString(i));
                                        }
                                    }
                                    // Log.i("tttttt", Arrays.deepToString(d));
                                    //
                                    // Log.i("hhhhhhhhhhh",
                                    // Integer.toString(count));

                                    String[] p = new String[count];
                                    int j = 0;
                                    for (int i = 0; i < d.length; i++) {

                                        if (!(d[i] == null)) {
                                            p[j] = d[i];
                                            j++;

                                        }

                                    }
                                    // Log.i("@@@@@@@@@@@@@@",
                                    // Arrays.deepToString(p));
                                    // Log.i("ppppppppppppp", p[0]);
                                    STDcount = new String[count];
                                    String n;
                                    int g;
                                    for (int i = 0; i < count; i++) {
                                        n = p[i];
                                        g = Integer.parseInt(n);

                                        STDcount[i] = std_id[g];

                                    }
                                    // Log.i("=============",
                                    // Arrays.deepToString(STDcount));

                                    if (lang.equalsIgnoreCase("sw")) {
                                        studentsBtn.setText(Integer.toString(STDcount.length) + "  Användare");

                                    } else {
                                        studentsBtn.setText(Integer.toString(STDcount.length) + "  User Selected");
                                    }


                                }

                                // ///////////////////////////////////////////////////////////////////////////
                            });
                    ad.setNegativeButton(cancel,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub

                                }
                            });
                    ad.show();
                }


            }
        });

    }
    // ////////////////////////////////////////////////////////////////////////////////////////////////

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

    public void setCurriculamTag() {
        try {


            if (curTagsSetPublish.size() > 0) {

                CURRICULUM_TAGS.setText(curTagsSetPublish.size() + " " + getActivity().getString(R.string.CURRICULUM_TAGS));


            } else {
                CURRICULUM_TAGS.setText(getActivity().getString(R.string.CURRICULUM_TAGS));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (CURRICULUM_TAGS != null) {


            if (useDraftVal.length() > 0) {
                if (retriveResponse.getPicturediaryKnowledgeArea().size() < curTagsSetPublish.size()) {
                    draftType = "publish_updated_draft";

                }
            }
            setCurriculamTag();
        }
    }
    // ////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String error = "noerror";
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
                cursor.close();

            }

        }

        // /////////////////////////////////////////////////////////////
        if (chooser_click == 2) {
            if (requestCode == LOAD_Video_RESULTS
                    && resultCode == Activity.RESULT_OK && data != null) {
                // Let's read picked image data - its URI
                try {
                    Uri pickedImage = data.getData();
                    // Let's read picked image path using content resolver
                    String[] filePath = {MediaStore.Video.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(
                            pickedImage, filePath, null, null, null);
                    cursor.moveToFirst();
                    imagePath = cursor.getString(cursor
                            .getColumnIndex(filePath[0]));
                    cursor.close();
                    InputStream inputStream = new FileInputStream(imagePath);

                    byte[] bytes;
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    ByteArrayOutputStream output = new ByteArrayOutputStream();

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }

                    bytes = output.toByteArray();
                    String encodedString_video = Base64.encodeToString(bytes,
                            Base64.DEFAULT);
                    output.reset();
                    Log.i("base64...", encodedString_video);
                    encoded_code.add(new ImageVideoBean("0", encodedString_video, "video"));

                    /*selectedItems.add(new ImageVideoBean())*/
                } catch (Error e) {
                    // TODO: handle exception


                    String erMsg = "";

                    if (lang.equalsIgnoreCase("en")) {
                        erMsg = "incompatible video";
                    } else {
                        erMsg = "oförenliga video";
                    }
                    Toast.makeText(getActivity(), erMsg, Toast.LENGTH_SHORT)
                            .show();

                    error = "error";

                } catch (IOException e) {
                    // TODO: handle exception
                }

            }
        }

        try {
            if (error.equalsIgnoreCase("error")) {

            }
            if (error.equalsIgnoreCase("noerror")) {
                imgvedioAddtolist();
            }


        } catch (NullPointerException e) {
            // TODO: handle exception
        } catch (Exception e) {
            // TODO: handle exception
        }


//		selectedItems.add(0, imagePath);
//		aa = new ArrayAdaptert(getActivity(), selectedItems);
//		im_vdo.setAdapter(aa);
//		aa.notifyDataSetChanged();
//		setListViewHeightBasedOnChildrener(im_vdo);

    }

    private void imgvedioAddtolist() {
        // TODO Auto-generated method stub


        if (useDraftVal.length() > 0) {
            draftType = "publish_updated_draft";

        }
        if (image_or_vedio.equalsIgnoreCase("vedio")) {
            selectedItems.add(0, new ImageVideoBean("0", imagePath, "video"));
            aa = new ArrayAdaptert(getActivity(), selectedItems);
        } else {
            aa = new ArrayAdaptert(getActivity(), selectedItems);
        }


        im_vdo.setAdapter(aa);
        aa.notifyDataSetChanged();
        setListViewHeightBasedOnChildrener(im_vdo);

    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    class filterclass extends AsyncTask<String, String, String> {
        String posterror = "no";
        JSONObject jsonfilterclass = new JSONObject();
        /*JSONParser jsonParser = new JSONParser();*/

        String status;
        private MyCustomProgressDialog dialog;
        private String url_create_product = Base_url
                + "lms_api/picture_diary/filter_view";

        private static final String TAG_staus = "status";
        private static final String TAG_groups = "groups";
        private static final String TAG_groups_id = "id";
        private static final String TAG_groups_name = "name";
        private static final String TAG_curriculum_tags = "curriculum_tags";
        private static final String TAG_curriculum_id = "id";
        private static final String TAG_curriculum_title = "title";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new MyCustomProgressDialog(getActivity());
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        protected String doInBackground(String... args) {


            String filterResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode("H67jdS7wwfh", "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8");


                filterResponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }
           /* List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("securityKey", "H67jdS7wwfh"));
            params.add(new BasicNameValuePair("authentication_token",
                    auth_token));
            params.add(new BasicNameValuePair("language", lang));

            try {
                jsonfilterclass = jsonParser.makeHttpRequest(
                        url_create_product, "POST", params);


            } catch (Exception e) {
                // TODO: handle exception
            }

            // check for success tag
*/

            return filterResponse;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String result) {
            // dismiss the dialog once done
            try {
                dialog.dismiss();

                if (result != null && !result.isEmpty()) {
                    jsonfilterclass = new JSONObject(result);
                    if (jsonfilterclass.has(Const.Params.Status)) {
                        status = jsonfilterclass.getString(TAG_staus);
                    } else {
                        status = "false";
                    }


                } else {


                    status = "false";
                }

       /*         if (posterror.equalsIgnoreCase("yes")) {

                } else {*/
                // Log.i("oooooo", imm[0]);


                if (status.equalsIgnoreCase("true")) {


                    Log.i("json data......", jsonfilterclass.toString());
                    try {
                        status = jsonfilterclass.getString(TAG_staus);

                        Log.e("=-=-=-=-=-=-", status);
                        try {
                            JSONArray grps_name = jsonfilterclass
                                    .optJSONArray(TAG_groups);

                            grp_id = new String[grps_name.length()];
                            grp_name = new String[grps_name.length()];

                            _selections = new boolean[grps_name.length()];


                            if (useDraftVal.trim().length() == 0) {


                            } else {


                                if (retriveResponse.getPicturediaryGroup().size() > 0) {
                                    selectedGroup = new String[retriveResponse.getPicturediaryGroup().size()];
                                    groupsBtn.setText(retriveResponse.getPicturediaryGroup().size() + " " + getString(R.string.groups));

                                    for (int s = 0; s < retriveResponse.getPicturediaryGroup().size(); s++) {

                                        selectedGroup[s] = retriveResponse.getPicturediaryGroup().get(s).getClaClass().getId();

                                    }

                                }


                            }
                            for (int j = 0; j < grps_name.length(); j++) {
                                JSONObject c = grps_name.getJSONObject(j);

                                grp_id[j] = c.getString(TAG_groups_id);

                                if (useDraftVal.trim().length() == 0) {


                                } else {

                                    if (checkGroup(c.getString(TAG_groups_id))) {
                                        _selections[j] = checkGroup(c.getString(TAG_groups_id));

                                    }


                                }
                                grp_name[j] = c.getString(TAG_groups_name);
                            }


                            JSONArray curriculum_tag = jsonfilterclass
                                    .optJSONArray(TAG_curriculum_tags);

                            for (int j = 0; j < curriculum_tag.length(); j++) {
                                JSONObject d = curriculum_tag.getJSONObject(j);

                            }


                            if (useDraftVal.trim().length() == 0) {


                            } else {

                                if (retriveResponse.getPicturediaryGroup().size() > 0) {
                                    new getstudent().execute();


                                }


                            }
                            if (status.equalsIgnoreCase("true")) {

                            } else {

                            }
                        } catch (JSONException e) {
                            Log.i("internal error", "error..");
                            posterror = "yes";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                 /*   }
                }*/
                } else {
                    try {


                        String msg = jsonfilterclass.getString("message");
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

    public boolean checkGroup(String groupId) {

        boolean bl_grp = false;
        if (retriveResponse.getPicturediaryGroup().size() > 0) {

            for (int i = 0; i < retriveResponse.getPicturediaryGroup().size(); i++) {

                if (retriveResponse.getPicturediaryGroup().get(i).getClaClass().getId().equalsIgnoreCase(groupId)) {
                    bl_grp = true;

                }

            }
        }

        return bl_grp;

    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class getstudent extends AsyncTask<String, String, String> {

        /*JSONParser jsonParser = new JSONParser();*/

        String status;
        private MyCustomProgressDialog dialog;
        private String url_create_product = Base_url
                + "lms_api/picture_diary/get_group_students";

        private static final String TAG_staus = "status";
        private static final String TAG_students = "students";
        private static final String TAG_student_id = "id";
        private static final String TAG_Student_name = "name";

        JSONObject studentjson;

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
            // String qe="student_ids";
            String studResponse = "";
            try {
                JSONArray mJSONArray = new JSONArray(Arrays.asList(selectedGroup));
                // Log.i("w array...", Arrays.deepToString(w));
                // {"group_ids":["66","40"],"securityKey":"H67jdS7wwfh","language":"en","authentication_token":"a0790005b5646c244434da977cd8cd94beb04baf"}
                String ui = "{" + "\"group_ids\"" + ":" + mJSONArray + ","
                        + "\"securityKey\"" + ":" + "\"H67jdS7wwfh\"" + ","
                        + "\"language\"" + ":" + JSONObject.quote(lang) + ","
                        + "\"authentication_token\"" + ":"
                        + JSONObject.quote(auth_token) + "}";

                AppLog.Log("stu ", ui);



              /*  // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("jsonData", ui));

                studentjson = jsonParser.makeHttpRequest(
                        url_create_product, "POST", params);

                // check for success tag
                Log.i("json data......", studentjson.toString());
                Log.i("ui data......", ui);*/
                try {
                    String urlParams = "&" + Const.Params.JsonData + "=" + URLEncoder.encode(ui, "UTF-8");


                    studResponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Log.e("=-=-=-=-=-=-", status);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return studResponse;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();

            try {

                if (results != null && !results.isEmpty()) {


                    studentjson = new JSONObject(results);

                    if (studentjson.has(Const.Params.Status)) {
                        status = studentjson.getString(TAG_staus);
                    } else {
                        status = "false";
                    }


                } else {

                    status = "false";

                }


                // Log.i("oooooo", imm[0]);
                if (status.equalsIgnoreCase("true")) {
                    JSONArray studentName = studentjson.optJSONArray(TAG_students);

                    std_id = new String[studentName.length()];
                    std_name = new String[studentName.length()];
                    _selectionstwo = new boolean[studentName.length()];
                    if (useDraftVal.trim().length() == 0) {

                    } else {


                        if (retriveResponse.getPicturediaryStudent().size() > 0) {
                            studentsBtn.setText(retriveResponse.getPicturediaryStudent().size() + " " + getString(R.string.users));
                            STDcount = new String[retriveResponse.getPicturediaryStudent().size()];

                            for (int st = 0; st < retriveResponse.getPicturediaryStudent().size(); st++) {

                                STDcount[st] = retriveResponse.getPicturediaryStudent().get(st).getStudent().getId();
                            }
                        }
                    }


                    for (int j = 0; j < studentName.length(); j++) {
                        JSONObject c = studentName.getJSONObject(j);
                        //
                        std_id[j] = c.getString(TAG_student_id);
                        std_name[j] = c.getString(TAG_Student_name);
                        if (useDraftVal.trim().length() == 0) {

                        } else {

                            _selectionstwo[j] = checkStudents(c.getString(TAG_student_id));
                        }

                        // Log.i("std_id..", std_id[j]);
                        // Log.i("std_name", std_name[j]);

                    }
                } else {

                    try {


                        String msg = studentjson.getString("message");
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


                    // failed to create product
                    Toast.makeText(getActivity(), "Group cannot be empty....",
                            Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                // TODO: handle exception
            }

        }
    }

    public boolean checkStudents(String groupId) {

        boolean bl_grp = false;


        if (retriveResponse.getPicturediaryStudent().size() > 0) {

            for (int i = 0; i < retriveResponse.getPicturediaryStudent().size(); i++) {

                if (retriveResponse.getPicturediaryStudent().get(i).getStudent().getId().equalsIgnoreCase(groupId)) {
                    bl_grp = true;

                }

            }
        }

        return bl_grp;

    }
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////

    class Save_post_draft extends AsyncTask<String, String, String> {

        /*JSONParser jsonParser = new JSONParser();*/
        JSONArray grpid = new JSONArray(), tag_ids = new JSONArray();
        JSONArray ss_id = new JSONArray();
        /* JSONArray oimg;
         JSONArray oimg_video,*/
        JSONArray oimg_othr = new JSONArray();
        String fg = "";
        String status = "";
        private MyCustomProgressDialog dialog;
        private String url_create_product = Base_url
                + "lms_api/picture_diary/save_blog";
        private static final String TAG_staus = "status";

        JSONObject json = null;

        String descriptionVal = "";

        JSONObject blogJson = new JSONObject();

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                dialog = new MyCustomProgressDialog(getActivity());
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();
                // Login_Email+Login_Password,Toast.LENGTH_LONG).show();

                if (selectedGroup == null) {
                    selectedGroup = new String[0];
                }
                if (STDcount == null) {
                    STDcount = new String[0];
                }
               /* if (imgsss == null) {
                    imgsss = new String[0];
                }

                if (videoss == null) {
                    videoss = new String[0];
                }
*/
                if (other_files == null) {
                    other_files = new String[0];
                }
                if (selectedCurTagPublis == null) {
                    selectedCurTagPublis = new String[0];
                }
                grpid = new JSONArray(Arrays.asList(selectedGroup));
                ss_id = new JSONArray(Arrays.asList(STDcount));
                /*oimg = new JSONArray(Arrays.asList(imgsss));
                oimg_video = new JSONArray(Arrays.asList(videoss));*/
                oimg_othr = new JSONArray(Arrays.asList(other_files));
//                descriptionVal = descriptionEditText.getText().toString();
                descriptionVal = descriptionEditText.getText().toString().replaceAll("\\n", "<br />");

                AppLog.Log("curiculam_ids",curiculam_ids);

                if (curiculam_ids.equalsIgnoreCase("[]")) {
                    tag_ids = new JSONArray();

                } else {
                    tag_ids = new JSONArray(curiculam_ids);

                }

                Log.e("tag  ", curiculam_ids + " " + tag_ids.toString());


            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {

            String saveBlogResponse = "";

            try {


                if (from_where.equalsIgnoreCase("save_post")) {
                    Log.i("save_post", "save_post");


                  /*  fg = "{" + "\"student_ids\"" + ":" + ss_id + "," + "\"images\""
                            + ":" + imageArr + "," + "\"videos\"" + ":" + videoArr + ","
                            + "\"random_files\"" + ":" + oimg_othr + ","
                            + "\"group_ids\"" + ":" + grpid + "," + "\"securityKey\""
                            + ":" + "\"H67jdS7wwfh\"" + "," + "\"curriculum_tag_ids\""
                            + ":" + tag_ids + "," + "\"language\"" + ":"
                            + JSONObject.quote(lang) + "," + "\"authentication_token\""
                            + ":" + JSONObject.quote(auth_token) + ","
                            + "\"description\"" + ":"
                            + JSONObject.quote(descriptionVal) + ","
                            + "\"mail\"" + ":" + JSONObject.quote(mail_staus) + "}";*/
                    blogJson.put("student_ids", ss_id);
                    blogJson.put("images", imageArr);
                    blogJson.put("videos", videoArr);
                    blogJson.put("random_files", oimg_othr);
                    blogJson.put("group_ids", grpid);
                    blogJson.put("securityKey", "H67jdS7wwfh");
                    blogJson.put("curriculum_tag_ids", tag_ids);
                    blogJson.put("language", lang);
                    blogJson.put("authentication_token", auth_token);
                    blogJson.put("description", descriptionVal);
                    blogJson.put("mail", mail_staus);
                    blogJson.put("user_type_app","android");


                }
                if (from_where.equalsIgnoreCase("save_draft")) {
                    Log.i("save_draft", "save_draft");
                  /*  fg = "{" + "\"student_ids\"" + ":" + ss_id + "," + "\"images\""
                            + ":" + imageArr + "," + "\"videos\"" + ":" + videoArr + ","
                            + "\"random_files\"" + ":" + oimg_othr + ","
                            + "\"group_ids\"" + ":" + grpid + "," + "\"securityKey\""
                            + ":" + "\"H67jdS7wwfh\"" + "," + "\"curriculum_tag_ids\""
                            + ":" + tag_ids + "," + "\"language\"" + ":"
                            + JSONObject.quote(lang) + "," + "\"authentication_token\""
                            + ":" + JSONObject.quote(auth_token) + ","
                            + "\"description\"" + ":"
                            + JSONObject.quote(descriptionVal) + ","
                            + "\"mail\"" + ":" + JSONObject.quote(mail_staus) + ","
                            + "\"save_as_draft\"" + ":" + "\"1\"" + "}";*/


                    blogJson.put("student_ids", ss_id);
                    blogJson.put("images", imageArr);
                    blogJson.put("videos", videoArr);
                    blogJson.put("random_files", oimg_othr);
                    blogJson.put("group_ids", grpid);
                    blogJson.put("securityKey", "H67jdS7wwfh");
                    blogJson.put("curriculum_tag_ids", tag_ids);
                    blogJson.put("language", lang);
                    blogJson.put("authentication_token", auth_token);
                    blogJson.put("description", descriptionVal);
                    blogJson.put("mail", mail_staus);
                    blogJson.put("save_as_draft", "1");
                    blogJson.put("user_type_app","android");

                }
                Log.e("save_post", blogJson.toString());

               /* List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("jsonData", fg));
                *//*Log.e("save_post", fg);*//*
                json = jsonParser.makeHttpRequest(url_create_product,
                        "POST", params);*/
                try {
                    String urlParams = "&" + Const.Params.JsonData + "=" + URLEncoder.encode(blogJson.toString(), "UTF-8");


                    saveBlogResponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (OutOfMemoryError e) {
                // TODO: handle exception
            } catch (Exception e) {
                // TODO: handle exception
            }


            return saveBlogResponse;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();

            // Log.i("oooooo", imm[0]);

            /*Log.e("save_post", results);*/
            try {

                json = new JSONObject();

                if (results != null && !results.isEmpty()) {
                    json = new JSONObject(results);

                    if (json.has(Const.Params.Status)) {
                        status = json.getString(TAG_staus);

                    }


                }


                if (status.equalsIgnoreCase("true")) {

                    if (from_where.equalsIgnoreCase("save_draft")) {
                        FragmentManager fragmentManager = getFragmentManager();
                        SaveDraft saveDraft = new SaveDraft();
                        Bundle bundle = new Bundle();
                        bundle.putString("my_draft", "");
                        bundle.putString("description_value", "");
                        bundle.putString("from_date", "");
                        bundle.putString("to_date", "");
                        saveDraft.setArguments(bundle);
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, saveDraft);
                        ft.commit();
                    }

                    if (from_where.equalsIgnoreCase("save_post")) {
                        session.cur_tag_ids("[]");
                        FragmentManager frfmnr_mngr = getFragmentManager();

                        MainActivity post_filtter_fr = new MainActivity();

                        FragmentTransaction ft = frfmnr_mngr.beginTransaction();

                        ft.replace(R.id.content_frame, post_filtter_fr);

                        ft.commit();
                    }

                } else {


                    if (json.has("message")) {
                        SmartClassUtil.showToast(getActivity(), json.getString("message"));
                    } else {
                        SmartClassUtil.showToast(getActivity(), "Service Failed");
                    }


                    try {


                        String msg = json.getString("message");
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
                // TODO: handle exception
            }

        }
    }

    class PublishDraftUnchange extends AsyncTask<String, String, String> {

        /*JSONParser jsonParser = new JSONParser();*/
        JSONArray grpid = new JSONArray();
        JSONArray tag_ids = new JSONArray();
        JSONArray ss_id = new JSONArray();
        JSONArray /*oimg;
        JSONArray oimg_video,*/ oimg_othr = new JSONArray();
        String fg;
        String status = "";
        private MyCustomProgressDialog dialog;
        private String url_create_product = Base_url
                + "lms_api/picture_diary/";
        private static final String TAG_staus = "status";

        JSONObject json = null;


        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                dialog = new MyCustomProgressDialog(getActivity());
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();
                // Login_Email+Login_Password,Toast.LENGTH_LONG).show();

                if (selectedGroup == null) {
                    selectedGroup = new String[0];
                }
                if (STDcount == null) {
                    STDcount = new String[0];
                }


                if (other_files == null) {
                    other_files = new String[0];
                }
                if (selectedCurTagPublis == null) {
                    selectedCurTagPublis = new String[0];
                }
                grpid = new JSONArray(Arrays.asList(selectedGroup));
                ss_id = new JSONArray(Arrays.asList(STDcount));

                oimg_othr = new JSONArray(Arrays.asList(other_files));
                descriptionVal = descriptionEditText.getText().toString().replaceAll("\\n", "<br />");
                tag_ids = new JSONArray(curTagsSetPublish);

                Log.e("tag  ", tag_ids.toString());

                /**/
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            String diaryResponse = "";

            try {


                JSONObject draftJsonObj = new JSONObject();
                if (draftType.equalsIgnoreCase("Publish_unchange")) {
                    url_create_product += "publish_draft";

                    JSONObject unchangeDraft = new JSONObject();


                    draftJsonObj.put("mail", mail_staus);
                    draftJsonObj.put("draft_id", retriveResponse.getPictureDiary().getId());
                    draftJsonObj.put("authentication_token", auth_token);
                    draftJsonObj.put("language", "en");
                    draftJsonObj.put("save_as_draft", save_as_draft);
                    draftJsonObj.put("securityKey", "H67jdS7wwfh");


                   /* fg = "{" + "\"student_ids\"" + ":" + ss_id + "," + "\"images\""
                            + ":" + oimg + "," + "\"videos\"" + ":" + oimg_video + ","
                            + "\"random_files\"" + ":" + oimg_othr + ","
                            + "\"group_ids\"" + ":" + grpid + "," + "\"securityKey\""
                            + ":" + "\"H67jdS7wwfh\"" + "," + "\"curriculum_tag_ids\""
                            + ":" + tag_ids + "," + "\"language\"" + ":"
                            + JSONObject.quote(lang) + "," + "\"authentication_token\""
                            + ":" + JSONObject.quote(auth_token) + ","
                            + "\"description\"" + ":"
                            + JSONObject.quote(descriptionVal) + ","
                            + "\"mail\"" + ":" + JSONObject.quote(mail_staus) + "}";*/


                }
                if (draftType.equalsIgnoreCase("Publish_Updated_Draft")) {

                    url_create_product += "publish_updated_draft";
                    Log.i("publish_updated_draft", "publish_updated_draft");
                   /* fg = "{" + "\"student_ids\"" + ":" + ss_id + "," + "\"images\""
                            + ":" + imageArr + "," + "\"videos\"" + ":" + videoArr + ","
                            + "\"random_files\"" + ":" + oimg_othr + ","
                            + "\"group_ids\"" + ":" + grpid + "," + "\"securityKey\""
                            + ":" + "\"H67jdS7wwfh\"" + "," + "\"curriculum_tag_ids\""
                            + ":" + tag_ids + "," + "\"language\"" + ":"
                            + JSONObject.quote(lang) + "," + "\"authentication_token\""
                            + ":" + JSONObject.quote(auth_token) + ","
                            + "\"description\"" + ":"
                            + JSONObject.quote(descriptionVal) + ","
                            + "\"mail\"" + ":" + JSONObject.quote(mail_staus) + ","
                            + "\"save_as_draft\"" + ":" + "\"1\"" + "}";*/

                    draftJsonObj.put("mail", mail_staus);
                    draftJsonObj.put("draft_id", retriveResponse.getPictureDiary().getId());
                    draftJsonObj.put("authentication_token", auth_token);
                    draftJsonObj.put("language", "en");
                    draftJsonObj.put("save_as_draft", save_as_draft);
                    draftJsonObj.put("securityKey", "H67jdS7wwfh");
                    draftJsonObj.put("student_ids", ss_id);
                    draftJsonObj.put("group_ids", grpid);
                    draftJsonObj.put("images", imageArr);
                    draftJsonObj.put("videos", videoArr);
                    draftJsonObj.put("curriculum_tag_ids", tag_ids);
                    draftJsonObj.put("description", descriptionVal);
                    draftJsonObj.put("ids_to_delete", idstodelete);
                    draftJsonObj.put("random_files", new JSONArray());

                    Log.e("update ", draftJsonObj.toString());

                    /*idstodelete*/
                }
                /*Log.e("save_post", fg);*/


                try {
                    String urlParams = "&" + Const.Params.JsonData + "=" + URLEncoder.encode(draftJsonObj.toString(), "UTF-8");


                    diaryResponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
                } catch (Exception e) {
                    e.printStackTrace();
                }


              /*  List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("jsonData", draftJsonObj.toString()));
                *//*Log.e("save_post", fg);*//*
                json = jsonParser.makeHttpRequest(url_create_product,
                        "POST", params);
*/

            } catch (OutOfMemoryError e) {
                // TODO: handle exception
            } catch (Exception e) {
                // TODO: handle exception
            }

            // check for success tag
            // Log.i("json data......", json.toString());
            try {


            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return diaryResponse;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();

            // Log.i("oooooo", imm[0]);

            try {
                json = new JSONObject();

                if (results != null && !results.isEmpty()) {
                    json = new JSONObject(results);

                    if (json.has(Const.Params.Status)) {
                        status = json.getString(TAG_staus);

                    }

                }


                if (status.equalsIgnoreCase("true")) {

                       /* if (from_where.equalsIgnoreCase("save_draft")) {
                            FragmentManager fragmentManager = getFragmentManager();
                            SaveDraft saveDraft = new SaveDraft();
                            Bundle bundle = new Bundle();
                            bundle.putString("my_draft", "");
                            bundle.putString("description_value", "");
                            bundle.putString("from_date", "");
                            bundle.putString("to_date", "");
                            saveDraft.setArguments(bundle);
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            ft.replace(R.id.content_frame, saveDraft);
                            ft.commit();
                        }*/

/*
                        if (from_where.equalsIgnoreCase("save_post")) {
*/
                    session.cur_tag_ids("[]");
                    FragmentManager frfmnr_mngr = getFragmentManager();

                    MainActivity post_filtter_fr = new MainActivity();

                    FragmentTransaction ft = frfmnr_mngr.beginTransaction();

                    ft.replace(R.id.content_frame, post_filtter_fr);

                    ft.commit();
/*
                        }
*/

                } else {
                    SmartClassUtil.showToast(getActivity(), "Service Failed");
                }


            } catch (Exception e) {
                // TODO: handle exception
            }

        }
    }

    // ////////////////

    public static class BitmapHelper {

        // decodes image and scales it to reduce memory consumption
        public static Bitmap decodeFile(String bitmapFile, int requiredWidth,
                                        int requiredHeight, boolean quickAndDirty) {
            try {
                // Decode image size
                BitmapFactory.Options bitmapSizeOptions = new BitmapFactory.Options();
                bitmapSizeOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(new FileInputStream(bitmapFile),
                        null, bitmapSizeOptions);

                // load image using inSampleSize adapted to required image size
                BitmapFactory.Options bitmapDecodeOptions = new BitmapFactory.Options();
                bitmapDecodeOptions.inTempStorage = new byte[16 * 1024];
                bitmapDecodeOptions.inSampleSize = computeInSampleSize(
                        bitmapSizeOptions, requiredWidth, requiredHeight, false);
                bitmapDecodeOptions.inPurgeable = true;
                bitmapDecodeOptions.inDither = !quickAndDirty;
                bitmapDecodeOptions.inPreferredConfig = quickAndDirty ? Bitmap.Config.RGB_565
                        : Bitmap.Config.ARGB_8888;

                Bitmap decodedBitmap = BitmapFactory.decodeStream(
                        new FileInputStream(bitmapFile), null,
                        bitmapDecodeOptions);

                // scale bitmap to mathc required size (and keep aspect ratio)

                float srcWidth = (float) bitmapDecodeOptions.outWidth;
                float srcHeight = (float) bitmapDecodeOptions.outHeight;

                float dstWidth = (float) requiredWidth;
                float dstHeight = (float) requiredHeight;

                float srcAspectRatio = srcWidth / srcHeight;
                float dstAspectRatio = dstWidth / dstHeight;

                // recycleDecodedBitmap is used to know if we must recycle
                // intermediary 'decodedBitmap'
                // (DO NOT recycle it right away: wait for end of bitmap
                // manipulation process to avoid
                // java.lang.RuntimeException: Canvas: trying to use a recycled
                // bitmap android.graphics.Bitmap@416ee7d8
                // I do not excatly understand why, but this way it's OK

                boolean recycleDecodedBitmap = false;

                Bitmap scaledBitmap = decodedBitmap;
                if (srcAspectRatio < dstAspectRatio) {
                    scaledBitmap = getScaledBitmap(decodedBitmap,
                            (int) dstWidth,
                            (int) (srcHeight * (dstWidth / srcWidth)));
                    // will recycle recycleDecodedBitmap
                    recycleDecodedBitmap = true;
                } else if (srcAspectRatio > dstAspectRatio) {
                    scaledBitmap = getScaledBitmap(decodedBitmap,
                            (int) (srcWidth * (dstHeight / srcHeight)),
                            (int) dstHeight);
                    recycleDecodedBitmap = true;
                }

                // crop image to match required image size

                int scaledBitmapWidth = scaledBitmap.getWidth();
                int scaledBitmapHeight = scaledBitmap.getHeight();

                Bitmap croppedBitmap = scaledBitmap;

                if (scaledBitmapWidth > requiredWidth) {
                    int xOffset = (scaledBitmapWidth - requiredWidth) / 2;
                    croppedBitmap = Bitmap.createBitmap(scaledBitmap, xOffset,
                            0, requiredWidth, requiredHeight);
                    scaledBitmap.recycle();
                } else if (scaledBitmapHeight > requiredHeight) {
                    int yOffset = (scaledBitmapHeight - requiredHeight) / 2;
                    croppedBitmap = Bitmap.createBitmap(scaledBitmap, 0,
                            yOffset, requiredWidth, requiredHeight);
                    scaledBitmap.recycle();
                }

                if (recycleDecodedBitmap) {
                    decodedBitmap.recycle();
                }
                decodedBitmap = null;

                scaledBitmap = null;
                return croppedBitmap;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        /**
         * compute powerOf2 or exact scale to be used as
         * {@link BitmapFactory.Options#inSampleSize} value (for subSampling)
         *
         * @param powerOf2 weither we want a power of 2 sclae or not
         * @return
         */
        public static int computeInSampleSize(BitmapFactory.Options options,
                                              int dstWidth, int dstHeight, boolean powerOf2) {
            int inSampleSize = 1;

            // Raw height and width of image
            final int srcHeight = options.outHeight;
            final int srcWidth = options.outWidth;

            if (powerOf2) {
                // Find the correct scale value. It should be the power of 2.

                int tmpWidth = srcWidth, tmpHeight = srcHeight;
                while (true) {
                    if (tmpWidth / 2 < dstWidth || tmpHeight / 2 < dstHeight)
                        break;
                    tmpWidth /= 2;
                    tmpHeight /= 2;
                    inSampleSize *= 2;
                }
            } else {
                // Calculate ratios of height and width to requested height and
                // width
                final int heightRatio = Math.round((float) srcHeight
                        / (float) dstHeight);
                final int widthRatio = Math.round((float) srcWidth
                        / (float) dstWidth);

                // Choose the smallest ratio as inSampleSize value, this will
                // guarantee
                // a final image with both dimensions larger than or equal to
                // the
                // requested height and width.
                inSampleSize = heightRatio < widthRatio ? heightRatio
                        : widthRatio;
            }

            return inSampleSize;
        }

        public static Bitmap drawableToBitmap(Drawable drawable) {
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }

            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

            return bitmap;
        }

        public static Bitmap getScaledBitmap(Bitmap bitmap, int newWidth,
                                             int newHeight) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;

            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);

            // RECREATE THE NEW BITMAP
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
                    height, matrix, false);
            return resizedBitmap;
        }

    }

    // //////////////////////
    // public class MultiPhotoSelectActivity extends BaseActivity {
    //
    // private ArrayList<String> imageUrls;
    // private DisplayImageOptions options;
    // private ImageAdapter imageAdapter;
    //
    // @Override
    // public void onCreate(Bundle savedInstanceState) {
    // super.onCreate(savedInstanceState);
    // setContentView(R.layout.ac_image_grid);
    // //Bundle bundle = getIntent().getExtras();
    // //imageUrls = bundle.getStringArray(Extra.IMAGES);
    //
    // final String[] columns = { MediaStore.Images.Media.DATA,
    // MediaStore.Images.Media._ID };
    // final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
    // Cursor imagecursor = managedQuery(
    // MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
    // null, orderBy + " DESC");
    //
    // this.imageUrls = new ArrayList<String>();
    //
    // for (int i = 0; i < imagecursor.getCount(); i++) {
    // imagecursor.moveToPosition(i);
    // int dataColumnIndex =
    // imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
    // imageUrls.add(imagecursor.getString(dataColumnIndex));
    //
    // System.out.println("=====> Array path => "+imageUrls.get(i));
    // }
    //
    //
    // options = new DisplayImageOptions.Builder()
    // .showStubImage(R.drawable.stub_image)
    // .showImageForEmptyUri(R.drawable.image_for_empty_url)
    // .cacheInMemory()
    // .cacheOnDisc()
    // .build();
    //
    // imageAdapter = new ImageAdapter(this, imageUrls);
    //
    // GridView gridView = (GridView) findViewById(R.id.gridview);
    // gridView.setAdapter(imageAdapter);
    // /*gridView.setOnItemClickListener(new OnItemClickListener() {
    // @Override
    // public void onItemClick(AdapterView<?> parent, View view, int position,
    // long id) {
    // startImageGalleryActivity(position);
    // }
    // });*/
    // }
    //
    // @Override
    // protected void onStop() {
    // imageLoader.stop();
    // super.onStop();
    // }
    //
    // public void btnChoosePhotosClick(View v){
    //
    // ArrayList<String> selectedItems = imageAdapter.getCheckedItems();
    // Toast.makeText(MultiPhotoSelectActivity.this,
    // "Total photos selected: "+selectedItems.size(),
    // Toast.LENGTH_SHORT).show();
    // Log.d(MultiPhotoSelectActivity.class.getSimpleName(), "Selected Items: "
    // + selectedItems.toString());
    // }
    //
    // /*private void startImageGalleryActivity(int position) {
    // Intent intent = new Intent(this, ImagePagerActivity.class);
    // intent.putExtra(Extra.IMAGES, imageUrls);
    // intent.putExtra(Extra.IMAGE_POSITION, position);
    // startActivity(intent);
    // }*/
    //
    // public class ImageAdapter extends BaseAdapter {
    //
    // ArrayList<String> mList;
    // LayoutInflater mInflater;
    // Context mContext;
    // SparseBooleanArray mSparseBooleanArray;
    //
    // public ImageAdapter(Context context, ArrayList<String> imageList) {
    // // TODO Auto-generated constructor stub
    // mContext = context;
    // mInflater = LayoutInflater.from(mContext);
    // mSparseBooleanArray = new SparseBooleanArray();
    // mList = new ArrayList<String>();
    // this.mList = imageList;
    //
    // }
    //
    // public ArrayList<String> getCheckedItems() {
    // ArrayList<String> mTempArry = new ArrayList<String>();
    //
    // for(int i=0;i<mList.size();i++) {
    // if(mSparseBooleanArray.get(i)) {
    // mTempArry.add(mList.get(i));
    // }
    // }
    //
    // return mTempArry;
    // }
    //
    // @Override
    // public int getCount() {
    // return imageUrls.size();
    // }
    //
    // @Override
    // public Object getItem(int position) {
    // return null;
    // }
    //
    // @Override
    // public long getItemId(int position) {
    // return position;
    // }
    //
    // @Override
    // public View getView(int position, View convertView, ViewGroup parent) {
    //
    // if(convertView == null) {
    // convertView = mInflater.inflate(R.layout.row_multiphoto_item, null);
    // }
    //
    // CheckBox mCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
    // final ImageView imageView = (ImageView)
    // convertView.findViewById(R.id.imageView1);
    //
    // imageLoader.displayImage("file://"+imageUrls.get(position), imageView,
    // options, new SimpleImageLoadingListener() {
    // @Override
    // public void onLoadingComplete(Bitmap loadedImage) {
    // Animation anim =
    // AnimationUtils.loadAnimation(MultiPhotoSelectActivity.this,
    // R.anim.fade_in);
    // imageView.setAnimation(anim);
    // anim.start();
    // }
    // });
    //
    // mCheckBox.setTag(position);
    // mCheckBox.setChecked(mSparseBooleanArray.get(position));
    // mCheckBox.setOnCheckedChangeListener(mCheckedChangeListener);
    //
    // return convertView;
    // }
    //
    // OnCheckedChangeListener mCheckedChangeListener = new
    // OnCheckedChangeListener() {
    //
    // @Override
    // public void onCheckedChanged(CompoundButton buttonView, boolean
    // isChecked) {
    // // TODO Auto-generated method stub
    // mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
    // }
    // };
    // }
    //
    //
    // }

    public class ImageAdapter extends BaseAdapter {

        ArrayList<String> mList;
        LayoutInflater mInflater;
        Context mContext;
        SparseBooleanArray mSparseBooleanArray;

        public ImageAdapter(Context context, ArrayList<String> imageList) {
            // TODO Auto-generated constructor stub
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
            mSparseBooleanArray = new SparseBooleanArray();
            mList = new ArrayList<String>();
            this.mList = imageList;

        }

        public ArrayList<ImageVideoBean> getCheckedItems() {
            ArrayList<ImageVideoBean> mTempArry = new ArrayList<ImageVideoBean>();

            for (int i = 0; i < mList.size(); i++) {
                if (mSparseBooleanArray.get(i)) {
                    mTempArry.add(new ImageVideoBean("0", mList.get(i), "image"));
                }
            }

            return mTempArry;
        }

        @Override
        public int getCount() {
            return imageUrls.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.row_multiphoto_item,
                        null);
            }

            CheckBox mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkBox1);
            final ImageView imageView = (ImageView) convertView
                    .findViewById(R.id.imageView1);
            imageLoader.displayImage("file://" + imageUrls.get(position),
                    imageView, options, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            Animation anim = AnimationUtils.loadAnimation(
                                    getActivity(), R.anim.fade_in);
                            imageView.setAnimation(anim);
                            anim.start();
                        }
                    });

            mCheckBox.setTag(position);
            mCheckBox.setChecked(mSparseBooleanArray.get(position));
            mCheckBox.setOnCheckedChangeListener(mCheckedChangeListener);

            return convertView;
        }

        OnCheckedChangeListener mCheckedChangeListener = new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                mSparseBooleanArray.put((Integer) buttonView.getTag(),
                        isChecked);
            }
        };
    }
    // ///////////////////
}
