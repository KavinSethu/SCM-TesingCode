package com.elar.attandance.list;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/*import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONObject;

import com.elar.util.NetworkUtil;
import com.elar.util.SmartClassUtil;
import com.pnf.elar.app.R;
import com.pnf.elar.app.Drawer;

import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.ParentChildComponent;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;
import com.pnf.elar.app.util.ImageLoadingUtils;
import com.soundcloud.android.crop.Crop;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

public class AddretrivalNotes extends Fragment {
    String studentId, regId, user_typ, name, child_id, child_name, child_img, imagePath, i_path,
            encodedString = "";
    View v;
    private static int LOAD_IMAGE_RESULTS = 1;
    public static final int CHOOSE_PHOTO = 112;

    RelativeLayout retrival_note;
    // Response
    String responseServer;
    ImageView profileImage;
    private TextView txtForUserName, txtForDateFrom, txtForToDate, Date, To,
            txt1, Portrait, txt2, txt3;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar2;
    DatePickerDialog.OnDateSetListener date2;
    private Button btnForCancel, btnForSave;

    UserSessionManager session;
    String auth_key, securityKey, lang, Base_url, Security = "H67jdS7wwfh";
    String contactNumber, fromDate, toDate, imageUrl, note, retrieverName;
    EditText edtForRname, edtForContact, edtForNotes;

    ImageLoadingUtils utils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.add_retrival_notes_fragment, container,
                false);

        session = new UserSessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        auth_key = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        regId = user.get(UserSessionManager.TAG_regId);
        user_typ = user.get(UserSessionManager.TAG_user_type);
        child_id = user.get(UserSessionManager.TAG_child_id);
        child_name = user.get(UserSessionManager.TAG_cld_nm);
        child_img = user.get(UserSessionManager.TAG_child_image);
//////////back to Main Activity ////Set title/////

        if (lang.equalsIgnoreCase("sw")) {
            ((Drawer) getActivity()).setActionBarTitle("Annan hämtare");
        } else {
            ((Drawer) getActivity()).setActionBarTitle("Edit Retrival Note");
        }
        /////////////

        retrival_note = (RelativeLayout) v.findViewById(R.id.retrival_note);

        try {
            Bundle bundle = this.getArguments();
            studentId = bundle.getString("keyForStudentId");

            name = bundle.getString("keyForStudentName");
        } catch (Exception e) {
            // TODO: handle exception
            studentId = "";
        }
        profileImage = (ImageView) v.findViewById(R.id.profileImage);
        txtForUserName = (TextView) v.findViewById(R.id.txtForUserName);
        Date = (TextView) v.findViewById(R.id.Date);
        To = (TextView) v.findViewById(R.id.To);
        txt1 = (TextView) v.findViewById(R.id.txt1);
        Portrait = (TextView) v.findViewById(R.id.Portrait);
        txt2 = (TextView) v.findViewById(R.id.txt2);
        txt3 = (TextView) v.findViewById(R.id.txt3);
        btnForCancel = (Button) v.findViewById(R.id.btnForCancel);
        btnForSave = (Button) v.findViewById(R.id.btnForSave);
        edtForNotes = (EditText) v.findViewById(R.id.editText1);
        txtForDateFrom = (TextView) v.findViewById(R.id.txtForDateFrom);
        txtForToDate = (TextView) v.findViewById(R.id.txtForToDate);

        if (user_typ.equalsIgnoreCase("Parent")) {

            Log.i("child_img", child_img);

            //    new ImageLoadTaskcliptwo(child_img,profileImage).execute();

            retrival_note.setBackgroundColor(Color.parseColor("#EC74A9"));
//			auth_key = user.get(UserSessionManager.TAG_Authntication_Children);
            studentId = child_id;
            txtForUserName.setText("" + child_name);
            btnForCancel.setBackgroundColor(Color.parseColor("#F29EC3"));
            btnForSave.setBackgroundColor(Color.parseColor("#F29EC3"));
            ((Drawer) getActivity()).setBackForChildEduedu();
            ((Drawer) getActivity()).Hideserch();
            ((Drawer) getActivity()).HideRefresh();


        } else {
//			auth_key = user.get(UserSessionManager.TAG_Authntication_token);
            txtForUserName.setText("" + name);
            ((Drawer) getActivity()).setBackFrompublishtomainAttendance();
        }

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEdt();
            }

        };


////////////choose image (user profile)		
        profileImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, CHOOSE_PHOTO);


            }
        });

//////////////////////	
        if (lang.equalsIgnoreCase("sw")) {
            Date.setText("Datum:");
            To.setText("Till:");
            txt1.setText("Hämtarsnamn:");
            Portrait.setText("Porträtt: ");
            txt2.setText("Kontaktuppgifter:");
            txt3.setText("Beskrivning:");
            btnForCancel.setText("Avbryt");
            btnForSave.setText("Spara");
            edtForNotes.setHint("Skriv här..");
            txtForDateFrom.setText("ÅÅÅÅ-MM-DD");
            txtForToDate.setText("ÅÅÅÅ-MM-DD");
        } else {

        }
//////////////////
        myCalendar2 = Calendar.getInstance();
        date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEdt2();
            }

        };

        txtForDateFrom.setText("" + getCurrentDate());

        txtForDateFrom.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Context themedContext1 = new ContextThemeWrapper(
                        getActivity(),
                        android.R.style.Theme_Holo
                );
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        DatePickerDialog dpd=  new DatePickerDialog(themedContext1, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH));
                        if (lang.equalsIgnoreCase("sw")) {
                            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Klar", dpd);
                            dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Avbryt", dpd);
                        }else{
                            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ok", dpd);
                            dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancel", dpd);
                        }
                        dpd.show();

                    }else{

                        new DatePickerDialog(getActivity(), date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        txtForToDate = (TextView) v.findViewById(R.id.txtForToDate);
        txtForToDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Context themedContext1 = new ContextThemeWrapper(
                        getActivity(),
                        android.R.style.Theme_Holo
                );
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        DatePickerDialog dpd= new DatePickerDialog(themedContext1, date2, myCalendar2
                                .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                                myCalendar2.get(Calendar.DAY_OF_MONTH));
                        if (lang.equalsIgnoreCase("sw")) {
                            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Klar", dpd);
                            dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Avbryt", dpd);
                        }else{
                            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ok", dpd);
                            dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancel", dpd);
                        }
                        dpd.show();

                    }else{

                        new DatePickerDialog(getActivity(), date2, myCalendar2
                                .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                                myCalendar2.get(Calendar.DAY_OF_MONTH)).show();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btnForCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                        if (user_typ.equalsIgnoreCase("parent")) {
                            FragmentManager fragmentManager = getFragmentManager();
                            ParentChildComponent Setting_frg = new
                                    ParentChildComponent();
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            ft.replace(R.id.content_frame, Setting_frg);
                            ft.commit();
                        } else {
                            FragmentManager fragmentManager = getFragmentManager();

                            UserProfileFragment rFragment = new UserProfileFragment();
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            Bundle bundle = new Bundle();
                            bundle.putString("keyForStudentId", studentId);
                            rFragment.setArguments(bundle);
                            ft.replace(R.id.content_frame, rFragment);
                            ft.commit();
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

        edtForContact = (EditText) v.findViewById(R.id.edtForContact);
        edtForRname = (EditText) v.findViewById(R.id.edtForRname);

        btnForSave.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                        fromDate = txtForDateFrom.getText().toString().trim();
                        toDate = txtForToDate.getText().toString().trim();

                        if (fromDate.equalsIgnoreCase("YYYY-MM-DD")) {
                            fromDate = "";
                        }
                        if (toDate.equalsIgnoreCase("YYYY-MM-DD")) {
                            toDate = "";
                        }
                        if (fromDate.equalsIgnoreCase("ÅÅÅÅ-MM-DD")) {
                            fromDate = "";
                        }
                        if (toDate.equalsIgnoreCase("ÅÅÅÅ-MM-DD")) {
                            toDate = "";
                        }


                        contactNumber = edtForContact.getText().toString().trim();
                        //Change by kavin
    //                note = edtForNotes.getText().toString().trim();
                        note = edtForNotes.getText().toString();

    //                retrieverName = edtForRname.getText().toString().trim();
                        retrieverName = edtForRname.getText().toString();


                        if (edtForContact.getText().toString().equals("")
                                || edtForContact.getText().toString().equals(null)) {
                            edtForContact.setError("Required Field");
                        }

                        if (edtForRname.getText().toString().equals("")
                                || edtForRname.getText().toString().equals(null)) {
                            edtForRname.setError("Required Field");
                        }

                        if (edtForNotes.getText().toString().equals("")
                                || edtForNotes.getText().toString().equals(null)) {
                            edtForNotes.setError("Required Field");
                        }

                        if (retrieverName.equals("") || retrieverName.equals(null)
                                || note.equals("") || note.equals(null)
                                || contactNumber.equals("")
                                || contactNumber.equals(null)) {
    //					Toast.makeText(getActivity(),
    //							"Please fill all required fields",
    //							Toast.LENGTH_LONG).show();

                        } else {
                            try {
                                AsyncT asyncT = new AsyncT();
                                asyncT.execute();
                            } catch (Exception e) {
                                // TODO: handle exception
    //						Toast.makeText(getActivity(), "--- " + e,
    //								Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

        new GetRetrieverData().execute();

        return v;
    }

    public void updateEdt() {

        String myFormat = "yyyy-MM-dd"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txtForDateFrom.setText(sdf.format(myCalendar.getTime()));
    }

    public void updateEdt2() {

        String myFormat = "yyyy-MM-dd"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txtForToDate.setText(sdf.format(myCalendar2.getTime()));
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

    /* Inner class to get response */
    class AsyncT extends AsyncTask<Void, Void, Void> {
        String status="", message="";
        private MyCustomProgressDialog dialog;
        String url = Base_url
                + "lms_api/retrivals/update_retriver_note";
        String updateResponse = "";


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            dialog = new MyCustomProgressDialog(getActivity());
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @SuppressWarnings({"deprecation", "deprecation"})
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.i("enString", auth_key);
                Log.i("enString", encodedString);
                Log.i("contactNumber", contactNumber);
                Log.i("fromDate", fromDate);
                Log.i("encodedString", encodedString);
                Log.i("retrieverName", retrieverName);
                Log.i("studentId", studentId);

            } catch (Exception e) {
                // TODO: handle exception
            }

           /* HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Base_url
                    + "lms_api/retrivals/update_retriver_note");*/

            try {

                JSONObject jsonobj = new JSONObject();
                jsonobj.put("authentication_token", auth_key);
                jsonobj.put("contact_number", contactNumber);
                jsonobj.put("from_date", fromDate);
                jsonobj.put("image", encodedString);
                jsonobj.put("language", lang);

                //jsonobj.put("note", URLEncoder.encode(note).toString());
//                jsonobj.put("retriver_name", URLEncoder.encode(retrieverName).toString());
                jsonobj.put("retriver_name", retrieverName);

                //Changes done by kavi
//                jsonobj.put("note",java.net.URLEncoder.encode(note, "UTF-8").replace("+", " "));
                jsonobj.put("note",note);


                jsonobj.put("securityKey", Security);
                jsonobj.put("student_id", studentId);
                jsonobj.put("to_date", toDate);

                Log.i("jsonobj", jsonobj.toString());


                try {
                  /*  String urlParams =
                            "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_key, "UTF-8") +
                                    "&" + Const.Params.ContactNumber + "=" + URLEncoder.encode(contactNumber, "UTF-8") +
                                    "&" + Const.Params.FromDate + "=" + URLEncoder.encode(fromDate, "UTF-8") +
                                    "&" + Const.Params.IMAGE + "=" + URLEncoder.encode(encodedString, "UTF-8") +
                                    "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                                    "&" + Const.Params.Note + "=" + URLEncoder.encode(note, "UTF-8") +
                                    "&" + Const.Params.RetriverName + "=" + URLEncoder.encode(retrieverName, "UTF-8") +
                                    "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                                    "&" + Const.Params.StudentId + "=" + URLEncoder.encode(studentId, "UTF-8") +
                                    "&" + Const.Params.ToDate + "=" + URLEncoder.encode(toDate, "UTF-8");*/
                    String urlParams =
                            "&" + Const.Params.JsonData + "=" + URLEncoder.encode(jsonobj.toString(), "UTF-8");


                    updateResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
                } catch (Exception e) {
                    e.printStackTrace();
                }


              /*  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("jsonData", jsonobj
                        .toString()));

                Log.e("mainToPost", "mainToPost" + nameValuePairs.toString());

                // Use UrlEncodedFormEntity to send in proper format which we
                // need
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

                Log.e("-=-=-=-=-=-=-=  ", "" + response);

                InputStream inputStream = response.getEntity().getContent();
                InputStreamToStringExample str = new InputStreamToStringExample();
                responseServer = str.getStringFromInputStream(inputStream);*/

                /*if (responseServer.equals("") || responseServer.equals(null)) {
                    status = "false";
                    message = "updated successfully";
                    Log.e("response", "response -----" + responseServer);
                } else {
                    JSONObject json = new JSONObject(responseServer);
                    status = json.getString("status");
                    message = json.getString(Const.Params.Message);
                    Log.e("response", "response -----" + responseServer);
                }
*/
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.e("responseServer", "" + responseServer);
            dialog.dismiss();
            try {
                if (updateResponse.equals("") || updateResponse.equals(null)) {
                    status = "false";
                    message = "updated successfully";
                    Log.e("response", "response -----" + updateResponse);
                } else {
                    JSONObject json = new JSONObject(updateResponse);
                    if (json.has("status")) {
                        status = json.getString(Const.Params.Status);

                        if(json.has(Const.Params.Message)) {
                            message = json.getString(Const.Params.Message);
                        }
                        Log.e("response", "response -----" + updateResponse);
                    } else {
                    }

                }


                if (status.equals("true")) {
                    if (user_typ.equalsIgnoreCase("parent")) {
                        String magg, ok;
                        if (lang.equalsIgnoreCase("sw")) {
                            magg = "Hämtaranmälan har skickats in";
                            ok = "OK";
                        } else {
                            magg = "Retriever note has been added";
                            ok = "OK";
                        }
                        Toast.makeText(getActivity(), magg, Toast.LENGTH_SHORT).show();

                        FragmentManager fragmentManager = getFragmentManager();
                        ParentChildComponent rFragment = new ParentChildComponent();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, rFragment);
                        ft.commit();

//					AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
//			        alertDialog.setTitle("");
//			        alertDialog.setMessage("Retrival Note added successfully...");
//			        alertDialog.setIcon(R.drawable.tick);
//			        alertDialog.setButton(ok, new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) 
//					{
//						FragmentManager fragmentManager = getFragmentManager();
//						ParentChildComponent rFragment = new ParentChildComponent();
//						FragmentTransaction ft = fragmentManager.beginTransaction();
//						ft.replace(R.id.content_frame, rFragment);
//						ft.commit();
//					}
//			});
//
//			// Showing Alert Message
//			alertDialog.show();

                    }
                    else
                    {
                        FragmentManager fragmentManager = getFragmentManager();

                        UserProfileFragment rFragment = new UserProfileFragment();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("keyForStudentId", studentId);
                        rFragment.setArguments(bundle);
                        ft.replace(R.id.content_frame, rFragment);
                        ft.commit();
                    }

                } else {





                    SmartClassUtil.showToast(getActivity(),message);


                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static class InputStreamToStringExample {

        public static void main(String[] args) throws IOException {

            // intilize an InputStream
            InputStream is = new ByteArrayInputStream(
                    "file content..blah blah".getBytes());

            String result = getStringFromInputStream(is);

            System.out.println(result);
            System.out.println("Done");

        }

        // convert InputStream to String
        private static String getStringFromInputStream(InputStream is) {

            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();

            String line;
            try {

                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return sb.toString();
        }

    }

    class GetRetrieverData extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/retrivals/get_retriver_note",
                Status="";
        String rName, rContact, note, rImage, created;

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

           /* JSONParser jsonParser = new JSONParser();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("securityKey", Security));
            params.add(new BasicNameValuePair("authentication_token", auth_key));
            params.add(new BasicNameValuePair("user_type_app", "android"));
            params.add(new BasicNameValuePair("language", lang));
            params.add(new BasicNameValuePair("date", "" + getCurrentDate()));
            params.add(new BasicNameValuePair("student_id", studentId));
            params.add(new BasicNameValuePair("device_token_app", regId));

            Log.e("Create Response =- ", params.toString());

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);*/


            String retNoteResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_key, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.UserTypeApp + "=" + URLEncoder.encode("android", "UTF-8") +
                        "&" + Const.Params.DATE + "=" + URLEncoder.encode(getCurrentDate(), "UTF-8") +
                        "&" + Const.Params.StudentId + "=" + URLEncoder.encode(studentId, "UTF-8") +
                        "&" + Const.Params.DeviceTokenApp + "=" + URLEncoder.encode(regId, "UTF-8");


                retNoteResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }


           /* try {

                JSONObject jsonObj = new JSONObject(json.toString());

                Status = jsonObj.getString("status").toString();
                if (Status.equalsIgnoreCase("true")) {

                    JSONObject data = jsonObj.getJSONObject("data");

                    JSONObject Retriever = data.getJSONObject("Retriever");

                    rContact = Retriever.getString("contact_number");
                    rName = URLDecoder.decode(data.getString("retriever_name")).toString();
                    //	rName = Retriever.getString("retriever_name");
                    note = URLDecoder.decode(data.getString("description")).toString();
                    //	note = Retriever.getString("description");
                    rImage = Base_url + Retriever.getString("retriever_image");
                    created = Retriever.getString("created");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            return retNoteResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {

                String messgage="";
                if (!results.isEmpty() && results != null) {
                    JSONObject jsonObj = new JSONObject(results);


                    if (jsonObj.has("status")) {


                        Status = jsonObj.getString("status").toString();
                        if (Status.equalsIgnoreCase("true")) {

                            JSONObject data = jsonObj.getJSONObject("data");
/*
                            if(jsonObj.has(Const.Params.Message))
                            {
                                messgage=jsonObj.getString(Const.Params.Message);
                            }*/
                            JSONObject Retriever = data.getJSONObject("Retriever");

                            rContact = Retriever.getString("contact_number");
                            rName= Retriever.getString("retriever_name");
/*
                            rName = URLDecoder.decode(data.getString("retriever_name")).toString();
*/
                            //	rName = Retriever.getString("retriever_name");
                            /*note = URLDecoder.decode(data.getString("description")).toString();*/
                            note= Retriever.getString("description");
                            //	note = Retriever.getString("description");
                            rImage = Base_url + Retriever.getString("retriever_image");
                            created = Retriever.getString("created");

                            new ImageLoadTaskcliptwo(rImage, profileImage).execute();
//					}
                            txtForToDate.setText(created);
                            edtForContact.setText("" + rContact);
                            edtForNotes.setText("" + note);
                            edtForRname.setText("" + rName);
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

/*
                            Toast.makeText(getActivity(), " Invalid ", Toast.LENGTH_LONG).show();
*/
                            if(jsonObj.has(Const.Params.Message))
                            {
                                messgage=jsonObj.getString(Const.Params.Message);
                            }
                        }

                    }
                    else
                    {
                    }
                }


                if (Status.equalsIgnoreCase("true")) {

//					if(user_typ.equalsIgnoreCase("parent"))
//					{
//						
//					}
//					else {

                } else {

/*
                    Toast.makeText(getActivity(), messgage, Toast.LENGTH_LONG).show();
*/
                }


            } catch (Exception e) {
            }
        }

    }

    ////////////////// image loader ///////////////
    class ImageLoadTaskcliptwo extends AsyncTask<Void, Void, Bitmap> {
        // ProgressDialog pDialog;
        private String url;
        private ImageView imageView;

        public ImageLoadTaskcliptwo(String url, ImageView imageView) {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("cor " + requestCode + " " + resultCode);

        if (requestCode == CHOOSE_PHOTO) {
            if (data != null) {
                // Let's read picked image data - its URI
                Uri pickedImage = data.getData();
                beginCrop(pickedImage);

			/*	// Let's read picked image path using content resolver
                String[] filePath = { MediaStore.Images.Media.DATA };
				Cursor cursor = getActivity().getContentResolver().query(
						pickedImage, filePath, null, null, null);

				cursor.moveToFirst();
				imagePath = cursor
						.getString(cursor.getColumnIndex(filePath[0]));
				i_path = imagePath;



				cursor.close();    

				// //////////////////////////////////////////

				InputStream inputStream = null;
				try {
					inputStream = new FileInputStream(imagePath);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}// You can get an inputStream using any IO API
			//	Bitmap tt = BitmapFactory.decodeFile(imagePath);   ////// new 
				Bitmap tt = BitmapHelper.decodeFile(imagePath, 50, 50, true);





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
//				bytes = output.toByteArray();
				tt.compress(CompressFormat.JPEG, 50,output);    ///// new
				bytes = output.toByteArray();
				
				
				try {
					 encodedString = Base64.encodeToString(bytes,
							Base64.DEFAULT);
				} catch (Exception e) {
					// TODO: handle exception
				}
				profileImage.setImageBitmap(tt);
*/


            }

        } else if (requestCode == Crop.REQUEST_CROP) {
            System.out.println("Crop photo on activity result");
            handleCrop(resultCode, data);


        }

    }

    private void beginCrop(Uri source) {
        // Uri outputUri = Uri.fromFile(new File(registerActivity.getCacheDir(),
        // "cropped"));
        Uri outputUri = Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), "/" + 1 + ".jpg"));
        new Crop(source).output(outputUri).asSquare().start(getActivity(), this);
    }

    private void handleCrop(int resultCode, Intent result) {
        int rotate = 0;

        System.out.println("resultCode " + resultCode);
        try {
            if (resultCode == RESULT_OK) {
                System.out.println("Handle crop");
                encodedString = getRealPathFromURI(Crop.getOutput(result));

                compressImage(encodedString);

            } else if (resultCode == Crop.RESULT_ERROR) {
                Toast.makeText(getActivity(), Crop.getError(result).getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String compressImage(String imageUri) throws Exception {

        String filePath = getRealPathFromURI(Uri.parse(imageUri));
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        float maxHeight = 1016.0f;
        float maxWidth = 812.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }
        utils = new ImageLoadingUtils(getActivity());
        options.inSampleSize = utils.calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inDither = false;
        // options.inPurgeable = true;
        //   options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));


        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
            scaledBitmap = scaledBitmap;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        // byte[] byteArray = stream.toByteArray();
        encodedString = Base64.encodeToString(byteArray, Base64.DEFAULT);

        /*System.out.println("selected gender" + kidGender);*/

     /*   if (kidGender == 3) {
            boyBabyImageView.setImageBitmap(scaledBitmap);
        } else if (kidGender == 4) {
            girlBabyImageView.setImageBitmap(scaledBitmap);
        }*/

        profileImage.setImageBitmap(scaledBitmap);
        /*filenameFinal = filename;*/
        System.out.println("encodedString   12 " + encodedString);


        try {
            File fileDel = new File(filename);
            fileDel.delete();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedString;

    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null,
                null, null);

        if (cursor == null) { // Source is Dropbox or other similar local file
            // path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor
                    .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath());
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + 1 + ".jpg");

        System.out.println("path+   __________" + uriSting);
        return uriSting;

    }


}
//////////////////////////////////////////////	

