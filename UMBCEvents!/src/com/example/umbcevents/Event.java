package com.example.umbcevents;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class Event implements Comparable<Event> {

	/* *********************** MEMBER VARIABLES *********************** */
	
	/**
	 * name of the organization hosting the event
	 */
	private String org;

	/**
	 * name of the Event
	 */
	private String eventName;

	/**
	 * where the event is located
	 */
	private String location;

	/**
	 * all the tags associated with the event separated by commas
	 */
	private String tags;

	/**
	 * a list of the tags as individual strings
	 */
	private ArrayList<String> tagsArray;

	/**
	 * a description of the event for users
	 */
	private String description;

	/**
	 * when the event starts
	 */
	private Calendar startTime;

	/**
	 * when the event ends
	 */
	private Calendar endTime;

	/**
	 * how relevant the event is to the LAST SEARCH PERFORMED ON IT
	 */
	private int relevance;

	/**
	 * The date format desired for the start and end times
	 */
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm");

	/* *********************** CONSTRUCTORS *********************** */

	// Full Constructor
	public Event(String org, String evName, String loc, String tags,
			String desc, Calendar starting, Calendar ending) {
		this.relevance = 0;
		this.org = org;
		this.eventName = evName;
		this.location = loc;
		this.tags = tags;
		this.tagsArray = Event.parseTags(this.tags);
		this.description = desc;
		this.startTime = starting;
		this.endTime = ending;
		// set default values
		if (this.org == null) {
			this.org = "UMBC";
		}
		if (this.eventName == null) {
			this.eventName = "Event";
		}
		if (this.location == null) {
			this.location = "Campus";
		}
		if (this.tags == null) {
			this.tags = "";
		}
		if (this.description == null) {
			this.description = "";
		}
		if (this.startTime == null) {
			this.startTime = new GregorianCalendar(1966, 8, 19, 7, 00);
		}
		if (this.endTime == null) {
			this.endTime = new GregorianCalendar(
					startTime.get(Calendar.YEAR),
					startTime.get(Calendar.MONTH),
					startTime.get(Calendar.DAY_OF_MONTH),
					startTime.get(Calendar.HOUR_OF_DAY) + 1,
					startTime.get(Calendar.MINUTE));
		}
	}

	// Copy Constructor
	public Event(Event otherEvent) {
		this.relevance = otherEvent.getRelevance();
		this.org = otherEvent.getOrg();
		this.eventName = otherEvent.getEventName();
		this.location = otherEvent.getLocation();
		this.setTags(otherEvent.getTags());
		this.description = otherEvent.getDescription();
		this.startTime = otherEvent.getStart();
		this.endTime = otherEvent.getEventEnd();
	}

	// Empty Constructor
	public Event() {
		this.relevance = 0;
		this.org = "";
		this.eventName = "";
		this.location = "";
		this.setTags("");
		this.description = "";
		this.startTime = new GregorianCalendar(0000, 0, 0, 0, 0, 0);
		this.endTime = new GregorianCalendar(0000, 0, 0, 0, 0, 0);
	}
	
	/* ********************* SEARCH METHODS *********************** */
	
	public int compareTo(Event otherEvent) {
		return (this.getStart().compareTo(otherEvent.getStart()));
	}

	/**
	 * Separates a string of tags into an ArrayList of individual tags
	 * @param longTags
	 * @return
	 */
	private static ArrayList<String> parseTags(String allTags) {
		ArrayList<String> tagList = new ArrayList<String>();
		String[] arr = splitUsingTokenizer(allTags, ", ");
		for (String s : arr) {
			String temp = s.trim();
			if (temp.length() > 0) {
				tagList.add(temp);
			}
		}
		return (tagList);
	}

	/**
	 * Check the relevance of this event against the given string of tags
	 * @param testTags - the string of unseparated tags to check
	 * @return
	 */
	public int calcRelevance(String testTags) {
		ArrayList<String> testTagArray = Event.parseTags(testTags);
		ArrayList<String> eventNameTags = Event.parseTags(eventName);
		ArrayList<String> orgNameTags = Event.parseTags(org);
		ArrayList<String> locationTags = Event.parseTags(location);
		int relevance = 0;
		for (String currentTag : testTagArray) {
			// if the tag matches the event name
			if (eventNameTags.contains(currentTag)) {
				relevance += 5;
			}
			// if the tag matches the organization
			if (orgNameTags.contains(currentTag)) {
				relevance += 3;
			}
			// if tag matches the location
			if (locationTags.contains(currentTag)) {
				relevance += 2;
			}
			// if there is a matching tag
			if (this.tagsArray.contains(currentTag)) {
				relevance++;
			}
		}
		this.relevance = relevance;
		return relevance;
	}
	
	//Search all events and return matched ones in order of relevance
	public static ArrayList<Event> searchByTags(String searchTags,
			ArrayList<Event> events) {
		ArrayList<Event> matchedEvents = new ArrayList<Event>();
		// For each recorded event
		for (Event evt : events) {
			int eventRelevance = evt.calcRelevance(searchTags);
			// if relevant, add after more relevant entries
			if (eventRelevance > 0) {
				int n = 0;
				boolean added = false;
				while (!added) {
					// if at end of list, (or list is empty) add event and break
					// loop
					if (n == matchedEvents.size()) {
						matchedEvents.add(evt);
						added = true;
					} else if (matchedEvents.get(n).getRelevance() < eventRelevance) {
						// current event is more relevant than already matched
						// one, add and break loop
						matchedEvents.add(n, evt);
						added = true;
					}
					// if current event less relevant, move to next index
					n++;
				} // end while
			} // end add event
		} // end for
		return matchedEvents;
	}

	/**
	 * Sorts and returns the unsorted Event list. Primary usage: myEvents =
	 * sortByDate(myEvents)
	 * @param events
	 * @return
	 */
	public static ArrayList<Event> sortByDate(ArrayList<Event> events) {
		Collections.sort(events);
		return events;
	}

	/**
	 * Overloaded Static Method that will filter out matches that happened
	 * before the start date
	 * @param events
	 * @param startAfter
	 * @return
	 */
	public static ArrayList<Event> filterByDate(ArrayList<Event> events,
			GregorianCalendar startAfter) {
		ArrayList<Event> filteredEvents = new ArrayList<Event>(events.size());
		for (Event evt : events) {
			if (evt.getStart().compareTo(startAfter) >= 0) {
				filteredEvents.add(evt);
			}
		}
		return filteredEvents;
	}

	/**
	 * Overloaded Static method that will filter out matches before start date
	 * and after the end date
	 * @param events
	 * @param startAfter
	 * @param endBefore
	 * @return
	 */
	public static ArrayList<Event> filterByDate(ArrayList<Event> events,
			GregorianCalendar startAfter, GregorianCalendar endBefore) {
		ArrayList<Event> filteredEvents = new ArrayList<Event>(events.size());
		for (Event evt : events) {
			if ((evt.getStart().compareTo(startAfter) >= 0)
					&& evt.getEventEnd().compareTo(endBefore) <= 0) {
				filteredEvents.add(evt);
			}
		}
		return filteredEvents;
	}

	/* ******************** Utility Methods ******************** */

	/**
	 *  toString method for Event object
	 *  String.format is slow so use "+"
	 */
	public String toString() {
		return eventName + " hosted by " + org + "\nFrom "
				+ sdf.format(startTime.getTime()) + " to "
				+ sdf.format(endTime.getTime()) + "\nLocated at: " + location
				+ "\n\"" + description + "\"\n";
	}
	
	/**
	 * Splits a string on multiple char delimiters
	 * @param subject
	 * @param delimiters
	 * @return the resulting String[] from splitting and re-joining "subject" on
	 *         each char of "delimiters".
	 */
	private static final String[] splitUsingTokenizer(String subject,
			String delimiters) {
		StringTokenizer tokens = new StringTokenizer(subject, delimiters);
		ArrayList<String> arr = new ArrayList<String>(subject.length());
		while (tokens.hasMoreTokens()) {
			arr.add(tokens.nextToken());
		}
		return arr.toArray(new String[0]);
	}
	
	/* ******************** Getters/Setters ******************** */

	public String getOrg() {
		return this.org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getEventName() {
		return this.eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTags() {
		return this.tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
		this.tagsArray = Event.parseTags(tags);
	}

	public ArrayList<String> getTagsArray() {
		return this.tagsArray;
	}

	public void setTagsArray(ArrayList<String> tagsArray) {
		this.tagsArray = tagsArray;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDesc(String descrip) {
		this.description = descrip;
	}

	public Calendar getStart() {
		return this.startTime;
	}

	public void setStart(Calendar startTime) {
		this.startTime = startTime;
	}

	public String getStartDate() {
		return ""
				+ startTime.get(Calendar.YEAR)
				+ startTime.get(Calendar.MONTH
						+ startTime.get(Calendar.DAY_OF_MONTH));
	}

	public String getStartTime() {
		return "" + startTime.get(Calendar.HOUR_OF_DAY) + ":"
				+ startTime.get(Calendar.MINUTE);
	}
	
	public Calendar getEventEnd() {
		return this.endTime;
	}

	public void setEnd(Calendar endTime) {
		this.endTime = endTime;
	}

	public String getEndDate() {
		return ""
				+ endTime.get(Calendar.YEAR)
				+ endTime.get(Calendar.MONTH
						+ endTime.get(Calendar.DAY_OF_MONTH));
	}

	public String getEndTime() {
		return "" + endTime.get(Calendar.HOUR_OF_DAY)
				+ endTime.get(Calendar.MINUTE);
	}

	public int getRelevance() {
		return this.relevance;
	}

	public void setRelevance(int relevance) {
		this.relevance = relevance;
	}
}
