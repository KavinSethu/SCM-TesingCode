package com.pnf.elar.app;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ParentChid extends ArrayAdapter<String> {
	TextView txtTitle;
	private final Activity context;
	private final String[] childname, uSR_img;
	ImageView imageone;
	String Base_url;

	public ImageLoadernew imageLoader;

	public ParentChid(Activity activity, String[] children_name,
			String[] uSR_image, String Base_url)
	{
		// TODO Auto-generated constructor stub
		super(activity, R.layout.parentchild, children_name);
		this.context = activity;
		this.childname = children_name;
		this.uSR_img = uSR_image;
		this.Base_url = Base_url;
		imageLoader = new ImageLoadernew(activity);
	}


	@Override
	public View getView(final int position, View view, ViewGroup parent) {

		Log.i("bbbbbbb", uSR_img[position]);
		
		LayoutInflater inflater = context.getLayoutInflater();

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.parentchild, parent, false);

		imageone = (ImageView) rowView.findViewById(R.id.child_image);
		txtTitle = (TextView) rowView.findViewById(R.id.child_name);

		txtTitle.setText(childname[position]);

		// imageone.setImageBitmap(BitmapFactory
		// .decodeFile("http://ps.pnf-sites.info//img/User/resolution_30/pic96422cpycvpowuaauhoh.png"));

		new ImageLoadTasklist(uSR_img[position], imageone).execute();

	//	 imageLoader.DisplayImage(Base_url + uSR_img[position], imageone);

		return rowView;
	}

	class ImageLoadTasklist extends AsyncTask<Void, Void, Bitmap> {
		// ProgressDialog pDialog;
		private String url;
		private ImageView imageViewone;

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

}
