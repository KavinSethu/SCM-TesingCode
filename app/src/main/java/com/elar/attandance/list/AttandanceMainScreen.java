package com.elar.attandance.list;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
/*
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONArray;
import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.elar.util.NetworkUtil;
import com.pnf.elar.app.R;
import com.pnf.elar.app.Drawer;
import com.pnf.elar.app.ImageLoadernew;
import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView.OnEditorActionListener;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AttandanceMainScreen extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    int posi = 0;
    DatePickerDialog.OnDateSetListener datepicker;
    Calendar myCalendar;
    String studentId = "", currentStatus = "", regId = "", srch = "";
    TextView spinnerForOption, Options, CHILDERN, txtForAttandanceOverView,
            txtForScheduleAttandance;
    // Spinner spinnerForOption;
    ArrayAdapter<String> adpterSpinner;
    String group_id, gp_id = "";// 1612460014
    String cDate, back_date; // current date
    String updatedDate, totalMarkedStudents;
    EditText serchbar;
    ImageView srchide, srchshow;

    // ------------------------------------------------------------------------------------------//
    ArrayList<String> listForID = new ArrayList<String>();
    ArrayList<String> listForName = new ArrayList<String>();
    ArrayList<String> listForTimeDuration = new ArrayList<String>();
    ArrayList<String> listForImage = new ArrayList<String>();
    ArrayList<String> listForCheckStatus = new ArrayList<String>();
    ArrayList<String> listForleaveReasonType = new ArrayList<String>();
    ArrayList<String> listForClassName = new ArrayList<String>();
    ArrayList<String> listForTotalMarkedStudent = new ArrayList<String>();
    ArrayList<String> listForAbsntDesc = new ArrayList<String>();
    ArrayList<String> listForReteivalDesc = new ArrayList<String>();
    ArrayList<String> listForRetrivalColor = new ArrayList<String>();
    ArrayList<String> listForLeftColor = new ArrayList<String>();
    private ArrayList<String> listForCheckTime = new ArrayList<String>();
    // ------------------------------------------------------------------------------------------//

    private ListView listForAttDetail;
    private ArrayList<Integer> listForId = new ArrayList<Integer>();
    // private ArrayList<String> listForName = new ArrayList<String>();
    // private ArrayList<String> listForTimeDuration = new ArrayList<String>();
    // private ArrayList<String> listForImage = new ArrayList<String>();
    // private ArrayList<String> listForCheck = new ArrayList<String>();
    // private ArrayList<String> listForCheckTime = new ArrayList<String>();

    private ArrayList<String> listForGroupId = new ArrayList<String>();
    private ArrayList<String> listForGroupName = new ArrayList<String>();

    // private ArrayList<String> listForCLassName = new ArrayList<String>();
    // private ArrayList<String> listForMarkedStudent = new ArrayList<String>();

    private TextView txtForCurrentDate, txtForMarkedStudent;
    private RelativeLayout backLeft, backRight;
    ArrayList<States> stateList = new ArrayList<States>();
    ArrayList<GetSetData> getSetList = new ArrayList<GetSetData>();

    String auth_key, securityKey, lang, Base_url, Security = "H67jdS7wwfh";
    String classStatus = null;

    UserSessionManager session;
    View v;
    ImageLoadernew imgLoader;
    int compareDate;

    private RelativeLayout optionLayoutForOptions, layout1, layout2,
            optionLayout;
    boolean status = false;


    TextView updatedTimeText;
    View line2View;
    String grpTitle = "";


    AtandanceListAdapter adp;

    SwipeRefreshLayout swipe_refresh_layout;

    Activity activity;
    private MyCustomProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.attandance_main_screen, container, false);
        // TODO Auto-generated method stub
//        getActivity().setTitle("Retriever List");

        dialog = new MyCustomProgressDialog(getActivity());

        myCalendar = Calendar.getInstance();
        session = new UserSessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        auth_key = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        regId = user.get(UserSessionManager.TAG_regId);

        gp_id = user.get(UserSessionManager.TAG_select_Group);

        System.out.println("gp id" + gp_id);

        srchide = (ImageView) v.findViewById(R.id.srchide);
        srchshow = (ImageView) v.findViewById(R.id.srchshow);
        serchbar = (EditText) v.findViewById(R.id.serchbar);
        line2View = (View) v.findViewById(R.id.line2View);
        listForAttDetail = (ListView) v.findViewById(R.id.listForAttDetail);
        swipe_refresh_layout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        spinnerForOption = (TextView) v.findViewById(R.id.spinnerForOption);
        updatedTimeText = (TextView) v.findViewById(R.id.updatedTimeText);

        Options = (TextView) v.findViewById(R.id.Options);
        CHILDERN = (TextView) v.findViewById(R.id.CHILDERN);
        txtForAttandanceOverView = (TextView) v
                .findViewById(R.id.txtForAttandanceOverView);
        txtForScheduleAttandance = (TextView) v
                .findViewById(R.id.txtForScheduleAttandance);
/////////// search bar //////////

        new AtandanceListAdapter(
                getActivity(), listForID, listForName,
                listForTimeDuration, listForImage,
                listForCheckStatus, listForleaveReasonType,
                listForClassName, listForTotalMarkedStudent,
                listForAbsntDesc, listForReteivalDesc,
                listForRetrivalColor, listForLeftColor,
                listForCheckTime);

        listForAttDetail.setAdapter(adp);
        swipe_refresh_layout.setOnRefreshListener(this);
        swipe_refresh_layout.post(new Runnable() {
            @Override
            public void run() {
                swipe_refresh_layout.setRefreshing(true);

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                        new GetDataCLass().execute();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        /*if(lang.equalsIgnoreCase("en"))
		{

		}
		else
		{

		}*/

        serchbar.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                srchide.setVisibility(View.GONE);
				/*serchbar.setHint("");*/
            }
        });
////////////////////// srchshow //////////
        serchbar.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    srch = serchbar.getText().toString();
                    if (srch.equalsIgnoreCase("")) {

                    } else {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(serchbar.getWindowToken(), 0);
                        new GetDataCLass().execute();
						/*serchbar.setText("");*/
                    }
                    return true;
                }
                return false;
            }

        });

