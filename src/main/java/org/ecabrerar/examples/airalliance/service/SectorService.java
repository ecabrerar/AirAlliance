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

import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import org.ecabrerar.examples.airalliance.entities.Sector;

/**
 *
 * @author ecabrerar
 */
@Stateless
public class SectorService extends BaseEntityService {

   @Inject
    private EntityManager entityManager;
    
    @Override
    protected EntityManager getEntityManager() {
       return entityManager;
    }

    public Optional<Sector> findSector(@NotNull String sector) {

       return entityManager
               .createQuery("SELECT s FROM Sector s WHERE s.sector=:sector", Sector.class)
               .setParameter("sector", sector)
               .getResultList()
               .stream()
               .findFirst();
        
    }
}
