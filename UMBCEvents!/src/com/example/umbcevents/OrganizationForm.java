package com.example.umbcevents;

import java.util.Calendar;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
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
	Button BDatePicker;

	int eventDay = -1, eventMonth = -1, eventYear = -1;

	int startHour = -1, startMinute = -1, finishHour = -1, finishMinute = -1;

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
		BDatePicker = (Button) findViewById(R.id.datePickerB);

		// Set up button listeners....
		BstartTime.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Do button things.
				createStartClock();

			}
		});

		BendTime.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Do button things.
				createEndClock();

			}
		});

		Bsubmit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Do button things.
				submitNonsense();

			}
		});

		BDatePicker.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Do button things.
				createDatePicker();

			}
		});

	}

	protected void createDatePicker() {
		// TODO Auto-generated method stub
		Calendar mcurrentTime = Calendar.getInstance();
		int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
		int month = mcurrentTime.get(Calendar.MONTH);
		int year = mcurrentTime.get(Calendar.YEAR);
		DatePickerDialog mDatePicker;
		mDatePicker = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {

					int counter = 0;

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						if (counter == 1) {
							eventDay = dayOfMonth;
							eventMonth = monthOfYear;
							eventYear = year;
						}
						counter++;
					}
				}, year, month, day);
		mDatePicker.setTitle("Set the Date of your Event!");
		mDatePicker.show();
	}

	
	
	
	
	protected void submitNonsense() {
		// TODO Auto-generated method stub
		boolean canWeSubmit = false;
		String errorMessage = "The following fields have not been filled out:\n";
		// Need to check all the categories, if they aren't filled in tell user.
		String messageEventName = ETEventName.getText().toString();
		String messageOrgName = ETOrgName.getText().toString();
		String messageEventLocation = ETEventLocation.getText().toString();
		String messageTags = ETTages.getText().toString();
		String messageExtraInfo = ETExtraInfo.getText().toString();

		boolean eventNameGood = false;
		boolean orgNameGood = false;
		boolean locationGood = false;
		boolean tagsGood = false;
		boolean extraInfoGood = false;
		boolean startStopGood = false;
		boolean startTimeGood = false;
		boolean endTimeGood = false;
		boolean dateGood = false;

		// Make sure five fields are NOT blank.
		// If they are, provide error messages.
		if (!messageEventName.equals("")) {
			eventNameGood = true;
			ETEventName.setBackground(getResources().getDrawable(
					R.drawable.cute_text_edit));
		} else {
			errorMessage += "-Event name was not specified.\n";
			ETEventName.setBackground(getResources().getDrawable(
					R.drawable.bad_box));
			// debugToast(errorMessage);
		}

		if (!messageOrgName.equals("")) {
			orgNameGood = true;
			ETOrgName.setBackground(getResources().getDrawable(
					R.drawable.cute_text_edit));
		} else {
			ETOrgName.setBackground(getResources().getDrawable(
					R.drawable.bad_box));
			errorMessage += "-Organization was not specified.\n";
		}

		if (!messageEventLocation.equals("")) {
			locationGood = true;
			ETEventLocation.setBackground(getResources().getDrawable(
					R.drawable.cute_text_edit));
		} else {
			ETEventLocation.setBackground(getResources().getDrawable(
					R.drawable.bad_box));
			errorMessage += "-Location of event was not specified.\n";

		}

		if (!messageTags.equals("") && !messageTags.contains(" ")) {
			tagsGood = true;
			ETTages.setBackground(getResources().getDrawable(
					R.drawable.cute_text_edit));
		} else {
			ETTages.setBackground(getResources()
					.getDrawable(R.drawable.bad_box));
			if (messageTags.contains(" ")) {
				errorMessage += "-Your tags contained spaces, please remove them.\n";
			} else {
				errorMessage += "-Your event did not specify any tags.\n";
			}
		}

		if (!messageExtraInfo.equals("")) {
			extraInfoGood = true;
			ETExtraInfo.setBackground(getResources().getDrawable(
					R.drawable.cute_text_edit));
		} else {
			ETExtraInfo.setBackground(getResources().getDrawable(
					R.drawable.bad_box));
			errorMessage += "-You didn't mention anything about your event! How are people suppose to know how great "
					+ "or how fun it's going to be!?\n";
		}
		// Now need to check the start and finish.
		// Make sure all have been set. Make sure event ends AFTER it
		// starts...duh!
		if (startHour != -1 && startMinute != -1) {
			startTimeGood = true;
			BstartTime.setBackground(getResources().getDrawable(
					R.drawable.pretty_button));
		} else {
			BstartTime.setBackground(getResources().getDrawable(
					R.drawable.bad_button));
			errorMessage += "You didn't specify a start time for your event.\n";
		}
		
		if(finishHour != -1 && finishMinute != -1){
			endTimeGood = true;
			BendTime.setBackground(getResources().getDrawable(
					R.drawable.pretty_button));
		} else {
			BendTime.setBackground(getResources().getDrawable(
					R.drawable.bad_button));
			errorMessage += "You didn't specify an end time! If the event doesn't have one, simply put 23:59.\n";
		}
		
		if(startTimeGood && endTimeGood){
			//Check to make sure start time is before end time.
			if(startHour < finishHour){
				startStopGood = true;
				BstartTime.setBackground(getResources().getDrawable(
						R.drawable.pretty_button));
				BendTime.setBackground(getResources().getDrawable(
						R.drawable.pretty_button));
			} else if (startHour == finishHour && startMinute < finishMinute){
				startStopGood = true;
				BstartTime.setBackground(getResources().getDrawable(
						R.drawable.pretty_button));
				BendTime.setBackground(getResources().getDrawable(
						R.drawable.pretty_button));
			} else {
				BstartTime.setBackground(getResources().getDrawable(
						R.drawable.bad_button));
				BendTime.setBackground(getResources().getDrawable(
						R.drawable.bad_button));
				errorMessage += "Your start time is after (or at the same time) as your finish time. Please adjust this " +
						"accordingly.\n";
			}
		}

		// Just make sure date is empty.
		if (eventDay != -1 && eventMonth != -1 && eventYear != -1) {
			dateGood = true;
			BDatePicker.setBackground(getResources().getDrawable(
					R.drawable.pretty_button));
		} else {
			errorMessage += "You didn't specify a date for your event!";
			BDatePicker.setBackground(getResources().getDrawable(
					R.drawable.bad_button));
		}

		// Check if all info was good. If it is, present to user before
		// submitting (one last check)
		// Or if not, tell user what was wrong.
		if (eventNameGood && orgNameGood && locationGood && tagsGood
				&& extraInfoGood && startStopGood && dateGood) {
			infoGood();
		} else {
			infoBad(errorMessage);
		}

	}

	private void infoBad(String errorMessage) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
	    .setTitle("Error!")
	    .setMessage(errorMessage)
	    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete
	        }
	     })
	   
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
	}

	private void infoGood() {
		// TODO Auto-generated method stub

	}

	protected void createEndClock() {
		// TODO Auto-generated method stub
		Calendar mcurrentTime = Calendar.getInstance();
		int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
		int minute = mcurrentTime.get(Calendar.MINUTE);
		TimePickerDialog mTimePicker;
		mTimePicker = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {

					int count = 0;

					public void onTimeSet(TimePicker timePicker,
							int selectedHour, int selectedMinute) {
						if (count == 1) {
							finishHour = selectedHour;
							finishMinute = selectedMinute;
						}
						count++;
					}
				}, hour, minute, true);// Yes 24 hour time
		mTimePicker.setTitle("Select Time");
		mTimePicker.show();
	}

	protected void createStartClock() {
		// TODO Auto-generated method stub
		Calendar mcurrentTime = Calendar.getInstance();
		int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
		int minute = mcurrentTime.get(Calendar.MINUTE);
		TimePickerDialog mTimePicker;
		mTimePicker = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {

					int count = 0;

					public void onTimeSet(TimePicker timePicker,
							int selectedHour, int selectedMinute) {
						if (count == 1) {
							startHour = selectedHour;
							startMinute = selectedMinute;
						}
						count++;
					}

				}, hour, minute, true);// Yes 24 hour time
		mTimePicker.setTitle("Select Time");
		mTimePicker.show();
	}

	protected void debugToast(String message) {
		// TODO Auto-generated method stub
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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
