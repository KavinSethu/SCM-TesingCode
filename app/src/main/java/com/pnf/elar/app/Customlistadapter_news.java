package com.pnf.elar.app;
//package com.example.news;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import android.app.Activity;
//import android.content.Context;
//import android.net.Uri;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//
//public class Customlistadapter extends ArrayAdapter implements ListAdapter {
//		private ArrayList<String> list = new ArrayList<String>();
//		Context context;
//		Uri[] image;
//
//		public Customlistadapter(Activity context, ArrayList<String> list1) {
//			super(context, R.layout.list_item, list1);
//			// TODO Auto-generated constructor stub
//			this.list = list1;
//
//			this.context = context;
//		}
//
//		@Override
//		public int getCount() {
//			return list.size();
//
//		}
//
//		@Override
//		public Object getItem(int pos) {
//			return list.get(pos);
//		}
//
//		@Override
//		public long getItemId(int pos) {
//
//			return pos;
//
//		}
//
//		@Override
//		public View getView(final int position, View convertView,
//				ViewGroup parent) {
//			// TODO Auto-generated method stub
//			LayoutInflater inflater = (LayoutInflater) context
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			View single_row = inflater.inflate(R.layout.list_item, null, true);
//			TextView textView1 = (TextView) single_row
//					.findViewById(R.id.tvDescr);
//
//			textView1.setText(list.get(position));
//			Button deleteBtn = (Button) single_row.findViewById(R.id.cbBox);
//			//
//
//			deleteBtn.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					// do something
//					list.remove(position);
//					Helper.getListViewSize(listview);
//
//					adapter1.notifyDataSetChanged();
//				}
//			});
//			
//			
//			if (position % 2 == 0) {
//				single_row.setBackgroundColor(Color.parseColor("#29B6C9"));
//    			} else {
//    				single_row.setBackgroundColor(Color.parseColor("#42C4D6"));
//    			}
//        
//			
//
//			return single_row;
//
//		}
//
//	}