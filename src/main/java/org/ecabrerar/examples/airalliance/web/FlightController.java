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

package org.ecabrerar.examples.airalliance.web;

import java.io.Serializable;
import java.util.ArrayList;
import org.ecabrerar.examples.airalliance.jaxb.data.Flight;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 * @author ecabrerar
 */
@Model
public class FlightController implements Serializable{

    private static final long serialVersionUID = 1L;

	private Flight currentFlight;

    @Inject
    private FlightBean flightBean;

    public FlightController() {

    }

    /**
     * @return the currentFlight
     */
    public Flight getCurrentFlight() {
        return currentFlight;
    }

    /**
     * @param currentFlight the currentFlight to set
     */
    public void setCurrentFlight(Flight currentFlight) {
        this.currentFlight = currentFlight;

    }

     public List<Flight> retrieveFlights() {
        List<Flight> flights;

        if (this.currentFlight == null) {

            flights = flightBean.getFlights();
        } else {
            flights = new ArrayList<>();
            flights.add(flightBean.getFlightById(currentFlight.getId()));
        }

        return flights;
    }

    public String viewFlight(Flight flight){
        this.setCurrentFlight(flight);

        return "flightinfo";
    }
}
