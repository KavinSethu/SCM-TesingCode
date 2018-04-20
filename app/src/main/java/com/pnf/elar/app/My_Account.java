package com.pnf.elar.app;
//package com.example.elar_app;
//
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import com.elar.attandance.list.AttandanceMainScreen;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//@SuppressLint("NewApi")
//public class My_Account extends Fragment {
//	
//	
//	Drawer dr ;
//	GridView gv;
//	Context context;
//	ArrayList prgmName;
//	UserSessionManager session;
//	LinearLayout actionbar;
//	ViewGroup actionBarLayout;
//	String srch;
//	String lang, username, pass, token, auth_token, auth_token_new = null,
//			user_name, password, new_Authntication, regId;
//	static String Base_url;
//	String language;
//	String compont, img;
//	String Security = "H67jdS7wwfh";
//	String lng;
//	View v;
//	static String[] component_id, component;
//	public static String[] prgmNameList = { "Retrieval List", "Edu Blog",
//			"Forum", "News", "Schedule", "Portfolio" };
//
//	public static String[] counter = { "2", "", "2", "", "", "" };
//
//	@SuppressLint("NewApi")
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//
//		v = inflater.inflate(R.layout.my__account, container, false);
//
//		session = new UserSessionManager(getActivity());
//
//		HashMap<String, String> user = session.getUserDetails();
//		lang = user.get(UserSessionManager.TAG_language);
//		auth_token = user.get(UserSessionManager.TAG_Authntication_token);
//		auth_token_new = user
//				.get(UserSessionManager.TAG_Authntication_new_tokn);
//		Base_url = user.get(UserSessionManager.TAG_Base_url);
//		user_name = user.get(UserSessionManager.TAG_username);
//		password = user.get(UserSessionManager.TAG_password);
//		regId = user.get(UserSessionManager.TAG_regId);
//		new_Authntication = user
//				.get(UserSessionManager.TAG_Authntication_new_tokn);
//
//		gv = (GridView) v.findViewById(R.id.gridForCategory);
//
//		Bundle bundle = this.getArguments();
//
//		String[] cmpnt_name = bundle.getStringArray("compnt_name");
//		String[] cmpnt_name_sw = bundle.getStringArray("compnt_name_sw");
//		String[] cmpnt_image = bundle.getStringArray("compnt_image");
//		component_id = bundle.getStringArray("compnt_id");
//
//		gv.setAdapter(new CustomAdapter_drawer(getActivity(), cmpnt_name,
//				cmpnt_image));
//
//		
//
//		return v;
//	}
//
//	
//
//	// ////////////////////////////////////////////////////
//	public class CustomAdapter_drawer extends BaseAdapter {
//
//		String[] result;
//		String[] notification;
//		Activity context;
//		String[] imageId;
//		LayoutInflater inflater = null;
//
//		public CustomAdapter_drawer(Activity activity, String[] component,
//				String[] image) {
//			// TODO Auto-generated constructor stub
//			result = component;
//			context = activity;
//			imageId = image;
//			// notification = counter;
//
//			inflater = (LayoutInflater) (getActivity())
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//		}
//
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return result.length;
//		}
//
//		@Override
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//
//		@Override
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//
//		public class Holder {
//			TextView tv;
//			ImageView imagView;
//			TextView notf;
//		}
//
//		@Override
//		public View getView(final int position, View convertView,
//				ViewGroup parent) {
//			// TODO Auto-generated method stub
//			Holder holder = new Holder();
//			View rowView;
//
//			// Log.i("c===+++=...", Arrays.deepToString(imageId));
//
//			rowView = inflater.inflate(R.layout.category_grid_layout, null);
//			holder.tv = (TextView) rowView
//					.findViewById(R.id.txtForListCategory);
//			holder.notf = (TextView) rowView
//					.findViewById(R.id.txtForListCategory1);
//			holder.imagView = (ImageView) rowView
//					.findViewById(R.id.imgForListCategory);
//			new ImageLoadTaskcliptwo(imageId[position], holder.imagView)
//					.execute();
//
//			holder.tv.setText(result[position]);
//
//			if (position == 0) {
//
//				if (component_id[position].equalsIgnoreCase("67")) {
//					rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
//				}
//				if (component_id[position].equalsIgnoreCase("29")) {
//					rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
//				}
//				if (component_id[position].equalsIgnoreCase("28")) {
//					rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
//				}
//				if (component_id[position].equalsIgnoreCase("23")) {
//					rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
//				}
//
//				// holder.notf.setVisibility(View.VISIBLE);
//
//			}
//			if (position == 1) {
//				if (component_id[position].equalsIgnoreCase("67")) {
//					rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
//				}
//
//				if (component_id[position].equalsIgnoreCase("29")) {
//					rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
//				}
//				if (component_id[position].equalsIgnoreCase("28")) {
//					rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
//				}
//				if (component_id[position].equalsIgnoreCase("23")) {
//					rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
//				}
//			}
//
//			if (position == 2) {
//				if (component_id[position].equalsIgnoreCase("67")) {
//					rowView.setBackgroundColor(Color.parseColor("#B2B2B2"));
//				}
//
//				if (component_id[position].equalsIgnoreCase("29")) {
//					rowView.setBackgroundColor(Color.parseColor("#F68B1F"));
//				}
//				if (component_id[position].equalsIgnoreCase("28")) {
//					rowView.setBackgroundColor(Color.parseColor("#EC74A9"));
//				}
//				if (component_id[position].equalsIgnoreCase("23")) {
//					rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
//				}
//			}
//			if (position == 3) {
//				rowView.setBackgroundColor(Color.parseColor("#2FBCD0"));
//				// holder.notf.setVisibility(View.GONE);
//			}
//			if (position == 4) {
//				rowView.setBackgroundColor(Color.parseColor("#617DBE"));
//				// holder.notf.setVisibility(View.GONE);
//			} else if (position == 5) {
//				rowView.setBackgroundColor(Color.parseColor("#F15A6B"));
//				// holder.notf.setVisibility(View.GONE);
//			}
//
//			rowView.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					if (position == 0) {
//
//						if (component_id[position].equalsIgnoreCase("67")) {
//							FragmentManager fragmentManager = getFragmentManager();
//
//							/* Creating fragment instance */
//							Child_Info rFragment = new Child_Info();
//
//							/* Passing selected item information to fragment */
//							/* Replace fragment */
//							FragmentTransaction ft = fragmentManager
//									.beginTransaction();
//							ft.replace(R.id.content_frame, rFragment);
//							ft.commit();
//						}
//						if (component_id[position].equalsIgnoreCase("29")) {
//							FragmentManager fragmentManager = getFragmentManager();
//
//							/* Creating fragment instance */
//							AttandanceMainScreen rFragment = new AttandanceMainScreen();
//
//							/* Passing selected item information to fragment */
//							/* Replace fragment */
//							FragmentTransaction ft = fragmentManager
//									.beginTransaction();
//							ft.replace(R.id.content_frame, rFragment);
//							ft.commit();
//						}
//						if (component_id[position].equalsIgnoreCase("28")) {
//							
//							FragmentManager fragmentManager = getFragmentManager();
//
//							/* Creating fragment instance */
//							MainActivity rFragment = new MainActivity();
//
//							/* Passing selected item information to fragment */
//							/* Replace fragment */
//							FragmentTransaction ft = fragmentManager
//									.beginTransaction();
//							ft.replace(R.id.content_frame, rFragment);
//							ft.commit();
//						}
//						if (component_id[position].equalsIgnoreCase("23")) {
//							Intent in = new Intent(getActivity(),
//									News_Post.class);
//							startActivity(in);
//						}
//
//					}
//
//					if (position == 1) {
//						if (component_id[position].equalsIgnoreCase("67")) {
//							FragmentManager fragmentManager = getFragmentManager();
//
//							/* Creating fragment instance */
//							Child_Info rFragment = new Child_Info();
//
//							/* Passing selected item information to fragment */
//							/* Replace fragment */
//							FragmentTransaction ft = fragmentManager
//									.beginTransaction();
//							ft.replace(R.id.content_frame, rFragment);
//							ft.commit();
//						}
//						if (component_id[position].equalsIgnoreCase("29")) {
//							FragmentManager fragmentManager = getFragmentManager();
//
//							/* Creating fragment instance */
//							AttandanceMainScreen rFragment = new AttandanceMainScreen();
//
//							/* Passing selected item information to fragment */
//							/* Replace fragment */
//							FragmentTransaction ft = fragmentManager
//									.beginTransaction();
//							ft.replace(R.id.content_frame, rFragment);
//							ft.commit();
//						}
//						if (component_id[position].equalsIgnoreCase("28")) {
//							
//						//	int id = dr.actionBarLayout.getId();
//							
//							FragmentManager fragmentManager = getFragmentManager();
//
//							/* Creating fragment instance */
//							MainActivity rFragment = new MainActivity();
//
//							/* Passing selected item information to fragment */
//							/* Replace fragment */
//							FragmentTransaction ft = fragmentManager
//									.beginTransaction();
//							ft.replace(R.id.content_frame, rFragment);
//							ft.commit();
//						}
//						if (component_id[position].equalsIgnoreCase("23")) {
//							Intent in = new Intent(getActivity(),
//									News_Post.class);
//							startActivity(in);
//						}
//
//					}
//					if (position == 2) {
//						if (component_id[position].equalsIgnoreCase("67")) {
//							FragmentManager fragmentManager = getFragmentManager();
//
//							/* Creating fragment instance */
//							Child_Info rFragment = new Child_Info();
//
//							/* Passing selected item information to fragment */
//							/* Replace fragment */
//							FragmentTransaction ft = fragmentManager
//									.beginTransaction();
//							ft.replace(R.id.content_frame, rFragment);
//							ft.commit();
//						}
//						if (component_id[position].equalsIgnoreCase("29")) {
//							FragmentManager fragmentManager = getFragmentManager();
//
//							/* Creating fragment instance */
//							AttandanceMainScreen rFragment = new AttandanceMainScreen();
//
//							/* Passing selected item information to fragment */
//							/* Replace fragment */
//							FragmentTransaction ft = fragmentManager
//									.beginTransaction();
//							ft.replace(R.id.content_frame, rFragment);
//							ft.commit();
//						}
//						if (component_id[position].equalsIgnoreCase("28")) {
//							
//							FragmentManager fragmentManager = getFragmentManager();
//
//							/* Creating fragment instance */
//							MainActivity rFragment = new MainActivity();
//
//							/* Passing selected item information to fragment */
//							/* Replace fragment */
//							FragmentTransaction ft = fragmentManager
//									.beginTransaction();
//							ft.replace(R.id.content_frame, rFragment);
//							ft.commit();
//						}
//						if (component_id[position].equalsIgnoreCase("23")) {
//							Intent in = new Intent(getActivity(),
//									News_Post.class);
//							startActivity(in);
//						}
//
//					}
//
//				}
//			});
//
//			return rowView;
//		}
//
//	}
//
//	// ////////////////////////////////////////////////////
//	class ImageLoadTaskcliptwo extends AsyncTask<Void, Void, Bitmap> {
//		// ProgressDialog pDialog;
//		private String url;
//		private ImageView image;
//
//		public ImageLoadTaskcliptwo(String url, ImageView imageView) {
//			this.url = url;
//			this.image = imageView;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			
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
//			image.setScaleType(ImageView.ScaleType.FIT_XY);
//			image.setImageBitmap(result);
//		}
//
//	}
//
//	// ////////////////////////////////////////////////////
//}
