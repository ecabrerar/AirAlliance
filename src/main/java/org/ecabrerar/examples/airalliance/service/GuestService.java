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
import javax.validation.constraints.NotNull;
import org.ecabrerar.examples.airalliance.entities.Guest;

/**
 *
 * @author ecabrerar
 */
@Stateless
public class GuestService extends BaseEntityService<Guest>{

    public GuestService() {
        super(Guest.class);
    }
    
    public Guest findGuest(@NotNull String firstName, @NotNull String lastName){
        
      return  getEntityManager()
              .createQuery(" SELECT g FROM Guest g WHERE g.firstname=:firstName AND g.lastname=:lastName", Guest.class)
              .setParameter("firstName", firstName)
              .setParameter("lastName", lastName)
              .getSingleResult();
                   
        
    }
}
