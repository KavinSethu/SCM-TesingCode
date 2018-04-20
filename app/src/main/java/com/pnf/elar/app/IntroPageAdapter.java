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
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pnf.elar.app.activity.PhotoGalleryActivity;
import com.pnf.elar.app.util.Const;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class IntroPageAdapter extends PagerAdapter {

    String[] img;
    String[] img_id;
    Context _context;
    TextView txtTitle;
    static ImageView imgView;
    public ImageLoadernew imageLoader;
    private static LayoutInflater inflater = null;

    public IntroPageAdapter(String[] head2, Context loginlive) {
        // TODO Auto-generated constructor stub
        this.img = new String[head2.length];
        this.img = head2;

        this._context = loginlive;
        imageLoader = new ImageLoadernew(loginlive);
        Log.i("image length", head2.length + "");
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        try {
            Log.i("vvvvvvv" + position, img[position]);
        } catch (Exception e) {
            // TODO: handle exception
        }

        LayoutInflater inflater = ((Activity) _context).getLayoutInflater();
        inflater = (LayoutInflater) _context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.intro_page, null, true);

        final ImageView imgView = (ImageView) rowView.findViewById(R.id.imageView1);
        /*imgView.setScaleType(ScaleType.MATRIX);*/


        imgView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //	Toast.makeText(_context, "working..", Toast.LENGTH_SHORT).show();

              /*  Intent in = new Intent(_context, View_pageraimages.class);
                in.putExtra("View_pager_images", img[position]);
                _context.startActivity(in);*/


                Intent intent = new Intent(_context, PhotoGalleryActivity.class);

                intent.putStringArrayListExtra(Const.CommonParams.EXTRA_NAME, new ArrayList(Arrays.asList(img)));
                _context.startActivity(intent);
            }
        });





        Glide.with(_context).load(img[position])
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(R.drawable.loaderbg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgView);




      /*  Picasso.with(_context)
                .load(img[position])
                .placeholder(R.drawable.loaderbg)
                .into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();

                imgView.setImageBitmap(bitmap);


                System.out.println("height "+height+" width "+width);


              *//*  if(bitmap.getHeight()<200)
                {

                    imgView.getLayoutParams().height=height+(height/3);
                    imgView.setImageBitmap(bitmap);


                    imgView.setScaleType(ImageView.ScaleType.FIT_XY);
                } else if(bitmap.getWidth()<200)
                {
                    imgView.getLayoutParams().width=height+(width/3);
                    imgView.setImageBitmap(bitmap);


                    imgView.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                else {*//*
                    imgView.setImageBitmap(bitmap);
                *//*}*//*
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });*/























		/*Picasso.with(_context)
				.load(Uri.parse(img[position]))
				.placeholder(R.drawable.loaderbg)
				// optional
				.error(R.drawable.loaderbg)         // optional
				.into(imgView);
*/
     //   imageLoader.DisplayImage(img[position], imgView);

        /*imgView.requestLayout();*/


	/*	System.out.println("height :  "+((Activity)_context).getWindowManager().getDefaultDisplay()
				.getHeight());
		Bitmap bMapScaled =imageLoader.getBitmap(img[position]);
// Loads the resized Bitmap into an ImageView
*//*
		ImageView image = (ImageView) findViewById(R.id.test_image);
*//*








		if (bMapScaled != null) {
			boolean flag = true;

			int deviceWidth = ((Activity)_context).getWindowManager().getDefaultDisplay()
					.getWidth();
			int deviceHeight = ((Activity)_context).getWindowManager().getDefaultDisplay()
					.getHeight();

			int bitmapHeight = bMapScaled.getHeight(); // 563
			int bitmapWidth = bMapScaled.getWidth(); // 900

// aSCPECT rATIO IS Always WIDTH x HEIGHT rEMEMMBER 1024 x 768

			if (bitmapWidth > deviceWidth) {
				flag = false;

// scale According to WIDTH
				int scaledWidth = deviceWidth;
				int scaledHeight = (scaledWidth * bitmapHeight) / bitmapWidth;

				try {
					if (scaledHeight > deviceHeight)
						scaledHeight = deviceHeight;

					bMapScaled = Bitmap.createScaledBitmap(bMapScaled, scaledWidth,
							scaledHeight, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (flag) {
				if (bitmapHeight > deviceHeight) {
					// scale According to HEIGHT
					int scaledHeight = deviceHeight;
					int scaledWidth = (scaledHeight * bitmapWidth)
							/ bitmapHeight;

					try {
						if (scaledWidth > deviceWidth)
							scaledWidth = deviceWidth;

						bMapScaled = Bitmap.createScaledBitmap(bMapScaled, scaledWidth,
								scaledHeight, true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}






		imgView.setImageBitmap(bMapScaled);*/


        container.addView(rowView);
        return rowView;
    }

	/*public Bitmap scaleToActualAspectRatio(Bitmap bitmap) {
		if (bitmap != null) {
			boolean flag = true;

			int deviceWidth = getWindowManager().getDefaultDisplay()
					.getWidth();
			int deviceHeight = getWindowManager().getDefaultDisplay()
					.getHeight();

			int bitmapHeight = bitmap.getHeight(); // 563
			int bitmapWidth = bitmap.getWidth(); // 900

// aSCPECT rATIO IS Always WIDTH x HEIGHT rEMEMMBER 1024 x 768

			if (bitmapWidth > deviceWidth) {
				flag = false;

// scale According to WIDTH
				int scaledWidth = deviceWidth;
				int scaledHeight = (scaledWidth * bitmapHeight) / bitmapWidth;

				try {
					if (scaledHeight > deviceHeight)
						scaledHeight = deviceHeight;

					bitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth,
							scaledHeight, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (flag) {
				if (bitmapHeight > deviceHeight) {
					// scale According to HEIGHT
					int scaledHeight = deviceHeight;
					int scaledWidth = (scaledHeight * bitmapWidth)
							/ bitmapHeight;

					try {
						if (scaledWidth > deviceWidth)
							scaledWidth = deviceWidth;

						bitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth,
								scaledHeight, true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return bitmap;
	}*/

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