package com.pnf.elar.app;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.HashMap;
/*
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONObject;


import com.elar.util.NetworkUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class Child_Info extends Fragment {

    View v;
    UserSessionManager session;
    String securityKey = "H67jdS7wwfh", lang="", auth_token="", Base_url="", user_name="", user_id, user_typ, child_id="", user_fname="", user_lname="", user_image="";
    EditText c_info, Allgies, Add_text;
    Button SaveChildInfo, close;
    TextView Child_Info, Contact_Information, Allergies, Additional_text;

    ImageView childImage;

    TextView childFnameText, childLnameText;
    Button editImageBtn, changeImageBtn, removeImageBtn, closeImageBtn;
    LinearLayout editLayout;

    String childImagePath = "", imageEncodedString = "";

    ImageLoader imageLoader;

    Animation fade_in_anim, fade_out_anim;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_child__info, container, false);

        session = new UserSessionManager(getActivity());

        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        auth_token = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        user_name = user.get(UserSessionManager.TAG_username);
        user_id = user.get(UserSessionManager.TAG_user_id);


        /*System.out.println("getUserId"+session.getUserId());*/

        if( user.get(UserSessionManager.TAG_child_id)!=null)
        {
            child_id = user.get(UserSessionManager.TAG_child_id);


        }
        else
        {
            child_id = user.get(UserSessionManager.TAG_user_id);

        }
        /*child_id = user.get(UserSessionManager.TAG_child_id);*/
        user_typ = user.get(UserSessionManager.TAG_user_type);
        user_fname = user.get(UserSessionManager.TAG_USR_FirstName);
        user_lname = user.get(UserSessionManager.TAG_USR_LastName);
        if(user.get(UserSessionManager.TAG_child_image)!=null) {
            user_image = user.get(UserSessionManager.TAG_child_image);
        }

        Log.e("name ", user_image);

        //////////back to Main Activity ////Set title/////

        initView(v);
        ((Drawer) getActivity()).setActionBarTitle(getString(R.string.childInfo));
        editImageBtn.setText(getString(R.string.edit));


        childImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
           /* Intent in = new Intent(_context,View_pageraimages.class);
            in.putExtra("View_pager_images",img[position]);
            startActivity(in);*/
            }
        });

        if (user_typ.equalsIgnoreCase("Parent")) {
            ((Drawer) getActivity()).setBackForChildEduedu();
            ((Drawer) getActivity()).Hideserch();
            ((Drawer) getActivity()).HideRefresh();
        } else {

            ((Drawer) getActivity()).Backtomain();


        }

        new getchildinfo().execute();

        if (lang.equalsIgnoreCase("sw")) {
            /*Child_Info.setText("Viktig Information");*/
            Contact_Information.setText("Kontaktinformation");
            Allergies.setText("Allergier");
            Additional_text.setText("Övrig information");
            close.setText("Stäng");
            SaveChildInfo.setText("Spara ändringar");

        }


        setCLickListners();

        return v;
    }


    public void initView(View v) {

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));


        c_info = (EditText) v.findViewById(R.id.c_info);
        Allgies = (EditText) v.findViewById(R.id.Allgies);
        Add_text = (EditText) v.findViewById(R.id.Add_text);
        SaveChildInfo = (Button) v.findViewById(R.id.SaveChildInfo);
        close = (Button) v.findViewById(R.id.close);
        Contact_Information = (TextView) v.findViewById(R.id.Contact_Information);
        Allergies = (TextView) v.findViewById(R.id.Allergies);
        Additional_text = (TextView) v.findViewById(R.id.Additional_text);


        //new

        childImage = (ImageView) v.findViewById(R.id.childImage);
        childFnameText = (TextView) v.findViewById(R.id.childFnameText);
        childLnameText = (TextView) v.findViewById(R.id.childLnameText);
        editImageBtn = (Button) v.findViewById(R.id.editImageBtn);

        editLayout = (LinearLayout) v.findViewById(R.id.editLayout);
        changeImageBtn = (Button) v.findViewById(R.id.changeImageBtn);
        removeImageBtn = (Button) v.findViewById(R.id.removeImageBtn);
        closeImageBtn = (Button) v.findViewById(R.id.closeImageBtn);


        if (lang.equalsIgnoreCase("sw")) {

            changeImageBtn.setBackgroundResource(R.drawable.replace_sw);
            removeImageBtn.setBackgroundResource(R.drawable.remove_sw);
            closeImageBtn.setBackgroundResource(R.drawable.close_sw);


        } else if (lang.equalsIgnoreCase("en")) {
            changeImageBtn.setBackgroundResource(R.drawable.replace);
            removeImageBtn.setBackgroundResource(R.drawable.remove);
            closeImageBtn.setBackgroundResource(R.drawable.close);


        }


        imageLoader.displayImage(user_image, childImage);


        setChildInfo();
    }

    public void setChildInfo() {

        childFnameText.setText(user_fname);
        childLnameText.setText(user_lname);
    }

    public void setCLickListners() {

        fade_in_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);

        fade_out_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
        close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                        if (user_typ.equalsIgnoreCase("parent")) {
                            FragmentManager fragmentManager = getFragmentManager();
                            ParentChildComponent rFragment = new ParentChildComponent();
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            ft.replace(R.id.content_frame, rFragment);
                            ft.commit();
                        } else {

                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


            }
        });
        SaveChildInfo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                        new getchildinfoupdate().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        editImageBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editLayout.getVisibility() == View.VISIBLE) {

                    hideEdit();

                } else {
                    showEdit();
                }

            }
        });


        changeImageBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, Utils.LOAD_IMAGE_RESULTS);
            }
        });

        removeImageBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                childImage.setImageResource(R.drawable.manjit);
                imageEncodedString = "";
            }
        });
        closeImageBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideEdit();
            }
        });
    }

    public void hideEdit() {

        try {
            editLayout.setVisibility(View.GONE);
            editLayout.setAnimation(fade_out_anim);
            fade_out_anim.start();


                    /*Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        }
                    }, 2000);*/
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void showEdit() {
        editLayout.setAnimation(fade_in_anim);
        fade_in_anim.start();
        editLayout.setVisibility(View.VISIBLE);
    }

    ////////////
    class getchildinfo extends AsyncTask<String, String, String> {

        String Status="", allergy, cont_info, info;
        private String url = Base_url + "lms_api/allergies/get_child_info";

        private static final String TAG_STATUS = "status";
        private static final String TAG_child_info = "child_info";
        private static final String TAG_allergy_name = "allergy_name";
        private static final String TAG_contact_info = "contact_info";
        private static final String TAG_information = "information";

        private MyCustomProgressDialog dialog;

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


            String getChildresponse = "";
            try {

                Log.d("tes", securityKey);
                Log.d("", auth_token);
                Log.d("", lang);
                Log.d("", child_id);

                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(securityKey, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.StudentId + "=" + URLEncoder.encode(child_id, "UTF-8");

                getChildresponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }


            // Log.i("Base_urlBase_url", Base_url);
           /* Log.i("user_id", user_id);
            JSONParser jsonParser = new JSONParser();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("securityKey", securityKey));
            params.add(new BasicNameValuePair("authentication_token",
                    auth_token));
            params.add(new BasicNameValuePair("language", lang));
            params.add(new BasicNameValuePair("student_id", child_id));

            Log.d("Create Response", params.toString());

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

            Log.d("Create Response", json.toString());

            try {

                JSONObject jsonObj = new JSONObject(json.toString());

                Status = jsonObj.getString(TAG_STATUS);

                JSONObject user = jsonObj.getJSONObject(TAG_child_info);

                allergy = user.getString(TAG_allergy_name);
                cont_info = user.getString(TAG_contact_info);
                info = user.getString(TAG_information);

                if (Status.equalsIgnoreCase("true")) {

                } else {

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            return getChildresponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {
                JSONObject jsonObj = new JSONObject();
                if (results != null && !results.isEmpty()) {
                    jsonObj = new JSONObject(results);
                    if (jsonObj.has(Const.Params.Status)) {
                        Status = jsonObj.getString(TAG_STATUS);
                    } else {
                    }
                } else {

                }
                if (Status.equalsIgnoreCase("true")) {
                    JSONObject user = jsonObj.getJSONObject(TAG_child_info);
                    allergy = user.getString(TAG_allergy_name);
                    cont_info = user.getString(TAG_contact_info);
                    info = user.getString(TAG_information);


                   /* if (Build.VERSION.SDK_INT >= 24) {

                        c_info.setText(Html.fromHtml(cont_info, Html.FROM_HTML_MODE_LEGACY));
                        Allgies.setText(Html.fromHtml(allergy, Html.FROM_HTML_MODE_LEGACY));
                        Add_text.setText(Html.fromHtml(info, Html.FROM_HTML_MODE_LEGACY));
                    }
                    else
                    {*/
                        c_info.setText(Html.fromHtml(cont_info));
                        Allgies.setText(Html.fromHtml(allergy));
                        Add_text.setText(Html.fromHtml(info));
                   /* }*/

                    c_info.setText(cont_info);
                    Allgies.setText(allergy);
                    Add_text.setText(info);
                } else {
                    /*Toast.makeText(getActivity(), " error" + "....",
                            Toast.LENGTH_LONG).show();*/

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
            }
        }

    }

    // /////////
    // /////////
    // /////////
    class getchildinfoupdate extends AsyncTask<String, String, String> {

        String Status, msg;
        private String url = Base_url + "lms_api/allergies/update_child_info";

        private static final String TAG_STATUS = "status";
        String TAG_message = Const.Params.Message;

        private MyCustomProgressDialog dialog;
        String urlParams = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {


                urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(securityKey, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.AllergyName + "=" + URLEncoder.encode(Allgies.getText().toString().trim(), "UTF-8") +
                        "&" + Const.Params.ContactInfo + "=" + URLEncoder.encode(c_info.getText().toString(), "UTF-8") +
                        "&" + Const.Params.Information + "=" + URLEncoder.encode(Add_text.getText().toString(), "UTF-8") +
                        "&" + Const.Params.StudentId + "=" + URLEncoder.encode(child_id, "UTF-8");

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
            String childInforesponse = "";
            try {

                childInforesponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);

            } catch (Exception e) {
                e.printStackTrace();
            }
            // Log.i("Base_urlBase_url", Base_url);
            // Log.i("language", language);
           /* JSONParser jsonParser = new JSONParser();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("securityKey", securityKey));
            params.add(new BasicNameValuePair("authentication_token",
                    auth_token));
            params.add(new BasicNameValuePair("language", lang));
            params.add(new BasicNameValuePair("allergy_name", Allgies.getText().toString()));
            params.add(new BasicNameValuePair("contact_info", c_info.getText()
                    .toString()));
            params.add(new BasicNameValuePair("information", Add_text.getText()
                    .toString()));
            params.add(new BasicNameValuePair("student_id", child_id));

            Log.d("Create Response", params.toString());

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

            Log.d("Create Response", json.toString());

            try {

                JSONObject jsonObj = new JSONObject(json.toString());

                Status = jsonObj.getString(TAG_STATUS);
                msg = jsonObj.getString(TAG_message);

                if (Status.equalsIgnoreCase("true")) {

                } else {

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            return childInforesponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {

                JSONObject jsonObj = new JSONObject();


                if (results != null && !results.isEmpty()) {

                    jsonObj = new JSONObject(results);

                    if (jsonObj.has("status")) {

                        Status = jsonObj.getString(TAG_STATUS);
                        msg = jsonObj.getString(TAG_message);
                    } else {

                        Status = "false";

                    }

                } else {
                    Status = "false";
                }

                if (Status.equalsIgnoreCase("true")) {
                    if(lang.equalsIgnoreCase("sw"))
				{
						 msg = "Frånvaroanmälan har skickats in";

				}
					else {
						 msg = "Note of absence has been added";
				}

                    Toast.makeText(getActivity(), msg,
                            Toast.LENGTH_LONG).show();

                    if (user_typ.equalsIgnoreCase("parent")) {
//						String msg;
//						if(lang.equalsIgnoreCase("sw"))
//						{
//							 msg = "Frånvaroanmälan har skickats in";
//
//						}
//						else {
//							 msg = "Note of absence has been added";
//						}

                        FragmentManager fragmentManager = getFragmentManager();
                        ParentChildComponent rFragment = new ParentChildComponent();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, rFragment);
                        ft.commit();


//						AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
//				        alertDialog.setTitle("");
//				        alertDialog.setMessage(msg);
//				        alertDialog.setIcon(R.drawable.tick);
//				        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//						public void onClick(DialogInterface dialog, int which) 
//						{
//							FragmentManager fragmentManager = getFragmentManager();
//							ParentChildComponent rFragment = new ParentChildComponent();
//							FragmentTransaction ft = fragmentManager.beginTransaction();
//							ft.replace(R.id.content_frame, rFragment);
//							ft.commit();
//						}
//				});
//
//				// Showing Alert Message
//				alertDialog.show();

                    }
                    else
                    {

                    }


                } else {
                    // failed to create product
                    Toast.makeText(getActivity(), " error" + "....",
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) throws NullPointerException {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Utils.LOAD_IMAGE_RESULTS
                && resultCode == Activity.RESULT_OK && data != null) {
            InputStream inputStream = null;

            try {

                // Let's read picked image data - its URI
                Uri pickedImage = data.getData();
                // Let's read picked image path using content resolver
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(
                        pickedImage, filePath, null, null, null);

                cursor.moveToFirst();
                childImagePath = cursor
                        .getString(cursor.getColumnIndex(filePath[0]));
                // i_path = imagePath;

                cursor.close();


                try {
                    inputStream = new FileInputStream(childImagePath);
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }// You can get an inputStream using any IO API
                //	Bitmap tt = BitmapFactory.decodeFile(imagePath);   ////// new

                Bitmap tt = Publish.BitmapHelper.decodeFile(childImagePath, 50, 50, true);
                byte[] bytes;
                byte[] buffer = new byte[8192];
                int bytesRead;
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }

//				bytes = output.toByteArray();
                tt.compress(Bitmap.CompressFormat.JPEG, 50, output);    ///// new
                bytes = output.toByteArray();


                imageEncodedString = Base64.encodeToString(bytes,
                        Base64.DEFAULT);


                childImage.setImageBitmap(tt);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }
    }
}
