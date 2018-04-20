package com.pnf.elar.app.activity.schedule;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;

import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elar.util.SmartClassUtil;

import com.pnf.elar.app.Bo.schedule.FoodMenuBo;

import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.R;
import com.pnf.elar.app.UILApplication;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.API;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.ToStringConverterFactory;

import com.pnf.elar.app.adapter.schedule.FoodMenuAdapter;
import com.pnf.elar.app.util.Base64;
import com.pnf.elar.app.util.Const;

import com.pnf.elar.app.util.FileUpload.GetPath;

import org.json.JSONObject;


import java.io.ByteArrayOutputStream;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

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

public class AddFoodMenuActivity extends AppCompatActivity {
    @Bind(R.id.backbtnImage)
    ImageView backbtnImage;
    @Bind(R.id.headerNameText)
    TextView headerNameText;
    @Bind(R.id.saveImage)
    ImageView saveImage;
    @Bind(R.id.foodMenuRv)
    RecyclerView foodMenuRv;
    @Bind(R.id.startHeaderText1)
    TextView startHeaderText1;
    @Bind(R.id.startDateText1)
    TextView startDateText1;
    @Bind(R.id.endDateHeaderText1)
    TextView endDateHeaderText1;
    @Bind(R.id.endDateText1)
    TextView endDateText1;
    @Bind(R.id.uploadText)
    TextView uploadText;
    @Bind(R.id.uploadIconImage)
    ImageView uploadIconImage;
    @Bind(R.id.uploadImage)
    ImageView uploadImage;
    @Bind(R.id.activity_add_notes)
    LinearLayout activityAddNotes;
    @Bind(R.id.noFilesText)
    TextView noFilesText;
    @Bind(R.id.startLineView)
    View startLineView;
    @Bind(R.id.endLineView)
    View endLineView;
    @Bind(R.id.uploadFoodMenu)
    RelativeLayout uploadFoodMenu;
    @Bind(R.id.tv_uploadFoodMenu)
    TextView tv_uploadFoodMenu;
    @Bind(R.id.header_upload)
    TextView header_upload;

    Context context;
    UserSessionManager session;
    HashMap<String, String> user;
    String language = "", auth_token = "", base_url = "", user_id = "", securityKey = "H67jdS7wwfh", fileName = "", userType = "";

    FoodMenuAdapter foodMenuAdapter;
    List<FoodMenuBo.FoodmenuEntity> foodmenuList = new ArrayList<>();

    Calendar startCalendar;
    DatePickerDialog.OnDateSetListener startDateDg;
    String startDateVal = "";

    Calendar endCalendar;
    DatePickerDialog.OnDateSetListener endDateDg;
    String endDateVal = "";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    MyCustomProgressDialog dialog;

