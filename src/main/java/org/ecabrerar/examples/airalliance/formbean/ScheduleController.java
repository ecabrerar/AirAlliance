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
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.ecabrerar.examples.airalliance.jaxrs.client.ScheduleRestClient;

/**
 *
 * @author ecabrerar
 */
@Named(value = "schedule")
@RequestScoped
public class ScheduleController {
    
    @Inject
    ScheduleRestClient rc;
    
    private Schedule itinerary = new Schedule();
    private List<Schedule> itineraries = new ArrayList<>();
    FacesContext facesContext = FacesContext.getCurrentInstance();

    public ScheduleController() {
    }

    /**
     * @return the itinerary
     */
    public Schedule getItinerary() {
        return itinerary;
    }

    /**
     * @param itinerary the itinerary to set
     */
    public void setItinerary(Schedule itinerary) {
        this.itinerary = itinerary;
    }

    /**
     * @return the itineraries
     */
    public List<Schedule> getItineraries() {
       
        itineraries = rc.getSchedules();
        
        return itineraries;
    }
    
    
    
    
}
