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


package org.ecabrerar.examples.airalliance.rest;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.ecabrerar.examples.airalliance.entities.Itinerary;
import org.ecabrerar.examples.airalliance.service.ItineraryService;

/**
 *
 * @author ecabrerar
 */

@Path("/itineraries")
@ApplicationScoped
public class ItineraryRestService  {
    
    @Inject
    ItineraryService itineraryService;
    

    @POST   
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Itinerary entity) {
        itineraryService.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Itinerary entity) {
        itineraryService.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        itineraryService.remove(itineraryService.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Itinerary find(@PathParam("id") Integer id) {
        return itineraryService.find(id);
    }

    @GET   
    @Produces({MediaType.APPLICATION_JSON})
    public List<Itinerary> findAll() {
        return itineraryService.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Itinerary> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return itineraryService.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(itineraryService.count());
    }

    
    
}
