package com.pnf.elar.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressWarnings("rawtypes")
public class Customlistadaptervedio extends ArrayAdapter{
 String[] names1;
 int[] immw;
 Context context;

 
 @SuppressWarnings("unchecked")
public Customlistadaptervedio(Context con,String[] title,int[] imm){
 super(con, R.layout.list_vedio_item, title );
 // TODO Auto-generated constructor stub
 this.names1 = title;

 this.immw = imm;
 
 this.context = con;
 }
 
@Override
 public View getView(int position, View convertView, ViewGroup parent) {
 // TODO Auto-generated method stub
 LayoutInflater inflater = (LayoutInflater) context
 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 View single_row = inflater.inflate(R.layout.list_vedio_item, null,true);
 ImageView textView1 = (ImageView) single_row.findViewById(R.id.list_vedio);
TextView title = (TextView)single_row.findViewById(R.id.title);

 textView1.setBackgroundResource(immw[position]);
 title.setText(names1[position]);
 
 
 
 return single_row; 
 
 
 
 
 }
 
 
 
 
 
}