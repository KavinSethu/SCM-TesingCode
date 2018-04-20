package com.pnf.elar.app;
 
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ArrayAdapter_newsEdit extends ArrayAdapter<String>
{
TextView txtTitle;
private final Activity context;
private final ArrayList<String> all;
// private final String[] img_vdo ;
String[] result;
ImageView main_image;

public ArrayAdapter_newsEdit(Activity edit_post, ArrayList<String> all
		) {
	// TODO Auto-generated constructor stub
	super(edit_post, R.layout.list_item_imagevideo_news, all);
	this.context=edit_post;
	this.all = all;
	}


@Override
public View getView(int position, View view, ViewGroup parent) {
	
	// Log.i("thummmmmm.....", imageId[0]);
LayoutInflater inflater =  context.getLayoutInflater();


// Log.i("vdoname///", Arrays.deepToString(vdoname));

///////////////////////////////////////////////
inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
View rowView= inflater.inflate(R.layout.list_item_imagevideo_news, parent, false);


main_image=(ImageView)rowView.findViewById(R.id.imageone);
main_image.setImageBitmap(BitmapFactory
        .decodeFile(all.get(position)));

 txtTitle = (TextView) rowView.findViewById(R.id.lli);
 
 String[] bits = all.get(position).split("/");
 String lastOne = bits[bits.length-1];
 
txtTitle.setText(lastOne);

if (position % 2 == 0) {
	rowView.setBackgroundColor(Color.parseColor("#81BEF7"));
	} else {
		rowView.setBackgroundColor(Color.parseColor("#A9D0F5"));
	}

return rowView;
}


}
