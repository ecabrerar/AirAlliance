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
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.ecabrerar.examples.airalliance.jaxb.data.Flight;

/**
 *
 * @author ecabrerar
 */
@Stateless
public class FlightBean {

    private  Client client;
    private final String baseUri = "http://localhost:8080/AirAlliance/webapi/flights";

    public FlightBean() {        
    }
        
    @PostConstruct
    private void init() {       
        client = ClientBuilder.newClient();
    }

    public List<Flight> getFlights() {

       List<Flight> flights = client.target(baseUri)
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<List<Flight>>(){});

       

        return flights;
    }
    
   public Flight getFlightById(int flightId){
         Flight flight = client.target(baseUri)
                        .path("{id}")
                        .resolveTemplate("id", String.valueOf(flightId))
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<Flight>(){});
    
        return flight;
     }
   
     public int getAvailableFlights(Integer source, Integer dest){
         
         Map<String,Object> templateValues = new HashMap<>();
         templateValues.put("source", String.valueOf(source));
         templateValues.put("dest", String.valueOf(dest));
         
         String availableFlights = client.target(baseUri)
                        .path("{source}")
                        .path("{dest}")
                        .resolveTemplates(templateValues)                        
                        .request(MediaType.TEXT_PLAIN)
                        .get(String.class);
         
       return Integer.parseInt(availableFlights);

     }    
     

    @PreDestroy
    public void close() {
        client.close();
    }

}
