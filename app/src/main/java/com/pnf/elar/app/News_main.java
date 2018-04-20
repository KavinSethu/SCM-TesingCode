package com.pnf.elar.app;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/*import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONArray;
import org.json.JSONObject;

import com.elar.util.NetworkUtil;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.pnf.elar.app.Publish.BitmapHelper;
import com.pnf.elar.app.service.FormDataWebservice;
import com.pnf.elar.app.util.Const;
import com.pnf.elar.app.util.CustomWidgets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar.LayoutParams;
import android.util.Base64;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class News_main extends Fragment {
	ImageLoader imageLoader ;
	ArrayList<String> selectedItems;
	DisplayImageOptions options;
	ArrayList<String> imageUrls;
	Button groups, publish;
	ListView listview;
	String[] imgsss, videoss;
	Customlistadapter adapter1;
	EditText title, description;
	LinearLayout news;
	AlertDialog.Builder ad;
	String[] grp_name, grp_id, type;
	boolean[] _selections;
	String[] w, p, slcted, other_other, course_course;
	ImageView chck;
	int chk_status;
	LinearLayout upload;
	UserSessionManager session;
	String lang, auth_token, Base_url, language;
	int chooser_click;
	private static int LOAD_IMAGE_RESULTS = 1;
	private static int LOAD_Video_RESULTS = 1;
	private static int PICK_REQUEST_CODE = 1;
	String imagePath, VideoPath, i_path, V_path;
	ArrayList<String> encoded_code = new ArrayList<String>();
	ArrayList<String> encoded_code_video = new ArrayList<String>();
	ArrayList<String> al;
	ArrayAdaptert_news aa;
	ImageView img;
	TextView MYAccount, text3, txt4, Recipients, Notify_by_mail, spn4;
	ListView im_vdo;
	String chkd = "no", des, titl;
	private static final int PICKFILE_RESULT_CODE = 1;
	View v;

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		v = inflater.inflate(R.layout.activity_main_news, container, false);

		session = new UserSessionManager(getActivity());
		
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

		HashMap<String, String> user = session.getUserDetails();
		lang = user.get(UserSessionManager.TAG_language);
		auth_token = user.get(UserSessionManager.TAG_Authntication_token);
		Base_url = user.get(UserSessionManager.TAG_Base_url);

		String news_create = "news_create";
		session.create_main_Screen("");
		session.createnews(news_create);

		if (lang.equalsIgnoreCase("sv")) {
			language = "sw";
		} else {
			language = "en";
		}

		new getgroups().execute();

		groups = (Button) v.findViewById(R.id.grpspin);
		chck = (ImageView) v.findViewById(R.id.chck);
		im_vdo = (ListView) v.findViewById(R.id.im_vdo);
		upload = (LinearLayout) v.findViewById(R.id.upload);
		publish = (Button) v.findViewById(R.id.publish);
		title = (EditText) v.findViewById(R.id.title);
		description = (EditText) v.findViewById(R.id.description);
		text3 = (TextView) v.findViewById(R.id.text3);
		txt4 = (TextView) v.findViewById(R.id.txt4);
		Recipients = (TextView) v.findViewById(R.id.Recipients);
		Notify_by_mail = (TextView) v.findViewById(R.id.Notify_by_mail);
		spn4 = (TextView) v.findViewById(R.id.spn4);

		if (lang.equalsIgnoreCase("sw")) {
			text3.setText("Publicera");
			txt4.setText("Nyheter");
			Recipients.setText("Mottagare");
			groups.setText("Valda grupper");
			Notify_by_mail.setText("Notifiera med epost");
			spn4.setText("Bifoga filer");
			publish.setText("Publicera");
			title.setHint("Nyhetstitel");
			description.setHint("Nyhetsbeskrivning");
		} else {

		}

		al = new ArrayList<String>();

		// //////// back to Main Activity ////Set title/////
		((Drawer) getActivity()).setBackFrompublishtomainnews();
		if (lang.equalsIgnoreCase("sw")) {
			((Drawer) getActivity()).setActionBarTitle("Nyheter");
		} else {
			((Drawer) getActivity()).setActionBarTitle("News");
		}
		// ///////////

		im_vdo.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					final int arg2, long arg3) {

				String title, yes, no;

				if (lang.equalsIgnoreCase("sw")) {
					title = "är du Säker,du brist till radera punkt ?";
					yes = "ja";
					no = "Nej";
				} else {
					title = "Are you sure,You want to delete item ?";
					yes = "yes";
					no = "no";
				}

//				String item = al.get(arg2);
				// al.remove(arg2);
				// Toast.makeText(getApplicationContext(), item, 0).show();
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());
				alertDialogBuilder.setMessage(title); // ///////
														// new

				alertDialogBuilder.setPositiveButton(yes,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {

								al.remove(arg2);
								aa.notifyDataSetChanged();
								setListViewHeightBasedOnChildrener(im_vdo);

							}
						});

				alertDialogBuilder.setNegativeButton(no,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								// Intent i = new
								// Intent(SettingPage.this,SettingPage.class);
								// startActivity(i);
								// finish();
								// closeContextMenu();

							}
						});

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();

			}
		});

		publish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                        // TODO Auto-generated method stub
                        titl = title.getText().toString();
    //				des = description.getText().toString();
                        des = description.getText().toString().replaceAll("\\n", "<br />");


                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(description.getWindowToken(), 0);
                        // Log.i("list Size...", Integer.toString(encoded_code.size()));

                        imgsss = new String[encoded_code.size()];

                        for (int i = 0; i < encoded_code.size(); i++) {
                            imgsss[i] = encoded_code.get(i);
                        }

                        // Log.i("Array Size...", Integer.toString(imgsss.length));

                        videoss = new String[encoded_code_video.size()];

                        for (int i = 0; i < encoded_code_video.size(); i++) {
                            videoss[i] = encoded_code_video.get(i);
                        }

                        Log.i("mail", chkd);
                        // int j = 0;
                        // for(int i= 0 ;i<type.length ;i++ )
                        // {
                        // if(p[j]==Integer.toString(i))
                        // {
                        // j=j+1;
                        // Log.i("match", "gr888888");
                        // }
                        //
                        // }
                        ArrayList<String> code = new ArrayList<String>();
                        if (w == null) {
                            w = new String[0];
                        }

                        String[] ll = new String[w.length];
                        for (int i = 0; i < w.length; i++) {
                            // type[p[i]]
                            code.add(type[Integer.parseInt(p[i])]);

                        }

                        slcted = new String[code.size()];

                        for (int i = 0; i < code.size(); i++) {
                            slcted[i] = code.get(i);
                        }

                        // Log.i("slcted", Arrays.deepToString(slcted));
                        ArrayList<String> course = new ArrayList<String>();
                        ArrayList<String> other = new ArrayList<String>();
                        for (int i = 0; i < slcted.length; i++) {
                            if (slcted[i].equalsIgnoreCase("course")) {
                                course.add(w[i]);
                            } else {
                                other.add(w[i]);
                            }
                        }


                        // Log.i("course", Integer.toString(course.size()));
                        // Log.i("other", Integer.toString(other.size()));

                        course_course = new String[course.size()];

                        for (int i = 0; i < course.size(); i++) {
                            course_course[i] = course.get(i);
                        }

                        other_other = new String[other.size()];

                        for (int i = 0; i < other.size(); i++) {
                            other_other[i] = other.get(i);
                        }

                        // Log.i("course_course", Arrays.deepToString(course_course));
                        // Log.i("other_other", Arrays.deepToString(other_other));
                        // Log.i("code", Integer.toString(code.size()));
                        // Log.i("code", Integer.toString(w.length));

                        new publish().execute();
                    }
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		});

		groups.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Log.i("grp_namegrp_name", Arrays.deepToString(grp_name));
				String title;

				if (lang.equalsIgnoreCase("sw")) {
					title = "grupper..";
				} else {
					title = "Groups..";
				}

				ad = new AlertDialog.Builder(getActivity());
				ad.setTitle(title);

				ad.setMultiChoiceItems(grp_name, _selections,
						new OnMultiChoiceClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1,
									boolean arg2) {
								// TODO Auto-generated method stub
								// Toast.makeText(getApplicationContext(),grp_name[0],
								// Toast.LENGTH_SHORT).show();

							}
						});
				ad.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								printSelectedPlanets();

								if (w != null && w.length > 0) {
									// new getstudent().execute();
								} else {
									Toast.makeText(getActivity(),
											"Group cannot be empty.....",
											Toast.LENGTH_SHORT).show();
								}

								// Studnts.setText("Student Selected :");

							}

							// //////////////////////////////////////////////////////////////////////////////////////
							private void printSelectedPlanets() {
								// TODO Auto-generated method stub
								String[] d = new String[_selections.length];
								int count = 0;
								for (int i = 0; i < grp_name.length; i++) {
									Log.i("ME", grp_name[i] + " selected: "
											+ _selections[i] + i);

									if (_selections[i] == true) {
										Log.i("true", Integer.toString(i));
										d[i] = Integer.toString(i);
										count = count + 1;
										// hm.add( Integer.toString(i));
									}
								}
								// Log.i("tttttt", Arrays.deepToString(d));
								//
								// Log.i("hhhhhhhhhhh",
								// Integer.toString(count));
								groups.setText(count + " " + "Groups Selected");
								p = new String[count];
								int j = 0;
								for (int i = 0; i < d.length; i++) {

									if (!(d[i] == null)) {
										p[j] = d[i];
										j++;

									}

								}
								// Log.i("@@@@@@@@@@@@@@",
								// Arrays.deepToString(p));//
								// /////////////////indexxxxx///////
								// Log.i("ppppppppppppp", p[0]);
								w = new String[count];
								String n;
								int g;
								for (int i = 0; i < count; i++) {
									n = p[i];
									g = Integer.parseInt(n);

									w[i] = grp_id[g];

								}
								// Log.i("000000000000",
								// Arrays.deepToString(w));//
								// ///////////////id's/////////////////
								// grps.setText(count + "  Groups Selected:");

							}
							// //////////////////////////////////////////////////////////////////////////////////////

						});
				ad.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});
				ad.show();

			}
		});

		upload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String title;

				if (lang.equalsIgnoreCase("sw")) {
					title = "Bifoga   filer";
				} else {
					title = "ATTACH   MEDIA";
				}

				final Dialog dialog = new Dialog(getActivity());
				// Include dialog.xml file
				dialog.setContentView(R.layout.custom_dialog_news);
				// Set dialog title
				dialog.setTitle(title);
				dialog.setCancelable(false);

				Button cancel = (Button) dialog.findViewById(R.id.cancel);

				TextView imageuploadstext=(TextView)dialog.findViewById(R.id.imageuploadstext);
	            TextView videouploadtext=(TextView)dialog.findViewById(R.id.videouploadtext);
			    LinearLayout uploadImage = (LinearLayout) dialog.findViewById(R.id.uploadImage);
				LinearLayout uploadVedio = (LinearLayout) dialog.findViewById(R.id.uploadVedio);
				
				if(lang.equalsIgnoreCase("sw"))
				{
					imageuploadstext.setText("Bildgalleri");
					videouploadtext.setText("Videogalleri");
					cancel.setText("Avbryt");
				}

				dialog.show();

				cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});

				// //////////////////////////
				uploadImage.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();

						final Dialog Forimages = new Dialog(getActivity());
						// Include dialog.xml file
						Forimages.setContentView(R.layout.ac_image_grid);
						
						RelativeLayout regrid = (RelativeLayout)Forimages.findViewById(R.id.regrid);
						GridView gridView = (GridView) Forimages.findViewById(R.id.gridview);
						regrid.setBackgroundColor(Color.parseColor("#2BBDD1"));
						gridView.setBackgroundColor(Color.parseColor("#2BBDD1"));
						// Set dialog title
						if(lang.equalsIgnoreCase("sw"))
						{
							Forimages.setTitle("Bildgalleri..");
						}else {
							Forimages.setTitle("Images..");
						}
						Forimages.setCancelable(false);
						
						 
						 final ImageAdapter imageAdapter;
						 
						 final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
							final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
							Cursor imagecursor = getActivity().managedQuery(
									MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
									null, orderBy + " DESC");
							
							imageUrls = new ArrayList<String>();
							
							for (int i = 0; i < imagecursor.getCount(); i++) {
								imagecursor.moveToPosition(i);
								int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
								imageUrls.add(imagecursor.getString(dataColumnIndex));
								
								System.out.println("=====> Array path => "+imageUrls.get(i));
							}
							
							
							options = new DisplayImageOptions.Builder()
								.showStubImage(R.drawable.stub_image)
								.showImageForEmptyUri(R.drawable.image_for_empty_url)
								.cacheInMemory()
								.cacheOnDisc()
								.build();

							imageAdapter = new ImageAdapter(getActivity(), imageUrls);
							
							
							gridView.setAdapter(imageAdapter);
							
							
							
							Button b1 = (Button)Forimages.findViewById(R.id.button1);
							if(lang.equalsIgnoreCase("sw"))
							{
								b1.setText("Välj");
							}
							
							 b1.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									selectedItems = imageAdapter.getCheckedItems();
								//	Toast.makeText(getActivity(), "Total photos selected: "+selectedItems.size(), Toast.LENGTH_SHORT).show();
								//	Log.d(MultiPhotoSelectActivity.class.getSimpleName(), "Selected Items: " + selectedItems.toString());
									/////////
									int siz = selectedItems.size();
									for(int i = 0;i<siz;i++)
									{
										InputStream inputStream = null;
										try {
											inputStream = new FileInputStream(selectedItems.get(i));
										} catch (FileNotFoundException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}// You can get an inputStream using any IO API
									//	Bitmap tt = BitmapFactory.decodeFile(imagePath);   ////// new 
										Bitmap tt = BitmapHelper.decodeFile(selectedItems.get(i), 50, 50, true);
										byte[] bytes;
										byte[] buffer = new byte[8192];
										int bytesRead;
										ByteArrayOutputStream output = new ByteArrayOutputStream();
										try {
											while ((bytesRead = inputStream.read(buffer)) != -1) {
												output.write(buffer, 0, bytesRead);
											}
										} catch (IOException e) {
											e.printStackTrace();
										}
//										bytes = output.toByteArray();
										tt.compress(CompressFormat.JPEG, 20,output);    ///// new
										bytes = output.toByteArray();
										String encodedString=null;
										
										try {
											 encodedString = Base64.encodeToString(bytes,
													Base64.DEFAULT);
										} catch (Exception e) {
											// TODO: handle exception
											e.printStackTrace();
										}
										
										encoded_code.add(encodedString);
									}
									Log.i("encoded_code", Integer.toString(encoded_code.size()));
									aa = new ArrayAdaptert_news(getActivity(), selectedItems);
									im_vdo.setAdapter(aa);
									aa.notifyDataSetChanged();
									setListViewHeightBasedOnChildrener(im_vdo);
									Forimages.dismiss();
								}
							});

							Forimages.show();

					}
				});

				// /////////////////////// ///////////////////////////////////

				// ///////////////////////////////////////////////////
				uploadVedio.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						chooser_click = 2;

						Intent i = new Intent(
								Intent.ACTION_PICK,
								android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
						startActivityForResult(i, LOAD_Video_RESULTS);
						dialog.dismiss();

					}
				});
				// //////////////////////////

				// UploadFiles.setOnClickListener(new OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// // TODO Auto-generated method stub
				// chooser_click=3;
				// Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				// intent.setType("file/*");
				// startActivityForResult(intent,PICKFILE_RESULT_CODE);
				//
				// }
				// });

			}
		});

		chck.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				if (chk_status == 1) {
					chck.setBackgroundResource(R.drawable.uncheck);
					chkd = "no";
					chk_status = 0;
				} else if (chk_status == 0) {
					chck.setBackgroundResource(R.drawable.check);
					chkd = "yes";
					chk_status = 1;

				}

			}
		});

		news = (LinearLayout) v.findViewById(R.id.linr6);
		news.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {
					if (NetworkUtil.getInstance(getActivity()).isInternet()) {
                        FragmentManager frfmnr_mngr = getFragmentManager();

                        News_Post post_filtter_fr = new News_Post();

                        FragmentTransaction ft = frfmnr_mngr.beginTransaction();

                        ft.replace(R.id.content_frame, post_filtter_fr);

                        ft.commit();
                    }
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}

			}
		});

		return v;
	}

	public class Customlistadapter extends ArrayAdapter implements ListAdapter {
		private ArrayList<String> list = new ArrayList<String>();
		Context context;
		Uri[] image;

		public Customlistadapter(Activity context, ArrayList<String> list1) {
			super(context, R.layout.list_item_news, list1);
			// TODO Auto-generated constructor stub
			this.list = list1;

			this.context = context;
		}

		@Override
		public int getCount() {
			return list.size();

		}

		@Override
		public Object getItem(int pos) {
			return list.get(pos);
		}

		@Override
		public long getItemId(int pos) {

			return pos;

		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View single_row = inflater.inflate(R.layout.list_item_news, null,
					true);
			TextView textView1 = (TextView) single_row
					.findViewById(R.id.tvDescr);

			textView1.setText(list.get(position));
			Button deleteBtn = (Button) single_row.findViewById(R.id.cbBox);
			//

			deleteBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// do something
					list.remove(position);
					Helper.getListViewSize(listview);

					adapter1.notifyDataSetChanged();
				}
			});

			if (position % 2 == 0) {
				single_row.setBackgroundColor(Color.parseColor("#29B6C9"));
			} else {
				single_row.setBackgroundColor(Color.parseColor("#42C4D6"));
			}

			return single_row;

		}

	}

	// ////////////////////////////////////////////////

	class getgroups extends AsyncTask<String, String, String> {

		/*JSONParser jsonParser = new JSONParser();*/

		String status="";
		private MyCustomProgressDialog dialog;
		private String url_create_product = Base_url
				+ "lms_api/news/get_classes";

		private static final String TAG_staus = "status";
		private static final String TAG_response = "response";
		private static final String TAG_groups_id = "id";
		private static final String TAG_groups_name = "name";
		private static final String TAG_type = "type";
		private static final String TAG_curriculum_tags = "curriculum_tags";
		private static final String TAG_curriculum_id = "id";
		private static final String TAG_curriculum_title = "title";

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new MyCustomProgressDialog(getActivity());
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			dialog.show();

		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			String classResponse = "";
			try {
				String urlParams = "&" + Const.Params.SecurityKey + "=" + URLEncoder.encode("H67jdS7wwfh", "UTF-8") +
						"&" + Const.Params.AuthToken + "=" + URLEncoder.encode(auth_token, "UTF-8") +
						"&" + Const.Params.Language + "=" + URLEncoder.encode(lang, "UTF-8");


				classResponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
			} catch (Exception e) {
				e.printStackTrace();
			}


			/*// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("securityKey", "H67jdS7wwfh"));
			params.add(new BasicNameValuePair("authentication_token",
					auth_token));
			params.add(new BasicNameValuePair("language", language));

			JSONObject json = jsonParser.makeHttpRequest(url_create_product,
					"POST", params);

			// check for success tag
			Log.i("json data......", json.toString());
			try {
				status = json.getString(TAG_staus);

				Log.e("=-=-=-=-=-=-", status);

				JSONArray grps_name = json.optJSONArray(TAG_response);

				grp_id = new String[grps_name.length()];
				grp_name = new String[grps_name.length()];
				type = new String[grps_name.length()];
				_selections = new boolean[grps_name.length()];
				for (int j = 0; j < grps_name.length(); j++) {
					JSONObject c = grps_name.getJSONObject(j);

					grp_id[j] = c.getString(TAG_groups_id);
					grp_name[j] = c.getString(TAG_groups_name);
					type[j] = c.getString(TAG_type);

					// Log.i("categoryyyyy", grp_id[j]);
					// Log.i("descr...........", grp_name[j]);

				}

				if (status.equalsIgnoreCase("true")) {

				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
*/
			return classResponse;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String results) {
			// dismiss the dialog once done
			dialog.dismiss();


			try
			{

				JSONObject jsonObject=new JSONObject();

				if(results!=null&&!results.isEmpty())
				{
					jsonObject=new JSONObject(results);

					if(jsonObject.has(Const.Params.Status))
					{
						status = jsonObject.getString(TAG_staus);

					}



				}

				Log.e("=-=-=-=-=-=-", status);

				if(status.equalsIgnoreCase("true")) {
					JSONArray grps_name = jsonObject.optJSONArray(TAG_response);

					grp_id = new String[grps_name.length()];
					grp_name = new String[grps_name.length()];
					type = new String[grps_name.length()];
					_selections = new boolean[grps_name.length()];
					for (int j = 0; j < grps_name.length(); j++) {
						JSONObject c = grps_name.getJSONObject(j);

						grp_id[j] = c.getString(TAG_groups_id);
						grp_name[j] = c.getString(TAG_groups_name);
						type[j] = c.getString(TAG_type);

						// Log.i("categoryyyyy", grp_id[j]);
						// Log.i("descr...........", grp_name[j]);

					}
				}
				else {
					try {


						String msg = jsonObject.getString("message");
						System.out.print(msg);

						if (lang.equalsIgnoreCase("sw")) {
							System.out.print("Sw_l");
							if (msg.equalsIgnoreCase("Autentisering misslyckades")) {
								Button btnLogout;
								TextView tvMessage;
								final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", getActivity());
								btnLogout = (Button) dialogs.findViewById(R.id.alert_logout_bun);
								tvMessage = (TextView) dialogs.findViewById(R.id.alert_msg);
								tvMessage.setText(msg);

								btnLogout.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										dialogs.dismiss();

										session.logoutUser();

									}
								});

							}
						} else {
							System.out.print("Eng_l");
							if (msg.equalsIgnoreCase("Authentication Failed")) {
								Button btnLogout;
								TextView tvMessage;
								final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", getActivity());
								btnLogout = (Button) dialogs.findViewById(R.id.alert_logout_bun);
								tvMessage = (TextView) dialogs.findViewById(R.id.alert_msg);
								tvMessage.setText(msg);

								btnLogout.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										dialogs.dismiss();

										session.logoutUser();

									}
								});
							}
						}
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
				}


			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}
	}

	// ///////////////////////////////////////////////

	class publish extends AsyncTask<String, String, String> {
		JSONArray grpid;
		JSONArray oimg, cou, g_id;
		JSONArray oimg_video;
		/*JSONParser jsonParser = new JSONParser();*/

		String status="";
		private MyCustomProgressDialog dialog;
		private String url_create_product = Base_url + "lms_api/news/add_news";

		private static final String TAG_staus = "status";

		private static final String TAG_response = "response";
		private static final String TAG_groups_id = "id";
		private static final String TAG_groups_name = "name";
		private static final String TAG_type = "type";
		private static final String TAG_curriculum_tags = "curriculum_tags";
		private static final String TAG_curriculum_id = "id";
		private static final String TAG_curriculum_title = "title";

		JSONObject addJson=new JSONObject();

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new MyCustomProgressDialog(getActivity());
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			dialog.show();

		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			String addNewsResponse = "";

			try {

				cou = new JSONArray(Arrays.asList(course_course));
				g_id = new JSONArray(Arrays.asList(other_other));
				// JSONArray ss_id = new JSONArray(Arrays.asList(STDcount));
				oimg = new JSONArray(Arrays.asList(imgsss));
				oimg_video = new JSONArray(Arrays.asList(videoss));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			// String
			// fg="{"+"\"authentication_token\""+": "+"\f009415940c48425d5502d128fcee2e36235b443"+","+"\"securityKey\""
			// + ":" + "\"H67jdS7wwfh\"" + ",";
			String fg = "{" + "\"authentication_token\"" + ":"
					+ JSONObject.quote(auth_token) + "," + "\"description\""
					+ ":" + JSONObject.quote(des) + "," + "\"random_files\""
					+ ":" + "[" + "]" + "," + "\"class_id\"" + ":" + g_id + ","
					+ "\"course_id\"" + ":" + cou + "," + "\"mail\"" + ":"
					+ JSONObject.quote(chkd) + "," + "\"images\"" + ":" + oimg
					+ "," + "\"securityKey\"" + ":" + "\"H67jdS7wwfh\"" + ","
					+ "\"videos\"" + ":" + oimg_video + "," + "\"language\""
					+ ":" + JSONObject.quote(language) + "," + "\"title\""
					+ ":" + JSONObject.quote(titl) + "}";




			try {
				addJson.put("authentication_token",auth_token);
				addJson.put("description",des);
				addJson.put("random_files",new JSONArray());
				addJson.put("class_id",g_id);
				addJson.put("course_id",cou);
				addJson.put("mail",chkd);
				addJson.put("images",oimg);
				addJson.put("securityKey","H67jdS7wwfh");
				addJson.put("videos",oimg_video);
				addJson.put("language",language);
				addJson.put("title",titl);
				addJson.put("user_type_app","android");


				Log.i("addJson data......", addJson.toString());


				String urlParams = "&" + Const.Params.JsonData + "=" + URLEncoder.encode(addJson.toString(), "UTF-8");


				addNewsResponse = FormDataWebservice.excutePost(url_create_product, urlParams, Const.MethodType.POST);
			} catch (Exception e) {
				e.printStackTrace();
			}



			/*List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("jsonData", fg));

			JSONObject json = jsonParser.makeHttpRequest(url_create_product,
					"POST", params);

			// check for success tag
			Log.i("json data......", json.toString());
			try {
				status = json.getString(TAG_staus);

				Log.e("=-=-=-=-=-=-", status);

				if (status.equalsIgnoreCase("true")) {

				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}*/

			return addNewsResponse;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String results) {
			// dismiss the dialog once done
			dialog.dismiss();
			try {
				JSONObject jsonObject=new JSONObject();

				if (results != null && !results.isEmpty()) {

					jsonObject=new JSONObject(results);
					if(jsonObject.has(Const.Params.Status))
					{
						status=jsonObject.getString(Const.Params.Status);
					}

				}

				// Log.i("oooooo", imm[0]);
				if (status.equalsIgnoreCase("true")) {

					FragmentManager frfmnr_mngr = getFragmentManager();

					News_Post post_filtter_fr = new News_Post();

					FragmentTransaction ft = frfmnr_mngr.beginTransaction();

					ft.replace(R.id.content_frame, post_filtter_fr);

					ft.commit();

				}else{

					try {


						String msg = jsonObject.getString("message");
						System.out.print(msg);

						if (lang.equalsIgnoreCase("sw")) {
							System.out.print("Sw_l");
							if (msg.equalsIgnoreCase("Autentisering misslyckades")) {
								Button btnLogout;
								TextView tvMessage;
								final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", getActivity());
								btnLogout = (Button) dialogs.findViewById(R.id.alert_logout_bun);
								tvMessage = (TextView) dialogs.findViewById(R.id.alert_msg);
								tvMessage.setText(msg);

								btnLogout.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										dialogs.dismiss();

										session.logoutUser();

									}
								});

							}
						} else {
							System.out.print("Eng_l");
							if (msg.equalsIgnoreCase("Authentication Failed")) {
								Button btnLogout;
								TextView tvMessage;
								final Dialog dialogs = CustomWidgets.getInstance().getDialogBox(R.layout.logout_alert_dialog, "Alert", getActivity());
								btnLogout = (Button) dialogs.findViewById(R.id.alert_logout_bun);
								tvMessage = (TextView) dialogs.findViewById(R.id.alert_msg);
								tvMessage.setText(msg);

								btnLogout.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										dialogs.dismiss();

										session.logoutUser();

									}
								});
							}
						}
					} catch (NullPointerException e) {
						e.printStackTrace();
					}

					String msg = jsonObject.getString("message");
					Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
				}
			}catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// ///////////////////////////////////////////////////////////////

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (chooser_click == 1) {
			if (requestCode == LOAD_IMAGE_RESULTS
					&& resultCode == Activity.RESULT_OK && data != null) {
				// Let's read picked image data - its URI
				Uri pickedImage = data.getData();
				// Let's read picked image path using content resolver
				String[] filePath = { MediaStore.Images.Media.DATA };
				Cursor cursor = getActivity().getContentResolver().query(
						pickedImage, filePath, null, null, null);
				cursor.moveToFirst();
				imagePath = cursor
						.getString(cursor.getColumnIndex(filePath[0]));
				i_path = imagePath;

				// Log.i("image_working", "yes..");
				//
				// Log.i("imagePathimagePath", imagePath);

				cursor.close();

				// //////////////////////////////////////////

				InputStream inputStream = null;
				try {
					inputStream = new FileInputStream(imagePath);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}// You can get an inputStream using any IO API
				byte[] bytes;
				byte[] buffer = new byte[8192];
				int bytesRead;
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				try {
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						output.write(buffer, 0, bytesRead);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				bytes = output.toByteArray();
				String encodedString = Base64.encodeToString(bytes,
						Base64.DEFAULT);
				output.reset();

				// Log.i("base6444444", encodedString);

				encoded_code.add(encodedString);

				// /////////////////////////////////////////

			}
		}

		// /////////////////////////////////////////////////////////////
		if (chooser_click == 2) {
			if (requestCode == LOAD_Video_RESULTS && resultCode == Activity.RESULT_OK && data != null) {
				// Let's read picked image data - its URI
				try {
					Uri pickedImage = data.getData();
					// Let's read picked image path using content resolver
					String[] filePath = { MediaStore.Video.Media.DATA };
					Cursor cursor = getActivity().getContentResolver().query(
							pickedImage, filePath, null, null, null);
					cursor.moveToFirst();
					imagePath = cursor
							.getString(cursor.getColumnIndex(filePath[0]));
					cursor.close();
					 InputStream inputStream = new FileInputStream(imagePath);
					
					byte[] bytes;
					byte[] buffer = new byte[8192];
					int bytesRead;
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					
						while ((bytesRead = inputStream.read(buffer)) != -1) {
							output.write(buffer, 0, bytesRead);
						}
					
					bytes = output.toByteArray();
					String encodedString_video = Base64.encodeToString(bytes,
							Base64.DEFAULT);
					output.reset();
					Log.i("base64...", encodedString_video);
					encoded_code_video.add(encodedString_video);
				} catch (Error e) {
					// TODO: handle exception
					e.printStackTrace();
					Toast.makeText(getActivity(), "errror",Toast.LENGTH_SHORT).show();
					
				}
				catch (IOException e ) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}
		}
		// ////////////////////////////////////////////////////

		if (chooser_click == 3) {
			switch (requestCode) {
			case PICKFILE_RESULT_CODE:
				if (resultCode == Activity.RESULT_OK) {
					imagePath = data.getData().getPath();
					// textFile.setText(FilePath);
					// ///////////////////////////////////////////////////

					InputStream inputStream = null;
					try {
						inputStream = new FileInputStream(imagePath);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}// You can get an inputStream using any IO API
					byte[] bytes;
					byte[] buffer = new byte[8192];
					int bytesRead;
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					try {
						while ((bytesRead = inputStream.read(buffer)) != -1) {
							output.write(buffer, 0, bytesRead);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					bytes = output.toByteArray();
					String encodedString_video = Base64.encodeToString(bytes,
							Base64.DEFAULT);
					output.reset();

					// Log.i("base64...", encodedString_video);

					encoded_code_video.add(encodedString_video);

				}
				break;

			}
		}

		// ///////////////////////////////////////////////////
		// try {
		//
		//
		// if (requestCode == PICK_REQUEST_CODE)
		// {
		// if (resultCode == RESULT_OK)
		// {
		// Uri uri = data.getData();
		//
		// if (uri.getScheme().toString().compareTo("content")==0)
		// {
		// Cursor cursor =getContentResolver().query(uri, null, null, null,
		// null);
		// if (cursor.moveToFirst())
		// {
		// int column_index =
		// cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//Instead
		// of "MediaStore.Images.Media.DATA" can be used "_data"
		// Uri filePathUri = Uri.parse(cursor.getString(column_index));
		// String file_name = filePathUri.getLastPathSegment().toString();
		// String file_path=filePathUri.getPath();
		//
		// Log.i("File url..... ", file_path);
		// //
		// Toast.makeText(this,"File Name & PATH are:"+file_name+"\n"+file_path,
		// Toast.LENGTH_LONG).show();
		// }
		// }
		// }
		// }
		//
		//
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		//
		//
		//

		// //////////////////////////////////////////////////
		al.add(0, imagePath);
		// step ii: notify to adapter
		// tvDescr.setText(imagePath);
		aa = new ArrayAdaptert_news(getActivity(), al); // /////////// new
		im_vdo.setAdapter(aa);
		aa.notifyDataSetChanged();
		setListViewHeightBasedOnChildrener(im_vdo);

	}

	// ///////////////////////////////////////////////////////////////////////////
	protected void setListViewHeightBasedOnChildrener(ListView im_vdo2) {
		// TODO Auto-generated method stub

		ListAdapter listAdapter = im_vdo2.getAdapter();
		if (listAdapter == null)
			return;

		int desiredWidth = MeasureSpec.makeMeasureSpec(im_vdo2.getWidth(),
				MeasureSpec.UNSPECIFIED);
		int totalHeight = 0;
		View view = null;

		for (int i = 0; i < listAdapter.getCount(); i++) {
			view = listAdapter.getView(i, view, im_vdo2);

			if (i == 0)
				view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
						LayoutParams.MATCH_PARENT));

			view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
			totalHeight += view.getMeasuredHeight();

		}

		ViewGroup.LayoutParams params = im_vdo2.getLayoutParams();
		params.height = totalHeight
				+ ((im_vdo2.getDividerHeight()) * (listAdapter.getCount()));

		im_vdo2.setLayoutParams(params);
		im_vdo2.requestLayout();

	}
