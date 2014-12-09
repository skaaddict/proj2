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

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Toast;

public class StudentActivity extends ListActivity implements OnRefreshListener {

	private ArrayList<EventStruct> events = new ArrayList<EventStruct>();
	private ArrayList<String> listItems = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private boolean fromRefresh = false;
	private String lastSearch = "";
	private SearchView searchView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student);
		String myMessage = "Actions: \n\tDrag down on the screen to refresh. " +
				"\n\tType keywords in search bar  to narrow your results." +
				"\n\tClick reset in menu to reset the search contents.";
		showAlert(myMessage);
		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.container);
		mSwipeRefreshLayout.setOnRefreshListener(this);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listItems);
		setListAdapter(adapter);
		// Get the intent, verify the action and get the query
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			doMySearch(query);

		}

		new SelectTask(this).execute();

		// Make select call.

	}

	private void setList() {
		events = EventTest.sortByDate(events);
		listItems.clear();
		for (int i = 0; i < events.size(); i++) {
			String toAdd = events.get(i).toString();
			listItems.add(toAdd);
			

		}
		listItems.add("\n");
		adapter.notifyDataSetChanged();
	}

	private void showAlert(String message) {

		new AlertDialog.Builder(this)
				.setTitle("Info")
				.setMessage(message)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// do whatever.
							}
						})

				.setIcon(android.R.drawable.ic_dialog_info).show();
	}

	protected void onNewIntent(Intent theIntent) {

		if (Intent.ACTION_SEARCH.equals(theIntent.getAction())) {
			String query = theIntent.getStringExtra(SearchManager.QUERY);
			doMySearch(query);

		}
	}

	private void doMySearch(String query) {
		// TODO Auto-generated method stub
		lastSearch = query;
		ArrayList<EventStruct> matchedEvents = EventTest.searchByTags(query,
				events);
		// listItems.add(query);
		listItems.clear();
		for (int i = 0; i < matchedEvents.size(); i++) {
			String toAdd = matchedEvents.get(i).toString();
			listItems.add(toAdd);

		}
		adapter.notifyDataSetChanged();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.student, menu);

		// Get the SearchView and set the searchable configuration
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		searchView = (SearchView) menu.findItem(R.id.menu_search)
				.getActionView();
		// Assumes current activity is the searchable activity
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(false); // Do not iconify the widget;
													// expand it by default

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.reset) {
			new SelectTask(this).execute();
			searchView.setQuery("", false);
			return true;
		} else if (id == R.id.help) {
			String myMessage = "Actions: \n\tDrag down on the screen to refresh. " +
					"\n\tType keywords in search bar  to narrow your results." +
					"\n\tClick reset in menu to reset the search contents.";
			showAlert(myMessage);
			return true;
		} 
		return super.onOptionsItemSelected(item);
	}

	// /////Danny's Task.
	private class SelectTask extends AsyncTask<String, String, Void> {

		private InputStream is = null;
		private String result = null;
		private String line = null;
		private ArrayList<EventStruct> eventList = new ArrayList<EventStruct>();
		private boolean done = false;
		private Context mContext;

		public SelectTask(Context context) {
			mContext = context;
		}

		@Override
		protected Void doInBackground(String... params) {
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
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 8);
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
			// convert php response output to json object and fetch values from
			// json
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

					// char[] test = startTime.toCharArray();
					// // if (test[6] == '0') {
					// // test[5]--;
					// // test[6] = '9';
					// // } else {
					// // test[6]--;
					// // }
					// startTime = new String(test);
					// Calendar test = Calendar.getInstance();
					Date date = sdf.parse(startTime);

					starting.setTime(date);



					String endTime = row.getString("end_time");
					GregorianCalendar ending = new GregorianCalendar();

					// test = endTime.toCharArray();
					// // if (test[6] == '0') {
					// // test[5]--;
					// // test[6] = '9';
					// // } else {
					// // test[6]--;
					// // }
					// endTime = new String(test);

					Date date2 = sdf.parse(endTime);

					ending.setTime(date2);

					String desc = row.getString("event_description");
					String tags = row.getString("event_tags");
					EventStruct toAdd = new EventStruct(org, evName, loc, tags,
							desc, starting, ending);
					eventList.add(toAdd);
					// events[i] = new Event();
					// events[i] = new Event(row.getString("org_name"),
					// row.getString("event_name"),
					// row.getString("event_location"),
					// row.getString("start_time"), row.getString("end_time"),
					// row.getString("event_description"),
					// row.getString("event_tags"));
				}
				Log.e("pass 3", "json output conversion success ");
				// TODO return
				done = true;
				// return events;

				events = eventList;

				return null;
			} catch (Exception e) {
				Log.e("Fail 3", e.toString());
				return null;
			}
		}

		@Override
		protected void onProgressUpdate(String... values) {
			if (values != null && values.length > 0) {
				Toast.makeText(mContext, values[0], Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub

			if (fromRefresh && !lastSearch.equals("")) {
				fromRefresh = false;
				mSwipeRefreshLayout.setRefreshing(false);
				doMySearch(lastSearch);
			} else if (fromRefresh) {
				mSwipeRefreshLayout.setRefreshing(false);
				setList();
			} else {
				lastSearch = "";
				setList();

			}
		}

		//
		// public ArrayList<EventStruct> grabIt() {
		// while (!done) {
		//
		// }
		// return eventList;
		// }
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		fromRefresh = true;
		Toast.makeText(this, "How refreshing...", Toast.LENGTH_SHORT).show();
		new SelectTask(this).execute();
	}
}
