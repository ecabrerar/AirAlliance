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
package org.ecabrerar.examples.airalliance.it.service;

import static junit.framework.TestCase.assertNotNull;
import static org.ecabrerar.examples.airalliance.test.helpers.TestHelpers.createSchedule;

import java.time.LocalDate;

import javax.inject.Inject;

import org.ecabrerar.examples.airalliance.entities.Flight;
import org.ecabrerar.examples.airalliance.entities.Guest;
import org.ecabrerar.examples.airalliance.entities.Schedule;
import org.ecabrerar.examples.airalliance.service.FlightService;
import org.ecabrerar.examples.airalliance.service.GuestService;
import org.ecabrerar.examples.airalliance.service.ScheduleService;
import org.ecabrerar.examples.airalliance.util.Deployments;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author ecabrerar
 */
@RunWith(Arquillian.class)
public class ScheduleServiceTest {

    @Inject
    private ScheduleService scheduleService;

    @Inject
    private FlightService flightService;

    @Inject
	private GuestService guestService;

    @Deployment
    public static Archive<?> deployment() {

    	WebArchive webArchive = Deployments.getBaseDeployment();
        System.out.println(webArchive.toString(true));

        return webArchive;
    }

    @Test
    public void scheduleServiceShouldNotBeNull() {
        assertNotNull(scheduleService);
    }

    @Test
    @UsingDataSet(value = {"guests.json","sectors.json", "flight.json"})
    @ShouldMatchDataSet(value = {"schedule.yml"}, excludeColumns = {"id"})
    public void save_NewSchedule_ShouldBeCreated() throws Exception {

        Guest guest = guestService.find(Guest.class, 1);
        Flight flight = flightService.find(Flight.class, 1);

        Schedule saved = scheduleService.create(createSchedule(flight, guest));

        assertNotNull(saved);
        assertNotNull(saved.getId());
    }

    @Test
    @UsingDataSet(value = {"schedule.yml","guests.json","sectors.json", "flight.json"})
    @ShouldMatchDataSet(value = {"schedule_updated.yml"}, excludeColumns = {"id"})
    public void shouldUpdateSchedule(){

    	 Schedule schedule = scheduleService.find(Schedule.class, 1);

    	 schedule.setScheduleDate(LocalDate.of(2016,9,14));

    	 scheduleService.edit(schedule);
    }

    @Test
    @UsingDataSet(value = {"schedule.yml","guests.json","sectors.json", "flight.json"})
    @ShouldMatchDataSet(value = {"schedule.yml"}, excludeColumns = {"id"})
    public void get_ExistingSchedule_Found() throws Exception {

        Schedule schedule = scheduleService.find(Schedule.class, 1);
        assertNotNull(schedule);
    }

}
