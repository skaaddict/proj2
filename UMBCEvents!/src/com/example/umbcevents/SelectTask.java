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
import org.json.JSONArray;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.util.Log;

/**
 * An AsyncTask that gets a list of events from the database.
 * AsyncTask<Params, Progress, Result>
 * @author Daniel Smith
 */
// TODO Change to <String, Void, Events[]>
public class SelectTask extends AsyncTask<String, Void, Void> {

	private InputStream is = null;
	private String result = null;
	private String line = null;
	
	@Override
	protected Void doInBackground(String... params) {
		// TODO implement String params for sql query
		// objects to send to php script (the sql query)
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		String condition = "where `start_time` >= '2014-12-06 14:30:00'";
		String sql = String.format("select * from `Events` %s order by `start_time` asc limit 100", condition);
		Log.e("sql request", sql);
		nameValuePairs.add(new BasicNameValuePair("sql", sql));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://userpages.umbc.edu/~danism1/CMSC331/Project2/select.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			Log.e("pass 1", "connection success ");
		} catch (Exception e) {
			Log.e("Fail 1", e.toString());
			return null;
		}
		// read php response output
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			Log.e("pass 2", "php output read success ");
			Log.e("php response", result);
		} catch (Exception e) {
			Log.e("Fail 2", e.toString());
			return null;
		}
		// convert php response output to json object and fetch values from json object
		try {
			JSONArray json_arr = new JSONArray(result);
			// TODO create Event[] here
			// Event[] events = new Event[json_arr.length()];
			for(int i = 0 ; i < json_arr.length() ; i ++){
				JSONObject row = json_arr.getJSONObject(i);
				String org_name = row.getString("org_name");
				row.getString("event_name");
				row.getString("event_location");
				row.getString("start_time");
				row.getString("end_time");
				row.getString("event_description");
				row.getString("event_tags");
				// events[i] = new Event();
				// events[i] = new Event(row.getString("org_name"), row.getString("event_name"), row.getString("event_location"), row.getString("start_time"), row.getString("end_time"), row.getString("event_description"), row.getString("event_tags"));
			}
			Log.e("pass 3", "json output conversion success ");
			// TODO return Event[] here
			// return events;
			return null;
		} catch (Exception e) {
			Log.e("Fail 3", e.toString());
			return null;
		}
	}
}
