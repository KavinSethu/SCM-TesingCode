package com.pnf.elar.app;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.pnf.elar.app.Bo.ImageVideoBean;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class ArrayAdaptert extends ArrayAdapter<ImageVideoBean> {
    TextView txtTitle;
    private final Activity context;
    private final ArrayList<ImageVideoBean> all;
    ImageView cross, main_image;

    public ImageLoadernew imageLoader;

    UserSessionManager session;
    HashMap<String, String> user;
    String auth_token;
    String Base_url;
    // LayoutInflater inflater;
    // static ImageView imageView;

    // public ArrayAdaptert(Context curriculum_tags,
    // String[] curriclm_tg_title) {
    // super(curriculum_tags, R.layout.curlm_listtwo, curriclm_tg_title);
    // this.context=curriculum_tags;
    // this.child = curriclm_tg_title;
    //
    // }

    public ArrayAdaptert(Activity applicationContext, ArrayList<ImageVideoBean> al) {
        // TODO Auto-generated constructor stub
        super(applicationContext, R.layout.list_item_imagevideo, al);
        this.context = applicationContext;
        this.all = al;

        session = new UserSessionManager(applicationContext);

        user = this.session.getUserDetails();
        auth_token = this.user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = this.user.get(UserSessionManager.TAG_Base_url);
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        // Log.i("thummmmmm.....", imageId[0]);
        LayoutInflater inflater = context.getLayoutInflater();

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_imagevideo, parent,
                false);
        cross = (ImageView) rowView.findViewById(R.id.cross);
        main_image = (ImageView) rowView.findViewById(R.id.imageone);
        txtTitle = (TextView) rowView.findViewById(R.id.lli);


        String fileExtension = all.get(position).getName().substring(all.get(position).getName().lastIndexOf(".") + 1);

        txtTitle.setText("index" + (position + 1) + "." + fileExtension);



        /*if (all.get(position).getId().equalsIgnoreCase("0")) {



            txtTitle.setText("index" + (position + 1) + "." + fileExtension);

        } else {
            txtTitle.setText("index" + (position + 1) + "." + all.get(position).getName());

        }*/



       /* if (all.get(position).getId().equalsIgnoreCase("0")) {
            *//*String[] bits = all.get(position).getName().split("/");*//*
*//*
            String lastOne = bits[bits.length - 1];
*//*
            System.out.println("all.get(position).getName()" + all.get(position).getName());

            String lastOne = all.get(position).getName().substring(all.get(position).getName().lastIndexOf("/") + 1);

            txtTitle.setText(lastOne);
        } else {
            txtTitle.setText(all.get(position).getName());
        }
*/

        main_image.setScaleType(ScaleType.FIT_XY); /////////////////// new

        // new ImageLoadTasklist(all.get(position),main_image);
        try {
            if (all.get(position).getType().equalsIgnoreCase("image")) {
                if (all.get(position).getId().equalsIgnoreCase("0")) {
                    main_image.setImageBitmap(BitmapFactory.decodeFile(all
                            .get(position).getName()));
                } else {


                    String imagePath = Base_url
                            + "picture_diary/viewPictureDiaryImages/"
                            + all.get(position).getId() + "?authentication_token="
                            + auth_token;

                    Picasso.with(context)
                            .load(Uri.parse(imagePath))
                            .placeholder(R.drawable.defaultimg)
                            // optional
                            .error(R.drawable.defaultimg)         // optional
                            .into(main_image);
                }
            } else {


            }
        } catch (OutOfMemoryError e) {
            // TODO: handle exception
        }

     /*   cross.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        });*/

        if (position % 2 == 0) {
            rowView.setBackgroundColor(Color.parseColor("#F6CED8"));
        } else {
            rowView.setBackgroundColor(Color.parseColor("#F7819F"));
        }

        return rowView;
    }

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

}
