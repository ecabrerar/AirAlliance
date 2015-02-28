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
import org.ecabrerar.examples.airalliance.jaxb.data.Sector;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ecabrerar
 */
@Named
@SessionScoped
public class SectorController implements Serializable{

    private Client client;

    private final String baseUri = "http://localhost:8080/AirAlliance/webapi";

    private Sector sector;   
    private List<Sector> sectors;

    public SectorController() {
    }

    @PostConstruct
    private void init() {
        client = ClientBuilder.newClient();
    }

    /**
     * @return the sector
     */
    public Sector getSector() {
        return sector;
    }

    /**
     * @param sector the sector to set
     */
    public void setSector(Sector sector) {
        this.sector = sector;
    }

    /**
     * @return the sectors
     */
    public List<Sector> getSectors() {

        List<Sector> returnedSectors = client.target(baseUri)
                                .path("/sectors")
                                .request(MediaType.APPLICATION_JSON)
                                .get(new GenericType<List<Sector>>() {
                                      });

        return returnedSectors;
    }
    
    
    /**
     * @param sectors the sectors to set
     */
    public void setSectors(List<Sector> sectors) {
        this.sectors = sectors;
    }

    @PreDestroy
    public void close() {
        client.close();
    }


}
