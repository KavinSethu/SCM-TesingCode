package com.pnf.elar.app.activity.schedule;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elar.attandance.list.AddretrivalNotes;
import com.elar.attandance.list.UserProfileFragment;
import com.elar.util.SmartClassUtil;
import com.pnf.elar.app.Drawer;
import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.ParentChildComponent;
import com.pnf.elar.app.R;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.API;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.ToStringConverterFactory;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.AppLog;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;
import com.pnf.elar.app.util.ImageLoadingUtils;
import com.soundcloud.android.crop.Crop;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.elar.attandance.list.AddretrivalNotes.CHOOSE_PHOTO;

public class AddRetriverParentActivity extends AppCompatActivity {


    UserSessionManager session;
    String auth_key = "", securityKey = "H67jdS7wwfh", language = "", Base_url = "";
    String contactNumber = "", fromDate = "", toDate = "", imageUrl, note = "", retrieverName = "";
    String studentId = "", regId = "", user_typ = "", name, child_id = "", child_name = "", child_img = "", imagePath, i_path,
            encodedString = "", userId = "";
    String retrieverId = "";
    /*
        EditText edtForRname, edtForContact, edtForNotes;
    */
    String sw_toDateError = "Välj giltigt hittills";
    String en_toDateError = "Select valid to date";
    String sw_fromDateError = "Välj giltigt datum";
    String en_fromDateError = "Select valid date";
    String selectedDate = "";
    String apiDate = "";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");

    Calendar fromCalendar;
    DatePickerDialog.OnDateSetListener fromDateDg;
    String fromDateVal = "";

    Calendar toCalendar;
    DatePickerDialog.OnDateSetListener toDateDg;
    String toDateVal = "";

    ImageLoadingUtils utils;

    boolean modified = false;

    @Bind(R.id.txtForUserName)
    TextView txtForUserName;
    @Bind(R.id.Date)
    TextView Date;
    @Bind(R.id.txtForDateFrom)
    TextView txtForDateFrom;
    @Bind(R.id.To)
    TextView To;
    @Bind(R.id.txtForToDate)
    TextView txtForToDate;
    @Bind(R.id.layoutForEdts)
    LinearLayout layoutForEdts;
    @Bind(R.id.txt1)
    TextView txt1;
    @Bind(R.id.edtForRname)
    EditText edtForRname;
    @Bind(R.id.txt2)
    TextView txt2;
    @Bind(R.id.edtForContact)
    EditText edtForContact;
    @Bind(R.id.Portrait)
    TextView Portrait;
    @Bind(R.id.profileImage)
    ImageView profileImage;
    @Bind(R.id.layout1)
    LinearLayout layout1;
    @Bind(R.id.txt3)
    TextView txt3;
    @Bind(R.id.editText1)
    EditText edtForNotes;
    @Bind(R.id.layout3)
    LinearLayout layout3;
    @Bind(R.id.btnForSave)
    Button btnForSave;
    @Bind(R.id.btnForCancel)
    Button btnForCancel;
    @Bind(R.id.retrival_note)
    RelativeLayout retrivalNote;
    @Bind(R.id.activity_add_retriver_parent)
    LinearLayout activityAddRetriverParent;
    @Bind(R.id.removeRetBtn)
    ImageView removeRetBtn;

    Context context;
    MyCustomProgressDialog dialog;

