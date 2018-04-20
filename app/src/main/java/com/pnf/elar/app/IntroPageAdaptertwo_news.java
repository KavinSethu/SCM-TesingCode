package com.pnf.elar.app;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import com.pnf.elar.app.activity.PhotoGalleryActivity;
import com.pnf.elar.app.activity.VideoGalleryActivity;
import com.pnf.elar.app.util.Const;

@SuppressWarnings("deprecation")
public class IntroPageAdaptertwo_news extends PagerAdapter {

    String[] img ;
    String[] v_url ;
	String[] img_id;
	Context _context;
	TextView txtTitle;
	static ImageView imgView;
	public ImageLoader_news imageLoader;
	private static LayoutInflater inflater = null;

	public IntroPageAdaptertwo_news(String[] head2, String[] vd_url,Context loginlive) {
		// TODO Auto-generated constructor stub
		this.img = new String[head2.length];
		this.img = head2;
		this.v_url = new String[vd_url.length];
        this.v_url= vd_url;
		this._context = loginlive;
		imageLoader = new ImageLoader_news(loginlive);
	}

	@Override
	public View instantiateItem(ViewGroup container, final int position) {
		try {
			Log.i("vul", v_url[position]);
		} catch (Exception e) {
			// TODO: handle exception
		}

		LayoutInflater inflater = ((Activity) _context).getLayoutInflater();
		inflater = (LayoutInflater) _context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.intro_page_two_news, null, true);

		ImageView imgView = (ImageView) rowView.findViewById(R.id.imageView12);

	//    new ImageLoadTaskclip(img[position], imgView).execute();

/*
	    imageLoader.DisplayImage(img[position], imgView);
*/

	    imgView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent rere = new Intent(_context, Post_play_video.class);
				rere.putExtra("v_url", v_url[position]);
				_context.startActivity(rere);
				/*Intent intent = new Intent(_context, VideoGalleryActivity.class);

				intent.putStringArrayListExtra(Const.CommonParams.EXTRA_NAME, new ArrayList(Arrays.asList(v_url)));
				_context.startActivity(intent);*/
				
			}
		});
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

	class ImageLoadTaskclip extends AsyncTask<Void, Void, Bitmap> {
		 ProgressDialog pDialog;
		private String url;
		private ImageView imageView;

		public ImageLoadTaskclip(String url, ImageView imageView) {
			this.url = url;
			this.imageView = imageView;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			 pDialog = new ProgressDialog(_context);
//			 pDialog.setMessage("load image ....");
//			 pDialog.setIndeterminate(false);
//			 pDialog.setCancelable(false);
//			 pDialog.show();
		}

		@Override
		protected Bitmap doInBackground(Void... params) {
			try {
				URL urlConnection = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) urlConnection
						.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				Bitmap myBitmap = BitmapFactory.decodeStream(input);
				return myBitmap;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			// pDialog.dismiss();
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setImageBitmap(result);
		}

	}

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