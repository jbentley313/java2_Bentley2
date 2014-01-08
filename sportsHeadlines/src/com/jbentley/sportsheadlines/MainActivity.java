package com.jbentley.sportsheadlines;

import com.jbentley.sportsheadlines.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

import com.jbentley.sportsheadlines.HeadlineDownloadService;


public class MainActivity extends Activity {
	//	private TextView titleTextView;
	private Button firstButton;
	private TextView resultText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);




		resultText = (TextView) this.findViewById(R.id.resultTextView);
		firstButton = (Button) this.findViewById(R.id.firstButton);
		//        titleTextView = (TextView) this.findViewById(R.id.titleTextView);


		Handler headlineDownloadHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub

				String response = null;


				if (msg.arg1 == RESULT_OK && msg.obj != null)
				{

					try {
						response = (String) msg.obj;
					} 
					catch (Exception e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.e("handleMessage", e.getMessage().toString());
					}
					
				}
			}

		};
		Messenger headlineDownloadMessenger = new Messenger(headlineDownloadHandler);


		Intent downloadIntent = new Intent(this, HeadlineDownloadService.class);
		downloadIntent.putExtra(HeadlineDownloadService.MESSENGER_KEY, headlineDownloadMessenger);
		startService(downloadIntent);
		resultText.setText("Loading...");
	}
}
