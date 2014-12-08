package proj2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;


public class EventTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		EventStruct myEvent = new EventStruct();
		myEvent.setTags("basketball, retrievers, final four,, women, march madness");
		//System.out.println(myEvent);
		//myEvent.calcRelevance("basketball, retrievers, final four, women, march madness, ,");
		
		EventStruct nullGame = new EventStruct(0, null, null, null,
				null,
				null,
				null, null);
		//System.out.println(nullGame);
		
		EventStruct basketballGame = new EventStruct(10001, "Athletics", "Women's Basketball Game", "RAC",
				"women's basketball, basketball, RAC, Hartford, retrievers, free",
				"Watch the Women's basketball team play University of Hartford",
				new GregorianCalendar(2014, 11, 4, 5, 30, 0), new GregorianCalendar(2014, 11, 4, 7,30));
		
		EventStruct hockeyGame = new EventStruct(10002, "Athletics", "Hockey Game", "Catonsville Ice Arena",
				"hockey, club, UMBC, Temple, off campus, winning streak, retrievers",
				"Watch the UMBC hockey team take on temple at nearby Catonsville Ice Rink", 
				new GregorianCalendar(2014, 11, 29, 4, 30, 0), new GregorianCalendar(2014, 11, 29, 5, 30));
		
		EventStruct lacrossGame = new EventStruct(10001, "Athletics", "Men's Lacross Game", "Stadium",
				"lacross, men's lacross, College Park, stadium, final four, retrievers, free",
				"Watch the Men's lacross team take on old rivals College Park for a shot at the championship",
				new GregorianCalendar(2015, 3, 6, 5, 30, 0), new GregorianCalendar(2015, 3, 6, 7,30));
		
		EventStruct soccerGame = new EventStruct(10001, "Athletics", "Men's Soccer Game", "Stadium",
				"Soccer, mens soccer, final four, retrievers, free",
				"Watch the Men's Soccer team in the semi-finals",
				new GregorianCalendar(2014, 11, 1, 8, 30, 0), new GregorianCalendar(2014, 11, 1, 11,00));
		
		
		EventTest.myEvents.add(basketballGame);
		EventTest.myEvents.add(hockeyGame);
		EventTest.myEvents.add(lacrossGame);
		EventTest.myEvents.add(soccerGame);
		
		/*
		for(int i = 0; i < myEvents.size(); i++){
			System.out.println(myEvents.get(i) + "\n\n");
		}
		*/
		
		
		ArrayList<EventStruct> searchResults = searchByTags("men, hockey, retrievers");
		System.out.println("Searching for tags: men, hockey, retrievers");
		
		for(int i = 0; i < searchResults.size(); i++){
			System.out.println(searchResults.get(i));
			//System.out.println("Relevance: " + searchResults.get(i).getRelevance() + "\n");
		}
		System.out.println("------------------------------------------------------------------------");
		
		searchResults = searchByTags("men's lacross game, final four, free,,,,,, on campus, Stadium");
		System.out.println("Searching for tags: men's lacross game, final four, free, on campus, Stadium");
		for(int i = 0; i < searchResults.size(); i++){
			System.out.println(searchResults.get(i) + "\n");
			//System.out.println("Relevance: " + searchResults.get(i).getRelevance() + "\n");
		}
		System.out.println("------------------------------------------------------------------------");
		
		
		System.out.println("After December 6th:");
		ArrayList<EventStruct> afterDecSix = new ArrayList<EventStruct>();
		afterDecSix = filterByDate(myEvents, new GregorianCalendar(2014, 11, 6, 00,00));
		for(int i = 0; i < afterDecSix.size(); i++){
			System.out.println(afterDecSix.get(i) + "\n");
			//System.out.println("Relevance: " + searchResults.get(i).getRelevance() + "\n");
		}
		System.out.println("------------------------------------------------------------------------");
		
		
		ArrayList<EventStruct> inDecember = new ArrayList<EventStruct>();
		inDecember = filterByDate(myEvents, new GregorianCalendar(2014, 11, 1, 00,00),
				new GregorianCalendar(2014, 11, 31, 23, 59));
		for(int i = 0; i < inDecember.size(); i++){
			System.out.println(inDecember.get(i) + "\n");
			//System.out.println("Relevance: " + searchResults.get(i).getRelevance() + "\n");
		}
		System.out.println("------------------------------------------------------------------------");
		
		
		System.out.println("All events sorted by date:");
		sortByDate(myEvents);
		for(int i = 0; i < myEvents.size(); i++){
			System.out.println(myEvents.get(i) + "\n");
		}
		System.out.println("------------------------------------------------------------------------");
		
		
		
		//System.out.println(soccerGame);
		//soccerGame.calcRelevance("basketball, retrievers, final four, women, march madness, ,");
		
		//soccerGame.calcRelevance("Mens Soccer game, athletics, mens, retriever fever, stadium, final four, free");
		
		/*
		EventStruct myEvent2 = new EventStruct();
		myEvent2.setTags("");
		System.out.println(myEvent2);
		myEvent2.calcRelevance("basketball, retrievers, final four, women, march madness");
		
		EventStruct myEvent3 = new EventStruct();
		myEvent3.setTags("tag1,   tag2, tag3,tag4, tag5,,    tag6");
		System.out.println(myEvent3);
		myEvent3.calcRelevance("basketball, retrievers, final four, women, march madness");
	*/
	
		
		
		
	}
	
	private static ArrayList<EventStruct> myEvents = new ArrayList<EventStruct>();
	
	
	//Search all events and return matched ones in order of relevance
	public static ArrayList<EventStruct> searchByTags(String searchTags){
		ArrayList<EventStruct> matchedEvents = new ArrayList<EventStruct>();

		//For each recorded event
		for(int i = 0; i < myEvents.size(); i++){
			int eventRelevance = myEvents.get(i).calcRelevance(searchTags);
			// if relevant, add after more relevant entries
			if(eventRelevance > 0){
				int n = 0;
				boolean added = false;
				
				
				while(added == false){
					//if at end of list, (or list is empty) add event and break loop
					if(n == matchedEvents.size()){
						matchedEvents.add(myEvents.get(i));
						added = true;
					} else if(matchedEvents.get(n).getRelevance() < eventRelevance){
						//current event is more relevant than already matched one, add and break loop
						matchedEvents.add(n, myEvents.get(i));
						added = true;
					}
					
					//if current event less relevant, move to next index
					n++;
				} // end while
								
			} // end add event
			
		} // end for
		
		return matchedEvents;	
	}
	

	//Overloaded Static Method that will filter out matches that happened before the start date
	public static ArrayList<EventStruct> filterByDate(ArrayList<EventStruct> unfilteredEvents, 
			GregorianCalendar startAfter){
		ArrayList<EventStruct> filteredEvents = new ArrayList<EventStruct>();
		for(int i = 0; i < unfilteredEvents.size(); i++){
			if(unfilteredEvents.get(i).getStart().compareTo(startAfter) >= 0){
				filteredEvents.add(unfilteredEvents.get(i));
			}	
		}
		
		return filteredEvents;
	}
	//Overloaded Static Method that will filter out matches before start date and after the end date
	public static ArrayList<EventStruct> filterByDate(ArrayList<EventStruct> unfilteredEvents,
			GregorianCalendar startAfter, GregorianCalendar endBefore){
		ArrayList<EventStruct> filteredEvents = new ArrayList<EventStruct>();
		for(int i = 0; i < unfilteredEvents.size(); i++){
			if((unfilteredEvents.get(i).getStart().compareTo(startAfter) >= 0) &&
					unfilteredEvents.get(i).getEventEnd().compareTo(endBefore) <= 0){
				filteredEvents.add(unfilteredEvents.get(i));
			}	
		}
		
		return filteredEvents;
	}
	
	
	//Sorts and returns the unsorted Event list.  Primary usage: myEvents = sortByDate(myEvents)
	public static ArrayList<EventStruct> sortByDate(ArrayList<EventStruct> unsortedEvents){
		Collections.sort(unsortedEvents);
		return unsortedEvents;
	}
	
	
}



