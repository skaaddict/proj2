package com.example.umbcevents;

import java.util.Calendar;

import android.support.v7.app.ActionBarActivity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class OrganizationForm extends ActionBarActivity {

	EditText ETEventName;
	EditText ETOrgName;
	EditText ETEventLocation;
	EditText ETTages;
	EditText ETExtraInfo;
	Button BstartTime;
	Button BendTime;
	Button Bsubmit;
	
	int startHour = -1, startMinute =-1, finishHour =-1, finishMinute =-1;
	
	
	
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
		
		//Set up button listeners....
		BstartTime.setOnClickListener( new OnClickListener() {

            public void onClick(View v) {
                // TODO Do button things.
            	createStartClock();
            	
            }
        });
		
		BendTime.setOnClickListener( new OnClickListener() {

            public void onClick(View v) {
                // TODO Do button things.
            	createEndClock();
            	
            }
        });
		
		Bsubmit.setOnClickListener( new OnClickListener() {

            public void onClick(View v) {
                // TODO Do button things.
            	submitNonsense();
            	
            }
        });
		
	}

	protected void submitNonsense() {
		// TODO Auto-generated method stub
		
	}

	protected void createEndClock() {
		// TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            
        	int count = 0;
        	
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
            	 if(count == 1){
                 	finishHour = selectedHour;
                 	finishMinute = selectedMinute;
                 }
                 count++;
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
	}

	protected void createStartClock() {
		// TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            
            int count = 0;
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if(count == 1){
                	startHour = selectedHour;
                	startMinute = selectedMinute;
                }
                count++;
            }
            
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
	}

	protected void debugToast() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "yay!", Toast.LENGTH_LONG).show();
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
