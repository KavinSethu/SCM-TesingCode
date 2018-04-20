package com.elar.attandance.list;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/*import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONArray;
import org.json.JSONObject;

import com.elar.util.NetworkUtil;
import com.pnf.elar.app.R;
import com.pnf.elar.app.Drawer;
import com.pnf.elar.app.ImageLoadernew;

import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.ParentChildComponent;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AbsenceNotes extends Fragment {
    View v;

    Button AlsoRemovedropoff, delete, cancel;
    UserSessionManager session;
    String auth_key, securityKey, lang, Base_url, Security = "H67jdS7wwfh",
            cDate, studentId, user_typ, keyForDisplayType, datacome,
            wholedaysick_delete;
    TextView txtForUserName, txtForRetrievrName, txtForContactDetail,
            txtForDescrption, txtForDescrption2, txtForWrittenBy, txtForDesc,
            txtForGuardian1, txtForGuardian1ph, txtForGuardian2,
            txtForGuardian2ph, txtForGuardian, txtForHeading, txtForHeading1,
            txtForHeading3, txtForHeading4;
    ImageView imgForUserImage, imageForRetriver;
    ImageLoadernew imgLoader;

    private RelativeLayout layoutForRetrievalNotes, layoutForAbsenceNotes, layoutForGuardian2, layoutForGuardian1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        v = inflater.inflate(R.layout.absence_notes, container, false);

        layoutForRetrievalNotes = (RelativeLayout) v
                .findViewById(R.id.layoutForRetrievalNotes);
        layoutForAbsenceNotes = (RelativeLayout) v
                .findViewById(R.id.layoutForAbsenceNotes);
        layoutForGuardian2 = (RelativeLayout) v.findViewById(R.id.layoutForGuardian2);
        layoutForGuardian1 = (RelativeLayout) v.findViewById(R.id.layoutForGuardian1);
        imageForRetriver = (ImageView) v.findViewById(R.id.imageForRetriver);
        imgForUserImage = (ImageView) v.findViewById(R.id.imgForUserImage);
        AlsoRemovedropoff = (Button) v.findViewById(R.id.AlsoRemovedropoff);
        delete = (Button) v.findViewById(R.id.delete);
        cancel = (Button) v.findViewById(R.id.cancel);
        txtForHeading1 = (TextView) v.findViewById(R.id.txtForHeading1);
        txtForHeading3 = (TextView) v.findViewById(R.id.txtForHeading3);
        txtForHeading4 = (TextView) v.findViewById(R.id.txtForHeading4);
        txtForGuardian = (TextView) v.findViewById(R.id.txtForGuardian);
        txtForHeading = (TextView) v.findViewById(R.id.txtForHeading);
        imgLoader = new ImageLoadernew(getActivity());

        session = new UserSessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        auth_key = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        user_typ = user.get(UserSessionManager.TAG_user_type);

        if (lang.equalsIgnoreCase("sw")) {
            txtForHeading1.setText("Hämtare");
            txtForHeading3.setText("Kontaktuppgifter");
            txtForHeading4.setText("Beskrivning");
            cancel.setText("Avbryt");
            delete.setText("Ta bort");
            txtForHeading.setText("Beskrivning");
            AlsoRemovedropoff.setText("Ta också bort drop-off och hämtning tid");
        }

        // ////////back to Main Activity ////Set title/////

        // ///////////
        try {
            Bundle bundle = this.getArguments();
            studentId = bundle.getString("keyForStudentId");
            cDate = getCurrentDate();
            String name = bundle.getString("keyForStudentName");
            keyForDisplayType = bundle.getString("keyForDisplayType");
            datacome = bundle.getString("absentnoteicon");

            if (datacome.equalsIgnoreCase("absentnoteicon")) {
                delete.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
            }else{
                delete.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
            }

            if (keyForDisplayType.equals("a")) { // a for absent
                layoutForRetrievalNotes.setVisibility(View.GONE);
                layoutForAbsenceNotes.setVisibility(View.VISIBLE);

                if (lang.equalsIgnoreCase("sw")) {
                    ((Drawer) getActivity()).setActionBarTitle("Frånvaroanmälan");
                } else {
                    ((Drawer) getActivity()).setActionBarTitle("Absence notes");
                }
            }
            if (keyForDisplayType.equals("r")) { // r for retrieval
                layoutForRetrievalNotes.setVisibility(View.VISIBLE);
                layoutForAbsenceNotes.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);

                if (lang.equalsIgnoreCase("sw")) {
                    ((Drawer) getActivity())
                            .setActionBarTitle("Annan hämtare");
                } else {
                    ((Drawer) getActivity())
                            .setActionBarTitle("Retriever notes");
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

//                    FragmentManager fragmentManager = getFragmentManager();
//                    AttandanceMainScreen rFragment = new AttandanceMainScreen();
//                    FragmentTransaction ft = fragmentManager.beginTransaction();
//                    ft.replace(R.id.content_frame, rFragment);
//                    ft.commit();

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                        Intent i = new Intent(getActivity(), Drawer.class);
                        startActivity(i);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

        delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                        try {
                            if (datacome.equalsIgnoreCase("absentnoteicon")) {
                                new deleteabsence().execute();
                            }
                            if (datacome.equalsIgnoreCase("retrievericon")) {

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        if (user_typ.equalsIgnoreCase("parent")) {
            AlsoRemovedropoff.setVisibility(View.GONE);
            studentId = user.get(UserSessionManager.TAG_child_id);
            ((Drawer) getActivity()).setBackForChildEduedu();
            Bundle bundleone = this.getArguments();
            datacome = bundleone.getString("absentnoteicon");
        } else {
            AlsoRemovedropoff.setVisibility(View.GONE);
            ((Drawer) getActivity()).setBackFrompublishtomainAttendance();
        }

        txtForRetrievrName = (TextView) v.findViewById(R.id.txtForRetrievrName);
        txtForContactDetail = (TextView) v
                .findViewById(R.id.txtForContactDetail);
        txtForDescrption = (TextView) v.findViewById(R.id.txtForDescrption);
        txtForDescrption2 = (TextView) v.findViewById(R.id.txtForDescrption2);

        txtForWrittenBy = (TextView) v.findViewById(R.id.txtForWrittenBy);
        txtForUserName = (TextView) v.findViewById(R.id.txtForUserName);
        txtForDesc = (TextView) v.findViewById(R.id.txtForDesc);
        txtForGuardian1 = (TextView) v.findViewById(R.id.txtForGuardian1);
        txtForGuardian1ph = (TextView) v.findViewById(R.id.txtForGuardian1ph);
        txtForGuardian2 = (TextView) v.findViewById(R.id.txtForGuardian2);
        txtForGuardian2ph = (TextView) v.findViewById(R.id.txtForGuardian2ph);

        txtForGuardian1ph.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub3
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                        + "123"));
                startActivity(intent);
            }
        });

        if (lang.equalsIgnoreCase("sw")) {
            txtForGuardian.setText("Vårdnadshavare");
            //	txtForHeading.setText("Frånvaroanmälan");
        }
        try {
            if (keyForDisplayType.equals("a")) {
                new GetData().execute();
            }
            if (keyForDisplayType.equals("r")) {
                new GetRetrievalNotes().execute();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        try {
            if (datacome.equalsIgnoreCase("absentnoteicon")) {
                layoutForRetrievalNotes.setVisibility(View.GONE);
                layoutForAbsenceNotes.setVisibility(View.VISIBLE);
                new GetData().execute();

                if (lang.equalsIgnoreCase("sw")) {
                    ((Drawer) getActivity()).setActionBarTitle("Frånvaroanmälan");
                } else {
                    ((Drawer) getActivity()).setActionBarTitle("Absence notes");
                }
            }
            if (datacome.equalsIgnoreCase("retrievericon")) {
                layoutForRetrievalNotes.setVisibility(View.VISIBLE);
                layoutForAbsenceNotes.setVisibility(View.GONE);
                new GetRetrievalNotes().execute();

                if (lang.equalsIgnoreCase("sw")) {
                    ((Drawer) getActivity())
                            .setActionBarTitle("Annan hämtare");
                } else {
                    ((Drawer) getActivity())
                            .setActionBarTitle("Retriever notes");
                }

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return v;
    }

    private class GetData extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/retrivals/get_absence_note",
                Status="", message, note, written_by, student_name, image,
                writtenbyparent;

        private MyCustomProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new MyCustomProgressDialog(getActivity());
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        protected String doInBackground(String... args) {

            String getAbsResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_key, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.SickDate + "=" +URLEncoder.encode(cDate, "UTF-8") +
                        "&" + Const.Params.StudentId + "=" +URLEncoder.encode(studentId, "UTF-8");


                getAbsResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }


			/*JSONParser jsonParser = new JSONParser();

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("securityKey", Security));
			params.add(new BasicNameValuePair("authentication_token", auth_key));
			params.add(new BasicNameValuePair("language", lang));
			params.add(new BasicNameValuePair("sickdate", "" + cDate));
			params.add(new BasicNameValuePair("student_id", studentId));

			Log.e("Create Response -=- ", params.toString());

			JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);*/


           /* JSONObject jsonObj = new JSONObject(json.toString());


            Status = jsonObj.getString("status").toString();

            if (Status.equalsIgnoreCase("true")) {
                JSONObject data = absNOteJson.getJSONObject("data");
                note = URLDecoder.decode(data.getString("description")).toString();
                // note = data.getString("description");
                if (data.has("written_by")) {

                    written_by = data.getString("written_by");

                } else {
                    written_by = "";
                }
                student_name = data.getString("student_name");
                image = data.getString("image");

            } else {
                message = absNOteJson.getString(Const.Params.Message).toString();

            }
*/

            return getAbsResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();

            if (results != null && !results.isEmpty()) {
                try {
                    JSONObject absNOteJson = new JSONObject(results);

                    if (absNOteJson.has("status")) {
                        Status = absNOteJson.getString("status").toString();


                   } else {
                            message = "Service Failed";

                    }

                    if (Status.equalsIgnoreCase("true")) {
                        JSONObject data = absNOteJson.getJSONObject("data");
                        note = URLDecoder.decode(data.getString("description")).toString();
                        // note = data.getString("description");
                        if (data.has("written_by")) {

                            written_by = data.getString("written_by");

                        } else {
                            written_by = "";
                        }
                        student_name = data.getString("student_name");
                        image = data.getString("image");
                        writtenbyparent = data.getString("written_by_parent");
                        wholedaysick_delete = data.getString("wholedaysick_id");
                        layoutForGuardian2.setVisibility(View.GONE);
                        layoutForGuardian1.setVisibility(View.GONE);
                        if (lang.equalsIgnoreCase("parent")) {
                            if (writtenbyparent.equalsIgnoreCase("0")) {
                                delete.setVisibility(View.GONE);
                            }
                        }


                        if (note != null) {
                            txtForDesc.setText("" + note);

                        } else {
                            txtForDesc.setText("");

                        }
/*
                    txtForDesc.setText("" + note);
*/
                        if (lang.equalsIgnoreCase("sw")) {
                            txtForWrittenBy.setText("Skriven utav: " + written_by);
                        } else {
                            txtForWrittenBy.setText("Written by: " + written_by);
                        }

                        txtForUserName.setText("" + student_name);
                        new ImageLoadTaskclip(Base_url + image, imgForUserImage)
                                .execute();

                        // imgLoader.DisplayImage(Base_url + image,
                        // imgForUserImage);
                    } else if(Status.equalsIgnoreCase("false")){

                        message = absNOteJson.getString(Const.Params.Message).toString();
                        /*Toast.makeText(getActivity(), "  " + message,
                                Toast.LENGTH_LONG).show();*/

                        try {


                            String msg = absNOteJson.getString("message");
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
                    else
                    {
                        Toast.makeText(getActivity(), "  " + message,
                                Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(getActivity(), "Service Failed  ",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private class GetRetrievalNotes extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/retrivals/get_retriver_note",
                Status="", message, note, written_by, student_name;

        private MyCustomProgressDialog dialog;

        String sName, rName, rContact, rDesc, rImage, rWrittenBy, sImage;
        private ArrayList<String> listForGuardian = new ArrayList<String>();
        private ArrayList<String> listForGuardianph = new ArrayList<String>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new MyCustomProgressDialog(getActivity());
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        protected String doInBackground(String... args) {


            String retriveResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_key, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8")  +
                        "&" + Const.Params.DATE + "=" +URLEncoder.encode(cDate, "UTF-8") +
                        "&" + Const.Params.StudentId + "=" +URLEncoder.encode(studentId, "UTF-8");


                retriveResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);




          /*  JSONParser jsonParser = new JSONParser();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("securityKey", Security));
            params.add(new BasicNameValuePair("authentication_token", auth_key));
            params.add(new BasicNameValuePair("language", lang));
            params.add(new BasicNameValuePair("date", "" + cDate));
            params.add(new BasicNameValuePair("student_id", studentId));

            Log.e("Create Response -=-=-=-=-=- ", params.toString());

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

            try {

                JSONObject jsonObj = new JSONObject(json.toString());

                Status = jsonObj.getString("status").toString();

                if (Status.equalsIgnoreCase("true")) {

                    JSONObject data = jsonObj.getJSONObject("data");
                    JSONObject Retriever = data.getJSONObject("Retriever");
                    JSONObject student = data.getJSONObject("student");

                    rName = URLDecoder.decode(data.getString("retriever_name")).toString();
                    // rName = Retriever.getString("retriever_name");
                    rContact = Retriever.getString("contact_number");
                    rDesc = URLDecoder.decode(data.getString("description")).toString();
                    //	rDesc = Retriever.getString("description");
                    rImage = Retriever.getString("retriever_image");
                    rWrittenBy = Retriever.getString("written_by");

                    // note = data.getString("description");
                    // written_by = data.getString("written_by");
                    // student_name = data.getString("student_name");

                    sName = student.getString("student_name");
                    sImage = student.getString("image");

                    JSONArray parents = data.getJSONArray("parents");
                    for (int i = 0; i < parents.length(); i++) {
                        JSONObject parentsData = parents.getJSONObject(i);
                        listForGuardian.add(parentsData
                                .getString("USR_FirstName")
                                + " "
                                + parentsData.getString("USR_LastName"));
                        listForGuardianph.add(parentsData
                                .getString("contact_number"));
                    }

                } else {

                    message = jsonObj.getString(Const.Params.Message).toString();
                }
                */

            } catch (Exception e) {
                e.printStackTrace();
            }

            return retriveResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            JSONObject jsonObj = new JSONObject();


            try {
                if (results != null && !results.isEmpty()) {

                    jsonObj = new JSONObject(results);
                    if (jsonObj.has("status")) {

                        Status = jsonObj.getString("status").toString();


                    } else {



                        message = "Service Failed";
                    }


                } else {
                }


                if (Status.equalsIgnoreCase("true")) {

                    JSONObject data = jsonObj.getJSONObject("data");
                    JSONObject Retriever = data.getJSONObject("Retriever");
                    JSONObject student = data.getJSONObject("student");

                    rName = URLDecoder.decode(Retriever.getString("retriever_name")).toString();
                    // rName = Retriever.getString("retriever_name");
                    rContact = Retriever.getString("contact_number");
                    rDesc = URLDecoder.decode(Retriever.getString("description")).toString();
                    //	rDesc = Retriever.getString("description");
                    rImage = Retriever.getString("retriever_image");
                    rWrittenBy = Retriever.getString("written_by");

                    // note = data.getString("description");
                    // written_by = data.getString("written_by");
                    // student_name = data.getString("student_name");

                    sName = student.getString("student_name");
                    sImage = student.getString("image");

                    JSONArray parents = data.getJSONArray("parents");
                    for (int i = 0; i < parents.length(); i++) {
                        JSONObject parentsData = parents.getJSONObject(i);
                        listForGuardian.add(parentsData
                                .getString("USR_FirstName")
                                + " "
                                + parentsData.getString("USR_LastName"));
                        listForGuardianph.add(parentsData
                                .getString("contact_number"));
                    }
                    if (listForGuardian.size() == 1) {

                    } else {
                        layoutForGuardian1.setVisibility(View.GONE);
                    }
                    txtForRetrievrName.setText("" + rName);
                    txtForContactDetail.setText("" + rContact);
                    if (lang.equalsIgnoreCase("sw")) {
                        txtForDescrption.setText("Skriven utav: " + rWrittenBy);
                    } else {
                        txtForDescrption.setText("Written By: " + rWrittenBy);
                    }

                    txtForDescrption2.setText("" + rDesc);
                    txtForUserName.setText("" + sName);

                    if (listForGuardian.size() == 1) {
                        txtForGuardian1.setText("" + listForGuardian.get(0));

                    }
                    if (listForGuardianph.size() == 1) {
                        txtForGuardian1ph
                                .setText("" + listForGuardianph.get(0));

                    }

                    if (listForGuardian.size() >= 2) {
                        txtForGuardian1.setText("" + listForGuardian.get(0));
                        txtForGuardian2.setText("" + listForGuardian.get(1));
                    }
                    if (listForGuardianph.size() >= 2) {
                        txtForGuardian1ph
                                .setText("" + listForGuardianph.get(0));
                        txtForGuardian2ph
                                .setText("" + listForGuardianph.get(1));
                    } else {

                        layoutForGuardian2.setVisibility(View.GONE);
                    }

                    new ImageLoadTaskclip(Base_url + rImage, imgForUserImage)
                            .execute();
                    new ImageLoadTaskclip(Base_url + sImage, imageForRetriver)
                            .execute();

                } else if(Status.equalsIgnoreCase("false")) {


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


                    if(jsonObj.has(Const.Params.Message)) {
                        message = jsonObj.getString(Const.Params.Message).toString();
                    }

                    Toast.makeText(getActivity(), "  " + message,
                            Toast.LENGTH_LONG).show();

                    String msg=jsonObj.getString("message");
                    System.out.print(msg);



                }
                else
                {
                    Toast.makeText(getActivity(), "  " + message,
                            Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public String getCurrentDate() {
        String currentDate = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // get current date time with Date()
        Date date = new Date();
        System.out.println(dateFormat.format(date));

        // get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));
        currentDate = dateFormat.format(cal.getTime());
        // Log.e("current date", currentDate);
        return currentDate;
    }

    // //////
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
            // pDialog = new ProgressDialog(_context);
            // pDialog.setMessage("load image ....");
            // pDialog.setIndeterminate(false);
            // pDialog.setCancelable(false);
            // pDialog.show();
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

    // ////
    private class deleteabsence extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/retrivals/delete_absent_desc",
                Status, message, note, written_by, student_name, image,
                writtenbyparent, msgg;

        private MyCustomProgressDialog dialog;

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
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_key, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8")  +
                        "&" + Const.Params.WholeDaysSickId + "=" +URLEncoder.encode(wholedaysick_delete, "UTF-8");


                deleteResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }
           /* JSONParser jsonParser = new JSONParser();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("securityKey", Security));
            params.add(new BasicNameValuePair("authentication_token", auth_key));
            params.add(new BasicNameValuePair("language", lang));
            params.add(new BasicNameValuePair("wholedaysick_id",
                    wholedaysick_delete));


            Log.e("Create  -=-=-=-=-=- ", urlParams.toString());


            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

            try {

                JSONObject jsonObj = new JSONObject(json.toString());

                Status = jsonObj.getString("status").toString();

                if (Status.equalsIgnoreCase("true")) {

                    msgg = jsonObj.getString(Const.Params.Message);
                } else {

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
*/
                return deleteResponse;
            }


        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            JSONObject deleteResponse = new JSONObject();

            try
            {

                if(results!=null && !results.isEmpty())
                {
                    deleteResponse = new JSONObject(results);


                    if(deleteResponse.has(Const.Params.Status))
                    {
                        Status = deleteResponse.getString(Const.Params.Status);
                        if(deleteResponse.has(Const.Params.Message)) {
                            message = deleteResponse.getString(Const.Params.Message);

                        }


                    }

                }
                else
                {

                    message="Service Failed";
                }



               /* Status = jsonObj.getString("status").toString();

                if (Status.equalsIgnoreCase("true")) {

                } else {

                }*/


            if (Status.equalsIgnoreCase("true")) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT)
                        .show();
//                FragmentManager fragmentManager = getFragmentManager();
//                AttandanceMainScreen rFragment = new AttandanceMainScreen();
//                FragmentTransaction ft = fragmentManager.beginTransaction();
//                ft.replace(R.id.content_frame, rFragment);
//                ft.commit();
                Intent i = new Intent(getActivity(),Drawer.class);
                startActivity(i);

            } else {


                try {


                    String msg = deleteResponse.getString("message");
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
                }catch (NullPointerException e)
                {
                    e.printStackTrace();
                }

                Toast.makeText(getActivity(), "  " + message,
                        Toast.LENGTH_LONG).show();
            }
        }

        catch(Exception e)

        {
            e.printStackTrace();
        }
    }
}
// /////
}