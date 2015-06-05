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

import java.util.Date;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import org.ecabrerar.examples.airalliance.entities.Schedule;

/**
 *
 * @author ecabrerar
 */
@Stateless
public class ScheduleService extends BaseEntityService<Schedule> {

    public ScheduleService() {
        super(Schedule.class);
    }

    public Schedule findScheduleByDate(@NotNull Date scheduleDate) {

        return  getEntityManager()
                .createQuery("SELECT s FROM Schedule s WHERE s.scheduleDate=:scheduleDate", Schedule.class)
                .setParameter("scheduleDate", scheduleDate)
                .getSingleResult();
       
    }

}
