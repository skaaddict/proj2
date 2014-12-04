package com.example.dbtest;

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
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

// param x returntype
public class InsertTask extends AsyncTask<String, Void, Void>{

	private InputStream is = null;
	private String result = null;
	private String line = null;
	private int code;
	
	void publishProgress(){
		
	}
	
	@Override
	protected Void doInBackground(String... params){
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("org_name", params[0]));
		nameValuePairs.add(new BasicNameValuePair("event_name", params[1]));
		nameValuePairs.add(new BasicNameValuePair("event_location", params[2]));
		nameValuePairs.add(new BasicNameValuePair("start_time", params[3]));
		nameValuePairs.add(new BasicNameValuePair("end_time", params[4]));
		nameValuePairs.add(new BasicNameValuePair("event_tags", params[5]));
		nameValuePairs.add(new BasicNameValuePair("event_description", params[6]));
		// get input stream connection to db
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
				"http://userpages.umbc.edu/~danism1/CMSC331/Project2/insert.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			Log.e("pass 1", "connection success ");
		} catch (Exception e){
			Log.e("Fail 1", e.toString());
			Toast.makeText(context(), e.toString(), Toast.LENGTH_SHORT).show();
		}
		// read from the input stream
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			while((line = reader.readLine()) != null){
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			Log.e("pass 2", "connection success ");
		} catch (Exception e){
			Log.e("Fail 2", e.toString());
			return e.toString();
		}
		// check to see if insertion was successful from json code in php script
		try{
			JSONObject json_data = new JSONObject(result);
			code = (json_data.getInt("code"));
			if(code == 1){
				return "Insert Success!";
			} else{
				return "Sorry, Try Again";
			}
		} catch (Exception e){
			Log.e("Fail 3", e.toString());
			return "Almost! " + e.toString();
		}
	}
}
