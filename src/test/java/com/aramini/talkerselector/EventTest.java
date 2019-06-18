package com.aramini.talkerselector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.aramini.talkerselector.exceptions.NoParticipantsException;

import java.util.ArrayList;
import java.util.GregorianCalendar;


class EventTest 
{
	
	@Test
	void addParticipantFailAndSuccess() 
	{
		Event e = new Event("Test Event", new GregorianCalendar());
		e.addParticipant("Mario Rossi");
		
		assertEquals(false, e.addParticipant("Mario Rossi"));
		assertEquals(true, e.addParticipant("Felice Bianchi"));
	}
	
	@Test
	void removeParticipantFailAndSuccess() 
	{
		Event e = new Event("Test Event", new GregorianCalendar());
		e.addParticipant("Felice Bianchi");
				
		assertEquals(false, e.removeParticipant("Luca Rossi"));
		assertEquals(true, e.removeParticipant("Felice Bianchi"));
		assertEquals(1, e.getRemovedPeople().size());
	}
	
	@Test
	void getRandomTalkerNoParticipants() 
	{
		Event e = new Event("Test Event", new GregorianCalendar());
				
		assertThrows(NoParticipantsException.class, ()-> {e.getRandomTalker();});
	}
	
	@Test
	void getRandomTalkerSuccess() throws NoParticipantsException 
	{
		Event e = new Event("Test Event", new GregorianCalendar());
		e.addParticipant("Mario Rossi");
		e.addParticipant("Felice Bianchi");
		e.addParticipant("Alessio Verdi");
		e.removeParticipant("Alessio Verdi");
				
		assertEquals(true, e.getParticipants().contains(e.getRandomTalker()));
		assertEquals(1, e.getRemovedPeople().size());
		assertEquals(true, e.getRemovedPeople().containsKey("Alessio Verdi"));
	}

}
