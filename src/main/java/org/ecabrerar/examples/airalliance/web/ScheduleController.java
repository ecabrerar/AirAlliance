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
import org.ecabrerar.examples.airalliance.jaxb.data.Schedule;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ecabrerar
 */
@Named
@SessionScoped
public class ScheduleController  implements Serializable{
    
    private static final long serialVersionUID = -3240069895629955984L;
    private final String baseUri = "http://localhost:8080/AirAlliance/webapi/schedules";    
    
    private Schedule schedule;   
    private List<Schedule> schedules;

    public ScheduleController() {
    }
   

    /**
     * @return the schedule
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * @param schedule the schedule to set
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * @return the schedules
     */
    public List<Schedule> getSchedules() {
        
         Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(baseUri);
        
        return webTarget
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Schedule>>() {});

        

    }
    

    /**
     * @param schedules the schedules to set
     */
    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
   

}
