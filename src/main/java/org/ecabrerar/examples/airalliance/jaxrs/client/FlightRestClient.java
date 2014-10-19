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

import java.net.URI;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.UriBuilder;
import org.ecabrerar.examples.airalliance.formbean.Flight;

/**
 *
 * @author ecabrerar
 */
@Stateless
public class FlightRestClient {

    private final URI uri;
    private final Client client;

    public FlightRestClient() {
        uri = UriBuilder
                .fromUri("http://localhost:8080/AirAlliance/rest/flights")
                .port(8080).build();
        client = ClientBuilder.newClient();
    }

    public List<Flight> getFlights() {
        List<Flight> flights = client.target(uri)
                .request()
                .get(new GenericType<List<Flight>>() {
                });
        return flights;
    }

    public void close() {
        client.close();
    }
}
