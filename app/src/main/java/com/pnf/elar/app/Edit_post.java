package com.pnf.elar.app;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/*import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.elar.util.NetworkUtil;
import com.elar.util.SmartClassUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pnf.elar.app.Bo.ImageVideoBean;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.ActionBarActivity;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class Edit_post extends Fragment {
    String imagePath, VideoPath, i_path, V_path;
    ArrayList<String> encoded_code = new ArrayList<String>();
    /*
        ArrayList<String> encoded_code_video = new ArrayList<String>();
    */
/*
    String[] img_vdo, img_vdo_rndm;
*/
    Button grupss, studentss, editpublish, deletePostBtn;
    AlertDialog.Builder ad;
    /*
        String[] imgsss, videoss;
    */
    private static int LOAD_IMAGE_RESULTS = 1;
    private static int LOAD_Video_RESULTS = 1;
    String[] groups_name, Student_name, grp_id, grp_name, groups_id, std_id,
            std_name;
    //imagename, videonameone, random_imagename,            video_imagename, random_file_name;
    boolean[] _selections;
    boolean[] _selectionstwo;
    LinearLayout CURRICULUM_TAGS_edit;
    String[] groupIdArr, ti;
    String post_id;
    EditText descript;
    ArrayAdapterEdit aa;
    LinearLayout upload;
    ViewGroup actionBarLayout;
    ListView im_vdo;
    int chooser_click;
    UserSessionManager session;
    HashMap<String, String> user;

    String lang, auth_token, Base_url;
    LinearLayout actionbar;
    ImageView back;
    TextView backalso, CURRICULUM_TAGS, escription, spn4;
    ArrayList<ImageVideoBean> imageVideoRandomList = new ArrayList<ImageVideoBean>();

    View v;

    /*String[] selected_cur_tag;*/

    public static HashSet curTagSet;


    JSONArray crmTag_id = new JSONArray();

    String curTagIds = "";


    JSONArray imageJsonArr = new JSONArray();
    JSONArray videoJsonArr = new JSONArray();
    JSONArray idstoDelete = new JSONArray();
    android.content.res.Resources res;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_edit_post, container, false);

        im_vdo = (ListView) v.findViewById(R.id.im_vdo);

        res = getActivity().getResources();


        try {
            Bundle bundle = this.getArguments();

            post_id = bundle.getString("post_id");

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        curTagSet = new HashSet();
        session = new UserSessionManager(getActivity());

        user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        auth_token = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);


        try {
            if (isOnline()) {
                new datacreate().execute();
            } else {
                try {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                            .create();
                    alertDialog.setTitle("Info");
                    alertDialog
                            .setMessage("Internet not available, Cross check your internet connectivity and try again");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                    alertDialog.setButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                }
                            });

                    alertDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

