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
import org.ecabrerar.examples.airalliance.jaxb.data.Itinerary;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;


/**
 *
 * @author ecabrerar
 */
@Model
public class ItineraryController implements Serializable{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ItineraryController.class.getName());

    private Itinerary itinerary;
    private List<Itinerary> itineraries;

    FacesContext facesContext  = FacesContext.getCurrentInstance();

    @Inject
    ItineraryBean itineraryBean;

    @Inject
    FlightBean flightBean;

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
        return itineraryBean.getItineraries();
    }

    public List<Itinerary> getListItinerary() {
        return itineraries;
    }

    public void confirmReservation() {

        if (itinerary.getId() > 0) {
            itineraries = new ArrayList<>();
            itineraries.add(itineraryBean.getItineraries(itinerary.getId()));
        }
    }

    /**
     * This method accepts the itinerary information from the guests and updates
     * the DB.
     *
     * @return
     */
    public String processReservation() {

        String status = "message";

        final Itinerary itin = itinerary;

        try {

            if (validate()) {
                itineraryBean.createReservation(itin);

                facesContext.addMessage(status, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Your itinerary has been processed successfully.", itin.toString()));

            } else {

                String strMessage = " Itinerary Rejected !. Your itinerary has been rejected. There is a similiar itinerary present in our records with the same guest name, flight details and travel date..";


                facesContext.addMessage(status, new FacesMessage(FacesMessage.SEVERITY_WARN, strMessage, null));

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

        Integer availableFlights = flightBean.getAvailableFlights(itinerary.getFlight().getSourceSector().getId(), itinerary.getFlight().getDestSector().getId());

        return availableFlights == 0;
    }

    /**
     * @param itineraries the itineraries to set
     */
    public void setItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }
}
