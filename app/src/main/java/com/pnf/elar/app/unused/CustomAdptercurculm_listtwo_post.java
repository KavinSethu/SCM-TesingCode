package com.pnf.elar.app.unused;

import java.util.ArrayList;
import java.util.List;

import com.pnf.elar.app.Bo.CurriculamEduPost;
import com.pnf.elar.app.CurTagDetailsActivity;
import com.pnf.elar.app.ImageLoadernew;
import com.pnf.elar.app.Model;
import com.pnf.elar.app.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar.LayoutParams;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CustomAdptercurculm_listtwo_post extends ArrayAdapter<CurriculamEduPost> {
    List<Model> list = new ArrayList<Model>();
    ArrayList<String> checkedValue;
    ListView cu;
    private final Context context;
    private final String[] child;
    private final ArrayList<String[]> sub1;
    int pn;

    private static LayoutInflater inflater = null;
    public ImageLoadernew imageLoader;

    List<CurriculamEduPost> childList=new ArrayList<>();


    Dialog tagDialog;
//LayoutInflater inflater;
//static ImageView imageView;

    public CustomAdptercurculm_listtwo_post(Context curriculum_tags,
                                            String[] curriclm_tg_title, ArrayList<String[]> sub, List<CurriculamEduPost> childList) {
        super(curriculum_tags, R.layout.curlm_child1_tag, childList);
        this.context = curriculum_tags;
        this.child = curriclm_tg_title;
        this.sub1 = sub;
        this.childList=childList;
    }


    @Override
    public View getView(final  int position, View view, ViewGroup parent) {

        Log.i("size...", Integer.toString(sub1.size()));

// Log.i("child....", Arrays.deepToString(sub1.get(position)));

        // Log.i("thummmmmm.....", imageId[0]);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.curlm_child1_tag, parent, false);

        cu = (ListView) rowView.findViewById(R.id.curriculm_list_three);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.tagChildTitle);
        txtTitle.setText(childList.get(position).getTitle());

        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent tagDetailsIntent = new Intent(context, CurTagDetailsActivity.class);

                tagDetailsIntent.putExtra("tagTitle",childList.get(position).getTitle());
                tagDetailsIntent.putExtra("tagDescrp",childList.get(position).getDescription());
                context.startActivity(tagDetailsIntent);

                Activity activity=(Activity)context;
                activity.overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
/*

*/
            }
        });
        CustomAdptercurculm_listthree_post adapter = new CustomAdptercurculm_listthree_post(getContext(), sub1.get(position),childList.get(position).getSubchildren());
        cu.setAdapter(adapter);
        // cu.setOnItemClickListener(new CheckBoxClick());
        setListViewHeightBasedOnChildrentwo(cu);

        cu.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // TODO Auto-generated method stub

                TextView label = (TextView) view.getTag(R.id.label);

            }
        });

        return rowView;
    }


    private void setListViewHeightBasedOnChildrentwo(ListView cu2) {
        // TODO Auto-generated method stub

        ListAdapter listAdapter = cu2.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = MeasureSpec.makeMeasureSpec(cu2.getWidth(), MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, cu2);

            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        LayoutParams.MATCH_PARENT));

            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = cu2.getLayoutParams();
        params.height = totalHeight + ((cu2.getDividerHeight()) * (listAdapter.getCount()));

        cu2.setLayoutParams(params);
        cu2.requestLayout();

    }

}
