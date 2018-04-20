package com.pnf.elar.app.adapter.schedule;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elar.util.SmartClassUtil;
import com.pnf.elar.app.Bo.schedule.FoodMenuBo;
import com.pnf.elar.app.Bo.schedule.FoodNoteBo;
import com.pnf.elar.app.FootMenuPdfView;
import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.R;
import com.pnf.elar.app.UILApplication;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.activity.schedule.AddFoodMenuActivity;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.API;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.ToStringConverterFactory;
import com.pnf.elar.app.util.Const;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by VKrishnasamy on 30-12-2016.
 */

public class FoodMenuAdapter extends RecyclerView.Adapter<FoodMenuAdapter.FoodMenuHolder> {
    View itemView;
    public List<FoodMenuBo.FoodmenuEntity> foodmenuList = new ArrayList<>();
    Activity ctx;
    JSONObject reqJsonObj;
    String stardate = "Start Date : ", enddate = " End Date : ", language = "", auth_token = "", baseUrl = "", user_id = "", securityKey = "H67jdS7wwfh", fileName = "";
    MyCustomProgressDialog dialogLoading;
    String userType = "";
    /*int positionRemove;*/

    public FoodMenuAdapter(Activity ctx, List<FoodMenuBo.FoodmenuEntity> foodmenuList, String language, String baseUrl, String auth_token, String user_id, String userType) {
        super();
        this.ctx = ctx;
        this.foodmenuList = foodmenuList;
        this.language = language;
        this.language = language;
        this.auth_token = auth_token;
        this.baseUrl = baseUrl;
        this.user_id = user_id;
        this.dialogLoading = new MyCustomProgressDialog(ctx);
        this.dialogLoading.setIndeterminate(true);
        this.dialogLoading.setCancelable(false);
        this.userType = userType;
    }


