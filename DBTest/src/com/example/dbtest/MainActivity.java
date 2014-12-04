package com.example.dbtest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity{

	private String org_name = "default org";
	private String event_name = "default name";
	private String event_location = "default location";
	private String start_time = "2014-00-00 00:00:00";
	private String end_time = "2014-00-00 00:00:00";
	private String event_tags = "default tags";
	private String event_description = "default description";

	// "constructor"
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final EditText e_org = (EditText) findViewById(R.id.editText1);
		final EditText e_name = (EditText) findViewById(R.id.editText2);
		Button insert = (Button) findViewById(R.id.button1);
		insert.setOnClickListener(new View.OnClickListener(){

			// does this when button is clicked
			public void onClick(View v){
				/*
				if (android.os.Build.VERSION.SDK_INT > 9) {
				    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				    StrictMode.setThreadPolicy(policy);
				}
				*/
				// TODO get assign variable values from text fields here
				org_name = e_org.getText().toString();
				event_name = e_name.getText().toString();
				// call new async task here
				new InsertTask().execute(org_name, event_name, event_location,
					start_time, end_time, event_tags, event_description);
				// Toast.makeText(getBaseContext(), x, Toast.LENGTH_SHORT).show();
			}
		});
	}

	public Context context(){
		return getBaseContext();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		// menu from activity_main.xml
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}