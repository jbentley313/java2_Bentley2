/*
 * project sportsHeadlines
 * 
 * package com.jbentley.sportsheadlines
 * 
 * @author Jason Bentley
 * 
 * date Jan 8, 2014
 */
package com.jbentley.sportsheadlines;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import com.jbentley.connectivityPackage.*;

public class HeadlineDownloadService extends IntentService{
	static String Tag = "HeadlineDownloadService";
	public static final String MESSENGER_KEY = "messenger";
	public static String responseString = "";
	final connectivityClass connectionCheck = new connectivityClass();
	Context mContext;
	
	public HeadlineDownloadService() {

		super("headlineDownloadService");
		// TODO Auto-generated constructor stub
	}

	@Override
	//onHandleIntent receives intent and downloads the latest espn headlines
	protected void onHandleIntent(Intent intent) {
		Log.i(Tag, "onHandleIntent called");
		Bundle extras = intent.getExtras();
		Messenger messenger = (Messenger) extras.get(MESSENGER_KEY);
		URL headlineURL;
		headlineURL = null;

		if (connectionCheck.connectionStatus(this)){
			try {
				headlineURL = new URL(
						"http://api.espn.com/v1/now?apikey=bbjwshe7tha4pnppmf7jsjwn");
				
				responseString = getHeadlines(headlineURL);
				//				Log.i("response", responseString);


			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("HEADLINEDOWNLOAD", e.getMessage().toString());
			}
		} else {
			//				Toast.makeText(getApplicationContext(), "No Network Connection Detected", Toast.LENGTH_LONG).show();
			//				Log.d("NO", "called too many");

		}


		// pass the message back to MainActivity 
		Message message = Message.obtain();
		message.arg1 = Activity.RESULT_OK;
		message.obj = responseString;

		try {
			Log.i(Tag, "sending messege");
			messenger.send(message);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//open a connection to download headlines
	public static String getHeadlines(URL url) {
		Log.i(Tag, "getHeadlines called");
		String response = "";

		try {
			URLConnection connect = url.openConnection();
			BufferedInputStream bufferInputStream = new BufferedInputStream(
					connect.getInputStream());
			byte[] contextBytes = new byte[1024];
			int byteRead = 0;
			StringBuffer responseBuffer = new StringBuffer();
			while ((byteRead = bufferInputStream.read(contextBytes)) != -1) {
				response = new String(contextBytes, 0, byteRead);
				responseBuffer.append(response);
			}
			response = responseBuffer.toString();

		} catch (IOException e) {
			response = "no info passed!";
			Log.e("IOException", response);
			Log.e("ERROR", e.getMessage().toString());
		}
		return response;

	}



}
