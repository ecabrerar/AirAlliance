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
import javax.persistence.EntityManager;

import org.ecabrerar.examples.airalliance.entities.BaseEntity;
import org.ecabrerar.examples.airalliance.entities.Flight;
import org.ecabrerar.examples.airalliance.entities.Guest;
import org.ecabrerar.examples.airalliance.entities.Itinerary;
import org.ecabrerar.examples.airalliance.entities.Schedule;
import org.ecabrerar.examples.airalliance.entities.Sector;
import org.ecabrerar.examples.airalliance.producers.EntityManagerProducer;
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
    private EntityManager entityManager;

    @Deployment
    public static Archive<?> deployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class)
                .addClasses(Itinerary.class, Guest.class, Flight.class, Sector.class, Schedule.class,
                        ItineraryService.class, GuestService.class, ScheduleService.class,
                        SectorService.class, EntityManagerProducer.class, BaseEntityService.class,
                        BaseEntity.class
                )
                .addAsResource("META-INF/test_persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        System.out.println(webArchive.toString(true));
        return webArchive;
    }

    @Test
    public void itineraryServiceShouldNotBeNull() {
        assertNotNull(itineraryService);
    }

    @Test
    @UsingDataSet(value = {"guests.json", "flight.json","schedule.yml"})
    @ShouldMatchDataSet(value = {"itinerary.yml"}, excludeColumns = {"id"})
    public void save_NewItinerary_ShouldBeCreated() throws Exception {

        Guest guest = entityManager.find(Guest.class, 1);
        Flight flight = entityManager.find(Flight.class, 1);
        Schedule schedule = entityManager.find(Schedule.class, 1);

        Itinerary  saved =  itineraryService.createItinerary(createItinerary(flight, guest, schedule));

        assertNotNull(saved);
        assertNotNull(saved.getId());
    }

    @Test
    @UsingDataSet({"schedule.yml","sector.json", "flight.json"})
    @ShouldMatchDataSet(value = {"schedule.yml"}, excludeColumns = {"id"})
    public void get_ExistingItinerary_Found() throws Exception {

        Itinerary itinerary = itineraryService.find(Itinerary.class, 1);
        assertNotNull(itinerary);
    }


}
