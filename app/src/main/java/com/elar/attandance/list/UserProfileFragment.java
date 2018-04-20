package com.elar.attandance.list;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/*import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.elar.attandance.list.model.RetrievalHistory;
import com.elar.util.NetworkUtil;
import com.google.gson.Gson;
import com.pnf.elar.app.R;
import com.pnf.elar.app.Drawer;

import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.activity.schedule.FoodNoteActivity;
import com.pnf.elar.app.fragments.WebFragment;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;
import com.squareup.picasso.Picasso;

import android.Manifest;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class UserProfileFragment extends Fragment {
    DatePickerDialog.OnDateSetListener datepicker;
    TextView txtForCurrentDate;
    RelativeLayout layoutForGuardian1, layoutForGuardian2;
    ArrayList<String> listForIDAD = new ArrayList<String>();
    ArrayList<String> listForFromAD = new ArrayList<String>();
    ArrayList<String> listForToAD = new ArrayList<String>();
    ArrayList<String> listForByWhomeAD = new ArrayList<String>();

   /* ArrayList<String> RH_LIST_retrieved_id = new ArrayList<String>();
    ArrayList<String> RH_LIST_retrieved_mark_time = new ArrayList<String>();
    ArrayList<String> RH_LIST_retrieved_written_by_name = new ArrayList<String>();
    ArrayList<String> RH_LIST_left_mark_time = new ArrayList<String>();
    ArrayList<String> RH_LIST_left_written_by_name = new ArrayList<String>();
    ArrayList<String> RH_LIST_left_id = new ArrayList<String>();
    ArrayList<String> RH_LIST_retrieved_mark_Type = new ArrayList<String>();*/

    List<RetrievalHistory> retrievalHistoryList = new ArrayList<>();

    private static int RESULT_LOAD_IMAGE = 1;

    String status = "", imagePath, i_path;

    // student varibales
    String group_id;
    String id;
    String image_bgcolor;
    String USR_CUS_Rid;
    String USR_FirstName;
    String USR_image;
    String USR_LastName;

    // parents varivales

    String p_contact_number;
    String p_end;
    String p_group_id;
    String p_id;
    String p_start;
    String p_username;
    String p_USR_Birthday;
    String p_USR_CUS_Rid;
    String p_USR_Email;
    String p_USR_FirstName;
    String p_USR_LastName;
    String p_USR_StreetAddress;
    String p_USR_contact;

    // allergy variables

    String a_allergy_name;
    String a_contact_info;
    String a_information;

    // absent days
    // retrival variables
    String r_from_date;
    String r_id;
    String r_student_id;
    String r_to_date;
    // retrieval users variables
    String ru_id;
    String ru_USR_FirstName;
    String ru_USR_LastName;

    // rettival history
    String rh_data_type;
    String rh_id;
    String rh_mark_time;
    String rh_name;
    String rh_type;

    UserSessionManager session;

    String Base_url, lang, auth_key, Security = "H67jdS7wwfh", cDate,
            studentId;
    View v;

    private TextView txtForUserName, txtForAddInfoDesc, txtForInfoDesc,
            txtForInfoAlleg, txtForRetDate, txtForGuardian1, txtForGuardian2,contact1,contact2,tv_header_popup;

    private RelativeLayout layoutForAbsenceNotes1, layoutForAbsenceNotes2,
            layoutForAbsenceNotes3;

    private ListView listViewForAbsenceDays;
    private ImageView imgForEditAddInfo, imgForEditDescInfo,
            imgForEditAllegInfo;
    private RelativeLayout layoutForAddInfor;
    private ScrollView scrollMain;
    private Button btnForCancel, btnForSave, btnForUpdateDropOffTime;
    private EditText edtForOtherInfo;
    private TextView txtForDropOff, txtForRetrieval;

    String contact;
    String updateStatus;
    private ImageView imgForUserImage;
    ImageLoaderchildren imgLoader;
    String timeDuration, dropOffTime, retTime, title, msg, yes, no;
    private RelativeLayout layoutForAttListEdit;
    private Button btnForCancel2;

    private TextView edtForFrom, edtForTo;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar2;
    DatePickerDialog.OnDateSetListener date2;

    private Button btnForSave2;

    ListView listViewForRHistory;

    // var for update absence list

    String _id, sickdate, to_sickdate, edit_to_sickdate, edit_sickdate;

    TextView Back, Next, txtForGuardian, txtForHeading, txtForHeadingDesc,
            txtForHeadingAllergies, Absence_notes, Retriever_notes, txt1,
            txtRet, txtDrop, Notes_history, txt2, txt3,txtForFrom,txtForTo;
    Button btnForEditProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        v = inflater.inflate(R.layout.user_profile_fragment, container, false);

        session = new UserSessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        auth_key = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        cDate = getCurrentDate();

        //////////back to Main Activity ////Set title/////
        ((Drawer) getActivity()).setBackFrompublishtomainAttendance();
        if (lang.equalsIgnoreCase("sw")) {
            ((Drawer) getActivity()).setActionBarTitle("Profil");
        } else {
            ((Drawer) getActivity()).setActionBarTitle("Profile");
        }

        myCalendar = Calendar.getInstance();
        datepicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEdt();
