package com.pnf.elar.app;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

public class IntroPageAdapter_fullImage extends PagerAdapter {
	
	private static final String TAG = "Touch";

	// These matrices will be used to move and zoom image
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();

	// We can be in one of these 3 states
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;

	// Remember some things for zooming
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist = 1f;
	
    String[] img;
	String[] img_id;
	Context _context;
	TextView txtTitle;
	static ImageView imgView;
	public ImageLoadernew imageLoader;
	private static LayoutInflater inflater = null;

	public IntroPageAdapter_fullImage(String[] head2, Context applicationContext) {
		// TODO Auto-generated constructor stub
		this.img = new String[head2.length];
		this.img = head2;

		this._context = applicationContext;
		imageLoader = new ImageLoadernew(applicationContext);
	}

	@Override
	public View instantiateItem( ViewGroup container, int position) {
		
		LayoutInflater inflater = ((Activity) _context).getLayoutInflater();
		inflater = (LayoutInflater) _context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.image_full_view, null, true);
		
		final ImageView view = (ImageView) rowView.findViewById(R.id.imageView);
		
		imageLoader.DisplayImage(img[position], view);
		
		view.setScaleType(ImageView.ScaleType.FIT_CENTER); // make the image fit to the center.
//		view.setOnTouchListener(_context);
		
		view.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				 ImageView view = (ImageView) v;
				   // make the image scalable as a matrix
				   view.setScaleType(ImageView.ScaleType.MATRIX);
				   float scale;

				   // Handle touch events here...
				   switch (event.getAction() & MotionEvent.ACTION_MASK) {

				   case MotionEvent.ACTION_DOWN: //first finger down only
				      savedMatrix.set(matrix);
				      start.set(event.getX(), event.getY());
				      Log.d(TAG, "mode=DRAG" );
				      mode = DRAG;
				      break;
				   case MotionEvent.ACTION_UP: //first finger lifted
				   case MotionEvent.ACTION_POINTER_UP: //second finger lifted
				      mode = NONE;
				      Log.d(TAG, "mode=NONE" );
				      break;
				   case MotionEvent.ACTION_POINTER_DOWN: //second finger down
				      oldDist = spacing(event); // calculates the distance between two points where user touched.
				      Log.d(TAG, "oldDist=" + oldDist);
				      // minimal distance between both the fingers
				      if (oldDist > 5f) {
				         savedMatrix.set(matrix);
				         midPoint(mid, event); // sets the mid-point of the straight line between two points where user touched. 
				         mode = ZOOM;
				         Log.d(TAG, "mode=ZOOM" );
				      }
				      break;

				   case MotionEvent.ACTION_MOVE: 
				      if (mode == DRAG) 
				      { //movement of first finger
				         matrix.set(savedMatrix);
				         if (view.getLeft() >= -392)
				         {
				            matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
				         }
				      }
				      else if (mode == ZOOM) { //pinch zooming
				         float newDist = spacing(event);
				         Log.d(TAG, "newDist=" + newDist);
				         if (newDist > 5f) {
				            matrix.set(savedMatrix);
				            scale = newDist/oldDist; //thinking I need to play around with this value to limit it**
				            matrix.postScale(scale, scale, mid.x, mid.y);
				         }
				      }
				      break;
				   }

				   // Perform the transformation
				   view.setImageMatrix(matrix);
				
				return false;
			}
		});
		container.addView(rowView);
		return rowView;
	}
///////////	
	private float spacing(MotionEvent event) {
		   float x = event.getX(0) - event.getX(1);
		   float y = event.getY(0) - event.getY(1);
		   return (float)Math.sqrt(x * x + y * y);
		}

		private void midPoint(PointF point, MotionEvent event) {
		   float x = event.getX(0) + event.getX(1);
		   float y = event.getY(0) + event.getY(1);
		   point.set(x / 2, y / 2);
		}
////////////////////////////////
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
/////////////////////////////////////////
}