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
import static org.ecabrerar.examples.airalliance.test.helpers.TestHelpers.createFlight;

import javax.inject.Inject;

import org.ecabrerar.examples.airalliance.entities.Flight;
import org.ecabrerar.examples.airalliance.entities.Sector;
import org.ecabrerar.examples.airalliance.service.FlightService;
import org.ecabrerar.examples.airalliance.service.SectorService;
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
public class FlightServiceTest {

    @Inject
    private FlightService flightService;

    @Inject
    private SectorService sectorService;

    @Deployment
    public static Archive<?> deployment() {

    	WebArchive webArchive = Deployments.getBaseDeployment();

		System.out.println(webArchive.toString(true));

		return webArchive;
    }

    @Test
    public void flightServiceShouldBeNotNull() {
        assertNotNull(flightService);
    }

    @Test
    @UsingDataSet(value = {"sectors.json"})
    @ShouldMatchDataSet(value = {"flight.json"}, excludeColumns = {"id"})
    public void save_NewFlight_ShouldBeCreated() throws Exception {

        Sector sourceSector = sectorService.find(Sector.class, 1);

        Sector destSector = sectorService.find(Sector.class, 3);

        Flight saved = flightService.create(createFlight(destSector, sourceSector));

        assertNotNull(saved);
        assertNotNull(saved.getId());
    }

    @Test
    @UsingDataSet(value = {"sectors.json", "flight.json"})
    @ShouldMatchDataSet(value = {"flight.json"}, excludeColumns = {"id"})
    public void get_ExistingFlight_Found() throws Exception {

        Flight flight = flightService.find(Flight.class, 1);
        assertNotNull(flight);
    }

}
