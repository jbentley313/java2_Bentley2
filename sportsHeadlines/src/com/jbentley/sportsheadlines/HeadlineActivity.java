package com.jbentley.sportsheadlines;

import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class HeadlineActivity extends Activity {
	TextView headDescrip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_headline);
		
		headDescrip = (TextView) this.findViewById(R.id.HeadlineTitleTextView);
		
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
			String newPassedDescription = secondHalf2.replace(", lastMod", "");
			Log.i("desc", newPassedDescription);
			
			
			headDescrip.setText(newPassedDescription);
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
