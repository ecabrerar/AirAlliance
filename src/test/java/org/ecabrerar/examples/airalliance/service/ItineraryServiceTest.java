/*
 * Copyright 2014 ecabrerar.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ecabrerar.examples.airalliance.service;

import static junit.framework.TestCase.assertNotNull;
import static org.ecabrerar.examples.airalliance.test.helpers.TestHelpers.createItinerary;

import javax.inject.Inject;

import org.ecabrerar.examples.airalliance.converters.LocalDateAdapter;
import org.ecabrerar.examples.airalliance.converters.LocalDateConverter;
import org.ecabrerar.examples.airalliance.entities.BaseEntity;
import org.ecabrerar.examples.airalliance.entities.Flight;
import org.ecabrerar.examples.airalliance.entities.Guest;
import org.ecabrerar.examples.airalliance.entities.Itinerary;
import org.ecabrerar.examples.airalliance.entities.Schedule;
import org.ecabrerar.examples.airalliance.entities.Sector;
import org.ecabrerar.examples.airalliance.producers.EntityManagerProducer;
import org.ecabrerar.examples.airalliance.test.helpers.TestHelpers;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author ecabrerar
 */
@RunWith(Arquillian.class)
public class ItineraryServiceTest {

    @Inject
    private ItineraryService itineraryService;

    @Inject
    private ScheduleService scheduleService;

    @Inject
    private FlightService flightService;

    @Inject
	private GuestService guestService;

    @Deployment
    public static Archive<?> deployment() {
		WebArchive webArchive = ShrinkWrap
				.create(WebArchive.class)
				.addClasses(BaseEntity.class, Flight.class, Guest.class,
						Itinerary.class, Schedule.class, Sector.class,
						ItineraryService.class, GuestService.class,	ScheduleService.class,
						EntityManagerProducer.class, BaseEntityService.class,
						SectorService.class, FlightService.class, LocalDateAdapter.class,
						LocalDateConverter.class,TestHelpers.class)
				.addAsResource("META-INF/test_persistence.xml","META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        System.out.println(webArchive.toString(true));
        return webArchive;
    }

    @Test
    public void itineraryServiceShouldNotBeNull() {
        assertNotNull(itineraryService);
    }

    @Test
    @UsingDataSet(value = {"schedule.yml","guests.json","sectors.json", "flight.json"})
    @ShouldMatchDataSet(value = {"itinerary.yml"}, excludeColumns = {"id"})
    public void save_NewItinerary_ShouldBeCreated() throws Exception {

        Guest guest = guestService.find(Guest.class, 1);
        Flight flight = flightService.find(Flight.class, 1);
        Schedule schedule = scheduleService.find(Schedule.class, 1);

        Itinerary  saved =  itineraryService.createItinerary(createItinerary(flight, guest, schedule));

        assertNotNull(saved);
        assertNotNull(saved.getId());
    }

    @Test
    @UsingDataSet(value = {"itinerary.yml","schedule.yml","guests.json","sectors.json", "flight.json"})
    @ShouldMatchDataSet(value = {"itinerary.yml"}, excludeColumns = {"id"})
    public void get_ExistingItinerary_Found() throws Exception {

        Itinerary itinerary = itineraryService.find(Itinerary.class, 1);
        assertNotNull(itinerary);
    }


}
