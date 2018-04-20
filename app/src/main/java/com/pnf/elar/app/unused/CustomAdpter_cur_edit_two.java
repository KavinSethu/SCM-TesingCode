package com.pnf.elar.app.unused;
 
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pnf.elar.app.ImageLoadernew;
import com.pnf.elar.app.Model;
import com.pnf.elar.app.R;

public class CustomAdpter_cur_edit_two extends ArrayAdapter<String>
{
List<Model> list = new ArrayList<Model>();
ArrayList <String> checkedValue;
ListView cu;	
private final Context context;
private final String[] child;
private final ArrayList<String[]> sub1;
int pn;

private static LayoutInflater inflater=null;
public ImageLoadernew imageLoader;
//LayoutInflater inflater;
//static ImageView imageView;

public CustomAdpter_cur_edit_two(Context curriculum_tags,
		String[] curriclm_tg_title, ArrayList<String[]> sub) 
{
	super(curriculum_tags, R.layout.curlm_child1_tag, curriclm_tg_title);
	this.context=curriculum_tags;
	this.child = curriclm_tg_title;
	this.sub1=sub;
}


@Override
public View getView(int position, View view, ViewGroup parent) {
	
	this.pn=position;
	
	// Log.i("thummmmmm.....", imageId[0]);
LayoutInflater inflater = ((Activity) context).getLayoutInflater();

inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
View rowView= inflater.inflate(R.layout.curlm_child1_tag, parent, false);

cu=(ListView)rowView.findViewById(R.id.curriculm_list_three);
TextView txtTitle = (TextView) rowView.findViewById(R.id.tagChildTitle);
txtTitle.setText(child[position]);

/////////////////////////////////////////////////////////

// CustomAdptercurculm_listthree adapter = new CustomAdptercurculm_listthree(getContext() , sub1.get(position));
//CustomAdpter_cur_edit_three adapter = new CustomAdpter_cur_edit_three(getContext() , sub1.get(position));
//cu.setAdapter(adapter);
// // cu.setOnItemClickListener(new CheckBoxClick());
////setListViewHeightBasedOnChildrentwo(cu);
//
//cu.setOnItemClickListener(new OnItemClickListener() {
//
//	@Override
//	public void onItemClick(AdapterView<?> parent, View view, int position,
//			long id) {
//		// TODO Auto-generated method stub
//		
//		TextView label = (TextView) view.getTag(R.id.label);
//		
//	//	Toast.makeText(view.getContext(), label.getText().toString()+" "+isCheckedOrNot(checkbox), Toast.LENGTH_LONG).show();
//		
//		
//	}
//});




return rowView;
}



//private String[] getModel() {
//	// TODO Auto-generated method stub
//	
//	for(int i=0; ;)
//	{
//	list.add(new Model(sub1.get(i)[i]));
//	}
//	return null;
//}


private void setListViewHeightBasedOnChildrentwo(ListView cu2) {
	// TODO Auto-generated method stub
	
	ListAdapter listAdapter = cu2.getAdapter();
    if (listAdapter == null)
        return;

    int desiredWidth = MeasureSpec.makeMeasureSpec(cu2.getWidth(), MeasureSpec.UNSPECIFIED);
    int totalHeight=0;
    View view = null;

    for (int i = 0; i < listAdapter.getCount(); i++) 
    {
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
