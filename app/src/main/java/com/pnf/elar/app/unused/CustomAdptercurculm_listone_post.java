package com.pnf.elar.app.unused;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pnf.elar.app.Bo.CurriculamMainTag;
import com.pnf.elar.app.CurTagDetailsActivity;
import com.pnf.elar.app.Curriculam_tag_post;
import com.pnf.elar.app.ImageLoadernew;
import com.pnf.elar.app.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutCompat.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CustomAdptercurculm_listone_post extends ArrayAdapter<CurriculamMainTag> {
    ArrayList<String[]> curriculum_post_title;
    ListView list2;
    private final Activity context;
    private final String[] web;
    //private final ArrayList<String[]> listone;
    HashMap<String, ArrayList<String[]>> hashMap1;

    private static LayoutInflater inflater = null;
    public ImageLoadernew imageLoader;
    List<CurriculamMainTag> tagPostList;

    Dialog tagDialog;

//LayoutInflater inflater;
//static ImageView imageView;

    public CustomAdptercurculm_listone_post(Curriculam_tag_post curriculam_tag_post,
                                            String[] curriclm_tg_title, ArrayList<String[]> curriculum_post_title_child, HashMap<String, ArrayList<String[]>> hashMap, List<CurriculamMainTag> tagPostList) {
        super(curriculam_tag_post, R.layout.curlm_main_tag, tagPostList);
        this.context = curriculam_tag_post;
        this.web = curriclm_tg_title;
        this.curriculum_post_title = curriculum_post_title_child;
//	this.listone= listone;
        this.hashMap1 = hashMap;

        this.tagPostList=tagPostList;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {


//	Log.i("hash.........",Integer.toString(hashMap1.size()));

        // Log.i("thummmmmm.....", imageId[0]);
        LayoutInflater inflater = context.getLayoutInflater();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.curlm_main_tag, parent, false);

        list2 = (ListView) rowView.findViewById(R.id.curlm_listtwo);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.tagRootTitle);
        txtTitle.setText(tagPostList.get(position).getTitle());


        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showTagDetails(tagPostList.get(position).getTitle(),tagPostList.get(position).getDescription());

                Intent tagDetailsIntent = new Intent(context, CurTagDetailsActivity.class);

                tagDetailsIntent.putExtra("tagTitle",tagPostList.get(position).getTitle());
                tagDetailsIntent.putExtra("tagDescrp",tagPostList.get(position).getDescription());
                context.startActivity(tagDetailsIntent);

                context.overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
            }
        });

        for (Map.Entry<String, ArrayList<String[]>> entry : hashMap1.entrySet()) {

            //  String key = entry.getKey();

            ArrayList<String[]> values = entry.getValue();

            try {
                CustomAdptercurculm_listtwo_post adapter = new CustomAdptercurculm_listtwo_post(getContext(), curriculum_post_title.get(position), values,tagPostList.get(position).getChildren());

                list2.setAdapter(adapter);
                //	setListViewHeightBasedOnChildren(list2);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        return rowView;
    }


    private void setListViewHeightBasedOnChildren(ListView list22) {
        // TODO Auto-generated method stub

        ListAdapter listAdapter = list22.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = MeasureSpec.makeMeasureSpec(list22.getWidth(), MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, list22);

            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        LayoutParams.MATCH_PARENT));

            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = list22.getLayoutParams();
        params.height = totalHeight + ((list22.getDividerHeight()) * (listAdapter.getCount()));

        list22.setLayoutParams(params);
        list22.requestLayout();

    }


    public void showTagDetails(String dgTitle, String dgDescrp) {
        tagDialog = new Dialog(context);
        tagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        tagDialog.setContentView(R.layout.dialog_curriculamtag);
        tagDialog.setCancelable(true);
        tagDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;


        ImageView closeTagImage = (ImageView) tagDialog.findViewById(R.id.closeTagImage);
        TextView tagHeaderText = (TextView) tagDialog.findViewById(R.id.tagHeaderText);
        final TextView tagDescrpText = (TextView) tagDialog.findViewById(R.id.tagDescrpText);


        tagHeaderText.setText(dgTitle);
        tagDescrpText.setText(dgDescrp);


        closeTagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagDialog.dismiss();
            }
        });

        tagDialog.show();



    }

}
