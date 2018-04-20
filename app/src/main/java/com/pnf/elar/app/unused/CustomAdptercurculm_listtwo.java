package com.pnf.elar.app.unused;
//package com.example.elar_app;
//
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.pm.ApplicationInfo;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//import android.support.v7.app.ActionBar.LayoutParams;
//import android.util.Base64;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.View.MeasureSpec;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.CheckBox;
//import android.widget.CheckedTextView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
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
//private static LayoutInflater inflater=null;
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
//
//
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
//
//}