//				back_date = txtForCurrentDate.getText().toString();
//				new GetDataCLass().execute();// ////////////////////////////
            }

        };

        /////////////
        layoutForGuardian2 = (RelativeLayout) v
                .findViewById(R.id.layoutForGuardian2);
        layoutForAttListEdit = (RelativeLayout) v
                .findViewById(R.id.layoutForAttListEdit);
        layoutForGuardian1 = (RelativeLayout) v.findViewById(R.id.layoutForGuardian1);
        btnForCancel2 = (Button) v.findViewById(R.id.btnForCancel2);
        imgForUserImage = (ImageView) v.findViewById(R.id.imgForUserImage);
        txtForRetrieval = (TextView) v.findViewById(R.id.txtForRetrieval);
        txtForDropOff = (TextView) v.findViewById(R.id.txtForDropOff);
        btnForUpdateDropOffTime = (Button) v
                .findViewById(R.id.btnForUpdateDropOffTime);

        layoutForAbsenceNotes1 = (RelativeLayout) v
                .findViewById(R.id.layoutForAbsenceNotes1);

        listViewForAbsenceDays = (ListView) v
                .findViewById(R.id.listViewForAbsenceDays);

        listViewForRHistory = (ListView) v
                .findViewById(R.id.listViewForRHistory);

        edtForFrom = (TextView) v.findViewById(R.id.edtForFrom);
        edtForTo = (TextView) v.findViewById(R.id.edtForTo);
        Back = (TextView) v.findViewById(R.id.Back);
        Next = (TextView) v.findViewById(R.id.Next);
        txtForGuardian = (TextView) v.findViewById(R.id.txtForGuardian);
        btnForEditProfile = (Button) v.findViewById(R.id.btnForEditProfile);
        txtForHeading = (TextView) v.findViewById(R.id.txtForHeading);
        txtForHeadingDesc = (TextView) v.findViewById(R.id.txtForHeadingDesc);
        txtForHeadingAllergies = (TextView) v
                .findViewById(R.id.txtForHeadingAllergies);
        Absence_notes = (TextView) v.findViewById(R.id.Absence_notes);
        Retriever_notes = (TextView) v.findViewById(R.id.Retriever_notes);
        Notes_history = (TextView) v.findViewById(R.id.Notes_history);
        txt1 = (TextView) v.findViewById(R.id.txt1);
        txtDrop = (TextView) v.findViewById(R.id.txtDrop);
        txtRet = (TextView) v.findViewById(R.id.txtRet);
        txt2 = (TextView) v.findViewById(R.id.txt2);
        txt3 = (TextView) v.findViewById(R.id.txt3);
        txtForFrom = (TextView) v.findViewById(R.id.txtForFrom);
        txtForTo = (TextView) v.findViewById(R.id.txtForTo);
        btnForSave2 = (Button) v.findViewById(R.id.btnForSave2);
        Back.setText("<<");
        Next.setText(">>");
        txtForCurrentDate = (TextView) v.findViewById(R.id.txtForCurrentDate);
        txtForCurrentDate.setText("" + getCurrentDate());
        tv_header_popup = (TextView) v.findViewById(R.id.tv_header_popup);
        if (lang.equalsIgnoreCase("sw")) {

            txtForGuardian.setText("Vårdnadshavare");
            btnForEditProfile.setText("Redigera");
            txtForHeading.setText("Annan information");
            txtForHeadingDesc.setText("Kontaktinformation");
            txtForHeadingAllergies.setText("Allergier");
            Absence_notes.setText("Frånvaroanmälan");
            Retriever_notes.setText("Annan hämtare");
            Notes_history.setText("Senaste meddelanden");
            txt1.setText("Dagens avlämnings- & hämtningstid");
            btnForUpdateDropOffTime.setText("Uppdatera");
            txt2.setText("Frånvaro Dagar");
            txt3.setText("Dagens Historik");
            txtDrop.setText("Avlämning");
            txtRet.setText("Upphämtning");
            txtForFrom.setText("Från");
            txtForTo.setText("Till");
            btnForSave2.setText("Spara");
            btnForCancel2.setText("Avbryt");

        } else {

        }

        txtForCurrentDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), datepicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    cDate = addDayInCurrentDate(cDate);
                    txtForCurrentDate.setText(cDate);
                    // Toast.makeText(getActivity(), "true.",
                    // Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    // Log.e("back right click ", "" + e);
                }

            }
        });

        Back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    cDate = removeDayInCurrentDate(cDate);
                    txtForCurrentDate.setText(cDate);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    // Log.e("back right click ", "" + e);
                }
            }
        });

        imgLoader = new ImageLoaderchildren(getActivity());

        try {
            Bundle bundle = this.getArguments();
            studentId = bundle.getString("keyForStudentId");
            timeDuration = bundle.getString("keyForTimeDuration");
            txtForRetrieval.setText(timeDuration.substring(timeDuration
                    .lastIndexOf("- ") + 2));
            txtForDropOff.setText(timeDuration.substring(0, 5));

        } catch (Exception e) {
            // TODO: handle exception
        }

        btnForEditProfile.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });


        txtForUserName = (TextView) v.findViewById(R.id.txtForUserName);
        txtForRetDate = (TextView) v.findViewById(R.id.txtForRetDate);
        txtForAddInfoDesc = (TextView) v.findViewById(R.id.txtForAddInfoDesc);
        txtForInfoDesc = (TextView) v.findViewById(R.id.txtForInfoDesc);
        txtForInfoAlleg = (TextView) v.findViewById(R.id.txtForInfoAlleg);
        edtForOtherInfo = (EditText) v.findViewById(R.id.edtForOtherInfo);

        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Message",txtForInfoDesc.getText().toString());
        clipboard.setPrimaryClip(clip);

        txtForGuardian1 = (TextView) v.findViewById(R.id.txtForGuardian1);
        txtForGuardian2 = (TextView) v.findViewById(R.id.txtForGuardian2);
        contact1 = (TextView) v.findViewById(R.id.contact1);
        contact2 = (TextView) v.findViewById(R.id.contact2);
        android.content.ClipData clip1 = android.content.ClipData.newPlainText("Message",contact1.getText().toString());
        clipboard.setPrimaryClip(clip1);
        android.content.ClipData clip2 = android.content.ClipData.newPlainText("Message",contact2.getText().toString());
        clipboard.setPrimaryClip(clip2);
        new GetUserDetail().execute();
        layoutForAbsenceNotes1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                        FragmentManager fragmentManager = getFragmentManager();
                        Bundle bundle = new Bundle();
    //                AddAbsentNotesFragment rFragment = new AddAbsentNotesFragment();
    //                FragmentTransaction ft = fragmentManager.beginTransaction();
    //                Bundle bundle = new Bundle();
    //                bundle.putString("keyForStudentId", studentId);
    //                bundle.putString("keyForStudentName", USR_FirstName + " "
    //                        + USR_LastName);
    //                rFragment.setArguments(bundle);
    //                ft.replace(R.id.content_frame, rFragment);
    //                ft.commit();


                        WebFragment rFragment = new WebFragment();
                        bundle.putString("Component_Key", "absence_noteP");
                        bundle.putString("name_en", "Absence Note");
                        bundle.putString("name_sw", "Frånvaroanmälan");
                        rFragment.setArguments(bundle);
                        FragmentTransaction ft = fragmentManager
                                .beginTransaction();
                        ft.replace(R.id.content_frame, rFragment);
                        ft.commit();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        layoutForAbsenceNotes2 = (RelativeLayout) v
                .findViewById(R.id.layoutForAbsenceNotes2);
        layoutForAbsenceNotes2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                        FragmentManager fragmentManager = getFragmentManager();

                        AddretrivalNotes rFragment = new AddretrivalNotes();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("keyForStudentId", studentId);
                        bundle.putString("keyForStudentName", USR_FirstName + " "
                                + USR_LastName);
                        rFragment.setArguments(bundle);
                        ft.replace(R.id.content_frame, rFragment);
                        ft.commit();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        layoutForAbsenceNotes3 = (RelativeLayout) v
                .findViewById(R.id.layoutForAbsenceNotes3);
        layoutForAbsenceNotes3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                        FragmentManager fragmentManager = getFragmentManager();

                        NoteHistoryFragment rFragment = new NoteHistoryFragment();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("keyForStudentId", studentId);
                        bundle.putString("keyForStudentName", USR_FirstName + " "
                                + USR_LastName);
                        rFragment.setArguments(bundle);
                        ft.replace(R.id.content_frame, rFragment);
                        ft.commit();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        imgForEditDescInfo = (ImageView) v
                .findViewById(R.id.imgForEditDescInfo);// contact information

        imgForEditDescInfo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                        // TODO Auto-generated method stub
                        layoutForAddInfor.setVisibility(View.VISIBLE);
                        if (lang.equalsIgnoreCase("sw")) {
                            tv_header_popup.setText("Kontaktinformation");
                        } else {
                            tv_header_popup.setText("Contact Information");

                        }
    //				scrollMain.setVisibility(View.GONE);
                        edtForOtherInfo.setText("" + a_contact_info);

                        updateStatus = "contact";
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

        imgForEditAddInfo = (ImageView) v.findViewById(R.id.imgForEditAddInfo); // other
        // information
        imgForEditAddInfo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                layoutForAddInfor.setVisibility(View.VISIBLE);
                if(lang.equalsIgnoreCase("sw")){
                    tv_header_popup.setText("Annan information");
                }else{
                    tv_header_popup.setText("Other Information");

                }
