package com.example.dbtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private String org_name = "default org";
	private String event_name = "default name";
	private String event_location = "default location";
	private String start_time = "2014-00-00 00:00:00";
	private String end_time = "2014-00-00 00:00:00";
	private String event_description = "default description";
	private String event_tags = "default tags";
	

	/**
	 * "constructor"
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final EditText e_org = (EditText) findViewById(R.id.editText1);
		final EditText e_name = (EditText) findViewById(R.id.editText2);
		Button insert = (Button) findViewById(R.id.submitButton);
		// anonymous class defined here to handle button click
		insert.setOnClickListener(new View.OnClickListener() {

			/**
			 * does this when button is clicked
			 */
			public void onClick(View v) {
				// Don't do this. For use when db call is not implemented as AsyncTask
				//if (android.os.Build.VERSION.SDK_INT > 9) {
				//    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				//    StrictMode.setThreadPolicy(policy);
				//}
				// TODO assign variable values from text fields here
				org_name = e_org.getText().toString();
				event_name = e_name.getText().toString();
				// TODO validation on variables
				// calls new async task to insert row into the db
				new InsertTask(getBaseContext()).execute(org_name, event_name, event_location,
						start_time, end_time, event_tags, event_description);
			}
		});
		
		Button selectButton = (Button) findViewById(R.id.selectButton);
		selectButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				org_name = e_org.getText().toString();
				// TODO get Event[] here
				new SelectTask().execute(org_name);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// menu from activity_main.xml
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}