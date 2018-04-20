package com.pnf.elar.app;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/*import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.elar.util.NetworkUtil;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;

@SuppressWarnings("deprecation")
public class Post_filtter extends Fragment {

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar2;
    DatePickerDialog.OnDateSetListener date2;

    View v;
    AlertDialog.Builder ad;
    String[] grp_id;
    String[] curriculum_title;
    String[] curriculum_id;
    String[] grp_name;
    Button cancel, Submit;
    Spinner spinner;
    private int year;
    private int month;
    private int day;
    TextView timePicker1, timePicker2, grps, Studnts, selectgrp, selectstd,
            selectcur, from, too;
    String[] w;
    String[] STDcount;
    String[] std_id;
    String[] std_name;
    int gl;
    int gs;
    UserSessionManager session;
    String lang, auth_token, Base_url, user_typ;
    String frm, to, class_name;
    static final int DATE_PICKER_ID = 1111;
    String[] myList = {"Tea", "Coffee", "Milk", "Tea", "Coffee", "Milk",
            "Tea", "Coffee", "Milk"};
    boolean[] _selections;
    boolean[] _selectionstwo;
    ArrayAdapter<String> adapter;
    String criclum_id;

    // protected boolean[] _selectionstwo = new boolean[ optntwo.length ];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_post_filtter, container, false);
        session = new UserSessionManager(getActivity());

        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        // auth_token = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        user_typ = user.get(UserSessionManager.TAG_user_type);

        too = (TextView) v.findViewById(R.id.to);
        from = (TextView) v.findViewById(R.id.from);
        selectcur = (TextView) v.findViewById(R.id.selectcur);
        selectstd = (TextView) v.findViewById(R.id.selectstd);
        selectgrp = (TextView) v.findViewById(R.id.selectgrp);
        grps = (TextView) v.findViewById(R.id.grps);
        Studnts = (TextView) v.findViewById(R.id.Studnts);
        cancel = (Button) v.findViewById(R.id.cancel);
        Submit = (Button) v.findViewById(R.id.Submit);
        spinner = (Spinner) v.findViewById(R.id.spinner);
        timePicker2 = (TextView) v.findViewById(R.id.timePicker2);
        timePicker1 = (TextView) v.findViewById(R.id.timePicker1);

        if (user_typ.equalsIgnoreCase("Parent")) {

            ((Drawer) getActivity()).setBackForChildEduedu();
            ((Drawer) getActivity()).Hideserch();
            ((Drawer) getActivity()).HideRefresh();
            selectstd.setVisibility(View.GONE);
            Studnts.setVisibility(View.GONE);
            auth_token = user
                    .get(UserSessionManager.TAG_Authntication_Children);
            Log.i("auth_token_child", auth_token);
        } else {
            auth_token = user.get(UserSessionManager.TAG_Authntication_token);
            Log.i("auth_token", auth_token);
        }

        /////////////////// firstly call ////
        new filterclass().execute();
        /////////////////////


        String publish_screen = "filter_class";
        session.create_main_Screen("");
        session.createpublish(publish_screen);

        if (lang.equalsIgnoreCase("sw")) {
            too.setText("Till");
            from.setText("Från");
            cancel.setText("Avbryt");
            Submit.setText("Klar");
            selectcur.setText("Läroplan Taggar");
            Studnts.setText("Alla");
            selectstd.setText("Utvalda användare");
            grps.setText("Välj grupp");
            selectgrp.setText("Gruppurval");
            timePicker1.setText("ÅÅÅÅ-MM-DD");
            timePicker2.setText("ÅÅÅÅ-MM-DD");
        } else {

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

        myCalendar2 = Calendar.getInstance();
        date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEdt2();
            }

        };

        // ///////////
        // /////////////
        timePicker1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        timePicker2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

//		// ///////////Back to mainActivity ////////
        ((Drawer) getActivity()).setBackFrompublishtomain();
        if (lang.equalsIgnoreCase("sw")) {
            ((Drawer) getActivity()).setActionBarTitle("Lärobloggsfilter");
        } else {
            ((Drawer) getActivity()).setActionBarTitle("Filter");
        }
        ((Drawer) getActivity()).Hideserch();
        ((Drawer) getActivity()).HideRefresh();
//		// ////////////

        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                FragmentManager fragmentManager = getFragmentManager();
                MainActivity rFragment = new MainActivity();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, rFragment);
                ft.commit();
            }
        });

        try {

            grps.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    String title;

                    if (lang.equalsIgnoreCase("sw")) {
                        title = "grupper..";
                    } else {
                        title = "Groups..";
                    }

                    // Log.i("999999999", Arrays.deepToString(grp_name));
                    ad = new AlertDialog.Builder(getActivity());
                    ad.setTitle(title);

                    ad.setMultiChoiceItems(grp_name, _selections,
                            new OnMultiChoiceClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0,
                                                    int arg1, boolean arg2) {
                                    // TODO Auto-generated method stub

                                }
                            });
                    ad.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    printSelectedPlanets();

                                    if (w != null && w.length > 0) {
                                        new getstudent().execute();
                                    } else {
                                        Toast.makeText(getActivity(),
                                                "Group cannot be empty.....",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    Studnts.setText("Student Selected :");

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
            });

        } catch (Exception e) {
            // TODO: handle exception
        }

        // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        try {

            Studnts.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    String title;

                    if (lang.equalsIgnoreCase("sw")) {
                        title = "grupper..";
                    } else {
                        title = "Groups..";
                    }

                    // Log.i("999999999", Arrays.deepToString(grp_name));
                    ad = new AlertDialog.Builder(getActivity());
                    ad.setTitle(title);

                    ad.setMultiChoiceItems(std_name, _selectionstwo,
                            new OnMultiChoiceClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0,
                                                    int arg1, boolean arg2) {
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
                                        printSelectedPlanetstwo();
                                    } catch (Exception e) {
                                        // TODO: handle exception
                                    }

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
            });

        } catch (Exception e) {
            // TODO: handle exception
        }

        Submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                        try {

                            frm = timePicker1.getText().toString();
                            to = timePicker2.getText().toString();
                            Log.i("grupisssss", Arrays.deepToString(w));
                            Log.i("stdidsssssssss", Arrays.deepToString(STDcount));
                            Log.i("curriculam tagss", criclum_id);
                            Log.i("from date", frm);
                            Log.i("to date ", to);


                            if (w == null && criclum_id.equalsIgnoreCase("none") && frm.equalsIgnoreCase("YYYY-MM-DD") && to.equalsIgnoreCase("YYYY-MM-DD")) {
                                if (lang.equalsIgnoreCase("en")) {
                                    Toast.makeText(getActivity(), "Select atleast one filter option", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Välj minst ett filteralternativ", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (w != null && w.length > 0) {
                                    if (STDcount != null && w.length > 0) {

                                        FragmentManager fragmentManager = getFragmentManager();
                                        MainActivity rFragment = new MainActivity();
                                        Bundle bundle = new Bundle();
                                        bundle.putStringArray("grps_ids", w);
                                        bundle.putStringArray("std_ids", STDcount);
                                        bundle.putString("curriculam_id", criclum_id);
                                        bundle.putString("from_data", frm);
                                        bundle.putString("to_data", to);
                                        rFragment.setArguments(bundle);
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();

                                    } else if (STDcount == null) {

                                        if ((frm.equalsIgnoreCase("YYYY-MM-DD")) || (to.equalsIgnoreCase("YYYY-MM-DD")) || (frm.equalsIgnoreCase("ÅÅÅÅ-MM-DD") || to.equalsIgnoreCase("ÅÅÅÅ-MM-DD"))) {
    //                                Toast.makeText(getActivity(), "Select Date..", Toast.LENGTH_SHORT).show();
                                            Calendar calendar = Calendar.getInstance();
                                            SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
                                            String strDate = mdformat.format(calendar.getTime());
                                            STDcount = new String[0];

                                            FragmentManager fragmentManager = getFragmentManager();
                                            MainActivity rFragment = new MainActivity();
                                            Bundle bundle = new Bundle();
                                            bundle.putStringArray("grps_ids", w);
                                            bundle.putStringArray("std_ids", STDcount);
                                            bundle.putString("curriculam_id", criclum_id);
                                            bundle.putString("from_data", strDate);
                                            bundle.putString("to_data", strDate);
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        } else {

                                            STDcount = new String[0];

                                            FragmentManager fragmentManager = getFragmentManager();
                                            MainActivity rFragment = new MainActivity();
                                            Bundle bundle = new Bundle();
                                            bundle.putStringArray("grps_ids", w);
                                            bundle.putStringArray("std_ids", STDcount);
                                            bundle.putString("curriculam_id", criclum_id);
                                            bundle.putString("from_data", frm);
                                            bundle.putString("to_data", to);
                                            rFragment.setArguments(bundle);
                                            FragmentTransaction ft = fragmentManager
                                                    .beginTransaction();
                                            ft.replace(R.id.content_frame, rFragment);
                                            ft.commit();
                                        }
                                    }
                                } else if (w == null) {

    //                        Toast.makeText(getActivity(), "Select group..", Toast.LENGTH_SHORT).show();

                                    STDcount = new String[0];
                                    w = new String[0];

                                    FragmentManager fragmentManager = getFragmentManager();
                                    MainActivity rFragment = new MainActivity();
                                    Bundle bundle = new Bundle();
                                    bundle.putStringArray("grps_ids", w);
                                    bundle.putStringArray("std_ids", STDcount);
                                    bundle.putString("curriculam_id", criclum_id);
                                    bundle.putString("from_data", frm);
                                    bundle.putString("to_data", to);
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();

                                }
                            }

                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        return v;
    }

    // //////
    public void updateEdt() {

        String myFormat = "yyyy-MM-dd"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        timePicker1.setText(sdf.format(myCalendar.getTime()));
    }

    public void updateEdt2() {

        String myFormat = "yyyy-MM-dd"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        timePicker2.setText(sdf.format(myCalendar2.getTime()));
    }

    // ///////////
    private boolean isOnline() {
        // TODO Auto-generated method stub

        ConnectivityManager conMgr = (ConnectivityManager) getActivity()
                .getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            // Toast.makeText(getApplicationContext(),
            // "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }

        return false;
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected void printSelectedPlanetstwo() {
        String[] d = new String[_selectionstwo.length];
        int count = 0;
        for (int i = 0; i < std_name.length; i++) {
            Log.i("ME", std_name[i] + " selected: " + _selectionstwo[i] + i);

            if (_selectionstwo[i] == true) {
                Log.i("true", Integer.toString(i));
                d[i] = Integer.toString(i);
                count = count + 1;
                // hm.add( Integer.toString(i));
            }
        }
        // Log.i("tttttt", Arrays.deepToString(d));
        //
        // Log.i("hhhhhhhhhhh", Integer.toString(count));

        String[] p = new String[count];
        int j = 0;
        for (int i = 0; i < d.length; i++) {

            if (!(d[i] == null)) {
                p[j] = d[i];
                j++;

            }

        }
        // Log.i("@@@@@@@@@@@@@@", Arrays.deepToString(p));
        // Log.i("ppppppppppppp", p[0]);
        STDcount = new String[count];
        String n;
        int g;
        for (int i = 0; i < count; i++) {
            n = p[i];
            g = Integer.parseInt(n);

            STDcount[i] = std_id[g];

        }
        Log.i("=============", Arrays.deepToString(w));
        Studnts.setText(count + "  Student Selected:");
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ///////////////////////////////////////////////////////////
    protected void printSelectedPlanets() {
        String[] d = new String[_selections.length];
        int count = 0;
        for (int i = 0; i < grp_name.length; i++) {
            Log.i("ME", grp_name[i] + " selected: " + _selections[i] + i);

            if (_selections[i] == true) {
                Log.i("true", Integer.toString(i));
                d[i] = Integer.toString(i);
                count = count + 1;
                // hm.add( Integer.toString(i));
            }
        }
        // Log.i("tttttt", Arrays.deepToString(d));
        //
        // Log.i("hhhhhhhhhhh", Integer.toString(count));

        String[] p = new String[count];
        int j = 0;
        for (int i = 0; i < d.length; i++) {

            if (!(d[i] == null)) {
                p[j] = d[i];
                j++;

            }

        }
        // Log.i("@@@@@@@@@@@@@@", Arrays.deepToString(p));
        // Log.i("ppppppppppppp", p[0]);
        w = new String[count];
        String n;
        int g;
        for (int i = 0; i < count; i++) {
            n = p[i];
            g = Integer.parseInt(n);

            w[i] = grp_id[g];

        }
        // Log.i("=============", Arrays.deepToString(w));
        grps.setText(count + "  Groups Selected:");
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
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

        private Boolean errorgroup = false;

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

            /*List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("securityKey", "H67jdS7wwfh"));
            params.add(new BasicNameValuePair("authentication_token", auth_token));
            params.add(new BasicNameValuePair("language", lang));

            try {
                json = jsonParser.makeHttpRequest(url_create_product,
                        "POST", params);

                status = json.getString(TAG_staus);
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                errorgroup = true;
                Log.i("error ", "error generate");
            }

            if (!errorgroup.equals(true)) {

                if (status.equalsIgnoreCase("true")) {
                    try {
                        JSONArray grps_name = json.optJSONArray(TAG_groups);

                        grp_id = new String[grps_name.length()];
                        grp_name = new String[grps_name.length()];
                        _selections = new boolean[grps_name.length()];
                        for (int j = 0; j < grps_name.length(); j++) {
                            JSONObject c = grps_name.getJSONObject(j);
                            //
                            grp_id[j] = c.getString(TAG_groups_id);
                            grp_name[j] = c.getString(TAG_groups_name);

                        }

                        JSONArray curriculum_tag = json
                                .optJSONArray(TAG_curriculum_tags);

                        curriculum_id = new String[curriculum_tag.length()];
                        curriculum_title = new String[curriculum_tag.length()];

                        for (int j = 0; j < curriculum_tag.length(); j++) {
                            JSONObject d = curriculum_tag.getJSONObject(j);
                            //
                            curriculum_id[j] = d.getString(TAG_curriculum_id);
                            curriculum_title[j] = d.getString(TAG_curriculum_title);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                }
            } else {
                Log.i("server error", "server error");
            }
*/
            return filterResponse;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject();
            try {

                if (results != null && !results.isEmpty()) {

                    jsonObject = new JSONObject(results);
                    if (jsonObject.has(Const.Params.Status)) {
                        status = jsonObject.getString(TAG_staus);

                    } else {
                        errorgroup = true;
                        status = "false";

                    }

                } else {
                    errorgroup = true;
                    status = "false";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            if (!errorgroup.equals(true)) {

                if (status.equalsIgnoreCase("true")) {
                    try {
                        JSONArray grps_name = jsonObject.optJSONArray(TAG_groups);

                        grp_id = new String[grps_name.length()];
                        grp_name = new String[grps_name.length()];
                        _selections = new boolean[grps_name.length()];
                        for (int j = 0; j < grps_name.length(); j++) {
                            JSONObject c = grps_name.getJSONObject(j);
                            //
                            grp_id[j] = c.getString(TAG_groups_id);
                            grp_name[j] = c.getString(TAG_groups_name);

                        }

                        JSONArray curriculum_tag = jsonObject
                                .optJSONArray(TAG_curriculum_tags);

                        curriculum_id = new String[curriculum_tag.length()];
                        curriculum_title = new String[curriculum_tag.length()];

                        for (int j = 0; j < curriculum_tag.length(); j++) {
                            JSONObject d = curriculum_tag.getJSONObject(j);
                            //
                            curriculum_id[j] = d.getString(TAG_curriculum_id);
                            curriculum_title[j] = d.getString(TAG_curriculum_title);

                        }


                        adapter = new ArrayAdapter<String>(getActivity(),
                                R.layout.spinner_item, curriculum_title);
                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                                       int arg2, long arg3) {

                                int position = spinner.getSelectedItemPosition();
                                // Toast.makeText(getApplicationContext(),"You have selected "+curriculum_title[+position],Toast.LENGTH_LONG).show();
                                // Log.i("position ", Integer.toString(position));
                                // TODO Auto-generated method stub
                                criclum_id = curriculum_id[position];
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub

                            }

                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Log.i("server error", "server error");
            }


        }
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


            JSONArray mJSONArray = new JSONArray(Arrays.asList(w));
            Log.i("w array...", Arrays.deepToString(w));
            // {"group_ids":["66","40"],"securityKey":"H67jdS7wwfh","language":"en","authentication_token":"a0790005b5646c244434da977cd8cd94beb04baf"}
            String ui = "{" + "\"group_ids\"" + ":" + mJSONArray + ","
                    + "\"securityKey\"" + ":" + "\"H67jdS7wwfh\"" + ","
                    + "\"language\"" + ":" + JSONObject.quote(lang) + ","
                    + "\"authentication_token\"" + ":"
                    + JSONObject.quote(auth_token) + "}";


            String studentsResponse = "";
            try {
                String urlParams = "&" + Const.Params.JsonData + "=" + URLEncoder.encode(ui, "UTF-8");


                studentsResponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }


			/*	// Building Parameters

			 try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("jsonData", ui));

				JSONObject json = jsonParser.makeHttpRequest(
						url_create_product, "POST", params);

				// check for success tag
				Log.i("json data......", json.toString());

				status = json.getString(TAG_staus);

				// Log.e("=-=-=-=-=-=-", status);

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

				/*if (status.equalsIgnoreCase("true")) {

				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			*/

            return studentsResponse;
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
                    }


                }


                // Log.e("=-=-=-=-=-=-", status);


                // Log.i("oooooo", imm[0]);
                if (status.equalsIgnoreCase("true")) {
                    JSONArray grps_name = jsonObject.optJSONArray(TAG_students);

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

}
