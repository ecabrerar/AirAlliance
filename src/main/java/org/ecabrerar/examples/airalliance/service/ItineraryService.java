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
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import org.ecabrerar.examples.airalliance.entities.Guest;
import org.ecabrerar.examples.airalliance.entities.Itinerary;
import org.ecabrerar.examples.airalliance.entities.Schedule;

/**
 *
 * @author ecabrerar
 */
@Stateless
public class ItineraryService extends BaseEntityService<Itinerary> {

    @Inject
    GuestService guestService;

    @Inject
    SectorService sectorService;

    @Inject
    ScheduleService scheduleService;

    public ItineraryService() {
        super(Itinerary.class);
    }

    @Override
    public Itinerary create(@NotNull Itinerary itinerary) {

        if (itinerary.getGuest() != null && itinerary.getGuest().getId() == 0) {

            Guest guest = guestService.findGuest(itinerary.getGuest().getFirstname(), itinerary.getGuest().getLastname());

            if (guest == null) {
                getEntityManager().persist(itinerary.getGuest());
                guest = itinerary.getGuest();
            }

            itinerary.setGuest(guest);
        }

        if (itinerary.getFlight() != null && itinerary.getFlight().getId() == 0) {

            getEntityManager().persist(itinerary.getFlight());
        }

        if (itinerary.getScheduleId() != null && itinerary.getScheduleId().getId() == 0) {

            Schedule schedule = scheduleService.findScheduleByDate(itinerary.getScheduleId().getScheduleDate());

            if (schedule == null) {
                getEntityManager().persist(itinerary.getScheduleId());
                schedule = itinerary.getScheduleId();
            }

            itinerary.setScheduleId(schedule);

        }

        getEntityManager().persist(itinerary);

        return itinerary;
    }
}