//		// ///////////Back to mainActivity ////////
        try {
            ((Drawer) getActivity()).setBackFrompublishtomain();
            if (lang.equalsIgnoreCase("sw")) {
                ((Drawer) getActivity()).setActionBarTitle("Editera inlägg");
            } else {
                ((Drawer) getActivity()).setActionBarTitle("Edit post");
            }
            ((Drawer) getActivity()).Hideserch();
            ((Drawer) getActivity()).HideRefresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
//		// ////////////

        grupss = (Button) v.findViewById(R.id.grupss);
        studentss = (Button) v.findViewById(R.id.studentss);
        descript = (EditText) v.findViewById(R.id.description);
        // ////////////////////////////////////////////
        upload = (LinearLayout) v.findViewById(R.id.upload);
        deletePostBtn = (Button) v.findViewById(R.id.deletePostBtn);
        studentss = (Button) v.findViewById(R.id.studentss);
        editpublish = (Button) v.findViewById(R.id.editpublish);
        // backalso=(TextView)findViewById(R.id.backalso);
        CURRICULUM_TAGS_edit = (LinearLayout) v
                .findViewById(R.id.CURRICULUM_TAGS_edit);
        CURRICULUM_TAGS = (TextView) v.findViewById(R.id.CURRICULUM_TAGS);
        escription = (TextView) v.findViewById(R.id.escription);
        spn4 = (TextView) v.findViewById(R.id.spn4);

        if (lang.equalsIgnoreCase("sw")) {
            grupss.setText("Grupper");
            studentss.setText("Användare");

            CURRICULUM_TAGS.setText("Läroplan Taggar");


            escription.setText("Beskrivning");

            editpublish.setText("PUBLICERA");
            spn4.setText("Bifoga filer");


            deletePostBtn.setText("Radera");
            editpublish.setText("Spara");

        } else {

        }

        setCurriculamTag();


        deletePostBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOnline()) {
                    new DeletePost(post_id).execute();
                } else {
                    try {
                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                                .create();
                        alertDialog.setTitle("Info");
                        alertDialog
                                .setMessage("Internet not available, Cross check your internet connectivity and try again");
                        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                        alertDialog.setButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                    }
                                });

                        alertDialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        editpublish.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

               /* imgsss = new String[encoded_code.size()];

                for (int i = 0; i < encoded_code.size(); i++) {
                    imgsss[i] = encoded_code.get(i);
                }

                Log.i("Array Size image...", Integer.toString(imgsss.length));*/
                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                        for (int i = 0; i < imageVideoRandomList.size(); i++) {

                            if (imageVideoRandomList.get(i).getType().equalsIgnoreCase("image")) {
                                if (imageVideoRandomList.get(i).getId().equalsIgnoreCase("0")) {
                                    imageJsonArr.put(imageVideoRandomList.get(i).getName());
                                }
                            } else if (imageVideoRandomList.get(i).getType().equalsIgnoreCase("video")) {
                                if (imageVideoRandomList.get(i).getId().equalsIgnoreCase("0")) {
                                    videoJsonArr.put(imageVideoRandomList.get(i).getName());
                                }
                            }
                        }

                                   /* videoss = new String[encoded_code_video.size()];

                                    for (int i = 0; i < encoded_code_video.size(); i++) {
                                        videoss[i] = encoded_code_video.get(i);
                                    }*/
                                    /*Log.i("Array Size vedio...", Integer.toString(videoss.length));*/


                    /*
                                    selected_cur_tag = new String[curTagSet.size()];
                    */
                        List<String> curmTagList = new ArrayList<String>(curTagSet);

                    /*
                                    selected_cur_tag = curmTagList.toArray(new String[curmTagList.size()]);
                    */

                                  /*  for (int c = 0; c < curmTagList.size(); c++) {

                                        *//*crmTag_id[c] = (String) curmTagList.get(c);*//*
                                        crmTag_id.put(curmTagList.get(c).toString());
                                    }*/

                        curTagIds = session.getCur_tag_ids();

                        Log.i("Array Size vedio...", curTagIds);

                                  /*  if (selected_cur_tag == null) {
                                        selected_cur_tag = new String[0];
                                    }*/
                        if (groupIdArr == null) {
                            groupIdArr = new String[0];
                        }

                        if (ti == null) {
                            ti = new String[0];

                        }

                        if (isOnline()) {
                            new fil().execute();
                        } else {
                            try {
                                AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                                        .create();
                                alertDialog.setTitle("Info");
                                alertDialog
                                        .setMessage("Internet not available, Cross check your internet connectivity and try again");
                                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                                alertDialog.setButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int which) {

                                            }
                                        });

                                alertDialog.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

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
                            public void onClick(DialogInterface arg0, int arg1) {


                                if (!imageVideoRandomList.get(position).getId().equalsIgnoreCase("0")) {
                                    idstoDelete.put(imageVideoRandomList.get(position).getId());
                                }
                                imageVideoRandomList.remove(position);
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

        CURRICULUM_TAGS_edit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                        Intent in = new Intent(getActivity(), Curriculam_tag_edit.class);
                        getActivity().startActivity(in);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
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

                // /////////////////////////////////////////////////////

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

            }
        });
        ////////////////////////


        grupss.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {


                    String title, ok, cancel;

                    if (lang.equalsIgnoreCase("sw")) {
                        title = "Välj grupper..";
                        ok = "Klar";
                        cancel = "Avbryt";
                    } else {
                        title = "Select groups..";
                        ok = "Ok";
                        cancel = "Cancel";
                    }

//				for (int i = 0; i < groups_name.length; i++) {
//					for (int j = 0; j < grp_name.length; j++) {
//						if (groups_name[i].equalsIgnoreCase(grp_name[j])) {
//							_selections[j] = true; // ////// problem ///////////
//						}
//					}
//
//				}

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
                    ad.setPositiveButton(ok,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    printSelectedPlanets();

                                    if (groupIdArr != null && groupIdArr.length > 0) {


                                        if (groupIdArr[0].equalsIgnoreCase("all")) {

                                            if (lang.equalsIgnoreCase("en")) {

                                                grupss.setText("All");
                                                studentss.setText("All");


                                            } else {
                                                grupss.setText("Alla");
                                                studentss.setText("Alla");
                                            }

                                        } else {
                                            if (lang.equalsIgnoreCase("en")) {


                                                grupss.setText(Integer.toString(groupIdArr.length) + " " + "Groups");
                                                studentss.setText("Users");

                                            } else {
                                                grupss.setText(Integer.toString(groupIdArr.length) + " " + "Grupper");
                                                studentss.setText("Användare");

                                            }
                                        }

                                        if (isOnline()) {
                                            new getstudent().execute();

                                        } else {
                                            SmartClassUtil.showToast(getActivity(), "No Active internet connection");
                                        }

                                    } else {
                                        if (lang.equalsIgnoreCase("en")) {

                                            grupss.setText("Groups");
                                            studentss.setText("Users");

                                        } else {
                                            grupss.setText("Grupper");
                                            studentss.setText("Användare");
                                        }


                                        if (lang.equalsIgnoreCase("en")) {
                                            Toast.makeText(getActivity(),
                                                    "Group cannot be empty.....",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {


                                            Toast.makeText(getActivity(),
                                                    "Grupp kan inte vara tomt .....",
                                                    Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                }

                                // //////////////////////////////////////////////////////////////////////////////////////
                                private void printSelectedPlanets() {
                                    // TODO Auto-generated method stub
                                    String[] d = new String[_selections.length];
                                    int count = 0;
                                    for (int i = 0; i < grp_name.length; i++) {
                                        Log.i("ME", grp_name[i] + " selected: "
                                                + _selections[i] + i);

                                        if (_selections[i] == true) {
                                            Log.i("true", Integer.toString(i));
                                            d[i] = Integer.toString(i);
                                            count = count + 1;
                                            // hm.add( Integer.toString(i));
                                        }
                                    }
//								Log.i("tttttt", Arrays.deepToString(d));
//
//								Log.i("hhhhhhhhhhh", Integer.toString(count));

                                    String[] p = new String[count];
                                    int j = 0;
                                    for (int i = 0; i < d.length; i++) {

                                        if (!(d[i] == null)) {
                                            p[j] = d[i];
                                            j++;

                                        }

                                    }
                                    //	Log.i("@@@@@@@@@@@@@@", Arrays.deepToString(p));
                                    // Log.i("ppppppppppppp", p[0]);
                                    groupIdArr = new String[count];
                                    String n;
                                    int g;
                                    for (int i = 0; i < count; i++) {
                                        n = p[i];
                                        g = Integer.parseInt(n);

                                        groupIdArr[i] = grp_id[g];

                                    }


                                    //	Log.i("=============", Arrays.deepToString(w));

                                }
                                // //////////////////////////////////////////////////////////////////////////////////////

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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        studentss.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    String title, ok, cancel;

                    boolean showDialog = true;
                    if (lang.equalsIgnoreCase("sw")) {

                        if (grupss.getText().toString().equalsIgnoreCase("alla")) {
                            showDialog = true;
                        }

                    } else {
                        if (grupss.getText().toString().equalsIgnoreCase("all")) {
                            showDialog = true;

                        }


                    }

                    if (showDialog) {

                        if (lang.equalsIgnoreCase("sw")) {
                            title = "Välj användare..";
                            ok = "Klar";
                            cancel = "Avbryt";


                        } else {
                            title = "Select Users..";
                            ok = "Ok";
                            cancel = "Cancel";
                        }

                        try {
                            for (int i = 0; i < Student_name.length; i++) {
                                for (int j = 0; j < std_name.length; j++) { // //////////////////errror/////////////////////
                                    if (Student_name[i].equalsIgnoreCase(std_name[j])) {
                                        _selectionstwo[j] = true;
                                    }
                                }

                            }


                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                        //	Log.i("checked value", Arrays.toString(_selectionstwo));

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
                        ad.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // TODO Auto-generated method stub
                                        try {
                                            printSelectedPlanets();
                                        } catch (Exception e) {
                                            // TODO: handle exception
                                        }

                                        if (ti != null && ti.length > 0) {
                                            // new getstudent().execute();
                                        } else {
                                            Toast.makeText(getActivity(),
                                                    "Group cannot be empty.....",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    // //////////////////////////////////////////////////////////////////////////////////////
                                    private void printSelectedPlanets() {
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

                                            }
                                        }


                                        String[] p = new String[count];
                                        int j = 0;
                                        for (int i = 0; i < d.length; i++) {

                                            if (!(d[i] == null)) {
                                                p[j] = d[i];
                                                j++;

                                            }

                                        }
                                        //	Log.i("@@@@@@@@@@@@@@", Arrays.deepToString(p));
                                        // Log.i("ppppppppppppp", p[0]);
                                        ti = new String[count];
                                        String n;
                                        int g;
                                        for (int i = 0; i < count; i++) {
                                            n = p[i];
                                            g = Integer.parseInt(n);

                                            ti[i] = std_id[g];

                                        }
                                        //	Log.i("=============", Arrays.deepToString(ti));


                                        setStudentValueView();
                                    }

                                });
                        ad.setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // TODO Auto-generated method stub

                                    }
                                });
                        ad.show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        });


        return v;
    }

    public void setCurriculamTag() {
        try {


            if (curTagSet.size() > 0) {

                if (lang.equalsIgnoreCase("en")) {
                    CURRICULUM_TAGS.setText(curTagSet.size() + " CURRICULUM TAGS");
                } else {
                    CURRICULUM_TAGS.setText(curTagSet.size() + " LÄROPLANS TAGGAR");

                }

            } else {
                if (lang.equalsIgnoreCase("en")) {

                    CURRICULUM_TAGS.setText("CURRICULUM TAGS");
                } else {
                    CURRICULUM_TAGS.setText("LÄROPLANS TAGGAR");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (CURRICULUM_TAGS != null) {
            setCurriculamTag();
        }
    }


    public void setGroupValueView(int groupCount) {


        if (lang.equalsIgnoreCase("en")) {
            grupss.setText(groupCount + " Groups");

        } else {
            grupss.setText(groupCount + " Grupper");

        }
        /*studentss.setText(getString(R.string.users));*/


    }

    public void setStudentValueView() {
        try {
            if (ti.length > 0) {


                if (lang.equalsIgnoreCase("en")) {
                    studentss.setText(ti.length + " User Selected");

                } else {


                    studentss.setText(ti.length + " Användare");

                }
            } else {

                if (lang.equalsIgnoreCase("en")) {
                    studentss.setText("Users");
                } else {

                    studentss.setText("Användare");

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void setListViewHeightBasedOnChildrener(ListView im_vdo2) {
        // TODO Auto-generated method stub

        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // //////////////////////////////////////////////////////
    class datacreate extends AsyncTask<String, String, String> {
        ArrayList<String[]> list6 = new ArrayList<String[]>();
        ArrayList<String[]> list7 = new ArrayList<String[]>();
        ArrayList<String[]> list5 = new ArrayList<String[]>();
        ArrayList<String[]> list8 = new ArrayList<String[]>();
        ArrayList<String[]> list9 = new ArrayList<String[]>();

        ArrayList<String[]> list222 = new ArrayList<String[]>();
        ArrayList<String[]> list1 = new ArrayList<String[]>();
        ArrayList<String[]> curriculum_post_title = new ArrayList<String[]>();
        Map<String, ArrayList<String[]>> map = new HashMap<String, ArrayList<String[]>>();

        /*JSONParser jsonParser = new JSONParser();*/
        /*JSONObject edit_post_viewJson;*/

        // String[] groups_name;
        String description;
        String[] descr;
        String[] createddate;
        String[] teacher_name;
        String[] post_id;

        String[] curriclm_tg_title;

        String[] liked;
        String[] img_name_id;
        String[] cntnt_type;
        String status;
        private MyCustomProgressDialog dialog;
        private String url_create_product = Base_url
                + "lms_api/picture_diary/edit_post_view";
        private static final String TAG_staus = "status";
        private static final String TAG_groups = "groups";
        private static final String TAG_groups_name = "name";
        private static final String TAG_post = "post";
        private static final String TAG_post_description = "description";
        private static final String TAG_students = "students";
        private static final String TAG_groups_id = "id";
        private static final String TAG_images = "images";
        private static final String TAG_images_imagename = "imagename";
        private static final String TAG_id = "id";
        private static final String TAG_videos = "videos";
        private static final String TAG_videoname_mp4 = "videoname_mp4";
        private static final String TAG_random_files = "random_files";
        private static final String TAG_imagename = "imagename";
        private static final String TAG_random_file_name = "random_file_name";
        private static final String TAG_video_imagename = "imagename";

        private static final String TAG_created = "created";
        private static final String TAG_post_id = "id";
        private static final String TAG_imagename_id = "id";

        private static final String TAG_post_student = "students";
        private static final String TAG_ss_name = "name";

        private static final String TAG_random_already_liked = "picture_diary_like_count";

        private static final String TAG_associated_curriculum_tags = "associated_curriculum_tags";
        private static final String TAG_curriculum_post_title = "title";
        private static final String TAG_curriculum_post_children = "children";
        private static final String TAG_curriculum_post_subchildren = "subchildren";

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
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            String editPostResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode("H67jdS7wwfh", "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.PostId + "=" + URLEncoder.encode(Edit_post.this.post_id, "UTF-8");


                editPostResponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }

           /* List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("securityKey", "H67jdS7wwfh"));
            params.add(new BasicNameValuePair("post_id", Edit_post.this.post_id));
            params.add(new BasicNameValuePair("authentication_token",
                    auth_token));
            edit_post_viewJson = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);
            Log.i("params data......", params.toString());
            // check for success tag
            Log.i("json data......", edit_post_viewJson.toString());
*/

            return editPostResponse;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String editResults) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {

                JSONObject edit_post_viewJson = new JSONObject();
                if (editResults != null && !editResults.isEmpty()) {
                    edit_post_viewJson = new JSONObject(editResults);

                    if (edit_post_viewJson.has(Const.Params.Status)) {
                        status = edit_post_viewJson.getString(TAG_staus);
                    } else {
                        status = "false";

                    }
                } else {
                    status = "false";
                }


                if (status.equalsIgnoreCase("true")) {
                    try {
                        //	Log.e("=-=-=-=-=-=-", status);

                        JSONObject jsonRootObject = edit_post_viewJson.getJSONObject(TAG_post);

                        description = jsonRootObject.getString(TAG_post_description);

                        // //////////////////////////////////////////////////////////////////////////
                        JSONArray groups = jsonRootObject.optJSONArray(TAG_groups);

                        if (jsonRootObject.getJSONArray(TAG_associated_curriculum_tags).length() > 0) {

                            if (lang.equalsIgnoreCase("en")) {
                                CURRICULUM_TAGS.setText(jsonRootObject.getJSONArray(TAG_associated_curriculum_tags).length() + " CURRICULUM TAGS");

                            } else {
                                CURRICULUM_TAGS.setText(jsonRootObject.getJSONArray(TAG_associated_curriculum_tags).length() + " LÄROPLANS TAGGAR");

                            }

                            JSONArray curTagArray = new JSONArray(jsonRootObject.getJSONArray(TAG_associated_curriculum_tags).toString());
                            for (int i = 0; i < curTagArray.length(); i++) {
                                curTagSet.add(curTagArray.get(i));
                            }
                        }

                        groups_name = new String[groups.length()];
                        groups_id = new String[groups.length()];
                        for (int j = 0; j < groups.length(); j++) {
                            JSONObject c = groups.getJSONObject(j);
                            //
                            groups_name[j] = c.getString(TAG_groups_name);
                            groups_id[j] = c.getString(TAG_groups_id);

                        }


                        Log.i("groups_idgroups_id", Arrays.deepToString(groups_id));


                        JSONArray Student = jsonRootObject.optJSONArray(TAG_students);

                        Student_name = new String[Student.length()]; // // //// ////


                        std_id = new String[Student.length()];
                        std_name = new String[Student.length()];
                        _selectionstwo = new boolean[Student.length()];
                        for (int j = 0; j < Student.length(); j++) {
                            JSONObject stuObject = Student.getJSONObject(j);
                            //
                            Student_name[j] = stuObject.getString(TAG_groups_name);
                            std_name[j] = stuObject.getString(TAG_groups_name);

                            _selectionstwo[j] = true;


                        }

                        Log.i("jaihoooooo", "" + Student_name.length);


                        if (Student.length() > 0) {
                            if (lang.equalsIgnoreCase("en")) {
                                studentss.setText(Student.length() + " Users");
                            } else {
                                studentss.setText(Student.length() + " Användare");

                            }
                        }

                        JSONArray images = jsonRootObject.optJSONArray(TAG_images);

/*
                        imagename = new String[images.length()]; // // //// ////
*/
                        // ////
                        // //////////////////////
                        // new
                        // /////////////////////////

                        for (int j = 0; j < images.length(); j++) {
                            JSONObject c = images.getJSONObject(j);
                            String fileExtension = c.getString(TAG_imagename).substring(c.getString(TAG_imagename).lastIndexOf(".") + 1);

                            imageVideoRandomList.add(new ImageVideoBean(c.getString(TAG_id), c.getString(TAG_imagename), "image", fileExtension));
                        }

                        JSONArray videoname = jsonRootObject.optJSONArray(TAG_videos);

                     /*   videonameone = new String[videoname.length()]; // // //// ////
                        video_imagename = new String[videoname.length()]; // //// ////*/

                        for (int j = 0; j < videoname.length(); j++) {
                            JSONObject c = videoname.getJSONObject(j);
                            //
                            /*videonameone[j] = c.getString(TAG_videoname_mp4);
                            video_imagename[j] = c.getString(TAG_video_imagename);*/
                            String fileExtension = c.getString(TAG_videoname_mp4).substring(c.getString(TAG_videoname_mp4).lastIndexOf(".") + 1);

                            imageVideoRandomList.add(new ImageVideoBean(c.getString(TAG_id), c.getString(TAG_videoname_mp4), "video", fileExtension));

                        }

                        JSONArray random_files = jsonRootObject
                                .optJSONArray(TAG_random_files);

                        /*random_imagename = new String[random_files.length()]; // // ////
                        // ////
                        // //// ////
                        random_file_name = new String[random_files.length()];*/
                        for (int j = 0; j < random_files.length(); j++) {
                            JSONObject c = random_files.getJSONObject(j);
                            //
                          /*  random_imagename[j] = c.getString(TAG_imagename);
                            random_file_name[j] = c.getString(TAG_random_file_name);*/
                            String fileExtension = c.getString(TAG_random_file_name).substring(c.getString(TAG_random_file_name).lastIndexOf(".") + 1);

                            imageVideoRandomList.add(new ImageVideoBean(c.getString(TAG_id), c.getString(TAG_random_file_name), "other", fileExtension));

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    try {
                        String[] t = list6.get(0);
                        //	Log.i("@@@@@@@@@@@@", Arrays.deepToString(t));
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                    //	Log.i("description", description);

                 /*   if (Build.VERSION.SDK_INT >= 24) {
                        descript.setText(Html.fromHtml(description,Html.FROM_HTML_MODE_LEGACY));
                    }
                    else
                    {*/
                        descript.setText(Html.fromHtml(description));
/*
                    }
*/
                    if (groups_id.length > 0) {
                        setGroupValueView(groups_id.length);
                    }

                    aa = new ArrayAdapterEdit(getActivity(), imageVideoRandomList); // ////////////////////
                    // new
                    // ///////////////
                    im_vdo.setAdapter(aa);
                    aa.notifyDataSetChanged();
                    setListViewHeightBasedOnChildrener(im_vdo);

                    if (isOnline()) {
                        new filterclass().execute();
                    } else {
                        SmartClassUtil.showToast(getActivity(), "No Active internet connection");
                    }
                } else {
                    try {


                        String msg = edit_post_viewJson.getString("message");
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
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e1) {
                e1.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }

        public String[] combine(String[] a, String[] b) {
            int length = a.length + b.length;
            String[] result = new String[length];
            System.arraycopy(a, 0, result, 0, a.length);
            System.arraycopy(b, 0, result, a.length, b.length);
            return result;
        }

    }

    // ///////////////////////////////////////////////////////////////////////

    class filterclass extends AsyncTask<String, String, String> {

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
            String filterResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode("H67jdS7wwfh", "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8");


                filterResponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }
          /*  List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("securityKey", "H67jdS7wwfh"));
            params.add(new BasicNameValuePair("authentication_token",
                    auth_token));
            params.add(new BasicNameValuePair("language", lang));

            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check for success tag
            Log.i("json data......", json.toString());
            try {
                status = json.getString(TAG_staus);

                //	Log.e("=-=-=-=-=-=-", status);

                JSONArray grps_name = json.optJSONArray(TAG_groups);

                grp_id = new String[grps_name.length()];
                grp_name = new String[grps_name.length()];
                _selections = new boolean[grps_name.length()];
                for (int j = 0; j < grps_name.length(); j++) {
                    JSONObject c = grps_name.getJSONObject(j);

                    grp_id[j] = c.getString(TAG_groups_id);
                    grp_name[j] = c.getString(TAG_groups_name);

                }

                if (status.equalsIgnoreCase("true")) {

                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            return filterResponse;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {

                JSONObject grpJson = new JSONObject();

                if (results != null && !results.isEmpty()) {

                    grpJson = new JSONObject(results);

                    if (grpJson.has(Const.Params.Status)) {

                        status = grpJson.getString(TAG_staus);

                    } else {
                        status = "false";
                    }

                } else {
                    status = "false";
                }
                //	Log.e("=-=-=-=-=-=-", status);


                if (status.equalsIgnoreCase("true")) {


                    JSONArray grps_name = grpJson.optJSONArray(TAG_groups);

                    grp_id = new String[grps_name.length()];
                    grp_name = new String[grps_name.length()];
                    _selections = new boolean[grps_name.length()];
                    for (int j = 0; j < grps_name.length(); j++) {
                        JSONObject c = grps_name.getJSONObject(j);

                        grp_id[j] = c.getString(TAG_groups_id);
                        grp_name[j] = c.getString(TAG_groups_name);

                    }

                    for (int i = 0; i < groups_name.length; i++) {
                        for (int j = 0; j < grp_name.length; j++) {
                            if (groups_name[i].equalsIgnoreCase(grp_name[j])) {
                                _selections[j] = true; // ////// problem ///////////

                            }
                        }

                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
    }

    // ////////////////////////////////////////////////////////////////////////////////////////

    public class fil extends AsyncTask<String, String, String> {
        JSONArray grpid = new JSONArray();
        JSONArray ss_id = new JSONArray();


        /*JSONArray crmTag_id = new JSONArray();*/
        /*JSONParser jsonParser = new JSONParser();*/


        /*JSONObject filJson;*/
        String status="";
        private MyCustomProgressDialog dialog;
        private String url_create_product = Base_url
                + "lms_api/picture_diary/update_post";
        private static final String TAG_staus = "status";
        String description = "";


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
            try {
/*
                Log.i("imimimiim", Arrays.deepToString(imgsss));
*/
                Log.i("p_id", post_id);
                Log.i("auedit", auth_token);
                Log.i("gridididi", Arrays.deepToString(groupIdArr));
                Log.i("titititi", Arrays.deepToString(ti));
                Log.i("nwnwnw", Arrays.deepToString(groups_id));
                Log.i("curta", curTagIds);


                grpid = new JSONArray(Arrays.asList(groupIdArr));
                ss_id = new JSONArray(Arrays.asList(ti));
            /*    oimg = new JSONArray(Arrays.asList(imgsss));
                oimg_video = new JSONArray(Arrays.asList(videoss));*/
//                description = descript.getText().toString().trim();
                description = descript.getText().toString().replaceAll("\\n", "<br />");


                crmTag_id = new JSONArray(curTagIds);

                Log.i("crmTag_id", crmTag_id.toString());

            } catch (Exception e) {
                // TODO: handle exception
            }

        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {


            //curriculum_tag_ids
            String updateResponse = "";


            try {
                JSONObject reqJson = new JSONObject();

                reqJson.put("student_ids", ss_id);
                reqJson.put("images", imageJsonArr);
                reqJson.put("videos", videoJsonArr);
                reqJson.put("random_files", new JSONArray());
                reqJson.put("curriculum_tag_ids", crmTag_id);
                reqJson.put("securityKey", "H67jdS7wwfh");
                reqJson.put("group_ids", grpid);
                reqJson.put("language", lang);
                reqJson.put("authentication_token", auth_token);
                reqJson.put("description", description);
                reqJson.put("post_id", post_id);

                reqJson.put("ids_to_delete", idstoDelete);
                reqJson.put("mail", "no");



/*
String s="{\"post_id\": \"608\",\"ids_to_delete\": [],\"language\": \"en\",\"description\": \"From iOSS\",\"student_ids\": [],\"random_files\": [],\"images\": [],\"securityKey\":\"H67jdS7wwfh\",\"videos\": [],\"curriculum_tag_ids\": [\"66\", \"65\", \"64\"],\"group_ids\": [],\"authentication_token\": \"182d5c281d50a40929af8bd12c0c1b43b2ff6447\",\"mail\": \"no\"}";
*/


          /*      String fg = "{" + "\"student_ids\":" + ss_id + ","
                        + "\"images\":" + oimg + "," + "\"videos\":"
                        + oimg_video + "," + "\"random_files\":" + "[" + "]"
                        + "," + "\"curriculum_tag_ids\":" + crmTag_id + ","
                        + "\"securityKey\":" + "\"H67jdS7wwfh\"" + ","
                        + "\"group_ids\":" + grpid + ","
                        + "\"language\":" + JSONObject.quote(lang) + ","
                        + "\"authentication_token\":"
                        + JSONObject.quote(auth_token) + "," + "\"description\":" + JSONObject.quote(description)
                        + "," + "\"post_id\":" + JSONObject.quote(post_id)
                        + "," + "\"ids_to_delete\":" + "[" + "]" + "," + "\"mail\":" + "\"no\"" + "}";*/



              /* String json = new JSONObject("{\"" + "post_id" + "\":" + "\"" + post_id+ "\""
                        + "," + "\"" + "ids_to_delete" + "\":[]" + "," + "\"" + "language"
                        + "\":" + "\"" + curriculum+ "\"" + "}");*/


              /*  String fg1="{\n" +
                        "\t\"post_id:\"608\",\n" +
                        "\t\"ids_to_delete\": [],\n" +
                        "\t\"language\": \"en\",\n" +
                        "\t\"description\": \"From iOSS\",\n" +
                        "\t\"student_ids\": [],\n" +
                        "\t\"random_files\": [],\n" +
                        "\t\"images\": [],\n" +
                        "\t\"securityKey\": \"H67jdS7wwfh\",\n" +
                        "\t\"videos\": [],\n" +
                        "\t\"curriculum_tag_ids\": [\"66\", \"65\", \"64\"],\n" +
                        "\t\"group_ids\": [],\n" +
                        "\t\"authentication_token\": \"182d5c281d50a40929af8bd12c0c1b43b2ff6447\",\n" +
                        "\t\"mail\": \"no\"\n" +
                        "}";
*/
                //JSONObject


                String curriculamStr = "[";

            /*    if (selected_cur_tag.length > 0) {
                    for (int i = 0; i < selected_cur_tag.length; i++) {
//[\"66\", \"65\", \"64\"]

                        // ["66", "65", "64"]


                        if (i == selected_cur_tag.length - 1) {
                            curriculamStr +='"'+ File.separator + selected_cur_tag[i] + File.separator+'"' ;
                        } else {
                            curriculamStr += '"'+ File.separator+ selected_cur_tag[i]  + File.separator+'"'+", ";

                        }


                    }

                    curriculamStr += "]";
                } else if (selected_cur_tag.length == 1) {
                    curriculamStr += '"'+ File.separator +selected_cur_tag[0] +File.separator+'"';
                    curriculamStr += "]";
                } else {
                    curriculamStr = "[]";
                }*/
/*   List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("jsonData", "{\n" +
                        "\t\"post_id\": \"" + post_id + "\",\n" +
                        "\t\"ids_to_delete\": [],\n" +
                        "\t\"language\": \"" + lang + "\",\n" +
                        "\t\"description\": \"" + description + "\",\n" +
                        "\t\"student_ids\": " + ss_id + ",\n" +
                        "\t\"random_files\": [],\n" +
                        "\t\"images\": " + oimg + ",\n" +
                        "\t\"securityKey\": \"H67jdS7wwfh\",\n" +
                        "\t\"videos\": " + oimg_video + ",\n" +
                        "\t\"curriculum_tag_ids\": " +curriculamStr+ ",\n" +
                        "\t\"group_ids\": " + grpid + ",\n" +
                        "\t\"authentication_token\": \"" + auth_token + "\",\n" +
                        "\t\"mail\": \"no\"\n" +
                        "}"));
*/

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();

                Log.e("lllll", curriculamStr);


                try {
                    String urlParams = "&" + Const.Params.JsonData + "=" + URLEncoder.encode(reqJson.toString(), "UTF-8");


                    updateResponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
                } catch (Exception e) {
                    e.printStackTrace();
                }

             /*new    List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("jsonData", reqJson.toString()));

                filJson = jsonParser.makeHttpRequest(url_create_product,
                        "POST", params);


                // check for success tag

                Log.i("update post params", params.toString());
                Log.i("json data......", filJson.toString());
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            } catch (JSONException e1) {
                e1.printStackTrace();
            }

            return updateResponse;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {

                JSONObject filJson = new JSONObject();
                if (results != null && !results.isEmpty()) {
                    filJson = new JSONObject(results);
                    if (filJson.has(Const.Params.Status)) {
                        status = filJson.getString(TAG_staus);
                    } else {
                        status = "false";
                    }
                } else {
                    status = "false";
                }

                if (status.equalsIgnoreCase("true")) {
                    session.cur_tag_ids("[]");
                    FragmentManager fragmentManager = getFragmentManager();
                    MainActivity Setting_frg = new MainActivity();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.content_frame, Setting_frg);
                    ft.commit();

                } else {
                    try {


                        String msg = filJson.getString("message");
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

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    // ////////////////////////////////////////////////////////////////////////////////////////
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
            String grpStuResponse = "";
            try {
                JSONArray mJSONArray = new JSONArray(Arrays.asList(groupIdArr));

                String ui = "{" + "\"group_ids\"" + ":" + mJSONArray + ","
                        + "\"securityKey\"" + ":" + "\"H67jdS7wwfh\"" + ","
                        + "\"language\"" + ":" + JSONObject.quote(lang) + ","
                        + "\"authentication_token\"" + ":"
                        + JSONObject.quote(auth_token) + "}";

                JSONObject reqJSONObject = new JSONObject();

                reqJSONObject.put(Const.Params.SecurityKey, JSONObject.quote("H67jdS7wwfh"));
                reqJSONObject.put(Const.Params.AuthToken, JSONObject.quote(auth_token));
                reqJSONObject.put(Const.Params.Language, JSONObject.quote(lang));
                reqJSONObject.put(Const.Params.GroupIds, JSONObject.quote(mJSONArray.toString()));


                String urlParams = "&" + Const.Params.JsonData + "=" + URLEncoder.encode(ui, "UTF-8");


                grpStuResponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }

               /* //	Log.i("w array...", Arrays.deepToString(w));
                // {"group_ids":["66","40"],"securityKey":"H67jdS7wwfh","language":"en","authentication_token":"a0790005b5646c244434da977cd8cd94beb04baf"}
                String ui = "{" + "\"group_ids\"" + ":" + mJSONArray + ","
                        + "\"securityKey\"" + ":" + "\"H67jdS7wwfh\"" + ","
                        + "\"language\"" + ":" + JSONObject.quote(lang) + ","
                        + "\"authentication_token\"" + ":"
                        + JSONObject.quote(auth_token) + "}";

                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("jsonData", ui));

                JSONObject json = jsonParser.makeHttpRequest(
                        url_create_product, "POST", params);

                // check for success tag
                Log.i("json data......", json.toString());

                status = json.getString(TAG_staus);

                //	Log.e("=-=-=-=-=-=-", status);

                JSONArray grps_name = json.optJSONArray(TAG_students);

                std_id = new String[grps_name.length()];
                std_name = new String[grps_name.length()];
                _selectionstwo = new boolean[grps_name.length()];
                for (int j = 0; j < grps_name.length(); j++) {
                    JSONObject c = grps_name.getJSONObject(j);
                    //
                    std_id[j] = c.getString(TAG_student_id);
                    std_name[j] = c.getString(TAG_Student_name);

                    // Log.i("std_id..", std_id[j]);
                    // Log.i("std_name", std_name[j]);

                }

                if (status.equalsIgnoreCase("true")) {

                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
*/
            return grpStuResponse;
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
                    if (jsonObject.has(Const.Params.Status)) {
                        status = jsonObject.getString(TAG_staus);
                    } else {
                        status = "false";
                    }
                } else {
                    status = "false";
                }
                if (status.equalsIgnoreCase("true")) {
                    JSONArray grps_name = jsonObject.optJSONArray(TAG_students);

                    std_id = new String[grps_name.length()];
                    std_name = new String[grps_name.length()];
                    _selectionstwo = new boolean[grps_name.length()];
                    for (int j = 0; j < grps_name.length(); j++) {
                        JSONObject c = grps_name.getJSONObject(j);
                        std_id[j] = c.getString(TAG_student_id);
                        std_name[j] = c.getString(TAG_Student_name);
                    }


                } else {

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


                    // failed to create product
                    Toast.makeText(getActivity(), "Group cannot be empty....",
                            Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                // TODO: handle exception
            }

        }
    }

    // ////////////////////////////////////////////////////////////////////

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
                catch (NullPointerException e1)
                {
                    e1.printStackTrace();
                }
                bytes = output.toByteArray();
                String encodedString = Base64.encodeToString(bytes,
                        Base64.DEFAULT);
                output.reset();

                //	Log.i("base6444444", encodedString);

                //encoded_code.add(encodedString);
                String fileExtension = imagePath.substring(imagePath.lastIndexOf(".") + 1);

                imageVideoRandomList.add(new ImageVideoBean("0", encodedString, "image", imagePath));

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

                //		Log.i("base64...", encodedString_video);

                String fileExtension = imagePath.substring(imagePath.lastIndexOf(".") + 1);

                imageVideoRandomList.add(new ImageVideoBean("0", encodedString_video, "video", imagePath));
                //.add(encodedString_video);

                // ///////////////////////////////////////////

            }
        }


/*
        al.add(imagePath);
*/
        // step ii: notify to adapter
        // tvDescr.setText(imagePath);
        aa = new ArrayAdapterEdit(getActivity(), imageVideoRandomList);
        im_vdo.setAdapter(aa);
        aa.notifyDataSetChanged();
        setListViewHeightBasedOnChildrener(im_vdo);

    }


    class DeletePost extends AsyncTask<String, String, String> {


        /*JSONParser jsonParser = new JSONParser();*/
        String title;
        String status;
        private MyCustomProgressDialog dialog;
        private String url_create_product = Base_url
                + "lms_api/picture_diary/delete_post";

        private static final String TAG_staus = "status";
         String TAG_message = Const.Params.Message;
        String post_id = "";

        public DeletePost(String post_id) {
            this.post_id = post_id;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new MyCustomProgressDialog(getActivity());
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }


        protected String doInBackground(String... args) {


            String deleteResponse = "";
            try {

                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode("H67jdS7wwfh", "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.PostId + "=" + URLEncoder.encode(post_id, "UTF-8");


                deleteResponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }

           /* // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("securityKey", "H67jdS7wwfh"));
            params.add(new BasicNameValuePair("authentication_token",
                    auth_token));
            params.add(new BasicNameValuePair("language", lang));
            params.add(new BasicNameValuePair("post_id", post_id));

            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);
            Log.i("json data......", params.toString());
*/

            return deleteResponse;
        }


        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();

            // Log.i("oooooo", imm[0]);
            // ///////////////////////////////////////////////////


            try {


                JSONObject retriveJson = new JSONObject();

                if (results != null && !results.isEmpty()) {

                    retriveJson = new JSONObject(results);

                    if (retriveJson.has(Const.Params.Status)) {
                        status = retriveJson.getString(TAG_staus);

                    } else {
                        status = "false";
                    }


                } else {
                    status = "false";
                    SmartClassUtil.showToast(getActivity(), "Service Failed");
                }




            /*    GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                status = retriveJson.getString(TAG_staus);*/


                if (status.equalsIgnoreCase("true")) {
                    if (retriveJson.getString(TAG_message).equalsIgnoreCase("Deleted")) {
                        SmartClassUtil.showToast(getActivity(), "Post Deleted");
                        FragmentManager fragmentManager = getFragmentManager();
                        MainActivity Setting_frg = new MainActivity();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, Setting_frg);
                        ft.commit();


                    }
                } else {
                    try {


                        String msg = retriveJson.getString("message");
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
                    SmartClassUtil.showToast(getActivity(), "Authentication Failed");

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    private boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getActivity()
                .getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            // Toast.makeText(getApplicationContext(),
            // "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