//				scrollMain.setVisibility(View.GONE);
                edtForOtherInfo.setText("" + a_information);

                updateStatus = "other";

            }
        });

        imgForEditAllegInfo = (ImageView) v
                .findViewById(R.id.imgForEditAllegInfo);
        imgForEditAllegInfo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                layoutForAddInfor.setVisibility(View.VISIBLE);
                if(lang.equalsIgnoreCase("sw")){
                    tv_header_popup.setText("Allergier");
                }else{
                    tv_header_popup.setText("Allergies");

                }
//				scrollMain.setVisibility(View.GONE);
                edtForOtherInfo.setText("" + a_allergy_name);
                updateStatus = "allergy";
            }
        });

        layoutForAddInfor = (RelativeLayout) v
                .findViewById(R.id.layoutForAddInfor);
        //	layoutForAddInfor.getBackground().setAlpha(210);

        scrollMain = (ScrollView) v.findViewById(R.id.scrollMain);
        layoutForAddInfor.setVisibility(View.GONE);

        btnForSave = (Button) v.findViewById(R.id.btnForSave);
        btnForCancel = (Button) v.findViewById(R.id.btnForCancel);

        if (lang.equalsIgnoreCase("en")) {

        } else {
            btnForSave.setText("Spara");
            btnForCancel.setText("Avbryt");
        }


        btnForCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                scrollMain.setVisibility(View.VISIBLE);
                layoutForAddInfor.setVisibility(View.GONE);
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        btnForSave.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                        contact = edtForOtherInfo.getText().toString();

                        if (edtForOtherInfo.getText().toString().equals("")
                                || edtForOtherInfo.getText().toString().equals(null)) {
                            edtForOtherInfo.setError("Required Field");
                        } else {

                            if (updateStatus.equals("allergy")) {
                                new UpdateAllergy().execute();
                            }

                            if (updateStatus.equals("other")) {
                                new updateAddInfo().execute();
                            }

                            if (updateStatus.equals("contact")) {
                                new updateContactDetail().execute();
                            }

                        }

                        View view = getActivity().getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
