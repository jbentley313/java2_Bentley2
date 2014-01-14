package com.jbentley.sportsheadlines;

import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class HeadlineActivity extends Activity {
	TextView headDescrip;
	TextView head;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_headline);
		
		headDescrip = (TextView) this.findViewById(R.id.HeadlineTitleTextView);
		head = (TextView) this.findViewById(R.id.HeadlineHeadTextView);
		
		Bundle data = getIntent().getExtras();
		if(data != null){
		  String myString = data.getString("headlineObject");
		  Log.i("blah", myString);
		  
		  
		  String[] fullHeadlineString = myString.toString().split("description");
			String firstHalf = fullHeadlineString[0];
			String secondHalf = fullHeadlineString[1];
			Log.i("1", firstHalf);
			Log.i("1", secondHalf);
			
			String[] descript1 = secondHalf.toString().split("=");
			String firstHalf2 = descript1[0];
			String secondHalf2 = descript1[1];
			Log.i("2", firstHalf2);
			Log.i("22", secondHalf2);
			String newPassedHead = secondHalf2.replace(", lastMod", "");
			Log.i("desc", newPassedHead);
			
			
			
			
			String[] urlP = firstHalf.toString().split("href");
			Log.i("hrefsplit0", urlP[0]);
			Log.i("hrefsplit1", urlP[1]);
			
			String[] urlSplit = urlP[1].split("http:");
			Log.i("urlSplit0", urlSplit[0]);
			Log.i("urlSplit1", urlSplit[1]);
			
			String urlSplitStrip = urlSplit[1].replace("\"},", "");
			Log.i("Str", urlSplitStrip);
			String urlFinal = urlSplitStrip.replace("\\/\\/", ""); 
			Log.i("urlFinal", urlFinal);
			
			String urlPassed = urlFinal.replace("\\", "");
			Log.i("finpass", urlPassed);
			
			String headLineSt = urlP[0];
			Log.i("hlt", headLineSt);
			
			String[] splitHLs= headLineSt.split("=");
			Log.i("splitbrackHL", splitHLs[1]);
			
			String[] headlinePassed = splitHLs[1].split(", links");
			Log.i("splitHead", headlinePassed[0]);
			
			head.setText(newPassedHead);
			headDescrip.setText(headlinePassed[0]);
			
			
//			String[] pdescription = 
//			String[] thirdString = myString.toString().split("=");
//			String firstHalf3 = thirdString[0];
//			String secondHalf3 = thirdString[1];
//			Log.i("3", firstHalf3);
//			Log.i("3", secondHalf3);
//			
//			String[] fourthString = myString.toString().replace(", links", "");
//			String firstHalf4 = fourthString[0];
//			String secondHalf4 = fourthString[1];
//			Log.i("4", firstHalf4);
//			Log.i("4", secondHalf4);
			
			
		}
	}

	

}
