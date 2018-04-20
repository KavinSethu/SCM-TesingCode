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
import android.widget.TextView;

public class ArrayAdapterEdit extends ArrayAdapter<ImageVideoBean> {
    TextView txtTitle;
    private final Activity context;
    private final ArrayList<ImageVideoBean> allFilesList;
    ImageView main_image;
    // private final String[] img_vdo ;
    String[] result;
    UserSessionManager session;
    HashMap<String, String> user;
    String auth_token;
    String Base_url;

    public ArrayAdapterEdit(Activity edit_post, ArrayList<ImageVideoBean> allFilesList
    ) {
        // TODO Auto-generated constructor stub
        super(edit_post, R.layout.list_item_imagevideo, allFilesList);
        this.context = edit_post;
        this.allFilesList = allFilesList;
        session = new UserSessionManager(edit_post);

        user = this.session.getUserDetails();
        auth_token = this.user.get(UserSessionManager.TAG_Authntication_token);
        Base_url = this.user.get(UserSessionManager.TAG_Base_url);

    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        // Log.i("thummmmmm.....", imageId[0]);
        LayoutInflater inflater = context.getLayoutInflater();


// Log.i("vdoname///", Arrays.deepToString(vdoname));

///////////////////////////////////////////////
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_imagevideo, parent, false);

        txtTitle = (TextView) rowView.findViewById(R.id.lli);

        main_image = (ImageView) rowView.findViewById(R.id.imageone);

        /*String titlteName="";*/
        String fileType = "";

        if (allFilesList.get(position).getId().equalsIgnoreCase("0")) {


            String fileExtension = allFilesList.get(position).getFileExtension().substring(allFilesList.get(position).getFileExtension().lastIndexOf(".") + 1);

            txtTitle.setText("index" + (position + 1) + "." + fileExtension);

        } else {
            txtTitle.setText("index" + (position + 1) + "." + allFilesList.get(position).getFileExtension());

        }

        if (allFilesList.get(position).getType().equalsIgnoreCase("image")) {


            if (allFilesList.get(position).getId().equalsIgnoreCase("0")) {

                main_image.setImageBitmap(BitmapFactory
                        .decodeFile(allFilesList.get(position).getFileExtension()));
            } else {
                String imagePath = Base_url
                        + "picture_diary/viewPictureDiaryImages/"
                        + allFilesList.get(position).getId() + "?authentication_token="
                        + auth_token;

                Picasso.with(context)
                        .load(Uri.parse(imagePath))
                        .placeholder(R.drawable.defaultimg)
                        // optional
                        .error(R.drawable.defaultimg)         // optional
                        .into(main_image);


            }
        } else {

            fileType = allFilesList.get(position).getName().substring(allFilesList.get(position).getName().lastIndexOf(".") + 1);


        }


        if (position % 2 == 0) {
            rowView.setBackgroundColor(Color.parseColor("#F5A9A9"));
        } else {
            rowView.setBackgroundColor(Color.parseColor("#F7819F"));
        }

        return rowView;
    }

    ///////
//////
/////
    class ImageLoadTasklist extends AsyncTask<Void, Void, Bitmap> {
        // ProgressDialog pDialog;
        private String url;
        private ImageView imageViewone;

//   public ImageLoadTasklist(String string, ImageView imageView2) {
//	// TODO Auto-generated constructor stub
//}

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
            //  pDialog.dismiss();
            imageViewone.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewone.setImageBitmap(resultlist);
        }

    }


}
