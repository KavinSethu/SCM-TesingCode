package com.pnf.elar.app;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author Paresh Mayani (@pareshmayani)
 */
public abstract class BaseActivity extends AppCompatActivity {

	protected ImageLoader imageLoader = ImageLoader.getInstance();
	
}