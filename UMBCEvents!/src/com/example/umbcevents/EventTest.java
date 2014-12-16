package com.example.umbcevents;

//import java.util.ArrayList;
//import java.util.GregorianCalendar;

public class EventTest {
	
	/*
	private static ArrayList<Event> myEvents = new ArrayList<Event>();
	
	public static void main(String[] args) {
		Event myEvent = new Event();
		myEvent.setTags("basketball, retrievers, final four,, women, march madness");
		Event basketballGame = new Event(
				"Athletics",
				"Women's Basketball Game",
				"RAC",
				"women's basketball, basketball, RAC, Hartford, retrievers, free",
				"Watch the Women's basketball team play University of Hartford",
				new GregorianCalendar(2014, 11, 4, 5, 30, 0),
				new GregorianCalendar(2014, 11, 4, 7, 30));
		Event hockeyGame = new Event(
				"Athletics",
				"Hockey Game",
				"Catonsville Ice Arena",
				"hockey, club, UMBC, Temple, off campus, winning streak, retrievers",
				"Watch the UMBC hockey team take on temple at nearby Catonsville Ice Rink",
				new GregorianCalendar(2014, 11, 29, 4, 30, 0),
				new GregorianCalendar(2014, 11, 29, 5, 30));
		Event lacrossGame = new Event(
				"Athletics",
				"Men's Lacross Game",
				"Stadium",
				"lacross, men's lacross, College Park, stadium, final four, retrievers, free",
				"Watch the Men's lacross team take on old rivals College Park for a shot at the championship",
				new GregorianCalendar(2015, 3, 6, 5, 30, 0),
				new GregorianCalendar(2015, 3, 6, 7, 30));
		Event soccerGame = new Event("Athletics",
				"Men's Soccer Game", "Stadium",
				"Soccer, mens soccer, final four, retrievers, free",
				"Watch the Men's Soccer team in the semi-finals",
				new GregorianCalendar(2014, 11, 1, 8, 30, 0),
				new GregorianCalendar(2014, 11, 1, 11, 00));
		myEvents.add(basketballGame);
		myEvents.add(hockeyGame);
		myEvents.add(lacrossGame);
		myEvents.add(soccerGame);
		
		doSearch("men, hockey, retrievers", myEvents);
		doSearch("men's lacross game, final four, free,, on campus, Stadium", myEvents);
		
		System.out.println("After December 6th:");
		 ArrayList<Event> afterDecSix = Event.filterByDate(myEvents, new GregorianCalendar(2014, 11, 6, 00,00));
		for(int i = 0; i < afterDecSix.size(); i++){
			System.out.println(afterDecSix.get(i) + "\n");
			//System.out.println("Relevance: " + searchResults.get(i).getRelevance() + "\n");
		}
		System.out.println("--------------------");
		
		
		ArrayList<Event> inDecember = new ArrayList<Event>();
		inDecember = Event.filterByDate(myEvents, new GregorianCalendar(2014, 11, 1, 00,00),
				new GregorianCalendar(2014, 11, 31, 23, 59));
		for(Event evt : inDecember){
			System.out.println(evt);
		}
		System.out.println("--------------------");
		
		
		System.out.println("All events sorted by date:");
		Event.sortByDate(myEvents);
		for(int i = 0; i < myEvents.size(); i++){
			System.out.println(myEvents.get(i) + "\n");
		}
		System.out.println("--------------------");
		
		System.out.println(soccerGame);
		soccerGame.calcRelevance("basketball, retrievers, final four, women, march madness, ,");
		soccerGame.calcRelevance("Mens Soccer game, athletics, mens, retriever fever, stadium, final four, free");
	}
	
	public static void doSearch(String tags, ArrayList<Event> events){
		ArrayList<Event> searchResults = Event.searchByTags(tags, events);
		System.out.println("Searching for tags: " + tags);
		for(Event evt : searchResults){
			System.out.println(evt);
			System.out.println("Relevance: " + evt.getRelevance());
		}
		System.out.println("--------------------");
	}
	
	*/
}



