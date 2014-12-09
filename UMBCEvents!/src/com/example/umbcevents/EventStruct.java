package com.example.umbcevents;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;



public class EventStruct implements Comparable<EventStruct>{
	
//	private int id; //event number in DB
	private String orgName; // name of the organization hosting the event
	private String eventName; // name of the Event
	private String eventLoc; // where the event is located 
	private String tags; // all the tags associated with the event separated by commas
	private ArrayList<String> tagsArray; //a list of the tags as individual strings
	private String eventDesc; // a description of the event for users
	private Calendar eventStart; // when the event starts
	private Calendar eventEnd; // when the event ends
	private int relevance; // how relevant the event is to the LAST SEARCH PERFORMED ON IT
	
	/* *************************************************************/
	// Getters/Setters
//	public int getID(){
//		return this.id;
//	}
//	public void setID(int idnum){
//		this.id = idnum;
//	}
	
	public String getOrg(){
		return this.orgName;
	}
	public void setOrg(String org){
		this.orgName = org;
	}
	
	public String getEventName(){
		return this.eventName;
	}
	public void setEventName(String eventName){
		this.eventName = eventName;
	}
	
	public String getLocation(){
		return this.eventLoc;
	}
	public void setLocation(String location){
		this.eventLoc = location;
	}
	
	public String getTags(){
		return this.tags;
	}
	public void setTags(String tags){
		this.tags = tags;
		this.tagsArray = this.parseTags(tags);
	}
	
	public ArrayList<String> getTagsArray(){
		return this.tagsArray;
	}
	public void setTagsArray(ArrayList<String> tagsArray){
		this.tagsArray = tagsArray;
	}
	
	public String getDescription(){
		return this.eventDesc;
	}
	public void setDesc(String descrip){
		this.eventDesc = descrip;
	}
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm");
	
	public Calendar getStart(){
		return this.eventStart;
	}
	public void setStart(Calendar startTime){
		this.eventStart = startTime;
	}
	public String getStartDate(){
		return "" + eventStart.get(Calendar.YEAR) + eventStart.get(Calendar.MONTH + eventStart.get(Calendar.DAY_OF_MONTH));
	}
	public String getStartTime(){
		return "" + eventStart.get(Calendar.HOUR_OF_DAY) + ":" + eventStart.get(Calendar.MINUTE);
	}
	
	public int compareTo(EventStruct otherEvent){
		return (this.getStart().compareTo(otherEvent.getStart()));
	}
	
	
	public Calendar getEventEnd(){
		return this.eventEnd;
	}
	public void setEnd(Calendar endTime){
		this.eventEnd = endTime;
	}
	public String getEndDate(){
		return "" + eventEnd.get(Calendar.YEAR) + eventEnd.get(Calendar.MONTH + eventEnd.get(Calendar.DAY_OF_MONTH));
	}
	public String getEndTime(){
		return "" + eventEnd.get(Calendar.HOUR_OF_DAY) + eventEnd.get(Calendar.MINUTE);
	}
	
	
	
	public int getRelevance(){
		return this.relevance;
	}
	public void setRelevance(int relevance){
		this.relevance = relevance;
	}
	
	
	/* *********************** CONSTRUCTORS ************************/
	
	// Full Constructor
	public EventStruct( String org, String evName, String loc, String tags, String desc, Calendar starting, Calendar ending){
		this.relevance = 0;
//		this.id = id;
		this.orgName = org;
		if(orgName == null){
			this.orgName = "UMBC";
		}
		this.eventName = evName;
		if(this.eventName == null){
			this.eventName = "Event";
		}
		this.eventLoc = loc;
		if(this.eventLoc == null){
			this.eventLoc = "Campus";
		}
		this.tags = tags;
		if(this.tags == null){
			this.tags = "";
		}
		this.tagsArray = this.parseTags(this.tags);
		this.eventDesc = desc;
		if(this.eventDesc == null){
			this.eventDesc = "";
		}
		this.eventStart = starting;
		if(this.eventStart == null){
			this.eventStart = new GregorianCalendar(1966, 8, 19, 7,00);
		}
		this.eventEnd = ending;
		if(this.eventEnd == null){
			this.eventEnd = new GregorianCalendar(eventStart.get(Calendar.YEAR), eventStart.get(Calendar.MONTH),
					eventStart.get(Calendar.DAY_OF_MONTH), eventStart.get(Calendar.HOUR_OF_DAY) + 1, eventStart.get(Calendar.MINUTE));
		}
	}
	
