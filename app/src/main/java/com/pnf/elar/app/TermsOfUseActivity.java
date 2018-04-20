package com.pnf.elar.app;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.Util.Model.Model.APIListResponse;
import com.Util.Model.Model.UserTerm;
import com.Util.Model.NonScroll.NonScrollListView;
import com.Util.Model.RetrofitServiceBuilder;
import com.Util.Model.Service.TermListService;
import com.elar.attandance.list.AttandanceMainScreen;
import com.elar.util.NetworkUtil;
import com.elar.util.SmartClassUtil;
import com.pnf.elar.app.adapter.SearchSchoolBo;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.service.WebServeRequest;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermsOfUseActivity extends AppCompatActivity implements View.OnClickListener {

//    ListView lv_term_of_use;


    NonScrollListView lv_term_of_use;
    NonScrollListView lv_term_of_use_checkbox;

    TextView tv_agree, tv_disagree, tv_logout;
    static String Base_url = "https://dev.elar.se/mobile_api/get_userterms_formobile/";
    static String Save_url ;
    static String Decline_url ;
    UserSessionManager session;
    String user_type;
    String user_id;

    String[] Accept_id;
    String[] Decline_id;
    String[] Accept_id_str;
    String[] Decline_id_str;


    String status;
    String sec_key = "H67jdS7wwfh";
    String platform = "android";
    String ids ;
    private MyCustomProgressDialog dialog;
    String auth_token;
    String lang;
    String regId;
    LinearLayout bottom;

    ArrayList<String> userTermArrayList_Title = new ArrayList<>();
    ArrayList<UserTerm> userTermArrayList = new ArrayList<>();

    ArrayList<String> selec_term_list = new ArrayList<>();

    int count;
    int che_size;

    Typeface custom_font;

    CustomAdapterCheckBox customAdapterCheckBox ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_use);


        dialog = new MyCustomProgressDialog(TermsOfUseActivity.this);


        session = new UserSessionManager(TermsOfUseActivity.this);
        customAdapterCheckBox = new CustomAdapterCheckBox(userTermArrayList, TermsOfUseActivity.this);

        HashMap<String, String> user = session.getUserDetails();
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        auth_token = user.get(UserSessionManager.TAG_Authntication_token);
        lang = user.get(UserSessionManager.TAG_language);
        regId = user.get(UserSessionManager.TAG_regId);
       // user_id = user.get(UserSessionManager.TAG_user_id);


        try {

            Intent in = getIntent();
            user_type = in.getStringExtra("user_type");
            user_id = in.getStringExtra("user_id");

        } catch (Exception e) {
            // TODO: handle exception
        }

        Log.d("userid_",""+user_id);

       /* lang="sw";
        auth_token="";
        regId="";
*/
        findView();


        System.out.print("BaseUrl" + Base_url);

        new GetTermList().execute();

        tv_agree.setOnClickListener(this);
        tv_disagree.setOnClickListener(this);
        tv_logout.setOnClickListener(this);


    }


    private void findView() {
        lv_term_of_use = (NonScrollListView) findViewById(R.id.lv_terms_of_use);
        lv_term_of_use_checkbox = (NonScrollListView) findViewById(R.id.lv_terms_of_use_checkbox);

        tv_agree = (TextView) findViewById(R.id.tv_agree);
        tv_disagree = (TextView) findViewById(R.id.tv_disagree);
        tv_logout = (TextView) findViewById(R.id.tv_logout);
        bottom = (LinearLayout) findViewById(R.id.bottom);

        bottom.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.tv_agree:

               /* count = userTermArrayList_Title.size();
                SparseBooleanArray checked = lv_term_of_use_checkbox.getCheckedItemPositions();

                int temp_count = 0;
                for (int i = 0; i <= count; i++) {

                    if (checked.get(i) == true) {
                        temp_count++;
                    }

                }
                if (count == temp_count) {
                    Toast.makeText(this, "Sucess", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(this, "Please Check all Term of Use", Toast.LENGTH_SHORT).show();
                }

                System.out.print("l_Count" + count + "----Checked" + temp_count);
                Log.d("llcount", "" + count + "----Checked" + temp_count);*/


                status="yes";
                count = userTermArrayList_Title.size();

                if (count==selec_term_list.size())
                {
                    StringBuilder commaSepValueBuilder = new StringBuilder();

                    //Looping through the list
                    for ( int i = 0; i< selec_term_list.size(); i++){
                        //append the value into the builder
                        commaSepValueBuilder.append(selec_term_list.get(i));

                        //if the value is not the last element of the list
                        //then append the comma(,) as well
                        if ( i != selec_term_list.size()-1){
                            commaSepValueBuilder.append(", ");
                        }
                    }

                    ids= String.valueOf(commaSepValueBuilder);
//
//                    Toast.makeText(TermsOfUseActivity.this, ""+ids, Toast.LENGTH_SHORT).show();

                    new SaveTermList().execute();

                }else {
                    Toast.makeText(this, "Please Check all Term of Use", Toast.LENGTH_SHORT).show();
                }


               // Toast.makeText(TermsOfUseActivity.this, ""+selec_term_list, Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_logout:

                try {
                    if (NetworkUtil.getInstance(TermsOfUseActivity.this).isInternet()) {

                        String msg = null;
                        String yes = null;
                        String no = null;
                        String title = null;

                        if (lang.equalsIgnoreCase("sw")) {
                            title = "Logga ut";
                            msg = "Vill du logga ut?";
                            yes = "Ja";
                            no = "Nej";
                        }
                        if (lang.equalsIgnoreCase("en")) {
                            title = "Log out";
                            msg = "Do you want to log out?";
                            yes = "Yes";
                            no = "No";
                        }

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                TermsOfUseActivity.this, R.style.MyAlertDialogStyle);
                        alertDialogBuilder
                                .setMessage(msg);
                        alertDialogBuilder.setTitle(title);

                        alertDialogBuilder.setPositiveButton(yes,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface arg0, int arg1) {

                                        new LogoutAPI().execute();


                                    }
                                });

                        alertDialogBuilder.setNegativeButton(no,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {

                                        // Intent i = new
                                        // Intent(SettingPage.this,SettingPage.class);
                                        // startActivity(i);
                                        // finish();
                                        // closeContextMenu();

                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder
                                .create();
                        alertDialog.show();

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.tv_disagree:


                status="no";
                count = userTermArrayList_Title.size();

                if (count==selec_term_list.size())
                {
                    StringBuilder commaSepValueBuilder = new StringBuilder();

                    //Looping through the list
                    for ( int i = 0; i< selec_term_list.size(); i++){
                        //append the value into the builder
                        commaSepValueBuilder.append(selec_term_list.get(i));

                        //if the value is not the last element of the list
                        //then append the comma(,) as well
                        if ( i != selec_term_list.size()-1){
                            commaSepValueBuilder.append(", ");
                        }
                    }

                    ids= String.valueOf(commaSepValueBuilder);


//                    new LogoutAPI().execute();

                    new SaveTermList().execute();

                }else {
                    Toast.makeText(this, "Please Check all Term of Use", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }


    private void getUserTermList() {


        String url = Base_url + "mobile_api/get_userterms_formobile/";


        Toast.makeText(this, "" + url, Toast.LENGTH_LONG).show();


        System.out.print("User_ID" + user_id);

        UserTerm userTerm = new UserTerm();
        userTerm.setSecurityKey(sec_key);
        userTerm.setLoginUserID(user_id);
        userTerm.setPlatform(platform);


        TermListService termListService = (TermListService) RetrofitServiceBuilder.getService(TermListService.class, url);
        termListService.getTermList(Base_url, userTerm).enqueue(new Callback<APIListResponse<UserTerm>>() {
            @Override
            public void onResponse(Call<APIListResponse<UserTerm>> call, Response<APIListResponse<UserTerm>> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(TermsOfUseActivity.this, "Suc", Toast.LENGTH_SHORT).show();
                    Toast.makeText(TermsOfUseActivity.this, "" + response.body(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<APIListResponse<UserTerm>> call, Throwable t) {

            }
        });
    }


    class GetTermList extends AsyncTask<String, String, String> {

        JSONObject TermListObject = new JSONObject();


         private String url = Base_url + "mobile_api/get_userterms_formobile";


      /*  String url = "https://dev.elar.se/mobile_api/get_userterms_formobile";
        String Security = "H67jdS7wwfh";
        String platform = "android";
        String user_id = "1243";
*/

        public String updatedTime = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        protected String doInBackground(String... args) {


            JSONObject paramsObject = new JSONObject();
            try {
                paramsObject.put("securityKey", sec_key);
                paramsObject.put(Const.Params.LoginUserId, user_id);
                paramsObject.put(Const.Params.PlarForm, platform);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {

                TermListObject = new JSONObject(WebServeRequest.postJSONRequest(url, paramsObject.toString()));
                Log.e("ul", TermListObject.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }


            return TermListObject.toString();
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done


            // Toast.makeText(TermsOfUseActivity.this, "" + results, Toast.LENGTH_LONG).show();

            try {
                JSONObject jsonObj = new JSONObject(results);


                String Status = jsonObj.getString("status");

                if (Status.equalsIgnoreCase("true")) {

                    JSONArray TermsArray = jsonObj.getJSONArray("term_list");




                    for (int j = 0; j < TermsArray.length(); j++) {

                        JSONObject jsonObject = TermsArray.getJSONObject(j);

                        UserTerm userTerm = new UserTerm();
                        JSONObject termObject = jsonObject.getJSONObject("UserTerm");

                        String temp_title = termObject.getString("term_title");
                        String final_title = temp_title.replaceAll("\\<.*?\\>", "");
                        userTerm.setTerm_title(final_title);


                        String temp_desc = termObject.getString("term_description");
                        String final_desc = temp_desc.replaceAll("\\<.*?\\>", "");
                        userTerm.setTerm_description(final_desc);

                        userTerm.setId(termObject.getString("id"));

                        userTermArrayList.add(userTerm);



                        userTermArrayList_Title.add(termObject.getString("term_title"));

                    }


                    CustomAdapter customAdapter = new CustomAdapter(userTermArrayList, TermsOfUseActivity.this);
                    lv_term_of_use.setAdapter(customAdapter);




                    lv_term_of_use_checkbox.setAdapter(customAdapterCheckBox);

                   /* lv_term_of_use_checkbox.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    lv_term_of_use_checkbox.setAdapter(new ArrayAdapter<String>(TermsOfUseActivity.this,
                            R.layout.checkboxlayout, userTermArrayList_Title));*/


                } else {


                    String msg = jsonObj.getString("message");



                    try {
                        System.out.print(msg);

                        HashMap<String, String> user = session.getUserDetails();
                        String lang = user.get(UserSessionManager.TAG_language);

                        if (lang.equalsIgnoreCase("sw")) {
                            System.out.print("Sw_l");
                            if (msg.equalsIgnoreCase("Autentisering misslyckades")) {
                                Button btnLogout;
                                TextView tvMessage;
                                final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", TermsOfUseActivity.this);
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
                                final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", TermsOfUseActivity.this);
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


            dialog.dismiss();
            bottom.setVisibility(View.VISIBLE);


        }

    }

    public class CustomAdapter extends ArrayAdapter {

        private ArrayList dataSet;
        Context mContext;

        // View lookup cache
        private class ViewHolder {
            TextView txtName, txtDesc;
            CheckBox checkBox;
            LinearLayout lay_che;
        }

        public CustomAdapter(ArrayList data, Context context) {
            super(context, R.layout.term_row_layout, data);
            this.dataSet = data;
            this.mContext = context;

        }

        @Override
        public int getCount() {
            return dataSet.size();
        }

        @Override
        public UserTerm getItem(int position) {
            return (UserTerm) dataSet.get(position);
        }


        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {

            ViewHolder viewHolder;
            final View result;

            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.term_row_layout, parent, false);
                viewHolder.txtName = (TextView) convertView.findViewById(R.id.tv_Title);
                viewHolder.txtDesc = (TextView) convertView.findViewById(R.id.tv_Desc);
                viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.row_checkbox);

                custom_font = Typeface.createFromAsset(getAssets(), "font/sans.ttf");
                viewHolder.txtName.setTypeface(custom_font);
                viewHolder.txtDesc.setTypeface(custom_font);

                viewHolder.checkBox.setVisibility(View.GONE);

                result = convertView;
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                result = convertView;
            }

            UserTerm userTerm = getItem(position);


//            viewHolder.txtName.setText(userTerm.getTerm_title());
//            viewHolder.txtDesc.setText(userTerm.getTerm_description());

            viewHolder.txtName.setText(Html.fromHtml(userTerm.getTerm_title()));
            viewHolder.txtDesc.setText(Html.fromHtml(userTerm.getTerm_description()));


            return result;
        }
    }


    public class CustomAdapterCheckBox extends ArrayAdapter {

        private ArrayList<UserTerm> dataSet;
        Context mContext;
        boolean[] checkBoxState;

        // View lookup cache
        private class ViewHolder {
            TextView txtName, txtDesc,txtId;
            CheckBox checkBox;
        }

        public CustomAdapterCheckBox(ArrayList<UserTerm> data, Context context) {
            super(context, R.layout.term_row_layout, data);
            this.dataSet = data;
            this.mContext = context;

            checkBoxState=new boolean[dataSet.size()];


        }


        @Override
        public int getCount() {
            return dataSet.size();
        }

        @Override
        public UserTerm getItem(int position) {
            return (UserTerm) dataSet.get(position);
        }


        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

            final ViewHolder viewHolder;
            final View result;

            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.term_row_layout, parent, false);
                viewHolder.txtName = (TextView) convertView.findViewById(R.id.tv_Title);
                viewHolder.txtDesc = (TextView) convertView.findViewById(R.id.tv_Desc);
                viewHolder.txtId = (TextView) convertView.findViewById(R.id.tv_Id);
                viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.row_checkbox);

                custom_font = Typeface.createFromAsset(getAssets(), "font/sans.ttf");
                viewHolder.txtName.setTypeface(custom_font);
                viewHolder.txtDesc.setTypeface(custom_font);

                viewHolder.txtDesc.setVisibility(View.GONE);
                viewHolder.checkBox.setVisibility(View.VISIBLE);

                result = convertView;
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                result = convertView;
            }

            UserTerm userTerm = getItem(position);


//            viewHolder.txtName.setText(userTerm.getTerm_title());
//            viewHolder.txtDesc.setText(userTerm.getTerm_description());

            viewHolder.txtName.setText(Html.fromHtml(userTerm.getTerm_title()));
            viewHolder.txtDesc.setText(Html.fromHtml(userTerm.getTerm_description()));
            viewHolder.txtId.setText(Html.fromHtml(userTerm.getId()));


            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    if (((CheckBox) v).isChecked()) {
//                        checkBoxState[position]=true;
                        viewHolder.checkBox.setSelected(false);

                        selec_term_list.add(dataSet.get(position).getId());

                    }
                    else {
//                        checkBoxState[position]=false;
                        viewHolder.checkBox.setSelected(true);



                        String id=dataSet.get(position).getId();

                        if (selec_term_list.contains(id))
                        {
                            selec_term_list.remove(id);
                        }

                    }


                }
            });

            return result;
        }
    }

    class LogoutAPI extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/users/logout";
        String Status = "", message = "", note, written_by, student_name, image;


        private MyCustomProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new MyCustomProgressDialog(TermsOfUseActivity.this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        protected String doInBackground(String... args) {

            String logoutResponse = "";


            try {
                try {

                    String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(sec_key, "UTF-8") +
                            "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                            "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                            "&" + Const.Params.DeviceTokenApp + "=" + URLEncoder.encode(regId, "UTF-8");


                    logoutResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
/*
                JSONObject jsonObj = new JSONObject(json.toString());
*/

                /*JSONObject json = new JSONObject();
                JSONParser jsonParser = new JSONParser();

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("securityKey", securityKey));
                params.add(new BasicNameValuePair("authentication_token",
                        auth_token));
                params.add(new BasicNameValuePair("language", lang));
                params.add(new BasicNameValuePair("device_token_app", regId));

                Log.d("response ", params.toString());

                json = jsonParser.makeHttpRequest(url, "POST", params);*/

            } catch (Exception e) {
                e.printStackTrace();
            }

            return logoutResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {
                JSONObject logoutJson = new JSONObject();
                if (results != null && !results.isEmpty()) {
                    logoutJson = new JSONObject(results);
                    Log.d("logoutJson", "" + logoutJson);
                    if (logoutJson.has(Const.Params.Status)) {
                        Status = logoutJson.getString("status").toString();
                    } else {
                        message = "Service Failed";
                    }
                } else {
                    message = "Service Failed";
                }
                if (Status.equalsIgnoreCase("true")) {
                    session.logoutUser();
                    Intent intent = new Intent(TermsOfUseActivity.this, Login.class);
                    // if you are checking for this in your other Activities
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else {

                    try {


                        String msg = logoutJson.getString("message");
                        System.out.print(msg);

                        HashMap<String, String> user = session.getUserDetails();
                        String lang = user.get(UserSessionManager.TAG_language);

                        if (lang.equalsIgnoreCase("sw")) {
                            System.out.print("Sw_l");
                            if (msg.equalsIgnoreCase("Autentisering misslyckades")) {
                                Button btnLogout;
                                TextView tvMessage;
                                final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", TermsOfUseActivity.this);
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
                                final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", TermsOfUseActivity.this);
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

                    if (logoutJson.has(Const.Params.Message)) {
                        message = logoutJson.getString(Const.Params.Message).toString();
                    }
                    SmartClassUtil.showToast(getApplicationContext(), message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    class SaveTermList extends AsyncTask<String, String, String> {

        JSONObject TermListObject = new JSONObject();


         private String url = Base_url + "mobile_api/save_term_details";


        public String updatedTime = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        protected String doInBackground(String... args) {


            JSONObject paramsObject = new JSONObject();
            try {
                paramsObject.put(Const.Params.AcceptStatus, status);
                paramsObject.put("securityKey", sec_key);
                paramsObject.put(Const.Params.LoginUserId, user_id);
                paramsObject.put(Const.Params.PlarForm, platform);
                paramsObject.put(Const.Params.AcceptedTerm, ids);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {

                TermListObject = new JSONObject(WebServeRequest.postJSONRequest(url, paramsObject.toString()));
                Log.e("ul", TermListObject.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }


            return TermListObject.toString();
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            try {
                JSONObject jsonObj = new JSONObject(results);

                //Toast.makeText(TermsOfUseActivity.this, ""+results, Toast.LENGTH_SHORT).show();

                String Status = jsonObj.getString("status");

                if (Status.equalsIgnoreCase("true")) {

                    Toast.makeText(TermsOfUseActivity.this, getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();

                    if(status=="no")
                    {
                        new LogoutAPI().execute();
                    }

                    Intent in = new Intent(getApplicationContext(), Drawer.class);
                    in.putExtra("user_type", user_type);
                    startActivity(in);
                    finish();


                } else {
                    try {


                        String msg = jsonObj.getString("message");
                        System.out.print(msg);

                        HashMap<String, String> user = session.getUserDetails();
                        String lang = user.get(UserSessionManager.TAG_language);

                        if (lang.equalsIgnoreCase("sw")) {
                            System.out.print("Sw_l");
                            if (msg.equalsIgnoreCase("Autentisering misslyckades")) {
                                Button btnLogout;
                                TextView tvMessage;
                                final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", TermsOfUseActivity.this);
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
                                final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", TermsOfUseActivity.this);
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


                    Intent in = new Intent(getApplicationContext(), Drawer.class);
                    in.putExtra("user_type", user_type);
                    startActivity(in);
                    finish();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            dialog.dismiss();



        }

    }




}
