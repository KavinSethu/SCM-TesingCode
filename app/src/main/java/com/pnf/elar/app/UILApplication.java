package com.pnf.elar.app;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class UILApplication extends MultiDexApplication {
private static UserSessionManager session;
	@Override
	public void onCreate() {
		super.onCreate();

		// This configuration tuning is custom. You can tune every option, you may tune some of them, 
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		/*ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
			.threadPoolSize(3)
			.threadPriority(Thread.NORM_PRIORITY - 2)
			.memoryCacheSize(1500000) // 1.5 Mb
			.denyCacheImageMultipleSizesInMemory()
			.discCacheFileNameGenerator(new Md5FileNameGenerator())
			 // Not necessary in common
			.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);*/

		session = new UserSessionManager(getBaseContext());
	}


	public static  UserSessionManager getUserSessionManager(Context context)
	{

		if(session==null)
		{
			session=new UserSessionManager(context);
			return session;
		}
		else
			return session;


	}
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}
}