///////////////
public class ImageAdapter extends BaseAdapter {
		
		ArrayList<String> mList;
		LayoutInflater mInflater;
		Context mContext;
		SparseBooleanArray mSparseBooleanArray;
		
		public ImageAdapter(Context context, ArrayList<String> imageList) {
			// TODO Auto-generated constructor stub
			mContext = context;
			mInflater = LayoutInflater.from(mContext);
			mSparseBooleanArray = new SparseBooleanArray();
			mList = new ArrayList<String>();
			this.mList = imageList;

		}
		
		public ArrayList<String> getCheckedItems() {
			ArrayList<String> mTempArry = new ArrayList<String>();

			for(int i=0;i<mList.size();i++) {
				if(mSparseBooleanArray.get(i)) {
					mTempArry.add(mList.get(i));
				}
			}

			return mTempArry;
		}
		
		@Override
		public int getCount() {
			return imageUrls.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView == null) {
				convertView = mInflater.inflate(R.layout.row_multiphoto_item, null);
			}

			CheckBox mCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
			final ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);
			
			imageLoader.displayImage("file://"+imageUrls.get(position), imageView, options, new SimpleImageLoadingListener()
			{
				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					Animation anim = AnimationUtils.loadAnimation(
							getActivity(), R.anim.fade_in);
					imageView.setAnimation(anim);
					anim.start();
				}

				/*@Override
				public void onLoadingComplete(Bitmap loadedImage) {
					Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
					imageView.setAnimation(anim);
					anim.start();
				}*/
			});

			/*new SimpleImageLoadingListener(){
				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					Animation anim = AnimationUtils.loadAnimation(
							getActivity(), R.anim.fade_in);
					imageView.setAnimation(anim);
					anim.start();
				}
			});
			*/
			mCheckBox.setTag(position);
			mCheckBox.setChecked(mSparseBooleanArray.get(position));
			mCheckBox.setOnCheckedChangeListener(mCheckedChangeListener);
			
			return convertView;
		}
		
		OnCheckedChangeListener mCheckedChangeListener = new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
			}
		};
	}
	// //////////////////////////////////////////////////////////////

}
