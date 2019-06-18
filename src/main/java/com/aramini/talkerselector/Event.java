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
	
	// Pending invitations
	private ArrayList<String> invitedPeople;
	// People who have accepted
	private ArrayList<String> participants;
	// People who have declined the invitation or have been removed from the Event
	private HashMap<String, GregorianCalendar> removedPeople;
	

	public Event(String name, GregorianCalendar date)
	{
		this.setName(name);
		this.setDate(date);
	}
	
	public Event(String name, GregorianCalendar date, ArrayList<String> invitedPeople)
	{
		this(name,date);
		this.setInvitedPeople(invitedPeople);
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

	public ArrayList<String> getInvitedPeople() 
	{
		return invitedPeople;
	}

	public void setInvitedPeople(ArrayList<String> invitedPeople) 
	{
		this.invitedPeople = invitedPeople;
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
	
	public boolean addInvitedPerson(String name)
	{
		if (invitedPeople == null)
			invitedPeople = new ArrayList<String>();
		if (!invitedPeople.contains(name))
			return invitedPeople.add(name);
		else
			return false;
	}
	
	/*
	 * Called in case a person declined the invitation or we manually remove him/her.
	 */
	public boolean removeInvitedPerson(String name)
	{
		if (invitedPeople == null || !invitedPeople.contains(name))
			return false;
		
		invitedPeople.remove(name);
		if (removedPeople == null)
			removedPeople = new HashMap<String, GregorianCalendar>();
		removedPeople.put(name, new GregorianCalendar());
		return true;
	}
	
	/*
	 * Called when an invitation is accepted by someone OR to manually add a participant "on the fly".
	 */
	public boolean addParticipant(String name)
	{
		if (participants == null)
			participants = new ArrayList<String>();
		
		if (!participants.contains(name))
		{
			if (invitedPeople != null && invitedPeople.contains(name))
				invitedPeople.remove(name);
			return participants.add(name);
		}
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
		if (invitedPeople != null && invitedPeople.size() > 0)
			for(int i=invitedPeople.size()-1;i>=0;i--)
				removeInvitedPerson(invitedPeople.get(i));
		
		if (participants == null || participants.size() == 0)
			throw new NoParticipantsException("No participants are registered for the event!");
		
		Random rnd = new Random();
		return participants.get(rnd.nextInt(participants.size()));
	}
}