    JSONObject reqJsonObj;
    String encodedString_video = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_food_menu);
        ButterKnife.bind(this);

        getSessionValues();
        initView();
        callPDFListService();

    }

    public void getSessionValues() {
        try {
            context = getApplicationContext();
            session = UILApplication.getUserSessionManager(context);
            user = session.getUserDetails();
            language = user.get(UserSessionManager.TAG_language);
            auth_token = user.get(UserSessionManager.TAG_Authntication_token);
            base_url = user.get(UserSessionManager.TAG_Base_url);
            user_id = user.get(UserSessionManager.TAG_user_id);
            userType = user.get(UserSessionManager.TAG_user_type);

            System.out.println("userType :: " + userType);

            if (("Teacher").equalsIgnoreCase(userType)) {
                showMenu(true);
            } else if (("Parent").equalsIgnoreCase(userType)) {
                user_id = user.get(UserSessionManager.TAG_child_id);
                showMenu(false);
            } else {
                showMenu(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMenu(boolean show) {

        if (show) {
            uploadFoodMenu.setVisibility(View.VISIBLE);
            header_upload.setVisibility(View.VISIBLE);
            startDateText1.setVisibility(View.VISIBLE);
            endDateText1.setVisibility(View.VISIBLE);
            startHeaderText1.setVisibility(View.VISIBLE);
            endDateHeaderText1.setVisibility(View.VISIBLE);
            uploadText.setVisibility(View.VISIBLE);
            uploadIconImage.setVisibility(View.VISIBLE);
            uploadImage.setVisibility(View.VISIBLE);
            startLineView.setVisibility(View.VISIBLE);
            endLineView.setVisibility(View.VISIBLE);

        } else {
            uploadFoodMenu.setVisibility(View.GONE);
            header_upload.setVisibility(View.GONE);
            startDateText1.setVisibility(View.GONE);
            endDateText1.setVisibility(View.GONE);
            startHeaderText1.setVisibility(View.GONE);
            endDateHeaderText1.setVisibility(View.GONE);
            uploadText.setVisibility(View.GONE);
            uploadIconImage.setVisibility(View.GONE);
            uploadImage.setVisibility(View.GONE);
            startLineView.setVisibility(View.GONE);
            endLineView.setVisibility(View.GONE);
        }
    }

    public void initView() {
        try {
            if (language.equalsIgnoreCase("sw")) {
                headerNameText.setText("Matsedel");
                startHeaderText1.setText("Start datum");
                endDateHeaderText1.setText("Slutdatum");
                endDateText1.setText("ÅÅÅÅ/MM/DD");
                startDateText1.setText("ÅÅÅÅ/MM/DD");
                uploadText.setText("Ladda upp fil");
                tv_uploadFoodMenu.setText("Ladda upp");
                header_upload.setText("Överför matsedel");
            } else {
                headerNameText.setText(" Food menu ");
                endDateText1.setText("YYYY/MM/DD");
                startDateText1.setText("YYYY/MM/DD");
                tv_uploadFoodMenu.setText("UPLOAD");
                header_upload.setText("Upload Food Menu");
            }
            /*saveImage.setVisibility(View.VISIBLE);*/

            dialog = new MyCustomProgressDialog(AddFoodMenuActivity.this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);

            setCalendar();
            setAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCalendar() {
        startCalendar = Calendar.getInstance();
/*
        startDateVal = sdf.format(startCalendar.getTime());
*/
        /*startDateText1.setText(sdf.format(startCalendar.getTime()));*/
        startDateDg = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                try {

                    startCalendar.set(Calendar.YEAR, year);
                    startCalendar.set(Calendar.MONTH, monthOfYear);
                    startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    if (Calendar.getInstance().getTime().compareTo(startCalendar.getTime()) == 0 || startCalendar.getTime().before(Calendar.getInstance().getTime())) {

                        startDateVal = sdf.format(startCalendar.getTime());
                        startDateText1.setText(sdf.format(startCalendar.getTime()));
                    } else {

                        String error_msg = "";
                        if (language.equalsIgnoreCase("en")) {
                            error_msg = "Start date cannot be future date";
                        } else {
                            error_msg = "Startdatum kan inte vara framtida tidpunkt";
                        }

                        SmartClassUtil.showToast(context, error_msg);
                    }
                    /*callAllergieService();*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
        endCalendar = Calendar.getInstance();
        /*endDateVal = sdf.format(startCalendar.getTime());*/
/*
        endDateText1.setText(sdf.format(startCalendar.getTime()));
*/
        endDateDg = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                try {

                    endCalendar.set(Calendar.YEAR, year);
                    endCalendar.set(Calendar.MONTH, monthOfYear);
                    endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    if (endCalendar.getTime().after(Calendar.getInstance().getTime())) {

                        endDateVal = sdf.format(endCalendar.getTime());
                        endDateText1.setText(sdf.format(endCalendar.getTime()));
                    } else {

                        String error_msg = "";
                        if (language.equalsIgnoreCase("en")) {
                            error_msg = "End date cannot be lessar than future date";
                        } else {
                            error_msg = "Slutdatum kan inte lessar än framtida tidpunkt";
                        }

                        SmartClassUtil.showToast(context, error_msg);
                    }
                    /*callAllergieService();*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };

    }

    public void setAdapter() {
        try {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            foodMenuRv.setLayoutManager(layoutManager);
            foodMenuAdapter = new FoodMenuAdapter(AddFoodMenuActivity.this, foodmenuList, language, base_url, auth_token, user_id, userType);
            foodMenuRv.setAdapter(foodMenuAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.backbtnImage, R.id.headerNameText, R.id.uploadFoodMenu, R.id.startDateText1, R.id.endDateText1, R.id.uploadIconImage, R.id.uploadImage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backbtnImage:
                finish();
                break;

            case R.id.uploadFoodMenu:

                if (startDateVal.trim().length() == 0) {
                    String msg = "";
                    if (language.equalsIgnoreCase("en")) {
                        msg = "Select start date";

                    } else {

                        msg = "Välj startdatum";

                    }
                    SmartClassUtil.showToast(context, msg);

                } else if (endDateVal.trim().length() == 0) {
                    String msg = "";
                    if (language.equalsIgnoreCase("en")) {
                        msg = "Select end date";

                    } else {

                        msg = "Välj slutdatum";

                    }
                    SmartClassUtil.showToast(context, msg);

                } else if (fileName.length() == 0) {
                    String msg = "";
                    if (language.equalsIgnoreCase("en")) {
                        msg = "Upload pdf file";

                    } else {

                        msg = "Ladda upp pdf-filer";

                    }
                    SmartClassUtil.showToast(context, msg);

                } else {
                    callAddMenuService();

                }

                break;
            case R.id.startDateText1:
                try {
                    new DatePickerDialog(AddFoodMenuActivity.this, startDateDg, startCalendar
                            .get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                            startCalendar.get(Calendar.DAY_OF_MONTH)).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.endDateText1:
                try {
                    new DatePickerDialog(AddFoodMenuActivity.this, endDateDg, endCalendar
                            .get(Calendar.YEAR), endCalendar.get(Calendar.MONTH),
                            endCalendar.get(Calendar.DAY_OF_MONTH)).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.uploadIconImage:
                pickPdfFile();
                break;
            case R.id.uploadImage:
                pickPdfFile();
                break;

        }
    }

    public void pickPdfFile() {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("application/*");
        startActivityForResult(Intent.createChooser(chooseFile, "Choose a file"), Const.CommonParams.ACTIVITY_CHOOSE_FILE);

       /* Intent intent = new Intent(this, FileChooser.class);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        startActivityForResult(intent, Const.CommonParams.ACTIVITY_CHOOSE_FILE);*/
    }

    public void callPDFListService() {

        try {
            reqJsonObj = new JSONObject();
            reqJsonObj.put(Const.Params.SecurityKey, securityKey);
            reqJsonObj.put(Const.Params.LoginUserId, user_id);
            reqJsonObj.put(Const.Params.Language, language);
/*
            {"securityKey":"H67jdS7wwfh","loginUserID":"44","date":"2016-12-29","language":"en","groupType":"my_group"}
*/
            dialog.show();

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(new ToStringConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqJsonObj.toString());
            Call<FoodMenuBo> response = retrofit.create(API.class).getFoodMenuLIst(body);
            response.enqueue(new Callback<FoodMenuBo>() {
                @Override
                public void onResponse(Call<FoodMenuBo> call, Response<FoodMenuBo> response) {
                    dialog.dismiss();

                    if (response.body() != null) {
                        FoodMenuBo foodNoteBo = response.body();
                        if (foodNoteBo.getStatus().equalsIgnoreCase(Const.Params.TRUE)) {

                            if (foodNoteBo.getFoodmenu().size() > 0) {
                                foodMenuRv.setVisibility(View.VISIBLE);
                                noFilesText.setVisibility(View.GONE);
                                foodmenuList.addAll(foodNoteBo.getFoodmenu());
                                foodMenuAdapter.notifyDataSetChanged();
                            } else {

                                if (language.equalsIgnoreCase("sw")) {
                                    noFilesText.setText("Ingen fil uppladdad");
                                }
                                noFilesText.setVisibility(View.VISIBLE);
                                foodMenuRv.setVisibility(View.GONE);
                            }
                        } else {


                            SmartClassUtil.showToast(context, foodNoteBo.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<FoodMenuBo> call, Throwable t) {
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.CommonParams.ACTIVITY_CHOOSE_FILE) {
            String selectedFilePath = null;
            // Make sure the request was successful
            if (resultCode == RESULT_OK && data != null) {
                // get the file uri
                try {

                    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        Log.i("data.getData()", " data.getData():" + data.getData());
                        selectedFilePath = getAbsolutePath(data.getData());

                        Log.i("selectedFilePath", " selectedFilePath:" + selectedFilePath);

                    } else {
                        String filePath = data.getData().getPath();
                        Uri fileUri = Uri.parse(filePath);
                        // validate the file
                     *//*   Log.i("fileUrist", fileUri.toString());
                        Log.i("fileUrl", "" + new File(fileUri.getPath()));*//*
                        *//*String validationMssg = validateFile(fileUri);*//*
                        *//*uploadText.setText("" + fileUri.getPath());*//*
                        selectedFilePath = fileUri.getPath();
                    }*/
                    try {
                        Log.i("data.getData()", " data.getData():" + data.getData());
                        selectedFilePath = GetPath.getPath(this, data.getData());
                        Log.i("selectedFilePath", " selectedFilePath:" + selectedFilePath);




                      /*  if(selectedFilePath==null)
                        {
                            Uri returnUri = data.getData();
                            String mimeType = getContentResolver().getType(returnUri);
                            Cursor returnCursor =
                                    getContentResolver().query(returnUri, null, null, null, null);
                            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                            returnCursor.moveToFirst();

                            SmartClassUtil.PrintMessage("mimeType "+mimeType+" "+(returnCursor.getString(nameIndex)+" : "+Long.toString(returnCursor.getLong(sizeIndex))));
                            if(mimeType.equalsIgnoreCase("application/pdf")) {
                                InputStream inputStreamer =getContentResolver().openInputStream(returnUri);
                            }


                        }*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    if (selectedFilePath != null && !selectedFilePath.equals("")) {
                        String[] parts = selectedFilePath.split("/");
                        fileName = parts[parts.length - 1];
                        //  fileName= parts[parts.length-1];
                        Log.i("Test", " File Extension:" + fileName.substring(fileName.indexOf(".")));


                        if (fileName.substring(fileName.indexOf(".") + 1).equalsIgnoreCase("pdf")) {

                            uploadText.setText(fileName);
                            InputStream inputStreamer = new FileInputStream(selectedFilePath);
                            byte[] bytes;
                            byte[] buffer = new byte[8192];
                            int bytesRead;
                            ByteArrayOutputStream output = new ByteArrayOutputStream();

                            while ((bytesRead = inputStreamer.read(buffer)) != -1) {
                                output.write(buffer, 0, bytesRead);
                            }

                            bytes = output.toByteArray();
                            encodedString_video = Base64.encodeToString(bytes,
                                    Base64.DEFAULT);
                            output.reset();
                            Log.i("File__base64", encodedString_video);
                        } else {
                            String errMsg = "";

                            if (language.equals("en")) {
                                errMsg = "Please upload pdf files";

                            } else {
                                errMsg = "Ladda upp pdf-filer";
                            }
                            SmartClassUtil.showToast(context, errMsg);
                        }


                    } else {


                        String errMsg = "";

                        if (language.equals("en")) {
                            errMsg = "Cannot upload this file";

                        } else {
                            errMsg = "Det går inte att ladda upp denna fil";
                        }
                        SmartClassUtil.showToast(context, errMsg);
                    }
                    // "/storage/emulated/0/SHAREit/files/Software_Upgrade.pdf"  it is working //////////
//             /storage/emulated/0//document/primary:SHAREit/files/Software_Upgrade.pdf

                    /*Log.i("work", "work");*/


                } catch (Error e) {
                    // TODO: handle exception
                  /*  Toast.makeText(getActivity(), "errror", Toast.LENGTH_SHORT)
                            .show();

                    error = "error";*/

                } catch (IOException e) {
                    // TODO: handle exception
                    Log.i("io", "io");
                }

            }
        }
    }

    public String getAbsolutePath(Uri uri) {
        try {
            String[] projection = {MediaStore.MediaColumns.DATA};
            @SuppressWarnings("deprecation")
            Cursor cursor = managedQuery(uri, projection, null, null, null);
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            } else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

   /* public String validateFile(Uri uri) {
        String mssg = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
        File file = new File(uri.getPath());
        // is the file readable?
        if (!file.canRead()) {
            mssg = uri.getPath();
        }
        // simple extension check
        else if (!extension.toLowerCase().equals("pdf")) {
            mssg = uri.getPath();
        }
        return mssg;
    }
*/

    public void callAddMenuService() {
        //{"securityKey":"H67jdS7wwfh","loginUserID":"44","start":"2016-11-14","end":"2016-11-19","file_name":"tn.pdf","pdf_file":"",”language”:”en”}


        try {
            reqJsonObj = new JSONObject();
            reqJsonObj.put(Const.Params.SecurityKey, securityKey);
            reqJsonObj.put(Const.Params.LoginUserId, user_id);
            reqJsonObj.put(Const.Params.START, startDateVal);
            reqJsonObj.put(Const.Params.END, endDateVal);
            reqJsonObj.put(Const.Params.Language, language);
            reqJsonObj.put(Const.Params.FILE_NAME, fileName);
            reqJsonObj.put(Const.Params.PDF_FILE, encodedString_video);
/*
            {"securityKey":"H67jdS7wwfh","loginUserID":"44","date":"2016-12-29","language":"en","groupType":"my_group"}
*/
            dialog.show();

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(new ToStringConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqJsonObj.toString());
            Call<FoodMenuBo> response = retrofit.create(API.class).addFoodMenuLIst(body);
            response.enqueue(new Callback<FoodMenuBo>() {
                @Override
                public void onResponse(Call<FoodMenuBo> call, Response<FoodMenuBo> response) {
                    dialog.dismiss();

                    if (response.body() != null) {
                        FoodMenuBo foodMenuBo = response.body();
                        if (foodMenuBo.getStatus().equalsIgnoreCase(Const.Params.TRUE)) {
                            clearDate();
                            if (foodMenuBo.getFoodmenu().size() > 0) {
                                foodMenuRv.setVisibility(View.VISIBLE);
                                noFilesText.setVisibility(View.GONE);

                                foodmenuList.clear();
                                foodmenuList.addAll(foodMenuBo.getFoodmenu());
                                foodMenuAdapter.notifyDataSetChanged();
                            } else {
                                foodMenuRv.setVisibility(View.GONE);

                                if (language.equalsIgnoreCase("sw")) {
                                    noFilesText.setText("Ingen fil uppladdad");
                                }
                                noFilesText.setVisibility(View.VISIBLE);
                            }
                        } else {


                            SmartClassUtil.showToast(context, foodMenuBo.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<FoodMenuBo> call, Throwable t) {
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void clearDate() {
        if (language.equals("en")) {
            uploadText.setText("Upload file");
            endDateText1.setText("YYYY/MM/DD");
            startDateText1.setText("YYYY/MM/DD");
        } else {
            uploadText.setText("Ladda upp fil");
            endDateText1.setText("ÅÅÅÅ/MM/DD");
            startDateText1.setText("ÅÅÅÅ/MM/DD");
        }
        fileName = "";
        encodedString_video = "";
        startDateVal = "";
        endDateVal = "";
        startCalendar = Calendar.getInstance();
        endCalendar = endCalendar.getInstance();
    }
   /* private String validateFile(Uri uri) {
        String mssg = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
        File file = new File(uri.getPath());
        // is the file readable?
        if(!file.canRead()) {
            mssg = "File selected: ".concat(uri.getPath()).concat("\nPlease, select a PDF file from your SD card");
        }
        // simple extension check
        else if (!extension.toLowerCase().equals("pdf")) {
            mssg = "File selected: ".concat(uri.getPath()).concat("\nPlease, select a PDF file from your SD card");
        }

        return mssg;
    }
    public String encode(String fileName) throws Exception {
        File dir = Environment.getExternalStorageDirectory();

        File sourceFile = new File(fileName);


        byte[] base64EncodedData =loadFile(sourceFile);

        String encoded = Base64.encodeToString(base64EncodedData, Base64.DEFAULT);

*//*
        Base64.encodeBase64(loadFileAsBytesArray(sourceFile), isChunked);
*//*

        *//*writeByteArraysToFile(targetFile, base64EncodedData);*//*

        return encoded;
    }
    public static void writeByteArraysToFile(String fileName, byte[] content) throws IOException {

        File file = new File(fileName);
        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
        writer.write(content);
        writer.flush();
        writer.close();

    }
  *//*  public static byte[] loadFileAsBytesArray(String fileName) throws Exception {

        File file = new File(fileName);

        int length = (int) file.length();
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[length];
        reader.read(bytes, 0, length);
        reader.close();
        return bytes;

    }
*//*
  private static byte[] loadFile(File file) throws IOException {

      InputStream is = new FileInputStream(file);

      long length = file.length();
      if (length > Integer.MAX_VALUE) {
          // File is too large
      }
      byte[] bytes = new byte[(int) length];
      int offset = 0;
      int numRead = 0;
      while (offset < bytes.length
              && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
          offset += numRead;
      }
      if (offset < bytes.length) {
          throw new IOException("Could not completely read file " + file.getName());
      }
      is.close();
      return bytes;
  }
*/
}
