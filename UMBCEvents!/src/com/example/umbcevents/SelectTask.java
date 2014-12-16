package com.example.umbcevents;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * An AsyncTask that gets a list of events from the database.<br>
 * AsyncTask<Params, Progress, Result>
 * 
 * @author Daniel Smith
 */
// TODO Change to <String, Void, Events[]>
public class SelectTask extends AsyncTask<String, String, ArrayList<Event>> {

	private InputStream is = null;
	private String result = null;
	private String line = null;
	private ArrayList<Event> eventList = new ArrayList<Event>();
	private Context mContext;

	public SelectTask(Context context) {
		mContext = context;
	}

	@Override
	protected ArrayList<Event> doInBackground(String... params) {
		// TODO implement String params for sql query
		// objects to send to php script (the sql query)
		eventList.clear();
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar myCalandar = Calendar.getInstance();

		String currentTime = sdf.format(myCalandar.getTime());
		String condition = "where `start_time` >= '" + currentTime + "'";
		String sql = String
				.format("select * from `Events` %s order by `start_time` asc limit 100",
						condition);
		Log.e("sql request", sql);
		nameValuePairs.add(new BasicNameValuePair("sql", sql));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://userpages.umbc.edu/~danism1/CMSC331/Project2/select.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			Log.e("pass 1", "connection success ");
		} catch (Exception e) {
			publishProgress("Check internet connection");
			Log.e("Fail 1", e.toString());
			return null;
		}
		// read php response output
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
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
		// convert php response output to json object and fetch values from json
		// object
		try {
			JSONArray json_arr = new JSONArray(result);
			// TODO create Event[] here
			// Event[] events = new Event[json_arr.length()];

			for (int i = 0; i < json_arr.length(); i++) {
				JSONObject row = json_arr.getJSONObject(i);
				String org = row.getString("org_name");
				String evName = row.getString("event_name");
				String loc = row.getString("event_location");
				String startTime = row.getString("start_time");
				GregorianCalendar starting = new GregorianCalendar();
				// Setting date.
				// "yyyy-MM-dd HH:mm:ss"

//				char[] test = startTime.toCharArray();
////				if (test[6] == '0') {
////					test[5]--;
////					test[6] = '9';
////				} else {
////					test[6]--;
////				}
//				startTime = new String(test);
				// Calendar test = Calendar.getInstance();
				Date date = sdf.parse(startTime);

				starting.setTime(date);

				// Log.e("<NOT TITS>", test.toString());
				// Log.e("<TITS>", evStart.toString());

				String endTime = row.getString("end_time");
				GregorianCalendar ending = new GregorianCalendar();

//				test = endTime.toCharArray();
////				if (test[6] == '0') {
////					test[5]--;
////					test[6] = '9';
////				} else {
////					test[6]--;
////				}
//				endTime = new String(test);

				Date date2 = sdf.parse(endTime);

				ending.setTime(date2);

				String desc = row.getString("event_description");
				String tags = row.getString("event_tags");
				Event toAdd = new Event(org, evName, loc, tags,
						desc, starting, ending);
				eventList.add(toAdd);
				// events[i] = new Event();
				// events[i] = new Event(row.getString("org_name"),
				// row.getString("event_name"), row.getString("event_location"),
				// row.getString("start_time"), row.getString("end_time"),
				// row.getString("event_description"),
				// row.getString("event_tags"));
			}
			Log.e("pass 3", "json output conversion success ");
			// TODO return
			// return events;
			return eventList;
		} catch (Exception e) {
			Log.e("Fail 3", e.toString());
			return null;
		}
	}

	@Override
	protected void onPostExecute(ArrayList<Event> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	@Override
	protected void onProgressUpdate(String... values) {
		if(values != null && values.length > 0){
			Toast.makeText(mContext, values[0], Toast.LENGTH_SHORT).show();
		}
	}

	

	

//
//	public ArrayList<EventStruct> grabIt() {
//		while (!done) {
//
//		}
//		return eventList;
//	}
}
