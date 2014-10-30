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
import javax.ws.rs.core.UriBuilder;
import org.ecabrerar.examples.airalliance.formbean.Sector;

/**
 *
 * @author ecabrerar
 */
@Stateless
public class SectorRestClient {
    private  URI uri;
    private  Client client;
    
    public SectorRestClient() {         
    }
    
    @PostConstruct
    private void init() {
        uri = UriBuilder
                .fromUri("http://localhost:8080/AirAlliance/webapi/sectors")
                .port(8080).build();
        client = ClientBuilder.newClient();
    }
    
    public List<Sector> getAllSector() {

        String data = client.target(uri)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);

        /* Parse the data using the document object model approach */    
        List<Sector> sectors = new ArrayList<>();

        try (JsonReader reader = Json.createReader(new StringReader(data))) {

            for (JsonValue result : reader.readArray()) {

                JsonObject object = (JsonObject) result;

                Sector sector = new Sector();
                sector.setId(object.getJsonNumber("id").toString());
                sector.setSector(object.getString("sector"));

                sectors.add(sector);
            }
        }

        return sectors;
    }

    @PreDestroy
    public void close() {
        client.close();
    }
}