//				Toast.makeText(getActivity(), "saving", Toast.LENGTH_LONG)
//						.show();

            }
        });

        txtForDropOff.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    final Context themedContext = new ContextThemeWrapper(
                            getActivity(),
                            android.R.style.Theme_Holo
                    );
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(themedContext,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view,
                                                      int hourOfDay, int minute) {
                                    // TODO Auto-generated method stub
                                    txtForDropOff.setText(hourOfDay + ":" + minute);
                                }

                            }, hour, minute, true);// Yes 24 hour time

                    if (lang.equalsIgnoreCase("sw")) {
                        mTimePicker.setTitle("Välj tid");
                        mTimePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Klar", mTimePicker);
                        mTimePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Avbryt", mTimePicker);
                    }else{
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ok", mTimePicker);
                        mTimePicker.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancel", mTimePicker);
                    }
                    mTimePicker.show();
                }else{
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getActivity(),
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view,
                                                      int hourOfDay, int minute) {
                                    // TODO Auto-generated method stub
                                    txtForDropOff.setText(hourOfDay + ":" + minute);
                                }

                            }, hour, minute, true);// Yes 24 hour time

                    if (lang.equalsIgnoreCase("sw")) {
                        mTimePicker.setTitle("Välj tid");
                        mTimePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Klar", mTimePicker);
                        mTimePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Avbryt", mTimePicker);
                    }else{
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ok", mTimePicker);
                        mTimePicker.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancel", mTimePicker);
                    }
                    mTimePicker.show();
                }

            }
        });

        txtForRetrieval.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    final Context themedContext = new ContextThemeWrapper(
                            getActivity(),
                            android.R.style.Theme_Holo
                    );
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(themedContext,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view,
                                                      int hourOfDay, int minute) {
                                    // TODO Auto-generated method stub
                                    txtForRetrieval.setText(hourOfDay + ":"
                                            + minute);
                                }

                            }, hour, minute, true);// Yes 24 hour time
                    if (lang.equalsIgnoreCase("sw")) {
                        mTimePicker.setTitle("Välj tid");
                        mTimePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Klar", mTimePicker);
                        mTimePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Avbryt", mTimePicker);
                    }else{
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ok", mTimePicker);
                        mTimePicker.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancel", mTimePicker);
                    }
                    mTimePicker.show();

                }else{
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getActivity(),
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view,
                                                      int hourOfDay, int minute) {
                                    // TODO Auto-generated method stub
                                    txtForRetrieval.setText(hourOfDay + ":"
                                            + minute);
                                }

                            }, hour, minute, true);// Yes 24 hour time
                    if (lang.equalsIgnoreCase("sw")) {
                        mTimePicker.setTitle("Välj tid");
                        mTimePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Klar", mTimePicker);
                        mTimePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Avbryt", mTimePicker);
                    }else{
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ok", mTimePicker);
                        mTimePicker.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancel", mTimePicker);
                    }
                    mTimePicker.show();
                }
            }
        });

        btnForUpdateDropOffTime.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                        if (txtForDropOff.getText().toString().equals("HH:mm")
                                || txtForRetrieval.getText().toString().equals("HH:mm")) {

    //					Toast.makeText(getActivity(),
    //							"Please select Dropoff and Retrieval time.",
    //							Toast.LENGTH_LONG).show();

                        } else {
                            new UpdateDropOffTime().execute();
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

        btnForCancel2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                layoutForAttListEdit.setVisibility(View.GONE);
                scrollMain.setVisibility(View.VISIBLE);

            }
        });



        btnForSave2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new UpdateAbsenceDays().execute();
            }
        });

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                ((DatePickerDialog) date).getDatePicker().setMinDate(
                        System.currentTimeMillis() - 1000);
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEdt();
            }

        };

        myCalendar2 = Calendar.getInstance();
        date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEdt22();
            }

        };

        edtForFrom.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        edtForTo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        contact1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                    String number = contact1.getText().toString();
                    String name = txtForGuardian1.getText().toString();
                    MakeCallAlert(number, name);

            }
        });

        contact2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                    String number = contact2.getText().toString();
                    String name = txtForGuardian2.getText().toString();
                    MakeCallAlert(number, name);

            }
        });

        if(!isPermissionGranted()){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},1);
        }

        return v;
    }

    class GetUserDetail extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/retrivals/user_profile";
        ArrayList<String> listForParent = new ArrayList<String>();
        ArrayList<String> mobileNoParent = new ArrayList<String>();

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
            String userProfileReponse = "";
            try {

                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_key, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.UserTypeApp + "=" + URLEncoder.encode("android", "UTF-8") +
                        "&" + Const.Params.DATE + "=" + URLEncoder.encode(cDate, "UTF-8") +
                        "&" + Const.Params.StudentId + "=" + URLEncoder.encode(studentId, "UTF-8");

                userProfileReponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);

            } catch (Exception e) {
                e.printStackTrace();
            }

            /*JSONParser jsonParser = new JSONParser();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("securityKey", Security));
            params.add(new BasicNameValuePair("authentication_token", auth_key));
            params.add(new BasicNameValuePair("user_type_app", "android"));
            params.add(new BasicNameValuePair("language", lang));
            params.add(new BasicNameValuePair("date", cDate));
            params.add(new BasicNameValuePair("student_id", studentId));

            Log.e("Create  -=-=-=-=-=- ", params.toString());

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

            try {

                listForIDAD.clear();
                listForFromAD.clear();
                listForToAD.clear();
                listForByWhomeAD.clear();
                JSONObject jsonObj = new JSONObject(json.toString());
                status = jsonObj.getString("status");

                if (status.equals("true")) {
                    JSONObject studentObj = jsonObj.optJSONObject("student");
                    group_id = studentObj.getString("group_id");
                    id = studentObj.getString("id");
                    image_bgcolor = studentObj.getString("image_bgcolor");
                    USR_CUS_Rid = studentObj.getString("USR_CUS_Rid");
                    USR_FirstName = studentObj.getString("USR_FirstName");
                    USR_image = studentObj.getString("USR_image");
                    USR_LastName = studentObj.getString("USR_LastName");

                    JSONArray parent = jsonObj.optJSONArray("parent");

                    for (int i = 0; i < parent.length(); i++) {
                        JSONObject User = parent.getJSONObject(i);
                        JSONObject data = User.getJSONObject("User");

                        // p_contact_number = data.getString("contact_number");
                        p_end = data.getString("end");
                        p_group_id = data.getString("group_id");
                        p_id = data.getString("id");
                        p_start = data.getString("start");
                        p_username = data.getString("username");
                        p_USR_Birthday = data.getString("USR_Birthday");
                        p_USR_CUS_Rid = data.getString("USR_CUS_Rid");
                        p_USR_Email = data.getString("USR_Email");
                        p_USR_FirstName = data.getString("USR_FirstName");
                        p_USR_LastName = data.getString("USR_LastName");
                        p_USR_StreetAddress = data
                                .getString("USR_StreetAddress");

                        String name = filterStringToNormal(p_USR_FirstName
                                + " " + p_USR_LastName);

                        listForParent.add(name);

                    }
                    JSONObject allergyObj = jsonObj.optJSONObject("allergy");

                    a_allergy_name = allergyObj.getString("allergy_name");
                    a_contact_info = allergyObj.getString("contact_info");
                    a_information = allergyObj.getString("information");

                    JSONArray absent_days = jsonObj.getJSONArray("absent_days");
                    for (int j = 0; j < absent_days.length(); j++) {
                        JSONObject data = absent_days.getJSONObject(j);

                        JSONObject Retrival = data.optJSONObject("Retrival");
                        JSONObject User = data.optJSONObject("User");

                        r_from_date = Retrival.getString("from_date");
                        r_id = Retrival.getString("id");
                        r_student_id = Retrival.getString("student_id");
                        r_to_date = Retrival.getString("to_date");
                        // retrieval users variables
                        ru_id = User.getString("id");
                        ru_USR_FirstName = User.getString("USR_FirstName");
                        ru_USR_LastName = User.getString("USR_LastName");

                        listForIDAD.add(r_id);
                        listForFromAD.add(r_from_date);
                        listForToAD.add(r_to_date);
                        listForByWhomeAD.add(ru_USR_FirstName + " "
                                + ru_USR_LastName);

                    }
                    Log.e("id di did idididididid", "" + listForIDAD);
                    Log.e("id di did idididididid", "" + listForFromAD);
                    Log.e("id di did idididididid", "" + listForToAD);
                    Log.e("id di did idididididid", "" + listForByWhomeAD);
                    JSONArray retriever_history = jsonObj
                            .getJSONArray("retriever_history");
                    Log.e("SIZE =-=-=", ""
                            + retriever_history.length());
                    for (int k = 0; k < retriever_history.length(); k++) {
                        JSONObject data = retriever_history.getJSONObject(k);
                        String retrieved_id = data.getString("retrieved_id");
                        String retrieved_mark_time = data
                                .getString("retrieved_mark_time");
                        String retrieved_written_by_name = data
                                .getString("retrieved_written_by_name");
                        String left_mark_time = data
                                .getString("left_mark_time");
                        String left_written_by_name = data
                                .getString("left_written_by_name");
                        String left_id = data.getString("left_id");

                        RH_LIST_left_id.add("" + left_id);
                        RH_LIST_left_mark_time.add("" + left_mark_time);
                        RH_LIST_left_written_by_name.add(""
                                + left_written_by_name);
                        RH_LIST_retrieved_id.add("" + retrieved_id);
                        RH_LIST_retrieved_mark_time.add(""
                                + retrieved_mark_time);
                        RH_LIST_retrieved_written_by_name.add(""
                                + retrieved_written_by_name);
                    }

                    Log.e("RH_LIST_left_id", ""
                            + RH_LIST_left_id);

                } else {
//					Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        */
            return userProfileReponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {
                listForIDAD.clear();
                listForFromAD.clear();
                listForToAD.clear();
                listForByWhomeAD.clear();
                JSONObject jsonObj = new JSONObject();


                if (results != null && !results.isEmpty()) {
                    jsonObj = new JSONObject(results);
                    if (jsonObj.has("status")) {
                        status = jsonObj.getString("status");


                    }


                }


                if (status.equals("true")) {
                    JSONObject studentObj = jsonObj.optJSONObject("student");
                    group_id = studentObj.getString("group_id");
                    id = studentObj.getString("id");
                    image_bgcolor = studentObj.getString("image_bgcolor");
                    USR_CUS_Rid = studentObj.getString("USR_CUS_Rid");
                    USR_FirstName = studentObj.getString("USR_FirstName");
                    USR_image = studentObj.getString("USR_image");
                    USR_LastName = studentObj.getString("USR_LastName");


                    JSONArray parent = jsonObj.optJSONArray("parent");

                    for (int i = 0; i < parent.length(); i++) {
                        JSONObject User = parent.getJSONObject(i);
                        JSONObject data = User.getJSONObject("User");

                        // p_contact_number = data.getString("contact_number");
                        p_end = data.getString("end");
                        p_group_id = data.getString("group_id");
                        p_id = data.getString("id");
                        p_start = data.getString("start");
                        p_username = data.getString("username");
                        p_USR_Birthday = data.getString("USR_Birthday");
                        p_USR_CUS_Rid = data.getString("USR_CUS_Rid");
                        p_USR_Email = data.getString("USR_Email");
                        p_USR_FirstName = data.getString("USR_FirstName");
                        p_USR_LastName = data.getString("USR_LastName");
                        p_USR_StreetAddress = data
                                .getString("USR_StreetAddress");
                        p_USR_contact = data.getString("contact_number");

                        String name = filterStringToNormal(p_USR_FirstName
                                + " " + p_USR_LastName);

                        String contact = filterStringToNormal(p_USR_contact);
                        listForParent.add(name);
                        mobileNoParent.add(contact);

                    }
                    JSONObject allergyObj = jsonObj.optJSONObject("allergy");

                    a_allergy_name = allergyObj.getString("allergy_name");
                    a_contact_info = allergyObj.getString("contact_info");
                    a_information = allergyObj.getString("information");

                    JSONArray absent_days = jsonObj.getJSONArray("absent_days");
                    for (int j = 0; j < absent_days.length(); j++) {
                        JSONObject data = absent_days.getJSONObject(j);

                        JSONObject Retrival = data.optJSONObject("Retrival");
                        JSONObject User = data.optJSONObject("User");

                        r_from_date = Retrival.getString("from_date");
                        r_id = Retrival.getString("id");
                        r_student_id = Retrival.getString("student_id");
                        r_to_date = Retrival.getString("to_date");
                        // retrieval users variables
                        ru_id = User.getString("id");
                        ru_USR_FirstName = User.getString("USR_FirstName");
                        ru_USR_LastName = User.getString("USR_LastName");

                        listForIDAD.add(r_id);
                        listForFromAD.add(r_from_date);
                        listForToAD.add(r_to_date);
                        listForByWhomeAD.add(ru_USR_FirstName + " "
                                + ru_USR_LastName);

                    }
                    Log.e("id di did idididididid", "" + listForIDAD);
                    Log.e("id di did idididididid", "" + listForFromAD);
                    Log.e("id di did idididididid", "" + listForToAD);
                    Log.e("id di did idididididid", "" + listForByWhomeAD);
                    JSONArray retriever_history = jsonObj
                            .getJSONArray("retriever_history");
                    Log.e("SIZE =-=-=", ""
                            + retriever_history.length());

                    if (retriever_history.length() > 0) {

                        retrievalHistoryList = Arrays.asList(new Gson().fromJson(retriever_history.toString(), RetrievalHistory[].class));


                    }


                   /* for (int k = 0; k < retriever_history.length(); k++) {
                        JSONObject data = retriever_history.getJSONObject(k);
                      *//*  String retrieved_id = data.getString("retrieved_id");
                        String retrieved_mark_time = data
                                .getString("retrieved_mark_time");

                                  String retrieved_written_by_name = data
                                .getString("retrieved_written_by_name");
                                                        String left_id = data.getString("left_id");

                                *//*
                        String retrieved_id = data.getString("id");
                        String retrieved_mark_time = data
                                .getString("mark_time");
                        String retrieved_written_by_name = data
                                .getString("name");
                        String retirvel_type = data.getString("data_type");
                       *//* String left_mark_time = data
                                .getString("left_mark_time");
                        String left_written_by_name = data
                                .getString("left_written_by_name");

                                String left_id = data.getString("left_id");*//*


                       *//* RH_LIST_left_id.add("" + left_id);
                        RH_LIST_left_mark_time.add("" + left_mark_time);
                        RH_LIST_left_written_by_name.add(""
                                + left_written_by_name);*//*
                      *//*  RH_LIST_retrieved_id.add("" + retrieved_id);
                        RH_LIST_retrieved_mark_time.add(""
                                + retrieved_mark_time);
                        RH_LIST_retrieved_written_by_name.add(""
                                + retrieved_written_by_name);

                        RH_LIST_retrieved_mark_Type*//*
                    }
*/
                    Log.e("retrievalHistoryList", ""
                            + retrievalHistoryList.size());
                    if (listForParent.size() == 1) {

                        layoutForGuardian1.setVisibility(View.VISIBLE);
                        txtForGuardian1.setText("" + listForParent.get(0));
                        contact1.setText(mobileNoParent.get(0));
                        layoutForGuardian2.setVisibility(View.GONE);

                    } else if(listForParent.size() == 2) {
                        layoutForGuardian1.setVisibility(View.VISIBLE);
                        txtForGuardian1.setText("" + listForParent.get(0));
                        contact1.setText(mobileNoParent.get(0));
                        layoutForGuardian2.setVisibility(View.VISIBLE);
                        txtForGuardian2.setText("" + listForParent.get(1));
                        contact2.setText(mobileNoParent.get(1));

                    }

                    if (listForParent.size() > 2) {
                        layoutForGuardian1.setVisibility(View.VISIBLE);
                        txtForGuardian1.setText("" + listForParent.get(0));
                        contact1.setText(mobileNoParent.get(0));
                        layoutForGuardian2.setVisibility(View.VISIBLE);
                        txtForGuardian2.setText("" + listForParent.get(1));
                        contact2.setText(mobileNoParent.get(1));
                    }

                    txtForUserName.setText(USR_FirstName + " " + USR_LastName);
                    txtForAddInfoDesc.setText(a_information);
                    txtForInfoDesc.setText(a_contact_info);
                    txtForInfoAlleg.setText(a_allergy_name);

                    txtForRetDate.setText(cDate);
                    /*imgLoader.DisplayImage(Base_url + USR_image, imgForUserImage);*/

                    Log.e("Base_url + USR_image", Base_url + USR_image);

                    Picasso.with(getActivity())
                            .load(Uri.parse(Base_url + USR_image))
                            .placeholder(R.drawable.manjit)
                            // optional
                            .error(R.drawable.manjit)         // optional
                            .into(imgForUserImage);

                    AtandanceListAdapter ad = new AtandanceListAdapter(
                            getActivity(), listForIDAD, listForFromAD, listForToAD,
                            listForByWhomeAD);

                    listViewForAbsenceDays.setAdapter(ad);
                    setListViewHeightBasedOnChildrener(listViewForAbsenceDays);

                    AdapterForRHistory adp_ = new AdapterForRHistory(getActivity(), retrievalHistoryList);

                    listViewForRHistory.setAdapter(adp_);
                    setListViewHeightBasedOnChildrener(listViewForRHistory);
                } else {
//					Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();

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
                Log.e("ERROR", "" + e);
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

    public class AtandanceListAdapter extends BaseAdapter {

        private Activity activity;
        private LayoutInflater inflater = null;

        ArrayList<String> listForIDAD = new ArrayList<String>();
        ArrayList<String> listForFromAD = new ArrayList<String>();
        ArrayList<String> listForToAD = new ArrayList<String>();
        ArrayList<String> listForByWhomeAD = new ArrayList<String>();

        public AtandanceListAdapter(Activity a, ArrayList<String> listForIDAD,
                                    ArrayList<String> listForFromAD, ArrayList<String> listForToAD,
                                    ArrayList<String> listForByWhomeAD) {
            activity = a;
            this.listForIDAD = listForIDAD;
            this.listForFromAD = listForFromAD;
            this.listForToAD = listForToAD;
            this.listForByWhomeAD = listForByWhomeAD;

            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return listForIDAD.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView,
                            ViewGroup parent) {

            View vi = convertView;
            if (convertView == null)
                vi = inflater.inflate(
                        R.layout.layout_for_profile_screen_listview, null);
            TextView txtForFrom = (TextView) vi.findViewById(R.id.txtForFrom);

            TextView txtForTo = (TextView) vi.findViewById(R.id.txtForTo);

            TextView txtForByWhome = (TextView) vi
                    .findViewById(R.id.txtForByWhome);

            final ImageView imageForEdit = (ImageView) vi
                    .findViewById(R.id.imageForEdit);

            final ImageView imageForDelete = (ImageView) vi
                    .findViewById(R.id.imageForDelete);

            txtForFrom.setText("" + listForFromAD.get(position));
            txtForTo.setText("" + listForToAD.get(position));
            txtForByWhome.setText("" + listForByWhomeAD.get(position));
            imageForDelete.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    sickdate = listForFromAD.get(position);
                    to_sickdate = listForToAD.get(position);

                    new AlertDialog.Builder(getActivity())
                            .setTitle("Title")
                            .setMessage("Do you really want to delete?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes,
                                    new DialogInterface.OnClickListener() {

                                        public void onClick(
                                                DialogInterface dialog,
                                                int whichButton) {

                                            new DeleteADItem().execute();
                                        }
                                    })
                            .setNegativeButton(android.R.string.no, null)
                            .show();

                }
            });

            imageForEdit.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    // Toast.makeText(activity, "edit",
                    // Toast.LENGTH_LONG).show();

                    layoutForAttListEdit.setVisibility(View.VISIBLE);
                    edtForFrom.setText("" + listForFromAD.get(position));
                    edtForTo.setText("" + listForToAD.get(position));
                    scrollMain.setVisibility(View.GONE);

                    _id = listForIDAD.get(position);
                    sickdate = listForFromAD.get(position);
                    to_sickdate = listForToAD.get(position);
                }
            });

            return vi;
        }
    }

    public class AtandanceListAdapterRH extends BaseAdapter {

        private Activity activity;
        private LayoutInflater inflater = null;

        ArrayList<String> listForIDAD = new ArrayList<String>();
        ArrayList<String> listForFromAD = new ArrayList<String>();
        ArrayList<String> listForToAD = new ArrayList<String>();
        ArrayList<String> listForByWhomeAD = new ArrayList<String>();

        public AtandanceListAdapterRH(Activity a,
                                      ArrayList<String> listForIDAD, ArrayList<String> listForFromAD,
                                      ArrayList<String> listForToAD,
                                      ArrayList<String> listForByWhomeAD) {
            activity = a;
            this.listForIDAD = listForIDAD;
            this.listForFromAD = listForFromAD;
            this.listForToAD = listForToAD;
            this.listForByWhomeAD = listForByWhomeAD;

            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return listForIDAD.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView,
                            ViewGroup parent) {

            View vi = convertView;
            if (convertView == null)
                vi = inflater.inflate(
                        R.layout.layout_for_profile_screen_listview, null);
            TextView txtForFrom = (TextView) vi.findViewById(R.id.txtForFrom);

            TextView txtForTo = (TextView) vi.findViewById(R.id.txtForTo);

            TextView txtForByWhome = (TextView) vi
                    .findViewById(R.id.txtForByWhome);

            final ImageView imageForEdit = (ImageView) vi
                    .findViewById(R.id.imageForEdit);

            final ImageView imageForDelete = (ImageView) vi
                    .findViewById(R.id.imageForDelete);

            txtForFrom.setText("" + listForFromAD.get(position));
            txtForTo.setText("" + listForToAD.get(position));
            txtForByWhome.setText("" + listForByWhomeAD.get(position));
            imageForDelete.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
//					Toast.makeText(activity, "delete", Toast.LENGTH_LONG)
//							.show();
                }
            });

            imageForEdit.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
