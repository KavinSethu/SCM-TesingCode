package com.pnf.elar.app;


import java.util.HashMap;
import java.util.Map;



import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetCheck {
	Context _context;
	
	public InternetCheck()
	{
		
	}
	public InternetCheck(Context c)
	{
		_context=c;
	}
	
	public final boolean isInternetOn() {
        
        // get Connectivity Manager object to check connection
		Map<String, String> networkDetails = getConnectionDetails();
		if (networkDetails.isEmpty()) {
			return false;
		} else {
			return true;
		}
		
        }
	private Map<String, String> getConnectionDetails() {
		Map<String, String> networkDetails = new HashMap<String, String>();
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager)_context. getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo wifiNetwork = connectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (wifiNetwork != null && wifiNetwork.isConnected()) {

				networkDetails.put("Type", wifiNetwork.getTypeName());
				networkDetails.put("Sub type", wifiNetwork.getSubtypeName());
				networkDetails.put("State", wifiNetwork.getState().name());
			}

			NetworkInfo mobileNetwork = connectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mobileNetwork != null && mobileNetwork.isConnected()) {
				networkDetails.put("Type", mobileNetwork.getTypeName());
				networkDetails.put("Sub type", mobileNetwork.getSubtypeName());
				networkDetails.put("State", mobileNetwork.getState().name());
				if (mobileNetwork.isRoaming()) {
					networkDetails.put("Roming", "YES");
				} else {
					networkDetails.put("Roming", "NO");
				}
			}
		} catch (Exception e) {
			networkDetails.put("Status", e.getMessage());
		}
		return networkDetails;
	}
}


