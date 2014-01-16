/*
 * project sportsHeadlines
 * 
 * package com.jbentley.sportsheadlines
 * 
 * @author Jason Bentley
 * 
 * date Jan 13, 2014
 */
package com.jbentley.sportsheadlines;


import java.io.File;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;

import com.jbentley.sportsheadlines.HeadlineDownloadService;
import coml.jbentley.utils.FileManager;
import com.jbentley.connectivityPackage.connectivityClass;

// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 */
public class MainActivity extends Activity implements OnClickListener, OnItemClickListener{
	static  String Tag = "MAINACTIVITY";
	private TextView resultText;
	FileManager fileManager;
	String filename = "headlineFile";
	Context mContext;
	ListView listview;
	Button refreshBtn;
	Button searchBtn;
	connectivityClass connectionCheck;
	Intent downloadIntent;
	Object obj;
	ArrayList<HashMap<String, String>>  mylist;
	EditText searchTxt;
	//	String searchString;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.activity_main);

		connectionCheck = new connectivityClass();

		resultText = (TextView) this.findViewById(R.id.resultTextView);
		refreshBtn = (Button) this.findViewById(R.id.firstButton);
		searchBtn = (Button) this.findViewById(R.id.searchButton);
		searchTxt = (EditText) this.findViewById(R.id.searchTxt);

		refreshBtn.setOnClickListener(this);
		searchBtn.setOnClickListener(this);
		mContext = this;


		//inflate the listview 
		listview = (ListView) this.findViewById(R.id.list);
		View list_header = this.getLayoutInflater().inflate(R.layout.list_header, null);
		listview.addHeaderView(list_header);
		listview.setOnItemClickListener(this);

		if (savedInstanceState != null) {
			Log.i("SIS", "In Saved Instance");
			//			Toast.makeText(mContext, "THERE IS A SAVED INSTANCE!",
			//					Toast.LENGTH_SHORT).show();
			mylist = (ArrayList<HashMap< String, String>>) savedInstanceState
					.getSerializable("saved");

			fileManager = FileManager.getInstance();

			//messenger for intent
			Messenger headlineDownloadMessenger = new Messenger(headlineDownloadHandler);

			//intent to call HeadlineDownloadService to download headlines
			downloadIntent = new Intent(this, HeadlineDownloadService.class);
			downloadIntent.putExtra(HeadlineDownloadService.MESSENGER_KEY, headlineDownloadMessenger);

			if (mylist != null) {

				// adapter to display saved serialized data on the listview
				SimpleAdapter adapter = new SimpleAdapter(this, mylist, R.layout.list_row, new String[] 
						{"headline", "lastMod"}, new int[]{R.id.headline, R.id.dateMod });

				listview.setAdapter(adapter);

			} else {
				Log.d("MAIN", "myList is Null");
			}


		} else {
			// if there is no saved instance, go get data and display it
			fileManager = FileManager.getInstance();

			//messenger for intent
			Messenger headlineDownloadMessenger = new Messenger(headlineDownloadHandler);

			//intent to call HeadlineDownloadService to download headlines
			downloadIntent = new Intent(this, HeadlineDownloadService.class);
			downloadIntent.putExtra(HeadlineDownloadService.MESSENGER_KEY, headlineDownloadMessenger);

			//start the intent
			startService(downloadIntent);
			resultText.setText("Loading...");


		}