//					Toast.makeText(activity, "edit", Toast.LENGTH_LONG).show();
                }
            });

            return vi;
        }
    }

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

    class updateContactDetail extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/retrivals/update_contact";

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

            String updateContactResponse = "";
            try {

                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_key, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.UserTypeApp + "=" + URLEncoder.encode("android", "UTF-8") +
                        "&" + Const.Params.Contact + "=" + URLEncoder.encode(contact, "UTF-8") +
                        "&" + Const.Params.UserId + "=" + URLEncoder.encode(studentId, "UTF-8");

                updateContactResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);

            } catch (Exception e) {
                e.printStackTrace();
            }


            /*JSONParser jsonParser = new JSONParser();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("securityKey", Security));
            params.add(new BasicNameValuePair("authentication_token", auth_key));
            params.add(new BasicNameValuePair("user_type_app", "android"));
            params.add(new BasicNameValuePair("language", lang));
            params.add(new BasicNameValuePair("contact", contact));
            params.add(new BasicNameValuePair("user_id", studentId));

            Log.e("Create Response -=-=-=-=-=- ", params.toString());

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

            try {

                JSONObject jsonObj = new JSONObject(json.toString());
                status = jsonObj.getString("status");

                if (status.equals("true")) {

                } else {

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
*/
            return updateContactResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {


                JSONObject jsonObj = new JSONObject();

                if (results != null && !results.isEmpty()) {
                    jsonObj = new JSONObject(results);
                    status = jsonObj.getString("status");

                    if (status.equals("true")) {

                        layoutForAddInfor.setVisibility(View.GONE);
                        scrollMain.setVisibility(View.VISIBLE);
                        txtForInfoDesc.setText("" + contact);
                        a_contact_info = contact;
                        updateStatus = "";
                        edtForOtherInfo.setText("");


                    } else {
//					Toast.makeText(getActivity(),
//							"Failed to update information.Try again",
//						Toast.LENGTH_LONG).show();


                    }


                } else {

                }


            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", "" + e);
            }
        }

    }

    class updateAddInfo extends AsyncTask<String, String, String> {

        private String url = Base_url
                + "lms_api/retrivals/update_additional_info";

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

            String updateInforesponse = "";
            try {

                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_key, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.UserTypeApp + "=" + URLEncoder.encode("android", "UTF-8") +
                        "&" + Const.Params.Information + "=" + URLEncoder.encode(contact, "UTF-8") +
                        "&" + Const.Params.UserId + "=" + URLEncoder.encode(studentId, "UTF-8");

                updateInforesponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);

            } catch (Exception e) {
                e.printStackTrace();
            }


           /* JSONParser jsonParser = new JSONParser();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("securityKey", Security));
            params.add(new BasicNameValuePair("authentication_token", auth_key));
            params.add(new BasicNameValuePair("user_type_app", "android"));
            params.add(new BasicNameValuePair("language", lang));
            params.add(new BasicNameValuePair("information", contact));// information
            // in
            // contact
            params.add(new BasicNameValuePair("user_id", studentId));

            Log.e("Create Response -=-=-=-=-=- ", params.toString());

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

            try {

                JSONObject jsonObj = new JSONObject(json.toString());
                status = jsonObj.getString("status");

                if (status.equals("true")) {

                } else {

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            return updateInforesponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {
                JSONObject jsonObj = new JSONObject();


                try {

                    if (results != null && !results.isEmpty()) {
                        jsonObj = new JSONObject(results);
                        if (jsonObj.has("status")) {
                            status = jsonObj.getString("status");

                        } else {
                            status = "false";
                        }
                    } else {
                        status = "false";
                    }


                    if (status.equals("true")) {
//					Toast.makeText(getActivity(),
//							"Information Updated Successfully",
//							Toast.LENGTH_LONG).show();
                        layoutForAddInfor.setVisibility(View.GONE);
                        scrollMain.setVisibility(View.VISIBLE);
                        txtForAddInfoDesc.setText("" + contact);
                        updateStatus = "";
                        a_information = contact;
                        edtForOtherInfo.setText("");

                    } else {
//					Toast.makeText(getActivity(),
//							"Failed to update information.Try again",
//							Toast.LENGTH_LONG).show();

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


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", "" + e);
            }
        }

    }

    class UpdateAllergy extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/retrivals/update_allergies";

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
            String updateAlrgyResponse = "";
            try {

                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_key, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.UserTypeApp + "=" + URLEncoder.encode("android", "UTF-8") +
                        "&" + Const.Params.AllergyName + "=" + URLEncoder.encode(contact, "UTF-8") +
                        "&" + Const.Params.UserId + "=" + URLEncoder.encode(studentId, "UTF-8");

                updateAlrgyResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);


            /*JSONParser jsonParser = new JSONParser();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("securityKey", Security));
            params.add(new BasicNameValuePair("authentication_token", auth_key));
            params.add(new BasicNameValuePair("user_type_app", "android"));
            params.add(new BasicNameValuePair("language", lang));
            params.add(new BasicNameValuePair("allergy_name", contact));// allergy
            // in
            // contact
            params.add(new BasicNameValuePair("user_id", studentId));

            Log.e("Create Response -=-=-=-=-=- ", params.toString());

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);



                JSONObject jsonObj = new JSONObject(json.toString());
                status = jsonObj.getString("status");

                if (status.equals("true")) {

                } else {

                }
*/
            } catch (Exception e) {
                e.printStackTrace();
            }

            return updateAlrgyResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {
                JSONObject jsonObj = new JSONObject();


                if (results != null && !results.isEmpty()) {
                    jsonObj = new JSONObject(results);
                    if (jsonObj.has("status")) {
                        status = jsonObj.getString("status");
                    } else {
                        status = "false";
                    }

                } else {
                    status = "false";

                }


                if (status.equals("true")) {
//					Toast.makeText(getActivity(),
//							"Information Updated Successfully",
//							Toast.LENGTH_LONG).show();
                    layoutForAddInfor.setVisibility(View.GONE);
                    scrollMain.setVisibility(View.VISIBLE);
                    txtForInfoAlleg.setText("" + contact);
                    a_allergy_name = contact;
                    edtForOtherInfo.setText("");
                    updateStatus = "";

                } else {
//					Toast.makeText(getActivity(),
//							"Failed to update information.Try again",
//							Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", "" + e);
            }
        }

    }

    public class UpdateDropOffTime extends AsyncTask<String, String, String> {

        private String url = Base_url
                + "lms_api/retrivals/update_dropoff_and_retrieve_time";
        ArrayList<String> listForParent = new ArrayList<String>();

        private ProgressDialog pDialog;
        String urlParams;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {


                urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_key, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.DroppOffTime + "=" + URLEncoder.encode(txtForDropOff.getText().toString().trim(), "UTF-8") +
                        "&" + Const.Params.DATE + "=" + URLEncoder.encode(cDate, "UTF-8") +
                        "&" + Const.Params.StudentId + "=" + URLEncoder.encode(studentId, "UTF-8") +
                        "&" + Const.Params.RetriveTime + "=" + URLEncoder.encode(txtForRetrieval.getText().toString().trim(), "UTF-8");
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage("loading...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
//			pDialog = new ProgressDialog(getActivity());
//			pDialog.setMessage("loading...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(false);
//			pDialog.show();
        }

        protected String doInBackground(String... args) {

            String dropResponse = "";
            try {

                dropResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);

            } catch (Exception e) {
                e.printStackTrace();
            }
            /*JSONParser jsonParser = new JSONParser();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("securityKey", Security));
            params.add(new BasicNameValuePair("authentication_token", auth_key));
            params.add(new BasicNameValuePair("dropoff_time", txtForDropOff
                    .getText().toString().trim()));
            params.add(new BasicNameValuePair("language", lang));
            params.add(new BasicNameValuePair("date", cDate));
            params.add(new BasicNameValuePair("student_id", studentId));
            params.add(new BasicNameValuePair("retrieve_time", txtForRetrieval
                    .getText().toString().trim()));

            Log.e("Create Response -=-=-=-=-=- ", params.toString());

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

            try {

                JSONObject jsonObj = new JSONObject(json.toString());
                status = jsonObj.getString("status");

                if (status.equals("true")) {

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
*/
            return dropResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            pDialog.dismiss();
            try {
                JSONObject jsonObj = new JSONObject();

                if (!results.isEmpty() && results != null) {

                    jsonObj = new JSONObject(results);
                    status = jsonObj.getString("status");
                    if (status.equals("true")) {

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
                }

/*                if (status.equals("true")) {
//					Toast.makeText(getActivity(), "Data successfully updated.",
//							Toast.LENGTH_LONG).show();
                } else {
//					Toast.makeText(getActivity(),
//							"Failed to updated.Try again.", Toast.LENGTH_LONG)
//							.show();


                }*/

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", "" + e);
            }
        }

    }

    public String filterStringToNormal(String data) {
        data = data.replaceAll("[^a-zA-Z0-9 -]", "");
        System.out.println(data);
        return data;
    }

    public void updateEdt2() {
        String myFormat = "yyyy-MM-dd"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtForTo.setText(sdf.format(myCalendar2.getTime()));
    }

    public void updateEdt() {
        String myFormat = "yyyy-MM-dd"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtForFrom.setText(sdf.format(myCalendar.getTime()));
    }

    public void updateEdt22() {
        String myFormat = "yyyy-MM-dd"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txtForCurrentDate.setText(sdf.format(myCalendar.getTime()));
    }

    class UpdateAbsenceDays extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/retrivals/update_absent_days";
        ArrayList<String> listForParent = new ArrayList<String>();

        private ProgressDialog pDialog;
        String urlParams = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_key, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.StudentId + "=" + URLEncoder.encode(studentId, "UTF-8") +
                        "&" + Const.Params.ID + "=" + URLEncoder.encode(_id, "UTF-8") +
                        "&" + Const.Params.SickDate + "=" + URLEncoder.encode(sickdate, "UTF-8") +
                        "&" + Const.Params.ToSickDate + "=" + URLEncoder.encode(to_sickdate, "UTF-8") +
                        "&" + Const.Params.EditSickDate + "=" + URLEncoder.encode(edtForFrom
                        .getText().toString(), "UTF-8") +
                        "&" + Const.Params.EditToSickDate + "=" + URLEncoder.encode(edtForTo
                        .getText().toString(), "UTF-8");

                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage("loading...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected String doInBackground(String... args) {


            String updateDaysResponse = "";
            try {


                updateDaysResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }

           /* JSONParser jsonParser = new JSONParser();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("securityKey", Security));
            params.add(new BasicNameValuePair("authentication_token", auth_key));
            params.add(new BasicNameValuePair("language", lang));
            params.add(new BasicNameValuePair("student_id", studentId));
            params.add(new BasicNameValuePair("id", _id));
            params.add(new BasicNameValuePair("sickdate", sickdate));
            params.add(new BasicNameValuePair("to_sickdate", to_sickdate));
            params.add(new BasicNameValuePair("edit_sickdate", edtForFrom
                    .getText().toString()));
            params.add(new BasicNameValuePair("edit_to_sickdate", edtForTo
                    .getText().toString()));

            Log.e("Create Response -=-=-=-=-=- ", params.toString());

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

            try {

                JSONObject jsonObj = new JSONObject(json.toString());
                status = jsonObj.getString("status");

                if (status.equals("true")) {

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            return updateDaysResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            pDialog.dismiss();
            try {
                JSONObject jsonObj = new JSONObject();

                try {

                    if (!results.isEmpty() && results != null) {
                        jsonObj = new JSONObject(results);

                        if (jsonObj.has("status")) {
                            status = jsonObj.getString("status");

                        } else {
                            status = "false";
                        }


                    } else {
                        status = "false";
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (status.equals("true")) {
                    layoutForAttListEdit.setVisibility(View.GONE);
                    scrollMain.setVisibility(View.VISIBLE);
                    new GetUserDetail().execute();

//					Toast.makeText(getActivity(), "Data successfully updated.",
//							Toast.LENGTH_LONG).show();
                } else {
//					Toast.makeText(getActivity(),
//							"Failed to updated.Try again.", Toast.LENGTH_LONG)
//							.show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", "" + e);
            }
        }

    }

    class DeleteADItem extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/retrivals/delete_absent_days";

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
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.StudentId + "=" + URLEncoder.encode(studentId, "UTF-8") +
                        "&" + Const.Params.FromDate + "=" + URLEncoder.encode(sickdate, "UTF-8") +
                        "&" + Const.Params.ToDate + "=" + URLEncoder.encode(to_sickdate, "UTF-8");


                deleteResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }

			/*JSONParser jsonParser = new JSONParser();

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("securityKey", Security));
			params.add(new BasicNameValuePair("authentication_token", auth_key));
			params.add(new BasicNameValuePair("language", lang));
			params.add(new BasicNameValuePair("student_id", studentId));
			params.add(new BasicNameValuePair("from_date", sickdate));
			params.add(new BasicNameValuePair("to_date", to_sickdate));

			Log.e("Create Response for delete -=-=-=-=-=- ", params.toString());

			JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

			try {

				JSONObject jsonObj = new JSONObject(json.toString());
				status = jsonObj.getString("status");

				if (status.equals("true")) {

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
            try {
                JSONObject jsonObj = new JSONObject();

                if (results != null && !results.isEmpty()) {

                    jsonObj = new JSONObject(results);
                    if (jsonObj.has("status")) {
                        status = jsonObj.getString("status");
                    } else {
                        status = "false";
                    }

                } else {
                    status = "false";
                }


                if (status.equals("true")) {

                    new GetUserDetail().execute();

//					Toast.makeText(getActivity(), "Data successfully updated.",
//							Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(),
                            "Failed to updated.Try again.", Toast.LENGTH_LONG)
                            .show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", "" + e);
            }
        }

    }

    class AdapterForRHistory extends BaseAdapter {

        private Activity activity;
        private LayoutInflater inflater = null;
        List<RetrievalHistory> retrievalHistoriesList;

        /* ArrayList<String> RH_LIST_retrieved_id = new ArrayList<String>();
         ArrayList<String> RH_LIST_retrieved_mark_time = new ArrayList<String>();
         ArrayList<String> RH_LIST_retrieved_written_by_name = new ArrayList<String>();
         ArrayList<String> RH_LIST_left_mark_time = new ArrayList<String>();
         ArrayList<String> RH_LIST_left_written_by_name = new ArrayList<String>();
         ArrayList<String> RH_LIST_left_id = new ArrayList<String>();

         public AdapterForRHistory(Activity a,
                                   ArrayList<String> RH_LIST_retrieved_id,
                                   ArrayList<String> RH_LIST_retrieved_mark_time,
                                   ArrayList<String> RH_LIST_retrieved_written_by_name,
                                   ArrayList<String> RH_LIST_left_mark_time,
                                   ArrayList<String> RH_LIST_left_written_by_name,
                                   ArrayList<String> RH_LIST_left_id) {
             activity = a;
             this.RH_LIST_retrieved_id = RH_LIST_retrieved_id;
             this.RH_LIST_retrieved_mark_time = RH_LIST_retrieved_mark_time;
             this.RH_LIST_retrieved_written_by_name = RH_LIST_retrieved_written_by_name;
             this.RH_LIST_left_mark_time = RH_LIST_left_mark_time;
             this.RH_LIST_retrieved_written_by_name = RH_LIST_left_written_by_name;
             this.RH_LIST_left_mark_time = RH_LIST_left_id;

             inflater = (LayoutInflater) activity
                     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         }
 */
        public AdapterForRHistory(Activity a,
                                  List<RetrievalHistory> retrievalHistories) {
            this.activity = a;
            this.retrievalHistoriesList = retrievalHistories;

            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return retrievalHistoriesList.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView,
                            ViewGroup parent) {

            View vi = convertView;
            if (convertView == null)
                vi = inflater
                        .inflate(
                                R.layout.layout_for_reti_list_view_profile_screen,
                                null);
           /* TextView txtForFrom = (TextView) vi.findViewById(R.id.txtForFrom);
            TextView txtForTo = (TextView) vi.findViewById(R.id.txtForTo);
            TextView txtForByWhome = (TextView) vi
                    .findViewById(R.id.txtForByWhome);*/
            RelativeLayout retMainLayout = (RelativeLayout) vi.findViewById(R.id.retMainLayout);
            TextView txtForFromRet = (TextView) vi
                    .findViewById(R.id.txtForFromRet);
            TextView txtForToRet = (TextView) vi.findViewById(R.id.txtForToRet);
            TextView txtForByWhomeRet = (TextView) vi
                    .findViewById(R.id.txtForByWhomeRet);

          /*  String dataTypelower = retrievalHistoriesList.get(position).getData_type();
            String dataType = dataTypelower.substring(0, 1).toUpperCase() + dataTypelower.substring(1);*/

            if (retrievalHistoriesList.get(position).getData_type().equalsIgnoreCase("letf")) {
                retMainLayout.setBackgroundColor(Color.parseColor("#E7831E"));

                if (lang.equalsIgnoreCase("sw")) {

                    txtForFromRet.setText("Avlämnad");
                } else {
                    txtForFromRet.setText("Left");

                }
            } else if (retrievalHistoriesList.get(position).getData_type().equalsIgnoreCase("retrieved"))

            {
                retMainLayout.setBackgroundColor(Color.parseColor("#E88418"));
                if (lang.equalsIgnoreCase("sw")) {

                    txtForFromRet.setText("Hämtad");
                } else {
                    txtForFromRet.setText("Retrieved");

                }
            }

          /*  ImageView imageForEdit1 = (ImageView) vi
                    .findViewById(R.id.imageForEdit1);
            ImageView imageForEdit2 = (ImageView) vi
                    .findViewById(R.id.imageForEdit2);
            ImageView imageForDelete2 = (ImageView) vi
                    .findViewById(R.id.imageForDelete2);*/

           /* txtForFrom.setText("Left");
            txtForTo.setText("" + retrievalHistoriesList.get(position).getMark_time());
            txtForByWhome.setText(""
                    + retrievalHistoriesList.get(position).getName());*/


            txtForToRet.setText("" + retrievalHistoriesList.get(position).getMark_time());
            txtForByWhomeRet.setText(""
                    + retrievalHistoriesList.get(position).getName());


            return vi;
        }
    }

    ///////////////////////////
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE
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

            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(imagePath);
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }// You can get an inputStream using any IO API
            Bitmap tt = BitmapFactory.decodeFile(imagePath);   ////// new
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

            tt.compress(CompressFormat.JPEG, 70, output);    ///// new
            bytes = output.toByteArray();
            imgForUserImage.setImageBitmap(tt);
        }

    }

    public String addDayInCurrentDate(String currentDate) throws ParseException {
        // String dt = "2008-01-01"; // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(currentDate));
        c.add(Calendar.DATE, 1); // number of days to add
        currentDate = sdf.format(c.getTime());
        return currentDate;
    }

    public String removeDayInCurrentDate(String currentDate)
            throws ParseException {
        // String dt = "2008-01-01"; // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(currentDate));
        c.add(Calendar.DATE, -1); // number of days to add
        currentDate = sdf.format(c.getTime());
        return currentDate;
    }

    public void MakeCallAlert(final String phoneNumber, final String name){

        if (lang.equalsIgnoreCase("sw")) {
            title = "Ring upp via telefonen";
            msg = "Vill du kontakta "+ name.trim() + " med registrerat telefonnummer: "+phoneNumber +" ?";
            yes = "Ja";
            no = "Avbryt";
        }
        if (lang.equalsIgnoreCase("en")) {
            title = "Make a phone call";
            msg = "Do you want to call "+ name.trim() + " with registered number: "+phoneNumber+" ?";
            yes = "Yes";
            no = "No";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity(), R.style.MyAlertDialogStyle);
        alertDialogBuilder
                .setMessage(msg);
        alertDialogBuilder.setTitle(title);

        alertDialogBuilder.setPositiveButton(yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface arg0, int arg1) {

                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+phoneNumber));
                        startActivity(callIntent);

                    }
                });

        alertDialogBuilder.setNegativeButton(no,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder
                .create();
        alertDialog.show();

    }

    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("TAG","Call Permission is ON");
                } else {
                    Log.d("TAG","Call Permission is OFF");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
