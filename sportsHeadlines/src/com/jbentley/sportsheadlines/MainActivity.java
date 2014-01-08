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


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jbentley.sportsheadlines.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.jbentley.sportsheadlines.HeadlineDownloadService;
import coml.jbentley.utils.FileManager;
import com.jbentley.connectivityPackage.connectivityClass;

// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 */
public class MainActivity extends Activity {
	static  String Tag = "MAINACTIVITY";
	private Button firstButton;
	private TextView resultText;
	FileManager fileManager;
	String filename = "headlineFile";
	Context mContext;
	ListView listview;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		resultText = (TextView) this.findViewById(R.id.resultTextView);
		firstButton = (Button) this.findViewById(R.id.firstButton);
		fileManager = FileManager.getInstance();
		final connectivityClass connectionCheck = new connectivityClass();
		mContext = this;

		//handler for headlinedownload service
		Handler headlineDownloadHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Log.i(Tag, "handleMessage called");

				String response = null;


				if (msg.arg1 == RESULT_OK && msg.obj != null)
				{

					try {
						response = (String) msg.obj;
						if (connectionCheck.connectionStatus(mContext)){

							//save data then display here
							fileManager.writeStringFile(mContext, filename, response);
						}
						resultText.setText("Done.");
						displayData();
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

		//messenger for intent
		Messenger headlineDownloadMessenger = new Messenger(headlineDownloadHandler);

		//intent to call HeadlineDownloadService to download headlines
		Intent downloadIntent = new Intent(this, HeadlineDownloadService.class);
		downloadIntent.putExtra(HeadlineDownloadService.MESSENGER_KEY, headlineDownloadMessenger);

		//start the intent
		startService(downloadIntent);
		resultText.setText("Loading...");

		//inflate the listview 
		listview = (ListView) this.findViewById(R.id.list);
		View list_header = this.getLayoutInflater().inflate(R.layout.list_header, null);
		listview.addHeaderView(list_header);


	}

	/**
	 * Display data displays the saved data from storage
	 */
	public void displayData() {
		Log.i(Tag, "displayData called");
		String JSONString = FileManager.readStringFile(this, filename);
		connectivityClass connectionCheck = new connectivityClass();

		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String,String>>();
		JSONObject job = null;
		JSONArray feed = null;

		try {
			job = new JSONObject(JSONString);
			feed = job.getJSONArray("feed");
			int feedSize = feed.length();
			resultText.setText("Displaying " + String.valueOf(feedSize) + " headlines");

			//loop through saved data and display
			for (int i = 0; i < feedSize; i++) {
				JSONObject feedObject = feed.getJSONObject(i);
				String headline = feedObject.getString("headline");
				String lastMod = feedObject.getString("lastModified");

				//2014-01-08T15:28:38Z
				Date date;
				date = null;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).parse(lastMod);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("ERRORDATE", e.getMessage().toString());
				}

				String time = new SimpleDateFormat("MM-dd ' at 'h:mm a " , Locale.US).format(date);

				HashMap<String, String> displayMap = new HashMap<String, String>();
				displayMap.put("headline", headline);
				displayMap.put("lastMod", time);

				//add displayMap to mylist
				mylist.add(displayMap);

			}

			//adapter to display
			SimpleAdapter adapter = new SimpleAdapter(this, mylist, R.layout.list_row, new String[] 
					{"headline", "lastMod"}, new int[]{R.id.headline, R.id.dateMod });

			listview.setAdapter(adapter);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Connection checker to notify user of no connection when displaying saved data
		if (!connectionCheck.connectionStatus(mContext)){
			Toast.makeText(mContext, "No network connection detected!", Toast.LENGTH_LONG).show();
		}

	}
}
