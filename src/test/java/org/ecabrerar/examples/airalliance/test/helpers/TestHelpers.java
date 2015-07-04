package org.ecabrerar.examples.airalliance.test.helpers;

import java.time.LocalDate;

import org.ecabrerar.examples.airalliance.entities.Flight;
import org.ecabrerar.examples.airalliance.entities.Guest;
import org.ecabrerar.examples.airalliance.entities.Itinerary;
import org.ecabrerar.examples.airalliance.entities.Schedule;
import org.ecabrerar.examples.airalliance.entities.Sector;

/**
 * @author ecabrerar
 * @date   Jul 3, 2015
 */
public class TestHelpers {

	private TestHelpers() { }

	public static Sector createSector(){

		Sector sector = new Sector();
		sector.setSector("BLR");

		return sector;
	}

	public static Guest createGuest(){
		Guest guest = new Guest();
		guest.setFirstname("Frank");
		guest.setLastname("Jennings");

		return guest;
	}

	public static Flight createFlight(Sector destSector, Sector sourceSector){
		Flight flight = new Flight();
		flight.setName("AA056");
		flight.setDestSectorId(destSector);
		flight.setSourceSector(sourceSector);
		return flight;
	}


	public static Schedule createSchedule(Flight flight, Guest guest){
		Schedule schedule = new Schedule();
		schedule.setFlight(flight);
		schedule.setGuest(guest);
		schedule.setScheduleDate(LocalDate.of(2008,11,01));

		return schedule;
	}

	public static Itinerary createItinerary(Flight flight, Guest guest, Schedule schedule){
		Itinerary itinerary = new Itinerary();
		itinerary.setFlight(flight);
		itinerary.setGuest(guest);
		itinerary.setScheduleId(schedule);

		return itinerary;
	}

}
