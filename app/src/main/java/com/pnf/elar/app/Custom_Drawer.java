package com.pnf.elar.app;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Normalizer;

import com.elar.imageloader.ImageLoader;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Custom_Drawer extends ArrayAdapter<String> {

	private final Activity context;
	public ImageLoader imageLoader;
	String item1;
	String item2;
	String item3;
	private final String[] item4, item5, item6, item7, profile_img;
	private int icon;
	private TextView txtTitle1, txtTitle2, txtTitle3, txtTitle4;
	private String ic;
	private String proflieImagePath;
	ImageView imag_logo_pic_2;

	String First_name, User_type, Customer_name;

	public Custom_Drawer(Drawer context_one, String[] first_name,
			String[] user_type, String[] customer_name, String[] username,
			String[] profile_images) {
		super(context_one, R.layout.drawer_list_item, first_name);
		this.context = context_one;
		this.item4 = first_name;
		this.item5 = user_type;
		this.item6 = customer_name;
		this.item7 = username;
		this.profile_img = profile_images;
		imageLoader = new ImageLoader(context_one);

		// Log.i("name..2.", Arrays.deepToString(item6));
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		// Log.i("thummmmmm.....", imageId[0]);
		LayoutInflater inflater = context.getLayoutInflater();

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.drawer_list_item, parent,
				false);
		txtTitle1 = (TextView) rowView.findViewById(R.id.textView1);
		txtTitle2 = (TextView) rowView.findViewById(R.id.textView2);
		txtTitle3 = (TextView) rowView.findViewById(R.id.textView3);
		txtTitle4 = (TextView) rowView.findViewById(R.id.textView4);
		imag_logo_pic_2 = (ImageView) rowView
				.findViewById(R.id.imag_logo_pic_2);

		System.out.println("position - "+position+" - "+profile_img[position]);
		// new ImageLoadTasklist(profile_img[position],
		// imag_logo_pic_2).execute();
		Picasso.with(context)
				.load(Uri.parse(profile_img[position]))
				.placeholder(R.drawable.roundedimageview)
				// optional
				.error(R.drawable.roundedimageview)         // optional
				.into(imag_logo_pic_2);
/*
		imageLoader.DisplayImage(profile_img[position], imag_logo_pic_2);
*/

		txtTitle1.setText(filterStringToNormal(item6[position]));
		txtTitle2.setText(filterStringToNormal(item4[position]));
		txtTitle3.setText(filterStringToNormal(item5[position]));
		txtTitle4.setText(filterStringToNormal(item7[position]));

		return rowView;

	}

	// /
	// /
	class ImageLoadTasklist extends AsyncTask<Void, Void, Bitmap> {
		// ProgressDialog pDialog;
		private String url;
		private ImageView imageViewone;

		// public ImageLoadTasklist(String string, ImageView imageView2) {
		// // TODO Auto-generated constructor stub
		// }

		public ImageLoadTasklist(String urlm, ImageView imageViewlist) {
			this.url = urlm;
			this.imageViewone = imageViewlist;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected Bitmap doInBackground(Void... params) {
			try {
				URL urlConnectionlist = new URL(url);
				HttpURLConnection connectionlist = (HttpURLConnection) urlConnectionlist
						.openConnection();
				connectionlist.setDoInput(true);
				connectionlist.connect();
				InputStream input = connectionlist.getInputStream();
				Bitmap myBitmaplist = BitmapFactory.decodeStream(input);
				return myBitmaplist;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap resultlist) {
			super.onPostExecute(resultlist);
			// pDialog.dismiss();
			imageViewone.setScaleType(ImageView.ScaleType.FIT_XY);
			imageViewone.setImageBitmap(resultlist);
		}

	}

	public String filterStringToNormal(String data) {
		String withoutAccent = Normalizer.normalize(data, Normalizer.Form.NFD);
		String output = withoutAccent.replaceAll("[^a-zA-Z ]", "");
		Log.e("String filter out-put", "" + output);
		return data;
	}
}