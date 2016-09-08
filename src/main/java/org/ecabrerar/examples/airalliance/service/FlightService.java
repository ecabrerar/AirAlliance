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

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;

import org.ecabrerar.examples.airalliance.entities.Flight;
import org.ecabrerar.examples.airalliance.entities.Sector;

/**
 *
 * @author ecabrerar
 */
@Stateless
public class FlightService extends BaseEntityService {

    @Inject
    private EntityManager entityManager;

    @Inject
    private SectorService sectorService;

    @Override
    protected EntityManager getEntityManager() {
       return entityManager;
    }

    public Flight create(@NotNull Flight flight){

    	if(flight.getDestSectorId() !=null && flight.getDestSectorId().getId() == 0){

    		  Sector destSector = sectorService.findSector(flight.getDestSectorId().getSector())
    				  			  .orElse(sectorService.create(flight.getDestSectorId()));

              flight.setDestSectorId(destSector);
    	}


    	if(flight.getSourceSector() !=null && flight.getSourceSector().getId() == 0){

    		 Sector sourceSector = sectorService.findSector(flight.getSourceSector().getSector())
    				 			   .orElse(sectorService.create(flight.getSourceSector()));

            flight.setSourceSector(sourceSector);

    	}

    	return create(flight);

    }


    /**
     * This method accepts two sectors and returns all the available flights between
     * those two sectors querying the Flights table.
     * Pass the source sector (Example, SFO) and the destination sector.
     *
     * Returns an List of Flights.
     * @param sourceSectorId
     * @param destSectorId
     * @return
    */
    public List<Flight> getAvailableFlights(@NotNull int sourceSectorId, @NotNull int destSectorId){

    	return entityManager
    			.createQuery("select f from Flight f where f.sourceSector.id=:sourceSectorId  AND f.destSector.id=:destSectorId", Flight.class)
    			.setParameter("sourceSectorId", sourceSectorId)
    			.setParameter("destSectorId", destSectorId)
    			.getResultList();



    }


}
