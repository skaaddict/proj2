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

/**
 * The starting page of the application that allows the user to select which mode to use.
 */
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
				// Go to organization
				gotoOrgForm();

			}
		});
		Button stuButton = (Button) findViewById(R.id.studentButton);
		stuButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Go to student activity
				gotoStudentActivity();

			}
		});
	}

	// TODO call me
	private void diplaySplashScreen() {
		Thread splashTimer = new Thread() {
			public void run() {
				try {
					sleep(5000);
					startActivity(new Intent(MainActivity.this, Splash.class));
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					// notice empty
				}
			}
		};
		splashTimer.start();
	}

	protected void gotoOrgForm() {
		Intent intent = new Intent(this, OrganizationForm.class);
		startActivity(intent);
	}

	protected void gotoStudentActivity() {
		Toast.makeText(this, "Loading events...", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, StudentActivity.class);
		startActivity(intent);
	}

	private void goToSettings() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Handle action bar item clicks here. The action bar will automatically handle clicks on the
	 * Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
	 * @Override
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			goToSettings();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
