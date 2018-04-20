package com.pnf.elar.app.unused;
//package com.example.elar_app;
// 
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//import android.support.v7.widget.LinearLayoutCompat.LayoutParams;
//import android.util.Base64;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.MeasureSpec;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.Toast;
// 
//public class CustomAdptercurculm_listone extends ArrayAdapter<String>
//{
//ArrayList<String> cur_ids = new ArrayList<String>();
//ListView list2;	
//private final Activity context;
//private final String[] web;
//private final ArrayList<String[]> listone;
//Map<String, ArrayList<String[]>> map1;
//// Map<String, ArrayList<String[]>> map123;
//
//private static LayoutInflater inflater=null;
//public ImageLoader imageLoader; 
////LayoutInflater inflater;
////static ImageView imageView;
//
//public CustomAdptercurculm_listone(Curriculum_tags curriculum_tags,
//		String[] curriclm_tg_title,ArrayList<String[]> listone,Map<String, ArrayList<String[]>> map) 
//{
//	super(curriculum_tags, R.layout.curlm_main_tag, curriclm_tg_title);
//	this.context=curriculum_tags;
//	this.web = curriclm_tg_title;
//	this.listone= listone;
//	this.map1=map;
////	this.map123=map12;
//}
//
//@Override
//public View getView(int position, View view, ViewGroup parent) {
//	
//	 Log.i("thummmmmm.....", Arrays.deepToString(web));
//LayoutInflater inflater = context.getLayoutInflater();
//
//inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//View rowView= inflater.inflate(R.layout.curlm_main_tag, parent, false);
//
//
//TextView txtTitle = (TextView) rowView.findViewById(R.id.cur_parent);
//txtTitle.setText(web[position]);
//
////try {
////	Log.i("chldennnnn...", Arrays.deepToString(listone.get(position)));
////} catch (Exception e) {
////	// TODO: handle exception
////}
//
//
////Log.i("hiiiiiiiiii.....","hlooooooooooo");
//// ArrayList<String[]> values = null;
//
//for (Map.Entry<String, ArrayList<String[]>> entry : map1.entrySet()) { 
//
//  //  String key = entry.getKey();
//
//	ArrayList<String[]>  values = entry.getValue();
//     
//     try {
//    	    list2=(ListView)rowView.findViewById(R.id.curlm_child1_tag);
//    //	 	CustomAdptercurculm_listtwo adapter = new CustomAdptercurculm_listtwo(getContext() , listone.get(position),map1.get(position));
// 		    CustomAdptercurculm_listtwo adapter = new CustomAdptercurculm_listtwo(getContext() , listone.get(position),values);
//
//    	    list2.setAdapter(adapter);
////    		Helper.getListViewSize(list2);
//    	    setListViewHeightBasedOnChildren(list2);
//    	} catch (Exception e) {
//    		// TODO: handle exception
//    	}
//
//}
// 
////Log.i("=========", Arrays.deepToString(values.get(position)));
//
//return rowView;
//}
//
//private void setListViewHeightBasedOnChildren(ListView list22) 
//{
//	// TODO Auto-generated method stub
//	
//	ListAdapter listAdapter = list22.getAdapter();
//    if (listAdapter == null)
//        return;
//
//    int desiredWidth = MeasureSpec.makeMeasureSpec(list22.getWidth(), MeasureSpec.UNSPECIFIED);
//    int totalHeight=0;
//    View view = null;
//
//    for (int i = 0; i < listAdapter.getCount(); i++) 
//    {
//        view = listAdapter.getView(i, view, list22);
//
//        if (i == 0)
//            view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,  
//                                      LayoutParams.MATCH_PARENT));
//
//        view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
//        totalHeight += view.getMeasuredHeight();
//
//    }
//
//    ViewGroup.LayoutParams params = list22.getLayoutParams();
//    params.height = totalHeight + ((list22.getDividerHeight()) * (listAdapter.getCount()));
//
//    list22.setLayoutParams(params);
//    list22.requestLayout();
//	
//}
//
///////////
///////////
///////////
//public class CustomAdptercurculm_listtwo extends ArrayAdapter<String>
//{
//List<Model> list = new ArrayList<Model>();
//ArrayList <String> checkedValue;
//ListView cu;	
//private final Context context;
//private final String[] child;
//private final ArrayList<String[]> sub1;
//int pn;
//
//private LayoutInflater inflater=null;
//public ImageLoader imageLoader; 
////LayoutInflater inflater;
////static ImageView imageView;
//
//public CustomAdptercurculm_listtwo(Context curriculum_tags,
//		String[] curriclm_tg_title, ArrayList<String[]> sub) 
//{
//	super(curriculum_tags, R.layout.curlm_child1_tag, curriclm_tg_title);
//	this.context=curriculum_tags;
//	this.child = curriclm_tg_title;
//	this.sub1=sub;
//}
//
//
//@Override
//public View getView(int position, View view, ViewGroup parent) {
//	
//	this.pn=position;
//	
//	// Log.i("thummmmmm.....", imageId[0]);
//LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//
//inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//View rowView= inflater.inflate(R.layout.curlm_child1_tag, parent, false);
//
//cu=(ListView)rowView.findViewById(R.id.curriculm_list_three);
//TextView txtTitle = (TextView) rowView.findViewById(R.id.cur_list_three);
//txtTitle.setText(child[position]);
//
///////////////////////////////////////////////////////////
//
//// CustomAdptercurculm_listthree adapter = new CustomAdptercurculm_listthree(getContext() , sub1.get(position));
//
//CustomAdptercurculm_listthree adapter = new CustomAdptercurculm_listthree(getContext() , sub1.get(position));
//cu.setAdapter(adapter);
// // cu.setOnItemClickListener(new CheckBoxClick());
// setListViewHeightBasedOnChildrentwo(cu);
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
//
//return rowView;
//}
//
//private void setListViewHeightBasedOnChildrentwo(ListView cu2) {
//	// TODO Auto-generated method stub
//	
//	ListAdapter listAdapter = cu2.getAdapter();
//    if (listAdapter == null)
//        return;
//
//    int desiredWidth = MeasureSpec.makeMeasureSpec(cu2.getWidth(), MeasureSpec.UNSPECIFIED);
//    int totalHeight=0;
//    View view = null;
//
//    for (int i = 0; i < listAdapter.getCount(); i++) 
//    {
//        view = listAdapter.getView(i, view, cu2);
//
//        if (i == 0)
//            view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,  
//                                      LayoutParams.MATCH_PARENT));
//
//        view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
//        totalHeight += view.getMeasuredHeight();
//
//    }
//
//    ViewGroup.LayoutParams params = cu2.getLayoutParams();
//    params.height = totalHeight + ((cu2.getDividerHeight()) * (listAdapter.getCount()));
//
//    cu2.setLayoutParams(params);
//    cu2.requestLayout();
//	
//}
///////////
///////////
///////////
//
//public class CustomAdptercurculm_listthree extends ArrayAdapter<String>
//{
//ImageView chk;
//private final Context context;
//private final String[] child;
//private  String[] index ;
//private  LayoutInflater inflater=null;
//public ImageLoader imageLoader; 
////LayoutInflater inflater;
////static ImageView imageView;
//
//
//
//public CustomAdptercurculm_listthree(Context context2, String[] strings) {
//	// TODO Auto-generated constructor stub
//	super(context2, R.layout.curlm_subchild_tag, strings);
//	this.context=context2;
//	this.child = strings;
//	this.index = new String[child.length];
//	 for(int i = 0 ; i<child.length;i++)
//	 {
//		 index[i] = "0";
//	 }
//}
//
//
//@Override
//public View getView(final int position, View view, ViewGroup parent) {
//
////  Log.i("child[position]....", child[position]);
//LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//
//inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//View rowView= inflater.inflate(R.layout.curlm_subchild_tag, parent, false);
//
// chk = (ImageView)rowView.findViewById(R.id.chk);
//TextView txtTitle = (TextView) rowView.findViewById(R.id.label);
//txtTitle.setText(child[position]);
//
//if (!(index[position].equalsIgnoreCase("0")));
//{
//
//	  chk.setBackgroundResource(R.drawable.like1);
//	if (index[position].equalsIgnoreCase("1")) {
//		chk.setBackgroundResource(R.drawable.like2);
//		// imageicon=1;
//	}
//
//}
//
//
//chk.setOnClickListener(new OnClickListener() {
//	
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		
////	Log.i("index[position]", Arrays.deepToString(index));
//	
//	if(index[position].equalsIgnoreCase("0"))
//	{
//		Toast.makeText(getContext(), child[position], Toast.LENGTH_SHORT).show();
//	    index[position]= "1";
//	    
//	    String[] parts = index[position].split("=");
//	    String part1 = parts[0];
//	    cur_ids.add(part1);
//	    
//	    notifyDataSetChanged();
//	}
//	else 		
//	{
//	    index[position]= "0";
//	    notifyDataSetChanged();
//	}
//	
//	}
//});
//
//return rowView;
//}
//
//}
///////////
///////////
///////////
//
//}
///////////
///////////
///////////
//}
