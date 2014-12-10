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

		int day;
		if (eventDay == -1) {
			day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
		} else {
			day = eventDay;
		}
		int month;
		if (eventMonth == -1) {
			month = mcurrentTime.get(Calendar.MONTH);
		} else {
			month = eventMonth;
		}
		int year;
		if (eventYear == -1) {
			year = mcurrentTime.get(Calendar.YEAR);
		} else {
			year = eventYear;
		}

		DatePickerDialog mDatePicker;
		mDatePicker = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {

					int counter = 0;

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						if (counter == 0) {
							eventDay = dayOfMonth;
							eventMonth = monthOfYear + 1;
							eventYear = year;
							BDatePicker.setText(eventMonth + "/" + eventDay + "/" + eventYear);
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
		String successMessage = "Your form:\n";
		// Need to check all the categories, if they aren't filled in tell user.
		messageEventName = ETEventName.getText().toString();
		messageOrgName = ETOrgName.getText().toString();
		messageEventLocation = ETEventLocation.getText().toString();
		messageTags = ETTages.getText().toString();
		messageExtraInfo = ETExtraInfo.getText().toString();

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
			successMessage += "Event name: " + messageEventName + "\n";
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
			successMessage += "Organization Name: " + messageOrgName + "\n";
		} else {
			ETOrgName.setBackground(getResources().getDrawable(
					R.drawable.bad_box));
			errorMessage += "-Organization was not specified.\n";
		}

		if (!messageEventLocation.equals("")) {
			locationGood = true;
			ETEventLocation.setBackground(getResources().getDrawable(
					R.drawable.cute_text_edit));
			successMessage += "Event Location: " + messageEventLocation + "\n";
		} else {
			ETEventLocation.setBackground(getResources().getDrawable(
					R.drawable.bad_box));
			errorMessage += "-Location of event was not specified.\n";

		}

		if (!messageTags.equals("")) {
			tagsGood = true;
			ETTages.setBackground(getResources().getDrawable(
					R.drawable.cute_text_edit));
			successMessage += "Tags: " + messageTags + "\n";
		} else {
			ETTages.setBackground(getResources()
					.getDrawable(R.drawable.bad_box));

			errorMessage += "-Your event did not specify any tags.\n";

		}

		if (!messageExtraInfo.equals("")) {
			extraInfoGood = true;
			ETExtraInfo.setBackground(getResources().getDrawable(
					R.drawable.cute_text_edit));
			successMessage += "Event Details: " + messageExtraInfo + "\n";
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

		if (finishHour != -1 && finishMinute != -1) {
			endTimeGood = true;
			BendTime.setBackground(getResources().getDrawable(
					R.drawable.pretty_button));
		} else {
			BendTime.setBackground(getResources().getDrawable(
					R.drawable.bad_button));
			errorMessage += "You didn't specify an end time! If the event doesn't have one, simply put 23:59.\n";
		}

		if (startTimeGood && endTimeGood) {
			// Check to make sure start time is before end time.
			if (startHour < finishHour) {
				startStopGood = true;
				BstartTime.setBackground(getResources().getDrawable(
						R.drawable.pretty_button));
				BendTime.setBackground(getResources().getDrawable(
						R.drawable.pretty_button));
				successMessage += "Event Start Time: " + startHour + ":"
						+ startMinute + "\nEvent End Time: " + finishHour + ":"
						+ finishMinute + "\n";
			} else if (startHour == finishHour && startMinute < finishMinute) {
				startStopGood = true;
				BstartTime.setBackground(getResources().getDrawable(
						R.drawable.pretty_button));
				BendTime.setBackground(getResources().getDrawable(
						R.drawable.pretty_button));
				successMessage += "Event Start Time: " + startHour + ":"
						+ startMinute + "\nEvent End Time: " + finishHour + ":"
						+ finishMinute + "\n";
			} else {
				BstartTime.setBackground(getResources().getDrawable(
						R.drawable.bad_button));
				BendTime.setBackground(getResources().getDrawable(
						R.drawable.bad_button));
				errorMessage += "Your start time is after (or at the same time) as your finish time. Please adjust this "
						+ "accordingly.\n";
			}
		}

		// Just make sure date is empty.
		if (eventDay != -1 && eventMonth != -1 && eventYear != -1) {
			dateGood = true;
			BDatePicker.setBackground(getResources().getDrawable(
					R.drawable.pretty_button));
			successMessage += "Event Date: " + eventMonth + "/" + eventDay
					+ "/" + eventYear + "\n";
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
			infoGood(successMessage);
		} else {
			infoBad(errorMessage);
		}

	}

	private void infoBad(String errorMessage) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
								// continue with delete
								submitForm();
							}
						})

				.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// continue with delete yeah
							}
						}).setIcon(android.R.drawable.ic_menu_info_details)
				.show();
	}

	protected void submitForm() {
		// TODO Auto-generated method stub
		debugToast("Submitting form");

		String start_time = eventYear + "-" + eventMonth + "-" + eventDay + " "
				+ startHour + ":" + startMinute + ":00";
		String end_time = eventYear + "-" + eventMonth + "-" + eventDay + " "
				+ finishHour + ":" + finishMinute + ":00";

		new InsertTask(getBaseContext()).execute(messageOrgName,
				messageEventName, messageEventLocation, start_time, end_time,
				messageTags, messageExtraInfo);
		finish();

	}

	protected void createEndClock() {
		// TODO Auto-generated method stub
		Calendar mcurrentTime = Calendar.getInstance();
		int hour;
		if (finishHour == -1) {
			hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
		} else {
			hour = finishHour;
		}
		int minute;
		if (finishMinute == -1) {
			minute = mcurrentTime.get(Calendar.MINUTE);
		} else {
			minute = finishMinute;
		}
		TimePickerDialog mTimePicker;
		mTimePicker = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {

					int count = 0;

					public void onTimeSet(TimePicker timePicker,
							int selectedHour, int selectedMinute) {
						if (count == 0) {
							finishHour = selectedHour;
							finishMinute = selectedMinute;
							String newTime = selectedHour + ":";
							if(selectedMinute == 0) {newTime += "00";}
							else {newTime += selectedMinute;}
							
							BendTime.setText(newTime);
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
		int hour;
		if (startHour == -1) {
			hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
		} else {
			hour = startHour;
		}
		int minute;
		if (startMinute == -1) {
			minute = mcurrentTime.get(Calendar.MINUTE);
		} else {
			minute = startMinute;
		}
		TimePickerDialog mTimePicker;
		mTimePicker = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {

					int count = 0;

					public void onTimeSet(TimePicker timePicker,
							int selectedHour, int selectedMinute) {
						if (count == 0) {
							startHour = selectedHour;
							startMinute = selectedMinute;
							String newTime = selectedHour + ":";
							if(selectedMinute == 0) {newTime += "00";}
							else {newTime += selectedMinute;}
							
							BstartTime.setText(newTime);
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