		super.onCreate(savedInstanceState);
	}

	/**
	 * Display data displays the saved data from storage
	 */
	public void displayData() {
		
		Log.i(Tag, "displayData called");
		mylist = new ArrayList<HashMap<String,String>>();
		String passedSearchString =  searchTxt.getText().toString();
		
		
		File file = this.getFileStreamPath(filename);
		if(file.exists()) {
			String JSONString = FileManager.readStringFile(getApplicationContext(), filename);
			//		connectivityClass connectionCheck = new connectivityClass();


			JSONObject job = null;
			JSONArray feed = null;
			String formattedDate = null;
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
					String description = feedObject.getString("description");
					JSONObject links = feedObject.getJSONObject("links");
					String moreLinks = links.getString("web");


					//date pattern for passed date
					SimpleDateFormat originalDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);

					//get timezone
					originalDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
					Date date = null;

					//parse passed date and then format it
					try {
						date = originalDateFormat.parse(lastMod);
						formattedDate = new SimpleDateFormat("E' at 'h:mm a " , Locale.US).format(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (passedSearchString.equalsIgnoreCase("")) {
//						Toast.makeText(mContext, "PASSEDSEARCH NULL", Toast.LENGTH_SHORT).show();
						//hashmap for listview
						HashMap<String, String> displayMap = new HashMap<String, String>();
						displayMap.put("description", description);

						displayMap.put("headline", headline);

						displayMap.put("lastMod", formattedDate);

						displayMap.put("links", moreLinks);



						//add displayMap to mylist
						mylist.add(displayMap);
					}
					else if (headline.toLowerCase().contains(passedSearchString.toLowerCase())){
						int listLength = (mylist.size() + 1);
						String listLengthText = Integer.toString(listLength);
						resultText.setText("Displaying " + listLengthText + " headlines");
						
						Toast.makeText(mContext, "YES", Toast.LENGTH_LONG).show();
//						mylist.clear();

						HashMap<String, String> displayMap = new HashMap<String, String>();
						displayMap.put("description", description);

						displayMap.put("headline", headline);

						displayMap.put("lastMod", formattedDate);

						displayMap.put("links", moreLinks);


						
						//add displayMap to mylist
						mylist.add(displayMap);

					}

				}
				
				searchTxt.setText("");

				//adapter to display
				SimpleAdapter adapter = new SimpleAdapter(this, mylist, R.layout.list_row, new String[] 
						{"headline", "lastMod"}, new int[]{R.id.headline, R.id.dateMod });

				listview.setAdapter(adapter);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("ERROR", e.getMessage().toString());
			}
			// Connection checker to notify user of no connection when displaying saved data
			if (!connectionCheck.connectionStatus(mContext)){
				Toast.makeText(mContext, "No network connection detected!", Toast.LENGTH_LONG).show();
			}
		} 
	}

	@Override
	//refresh button (need to implement a three second delay on week2 to prevent too many api calls)
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.firstButton:
			startService(downloadIntent);
			break;

		case R.id.searchButton:
			Log.i("SEARCH", "BUTTON");
//			String searchString = searchTxt.getText().toString();
//			Log.i("SearchStrg", searchString);

			displayData();


			break;
		}

	}

	//listview onItemClick
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		if (position !=0){
			obj = arg0.getItemAtPosition(position);

			int itemPos = ((position)-1);
			String itemStrg = Integer.toString(itemPos);
			Log.i(Tag, itemStrg);


			//			String JSONString = FileManager.readStringFile(getApplicationContext(), filename);
			//			
			//			JSONObject job = null;
			//			JSONArray feed = null;
			//			
			//			
			//				try {
			//					job = new JSONObject(JSONString);
			//				} catch (JSONException e2) {
			//					// TODO Auto-generated catch block
			//					e2.printStackTrace();
			//				}
			//				try {
			//					feed = job.getJSONArray("feed");
			//				} catch (JSONException e1) {
			//					// TODO Auto-generated catch block
			//					e1.printStackTrace();
			//				}
			//				int feedSize = feed.length();
			//				
			//				//loop through saved data and display
			//				
			//					try {
			//						JSONObject feedObject = feed.getJSONObject(itemPos);
			//						
			//						String passHeadline = feedObject.getString("headline");
			//						String passDescription = feedObject.getString("description");
			//						JSONObject passOb = feedObject.getJSONObject("links");
			//						String passURL = passOb.getString("web");
			//						
			//						
			//						Log.i(Tag, passHeadline);
			//						Log.i(Tag, passDescription);
			//						Log.i(Tag, passURL);
			//						
			//						
			//					} catch (JSONException e) {
			//						// TODO Auto-generated catch block
			//						e.printStackTrace();
			//					}








			String value= obj.toString();

			Log.i("has", value);

			Intent headlineIntent = new Intent(this, HeadlineActivity.class);
			headlineIntent.putExtra("headlineObject",  value);

			startActivityForResult(headlineIntent, 0);
		}
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

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
					resultText.setText("Nothing to display, check network connection");
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

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		if (mylist != null && !mylist.isEmpty()) {
			outState.putSerializable("saved", (Serializable) mylist);
			Log.i("SAVE INSTANCE", "Saving Instance State data");

		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  if (resultCode == RESULT_OK && requestCode == 0) {
		    Bundle result = data.getExtras();
		    Log.i("RES", result.toString());
		    Object aa = result.get("keyA");
		    Log.i("DescMain", aa.toString());
		    
		    Toast.makeText(mContext, "Just read headline: " + aa, Toast.LENGTH_SHORT).show();
		    
		  }
		}


}
