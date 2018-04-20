package com.pnf.elar.app;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONException;
import org.json.JSONObject;

import com.elar.util.SmartClassUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pnf.elar.app.Bo.RetriveDraftBo;

import com.pnf.elar.app.activity.FilterSaveDraftActivity;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SaveDraft extends Fragment {
    TextView mainFilterText,dateTextView,byTextView,forTextView;
    TextView datetext, by, grps;
    View v;
    UserSessionManager session;
    String lang, Base_url, auth_token;
    ListView ListSaveDraft;
    int status = 0;
    Context context;

    Button removeDraftBtn, useDraftBtn;
    List<RetriveDraftBo.DraftsEntity> retriveList = new ArrayList<>();
    String post_id;

    int deletePos;
    CustomAdpterSavedraft caSavedraft;


    RelativeLayout filterMinLayout, filter_save_draft;

    String my_draft = "", description_value = "", from_date = "", to_date = "";

    String selectedDraft = "";

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_save_draft, container, false);

        session = new UserSessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        auth_token = user.get(UserSessionManager.TAG_Authntication_token);


        initView(v);

        context = getActivity();
        ((Drawer) getActivity()).setBackFrompublishtomain();

        if (lang.equalsIgnoreCase("sw")) {
            ((Drawer) getActivity()).setActionBarTitle("Sparade Utkast");
        } else {
            ((Drawer) getActivity()).setActionBarTitle("Save Draft");

        }
        ((Drawer)getActivity()).Hideserch();
        ((Drawer) getActivity()).HideRefresh();

        ((Drawer) getActivity()).setBackFromDraftToPublish();
        if (isOnline()) {
            new get_drafts().execute();
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

            }

        }


        return v;
    }

    public void initView(View rootView) {

        mainFilterText=(TextView)rootView.findViewById(R.id.mainFilterText);
        dateTextView=(TextView)rootView.findViewById(R.id.dateTextView);
        byTextView=(TextView)rootView.findViewById(R.id.byTextView);
        forTextView=(TextView)rootView.findViewById(R.id.forTextView);

        ListSaveDraft = (ListView) rootView.findViewById(R.id.ListSaveDraft);

        removeDraftBtn = (Button) rootView.findViewById(R.id.removeDraftBtn);
        useDraftBtn = (Button) rootView.findViewById(R.id.useDraftBtn);



        /*Filter*/

        filterMinLayout = (RelativeLayout) rootView.findViewById(R.id.filterMinLayout);

        filter_save_draft = (RelativeLayout) rootView.findViewById(R.id.filter_save_draft);


        getValues();
        setClickListeners();
        setLanguage();
    }

    public void setLanguage()
    {
        if(lang.equalsIgnoreCase("en"))
        {
            mainFilterText.setText("Filter");
            dateTextView.setText("Date");
            byTextView.setText("By");
            forTextView.setText("For");
        }

        else
        {
            mainFilterText.setText("Filtrera");
            dateTextView.setText("Datum");
            byTextView.setText("Av");
            forTextView.setText("För");



        }
    }

    public void getValues() {
        try {
            Bundle bundle = SaveDraft.this.getArguments();
            if (bundle != null) {
                description_value = bundle.getString("description_value");
                from_date = bundle.getString("from_date");
                to_date = bundle.getString("to_date");
                my_draft = bundle.getString("my_draft");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setClickListeners() {
        removeDraftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!post_id.equalsIgnoreCase("0")) {
                    DeleteDraft deleteDraft = new DeleteDraft(post_id);
                    if (AsyncTask.Status.RUNNING == deleteDraft.getStatus()) {
                        deleteDraft.cancel(true);
                        deleteDraft = new DeleteDraft(post_id);
                    } else if (AsyncTask.Status.FINISHED == deleteDraft.getStatus()) {
                        deleteDraft = new DeleteDraft(post_id);
                    }


                    deleteDraft.execute();
                } else {

                }


            }
        });

        useDraftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                Publish publishFragment = new Publish();
                Bundle bundle = new Bundle();
                bundle.putString("draftObject", selectedDraft);

                publishFragment.setArguments(bundle);
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, publishFragment);
                ft.commit();

            }
        });

        filterMinLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent intent = new Intent(getActivity(), FilterSaveDraftActivity.class);
                startActivity(intent);*/

                try {
                    FragmentManager frfmnr_mngr = getFragmentManager();

                    FilterSaveDraftActivity post_filtter_fr = new FilterSaveDraftActivity();

                    FragmentTransaction ft = frfmnr_mngr.beginTransaction();

                    ft.replace(R.id.content_frame, post_filtter_fr);

                    ft.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    // /////// check internet status///////
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        System.out.println("ss resume");
    }

    // ///////// get draft /////////////

    class get_drafts extends AsyncTask<String, String, String> {


        /*JSONParser jsonParser = new JSONParser();*/
        String title;
        String status;
        private MyCustomProgressDialog dialog;
        private String url_create_product = Base_url
                + "lms_api/picture_diary/get_drafts";
        private static final String TAG_video_imagename = "imagename";
        private static final String TAG_groups = "groups";
        private static final String TAG_name = "name";
        private static final String TAG_staus = "status";
        private static final String TAG_drafts = "drafts";

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
            // Building Parameters

            String draftsResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode("H67jdS7wwfh", "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8")+
                        "&" + Const.Params.FromDate + "=" + URLEncoder.encode(from_date, "UTF-8")+
                        "&" + Const.Params.ToDate + "=" + URLEncoder.encode(to_date, "UTF-8")+
                        "&" + Const.Params.DescriptionValue + "=" + URLEncoder.encode(description_value, "UTF-8")+
                        "&" + Const.Params.MyDrafts + "=" + URLEncoder.encode(my_draft, "UTF-8");


                draftsResponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }

          /*  List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("securityKey", "H67jdS7wwfh"));
            params.add(new BasicNameValuePair("authentication_token",
                    auth_token));
            params.add(new BasicNameValuePair("from_date", from_date));
            params.add(new BasicNameValuePair("to_date", to_date));

            params.add(new BasicNameValuePair("description_value", description_value));
            params.add(new BasicNameValuePair("language", lang));
            params.add(new BasicNameValuePair("my_draft", my_draft));

            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);
            Log.i("json data......", params.toString());*/


            return draftsResponse;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();

            // Log.i("oooooo", imm[0]);
            // ///////////////////////////////////////////////////

            JSONObject retriveJson = new JSONObject();
            try {




                if(results!=null&&!results.isEmpty())
                {
                    retriveJson=new JSONObject(results);

                    if(retriveJson.has(Const.Params.Status))
                    {
                        status = retriveJson.getString(TAG_staus);

                    }

                }


                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();


               /* if (retriveJson != null) {*/




                    if (status.equalsIgnoreCase("true")) {
                        RetriveDraftBo retriveResponse = gson.fromJson(results, RetriveDraftBo.class);


                        if (retriveResponse.getDrafts().size() > 0) {
                            retriveList.addAll(retriveResponse.getDrafts());
                        }

                        caSavedraft = new CustomAdpterSavedraft(
                                getActivity(), retriveList);
                        ListSaveDraft.setAdapter(caSavedraft);
                        ListSaveDraft.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);


                        ListSaveDraft.setOnItemClickListener(new OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                // TODO Auto-generated method stub #F29FC4

                                post_id = retriveList.get(position).getPictureDiary().getId();
                                deletePos = position;

                                selectedDraft = new Gson().toJson(retriveList.get(position));
                                System.out.println("id" + selectedDraft);


                                for (int j = 0; j < parent.getChildCount(); j++)
                                    parent.getChildAt(j).setBackgroundColor(Color.parseColor("#ED74A9"));

                                // change the background color of the selected element
                                view.setBackgroundColor(Color.parseColor("#D66A9A"));
                                /*caSavedraft.getItemId(position);*/

						/*date.setBackgroundColor(Color.parseColor("#F29FC4"));
                        by.setBackgroundColor(Color.parseColor("#F29FC4"));
						grps.setBackgroundColor(Color.parseColor("#F29FC4"));*/

					/*	LinearLayout retriveLayout=(LinearLayout)v.findViewById(R.id.retriveLayout);
                        retriveLayout.setBackgroundColor(Color.parseColor("#F29FC4"));*/


                            }
                        });
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

                        if(lang.equalsIgnoreCase("en")) {

                            SmartClassUtil.showToast(context, "Service Failed");
                        }
                        else
                        {
                            SmartClassUtil.showToast(context, "tjänsten Misslyckades");

                        }
                    }

				/*	for(int i=0;i<retriveList.size();i++)
                    {


						RetriveDraftBo.DraftsEntity draftsEntity=retriveList.get(i);
						for (int uk = 0; uk < draftsEntity.getImages().size(); uk++) {
							JSONObject rt = images.getJSONObject(uk);
							image[uk] = Base_url
									+ "picture_diary/viewPictureDiaryImages/"
									+ rt.optString("id") + "?authentication_token="
									+ auth_token;

							retriveList.get(i).getImages().get(uk).setI
						}


					}*/

