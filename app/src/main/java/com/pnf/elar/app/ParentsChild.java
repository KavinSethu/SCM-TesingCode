package com.pnf.elar.app;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/*import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.elar.util.NetworkUtil;
import com.pnf.elar.app.activity.schedule.AsynchronousActivity;
import com.pnf.elar.app.fragments.WebFragment;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;

@SuppressWarnings("deprecation")
@SuppressLint("NewApi")
public class ParentsChild extends Fragment {
    UserSessionManager session;
    View v;
    String lang, username, auth_token, auth_token_new = null, user_name,
            password, new_Authntication, Base_url, regId, SwitchPar, newTok,
            OldTok;
    ListView im_vdo;
    ParentChid parentch;

    LinearLayout trackBusLayout;
    TextView smartnotes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_parents_child, container, false);

        session = new UserSessionManager(getActivity());

        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        auth_token = user.get(UserSessionManager.TAG_Authntication_token);
        auth_token_new = user
                .get(UserSessionManager.TAG_Authntication_new_tokn);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        user_name = user.get(UserSessionManager.TAG_username);
        password = user.get(UserSessionManager.TAG_password);
        regId = user.get(UserSessionManager.TAG_regId);
        new_Authntication = user
                .get(UserSessionManager.TAG_Authntication_new_tokn);
        SwitchPar = user.get(UserSessionManager.TAG_SwitchParent);
        newTok = user.get(UserSessionManager.TAG_newTokn);
        OldTok = user.get(UserSessionManager.TAG_OldTokn);
        // ///////Actionbar title /////////

        trackBusLayout=(LinearLayout)v.findViewById(R.id.trackBusLayout);
        trackBusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* String url = "https://map-html-kishoreindraganti.c9users.io/hello-world.html";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);*/
                Intent schedeluIntent = new Intent(getActivity(), AsynchronousActivity.class);
                getActivity().startActivity(schedeluIntent);


            }
        });


        if (lang.equalsIgnoreCase("sw")) {
            ((Drawer) getActivity()).setActionBarTitle("Barnkonton");
        } else {
            ((Drawer) getActivity()).setActionBarTitle("Child accounts");

        }
        ((Drawer) getActivity()).Hideserch();
        ((Drawer) getActivity()).HideRefresh();
        ((Drawer) getActivity()).Hidebackbutton();
        //	((Drawer)getActivity()).forparent();

        // ////////////////////////////
        session.createUserparentorchild("");

        im_vdo = (ListView) v.findViewById(R.id.ParentsChild);

        if (SwitchPar != null && !SwitchPar.isEmpty()) {
            if (SwitchPar.equalsIgnoreCase("SwitchParent")) {
                String Sw = "Swe";
                // session.createUserSwitchParent(Sw, "yes" ,"no");
                // Log.i("slslslsl", "slslslsl");
                // Log.i("newTok", newTok);
                // Log.i("OldTok", OldTok);

                new Swich_user(newTok).execute();
            } else {
                Log.i("yuyuyul", "yuyuyu");
                new prntchld().execute();
            }
        } else {
            new prntchld().execute();
        }

        return v;
    }

    // ///////////
    class prntchld extends AsyncTask<String, String, String> {

        ArrayList<String[]> component_name = new ArrayList<String[]>();
        ArrayList<String[]> component_image = new ArrayList<String[]>();
        ArrayList<String[]> component_id = new ArrayList<String[]>();

        private String url = Base_url + "lms_api/users/login";
        private static final String TAG_STATUS = "status";
        private static final String TAG_TOKEN = "authentication_token";
        private static final String TAG_Component = "ComComponent";
        private static final String TAG_User = "User";
        private static final String TAG_other_accounts = "other_accounts";
        private static final String TAG_name = "name";
        private static final String TAG_img_path = "img_path";
        private static final String TAG_id = "id";
        private static final String TAG_USR_Firstname = "USR_Firstname";
        private static final String TAG_customer_name = "customer_name";
        private static final String TAG_user_type = "user_type";
        private static final String TAG_USER_Firstname = "USR_Firstname";
        private static final String TAG_USR_Email = "USR_Email";
        private static final String TAG_username = "username";
        private static final String TAG_USR_Lastname = "USR_Lastname";
        private static final String TAG_image = "USR_image";
        private static final String TAG_children = "children";
        private static final String TAG_USR_image = "USR_image";
        private static final String TAG_childFirstname = "USR_FirstName";
        private static final String TAG_childlastname = "USR_LastName";
        private static final String TAG_child_ComComponent = "ComComponent";

        private MyCustomProgressDialog dialog;

        String first_name, last_name, user_type, customer_name,
                first_name_header, last_name_header, user_type_header,
                customer_name_header, image_header, main_image_header,
                parent_id;

        String Login_Email, Login_Password, Security = "H67jdS7wwfh";
        String Status, token, Remember_me;
        String[] component, image, other_accounts, name, first_name1, lastName,
                user_type1, customer_name1, username1, new_auth_tokn,
                children_name, USR_image, nm, img, id, Child_Token, Child_id;

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

            Log.i("user_name", user_name);
            Log.i("password", password);
            Log.i("parent child", "parent child");


            String loginResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.UserName + "=" + URLEncoder.encode(user_name, "UTF-8") +
                        "&" + Const.Params.Password + "=" + URLEncoder.encode(password, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.DeviceTokenApp + "=" + URLEncoder.encode(regId, "UTF-8") +
                        "&" + Const.Params.UserTypeApp + "=" + URLEncoder.encode("android", "UTF-8");


                loginResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }

          /*  JSONParser jsonParser = new JSONParser();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("securityKey", Security));
            params.add(new BasicNameValuePair("username", user_name));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("language", lang));
            params.add(new BasicNameValuePair("device_token_app", regId));
            params.add(new BasicNameValuePair("user_type_app", "android"));

            // Log.d("lng1", language);

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

            Log.d("otheraccount......", json.toString());

            try {

                JSONObject jsonObj = new JSONObject(json.toString());

                Status = jsonObj.getString(TAG_STATUS);
                // ////////////

                JSONArray children = jsonObj.optJSONArray(TAG_children);

                children_name = new String[children.length()];
                USR_image = new String[children.length()];
                Child_Token = new String[children.length()];
                Child_id = new String[children.length()];

                for (int j = 0; j < children.length(); j++) {
                    JSONObject d = children.getJSONObject(j);

                    children_name[j] = d.optString(TAG_childFirstname) + "  "
                            + d.optString(TAG_childlastname);
                    USR_image[j] = Base_url + d.optString(TAG_USR_image);
                    Child_Token[j] = d.optString(TAG_TOKEN);
                    Child_id[j] = d.optString(TAG_id);

                }

                token = jsonObj.getString(TAG_TOKEN);

                JSONObject user = jsonObj.optJSONObject(TAG_User);

                first_name_header = user.getString(TAG_USR_Firstname);
                last_name_header = user.getString(TAG_USR_Lastname);
                user_type_header = user.getString(TAG_user_type);
                customer_name_header = user.getString(TAG_customer_name);
                image_header = user.getString(TAG_image);
                parent_id = user.getString(TAG_id);
                main_image_header = Base_url + image_header;
                // ////////////////////

                // // /////////////////
                JSONArray comp = jsonObj.optJSONArray(TAG_other_accounts);
                first_name1 = new String[comp.length()];
                lastName = new String[comp.length()];
                user_type1 = new String[comp.length()];
                customer_name1 = new String[comp.length()];
                username1 = new String[comp.length()];
                new_auth_tokn = new String[comp.length()];

                for (int j = 0; j < comp.length(); j++) {
                    JSONObject c = comp.getJSONObject(j);

                    first_name1[j] = c.optString(TAG_USR_Firstname);
                    lastName[j] = c.getString(TAG_USR_Lastname);
                    user_type1[j] = c.optString(TAG_user_type);
                    customer_name1[j] = c.optString(TAG_customer_name);
                    username1[j] = c.optString(TAG_username);
                    new_auth_tokn[j] = c.optString(TAG_TOKEN);

                }

                if (Status.equalsIgnoreCase("true")) {

                } else {
                    // Log.i("res", "false");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
*/
            return loginResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {

                JSONObject jsonObject = new JSONObject();

                if (results != null && !results.isEmpty()) {
                    jsonObject = new JSONObject(results);
                    if (jsonObject.has(Const.Params.Status)) {
                        Status = jsonObject.getString(Const.Params.Status);
                    } else {
                        Status = "false";
                    }
                } else {
                    Status = "false";
                }
                // Log.i("main_image_header", main_image_header);

                if (Status.equalsIgnoreCase("true")) {
                    JSONObject jsonObj = new JSONObject(jsonObject.toString());

                    Status = jsonObj.getString(TAG_STATUS);
                    // ////////////

                    JSONArray children = jsonObj.optJSONArray(TAG_children);

                    children_name = new String[children.length()];
                    USR_image = new String[children.length()];
                    Child_Token = new String[children.length()];
                    Child_id = new String[children.length()];
                    first_name1 = new String[children.length()];
                    lastName = new String[children.length()];
                    for (int j = 0; j < children.length(); j++) {
                        JSONObject d = children.getJSONObject(j);

                        children_name[j] = d.optString(TAG_childFirstname) + "  "
                                + d.optString(TAG_childlastname);
                        USR_image[j] = Base_url + d.optString(TAG_USR_image);
                        Child_Token[j] = d.optString(TAG_TOKEN);
                        Child_id[j] = d.optString(TAG_id);
                        first_name1[j] = d.optString(TAG_childFirstname);
                        lastName[j] = d.getString(TAG_childlastname);
                    }

                    token = jsonObj.getString(TAG_TOKEN);

                    JSONObject user = jsonObj.optJSONObject(TAG_User);

                    first_name_header = user.getString(TAG_USR_Firstname);
                    last_name_header = user.getString(TAG_USR_Lastname);
                    user_type_header = user.getString(TAG_user_type);
                    customer_name_header = user.getString(TAG_customer_name);
                    image_header = user.getString(TAG_image);
                    parent_id = user.getString(TAG_id);
                    main_image_header = Base_url + image_header;
                    // ////////////////////

                    // // /////////////////
                    JSONArray comp = jsonObj.optJSONArray(TAG_other_accounts);

                    user_type1 = new String[comp.length()];
                    customer_name1 = new String[comp.length()];
                    username1 = new String[comp.length()];
                    new_auth_tokn = new String[comp.length()];

                    for (int j = 0; j < comp.length(); j++) {
                        JSONObject c = comp.getJSONObject(j);
                        user_type1[j] = c.optString(TAG_user_type);
                        customer_name1[j] = c.optString(TAG_customer_name);
                        username1[j] = c.optString(TAG_username);
                        new_auth_tokn[j] = c.optString(TAG_TOKEN);

                    }


                    parentch = new ParentChid(getActivity(), children_name,
                            USR_image, Base_url);
                    im_vdo.setAdapter(parentch);

                } else {

                    // failed to creat

                }
            } catch (Exception e) {
            }
            im_vdo.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    try {


                        // TODO Auto-generated method stub

                        // Toast.makeText(getActivity(), Child_Token[position],
                        // Toast.LENGTH_SHORT).show();
                        Log.e("option", "page");

                        if(NetworkUtil.getInstance(getActivity()).isInternet()) {

                            session.childrenauthoken(parent_id, Child_Token[position],
                                    children_name[position], Child_id[position], USR_image[position], first_name1[position], lastName[position]);

                            session.putSelectedChildImage(USR_image[position]);
                            Log.d("sess_img", "" + USR_image[position]);


                            FragmentManager fragmentManager = getFragmentManager();
                            ParentChildComponent Setting_frg = new ParentChildComponent();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", parent_id);
                            bundle.putString("child_token", Child_Token[position]);

                            bundle.putString("child_image", USR_image[position]);


                            Setting_frg.setArguments(bundle);
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            ft.replace(R.id.content_frame, Setting_frg);
                            ft.commit();

                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }


                }
            });
        }

    }

    // /////////////////////
    class Swich_user extends AsyncTask<String, String, String> {

        String new_auth, first_name_header, last_name_header,
                customer_name_header, image_header, main_image_header,
                parent_id;
        private MyCustomProgressDialog dialog;
        private String url = Base_url + "lms_api/users/switch_user";

        private static final String TAG_STATUS = "status";
        private static final String TAG_TOKEN = "authentication_token";
        private static final String TAG_Component = "ComComponent";
        private static final String TAG_User = "User";
        private static final String TAG_username = "username";
        private static final String TAG_USR_Lastname = "USR_Lastname";
        private static final String TAG_user_type = "user_type";
        private static final String TAG_user_type_sw = "user_type_sw";
        private static final String TAG_other_accounts = "other_accounts";
        private static final String TAG_image = "USR_image";
        private static final String TAG_customer_name = "customer_name";
        private static final String TAG_USR_Firstname = "USR_Firstname";
        private static final String TAG_id = "id";
        private static final String TAG_name = "name";
        private static final String TAG_name_sw = "name_sw";
        private static final String TAG_img_path = "img_path";
        private static final String TAG_news_count = "news_count";
        private static final String TAG_eduBlog_count = "eduBlog_count";
        private static final String TAG_USR_image = "USR_image";
        private static final String TAG_childFirstname = "USR_FirstName";
        private static final String TAG_childlastname = "USR_LastName";
        private static final String TAG_child_ComComponent = "ComComponent";
        private static final String TAG_children = "children";

        String Login_Email, Login_Password, Security = "H67jdS7wwfh";
        String Status, token, first_name, user_type, Remember_me, user_id,
                user_type_sw;
        String[] component, image, other_accounts, name, first_name1, lastName,
                customer_name1, user_type1, username1, new_auth_tokn,
                profile_images, user_type1_sw, children_name, USR_image,
                Child_Token, Child_id;

        // CustomAdapter_drawer adapter2; // /////////////// new code

        public Swich_user(String new_toknone) {
            // TODO Auto-generated constructor stub
            this.new_auth = new_toknone;
        }

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
            String switchResponse = "";
            try {
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(Security, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8") +
                        "&" + Const.Params.CurrentAuthToken + "=" + URLEncoder.encode(OldTok, "UTF-8") +
                        "&" + Const.Params.UserTypeApp + "=" + URLEncoder.encode("android", "UTF-8") +
                        "&" + Const.Params.DeviceTokenApp + "=" + URLEncoder.encode(regId, "UTF-8");


                switchResponse = FormDataWebservice.excutePost(url, urlParams, Const.MethodType.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*JSONParser jsonParser = new JSONParser();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // Log.i("::::::::", regId);
            Log.i("new_auth", new_auth);

            Log.i("purana tokn", auth_token);

            params.add(new BasicNameValuePair("securityKey", Security));
            params.add(new BasicNameValuePair("authentication_token", newTok));
            params.add(new BasicNameValuePair("language", lang));
            params.add(new BasicNameValuePair("current_authentication_token",
                    OldTok));
            params.add(new BasicNameValuePair("user_type_app", "android"));
            params.add(new BasicNameValuePair("device_token_app", regId));

            Log.d("Create Response", params.toString());

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

            // Log.d("Create Response.....", json.toString());

            try {

                JSONObject jsonObj = new JSONObject(json.toString());

                Status = jsonObj.getString(TAG_STATUS).toString();

                Log.i("status-----...", Status);
                token = jsonObj.getString(TAG_TOKEN).toString();

                JSONArray children = new JSONArray();

                if (jsonObj.has(TAG_children)) {
                    children = jsonObj.optJSONArray(TAG_children);
                } else {

                }


                first_name1 = new String[children.length()];
                lastName = new String[children.length()];
                children_name = new String[children.length()];

                USR_image = new String[children.length()];
                Child_Token = new String[children.length()];
                Child_id = new String[children.length()];

                for (int j = 0; j < children.length(); j++) {
                    JSONObject d = children.getJSONObject(j);

                    children_name[j] = d.optString(TAG_childFirstname) + "  "
                            + d.optString(TAG_childlastname);

                    first_name1[j]=d.optString(TAG_childFirstname);
                    lastName[j]=d.optString(TAG_childlastname);

                    USR_image[j] = Base_url + d.optString(TAG_USR_image);
                    Child_Token[j] = d.optString(TAG_TOKEN);
                    Child_id[j] = d.optString(TAG_id);
                }

                JSONObject user = jsonObj.optJSONObject(TAG_User);

                parent_id = user.getString(TAG_id);

            } catch (JSONException e) {
                e.printStackTrace();
            }
*/
            return switchResponse;
        }

        protected void onPostExecute(String results) {
            // dismiss the dialog once done
            dialog.dismiss();
            try {
                JSONObject jsonObj = new JSONObject();

                if (results != null && !results.isEmpty()) {

                    jsonObj = new JSONObject(results);
                    if (jsonObj.has(Const.Params.Status)) {

                        Status = jsonObj.getString(TAG_STATUS).toString();
                    } else {
                        Status = "false";
                    }
                } else {
                    Status = "false";
                }

                if (Status.equalsIgnoreCase("true")) {


                    Log.i("status-----...", Status);
                    token = jsonObj.getString(TAG_TOKEN).toString();

                    JSONArray children = new JSONArray();

                    if (jsonObj.has(TAG_children)) {
                        children = jsonObj.optJSONArray(TAG_children);
                    } else {

                    }
/*
                JSONArray children = jsonObj.optJSONArray(TAG_children);
*/

                    first_name1 = new String[children.length()];
                    lastName = new String[children.length()];
                    children_name = new String[children.length()];

                    USR_image = new String[children.length()];
                    Child_Token = new String[children.length()];
                    Child_id = new String[children.length()];

                    for (int j = 0; j < children.length(); j++) {
                        JSONObject d = children.getJSONObject(j);

                        children_name[j] = d.optString(TAG_childFirstname) + "  "
                                + d.optString(TAG_childlastname);

                        first_name1[j] = d.optString(TAG_childFirstname);
                        lastName[j] = d.optString(TAG_childlastname);

                        USR_image[j] = Base_url + d.optString(TAG_USR_image);
                        Child_Token[j] = d.optString(TAG_TOKEN);
                        Child_id[j] = d.optString(TAG_id);
                    }

                    JSONObject user = jsonObj.optJSONObject(TAG_User);

                    parent_id = user.getString(TAG_id);


                    parentch = new ParentChid(getActivity(), children_name,
                            USR_image, Base_url);
                    im_vdo.setAdapter(parentch);

                } else {

                }
            } catch (Exception e) {
            }

            im_vdo.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub


                    try {
                        if(NetworkUtil.getInstance(getActivity()).isInternet()) {

                            session.childrenauthoken(parent_id, Child_Token[position],
                                    children_name[position], Child_id[position], USR_image[position], first_name1[position], lastName[position]);

                            session.putSelectedChildImage(USR_image[position]);
                            Log.d("sess_img", "" + USR_image[position]);

                            //	Toast.makeText(getActivity(), Child_id[position], Toast.LENGTH_SHORT).show();

                            FragmentManager fragmentManager = getFragmentManager();
                            ParentChildComponent Setting_frg = new ParentChildComponent();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", parent_id);
                            bundle.putString("child_token", Child_Token[position]);
                            bundle.putString("child_image", USR_image[position]);
                            Setting_frg.setArguments(bundle);
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            ft.replace(R.id.content_frame, Setting_frg);
                            ft.commit();
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }
}
