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

package org.ecabrerar.examples.airalliance.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.ecabrerar.examples.airalliance.entities.Itinerary;

/**
 *
 * @author ecabrerar
 */
@Stateless
public class ItineraryService extends BaseEntityService<Itinerary>{

    @PersistenceContext(unitName = "AirAlliancePU")
    private EntityManager entityManager;
    
    public ItineraryService() {
        super(Itinerary.class);
    }

    @Override
    public void create(Itinerary itinerary) {
        

        
        if(itinerary.getGuest() !=null && itinerary.getGuest().getId() == null){
            entityManager.persist(itinerary.getGuest());
        }
        
        if(itinerary.getFlight()!=null && itinerary.getFlight().getId()==null){
            entityManager.persist(itinerary.getFlight());
        }
        
        if(itinerary.getScheduleId()!=null && itinerary.getScheduleId().getId()==null){
            entityManager.persist(itinerary.getScheduleId());            
        }
        
        entityManager.persist(itinerary);
    }
    
    
    
}