/*
                    Log.e("=-=-=-=-=-=-", status);

                    JSONArray response = retriveJson.optJSONArray(TAG_drafts);
                    picdiary_created = new String[response.length()];
                    FirstName = new String[response.length()];

                    for (int j = 0; j < response.length(); j++) {
                        JSONObject c = response.getJSONObject(j);

                        JSONObject picdiary = c.getJSONObject("PictureDiary");
                        String picdiary_id = picdiary.getString("id");
                        String picdiary_description = picdiary
                                .getString("description");
                        picdiary_created[j] = picdiary.getString("created");

                        JSONObject PicturediaryTeacher = c
                                .getJSONObject("PicturediaryTeacher");
                        FirstName[j] = PicturediaryTeacher
                                .getString("USR_FirstName")
                                + "  "
                                + PicturediaryTeacher.getString("USR_LastName");
                        String LastName = PicturediaryTeacher
                                .getString("USR_LastName");

                        JSONArray PicturediaryContent = c
                                .optJSONArray("PicturediaryContent");

                        JSONArray PicturediaryGroup = c
                                .optJSONArray("PicturediaryGroup");

                        ClaClass_name = new String[PicturediaryGroup.length()];

                        for (int k = 0; k < PicturediaryGroup.length(); k++) {
                            JSONObject m = PicturediaryGroup.getJSONObject(k);

                            JSONObject ClaClass = m.getJSONObject("ClaClass");
                            String ClaClass_id = ClaClass.getString("id");
                            ClaClass_name[k] = ClaClass.getString("name");
                        }
                        groups.add(ClaClass_name);

                        JSONArray PicturediaryStudent = c
                                .optJSONArray("PicturediaryStudent");

                        for (int u = 0; u < PicturediaryStudent.length(); u++) {
                            JSONObject r = PicturediaryStudent.getJSONObject(u);

                        }

                        JSONArray images = c.optJSONArray("images");
                        image = new String[images.length()];
                        for (int uk = 0; uk < images.length(); uk++) {
                            JSONObject rt = images.getJSONObject(uk);
                            image[uk] = Base_url
                                    + "picture_diary/viewPictureDiaryImages/"
                                    + rt.optString("id") + "?authentication_token="
                                    + auth_token;


                        }

                        img.add(image);

                        JSONArray videos = c.optJSONArray("videos");

                        for (int ukv = 0; ukv < videos.length(); ukv++) {
                            JSONObject rtv = videos.getJSONObject(ukv);

                        }

                        JSONArray random_files = c.optJSONArray("random_files");

                        for (int ukvr = 0; ukvr < random_files.length(); ukvr++) {
                            JSONObject rtvr = random_files.getJSONObject(ukvr);

                        }

                    }*/


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    // /////////Save draft adpter////

    @SuppressWarnings("deprecation")
    public class CustomAdpterSavedraft extends ArrayAdapter<RetriveDraftBo.DraftsEntity> {
        private final Activity contextA;
        ImageView imgsavedraft;
        TextView imageCount;
        LayoutInflater inflater;
        View rowView;

        List<RetriveDraftBo.DraftsEntity> draftList = new ArrayList<>();


        public CustomAdpterSavedraft(Activity context,
                                     List<RetriveDraftBo.DraftsEntity> draftList) {
            super(context, R.layout.custmsavedraft, draftList);
            this.contextA = context;
            this.draftList = draftList;

        }

        @Override
        public View getView(final int position, View view,
                            final ViewGroup parent) {
            // imageicon=0;
            inflater = contextA.getLayoutInflater();
            inflater = (LayoutInflater) contextA
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.custmsavedraft, parent, false);
            datetext = (TextView) rowView.findViewById(R.id.datetext);
            by = (TextView) rowView.findViewById(R.id.by);
            grps = (TextView) rowView.findViewById(R.id.grps);
            imgsavedraft = (ImageView) rowView.findViewById(R.id.imgsavedraft);
            imageCount = (TextView) rowView.findViewById(R.id.imageCount);

            // Log.i("picdiary_created", picdiary_created[position]);


            RetriveDraftBo.DraftsEntity dratfObj = draftList.get(position);

            String fName = dratfObj.getPicturediaryTeacher().getUSR_FirstName();
            String lName = dratfObj.getPicturediaryTeacher().getUSR_LastName();
            int grpCount = draftList.get(position).getPicturediaryGroup().size();


            datetext.setText(dratfObj.getPictureDiary().getCreated());
            by.setText(fName + " " + lName);
          /*  if (grpCount > 1) {
                grps.setText(Integer.toString(grpCount) + " Groups");

            } else {
                grps.setText(Integer.toString(grpCount) + " Group");

            }*/


            grps.setText(draftList.get(position).getPictureDiary().getCategory());


            if (dratfObj.getPictureDiary().getCategory().equalsIgnoreCase("school")) {
                if (lang.equalsIgnoreCase("en")) {

                    grps.setText("Preschool");
                } else {
                    grps.setText("Förskola");
                }

            } else if (dratfObj.getPictureDiary().getCategory().equalsIgnoreCase("student")) {
                String stuNames = "";
                int studentCount = dratfObj.getPicturediaryStudent().size();
                if (studentCount >= 3) {
                    stuNames = dratfObj.getPicturediaryStudent().get(0).getStudent().getUSR_FirstName() + " " + dratfObj.getPicturediaryStudent().get(1).getStudent().getUSR_FirstName() + "....";

                } else if (studentCount == 2) {
                    stuNames = dratfObj.getPicturediaryStudent().get(0).getStudent().getUSR_FirstName() + " " + dratfObj.getPicturediaryStudent().get(1).getStudent().getUSR_FirstName();
                } else if (studentCount == 1) {
                    stuNames = dratfObj.getPicturediaryStudent().get(0).getStudent().getUSR_FirstName();
                } else {
                    stuNames = "";
                }
                grps.setText(stuNames);

            } else if (dratfObj.getPictureDiary().getCategory().equalsIgnoreCase("class")) {

                String groupNames = "";

                if (dratfObj.getPicturediaryGroup().size() == 0) {

                } else if (dratfObj.getPicturediaryGroup().size() >= 3) {

                    groupNames = dratfObj.getPicturediaryGroup().get(0).getClaClass().getName() + " " + dratfObj.getPicturediaryGroup().get(1).getClaClass().getName() + "....";
                } else if (dratfObj.getPicturediaryGroup().size() == 2) {
                    groupNames = dratfObj.getPicturediaryGroup().get(0).getClaClass().getName() + " " + dratfObj.getPicturediaryGroup().get(1).getClaClass().getName() + "";
                } else if (dratfObj.getPicturediaryGroup().size() == 1) {
                    groupNames = dratfObj.getPicturediaryGroup().get(0).getClaClass().getName();

                }
                grps.setText(groupNames);

            }


            System.out.println("len " + draftList.get(position).getImages().size());

            int imageCountVal = 0;

            if (draftList.get(position).getImages().size() > 0) {
                imageCount.setVisibility(View.VISIBLE);

                int imgPos = draftList.get(position).getImages().size() - 1;

                imageCountVal = draftList.get(position).getImages().size();


                String imagePath = Base_url
                        + "picture_diary/viewPictureDiaryImages/"
                        + draftList.get(position).getImages().get(imgPos).getId() + "?authentication_token="
                        + auth_token;

                Log.d("imagePath", imagePath);

                //  new ImageLoadTaskclip(imagePath, imgsavedraft).execute();


                Picasso.with(context)
                        .load(Uri.parse(imagePath))
                        // optional
                        .error(R.color.RED)         // optional
                        .into(imgsavedraft);

            } else {
/*
                imageCount.setVisibility(View.GONE);
*/
            }


            if (draftList.get(position).getVideos().size() > 0) {


                imageCountVal += draftList.get(position).getVideos().size();


              /*  String videoPath = Base_url
                        + "picture_diary/viewPictureDiaryImages/"
                        + draftList.get(position).getVideos().get(videoPos).getId() + "?authentication_token="
                        + auth_token;
                new ImageLoadTaskclip(videoPath, imgsavedraft).execute();*/
            }
            if (imageCountVal > 0) {
                imageCount.setVisibility(View.VISIBLE);

            } else {
                imageCount.setVisibility(View.GONE);

            }
            imageCount.setText(Integer.toString(imageCountVal));

            return rowView;
        }

        @Override
        public RetriveDraftBo.DraftsEntity getItem(int position) {
            return draftList.get(position);
        }

        // ////////////////////////////////////////////////////////////////
        public TextView findViewById(int catagry) {
            // TODO Auto-generated method stub
            return null;
        }

    }

    ///////// image loader class ////////////////
    class ImageLoadTaskclip extends AsyncTask<Void, Void, Bitmap> {
        ProgressDialog pDialog;
        private String url;
        private ImageView imageView;

        public ImageLoadTaskclip(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
//			 pDialog = new ProgressDialog(_context);
//			 pDialog.setMessage("load image ....");
//			 pDialog.setIndeterminate(false);
//			 pDialog.setCancelable(false);
//			 pDialog.show();
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            // pDialog.dismiss();
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(result);


        }

    }
    ////////////


    class DeleteDraft extends AsyncTask<String, String, String> {


        /*JSONParser jsonParser = new JSONParser();*/
        String title;
        String status="";
        private MyCustomProgressDialog dialog;
        private String url_create_product = Base_url
                + "lms_api/picture_diary/delete_post";

        private static final String TAG_staus = "status";
        private  final String TAG_message = Const.Params.Message;
        String post_id = "";

        public DeleteDraft(String post_id) {
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
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8")+
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


                if (results != null && !results.isEmpty()) {

                    JSONObject retriveJson = new JSONObject(results);


                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    status = retriveJson.getString(TAG_staus);


                    if (status.equalsIgnoreCase("true")) {
                        if (retriveJson.getString(TAG_message).equalsIgnoreCase("Deleted")) {
                            if(lang.equalsIgnoreCase("en")) {
                                SmartClassUtil.showToast(getActivity(), "Draft item Deleted");
                            }
                            else
                            {


                                SmartClassUtil.showToast(getActivity(), "Utkast post Utgår");

                            }
                            retriveList.remove(deletePos);
                            caSavedraft.notifyDataSetChanged();
                            post_id = "0";
                        }
                    }

                } else {
                    if(lang.equalsIgnoreCase("en")) {

                        SmartClassUtil.showToast(context, "Service Failed");
                    }
                    else
                    {
                        SmartClassUtil.showToast(context, "tjänsten Misslyckades");

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


}
