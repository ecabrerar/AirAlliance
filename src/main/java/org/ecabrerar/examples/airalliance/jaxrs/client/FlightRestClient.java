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
package org.ecabrerar.examples.airalliance.jaxrs.client;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.ecabrerar.examples.airalliance.formbean.Flight;
import org.ecabrerar.examples.airalliance.formbean.Sector;

/**
 *
 * @author ecabrerar
 */
@Stateless
public class FlightRestClient {

    private  Client client;
    private final String baseUri = "http://localhost:8080/AirAlliance/webapi/flights";

    public FlightRestClient() {        
    }
        
    @PostConstruct
    private void init() {       
        client = ClientBuilder.newClient();
    }

    public List<Flight> getFlights() {

        String data = client.target(baseUri)
                        .request(MediaType.APPLICATION_JSON)
                        .get(String.class);

        /* Parse the data using the document object model approach */    
        List<Flight> flights = new ArrayList<>();

        try (JsonReader reader = Json.createReader(new StringReader(data))) {

            for (JsonValue result : reader.readArray()) {

                JsonObject object = (JsonObject) result;

                Flight flight = new Flight();
                flight.setId(object.getJsonNumber("id").toString());
                flight.setName(object.getString("name"));

                JsonObject sourceObj = object.getJsonObject("sourceSector");

                Sector source = new Sector();
                source.setId(sourceObj.getJsonNumber("id").toString());
                source.setSector(sourceObj.getString("sector"));

                flight.setSource(source);
                JsonObject destObj = object.getJsonObject("destSector");

                Sector dest = new Sector();
                dest.setId(destObj.getJsonNumber("id").toString());
                dest.setSector(destObj.getString("sector"));

                flight.setDest(dest);

                flights.add(flight);
            }

        }

        return flights;
    }
    
   public List<Flight> getFlightById(int flightId){
         String data = client.target(baseUri)
                        .path(String.valueOf(flightId))
                        .request(MediaType.APPLICATION_JSON)
                        .get(String.class);
     
        /* Parse the data using the document object model approach */    
        List<Flight> flights = new ArrayList<>();

        try (JsonReader reader = Json.createReader(new StringReader(data))) {       

                JsonObject object = reader.readObject();

                Flight flight = new Flight();
                flight.setId(object.getJsonNumber("id").toString());
                flight.setName(object.getString("name"));

                JsonObject sourceObj = object.getJsonObject("sourceSector");

                Sector source = new Sector();
                source.setId(sourceObj.getJsonNumber("id").toString());
                source.setSector(sourceObj.getString("sector"));

                flight.setSource(source);
                JsonObject destObj = object.getJsonObject("destSector");

                Sector dest = new Sector();
                dest.setId(destObj.getJsonNumber("id").toString());
                dest.setSector(destObj.getString("sector"));

                flight.setDest(dest);

                flights.add(flight);            

        }

        return flights;
     }
    

    @PreDestroy
    public void close() {
        client.close();
    }

}
