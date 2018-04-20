package com.pnf.elar.app.unused;
 
import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pnf.elar.app.Curriculam_tag_edit;
import com.pnf.elar.app.ImageLoadernew;
import com.pnf.elar.app.R;

public class CustomAdpter_cur_edit_one extends ArrayAdapter<String>
{

ListView list2;	
private final Activity context;
private final String[] web;
private final ArrayList<String[]> listone;
Map<String, ArrayList<String[]>> map1;
// Map<String, ArrayList<String[]>> map123;

private static LayoutInflater inflater=null;
public ImageLoadernew imageLoader;
//LayoutInflater inflater;
//static ImageView imageView;

public CustomAdpter_cur_edit_one(Curriculam_tag_edit curriculum_tags,
		String[] curriclm_tg_title,ArrayList<String[]> listone,Map<String, ArrayList<String[]>> map) 
{
	super(curriculum_tags, R.layout.curlm_main_tag, curriclm_tg_title);
	this.context=curriculum_tags;
	this.web = curriclm_tg_title;
	this.listone= listone;
	this.map1=map;
//	this.map123=map12;
}

@Override
public View getView(int position, View view, ViewGroup parent) {
	
	// Log.i("thummmmmm.....", imageId[0]);
LayoutInflater inflater = context.getLayoutInflater();

inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
View rowView= inflater.inflate(R.layout.curlm_main_tag, parent, false);

list2=(ListView)rowView.findViewById(R.id.curlm_listtwo);
TextView txtTitle = (TextView) rowView.findViewById(R.id.tagRootTitle);
txtTitle.setText(web[position]);

//try {
//	Log.i("chldennnnn...", Arrays.deepToString(listone.get(position)));
//} catch (Exception e) {
//	// TODO: handle exception
//}


//Log.i("hiiiiiiiiii.....","hlooooooooooo");
// ArrayList<String[]> values = null;

//for (Map.Entry<String, ArrayList<String[]>> entry : map1.entrySet()) { 
//
//  //  String key = entry.getKey();
//
//	ArrayList<String[]>  values = entry.getValue();
//     
//     try {
//    //	 	CustomAdptercurculm_listtwo adapter = new CustomAdptercurculm_listtwo(getContext() , listone.get(position),map1.get(position));
//    	 CustomAdpter_cur_edit_two adapter = new CustomAdpter_cur_edit_two(getContext() , listone.get(position),values);
//
//    	    list2.setAdapter(adapter);
//    	//	setListViewHeightBasedOnChildren(list2);
//    	} catch (Exception e) {
//    		// TODO: handle exception
//    	}
//
//}
 
//Log.i("=========", Arrays.deepToString(values.get(position)));


return rowView;
}


private void setListViewHeightBasedOnChildren(ListView list22) 
{
	// TODO Auto-generated method stub
	
	ListAdapter listAdapter = list22.getAdapter();
    if (listAdapter == null)
        return;

    int desiredWidth = MeasureSpec.makeMeasureSpec(list22.getWidth(), MeasureSpec.UNSPECIFIED);
    int totalHeight=0;
    View view = null;

    for (int i = 0; i < listAdapter.getCount(); i++) 
    {
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

}
