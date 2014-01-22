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

public class MainActivityFragment extends Fragment implements OnClickListener {
	
	Button refreshBtn;
	Button searchBtn;
	

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("FRAG", "CREATE VIEW");
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_main, container);
		
		refreshBtn = (Button) view.findViewById(R.id.refreshBtn);
		searchBtn = (Button) view.findViewById(R.id.searchButton);
		
		refreshBtn.setOnClickListener(this);
		searchBtn.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.refreshBtn:
			Log.i("Refresh", "BUTTON from fragment");
			//startService(downloadIntent);
			break;

		case R.id.searchButton:
			Log.i("SEARCH", "BUTTON from fragment");

			//displayData();

			break;
		}
	}
	
}
