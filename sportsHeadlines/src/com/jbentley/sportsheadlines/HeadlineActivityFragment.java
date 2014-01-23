package com.jbentley.sportsheadlines;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HeadlineActivityFragment extends Fragment implements OnClickListener{
	TextView headDescrip;
	TextView head;
	Button espnBtn;
	private String urlString;
	String newPassedHead;
	String hlToPassBacktoMainAct;
	
	public interface pubMethodsfromHLFrag {
		 void strtESPN(String urlString);
		 void headlineJustRead(String hljr);
		
	}
	
	private pubMethodsfromHLFrag parentActivity;
	

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
	
		super.onAttach(activity);
		Log.i("HLFrag", "onAttatch");
		if (activity instanceof pubMethodsfromHLFrag) {
			parentActivity = (pubMethodsfromHLFrag) activity;
		}
		else {
			throw new ClassCastException(activity.toString() + " must implement pubMethodsfromHLFrag!!");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_headline, container);
		Log.i("HeadlineActivity", "FRAGMENT!!");
		headDescrip = (TextView) view.findViewById(R.id.HeadlineTitleTextView);
		head = (TextView) view.findViewById(R.id.HeadlineHeadTextView);
		espnBtn = (Button) view.findViewById(R.id.moreButton);
		espnBtn.setOnClickListener(this);
		
		Bundle data = getActivity().getIntent().getExtras();
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
			parentActivity.headlineJustRead(hlToPassBacktoMainAct);
			
			head.setText(newPassedHead);
			headDescrip.setText(headlinePassed[0]);
		
		}
		
		return view;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		parentActivity.strtESPN(urlString);
	}

}
