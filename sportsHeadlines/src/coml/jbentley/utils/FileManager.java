/*
 * project sportsHeadlines
 * 
 * package coml.jbentley.utils
 * 
 * @author Jason Bentley
 * 
 * date Jan 8, 2014
 */
package coml.jbentley.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class FileManager is a singleton class that can read and write to storage
 */
public class FileManager {
	private static FileManager _instance;
	static String Tag = "FILEMANAGER";
	Context mContext;

	/**
	 * Instantiates a new file manager.
	 */
	private FileManager() {

	}
	//get instance of FileManager
	/**
	 * Gets the single instance of FileManager.
	 *
	 * @return single instance of FileManager
	 */
	public static FileManager getInstance() {
		Log.i(Tag, "FileManager called");
		if (_instance == null) {
			_instance = new FileManager();
		}
		return _instance;
	}

	//Write the file
	public Boolean writeStringFile(Context context, String filename,
			String content) {
		Log.i(Tag, "writeStringFile called");
		Boolean dataResult = false;

		FileOutputStream fos = null;
		try {
			fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
			fos.write(content.getBytes());
		} catch (FileNotFoundException e) {
			Log.e(Tag, e.getMessage().toString());
		} catch (IOException e) {
			Log.e(Tag, e.getMessage().toString());
		}

		Log.i(Tag, "file saved");

		return dataResult;
	}

	//Read the file from storage
	public static String readStringFile(Context context, String filename) {
		String content = "";
		Log.i(Tag, "readStringFile called");
		FileInputStream fis = null;

		
		try {
			fis = context.openFileInput(filename);
			BufferedInputStream biStream = new BufferedInputStream(fis);
			byte[] contentBytes = new byte[1024];
			int bytesRead = 0;
			StringBuffer contentBuff = new StringBuffer();
			while ((bytesRead = biStream.read(contentBytes)) != -1) {
				content = new String(contentBytes, 0, bytesRead);
				contentBuff.append(content);
			}
			content = contentBuff.toString();

		} catch (Exception e) {
			Log.e("Read File Error", e.toString());

		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				Log.e("Read String File fis.close", e.toString());
			}

		}
		return content;
	}

}
