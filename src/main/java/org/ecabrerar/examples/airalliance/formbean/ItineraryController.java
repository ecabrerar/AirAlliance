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
import javax.annotation.PostConstruct;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.ecabrerar.examples.airalliance.jaxrs.client.FlightRestClient;

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

    @Inject
    FlightRestClient fc;

    private Itinerary itinerary;
    private List<Itinerary> itineraries = new ArrayList<>();
    FacesContext facesContext = FacesContext.getCurrentInstance();

    private static final Logger logger = Logger.getLogger(ItineraryController.class.getName());

    public ItineraryController() {
    }

    @PostConstruct
    public void init() {
        itinerary = new Itinerary();
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
        Logger.getLogger(ItineraryController.class.getName()).log(Level.INFO, "Itineraries {0}", itineraries);
    }

    public void confirmReservation() {

        if (this.itinerary.getId() > 0) {

            int id = this.itinerary.getId();
            getItineraries(id);

        } 
    }

    /**
     * This method accepts the itinerary information from the guests and updates
     * the DB.
     *
     * @param itin
     * @return
     */
    public String processReservation(Itinerary itin) {

        String status = "message";

        try {

            if (validate()) {
                rc.createReservation(itin);
               
                facesContext.addMessage(status, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Your itinerary has been processed successfully.", itin.toString()));

            } else {

                StringBuilder strMessage = new StringBuilder(45)
                        .append(" Itinerary Rejected !.")
                        .append(" Your itinerary has been rejected.")
                        .append(" There is a similiar itinerary present in our records with the same guest name, flight details and travel date..");

                 
                facesContext.addMessage(status, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        strMessage.toString(), null));

            }
        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
            facesContext.addMessage(status,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "New Itinerary cannot be added", ex.getMessage()));
        }

        return "processitinerary";

    }

    /**
     * Validate if return flights between two sectors querying the Flights
     * table.
     *
     * @return
     */
    private boolean validate() {

        Integer availableFlights = fc.getAvailableFlights(itinerary.getFlight().getSource().getId(), itinerary.getFlight().getDest().getId());

        return availableFlights == 0;
    }

}
