package com.example.umbcevents;

import java.util.Calendar;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

	private EditText ETEventName;
	private EditText ETOrgName;
	private EditText ETEventLocation;
	private EditText ETTages;
	private EditText ETExtraInfo;
	private Button BstartTime;
	private Button BendTime;
	private Button Bsubmit;
	private Button BDatePicker;
	private String messageEventName;
	private String messageOrgName;
	private String messageEventLocation;
	private String messageTags;
	private String messageExtraInfo;
	// preference member variables
	private String prefOrgName;
	private boolean prefClockType;
	private SharedPreferences prefs ;
	Calendar currentTime;

	int eventDay = -1, eventMonth = -1, eventYear = -1;
	int startHour = -1, startMinute = -1, endHour = -1, endMinute = -1;

	// TODO strip invalid chars
	
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
		currentTime = Calendar.getInstance();
		prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		setPreferences();
		// Set up button listeners....
		BstartTime.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setClockPreferences();
				createStartClock();
			}
		});
		BendTime.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setClockPreferences();
				createEndClock();
			}
		});
		Bsubmit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				checkForm();
			}
		});
		BDatePicker.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				createDatePicker();
			}
		});
	}
	
	/**
	 *  get preferences
	 */
	private void setPreferences(){
		setClockPreferences();
		prefOrgName = prefs.getString("pref_org_name", null);
		if(prefOrgName != null){
			ETOrgName.setText(prefOrgName);
		}
	}
	
	private void setClockPreferences(){
		prefClockType = prefs.getBoolean("pref_clock", false);
	}

	protected void createDatePicker(){
		int day, month, year;
		if (eventDay == -1) {
			day = currentTime.get(Calendar.DAY_OF_MONTH);
		} else {
			day = eventDay;
		}
		if (eventMonth == -1) {
			month = currentTime.get(Calendar.MONTH);
		} else {
			// -1 because the picker moves forward a month every time
			month = eventMonth - 1;
		}
		if (eventYear == -1) {
			year = currentTime.get(Calendar.YEAR);
		} else {
			year = eventYear;
		}
		DatePickerDialog datePicker;
		datePicker = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {

					int counter = 0;

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// TODO why?
						if (counter == 0) {
							eventDay = dayOfMonth;
							eventMonth = monthOfYear + 1;
							eventYear = year;
							BDatePicker.setText(eventMonth + "/" + eventDay + "/" + eventYear);
						}
						counter++;
					}
				}, year, month, day);
		datePicker.setTitle("Set the Date of your Event!");
		datePicker.show();
	}

	/**
	 * Validation of user input
	 */
	protected void checkForm() {
		String errorMessage = "The following fields are invalid:\n";
		String successMessage = "Your form:\n";
		// Need to check all the categories, if they aren't filled in tell user.
		messageEventName = ETEventName.getText().toString();
		messageOrgName = ETOrgName.getText().toString();
		messageEventLocation = ETEventLocation.getText().toString();
		messageTags = ETTages.getText().toString();
		messageExtraInfo = ETExtraInfo.getText().toString();
		
		Drawable badBox = getResources().getDrawable(R.drawable.bad_box);
		Drawable goodBox = getResources().getDrawable(R.drawable.cute_text_edit);
		Drawable badButton = getResources().getDrawable(R.drawable.bad_button);
		Drawable goodButton = getResources().getDrawable(R.drawable.pretty_button);
		
		boolean valid = true, validTimes = true;

		// Make sure five fields are NOT blank.
		// If they are, provide error messages.
		// event name
		if (!messageEventName.equals("")) {
			ETEventName.setBackground(goodBox);
			successMessage += "Event name: " + messageEventName + "\n";
		} else {
			errorMessage += "- Event name was not specified.\n";
			ETEventName.setBackground(badBox);
			valid = false;
		}
		// organization name
		if (!messageOrgName.equals("")) {
			ETOrgName.setBackground(goodBox);
			successMessage += "Organization Name: " + messageOrgName + "\n";
		} else {
			ETOrgName.setBackground(badBox);
			errorMessage += "- Organization was not specified.\n";
			valid = false;
		}
		// location
		if (!messageEventLocation.equals("")) {
			ETEventLocation.setBackground(goodBox);
			successMessage += "Event Location: " + messageEventLocation + "\n";
		} else {
			ETEventLocation.setBackground(badBox);
			errorMessage += "- Location of event was not specified.\n";
			valid = false;
		}
		// tags
		if (!messageTags.equals("")) {
			ETTages.setBackground(goodBox);
			successMessage += "Tags: " + messageTags + "\n";
		} else {
			ETTages.setBackground(badBox);
			errorMessage += "- Your event did not specify any tags.\n";
			valid = false;
		}
		// description
		if (!messageExtraInfo.equals("")) {
			ETExtraInfo.setBackground(goodBox);
			successMessage += "Event Details: " + messageExtraInfo + "\n";
		} else {
			ETExtraInfo.setBackground(badBox);
			errorMessage += "- You didn't add a description for your event.\n";
			valid = false;
		}
		// check the start and finish times
		// Make sure times are set and event ends AFTER it starts
		if (startHour != -1 && startMinute != -1) {
		} else {
			BstartTime.setBackground(badButton);
			errorMessage += "- You didn't specify a start time for your event.\n";
			valid = false;
			validTimes = false;
		}

		if (endHour != -1 && endMinute != -1) {
		} else {
			BendTime.setBackground(badButton);
			errorMessage += "- You didn't specify an end time! If the event doesn't have one, simply put 23:59.\n";
			valid = false;
			validTimes = false;
		}
		if (validTimes) {
			// Check to make sure start time is before end time.
			if ((startHour < endHour) || startHour == endHour && startMinute < endMinute) {
				BstartTime.setBackground(goodButton);
				BendTime.setBackground(goodButton);
				successMessage += "Event Start Time: " + startHour + ":"
						+ startMinute + "\nEvent End Time: " + endHour + ":"
						+ endMinute + "\n";
			} else {
				BstartTime.setBackground(badButton);
				BendTime.setBackground(badButton);
				errorMessage += "Your start time is after (or at the same time) as your finish time. Please adjust this "
						+ "accordingly.\n";
				validTimes = false;
			}
		}
		// date
		if (eventDay != -1 && eventMonth != -1 && eventYear != -1) {
			BDatePicker.setBackground(goodButton);
			successMessage += "Event Date: " + eventMonth + "/" + eventDay
					+ "/" + eventYear;
		} else {
			errorMessage += "You didn't specify a date for your event!";
			BDatePicker.setBackground(badButton);
			valid = false;
		}
		// Check if all info was good. If it is, present to user before
		// submitting (one last check). If not, tell user what was wrong.
		if (valid) {
			infoGood(successMessage);
		} else {
			infoBad(errorMessage);
		}
	}

	private void infoBad(String errorMessage) {
		new AlertDialog.Builder(this)
				.setTitle("Error!")
				.setMessage(errorMessage)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// continue with delete
							}
						})
				.setIcon(android.R.drawable.ic_dialog_alert).show();
	}

	private void infoGood(String successMessage) {
		new AlertDialog.Builder(this)
				.setTitle("One last look!")
				.setMessage(
						"If everything looks ok, click \"ok\" and we'll submit it! "
								+ "If not, simply click \"cancel\" and you can go back and modify your event.\n\n"
								+ successMessage)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// continue with submission
								submitForm();
							}
						})
				.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// go back to edit (do nothing)
							}
						}).setIcon(android.R.drawable.ic_menu_info_details)
				.show();
	}

	private void submitForm() {
		debugToast("Submitting form...");
		String start_time = eventYear + "-" + eventMonth + "-" + eventDay + " "
				+ startHour + ":" + startMinute + ":00";
		String end_time = eventYear + "-" + eventMonth + "-" + eventDay + " "
				+ endHour + ":" + endMinute + ":00";
		new InsertTask(getBaseContext()).execute(messageOrgName,
				messageEventName, messageEventLocation, start_time, end_time,
				messageTags, messageExtraInfo);
		// exit submission form
		finish();
	}
	
	private void createStartClock() {
		// select default start time
		int hour, minute;
		if (startHour == -1) {
			hour = currentTime.get(Calendar.HOUR_OF_DAY);
		} else {
			hour = startHour;
		}
		if (startMinute == -1) {
			minute = 0;
		} else {
			minute = startMinute;
		}
		TimePickerDialog mTimePicker;
		mTimePicker = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {
					public void onTimeSet(TimePicker timePicker,
							int selectedHour, int selectedMinute) {
						startHour = selectedHour;
						startMinute = selectedMinute;
						BstartTime.setText(formatTime(selectedHour, selectedMinute));
					}
				}, hour, minute, prefClockType); // true for 24 hour time
		mTimePicker.setTitle("Select Starting Time");
		mTimePicker.show();
	}
	
	// TODO pick end time based on start time
	private void createEndClock() {
		int hour, minute;
		if (endHour == -1) {
			hour = currentTime.get(Calendar.HOUR_OF_DAY);
		} else {
			hour = endHour;
		}
		if (endMinute == -1) {
			// minute = currentTime.get(Calendar.MINUTE);
			minute = 0;
		} else {
			minute = endMinute;
		}
		TimePickerDialog mTimePicker;
		mTimePicker = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {
					public void onTimeSet(TimePicker timePicker,
							int selectedHour, int selectedMinute) {
						endHour = selectedHour;
						endMinute = selectedMinute;
						BendTime.setText(formatTime(selectedHour, selectedMinute));
					}
				}, hour, minute, prefClockType); // true for 24 hour time
		mTimePicker.setTitle("Select Ending Time");
		mTimePicker.show();
	}
	
	/**
	 * Turns an hour and minute into a human-readable time.<br>
	 * Ex: formatTime(13, 30) = "1:30 PM"
	 * @param hour - the hour of the time
	 * @param min - the minute of the time
	 * @return a string representing the given time
	 * @warning undefined behavior for hour > 23, min > 60
	 */
	private String formatTime(int hour, int min) {
		String str = "";
		if(prefClockType){
			// 24 hour clock
			str +=  hour;
			str += ":";
			if (min < 10) {
				str += "0";
			}
			str += min;
		} else{
			// 12 hour clock
			str += (hour > 12) ? hour - 12 : hour;
			str += ":";
			if (min < 10) {
				str += "0";
			}
			str += min;
			str += (hour < 12) ? " AM" : " PM";
		}
		return str;
	}

	private void debugToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.organization_form, menu);
		return true;
	}

	/**
	 * Handle action bar item clicks here. The action bar will automatically
	 * handle clicks on the Home/Up button, so long as you specify a parent
	 * activity in AndroidManifest.xml.
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
	
	private void goToSettings() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}
}