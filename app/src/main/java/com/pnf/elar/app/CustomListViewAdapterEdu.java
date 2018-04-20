package com.pnf.elar.app;
//package com.example.elar_app;
//
//import java.io.BufferedInputStream;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import com.example.elar_app.CustomAdpterPost.likeclass;
//import com.viewpagerindicator.CirclePageIndicator;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.drawable.Drawable;
//import android.os.AsyncTask;
//import android.os.Environment;
//import android.support.v4.view.ViewPager;
//import android.text.Html;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.Toast;
//
//public class CustomListViewAdapterEdu extends BaseAdapter {
//
//	UserSessionManager session;
//	String count, auth_token, Base_url;
//	int c = 0;
//	// ViewHolder holder;
//	ViewPager introSlider, introSlidertwo;
//	CirclePageIndicator indicator, indicatortwo;
//	private final Activity contextA;
//	private final String[] catgry;
//	private final String[] tec;
//	private final String[] dess;
//	private final String[] pst_id;
//	String[] curriclm_tg_title;
//	// private static LayoutInflater inflater = null;
//	private final ArrayList<String[]> lis;
//	private final ArrayList<String[]> vdio;
//	private final ArrayList<String[]> vdio_url;
//	private final ArrayList<String[]> std;
//	private final ArrayList<String[]> randm_nme;
//	ArrayList<String[]> curriculum_post_title;
//	ArrayList<String[]> list1;
//	public static HashMap<String, ArrayList<String[]>> map;
//	TextView random1;
//
//	ImageView curriculm_image, pen;
//	ListView list;
//	TextView txtTitle;
//	ListView dialog_ListView;
//	RelativeLayout vedio_sliders;
//	RelativeLayout imageview_slider;
//	LinearLayout likess;
//	String[] listContent = { "January", "February" };
//	String[] TAG_s_name;
//	Drawable myDrawable;
//	String[] like, alrdy_liked;
//	int kk;
//	TextView lik;
//
//	LayoutInflater inflater;
//	// String count;
//	String dwnload_file_path = "http://ps.pnf-sites.info/picture_diary/viewOtherFiles/305?authentication_token=86e63e91b05fb446ab01e6732da96dcb2bffc408";
//	int totalSize = 0;
//	ProgressBar pb;
//	int downloadedSize = 0;
//	TextView cur_val;
//	int imageicon;
//	ImageView immm;
//
//	private ProgressDialog pDialog;
//
//	// Progress dialog type (0 - for Horizontal progress bar)
//	public static final int progress_bar_type = 0;
//
//	// File url to download
//	private static String file_url = "http://maven.apache.org/maven-1.x/maven.pdf";
//
//	public CustomListViewAdapterEdu(MainActivity context, String[] web,
//			String[] imageIdthumb, String[] vediopostedOn12, String[] post_idc,
//			ArrayList<String[]> lis, ArrayList<String[]> li,
//			ArrayList<String[]> st, ArrayList<String[]> randm, String[] liked,
//			ArrayList<String[]> vdio_urll,
//			ArrayList<String[]> curriculum_post_title1,
//			ArrayList<String[]> list12,
//			HashMap<String, ArrayList<String[]>> map1, String[] already_liked) {
//
//		this.contextA = context;
//		this.catgry = web;
//		this.tec = imageIdthumb;
//		this.dess = vediopostedOn12;
//		this.pst_id = post_idc;
//		this.lis = lis;
//		this.vdio = li;
//		this.std = st;
//		this.randm_nme = randm;
//		this.like = liked;
//		this.vdio_url = vdio_urll;
//		this.curriculum_post_title = curriculum_post_title1;
//		this.list1 = list12;
//		this.map = map1;
//		this.alrdy_liked = already_liked;
//		// imageLoader=new ImageLoader(context);............................
//
//		inflater = (LayoutInflater) context
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//	}
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return catgry.length;
//	}
//
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//
//		session = new UserSessionManager(contextA);
//
//		HashMap<String, String> user = session.getUserDetails();
//
//		auth_token = user.get(UserSessionManager.TAG_Authntication_token);
//		Base_url = user.get(UserSessionManager.TAG_Base_url);
//		// TODO Auto-generated method stub
//		if (inflater == null)
//			inflater = (LayoutInflater) contextA
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		if (convertView == null)
//			convertView = inflater.inflate(R.layout.custompost, null);
//		// ////////////////////////////////////
//		immm = (ImageView) convertView.findViewById(R.id.immm);
//		lik = (TextView) convertView.findViewById(R.id.likecount);
//
//		// //////////////////////////////////////////////////////////////////////////////////////////////////////
//		imageview_slider = (RelativeLayout) convertView
//				.findViewById(R.id.imageview_slider);
//
//		vedio_sliders = (RelativeLayout) convertView
//				.findViewById(R.id.vedio_sliders);
//
//		// ///////////////////////////////////////////////////////////
//		curriculm_image = (ImageView) convertView
//				.findViewById(R.id.curriculm_image);
//		likess = (LinearLayout) convertView.findViewById(R.id.likess);
//		txtTitle = (TextView) convertView.findViewById(R.id.catagry);
//		TextView txtTitledate = (TextView) convertView
//				.findViewById(R.id.belowfirst);
//		random1 = (TextView) convertView.findViewById(R.id.random1);
//
//		TextView des = (TextView) convertView.findViewById(R.id.des);
//		// immm = (ImageView) rowView.findViewById(R.id.immm);
//		pen = (ImageView) convertView.findViewById(R.id.pen);
//		kk = Integer.parseInt(like[position]);
//		// //////////////// ///////////////////////////////////
//
//		// //////////////////////////////////////////
//
//		Log.i("cur.........", Arrays.deepToString(curriclm_tg_title));
//
//		if (!(alrdy_liked[position].equalsIgnoreCase("yes")))
//			;
//		{
//			Log.i("like Status..", alrdy_liked[position]);
//
//			// holder.immm.setVisibility(View.GONE);
//
//			immm.setBackgroundResource(R.drawable.like1);
//			if (alrdy_liked[position].equalsIgnoreCase("yes")) {
//				immm.setBackgroundResource(R.drawable.like2);
//				imageicon = 1;
//			}
//
//		}
//
//		if (!(curriculum_post_title.get(position) != null && curriculum_post_title
//				.get(position).length > 0)) {
//
//			// Log.i("cur.........",Arrays.deepToString(curriculum_post_title.get(3))
//			// );
//			curriculm_image.setVisibility(View.GONE);
//		}
//
//		else {
//
//			curriculm_image.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					// Log.i("123456789",
//					// Arrays.deepToString(list1.get(position)));
//
//					Intent li = new Intent(contextA, Curriculam_tag_post.class);
//					li.putExtra("tagsss", curriculum_post_title.get(position));
//					li.putExtra("child_title", list1);
//					li.putExtra("Sub_child_title", map);
//					// rowView.
//					contextA.startActivity(li);
//					contextA.finish();
//
//					// new
//					// Curriculam_tag_post(getContext(),curriculum_post_title.get(position),list1);
//
//					// Intent li = new
//					// Intent(getContext(),Curriculam_tag_post.class);
//					// contextA.startActivity(li);
//					// contextA.finish();
//
//				}
//			});
//		}
//		// /////////
//
//		pen.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//				Intent in = new Intent(contextA, Edit_post.class);
//				in.putExtra("post_id", pst_id[position]);
//				contextA.startActivity(in);
//
//			}
//		});
//
//		// /////////////////////////////////////////////
//
//		immm.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				// Log.d("fregreger", "gregregerger  "+pst_id[position]);
////				c = c + 1;
////				// new likeclass(pst_id[position]).execute();
////
////				Toast.makeText(contextA, "" + c, Toast.LENGTH_SHORT).show();
////				// lik.setText("" + c);
////
////				TextView main = ((TextView) v);
////				main.setText("" + c);
//				immm.setBackgroundResource(R.drawable.ic_launcher);
//
//			}
//		});
//
//		// imagPlay.setOnClickListener(new View.OnClickListener() {
//		// @Override
//		// public void onClick(View v) {
//		// ImageView current=((ImageView)v);
//		// current.setSelected(true);
//		// previous.setSelected(false);
//		// previous=current;
//		// }
//		// });
//
//		lik.setText(like[position]);
//
//		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		try {
//			if (catgry[position].equalsIgnoreCase("student")) {
//				String[] pp = std.get(position);
//				txtTitle.setText(pp[0] + "#........");
//			} else {
//				txtTitle.setText(catgry[position]);
//			}
//
//			txtTitledate.setText(tec[position]);
//			des.setText(Html.fromHtml(dess[position]));
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		// ///
//		// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		try {
//
//			introSlider = (ViewPager) convertView
//					.findViewById(R.id.intro_pager);
//			indicator = (CirclePageIndicator) convertView
//					.findViewById(R.id.indicator);
//			if (!(lis.get(position).length == 0)) {
//
//				introSlider.setAdapter(new IntroPageAdapter(lis.get(position),
//						contextA));
//				introSlider.setCurrentItem(0);
//				indicator.setViewPager(introSlider);
//			} else {
//				imageview_slider.setVisibility(View.GONE);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		try {
//			introSlidertwo = (ViewPager) convertView
//					.findViewById(R.id.intro_pagertwo);
//			indicatortwo = (CirclePageIndicator) convertView
//					.findViewById(R.id.indicatortwo);
//
//			if (!(vdio.get(position).length == 0)) {
//
//				introSlidertwo.setAdapter(new IntroPageAdaptertwo(vdio
//						.get(position), vdio_url.get(position), contextA));
//				introSlidertwo.setCurrentItem(0);
//				indicatortwo.setViewPager(introSlidertwo);
//			} else {
//				vedio_sliders.setVisibility(View.GONE);
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		// Log.i("randm_nmerandm_nme",
//		// Arrays.deepToString(randm_nme.get(position)));
//
//		// try {
//		// String[] uiui ={"sfsf","dsfdsf","fdsfd"};
//		// adapter = new ArrayAdapter<String>(getContext(),
//		// R.layout.spinner_item, uiui);
//		// spinner.setAdapter(adapter);
//		// spinner.setOnItemSelectedListener(new
//		// AdapterView.OnItemSelectedListener() {
//		//
//		// @Override
//		// public void onItemSelected(AdapterView<?> arg0, View arg1,
//		// int arg2, long arg3) {
//		//
//		// int position = spinner.getSelectedItemPosition();
//		// //
//		// Toast.makeText(getApplicationContext(),"You have selected "+curriculum_title[+position],Toast.LENGTH_LONG).show();
//		// Log.i("position ", Integer.toString(position));
//		// // TODO Auto-generated method stub
//		// // criclum_id = curriculum_id[position];
//		// }
//		//
//		// @Override
//		// public void onNothingSelected(AdapterView<?> arg0) {
//		// // TODO Auto-generated method stub
//		//
//		// }
//		//
//		// });
//		//
//		//
//		// } catch (Exception e) {
//		// // TODO: handle exception
//		// }
//		//
//		//
//
//		// //////////////////////////////////////////////////////////////////////////////////////////////
//
//		// ///////////////////////////////////////////////////////////////////////////////////////
//
//		try {
//			String[] rn_files = randm_nme.get(position);
//			Log.i("random files..", Arrays.deepToString(rn_files));
//			// if(!(rn_files[0]==null)){
//			random1.setText(rn_files[0] + ".....");
//
//			random1.setOnClickListener(new OnClickListener() {
//				Dialog dialog;
//
//				@Override
//				public void onClick(View v) {
//					try {
//
//						String[] rn_files1 = randm_nme.get(position);
//
//						Log.i("====random files..",
//								Arrays.deepToString(rn_files1));
//
//						if ((rn_files1 != null && rn_files1.length > 0)) {
//							dialog = new Dialog(contextA);
//
//							dialog.setContentView(R.layout.dialoglayout);
//							dialog.setTitle("Random files... ");
//
//							dialog.setCancelable(true);
//							dialog.setCanceledOnTouchOutside(true);
//
//							dialog_ListView = (ListView) dialog
//									.findViewById(R.id.dialoglist);
//							ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//									contextA,
//
//									android.R.layout.simple_list_item_1,
//									rn_files1);
//							dialog_ListView.setAdapter(adapter);
//							dialog.show();
//
//							dialog_ListView
//									.setOnItemClickListener(new OnItemClickListener() {
//
//										@Override
//										public void onItemClick(
//												AdapterView<?> parent,
//												View view, int position, long id) {
//											// TODO Auto-generated method stub
//											dialog.dismiss();
//											new DownloadFileFromURL()
//													.execute(file_url);
//
//										}
//
//										// //////////////////////////////////////////////////////////////////////////////////////////////
//
//										class DownloadFileFromURL
//												extends
//												AsyncTask<String, String, String> {
//
//											/**
//											 * Before starting background thread
//											 * Show Progress Bar Dialog
//											 * */
//											@Override
//											protected void onPreExecute() {
//												super.onPreExecute();
//												showDialog(progress_bar_type);
//											}
//
//											private void showDialog(
//													int progressBarType) {
//
//												// TODO Auto-generated method
//												// stub
//
//												pDialog = new ProgressDialog(
//														contextA);
//												pDialog.setMessage("Downloading file. Please wait...");
//												pDialog.setIndeterminate(false);
//												pDialog.setMax(100);
//												pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//												pDialog.setCancelable(true);
//												pDialog.show();
//
//											}
//
//											/**
//											 * Downloading file in background
//											 * thread
//											 * */
//											@Override
//											protected String doInBackground(
//													String... f_url) {
//												int count;
//												try {
//													URL url = new URL(f_url[0]);
//													URLConnection conection = url
//															.openConnection();
//													conection.connect();
//													// getting file length
//													int lenghtOfFile = conection
//															.getContentLength();
//
//													// input stream to read file
//													// - with 8k buffer
//													InputStream input = new BufferedInputStream(
//															url.openStream(),
//															8192);
//
//													// Output stream to write
//													// file
//													OutputStream output = new FileOutputStream(
//															"/sdcard/file.pdf");
//
//													byte data[] = new byte[1024];
//
//													long total = 0;
//
//													while ((count = input
//															.read(data)) != -1) {
//														total += count;
//														// publishing the
//														// progress....
//														// After this
//														// onProgressUpdate will
//														// be called
//														publishProgress(""
//																+ (int) ((total * 100) / lenghtOfFile));
//
//														// writing data to file
//														output.write(data, 0,
//																count);
//													}
//
//													// flushing output
//													output.flush();
//
//													// closing streams
//													output.close();
//													input.close();
//
//												} catch (Exception e) {
//													Log.e("Error: ",
//															e.getMessage());
//												}
//
//												return null;
//											}
//
//											/**
//											 * Updating progress bar
//											 * */
//											protected void onProgressUpdate(
//													String... progress) {
//												// setting progress percentage
//												pDialog.setProgress(Integer
//														.parseInt(progress[0]));
//											}
//
//											/**
//											 * After completing background task
//											 * Dismiss the progress dialog
//											 * **/
//											@Override
//											protected void onPostExecute(
//													String file_url) {
//												// dismiss the dialog after the
//												// file was downloaded
//												// dismissDialog(progress_bar_type);
//
//												// Displaying downloaded image
//												// into image view
//												// Reading image path from
//												// sdcard
//												String imagePath = Environment
//														.getExternalStorageDirectory()
//														.toString()
//														+ "/downloadedfile.jpg";
//												// setting downloaded into image
//												// view
//												// my_image.setImageDrawable(Drawable.createFromPath(imagePath));
//											}
//
//										}
//
//										// ///////////////////////////////////////////////////////////////////////////////////////////
//
//									});
//
//							// Log.i("yyyyyyyyyy",
//							// Arrays.deepToString(TAG_s_name));
//						}
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//
//				}
//			});
//
//			// }else {
//			// random1.setVisibility(View.GONE);
//			//
//			// }
//			//
//			// if(!(rn_files[1]==null)){
//			// random2.setText(rn_files[1]);
//			// }else {
//			// random2.setVisibility(View.GONE);
//			// }
//			//
//			// if(!(rn_files[2]==null)){
//			// random3.setText(rn_files[2]);
//			// }else {
//			//
//			// random3.setVisibility(View.GONE);
//			// }
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		// /////////////////////////////////////////////////////////////////////////////////////////
//
//		// ////////////////////////////////////////////////////////////////////////////////////////
//
//		// ///////////
//		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		// new imgessss(pst_id[position]).execute();
//
//		txtTitle.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				try {
//
//					if (!(std.get(position).length == 0)) {
//						Dialog dialog = new Dialog(contextA);
//
//						dialog.setContentView(R.layout.dialoglayout);
//						dialog.setTitle("Students Name.. ");
//
//						dialog.setCancelable(true);
//						dialog.setCanceledOnTouchOutside(true);
//
//						dialog_ListView = (ListView) dialog
//								.findViewById(R.id.dialoglist);
//						ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//								contextA,
//
//								android.R.layout.simple_list_item_1, std
//										.get(position));
//						dialog_ListView.setAdapter(adapter);
//						dialog.show();
//
//						// Log.i("yyyyyyyyyy", Arrays.deepToString(TAG_s_name));
//					}
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//
//			}
//		});
//
//		return convertView;
//	}
//
//	class likeclass extends AsyncTask<String, String, String> {
//
//		JSONParser jsonParser = new JSONParser();
//		String post_id;
//		String status;
//
//		private ProgressDialog pDialog;
//		private String url_create_product = Base_url
//				+ "/lms_api/picture_diary/like_post";
//
//		private static final String TAG_staus = "status";
//		private static final String TAG_post_like_count = "post_like_count";
//
//		public likeclass(String string) {
//			// TODO Auto-generated constructor stub
//			this.post_id = string;
//		}
//
//		/**
//		 * Before starting background thread Show Progress Dialog
//		 * */
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(contextA);
//			pDialog.setMessage(null);
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(false);
//
//			pDialog.show();
//
//			// Toast.makeText(getApplicationContext(),
//			// Login_Email+Login_Password,Toast.LENGTH_LONG).show();
//		}
//
//		/**
//		 * Creating product
//		 * */
//		protected String doInBackground(String... args) {
//
//			Log.i("ttttttttttttt", auth_token);
//			// Building Parameters
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("securityKey", "H67jdS7wwfh"));
//			params.add(new BasicNameValuePair("authentication_token",
//					auth_token));
//			params.add(new BasicNameValuePair("language", "en"));
//			params.add(new BasicNameValuePair("post_id", post_id));
//
//			JSONObject json = jsonParser.makeHttpRequest(url_create_product,
//					"POST", params);
//
//			// check for success tag
//			Log.i("json data......", json.toString());
//			try {
//				status = json.getString(TAG_staus);
//
//				Log.e("=-=-=-=-=-=-", status);
//				count = json.getString(TAG_post_like_count);
//
//				// Log.i("count value ", count);
//				if (status.equalsIgnoreCase("true")) {
//
//					// successfully created product
//
//					// Toast.makeText(getApplicationContext(),
//					// "Sign in Sucessfully", Toast.LENGTH_LONG).show();
//				} else {
//					// failed to create product
//					// Toast.makeText(getApplicationContext(),
//					// "invalid business_email and business_password",
//					// Toast.LENGTH_LONG).show();
//				}
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//
//			return null;
//		}
//
//		/**
//		 * After completing background task Dismiss the progress dialog
//		 * **/
//		protected void onPostExecute(String file_url) {
//			// dismiss the dialog once done
//			pDialog.dismiss();
//
//			// Log.i("oooooo", imm[0]);
//			if (status.equalsIgnoreCase("true")) {
//
//				// Toast.makeText(getContext(),
//				// "execute....",Toast.LENGTH_SHORT).show();
//
//				Log.i("countcount", count);
//
//			}
//
//		}
//	}
//
//}