/////////////////////////////////////////		
        if (lang.equalsIgnoreCase("sw")) {
            spinnerForOption.setText("Mina grupper");
            Options.setText("Alternativ");
            CHILDERN.setText("Barn");
            txtForAttandanceOverView.setText("Närvaroöversikt");
            txtForScheduleAttandance.setText("Schemaläggaren");
            grpTitle = "Välj grupp";
            serchbar.setHint("Filtrera användarlistan");
        } else {
            grpTitle = "Select group";
            serchbar.setHint("Filter userlist");


        }

        cDate = getCurrentDate();
        imgLoader = new ImageLoadernew(getActivity());

        txtForMarkedStudent = (TextView) v
                .findViewById(R.id.txtForMarkedStudentTop);

        txtForCurrentDate = (TextView) v.findViewById(R.id.txtForCurrentDate);
        txtForCurrentDate.setText("" + getCurrentDate());

        backLeft = (RelativeLayout) v.findViewById(R.id.backLeft);

        optionLayoutForOptions = (RelativeLayout) v
                .findViewById(R.id.optionLayoutForOptions);

        final String cDateCompare = getCurrentDate();
        compareDate = cDate.compareTo(cDate);
        // Log.e("comapre date", "compared date: " + compareDate);
        // ///////navigation drawer /////////
        if (lang.equalsIgnoreCase("sw")) {
            ((Drawer) getActivity()).setActionBarTitle("Närvarolista");
        } else {
            ((Drawer) getActivity()).setActionBarTitle("Retrieval List");
        }

        ((Drawer) getActivity()).Backtomain();
        // ////////






        datepicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEdt();
                back_date = txtForCurrentDate.getText().toString();
                new GetDataCLass().execute();// ////////////////////////////
            }

        };
        /////////
        txtForCurrentDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), datepicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        backLeft.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                        try {
                            cDate = removeDayInCurrentDate(cDate);
                            txtForCurrentDate.setText(cDate);
                            new GetDataCLass().execute();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            // Log.e("back right click ", "" + e);
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

        backRight = (RelativeLayout) v.findViewById(R.id.backRight);

        backRight.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // Log.e("comapre date", "compared date: " + compareDate);
                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                        try {
                            cDate = addDayInCurrentDate(cDate);
                            txtForCurrentDate.setText(cDate);
                            new GetDataCLass().execute();
                            // Toast.makeText(getActivity(), "true.",
                            // Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            // Log.e("back right click ", "" + e);
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

        new GetDataCLass().execute();
        new GetAllGroupList().execute();

        layout1 = (RelativeLayout) v.findViewById(R.id.layout1);
        layout2 = (RelativeLayout) v.findViewById(R.id.layout2);

        optionLayout = (RelativeLayout) v.findViewById(R.id.optionLayout);

        optionLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (status == false) {
                    optionLayoutForOptions.setVisibility(View.VISIBLE);
                    line2View.setVisibility(View.VISIBLE);
                    status = true;
                } else {
                    optionLayoutForOptions.setVisibility(View.GONE);
                    line2View.setVisibility(View.GONE);
                    serchbar.setText("");
                    status = false;
                }

            }
        });

        layout1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                        FragmentManager fragmentManager = getFragmentManager();
                        AttandanceOverView rFragment = new AttandanceOverView();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, rFragment);
                        ft.commit();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

        layout2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                        FragmentManager fragmentManager = getFragmentManager();
                        ScheduleAttendanceFragment rFragment = new ScheduleAttendanceFragment();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.content_frame, rFragment);
                        ft.commit();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

        return v;
    }

    @Override
    public void onRefresh() {
        new GetDataCLass().execute();
    }

    // class AdapterForAttandanceList extends ArrayAdapter<States> {
    //
    // private Activity activity;
    // public Context mContext;
    // private LayoutInflater inflater = null;
    // ViewHolder holder = null;
    //
    // private ArrayList<States> stateList;
    //
    // public AdapterForAttandanceList(Context context,
    // int textViewResourceId,
    //
    // ArrayList<States> stateList) {
    // super(context, textViewResourceId, stateList);
    // this.stateList = new ArrayList<States>();
    // this.stateList.addAll(stateList);
    // // activity = context;
    // }
    //
    // private class ViewHolder {
    // private RelativeLayout layoutForTick;
    // private CheckBox imgForCheck;
    // TextView textForStudentName;
    // TextView textForTimeDuration;
    // TextView txtForCheckTime, txtForCLassName, txtForMarkedStudent;
    // RelativeLayout topMain;
    // ImageView imgForProfile, imageForAbsentNotes,
    // imageForRetrivalNotes;
    // }
    //
    // @Override
    // public View getView(final int position, View convertView,
    // ViewGroup parent) {
    // // TODO Auto-generated method stub
    //
    // // Log.v("ConvertView", String.valueOf(position));
    // final States state = stateList.get(position);
    // if (convertView == null) {
    //
    // LayoutInflater vi = (LayoutInflater) getActivity()
    // .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    //
    // convertView = vi.inflate(R.layout.layout_for_attandance_list,
    // null);
    //
    // holder = new ViewHolder();
    //
    // holder.textForStudentName = (TextView) convertView
    // .findViewById(R.id.textForStudentName);
    //
    // holder.textForTimeDuration = (TextView) convertView
    // .findViewById(R.id.textForTimeDuration);
    //
    // holder.txtForCheckTime = (TextView) convertView
    // .findViewById(R.id.txtForCheckTime);
    //
    // holder.imgForCheck = (CheckBox) convertView
    // .findViewById(R.id.imgForCheck);
    //
    // holder.layoutForTick = (RelativeLayout) convertView
    // .findViewById(R.id.layoutForTick);
    //
    // holder.txtForCLassName = (TextView) convertView
    // .findViewById(R.id.txtForCLassName);
    //
    // holder.txtForMarkedStudent = (TextView) convertView
    // .findViewById(R.id.txtForMarkedStudent);
    // holder.topMain = (RelativeLayout) convertView
    // .findViewById(R.id.topMain);
    //
    // holder.imgForProfile = (ImageView) convertView
    // .findViewById(R.id.imgForProfile);
    //
    // holder.imageForRetrivalNotes = (ImageView) convertView
    // .findViewById(R.id.imageForRetrivalNotes);
    // holder.imageForAbsentNotes = (ImageView) convertView
    // .findViewById(R.id.imageForAbsentNotes);
    //
    // convertView.setTag(holder);
    //
    //
    // holder.imgForCheck.setOnClickListener(new OnClickListener() {
    //
    // @Override
    // public void onClick(View v) {
    // // is chkIos checked?
    // currentStatus = state.getCheckStatus();
    // studentId = state.getId();
    //
    // Log.e("data", " ---- status----- " + currentStatus
    // + "  -------id-------" + studentId);
    //
    // new MarkStudent().execute();
    //
    // }
    // });
    //
    //
    //
    // } else {
    // holder = (ViewHolder) convertView.getTag();
    // }
    //
    // holder.textForStudentName.setText("" + state.getName());
    // holder.textForStudentName.setTag(state);
    //
    // holder.textForTimeDuration.setText("" + state.getTimeDuration());
    // holder.textForTimeDuration.setTag(state);
    //
    // holder.txtForCheckTime.setText("" + state.getCheckTime());
    // holder.txtForCheckTime.setTag(state);
    //
    // holder.layoutForTick.setTag(state);
    //
    // holder.imgForCheck.setTag(state);
    //
    // if (state.getCheckStatus().equals("true")) {
    // holder.imgForCheck.setVisibility(View.VISIBLE);
    // holder.imgForCheck.setChecked(true);
    // holder.imgForCheck.setTag(state);
    // } else {
    // holder.imgForCheck.setVisibility(View.VISIBLE);
    // holder.imgForCheck.setChecked(false);
    // holder.imgForCheck.setTag(state);
    // }
    //
    // imgLoader.DisplayImage(state.getImage(), holder.imgForProfile);
    // // Log.e("image url", "" + state.getImage());
    //
    // if (classStatus.equals("true")) {
    // if (listForCLassName.get(position).equals("")) {
    // holder.topMain.setVisibility(View.GONE);
    // } else {
    // holder.topMain.setVisibility(View.VISIBLE);
    // }
    // holder.txtForCLassName.setText(""
    // + listForCLassName.get(position));
    //
    // holder.txtForMarkedStudent.setText(""
    // + listForMarkedStudent.get(position));
    // } else {
    // holder.topMain.setVisibility(View.GONE);
    // }
    //
    // if (state.getAbsentDesc().equals("true")) {
    //
    // if (state.getLeaveReasonType().equals("sick")) {
    // holder.imageForAbsentNotes
    // .setBackgroundResource(R.drawable.sickleave);
    // holder.imageForAbsentNotes.setTag(state);
    // }
    //
    // if (state.getLeaveReasonType().equals("leave")) {
    // holder.imageForAbsentNotes
    // .setBackgroundResource(R.drawable.absent_note);
    // holder.imageForAbsentNotes.setTag(state);
    // }
    //
    // if (state.getLeaveReasonType().equals("other")) {
    // holder.imageForAbsentNotes
    // .setBackgroundResource(R.drawable.retrieval_note);
    // holder.imageForAbsentNotes.setTag(state);
    // }
    // holder.imageForAbsentNotes.setTag(state);
    // } else {
    // holder.imageForAbsentNotes.setVisibility(View.GONE);
    // holder.imageForAbsentNotes.setTag(state);
    // }
    //
    // if (state.getRetrivalDesc().equals("true")) {
    // holder.imageForRetrivalNotes
    // .setBackgroundResource(R.drawable.retrieval_note);
    // holder.imageForRetrivalNotes.setTag(state);
    // } else {
    // holder.imageForRetrivalNotes.setVisibility(View.GONE);
    // holder.imageForRetrivalNotes.setTag(state);
    //
    // }
    //
    // // Log.e("position", "-=-=-=-=-=-=-=-=- " + position);
    //
    // // holder.code.setText(" (" + state.getCode() + ")");
    // // holder.name.setText(state.getName());
    // // holder.name.setChecked(state.isSelected());
    // //
    // // holder.name.setTag(state);
    //
    // // View vi = convertView;
    // // if (convertView == null)
    // // vi = inflater
    // // .inflate(R.layout.layout_for_attandance_list, null);
    // //
    // // layoutForTick = (RelativeLayout) vi
    // // .findViewById(R.id.layoutForTick);
    // //
    // // imgForCheck = (ImageView) vi.findViewById(R.id.imgForCheck);
    // // Log.e("-=-=-=-=-=-=-", listForCheck.get(position));
    // // if (listForCheck.get(position).equals("true")) {
    // // imgForCheck.setVisibility(View.VISIBLE);
    // // } else {
    // // imgForCheck.setVisibility(View.INVISIBLE);
    // // }
    // //
    // // textForStudentName = (TextView) vi
    // // .findViewById(R.id.textForStudentName);
    // // textForStudentName.setText("" + listForName.get(position));
    // //
    // // textForTimeDuration = (TextView) vi
    // // .findViewById(R.id.textForTimeDuration);
    // // textForTimeDuration.setText("" +
    // // listForTimeDuration.get(position));
    // //
    // // txtForCheckTime = (TextView)
    // // vi.findViewById(R.id.txtForCheckTime);
    // // txtForCheckTime.setText("" + listForCheckTime.get(position));
    // // layoutForTick.setTag(position);
    // // imgForCheck.setTag(position);
    // // layoutForTick.setOnClickListener(new OnClickListener() {
    // //
    // // @Override
    // // public void onClick(View v) {
    // // // TODO Auto-generated method stub
    // //
    // // if (imgForCheck.isShown()) {
    // // imgForCheck.setVisibility(View.INVISIBLE);
    // // } else {
    // // imgForCheck.setVisibility(View.VISIBLE);
    // // }
    // // }
    // // });
    //
    // return convertView;
    // }
    // }

    // --------------------------------------------------------- API EXECUTE
    // CLASS CODE START ------------------------------------------------//
    class GetDataCLass extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/retrivals/get_attendance_list";

        private static final String TAG_STATUS = "status";
        private static final String TAG_TOKEN = "authentication_token";
        private static final String TAG_Component = "data";
        private static final String TAG_User = "User";
        private static final String TAG_other_accounts = "other_accounts";
        private static final String TAG_name = "name";
        private static final String TAG_img_path = "img_path";
        private static final String TAG_img_path_id = "id";
        private static final String TAG_USR_Firstname = "USR_Firstname";
        private static final String TAG_updated_time = "updated_time";



        String Login_Email, Login_Password, Security = "H67jdS7wwfh";
        String Status, token, first_name, user_type, Remember_me;
        String[] component, image, other_accounts, name, image_id;


        public String updatedTime = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipe_refresh_layout.setRefreshing(false);


            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        protected String doInBackground(String... args) {

            String attendListResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_key, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.UserTypeApp + "=" + URLEncoder.encode("android", "UTF-8") +
                        "&" + Const.Params.DATE + "=" + URLEncoder.encode(cDate, "UTF-8") +
                        "&" + Const.Params.GroupId + "=" + URLEncoder.encode(gp_id, "UTF-8") +
                        "&" + Const.Params.Search + "=" + URLEncoder.encode(srch.toString(), "UTF-8");


                attendListResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
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
			params.add(new BasicNameValuePair("group_id", gp_id));
			params.add(new BasicNameValuePair("search", srch));

			Log.e("ID ", "-=-=-=-=-=-=-=-=-=-=-=-=-=-=- " + gp_id);

			Log.e("search Request=- ", params.toString());

			JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

*/
			/*Log.e("search Response=- ", json.toString());

			try {

				// listForCLassName.clear();
				// listForMarkedStudent.clear();
				// stateList.clear();
				listForID.clear();
				listForName.clear();
				listForTimeDuration.clear();
				listForImage.clear();
				listForCheckStatus.clear();
				listForleaveReasonType.clear();
				listForClassName.clear();
				listForTotalMarkedStudent.clear();
				listForAbsntDesc.clear();
				listForReteivalDesc.clear();
				listForRetrivalColor.clear();
				listForLeftColor.clear();
				listForCheckTime.clear();
				if(!json.equals(null) || !json.equals("")){
					JSONObject jsonObj = new JSONObject(json.toString());

					Status = jsonObj.getString(TAG_STATUS).toString();
					classStatus = jsonObj.optString("classStatus");
					totalMarkedStudents = jsonObj.optString("totalMarkedStudents");

					if(jsonObj.has(TAG_updated_time))
					{
						updatedTime=jsonObj.getString(TAG_updated_time);

					}

					if (jsonObj.optString("classStatus").equals("false")) {
						JSONArray comp = jsonObj.optJSONArray(TAG_Component);

						for (int j = 0; j < comp.length(); j++) {
							JSONObject c = comp.getJSONObject(j);

							JSONObject c2 = c.getJSONObject("User");

							String leftTime = c.optString("std_left_time");
							String rTime = c.optString("std_retrieval_time");

							listForTimeDuration.add(leftTime + " - " + rTime);

							if (c.optString("mark").equals("true")) {
								JSONObject student_retrival_timing = c
										.getJSONObject("student_retrival_timing");

								listForClassName.add("");
								listForTotalMarkedStudent.add("");

								listForID.add(c2.optString("id"));
								listForName.add(c2.optString("USR_FirstName") + " "
										+ c2.optString("USR_LastName"));
								// listForTimeDuration.add(c2
								// .optString("student_left_time")
								// + " - "
								// + c2.optString("student_retrieval_time"));
								listForImage.add(Base_url
										+ c2.optString("USR_image"));
								listForCheckStatus.add(c.optString("mark"));
								listForleaveReasonType.add(c
										.optString("leave_type"));

								listForAbsntDesc.add(c.optString("absent_desc"));
								listForReteivalDesc.add(c
										.optString("retriever_desc"));
								listForRetrivalColor.add(c
										.optString("std_retrieve_time_red"));
								listForLeftColor.add(c
										.optString("std_left_time_red"));
								listForCheckTime.add(student_retrival_timing
										.optString("marked_time"));
							} else {

								listForClassName.add("");
								listForTotalMarkedStudent.add("");

								listForID.add(c2.optString("id"));
								listForName.add(c2.optString("USR_FirstName") + " "
										+ c2.optString("USR_LastName"));
								// listForTimeDuration.add(c2
								// .optString("student_left_time")
								// + " - "
								// + c2.optString("student_retrieval_time"));
								listForImage.add(Base_url
										+ c2.optString("USR_image"));
								listForCheckStatus.add(c.optString("mark"));
								listForleaveReasonType.add(c
										.optString("leave_type"));

								listForAbsntDesc.add(c.optString("absent_desc"));
								listForReteivalDesc.add(c
										.optString("retriever_desc"));
								listForRetrivalColor.add(c
										.optString("std_retrieve_time_red"));
								listForLeftColor.add(c
										.optString("std_left_time_red"));
								listForCheckTime.add("");
							}

						}
					}

					if (jsonObj.optString("classStatus").equals("true")) {
						JSONArray comp = jsonObj.getJSONArray(TAG_Component);
						// Log.e("gergeggerwgerwgrewgerwgregergergewrgergewrgwergwergwergewrgewrgergerg",
						// "fergerggergregergergergregergergergerger");
						// Log.e("Create Response 2.....", json.toString());
						// stateList.clear();
						//
						// Log.e("length: ", "length 1:  " + comp.length());

						for (int j = 0; j < comp.length(); j++) {
							JSONObject c = comp.getJSONObject(j);

							listForClassName.add(c.optString("name"));

							System.out.println("listForClassName -- "+c.optString("name"));

							listForTotalMarkedStudent.add(c
									.optString("markedStudents"));

							JSONArray listArray = c.optJSONArray("list");
							for (int i = 0; i < listArray.length(); i++) {
								JSONObject listObject = listArray.getJSONObject(i);
								// object for user detail
								JSONObject userObject = listObject
										.getJSONObject("User");

								// user detail variables
								String userId = userObject.optString("id");
								String group_name = userObject
										.optString("group_name");

								String image_bgcolor = userObject
										.optString("image_bgcolor");
								String picturediary_student_count = userObject
										.optString("picturediary_student_count");
								String protection_level = userObject
										.optString("protection_level");
								String student_left_time = userObject
										.optString("student_left_time");
								String student_retrieval_time = userObject
										.optString("student_retrieval_time");
								String username = userObject.optString("username");
								String USR_Birthday = userObject
										.optString("USR_Birthday");
								String USR_Email = userObject
										.optString("USR_Email");
								String USR_FirstName = userObject
										.optString("USR_FirstName");
								String USR_image = userObject
										.optString("USR_image");
								String USR_LastName = userObject
										.optString("USR_LastName");

								// array index data variables
								String absent_desc = listObject
										.optString("absent_desc");
								String leave_type = listObject
										.optString("leave_type");
								String left_status = listObject
										.optString("left_status");
								String mark = listObject.optString("mark");
								String retriever_desc = listObject
										.optString("retriever_desc");
								String std_left_time = listObject
										.optString("std_left_time");
								String std_left_time_red = listObject
										.optString("std_left_time_red");
								String std_retrieval_time = listObject
										.optString("std_retrieval_time");
								String std_retrieve_time_red = listObject
										.optString("std_retrieve_time_red");
								String marked_time = "";

								if (mark.equals("true")) {

									// student_retrival_timing

									JSONObject student_retrival_timing = listObject
											.getJSONObject("student_retrival_timing");
									String action_type = student_retrival_timing
											.optString("action_type");
									String id = student_retrival_timing
											.optString("id");
									marked_time = student_retrival_timing
											.optString("marked_time");
								}

								listForClassName.add("");
								listForTotalMarkedStudent.add("");

								listForID.add(userId);
								listForName
										.add(USR_FirstName + " " + USR_FirstName);
								listForTimeDuration.add(std_left_time + " - "
										+ std_retrieval_time);
								listForImage.add(Base_url + USR_image);
								listForCheckStatus.add(mark);
								listForleaveReasonType.add(leave_type);

								listForAbsntDesc.add(absent_desc);
								listForReteivalDesc.add(retriever_desc);
								listForRetrivalColor.add(std_retrieve_time_red);
								listForLeftColor.add(std_left_time_red);
								listForCheckTime.add(marked_time);

								// States setData = new States(userId, USR_FirstName
								// + " " + USR_FirstName, std_left_time
								// + " - " + std_retrieval_time, Base_url
								// + USR_image, mark, marked_time, leave_type,
								// "", "", absent_desc, retriever_desc,
								// std_retrieve_time_red, std_left_time_red);

								// stateList.add(setData);
								//
								// listForClassName.add("");
								// listForTotalMarkedStudent.add("");

							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
*/
            return attendListResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            JSONObject jsonObj = new JSONObject();
            swipe_refresh_layout.setRefreshing(false);

            try {

                listForID.clear();
                listForName.clear();
                listForTimeDuration.clear();
                listForImage.clear();
                listForCheckStatus.clear();
                listForleaveReasonType.clear();
                listForClassName.clear();
                listForTotalMarkedStudent.clear();
                listForAbsntDesc.clear();
                listForReteivalDesc.clear();
                listForRetrivalColor.clear();
                listForLeftColor.clear();
                listForCheckTime.clear();
				/*adp.notifyDataSetChanged();*/


                if (results != null && !results.isEmpty()) {

                    try {
                        jsonObj = new JSONObject(results);


                    } catch (Exception e) {
                        e.printStackTrace();

                    }


/*
					JSONObject jsonObj = new JSONObject(json.toString());
*/

                    Status = jsonObj.getString(TAG_STATUS).toString();

                    if (Status.equalsIgnoreCase("true"))
                    {


                    classStatus = jsonObj.optString("classStatus");
                    totalMarkedStudents = jsonObj.optString("totalMarkedStudents");

                    if (jsonObj.has(TAG_updated_time)) {
                        updatedTime = jsonObj.getString(TAG_updated_time);

                    }

                    if (jsonObj.optString("classStatus").equals("false")) {
                        JSONArray comp = jsonObj.optJSONArray(TAG_Component);
                        if (comp.length() == 0) {
                            adp.notifyDataSetChanged();
                        }

                        for (int j = 0; j < comp.length(); j++) {
                            JSONObject c = comp.getJSONObject(j);

                            JSONObject c2 = c.getJSONObject("User");

                            String leftTime = c.optString("std_left_time");
                            String rTime = c.optString("std_retrieval_time");

                            listForTimeDuration.add(leftTime + " - " + rTime);

                            if (c.optString("mark").equals("true")) {
                                JSONObject student_retrival_timing = c
                                        .getJSONObject("student_retrival_timing");

                                listForClassName.add("");
                                listForTotalMarkedStudent.add("");

                                listForID.add(c2.optString("id"));
                                listForName.add(c2.optString("USR_FirstName") + " "
                                        + c2.optString("USR_LastName"));
                                // listForTimeDuration.add(c2
                                // .optString("student_left_time")
                                // + " - "
                                // + c2.optString("student_retrieval_time"));
                                listForImage.add(Base_url
                                        + c2.optString("USR_image"));
                                listForCheckStatus.add(c.optString("mark"));
                                listForleaveReasonType.add(c
                                        .optString("leave_type"));

                                listForAbsntDesc.add(c.optString("absent_desc"));
                                listForReteivalDesc.add(c
                                        .optString("retriever_desc"));
                                listForRetrivalColor.add(c
                                        .optString("std_retrieve_time_red"));
                                listForLeftColor.add(c
                                        .optString("std_left_time_red"));
                                listForCheckTime.add(student_retrival_timing
                                        .optString("marked_time"));
                            } else {

                                listForClassName.add("");
                                listForTotalMarkedStudent.add("");

                                listForID.add(c2.optString("id"));
                                listForName.add(c2.optString("USR_FirstName") + " "
                                        + c2.optString("USR_LastName"));
                                // listForTimeDuration.add(c2
                                // .optString("student_left_time")
                                // + " - "
                                // + c2.optString("student_retrieval_time"));
                                listForImage.add(Base_url
                                        + c2.optString("USR_image"));
                                listForCheckStatus.add(c.optString("mark"));
                                listForleaveReasonType.add(c
                                        .optString("leave_type"));

                                listForAbsntDesc.add(c.optString("absent_desc"));
                                listForReteivalDesc.add(c
                                        .optString("retriever_desc"));
                                listForRetrivalColor.add(c
                                        .optString("std_retrieve_time_red"));
                                listForLeftColor.add(c
                                        .optString("std_left_time_red"));
                                listForCheckTime.add("");
                            }

                        }
                    }

                    if (jsonObj.optString("classStatus").equals("true")) {
                        JSONArray comp = jsonObj.getJSONArray(TAG_Component);
                        // Log.e("gergeggerwgerwgrewgerwgregergergewrgergewrgwergwergwergewrgewrgergerg",
                        // "fergerggergregergergergregergergergerger");
                        // Log.e("Create Response 2.....", json.toString());
                        // stateList.clear();
                        //
                        // Log.e("length: ", "length 1:  " + comp.length());
                        if (comp.length() == 0) {
                            adp.notifyDataSetChanged();
                        }
                        for (int j = 0; j < comp.length(); j++) {
                            JSONObject c = comp.getJSONObject(j);

                            listForClassName.add(c.optString("name"));

                            System.out.println("listForClassName -- " + c.optString("name"));

                            listForTotalMarkedStudent.add(c
                                    .optString("markedStudents"));

                            JSONArray listArray = c.optJSONArray("list");
                            for (int i = 0; i < listArray.length(); i++) {
                                JSONObject listObject = listArray.getJSONObject(i);
                                // object for user detail
                                JSONObject userObject = listObject
                                        .getJSONObject("User");

                                // user detail variables
                                String userId = userObject.optString("id");
                                String group_name = userObject
                                        .optString("group_name");

                                String image_bgcolor = userObject
                                        .optString("image_bgcolor");
                                String picturediary_student_count = userObject
                                        .optString("picturediary_student_count");
                                String protection_level = userObject
                                        .optString("protection_level");
                                String student_left_time = userObject
                                        .optString("student_left_time");
                                String student_retrieval_time = userObject
                                        .optString("student_retrieval_time");
                                String username = userObject.optString("username");
                                String USR_Birthday = userObject
                                        .optString("USR_Birthday");
                                String USR_Email = userObject
                                        .optString("USR_Email");
                                String USR_FirstName = userObject
                                        .optString("USR_FirstName");
                                String USR_image = userObject
                                        .optString("USR_image");
                                String USR_LastName = userObject
                                        .optString("USR_LastName");

                                // array index data variables
                                String absent_desc = listObject
                                        .optString("absent_desc");
                                String leave_type = listObject
                                        .optString("leave_type");
                                String left_status = listObject
                                        .optString("left_status");
                                String mark = listObject.optString("mark");
                                String retriever_desc = listObject
                                        .optString("retriever_desc");
                                String std_left_time = listObject
                                        .optString("std_left_time");
                                String std_left_time_red = listObject
                                        .optString("std_left_time_red");
                                String std_retrieval_time = listObject
                                        .optString("std_retrieval_time");
                                String std_retrieve_time_red = listObject
                                        .optString("std_retrieve_time_red");
                                String marked_time = "";

                                if (mark.equals("true")) {

                                    // student_retrival_timing

                                    JSONObject student_retrival_timing = listObject
                                            .getJSONObject("student_retrival_timing");
                                    String action_type = student_retrival_timing
                                            .optString("action_type");
                                    String id = student_retrival_timing
                                            .optString("id");
                                    marked_time = student_retrival_timing
                                            .optString("marked_time");
                                }

                                listForClassName.add("");
                                listForTotalMarkedStudent.add("");

                                listForID.add(userId);
                                listForName
                                        .add(USR_FirstName + " " + USR_LastName);
                                listForTimeDuration.add(std_left_time + " - "
                                        + std_retrieval_time);
                                listForImage.add(Base_url + USR_image);
                                listForCheckStatus.add(mark);
                                listForleaveReasonType.add(leave_type);

                                listForAbsntDesc.add(absent_desc);
                                listForReteivalDesc.add(retriever_desc);
                                listForRetrivalColor.add(std_retrieve_time_red);
                                listForLeftColor.add(std_left_time_red);
                                listForCheckTime.add(marked_time);

                                // States setData = new States(userId, USR_FirstName
                                // + " " + USR_FirstName, std_left_time
                                // + " - " + std_retrieval_time, Base_url
                                // + USR_image, mark, marked_time, leave_type,
                                // "", "", absent_desc, retriever_desc,
                                // std_retrieve_time_red, std_left_time_red);

                                // stateList.add(setData);
                                //
                                // listForClassName.add("");
                                // listForTotalMarkedStudent.add("");

                            }
                        }
                    }
                }


                if (Status.equalsIgnoreCase("true")) {
                    txtForMarkedStudent.setText("" + totalMarkedStudents);

                    if (updatedTime.trim().length() > 0) {
                        if (lang.equalsIgnoreCase("en")) {
                            updatedTimeText.setText("Updated : " + updatedTime);
                        } else {


                            updatedTimeText.setText("Uppdaterad : " + updatedTime);


                        }

                    } else {
                        updatedTimeText.setText("");
                    }


                    adp = new AtandanceListAdapter(
                            getActivity(), listForID, listForName,
                            listForTimeDuration, listForImage,
                            listForCheckStatus, listForleaveReasonType,
                            listForClassName, listForTotalMarkedStudent,
                            listForAbsntDesc, listForReteivalDesc,
                            listForRetrivalColor, listForLeftColor,
                            listForCheckTime);

                    listForAttDetail.setAdapter(adp);
                    adp.notifyDataSetChanged();
                    listForAttDetail.setVisibility(View.VISIBLE);
                }
                else {
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




                } else {
                    // failed to create product
                    // Toast.makeText(getActivity(), " Invalid ",
                    // Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
            }
        }

    }

    // --------------------------------------------------------- API EXECUTE
    // CLASS CODE STOP -------------------------------------------------//

    // ----------------------------------------API TO GET ALL GROUP LIST CODE
    // START -----------------------------------//
    class GetAllGroupList extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/retrivals/all_groups";

        private static final String TAG_STATUS = "status";
        private static final String TAG_TOKEN = "authentication_token";
        private static final String TAG_Component = "data";
        private static final String TAG_User = "User";
        private static final String TAG_other_accounts = "other_accounts";
        private static final String TAG_name = "name";
        private static final String TAG_img_path = "img_path";
        private static final String TAG_id = "id";
        private static final String TAG_USR_Firstname = "USR_Firstname";

        private ProgressDialog pDialog;
        String[] listForGroupId_array, listForGroupId_id;

        String Login_Email, Login_Password, Security = "H67jdS7wwfh";
        String Status = "", token, first_name, user_type, Remember_me;
        String[] component, image, other_accounts, name, image_id, grp_id;

        // CustomAdapter_drawer adapter2;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // pDialog = new ProgressDialog(getActivity());
            // pDialog.setMessage("loading...");
            // pDialog.setIndeterminate(false);
            // pDialog.setCancelable(false);
            // pDialog.show();

        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {

            String groupListResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_key, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.UserTypeApp + "=" + URLEncoder.encode("android", "UTF-8");


                groupListResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }

			/*JSONParser jsonParser = new JSONParser();

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("securityKey", Security));
			params.add(new BasicNameValuePair("authentication_token", auth_key));
			params.add(new BasicNameValuePair("user_type_app", "android"));
			params.add(new BasicNameValuePair("language", lang));
			// params.add(new BasicNameValuePair("date", "2016-01-12"));
			// params.add(new BasicNameValuePair("group_id", "-1"));

			// Log.e("Create Response -=-=-=-=-=- ", params.toString());
			// Log.e("url", "url:- " + url);

			JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

			Log.e("Create Response.....", json.toString());
*/


            return groupListResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            // pDialog.dismiss();
            try {
                listForGroupId.clear();
                listForGroupName.clear();

                JSONObject jsonObj = new JSONObject();
                if (!results.isEmpty() && results != null) {
                    jsonObj = new JSONObject(results);

                    if (jsonObj.has("status")) {
                        Status = jsonObj.getString(TAG_STATUS).toString();
                    } else {
                    }
                } else {
                }

                if (Status.equalsIgnoreCase("true")) {
                    Log.i("apii", "calling");
                    // gv = (GridView) v.findViewById(R.id.gridForCategory);
                    //
                    // gv.setAdapter(new CustomAdapter_drawer(getActivity(),
                    // component, image));

                    // Log.i("c====...", Arrays.deepToString(image));


                    JSONArray comp = jsonObj.optJSONArray(TAG_Component);
                    listForGroupId_id = new String[comp.length()];
                    listForGroupId_array = new String[comp.length()];
                    // grp_id = new String[comp.length()];
                    for (int j = 0; j < comp.length(); j++) {
                        JSONObject c = comp.getJSONObject(j);
                        listForGroupId_array[j] = c.optString(TAG_name);
                        listForGroupId_id[j] = c.optString(TAG_id);
                        listForGroupId.add(c.optString(TAG_id));
                        listForGroupName.add(c.optString(TAG_name));

                        // component[j] = c.optString(TAG_name);
                        // grp_id[j] = c.optString(TAG_id);

                    }
                    int gr_pos = Arrays.asList(listForGroupId_id).indexOf(gp_id);

                    spinnerForOption
                            .setText(Html.fromHtml(listForGroupId_array[gr_pos]));


                    spinnerForOption.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub

                            try {
                                if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(
                                            getActivity());
                                    builder.setTitle(grpTitle);
                                    builder.setItems(listForGroupId_array,
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(
                                                        DialogInterface dialog, int item) {
                                                    // Do something with the selection
                                                    gp_id = listForGroupId_id[item];

                                                    session.putSelectedGroup(gp_id);
                                                    new GetDataCLass().execute();

                                                    System.out.println("titll" + listForGroupId_array[item]);
                                                    spinnerForOption
                                                            .setText(Html.fromHtml(listForGroupId_array[item]));
                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    // adpterSpinner = new ArrayAdapter<String>(getActivity(),
                    // R.layout.layout_for_groupspinner, R.id.weekofday,
                    // listForGroupName);
                    // spinnerForOption.setAdapter(adpterSpinner);
                    //
                    // spinnerForOption
                    // .setOnItemSelectedListener(new OnItemSelectedListener() {
                    //
                    // @Override
                    // public void onItemSelected(
                    // AdapterView<?> parent, View view,
                    // int position, long id) {
                    // // TODO Auto-generated method stub
                    // // /gp_id = grp_id[position]; // id
                    // gp_id = listForGroupId.get(position);
                    // group_id = listForGroupName.get(position);// name
                    // Log.e("group id = ", gp_id);
                    // new GetDataCLass().execute();
                    //
                    // }
                    //
                    // @Override
                    // public void onNothingSelected(
                    // AdapterView<?> parent) {
                    // // TODO Auto-generated method stub
                    //
                    // }
                    // });

                } else {
                    // failed to create product
                    // Toast.makeText(getActivity(), " Invalid ",
                    // Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ----------------------------------------API TO GET ALL GROUP LIST CODE
    // END -----------------------------------//\

    // ------------- mark api code start ----------//
    public class MarkStudent extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/retrivals/mark_student";

        private static final String TAG_STATUS = "status";
        private static final String TAG_TOKEN = "authentication_token";
        private static final String TAG_Component = "data";
        private static final String TAG_User = "User";
        private static final String TAG_other_accounts = "other_accounts";
        private static final String TAG_name = "name";
        private static final String TAG_img_path = "img_path";
        private static final String TAG_img_path_id = "id";
        private static final String TAG_USR_Firstname = "USR_Firstname";

        private MyCustomProgressDialog dialog;

        String Login_Email, Login_Password, Security = "H67jdS7wwfh";
        String Status, token, first_name, user_type, Remember_me;
        String[] component, image, other_accounts, name, image_id;

        // CustomAdapter_drawer adapter2;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new MyCustomProgressDialog(getActivity());
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        protected String doInBackground(String... args) {


            String markStudResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_key, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.UserTypeApp + "=" + URLEncoder.encode("android", "UTF-8") +
                        "&" + Const.Params.DATE + "=" + URLEncoder.encode(cDate, "UTF-8") +
                        "&" + Const.Params.GroupId + "=" + URLEncoder.encode(gp_id, "UTF-8") +
                        "&" + Const.Params.StudentId + "=" + URLEncoder.encode(studentId, "UTF-8") +
                        "&" + Const.Params.CurrentStatus + "=" + URLEncoder.encode(currentStatus, "UTF-8") +
                        "&" + Const.Params.DeviceTokenApp + "=" + URLEncoder.encode(regId, "UTF-8");


                markStudResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
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
			params.add(new BasicNameValuePair("group_id", gp_id));
			params.add(new BasicNameValuePair("student_id", studentId));
			params.add(new BasicNameValuePair("current_status", currentStatus));
			params.add(new BasicNameValuePair("device_token_app", regId));
			Log.e("ID ", "-=-=-=-=-=-=-=-=-=-=-=-=-=-=- " + gp_id);

			Log.e("Create Response -=- ", params.toString());

			JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

			try {

				// listForCLassName.clear();
				// listForMarkedStudent.clear();
				// stateList.clear();
				listForID.clear();
				listForName.clear();
				listForTimeDuration.clear();
				listForImage.clear();
				listForCheckStatus.clear();
				listForleaveReasonType.clear();
				listForClassName.clear();
				listForTotalMarkedStudent.clear();
				listForAbsntDesc.clear();
				listForReteivalDesc.clear();
				listForRetrivalColor.clear();
				listForLeftColor.clear();
				listForCheckTime.clear();

				JSONObject jsonObj = new JSONObject(json.toString());

				Status = jsonObj.getString(TAG_STATUS).toString();
				classStatus = jsonObj.optString("classStatus");
				totalMarkedStudents = jsonObj.optString("totalMarkedStudents");

				if (jsonObj.optString("classStatus").equals("false")) {
					JSONArray comp = jsonObj.optJSONArray(TAG_Component);

					for (int j = 0; j < comp.length(); j++) {
						JSONObject c = comp.getJSONObject(j);

						JSONObject c2 = c.getJSONObject("User");

						String leftTime = c.optString("std_left_time");
						String rTime = c.optString("std_retrieval_time");

						listForTimeDuration.add(leftTime + " - " + rTime);

						if (c.optString("mark").equals("true")) {
							JSONObject student_retrival_timing = c
									.getJSONObject("student_retrival_timing");

							listForClassName.add("");
							listForTotalMarkedStudent.add("");

							listForID.add(c2.optString("id"));
							listForName.add(c2.optString("USR_FirstName") + " "
									+ c2.optString("USR_LastName"));
							// listForTimeDuration.add(c2
							// .optString("student_left_time")
							// + " - "
							// + c2.optString("student_retrieval_time"));
							listForImage.add(Base_url
									+ c2.optString("USR_image"));
							listForCheckStatus.add(c.optString("mark"));
							listForleaveReasonType.add(c
									.optString("leave_type"));

							listForAbsntDesc.add(c.optString("absent_desc"));
							listForReteivalDesc.add(c
									.optString("retriever_desc"));
							listForRetrivalColor.add(c
									.optString("std_retrieve_time_red"));
							listForLeftColor.add(c
									.optString("std_left_time_red"));
							listForCheckTime.add(student_retrival_timing
									.optString("marked_time"));
						} else {

							listForClassName.add("");
							listForTotalMarkedStudent.add("");

							listForID.add(c2.optString("id"));
							listForName.add(c2.optString("USR_FirstName") + " "
									+ c2.optString("USR_LastName"));
							// listForTimeDuration.add(c2
							// .optString("student_left_time")
							// + " - "
							// + c2.optString("student_retrieval_time"));
							listForImage.add(Base_url
									+ c2.optString("USR_image"));
							listForCheckStatus.add(c.optString("mark"));
							listForleaveReasonType.add(c
									.optString("leave_type"));

							listForAbsntDesc.add(c.optString("absent_desc"));
							listForReteivalDesc.add(c
									.optString("retriever_desc"));
							listForRetrivalColor.add(c
									.optString("std_retrieve_time_red"));
							listForLeftColor.add(c
									.optString("std_left_time_red"));
							listForCheckTime.add("");
						}

					}
				}

				if (jsonObj.optString("classStatus").equals("true")) {
					JSONArray comp = jsonObj.getJSONArray(TAG_Component);

					for (int j = 0; j < comp.length(); j++) {
						JSONObject c = comp.getJSONObject(j);

						listForClassName.add(c.optString("name"));


						listForTotalMarkedStudent.add(c
								.optString("markedStudents"));

						JSONArray listArray = c.optJSONArray("list");
						for (int i = 0; i < listArray.length(); i++) {
							JSONObject listObject = listArray.getJSONObject(i);
							// object for user detail
							JSONObject userObject = listObject
									.getJSONObject("User");

							// user detail variables
							String userId = userObject.optString("id");
							String group_name = userObject
									.optString("group_name");
							// Log.e("group name -=-=-=-=-=-=-=-=-=-=-=-=-  ",
							// ""
							// + group_name);

							String image_bgcolor = userObject
									.optString("image_bgcolor");
							String picturediary_student_count = userObject
									.optString("picturediary_student_count");
							String protection_level = userObject
									.optString("protection_level");
							String student_left_time = userObject
									.optString("student_left_time");
							String student_retrieval_time = userObject
									.optString("student_retrieval_time");
							String username = userObject.optString("username");
							String USR_Birthday = userObject
									.optString("USR_Birthday");
							String USR_Email = userObject
									.optString("USR_Email");
							String USR_FirstName = userObject
									.optString("USR_FirstName");
							String USR_image = userObject
									.optString("USR_image");
							String USR_LastName = userObject
									.optString("USR_LastName");

							// array index data variables
							String absent_desc = listObject
									.optString("absent_desc");
							String leave_type = listObject
									.optString("leave_type");
							String left_status = listObject
									.optString("left_status");
							String mark = listObject.optString("mark");
							String retriever_desc = listObject
									.optString("retriever_desc");
							String std_left_time = listObject
									.optString("std_left_time");
							String std_left_time_red = listObject
									.optString("std_left_time_red");
							String std_retrieval_time = listObject
									.optString("std_retrieval_time");
							String std_retrieve_time_red = listObject
									.optString("std_retrieve_time_red");
							String marked_time = "";
							if (mark.equals("true")) {

								// student_retrival_timing

								JSONObject student_retrival_timing = listObject
										.getJSONObject("student_retrival_timing");
								String action_type = student_retrival_timing
										.optString("action_type");
								String id = student_retrival_timing
										.optString("id");
								marked_time = student_retrival_timing
										.optString("marked_time");
							}

							listForClassName.add("");
							listForTotalMarkedStudent.add("");

							listForID.add(userId);
							listForName
									.add(USR_FirstName + " " + USR_FirstName);
							listForTimeDuration.add(std_left_time + " - "
									+ std_retrieval_time);
							listForImage.add(Base_url + USR_image);
							listForCheckStatus.add(mark);
							listForleaveReasonType.add(leave_type);

							listForAbsntDesc.add(absent_desc);
							listForReteivalDesc.add(retriever_desc);
							listForRetrivalColor.add(std_retrieve_time_red);
							listForLeftColor.add(std_left_time_red);
							listForCheckTime.add(marked_time);

							// States setData = new States(userId, USR_FirstName
							// + " " + USR_FirstName, std_left_time
							// + " - " + std_retrieval_time, Base_url
							// + USR_image, mark, marked_time, leave_type,
							// "", "", absent_desc, retriever_desc,
							// std_retrieve_time_red, std_left_time_red);

							// stateList.add(setData);
							//
							// listForClassName.add("");
							// listForTotalMarkedStudent.add("");

						}
					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
*/
            return markStudResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();


            try {

                if (!results.isEmpty() && results != null) {


                    JSONObject jsonObj = new JSONObject(results);


                    if (jsonObj.has("status")) {
                        Status = jsonObj.getString(TAG_STATUS).toString();
                        if (Status.equalsIgnoreCase("true")) {

                            listForID.clear();
                            listForName.clear();
                            listForTimeDuration.clear();
                            listForImage.clear();
                            listForCheckStatus.clear();
                            listForleaveReasonType.clear();
                            listForClassName.clear();
                            listForTotalMarkedStudent.clear();
                            listForAbsntDesc.clear();
                            listForReteivalDesc.clear();
                            listForRetrivalColor.clear();
                            listForLeftColor.clear();
                            listForCheckTime.clear();

                            classStatus = jsonObj.optString("classStatus");
                            totalMarkedStudents = jsonObj.optString("totalMarkedStudents");

                            if (jsonObj.optString("classStatus").equals("false")) {
                                JSONArray comp = jsonObj.optJSONArray(TAG_Component);

                                for (int j = 0; j < comp.length(); j++) {
                                    JSONObject c = comp.getJSONObject(j);

                                    JSONObject c2 = c.getJSONObject("User");

                                    String leftTime = c.optString("std_left_time");
                                    String rTime = c.optString("std_retrieval_time");

                                    listForTimeDuration.add(leftTime + " - " + rTime);

                                    if (c.optString("mark").equals("true")) {
                                        JSONObject student_retrival_timing = c
                                                .getJSONObject("student_retrival_timing");

                                        listForClassName.add("");
                                        listForTotalMarkedStudent.add("");

                                        listForID.add(c2.optString("id"));
                                        listForName.add(c2.optString("USR_FirstName") + " "
                                                + c2.optString("USR_LastName"));
                                        // listForTimeDuration.add(c2
                                        // .optString("student_left_time")
                                        // + " - "
                                        // + c2.optString("student_retrieval_time"));
                                        listForImage.add(Base_url
                                                + c2.optString("USR_image"));
                                        listForCheckStatus.add(c.optString("mark"));
                                        listForleaveReasonType.add(c
                                                .optString("leave_type"));

                                        listForAbsntDesc.add(c.optString("absent_desc"));
                                        listForReteivalDesc.add(c
                                                .optString("retriever_desc"));
                                        listForRetrivalColor.add(c
                                                .optString("std_retrieve_time_red"));
                                        listForLeftColor.add(c
                                                .optString("std_left_time_red"));
                                        listForCheckTime.add(student_retrival_timing
                                                .optString("marked_time"));
                                    } else {

                                        listForClassName.add("");
                                        listForTotalMarkedStudent.add("");

                                        listForID.add(c2.optString("id"));
                                        listForName.add(c2.optString("USR_FirstName") + " "
                                                + c2.optString("USR_LastName"));
                                        // listForTimeDuration.add(c2
                                        // .optString("student_left_time")
                                        // + " - "
                                        // + c2.optString("student_retrieval_time"));
                                        listForImage.add(Base_url
                                                + c2.optString("USR_image"));
                                        listForCheckStatus.add(c.optString("mark"));
                                        listForleaveReasonType.add(c
                                                .optString("leave_type"));

                                        listForAbsntDesc.add(c.optString("absent_desc"));
                                        listForReteivalDesc.add(c
                                                .optString("retriever_desc"));
                                        listForRetrivalColor.add(c
                                                .optString("std_retrieve_time_red"));
                                        listForLeftColor.add(c
                                                .optString("std_left_time_red"));
                                        listForCheckTime.add("");
                                    }

                                }
                            }

                            if (jsonObj.optString("classStatus").equals("true")) {
                                JSONArray comp = jsonObj.getJSONArray(TAG_Component);

                                for (int j = 0; j < comp.length(); j++) {
                                    JSONObject c = comp.getJSONObject(j);

                                    listForClassName.add(c.optString("name"));


                                    listForTotalMarkedStudent.add(c
                                            .optString("markedStudents"));

                                    JSONArray listArray = c.optJSONArray("list");
                                    for (int i = 0; i < listArray.length(); i++) {
                                        JSONObject listObject = listArray.getJSONObject(i);
                                        // object for user detail
                                        JSONObject userObject = listObject
                                                .getJSONObject("User");

                                        // user detail variables
                                        String userId = userObject.optString("id");
                                        String group_name = userObject
                                                .optString("group_name");
                                        // Log.e("group name -=-=-=-=-=-=-=-=-=-=-=-=-  ",
                                        // ""
                                        // + group_name);

                                        String image_bgcolor = userObject
                                                .optString("image_bgcolor");
                                        String picturediary_student_count = userObject
                                                .optString("picturediary_student_count");
                                        String protection_level = userObject
                                                .optString("protection_level");
                                        String student_left_time = userObject
                                                .optString("student_left_time");
                                        String student_retrieval_time = userObject
                                                .optString("student_retrieval_time");
                                        String username = userObject.optString("username");
                                        String USR_Birthday = userObject
                                                .optString("USR_Birthday");
                                        String USR_Email = userObject
                                                .optString("USR_Email");
                                        String USR_FirstName = userObject
                                                .optString("USR_FirstName");
                                        String USR_image = userObject
                                                .optString("USR_image");
                                        String USR_LastName = userObject
                                                .optString("USR_LastName");

                                        // array index data variables
                                        String absent_desc = listObject
                                                .optString("absent_desc");
                                        String leave_type = listObject
                                                .optString("leave_type");
                                        String left_status = listObject
                                                .optString("left_status");
                                        String mark = listObject.optString("mark");
                                        String retriever_desc = listObject
                                                .optString("retriever_desc");
                                        String std_left_time = listObject
                                                .optString("std_left_time");
                                        String std_left_time_red = listObject
                                                .optString("std_left_time_red");
                                        String std_retrieval_time = listObject
                                                .optString("std_retrieval_time");
                                        String std_retrieve_time_red = listObject
                                                .optString("std_retrieve_time_red");
                                        String marked_time = "";
                                        if (mark.equals("true")) {

                                            // student_retrival_timing

                                            JSONObject student_retrival_timing = listObject
                                                    .getJSONObject("student_retrival_timing");
                                            String action_type = student_retrival_timing
                                                    .optString("action_type");
                                            String id = student_retrival_timing
                                                    .optString("id");
                                            marked_time = student_retrival_timing
                                                    .optString("marked_time");
                                        }
                                        listForClassName.add("");
                                        listForTotalMarkedStudent.add("");

                                        listForID.add(userId);
                                        listForName
                                                .add(USR_FirstName + " " + USR_LastName);
                                        listForTimeDuration.add(std_left_time + " - "
                                                + std_retrieval_time);
                                        listForImage.add(Base_url + USR_image);
                                        listForCheckStatus.add(mark);
                                        listForleaveReasonType.add(leave_type);

                                        listForAbsntDesc.add(absent_desc);
                                        listForReteivalDesc.add(retriever_desc);
                                        listForRetrivalColor.add(std_retrieve_time_red);
                                        listForLeftColor.add(std_left_time_red);
                                        listForCheckTime.add(marked_time);

                                        // States setData = new States(userId, USR_FirstName
                                        // + " " + USR_FirstName, std_left_time
                                        // + " - " + std_retrieval_time, Base_url
                                        // + USR_image, mark, marked_time, leave_type,
                                        // "", "", absent_desc, retriever_desc,
                                        // std_retrieve_time_red, std_left_time_red);

                                        // stateList.add(setData);
                                        //
                                        // listForClassName.add("");
                                        // listForTotalMarkedStudent.add("");

                                    }
                                }
                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), " Service Failed ",
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getActivity(), " Service Failed ",
                            Toast.LENGTH_LONG).show();
                }


                if (Status.equalsIgnoreCase("true")) {
                    txtForMarkedStudent.setText("" + totalMarkedStudents);
                   /* adp = new AtandanceListAdapter(
                            getActivity(), listForID, listForName,
                            listForTimeDuration, listForImage,
                            listForCheckStatus, listForleaveReasonType,
                            listForClassName, listForTotalMarkedStudent,
                            listForAbsntDesc, listForReteivalDesc,
                            listForRetrivalColor, listForLeftColor,
                            listForCheckTime);
*/
                    /*listForAttDetail.setAdapter(adp);*/

                    adp.notifyDataSetChanged();

                    /*listForAttDetail.setSelection(posi);*/


                } else {
                    // failed to create product
                    // Toast.makeText(getActivity(), " Invalid ",
                    // Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    // ------------- mark api code stop -----------//

    public String getCurrentDate() {
        String currentDate = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // get current date time with Date()
        Date date = new Date();
        System.out.println(dateFormat.format(date));

        // get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(myCalendar.getTime()));
        currentDate = dateFormat.format(cal.getTime());
        // Log.e("current date", currentDate);
        return currentDate;
    }

    public String addDayInCurrentDate(String currentDate) throws ParseException {
        // String dt = "2008-01-01"; // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        myCalendar.setTime(sdf.parse(currentDate));
        myCalendar.add(Calendar.DATE, 1); // number of days to add
        currentDate = sdf.format(myCalendar.getTime());
        return currentDate;
    }

    public String removeDayInCurrentDate(String currentDate)
            throws ParseException {
        // String dt = "2008-01-01"; // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        myCalendar.setTime(sdf.parse(currentDate));
        myCalendar.add(Calendar.DATE, -1); // number of days to add
        currentDate = sdf.format(myCalendar.getTime());
        return currentDate;
    }

    public class AtandanceListAdapter extends BaseAdapter {

        ImageView imgForProfile;
        private Activity activity;
        private LayoutInflater inflater = null;
        public ImageLoaderchildren imageLoaderchildren;
        ArrayList<String> listForID = new ArrayList<String>();
        ArrayList<String> listForName = new ArrayList<String>();
        ArrayList<String> listForTimeDuration = new ArrayList<String>();
        ArrayList<String> listForImage = new ArrayList<String>();
        ArrayList<String> listForCheckStatus = new ArrayList<String>();
        ArrayList<String> listForleaveReasonType = new ArrayList<String>();
        ArrayList<String> listForClassName = new ArrayList<String>();
        ArrayList<String> listForTotalMarkedStudent = new ArrayList<String>();
        ArrayList<String> listForAbsntDesc = new ArrayList<String>();
        ArrayList<String> listForReteivalDesc = new ArrayList<String>();
        ArrayList<String> listForRetrivalColor = new ArrayList<String>();
        ArrayList<String> listForLeftColor = new ArrayList<String>();
        private ArrayList<String> listForCheckTime = new ArrayList<String>();

        public AtandanceListAdapter(Activity a, ArrayList<String> listForID,
                                    ArrayList<String> listForName,
                                    ArrayList<String> listForTimeDuration,
                                    ArrayList<String> listForImage,
                                    ArrayList<String> listForCheckStatus,
                                    ArrayList<String> listForleaveReasonType,
                                    ArrayList<String> listForClassName,
                                    ArrayList<String> listForTotalMarkedStudent,
                                    ArrayList<String> listForAbsntDesc,
                                    ArrayList<String> listForReteivalDesc,
                                    ArrayList<String> listForRetrivalColor,
                                    ArrayList<String> listForLeftColor,
                                    ArrayList<String> listForCheckTime) {
            activity = a;
            this.listForID = listForID;
            this.listForName = listForName;
            this.listForTimeDuration = listForTimeDuration;
            this.listForImage = listForImage;
            this.listForCheckStatus = listForCheckStatus;
            this.listForleaveReasonType = listForleaveReasonType;
            this.listForClassName = listForClassName;
            this.listForTotalMarkedStudent = listForTotalMarkedStudent;
            this.listForAbsntDesc = listForAbsntDesc;
            this.listForReteivalDesc = listForReteivalDesc;
            this.listForRetrivalColor = listForRetrivalColor;
            this.listForLeftColor = listForLeftColor;
            this.listForCheckTime = listForCheckTime;

            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            imageLoaderchildren = new ImageLoaderchildren(getActivity());

            System.out.println("listForID" + listForID.size());


        }

        public int getCount() {
            return listForID.size();
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
                        .inflate(R.layout.layout_for_attandance_list, null);
            TextView textForStudentName = (TextView) vi
                    .findViewById(R.id.textForStudentName);

            TextView textForTimeDuration = (TextView) vi
                    .findViewById(R.id.textForTimeDuration);

            TextView txtForCheckTime = (TextView) vi
                    .findViewById(R.id.txtForCheckTime);

            RelativeLayout layoutForCheckBox = (RelativeLayout) vi
                    .findViewById(R.id.layoutForCheckBox);

            RelativeLayout layoutForTick = (RelativeLayout) vi
                    .findViewById(R.id.layoutForTick);

            TextView txtForCLassName = (TextView) vi
                    .findViewById(R.id.txtForCLassName);

            TextView txtForMarkedStudent = (TextView) vi
                    .findViewById(R.id.txtForMarkedStudent);

            RelativeLayout topMain = (RelativeLayout) vi
                    .findViewById(R.id.topMain);

            RelativeLayout main = (RelativeLayout) vi.findViewById(R.id.main);

            imgForProfile = (ImageView) vi.findViewById(R.id.imgForProfile);

            ImageView imageForRetrivalNotes = (ImageView) vi
                    .findViewById(R.id.imageForRetrivalNotes);
            ImageView imageForAbsentNotes = (ImageView) vi
                    .findViewById(R.id.imageForAbsentNotes);
            imageForRetrivalNotes.setVisibility(View.INVISIBLE);
            imageForAbsentNotes.setVisibility(View.INVISIBLE);

            // imgLoader.DisplayImage(listForImage.get(position),
            // imgForProfile);

            try {

                // Log.e("%%%%%%%%%%", listForImage.get(position));
              /*  imageLoaderchildren.DisplayImage(listForImage.get(position),
                        imgForProfile);*/
                Glide.with(activity)
                        .load(Uri.parse(listForImage.get(position)))
                        .asBitmap()
                        .error(R.drawable.manjit)
                        .placeholder(R.drawable.manjit)
                        .into(imgForProfile);

//                Picasso.with(activity)
//                        .load(Uri.parse(listForImage.get(position)))
//                        .placeholder(R.drawable.manjit)
//                        // optional
//                        .error(R.drawable.manjit)         // optional
//                        .into(imgForProfile);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

            // new ImageLoadTaskclip(listForImage.get(position), imgForProfile);
            try {
                textForStudentName.setText(listForName.get(position));
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

            if (listForTimeDuration.size() > 0) {
                textForTimeDuration.setText(listForTimeDuration.get(position));
            }

            txtForCheckTime.setText(listForCheckTime.get(position));

            if (listForClassName.get(position).equals("")) {
                topMain.setVisibility(View.GONE);
            } else {
                topMain.setVisibility(View.VISIBLE);
            }
            txtForCLassName.setText(Html.fromHtml("" + listForClassName.get(position)));

            System.out.println("cc" + listForClassName.get(position));

            txtForMarkedStudent.setText(""
                    + listForTotalMarkedStudent.get(position));

            if (listForAbsntDesc.get(position).equals("true")) {

                if (listForleaveReasonType.get(position).equals("sick")) {
                    imageForAbsentNotes.setVisibility(View.VISIBLE);
                    imageForAbsentNotes
                            .setBackgroundResource(R.drawable.sickleave);
                }

                if (listForleaveReasonType.get(position).equals("leave")) {
                    imageForAbsentNotes.setVisibility(View.VISIBLE);
                    imageForAbsentNotes
                            .setBackgroundResource(R.drawable.absent_note);
                }

                if (listForleaveReasonType.get(position).equals("other")) {
                    imageForAbsentNotes.setVisibility(View.VISIBLE);
                    imageForAbsentNotes
                            .setBackgroundResource(R.drawable.retrieval_note);
                }
            }

            if (listForReteivalDesc.get(position).equals("true")) {
                imageForRetrivalNotes.setVisibility(View.VISIBLE);
                imageForRetrivalNotes
                        .setBackgroundResource(R.drawable.retrieval_note);
            }

            final CheckBox msgChkNew = (CheckBox) vi
                    .findViewById(R.id.imgForCheck);
            if (listForCheckStatus.get(position).equals("true")) {
                msgChkNew.setVisibility(View.VISIBLE);
                msgChkNew.setChecked(true);
            } else {
                msgChkNew.setVisibility(View.VISIBLE);
                msgChkNew.setChecked(false);
            }

            msgChkNew.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    try {
                        if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                            currentStatus = listForCheckStatus.get(position);
                            posi = listForAttDetail.getFirstVisiblePosition();

                            System.out.println("posi -- " + listForAttDetail.getFirstVisiblePosition());
                    /*	if (currentStatus.equals("true")) {
                            listForCheckStatus.set(position, "false");
                        } else {
                            listForCheckStatus.set(position, "true");
                        }*/
                            studentId = listForID.get(position);
                            new MarkStudent().execute();
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                }
            });
            layoutForCheckBox.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                            currentStatus = listForCheckStatus.get(position);
                            posi = listForAttDetail.getFirstVisiblePosition();
                    /*	if (currentStatus.equals("true")) {
                            listForCheckStatus.set(position, "false");
                        } else {
                            listForCheckStatus.set(position, "true");
                        }*/

                            studentId = listForID.get(position);
                            new MarkStudent().execute();
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            });
            main.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    try {
                        if (NetworkUtil.getInstance(getActivity()).isInternet()) {

                            FragmentManager fragmentManager = getFragmentManager();

                            UserProfileFragment rFragment = new UserProfileFragment();
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            Bundle bundle = new Bundle();
                            bundle.putString("keyForStudentId", listForID.get(position));
                            bundle.putString("keyForTimeDuration",
                                    listForTimeDuration.get(position));
                            rFragment.setArguments(bundle);
                            ft.replace(R.id.content_frame, rFragment);
                            ft.commit();
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            });

            imageForAbsentNotes.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    try {
                        if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                            FragmentManager fragmentManager = getFragmentManager();

                            AbsenceNotes rFragment = new AbsenceNotes();
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            Bundle bundle = new Bundle();
                            bundle.putString("keyForStudentId", listForID.get(position));
                            bundle.putString("keyForDisplayType", "a"); // "a" for type
                            // absent. this
                            // variable is
                            // used in next
                            // screen cDate
                            bundle.putString("keyForDate", cDate);
                            bundle.putString("absentnoteicon", "absentnoteicon");
                            rFragment.setArguments(bundle);
                            ft.replace(R.id.content_frame, rFragment);
                            ft.commit();
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            });

            imageForRetrivalNotes.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    try {
                        if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                            FragmentManager fragmentManager = getFragmentManager();

                            AbsenceNotes rFragment = new AbsenceNotes();
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            Bundle bundle = new Bundle();
                            bundle.putString("keyForStudentId", listForID.get(position));
                            bundle.putString("keyForDisplayType", "r"); // "r" for type
                            // retrieval.
                            // this variable
                            // is used in
                            // next screen
                            bundle.putString("absentnoteicon", "retrievericon");
                            rFragment.setArguments(bundle);
                            ft.replace(R.id.content_frame, rFragment);
                            ft.commit();

                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            });

            // msgChkNew.setOnCheckedChangeListener(new
            // OnCheckedChangeListener() {
            //
            // @Override
            // public void onCheckedChanged(CompoundButton buttonView,
            // boolean isChecked) {
            // if (msgChkNew.isChecked()) {
            //
            // } else {
            //
            // }
            // }
            // });

            return vi;
        }

        // //////////////
        // /////////
        class ImageLoadTaskclip extends AsyncTask<Void, Void, Bitmap> {
            // ProgressDialog pDialog;
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
        // ///////
        // ///////
    }

    public String filterStringToNormal(String data) {
        String input = "Carmen López-Delina Santos";
        input = data;
        String withoutAccent = Normalizer.normalize(input, Normalizer.Form.NFD);
        String output = withoutAccent.replaceAll("[^a-zA-Z ]", "");
        System.out.println(output);
        return data;
    }

    public void updateEdt() {

        String myFormat = "yyyy-MM-dd"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txtForCurrentDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            activity=(Activity) context;
        }
    }
}