package com.example.umbcevents;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * An AsyncTask that inserts a new row into the database. Takes string
 * parameters of the data to be inserted into the database.
 * 
 * @author Daniel Smith
 */
public class InsertTask extends AsyncTask<String, String, Void> {

	private InputStream is = null;
	private String result = null;
	private String line = null;
	private int code;
	private Context mContext;

	private static final String insertSuccessString = "Event successfully created!";
	private static final String insertFailString = "Event not created.";
	
	/**
	 * Constructor for InsertTask.
	 * @param context - context of the activity that called this task. used to raise toast.
	 */
    public InsertTask (Context context){
         mContext = context;
    }

	/**
	 * Background task that inserts a row into the Events database.
	 * @return null
	 */
	protected Void doInBackground(String... params) {
		// objects to send to php script
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("org_name", params[0]));
		nameValuePairs.add(new BasicNameValuePair("event_name", params[1]));
		nameValuePairs.add(new BasicNameValuePair("event_location", params[2]));
		nameValuePairs.add(new BasicNameValuePair("start_time", params[3]));
		nameValuePairs.add(new BasicNameValuePair("end_time", params[4]));
		nameValuePairs.add(new BasicNameValuePair("event_tags", params[5]));
		nameValuePairs.add(new BasicNameValuePair("event_description", params[6]));
		// get input stream connection to db
		try {
			// post data to php script
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://userpages.umbc.edu/~danism1/CMSC331/Project2/insert.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			// get response from php script
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			Log.e("pass 1", "connection success ");
		} catch (Exception e) {
			Log.e("Fail 1", e.toString());
			// publishProgress("Failed to connect to db: " + e.toString());
			publishProgress("Check internet connection");
			return null;
		}
		try {
			// read output response from php script
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			Log.e("pass 2", "read success ");
		} catch (Exception e) {
			Log.e("Fail 2", e.toString());
			publishProgress(e.toString());
			return null;
		}
		// check to see if insertion was successful from json code in php script
		try {
			// make a json object from the resulting php script response
			JSONObject json_data = new JSONObject(result);
			code = (json_data.getInt("code"));
			// check the returned code
			if (code == 1) {
				Log.e("pass 3", "insert success!");
				publishProgress(insertSuccessString);
			} else {
				Log.e("fail 3", "insert fail!");
				publishProgress(insertFailString);
			}
		} catch (Exception e) {
			Log.e("Fail 3", e.toString());
			publishProgress("Could not read json response code. "
					+ e.toString());
		}
		// to satisfy the return type of Void
		return null;
	}

	@Override
	protected void onProgressUpdate(String... msg) {
		if(msg!= null && msg.length > 0){
			Toast.makeText(mContext, msg[0], Toast.LENGTH_SHORT).show();
		}
	}
}
