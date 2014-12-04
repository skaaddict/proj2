package com.example.umbcevents;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class OrganizationForm extends ActionBarActivity {

	EditText ETEventName;
	EditText ETOrgName;
	EditText ETEventLocation;
	EditText ETTages;
	EditText ETExtraInfo;
	Button BstartTime;
	Button BendTime;
	Button Bsubmit;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_organization_form);
		ETEventName = (EditText) findViewById(R.id.incEventName);
		ETOrgName = (EditText) findViewById(R.id.incOrgNameForm);
		ETEventLocation = (EditText) findViewById(R.id.incEventLocation);
		ETTages = (EditText) findViewById(R.id.incTags);
		ETExtraInfo = (EditText) findViewById(R.id.incEventInfo);
		BstartTime = (Button) findViewById(R.id.enterStartTime);
		BendTime = (Button) findViewById(R.id.enterFinishTime);
		Bsubmit = (Button) findViewById(R.id.submitButton);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.organization_form, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
