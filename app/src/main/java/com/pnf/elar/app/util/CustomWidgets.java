package com.pnf.elar.app.util;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by user on 15-02-2018.
 */

public class CustomWidgets {

    static CustomWidgets customWidgets=getInstance();

    public static CustomWidgets getInstance(){
        if (customWidgets==null){
            customWidgets=new CustomWidgets();
        }
        return customWidgets;
    }

    public Dialog getDialogBox(int layoutID, String title, Context context){
        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layoutID);
        WindowManager.LayoutParams a = dialog.getWindow().getAttributes();
        a.dimAmount = 0;
        dialog.getWindow().setAttributes(a);
        dialog.setCancelable(true);

        int density=context.getResources().getDisplayMetrics().densityDpi;
        int screenLayout=(context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK);


        int xhigh= Configuration.SCREENLAYOUT_SIZE_LARGE;
        if(screenLayout==Configuration.SCREENLAYOUT_SIZE_LARGE ||screenLayout==Configuration.SCREENLAYOUT_SIZE_XLARGE ) {
//            int fullWidth = Bitmap.Config.getInstance().getDisplayWidth(context);
//            int dialogwidth = (int) (fullWidth * 0.60);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }else {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }




        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setTitle(title);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.dimAmount=0.0f;
        //dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        wmlp.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        dialog.getWindow().setAttributes(wmlp);




        dialog.show();
        return dialog;
    }


}