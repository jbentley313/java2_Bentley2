package com.jbentley.sportsheadlines;

import com.jbentley.connectivityPackage.connectivityClass;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.jbentley.sportsheadlines.HeadlineActivityFragment;

public class HeadlineActivity extends Activity implements OnClickListener, HeadlineActivityFragment.pubMethodsfromHLFrag {

	String hlToPassBacktoMainAct;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//			finish();
//			return;
//		}
		
		setContentView(R.layout.headline_frag);
		
	}

	@Override
	
	//more on ESPN btn
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	//start ESPN intent
	public void strtESPN(String urlString) {
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
	
	
	public void headlineJustRead(String hljr) {
		hlToPassBacktoMainAct = hljr;
	}
	
	
	@Override
	
	//pass data back to mainactivity
	public void finish() {
	    Intent data = new Intent();
	    data.putExtra("keyA", hlToPassBacktoMainAct);
	    setResult(RESULT_OK, data);
	    super.finish();
	}

}