    boolean refresh = false;
    String tag = "retriver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_retriver_parent);
        ButterKnife.bind(this);
        initView();
    }

    public void initView() {
        context = getApplicationContext();
        session = new UserSessionManager(AddRetriverParentActivity.this);

        dialog = new MyCustomProgressDialog(AddRetriverParentActivity.this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);

        HashMap<String, String> user = session.getUserDetails();
        language = user.get(UserSessionManager.TAG_language);
        auth_key = user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        regId = user.get(UserSessionManager.TAG_regId);
        user_typ = user.get(UserSessionManager.TAG_user_type);
        child_id = user.get(UserSessionManager.TAG_child_id);
        child_img = user.get(UserSessionManager.TAG_child_image);
        userId = user.get(UserSessionManager.TAG_user_id);
        if (user_typ.equalsIgnoreCase("Parent")) {
            studentId = child_id;
            child_name = user.get(UserSessionManager.TAG_cld_nm);
            removeRetBtn.setVisibility(View.VISIBLE);

        } else {
            studentId = user.get(UserSessionManager.TAG_user_id);
            txtForDateFrom.setEnabled(false);
            txtForToDate.setEnabled(false);
            edtForContact.setEnabled(false);
            edtForNotes.setEnabled(false);
            edtForRname.setEnabled(false);
            profileImage.setEnabled(false);
            btnForCancel.setVisibility(View.GONE);
            btnForSave.setVisibility(View.GONE);


        }
        selectedDate = getIntent().getStringExtra(Const.CommonParams.SELECTED_DATE);
        apiDate = getIntent().getStringExtra(Const.CommonParams.API_DATE);
        System.out.println("apidate" + apiDate + " " + selectedDate);

        if (language.equalsIgnoreCase("sw")) {
            txtForUserName.setText("Annan hämtare\n " + child_name);


            Date.setText("Datum:");
            To.setText("Till:");
            txt1.setText("Hämtarsnamn:");
            Portrait.setText("Porträtt: ");
            txt2.setText("Kontaktuppgifter:");
            txt3.setText("Beskrivning:");
            btnForCancel.setText("Avbryt");
            btnForSave.setText("Spara");
            edtForNotes.setHint("Skriv här..");
/*
            txtForDateFrom.setText("ÅÅÅÅ-MM-DD");
*/
            txtForToDate.setText("ÅÅÅÅ-MM-DD");


        } else {
            txtForUserName.setText("Edit Retrival Note\n" + child_name);
        }


        new GetRetrieverData().execute();
        setFromToDate();
    }

    public void setFromToDate() {
        try {
            Log.e("sdsd", selectedDate);
            final java.util.Date selDate = df.parse(selectedDate);
            fromCalendar = Calendar.getInstance();
            fromCalendar.setTime(selDate);
            fromDateVal = sdf.format(fromCalendar.getTime());
            txtForDateFrom.setText(sdf.format(fromCalendar.getTime()));
            fromDateDg = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    try {
                        fromCalendar.set(Calendar.YEAR, year);
                        fromCalendar.set(Calendar.MONTH, monthOfYear);
                        fromCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        Date fromDate = sdf.parse(sdf.format(fromCalendar.getTime()));
                        if (fromDate.after(selDate) || (fromDate.equals(selDate))) {
                            fromDateVal = sdf.format(fromCalendar.getTime());
                            txtForDateFrom.setText(sdf.format(fromCalendar.getTime()));
                        } else {
                            if (language.equals("en")) {
                                SmartClassUtil.showToast(context, en_fromDateError);
                            } else {
                                SmartClassUtil.showToast(context, sw_fromDateError);
                            }
                            fromCalendar.setTime(selDate);
                            fromDateVal = sdf.format(fromCalendar.getTime());
                            txtForDateFrom.setText(sdf.format(fromCalendar.getTime()));
                        }
                        if (language.equals("sw")) {
                            txtForToDate.setText("ÅÅÅÅ-MM-DD");
                        } else {
                            txtForToDate.setText("YYY-MM-DD");
                        }
                    } catch (Exception e) {
                        AppLog.handleException(tag, e);
                    }
                }

            };

            toCalendar = Calendar.getInstance();
            toCalendar.setTime(fromCalendar.getTime());
            toDateVal = sdf.format(toCalendar.getTime());
            toDateDg = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    try {
                        // TODO Auto-generated method stub
                        toCalendar.set(Calendar.YEAR, year);
                        toCalendar.set(Calendar.MONTH, monthOfYear);
                        toCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        Date fromDate = sdf.parse(sdf.format(fromCalendar.getTime()));
                        Date toDate = sdf.parse(sdf.format(toCalendar.getTime()));
                        if (toDate.after(fromDate) || toDate.equals(fromDate)) {
                            toDateVal = sdf.format(toCalendar.getTime());
                            txtForToDate.setText(sdf.format(toCalendar.getTime()));
                        } else {
                            if (("en").equals(language)) {
                                SmartClassUtil.showToast(context, en_toDateError);
                            } else {
                                SmartClassUtil.showToast(context, sw_toDateError);
                            }
                            toCalendar.setTime(fromCalendar.getTime());
                            toDateVal = sdf.format(toCalendar.getTime());
                            txtForToDate.setText(sdf.format(toCalendar.getTime()));
                        }
                    } catch (Exception e) {
                        AppLog.handleException(tag, e);
                    }
                }

            };
        } catch (Exception e) {
            AppLog.handleException(tag, e);
        }
    }

    @OnClick({R.id.txtForDateFrom, R.id.txtForToDate, R.id.btnForCancel, R.id.btnForSave, R.id.profileImage,R.id.removeRetBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtForDateFrom:
                try {
                    new DatePickerDialog(AddRetriverParentActivity.this, fromDateDg, fromCalendar
                            .get(Calendar.YEAR), fromCalendar.get(Calendar.MONTH),
                            fromCalendar.get(Calendar.DAY_OF_MONTH)).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.txtForToDate:
                try {
                    new DatePickerDialog(AddRetriverParentActivity.this, toDateDg, toCalendar
                            .get(Calendar.YEAR), toCalendar.get(Calendar.MONTH),
                            toCalendar.get(Calendar.DAY_OF_MONTH)).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnForCancel:
                setRefresh();
                break;
            case R.id.btnForSave:

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

                retrieverName = edtForRname.getText().toString().trim();
                contactNumber = edtForContact.getText().toString().trim();
                note = edtForNotes.getText().toString().trim();

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
                        /*AsyncT asyncT = ;*/
                        new AsyncT().execute();
                    } catch (Exception e) {
                        // TODO: handle exception
//						Toast.makeText(getActivity(), "--- " + e,
//								Toast.LENGTH_LONG).show();
                    }

                }

                break;
            case R.id.profileImage:

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, CHOOSE_PHOTO);


                break;
            case R.id.removeRetBtn:
                callDeleteApi();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setRefresh();
    }

    public void setRefresh() {
        try {
            Intent mainIntent = new Intent();
            mainIntent.putExtra(Const.CommonParams.REFRESH, modified);
            setResult(RESULT_OK, mainIntent);
            finish();
        } catch (Exception e) {
            AppLog.handleException(tag, e);
        }
    }


    class GetRetrieverData extends AsyncTask<String, String, String> {

        private String url = Base_url + "lms_api/retrivals/get_retriver_note",
                Status = "";
        String rName, rContact, note, rImage, created;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

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
                String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode(securityKey, "UTF-8") +
                        "&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_key, "UTF-8") +
                        "&" + Const.Params.Language + "=" + URLEncoder.encode(language, "UTF-8") +
                        "&" + Const.Params.UserTypeApp + "=" + URLEncoder.encode("android", "UTF-8") +
                        "&" + Const.Params.DATE + "=" + URLEncoder.encode(apiDate, "UTF-8") +
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

                String messgage = "";
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
                            rName = Retriever.getString("retriever_name");

                            retrieverId = Retriever.getString("id");
/*
                            rName = URLDecoder.decode(data.getString("retriever_name")).toString();
*/
                            //	rName = Retriever.getString("retriever_name");
                            /*note = URLDecoder.decode(data.getString("description")).toString();*/
                            note = Retriever.getString("description");
                            //	note = Retriever.getString("description");
                            rImage = Base_url + Retriever.getString("retriever_image");
                            created = Retriever.getString("created");

                            new ImageLoadTaskcliptwo(rImage, profileImage).execute();
//					}
                            txtForToDate.setText(created);
                            edtForContact.setText("" + rContact);
                            edtForNotes.setText("" + note);
                            edtForRname.setText("" + rName);



                            if(user_typ.equalsIgnoreCase("student")) {



                                JSONObject studentObj=data.getJSONObject("student");
                                child_name=studentObj.getString("student_name");

                                if (language.equalsIgnoreCase("sw")) {
                                    txtForUserName.setText("Annan hämtare\n " + child_name);
                                } else {
                                    txtForUserName.setText("Edit Retrival Note\n" + child_name);
                                }
                            }


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
                                        final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", AddRetriverParentActivity.this);
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
                                        final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", AddRetriverParentActivity.this);
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
                            if (jsonObj.has(Const.Params.Message)) {
                                messgage = jsonObj.getString(Const.Params.Message);
                            }
                        }

                    } else {
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
        new Crop(source).output(outputUri).asSquare().start(AddRetriverParentActivity.this);
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
                Toast.makeText(context, Crop.getError(result).getMessage(),
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
        utils = new ImageLoadingUtils(context);
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
        Cursor cursor = getContentResolver().query(contentURI, null, null,
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

    class AsyncT extends AsyncTask<String, String, String> {
        String status = "", message = "";

        String url = Base_url
                + "lms_api/retrivals/update_retriver_note";
        String updateResponse = "";


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

            dialog.show();
        }


        @Override
        protected String doInBackground(String... args) {
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
                jsonobj.put("language", language);
                jsonobj.put("note", URLEncoder.encode(note).toString());
                jsonobj.put("retriver_name", URLEncoder.encode(retrieverName).toString());
                jsonobj.put("securityKey", securityKey);
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
            return updateResponse;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
/*
            Log.e("responseServer", "" + responseServer);
*/
            dialog.dismiss();
            try {
                if (updateResponse.equals("") || updateResponse.equals(null)) {
                    /*status = "false";
                    message = "updated successfully";
                    Log.e("response", "response -----" + updateResponse);*/
                } else {
                    JSONObject json = new JSONObject(updateResponse);
                    if (json.has("status")) {
                        status = json.getString(Const.Params.Status);

                        if (json.has(Const.Params.Message)) {
                            message = json.getString(Const.Params.Message);
                        }
/*
                        Log.e("response", "response -----" + updateResponse);
*/
                    } else {
                    }

                }


                if (status.equals("true")) {

                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                    modified = true;
                    setRefresh();


                } else {


                    SmartClassUtil.showToast(context, message);


                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void callDeleteApi() {
        try {
            dialog.show();

            if (SmartClassUtil.isNetworkAvailable(context)) {

                    /*callAspectsApi();*/

                JSONObject reqJsonObj = new JSONObject();
                reqJsonObj.put(Const.Params.SecurityKey, securityKey);
                reqJsonObj.put(Const.Params.LoginUserId, userId);
                reqJsonObj.put(Const.Params.Language, language);
                reqJsonObj.put(Const.Params.ID, retrieverId);

                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Base_url)
                        .addConverterFactory(new ToStringConverterFactory())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqJsonObj.toString());
                Call<String> response = retrofit.create(API.class).delete_retriever_desc(body);
                response.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        dialog.dismiss();
                        try {
                            if (response.body() != null) {
                                String activityResponse = response.body();
                                JSONObject activityJson = new JSONObject();

                                if (activityResponse != null && activityResponse.trim().length() != 0) {

                                    activityJson = new JSONObject(activityResponse);
                                    if (activityJson.getString(Const.Params.Status).equalsIgnoreCase(Const.Params.TRUE)) {
                                        refresh = true;
                                        if (activityJson.has(Const.Params.MSG)) {
                                            SmartClassUtil.showToast(context, activityJson.getString(Const.Params.MSG));
                                        }

                                        Intent mainIntent = new Intent();
                                        mainIntent.putExtra(Const.CommonParams.REFRESH, refresh);
                                        setResult(RESULT_OK, mainIntent);
                                        finish();
                                    } else {
                                        if (activityJson.has(Const.Params.MSG)) {
                                            SmartClassUtil.showToast(context, activityJson.getString(Const.Params.MSG));
                                        }
                                    }


                                } else {

                                }

                            }
                        } catch (Exception e) {
                            AppLog.handleException(tag, e);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        dialog.dismiss();
                    }
                });

            } else {
                SmartClassUtil.showToast(context, language);

            }


        } catch (Exception e) {
            AppLog.handleException(tag, e);
        }
    }
}