	// Copy Constructor
	public EventStruct(EventStruct otherEvent){
		this.relevance = otherEvent.getRelevance();
//		this.id = otherEvent.getID();
		this.orgName = otherEvent.getOrg();
		this.eventName = otherEvent.getEventName();
		this.eventLoc = otherEvent.getLocation();
		this.setTags(otherEvent.getTags());
		this.eventDesc = otherEvent.getDescription();
		this.eventStart = otherEvent.getStart();
		this.eventEnd = otherEvent.getEventEnd();
	}
	
	// Empty Constructor
	public EventStruct(){
		this.relevance = 0;
//		this.id = 0;
		this.orgName = "";
		this.eventName = "";
		this.eventLoc = "";
		this.setTags("");
		this.eventDesc = "";
		this.eventStart = new GregorianCalendar(0000, 0, 0, 0, 0, 0);
		this.eventEnd = new GregorianCalendar(0000, 0, 0, 0, 0, 0);
	}
	
	/* ********************* SEARCH METHODS ************************/
	
	// Separates a long string into an ArrayList of individual tags
	public ArrayList<String> parseTags(String longTags){
		ArrayList<String> tagList = new ArrayList<String>();
		// String[] arr = longTags.split(",");
		String[] arr = splitUsingTokenizer(longTags, ", ");
		for(String s : arr){
			String temp = s.trim();
			if(temp.length() > 0){
				tagList.add(temp);
			}
		}
		return(tagList);
	
	}
	
	/**
	 * Splits a string on multiple char delimiters
	 * @param subject
	 * @param delimiters
	 * @return the resulting String[] from splitting and re-joining "subject" on each char of
	 *         "delimiters".
	 */
	public static final String[] splitUsingTokenizer(String subject, String delimiters){
		StringTokenizer tokens = new StringTokenizer(subject, delimiters);
		ArrayList<String> arr = new ArrayList<String>(subject.length());
		while(tokens.hasMoreTokens()){
			arr.add(tokens.nextToken());
		}
		return arr.toArray(new String[0]);
	}
	
	//Check the relevance of an Event vs a given string of tags
	public int calcRelevance(String testTags){
		ArrayList<String> testTagArray = this.parseTags(testTags);
		
		int relevance = 0;
		
		for(int i = 0; i<testTagArray.size(); i++){
			ArrayList<String> tempTags = this.parseTags(this.getEventName());
			if(tempTags.contains(testTagArray.get(i))){
				relevance+= 5;
				//System.out.println("Name matches " + testTagArray.get(i));
				
			}
			tempTags = this.parseTags(this.getOrg());
			if(tempTags.contains(testTagArray.get(i))){
				relevance += 3;
				//System.out.println("Org matches " + testTagArray.get(i));
				
			}
			tempTags = this.parseTags(this.getLocation());
			if(tempTags.contains(testTagArray.get(i))){
				relevance +=2;
				//System.out.println("Location matches " + testTagArray.get(i));
				
			}
			
			if(this.tagsArray.contains(testTagArray.get(i))){
				//System.out.println("Contains " + testTagArray.get(i));
				relevance++;
			}
			
			
		}
		
		//System.out.println("Relevance: " + relevance);
		this.relevance = relevance;
		return relevance;
	}
	
	
	
	/* *************************************************************/
	
	//toString method
	public String toString(){
		String eventString = "";
		
		eventString += this.getEventName() + ", hosted by " + this.getOrg() +"\n";
		eventString += "From " + this.sdf.format(eventStart.getTime()) + 
				" until " + this.sdf.format(eventEnd.getTime()) + "\n";
		eventString += "Located at: " + this.getLocation() + "\n";
		eventString += "\"" + this.getDescription() + "\"\n";
//		eventString += "Tags:         ";
//		for(int i = 0; i < tagsArray.size(); i ++){
//			eventString += tagsArray.get(i);
//			if(i < (tagsArray.size()-1)){
//				eventString += ", ";
//			}
//		}
		
		
		return eventString;
	}

}
