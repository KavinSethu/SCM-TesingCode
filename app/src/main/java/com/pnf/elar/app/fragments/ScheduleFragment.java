package com.pnf.elar.app.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.elar.util.NetworkUtil;
import com.pnf.elar.app.Drawer;
import com.pnf.elar.app.FootMenuPdfView;

import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.ParentChildComponent;
import com.pnf.elar.app.R;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;

/*import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by VKrishnasamy on 15-09-2016.
 */


@SuppressWarnings("deprecation")
public class ScheduleFragment extends Fragment {

    View v;
    UserSessionManager session;
    String securityKey = "H67jdS7wwfh", lang, auth_token, Base_url, user_name, user_id, user_typ, child_id;
    Button foodMenuBtn;
    Context context;

    List<String> foodMenuList;

    Dialog dialogPDF;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.schedule_fragment, container, false);

        context = getActivity();

        session = new UserSessionManager(getActivity());

        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        auth_token = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        user_name = user.get(UserSessionManager.TAG_username);
        user_id = user.get(UserSessionManager.TAG_user_id);
        child_id = user.get(UserSessionManager.TAG_child_id);
        user_typ = user.get(UserSessionManager.TAG_user_type);

        //////////back to Main Activity ////Set title/////

        initView(v);

        if (lang.equalsIgnoreCase("sw")) {
            ((Drawer) getActivity()).setActionBarTitle("Schema");
            foodMenuBtn.setText("Öppna matsedel");

        } else {
            ((Drawer) getActivity()).setActionBarTitle("Schedule");
            foodMenuBtn.setText("Open Food Menu");
        }

        ((Drawer) getActivity()).showbackbutton();

        if (user_typ.equalsIgnoreCase("Parent")) {
            ((Drawer) getActivity()).setBackForChildEduedu();
            ((Drawer) getActivity()).Hideserch();
            ((Drawer) getActivity()).HideRefresh();


        } else {

            ((Drawer) getActivity()).Hideserch();
            ((Drawer) getActivity()).HideRefresh();
            ((Drawer) getActivity()).Backtomain();


        }

/*
        new getchildinfo().execute();
*/


        initView(v);
        pdfDialog();
        return v;
    }


    public void initView(View v) {
        //http://maven.apache.org/maven-1.x/maven.pdf

        foodMenuBtn = (Button) v.findViewById(R.id.foodMenuBtn);

        foodMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfDialog();
            }
        });
    }


    public void pdfDialog() {


        foodMenuList = new ArrayList<>();

        foodMenuList.add("Food Menu 1");
        foodMenuList.add("Food Menu 2");
        foodMenuList.add("Food Menu 3");
        foodMenuList.add("Food Menu 4");
        foodMenuList.add("Food Menu 5");
        foodMenuList.add("Food Menu 6");
        foodMenuList.add("Food Menu 7");

        dialogPDF = new Dialog(context);
        dialogPDF.setContentView(R.layout.dialoglayout);
        if (lang.equalsIgnoreCase("sw")) {

            dialogPDF.setTitle("Matsedel");
        } else {
            dialogPDF.setTitle("Food Menu");
        }
        dialogPDF.setCancelable(true);
        dialogPDF.setCanceledOnTouchOutside(true);

        ListView dialog_ListView = (ListView) dialogPDF
                .findViewById(R.id.dialoglist);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                context, android.R.layout.test_list_item,
                foodMenuList);
        dialog_ListView.setAdapter(adapter);
        dialogPDF.show();

        dialog_ListView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        // TODO Auto-generated method stub
                        dialogPDF.dismiss();

                        try {
                            if (NetworkUtil.getInstance(context).isInternet()) {
                                Intent pdfIntent = new Intent(context, FootMenuPdfView.class);
                                pdfIntent.putExtra("pdfUrl", adapter.getItem(position));
                                startActivity(pdfIntent);
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }


                    }
                });

        dialogPDF.show();
    }


    /* public void download(View v)
     {
         new DownloadFile().execute("http://maven.apache.org/maven-1.x/maven.pdf", "maven.pdf");
     }*/
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

            // Log.i("Base_urlBase_url", Base_url);
            Log.i("user_id", user_id);


            String childResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(securityKey, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.StudentId + "=" + URLEncoder.encode(child_id, "UTF-8");


                childResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }

           /* JSONParser jsonParser = new JSONParser();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("securityKey", securityKey));
            params.add(new BasicNameValuePair("authentication_token",
                    auth_token));
            params.add(new BasicNameValuePair("language", lang));
            params.add(new BasicNameValuePair("student_id", child_id));

            Log.d("Create Response", params.toString());

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

            Log.d("Create Response", json.toString());
*/


            return childResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {



                JSONObject jsonObj = new JSONObject();

                if(results!=null&&!results.isEmpty())
                {
                    jsonObj = new JSONObject(results);

                    if(jsonObj.has(Const.Params.Status))
                    {
                        Status = jsonObj.getString(TAG_STATUS);

                    }

                }





                if (Status.equalsIgnoreCase("true")) {

                    JSONObject user = jsonObj.getJSONObject(TAG_child_info);

                    allergy = user.getString(TAG_allergy_name);
                    cont_info = user.getString(TAG_contact_info);
                    info = user.getString(TAG_information);
                } else {

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

                    // failed to create product
                    Toast.makeText(getActivity(), " error" + "....",
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    // /////////
    // /////////
    // /////////
    class getchildinfoupdate extends AsyncTask<String, String, String> {

        String Status="", msg;
        private String url = Base_url + "lms_api/allergies/update_child_info";

         String TAG_STATUS = "status";
       String TAG_message = Const.Params.Message;

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
            String childResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(securityKey, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.StudentId + "=" + URLEncoder.encode(child_id, "UTF-8");


                childResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
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
            *//*params.add(new BasicNameValuePair("allergy_name", Allgies.getText()
                    .toString()));
            params.add(new BasicNameValuePair("contact_info", c_info.getText()
                    .toString()));
            params.add(new BasicNameValuePair("information", Add_text.getText()
                    .toString()));*//*
            params.add(new BasicNameValuePair("student_id", child_id));

            Log.d("Create Response", params.toString());

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

            Log.d("Create Response", json.toString());*/

            /*try {

                JSONObject jsonObj = new JSONObject(json.toString());

                Status = jsonObj.getString(TAG_STATUS);
                msg = jsonObj.getString(TAG_message);

                if (Status.equalsIgnoreCase("true")) {

                } else {

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            return childResponse;
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


                        if (jsonObj.has(Const.Params.Message)) {
                            msg = jsonObj.getString(TAG_message);
                        }

                    }
                }


                if (Status.equalsIgnoreCase("true")) {

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


                } else {

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

                    // failed to create product
                    Toast.makeText(getActivity(), " error" + "....",
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    // ////////
    // ////////
    // ////////

}
