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

package org.ecabrerar.examples.airalliance.formbean;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.MediaType;
import org.ecabrerar.examples.airalliance.jaxrs.client.FlightRestClient;

/**
 *
 * @author ecabrerar
 */
@Named(value = "flight")
@RequestScoped
public class FlightController {
    @Inject
    private FlightRestClient rc;
    private Flight currentFlight;
   // List<Flight> flights = new ArrayList<>();
    FacesContext facesContext = FacesContext.getCurrentInstance();
    private static final Logger logger = Logger.getLogger(FlightController.class.getName());
    
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
            logger.log(Level.WARNING, "current flight is null");
            
            flights = rc.getFlights();
        } else {
            flights = rc.getFlightById(Integer.parseInt(currentFlight.getId()));
        }
        
        return flights;        
    }

    public String viewFlight(Flight flight){
        this.setCurrentFlight(flight);
        
        return "flightinfo";
    }
}
