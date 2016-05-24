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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.ecabrerar.examples.airalliance.jaxb.data.Flight;

/**
 *
 * @author ecabrerar
 */
@Stateless
public class FlightBean extends AbstractBaseRestClient{

    private  WebTarget webTarget;

    public FlightBean() {
    }

    @PostConstruct
    private void initWebTarget() {
        webTarget  = getWebTarget();
    }

    public List<Flight> getFlights() {

     return webTarget.path("flights")
                     .request(MediaType.APPLICATION_JSON)
                     .get(new GenericType<List<Flight>>(){});

    }

   public Flight getFlightById(int flightId){
         return webTarget
                        .path("flights/{id}")
                        .resolveTemplate("id", String.valueOf(flightId))
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<Flight>(){});


     }

     public int getAvailableFlights(Integer source, Integer dest){

         Map<String,Object> templateValues = new HashMap<>();
         templateValues.put("source", String.valueOf(source));
         templateValues.put("dest", String.valueOf(dest));

         String availableFlights = webTarget
                        .path("flights")
                        .path("{source}")
                        .path("{dest}")
                        .resolveTemplates(templateValues)
                        .request(MediaType.TEXT_PLAIN)
                        .get(String.class);

       return Integer.parseInt(availableFlights);

     }

}
