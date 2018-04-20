package com.pnf.elar.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.FileDescriptorBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.VideoBitmapDecoder;

@SuppressWarnings("deprecation")
public class IntroPageAdaptertwo extends PagerAdapter {

	String[] img;
	String[] vd_url;
	Context _context;
	TextView txtTitle;
	static ImageView imgView;
	public ImageLoadernew imageLoader;
	private static LayoutInflater inflater = null;

	public IntroPageAdaptertwo(String[] head2, String[] v_url, Context loginlive) {
		// TODO Auto-generated constructor stub
		this.img = new String[head2.length];
		this.img = head2;
		this.vd_url = new String[v_url.length];
		this.vd_url = v_url;
		this._context = loginlive;
		imageLoader = new ImageLoadernew(loginlive);
	}

	@Override
	public View instantiateItem(ViewGroup container, final int position) {
		try {
			Log.i("vvvvvvv", img[position]);
		} catch (Exception e) {
			// TODO: handle exception
		}

		LayoutInflater inflater = ((Activity) _context).getLayoutInflater();
		inflater = (LayoutInflater) _context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView;

		rowView = inflater.inflate(R.layout.intro_page_two, null, true);

		ImageView imgView = (ImageView) rowView.findViewById(R.id.imageView12);

		// new ImageLoadTaskcliptwo(img[position],imgView).execute();

		imgView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent rere = new Intent(_context, Post_play_video.class);
				rere.putExtra("v_url", vd_url[position]);
				_context.startActivity(rere);
			}
		});

		try {
		//	imageLoader.DisplayImage(img[position], imgView);


		} catch (Exception e) {
			// TODO: handle exception
		}


		BitmapPool bitmapPool = Glide.get(_context).getBitmapPool();
		int microSecond = 6000000;// 6th second as an example
		VideoBitmapDecoder videoBitmapDecoder = new VideoBitmapDecoder(microSecond);
		FileDescriptorBitmapDecoder fileDescriptorBitmapDecoder = new FileDescriptorBitmapDecoder(videoBitmapDecoder, bitmapPool, DecodeFormat.PREFER_ARGB_8888);
		Glide.with(_context)
				.load(img[position])
				.asBitmap()

				.videoDecoder(fileDescriptorBitmapDecoder)
				.into(imgView);
		container.addView(rowView);

		return rowView;

	}

//	class ImageLoadTaskcliptwo extends AsyncTask<Void, Void, Bitmap> {
//		// ProgressDialog pDialog;
//		private String url;
//		private ImageView imageView;
//
//		public ImageLoadTaskcliptwo(String url, ImageView imageView) {
//			this.url = url;
//			this.imageView = imageView;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			// pDialog = new ProgressDialog(_context);
//			// pDialog.setMessage("load image ....");
//			// pDialog.setIndeterminate(false);
//			// pDialog.setCancelable(false);
//			// pDialog.show();
//		}
//
//		@Override
//		protected Bitmap doInBackground(Void... params) {
//			try {
//				URL urlConnection = new URL(url);
//				HttpURLConnection connection = (HttpURLConnection) urlConnection
//						.openConnection();
//				connection.setDoInput(true);
//				connection.connect();
//				InputStream input = connection.getInputStream();
//				Bitmap myBitmap = BitmapFactory.decodeStream(input);
//				return myBitmap;
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(Bitmap result) {
//			super.onPostExecute(result);
//			// pDialog.dismiss();
//			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//			imageView.setImageBitmap(result);
//		}
//
//	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return img.length;
	}

}