    @Override
    public FoodMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_menu, parent, false);
        return new FoodMenuHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FoodMenuHolder holder, final int position) {

        try {
            final FoodMenuBo.FoodmenuEntity foodmenuEntity = foodmenuList.get(position);
            holder.pdfName.setText(foodmenuEntity.getFile_name());

            if (language.equalsIgnoreCase("en")) {

            } else {
                stardate = "Start datum : ";
                enddate = "Slutdatum : ";
            }
            if (userType.equalsIgnoreCase("Teacher")) {

                holder.removePdfBtn.setVisibility(View.VISIBLE);
            } else {
                holder.removePdfBtn.setVisibility(View.GONE);

            }

            holder.pdfStartDate.setText(stardate + foodmenuEntity.getStart_date());
            holder.pdfEndDate.setText(enddate + foodmenuEntity.getEnd_date());

            holder.viewPdfBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent pdfIntent = new Intent(ctx, FootMenuPdfView.class);
                    pdfIntent.putExtra(Const.CommonParams.PDFNAME, foodmenuEntity.getFile_name());
                    pdfIntent.putExtra(Const.CommonParams.PDFURL, baseUrl + "cus_customers/viewFoodMenuPdfMobile/" + foodmenuEntity.getId() + "?authentication_token=" + auth_token);
                    SmartClassUtil.PrintMessage("baseUrl " + baseUrl + foodmenuEntity.getPath());
                    pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(pdfIntent);
                }
            });
            holder.pdfName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent pdfIntent = new Intent(ctx, FootMenuPdfView.class);
                    pdfIntent.putExtra(Const.CommonParams.PDFNAME, foodmenuEntity.getFile_name());
                    pdfIntent.putExtra(Const.CommonParams.PDFURL, baseUrl + "cus_customers/viewFoodMenuPdfMobile/" + foodmenuEntity.getId() + "?authentication_token=" + auth_token);
                    SmartClassUtil.PrintMessage("baseUrl " + baseUrl + foodmenuEntity.getPath());
                    pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(pdfIntent);
                }
            });
            holder.pdfStartDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent pdfIntent = new Intent(ctx, FootMenuPdfView.class);
                    pdfIntent.putExtra(Const.CommonParams.PDFNAME, foodmenuEntity.getFile_name());
                    pdfIntent.putExtra(Const.CommonParams.PDFURL, baseUrl + "cus_customers/viewFoodMenuPdfMobile/" + foodmenuEntity.getId() + "?authentication_token=" + auth_token);
                    SmartClassUtil.PrintMessage("baseUrl " + baseUrl + foodmenuEntity.getPath());
                    pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(pdfIntent);
                }
            });
            holder.removeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (userType.equalsIgnoreCase("Teacher")) {
                        /*positionRemove=position;*/
                            reqJsonObj = new JSONObject();
                            reqJsonObj.put(Const.Params.SecurityKey, securityKey);
                            reqJsonObj.put(Const.Params.LoginUserId, user_id);
                            reqJsonObj.put(Const.Params.Language, language);
                            reqJsonObj.put(Const.Params.ID, foodmenuEntity.getId());
                            showDialog(position);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return foodmenuList.size();
    }

    public static class FoodMenuHolder extends RecyclerView.ViewHolder {
        TextView pdfName, pdfStartDate, pdfEndDate;
        ImageView removePdfBtn;
        Button viewPdfBtn;
        LinearLayout removeLayout;

        FoodMenuHolder(View itemView) {
            super(itemView);
            pdfName = (TextView) itemView.findViewById(R.id.pdfName);
            pdfStartDate = (TextView) itemView.findViewById(R.id.pdfStartDate);
            pdfEndDate = (TextView) itemView.findViewById(R.id.pdfEndDate);
            viewPdfBtn = (Button) itemView.findViewById(R.id.viewPdfBtn);
            removePdfBtn = (ImageView) itemView.findViewById(R.id.removePdfBtn);
            removeLayout = (LinearLayout) itemView.findViewById(R.id.removeLayout);
        }
    }

    public void showDialog(final int pos) {

        String msg = "Are you sure, You want to delete Food menu?";
        String yes = "Yes";
        String no = "No";

        if (language.equalsIgnoreCase("sw")) {

            msg = "Är du säker på att du vill radera Livsmedel-menyn?";
            yes = "Ja";
            no = "Nej";

        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton(yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                        callremoveMenuService(pos);
                    }
                });
        alertDialogBuilder.setNegativeButton(no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void callremoveMenuService(final int positionRemove) {


        //{"securityKey":"H67jdS7wwfh","loginUserID":"44","start":"2016-11-14","end":"2016-11-19","file_name":"tn.pdf","pdf_file":"",”language”:”en”}
        try {
            /*{"securityKey":"H67jdS7wwfh","loginUserID":"44","date":"2016-12-29","language":"en","groupType":"my_group"}*/
            dialogLoading.show();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(new ToStringConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqJsonObj.toString());
            Call<FoodMenuBo> response = retrofit.create(API.class).removeFoodMneu(body);
            response.enqueue(new Callback<FoodMenuBo>() {
                @Override
                public void onResponse(Call<FoodMenuBo> call, Response<FoodMenuBo> response) {
                    dialogLoading.dismiss();
                    if (response.body() != null) {
                        FoodMenuBo foodMenuBo = response.body();
                        if (foodMenuBo.getStatus().equalsIgnoreCase(Const.Params.TRUE)) {
                            foodmenuList.remove(positionRemove);

                            notifyItemRemoved(positionRemove);
                            notifyItemRangeChanged(positionRemove, foodmenuList.size());


                        } else {
                            SmartClassUtil.showToast(ctx, "Service Failed");
                        }
                    }
                }

                @Override
                public void onFailure(Call<FoodMenuBo> call, Throwable t) {
                    dialogLoading.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*    {"securityKey":"H67jdS7wwfh","loginUserID":"44","language":"en","id":"17"}*/
}
