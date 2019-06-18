package com.aramini.talkerselector;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Random;

import com.aramini.talkerselector.exceptions.NoParticipantsException;

public class Event 
{
	private String name;
	private GregorianCalendar date;

	// People who have accepted
	private ArrayList<String> participants;
	// People who have declined the invitation or have been removed from the Event
	private HashMap<String, GregorianCalendar> removedPeople;
	

	public Event(String name, GregorianCalendar date)
	{
		this.setName(name);
		this.setDate(date);
	}
	
	public Event(String name, GregorianCalendar date, ArrayList<String> participants)
	{
		this(name,date);
		this.setParticipants(participants);
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public GregorianCalendar getDate() 
	{
		return date;
	}
	
	public void setDate(GregorianCalendar date) 
	{
		this.date = date;
	}

	public ArrayList<String> getParticipants() 
	{
		return participants;
	}

	public void setParticipants(ArrayList<String> participants) 
	{
		this.participants = participants;
	}

	public HashMap<String, GregorianCalendar> getRemovedPeople() 
	{
		return removedPeople;
	}

	public void setRemovedPeople(HashMap<String, GregorianCalendar> removedPeople) 
	{
		this.removedPeople = removedPeople;
	}
	
	/*
	 * Called when an invitation is accepted by someone OR to manually add a participant "on the fly".
	 */
	public boolean addParticipant(String name)
	{
		if (participants == null)
			participants = new ArrayList<String>();
		
		if (removedPeople != null && removedPeople.containsKey(name))
		{
			removedPeople.remove(name);
			return participants.add(name);
		}
		else if (!participants.contains(name))
			return participants.add(name);
		else
			return false;
	}
	
	/*
	 * Called when someone declines, but previously accepted the invitation OR to manually remove a participant "on the fly".
	 */
	public boolean removeParticipant(String name)
	{
		if (participants == null || !participants.contains(name))
			return false;
		
		participants.remove(name);
		if (removedPeople == null)
			removedPeople = new HashMap<String, GregorianCalendar>();
		removedPeople.put(name, new GregorianCalendar());
		return true;
	}
	
	/*
	 * Called when the event is started in order to select and return a random talker.
	 * It excludes people who have been invited, but have not answered.
	 */
	public String getRandomTalker() throws NoParticipantsException
	{
		if (participants == null || participants.size() == 0)
			throw new NoParticipantsException("No participants are registered for the event!");
		
		Random rnd = new Random();
		return participants.get(rnd.nextInt(participants.size()));
	}
}
