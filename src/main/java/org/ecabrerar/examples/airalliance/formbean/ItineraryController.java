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
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
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
    private String message;
    
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
    }  
    
    
    
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void confirmReservation(AjaxBehaviorEvent event){        
       
        if (this.itinerary.getId().trim().length() > 0) {
            
           int  id= Integer.parseInt(this.itinerary.getId());
            getItineraries(id);
            
            if(itineraries.isEmpty()){                             
                setMessage("No record found. Please check the Itinerary ID");                
            }
            
        } else {
             setMessage("No record found. Please check the Itinerary ID");  
        }
    }

    /** 
     *   Guests are allowed to cancel reservation based on their Itinerary
     *   IID. This method flushes the itinerary records from the Itinerary table and the Schedule table.
     *   However, the guest information is retained.
     *
     *   @param itinerary
    */
    public void cancelReservation(Itinerary itinerary){
        rc.remove(itinerary);
    }
    
    public void processReservation(){
        
    }
    
    
}
