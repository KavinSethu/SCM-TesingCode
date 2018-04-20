package com.pnf.elar.app.unused;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.pnf.elar.app.ImageLoadernew;
import com.pnf.elar.app.R;

public class CustomAdpter_cur_edit_three extends ArrayAdapter<String> {

	private final Context cont;
	private final String[] chi;
	ArrayList<String> ids = new ArrayList<String>();
	String[] stockArr;
	boolean checkAll_flag = false;
	boolean checkItem_flag = false;
	

	private static LayoutInflater inflater = null;
	public ImageLoadernew imageLoader;

	// LayoutInflater inflater;
	// static ImageView imageView;
	
	
	static class ViewHolder {
		protected TextView text;
		protected CheckBox checkbox;
	}

	public CustomAdpter_cur_edit_three(Context curricu, String[] curriclm_tg) {
		super(curricu, R.layout.curlm_subchild_tag, curriclm_tg);
		this.cont = curricu;
		this.chi = curriclm_tg;

	}
	
	

	@Override
public View getView(final int position, View view, ViewGroup parent)
{
		
		ViewHolder viewHolder = null;
		if (view == null) {
			LayoutInflater inflator = ((Activity) cont).getLayoutInflater();
			view = inflator.inflate(R.layout.curlm_subchild_tag, null);
			viewHolder = new ViewHolder();
			viewHolder.text = (TextView) view.findViewById(R.id.label);
			
			viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
						//	list.get(getPosition).setSelected(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
						}
					});
			view.setTag(viewHolder);
			view.setTag(R.id.label, viewHolder.text);
			
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.checkbox.setTag(position); // This line is important.
		
	viewHolder.text.setText(chi[position]);
//		viewHolder.checkbox.setChecked(list.get(position).isSelected());	
		
		
		
		
		
		
//	 Log.i("thummmmmm.....", Arrays.toString(chi));
//	 
//final ViewHolder holder;	 
//	 
//LayoutInflater inflater = ((Activity) cont).getLayoutInflater();
//
//inflater = (LayoutInflater) cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//View row= inflater.inflate(R.layout.curlm_subchild_tag, parent, false);
// final CheckedTextView ctv = (CheckedTextView) row.findViewById(R.id.my_checkedtextview);
//ctv.setText(chi[position]);
//
////////////////////////////////////////////////////////
////	try {
////		
////		for(int i = 0 ;i < ids.size();i++)
////		{
////			if(chi[position].equalsIgnoreCase(ids.get(i)))
////			{
////				ctv.setChecked(true);
////			}
////			
////		}
////		
////	} catch (Exception e) {
////		// TODO: handle exception
////	}
//////////////////////////////////////////////////////////////
//	
//try {
//	Log.i("selected data ", Integer.toString(ids.size()));
//} catch (Exception e) {
//	// TODO: handle exception
//}
//
//	
//
//////////////////////////////////////////////////////////////
//ctv.setOnClickListener(new OnClickListener() {
//
//	@Override
//	public void onClick(View v) {
//		ctv.toggle();
//		if (ctv.isChecked()) {
////			ctv.setText("Checked");
////			String[] parts = chi[position].split("=");
////			String part1 = parts[0]; 
////			String part2 = parts[1];
//			
//			ids.add(chi[position]);
//			Toast.makeText(getContext(), "stored..", Toast.LENGTH_SHORT).show();
//			
//
//		} else {
////			ctv.setText("Unchecked");
//		}
//	}
//});



return  view;
}
}
