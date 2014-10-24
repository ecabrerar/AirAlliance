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
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.ecabrerar.examples.airalliance.formbean.Flight;
import org.ecabrerar.examples.airalliance.formbean.Guest;
import org.ecabrerar.examples.airalliance.formbean.Schedule;
import org.ecabrerar.examples.airalliance.formbean.Sector;

/**
 *
 * @author ecabrerar
 */
@Stateless
public class ScheduleRestClient {

    private final URI uri;
    private final Client client;
    
    public ScheduleRestClient() {
         uri = UriBuilder
                .fromUri("http://localhost:8080/AirAlliance/webapi/schedules")
                .port(8080).build();
        client = ClientBuilder.newClient();
    }
    
    
    public List<Schedule> getSchedules(){
        
         String data = client.target(uri)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);
         
         
          /* Parse the data using the document object model approach */    
        List<Schedule> schedules = new ArrayList<>();

        try (JsonReader reader = Json.createReader(new StringReader(data))) {

            for (JsonValue result : reader.readArray()) {

               JsonObject object = (JsonObject) result;               
              
               JsonObject scheduleObject = object.getJsonObject("schedule");
               
               Schedule schedule = new Schedule();
               schedule.setId(scheduleObject.getString("id"));
               schedule.setScheduleDate(scheduleObject.getString("scheduleDate"));
               
                JsonObject guestObject = scheduleObject.getJsonObject("guest");
                
                Guest guest = new Guest();
                guest.setId(guestObject.getJsonNumber("id").toString());
                guest.setFirstName(guestObject.getString("firstName"));
                guest.setLastName(guestObject.getString("lastName"));
                
                schedule.setGuest(guest);
                
                JsonObject flightObject = scheduleObject.getJsonObject("flight");                

                Flight flight = new Flight();
                flight.setId(flightObject.getJsonNumber("id").toString());
                flight.setName(flightObject.getString("name"));

                JsonObject sourceObj = flightObject.getJsonObject("sourceSector");

                Sector source = new Sector();
                source.setId(sourceObj.getJsonNumber("id").toString());
                source.setSector(sourceObj.getString("sector"));

                flight.setSource(source);
                JsonObject destObj = object.getJsonObject("destSector");

                Sector dest = new Sector();
                dest.setId(destObj.getJsonNumber("id").toString());
                dest.setSector(destObj.getString("sector"));

                flight.setDest(dest);
                
                schedule.setFlight(flight);
     
                schedules.add(schedule);
            }

        }
        
        return schedules;      
    }
    
    public void close() {
        client.close();
    }
    
}
