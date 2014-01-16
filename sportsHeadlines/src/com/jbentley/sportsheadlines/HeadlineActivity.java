package com.jbentley.sportsheadlines;

import com.jbentley.connectivityPackage.connectivityClass;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HeadlineActivity extends Activity implements OnClickListener{
	TextView headDescrip;
	TextView head;
	Button espnBtn;
	private String urlString;
	String newPassedHead;
	String hlToPassBacktoMainAct;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_headline);
		
		headDescrip = (TextView) this.findViewById(R.id.HeadlineTitleTextView);
		head = (TextView) this.findViewById(R.id.HeadlineHeadTextView);
		espnBtn = (Button) this.findViewById(R.id.moreButton);
		espnBtn.setOnClickListener(this);
		
		Bundle data = getIntent().getExtras();
		if(data != null){
		  String myString = data.getString("headlineObject");
		  Log.i("passed string", myString);
		  
		  
		  
		  
		  
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
			 newPassedHead = secondHalf2.replace(", lastMod", "");
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
			
			 urlString = urlFinal.replace("\\", "");
			Log.i("finpass", urlString);
			
			String headLineSt = urlP[0];
			Log.i("hlt", headLineSt);
			
			String[] splitHLs= headLineSt.split("=");
			Log.i("splitbrackHL", splitHLs[1]);
			
			String[] headlinePassed = splitHLs[1].split(", links");
			Log.i("splitHead", headlinePassed[0]);
			
			hlToPassBacktoMainAct = headlinePassed[0];
			
			
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		connectivityClass connectionChecker = new connectivityClass();

		// TODO Auto-generated method stub
		if (connectionChecker.connectionStatus(this)) {

			Intent intentESPN = new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://" + urlString));

			startActivity(intentESPN);
		} else {
			Toast.makeText(this,
					"Sorry,  a network connection is required.",
					Toast.LENGTH_LONG).show();

		}
	}

	@Override
	public void finish() {
	    Intent data = new Intent();
	    data.putExtra("keyA", hlToPassBacktoMainAct);
	    setResult(RESULT_OK, data);
	    super.finish();
	}

}
