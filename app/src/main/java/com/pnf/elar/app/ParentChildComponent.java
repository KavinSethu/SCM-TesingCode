package com.pnf.elar.app;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/*import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elar.attandance.list.AbsenceNotes;
import com.elar.attandance.list.AddAbsentNotesFragment;
import com.elar.attandance.list.AddretrivalNotes;
import com.elar.util.NetworkUtil;
import com.pnf.elar.app.activity.schedule.AddFoodMenuActivity;
import com.pnf.elar.app.activity.schedule.AsynchronousActivity;
import com.pnf.elar.app.fragments.EduBlogWebFragment;
import com.pnf.elar.app.fragments.WebFragment;
import com.pnf.elar.app.fragments.NewsWebFragment;
import com.pnf.elar.app.fragments.TodaysNoteFragment;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;
import com.squareup.picasso.Picasso;

@SuppressWarnings("deprecation")
@SuppressLint("NewApi")
public class ParentChildComponent extends Fragment {
    DatePickerDialog.OnDateSetListener datepicker;
    Calendar myCalendar;
    TextView Back, Forwrd, date, drop_off, Retrieval, drp_off, Rtrvl,
            Sundayshow,smartnotes;
    UserSessionManager session;
    View v;
    String cDate, back_date;
    GridView gv;
    String[] component_id;
    String lang, auth_token, Base_url, prnt_id, child_token="",
            Authntication_Child, parnt_id, eduBlog_count = "0",
            news_count = "0", children_name, edu_count, new_count;
    int compareDate;

    String child_image;
    ImageView retriever_icon, absent_note_icon;

    ViewGroup actionBarLayout;
    LinearLayout actionbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_parent_child_component,
                container, false);


        session = new UserSessionManager(getActivity());

        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        auth_token = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        parnt_id = user.get(UserSessionManager.TAG_prnt_id);
        Authntication_Child = user
                .get(UserSessionManager.TAG_Authntication_Children);
        children_name = user.get(UserSessionManager.TAG_cld_nm);
        edu_count = user.get(UserSessionManager.TAG_eduBlog_count);
        new_count = user.get(UserSessionManager.TAG_news_count);

        smartnotes = (TextView) ((Drawer) getActivity()).findViewById(R.id.text1);
        smartnotes.setVisibility(View.GONE);





        cDate = getCurrentDate();
        Back = (TextView) v.findViewById(R.id.Back);
        Forwrd = (TextView) v.findViewById(R.id.Forwrd);
        date = (TextView) v.findViewById(R.id.date);
        drop_off = (TextView) v.findViewById(R.id.drop_off);
        Retrieval = (TextView) v.findViewById(R.id.Retrieval);
        retriever_icon = (ImageView) v.findViewById(R.id.retriever_icon);
        absent_note_icon = (ImageView) v.findViewById(R.id.absent_note_icon);
        drp_off = (TextView) v.findViewById(R.id.drp_off);
        Rtrvl = (TextView) v.findViewById(R.id.Rtrvl);
        // Sundayshow=(TextView)v.findViewById(R.id.Sundayshow);
        Back.setText("<<");
        Forwrd.setText(">>");
        date.setText("" + getCurrentDate());
        final String cDateCompare = getCurrentDate();
        compareDate = cDate.compareTo(cDate);

        if (lang.equalsIgnoreCase("sw")) {
            drp_off.setText("Avlämning :");
            Rtrvl.setText("Upphämtning :");
        }

        gv = (GridView) v.findViewById(R.id.parnt_child_component);
        try {
            Bundle bundle = this.getArguments();
            child_token = bundle.getString("child_token");
            prnt_id = bundle.getString("id");
            child_image = bundle.getString("child_image");

            Log.i("prnt_id", prnt_id);
            Log.i("child_token", child_token);
            Log.d("child_image", child_image);

        } catch (Exception e2) {
            // TODO: handle exception
            e2.printStackTrace();
        }



        //ACTION BAR IMAGE CHNAGE
        actionBarLayout = (ViewGroup)getActivity().getLayoutInflater().inflate(
                R.layout.actionbar, null);
        actionbar = (LinearLayout)getActivity().findViewById(R.id.actionbar);

        ImageView img = (ImageView)actionBarLayout. findViewById(R.id.img);
        ImageView img2 = (ImageView)actionBarLayout. findViewById(R.id.img2);

        // /////Actionbar title /////////
        ((Drawer) getActivity()).setActionBarTitle(children_name);
        ((Drawer) getActivity()).showbackbutton();
        ((Drawer) getActivity()).BackToParrentChidrn();
        ((Drawer) getActivity()).Hideserch();
        ((Drawer) getActivity()).HideRefresh();





        Log.d("child_image_after",""+child_image);
        String sel_child_image_before=session.getSelectedChildImage();
        Log.d("sel_child_image_before",""+sel_child_image_before);
        ((Drawer) getActivity()).setActionBarImage(sel_child_image_before);
        /*if (child_image=="")
        {
            String sel_child_image=session.getSelectedChildImage();
            Log.d("sel_child_image",""+sel_child_image);
            ((Drawer) getActivity()).setActionBarImage(sel_child_image);
        }
        else {
            ((Drawer) getActivity()).setActionBarImage(child_image);
        }*/
        // ((Drawer)getActivity()).hideforall();
        // //////////////////////////



        // ////////// date picker////////
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
                back_date = date.getText().toString();
                new GetDataCLass().execute();// ////////////////////////////
            }

        };

        absent_note_icon.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                FragmentManager fragmentManager = getFragmentManager();
                AbsenceNotes rFragment = new AbsenceNotes();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                Bundle bundleone = new Bundle();
                bundleone.putString("absentnoteicon", "absentnoteicon");
                rFragment.setArguments(bundleone);
                ft.replace(R.id.content_frame, rFragment);
                ft.commit();
            }
        });

        date.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new DatePickerDialog(getActivity(), datepicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        retriever_icon.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                FragmentManager fragmentManager = getFragmentManager();
                AbsenceNotes rFragment = new AbsenceNotes();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                Bundle bundleone = new Bundle();
                bundleone.putString("absentnoteicon", "retrievericon");
                rFragment.setArguments(bundleone);
                ft.replace(R.id.content_frame, rFragment);
                ft.commit();
            }
        });

        // ////////////
        Back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    cDate = removeDayInCurrentDate(cDate);
                    back_date = cDate;
                    date.setText(cDate);
                    new GetDataCLass().execute();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    // Log.e("back right click ", "" + e);
                }

            }
        });

        Forwrd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // Log.e("comapre date", "compared date: " + compareDate);

                try {
//					if (cDateCompare.equals(addDayInCurrentDate(cDate))) {
                    cDate = addDayInCurrentDate(cDate);
                    back_date = cDate;
                    date.setText(cDate);
                    new GetDataCLass().execute();
                    // Toast.makeText(getActivity(), "true.",
                    // Toast.LENGTH_LONG).show();
//					} else {
//						// Toast.makeText(getActivity(), "Invalid Request.",
//						// Toast.LENGTH_LONG).show();
//					}
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    // Log.e("back right click ", "" + e);
                }

            }
        });

        new prntchld().execute();

        return v;
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
    // ////////////
    class prntchld extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/users/get_parent_components";
        private static final String TAG_STATUS = "status";
        private static final String TAG_Component = "ComComponent";
        private static final String TAG_name = "name";
        private static final String TAG_name_sw = "name_sw";
        private static final String TAG_img_path = "img_path";
        private static final String TAG_id = "id";
        private static final String TAG_app_status = "App_Status";

        private static final String TAG_child_attedance_info = "child_attedance_info";
        private static final String TAG_date = "date";
        private static final String TAG_drop_off_time = "drop_off_time";
        private static final String TAG_retrieval_time = "retrieval_time";
        private static final String TAG_retriever_icon = "retriever_icon";
        private static final String TAG_absent_note_icon = "absent_note_icon";

        private MyCustomProgressDialog dialog;

        String Security = "H67jdS7wwfh", Status="", Drop_off_time, Retrieval_time,
                Date, ret_icon, absent_icon, current_day;
        String[] name, name_sw, image_path, component_id, component_status;

        // int[] image;

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
            String parentResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(Authntication_Child, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.ParentId + "=" + URLEncoder.encode(parnt_id, "UTF-8");


                parentResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }

           /* JSONParser jsonParser = new JSONParser();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("securityKey", Security));
            params.add(new BasicNameValuePair("authentication_token",
                    Authntication_Child));
            params.add(new BasicNameValuePair("language", lang));
            params.add(new BasicNameValuePair("parent_id", parnt_id));
            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

            Log.d("otheraccount......", json.toString());

            try {

                JSONObject jsonObj = new JSONObject(json.toString());

                Status = jsonObj.getString(TAG_STATUS);
                // ////////////

                if (Status.equalsIgnoreCase("true")) {

                    JSONArray Component = jsonObj.optJSONArray(TAG_Component);

                    name = new String[Component.length() + 1];
                    name_sw = new String[Component.length() + 1];
                    image_path = new String[Component.length() + 1];
                    component_id = new String[Component.length()];

                    for (int j = 0; j < Component.length(); j++) {
                        JSONObject d = Component.getJSONObject(j);

                        name[j] = d.optString(TAG_name);
                        name_sw[j] = d.optString(TAG_name_sw);

                        image_path[j] = Base_url + d.optString(TAG_img_path);
                        component_id[j] = d.optString(TAG_id);

                    }
                    name[name.length - 1] = "Schedule";
                    component_id[component_id.length - 1] = "113";
                    image_path[image_path.length - 1] = "http://presentation.elar.se/img/Schedule.png";
                    name_sw[name_sw.length - 1] = "Schema";


                    Log.i("component_id", Arrays.deepToString(component_id));

                    JSONObject child_attedance = jsonObj
                            .getJSONObject(TAG_child_attedance_info);

                    Drop_off_time = child_attedance
                            .getString(TAG_drop_off_time);
                    Retrieval_time = child_attedance
                            .getString(TAG_retrieval_time);
                    Date = child_attedance.getString(TAG_date);
                    ret_icon = child_attedance.getString(TAG_retriever_icon);
                    absent_icon = child_attedance
                            .getString(TAG_absent_note_icon);
                    current_day = child_attedance.getString("current_day");

                    eduBlog_count = jsonObj.getString("eduBlog_count");
                    news_count = jsonObj.getString("news_count");

                } else {
                    // Log.i("res", "false");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
*/
            return parentResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {
                back_date = null;
                // Log.i("main_image_header", main_image_header);

                JSONObject jsonObj=new JSONObject();

                if(results!=null&&!results.isEmpty())
                {

                    jsonObj=new JSONObject(results);

                    if(jsonObj.has(Const.Params.Status))
                    {
                        Status=jsonObj.getString(Const.Params.Status);
                    }

                }

                if (Status.equalsIgnoreCase("true")) {


                    JSONArray Component = jsonObj.optJSONArray(TAG_Component);

                    int tilesLength = Component.length();

                    if(tilesLength > 10){
                        gv.setNumColumns(3);
                        gv.setColumnWidth(100);
                        gv.setVerticalSpacing(26);
                        gv.setHorizontalSpacing(18);
                    }else{
                        gv.setNumColumns(2);
                    }

                    name = new String[Component.length()];
                    name_sw = new String[Component.length() ];
                    image_path = new String[Component.length()];
                    component_id = new String[Component.length()];
                    component_status = new String[Component.length()];

                    for (int j = 0; j < Component.length(); j++) {
                        JSONObject d = Component.getJSONObject(j);

                        name[j] = d.optString(TAG_name);
                        name_sw[j] = d.optString(TAG_name_sw);

                        image_path[j] = Base_url + d.optString(TAG_img_path);
                        component_id[j] = d.optString(TAG_id);
                        component_status[j] = d.optString(TAG_app_status);

                    }
                   /* name[name.length- 1] = "Schedule";
                    component_id[component_id.length - 1] = "113";
                    image_path[image_path.length - 1] = "http://presentation.elar.se/img/Schedule.png";
                    name_sw[name_sw.length - 1] = "Schema";*/


                    Log.i("component_id", Arrays.deepToString(component_id));

                    JSONObject child_attedance = jsonObj
                            .getJSONObject(TAG_child_attedance_info);

                    Drop_off_time = child_attedance
                            .getString(TAG_drop_off_time);
                    Retrieval_time = child_attedance
                            .getString(TAG_retrieval_time);
                    Date = child_attedance.getString(TAG_date);
                    ret_icon = child_attedance.getString(TAG_retriever_icon);
                    absent_icon = child_attedance
                            .getString(TAG_absent_note_icon);
                    current_day = child_attedance.getString("current_day");

                    eduBlog_count = jsonObj.getString("eduBlog_count");
                    news_count = jsonObj.getString("news_count");

                    if (ret_icon.equalsIgnoreCase("true")) {
                        retriever_icon.setVisibility(View.VISIBLE);
                    } else {
                        retriever_icon.setVisibility(View.GONE);
                    }
                    if (absent_icon.equalsIgnoreCase("true")) {
                        absent_note_icon.setVisibility(View.VISIBLE);
                    } else {
                        absent_note_icon.setVisibility(View.GONE);
                    }
                    if (current_day.equalsIgnoreCase("sun")) {
                        // drp_off.setVisibility(View.GONE);
                        // Rtrvl.setVisibility(View.GONE);
                    } else {
                        // drp_off.setVisibility(View.VISIBLE);
                        // Rtrvl.setVisibility(View.VISIBLE);

                        drop_off.setText(Drop_off_time);
                        // Retrieval.setTextSize(18.0f);
                        Retrieval.setText(Retrieval_time);
                    }

                    gv.setAdapter(new Adapter_drawer(getActivity(), name,
                            name_sw, image_path,component_id,tilesLength,component_status));

                } else {

                    // failed to creat

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

    // ////////
    // ////////
    class GetDataCLass extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/users/get_parent_components";
        private static final String TAG_STATUS = "status";
        private static final String TAG_Component = "ComComponent";
        private static final String TAG_name = "name";
        private static final String TAG_name_sw = "name_sw";
        private static final String TAG_img_path = "img_path";
        private static final String TAG_id = "id";

        private static final String TAG_child_attedance_info = "child_attedance_info";
        private static final String TAG_date = "date";
        private static final String TAG_drop_off_time = "drop_off_time";
        private static final String TAG_retrieval_time = "retrieval_time";
        private static final String TAG_retriever_icon = "retriever_icon";
        private static final String TAG_absent_note_icon = "absent_note_icon";

        private MyCustomProgressDialog dialog;

        String Security = "H67jdS7wwfh", Status="", Drop_off_time, Retrieval_time,
                Date, ret_icon, absent_icon, current_day;
        String[] name, name_sw, image_path, component_id;

        // int[] image;

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
            String parentResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(child_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.ParentId + "=" + URLEncoder.encode(prnt_id, "UTF-8") +
                        "&" + Const.Params.SelectedDate + "=" + URLEncoder.encode(back_date, "UTF-8");


                parentResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }

			/*JSONParser jsonParser = new JSONParser();

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("securityKey", Security));
			params.add(new BasicNameValuePair("authentication_token",
					child_token));
			params.add(new BasicNameValuePair("language", lang));
			params.add(new BasicNameValuePair("parent_id", prnt_id));
			params.add(new BasicNameValuePair("selected_date", back_date));

			// Log.d("lng1", language);

			JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

			Log.d("otheraccount......", json.toString());

			try {

				JSONObject jsonObj = new JSONObject(json.toString());

				Status = jsonObj.getString(TAG_STATUS);
				// ////////////

				if (Status.equalsIgnoreCase("true")) {

					// JSONArray Component =
					// jsonObj.optJSONArray(TAG_Component);
					//
					// name= new String[Component.length()];
					// name_sw = new String[Component.length()];
					// image_path = new String[Component.length()];
					// component_id= new String[Component.length()];
					//
					// for (int j = 0; j < Component.length(); j++) {
					// JSONObject d = Component.getJSONObject(j);
					//
					// name[j] = d.optString(TAG_name);
					// name_sw[j] = d.optString(TAG_name_sw);
					// image_path[j] = Base_url+d.optString(TAG_img_path);
					// component_id[j] = d.optString(TAG_id);
					//
					// }
					//
					// Log.i("component_id", Arrays.deepToString(component_id));

					JSONObject child_attedance = jsonObj
							.getJSONObject(TAG_child_attedance_info);

					Drop_off_time = child_attedance
							.getString(TAG_drop_off_time);
					Retrieval_time = child_attedance
							.getString(TAG_retrieval_time);
					Date = child_attedance.getString(TAG_date);
					ret_icon = child_attedance.getString(TAG_retriever_icon);
					absent_icon = child_attedance
							.getString(TAG_absent_note_icon);
					current_day = child_attedance.getString("current_day");

				} else {
					// Log.i("res", "false");
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
*/
            return parentResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {
                back_date = null;
                // Log.i("main_image_header", main_image_header);

                JSONObject jsonObject = new JSONObject();

                if (results != null && !results.isEmpty()) {
                    jsonObject = new JSONObject(results);
                    if (jsonObject.has(Const.Params.Status)) {
                        Status = jsonObject.getString(Const.Params.Status);
                    }

                }


                if (Status.equalsIgnoreCase("true")) {

                    JSONObject child_attedance = jsonObject
                            .getJSONObject(TAG_child_attedance_info);

                    Drop_off_time = child_attedance
                            .getString(TAG_drop_off_time);
                    Retrieval_time = child_attedance
                            .getString(TAG_retrieval_time);
                    Date = child_attedance.getString(TAG_date);
                    ret_icon = child_attedance.getString(TAG_retriever_icon);
                    absent_icon = child_attedance
                            .getString(TAG_absent_note_icon);
                    current_day = child_attedance.getString("current_day");

                    if (ret_icon.equalsIgnoreCase("true")) {
                        retriever_icon.setVisibility(View.VISIBLE);
                    } else {
                        retriever_icon.setVisibility(View.GONE);
                    }
                    if (absent_icon.equalsIgnoreCase("true")) {
                        absent_note_icon.setVisibility(View.VISIBLE);
                    } else {
                        absent_note_icon.setVisibility(View.GONE);
                    }
                    if (current_day.equalsIgnoreCase("sun")) {
                        // drp_off.setVisibility(View.GONE);
                        // Retrieval.setTextSize(18.0f);
                        // Rtrvl.setText("Sunday"); /////////////// sunday
                        // drop_off.setVisibility(View.GONE);
                        // Retrieval.setVisibility(View.GONE);
                        // drp_off.setVisibility(View.GONE);
                        // Rtrvl.setVisibility(View.GONE);
                        // Sundayshow.setText("Sunday");

                    } else {
                        drop_off.setText(Drop_off_time);
                        Retrieval.setText(Retrieval_time);
                    }

                    // gv.setAdapter(new Adapter_drawer(getActivity(), name,
                    // image_path));

                } else {

                    // failed to creat

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
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    // ////////////

    public class Adapter_drawer extends BaseAdapter {

        String[] result, nmsw;
        /*String[] id = new String[]{"28", "23", "1", "2", "3", "113"};*/
        String[] id;
        String[] notification;
        Activity context;
        String[] imageId;
        LayoutInflater inflater = null;
        int tiles = 0;
        String[] appStatus;
        View rowView;

        public Adapter_drawer(Activity activity, String[] component,
                              String[] nm_sw, String[] image,String[] id, int tiles, String[] app_status) {
            // TODO Auto-generated constructor stub



            this.result = component;
            this.context = activity;
            this.imageId = image;
            this.nmsw = nm_sw;
            this.id=id;
            this.tiles=tiles;
            this.appStatus=app_status;

            System.out.println("result"+result.length);
            // notification = counter;

            inflater = (LayoutInflater) (getActivity())
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return this.id.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public class Holder {
            TextView tv;
            ImageView imagView;
            TextView notf;
            RelativeLayout grid_relative_layout;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            // TODO Auto-generated method stub
            Holder holder = new Holder();


            // Log.i("c===+++=...", Arrays.deepToString(imageId));

            if(tiles > 10) {
                rowView = inflater.inflate(R.layout.category_grid_small_layout, null);
            }else{
                rowView = inflater.inflate(R.layout.category_grid_layout, null);
            }
            holder.tv = (TextView) rowView
                    .findViewById(R.id.txtForListCategory);
            holder.notf = (TextView) rowView
                    .findViewById(R.id.txtForListCategory1);
            holder.imagView = (ImageView) rowView
                    .findViewById(R.id.imgForListCategory);
            holder.grid_relative_layout = (RelativeLayout) rowView.findViewById(R.id.grid_relative_layout);

//            if(tiles > 10){
//                holder.grid_relative_layout.getLayoutParams().height = 300;
//                holder.grid_relative_layout.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
//                holder.notf.getLayoutParams().height = 35;
//                holder.notf.getLayoutParams().width = 35;
//                holder.tv.setTextSize(10);
//                holder.imagView.getLayoutParams().height = 100;
//                holder.imagView.getLayoutParams().width = 100;
//            }

            if (lang.equalsIgnoreCase("sw")) {
                holder.tv.setText(nmsw[position]);
            } else {
                holder.tv.setText(result[position]);
            }


            Log.d("imageId[position]", imageId[position]);

//            new ImageLoadTaskcliptwo(imageId[position], holder.imagView)
//                    .execute();

            Picasso.with(getActivity())
                    .load(imageId[position])
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.imagView);

            if (id[position].equalsIgnoreCase("28")) {
                if (eduBlog_count.equalsIgnoreCase("0")) {
                    holder.notf.setVisibility(View.GONE);
                } else {
                    if (eduBlog_count.equalsIgnoreCase(edu_count)) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        holder.notf.setText(eduBlog_count);
                    }
                }

                rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
            }
            if (id[position].equalsIgnoreCase("23")) {
                if (news_count.equalsIgnoreCase("0")) {
                    holder.notf.setVisibility(View.GONE);
                } else {
                    if (news_count.equalsIgnoreCase(new_count)) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        holder.notf.setText(news_count);
                    }
                }
                rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
            }
            if (id[position].equalsIgnoreCase("latest")) {
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#617CBF"));
            }
            if (id[position].equalsIgnoreCase("105")) {
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#617CBF"));
            }
            if (id[position].equalsIgnoreCase("1")) {
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
            }
            if (id[position].equalsIgnoreCase("2")) {
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
            }
            if (id[position].equalsIgnoreCase("3")) {
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#666666"));
            }

            if (id[position].equalsIgnoreCase("27")) {
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#617DBE"));
            }
            if (id[position].equalsIgnoreCase("63")) {
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#8863A9"));
            }
            if (id[position].equalsIgnoreCase("11")){
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
            }
            if (id[position].equalsIgnoreCase("78")){
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#8863a9"));
            }
            if (id[position].equalsIgnoreCase("100")){
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#617CBF"));
            }
            if (id[position].equalsIgnoreCase("87") || id[position].equalsIgnoreCase("87q")){
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#8863a9"));
            }
            if (id[position].equalsIgnoreCase("85")){
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
            }
            if (id[position].equalsIgnoreCase("73")){
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
            }
            if (id[position].equalsIgnoreCase("62")){
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
            }
            if (id[position].equalsIgnoreCase("81")){
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
            }
            if (id[position].equalsIgnoreCase("68")){
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
            }
            if (id[position].equalsIgnoreCase("50")){
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
            }
            if (id[position].equalsIgnoreCase("16")){
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
            }
            if (id[position].equalsIgnoreCase("103")){
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#8863a9"));
            }
            if (id[position].equalsIgnoreCase("10")){
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#2fbcd0"));
            }
            if (id[position].equalsIgnoreCase("47")){
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
            }
            if(id[position].equalsIgnoreCase("101")){
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#617DBE"));
            }
            if (id[position].equalsIgnoreCase("86")){
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
            }
            if (id[position].equalsIgnoreCase("83")){
                holder.notf.setVisibility(View.GONE);
                rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
            }

            if (position == 0) {

                if (id[position].equalsIgnoreCase("28")) {
                    if (eduBlog_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (eduBlog_count.equalsIgnoreCase(edu_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(eduBlog_count);
                        }
                    }

                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("23")) {
                    if (news_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (news_count.equalsIgnoreCase(new_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(news_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("1")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
                }
                if (id[position].equalsIgnoreCase("2")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("3")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#666666"));
                }

                if (id[position].equalsIgnoreCase("27")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("63")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                }
                if (id[position].equalsIgnoreCase("11")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("78")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("100")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }
                if (id[position].equalsIgnoreCase("87") || id[position].equalsIgnoreCase("87q")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("85")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("73")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                }
                if (id[position].equalsIgnoreCase("62")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("81")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("68")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("50")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("16")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("103")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("10")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2fbcd0"));
                }
                if (id[position].equalsIgnoreCase("47")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if(id[position].equalsIgnoreCase("101")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("86")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                // holder.notf.setVisibility(View.VISIBLE);

            }
            if (position == 1) {
                if (id[position].equalsIgnoreCase("28")) {
                    if (eduBlog_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (eduBlog_count.equalsIgnoreCase(edu_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(eduBlog_count);
                        }
                    }

                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("23")) {
                    if (news_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (news_count.equalsIgnoreCase(new_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(news_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("1")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
                }
                if (id[position].equalsIgnoreCase("2")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("3")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#666666"));
                }

                if (id[position].equalsIgnoreCase("27")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("63")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                }
                if (id[position].equalsIgnoreCase("11")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("78")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("100")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }
                if (id[position].equalsIgnoreCase("87") || id[position].equalsIgnoreCase("87q")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("85")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("73")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                }
                if (id[position].equalsIgnoreCase("62")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("81")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("68")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("50")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("16")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("103")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("10")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("47")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if(id[position].equalsIgnoreCase("101")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("86")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
            }

            if (position == 2) {

                if (id[position].equalsIgnoreCase("28")) {
                    if (eduBlog_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (eduBlog_count.equalsIgnoreCase(edu_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(eduBlog_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("23")) {
                    if (news_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (news_count.equalsIgnoreCase(new_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(news_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("1")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
                }
                if (id[position].equalsIgnoreCase("2")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("3")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#666666"));
                }

                if (id[position].equalsIgnoreCase("27")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("63")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                }
                if (id[position].equalsIgnoreCase("11")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("78")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("100")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }
                if (id[position].equalsIgnoreCase("87") || id[position].equalsIgnoreCase("87q")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("85")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("73")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                }
                if (id[position].equalsIgnoreCase("62")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("81")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("68")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("50")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("16")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("103")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("10")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("47")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if(id[position].equalsIgnoreCase("101")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("86")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
            }
            if (position == 3) {
                if (id[position].equalsIgnoreCase("28")) {
                    if (eduBlog_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (eduBlog_count.equalsIgnoreCase(edu_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(eduBlog_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("23")) {
                    if (news_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (news_count.equalsIgnoreCase(new_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(news_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("1")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
                }
                if (id[position].equalsIgnoreCase("2")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("3")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#666666"));
                }
                if (id[position].equalsIgnoreCase("27")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("63")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                }
                if (id[position].equalsIgnoreCase("11")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("78")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("100")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }
                if (id[position].equalsIgnoreCase("87") || id[position].equalsIgnoreCase("87q")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("85")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("73")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                }
                if (id[position].equalsIgnoreCase("62")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("81")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("68")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("50")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("16")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("103")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("10")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("47")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if(id[position].equalsIgnoreCase("101")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("86")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
            }
            if (position == 4) {
                if (id[position].equalsIgnoreCase("28")) {
                    if (eduBlog_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (eduBlog_count.equalsIgnoreCase(edu_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(eduBlog_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("23")) {
                    if (news_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (news_count.equalsIgnoreCase(new_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(news_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("1")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
                }
                if (id[position].equalsIgnoreCase("2")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("3")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#666666"));
                }
                if (id[position].equalsIgnoreCase("27")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("63")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                }
                if (id[position].equalsIgnoreCase("11")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("78")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("100")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }
                if (id[position].equalsIgnoreCase("87") || id[position].equalsIgnoreCase("87q")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("85")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("73")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                }
                if (id[position].equalsIgnoreCase("62")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("81")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("68")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("50")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("16")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("103")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("10")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("47")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if(id[position].equalsIgnoreCase("101")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("86")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
            } else if (position == 5) {
                if (id[position].equalsIgnoreCase("28")) {
                    if (eduBlog_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (eduBlog_count.equalsIgnoreCase(edu_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(eduBlog_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("23")) {
                    if (news_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (news_count.equalsIgnoreCase(new_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(news_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("1")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
                }
                if (id[position].equalsIgnoreCase("2")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("3")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#666666"));
                }
                if (id[position].equalsIgnoreCase("27")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("63")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                }
                if (id[position].equalsIgnoreCase("11")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("78")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("100")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }
                if (id[position].equalsIgnoreCase("87") || id[position].equalsIgnoreCase("87q")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("85")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("73")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                }
                if (id[position].equalsIgnoreCase("62")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("81")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("68")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("50")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("16")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("103")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("10")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("47")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if(id[position].equalsIgnoreCase("101")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("86")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }

            } else if (position == 6) {

                if (id[position].equalsIgnoreCase("28")) {
                    if (eduBlog_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (eduBlog_count.equalsIgnoreCase(edu_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(eduBlog_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("23")) {
                    if (news_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (news_count.equalsIgnoreCase(new_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(news_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("1")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
                }
                if (id[position].equalsIgnoreCase("2")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("3")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#666666"));
                }
                if (id[position].equalsIgnoreCase("27")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("63")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                }
                if (id[position].equalsIgnoreCase("11")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("78")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("100")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }
                if (id[position].equalsIgnoreCase("87") || id[position].equalsIgnoreCase("87q")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("85")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("73")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                }
                if (id[position].equalsIgnoreCase("62")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("81")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("68")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("50")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("16")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("103")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("10")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("47")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if(id[position].equalsIgnoreCase("101")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("86")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
            }
            else if (position == 7) {

                if (id[position].equalsIgnoreCase("28")) {
                    if (eduBlog_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (eduBlog_count.equalsIgnoreCase(edu_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(eduBlog_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("23")) {
                    if (news_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (news_count.equalsIgnoreCase(new_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(news_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("1")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
                }
                if (id[position].equalsIgnoreCase("2")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("3")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#666666"));
                }
                if (id[position].equalsIgnoreCase("27")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("63")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                }
                if (id[position].equalsIgnoreCase("11")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("78")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("100")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }
                if (id[position].equalsIgnoreCase("87") || id[position].equalsIgnoreCase("87q")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("85")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("73")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                }
                if (id[position].equalsIgnoreCase("62")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("81")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("68")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("50")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("16")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("103")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("10")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("47")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if(id[position].equalsIgnoreCase("101")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("86")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
            }
            else if (position == 8) {

                if (id[position].equalsIgnoreCase("28")) {
                    if (eduBlog_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (eduBlog_count.equalsIgnoreCase(edu_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(eduBlog_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("23")) {
                    if (news_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (news_count.equalsIgnoreCase(new_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(news_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("1")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
                }
                if (id[position].equalsIgnoreCase("2")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("3")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#666666"));
                }
                if (id[position].equalsIgnoreCase("27")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("63")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                }
                if (id[position].equalsIgnoreCase("11")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("78")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("100")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }
                if (id[position].equalsIgnoreCase("87") || id[position].equalsIgnoreCase("87q")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("85")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("73")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                }
                if (id[position].equalsIgnoreCase("62")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("81")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("68")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("50")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("16")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("103")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("10")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("47")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if(id[position].equalsIgnoreCase("101")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("86")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
            }
            else if (position == 9) {

                if (id[position].equalsIgnoreCase("28")) {
                    if (eduBlog_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (eduBlog_count.equalsIgnoreCase(edu_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(eduBlog_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("23")) {
                    if (news_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (news_count.equalsIgnoreCase(new_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(news_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("1")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
                }
                if (id[position].equalsIgnoreCase("2")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("3")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#666666"));
                }
                if (id[position].equalsIgnoreCase("27")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("63")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                }
                if (id[position].equalsIgnoreCase("11")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("78")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("100")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }
                if (id[position].equalsIgnoreCase("87") || id[position].equalsIgnoreCase("87q")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("85")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("73")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                }
                if (id[position].equalsIgnoreCase("62")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("81")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("68")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("50")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("16")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("103")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("10")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("47")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if(id[position].equalsIgnoreCase("101")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("86")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
            }
            else if (position == 10) {

                if (id[position].equalsIgnoreCase("28")) {
                    if (eduBlog_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (eduBlog_count.equalsIgnoreCase(edu_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(eduBlog_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("23")) {
                    if (news_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (news_count.equalsIgnoreCase(new_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(news_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("1")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
                }
                if (id[position].equalsIgnoreCase("2")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("3")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#666666"));
                }
                if (id[position].equalsIgnoreCase("27")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("63")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                }
                if (id[position].equalsIgnoreCase("11")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("78")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("100")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }
                if (id[position].equalsIgnoreCase("87") || id[position].equalsIgnoreCase("87q")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("85")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("73")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                }
                if (id[position].equalsIgnoreCase("62")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("81")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("68")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("50")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("16")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("103")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("10")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("47")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if(id[position].equalsIgnoreCase("101")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("86")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
            }
            else if (position == 11) {

                if (id[position].equalsIgnoreCase("28")) {
                    if (eduBlog_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (eduBlog_count.equalsIgnoreCase(edu_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(eduBlog_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("23")) {
                    if (news_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (news_count.equalsIgnoreCase(new_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(news_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("1")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
                }
                if (id[position].equalsIgnoreCase("2")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("3")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#666666"));
                }
                if (id[position].equalsIgnoreCase("27")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("63")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                }
                if (id[position].equalsIgnoreCase("11")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("78")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("100")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }
                if (id[position].equalsIgnoreCase("87") || id[position].equalsIgnoreCase("87q")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("85")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("73")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                }
                if (id[position].equalsIgnoreCase("62")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("81")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("68")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("50")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("16")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("103")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("10")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("47")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if(id[position].equalsIgnoreCase("101")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("86")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
            }
            else if (position == 12) {

                if (id[position].equalsIgnoreCase("28")) {
                    if (eduBlog_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (eduBlog_count.equalsIgnoreCase(edu_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(eduBlog_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("23")) {
                    if (news_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (news_count.equalsIgnoreCase(new_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(news_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("1")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
                }
                if (id[position].equalsIgnoreCase("2")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("3")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#666666"));
                }
                if (id[position].equalsIgnoreCase("27")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("63")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                }
                if (id[position].equalsIgnoreCase("11")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("78")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("100")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }
                if (id[position].equalsIgnoreCase("87") || id[position].equalsIgnoreCase("87q")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("85")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("73")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                }
                if (id[position].equalsIgnoreCase("62")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("81")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("68")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("50")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("16")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("103")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("10")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("47")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if(id[position].equalsIgnoreCase("101")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("86")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
            }
            else if (position == 13) {

                if (id[position].equalsIgnoreCase("28")) {
                    if (eduBlog_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (eduBlog_count.equalsIgnoreCase(edu_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(eduBlog_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("23")) {
                    if (news_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (news_count.equalsIgnoreCase(new_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(news_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("1")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
                }
                if (id[position].equalsIgnoreCase("2")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("3")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#666666"));
                }
                if (id[position].equalsIgnoreCase("27")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("63")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                }
                if (id[position].equalsIgnoreCase("11")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("78")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("100")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }
                if (id[position].equalsIgnoreCase("87") || id[position].equalsIgnoreCase("87q")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("85")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("73")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                }
                if (id[position].equalsIgnoreCase("62")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("81")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("68")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("50")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("16")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("103")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("10")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("47")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if(id[position].equalsIgnoreCase("101")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("86")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
            }
            else if (position == 14) {

                if (id[position].equalsIgnoreCase("28")) {
                    if (eduBlog_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (eduBlog_count.equalsIgnoreCase(edu_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(eduBlog_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("23")) {
                    if (news_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (news_count.equalsIgnoreCase(new_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(news_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("1")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
                }
                if (id[position].equalsIgnoreCase("2")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("3")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#666666"));
                }
                if (id[position].equalsIgnoreCase("27")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("63")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                }
                if (id[position].equalsIgnoreCase("11")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("78")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("100")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }
                if (id[position].equalsIgnoreCase("87") || id[position].equalsIgnoreCase("87q")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("85")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("73")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                }
                if (id[position].equalsIgnoreCase("62")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("81")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("68")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("50")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("16")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("103")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("10")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("47")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if(id[position].equalsIgnoreCase("101")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("86")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
            }
            else if (position == 15) {

                if (id[position].equalsIgnoreCase("28")) {
                    if (eduBlog_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (eduBlog_count.equalsIgnoreCase(edu_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(eduBlog_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("23")) {
                    if (news_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (news_count.equalsIgnoreCase(new_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(news_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("1")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
                }
                if (id[position].equalsIgnoreCase("2")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("3")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#666666"));
                }
                if (id[position].equalsIgnoreCase("27")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("63")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                }
                if (id[position].equalsIgnoreCase("11")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("78")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("100")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }
                if (id[position].equalsIgnoreCase("87") || id[position].equalsIgnoreCase("87q")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("85")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("73")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                }
                if (id[position].equalsIgnoreCase("62")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("81")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("68")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("50")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("16")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("103")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("10")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("47")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if(id[position].equalsIgnoreCase("101")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("86")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
            }
            else if (position == 16) {

                if (id[position].equalsIgnoreCase("28")) {
                    if (eduBlog_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (eduBlog_count.equalsIgnoreCase(edu_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(eduBlog_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("23")) {
                    if (news_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (news_count.equalsIgnoreCase(new_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(news_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("1")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
                }
                if (id[position].equalsIgnoreCase("2")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("3")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#666666"));
                }
                if (id[position].equalsIgnoreCase("27")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("63")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                }
                if (id[position].equalsIgnoreCase("11")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("78")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("100")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }
                if (id[position].equalsIgnoreCase("87") || id[position].equalsIgnoreCase("87q")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("85")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("73")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                }
                if (id[position].equalsIgnoreCase("62")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("81")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("68")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("50")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("16")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("103")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("10")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("47")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if(id[position].equalsIgnoreCase("101")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("86")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
            }
            else if (position == 17) {

                if (id[position].equalsIgnoreCase("28")) {
                    if (eduBlog_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (eduBlog_count.equalsIgnoreCase(edu_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(eduBlog_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("23")) {
                    if (news_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (news_count.equalsIgnoreCase(new_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(news_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("1")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
                }
                if (id[position].equalsIgnoreCase("2")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("3")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#666666"));
                }
                if (id[position].equalsIgnoreCase("27")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("63")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                }
                if (id[position].equalsIgnoreCase("11")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("78")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("100")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }
                if (id[position].equalsIgnoreCase("87") || id[position].equalsIgnoreCase("87q")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("85")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("73")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                }
                if (id[position].equalsIgnoreCase("62")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("81")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("68")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("50")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("16")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("103")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("10")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("47")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if(id[position].equalsIgnoreCase("101")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("86")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
            }
            else if (position == 18) {

                if (id[position].equalsIgnoreCase("28")) {
                    if (eduBlog_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (eduBlog_count.equalsIgnoreCase(edu_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(eduBlog_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("23")) {
                    if (news_count.equalsIgnoreCase("0")) {
                        holder.notf.setVisibility(View.GONE);
                    } else {
                        if (news_count.equalsIgnoreCase(new_count)) {
                            holder.notf.setVisibility(View.GONE);
                        } else {
                            holder.notf.setText(news_count);
                        }
                    }
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("1")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
                }
                if (id[position].equalsIgnoreCase("2")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
                }
                if (id[position].equalsIgnoreCase("3")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#666666"));
                }
                if (id[position].equalsIgnoreCase("27")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("63")) {
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863A9"));
                }
                if (id[position].equalsIgnoreCase("11")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("78")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("100")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617CBF"));
                }
                if (id[position].equalsIgnoreCase("87") || id[position].equalsIgnoreCase("87q")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("85")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("73")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#ec74a9"));
                }
                if (id[position].equalsIgnoreCase("62")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("81")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("68")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
                }
                if (id[position].equalsIgnoreCase("50")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
                }
                if (id[position].equalsIgnoreCase("16")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
                if (id[position].equalsIgnoreCase("103")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#8863a9"));
                }
                if (id[position].equalsIgnoreCase("10")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if (id[position].equalsIgnoreCase("47")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#5ABB54"));
                }
                if(id[position].equalsIgnoreCase("101")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#617DBE"));
                }
                if (id[position].equalsIgnoreCase("86")){
                    holder.notf.setVisibility(View.GONE);
                    rowView.setBackgroundColor(Color.parseColor("#f68b1f"));
                }
            }
            rowView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    final Bundle bundle = new Bundle();

                    if (id[position].equalsIgnoreCase("latest")){
//                            Toast.makeText(context,"Under Development",Toast.LENGTH_SHORT).show();

                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                FragmentManager fragmentManager = getFragmentManager();
                                WebFragment rFragment = new WebFragment();
                                bundle.putString("Component_Key", "smart_notes_parent");
                                bundle.putString("name_en", "Latest");
                                bundle.putString("name_sw", "Senaste");
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

                    if (id[position].equalsIgnoreCase("105")){
//                            Toast.makeText(context,"Under Development",Toast.LENGTH_SHORT).show();
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                FragmentManager fragmentManager = getFragmentManager();
                                WebFragment rFragment = new WebFragment();
                                bundle.putString("Component_Key", "location_details");
                                bundle.putString("name_en", "Locations");
                                bundle.putString("name_sw", "Platser");
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

                    if(id[position].equalsIgnoreCase("101")){
//                        FragmentManager fragmentManager = getFragmentManager();
//                        TodaysNoteFragment rFragment = new TodaysNoteFragment();
//                        FragmentTransaction ft = fragmentManager
//                                .beginTransaction();
//                        ft.replace(R.id.content_frame, rFragment);
//                        ft.commit();
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                FragmentManager fragmentManager = getFragmentManager();
                                WebFragment rFragment = new WebFragment();
                                bundle.putString("Component_Key", "todays_noteP");
                                bundle.putString("name_en", "Todays Note");
                                bundle.putString("name_sw", "Dagens Anteckning");
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
                    if (id[position].equalsIgnoreCase("11")){
//                            Toast.makeText(context,"Under Development",Toast.LENGTH_SHORT).show();
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                FragmentManager fragmentManager = getFragmentManager();
                                WebFragment rFragment = new WebFragment();
                                bundle.putString("Component_Key", "forum");
                                bundle.putString("name_en", "Forum");
                                bundle.putString("name_sw", "Forum");
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
                    if(id[position].equalsIgnoreCase("100")){

                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                FragmentManager fragmentManager = getFragmentManager();
                                WebFragment rFragment = new WebFragment();
                                bundle.putString("Component_Key", "edu_step_planner");
                                bundle.putString("name_en", "Edu steps");
                                bundle.putString("name_sw", "Lärstegsplaneraren");
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
                    if(id[position].equalsIgnoreCase("78")){
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                FragmentManager fragmentManager = getFragmentManager();
                                WebFragment rFragment = new WebFragment();
                                bundle.putString("Component_Key", "progresschart");
                                bundle.putString("name_en", "Progress Table");
                                bundle.putString("name_sw", "Utvecklingsschema");
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
                    if(id[position].equalsIgnoreCase("87")){
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                FragmentManager fragmentManager = getFragmentManager();
                                WebFragment rFragment = new WebFragment();
                                bundle.putString("Component_Key", "quiz_manager");
                                bundle.putString("name_en", "Quiz Manager");
                                bundle.putString("name_sw", "Quiz Manager");
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
                    if(id[position].equalsIgnoreCase("85")){
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                FragmentManager fragmentManager = getFragmentManager();
                                WebFragment rFragment = new WebFragment();
                                bundle.putString("Component_Key", "message_manager");
                                bundle.putString("name_en", "Message Manager");
                                bundle.putString("name_sw", "Message Manager");
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
                    if(id[position].equalsIgnoreCase("73")){
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                FragmentManager fragmentManager = getFragmentManager();
                                WebFragment rFragment = new WebFragment();
                                bundle.putString("Component_Key", "edublog_statistics");
                                bundle.putString("name_en", "Edublog Statistics");
                                bundle.putString("name_sw", "Edublog Statistik");
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
                    if(id[position].equalsIgnoreCase("62")){

                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                FragmentManager fragmentManager = getFragmentManager();
                                WebFragment rFragment = new WebFragment();
                                bundle.putString("Component_Key", "retrieval_statistics");
                                bundle.putString("name_en", "Retrieval Statistics");
                                bundle.putString("name_sw", "Närvaro Statistik");
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
                    if(id[position].equalsIgnoreCase("81")){
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                FragmentManager fragmentManager = getFragmentManager();
                                WebFragment rFragment = new WebFragment();
                                bundle.putString("Component_Key", "contact_information");
                                bundle.putString("name_en", "Contact Information");
                                bundle.putString("name_sw", "Kontakt Information");
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
                    if(id[position].equalsIgnoreCase("68")){
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                FragmentManager fragmentManager = getFragmentManager();
                                WebFragment rFragment = new WebFragment();
                                bundle.putString("Component_Key", "school_information");
                                bundle.putString("name_en", "School Information");
                                bundle.putString("name_sw", "Skolinformation");
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
                    if(id[position].equalsIgnoreCase("50")){
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                FragmentManager fragmentManager = getFragmentManager();
                                WebFragment rFragment = new WebFragment();
                                bundle.putString("Component_Key", "survey_forms_students");
                                bundle.putString("name_en", "Survey Forms");
                                bundle.putString("name_sw", "Enkätutskick");
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
                    if(id[position].equalsIgnoreCase("10")){
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                FragmentManager fragmentManager = getFragmentManager();
                                WebFragment rFragment = new WebFragment();
                                bundle.putString("Component_Key", "examination");
                                bundle.putString("name_en", "Examinations");
                                bundle.putString("name_sw", "Prov");
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
                    if(id[position].equalsIgnoreCase("47")){
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                FragmentManager fragmentManager = getFragmentManager();
                                WebFragment rFragment = new WebFragment();
                                bundle.putString("Component_Key", "course_videos");
                                bundle.putString("name_en", "Course Videos");
                                bundle.putString("name_sw", "Kursen Videor");
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
                    if(id[position].equalsIgnoreCase("87q")){
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                FragmentManager fragmentManager = getFragmentManager();
                                WebFragment rFragment = new WebFragment();
                                bundle.putString("Component_Key", "quiz_editor");
                                bundle.putString("name_en", "Quiz Editor");
                                bundle.putString("name_sw", "Quiz Editeraren");
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
                    if(id[position].equalsIgnoreCase("86")){
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                FragmentManager fragmentManager = getFragmentManager();
                                WebFragment rFragment = new WebFragment();
                                bundle.putString("Component_Key", "recovery_notice");
                                bundle.putString("name_en", "Recovery Notice");
                                bundle.putString("name_sw", "Friskanmälan");
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
                    if (id[position].equalsIgnoreCase("28")) {
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                Log.d("LMS", "status of edublog component is --> " + appStatus[position]);
                                if (appStatus[position].equalsIgnoreCase("0")) {
                                    session.Educount(eduBlog_count);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    MainActivity rFragment = new MainActivity();
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                } else {
                                    session.Educount(eduBlog_count);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "edublog");
                                    bundle.putString("name_en", "Edu Blog");
                                    bundle.putString("name_sw", "Läroblogg");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (id[position].equalsIgnoreCase("23")) {

                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                if (lang.equalsIgnoreCase("sw")) {
                                    ((Drawer) getActivity())
                                            .setActionBarTitle("Nyheter");
                                }
                                if (lang.equalsIgnoreCase("en")) {

                                    ((Drawer) getActivity())
                                            .setActionBarTitle("News");
                                }

                                if (appStatus[position].equalsIgnoreCase("0")) {
                                    ((Drawer) getActivity()).setBackForChildEdu();
                                    session.Newscount(news_count);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    News_Post rFragment = new News_Post();
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                } else {
                                    ((Drawer) getActivity()).setBackForChildEdu();
                                    session.Newscount(news_count);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "news");
                                    bundle.putString("name_en", "News");
                                    bundle.putString("name_sw", "Nyheter");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (id[position].equalsIgnoreCase("1")) {

                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                                if (lang.equalsIgnoreCase("sw")) {

                                    ((Drawer) getActivity())
                                            .setActionBarTitle("Absence note");
                                }
                                if (lang.equalsIgnoreCase("en")) {

                                    ((Drawer) getActivity())
                                            .setActionBarTitle("Absence note");
                                }

                                ((Drawer) getActivity()).setBackForChildEdu();

    //                        FragmentManager fragmentManager = getFragmentManager();
    //                        AddAbsentNotesFragment rFragment = new AddAbsentNotesFragment();
    //                        FragmentTransaction ft = fragmentManager
    //                                .beginTransaction();
    //                        ft.replace(R.id.content_frame, rFragment);
    //                        ft.commit();

                                FragmentManager fragmentManager = getFragmentManager();
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
                    if (id[position].equalsIgnoreCase("2")) {

                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                                if (lang.equalsIgnoreCase("sw")) {
                                    ((Drawer) getActivity())
                                            .setActionBarTitle("Nyheter");
                                }
                                if (lang.equalsIgnoreCase("en")) {

                                    ((Drawer) getActivity())
                                            .setActionBarTitle("News");
                                }

                                ((Drawer) getActivity()).setBackForChildEdu();

                                FragmentManager fragmentManager = getFragmentManager();
                                AddretrivalNotes rFragment = new AddretrivalNotes();
                                FragmentTransaction ft = fragmentManager
                                        .beginTransaction();
                                ft.replace(R.id.content_frame, rFragment);
                                ft.commit();
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (id[position].equalsIgnoreCase("3")) {

                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                if (lang.equalsIgnoreCase("sw")) {
                                    ((Drawer) getActivity())
                                            .setActionBarTitle("Child Info");
                                }
                                if (lang.equalsIgnoreCase("en")) {

                                    ((Drawer) getActivity())
                                            .setActionBarTitle("Child Info");
                                }
                                ((Drawer) getActivity()).setBackForChildEdu();

                                FragmentManager fragmentManager = getFragmentManager();

                                /* Creating fragment instance */
                                Child_Info rFragment = new Child_Info();
                                /* Passing selected item information to fragment */
                                /* Replace fragment */
                                FragmentTransaction ft = fragmentManager
                                        .beginTransaction();
                                ft.replace(R.id.content_frame, rFragment);
                                ft.commit();
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (id[position].equalsIgnoreCase("27")) {

                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                if (lang.equalsIgnoreCase("sw")) {
                                    /*((Drawer) getActivity())
                                            .setActionBarTitle("Child Info");*/
                                }
                                if (lang.equalsIgnoreCase("en")) {

                                    /*((Drawer) getActivity())
                                            .setActionBarTitle("Child Info");*/
                                }
                                ((Drawer) getActivity()).setBackForChildEdu();

                               /* FragmentManager fragmentManager = getFragmentManager();

                                ScheduleFragment rFragment = new ScheduleFragment();

                                FragmentTransaction ft = fragmentManager
                                        .beginTransaction();
                                ft.replace(R.id.content_frame, rFragment);
                                ft.commit();*/
                                Intent schedeluIntent = new Intent(getActivity(), AsynchronousActivity.class);
                                schedeluIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                getActivity().startActivity(schedeluIntent);
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (id[position].equalsIgnoreCase("63")) {
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                Intent foodMenuIntent = new Intent(getActivity(), AddFoodMenuActivity.class);
                                startActivity(foodMenuIntent);
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                    if(id[position].equalsIgnoreCase("83")){
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                FragmentManager fragmentManager = getFragmentManager();
                                WebFragment rFragment = new WebFragment();
                                bundle.putString("Component_Key", "week_list");
                                bundle.putString("name_en", "Weekly Retrieval");
                                bundle.putString("name_sw", "Veckans Hämtning");
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
                    if (position == 0) {

                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                            if (id[position].equalsIgnoreCase("28")) {
                                Log.d("LMS", "status of edublog component is --> " + appStatus[position]);
                                if (appStatus[position].equalsIgnoreCase("0")) {
                                    session.Educount(eduBlog_count);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    MainActivity rFragment = new MainActivity();
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                } else {
                                    session.Educount(eduBlog_count);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "edublog");
                                    bundle.putString("name_en", "Edu Blog");
                                    bundle.putString("name_sw", "Läroblogg");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            }
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        if (id[position].equalsIgnoreCase("23")) {

                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Nyheter");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("News");
                                    }

                                    if (appStatus[position].equalsIgnoreCase("0")) {
                                        ((Drawer) getActivity()).setBackForChildEdu();
                                        session.Newscount(news_count);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        News_Post rFragment = new News_Post();
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    } else {
                                        ((Drawer) getActivity()).setBackForChildEdu();
                                        session.Newscount(news_count);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        WebFragment rFragment = new WebFragment();
                                        bundle.putString("Component_Key", "news");
                                        bundle.putString("name_en", "News");
                                        bundle.putString("name_sw", "Nyheter");
                                        rFragment.setArguments(bundle);
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    }

                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("1")) {

                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                                    if (lang.equalsIgnoreCase("sw")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Absence note");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Absence note");
                                    }

                                    ((Drawer) getActivity()).setBackForChildEdu();

    //                            FragmentManager fragmentManager = getFragmentManager();
    //                            AddAbsentNotesFragment rFragment = new AddAbsentNotesFragment();
    //                            FragmentTransaction ft = fragmentManager
    //                                    .beginTransaction();
    //                            ft.replace(R.id.content_frame, rFragment);
    //                            ft.commit();

                                    FragmentManager fragmentManager = getFragmentManager();
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
                        if (id[position].equalsIgnoreCase("2")) {

                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Nyheter");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("News");
                                    }

                                    ((Drawer) getActivity()).setBackForChildEdu();

                                    FragmentManager fragmentManager = getFragmentManager();
                                    AddretrivalNotes rFragment = new AddretrivalNotes();
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("3")) {

                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Child Info");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Child Info");
                                    }
                                    ((Drawer) getActivity()).setBackForChildEdu();

                                    FragmentManager fragmentManager = getFragmentManager();

                                /* Creating fragment instance */
                                    Child_Info rFragment = new Child_Info();
                                /* Passing selected item information to fragment */
                                /* Replace fragment */
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("27")) {

                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                    /*((Drawer) getActivity())
                                            .setActionBarTitle("Child Info");*/
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                    /*((Drawer) getActivity())
                                            .setActionBarTitle("Child Info");*/
                                    }
                                    ((Drawer) getActivity()).setBackForChildEdu();

                               /* FragmentManager fragmentManager = getFragmentManager();

                                ScheduleFragment rFragment = new ScheduleFragment();

                                FragmentTransaction ft = fragmentManager
                                        .beginTransaction();
                                ft.replace(R.id.content_frame, rFragment);
                                ft.commit();*/
                                    Intent schedeluIntent = new Intent(getActivity(), AsynchronousActivity.class);
                                    schedeluIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    getActivity().startActivity(schedeluIntent);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("63")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    Intent foodMenuIntent = new Intent(getActivity(), AddFoodMenuActivity.class);
                                    startActivity(foodMenuIntent);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                    if (position == 1) {
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                            if (id[position].equalsIgnoreCase("28")) {
                                Log.d("LMS", "status of edublog component is --> " + appStatus[position]);
                                if (appStatus[position].equalsIgnoreCase("0")) {
                                    session.Educount(eduBlog_count);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    MainActivity rFragment = new MainActivity();
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                } else {
                                    session.Educount(eduBlog_count);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "edublog");
                                    bundle.putString("name_en", "Edu Blog");
                                    bundle.putString("name_sw", "Läroblogg");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            }
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        if (id[position].equalsIgnoreCase("23")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Nyheter");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("News");
                                    }

                                    if (appStatus[position].equalsIgnoreCase("0")) {
                                        ((Drawer) getActivity()).setBackForChildEdu();
                                        session.Newscount(news_count);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        News_Post rFragment = new News_Post();
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    } else {
                                        ((Drawer) getActivity()).setBackForChildEdu();
                                        session.Newscount(news_count);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        WebFragment rFragment = new WebFragment();
                                        bundle.putString("Component_Key", "news");
                                        bundle.putString("name_en", "News");
                                        bundle.putString("name_sw", "Nyheter");
                                        rFragment.setArguments(bundle);
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    }
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("1")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Absence note");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Absence note");
                                    }

                                    ((Drawer) getActivity()).setBackForChildEdu();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("2")) {

                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Nyheter");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("News");
                                    }

                                    ((Drawer) getActivity()).setBackForChildEdu();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    AddretrivalNotes rFragment = new AddretrivalNotes();
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("3")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Child Info");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Child Info");
                                    }
                                    ((Drawer) getActivity()).setBackForChildEdu();

                                    FragmentManager fragmentManager = getFragmentManager();

                                /* Creating fragment instance */
                                    Child_Info rFragment = new Child_Info();
                                /* Passing selected item information to fragment */
                                /* Replace fragment */
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("27")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                    /*((Drawer) getActivity())
                                            .setActionBarTitle("Child Info");*/
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                    /*((Drawer) getActivity())
                                            .setActionBarTitle("Child Info");*/
                                    }
                                    ((Drawer) getActivity()).setBackForChildEdu();

                               /* FragmentManager fragmentManager = getFragmentManager();

                                ScheduleFragment rFragment = new ScheduleFragment();

                                FragmentTransaction ft = fragmentManager
                                        .beginTransaction();
                                ft.replace(R.id.content_frame, rFragment);
                                ft.commit();*/
                                    Intent schedeluIntent = new Intent(getActivity(), AsynchronousActivity.class);
                                    schedeluIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    getActivity().startActivity(schedeluIntent);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }

                        if (id[position].equalsIgnoreCase("63")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    Intent foodMenuIntent = new Intent(getActivity(), AddFoodMenuActivity.class);
                                    startActivity(foodMenuIntent);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (position == 2) {
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                            if (id[position].equalsIgnoreCase("28")) {
                                Log.d("LMS", "status of edublog component is --> " + appStatus[position]);
                                if (appStatus[position].equalsIgnoreCase("0")) {
                                    session.Educount(eduBlog_count);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    MainActivity rFragment = new MainActivity();
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                } else {
                                    session.Educount(eduBlog_count);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "edublog");
                                    bundle.putString("name_en", "Edu Blog");
                                    bundle.putString("name_sw", "Läroblogg");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            }
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        if (id[position].equalsIgnoreCase("23")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Nyheter");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("News");
                                    }

                                    if (appStatus[position].equalsIgnoreCase("0")) {
                                        ((Drawer) getActivity()).setBackForChildEdu();
                                        session.Newscount(news_count);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        News_Post rFragment = new News_Post();
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    } else {
                                        ((Drawer) getActivity()).setBackForChildEdu();
                                        session.Newscount(news_count);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        WebFragment rFragment = new WebFragment();
                                        bundle.putString("Component_Key", "news");
                                        bundle.putString("name_en", "News");
                                        bundle.putString("name_sw", "Nyheter");
                                        rFragment.setArguments(bundle);
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    }
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("1")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                                    if (lang.equalsIgnoreCase("sw")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Absence note");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Absence note");
                                    }

                                    ((Drawer) getActivity()).setBackForChildEdu();
    //                            FragmentManager fragmentManager = getFragmentManager();
    //                            AddAbsentNotesFragment rFragment = new AddAbsentNotesFragment();
    //                            FragmentTransaction ft = fragmentManager
    //                                    .beginTransaction();
    //                            ft.replace(R.id.content_frame, rFragment);
    //                            ft.commit();

                                    FragmentManager fragmentManager = getFragmentManager();
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
                        if (id[position].equalsIgnoreCase("2")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Nyheter");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("News");
                                    }

                                    ((Drawer) getActivity()).setBackForChildEdu();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    AddretrivalNotes rFragment = new AddretrivalNotes();
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("3")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Child Info");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Child Info");
                                    }
                                    ((Drawer) getActivity()).setBackForChildEdu();

                                    FragmentManager fragmentManager = getFragmentManager();

                                /* Creating fragment instance */
                                    Child_Info rFragment = new Child_Info();
                                /* Passing selected item information to fragment */
                                /* Replace fragment */
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("27")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                    /*((Drawer) getActivity())
                                            .setActionBarTitle("Child Info");*/
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                    /*((Drawer) getActivity())
                                            .setActionBarTitle("Child Info");*/
                                    }
                                    ((Drawer) getActivity()).setBackForChildEdu();

                              /*  FragmentManager fragmentManager = getFragmentManager();

                                ScheduleFragment rFragment = new ScheduleFragment();

                                FragmentTransaction ft = fragmentManager
                                        .beginTransaction();
                                ft.replace(R.id.content_frame, rFragment);
                                ft.commit();*/
                                    Intent schedeluIntent = new Intent(getActivity(), AsynchronousActivity.class);
                                    schedeluIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    getActivity().startActivity(schedeluIntent);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }

                        if (id[position].equalsIgnoreCase("63")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    Intent foodMenuIntent = new Intent(getActivity(), AddFoodMenuActivity.class);
                                    startActivity(foodMenuIntent);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (position == 3) {
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                            if (id[position].equalsIgnoreCase("28")) {
                                Log.d("LMS", "status of edublog component is --> " + appStatus[position]);
                                if (appStatus[position].equalsIgnoreCase("0")) {
                                    session.Educount(eduBlog_count);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    MainActivity rFragment = new MainActivity();
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                } else {
                                    session.Educount(eduBlog_count);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "edublog");
                                    bundle.putString("name_en", "Edu Blog");
                                    bundle.putString("name_sw", "Läroblogg");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            }
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        if (id[position].equalsIgnoreCase("23")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Nyheter");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("News");
                                    }

                                    if (appStatus[position].equalsIgnoreCase("0")) {
                                        ((Drawer) getActivity()).setBackForChildEdu();
                                        session.Newscount(news_count);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        News_Post rFragment = new News_Post();
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    } else {
                                        ((Drawer) getActivity()).setBackForChildEdu();
                                        session.Newscount(news_count);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        WebFragment rFragment = new WebFragment();
                                        bundle.putString("Component_Key", "news");
                                        bundle.putString("name_en", "News");
                                        bundle.putString("name_sw", "Nyheter");
                                        rFragment.setArguments(bundle);
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    }
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("1")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Absence note");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Absence note");
                                    }

                                    ((Drawer) getActivity()).setBackForChildEdu();
    //                            FragmentManager fragmentManager = getFragmentManager();
    //                            AddAbsentNotesFragment rFragment = new AddAbsentNotesFragment();
    //                            FragmentTransaction ft = fragmentManager
    //                                    .beginTransaction();
    //                            ft.replace(R.id.content_frame, rFragment);
    //                            ft.commit();

                                    FragmentManager fragmentManager = getFragmentManager();
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
                        if (id[position].equalsIgnoreCase("2")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Nyheter");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("News");
                                    }

                                    ((Drawer) getActivity()).setBackForChildEdu();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    AddretrivalNotes rFragment = new AddretrivalNotes();
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    Bundle bndle = new Bundle();
                                    bundle.putString("keyForStudentId", "473");
                                    bundle.putString("keyForStudentName", "aaa" + " "
                                            + "bbb");
                                    rFragment.setArguments(bndle);
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("3")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Child Info");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Child Info");
                                    }
                                    ((Drawer) getActivity()).setBackForChildEdu();

                                    FragmentManager fragmentManager = getFragmentManager();

                                /* Creating fragment instance */
                                    Child_Info rFragment = new Child_Info();
                                /* Passing selected item information to fragment */
                                /* Replace fragment */
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("27")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {

                                    /*((Drawer) getActivity())
                                            .setActionBarTitle("Child Info");*/
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                    /*((Drawer) getActivity())
                                            .setActionBarTitle("Child Info");*/
                                    }
                                    ((Drawer) getActivity()).setBackForChildEdu();

                              /*  FragmentManager fragmentManager = getFragmentManager();

                                ScheduleFragment rFragment = new ScheduleFragment();

                                FragmentTransaction ft = fragmentManager
                                        .beginTransaction();
                                ft.replace(R.id.content_frame, rFragment);
                                ft.commit();*/
                                    Intent schedeluIntent = new Intent(getActivity(), AsynchronousActivity.class);
                                    schedeluIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    getActivity().startActivity(schedeluIntent);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("63")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    Intent foodMenuIntent = new Intent(getActivity(), AddFoodMenuActivity.class);
                                    startActivity(foodMenuIntent);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (position == 4) {
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                            if (id[position].equalsIgnoreCase("28")) {
                                Log.d("LMS", "status of edublog component is --> " + appStatus[position]);
                                if (appStatus[position].equalsIgnoreCase("0")) {
                                    session.Educount(eduBlog_count);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    MainActivity rFragment = new MainActivity();
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                } else {
                                    session.Educount(eduBlog_count);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "edublog");
                                    bundle.putString("name_en", "Edu Blog");
                                    bundle.putString("name_sw", "Läroblogg");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            }
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        if (id[position].equalsIgnoreCase("23")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Nyheter");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("News");
                                    }

                                    if (appStatus[position].equalsIgnoreCase("0")) {
                                        ((Drawer) getActivity()).setBackForChildEdu();
                                        session.Newscount(news_count);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        News_Post rFragment = new News_Post();
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    } else {
                                        ((Drawer) getActivity()).setBackForChildEdu();
                                        session.Newscount(news_count);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        WebFragment rFragment = new WebFragment();
                                        bundle.putString("Component_Key", "news");
                                        bundle.putString("name_en", "News");
                                        bundle.putString("name_sw", "Nyheter");
                                        rFragment.setArguments(bundle);
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    }
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("1")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Absence note");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Absence note");
                                    }

                                    ((Drawer) getActivity()).setBackForChildEdu();
    //                            FragmentManager fragmentManager = getFragmentManager();
    //                            AddAbsentNotesFragment rFragment = new AddAbsentNotesFragment();
    //                            FragmentTransaction ft = fragmentManager
    //                                    .beginTransaction();
    //                            ft.replace(R.id.content_frame, rFragment);
    //                            ft.commit();

                                    FragmentManager fragmentManager = getFragmentManager();
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
                        if (id[position].equalsIgnoreCase("2")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Nyheter");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("News");
                                    }

                                    ((Drawer) getActivity()).setBackForChildEdu();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    AddretrivalNotes rFragment = new AddretrivalNotes();
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("3")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Child Info");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Child Info");
                                    }
                                    ((Drawer) getActivity()).setBackForChildEdu();

                                    FragmentManager fragmentManager = getFragmentManager();

                                /* Creating fragment instance */
                                    Child_Info rFragment = new Child_Info();
                                /* Passing selected item information to fragment */
                                /* Replace fragment */
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("27")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                    /*((Drawer) getActivity())
                                            .setActionBarTitle("Child Info");*/
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                    /*((Drawer) getActivity())
                                            .setActionBarTitle("Child Info");*/
                                    }
                                    ((Drawer) getActivity()).setBackForChildEdu();

                               /* FragmentManager fragmentManager = getFragmentManager();

                                ScheduleFragment rFragment = new ScheduleFragment();

                                FragmentTransaction ft = fragmentManager
                                        .beginTransaction();
                                ft.replace(R.id.content_frame, rFragment);
                                ft.commit();*/
                                    Intent schedeluIntent = new Intent(getActivity(), AsynchronousActivity.class);
                                    schedeluIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    getActivity().startActivity(schedeluIntent);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("63")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    Intent foodMenuIntent = new Intent(getActivity(), AddFoodMenuActivity.class);
                                    startActivity(foodMenuIntent);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (position == 5) {
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                            if (id[position].equalsIgnoreCase("28")) {
                                Log.d("LMS", "status of edublog component is --> " + appStatus[position]);
                                if (appStatus[position].equalsIgnoreCase("0")) {
                                    session.Educount(eduBlog_count);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    MainActivity rFragment = new MainActivity();
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                } else {
                                    session.Educount(eduBlog_count);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "edublog");
                                    bundle.putString("name_en", "Edu Blog");
                                    bundle.putString("name_sw", "Läroblogg");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            }
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        if (id[position].equalsIgnoreCase("23")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Nyheter");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("News");
                                    }

                                    if (appStatus[position].equalsIgnoreCase("0")) {
                                        ((Drawer) getActivity()).setBackForChildEdu();
                                        session.Newscount(news_count);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        News_Post rFragment = new News_Post();
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    } else {
                                        ((Drawer) getActivity()).setBackForChildEdu();
                                        session.Newscount(news_count);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        WebFragment rFragment = new WebFragment();
                                        bundle.putString("Component_Key", "news");
                                        bundle.putString("name_en", "News");
                                        bundle.putString("name_sw", "Nyheter");
                                        rFragment.setArguments(bundle);
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    }
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("1")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Absence note");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Absence note");
                                    }

                                    ((Drawer) getActivity()).setBackForChildEdu();
    //                            FragmentManager fragmentManager = getFragmentManager();
    //                            AddAbsentNotesFragment rFragment = new AddAbsentNotesFragment();
    //                            FragmentTransaction ft = fragmentManager
    //                                    .beginTransaction();
    //                            ft.replace(R.id.content_frame, rFragment);
    //                            ft.commit();

                                    FragmentManager fragmentManager = getFragmentManager();
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
                        if (id[position].equalsIgnoreCase("2")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Nyheter");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("News");
                                    }

                                    ((Drawer) getActivity()).setBackForChildEdu();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    AddretrivalNotes rFragment = new AddretrivalNotes();
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("3")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Child Info");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Child Info");
                                    }
                                    ((Drawer) getActivity()).setBackForChildEdu();

                                    FragmentManager fragmentManager = getFragmentManager();

                                /* Creating fragment instance */
                                    Child_Info rFragment = new Child_Info();
                                /* Passing selected item information to fragment */
                                /* Replace fragment */
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("27")) {

                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                    /*((Drawer) getActivity())
                                            .setActionBarTitle("Child Info");*/
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                    /*((Drawer) getActivity())
                                            .setActionBarTitle("Child Info");*/
                                    }
                                    ((Drawer) getActivity()).setBackForChildEdu();

                               /* FragmentManager fragmentManager = getFragmentManager();

                                ScheduleFragment rFragment = new ScheduleFragment();

                                FragmentTransaction ft = fragmentManager
                                        .beginTransaction();
                                ft.replace(R.id.content_frame, rFragment);
                                ft.commit();*/
                                    Intent schedeluIntent = new Intent(getActivity(), AsynchronousActivity.class);
                                    schedeluIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    getActivity().startActivity(schedeluIntent);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("63")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    Intent foodMenuIntent = new Intent(getActivity(), AddFoodMenuActivity.class);
                                    startActivity(foodMenuIntent);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (position == 6) {
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                            if (id[position].equalsIgnoreCase("28")) {
                                Log.d("LMS", "status of edublog component is --> " + appStatus[position]);
                                if (appStatus[position].equalsIgnoreCase("0")) {
                                    session.Educount(eduBlog_count);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    MainActivity rFragment = new MainActivity();
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                } else {
                                    session.Educount(eduBlog_count);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "edublog");
                                    bundle.putString("name_en", "Edu Blog");
                                    bundle.putString("name_sw", "Läroblogg");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            }
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        if (id[position].equalsIgnoreCase("23")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Nyheter");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("News");
                                    }

                                    if (appStatus[position].equalsIgnoreCase("0")) {
                                        ((Drawer) getActivity()).setBackForChildEdu();
                                        session.Newscount(news_count);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        News_Post rFragment = new News_Post();
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    } else {
                                        ((Drawer) getActivity()).setBackForChildEdu();
                                        session.Newscount(news_count);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        WebFragment rFragment = new WebFragment();
                                        bundle.putString("Component_Key", "news");
                                        bundle.putString("name_en", "News");
                                        bundle.putString("name_sw", "Nyheter");
                                        rFragment.setArguments(bundle);
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    }
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("1")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Absence note");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Absence note");
                                    }

                                    ((Drawer) getActivity()).setBackForChildEdu();
    //                            FragmentManager fragmentManager = getFragmentManager();
    //                            AddAbsentNotesFragment rFragment = new AddAbsentNotesFragment();
    //                            FragmentTransaction ft = fragmentManager
    //                                    .beginTransaction();
    //                            ft.replace(R.id.content_frame, rFragment);
    //                            ft.commit();

                                    FragmentManager fragmentManager = getFragmentManager();
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
                        if (id[position].equalsIgnoreCase("2")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Nyheter");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("News");
                                    }

                                    ((Drawer) getActivity()).setBackForChildEdu();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    AddretrivalNotes rFragment = new AddretrivalNotes();
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("3")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {


                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Child Info");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Child Info");
                                    }
                                    ((Drawer) getActivity()).setBackForChildEdu();

                                    FragmentManager fragmentManager = getFragmentManager();

                                /* Creating fragment instance */
                                    Child_Info rFragment = new Child_Info();
                                /* Passing selected item information to fragment */
                                /* Replace fragment */
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("27")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                    /*((Drawer) getActivity())
                                            .setActionBarTitle("Child Info");*/
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                    /*((Drawer) getActivity())
                                            .setActionBarTitle("Child Info");*/
                                    }
                                    ((Drawer) getActivity()).setBackForChildEdu();

                               /* FragmentManager fragmentManager = getFragmentManager();

                                ScheduleFragment rFragment = new ScheduleFragment();

                                FragmentTransaction ft = fragmentManager
                                        .beginTransaction();
                                ft.replace(R.id.content_frame, rFragment);
                                ft.commit();*/
                                    Intent schedeluIntent = new Intent(getActivity(), AsynchronousActivity.class);
                                    schedeluIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    getActivity().startActivity(schedeluIntent);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("63")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    Intent foodMenuIntent = new Intent(getActivity(), AddFoodMenuActivity.class);
                                    startActivity(foodMenuIntent);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (position == 7) {
                        try {
                            if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                            if (id[position].equalsIgnoreCase("28")) {
                                Log.d("LMS", "status of edublog component is --> " + appStatus[position]);
                                if (appStatus[position].equalsIgnoreCase("0")) {
                                    session.Educount(eduBlog_count);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    MainActivity rFragment = new MainActivity();
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                } else {
                                    session.Educount(eduBlog_count);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    WebFragment rFragment = new WebFragment();
                                    bundle.putString("Component_Key", "edublog");
                                    bundle.putString("name_en", "Edu Blog");
                                    bundle.putString("name_sw", "Läroblogg");
                                    rFragment.setArguments(bundle);
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            }
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        if (id[position].equalsIgnoreCase("23")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Nyheter");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("News");
                                    }

                                    if (appStatus[position].equalsIgnoreCase("0")) {
                                        ((Drawer) getActivity()).setBackForChildEdu();
                                        session.Newscount(news_count);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        News_Post rFragment = new News_Post();
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    } else {
                                        ((Drawer) getActivity()).setBackForChildEdu();
                                        session.Newscount(news_count);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        WebFragment rFragment = new WebFragment();
                                        bundle.putString("Component_Key", "news");
                                        bundle.putString("name_en", "News");
                                        bundle.putString("name_sw", "Nyheter");
                                        rFragment.setArguments(bundle);
                                        FragmentTransaction ft = fragmentManager
                                                .beginTransaction();
                                        ft.replace(R.id.content_frame, rFragment);
                                        ft.commit();
                                    }
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("1")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Absence note");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Absence note");
                                    }

                                    ((Drawer) getActivity()).setBackForChildEdu();
    //                            FragmentManager fragmentManager = getFragmentManager();
    //                            AddAbsentNotesFragment rFragment = new AddAbsentNotesFragment();
    //                            FragmentTransaction ft = fragmentManager
    //                                    .beginTransaction();
    //                            ft.replace(R.id.content_frame, rFragment);
    //                            ft.commit();

                                    FragmentManager fragmentManager = getFragmentManager();
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
                        if (id[position].equalsIgnoreCase("2")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Nyheter");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("News");
                                    }

                                    ((Drawer) getActivity()).setBackForChildEdu();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    AddretrivalNotes rFragment = new AddretrivalNotes();
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("3")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                                    if (lang.equalsIgnoreCase("sw")) {
                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Child Info");
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                        ((Drawer) getActivity())
                                                .setActionBarTitle("Child Info");
                                    }
                                    ((Drawer) getActivity()).setBackForChildEdu();

                                    FragmentManager fragmentManager = getFragmentManager();

                                /* Creating fragment instance */
                                    Child_Info rFragment = new Child_Info();
                                /* Passing selected item information to fragment */
                                /* Replace fragment */
                                    FragmentTransaction ft = fragmentManager
                                            .beginTransaction();
                                    ft.replace(R.id.content_frame, rFragment);
                                    ft.commit();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("27")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    if (lang.equalsIgnoreCase("sw")) {
                                    /*((Drawer) getActivity())
                                            .setActionBarTitle("Child Info");*/
                                    }
                                    if (lang.equalsIgnoreCase("en")) {

                                    /*((Drawer) getActivity())
                                            .setActionBarTitle("Child Info");*/
                                    }
                                    ((Drawer) getActivity()).setBackForChildEdu();

                               /* FragmentManager fragmentManager = getFragmentManager();

                                ScheduleFragment rFragment = new ScheduleFragment();

                                FragmentTransaction ft = fragmentManager
                                        .beginTransaction();
                                ft.replace(R.id.content_frame, rFragment);
                                ft.commit();*/
                                    Intent schedeluIntent = new Intent(getActivity(), AsynchronousActivity.class);
                                    schedeluIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    getActivity().startActivity(schedeluIntent);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (id[position].equalsIgnoreCase("63")) {
                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                                    Intent foodMenuIntent = new Intent(getActivity(), AddFoodMenuActivity.class);
                                    startActivity(foodMenuIntent);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });

            return rowView;
        }

        // ////////////////////////////////////////////////////
        class ImageLoadTaskcliptwo extends AsyncTask<Void, Void, Bitmap> {
            // ProgressDialog pDialog;
            private String url;
            private ImageView image;

            public ImageLoadTaskcliptwo(String url, ImageView imageView) {
                this.url = url;
                this.image = imageView;
            }

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();

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
                image.setScaleType(ImageView.ScaleType.FIT_XY);
                image.setImageBitmap(result);
            }

        }
    }

    public void updateEdt() {

        String myFormat = "yyyy-MM-dd"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date.setText(sdf.format(myCalendar.getTime()));
    }

}
