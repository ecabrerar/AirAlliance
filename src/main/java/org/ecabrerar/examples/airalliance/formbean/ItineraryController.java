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
import org.ecabrerar.examples.airalliance.jaxrs.client.ItineraryRestClient;

/**
 *
 * @author ecabrerar
 */
@Named(value = "itinerary")
@RequestScoped
public class ItineraryController {

    @Inject
    ItineraryRestClient rc;

    private Itinerary itinerary = new Itinerary();
    private List<Itinerary> itineraries = new ArrayList<>();
    FacesContext facesContext = FacesContext.getCurrentInstance();

    private static final Logger logger = Logger.getLogger(ItineraryController.class.getName());

    public ItineraryController() {
    }

    /**
     * @return the itinerary
     */
    public Itinerary getItinerary() {
        return itinerary;
    }

    /**
     * @param itinerary the itinerary to set
     */
    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    /**
     * @return the flights
     */
    public List<Itinerary> getItineraries() {
        itineraries = rc.getItineraries();

        return itineraries;
    }

    public List<Itinerary> getListItinerary() {
        return itineraries;
    }

    private void getItineraries(int idItinerary) {
        itineraries = rc.getItineraries(idItinerary);
        Logger.getLogger(ItineraryController.class.getName()).log(Level.WARNING, "Itineraries {0}", itineraries);
    }

    public void confirmReservation() {

        if (this.itinerary.getId().trim().length() > 0) {

            int id = Integer.parseInt(this.itinerary.getId());
            getItineraries(id);

        } else {
            logger.log(Level.WARNING, "current event is null");
        }
    }

    /**
     * This method accepts the itinerary information from the guests and updates
     * the DB.
     *
     * @return
     */
    public String processReservation() {

        return null;

    }

}
