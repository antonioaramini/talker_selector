package com.aramini.talkerselector;

import java.util.GregorianCalendar;
import java.util.Map.Entry;
import java.util.Scanner;

import com.aramini.talkerselector.exceptions.NoParticipantsException;


public class EventInputManager 
{
    public static void main( String[] args )
    {
    	EventInputManager em = new EventInputManager();
    	em.manageEventMainMenu();
    }
    
    private void manageEventMainMenu()
    {
    	Event e = new Event("Main Event", new GregorianCalendar());
    	boolean exit = false;
    	while(!exit)
    	{
	        System.out.println("Enter a command among the following: ");
	        System.out.println("* 'add <name>' to add a participant to the event.");
	        System.out.println("* 'rmv <name>' to remove a participant from the event.");
	        System.out.println("* 'lst' to print the current participants list.");
	        System.out.println("* 'rem' to print the list of removed people.");
	        System.out.println("* 'rnd' to select a random talker among the participants.");
	        System.out.println("* 'exit' to quit the program.");
	
	
	    	Scanner in = new Scanner(System.in);
	        String s = in.nextLine();
	        if (s.toLowerCase().equals("exit"))
	        	exit = true;
	        else if (s.length() >= 3)
	        	switch(s.substring(0,3).toLowerCase())
	        	{
	        	case "add": addParticipantToEvent(s, e);
	        		break;
	        	case "rmv": removeParticipantFromEvent(s, e);
	        		break;
	        	case "lst": printParticipantsList(e);
	        		break;
	        	case "rem": printRemovedPeopleList(e);
	        		break;
	        	case "rnd": printRandomTalker(e);
	        				exit = true;
	        		break;
	        	default:break;
	        	}
        
    	}
    }
    
    private void addParticipantToEvent(String addCommand, Event e)
    {
    	if (addCommand.length() > 4)
    	{
    		if (e.addParticipant(addCommand.substring(4)))
    			System.out.println("Participant added!");
    		else
    			System.out.println("ERROR: Participant was not added!");
    	}
    	else
			System.out.println("ERROR: Command string was too short!");
    }
    
    private void removeParticipantFromEvent(String rmvCommand, Event e)
    {
    	if (rmvCommand.length() > 4)
    	{
    		if (e.removeParticipant(rmvCommand.substring(4)))
    			System.out.println("Participant removed!");
    		else
    			System.out.println("ERROR: The specified name was not a participant!");
    	}
    	else
			System.out.println("ERROR: Command string was too short!");
    }
    
    private void printParticipantsList(Event e)
    {
    	if (e.getParticipants() != null)
    		for(String name:e.getParticipants())
    			System.out.println(name);
    }
    
    private void printRemovedPeopleList(Event e)
    {
    	if (e.getRemovedPeople() != null)
    		for(Entry<String,GregorianCalendar> entry:e.getRemovedPeople().entrySet())
    			System.out.println(entry.getKey()+" "+getReadableDate(entry.getValue()));
    }
    
    private void printRandomTalker(Event e)
    {
    	try 
    	{
			System.out.println("Today's talker is "+e.getRandomTalker());
		} catch (NoParticipantsException e1) 
    	{
			System.out.println("No participants were added to the event!");
		}
    }
    
    private String getReadableDate(GregorianCalendar calendar)
	{
		return calendar.get(GregorianCalendar.DAY_OF_MONTH)+"/"+calendar.get(GregorianCalendar.MONTH)+"/"+calendar.get(GregorianCalendar.YEAR)
				+"   "+calendar.get(GregorianCalendar.HOUR)+":"+calendar.get(GregorianCalendar.MINUTE)
				+((calendar.get(GregorianCalendar.AM_PM) == GregorianCalendar.AM)? "AM" : "PM"); 
	}
}
