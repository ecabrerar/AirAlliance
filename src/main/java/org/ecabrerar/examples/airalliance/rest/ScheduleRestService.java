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

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.ecabrerar.examples.airalliance.entities.Schedule;
import org.ecabrerar.examples.airalliance.service.ScheduleService;

/**
 *
 * @author ecabrerar
 */

@Path("/schedules")
public class ScheduleRestService implements IRestService{

    @Inject
    ScheduleService  scheduleService;

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(Schedule entity) {
        scheduleService.create(entity);

        return Response.created(UriBuilder.fromPath(String.valueOf(entity.getId())).build()).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Integer id, Schedule entity) {

    	if (entity == null) {
    		return Response.status(Status.BAD_REQUEST).build();
    	}

    	if (!id.equals(entity.getId())) {
    		return Response.status(Status.CONFLICT).entity(entity).build();
    	}

    	if (scheduleService.count(Schedule.class) == 0) {
    		return Response.status(Status.NOT_FOUND).build();
    	}

    	try {

    		scheduleService.edit(entity);

    	} catch (OptimisticLockException e) {
    		return Response.status(Status.CONFLICT).entity(e.getEntity()).build();
    	}

    	return Response.noContent().build();

    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Integer id) {

    	Schedule entity = scheduleService.find(Schedule.class, id);

    	 if (entity == null) {
             return Response.status(Status.NOT_FOUND).build();
         }

        scheduleService.remove(entity);

        return Response.noContent().build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Integer id) {

    	Schedule entity;

    	 try {
    		 entity = scheduleService.find(Schedule.class, id);
         } catch (NoResultException nre) {
             return Response.status(Status.NOT_FOUND).build();
         }

    	 return  Response.ok(entity).build();

    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Schedule> findAll() {
        return scheduleService.findAll(Schedule.class);
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Schedule> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return scheduleService.findRange(Schedule.class, new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(scheduleService.count(Schedule.class));
    }

}
