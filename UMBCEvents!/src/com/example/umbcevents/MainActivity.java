package com.example.umbcevents;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create button listeners for navigation to organization and student
		// activities.

		Button orgButton = (Button) findViewById(R.id.organizationButton);
		orgButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Go to orginization.
				headToOrgForm();

			}
		});

		Button stuButton = (Button) findViewById(R.id.studentButton);
		stuButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Go to orginization.
				headToStudentActivity();

			}
		});

	}

	protected void headToOrgForm() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, OrganizationForm.class);
		startActivity(intent);
	}

	protected void headToStudentActivity() {
		Toast.makeText(this, "Loading events...", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, StudentActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			goToSettings();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void goToSettings() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}
}
