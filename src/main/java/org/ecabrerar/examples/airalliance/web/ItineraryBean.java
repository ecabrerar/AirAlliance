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

import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.ecabrerar.examples.airalliance.jaxb.data.Itinerary;

/**
 *
 * @author ecabrerar
 */
@Stateless
public class ItineraryBean {

    private Client client;

    private final String baseUri = "http://localhost:8080/AirAlliance/webapi/itineraries";

    public ItineraryBean() {
    }

    @PostConstruct
    private void init() {
        client = ClientBuilder.newClient();
    }
    
    public void createReservation(Itinerary itinerary){
         client.target(baseUri)
                 .request()
                 .post(Entity.entity(itinerary,MediaType.APPLICATION_JSON));
     }
    

    public Itinerary getItineraries(int idItinerary) {
         Itinerary itinerary = client.target(baseUri)
                .path("{id}")
                .resolveTemplate("id", String.valueOf(idItinerary))
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<Itinerary>(){});

        return itinerary;
    }

    public List<Itinerary> getItineraries() {
         List<Itinerary> itineraries = client.target(baseUri)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Itinerary>>(){});
        
        return itineraries;
    }  

    @PreDestroy
    public void close() {
        client.close();
    }
}
