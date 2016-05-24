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
import javax.ejb.Stateless;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.ecabrerar.examples.airalliance.jaxb.data.Itinerary;

/**
 *
 * @author ecabrerar
 */
@Stateless
public class ItineraryBean extends AbstractBaseRestClient {

    private  WebTarget webTarget;

    public ItineraryBean() {
    }

    @PostConstruct
    private void initClient() {
        webTarget = getWebTarget();
    }

    public void createReservation(Itinerary itinerary){
         webTarget
                 .path("itineraries")
                 .request()
                 .post(Entity.entity(itinerary,MediaType.APPLICATION_JSON));
     }


    public Itinerary getItineraries(int idItinerary) {
         return  webTarget
                 .path("itineraries/{id}")
                 .resolveTemplate("id", String.valueOf(idItinerary))
                 .request(MediaType.APPLICATION_JSON)
                 .get(new GenericType<Itinerary>(){});

    }

    public List<Itinerary> getItineraries() {
         return webTarget
                .path("itineraries")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Itinerary>>(){});

    }
}
