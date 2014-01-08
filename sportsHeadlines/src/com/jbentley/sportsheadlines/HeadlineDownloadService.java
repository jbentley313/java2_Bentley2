package com.jbentley.sportsheadlines;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.nfc.Tag;
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

	public HeadlineDownloadService() {
		
		super("headlineDownloadService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i(Tag, "onHandleIntent called");
		Bundle extras = intent.getExtras();
		Messenger messenger = (Messenger) extras.get(MESSENGER_KEY);
		URL headlineURL;
		headlineURL = null;


		try {
			headlineURL = new URL(
					"http://api.espn.com/v1/now?apikey=bbjwshe7tha4pnppmf7jsjwn");
			responseString = getHeadlines(headlineURL);
//			Log.i("response", responseString);
			

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("HEADLINEDOWNLOAD", e.getMessage().toString());
		}

		// pass the message back to MainActivity 
					Message message = Message.obtain();
					message.arg1 = Activity.RESULT_OK;
					message.obj = responseString;

					try {
						messenger.send(message);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		
	}

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
		}
		return response;

	}

	
	
}
