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
//
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
//import android.view.View.OnClickListener;
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
//public class CustomAdptercurculm_listthree extends ArrayAdapter<String>
//{
//ImageView chk;
//private final Context context;
//private final String[] child;
//private  String[] index ;
//private static LayoutInflater inflater=null;
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
//	  chk.setBackgroundResource(R.drawable.like2);
//	if (index[position].equalsIgnoreCase("1")) {
//		chk.setBackgroundResource(R.drawable.like1);
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
////		Toast.makeText(getContext(), Integer.toString(index.length), Toast.LENGTH_SHORT).show();
////		chk.setBackgroundResource(R.drawable.like2);
//	    index[position]= "1";
//	//    notifyDataSetChanged();
//	}
////	Toast.makeText(getContext(), Integer.toString(index.length), Toast.LENGTH_SHORT).show();
//		
//	}
//});
//
//return rowView;
//}
//
//
//}
