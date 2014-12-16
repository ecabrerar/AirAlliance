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
import org.ecabrerar.examples.airalliance.entities.Flight;

/**
 *
 * @author ecabrerar
 */
@Stateless
public class FlightService extends BaseEntityService<Flight> {

    public FlightService() {
        super(Flight.class);
    }
    
    /**
     * This method accepts two sectors and returns all the available flights between
     *  those two sectors querying the Flights table.
     *  Pass the source sector (Example, SFO) and the destination sector.
     *   Returns an List of Flights.
     * @return
    */
    public List<Flight> getAvailableFlights(){
        
        return null;
        
    }

}
